package com.sm.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sm.Handler.MyinfoInterceptor;


public class MyinfoConfig implements WebMvcConfigurer{

	@Autowired
	private MyinfoInterceptor myinfoInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(myinfoInterceptor)
			.addPathPatterns("/travel/travel_blog/**");
	}
}
