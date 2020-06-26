package com.sm.domain;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlacelistVo {
	
	private int place_id;
	private String place_name;
	private Date place_regdate;
	private String place_check;
	private String place_juso;
	private int place_order;
	private String place_comment;
	private int uuid;
	private int place_category;
	private String place_main_food;
	private String bloglink;

}
