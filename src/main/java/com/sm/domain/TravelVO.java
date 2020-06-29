package com.sm.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelVO {
	
	private int tsid;
	private int uuid;
	private String tstitle;
	private int tslike;
	private Date tsregdate;
	private String hashtag;
	private String tsstartdate;
	private String tsenddate;
	private int tsfare;
	
	///////////////////////
	
	private String tsirootname;
	private int tsirootorder;
	
	///////////////////////
	
	private int tsiid;
	private int tsidDay;
	private String tsiname;
	private int tsicategory;
	private String tsicomment;
	
	////////////////////////
	
	private int tsreplyid;
	private String tsreplycomment;
}
