package com.sm.domain;

import lombok.Data;

@Data
public class TravelVO {
	
	private int tsid; //여행 id값
	private int uuid; //유저 id값
	private String tstitle; //제목
	private int tslike;  //좋아요수
	private String tsregdate; //등록일
	private String tsstartdate; //여행시작일
	private String tsenddate; //여행마지막일
	private String tshashtagone; //여행마지막일
	private String tempSave; //임시저장 여부용
	private int datediff;  //날짜차이
	private String photoId; //사진id
	
	private int bookmark;
	private String photoMain;
	
	private int totalLike; //내가 받은 총 좋아요 개수
	private int followCount; //나를 팔로우 한 사람 수
	private int travelCount; //나의 일지 개수
}
