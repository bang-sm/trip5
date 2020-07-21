package com.sm.domain;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TravelInfoVO {
	
	
	private int tsiid;
	private int tsid;
	private int tsidDay;
	private String tsititle;
	private String tsicomment;
	
	private List<TravelInfoVO> list;

}
