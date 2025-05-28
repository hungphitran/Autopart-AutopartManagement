package com.controller;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import com.entity.XMailer;

@Controller
public class ForgotPasswordController 
{
	@Autowired
	Customer_DAO customerDao;
	
	@Autowired
	Account_DAO accountDao;
	
	 @Autowired
	 XMailer xmailer;
	
	
	@RequestMapping("/forgot-pasword")
	private String showForm()
	{
		return "forgot-password/form";
	}
	
	@RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
    public String forgotPassword(HttpServletRequest req, HttpServletResponse res, ModelMap model) {
        String email = req.getParameter("email");
        System.out.println(email);

        String from = "no-reply@autopart.com"; // Địa chỉ email gửi
        String to = email; // Địa chỉ email người nhận
        String subject = "Khôi phục mật khẩu"; // Tiêu đề email
        String otp = String.valueOf((int) (Math.random() * 900000) + 100000);
        // Nội dung email chứa OTP
        String body = "Chào bạn,\n\n" +
                      "Mã OTP để khôi phục mật khẩu của bạn là: <strong>" + otp + "</strong>\n" +
                      "Vui lòng sử dụng mã này để đặt lại mật khẩu. Mã OTP có hiệu lực trong 5 phút.\n\n" +
                      "Trân trọng,\n" +
                      "Đội ngũ AutoPart";

        if (customerDao.getByEmail(email) == null) {
            model.addAttribute("message", "Email không đúng");
            return "forgot-password/form";
        }

        try {
            xmailer.send(from, to, subject, body);

            // Tạo cookie cho OTP (hết hạn sau 5 phút)
            Cookie otpCookie = new Cookie("otp", otp);
            otpCookie.setMaxAge(5 * 60); // 5 phút
            otpCookie.setHttpOnly(true); // Bảo mật, không cho JavaScript truy cập
            otpCookie.setPath("/"); // Có thể truy cập trên toàn ứng dụng
            res.addCookie(otpCookie);

            // Tạo cookie cho recoveringMail (hết hạn sau 1 giờ)
            Cookie mailCookie = new Cookie("recoveringMail", email);
            mailCookie.setMaxAge(60 * 60); // 1 giờ
            mailCookie.setHttpOnly(true);
            mailCookie.setPath("/");
            res.addCookie(mailCookie);

            // Chuyển hướng đến trang nhập OTP
            return "redirect:/enter-otp.htm";
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("message", "Đã có lỗi xảy ra, vui lòng thử lại!");
            return "forgot-password/form";
        }
    }

    @RequestMapping(value = "/resend-otp", method = RequestMethod.POST)
    public String resendOtp(HttpServletRequest req, HttpServletResponse res, ModelMap model, RedirectAttributes redirectAttributes) {
        // Lấy email từ cookie
        String email = null;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("recoveringMail".equals(cookie.getName())) {
                    email = cookie.getValue();
                    break;
                }
            }
        }

        if (email == null || email.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Email không được tìm thấy trong cookie!");
            return "redirect:/forgot-password.htm";
        }

        if (customerDao.getByEmail(email) == null) {
            redirectAttributes.addFlashAttribute("message", "Email không đúng!");
            return "redirect:/forgot-password.htm";
        }

        try {
            String from = "no-reply@autopart.com";
            String to = email;
            String subject = "Khôi phục mật khẩu";
            String otp = String.valueOf((int) (Math.random() * 900000) + 100000); // Tạo OTP mới
            String body = "Chào bạn,\n\n" +
                          "Mã OTP để khôi phục mật khẩu của bạn là: <strong>" + otp + "</strong>\n" +
                          "Vui lòng sử dụng để đặt lại mật khẩu. Mã OTP có hiệu lực trong 5 phút.\n\n" +
                          "Trân trọng,\nĐội ngũ AutoPart";

            xmailer.send(from, to, subject, body);

            // Tạo cookie mới cho OTP (hết hạn sau 5 phút)
            Cookie otpCookie = new Cookie("otp", otp);
            otpCookie.setMaxAge(5 * 60); // 5 phút
            otpCookie.setHttpOnly(true);
            otpCookie.setPath("/");
            res.addCookie(otpCookie);

            System.out.println("New OTP: " + otp);

            return "redirect:/enter-otp.htm";
        } catch (Exception ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Đã có lỗi xảy ra, vui lòng thử lại!");
            return "redirect:/forgot-password.htm";
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
