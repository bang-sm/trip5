package com.sm.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MypageVO {
	
	private int uuid;
	private int followUuid;
	private String status;
	private int ts_id;
	private int tsid;
	private String membernick;
	private String pName;
	private int blackUuid;
	private String tsReplyComment;
	private String  replyRegdate;
	private String tstitle;
	private String tsstartdate;
	private String tsenddate;
	private String tsregdate;
	private String tempsave;
	
	
}
