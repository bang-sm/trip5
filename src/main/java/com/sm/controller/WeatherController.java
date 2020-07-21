package com.sm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
//		System.out.println(weatherAPIservice.weatherData(60, 126));
		
		List<WeatherLocalVO> list = new ArrayList<WeatherLocalVO>();
		
		list = weatherService.selectParentweather();
		
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i).getLocalname());
//		}
		
//		System.out.println(weatherService.selectParentweather().toString());
		model.addAttribute("list", list);
		System.out.println("컨트롤러 GET 들어오나?");
		return "weather/weather";
	}
	
	@ResponseBody
	@PostMapping("/depth2")
	public List<WeatherLocalVO> getLowLocal(int weatherparentuid) throws Exception {
		System.out.println("컨트롤러 POST 들어오나?");
		System.out.println(weatherparentuid);
		System.out.println(weatherService.weatherLocalName(weatherparentuid));
		return weatherService.weatherLocalName(weatherparentuid);
	}
	
	@ResponseBody
	@PostMapping("/api")
	public List<WeatherInfoVO> getWeatherAPI(int weatherlocalnx, int weatherlocalny, Model model){
		
		List<WeatherInfoVO> list = new ArrayList<WeatherInfoVO>();
		Map<String, String> nowData = new HashMap<String, String>();
		
		System.out.println("api 가져오나? (POST)");
		System.out.println("x값:" + weatherlocalnx + " y값:" + weatherlocalny );
		
		try {
			
			list = weatherAPIservice.weatherData(weatherlocalnx, weatherlocalnx);
			nowData = weatherAPIservice.sortNowData(list);
			
			model.addAttribute("nowData", nowData);
			
		} catch (Exception e) {
			System.out.println("api 에러(오류)" + e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println(list.get(0).getCategory());
		return list;
	}
		
}
