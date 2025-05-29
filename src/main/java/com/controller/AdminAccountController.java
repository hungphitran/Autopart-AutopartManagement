package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dao.Account_DAO;
import com.dao.RoleGroup_DAO;
import com.entity.Account;
import com.entity.RoleGroup;

@Controller
@RequestMapping("/admin")
public class AdminAccountController {
    @Autowired
    Account_DAO accountDao;

    @Autowired
    RoleGroup_DAO roleGroupDao;

    @RequestMapping("/account")
    public String showAccounts(HttpServletRequest req) {
    	try
    	{
    		HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("account") == null) {
                return "redirect:/admin/login.htm";
            }

            List<Account> accounts = accountDao.getAll();
            req.setAttribute("accounts", accounts);
            return "adminview/account/index";

    	}
    	catch (Exception e)
		{
			System.out.println("Test1");
	        req.setAttribute("errorMessage", "Tải danh sách tài khoản thất bại!"); 
			e.printStackTrace();
			System.out.println("Test2");
            return "adminview/account/index";
			
		}
    }

    @RequestMapping(value = "/account/changeStatus", method = RequestMethod.POST)
    public String changeStatus(@RequestParam("accEmail") String accEmail, HttpServletRequest req, RedirectAttributes redirectAttributes) {
    	try
    	{
    		accountDao.changeStatus(accEmail);
            return "redirect:/admin/account.htm";
    	}
    	catch (Exception e)
		{
			System.out.println("Test1");
	        redirectAttributes.addFlashAttribute("errorMessage", "Thay đổi trạng thái tài khoản thất bại!");  
			e.printStackTrace();
			System.out.println("Test2");
            return "redirect:/admin/account.htm";
			
		}

    }

    @RequestMapping(value = "/account/edit", method = RequestMethod.GET)
    public String edit(@RequestParam("accEmail") String accEmail, HttpServletRequest req, RedirectAttributes redirectAttributes) {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
        	redirectAttributes.addFlashAttribute("successError", "Không tìm thấy thông tin!"); 
        }

        Account account = accountDao.getByEmail(accEmail);
        List<RoleGroup> roleGroup = roleGroupDao.getAll();
        req.setAttribute("account", account);
        req.setAttribute("roleGroup", roleGroup);
        return "adminview/account/editModal";
    }

    @RequestMapping(value = "/account/edit", method = RequestMethod.POST)
    public String editPatch(@ModelAttribute("account") Account acc, @RequestParam("email") String email, RedirectAttributes redirectAttributes, HttpServletRequest req) {
    	try
    	{
    		accountDao.update(acc);
    		req.setAttribute("successMessage", "Chỉnh sửa tài khoản thành công!"); 
            return "redirect:/admin/account.htm";
    	}
    	catch (Exception e)
		{
			System.out.println("Test1");
	        req.setAttribute("errorMessage", "Chỉnh sửa tài khoản thất bại!"); 
			e.printStackTrace();
			System.out.println("Test2");
            return "redirect:/admin/account.htm";
			
		}
    }
}