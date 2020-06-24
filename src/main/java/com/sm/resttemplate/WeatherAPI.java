package com.sm.resttemplate;

import java.util.HashMap;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class WeatherAPI {
	
	@Value("${spring.weather.key}")
	private String WEATHER_API_KEY;

	@GetMapping("/getWeatherData")
	public String callWeatherAPI() {
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		String jsonInString = "";
		
		
		try {
			
			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
			factory.setConnectTimeout(5000);
			factory.setReadTimeout(5000);
			RestTemplate restTemplate = new RestTemplate(factory);
			
			HttpHeaders header = new HttpHeaders();
			HttpEntity<?>entity = new HttpEntity<>(header);
			
			String urlStr = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?"
					+ "serviceKey" + WEATHER_API_KEY
					+ "pageNo" + "1" 
					+ "numOfRows" + "255"
					+ "dataType" + "JSON"
					+ "base_date" + "20200622"
					+ "base_time" + "0500"
					+ "nx" + "60"
					+ "ny" + "127"
					;
			
//			UriComponents uri = UriComponentsBuilder.fromHttpUrl(urlStr);
			
			// API 호출하며 MAP 타입으로 전달 받는다.
			ResponseEntity<Map>resultMap = restTemplate.exchange(urlStr, HttpMethod.GET, entity, Map.class);
			
			result.put("statusCode", resultMap.getStatusCodeValue()); // statusCode 확인
			result.put("header", resultMap.getHeaders()); // 헤더정보
			result.put("body", resultMap.getBody()); // 실제 데이터
			
			ObjectMapper mapper = new ObjectMapper();
			jsonInString = mapper.writeValueAsString(resultMap.getBody());
			
			
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			
			result.put("statusCode", e.getRawStatusCode());
			result.put("body" ,e.getStatusText());
			System.out.println(e.toString());
			
		} catch (Exception e) {
			result.put("statusCode", 999);
			result.put("body", "exception error");
			System.out.println(e.toString());
		
		}
		return jsonInString;
	}
	
}
