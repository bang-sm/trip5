package com.sm.domain;

import lombok.Data;

@Data
public class TravelReplyVO {
	private int tsReplyId;
	private int uuid;
	private String tsReplyComment;
	private String replyRegdate;
	private int tsId;
	private int count;
	private String memberName;
}
