package com.sm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	/**
	 * @author smbang 첫 등록화면에서 날짜와 제목 선택후 저장 시킬 맵핑
	 * 제목과 날짜 우선으로 일지를 우선저장 합니다.
	 */
	@ResponseBody
	@PostMapping("/travel_firstSave")
	public TravelVO travel_firstSave(TravelVO travelVO) {
		logger.info("travel_firstSave");
		travelService.travel_firstSave(travelVO);
		return travelVO;
	}
	
	/**
	 * @author smbang 일지 디테일 등록할 화면 맵핑
	 */
	@GetMapping(value = "/regist")
	public String regist_get(Model model,String tsid) {
		logger.info("regist_get");
		
		tsid="79";
		
		//일지 id값없이 접근하면 홈으로 돌려보낸다.
		if(tsid=="" || tsid==null) {
			return "redirect:/index";
		}
		model.addAttribute("travel",travelService.getTravelStory(tsid));
		System.out.println(travelService.getTravelStory(tsid));
		return "/travel/regist";
	}
	/*
	 * @PostMapping(value = "/regist") public String regist_get(TravelVO
	 * travelVO,@ModelAttribute TravelInfoVO travelinfoVO,HttpSession session)
	 * throws Exception{ logger.info("regist_get");
	 * travelService.storyRegist(travelVO,travelinfoVO);
	 * 
	 * return "redirect:/index"; }
	 */
}
