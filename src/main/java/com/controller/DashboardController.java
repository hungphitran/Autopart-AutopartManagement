package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dao.Product_DAO;
import com.entity.Product;

@Controller
public class DashboardController {
	
	@Autowired
	Product_DAO productDao;
	
	@RequestMapping("/index")
	public String showDashboard(HttpServletRequest req) {
		
		List<Product> proLst = productDao.getAll();	
		for(int i=0;i<proLst.size();i++) {
			String img= proLst.get(i).getImageUrls();
			proLst.get(i).setImageUrls(img.split(",", i)[0]);
		}
		req.setAttribute("products",proLst.subList(0, 0));//get 12 first products
		return "dashboard";
	}
}
