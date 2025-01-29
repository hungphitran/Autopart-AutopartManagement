package com.controller;

import java.sql.SQLException;

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

@Controller
public class LoginController {
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

	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(Model model, HttpServletRequest req,HttpServletResponse res) throws ClassNotFoundException {
		
		//get input from request
		String email= req.getParameter("email");
		String pass = req.getParameter("password");
		
		System.out.println(rgdao.getById("RG001"));
		System.out.println(pdao.getById("PER001"));
		System.out.println(accountDao.getByEmail("johndoe@example.com"));
		System.out.println(blogDao.getById("BLOG001"));
		System.out.println(productGroupDao.getByGroupName("Electronics"));
		System.out.println(brandDao.getByBrandName("SuperTech"));
		System.out.println(orderDao.getById("ORD001"));
		System.out.println(productDao.getById("PROD001"));
		System.out.println(employeeDao.getByEmail("johndoe@example.com"));
		System.out.println(gsdao.getByWebsiteName("SuperStore"));



		if(pass.length()<4) {//check valid password and
			req.setAttribute("message", "password is incorrect");
			return "login";			
		}
		
		// add user info to session
		req.getSession().setAttribute("email",email);
		req.getSession().setAttribute("user", "Hung");
		return "redirect:/";////
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String getLogin(HttpServletRequest req,Model  model) {
		HttpSession session = req.getSession();
		if(session.getAttribute("email")!=null) {
			model.addAttribute("email", session.getAttribute("email"));
			return "profile";
		}
		return "login";
	}
	
	@RequestMapping(value="/logout",method = RequestMethod.GET)
	public String logout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.removeAttribute("user");
		session.removeAttribute("email");
		return "login";
	}
}
