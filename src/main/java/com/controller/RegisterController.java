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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dao.Account_DAO;
import com.dao.Cart_DAO;
import com.dao.Customer_DAO;
import com.entity.Account;
import com.entity.Cart;
import com.entity.Customer;
import com.utils.ValidationUtils;
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
	public String login(HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			String repassword = req.getParameter("repassword");
			String phone = req.getParameter("phone");
			String address = req.getParameter("address");
			String fullName= req.getParameter("fullName");
			System.out.println(email + " " +password+  " " + fullName+  " "+repassword+  " "+phone+  " "+address);
			if(email==null || password==null || repassword==null || phone==null || address==null || fullName==null) {
				req.setAttribute("message", "Vui lòng nhập đầy đủ thông tin");
				return "register";
			}
			if(!ValidationUtils.isValidEmail(email) || !ValidationUtils.isValidPassword(password) || !ValidationUtils.isValidPassword(repassword) || !ValidationUtils.isValidPhone(phone) || !ValidationUtils.isValidAddress(address) || !ValidationUtils.isValidName(fullName)) {
				req.setAttribute("message", "Vui lòng nhập thông tin hợp lệ");
				return "register";
			}
			
			if(accountDao.getByEmail(email) != null) {
				req.setAttribute("message", "Email đã được sử dụng");
				return "register";
			}
			
			//validate
			if(password.equals(repassword)) {
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
							accountDao.hardDelete(email);
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
		catch (Exception e)
		{
			System.out.println("Test1");
			redirectAttributes.addFlashAttribute("message", "Có lỗi khi đăng kí!"); 
			e.printStackTrace();
			System.out.println("Test2");
			return "redirect:/register.htm";
			
		}
	
	}
	
	@RequestMapping(method= RequestMethod.GET)
	public String getRegisterPage() {
		return "register";
	}
}
