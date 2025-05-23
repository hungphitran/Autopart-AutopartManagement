package com.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

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
	
	@RequestMapping(method=RequestMethod.POST)
	public String login(HttpServletRequest req) {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String repassword = req.getParameter("repassword");
		String phone = req.getParameter("phone");
		String address = req.getParameter("address");
		String fullName= req.getParameter("fullName");
		System.out.println(email+password+fullName+repassword+phone+address);
		if(email==null || password==null || repassword==null || phone==null || address==null || fullName==null) {
			req.setAttribute("message", "Vui lòng nhập đầy đủ thông tin");
			return "register";
		}
		//validate
		else if(password.equals(repassword)) {
			Cart newCart= new Cart(cartDao.generateNextCartId(),null);
			if(cartDao.add(newCart)) {
				Account acc =new Account(email,getMD5Hash(password)	,"","RG002","Active", Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), false) ;
				if(accountDao.add(acc)) {
					Customer cus = new Customer(newCart.getCartId(),fullName, email,phone, address, "Active", Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));
					if(customerDao.add(cus)) {
						req.setAttribute("message", "Đăng ký thành công, vui lòng đăng nhập");
						return "redirect:/login.htm";
					}
					else {
						accountDao.delete(email);
						req.setAttribute("message", "Không thể thêm tài khoản");
						return "register";
					}
				}

				else {
					req.setAttribute("message", "Không thể thêm tài khoản");
					return "register";
				}
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
