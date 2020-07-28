package com.sm.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PopUpNoticeVO {
	private int pnId;
	private String pnHeader;
	private String pnContent;
	private String pnEnrollment;
	
	public PopUpNoticeVO(int pnId, String pnHeader, String pnContent) {
		super();
		this.pnId = pnId;
		this.pnHeader = pnHeader;
		this.pnContent = pnContent;
	}

}
