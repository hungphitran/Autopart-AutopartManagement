package com.controller;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.RoleGroup_DAO;
import com.entity.Brand;
import com.entity.Employee;
import com.entity.RoleGroup;

@Controller
@RequestMapping("/admin")
public class AdminRoleController {
	private static final List<String> ALL_PERMISSIONS = Arrays.asList(
        "THONG_KE",
        "PHIEU_NHAP_XEM", "PHIEU_NHAP_THEM",
		"QUAN_LY_SAN_PHAM_XEM", "QUAN_LY_SAN_PHAM_THEM", "QUAN_LY_SAN_PHAM_SUA", "QUAN_LY_SAN_PHAM_XOA",
        "DANH_MUC_SAN_PHAM_XEM", "DANH_MUC_SAN_PHAM_THEM", "DANH_MUC_SAN_PHAM_SUA", "DANH_MUC_SAN_PHAM_XOA",
		"QUAN_LY_NHAN_HANG_XEM", "QUAN_LY_NHAN_HANG_THEM", "QUAN_LY_NHAN_HANG_SUA", "QUAN_LY_NHAN_HANG_XOA",
		"DANH_SACH_KHACH_HANG_XEM", "DANH_SACH_KHACH_HANG_THEM", "DANH_SACH_KHACH_HANG_SUA", "DANH_SACH_KHACH_HANG_XOA",
		"DANH_SACH_NHAN_VIEN_XEM", "DANH_SACH_NHAN_VIEN_THEM", "DANH_SACH_NHAN_VIEN_SUA", "DANH_SACH_NHAN_VIEN_XOA",
		"QUAN_LY_BAI_VIET_XEM", "QUAN_LY_BAI_VIET_THEM", "QUAN_LY_BAI_VIET_SUA", "QUAN_LY_BAI_VIET_XOA",
		"QUAN_LY_KHUYEN_MAI_XEM", "QUAN_LY_KHUYEN_MAI_THEM", "QUAN_LY_KHUYEN_MAI_SUA", "QUAN_LY_KHUYEN_MAI_XOA",
		"QUAN_LY_DON_HANG_XEM", "QUAN_LY_DON_HANG_THEM", "QUAN_LY_DON_HANG_SUA",
        "DANH_SACH_TAI_KHOAN_XEM", "DANH_SACH_TAI_KHOAN_THEM", "DANH_SACH_TAI_KHOAN_SUA", "DANH_SACH_TAI_KHOAN_XOA",
        "CAI_DAT_CHUNG_XEM", "CAI_DAT_CHUNG_THEM", "CAI_DAT_CHUNG_SUA", "CAI_DAT_CHUNG_XOA",
        "NHOM_QUYEN_XEM", "NHOM_QUYEN_THEM", "NHOM_QUYEN_SUA", "NHOM_QUYEN_XOA", "PHAN_QUYEN"
    );
	
	@Autowired
	RoleGroup_DAO roleGroupDao;
	
	@RequestMapping("/role")
	public String showRoleGroup(HttpServletRequest req) {
		List<RoleGroup> roleGroup = roleGroupDao.getAll();
		req.setAttribute("roleGroup", roleGroup);
		
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
	
	@RequestMapping("/role/permission")
	public String showPermission(Model model, HttpServletRequest req) {
		List<RoleGroup> roleGroup = roleGroupDao.getAll();
		req.setAttribute("roleGroup", roleGroup);
		model.addAttribute("allPermissions", ALL_PERMISSIONS);
		
		return "adminview/role/permission/index";
	}
	
	@RequestMapping(value = "/role/updatePermissions", method = RequestMethod.POST)
	public String updatePermissions(
	        @RequestParam("roleGroupIds") List<String> roleGroupIds,
	        HttpServletRequest req) {
	    
	    for (String roleGroupId : roleGroupIds) {
	        String[] permissions = req.getParameterValues("permissions[" + roleGroupId + "]");
	        List<String> permissionList = (permissions != null) ? Arrays.asList(permissions) : Arrays.asList();
	        System.out.println(roleGroupId + " " + permissionList);
	        
	        RoleGroup roleGroup = roleGroupDao.getById(roleGroupId);
	        if (roleGroup != null) {
	            roleGroup.setPermissions(permissionList);
	            roleGroup.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
	            roleGroupDao.update(roleGroup);
	        }
	    }

	    return "redirect:/admin/role/permission.htm";
	}
}
