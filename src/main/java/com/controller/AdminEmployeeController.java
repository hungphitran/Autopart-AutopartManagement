package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dao.Employee_DAO;
import com.entity.Employee;


@Controller
@RequestMapping("/admin")
public class AdminEmployeeController {
	@Autowired
	Employee_DAO employeeDao;
	
//	@RequestMapping("/productGroup")
//	public String showGroup(HttpServletRequest req) {
//		List<Employee> employees = employeeDao.getAll();
//		req.setAttribute("employees", employees);
//		return "adminview/employee/index";
//	}
}
