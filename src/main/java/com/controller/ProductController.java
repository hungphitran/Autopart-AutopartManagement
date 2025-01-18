package com.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductController {
	@RequestMapping("/detailproduct")
	public String showProduct() {
		return "detailproduct";
	}
	@RequestMapping("/addproduct") // quantity && id
	@ResponseBody
	public String addProduct(HttpServletRequest req) {
		return "Quantity"+ req.getParameter("quantity");
	}
	
	@RequestMapping("/search")
	public String showFilter(HttpServletRequest req) {
		String key = req.getParameter("keyword");
		req.setAttribute("keyword", key);
		
		String[] brands = {"Toyota", "Nissan","Huyndai"};
		String[] categories = {"Lọc lạnh","Lọc gió","Lọc nhớt"};
		
		req.setAttribute("brands",brands);
		req.setAttribute("categories", categories);
		
		req.setAttribute("brand", req.getParameter("brand"));
		req.setAttribute("category",req.getParameter("category"));
		
		return "filterproduct";
	}
	
	
	@RequestMapping("/cart")
	public String showCart() {
		return "cart";
	}
}
