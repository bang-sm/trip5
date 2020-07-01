package com.sm.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sm.domain.PlacelistVo;

@Repository
public class PlacelistDAO {

	@Autowired
	SqlSession sql;
	
	public List<PlacelistVo> show() {
		return sql.selectList("mappers.placelistMapper.placeshow");
	}
	
	public void select(PlacelistVo placelistVo) {
		sql.insert("mappers.placelistMapper.placeinsert",placelistVo);
	}
	
	public void bookmark(int bookmark,int placeid) {
		HashMap<String, Integer> hmap = new HashMap<>();
		hmap.put("bookmark", bookmark);
		hmap.put("placeid", placeid);
		sql.update("mappers.placelistMapper.bookmark",hmap);
	}
	
	public void checkbox(int placecheck,int placeid) {
		HashMap<String, Integer> cmap = new HashMap<>();
		cmap.put("placecheck", placecheck);
		cmap.put("placeid", placeid);
		sql.update("mappers.placelistMapper.checkbox",cmap);
	}
	
	public List<PlacelistVo> goplace(int placecheck){
		return sql.selectList("mappers.placelistMapper.goplace",placecheck);
	}
	
	public List<PlacelistVo> buttoncategory(int placecategory,int placecheck){
		HashMap<String, Integer> bmap = new HashMap<>();
		bmap.put("placecategory", placecategory);
		bmap.put("placecheck", placecheck);
		
		return sql.selectList("mappers.placelistMapper.buttoncategory",bmap);
	}
}
