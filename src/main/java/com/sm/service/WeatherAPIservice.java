package com.sm.service;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sm.domain.WeatherInfoVO;

@Service
public class WeatherAPIservice {
	//@Value("spring.weather.key")
	//private String WEATHER_API_KEY;
	private String WEATHER_API_KEY="HkKlYlenzlKaNIWkkjRw3wdyNnwbX%2FC0RZEsmnspycI9DCpnPheT0QjG38u7pji5RsSzqN8CV8g1PmNcOOnh6Q%3D%3D";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private String nowTime = baseTime3hr(now());
	private String nowDate = baseDate(now());
	private int yesterday = 0;
	
	// API 변수 설정
	public String[] now() {
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd:HHmm");
			String realTime = formatter.format(new Date());
			String [] timeArr = realTime.split(":");
			return timeArr;
		
	} // end now()
	
	public String baseTime3hr(String[] date) {
		
		String time = date[1].substring(0,2);
		String baseTime = "";
		
		int timeResult = (int)(Integer.parseInt(time) / 3) -1;
		
		/*
		 * Base Time
		 *  0200, 0500, 0800, 1100, 1400, 1700, 2000, 2300 (1일 8회)
		 * 	  0		1	  2		3	  4		5	  6     7
		 */
		
		if(timeResult == -1) { // 전 시간으로 검색해야 현재시간을 알 수 있다.
			timeResult = 7;
			yesterday = 1;
		}
		
		switch (timeResult) {
		case 0: baseTime = "2300";
			break;
		case 1: baseTime = "0200";
			break;
		case 2: baseTime = "0500";
			break;
		case 3: baseTime = "0800";
			break;
		case 4: baseTime = "1100";
			break;
		case 5: baseTime = "1400";
			break;
		case 6: baseTime = "1700";
			break;
		case 7: baseTime = "2000";
			break;
		}
		return baseTime;
		
//		switch (timeResult) {
//		case 0: baseTime = "0200";
//			break;
//		case 1: baseTime = "0500";
//			break;
//		case 2: baseTime = "0800";
//			break;
//		case 3: baseTime = "1100";
//			break;
//		case 4: baseTime = "1400";
//			break;
//		case 5: baseTime = "1700";
//			break;
//		case 6: baseTime = "2000";
//			break;
//		case 7: baseTime = "2300";
//			break;
//		}
//		return baseTime;
	
	} // end baseTime()
	
	
	public String baseDate(String [] date) {
		
		String baseDate = date[0];
		
		if(yesterday == 1) {
			
		SimpleDateFormat datefix = new SimpleDateFormat("yyyyMMdd");
	      Calendar cal = Calendar.getInstance();
	      cal.add(Calendar.DATE, -1);
	      String yesterday = datefix.format(cal.getTime());
			
	      baseDate = yesterday;
		}
		
		return baseDate;
	
	} // end baseDate() 
	
	public List<WeatherInfoVO> weatherData(int localnx, int localny) throws Exception{
		
		ResponseEntity<String> response = null;
		List<WeatherInfoVO> list=new ArrayList<WeatherInfoVO>();
	
		String inputUrl="http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst";
		inputUrl+="?serviceKey="+URLDecoder.decode(WEATHER_API_KEY,"UTF-8");
		inputUrl+="&pageNo=1";
		inputUrl+="&base_date=";
		inputUrl+=nowDate;
		inputUrl+="&base_time=";
		inputUrl+=nowTime;
		inputUrl+="&dataType=json";
		inputUrl+="&numOfRows=300";
		inputUrl+="&nx=";
		inputUrl+=localnx+"";
		inputUrl+="&ny=";
		inputUrl+=localny+"";
		
	    try {
	    	response = restTemplate.getForEntity(inputUrl, String.class);
	    	
	    	Map<?, ?> parsingMap = objectMapper.readValue(response.getBody(), Map.class);
	        Map<?, ?> body = (Map<?, ?>) ((Map<?, ?>) parsingMap.get("response")).get("body");
	        Map<?, ?> headers = (Map<?, ?>) ((Map<?, ?>) parsingMap.get("response")).get("header");
	        
	        String resultCode = (String) headers.get("resultCode");
	        int totalCount = (int) body.get("totalCount");
	        	
	        System.out.println( resultCode+ " // " + totalCount);
	        
	        List<?> items = (List<?>)((Map<?,?>) body.get("items")).get("item");  
	        
	        String fcstDate = "";
	        String fcstTime = "";
	        
	        String category = "";
	        double fcstValue = 0;
	        
	        for(Object obj : items) {
	        	
	        	Map<?,?> item = (Map<?,?>) obj;
	        	
	        	fcstDate = String.valueOf(item.get("fcstDate"));
	        	fcstTime = String.valueOf(item.get("fcstTime"));
	        	
	        	category = String.valueOf(item.get("category"));
	        	fcstValue = Double.parseDouble(String.valueOf(item.get("fcstValue")));
	        	
	        	WeatherInfoVO infoVO = new WeatherInfoVO();
	        	
	        	infoVO.setFcstDate(fcstDate);
	        	infoVO.setFcstTime(fcstTime);
	        	infoVO.setCategory(category);
	        	infoVO.setFcstValue(fcstValue);
	        	list.add(infoVO);
				
				// 초기화
				fcstDate = null;
				fcstTime = null;
				category = null;
				fcstValue = 0.0;
	        	
	        } // end for

		} catch (Exception e) {
			System.out.println(e.getMessage()  +"위에서 예외처리됨");
			e.printStackTrace();
		}
	    
	    System.out.println(response);
	    
	    return list;
	}
	
	public Map<String, Object> sortNowData(List<WeatherInfoVO> list) {

		Map<String, Object> nowData = new HashMap<String, Object>();
		
		String nowDate = list.get(0).getFcstDate();
		String nowTime = list.get(0).getFcstTime();
		
		String nowDay = nowDate.substring(2);
		String nowMonth = nowDate.substring(4, 6);
		nowData.put("nowDay", nowDay);
		nowData.put("nowMonth", nowMonth);
		
		for (int i = 0; i < list.size(); i++) {
			String category = list.get(i).getCategory();
			
			if(list.get(i).getFcstTime().equals(nowTime) && list.get(i).getFcstDate().equals(nowDate)) {
				
				switch (category) {
				
				case "POP": 
					nowData.put("POP", list.get(i).getFcstValue());
					break;
					
				case "REH":
					nowData.put("HUM", list.get(i).getFcstValue());
					break;
					
				case "SKY": 
					nowData.put("SKY", list.get(i).getFcstValue());
					break;
					
				case "PTY": 
					nowData.put("PTY", list.get(i).getFcstValue()); 
					break;
					
				case "T3H": 
					nowData.put("TEMP", list.get(i).getFcstValue());
					break;
					
				case "TMN": 
					nowData.put("MIN", list.get(i).getFcstValue());
					break;
					
				case "TMX": 
					nowData.put("MAX", list.get(i).getFcstValue());
					break;
					
				case "VEC": 
					nowData.put("VEC", list.get(i).getFcstValue());
					break;
					
				case "WSD": 
					nowData.put("WSD", list.get(i).getFcstValue());
					break;

				default:
					break;
				}
				
			}
			
		}
		return nowData;
	}

}
