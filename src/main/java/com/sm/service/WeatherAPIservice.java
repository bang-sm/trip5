package com.sm.service;

import java.net.URLDecoder;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeatherAPIservice {
	//@Value("spring.weather.key")
	//private String WEATHER_API_KEY;
	private String WEATHER_API_KEY="HkKlYlenzlKaNIWkkjRw3wdyNnwbX%2FC0RZEsmnspycI9DCpnPheT0QjG38u7pji5RsSzqN8CV8g1PmNcOOnh6Q%3D%3D";
	@Autowired
	private RestTemplate restTemplate;

	public String weatherData() throws Exception{
		
		ResponseEntity<String> response = null;
	
		String inputUrl="http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst";
		inputUrl+="?serviceKey="+URLDecoder.decode(WEATHER_API_KEY,"UTF-8");
		inputUrl+="&pageNo=1";
		inputUrl+="&base_date=20200629";
		inputUrl+="&base_time=1700";
		inputUrl+="&dataType=json";
		inputUrl+="&numOfRows=9";
		inputUrl+="&nx=1";
		inputUrl+="&ny=1";
		
		
	    try {
	    	response=restTemplate.getForEntity(inputUrl, String.class);
	    	Map<?, ?> parsingMap = objectMapper.readValue(response.getBody(), Map.class);
	        Map<?, ?> body = (Map<?, ?>) ((Map<?, ?>) parsingMap.get("response")).get("body");
	        Map<?, ?> headers = (Map<?, ?>) ((Map<?, ?>) parsingMap.get("response")).get("header");
	        
	        String resultCode = (String) headers.get("resultCode");
	        int totalCount = (int) body.get("totalCount");
	        	
	        System.out.println(resultCode+ " // "+totalCount);

		} catch (Exception e) {
			// TODO: handle exception
		}
	    System.out.println(response);
		
		return "서비스다녀왔습니다.";
	}
	

}
