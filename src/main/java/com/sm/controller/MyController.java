package com.sm.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
	
	// 보낸 쪽지함
	@PostMapping("/message")
	public List<MessageVO> message(MessageVO messageVO) throws ParseException {
		List<MessageVO> list = myService.sendMessage(messageVO);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format2 = new SimpleDateFormat("MM월dd일 HH:mm");
		for(int i = 0; i < list.size(); i++) {
			Date date = format1.parse(list.get(i).getMsgregdate());
			list.get(i).setMsgregdate(format2.format(date));
		}
		
		return list;
	}
	
	// 받은 쪽지 개수 구하기
	@PostMapping("/cntMsg")
	public int cntMsg(MessageVO messageVO) {
		
		int cnt = myService.countMessage(messageVO);
		
		return cnt;
		
//		List<MessageVO> list = myService.receiveMessage(messageVO);
//		
//		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		SimpleDateFormat format2 = new SimpleDateFormat("MM월dd일 HH:mm");
//		for(int i = 0; i < list.size(); i++) {
//			Date date = format1.parse(list.get(i).getMsgregdate());
//			list.get(i).setMsgregdate(format2.format(date));
//		}
//		
//		return list;
	}
	
	@PostMapping("/receive")
	public List<MessageVO> receiveMsg(MessageVO messageVO) throws ParseException{
		List<MessageVO> list = myService.receiveMessage(messageVO);
		
		messageVO.setFromid(messageVO.getSendid());
		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format2 = new SimpleDateFormat("MM월dd일 HH:mm");
		for(int i = 0; i < list.size(); i++) {
			Date date = format1.parse(list.get(i).getMsgregdate());
			list.get(i).setMsgregdate(format2.format(date));
		}
		
		return list;
	}
	
}
