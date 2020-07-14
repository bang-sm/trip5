package com.sm.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.dao.TravelDAO;
import com.sm.domain.MemberVO;
import com.sm.domain.TravelInfoVO;
import com.sm.domain.TravelVO;

@Service
public class TravelService {

	@Autowired
	TravelDAO travelDAO;
	@Autowired
	HttpSession session;

	// 일지 등록
	public void storyRegist(TravelVO travelVO, TravelInfoVO travelinfoVO) {
		// MemberVO memberVO=new MemberVO();
		// memberVO = (MemberVO) session.getAttribute("userInfo");
		// travelVO.setUuid(memberVO.getUuid());
		travelDAO.storyRegist(travelVO, travelinfoVO);
	}

	/**
	 * @author smbang 일지의 기본 데이터 저장 하기 위한
	 *  매소드 제목 , 시작날짜 , 마지막날짜
	 */
	public void travel_firstSave(TravelVO travelVO) {
		MemberVO memberVO = new MemberVO();
		memberVO = (MemberVO) session.getAttribute("userInfo");
		travelVO.setUuid(memberVO.getUuid());
		
		travelDAO.travel_firstSave(travelVO);
	}

	/**
	 * @author smbang 
	 * 일지작성을 위해서 tsid,uuid 값을 통해  해당 일지의 정보를 받아온다. 
	 */
	public TravelVO getTravelStory(String tsid) {
		//MemberVO memberVO = new MemberVO();
		//memberVO = (MemberVO) session.getAttribute("userInfo");
		
		return travelDAO.getTravelStory(tsid,9);
	}

}
