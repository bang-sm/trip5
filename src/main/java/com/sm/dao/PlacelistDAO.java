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
	
	public List<PlacelistVo> show(int uuid) {
		return sql.selectList("mappers.placelistMapper.placeshow",uuid);
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
	
	public List<PlacelistVo> goplace(int placecheck,int uuid){
		HashMap<String, Integer> gmap = new HashMap<>();
		gmap.put("placecheck", placecheck);
		gmap.put("uuid", uuid);
		
		return sql.selectList("mappers.placelistMapper.goplace",gmap);
	}
	
	public int barchart(int placecategory,int placecheck,int uuid){
		HashMap<String, Integer> bmap = new HashMap<>();
		bmap.put("placecategory", placecategory);
		bmap.put("placecheck", placecheck);
		bmap.put("uuid", uuid);
		
		
		return sql.selectOne("mappers.placelistMapper.barchart",bmap);
	}
	
	public void delete(int placeid) {
		sql.delete("mappers.placelistMapper.delete",placeid);
	}
	
	public int chartcount(int category,int uuid) {
		System.out.println("cate : "+ category);
		HashMap<String, Integer> fmap = new HashMap<>();
		fmap.put("placecategory", category);
		fmap.put("uuid", uuid);
		return sql.selectOne("mappers.placelistMapper.chartcount",fmap);
	}
	
	public int linechart(int first,int end, int uuid) {
		String enddate;
		String firstdate = "2020-0"+Integer.toString(first)+"-01";
		if(end == 13) {
			enddate = "2020-0"+Integer.toString(end-1)+"-31";
		}else {
			enddate = "2020-0"+Integer.toString(end)+"-01";
		}
		System.out.println(firstdate);
		System.out.println(enddate);
		HashMap<String, Object> fmap = new HashMap<>();
		fmap.put("firstdate", firstdate);
		fmap.put("enddate", enddate);
		fmap.put("uuid", uuid);
		return sql.selectOne("mappers.placelistMapper.chartline",fmap);
	}
	
	
	public int area(int area,int uuid) {
		String [] areaname = {"서울%","경기%","강원%","인천%","충북%","충남%","세종%","대전%","광주%","전북%","전남%","경북%","경남%","대구%","울산%","부산%","제주%"};
		
		HashMap<String, Object> fmap = new HashMap<>();
		fmap.put("areaname", areaname[area]);
		fmap.put("uuid", uuid);
		return sql.selectOne("mappers.placelistMapper.area",fmap);
	}
}
