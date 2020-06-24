package com.sm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/travel")
public class TravelController {
	private static final Logger logger = LoggerFactory.getLogger(TravelController.class);
	
	@GetMapping(value = "/travel_main")
	public String travel_main() {
		logger.info("travel_main");
		
		return "/travel/travel_main";
	}
	@GetMapping(value = "/travel_detail")
	public String travel_detail() {
		logger.info("travel_detail");
		
		return "/travel/travel_detail";
	}
	@GetMapping(value = "/regist")
	public String regist() {
		logger.info("regist");
		
		return "/travel/regist";
	}
}
