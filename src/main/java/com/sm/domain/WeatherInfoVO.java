package com.sm.domain;

import lombok.Data;

//@Data
public class WeatherInfoVO {

	private String fcstTime;
	private String fcstDate;
	private String category;
	private double fcstValue;
	
	
	public WeatherInfoVO() {
		super();
	}
	
	public String getFcstTime() {
		return fcstTime;
	}
	public void setFcstTime(String fcstTime) {
		this.fcstTime = fcstTime;
	}
	public String getFcstDate() {
		return fcstDate;
	}
	public void setFcstDate(String fcstDate) {
		this.fcstDate = fcstDate;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public double getFcstValue() {
		return fcstValue;
	}
	public void setFcstValue(double fcstValue) {
		this.fcstValue = fcstValue;
	}
	
//	private int SKY;
//	private int TEMP;
//	private int HUM;
//	private int POP;
//	
//	private int VEC;
//	private int WSD;

	
	
}
