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

import com.dao.Discount_DAO;
import com.entity.Discount;

@Controller
@RequestMapping("/admin")
public class AdminDiscountController {
	
	@Autowired
	Discount_DAO discountDao;
	
	// -- Discount --
	@RequestMapping("/discount")
	public String showDiscounts(HttpServletRequest req) {
		List<Discount> discounts = discountDao.getAll();
		req.setAttribute("discounts", discounts);
		return "adminview/discount/index";
	}
	
	@RequestMapping(value = "/discount/add", method= RequestMethod.GET)
	public String addDiscount(Model model, HttpServletRequest req) {
		model.addAttribute("discount", new Discount());
		model.addAttribute("nextDiscountId", discountDao.generateNextDiscountId());
		
		return "adminview/discount/add";
	}
	
	@RequestMapping(value = "/discount/add", method= RequestMethod.POST)
	public String addDiscountPost(@ModelAttribute("discount") Discount discount, HttpServletRequest req) {
		if (discount.getStatus() == null) {
			discount.setStatus("Inactive");
		}
		
		discountDao.add(discount);
		
		return "redirect:/admin/discount.htm";
	}

	@RequestMapping(value = "/discount/edit", method= RequestMethod.GET)
	public String editDiscount(@RequestParam("discountId") String discountId, HttpServletRequest req) {
		Discount discount = discountDao.getById(discountId);
		req.setAttribute("discount", discount);
		return "adminview/discount/edit";
	}
	
	@RequestMapping(value = "/discount/edit", method= RequestMethod.POST)
	public String editDiscountPatch(@ModelAttribute("discount") Discount discount) {
		if (discount.getStatus() == null) {
			discount.setStatus("Inactive");
		}
		discountDao.update(discount);
		
		return "redirect:/admin/discount.htm";
	}  
	
	@RequestMapping("/discount/delete")
	public String deleteDiscount(@RequestParam("discountId") String discountId, HttpServletRequest req) {
		discountDao.delete(discountId);
		return "redirect:/admin/discount.htm";
	}
	
	@RequestMapping(value = "/discount/detail", method= RequestMethod.GET)
	public String detailDiscount(@RequestParam("discountId") String discountId, HttpServletRequest req) {
		Discount discount = discountDao.getById(discountId);

		req.setAttribute("discount", discount);
		return "adminview/discount/detailModal";
	}
	
	@RequestMapping(value = "/discount/changeStatus", method= RequestMethod.POST)
	public String changeStatusDiscount(@RequestParam("discountId") String discountId) {
		discountDao.changeStatus(discountId);
		return "adminview/discount/index";
	}

}
