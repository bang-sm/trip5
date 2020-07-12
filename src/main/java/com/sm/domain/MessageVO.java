package com.sm.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageVO {

	private int msgid;
	private Date msgregdate;
	private String msgcontent;
	private int fromid;
	private int sendid;
	
}
