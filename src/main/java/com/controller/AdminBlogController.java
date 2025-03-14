package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dao.BlogGroup_DAO;
import com.dao.Blog_DAO;
import com.entity.Blog;
import com.entity.BlogGroup;

@Controller
@RequestMapping("/admin")
public class AdminBlogController {
	@Autowired
	Blog_DAO blogDao;
	
	@Autowired
	BlogGroup_DAO blogGroupDao;
	
	
	// -- blog --
	@RequestMapping("/blog")
	public String showBlogs(HttpServletRequest req) {
		List<Blog> blogs = blogDao.getAll();
		req.setAttribute("blogs", blogs);
		return "adminview/blog/index";
	}
	
	@RequestMapping(value = "/blog/add", method= RequestMethod.GET)
	public String addBlog(Model model, HttpServletRequest req) {
		model.addAttribute("blog", new Blog());
		model.addAttribute("nextBlogId", blogDao.generateNextBlogId());
		
		List<BlogGroup> blogGroups = blogGroupDao.getAll();
		req.setAttribute("blogGroupList", blogGroups);
		
		return "adminview/blog/add";
	}
	
	@RequestMapping(value = "/blog/add", method = RequestMethod.POST)
    public String addBlogPost(@ModelAttribute("blog") Blog blog, HttpServletRequest req) {
        if (blog.getStatus() == null) {
            blog.setStatus("Inactive");
        }
        
        // Set blogGroupId and blogGroup based on the selected blogGroupId
        String blogGroupId = req.getParameter("blogGroupId"); // Get from the form
        if (blogGroupId != null && !blogGroupId.isEmpty()) {
            BlogGroup blogGroup = blogGroupDao.getById(blogGroupId);
            blog.setBlogGroupId(blogGroupId);
            //blog.setBlogGroup(blogGroup);
        }
        blog.setCreatedBy("0901234001");

        blogDao.add(blog);
        return "redirect:/admin/blog.htm";
    }
	
	@RequestMapping(value = "/blog/edit", method= RequestMethod.GET)
	public String editBlog(@RequestParam("blogId") String blogId, HttpServletRequest req) {
		Blog blog = blogDao.getById(blogId);
		List<BlogGroup> blogGroup = blogGroupDao.getAll();
		req.setAttribute("blog", blog);
		req.setAttribute("blogGroupList", blogGroup);
		
		return "adminview/blog/edit";
	}
	
	@RequestMapping(value = "/blog/edit", method = RequestMethod.POST)
    public String editBlogPatch(@ModelAttribute("blog") Blog blog, @RequestParam("blogGroupId") String blogGroupId) {
        if (blog.getStatus() == null) {
            blog.setStatus("Inactive");
        }
        
        // Set blogGroupId and blogGroup based on the selected blogGroupId
        BlogGroup blogGroup = blogGroupDao.getById(blogGroupId);
        blog.setBlogGroupId(blogGroupId);
        //blog.setBlogGroup(blogGroup);
        
        blog.setCreatedBy("0901234001");
        
        blogDao.update(blog);
        return "redirect:/admin/blog.htm";
    }
	
	@RequestMapping(value = "/blog/detail", method= RequestMethod.GET)
	public String detailBlog(@RequestParam("blogId") String blogId, HttpServletRequest req) {
		Blog blog = blogDao.getById(blogId);
		req.setAttribute("blog", blog);
		return "adminview/blog/detail";
	}
	
	@RequestMapping("/blog/delete")
	public String deleteBlog(@RequestParam("blogId") String blogId, HttpServletRequest req) {
		blogDao.delete(blogId);
		return "redirect:/admin/blog.htm";
	}
	
	@RequestMapping(value = "/blog/changeStatus", method= RequestMethod.POST)
	public String changeStatusBlog(@RequestParam("blogId") String blogId) {
		blogDao.changeStatus(blogId);
		return "adminview/blog/index";
	}

}
