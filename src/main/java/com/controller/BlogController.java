package com.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dao.Blog_DAO;
import com.dao.Employee_DAO;
import com.entity.Blog;

@Controller
public class BlogController {
	@Autowired
	Blog_DAO blogDao;
	
	@Autowired
	Employee_DAO edao;
	
	@RequestMapping("/blog")
	public String showAll(HttpServletRequest req) {
		req.setAttribute("blogs", blogDao.getAll());
		return "blog";
	}   
	
	@RequestMapping("/blog/detail")
	public String showOne(HttpServletRequest req) {
		String id = req.getParameter("id");
		Blog blog = blogDao.getById(id);
		String employee = edao.getByEmail(blog.getCreatedBy()).getFullName();
		req.setAttribute("blog",blog);
		req.setAttribute("author",employee);
		return "blogdetail";
	}
}
