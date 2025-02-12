package com.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.dao.Account_DAO;
import com.entity.Account;

@Controller
@RequestMapping("/register")
public class RegisterController {
	
	@Autowired
	Account_DAO accountDao;
	
	@RequestMapping(method=RequestMethod.POST)
	public String login(HttpServletRequest req) {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String repassword = req.getParameter("repassword");
		String phone = req.getParameter("phone");
		String address = req.getParameter("address");
		String fullName= req.getParameter("fullName");
		String avt = "https://yt3.ggpht.com/bpKh5MyMojA9WcirbVyVO0I0F0ZqvIMLodbqT_g8pjEUXBNo2ZrZCmiDZ5PTo8lrCBdlTbMd3Q=s88-c-k-c0x00ffffff-no-rj";
		System.out.println(email+password+fullName+repassword+phone+address);
		//validate
		if(password.equals(repassword)) {
			Account acc = new Account(fullName, email, password, repassword, phone, address,avt, null,"Active",null);
			
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
