package com.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.http.*;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.dao.*;
import com.entity.Account;
import com.entity.Blog;
import com.entity.BlogGroup;
import com.entity.Brand;
import com.entity.Cart;
import com.entity.Customer;
import com.entity.Discount;
import com.entity.Employee;
import com.entity.GeneralSettings;
import com.entity.Order;
import com.entity.Product;
import com.entity.ProductGroup;
import com.entity.RoleGroup;

@Controller
public class LoginController {
	@Autowired
	RoleGroup_DAO rgdao;
	
	@Autowired
	Account_DAO accountDao;
	
	@Autowired 
	Customer_DAO customerDao;
	
	@Autowired 
	Discount_DAO discountDao;
	
//	@Autowired
//	OrderDetail_DAO oddao;

	@Autowired
	Blog_DAO blogDao;

	@Autowired
	ProductGroup_DAO productGroupDao;

	@Autowired
	Brand_DAO brandDao;

	@Autowired
	Employee_DAO employeeDao;

	@Autowired
	Order_DAO orderDao;

	@Autowired
	Product_DAO productDao;
	
	@Autowired
	GeneralSettings_DAO gsdao;
	
	@Autowired
	BlogGroup_DAO bgdao;
	
	@Autowired
	Cart_DAO cdao;

	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(Model model, HttpServletRequest req,HttpServletResponse res) throws ClassNotFoundException {
		
		//get input from request
		String phone= req.getParameter("email");
		String pass = req.getParameter("password");
		
		test();
		Account acc = accountDao.getByPhone(phone);
		

		if(pass==null||phone==null||pass.length()<4) {//check valid password
			req.setAttribute("message", "dữ liệu không hợp lệ");
			return "login";			
		}
		else if(acc==null) {
			req.setAttribute("message", "không tìm thấy tài khoản");
			return "login";	
		}
		else if(pass.equals(acc.getPassword())) {
			// add user info to session
			req.getSession().setAttribute("user", acc);
			return "redirect:/";
		}
		else {
			req.setAttribute("message", "sai email hoặc mật khẩu");
			return "login";		
		}

	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String getLogin(HttpServletRequest req) {
		HttpSession session = req.getSession();
		if(session.getAttribute("user")!=null) {
			return "profile";
		}
		return "login";
	}
	
	@RequestMapping(value="/logout",method = RequestMethod.GET)
	public String logout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.removeAttribute("user");
		return "login";
	}
	
