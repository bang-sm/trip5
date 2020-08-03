package com.sm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.dao.WeatherLocalDAO;
import com.sm.domain.MemberVO;
import com.sm.domain.WeatherLocalVO;

@Service
public class WeatherService {
	
	@Autowired
	WeatherLocalDAO weatherLocalDAO;

	public List<WeatherLocalVO> weatherLocalName(int localparent) {

		List<WeatherLocalVO> list = new ArrayList<WeatherLocalVO>();
		list = weatherLocalDAO.weatherLocalName(localparent);	
		
		 return list;
	}
	
	public List<WeatherLocalVO> selectParentweather(){
		return weatherLocalDAO.selectParentWeather();
	}
	
	public int selectWeatherlocaluid(int uuid) {
		System.out.println(uuid);
		return weatherLocalDAO.selectWeatherlocaluid(uuid);
	}
	
	public int updateWeatherlocaluid(HttpSession httpSession ,int weatherlocaluid) {
		
		Map<String, Integer> localmap = new HashMap<String, Integer>();
		
		MemberVO memberVO = new MemberVO();
		int check = 0;

		memberVO = (MemberVO) httpSession.getAttribute("userInfo");
		
		if(memberVO != null) {
			
			localmap.put("uuid", memberVO.getUuid());
			localmap.put("weatherlocaluid", weatherlocaluid);
			
			weatherLocalDAO.updateWeatherlocaluid(localmap);
			check = 1;
		}
		
		return check;
	}
	
	public WeatherLocalVO selectlocalInfoBylocaluid(int localuid) {
		return weatherLocalDAO.selectlocalInfoBylocaluid(localuid);
		
	}


	
	

}
