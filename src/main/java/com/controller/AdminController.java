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
    public String showStatistic(HttpServletRequest req, @RequestParam(required = false) String fromDate, 
                                @RequestParam(required = false) String toDate) {
        HttpSession session = req.getSession();
        Account acc = (Account) session.getAttribute("account");
        
        // Setup date range
        java.util.Date today = new java.util.Date();
        int currentYear = today.getYear() + 1900;
        int currentMonth = today.getMonth();
        
        java.sql.Date startDate = null;
        java.sql.Date endDate = null;
        
        // Set default date range to current year if not specified
        if (fromDate == null || fromDate.isEmpty()) {
            startDate = new java.sql.Date(currentYear - 1900, 0, 1); // Start of current year
        } else {
            try {
                startDate = java.sql.Date.valueOf(fromDate);
            } catch (Exception e) {
                startDate = new java.sql.Date(currentYear - 1900, 0, 1);
            }
        }
        
        if (toDate == null || toDate.isEmpty()) {
            endDate = new java.sql.Date(today.getTime()); // Today
        } else {
            try {
                endDate = java.sql.Date.valueOf(toDate);
            } catch (Exception e) {
                endDate = new java.sql.Date(today.getTime());
            }
        }
        
        // Format dates for the view
        req.setAttribute("fromDate", startDate.toString());
        req.setAttribute("toDate", endDate.toString());
        
        // Get orders within date range
        List<Order> orders = orderDao.getOrdersByDateRangeAndStatus(startDate, endDate, "Completed");
        
        // Initialize income tracking
        BigDecimal[] income = new BigDecimal[12];
        for (int i = 0; i < income.length; i++) {
            income[i] = BigDecimal.ZERO;
        }
        
        // Initialize collections for statistics
        List<Order> ordersThisYear = new ArrayList<>();
        List<Order> ordersThisMonth = new ArrayList<>();
        List<Order> ordersLastMonth = new ArrayList<>();
        List<Order> ordersLastYear = new ArrayList<>();
        int totalProductThisYear = 0;
        int totalProductLastYear = 0;
        
        // For tracking daily income (for chart)
        List<BigDecimal> dailyIncome = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        
        // Create date labels for each day in the range
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(startDate);
        java.util.Calendar endCal = java.util.Calendar.getInstance();
        endCal.setTime(endDate);
        endCal.add(java.util.Calendar.DATE, 1); // Include end date
        
        // Initialize daily income map
        Map<String, BigDecimal> dailyIncomeMap = new HashMap<>();
        
        while (cal.before(endCal)) {
            String dateStr = new java.sql.Date(cal.getTimeInMillis()).toString();
            labels.add(dateStr);
            dailyIncomeMap.put(dateStr, BigDecimal.ZERO);
            cal.add(java.util.Calendar.DATE, 1);
        }
        
        // Product sales tracking
        Map<Product, Integer> products = new TreeMap<>(new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p2.getProductId().compareTo(p1.getProductId());
            }
        });
        
        // Process orders for statistics
        BigDecimal totalIncome = BigDecimal.ZERO;
        for (Order o : orders) {
            Date orderDate = o.getOrderDate();
            int orderYear = orderDate.getYear() + 1900;
            int orderMonth = orderDate.getMonth();
            
            // Add to monthly income
            income[orderMonth] = income[orderMonth].add(o.getTotalCost());
            
            // Add to total income
            totalIncome = totalIncome.add(o.getTotalCost());
            
            // Add to daily income for chart
            String orderDateStr = orderDate.toString();
            BigDecimal dayIncome = dailyIncomeMap.get(orderDateStr);
            if (dayIncome != null) {
                dailyIncomeMap.put(orderDateStr, dayIncome.add(o.getTotalCost()));
            }
            
            // Categorize orders by year and month
            if (orderYear == currentYear) {
                ordersThisYear.add(o);
                
                // Count products sold this year
                List<OrderDetail> orderDetails = orderDetailDao.getAllByOrderId(o.getOrderId());
                totalProductThisYear += orderDetails.stream().mapToInt(OrderDetail::getAmount).sum();
                
                if (orderMonth == currentMonth) {
                    ordersThisMonth.add(o);
                } else if (orderMonth == (currentMonth > 0 ? currentMonth - 1 : 11)) {
                    ordersLastMonth.add(o);
                }
            } else if (orderYear == currentYear - 1) {
                ordersLastYear.add(o);
                
                // Count products sold last year
                List<OrderDetail> orderDetails = orderDetailDao.getAllByOrderId(o.getOrderId());
                totalProductLastYear += orderDetails.stream().mapToInt(OrderDetail::getAmount).sum();
            }
            
            // Process product sales
            List<OrderDetail> details = orderDetailDao.getAllByOrderId(o.getOrderId());
            for (OrderDetail detail : details) {
                Product product = productDao.getById(detail.getProductId());
                if (product != null) {
                    products.put(product, products.getOrDefault(product, 0) + detail.getAmount());
                }
            }
        }
        
        // Convert daily income map to list in the correct order
        for (String dateStr : labels) {
            dailyIncome.add(dailyIncomeMap.getOrDefault(dateStr, BigDecimal.ZERO));
        }
        
        // Calculate statistics for this month
        BigDecimal incomeThisMonth = income[currentMonth];
        BigDecimal incomeLastMonth = income[currentMonth > 0 ? currentMonth - 1 : 11];
        BigDecimal incomeGrowth = BigDecimal.ZERO;
        
        if (incomeLastMonth.compareTo(BigDecimal.ZERO) > 0) {
            incomeGrowth = incomeThisMonth.subtract(incomeLastMonth)
                         .multiply(new BigDecimal(100))
                         .divide(incomeLastMonth, 2, BigDecimal.ROUND_HALF_UP);
        } else {
            incomeLastMonth = BigDecimal.ONE;
        }
        
        // Calculate product growth
        int productGrowth = 0;
        if (totalProductLastYear > 0) {
            productGrowth = ((totalProductThisYear - totalProductLastYear) * 100) / totalProductLastYear;
        }
        
        // Calculate order growth
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
            if (a.getCreatedAt() != null) {
                int accountMonth = a.getCreatedAt().getMonth();
                if (accountMonth == currentMonth) {
                    accsThisMonth.add(a);
                } else if (accountMonth == (currentMonth > 0 ? currentMonth - 1 : 11)) {
                    accsLastMonth.add(a);
                }
            }
        }
        
        int accountThisMonthCount = accsThisMonth.size();
        int accountLastMonthCount = accsLastMonth.size();
        int accountGrowth = 0;
        if (accountLastMonthCount > 0) {
            accountGrowth = ((accountThisMonthCount - accountLastMonthCount) * 100) / accountLastMonthCount;
        }
        
        // Get pending orders
        List<Order> newOrders = orderDao.getOrderByStatus("Pending");
        
        // Set request attributes for view
        req.setAttribute("income", income);
        req.setAttribute("totalProductThisYear", totalProductThisYear);
        req.setAttribute("totalProductLastYear", totalProductLastYear);
        req.setAttribute("productGrowth", productGrowth);
        req.setAttribute("ordersThisYear", ordersThisYear);
        req.setAttribute("ordersLastYear", ordersLastYear);
        req.setAttribute("ordersLastMonth", ordersLastMonth);
        req.setAttribute("ordersThisMonth", ordersThisMonth);
        req.setAttribute("orderGrowth", orderGrowth);
        req.setAttribute("newOrders", newOrders);
        req.setAttribute("accsLastMonth", accsLastMonth);
        req.setAttribute("accsThisMonth", accsThisMonth);
        req.setAttribute("accountGrowth", accountGrowth);
        req.setAttribute("incomeLastMonth", incomeLastMonth);
        req.setAttribute("incomeThisMonth", incomeThisMonth);
        req.setAttribute("incomeGrowth", incomeGrowth);
        req.setAttribute("products", products);
        req.setAttribute("dailyIncome", dailyIncome);
        req.setAttribute("labels", labels);
        req.setAttribute("totalIncome", totalIncome);
        
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

