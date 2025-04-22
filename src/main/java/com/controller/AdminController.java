package com.controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dao.Account_DAO;
import com.dao.BlogGroup_DAO;
import com.dao.Blog_DAO;
import com.dao.Brand_DAO;
import com.dao.Customer_DAO;
import com.dao.Employee_DAO;
import com.dao.GeneralSettings_DAO;
import com.dao.OrderDetail_DAO;
import com.dao.Order_DAO;
//import com.dao.Permission_DAO;
import com.dao.ProductGroup_DAO;
import com.dao.Product_DAO;
import com.dao.RoleGroup_DAO;
import com.dao.Discount_DAO;

import com.entity.Account;
import com.entity.Brand;
import com.entity.Product;
import com.entity.ProductGroup;


import com.entity.Customer;
import com.entity.Blog;
import com.entity.BlogGroup;
import com.entity.Discount;
import com.entity.Employee;
import com.entity.Order;
import com.entity.OrderDetail;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	RoleGroup_DAO rgdao;
	
//	@Autowired
//	Permission_DAO pdao;
	
	@Autowired
	Account_DAO accountDao;
	
	@Autowired
	Customer_DAO customerDao;
	
	@Autowired
	Discount_DAO discountDao;

	@Autowired
	Blog_DAO blogDao;
	
	@Autowired
	BlogGroup_DAO blogGroupDao;

	@Autowired
	ProductGroup_DAO productGroupDao;

	@Autowired
	Brand_DAO brandDao;

	@Autowired
	Employee_DAO employeeDao;

	@Autowired
	Order_DAO orderDao;
	
	@Autowired
	OrderDetail_DAO orderDetailDao;

	@Autowired
	Product_DAO productDao;
	
	@Autowired
	GeneralSettings_DAO gsdao;
	
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLogin() {
        return "adminview/account/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String phone, @RequestParam String password, HttpSession session, Model model) {
        Account account = accountDao.getByEmail(phone);
        if (account != null && account.getPassword().equals(password) && !"Deleted".equals(account.getStatus())) {
        	session.setAttribute("account", account);
        	session.setAttribute("permissions", rgdao.getById(account.getPermission()).getPermissions());
    		session.setAttribute("name", employeeDao.getByEmail(account.getEmail()).getFullName());
    		return "redirect:/admin/profile.htm";
        }
        model.addAttribute("error", "Sai số điện thoại hoặc mật khẩu!");
        return "adminview/account/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login.htm";
    }
    
    @RequestMapping(value = "/access-denied", method = RequestMethod.GET)
    public String accessDenied(HttpSession session,HttpServletRequest req) {
    	String referer = req.getHeader("Referer");
    	req.setAttribute("preUrl", referer);
    	System.out.println(referer);
        return "adminview/account/access-denied";
    }
	
	@SuppressWarnings("deprecation")
	@RequestMapping("/statistic")
	public String showStatistic(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Account acc = (Account) session.getAttribute("account");
		
		Date today = new Date(System.currentTimeMillis());
		
		List<Order> orders = orderDao.getOrderByStatus("Completed");
		BigDecimal[] income = new BigDecimal[12];
		
		List<Order> ordersThisYear = new ArrayList<Order>();
		List<Order> ordersThisMonth = new ArrayList<Order>();
		int totalProductThisYear =0;
		
		
		List<Order> ordersLastYear = new ArrayList<Order>();
		List<Order> ordersLastMonth = new ArrayList<Order>();
		int totalProductLastYear =0;
		
		Map<Product,Integer> products =  new TreeMap<Product, Integer>();
		List<String> ids = new ArrayList<String>();
		//List<Order> 
		for(Order o : orders) {
			Date date = o.getOrderDate();
			//if(date.getYear() == today.getYear()){
				ordersThisYear.add(o);
				int month = date.getMonth();
				if(month == today.getMonth()) {
					ordersThisMonth.add(o);
				}
				if(month == today.getMonth()-1) {
					ordersThisMonth.add(o);
				}
				if(income[month]==null) {
					income[month]= BigDecimal.ZERO;
				}
				income[month] = income[month].add(o.getTotalCost());
			
				totalProductThisYear += o.getOrderDetails().size();
				
				List<OrderDetail> details = orderDetailDao.getAllByOrderId(o.getOrderId()) ;
				for(int j=0;j<details.size();j++) {
					if(!ids.contains(details.get(j).getProductId())) {
						products.put(productDao.getById(details.get(j).getProductId()), details.get(j).getAmount());
					}
				}
			//}
			
			if(date.getYear() == today.getYear()-1){
				ordersThisYear.add(o);
			}
			totalProductLastYear+=o.getOrderDetails().size();
		}
		

		req.setAttribute("income", income);
		
		req.setAttribute("totalProductThisYear", totalProductThisYear);
		req.setAttribute("totalProductLastYear", totalProductLastYear);
		req.setAttribute("ordersLastMonth", ordersLastMonth);
		req.setAttribute("ordersThisMonth", ordersThisMonth);
		
		//orders need confirmation
		List<Order> newOrders = orderDao.getOrderByStatus("Pending");
		req.setAttribute("newOrders", newOrders);
		
		//new account this month 
		List<Account> accs = accountDao.getAll();
		List<Account> accsThisMonth = new ArrayList<Account>();
		List<Account> accsLastMonth = new ArrayList<Account>();

		for(Account a: accs) {
			if(a.getCreatedAt().getMonth()== today.getMonth()) {
				accsThisMonth.add(a);
			}
			if(a.getCreatedAt().getMonth()== today.getMonth()-1) {
				accsLastMonth.add(a);
			}
		}
		req.setAttribute("accsLastMonth", accsLastMonth);
		req.setAttribute("accsThisMonth", accsThisMonth);
		req.setAttribute("incomeLastMonth", income[today.getMonth()-1]);
		req.setAttribute("incomeThisMonth", income[today.getMonth()]);
		
		req.setAttribute("products", products);
		
		return "adminview/statistic";
	}
	
	@RequestMapping("/profile")
	public String showProfile(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		Account acc = (Account) session.getAttribute("account");
		if(acc== null) {
			return "redirect:/admin/login.htm";
		}
		model.addAttribute(employeeDao.getByEmail(acc.getEmail()));
		return "adminview/profile";
	}
	
	@RequestMapping(value="/profile/edit", method=RequestMethod.POST)
	public String edit(HttpServletRequest req, @ModelAttribute("employee") Employee e) {
		HttpSession session = req.getSession();
		Account acc = (Account) session.getAttribute("account");
		Employee emp = employeeDao.getByEmail(acc.getEmail());
		emp.setAddress(e.getAddress());
		emp.setBirthDate(e.getBirthDate());
		emp.setFullName(e.getFullName());
		emp.setEmail(e.getEmail());
		emp.setEducationLevel(e.getEducationLevel());
		emp.setGender(e.getGender());
		employeeDao.update(emp);
		session.setAttribute("name", emp.getFullName());
		return "redirect:/admin/profile.htm";
	}
	@RequestMapping(value="/profile/changepass", method= RequestMethod.POST)
	public String changePass(HttpServletRequest req) {
		String pass= req.getParameter("pass");
		String newPass = req.getParameter("newpass");
		String confirmPass = req.getParameter("confirmpass");
		HttpSession session = req.getSession();
		Account acc = (Account) session.getAttribute("account");
		if(pass.equals(acc.getPassword()) && newPass.equals(confirmPass)) {
			acc.setPassword(newPass);
			accountDao.update(acc);
		}

		return "redirect:/admin/profile.htm";
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.removeAttribute("account");
		session.removeAttribute("name");
		return "redirect:/admin/login.htm";
	}
	
}

