package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dao.ProductGroup_DAO;
import com.entity.ProductGroup;

@Controller
@RequestMapping("/admin")
public class AdminProductGroupController {
	@Autowired
	ProductGroup_DAO productGroupDao;
	
//	@RequestMapping("/productGroup")
//	public String showGroup(HttpServletRequest req) {
//		List<ProductGroup> groupList = productGroupDao.getAll();
//		req.setAttribute("productGroups", groupList);
//		return "adminview/productGroup/index";
//	}
}
