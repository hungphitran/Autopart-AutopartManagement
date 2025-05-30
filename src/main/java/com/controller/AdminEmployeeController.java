package com.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dao.Account_DAO;
import com.dao.Employee_DAO;
import com.dao.RoleGroup_DAO;
import com.entity.Account;
import com.entity.Blog;
import com.entity.BlogGroup;
import com.entity.Employee;
import com.entity.Product;
import com.entity.RoleGroup;
import com.utils.ValidationUtils;


@Controller
@RequestMapping("/admin")
public class AdminEmployeeController {
	@Autowired
	Employee_DAO employeeDao;
	
	@Autowired
	RoleGroup_DAO roleGroupDao;
	
	@Autowired
	Account_DAO accountDao;
	
	private String getMD5Hash(String input) {
	    try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(input.getBytes());
	        byte[] digest = md.digest();
	        return DatatypeConverter.printHexBinary(digest).toLowerCase();
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	
	@RequestMapping("/employee")
	public String showEmployee(HttpServletRequest req) {
		try
		{
			List<Employee> employees = employeeDao.getAll();
			req.setAttribute("employees", employees);
			
			return "adminview/employee/index";
		}
		catch (Exception e)
		{
			System.out.println("Test1");
	        req.setAttribute("errorMessage", "Có lỗi khi tải danh sách nhân viên!"); 
			e.printStackTrace();
			System.out.println("Test2");
			return "adminview/employee/index";
			
		}
	
	}
	
	@RequestMapping(value = "/employee/changeStatus", method= RequestMethod.POST)
	public String changeStatus(@RequestParam("empEmail") String empEmail, RedirectAttributes redirectAttributes) {
		try
		{
			employeeDao.changeStatus(empEmail);
			return "adminview/employee/index";
		}
		catch (Exception e)
		{
	        redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi thay đổi trạng thái nhân viên!"); 
			e.printStackTrace();
			return "redirect:/admin/employee.htm";
			
		}

	}
	
	@RequestMapping(value = "/employee/detail", method= RequestMethod.GET)
	public String detail(@RequestParam("empEmail") String empPhone, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			Employee employee = employeeDao.getByEmail(empPhone);
			
			req.setAttribute("emp", employee);
			return "adminview/employee/detail";

		}
		catch (Exception e)
		{
	        redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi tải nhân viên!"); 
			e.printStackTrace();
			return "redirect:/admin/employee.htm";
			
		}
	

	}
	
	@RequestMapping(value = "/employee/add", method= RequestMethod.GET)
	public String add(Model model, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			model.addAttribute("emp", new Employee());
			
			List<RoleGroup> roleGroup = roleGroupDao.getAll();
			model.addAttribute("roleGroup", roleGroup);
			
			return "adminview/employee/add";

		}
		
