package com.sm.domain;

import lombok.Data;



public class WeatherLocalVO {
	
	private int localuid;
	private String localname;
	private int localdepth;
	private int localparent;
	private int localnx;
	private int localny;
	
	public int getLocaluid() {
		return localuid;
	}
	public void setLocaluid(int localuid) {
		this.localuid = localuid;
	}
	public String getLocalname() {
		return localname;
	}
	public void setLocalname(String localname) {
		this.localname = localname;
	}
	public int getLocaldepth() {
		return localdepth;
	}
	public void setLocaldepth(int localdepth) {
		this.localdepth = localdepth;
	}
	public int getLocalparent() {
		return localparent;
	}
	public void setLocalparent(int localparent) {
		this.localparent = localparent;
	}
	public int getLocalnx() {
		return localnx;
	}
	public void setLocalnx(int localnx) {
		this.localnx = localnx;
	}
	public int getLocalny() {
		return localny;
	}
	public void setLocalny(int localny) {
		this.localny = localny;
	}

	
	
}
