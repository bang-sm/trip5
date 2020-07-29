package com.sm.domain;

import javax.validation.constraints.NotBlank;

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
	private String msgregdate;
	private String msgcontent;
	private String msgsubject;
	private int fromid;
	private int sendid;
	private String membernick;
	private String othernick;
	private int msgdel;
	private int msgunwrite;
}
