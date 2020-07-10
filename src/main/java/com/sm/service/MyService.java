package com.sm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.dao.MyDAO;
import com.sm.domain.MemberVO;
import com.sm.domain.MessageVO;

@Service
public class MyService {

	@Autowired
	MyDAO dao;
	
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
}
