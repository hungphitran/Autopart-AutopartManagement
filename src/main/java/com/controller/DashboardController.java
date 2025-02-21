package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dao.Account_DAO;
import com.dao.Cart_DAO;
import com.dao.Customer_DAO;
import com.dao.Product_DAO;
import com.entity.Account;
import com.entity.Cart;
import com.entity.Customer;
import com.entity.Product;

@Controller
public class DashboardController {
	
	@Autowired
	Product_DAO productDao;
	@Autowired
	Customer_DAO customerDao;
	@Autowired
	Account_DAO accountDao;
	@Autowired 
	Cart_DAO cartDao;
	
	@RequestMapping("/index")
	public String showDashboard(HttpServletRequest req) {
		
		List<Product> proLst = productDao.getAll();	
		for(int i=0;i<proLst.size();i++) {
			String img= proLst.get(i).getImageUrls();
			proLst.get(i).setImageUrls(img.split(",", i)[0]);
		}
		req.setAttribute("products",proLst.subList(0, 12));//show first 12 products
		//check user in session
		HttpSession session = req.getSession();
		Account acc =(Account) session.getAttribute("user");
		if(acc != null ) {//get cart if user logged in
			Customer cus = customerDao.getByPhone(acc.getPhone());
			Cart cart =cartDao.getById(cus.getCartId());
			req.setAttribute("cart", cart);
		}

		return "dashboard";
	}
}