	public void test()
	{
		
		 RoleGroup newRoleGroup1 = new RoleGroup("RG001", "Admin Group", "Group for Admins", "Active", null);
		 RoleGroup newRoleGroup2 = new RoleGroup("RG002", "Customer Group", "Group for Customers", "Active", null);

	     Account account1 = new Account("0909876543", "securePassword", "randomToken123", "RG001");
	     Account account2 = new Account("0901234567", "securePassword", "randomToken123", "RG002");
	     BlogGroup blogGroup = new BlogGroup("BG001", "Tech Blogs", "Active", null);
	  // Blog instance
	     Blog blog = new Blog(
	         "BLOG001",
	         "BG001",
	         "Hướng dẫn chăm sóc cây cảnh",
	         "Bài viết chia sẻ các tips chăm sóc cây cảnh trong nhà...",
	         "Active",
	         null
	     );

	     // Brand instance
	     Brand brand = new Brand(
	         "BRAND001",
	         "Nike",
	         "Active",
	         null
	     );

	     // Cart instance
	     Cart cart = new Cart("CART001", null);
	     // Thêm sản phẩm vào giỏ hàng
//	     cart.getProducts().put("PROD001", 2);
//	     cart.getProducts().put("PROD002", 1);

	     // Customer instance
	     Customer customer = new Customer(
	         "CART001",
	         "Nguyễn Văn A",
	         "0901234567",
	         "123 Đường ABC, Quận 1, TP.HCM",
	         "Active"
	     );

	     // Employee instance
	     Employee employee = new Employee(
	         "079123456789",        // citizenId
	         "0909876543",         // phone
	         "Trần Thị B",         // fullName
	         "tranb@email.com",    // email
	         null, // birthDate
	         "Female",             // gender
	         null,  // startDate
	         "456 Đường XYZ, Quận 2, TP.HCM", // address
	         "avatar_url.jpg",     // avatar
	         "University",         // educationLevel
	         "Active",            // status
	         null                 // deletedAt
	     );

	     // Discount instance
	     Discount discount = new Discount(
	         "DIS001",                          // discountId
	         "Giảm giá mùa hè",                  // discountDesc
	         20,                                 // discountAmount (20%)
	         new BigDecimal("1000000"),         // minimumAmount
	         100,                               // usageLimit
	         null,
	         null,
	         "Active"                           // status
	     );
	     
	     // ProductGroup instance
	     ProductGroup productGroup = new ProductGroup(
	         "PG001",                       // productGroupId
	         "Giày Thể Thao",              // groupName
	         null,                          // parentGroup (null if it's a root group)
	         "Active",                      // status
	         null                           // deletedAt
	     );
	     
	  // Product instance
	     Product product = new Product(
	         "PROD001",                      // productId
	         "Giày thể thao Nike Air Max",   // productName
	         "PG001",                        // productGroupId
	         "BRAND001",                        // brandId
	         2500000.0,                      // salePrice
	         1800000.0,                      // costPrice
	         50,                             // stock
	         "Đôi",                          // unit
	         "nike-air-max-1.jpg,nike-air-max-2.jpg",  // imageUrls
	         0.8,                            // weight
	         "Active",                       // status
	         null,                          // deletedAt
	         "Giày thể thao cao cấp từ Nike" // description
	     );

	     // GeneralSettings instance
	     GeneralSettings settings = new GeneralSettings(
	         "Shop Thể Thao Pro",           // websiteName
	         "logo.png",                    // logo
	         "1900123456",                  // phone
	         "contact@shopthethaopro.com",  // email
	         "789 Đường DEF, Quận 3, TP.HCM", // address
	         "© 2025 Shop Thể Thao Pro. All rights reserved." // copyright
	     );

	     // Order instance
	     Order order = new Order(
	         "ORD001",                      // orderId
	         "DIS001",                     // discountId
	         "0901234567",                  // userPhone
	         "123 Đường ABC, Quận 1, TP.HCM", // shipAddress
	         new BigDecimal("2300000"),     // totalCost
	         null,               // orderDate
	         "Pending",                     // status
	         null                           // deletedAt
	     );

//	     // OrderDetail instance
//	     OrderDetail orderDetail = new OrderDetail(
//	         "ORD001",                      // orderId
//	         "PROD001",                     // productId
//	         "Giày thể thao Nike Air Max",  // productName
//	         1,                             // amount
//	         new BigDecimal("2500000")      // unitPrice
//	     );
	     
//	     System.out.println(rgdao.add(newRoleGroup1));
//	     System.out.println(rgdao.add(newRoleGroup2));
//	     System.out.println(rgdao.getById("RG001"));
//	     System.out.println(rgdao.getById("RG002"));

	     
//	     System.out.println(accountDao.add(account1));
//	     System.out.println(accountDao.add(account2));
//	     System.out.println(accountDao.getByPhone("0909876543"));
//	     System.out.println(accountDao.getByPhone("0901234567"));
//
//	     System.out.println(bgdao.add(blogGroup));
//	     System.out.println(bgdao.getByBlogGroupId("BG001"));
//	     
//	     System.out.println(blogDao.add(blog));
//	     System.out.println(blogDao.getById("BLOGG001"));
	     
//	     System.out.println(brandDao.add(brand));
//	     System.out.println(brandDao.getByBrandId("BRAND001"));
	     
//	     System.out.println(cdao.add(cart));
//	     System.out.println(cdao.getById("CART001"));
//	     
//	     System.out.println(customerDao.add(customer));
//	     System.out.println(customerDao.getByPhone("0901234567"));
	     
//	     System.out.println(employeeDao.add(employee));
//	     System.out.println(employeeDao.getByCitizenId("079123456789"));
	     
//	     System.out.println(discountDao.add(discount));
	     System.out.println(discountDao.getById("DIS001"));
	     
//	     System.out.println(productGroupDao.add(productGroup));
//	     System.out.println(productGroupDao.getByProductGroupId("PG001"));
	  
//	     System.out.println(productDao.add(product));
//	     System.out.println(productDao.getById("PROD001"));
	     
//	     System.out.println(gsdao.add(settings));
//	     System.out.println(gsdao.getByWebsiteName("Shop Thể Thao Pro"));
	     
	     System.out.println(orderDao.add(order));
	     System.out.println(orderDao.getById("ORD001"));
	     
//	     System.out.println(oddao.add(orderDetail));
//	     System.out.println(oddao.getAllByOrderId("ORD001"));
	     
	     
	}
}
