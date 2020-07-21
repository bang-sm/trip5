package com.sm.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sm.domain.TravelInfoRootVO;
import com.sm.domain.TravelInfoVO;
import com.sm.domain.TravelVO;
import com.sm.service.TravelService;

/**
 * @author smbang 작업 컨트롤러 일지등록 관련한 컨트롤러입니다.
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

	@GetMapping(value = "/travel_blog")
	public String travel_detail() {
		logger.info("travel_blog");

		return "/travel/travel_blog";
	}

	@GetMapping(value = "/intro_date")
	public String intro_date() {
		logger.info("intro_date");

		return "/travel/intro_date";
	}

	/**
	 * @author smbang 첫 등록화면에서 날짜와 제목 선택후 저장 시킬 맵핑 제목과 날짜 우선으로 일지를 우선저장 합니다.
	 */
	@ResponseBody
	@PostMapping("/travel_firstSave")
	public void travel_firstSave(@ModelAttribute TravelVO travelVO) {
		logger.info("travel_firstSave");
		travelService.travel_firstSave(travelVO);
	}
	
	/**
	 * @author smbang 일지디테일 임시저장용 맵핑
	 */
	@ResponseBody
	@PostMapping("/travel_temp_save")
	public String travel_temp_save(@ModelAttribute TravelInfoVO travelInfoVO,
			@ModelAttribute TravelInfoRootVO travelInfoRootVO) {
		logger.info("travel_temp_save");
		
		travelService.tempSave(travelInfoVO,travelInfoRootVO);
		
		return "다녀왔습니다";
	}
	@ResponseBody
	@PostMapping(value = "/travel_root_delete")
	public String travel_root_delete(int tsirootorder,int tsid) throws Exception {
		logger.info("travel_root_delete");
		
		System.out.println(tsirootorder + " /  "+tsid);
		travelService.travel_root_delete(tsirootorder,tsid);

		return "삭제되었습니다";
	}
	@ResponseBody
	@PostMapping(value = "/travel_detail_data")
	public List<TravelInfoVO> travel_detail_data(String tsid) throws Exception {
		logger.info("travel_detail_data");
		return travelService.getTravelInfo(tsid);
	}

	/**
	 * @author smbang 일지 디테일 등록할 화면 맵핑
	 */
	@GetMapping(value = "/regist")
	public String regist_get(Model model, String tsid) {
		logger.info("regist_get");

		tsid = "84";

		// 일지 id값없이 접근하면 홈으로 돌려보낸다.
		if (tsid == "" || tsid == null) {
			return "redirect:/index";
		}
		model.addAttribute("travel", travelService.getTravelStory(tsid)); //기본정보
		model.addAttribute("travelInfo", travelService.getTravelInfo(tsid)); //임시저장된 상세정보
		model.addAttribute("travelRootInfo", travelService.getTravelRootInfo(tsid)); //임시저장된 루트정보
		return "/travel/regist";
	}
	@PostMapping(value = "/registSave")
	public String regist_get(TravelVO travelVO, @ModelAttribute TravelInfoVO travelinfoVO,
			@ModelAttribute TravelInfoRootVO travelinfoRootVO, HttpSession session) throws Exception {
		logger.info("regist_get");
		// travelService.storyRegist(travelVO,travelinfoVO);

		System.out.println(travelinfoVO);
		System.out.println(travelinfoRootVO.getRootlist().get(0).getTsirootname());
		System.out.println(travelinfoRootVO.getRootlist().get(1).getTsirootname());

		return "redirect:/index";
	}
	


}
