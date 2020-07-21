package com.sm.domain;

import java.sql.Date;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlacelistVo {
	
	@Min(0)
	private int placeid;
	
	
	@NotBlank(message = "필수 정보입니다.")
	private String placename;
	
	@NotBlank(message = "필수 정보입니다.")
	private String placejuso;
	
	@NotBlank(message = "필수 정보입니다.")
	private String bloglink;
	
	@NotBlank(message = "필수 정보입니다.")
	private String iconname;
	
	@Range(min = 0, max=3, message = "카테고리를 다시 선택해주세요")
	private int placecategory;
	
	@Min(0)
	private int uuid;
	

	private Date placeregdate;
	private int placecheck;
	private int bookmark;
	
	//donut-chart 음식 개수
	private int foodcount;
	//donut-chart 카페 개수
	private int cafecount;
	//donut-chart 관광지 개수
	private int placecount;
	
	// bar-chart 계획한 음식 개수
	private int planfood;
	// bar-chart 계획한 카페 개수
	private int plancafe;
	// bar-chart 계획한 관광지 개수
	private int planplace;
	
	// bar-chart 방문한 음식 개수
	private int visitfood;
	// bar-chart 방문한 카페 개수
	private int visitcafe;
	// bar-chart 방문한 관광지 개수
	private int visitplace;
	
	//linechart 몇월부터
	private String firstdate;
	//linechart 몇월 까지
	private String enddate;
	
	private String foodtitle;
	private String cafetitle;
	private String placetitle;
	
	
	
	
	
}
