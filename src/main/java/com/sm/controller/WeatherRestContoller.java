package com.sm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sm.service.WeatherAPIservice;
import com.sm.service.WeatherService;

@RestController
@RequestMapping("/weather")
public class WeatherRestContoller {
	
	@Autowired
	WeatherAPIservice weatherAPIservice;
	
	@Autowired
	WeatherService weatherService;

	
	
}
