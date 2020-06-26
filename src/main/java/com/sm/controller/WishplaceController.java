package com.sm.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sm.domain.PlacelistVo;
import com.sm.service.PlacelistService;

@Controller
public class WishplaceController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	PlacelistService service;
	
	
	@RequestMapping("/wish/place")
	public String place()  throws Exception{
		return "wish/place";
	}
	
	@RequestMapping("/wish/slide")
	public String slide() throws Exception{
		return "wish/slide";
	}
	
	@PostMapping("/placeregist")
	public String placeregist(@Valid PlacelistVo placelistVo) throws Exception{
		service.select(placelistVo);
		
		return "redirect:/wish/place";
	}
}
