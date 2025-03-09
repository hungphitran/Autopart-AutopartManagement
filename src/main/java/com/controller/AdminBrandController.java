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

import com.dao.Brand_DAO;
import com.entity.Brand;

@Controller
@RequestMapping("/admin")
public class AdminBrandController {
	
	@Autowired
	Brand_DAO brandDao;
	
	// -- Brand --
	@RequestMapping("/brand")
	public String showBrands(HttpServletRequest req) {
		List<Brand> brands = brandDao.getAll();
		req.setAttribute("brands", brands);
		return "adminview/brand/index";
	}
	
	@RequestMapping(value = "/brand/add", method= RequestMethod.GET)
	public String addBrand(Model model, HttpServletRequest req) {
		model.addAttribute("brand", new Brand());
		model.addAttribute("nextBrandId", brandDao.generateNextBrandId());
		
		return "adminview/brand/add";
	}
	
	@RequestMapping(value = "/brand/add", method= RequestMethod.POST)
	public String addBrandPost(@ModelAttribute("brand") Brand brand, HttpServletRequest req) {
		List<Brand> brands = brandDao.getAll();
		for(int i=0;i<brands.size();i++) {
			if(brands.get(i).getBrandName().toLowerCase().equals(brand.getBrandName().toLowerCase())){
				return "redirect:/admin/brand.htm";
			} // sửa lại bằng cách viết thêm hàm checkExistByName
		}
		
		brand.setBrandId(brandDao.generateNextBrandId());
		Boolean success = brandDao.add(brand);
		
		return "redirect:/admin/brand.htm";
	}
	
	@RequestMapping(value = "/brand/edit", method= RequestMethod.GET)
	public String editBrand(@RequestParam("brandId") String brandId, HttpServletRequest req) {
		Brand brand = brandDao.getById(brandId);
		req.setAttribute("brand", brand);

		return "adminview/brand/editModal";
	}
	
	@RequestMapping(value = "/brand/edit", method= RequestMethod.POST)
	public String editBrandPatch(@ModelAttribute("brand") Brand brand) {
		if (brand.getStatus() == null) {
			brand.setStatus("Inactive");
	    }
		brandDao.update(brand);
		
		return "redirect:/admin/brand.htm";
	} 
	
	@RequestMapping(value = "/brand/detail", method= RequestMethod.GET)
	public String detailBrand(@RequestParam("brandId") String brandId, HttpServletRequest req) {
		Brand brand = brandDao.getById(brandId);
		req.setAttribute("brand", brand);
		return "adminview/brand/detailModal";
	}
	
	@RequestMapping(value = "/brand/changeStatus", method= RequestMethod.POST)
	public String changeStatusBrand(@RequestParam("brandId") String brandId) {
		brandDao.changeStatus(brandId);
		return "adminview/brand/index";
	}

}
