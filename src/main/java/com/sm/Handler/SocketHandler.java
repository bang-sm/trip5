package com.sm.Handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.sm.controller.ChatController;
import com.sm.domain.ChatMessage;
import com.sm.domain.ChatMessage.MessageType;

@Component
public class SocketHandler {
	// extends TextWebSocketHandler
//	HashMap<String, WebSocketSession> sessionMap = new HashMap<>(); //웹소켓 세션을 담아둘 맵
//
//	@Autowired
//	HttpServletRequest request;
//	
//	@Override
//	public void handleTextMessage(WebSocketSession session, TextMessage message) {
//		//메시지 발송
//		String msg = message.getPayload();
//		System.out.println(msg + " ///");
//		
//		JSONObject obj = jsonToObjectParser(msg);
//		for(String key : sessionMap.keySet()) {
//			WebSocketSession wss = sessionMap.get(key);
//			try {
//				wss.sendMessage(new TextMessage(obj.toJSONString()));
//			} catch(Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//		//소켓 연결
//		super.afterConnectionEstablished(session);
//		sessionMap.put(session.getId(), session);
//		JSONObject obj = new JSONObject();
//		
//		obj.put("type", "getId");
//		obj.put("sessionId", session.getId());
////		session.sendMessage(new TextMessage(obj.toJSONString()));
//		
//		for(String key : sessionMap.keySet()) {
//			WebSocketSession wss = sessionMap.get(key);
//			try {
//				wss.sendMessage(new TextMessage(obj.toJSONString()));
//			} catch(Exception e) {
//				e.printStackTrace();
//			}
//		}
//		System.out.println(obj.toString());
//	}
//	
//	@Override
//	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//		//소켓 종료
//		sessionMap.remove(session.getId());
//		super.afterConnectionClosed(session, status);
//		System.out.println("소켓 종료!");
//		System.out.println(status + "///////////////////////////////");
//	}
//	
//	
//	private static JSONObject jsonToObjectParser(String jsonStr) {
//		JSONParser parser = new JSONParser();
//		JSONObject obj = null;
//		try {
//			obj = (JSONObject) parser.parse(jsonStr);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return obj;
//	}
	
	  private static final Logger logger = LoggerFactory.getLogger(SocketHandler.class);

	    @Autowired
	    private SimpMessageSendingOperations messagingTemplate;

	    @EventListener
	    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
	        logger.info("유저 connected");
	    }

	    @EventListener
	    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
	        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
	       	
	        String username = (String) headerAccessor.getSessionAttributes().get("username");
	        if(username != null) {
	            logger.info("나간 유저 : " + username);

	            ChatMessage chatMessage = new ChatMessage();
	            chatMessage.setType(MessageType.LEAVE);
	            chatMessage.setSender(username);
	            
	            messagingTemplate.convertAndSend("/topic/public", chatMessage);
	            System.out.println("보내짐???");
	        }
	    }
}





