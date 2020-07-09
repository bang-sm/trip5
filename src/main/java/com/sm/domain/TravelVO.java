package com.sm.domain;

import java.util.Date;

import lombok.Data;

@Data
public class TravelVO {
	
	private int tsid; //여행 id값
	private int uuid; //유저 id값
	private String tstitle; //제목
	private int tslike;  //좋아요수
	private Date tsregdate; //등록일
	private String tshashtagone; //해쉬태그
	private String tsstartdate; //여행시작일
	private String tsenddate; //여행마지막일
	private String tempSave; //임시저장 여부용
	private String datediff;
	
	
	private int tsreplyid;
	private String tsreplycomment;
}
