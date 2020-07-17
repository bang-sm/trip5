package com.sm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.dao.WeatherLocalDAO;
import com.sm.domain.WeatherLocalVO;

@Service
public class WeatherService {
	
	@Autowired
	WeatherLocalDAO weatherLocalDAO;

	public List<WeatherLocalVO> weatherLocalName(WeatherLocalVO vo) {
		
//		localdepth = 0;
//		localparent = 0;
//		
//		String paramdepth = (String) model.getAttribute("localdepth");
//		String paramparent = (String) model.getAttribute("localparent");
//		
//		try {
//		
//			if(paramdepth != null) localdepth = Integer.parseInt(paramdepth);
//			if(paramparent != null) localparent = Integer.parseInt(paramdepth);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		List<WeatherLocalVO> list = new ArrayList<WeatherLocalVO>();
		list = weatherLocalDAO.weatherLocalName(vo);	
		
		 return list;
	}

//	public List<WeatherLocalVO> lowLocalWeather(int localuid) {
//		// TODO Auto-generated method stub
//		return weatherLocalDAO.lowLocalWeather(localuid);
//	}
	
	

}
