package com.pesol.spring.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(Exception.class)
	public String renderException(HttpServletRequest request, Exception e, Model model) {
		
		model.addAttribute("errorMsg", "Error message: " + e.getMessage());
		model.addAttribute("reqUrl", "Request part: " + request.getRequestURI());
		
		return "error";
	}

}
