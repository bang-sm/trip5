package com.sm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.dao.MyDAO;
import com.sm.domain.MemberVO;

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
	
	public int selectBlackList(int myuid) {
		return dao.selectBlack(myuid);
	}
}
