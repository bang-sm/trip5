package com.sm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.dao.MyinfoDAO;
import com.sm.domain.TravelVO;
import com.sm.domain.TravelViewVO;

@Service
public class MyinfoService {

	@Autowired
	MyinfoDAO myinfoDAO;
	
	// insert + update
	public void insertTravelView(TravelViewVO travelViewVO, int uuid) {
		
		List<TravelViewVO> tvList = new ArrayList<TravelViewVO>();
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		int updateCheck = 0;
		
		tvList = myinfoDAO.selectTravelViewByUuid(uuid);
		
		for (int i = 0; i < tvList.size(); i++) {
			
			int dbTsid = tvList.get(i).getTsid();
			int paramTsid = travelViewVO.getTsid();
			
			if (dbTsid == paramTsid) {
				paramMap.put("uuid", uuid);
				paramMap.put("tsid", paramTsid);
				
				myinfoDAO.updateViewDateBytsid(paramMap);
				updateCheck = 1;
			}
		}
		if (updateCheck == 0) {
			
			myinfoDAO.insertTravelView(travelViewVO);
		}
	}
	
	// select 
	public List<TravelViewVO> selectTravelView(int uuid) {
		List<TravelViewVO> tvList = new ArrayList<TravelViewVO>();
		tvList = myinfoDAO.selectTravelViewByUuid(uuid);
		return tvList;
	}
	
	// delete
	public void deleteDataAuto() {
		myinfoDAO.deleteTravelViewAuto();
	}
	
	public TravelVO selectTravelStoryByTsid(int tsid) {
		return myinfoDAO.selectTravelStoryByTsid(tsid);
	}
	
}
