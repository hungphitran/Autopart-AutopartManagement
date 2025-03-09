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

import com.dao.Account_DAO;
import com.dao.Employee_DAO;
import com.dao.RoleGroup_DAO;
import com.entity.Account;
import com.entity.Blog;
import com.entity.BlogGroup;
import com.entity.Employee;
import com.entity.Product;
import com.entity.RoleGroup;


@Controller
@RequestMapping("/admin")
public class AdminEmployeeController {
	@Autowired
	Employee_DAO employeeDao;
	
	@Autowired
	RoleGroup_DAO roleGroupDao;
	
	@Autowired
	Account_DAO accountDao;
	
	@RequestMapping("/employee")
	public String showEmployee(HttpServletRequest req) {
		List<Employee> employees = employeeDao.getAll();
		req.setAttribute("employees", employees);
		
		return "adminview/employee/index";
	}
	
	@RequestMapping(value = "/employee/changeStatus", method= RequestMethod.POST)
	public String changeStatus(@RequestParam("empPhone") String empPhone) {
		employeeDao.changeStatus(empPhone);
		return "adminview/employee/index";
	}
	
	@RequestMapping(value = "/employee/detail", method= RequestMethod.GET)
	public String detail(@RequestParam("empPhone") String empPhone, HttpServletRequest req) {
		Employee employee = employeeDao.getByPhone(empPhone);
		
		req.setAttribute("emp", employee);
		return "adminview/employee/detail";
	}
	
	@RequestMapping(value = "/employee/add", method= RequestMethod.GET)
	public String add(Model model, HttpServletRequest req) {
		model.addAttribute("emp", new Employee());
		
		List<RoleGroup> roleGroup = roleGroupDao.getAll();
		model.addAttribute("roleGroup", roleGroup);
		
		return "adminview/employee/add";
	}
	
	@RequestMapping(value = "/employee/add", method= RequestMethod.POST)
	public String addPost(@ModelAttribute("emp") Employee emp, @RequestParam("permission") String permission, HttpServletRequest req) {
		if (emp.getStatus() == null) {
			emp.setStatus("Inactive");
        }
        
		Account acc = new Account(emp.getPhone(), "1111", null, permission, emp.getStatus());
		accountDao.add(acc);
		
		employeeDao.add(emp);
        return "redirect:/admin/employee.htm";
	}
	
	@RequestMapping(value = "/employee/edit", method= RequestMethod.GET)
	public String edit(@RequestParam("empPhone") String empPhone, HttpServletRequest req) {
		Employee emp = employeeDao.getByPhone(empPhone);
		req.setAttribute("emp", emp);
		
		List<RoleGroup> roleGroup = roleGroupDao.getAll();
		req.setAttribute("roleGroup", roleGroup);
		
		return "adminview/employee/edit";
	}
	
	@RequestMapping(value = "/employee/edit", method= RequestMethod.POST)
	public String editPatch(@ModelAttribute("emp") Employee emp, HttpServletRequest req) {
		if (emp.getStatus() == null) {
			emp.setStatus("Inactive");
        }
		
		employeeDao.update(emp);
        return "redirect:/admin/employee.htm";
	}
}
