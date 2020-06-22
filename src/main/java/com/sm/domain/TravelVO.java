package com.sm.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelVO {
	
	private int ts_id;
	private int uuid;
	private String ts_title;
	private int ts_like;
	private Date ts_regdate;
	private String hashtag;
	
	///////////////////////
	
	private int tsi_id;
	private int tsi_dDay;
	private String tsi_address;
	private String tsi_name;
	private String tsi_tel;
	private int tsi_category;
	private int order;
	private int tsi_fare;
	
	////////////////////////
	
	private int ts_reply_id;
	private String ts_reply_comment;
}
