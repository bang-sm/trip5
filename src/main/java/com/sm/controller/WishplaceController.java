package com.sm.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sm.domain.MemberVO;
import com.sm.domain.PlacelistVo;
import com.sm.service.PlacelistService;

@Controller
public class WishplaceController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	PlacelistService service;
	
	@GetMapping("/wish/place")
	public String placed(Model model,HttpSession session)  throws Exception{
		//사용자 uuid 값 가지고 오기
		MemberVO vo=(MemberVO) session.getAttribute("userInfo");  
		
		logger.info("session : "+ vo);
		int uuid;
		try {
			uuid=vo.getUuid();
		}catch(NullPointerException e) {
			logger.info("session 없음");
			return "redirect:/user/login";
		}
		List<PlacelistVo> list = service.show(uuid);
		model.addAttribute("place", service.show(uuid));
		logger.info("확인 : " +list);
		return "wish/place";
	}
	
	@GetMapping("/wish/slide")
	public String slide() throws Exception{
		return "wish/slide";
	}
	
	@PostMapping("/placeregist")
	public String placeregist(@Valid PlacelistVo placelistVo,Errors errors) throws Exception{
		
		
		if(errors.hasErrors()) {
			logger.info("@Valid 유효성 에러");
			System.out.println("@Vaild 유효성 에러");
			return "redirectL/wish/place";
		}
		service.select(placelistVo);
		return "redirect:/wish/place";
	}
	
	@GetMapping("/example")
	public String example() throws Exception{
		
		return "common/commonSidebar";
	}
	
	@GetMapping("/wish/placechart")
	public String chart(Model model,HttpSession session)  throws Exception{
		//사용자 uuid 값 가지고 오기
		MemberVO vo=vo=(MemberVO) session.getAttribute("userInfo");  
		
		logger.info("session : "+ vo);
		int uuid;
		try {
			uuid=vo.getUuid();
		}catch(NullPointerException e) {
			logger.info("session 없음");
			return "redirect:/index";
		}
		List<PlacelistVo> list = service.show(uuid);
		model.addAttribute("place", service.show(uuid));
		logger.info("확인 : " +list);
		return "wish/placechart";
	}
	
	@GetMapping("/wish/korea")
	public String korea()throws Exception{
		return "wish/koreamap";
	}
	
	
}
