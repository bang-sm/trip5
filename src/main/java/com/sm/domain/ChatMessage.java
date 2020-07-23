package com.sm.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
	private MessageType type;
	private String content;
	private String sender;
	
	private int uuid;
	
	List<String> participant = new ArrayList<String>();
	
	
	public enum MessageType{
		CHAT,
		JOIN,
		LEAVE
	}

	public ChatMessage() {
		System.out.println("ChatMessage()");
	}
}
