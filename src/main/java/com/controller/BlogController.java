package com.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String showAll(HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			req.setAttribute("blogs", blogDao.getAll());
			return "blog";

		}
		catch (Exception e)
		{
			System.out.println("Test1");
			redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi khi tải danh sách bài viết!"); 
			e.printStackTrace();
			System.out.println("Test2");
			return "redirect:/index.htm";
			
		}
	
	}   
	
	@RequestMapping("/blog/detail")
	public String showOne(HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			String id = req.getParameter("id");
			Blog blog = blogDao.getById(id);
			String employee = edao.getByEmail(blog.getCreatedBy()).getFullName();
			req.setAttribute("blog",blog);
			req.setAttribute("author",employee);
			return "blogdetail";

		}
		catch (Exception e)
		{
			System.out.println("Test1");
			redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi khi tải bài viết!"); 
			e.printStackTrace();
			System.out.println("Test2");
			return "redirect:/blog.htm";
			
		}
	
	}
}
