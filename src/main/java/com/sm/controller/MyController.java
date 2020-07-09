package com.sm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sm.domain.MemberVO;
import com.sm.service.MyService;

@RestController
public class MyController {
	
	@Autowired
	private MyService myService;
	
	@PostMapping("/black")
	public void blackAjax(MemberVO memberVO) {
		int uuid = myService.selectUuid(memberVO).getUuid();
		
		myService.insertBlackList(memberVO.getUuid(), uuid);
	}
	
	@PostMapping("/load")
	public void loadAjax(int uuid) {
		
		myService.selectBlackList(uuid);
	}
	
}
