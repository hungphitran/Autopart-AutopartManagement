package com.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.*;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dao.*;
import com.entity.Account;

import com.entity.Cart;
import com.entity.Customer;
import com.entity.Product;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;




@Controller
public class LoginController {
	@Autowired
	RoleGroup_DAO rgdao;
	
	@Autowired
	Account_DAO accountDao;
	
	@Autowired 
	Customer_DAO customerDao;
	
	@Autowired 
	Discount_DAO discountDao;
	
	@Autowired
	OrderDetail_DAO oddao;

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
	
	@Autowired
	BlogGroup_DAO bgdao;
	
	@Autowired
	Cart_DAO cdao;
	
	@Autowired
	Import_DAO importDao;
	
	
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

	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(Model model, HttpServletRequest req,HttpServletResponse res, RedirectAttributes redirectAttributes) {
		try
		{
			//get input from request
			String email= req.getParameter("email");
			String pass = req.getParameter("password");
			
//			test();

			

			if(pass==null||email==null||pass.length()<4|| email.length()<10) {//check valid password
				req.setAttribute("message", "dữ liệu không hợp lệ");
				return "login";			
			}
			

			Account acc = accountDao.getByEmail(email);
			
			Customer c = customerDao.getByEmail(email);
			
			if(acc==null) {
				req.setAttribute("message", "không tìm thấy tài khoản");
				return "login";	
			}
			
			if(c==null) {
				req.setAttribute("message", "không tìm thấy tài khoản");
				return "login";	
			}

			else if(getMD5Hash(pass).equals(acc.getPassword())) {
				// add user info to session
				req.getSession().setAttribute("user", acc);
				req.getSession().setAttribute("userName", c.getFullName());	
				Customer cus = customerDao.getByEmail(acc.getEmail());
				Cart cart =cdao.getById(cus.getCartId());
				Map<String,Integer> productsInCart =cart.getProducts();
				Map<Product,Integer> products= new HashMap<Product, Integer>();
				for(String key : productsInCart.keySet()) {
					products.put(productDao.getById(key),productsInCart.get(key));
				}
				req.getSession().setAttribute("productInCart", products);
				return "redirect:/";
			}
			else {
				req.setAttribute("message", "sai email hoặc mật khẩu");
				return "login";		
			}
		}
		catch (Exception e)
		{
			System.out.println("Test1");
			redirectAttributes.addFlashAttribute("message", "Có lỗi khi đăng nhập!"); 
			e.printStackTrace();
			System.out.println("Test2");
			return "redirect:/login.htm";
			
		}

		
		

	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String getLogin(HttpServletRequest req) {
		HttpSession session = req.getSession();
		if(session.getAttribute("user")!=null) {//show profile if user logged in
			return "redirect:/account.htm";
		}
		return "login";
	}
	
	@RequestMapping(value="/logout",method = RequestMethod.GET)
	public String logout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.removeAttribute("user");
		session.removeAttribute("productInCart");
		return "login";
	}
	
}
