package com.sm.controller;

import java.util.List;

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
//	public String callWeatherAPI() throws Exception {
	public String callWeatherAPI(Model model) throws Exception {
//		
//		int localnx = Integer.parseInt(nx);
//		int localny = Integer.parseInt(ny);
//		
//		System.out.println(weatherAPIservice.weatherData(60, 126));
		model.addAttribute("list",weatherService.selectAllweather());
		System.out.println("컨트롤러 GET 들어오나?");
		return "/weather/weather";
	}
	
//	@ResponseBody
//	@PostMapping(value = "/weather")
////	public JSONObject getLowLocal(@RequestParam int localdepth, @RequestParam int localparent, Model model) throws Exception {
//	public List<WeatherLocalVO> getLowLocal(@ModelAttribute WeatherLocalVO vo, Model model) throws Exception {
//		System.out.println("컨트롤러 POST 들어오나?");
//		
//		return weatherService.weatherLocalName(vo);
////		return null;
//	}
		
}
