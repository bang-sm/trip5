package com.sm.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sm.domain.TravelInfoVO;
import com.sm.domain.TravelVO;
import com.sm.service.TravelService;

/**
 * @author smbang 작업 컨트롤러
 * 일지등록 관련한 컨트롤러입니다.
 */
@Controller
@RequestMapping(value = "/travel")
public class TravelController {
	private static final Logger logger = LoggerFactory.getLogger(TravelController.class);
	
	@Autowired
	private TravelService travelService;

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

	@GetMapping(value = "/intro_date")
	public String intro_date() {
		logger.info("intro_date");

		return "/travel/intro_date";
	}

	@GetMapping(value = "/regist")
	public String regist_get(Model model,String startDate,String endDate,int dateDiff) {
		logger.info("regist_get");
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		model.addAttribute("dateDiff",dateDiff+1);
		return "/travel/regist";
	}
	
	@PostMapping(value = "/regist")
	public String regist_get(TravelVO travelVO,TravelInfoVO travelinfoVO,HttpSession session) throws Exception{
		logger.info("regist_get");
		System.out.println(travelVO.toString());
		System.out.println(travelinfoVO.toString());
		System.out.println(travelinfoVO.getTsidDay()[0]+ " // "+travelinfoVO.getTsidDay()[1] );
		//travelService.storyRegist(travelVO,travelinfoVO);
		
		return "redirect:/index";
	}
}
