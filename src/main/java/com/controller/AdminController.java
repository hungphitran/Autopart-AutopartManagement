package com.controller;

import java.util.List;

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
import com.entity.Brand;
import com.entity.Product;
import com.entity.ProductGroup;

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
		return "adminview/product/index";
	}
	
	@RequestMapping("/product/add")
	public String addProducts(HttpServletRequest req) {
		return "adminview/product/add";
	}

	@RequestMapping(value = "/product/edit", method= RequestMethod.GET)
	public String editProduct(@RequestParam("productId") String productId, HttpServletRequest req) {
		Product product = productDao.getById(productId);
		List<Brand> brandList = brandDao.getAll();
		List<ProductGroup> productGroupList;
		req.setAttribute("product", product);
		req.setAttribute("brandList", brandList);
		return "adminview/product/edit";
	}
	
	@RequestMapping(value = "/product/edit", method= RequestMethod.POST)
	public String editProductPatch(@ModelAttribute("product") Product product) {
		System.out.println(product.getProductId());
		return null;
	}
	
	@RequestMapping("/product/delete")
	public String deleteProduct(@RequestParam("productId") String productId, HttpServletRequest req) {
		productDao.delete(productId);
		return "redirect:/admin/product.htm";
	}
	
	@RequestMapping(value = "/product/detail", method= RequestMethod.GET)
	public String detailProduct(@RequestParam("productId") String productId, HttpServletRequest req) {
		Product product = productDao.getById(productId);
		req.setAttribute("product", product);
		return "adminview/product/detail";
	}
	
	@RequestMapping(value = "/product/changeStatus", method= RequestMethod.POST)
	public String changeStatus(@RequestParam("productId") String productId) {
		productDao.changeStatus(productId);
		return "adminview/product/index";
	}
}
