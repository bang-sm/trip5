package com.sm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
 
@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@GetMapping(value = "/index")
	public String index() {
		logger.debug("index 페이지입니다");
		return "index";
	}
}
