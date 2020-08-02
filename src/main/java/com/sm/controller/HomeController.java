package com.sm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sm.service.TravelService;
 
@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private TravelService service;
	
	@GetMapping(value = "/index")
	public String index(Model model) {
		logger.debug("index 페이지입니다");
		
		service.mainTravleList();
		
		
		return "index";
	}
}
