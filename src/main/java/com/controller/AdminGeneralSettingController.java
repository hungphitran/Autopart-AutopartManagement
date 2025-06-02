package com.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dao.GeneralSettings_DAO;
import com.entity.GeneralSettings;

@Controller
@RequestMapping("/admin")
public class AdminGeneralSettingController {
    @Autowired
    GeneralSettings_DAO gSettingsDao;

    @RequestMapping(value = "/generalSettings", method = RequestMethod.GET)
    public String showGeneralSettings(Model model, HttpServletRequest req) {
        GeneralSettings settings = gSettingsDao.get();
        if (settings == null) {
            settings = new GeneralSettings();
        }
        if (settings.getLogo()==null || !settings.getLogo().startsWith("https")) {
        	String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/resources/img/";
        	settings.setLogo(baseUrl + settings.getLogo());
    	}
        model.addAttribute("generalSettings", settings);
        return "adminview/generalSettings/index";
    }

    @RequestMapping(value = "/generalSettings/update", method = RequestMethod.POST)
    public String updateGeneralSettings(@ModelAttribute("generalSettings") GeneralSettings formSettings, @RequestParam("logoFile") MultipartFile logoFile, HttpServletRequest req, RedirectAttributes redirectAttributes) {
    	// validate form settings
    	if (formSettings.getWebsiteName() == null || formSettings.getWebsiteName().isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage", "Tên website không được để trống!");
			return "redirect:/admin/generalSettings.htm";
		}
    	if (formSettings.getWebsiteName().length() > 100) {
			redirectAttributes.addFlashAttribute("errorMessage", "Tên website không được quá 100 ký tự!");	
			return "redirect:/admin/generalSettings.htm";
    								
    	}
    	if (formSettings.getAddress() == null || formSettings.getAddress().isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage", "Địa chỉ không được để trống!");
			return "redirect:/admin/generalSettings.htm";
    	}
    	if (formSettings.getAddress().length() > 200) {
			redirectAttributes.addFlashAttribute("errorMessage", "Địa chỉ không được quá 200 ký tự!");
			return "redirect:/admin/generalSettings.htm";
		}
    	if (formSettings.getPhone() == null || formSettings.getPhone().isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage", "Số điện thoại không được để trống!");
			return "redirect:/admin/generalSettings.htm";
    	}
    	if (formSettings.getPhone().length() > 20) {
    		redirectAttributes.addFlashAttribute("errorMessage", "Số điện thoại không được quá 20 ký tự!");
    					return "redirect:/admin/generalSettings.htm";
    	}
    	try
    	{
    		GeneralSettings exisitingSetting = gSettingsDao.get();
        	String webName = exisitingSetting.getWebsiteName();
        	String logoUrl = exisitingSetting.getLogo();
        	gSettingsDao.delete(webName);
        	
        	// Đường dẫn thư mục lưu ảnh
    	    String uploadDir = req.getServletContext().getRealPath("/resources/img/");
    	    File dir = new File(uploadDir);
    	    if (!dir.exists()) {
    	        dir.mkdirs(); // Tạo thư mục nếu chưa tồn tại
    	    }
        	
    	    String imageUrl = "";
            if (!logoFile.isEmpty()) {
                String logoPath = System.currentTimeMillis() + "_" + logoFile.getOriginalFilename();
                if (logoPath != null) {
                	
                	try {
                        File destination = new File(uploadDir + logoPath);
                        logoFile.transferTo(destination); // Lưu file vào thư mục
                        imageUrl = logoPath;
                    } catch (IOException e) {
                        e.printStackTrace();
                        imageUrl = "";
                    }
                }
            }
            
            formSettings.setLogo(imageUrl);
            if (formSettings.getLogo() == "") {
            	formSettings.setLogo(logoUrl);
            }
            
            boolean success = gSettingsDao.add(formSettings);
	        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật các thông số chung thành công!"); 
            return "redirect:/admin/generalSettings.htm";
    	}
    	catch (Exception e)
		{
	        redirectAttributes.addFlashAttribute("errorMessage", "Cập nhật các thông số chung thất bại!"); 
			e.printStackTrace();
            return "redirect:/admin/generalSettings.htm";
			
		}
    	
    }
}