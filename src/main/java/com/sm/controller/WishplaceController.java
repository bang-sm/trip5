package com.sm.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sm.domain.PlacelistVo;
import com.sm.service.PlacelistService;

@Controller
public class WishplaceController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	PlacelistService service;
	
	
	@GetMapping("/wish/place")
	public String place(Model model)  throws Exception{
		List<PlacelistVo> list = service.show();
		model.addAttribute("place", service.show());
		logger.info("확인 : " +list);
		return "wish/place";
	}
	
	@GetMapping("/wish/slide")
	public String slide() throws Exception{
		return "wish/slide";
	}
	
	@PostMapping("/placeregist")
	public String placeregist(@Valid PlacelistVo placelistVo) throws Exception{
		service.select(placelistVo);
		
		return "redirect:/wish/place";
	}
	
	
}
