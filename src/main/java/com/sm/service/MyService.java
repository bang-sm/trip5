package com.sm.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.dao.MyDAO;
import com.sm.domain.MemberVO;
import com.sm.domain.MessageVO;

@Service
public class MyService {

	@Autowired
	MyDAO dao;
	
	@Autowired
	HttpSession session;
	
	public MemberVO selectUuid(MemberVO memberVO) {
		
		return dao.selectUuid(memberVO);
	}
	
	public int insertBlackList(int myuid, int otheruid) {
		return dao.insertBlack(myuid, otheruid);
	}
	
	public List<MemberVO> selectBlackList(int uuid) {
		return dao.selectBlack(uuid);
	}
	
	public int deleteBlackList(int otheruid) {
		return dao.deleteBlackList(otheruid);
	}
	
	public int sendToMsg(MessageVO messageVO) {
		return dao.sendToMsg(messageVO);
	}
	
	public List<MessageVO> sendMessage(MessageVO messageVO){
		return dao.sendMessage(messageVO);
	}
	
	public int countMessage(MessageVO messageVO) {
		MemberVO mem = (MemberVO) session.getAttribute("userInfo");
		
		messageVO.setFromid(mem.getUuid());
		return dao.countMessage(messageVO);
	}
	
	public List<MessageVO> receiveMessage(MessageVO messageVO){
		return dao.receiveMessage(messageVO);
	}
	
	public MessageVO clipRead(MessageVO messageVO) {
		return dao.clipRead(messageVO);
	}
	
	public int readed(MessageVO messageVO) {
		return dao.readed(messageVO);
	}
	
	public List<MessageVO> clipTrash(MessageVO messageVO) {
		List<MessageVO> msg = dao.clipTrash(messageVO);
		
		for(int i =0; i<msg.size(); i++) {
			if(msg.get(i).getMsgdelrcv() == 1) {
				msg.get(i).setOthernick(dao.selectNick(msg.get(i).getSendid()));
			}
		}
		
		return msg;
	}
	
	public int gotoTrash(String [] msgid, String sendid) {
		return dao.gotoTrash(msgid, sendid);
	}
	
	public int gotoTrashRead(int msgid) {
		MessageVO msg = dao.selectByMsgid(msgid);
		MemberVO mem = (MemberVO) session.getAttribute("userInfo");
		System.out.println(msg.getSendid() + " < -- msg.getsendid");
		System.out.println(mem.getUuid() + " < -- mem.getuuid");
		
		int cnt;
		if(mem.getUuid() == msg.getSendid()) {
			cnt = dao.gotoTrashRead(msg);
		} else {
			msg.setSendid(0);
			cnt = dao.gotoTrashRead(msg);
		}
		
		return cnt;
	}
	
	public MessageVO prevClip(MessageVO messageVO) {
		return dao.prevClip(messageVO);
	}
	
	public MessageVO nextClip(MessageVO messageVO) {
		return dao.nextClip(messageVO);
	}
}

