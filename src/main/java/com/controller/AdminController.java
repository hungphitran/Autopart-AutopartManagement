package com.controller;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
import com.utils.ValidationUtils;
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
	
	private String getMD5Hash(String input) {
	    try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(input.getBytes());
	        byte[] digest = md.digest();
	        return DatatypeConverter.printHexBinary(digest).toLowerCase();
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLogin() {
        return "adminview/account/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model,RedirectAttributes redirectAttributes) {
        if(!ValidationUtils.isValidEmail(email)) {
			redirectAttributes.addFlashAttribute("errorMessage", "Email không hợp lệ!");
			return "redirect:/admin/login.htm";
        	
        }
        if(!ValidationUtils.isValidPassword(password)) {
        	
        	redirectAttributes.addFlashAttribute("errorMessage", "Mật khẩu không hợp lệ!");
			return "redirect:/admin/login.htm";
		}
		Account account = accountDao.getByEmail(email);
		if (account == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "Sai email hoặc mật khẩu!");
			return "redirect:/admin/login.htm";
		}
        
        System.out.println(account.getPassword()+ getMD5Hash(password)	);
        if (account != null && account.getPassword().equals(getMD5Hash(password) ) && employeeDao.getByEmail(email)!=null) {
        	session.setAttribute("account", account);
        	session.setAttribute("permissions", rgdao.getById(account.getPermission()).getPermissions());
    		session.setAttribute("name", employeeDao.getByEmail(account.getEmail()).getFullName());
    		redirectAttributes.addFlashAttribute("successMessage", "Đăng nhập thành công!");
    		return "redirect:/admin/profile.htm";
        }
        
        redirectAttributes.addFlashAttribute("errorMessage", "Sai email hoặc mật khẩu!");
		return "redirect:/admin/login.htm";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("successMessage", "Đăng xuất thành công!");
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
        
        // Use java.time instead of deprecated Date
        java.time.LocalDate today = java.time.LocalDate.now();
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue() - 1; // 0-based for array indexing
        
        List<Order> orders = orderDao.getOrderByStatus("Completed");
        BigDecimal[] income = new BigDecimal[12];
        // Initialize all array elements to prevent NullPointerException
        for (int i = 0; i < 12; i++) {
            income[i] = BigDecimal.ZERO;
        }
        
        List<Order> ordersThisYear = new ArrayList<>();
        List<Order> ordersThisMonth = new ArrayList<>();
        List<Order> ordersLastMonth = new ArrayList<>();
        List<Order> ordersLastYear = new ArrayList<>();
        int totalProductThisYear = 0;
        int totalProductLastYear = 0;
        
        Map<String, Integer> productSales = new HashMap<>();
        Map<Product, Integer> products = new HashMap<>();
        
        for (Order o : orders) {
            Date orderDate = o.getOrderDate();
            // Convert SQL Date year to actual year (1900 + getYear())
            int orderYear = orderDate.getYear() + 1900;
            // Month is already 0-based (0-11)
            int orderMonth = orderDate.getMonth();
            
            // Process income for all completed orders
            income[orderMonth] = income[orderMonth].add(o.getTotalCost());
            
            // Separate orders by year
            if (orderYear == currentYear) {
                ordersThisYear.add(o);
                totalProductThisYear += o.getOrderDetails().size();
                
                // Separate orders by month
                if (orderMonth == currentMonth) {
                    ordersThisMonth.add(o);
                } else if (orderMonth == (currentMonth > 0 ? currentMonth - 1 : 11)) {
                    ordersLastMonth.add(o);
                }
            } else if (orderYear == currentYear - 1) {
                ordersLastYear.add(o);
                totalProductLastYear += o.getOrderDetails().size();
            }
            
            // Track product sales
            List<OrderDetail> details = orderDetailDao.getAllByOrderId(o.getOrderId());
            for (OrderDetail detail : details) {
                String productId = detail.getProductId();
                int amount = detail.getAmount();
                
                // Update product sales count
                productSales.put(productId, productSales.getOrDefault(productId, 0) + amount);
                
                // Store product object with total sales (only if not already in map)
                if (!products.containsKey(productDao.getById(productId))) {
                    products.put(productDao.getById(productId), amount);
                }
            }
        }
        
        // Pre-calculate growth rates and percentages to avoid division by zero in JSP
        BigDecimal incomeThisMonth = income[currentMonth];
        BigDecimal incomeLastMonth = income[currentMonth > 0 ? currentMonth - 1 : 11];
        BigDecimal incomeGrowth = BigDecimal.ZERO;
        
        if (incomeLastMonth.compareTo(BigDecimal.ZERO) > 0) {
            // Calculate growth only if last month's income is greater than zero
            incomeGrowth = incomeThisMonth.subtract(incomeLastMonth)
                         .multiply(new BigDecimal(100))
                         .divide(incomeLastMonth, 2, BigDecimal.ROUND_HALF_UP);
        }
        else {
        	incomeLastMonth = BigDecimal.ONE;
        }
        
        // Calculate product growth - handle division by zero
        int productGrowth = 0;
        if (totalProductLastYear > 0) {
            productGrowth = ((totalProductThisYear - totalProductLastYear) * 100) / totalProductLastYear;
        }
        
        // Calculate order growth rates
        int orderThisMonthCount = ordersThisMonth.size();
        int orderLastMonthCount = ordersLastMonth.size();
        int orderGrowth = 0;
        if (orderLastMonthCount > 0) {
            orderGrowth = ((orderThisMonthCount - orderLastMonthCount) * 100) / orderLastMonthCount;
        }
        
        // Calculate account growth
        List<Account> accs = accountDao.getAll();
        List<Account> accsThisMonth = new ArrayList<>();
        List<Account> accsLastMonth = new ArrayList<>();

        for (Account a : accs) {
            int accountMonth = a.getCreatedAt().getMonth();
            if (accountMonth == currentMonth) {
                accsThisMonth.add(a);
            } else if (accountMonth == (currentMonth > 0 ? currentMonth - 1 : 11)) {
                accsLastMonth.add(a);
            }
        }
        
        int accountThisMonthCount = accsThisMonth.size();
        int accountLastMonthCount = accsLastMonth.size();
        int accountGrowth = 0;
        if (accountLastMonthCount > 0) {
            accountGrowth = ((accountThisMonthCount - accountLastMonthCount) * 100) / accountLastMonthCount;
        }
        
        // Set attributes for the view
        req.setAttribute("income", income);
        req.setAttribute("totalProductThisYear", totalProductThisYear);
        req.setAttribute("totalProductLastYear", totalProductLastYear);
        req.setAttribute("productGrowth", productGrowth); // Safe pre-calculated value
        req.setAttribute("ordersThisYear", ordersThisYear);
        req.setAttribute("ordersLastYear", ordersLastYear);
        req.setAttribute("ordersLastMonth", ordersLastMonth);
        req.setAttribute("ordersThisMonth", ordersThisMonth);
        req.setAttribute("orderGrowth", orderGrowth); // Safe pre-calculated value
        
        // Orders needing confirmation
        List<Order> newOrders = orderDao.getOrderByStatus("Pending");
        req.setAttribute("newOrders", newOrders);
        
        req.setAttribute("accsLastMonth", accsLastMonth);
        req.setAttribute("accsThisMonth", accsThisMonth);
        req.setAttribute("accountGrowth", accountGrowth); // Safe pre-calculated value
        req.setAttribute("incomeLastMonth", incomeLastMonth);
        req.setAttribute("incomeThisMonth", incomeThisMonth);
        req.setAttribute("incomeGrowth", incomeGrowth); // Safe pre-calculated value
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
	public String edit(HttpServletRequest req, @ModelAttribute("employee") Employee e, RedirectAttributes redirectAttributes) {
		try
		{
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
	        redirectAttributes.addFlashAttribute("successMessage", "Chỉnh sửa tài khoản thành công!"); 

			return "redirect:/admin/profile.htm";
		}
		catch (Exception ex)
		{
			System.out.println("Test1");
	        redirectAttributes.addFlashAttribute("errorMessage", "Chỉnh sửa tài khoản thất bại!"); 
			ex.printStackTrace();
			System.out.println("Test2");
			return "redirect:/admin/profile.htm";
			
		}
		
	}
	@RequestMapping(value="/profile/changepass", method= RequestMethod.POST)
	public String changePass(HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			String pass= req.getParameter("pass");
			String newPass = req.getParameter("newpass");
			String confirmPass = req.getParameter("confirmpass");
			HttpSession session = req.getSession();
			Account acc = (Account) session.getAttribute("account");
			if(getMD5Hash(pass).equals(acc.getPassword()) && newPass.equals(confirmPass)) {
				acc.setPassword(getMD5Hash(pass));
				accountDao.update(acc);
			}
			else {
				throw new Exception("Mật khẩu không đúng hoặc không khớp");
			}
	        redirectAttributes.addFlashAttribute("successMessage", "Đổi mật khẩu thành công!"); 

			return "redirect:/admin/profile.htm";
		}
		catch (Exception e)
		{
			System.out.println("Test1");
	        redirectAttributes.addFlashAttribute("errorMessage", "Đổi mật khẩu thất bại!"); 
			e.printStackTrace();
			System.out.println("Test2");
			return "redirect:/admin/profile.htm";
			
		}
		
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.removeAttribute("account");
		session.removeAttribute("name");
		return "redirect:/admin/login.htm";
	}
	
}

