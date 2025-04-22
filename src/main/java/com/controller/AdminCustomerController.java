package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dao.Customer_DAO;
import com.entity.Customer;

@Controller
@RequestMapping("/admin")
public class AdminCustomerController {
	
	@Autowired
	Customer_DAO customerDao;
	
	// -- customer --
	@RequestMapping("/customer")
	public String showCustomers(HttpServletRequest req) {
		List<Customer> customers = customerDao.getAll();
		req.setAttribute("customers", customers);
		return "adminview/customer/index";
	}
	
	@RequestMapping(value = "/customer/detail", method= RequestMethod.GET)
	public String detailCustomer(@RequestParam("cusEmail") String cusPhone, HttpServletRequest req) {
		Customer customer = customerDao.getByEmail(cusPhone);
		req.setAttribute("customer", customer);
		return "adminview/customer/detailModal";
	}

}
