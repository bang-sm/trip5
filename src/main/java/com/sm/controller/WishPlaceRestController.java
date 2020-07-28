package com.sm.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sm.domain.MemberVO;
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
	
	
	@PostMapping(value="/delete")
	public void delete(int placeid) throws Exception{
		System.out.println("딜리트 맵핑");
		System.out.println(placeid);
		//service.delete(placeid);
	}
	
	@PostMapping(value="/count")
	public PlacelistVo chartcount(HttpSession session) throws Exception{
		MemberVO vo=vo=(MemberVO) session.getAttribute("userInfo");  
		int uuid = vo.getUuid();
		logger.info("uuid 값 은 : "+uuid);
		PlacelistVo plvo = new PlacelistVo();
		plvo.setFoodcount(service.chartcount(0, uuid));
		plvo.setCafecount(service.chartcount(1, uuid));
		plvo.setPlacecount(service.chartcount(2, uuid));
		
		
		
		return plvo;
	}
	
	@PostMapping(value="/bar")
	public PlacelistVo barplan(HttpSession session) throws Exception{
		MemberVO vo=vo=(MemberVO) session.getAttribute("userInfo");  
		int uuid = vo.getUuid();
		logger.info("uuid 값 은 : "+uuid);
		PlacelistVo plvo = new PlacelistVo();
		plvo.setPlanfood(service.barchart(0, 0, uuid));
		plvo.setPlancafe(service.barchart(1, 0, uuid));
		plvo.setPlanplace(service.barchart(2, 0, uuid));
		plvo.setVisitfood(service.barchart(0, 1, uuid));
		plvo.setVisitcafe(service.barchart(1, 1, uuid));
		plvo.setVisitplace(service.barchart(2, 1, uuid));
		
		return plvo;
	}
	
	@PostMapping(value="/line")
	public int[] line(HttpSession session) throws Exception{
		MemberVO vo=vo=(MemberVO) session.getAttribute("userInfo");  
		int uuid = vo.getUuid();
		logger.info("uuid 값 은 : "+uuid);
		int [] arr = new int[12];
		PlacelistVo plvo = new PlacelistVo();
		for(int i=1;i<=arr.length;i++) {
			arr[i-1]=service.linechart(i,i+1,uuid);
		}
		return arr;
	}
	
	@PostMapping(value="/area")
	public int[] area(HttpSession session) throws Exception{
		MemberVO vo=vo=(MemberVO) session.getAttribute("userInfo");  
		int uuid = vo.getUuid();
		logger.info("uuid 값 은 : "+uuid);
		
		int [] arr = new int[17];
		for(int i=0;i<arr.length;i++) {
			arr[i]= service.area(i,uuid);
		}
		
		
		
		return arr;
	}

}
