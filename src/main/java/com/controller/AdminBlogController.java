package com.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		try
		{
			List<Blog> blogs = blogDao.getAll();
			req.setAttribute("blogs", blogs);
			return "adminview/blog/index";
		}
		catch (Exception e)
		{
			System.out.println("Test1");
	        req.setAttribute("errorMessage", "Tải danh sách bài viết thất bại!"); 
			e.printStackTrace();
			System.out.println("Test2");
			return "adminview/blog/index";
			
		}
		
	}
	
	@RequestMapping(value = "/blog/add", method= RequestMethod.GET)
	public String addBlog(Model model, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			model.addAttribute("blog", new Blog());
			model.addAttribute("nextBlogId", blogDao.generateNextBlogId());
			
			List<BlogGroup> blogGroups = blogGroupDao.getAll();
			req.setAttribute("blogGroupList", blogGroups);
			
			return "adminview/blog/add";
		}
		catch (Exception e)
		{
			System.out.println("Test1");
			redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi khi tải form thêm bài viết!"); 
			e.printStackTrace();
			System.out.println("Test2");
	        return "redirect:/admin/blog.htm";
			
		}

		
	}
	
	@RequestMapping(value = "/blog/add", method = RequestMethod.POST)
    public String addBlogPost(@ModelAttribute("blog") Blog blog, HttpServletRequest req, RedirectAttributes redirectAttributes, HttpSession session) {
		try
		{
			if (blog.getStatus() == null) {
	            blog.setStatus("Inactive");
	        }
	        
	        // Set blogGroupId and blogGroup based on the selected blogGroupId
	        String blogGroupId = req.getParameter("blogGroupId"); // Get from the form
	        if (blogGroupId != null && !blogGroupId.isEmpty()) {
	            BlogGroup blogGroup = blogGroupDao.getById(blogGroupId);
	            blog.setBlogGroupId(blogGroupId);
//	            blog.setBlogGroup(blogGroup);
	        }
	        blog.setCreatedBy((String)session.getAttribute("email"));

	        blogDao.add(blog);
	        redirectAttributes.addFlashAttribute("successMessage", "Thêm bài viết thành công!");  
	        return "redirect:/admin/blog.htm";
		}
		catch (Exception e)
		{
			System.out.println("Test1");
			String referer = req.getHeader("Referer");
			System.out.println(referer);
			redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi khi thêm bài viết!"); 
			e.printStackTrace();
			System.out.println("Test2");
			return "redirect" + referer;
			
		}

        
    }
	
	@RequestMapping(value = "/blog/edit", method= RequestMethod.GET)
	public String editBlog(@RequestParam("blogId") String blogId, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			Blog blog = blogDao.getById(blogId);
			List<BlogGroup> blogGroup = blogGroupDao.getAll();
			req.setAttribute("blog", blog);
			req.setAttribute("blogGroupList", blogGroup);
			
			return "adminview/blog/edit";

		}
		catch (Exception e)
		{
			System.out.println("Test1");
			redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi khi tải bài viết!"); 
			e.printStackTrace();
			System.out.println("Test2");
	        return "redirect:/admin/blog.htm";
			
		}

			}
	
	@RequestMapping(value = "/blog/edit", method = RequestMethod.POST)
    public String editBlogPatch(@ModelAttribute("blog") Blog blog, @RequestParam("blogGroupId") String blogGroupId, HttpSession session, RedirectAttributes redirectAttributes, HttpServletRequest req) {
		
		try
		{
			if (blog.getStatus() == null) {
	            blog.setStatus("Inactive");
	        }
	        
	        // Set blogGroupId and blogGroup based on the selected blogGroupId
	        BlogGroup blogGroup = blogGroupDao.getById(blogGroupId);
	        blog.setBlogGroupId(blogGroupId);
	        //blog.setBlogGroup(blogGroup);
	        
	        blog.setCreatedBy((String)session.getAttribute("email"));
	        blog.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
	        
	        blogDao.update(blog);
	        redirectAttributes.addFlashAttribute("successMessage", "Chỉnh sửa bài viết thành công!");  
	        return "redirect:/admin/blog.htm";

		}
		catch (Exception e)
		{
			System.out.println("Test1");
			String referer = req.getHeader("Referer");
			System.out.println(referer);

			redirectAttributes.addFlashAttribute("errorMessage", "có lỗi khi sửa bài viết!"); 
			e.printStackTrace();
			System.out.println("Test2");
			return "redirect" + referer;
			
		}

            
	}
	
	@RequestMapping(value = "/blog/detail", method= RequestMethod.GET)
	public String detailBlog(@RequestParam("blogId") String blogId, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			Blog blog = blogDao.getById(blogId);
			req.setAttribute("blog", blog);
			return "adminview/blog/detail";

		}
		catch (Exception e)
		{
			System.out.println("Test1");
			redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi khi tải bài viết!"); 
			e.printStackTrace();
			System.out.println("Test2");
	        return "redirect:/admin/blog.htm";
			
		}

			
	}
	
	@RequestMapping("/blog/delete")
	public String deleteBlog(@RequestParam("blogId") String blogId, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			blogDao.delete(blogId);
	        redirectAttributes.addFlashAttribute("successMessage", "Xóa bài viết thành công!");  
			return "redirect:/admin/blog.htm";

		}
		catch (Exception e)
		{
			System.out.println("Test1");
			redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi khi xóa bài viết!"); 
			e.printStackTrace();
			System.out.println("Test2");
	        return "redirect:/admin/blog.htm";
			
		}

			
	}
	
	@RequestMapping(value = "/blog/changeStatus", method= RequestMethod.POST)
	public String changeStatusBlog(@RequestParam("blogId") String blogId, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			blogDao.changeStatus(blogId);
			return "adminview/blog/index";

		}
		catch (Exception e)
		{
			System.out.println("Test1");
			redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi khi thay đổi trạng thái bài viết!"); 
			e.printStackTrace();
			System.out.println("Test2");
	        return "redirect:/admin/blog.htm";
			
		}

	}

}
