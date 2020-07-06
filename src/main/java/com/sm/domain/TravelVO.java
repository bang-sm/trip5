package com.sm.domain;

import java.util.Date;

import lombok.Data;

@Data
public class TravelVO {
	
	private int tsid;
	private int uuid;
	private String tstitle;
	private int tslike;
	private Date tsregdate;
	private String tshashtagone;
	private String tshashtagtwo;
	private String tshashtagthree;
	private String tsstartdate;
	private String tsenddate;
	private int tsfare;
	
	
	private int tsreplyid;
	private String tsreplycomment;
}
