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
}
