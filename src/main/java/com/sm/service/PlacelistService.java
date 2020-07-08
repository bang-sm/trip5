package com.sm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.dao.PlacelistDAO;
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
	
	public List<PlacelistVo> buttoncategory(int placecategory,int placecheck,int uuid) throws Exception{
		return dao.buttoncategory(placecategory,placecheck,uuid);
	}
	
	public void delete(int placeid) throws Exception{
		dao.delete(placeid);
	}
	
	
	
}
