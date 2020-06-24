package com.sm.resttemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherAPI {
	
	private static final String WEATHER_API_KEY
		= "2XvzikuY6DfCNryEkAgrB8IeC8g6W%2BIuc4zqEuKB24Z2Pd3%2B2KDw1JuiUj%2FByKxcZM8vsFFEvYkOfegKRvpADw%3D%3D";

	@GetMapping("/GetWeatherData")
	public String callAPI() {
		
		StringBuffer result = new StringBuffer();
		
		try {
			
			String urlStr = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?"
					+ "serviceKey" + WEATHER_API_KEY
					+ "pageNo" + "1" 
					+ "numOfRows" + "9"
					+ "dataType" + "JSON"
					+ "base_date" + "20200622"
					+ "base_time" + "0500"
					+ "nx" + "60"
					+ "ny" + "127"
					;
			
			URL url = new URL(urlStr);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
			
			String returnLine;
			result.append("<xmp>");
			while((returnLine = br.readLine()) != null) {
				result.append(returnLine + "\n");
			}
			
			urlConnection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result + "</xmp>";
	}
	
}
