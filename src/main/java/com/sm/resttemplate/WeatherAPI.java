
package com.sm.resttemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sm.service.WeatherAPIservice;

@Controller
public class WeatherAPI {

	@Autowired
	WeatherAPIservice weatherAPIservice;
	
	@GetMapping("/weather")
	public String callWeatherAPI() throws Exception {
		System.out.println(weatherAPIservice.weatherData());
		
		return "/weather/weather";
	}
}
