package com.sm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sm.domain.ChatMessage;
import com.sm.domain.Participant;

import lombok.Getter;
import lombok.Setter;

@Controller
@Setter
@Getter
public class ChatController {
	private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

	private List<String> chatParticipant = new ArrayList<String>();
	
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
}





