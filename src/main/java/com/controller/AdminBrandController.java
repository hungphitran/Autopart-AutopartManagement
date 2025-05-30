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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dao.Brand_DAO;
import com.entity.Brand;

@Controller
@RequestMapping("/admin")
public class AdminBrandController {
	
	@Autowired
	Brand_DAO brandDao;
	
	// -- Brand --
	@RequestMapping("/brand")
	public String showBrands(HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			List<Brand> brands = brandDao.getAll();
			req.setAttribute("brands", brands);
			return "adminview/brand/index";
		}
		catch (Exception e)
		{
			System.out.println("Test1");
	        req.setAttribute("errorMessage", "Có lỗi khi tải danh sách nhãn hàng!"); 
			e.printStackTrace();
			System.out.println("Test2");
			return "adminview/brand/index";
			
		}

		
		
	}
	
	@RequestMapping(value = "/brand/add", method= RequestMethod.GET)
	public String addBrand(Model model, HttpServletRequest req) {
		
		model.addAttribute("brand", new Brand());
		model.addAttribute("nextBrandId", brandDao.generateNextBrandId());
		
		return "adminview/brand/add";
	}
	
	@RequestMapping(value = "/brand/add", method= RequestMethod.POST)
	public String addBrandPost(@ModelAttribute("brand") Brand brand, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			
			
			
			if(brandDao.checkExistByName(brand.getBrandName()))
			{
				String referer = req.getHeader("Referer");
				System.out.println(referer);
				redirectAttributes.addFlashAttribute("errorMessage", "Thương hiệu đã tồn tại!"); 
				return "redirect" + referer;

			}
//			List<Brand> brands = brandDao.getAll();
//			for(int i=0;i<brands.size();i++) {
//				if(brands.get(i).getBrandName().toLowerCase().equals(brand.getBrandName().toLowerCase())){
//					return "redirect:/admin/brand.htm";
//				} // sửa lại bằng cách viết thêm hàm checkExistByName
//			}
		
		
			brand.setBrandId(brandDao.generateNextBrandId());
			Boolean success = brandDao.add(brand);
			redirectAttributes.addFlashAttribute("successMessage", "Thêm thương hiệu thành công"); 
			
			return "redirect:/admin/brand.htm";
		}
		catch(Exception e)
		{
			String referer = req.getHeader("Referer");
			System.out.println(referer);
			redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi thêm thương hiệu!"); 
			e.printStackTrace();
			return "redirect" + referer;
		}
	}
	
	@RequestMapping(value = "/brand/edit", method= RequestMethod.GET)
	public String editBrand(@RequestParam("brandId") String brandId, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			Brand brand = brandDao.getById(brandId);
			req.setAttribute("brand", brand);

			return "adminview/brand/editModal";

		}
		catch (Exception e)
		{
			redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi tải thương hiệu!"); 
			e.printStackTrace();
			return "redirect:/admin/brand.htm";
		}

			
	}
	
	@RequestMapping(value = "/brand/edit", method= RequestMethod.POST)
	public String editBrandPatch(@ModelAttribute("brand") Brand brand, RedirectAttributes redirectAttributes, HttpServletRequest req) {
		
		if(!brand.getBrandName().equals(brandDao.getById(brand.getBrandId()).getBrandName()) && brandDao.checkExistByName(brand.getBrandName()))
		{
			redirectAttributes.addFlashAttribute("errorMessage", "Thương hiệu đã tồn tại!"); 
			return "redirect:/admin/brand.htm";

		}
		
		try
		{
			if (brand.getStatus() == null) {
				brand.setStatus("Inactive");
		    }
			brandDao.update(brand);
			redirectAttributes.addFlashAttribute("successMessage", "Chỉnh sửa thương hiệu thành công!"); 
			return "redirect:/admin/brand.htm";
		}
		catch (Exception e)
		{
			redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi chỉnh sửa thương hiệu!"); 
			e.printStackTrace();
			return "redirect:/admin/brand.htm";
		}
	} 
	
	@RequestMapping(value = "/brand/detail", method= RequestMethod.GET)
	public String detailBrand(@RequestParam("brandId") String brandId, HttpServletRequest req, RedirectAttributes redirectAttributes) { 
		try
		{
			Brand brand = brandDao.getById(brandId);
			req.setAttribute("brand", brand);
			return "adminview/brand/detailModal";
		}
		catch (Exception e)
		{
			redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi tải thương hiệu!"); 
			e.printStackTrace();
			return "redirect:/admin/brand.htm";
		}

		
	}
	
	@RequestMapping(value = "/brand/changeStatus", method= RequestMethod.POST)
	public String changeStatusBrand(@RequestParam("brandId") String brandId, RedirectAttributes redirectAttributes) {
		try
		{
			brandDao.changeStatus(brandId);
			return "adminview/brand/index";

		}
		catch (Exception e)
		{
			redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi thay đổi trạng thái thương hiệu!"); 
			e.printStackTrace();
			return "redirect:/admin/brand.htm";
		}

	}

}
