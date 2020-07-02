package com.sm.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.dao.TravelDAO;
import com.sm.domain.TravelInfoVO;
import com.sm.domain.TravelVO;

@Service
public class TravelService {
	
	@Autowired
	TravelDAO travelDAO;
	@Autowired
	HttpSession session;

	//일지 등록
	public void storyRegist(TravelVO travelVO, TravelInfoVO travelinfoVO) {
		//MemberVO memberVO=new MemberVO();
		//memberVO = (MemberVO) session.getAttribute("userInfo");
		//travelVO.setUuid(memberVO.getUuid());
		travelDAO.storyRegist(travelVO,travelinfoVO);
	}

}
