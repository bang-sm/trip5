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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sm.domain.PlacelistVo;
import com.sm.service.PlacelistService;

@RestController
@RequestMapping("/wish/rest")
public class WishPlaceRestController {

	private static final Logger logger = LoggerFactory.getLogger(WishPlaceRestController.class);
	
	@Autowired
	PlacelistService service;
	
	@PostMapping(value="/naver")
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
	@PostMapping(value="/bookmark")
	public void bookmark(int bookmark,int placeid) throws Exception{
		service.bookmark(bookmark,placeid);
	}
	
	@PostMapping(value="/checkbox")
	public void checkbox(int placecheck, int placeid) throws Exception{
		service.checkbox(placecheck,placeid);
	}
	
	@PostMapping(value="/goplace")
	public List<PlacelistVo> goplace(int placecheck,int uuid) throws Exception{
		return service.goplace(placecheck,uuid);
	}
	
	@PostMapping(value="/buttoncategory")
	public List<PlacelistVo> buttoncategory(int placecategory,int placecheck,int uuid) throws Exception{
		return service.buttoncategory(placecategory,placecheck,uuid);
	}
	
	@PostMapping(value="/delete")
	public void delete(int placeid) throws Exception{
		System.out.println("딜리트 맵핑");
		System.out.println(placeid);
		//service.delete(placeid);
	}
	
	

}
