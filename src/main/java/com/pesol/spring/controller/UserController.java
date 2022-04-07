package com.pesol.spring.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.pesol.spring.config.MyUserDetail;
import com.pesol.spring.entity.User;
import com.pesol.spring.model.UserRegisterModel;
import com.pesol.spring.service.UserService;

@Controller
@SessionAttributes(names = "registrationSuccess")
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/principal")
	@ResponseBody
	public MyUserDetail getPrincipal(Authentication authentication) {
		return (MyUserDetail) authentication.getPrincipal();
	}
	
	@GetMapping("/register")
	public String renderRegister(Model model) {
		
		model.addAttribute("userRegisterModel", new UserRegisterModel());
		
		return "user/register";
	}
	
	@PostMapping("/register")
	public String processRegister(@Valid 
			@ModelAttribute(name = "userRegisterModel") UserRegisterModel userRegisterModel,
			BindingResult result,
			Model model) {
		
		if (result.hasErrors()) {
			return "user/register";
		}
		
		User user = userService.findByUsername(userRegisterModel.getUsername());
		if (user != null) {
			model.addAttribute("userRegisterModel", new UserRegisterModel());
			model.addAttribute("registrationError", 
					"Username " + userRegisterModel.getUsername() + " is already exists!");
			
			return "user/register";
		}
		
		userService.save(userRegisterModel);
		model.addAttribute("registrationSuccess", "Registration successfully");
		return "redirect:/register";
	}
	
	@GetMapping("/login")
	public String renderLogin() {
		return "user/login";
	}
	
	@GetMapping("/access-denied")
	public String renderAccessDenied() {
		return "access-denied";
	}
}
