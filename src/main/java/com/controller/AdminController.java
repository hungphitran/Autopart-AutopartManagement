package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dao.Account_DAO;
import com.dao.Blog_DAO;
import com.dao.Brand_DAO;
import com.dao.Employee_DAO;
import com.dao.GeneralSettings_DAO;
import com.dao.Order_DAO;
import com.dao.Permission_DAO;
import com.dao.ProductGroup_DAO;
import com.dao.Product_DAO;
import com.dao.RoleGroup_DAO;
import com.entity.Account;
import com.entity.Product;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	RoleGroup_DAO rgdao;
	
	@Autowired
	Permission_DAO pdao;
	
	@Autowired
	Account_DAO accountDao;

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
	@RequestMapping(value ="/login",method=RequestMethod.GET )
	public String showLogin(HttpServletRequest req) {
		return "adminview/login";
	}
	
	@RequestMapping(value = "/login", method= RequestMethod.POST)
	public String login(HttpServletRequest req) {
		req.setAttribute("title","Tài khoản");
		return "redirect:/admin/account.htm";
	}
	
	@RequestMapping("/statistic")
	public String showStatistic(HttpServletRequest req) {
		req.setAttribute("title","Thống kê");
		return "adminview/statistic";
	}
	
	@RequestMapping("/account")
	public String showInfo(HttpServletRequest req) {
		//HttpSession session = req.getSession();
		//Account account= (Account) session.getAttribute("user");
		req.setAttribute("title","Tài khoản");
		return "adminview/profile";
	}
	
	@RequestMapping("/blog")
	public String showBlog(HttpServletRequest req) {
		return "adminview/blog";
	}
	
	@RequestMapping("/product")
	public String showProducts(HttpServletRequest req) {
		List<Product> products = productDao.getAll();
		req.setAttribute("products", products);
		req.setAttribute("title","Sản phẩm");
		return "adminview/product";
	}
}
