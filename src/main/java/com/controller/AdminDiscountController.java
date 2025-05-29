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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dao.Discount_DAO;
import com.entity.Discount;

@Controller
@RequestMapping("/admin")
public class AdminDiscountController {
	
	@Autowired
	Discount_DAO discountDao;
	
	// -- Discount --
	@RequestMapping("/discount")
	public String showDiscounts(HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			List<Discount> discounts = discountDao.getAll();
			req.setAttribute("discounts", discounts);
			return "adminview/discount/index";
		}
		catch (Exception e)
		{
			System.out.println("Test1");
	        req.setAttribute("errorMessage", "Có lỗi khi tải danh sách khuyến mãi!"); 
			e.printStackTrace();
			System.out.println("Test2");
			return "adminview/discount/index";
			
		}
		
	}	
	
	@RequestMapping(value = "/discount/add", method= RequestMethod.GET)
	public String addDiscount(Model model, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			model.addAttribute("discount", new Discount());
			model.addAttribute("nextDiscountId", discountDao.generateNextDiscountId());
			
			return "adminview/discount/add";

		}
		catch (Exception e)
		{
	        redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi tải giao diện thêm khuyến mãi!"); 
			e.printStackTrace();
			return "redirect:/admin/discount.htm";
			
		}
	
	}
	
	@RequestMapping(value = "/discount/add", method= RequestMethod.POST)
	public String addDiscountPost(@ModelAttribute("discount") Discount discount, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			if (discount.getStatus() == null) {
				discount.setStatus("Inactive");
			}
			
			discountDao.add(discount);
	        redirectAttributes.addFlashAttribute("successMessage", "Thêm khuyến mãi thành công!"); 

			
			return "redirect:/admin/discount.htm";
		}
		catch (Exception e)
		{
			String referer = req.getHeader("Referer");
			System.out.println(referer);

	        redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi thêm khuyến mãi!"); 
			e.printStackTrace();
			return "redirect" + referer;
			
		}
	}

	@RequestMapping(value = "/discount/edit", method= RequestMethod.GET)
	public String editDiscount(@RequestParam("discountId") String discountId, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			Discount discount = discountDao.getById(discountId);
			req.setAttribute("discount", discount);
			return "adminview/discount/edit";

		}
		catch (Exception e)
		{
	        redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi tải khuyến mãi!"); 
			e.printStackTrace();
			return "redirect:/admin/discount.htm";
			
		}
	
	}
	
	@RequestMapping(value = "/discount/edit", method= RequestMethod.POST)
	public String editDiscountPatch(@ModelAttribute("discount") Discount discount, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			if (discount.getStatus() == null) {
				discount.setStatus("Inactive");
			}
			discountDao.update(discount);
	        redirectAttributes.addFlashAttribute("successMessage", "Chỉnh sửa khuyến mãi thành công!"); 

			return "redirect:/admin/discount.htm";
		}
		catch (Exception e)
		{
			String referer = req.getHeader("Referer");
			System.out.println(referer);
	        redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi chỉnh sửa khuyến mãi!"); 
			e.printStackTrace();
			return "redirect" + referer;
			
		}
	}  
	
	@RequestMapping("/discount/delete")
	public String deleteDiscount(@RequestParam("discountId") String discountId, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			discountDao.delete(discountId);
	        redirectAttributes.addFlashAttribute("successMessage", "Xóa khuyến mãi thành công!"); 

			return "redirect:/admin/discount.htm";
		}
		catch (Exception e)
		{
	        redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi xóa khuyến mãi!"); 
			e.printStackTrace();
			return "redirect:/admin/discount.htm";
			
		}

	}
	
	@RequestMapping(value = "/discount/detail", method= RequestMethod.GET)
	public String detailDiscount(@RequestParam("discountId") String discountId, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			Discount discount = discountDao.getById(discountId);

			req.setAttribute("discount", discount);
			return "adminview/discount/detailModal";
		}
		catch (Exception e)
		{
	        redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi tải khuyến mãi!"); 
			e.printStackTrace();
			return "redirect:/admin/discount.htm";
			
		}

		
	}
	
	@RequestMapping(value = "/discount/changeStatus", method= RequestMethod.POST)
	public String changeStatusDiscount(@RequestParam("discountId") String discountId, RedirectAttributes redirectAttributes) {
		try
		{
			discountDao.changeStatus(discountId);
			return "adminview/discount/index";
		}
		catch (Exception e)
		{
	        redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi tải khuyến mãi!"); 
			e.printStackTrace();
			return "redirect:/admin/discount.htm";
			
		}

		
	}

}
