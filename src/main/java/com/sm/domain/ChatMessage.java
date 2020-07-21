package com.sm.domain;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {
	private MessageType type;
	private String content;
	private String sender;
	private int uuid;
	List<String> participant;
	
	public enum MessageType{
		CHAT,
		JOIN,
		LEAVE
	}
	
	
	
}
