package com.sm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sm.controller.MemberController;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class ChatController {
	private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
	
	// 채팅 페이지
	@GetMapping(value="/chat")
    public String chatTest() {
        return "chatting/chat";
    }
}