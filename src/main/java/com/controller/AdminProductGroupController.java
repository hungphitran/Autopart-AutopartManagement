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

import com.dao.ProductGroup_DAO;
import com.entity.Brand;
import com.entity.ProductGroup;

@Controller
@RequestMapping("/admin")
public class AdminProductGroupController {
	@Autowired
	ProductGroup_DAO productGroupDao;
	
	@RequestMapping("/productGroup")
	public String showGroup(HttpServletRequest req) {
		List<ProductGroup> groupList = productGroupDao.getAll();
		req.setAttribute("productGroups", groupList);
		return "adminview/productGroup/index";
	}
	
	@RequestMapping("/productGroup/add")
	public String showAdd(HttpServletRequest req,Model model) {
		model.addAttribute("newGroup", new ProductGroup() );
		model.addAttribute("nextGroupId", productGroupDao.generateNextProductGroupId());
		return "adminview/productGroup/add";
	}
	@RequestMapping(value = "/productGroup/add", method= RequestMethod.POST)
	public String addBrandPost(@ModelAttribute("newGroup") ProductGroup group, HttpServletRequest req) {
		List<ProductGroup> groups = productGroupDao.getAll();
		for(int i=0;i<groups.size();i++) {
			if(groups.get(i).getGroupName().toLowerCase().equals(group.getGroupName().toLowerCase())){
				return "redirect:/admin/productGroup.htm";
			} // sửa lại bằng cách viết thêm hàm checkExistByName
		}
		
		group.setProductGroupId(productGroupDao.generateNextProductGroupId());
		Boolean success = productGroupDao.add(group);
		
		return "redirect:/admin/productGroup.htm";
	}
	
	@RequestMapping("/productGroup/delete")
	public String deleteProduct(@RequestParam("productGroupId") String productGroupId, HttpServletRequest req) {
		productGroupDao.delete(productGroupId);
		return "redirect:/admin/productGroup.htm";
	}
	
	@RequestMapping(value = "/productGroup/changeStatus", method= RequestMethod.POST)
	public String changeStatusGroup(@RequestParam("groupId") String groupId,HttpServletRequest req) {
		productGroupDao.changeStatus(groupId);
		req.setAttribute("productGroups", productGroupDao.getAll());
		return "adminview/productGroup/index";
	}
}
