package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dao.Customer_DAO;
import com.entity.Customer;

@Controller
@RequestMapping("/admin")
public class AdminCustomerController {
	
	@Autowired
	Customer_DAO customerDao;
	
	// -- customer --
	@RequestMapping("/customer")
	public String showCustomers(HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			List<Customer> customers = customerDao.getAll();
			req.setAttribute("customers", customers);
			return "adminview/customer/index";
		}
		catch (Exception e)
		{
			System.out.println("Test1");
	        req.setAttribute("errorMessage", "Có lỗi khi tải danh sách khách hàng!"); 
			e.printStackTrace();
			System.out.println("Test2");
			return "adminview/customer/index";
			
		}

		

	}
	
	@RequestMapping(value = "/customer/detail", method= RequestMethod.GET)
	public String detailCustomer(@RequestParam("cusEmail") String cusPhone, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			Customer customer = customerDao.getByEmail(cusPhone);
			req.setAttribute("customer", customer);
			return "adminview/customer/detailModal";

		}
		catch (Exception e)
		{
			redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi tải khách hàng!"); 
			e.printStackTrace();
			return "redirect:/admin/customer.htm";
		}

			
	}

}
