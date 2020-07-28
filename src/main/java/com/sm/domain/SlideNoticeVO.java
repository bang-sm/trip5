package com.sm.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SlideNoticeVO {
	private int snId;
	private String snContent;
	private String snEnrollment;
	
	public SlideNoticeVO(int snId, String snContent) {
		super();
		this.snId = snId;
		this.snContent = snContent;
	}
	
}
