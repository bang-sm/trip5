package com.sm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sm.domain.MemberVO;
import com.sm.service.MyService;

@RestController
@RequestMapping(value="/my")
public class MyController {
	
	@Autowired
	private MyService myService;
	
	@PostMapping("/black")
	public void blackAjax(MemberVO memberVO) {
		int uuid = myService.selectUuid(memberVO).getUuid();
		
		myService.insertBlackList(memberVO.getUuid(), uuid);
	}

	@PostMapping("/load")
	public List<MemberVO> loadAjax(String uuid) {
		System.out.println(uuid);
		List<MemberVO> list = myService.selectBlackList(Integer.parseInt(uuid));
		
		return list;
	}
	
	@PostMapping("/disblack")
	public void disblackAjax(MemberVO memberVO) {
		int uuid = myService.selectUuid(memberVO).getUuid();
		
		myService.deleteBlackList(uuid);
		
	}
	
	
}
