package com.sm.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sm.domain.ChatMessage;
import com.sm.domain.MessageVO;
import com.sm.service.MyService;

@Controller
public class ChatController {
	private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

	private List<String> chatParticipant = new ArrayList<String>();
	
	@Autowired
	private MyService myService;
	
	// 채팅 페이지
	@GetMapping(value = "/chatting/chat")
	public String chatTest(HttpSession session, Model model) {

		model.addAttribute("uuid", session.getAttribute("userInfo"));
		return "chatting/chat";
	}
	
	@MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

	@MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        
        System.out.println(headerAccessor + "<-- headeraccess");
        
        if(!chatParticipant.contains(chatMessage.getSender())) {
        	chatParticipant.add(chatMessage.getSender());
        }
        
        chatMessage.setParticipant(chatParticipant);
        
        return chatMessage;
    }
	
	@MessageMapping("/chat.outUser")
	@SendTo("/topic/public")
	public void outUser(@Payload ChatMessage chatMessage) {
		System.out.println("outUser()실행/////" + chatMessage.getSender());
		chatParticipant.remove(chatMessage.getSender());
		for(int i = 0; i<chatParticipant.size(); i++) {
        	System.out.println(chatParticipant.get(i) + " remove 후 ");
        }
	}
	
	//쪽지 알람
	@MessageMapping("/chat.sendAlarm")
	@SendTo("/topic/public")
	public ChatMessage sendAlarm(@Payload ChatMessage chatMessage) {
		System.out.println(chatMessage.getContent() + " ///////////////////////////");
		
		return chatMessage;
	}
	
	//보낸 메일함
	@GetMapping("/my/clipSend")
	public String message(MessageVO messageVO, Model model) throws ParseException {
		List<MessageVO> list = myService.sendMessage(messageVO);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format2 = new SimpleDateFormat("MM월dd일 HH:mm");
		for(int i = 0; i < list.size(); i++) {
			Date date = format1.parse(list.get(i).getMsgregdate());
			list.get(i).setMsgregdate(format2.format(date));
		}
		
		messageVO.setFromid(messageVO.getSendid());
		
		int count = myService.countMessage(messageVO);
		
		model.addAttribute("cntMsg", count);
		model.addAttribute("list", list);
		
		return "/chatting/messageSend";
	}
	
	// 받은 메일함
	@GetMapping("/my/clipReceive")
	public String clipReceive(MessageVO messageVO, Model model) throws ParseException	{
		List<MessageVO> list = myService.receiveMessage(messageVO);
		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format2 = new SimpleDateFormat("MM월dd일 HH:mm");
		for(int i = 0; i < list.size(); i++) {
			Date date = format1.parse(list.get(i).getMsgregdate());
			list.get(i).setMsgregdate(format2.format(date));
		}
		
		int count = myService.countMessage(messageVO);
		
		model.addAttribute("list", list);
		model.addAttribute("cntMsg", count);
		
		return "/chatting/messageReceive";
	}
	
	// 휴지통
	@GetMapping("/my/clipTrash")
	public String clipTrash(MessageVO messageVO, Model model) throws ParseException {
		
		List<MessageVO> list = myService.clipTrash(messageVO);
		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format2 = new SimpleDateFormat("MM월dd일 HH:mm");
		
		for(int i = 0; i < list.size(); i++) {
			Date date = format1.parse(list.get(i).getMsgregdate());
			list.get(i).setMsgregdate(format2.format(date));
		}
		
		int cnt = myService.countMessage(messageVO);
		
		model.addAttribute("list", list);
		model.addAttribute("cntMsg", cnt);
		
		return "/chatting/messageTrash";
	}
	
	// 쪽지 읽기
	@GetMapping("/my/clipread")
	public String messageRead(MessageVO messageVO, String reader, Model model) {
		int read = 0;
		MessageVO msg= myService.clipRead(messageVO);
		System.out.println(reader + " <-- reader");
		
		if(messageVO.getFromid() != 0) {
			read = myService.readed(messageVO);
		}
		
		int cnt = myService.countMessage(messageVO);
		
		model.addAttribute("msg", msg);
		model.addAttribute("cntMsg", cnt);
		
		return "/chatting/messageRead";
	}
}





