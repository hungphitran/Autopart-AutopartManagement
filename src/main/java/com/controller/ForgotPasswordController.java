package com.controller;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dao.Account_DAO;
import com.dao.Customer_DAO;
import com.entity.Account;

@Controller
public class ForgotPasswordController 
{
	@Autowired
	Customer_DAO customerDao;
	
	@Autowired
	Account_DAO accountDao;
	
	@Autowired
	JavaMailSender mailer;
	
	
	@RequestMapping("/forgot-pasword")
	private String showForm()
	{
		return "forgot-password/form";
	}
	
	@RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
	private String forgotPassword(HttpServletRequest req, ModelMap model)
	{
		String email = req.getParameter("email");
		System.out.println(email);

		
		if(accountDao.getByEmail(email)==null)
		{
			model.addAttribute("message", "Không tìm thấy tài khoản với email này");
			return "forgot-password/form";
		}
		if(customerDao.getByEmail(email)==null)
		{
			model.addAttribute("message", "Email không đúng");
			return "forgot-password/form";
		}
		
		String from = "no-reply@autopart.com"; // Địa chỉ email gửi
	    String to = email; // Địa chỉ email người nhận (lấy từ input)
	    String subject = "Khôi phục mật khẩu"; // Tiêu đề email
	    String otp = String.valueOf((int) (Math.random() * 900000) + 100000);
	 // Nội dung email chứa OTP
	    String body = "Chào bạn,\n\n" +
	                  "Mã OTP để khôi phục mật khẩu của bạn là: <strong>" + otp + "</strong>\n" +
	                  "Vui lòng sử dụng mã này để đặt lại mật khẩu. Mã OTP có hiệu lực trong 5 phút.\n\n" +
	                  "Trân trọng,\n" +
	                  "Đội ngũ AutoPart";
	    

		try{
	        MimeMessage mail = mailer.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
	        helper.setFrom(from, from);
	        helper.setTo(to);
	        helper.setReplyTo(from, from);
	        helper.setSubject(subject);
	        helper.setText(body, true);
	        
	        mailer.send(mail);
	        req.getSession().setAttribute("otp", otp);
	        req.getSession().setAttribute("recoveringMail", email);
	        
	     // Chuyển hướng đến trang nhập OTP
	        return "redirect:/enter-otp.htm";
	    }
	    catch (Exception ex) {
	        ex.printStackTrace();
	        model.addAttribute("message", "Đã có lỗi xảy ra, vui lòng thử lại!");
	        return "forgot-password/form";
	        
	    }
	}
	
	@RequestMapping("enter-otp")
	private String showOtpForm()
	{	
		return "forgot-password/enter-otp";
	}
	
	@RequestMapping(value= "otpVerify", method = RequestMethod.POST)
	private String otpVerify(HttpServletRequest req, ModelMap model, RedirectAttributes redirectAttributes)
	{	
		//req.getSession().setAttribute("otp", "12345");
		System.out.println(req.getSession().getAttribute("otp"));
		String otp = req.getParameter("otp");
		if(otp.equals(req.getSession().getAttribute("otp")))
		{
			model.addAttribute("message", "OTP đúng");
			return "redirect:/enter-password.htm";
		}
		else
		{
			model.addAttribute("message", "OTP không đúng");
			return "redirect:/enter-otp.htm";
		}
		
	}
	
	@RequestMapping("enter-password")
	private String showPasswordForm(HttpServletRequest req)
	{	//req.getSession().setAttribute("recoveringMail", "mail");
		System.out.println(req.getSession().getAttribute("recoveringMail"));
		return "forgot-password/enter-password";
	}
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

	@RequestMapping(value= "updatePassword", method = RequestMethod.POST)
	private String updatePassword(HttpServletRequest req, ModelMap model, RedirectAttributes redirectAttributes)
	{	
		Account account = accountDao.getByEmail((String) req.getSession().getAttribute("recoveringMail"));
		String password = req.getParameter("password");
		String confirmPassword = req.getParameter("confirmPassword");
		System.out.println(account);

		if(password.equals(confirmPassword))
		{
			account.setPassword(getMD5Hash(confirmPassword));
			accountDao.update(account);
			redirectAttributes.addFlashAttribute("successMessage", "Thay đổi mật khẩu thành công");
			return "redirect:/login.htm";
		}
		else
		{
			model.addAttribute("message", "Mật khẩu không khớp");
			return "redirect:/enter-otp.htm";
		}
		
	}
	
}
