package com.sm.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sm.domain.MemberVO;
import com.sm.domain.MessageVO;
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
		List<MemberVO> list = myService.selectBlackList(Integer.parseInt(uuid));
		
		return list;
	}
	
	@PostMapping("/disblack")
	public void disblackAjax(MemberVO memberVO) {
		int uuid = myService.selectUuid(memberVO).getUuid();
		
		myService.deleteBlackList(uuid);
	}
	
	@PostMapping("/sendMsg")
	public void sendMsg(MessageVO messageVO) {
		myService.sendToMsg(messageVO);
	}
	
	@PostMapping("/sendOther")
	public int sendMsgOther(MemberVO memberVO) {
		int uuid = myService.selectUuid(memberVO).getUuid();
		return uuid;
	}
	
	@PostMapping("/gototrash")
	public void delete(HttpServletRequest request) {
		String [] msgid = request.getParameterValues("msgid");
		String sendid = request.getParameter("sendid");
		
		myService.gotoTrash(msgid, sendid);
		
		System.out.println(sendid + "<-- sendid");
	}
	
	@PostMapping("/gotoTrashRead")
	public int gotoTrashRead(int msgid) {
		int cnt = myService.gotoTrashRead(msgid);
		
		return cnt;
	}
	
	@PostMapping("/prevClip")
	public MessageVO prevClip(MessageVO messageVO) {
		return myService.prevClip(messageVO);
	}
	
	@PostMapping("/nextClip")
	public MessageVO nextClip(MessageVO messageVO) {
		return myService.nextClip(messageVO);
	}
}
