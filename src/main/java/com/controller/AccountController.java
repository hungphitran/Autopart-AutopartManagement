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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String edit(@ModelAttribute("customer") Customer cus, HttpServletRequest req,RedirectAttributes redirectAttributes) {
	    if (cus.getFullName() == null || cus.getFullName().trim().isEmpty()) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Tên không được để trống");
	        return "redirect:/account.htm";
	    }
	    
	    if (cus.getEmail() == null || cus.getEmail().trim().isEmpty()) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Email không được để trống");
	        return "redirect:/account.htm";
	    }
	    
	    // Email format validation using simple regex
	    String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
	    if (!cus.getEmail().matches(emailRegex)) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Email không hợp lệ");
	        return "redirect:/account.htm";
	    }
	    
	    // If validation passes, update customer
	    boolean updateSuccess = customerDao.update(cus);
	    if (!updateSuccess) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật thông tin");
	        return "redirect:/account.htm";
	    }
	    else {
	        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thông tin thành công");
	    }
		HttpSession session = req.getSession();
		Account acc = (Account) session.getAttribute("user");
		Customer c = customerDao.getByEmail(acc.getEmail());
		session.setAttribute("userName", c.getFullName());	
		return "redirect:/account.htm";
	}
	@RequestMapping(value="/account/changepass",method=RequestMethod.POST)
	public String changePass(HttpServletRequest req,RedirectAttributes redirectAttributes) {
		System.out.println("changepassword");
	    HttpSession session = req.getSession();
	    String pass =(String) req.getParameter("pass");
	    String newpass = (String) req.getParameter("newpass");
	    String confirmpass = (String) req.getParameter("confirmpass");
	    Account acc = (Account) session.getAttribute("user");
	    // Validate inputs
	    if (pass == null || pass.trim().isEmpty()) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Mật khẩu hiện tại không được để trống");
	        return "redirect:/account.htm";
	    }
	    
	    if (newpass == null || newpass.trim().isEmpty()) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Mật khẩu mới không được để trống");
	        return "redirect:/account.htm";
	    }
	    
	    if (confirmpass == null || !confirmpass.equals(newpass)) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Mât khẩu xác nhận không khớp");
	        return "redirect:/account.htm";
	    }
	    
	    // Password complexity check
	    if (newpass.length() < 4) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Mât khẩu mới phải có ít nhất 4 ký tự");
	        return "redirect:/account.htm";
	    }
	    
	    // Validate current password is correct
	    if (!acc.getPassword().equals(pass)) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Mật khẩu hiện tại không đúng");
	        return "redirect:/account.htm";
	    }
	    if(acc.getPassword().equals(pass)) {
	    	acc.setPassword(newpass);
	    	accountDao.update(acc);
	    	redirectAttributes.addFlashAttribute("successMessage", "Đổi mật khẩu thành công");
	    }
		return "redirect:/account.htm";
	}
}
