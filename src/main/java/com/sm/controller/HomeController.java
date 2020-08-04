package com.sm.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sm.domain.TravelVO;
import com.sm.service.TravelService;

@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private TravelService service;

	@GetMapping(value = "/index")
	public String index(Model model) {
		logger.debug("index 페이지입니다");

		model.addAttribute("list", service.mainTravleList(0));

		return "index";
	}

	@GetMapping(value = "/")
	public String root(Model model) {
		logger.debug("root");
		model.addAttribute("list", service.mainTravleList(0));
		return "index";
	}
	
	@ResponseBody
	@PostMapping(value = "/mainlist")
	public List<TravelVO> selectmainTravleList(int buttonNum){
		List<TravelVO> returnList = new ArrayList<TravelVO>();
		
		returnList = service.mainTravleList(buttonNum);
		return returnList;
	}
}
