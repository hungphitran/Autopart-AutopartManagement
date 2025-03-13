package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dao.RoleGroup_DAO;
import com.entity.Brand;
import com.entity.Employee;
import com.entity.RoleGroup;

@Controller
@RequestMapping("/admin")
public class AdminRoleController {
	@Autowired
	RoleGroup_DAO roleGroupDao;
	
	@RequestMapping("/role")
	public String showRoleGroup(HttpServletRequest req) {
		List<RoleGroup> roleGroup = roleGroupDao.getAll();
		req.setAttribute("roleGroup", roleGroup);
		System.out.println(roleGroup);
		
		return "adminview/role/roleGroup/index";
	}
	
	@RequestMapping(value = "/role/changeStatus", method= RequestMethod.POST)
	public String changeStatus(@RequestParam("roleGroupId") String roleGroupId) {
		roleGroupDao.changeStatus(roleGroupId);
		return "adminview/role/roleGroup/index";
	}
	
	@RequestMapping("/role/delete")
	public String delete(@RequestParam("roleGroupId") String roleGroupId, HttpServletRequest req) {
		roleGroupDao.delete(roleGroupId);
		return "redirect:/admin/role.htm";
	}
	
	@RequestMapping(value = "/role/edit", method= RequestMethod.GET)
	public String editRoleGroup(@RequestParam("roleGroupId") String roleGroupId, HttpServletRequest req) {
		RoleGroup roleGroup = roleGroupDao.getById(roleGroupId);
		req.setAttribute("roleGroup", roleGroup);

		return "adminview/role/roleGroup/editModal";
	}
}
