package com.sm.domain;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlacelistVo {
	
	private int placeid;
	private String placename;
	private Date placeregdate;
	private int placecheck;
	private String placejuso;
	private int uuid;
	private int placecategory;
	private String placemainfood;
	private String bloglink;
	private String iconname;
	private int bookmark;

}
