package com.controller;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.dao.Account_DAO;
import com.dao.Cart_DAO;
import com.dao.Customer_DAO;
import com.entity.Account;
import com.entity.Cart;
import com.entity.Customer;

@Controller
@RequestMapping("/register")
public class RegisterController {
	
	@Autowired
	Account_DAO accountDao;
	
	@Autowired
	Customer_DAO customerDao;
	@Autowired
	Cart_DAO cartDao;
	
	@RequestMapping(method=RequestMethod.POST)
	public String login(HttpServletRequest req) {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String repassword = req.getParameter("repassword");
		String phone = req.getParameter("phone");
		String address = req.getParameter("address");
		String fullName= req.getParameter("fullName");
		System.out.println(email+password+fullName+repassword+phone+address);
		//validate
		if(password.equals(repassword)) {
//			Cart newCart= new Cart(cartDao.generateNextCartId(),Timestamp.,null);
			Account acc =new Account() ;
			if(accountDao.add(acc)) {
				req.setAttribute("message","Đăng ký thành công. Vui lòng đăng nhập");
				return "redirect:/login";
			}
			else {
				req.setAttribute("message", "Không thể thêm tài khoản");
				return "register";
			}
		}
		
		return "register";
	}
	
	@RequestMapping(method= RequestMethod.GET)
	public String getRegisterPage() {
		return "register";
	}
}
