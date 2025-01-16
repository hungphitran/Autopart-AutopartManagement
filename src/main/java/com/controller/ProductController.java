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
}
