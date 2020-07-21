package com.sm.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.dao.TravelDAO;
import com.sm.domain.TravelInfoRootVO;
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
	 *  매소드 제목 , 시작날짜 , 마지막날짜 , 기간 만큼 미리  DAY별 임시저장하고 키를 미리 받아 리턴시킨다. 
	 */
	public void travel_firstSave(TravelVO travelVO) {
		//MemberVO memberVO = new MemberVO();
		//memberVO = (MemberVO) session.getAttribute("userInfo");
		//travelVO.setUuid(memberVO.getUuid());
		
		travelDAO.travel_firstSave(travelVO);
	}

	/**
	 * @author smbang 
	 * 일지작성을 위해서 tsid,uuid 값을 통해  해당 일지의 정보를 받아온다. 
	 */
	public TravelVO getTravelStory(String tsid) {
		//MemberVO memberVO = new MemberVO();
		//memberVO = (MemberVO) session.getAttribute("userInfo");
		
		return travelDAO.getTravelStory(tsid,7);
	}
	
	/**
	 * @author smbang 
	 * 일지의 디테일 getTravelInfo 기본 정보를 받아오기위해서
	 * 일지의 디테일 TravelInfoRootVO 루트정보를 받아오기위해서
	 */
	public List<TravelInfoVO> getTravelInfo(String tsid) {
		return travelDAO.getTravelInfo(tsid);
	}
	public List<TravelInfoRootVO> getTravelRootInfo(String tsid) {
		// TODO Auto-generated method stub
		return travelDAO.getTravelRootInfo(tsid);
	}

	/**
	 * @author smbang 
	 * 일지 임시저장 데이터를 저장합니다.
	 */
	public void tempSave(TravelInfoVO travelInfoVO, TravelInfoRootVO travelInfoRootVO) {
		travelDAO.tempSave(travelInfoVO,travelInfoRootVO);
	}
	
	/**
	 * @author smbang 
	 * 경로삭제
	 */
	public void travel_root_delete(int tsirootorder, int tsid) {
		HashMap<String, Integer> map=new HashMap<String, Integer>();
		map.put("tsirootorder", tsirootorder);
		map.put("tsid", tsid);
		travelDAO.travel_root_delete(map);
	}

}
