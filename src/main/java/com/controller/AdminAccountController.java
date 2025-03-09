package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dao.Account_DAO;
import com.dao.RoleGroup_DAO;
import com.entity.Account;
import com.entity.Blog;
import com.entity.BlogGroup;
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
		List<Account> accounts = accountDao.getAll();
		req.setAttribute("accounts", accounts);
		
		return "adminview/account/index";
	}
	
	@RequestMapping(value = "/account/changeStatus", method= RequestMethod.POST)
	public String changeStatus(@RequestParam("accPhone") String accPhone) {
		accountDao.changeStatus(accPhone);
		return "adminview/account/index";
	}
	
	@RequestMapping(value = "/account/edit", method= RequestMethod.GET)
	public String edit(@RequestParam("accPhone") String accPhone, HttpServletRequest req) {
		Account account = accountDao.getByPhone(accPhone);
		List<RoleGroup> roleGroup = roleGroupDao.getAll();
		req.setAttribute("account", account);
		req.setAttribute("roleGroup", roleGroup);
		
		return "adminview/account/editModal";
	}
	
	@RequestMapping(value = "/account/edit", method = RequestMethod.POST)
    public String editPatch(@ModelAttribute("account") Account acc, @RequestParam("phone") String phone) {
        accountDao.update(acc);
        return "redirect:/admin/account.htm";
    }
}
