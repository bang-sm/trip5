package com.sm.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sm.domain.PlacelistVo;
import com.sm.service.PlacelistService;

@RestController
public class RestAPIController {

	private static final Logger logger = LoggerFactory.getLogger(RestAPIController.class);
	
	@Autowired
	PlacelistService service;
	
	@GetMapping("/naver")
	public ResponseEntity<String> callAPI(String keyword) throws JsonProcessingException {
		String clientId ="SCrW8Vqyf_Whz991VMq5";
		String clientSecret = "Xyeh7whQVZ";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		header.add("X-Naver-Client-Id", clientId);
		header.add("X-Naver-Client-Secret", clientSecret);
		

		String new_url=String.format("https://openapi.naver.com/v1/search/blog?query=%s&display=100&start=1&sort=sim",keyword);
		System.out.println(new_url);
		return restTemplate.exchange(new_url, HttpMethod.GET, new HttpEntity(header), String.class);
		
	}
	@GetMapping("/bookmark")
	public void bookmark(int bookmark,int placeid) throws Exception{
		service.bookmark(bookmark,placeid);
	}
	
	@GetMapping("/checkbox")
	public void checkbox(int placecheck, int placeid) throws Exception{
		service.checkbox(placecheck,placeid);
	}
	
	@GetMapping("/goplace")
	public List<PlacelistVo> goplace(int placecheck) throws Exception{
		return service.goplace(placecheck);
	}
	
	@GetMapping("/buttoncategory")
	public List<PlacelistVo> buttoncategory(int placecategory,int placecheck) throws Exception{
		return service.buttoncategory(placecategory,placecheck);
	}

}
