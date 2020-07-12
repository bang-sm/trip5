package com.sm.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
 
@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@GetMapping(value = "/index")
	public String index(HttpSession session, Model model) {
		logger.debug("index 페이지입니다");
		model.addAttribute("member", session.getAttribute("userInfo"));
		
		return "index";
	}
}
