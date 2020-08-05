package com.sm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sm.domain.MemberVO;
import com.sm.domain.WeatherInfoVO;
import com.sm.domain.WeatherLocalVO;
import com.sm.service.WeatherAPIservice;
import com.sm.service.WeatherService;

@Controller
@RequestMapping("/weather")
public class WeatherController {

	@Autowired
	WeatherAPIservice weatherAPIservice;
	
	@Autowired
	WeatherService weatherService;
	
	@GetMapping("/weather")
	public String callWeatherAPI(Model model) throws Exception {
		
		List<WeatherLocalVO> list = new ArrayList<WeatherLocalVO>();
		
		list = weatherService.selectParentweather();
		model.addAttribute("list", list);
		return "weather/weather";
	}
	
	
	@ResponseBody
	@PostMapping("/depth2")
	public List<WeatherLocalVO> getLowLocal(int weatherparentuid) throws Exception {
		System.out.println(weatherparentuid);
		System.out.println(weatherService.weatherLocalName(weatherparentuid));
		return weatherService.weatherLocalName(weatherparentuid);
	}
	
	@ResponseBody
	@PostMapping("/api")
	public List<WeatherInfoVO> getWeatherAPI(int weatherlocalnx, int weatherlocalny, Model model){
		
		List<WeatherInfoVO> list = new ArrayList<WeatherInfoVO>();
		Map<String, Object> nowData = new HashMap<String, Object>();
		System.out.println("api 가져오나? (POST)");
		System.out.println("x값:" + weatherlocalnx + " y값:" + weatherlocalny );
		
		try {
			list = weatherAPIservice.weatherData(weatherlocalnx, weatherlocalny);
			nowData = weatherAPIservice.sortNowData(list);
			
			model.addAttribute("nowData", nowData);
			
		} catch (Exception e) {
			System.out.println("api 에러(오류)" + e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println(list.get(0).getCategory());
		return list;
	}
		
	@ResponseBody
	@PostMapping("/updateuid")
	public String updateWeatherlocaluid(HttpSession httpSession, int weatherlocaluid) {
		
		String returnString = "FALSE";
		int check = 0;
		
		check = weatherService.updateWeatherlocaluid(httpSession, weatherlocaluid);
		
		if(check == 1) {
			returnString = "OK";
		}
		
		return returnString;
	}
	
	
	@ResponseBody
	@PostMapping("/getuid")
	public int getWeatherlocaluid(HttpSession httpSession) throws Exception{
		int result = 0;
		MemberVO memberVO = new MemberVO();
		
		if(httpSession != null) {
			memberVO = (MemberVO) httpSession.getAttribute("userInfo");
			if(memberVO != null) {
				int uuid = memberVO.getUuid();
				result = weatherService.selectWeatherlocaluid(uuid);
			}
		}
		return result;
	}
	
	@ResponseBody
	@PostMapping("/getlocalinfo")
	public WeatherLocalVO selectlocalInfoBylocaluid(int localuid) {
		
		WeatherLocalVO weatherLocalVO = new WeatherLocalVO();
		weatherLocalVO = weatherService.selectlocalInfoBylocaluid(localuid);
		return weatherLocalVO;
	}
	
	@GetMapping("/weatherModal")
	public String getWeatherModal(Model model) {
		
		return "weather/weatherModal";
	}
	
	@ResponseBody
	@PostMapping("/weatherModal")
	public Map<String, Object> postWeatherModal(HttpSession httpSession){
		
		Map<String, Object> postMap = new HashMap<String, Object>();
		List<WeatherInfoVO> postList = new ArrayList<WeatherInfoVO>();
		int weatherlocaluid = 1;
		
		MemberVO memberVO = new MemberVO();
		WeatherLocalVO weatherLocalVO = new WeatherLocalVO();
		
		if(httpSession != null) {
			memberVO = (MemberVO) httpSession.getAttribute("userInfo");  
			
			if(memberVO != null) {
				
				int uuid = memberVO.getUuid();
				weatherlocaluid = weatherService.selectWeatherlocaluid(uuid);
				
				if(weatherlocaluid == 0) {
					
					weatherlocaluid = 1;
				}
			} 
		} 
		
		weatherLocalVO = weatherService.selectlocalInfoBylocaluid(weatherlocaluid);
		
		int weatherlocalnx = weatherLocalVO.getLocalnx();
		int weatherlocalny = weatherLocalVO.getLocalny();
		String weatherlocalname = weatherLocalVO.getLocalname();
		int weatherparent = weatherLocalVO.getLocalparent(); 
		
		try {
			postList = weatherAPIservice.weatherData(weatherlocalnx, weatherlocalny);
		
			postMap = weatherAPIservice.sortNowData(postList);
			
			if(weatherparent != 0) { // 부모가 있을 경우
				
				WeatherLocalVO parentLocalVO = new WeatherLocalVO();
				
				parentLocalVO = weatherService.selectlocalInfoBylocaluid(weatherparent);
				String parentName = parentLocalVO.getLocalname();
				postMap.put("parentName", parentName);
				
			} else if (weatherparent == 0) {
				postMap.put("parentName", null);
			}
			
		} catch (Exception e) {
			System.out.println("getAPI error" + e.getMessage());
			e.printStackTrace();
		}
		
		postMap.put("localname", weatherlocalname);
		
		/*
		 *  POP
		 * 	HUM
		 *  VEC
		 *  WSD
		 */
		
		return postMap;
	}
	
}
