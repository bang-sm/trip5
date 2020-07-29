package com.sm.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.dao.PlacelistDAO;
import com.sm.domain.MypageVO;
import com.sm.domain.PlacelistVo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PlacelistService{

	@Autowired
	PlacelistDAO dao;
	
	public List<PlacelistVo> show(int uuid) throws Exception{
		return dao.show(uuid);
	}
	
	public void select(PlacelistVo placelistVo) throws Exception{
		dao.select(placelistVo);
	}
	
	public void bookmark(int bookmark,int placeid) throws Exception{
		dao.bookmark(bookmark,placeid);
	}
	
	public void checkbox(int placecheck,int placeid) throws Exception{
		dao.checkbox(placecheck,placeid);
	}
	
	public List<PlacelistVo> goplace(int placecheck,int uuid) throws Exception{
		return dao.goplace(placecheck,uuid);
	}
	
	public int barchart(int placecategory,int placecheck,int uuid) throws Exception{
		return dao.barchart(placecategory,placecheck,uuid);
	}
	
	public void delete(int placeid) throws Exception{
		dao.delete(placeid);
	}
	public int  chartcount(int category,int uuid) throws Exception{
		return dao.chartcount(category,uuid);
	}
	
	public int linechart(int first,int end, int uuid) throws Exception{
		return dao.linechart(first,end,uuid);
	}
	
	public int area(int area, int uuid) throws Exception{
		return dao.area(area,uuid);
	}
	
	public HashMap<String, Object> mypage(int uuid) throws Exception{
		return dao.mypage(uuid);
	}
	
	public HashMap<String, Object> following(int uuid) throws Exception{
		return dao.following(uuid);
	}
	
	public HashMap<String, Object> follower(int uuid) throws Exception{
		return dao.follower(uuid);
	}
	
	public HashMap<String, Object> mypagebookmark(int uuid) throws Exception{
		return dao.mypagebookmark(uuid);
	}
	
	public HashMap<String, Object> mypagelike(int uuid) throws Exception{
		return dao.mypagelike(uuid);
	}
	
	public void followok(int uuid, int followuuid) throws Exception{
		dao.followok(uuid,followuuid);
	}
	
	public void followdel(int uuid, int followuuid) throws Exception{
		dao.followdel(uuid,followuuid);
	}
	
	
}
