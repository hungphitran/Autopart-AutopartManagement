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

import com.dao.Account_DAO;
import com.dao.Customer_DAO;
import com.dao.Order_DAO;
import com.entity.Account;
import com.entity.Customer;
import com.entity.Order;

@Controller
public class AccountController {
	@Autowired
	Account_DAO accountDao;
	@Autowired
	Customer_DAO customerDao;
	
	@Autowired
	Order_DAO orderDao;
	
	@RequestMapping("/account")
	public String showProfile(HttpServletRequest req,Model model) {
		HttpSession session = req.getSession();
		Account acc = (Account) session.getAttribute("user");
		if(acc==null) {
			return "redirect:/login.htm";
		}
		List<Order> orderLst= orderDao.getOrderByEmail(acc.getEmail());
		model.addAttribute("customer",customerDao.getByEmail(acc.getEmail()));
		System.out.println(orderLst);
		req.setAttribute("orders", orderLst);
		return "profile";
	}
	@RequestMapping(value="/account/edit", method = RequestMethod.POST)
	public String edit(@ModelAttribute("customer") Customer cus, HttpServletRequest req) {
		System.out.println(customerDao.update(cus));
		HttpSession session = req.getSession();
		Account acc = (Account) session.getAttribute("user");
		Customer c = customerDao.getByEmail(acc.getEmail());
		req.getSession().setAttribute("userName", c.getFullName());	
		return "redirect:/account.htm";
	}
	@RequestMapping(value="/account/changepass",method=RequestMethod.POST)
	public String changePass(HttpServletRequest req) {
		System.out.println("changepassword");
	    HttpSession session = req.getSession();
	    String pass =(String) req.getParameter("pass");
	    String newpass = (String) req.getParameter("newpass");
	    Account acc = (Account) session.getAttribute("user");
	    System.out.println(pass);
	    if(acc.getPassword().equals(pass)) {
	    	acc.setPassword(newpass);
	    	accountDao.update(acc);
	    	return "redirect:/logout.htm";
	    }
		return "profile";
	}
}
