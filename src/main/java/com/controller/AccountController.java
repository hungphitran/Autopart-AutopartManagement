package com.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

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

import com.utils.ValidationUtils;

@Controller
public class AccountController {
	@Autowired
	Account_DAO accountDao;
	@Autowired
	Customer_DAO customerDao;
	
	@Autowired
	Order_DAO orderDao;
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
	
	@RequestMapping("/account")
	public String showProfile(HttpServletRequest req,Model model, RedirectAttributes redirectAttributes) {
		try
		{
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
		catch (Exception e)
		{
			System.out.println("Test1");
			redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi khi tải thông tin tài khoản!"); 
			e.printStackTrace();
			System.out.println("Test2");
			return "redirect:/index.htm";
			
		}

			
	}
	@RequestMapping(value="/account/edit", method = RequestMethod.POST)
	public String edit(@ModelAttribute("customer") Customer cus, HttpServletRequest req,RedirectAttributes redirectAttributes) {
		try
		{
			if (!ValidationUtils.isValidName(cus.getFullName())) {
		        redirectAttributes.addFlashAttribute("errorMessage", "Tên không hợp lệ");
		        return "redirect:/account.htm";
		    }
		    
		    if (!ValidationUtils.isValidEmail(cus.getEmail())) {
		        redirectAttributes.addFlashAttribute("errorMessage", "Email không hợp lệ");
		        return "redirect:/account.htm";
		    }
		    
		    if (!ValidationUtils.isValidPhone(cus.getPhone())) {
		        redirectAttributes.addFlashAttribute("errorMessage", "Số điện thoại không hợp lệ");
		        return "redirect:/account.htm";
		    }
		    
		    if (!ValidationUtils.isValidAddress(cus.getAddress())) {
		        redirectAttributes.addFlashAttribute("errorMessage", "Địa chỉ không hợp lệ");
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
		catch (Exception e)
		{
			System.out.println("Test1");
			redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi khi chỉnh sửa thông tin tài khoản!"); 
			e.printStackTrace();
			System.out.println("Test2");
			return "redirect:/account.htm";
			
		}

	    
	}
	@RequestMapping(value="/account/changepass",method=RequestMethod.POST)
	public String changePass(HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			HttpSession session = req.getSession();
		    String pass = req.getParameter("pass");
		    String newpass = req.getParameter("newpass");
		    String confirmpass = req.getParameter("confirmpass");
		    Account acc = (Account) session.getAttribute("user");

		    // Validate current password
		    if (!ValidationUtils.isValidPassword(pass)) {
		        redirectAttributes.addFlashAttribute("errorMessage", "Mật khẩu hiện tại không hợp lệ");
		        return "redirect:/account.htm";
		    }

		    // Validate new password
		    if (!ValidationUtils.isValidPassword(newpass)) {
		        redirectAttributes.addFlashAttribute("errorMessage", "Mật khẩu mới không hợp lệ");
		        return "redirect:/account.htm";
		    }

		    // Confirm new password
		    if (!newpass.equals(confirmpass)) {
		        redirectAttributes.addFlashAttribute("errorMessage", "Mật khẩu xác nhận không khớp");
		        return "redirect:/account.htm";
		    }


		    // Check if old password is correct
		    if (!acc.getPassword().equals(getMD5Hash(pass))) {
		        redirectAttributes.addFlashAttribute("errorMessage", "Mật khẩu hiện tại không đúng");
		        return "redirect:/account.htm";
		    }

		    // Update password
		    acc.setPassword(getMD5Hash(newpass));
		    accountDao.update(acc);
		    redirectAttributes.addFlashAttribute("successMessage", "Đổi mật khẩu thành công");

		    return "redirect:/account.htm";

		}
		catch (Exception e)
		{
			System.out.println("Test1");
			redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi khi đổi mật khẩu!"); 
			e.printStackTrace();
			System.out.println("Test2");
			return "redirect:/account.htm";
			
		}
	
	}

}
