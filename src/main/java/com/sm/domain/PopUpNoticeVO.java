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
	private String pnTop;
	private String pnLeft;
	private String pnWidth;
	private String pnHeight;
	private String pnDate;
	
	public PopUpNoticeVO(int pnId, String pnHeader, String pnContent, String pnTop,
			String pnLeft, String pnWidth,
			String pnHeight, String pnDate) {
		super();
		this.pnId = pnId;
		this.pnHeader = pnHeader;
		this.pnContent = pnContent;
		this.pnTop = pnTop;
		this.pnLeft = pnLeft;
		this.pnWidth = pnWidth;
		this.pnHeight = pnHeight;
		this.pnDate = pnDate;
	}

}
