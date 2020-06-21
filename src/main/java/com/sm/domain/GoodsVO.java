package com.sm.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsVO {
	private int gbsNum;
	private String gbsName;
	private String cateCode;
	private int gbsPrice;
	private int gbsSTock;
	private String gbsDes;
	private String gbsImg;
	private Date gbsDate;
}
