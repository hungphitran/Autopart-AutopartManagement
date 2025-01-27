package com.controller;

import javax.servlet.http.*;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
public class LoginController {
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(Model model, HttpServletRequest req,HttpServletResponse res) {
		
		//get input from request
		String email= req.getParameter("email");
		String pass = req.getParameter("password");

		if(pass.length()<4) {//check valid password and
			req.setAttribute("message", "password is incorrect");
			return "login";			
		}
		
		// add user info to session
		req.getSession().setAttribute("email",email);
		req.getSession().setAttribute("user", "Hung");
		return "redirect:/";////
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String getLogin(HttpServletRequest req,Model  model) {
		HttpSession session = req.getSession();
		if(session.getAttribute("email")!=null) {
			model.addAttribute("email", session.getAttribute("email"));
			return "profile";
		}
		return "login";
	}
	
	@RequestMapping(value="/logout",method = RequestMethod.GET)
	public String logout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.removeAttribute("user");
		session.removeAttribute("email");
		return "login";
	}
}
