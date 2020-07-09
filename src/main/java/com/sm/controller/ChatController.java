package com.sm.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class ChatController {
	private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
	
	// 채팅 페이지
	@GetMapping(value="/chat")
    public String chatTest(HttpSession session, Model model) {
		
		model.addAttribute("uuid", session.getAttribute("userInfo"));
        return "chatting/chat";
    }
}