package com.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dao.Blog_DAO;

@Controller
public class BlogController {
	@Autowired
	Blog_DAO blogDao;
	
	@RequestMapping("/blog")
	public String showAll(HttpServletRequest req) {
		req.setAttribute("blogs", blogDao.getAll());
		return "blog";
	}
	
	@RequestMapping("/blog/detail")
	public String showOne(HttpServletRequest req) {
		String id = req.getParameter("id");
		
		req.setAttribute("blog",blogDao.getById(id));
		return "blogdetail";
	}
}
