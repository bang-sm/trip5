package com.sm.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;

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
		//logger.info("date : "+ list.get(0).getPlaceregdate());
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
	
	@GetMapping("/mypage")
	public String mypage(Model model,HttpSession session,@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage) throws Exception{
		MemberVO vo=vo=(MemberVO) session.getAttribute("userInfo");  
		
		logger.info("session : "+ vo);
		int uuid;
		try {
			uuid=vo.getUuid();
		}catch(NullPointerException e) {
			logger.info("session 없음");
			return "redirect:/index";
		}
		Map<String, Object> map = service.boardList(currentPage,uuid);
        model.addAttribute("boardList", map.get("list"));
        model.addAttribute("currentPage", map.get("currentPage"));
        model.addAttribute("lastPage", map.get("lastPage"));
        model.addAttribute("startPageNum", map.get("startPageNum"));
        model.addAttribute("lastPageNum", map.get("lastPageNum"));
		model.addAttribute("mypage",service.mypage(uuid));
		
		return "wish/mypagetotal";
	}
	

}
