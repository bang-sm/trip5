package com.sm.domain;

import java.util.List;

import lombok.Data;

@Data
public class TravelInfoRootVO {
	private int tsid;
	private String tsirootname;
	private int tsirootorder;
	private String tsirootvehicle;
	
	private List<TravelInfoRootVO> rootlist;
}
