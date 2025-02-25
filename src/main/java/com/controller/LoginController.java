package com.controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import com.entity.OrderDetail;


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
	
	@Autowired
	OrderDetail_DAO oddao;

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
		String phone= req.getParameter("phone");
		String pass = req.getParameter("password");
		
//		test();
		
		Account acc = accountDao.getByPhone(phone);
		

		if(pass==null||phone==null||pass.length()<4|| phone.length()<10) {//check valid password
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
			Customer c = customerDao.getByPhone(phone);
			req.getSession().setAttribute("userName", c.getFullName());	
			req.setAttribute("cart", cdao.getById(c.getCartId()));
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
		if(session.getAttribute("user")!=null) {//show profile if user logged in
			return "redirect:/account.htm";
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
		
//		 RoleGroup newRoleGroup1 = new RoleGroup("RG001", "Admin Group", "Group for Admins", "Active", new Timestamp(System.currentTimeMillis()));
//		 RoleGroup newRoleGroup2 = new RoleGroup("RG002", "Customer Group", "Group for Customers", "Active", new Timestamp(System.currentTimeMillis()));
//
//	     Account account1 = new Account("0909876543", "securePassword1", "randomToken123", "RG001");
//	     Account account2 = new Account("0901234567", "securePassword2", "randomToken123", "RG002");
//	     BlogGroup blogGroup = new BlogGroup("BG001", "Tech Blogs", "Active", new Timestamp(System.currentTimeMillis()));
//	  // Blog instance
//	     Blog blog = new Blog(
//	         "BLOG001",
//	         "BG001",
//	         "Hướng dẫn chăm sóc cây cảnh",
//	         "Bài viết chia sẻ các tips chăm sóc cây cảnh trong nhà...",
//	         "Active",
//	         new Timestamp(System.currentTimeMillis())
//	     );
//
//	     // Brand instance
//	     Brand brand = new Brand(
//	         "BRAND001",
//	         "Nike",
//	         "Active",
//	         new Timestamp(System.currentTimeMillis())
//	     );
//
//	     // Cart instance
//	     Cart cart = new Cart("CART001", new Timestamp(System.currentTimeMillis()));
//	     // Thêm sản phẩm vào giỏ hàng
////	     cart.getProducts().put("PROD001", 2);
////	     cart.getProducts().put("PROD002", 1);
//
//	     // Customer instance
//	     Customer customer = new Customer(
//	         "CART001",
//	         "Nguyễn Văn B",
//	         "0901234567",
//	         "123 Đường ABC, Quận 1, TP.HCM",
//	         "Active"
//	     );
//
//	     // Employee instance
//	     Employee employee = new Employee(
//	         "079123456789",        // citizenId
//	         "0909876543",         // phone
//	         "Trần Thị A",         // fullName
//	         "tranb@email.com",    // email
//	         Date.valueOf(LocalDate.now()), // birthDate
//	         "Female",             // gender
//	         Date.valueOf(LocalDate.now()),  // startDate
//	         "456 Đường XYZ, Quận 2, TP.HCM", // address
//	         "avatar_url.jpg",     // avatar
//	         "University",         // educationLevel
//	         "Active",            // status
//	         new Timestamp(System.currentTimeMillis())                 // deletedAt
//	     );
//
//	     // Discount instance
//	     Discount discount = new Discount(
//	         "DIS001",                          // discountId
//	         "Giảm giá mùa hè",                  // discountDesc
//	         20,                                 // discountAmount (20%)
//	         new BigDecimal("1000000"),         // minimumAmount
//	         100,                               // usageLimit
//	         Date.valueOf(LocalDate.now()),
//	         Date.valueOf(LocalDate.now()),
//	         "Active"                           // status
//	     );
//	     
//	     // ProductGroup instance
//	     ProductGroup productGroup = new ProductGroup(
//	         "PG001",                       // productGroupId
//	         "Giày Thể Thao",              // groupName
//	         null,                          // parentGroup (null if it's a root group)
//	         "Active",                      // status
//	         new Timestamp(System.currentTimeMillis())                           // deletedAt
//	     );
//	     
//	  // Product instance
//	     Product product = new Product(
//	         "PROD001",                      // productId
//	         "Giày thể thao Nike Air Max",   // productName
//	         "PG001",                        // productGroupId
//	         "BRAND001",                        // brandId
//	         2500000.0,                      // salePrice
//	         1800000.0,                      // costPrice
//	         50,                             // stock
//	         "Đôi",                          // unit
//	         "nike-air-max-1.jpg,nike-air-max-2.jpg",  // imageUrls
//	         0.8,                            // weight
//	         "Active",                       // status
//	         new Timestamp(System.currentTimeMillis()),                          // deletedAt
//	         "Giày thể thao cao cấp từ Nike" // description
//	     );
//
//	     // GeneralSettings instance
//	     GeneralSettings settings = new GeneralSettings(
//	         "Shop Thể Thao Pro",           // websiteName
//	         "logo1.png",                    // logo
//	         "1900123456",                  // phone
//	         "contact@shopthethaopro.com",  // email
//	         "789 Đường DEF, Quận 3, TP.HCM", // address
//	         "© 2025 Shop Thể Thao Pro. All rights reserved." // copyright
//	     );
//
//	     // Order instance
//	     Order order = new Order(
//	         "ORD001",                      // orderId
//	         "DIS001",                     // discountId
//	         "0901234567",                  // userPhone
//	         "123 Đường ABC, Quận 1, TP.HCM", // shipAddress
//	         new BigDecimal("2300000"),     // totalCost
//	         Date.valueOf(LocalDate.now()),               // orderDate
//	         "Pending",                     // status
//	         new Timestamp(System.currentTimeMillis())                           // deletedAt
//       
//	     );
//	     


//	     // OrderDetail instance
//	     OrderDetail orderDetail = new OrderDetail(
//	         "ORD001",                      // orderId
//	         "PROD001",                     // productId
//	         "Giày thể thao Nike Air Max",  // productName
//	         1,                             // amount
//	         new BigDecimal("2500000")      // unitPrice
//	     );
//	     
//	     System.out.println(rgdao.update(newRoleGroup1));
//	     System.out.println(rgdao.update(newRoleGroup2));
//	     System.out.println(rgdao.getById("RG001"));
//	     System.out.println(rgdao.getById("RG002"));
//
//	     
//	     System.out.println(accountDao.update(account1));
//	     System.out.println(accountDao.update(account2));
//	     System.out.println(accountDao.getByPhone("0909876543"));
//	     System.out.println(accountDao.getByPhone("0901234567"));
//
//	     System.out.println(bgdao.update(blogGroup));
//	     System.out.println(bgdao.getByBlogGroupId("BG001"));
//
//	     System.out.println(blogDao.update(blog));
//	     System.out.println(blogDao.getById("BLOG001"));
//
//	     System.out.println(brandDao.update(brand));
//	     System.out.println(brandDao.getByBrandId("BRAND001"));
//
//	     System.out.println(cdao.update(cart));
//	     System.out.println(cdao.getById("CART001"));
//
//	     System.out.println(customerDao.update(customer));
//	     System.out.println(customerDao.getByPhone("0901234567"));
//
//	     System.out.println(employeeDao.update(employee));
//	     System.out.println(employeeDao.getByCitizenId("079123456789"));
//
//	     System.out.println(discountDao.update(discount));
//	     System.out.println(discountDao.getById("DIS001"));
//
//	     System.out.println(productGroupDao.update(productGroup));
//	     System.out.println(productGroupDao.getByProductGroupId("PG001"));
//
//	     System.out.println(productDao.update(product));
//	     System.out.println(productDao.getById("PROD001"));
//
//	     System.out.println(gsdao.update(settings));
//	     System.out.println(gsdao.getByWebsiteName("Shop Thể Thao Pro"));
//
//	     System.out.println(orderDao.update(order));
//	     System.out.println(orderDao.getById("ORD001"));
//
//	     
//	     System.out.println(order.getOrderDate().getClass().getName());
//	     
//	     System.out.println(oddao.getAllByOrderId("ORD001"));
	     
	     
	}
}
