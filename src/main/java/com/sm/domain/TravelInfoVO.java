package com.sm.domain;

import lombok.Data;

@Data
public class TravelInfoVO {
	
	
	private int tsiid;
	private int[] tsidDay;
	private String[] tsititle;
	private int tsicategory;
	private String[] tsicomment;
	private String[] tsirootname;
	private int[] tsirootorder;

}
