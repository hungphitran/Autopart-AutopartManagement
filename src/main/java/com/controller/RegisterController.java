package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterController {
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public String login(@RequestParam("email") String email,
			@RequestParam("password") String password,Model model) {

		System.out.println(email+ password);
		return email+ password;
	}
	
	@RequestMapping(method= RequestMethod.GET)
	public String getRegisterPage() {
		return "register";
	}
}
