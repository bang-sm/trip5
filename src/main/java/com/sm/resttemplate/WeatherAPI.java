
package com.sm.resttemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sm.controller.HomeController;

@RestController
public class WeatherAPI {
	
	@Value("${spring.weather.key}")
	private String WEATHER_API_KEY;

	@GetMapping("/weather")
	public String callWeatherAPI() throws UnsupportedEncodingException {
		
		final Logger logger = LoggerFactory.getLogger(HomeController.class);
		
		ResponseEntity<String> response = null;
		HashMap<String, Object> result = new HashMap<String, Object>();
		String jsonInString = "";
		
//		String urlStr = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst"
//				+ "?serviceKey=" + URLDecoder.decode(WEATHER_API_KEY,"UTF-8")
//				+ "&pageNo=" + "1" 
//				+ "&numOfRows=" + "225"
//				+ "&dataType=" + "JSON"
//				+ "&base_date=" + "20200625"
//				+ "&base_time=" + "0500"
//				+ "&nx=" + "60"
//				+ "&ny=" + "127"
//				;
		
		String urlStr = WEATHER_API_KEY;
		
		System.out.println(urlStr);
		
		try {
			
//			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
//			factory.setConnectTimeout(5000);
//			factory.setReadTimeout(5000);
			RestTemplate restTemplate = new RestTemplate();
			ObjectMapper objectMapper = new ObjectMapper();
			HttpHeaders header = new HttpHeaders();
			
			HttpEntity<?>entity = new HttpEntity<>(header);
			
//			UriComponents uri = UriComponentsBuilder.fromHttpUrl(urlStr).build();
			
			response = restTemplate.getForEntity(urlStr, String.class);
			Map parsingMap = objectMapper.readValue(response.getBody(), Map.class);
			Map body = (Map)((Map)parsingMap.get("response")).get("body");
			
			Map headers = (Map)((Map)parsingMap.get("response")).get("header");
			String resultCode = (String) headers.get("resultCode");
			
			jsonInString = resultCode;
			
			int totalCount = (int) body.get("totalCount");
			
			List items = (List)((Map) body.get("items")).get("item");
			
			for(Object obj: items) {
				Map item = (Map) obj;
			}
			
			
			// API 호출하며 MAP 타입으로 전달 받는다.
//			ResponseEntity<Map>resultMap = restTemplate.exchange(uri.toUriString(), HttpMethod.GET, entity, Map.class);
			
//			result.put("statusCode", resultMap.getStatusCodeValue()); // statusCode 확인
//			result.put("header", resultMap.getHeaders()); // 헤더정보
//			result.put("body", resultMap.getBody()); // 실제 데이터
			
//			ObjectMapper mapper = new ObjectMapper();
//			jsonInString = mapper.writeValueAsString(resultMap.getBody());
			
			logger.info(jsonInString);
			
			
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
