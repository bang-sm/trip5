package com.sm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sm.domain.MemberVO;
import com.sm.domain.TravelViewVO;
import com.sm.service.MyinfoService;

@RequestMapping("/mypage")
public class MyinfoController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	MyinfoService myinfoService;
	
	
	
	
	
	@ResponseBody
	@PostMapping("/insertView")
	public void insertTravelView(Map<Object, Object> paramMap) {
		
		TravelViewVO travelViewVO = new TravelViewVO();
		
		travelViewVO.setTsid((int)paramMap.get("tsid"));
		travelViewVO.setTsid((int)paramMap.get("tsid"));
		travelViewVO.setTsid((int)paramMap.get("tsid"));
		travelViewVO.setTsid((int)paramMap.get("tsid"));
		
		
	}
	
	@ResponseBody
	@PostMapping("/selectView")
	public List<TravelViewVO> selectTravelView(HttpSession httpSession){
		List<TravelViewVO> tvList = new ArrayList<TravelViewVO>();
		MemberVO memberVO = new MemberVO();
		
		memberVO = (MemberVO) httpSession.getAttribute("userInfo");
		int uuid = memberVO.getUuid();
		
		tvList = myinfoService.selectTravelView(uuid);
		return tvList;
	}
	
	
}