		catch (Exception e)
		{
	        redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi tải giao diện thêm nhân viên!"); 
			e.printStackTrace();
			return "redirect:/admin/employee.htm";
			
		}

				

	}
	
	@RequestMapping(value = "/employee/add", method= RequestMethod.POST)
	public String addPost(@ModelAttribute("emp") Employee emp, @RequestParam("permission") String permission, HttpServletRequest req, RedirectAttributes redirectAttributes) {

	    // Validate email format
	    if (!ValidationUtils.isValidEmail(emp.getEmail())) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Email không hợp lệ!");
	        return "redirect:/admin/employee/add.htm";
	    } else if (employeeDao.getByEmail(emp.getEmail()) != null) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Email đã tồn tại!");
	        return "redirect:/admin/employee/add.htm";
	    }

	    // Validate phone format
	    if (!ValidationUtils.isValidPhone(emp.getPhone())) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Số điện thoại không hợp lệ!");
	        return "redirect:/admin/employee/add.htm";
	    }

	    // Validate name format
	    if (!ValidationUtils.isValidName(emp.getFullName())) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Tên không hợp lệ!");
	        return "redirect:/admin/employee/add.htm";
	    }

	    // Validate citizen ID
	    if (emp.getCitizenId() == null || emp.getCitizenId().isEmpty()) {
	        redirectAttributes.addFlashAttribute("errorMessage", "CMND/CCCD không hợp lệ!");
	        return "redirect:/admin/employee/add.htm";
	    }

	    // Validate address
	    if (emp.getAddress() == null || emp.getAddress().isEmpty()) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Địa chỉ không hợp lệ!");
	        return "redirect:/admin/employee/add.htm";
	    }

	    // Validate birth date
	    if (emp.getBirthDate() == null) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Ngày sinh không hợp lệ!");
	        return "redirect:/admin/employee/add.htm";
	    }

	    // Validate start date
	    if (emp.getStartDate() == null) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Ngày bắt đầu làm việc không hợp lệ!");
	        return "redirect:/admin/employee/add.htm";
	    }

	    LocalDate today = LocalDate.now();
	    LocalDate birthDate = emp.getBirthDate().toLocalDate();
	    LocalDate startDate = emp.getStartDate().toLocalDate();

	    if (!birthDate.isBefore(today)) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Ngày sinh phải nhỏ hơn ngày hiện tại!");
	        return "redirect:/admin/employee/add.htm";
	    }

	    if (!startDate.isAfter(today)) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Ngày bắt đầu làm việc phải lớn hơn ngày hiện tại!");
	        return "redirect:/admin/employee/add.htm";
	    }

	    if (!birthDate.isBefore(startDate)) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Ngày sinh phải nhỏ hơn ngày bắt đầu làm việc!");
	        return "redirect:/admin/employee/add.htm";
	    }

	    try {
	        if (emp.getStatus() == null) {
	            emp.setStatus("Inactive");
	        }

	        Account acc = new Account(
	            emp.getEmail(),
	            getMD5Hash("1111"),
	            null,
	            permission,
	            emp.getStatus(),
	            Timestamp.valueOf(LocalDateTime.now()),
	            Timestamp.valueOf(LocalDateTime.now()),
	            false
	        );

	        accountDao.add(acc);
	        employeeDao.add(emp);

	        redirectAttributes.addFlashAttribute("successMessage", "Thêm nhân viên thành công!");
	        return "redirect:/admin/employee.htm";
	    } catch (Exception e) {
	        e.printStackTrace();
	        redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi thêm nhân viên!");
	        return "redirect:/admin/employee.htm";
	    }
	}

	
	@RequestMapping(value = "/employee/edit", method= RequestMethod.GET)
	public String edit(@RequestParam("empEmail") String empPhone, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			Employee emp = employeeDao.getByEmail(empPhone);
			req.setAttribute("emp", emp);
			
			List<RoleGroup> roleGroup = roleGroupDao.getAll();
			req.setAttribute("roleGroup", roleGroup);
			
			return "adminview/employee/edit";

		}
		catch (Exception e)
		{
	        redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra tải nhân viên!"); 
			e.printStackTrace();
			return "redirect:/admin/employee.htm";
			
		}
	
	}
	
	@RequestMapping(value = "/employee/edit", method = RequestMethod.POST)
	public String editPatch(@ModelAttribute("emp") Employee emp, HttpServletRequest req, RedirectAttributes redirectAttributes) {
	    String referer = req.getHeader("Referer");


	    // Validate phone format
	    if (!ValidationUtils.isValidPhone(emp.getPhone())) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Số điện thoại không hợp lệ!");
	        return "redirect:" + referer;
	    }

	    // Validate name format
	    if (!ValidationUtils.isValidName(emp.getFullName())) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Tên không hợp lệ!");
	        return "redirect:" + referer;
	    }

	    // Validate citizen ID
	    if (emp.getCitizenId() == null || emp.getCitizenId().isEmpty()) {
	        redirectAttributes.addFlashAttribute("errorMessage", "CMND/CCCD không hợp lệ!");
	        return "redirect:" + referer;
	    }

	    // Validate address
	    if (emp.getAddress() == null || emp.getAddress().isEmpty()) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Địa chỉ không hợp lệ!");
	        return "redirect:" + referer;
	    }

	    // Validate birth date
	    if (emp.getBirthDate() == null) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Ngày sinh không hợp lệ!");
	        return "redirect:" + referer;
	    }

	    // Validate start date
	    if (emp.getStartDate() == null) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Ngày bắt đầu làm việc không hợp lệ!");
	        return "redirect:" + referer;
	    }

	    // Logic date check
	    LocalDate today = LocalDate.now();
	    LocalDate birthDate = emp.getBirthDate().toLocalDate();
	    LocalDate startDate = emp.getStartDate().toLocalDate();

	    if (!birthDate.isBefore(today)) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Ngày sinh phải nhỏ hơn ngày hiện tại!");
	        return "redirect:" + referer;
	    }

	    if (!startDate.isAfter(today)) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Ngày bắt đầu làm việc phải lớn hơn ngày hiện tại!");
	        return "redirect:" + referer;
	    }

	    if (!birthDate.isBefore(startDate)) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Ngày sinh phải nhỏ hơn ngày bắt đầu làm việc!");
	        return "redirect:" + referer;
	    }

	    try {
	        if (emp.getStatus() == null) {
	            emp.setStatus("Inactive");
	        }

	        employeeDao.update(emp);
	        redirectAttributes.addFlashAttribute("successMessage", "Chỉnh sửa nhân viên thành công!");
	        return "redirect:/admin/employee.htm";
	    } catch (Exception e) {
	        e.printStackTrace();
	        redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi chỉnh sửa nhân viên!");
	        return "redirect:" + referer;
	    }
	}

}
