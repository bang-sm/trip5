package com.sm.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sm.domain.MypageVO;
import com.sm.domain.PlacelistVo;
import com.sm.domain.TravelVO;
import com.sm.domain.TravelViewVO;

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
	
	public HashMap<String, Object> mypage(int uuid){
		HashMap<String, Object> mymap = new HashMap<>();
		List<MypageVO> followme =new ArrayList<MypageVO>();
		followme = sql.selectList("mappers.placelistMapper.followme",uuid);
		int followmecount = followme.size();
		
		List<MypageVO> followyou =new ArrayList<MypageVO>();
		followyou = sql.selectList("mappers.placelistMapper.followyou",uuid);
		int followyoucount = followyou.size();
		
		List<MypageVO> follower =new ArrayList<MypageVO>();
		follower= sql.selectList("mappers.placelistMapper.follower",uuid);
		int followercount = follower.size();
		
		List<MypageVO> bm =new ArrayList<MypageVO>();
		bm= sql.selectList("mappers.placelistMapper.bmcount",uuid);
		int bmcount = bm.size();
		
		//수락요청 보낸거
		String [] followmename = new String[followmecount];
		
		//수락요청 온거
		String [] followyouname = new String[followyoucount];
		for(int i=0;i<followme.size();i++) {
			followmename[i]=sql.selectOne("mappers.placelistMapper.membername",followme.get(i).getFollowUuid());
		}
		
		for(int j=0;j<followyou.size();j++) {
			followyouname[j]=sql.selectOne("mappers.placelistMapper.membername",followyou.get(j).getUuid());
		}
		
		for(String t : followmename) {
			System.out.println("수락요청 보냄 : "+t);
		}
		
		for(String t : followyouname) {
			System.out.println("수락요청 받은 : "+ t);
		}
		
		
		
		mymap.put("followme", followme);
		mymap.put("followmecount", followmecount);
		mymap.put("followyou", followyou);
		mymap.put("followyoucount", followyoucount);
		mymap.put("follower", follower);
		mymap.put("followercount", followercount);
		mymap.put("bm", bm);
		mymap.put("bmcount", bmcount);
		mymap.put("followmename", followmename);
		mymap.put("followyouname", followyouname);
		
		return  mymap;
	}
	
	public HashMap<String, Object> following(int uuid){
		HashMap<String, Object> mymap = new HashMap<>();
		List<Integer> flm =new ArrayList<Integer>();
		flm = sql.selectList("mappers.placelistMapper.followingyoucheck",uuid);
		List<MypageVO> followingme = new ArrayList<MypageVO>();
		for(int i =0;i<flm.size();i++) {
			try {
				MypageVO mypageVO = sql.selectOne("mappers.placelistMapper.following",flm.get(i));
				followingme.add(mypageVO);
			}catch(IndexOutOfBoundsException e) {
				System.out.println("꺼억");
			}
		}
		
		List<Integer> fly =new ArrayList<Integer>();
		fly = sql.selectList("mappers.placelistMapper.followingmecheck",uuid);
		List<MypageVO> followingyou = new ArrayList<MypageVO>();
		for(int i =0;i<flm.size();i++) {
			try {
				MypageVO mypageVO = sql.selectOne("mappers.placelistMapper.following",fly.get(i));
				followingyou.add(mypageVO);
			}catch(IndexOutOfBoundsException e) {
				System.out.println("꺼억");
			}
		}
		mymap.put("followingme", followingme);
		mymap.put("followingyou", followingyou);
		return mymap;
	}
	
	public HashMap<String, Object> follower(int uuid){
		HashMap<String, Object> mymap = new HashMap<>();
		List<Integer> flm =new ArrayList<Integer>();
		flm = sql.selectList("mappers.placelistMapper.followeryoucheck",uuid);
		List<MypageVO> follower = new ArrayList<MypageVO>();
		for(int i =0;i<flm.size();i++) {
			try {
				MypageVO mypageVO = sql.selectOne("mappers.placelistMapper.following",flm.get(i));
				follower.add(mypageVO);
			}catch(IndexOutOfBoundsException e) {
				System.out.println("꺼억");
			}
		}
		
		List<Integer> fly =new ArrayList<Integer>();
		fly = sql.selectList("mappers.placelistMapper.followermecheck",uuid);
		for(int i =0;i<flm.size();i++) {
			try {
				MypageVO mypageVO = sql.selectOne("mappers.placelistMapper.following",fly.get(i));
				follower.add(mypageVO);
			}catch(IndexOutOfBoundsException e) {
				System.out.println("꺼억");
			}
		}
		mymap.put("follower", follower);
		return mymap;
	}
	
	public HashMap<String, Object> mypagebookmark(int uuid){
		HashMap<String, Object> mymap = new HashMap<>();
		//먼저 uuid 로 게시글 목록가지고 오기
		
		List<Integer> bm =new ArrayList<Integer>();
		bm = sql.selectList("mappers.placelistMapper.mypagebm",uuid);
		List<TravelVO> bookmark = new ArrayList<TravelVO>();
		for(int i=0;i<bm.size();i++) {
			try {
				TravelVO travelVO = sql.selectOne("mappers.placelistMapper.mypagetravelstory",bm.get(i));
				bookmark.add(travelVO);
			}catch(IndexOutOfBoundsException e) {
				System.out.println("꺼억");
			}
		}
		mymap.put("bookmark",bookmark);
		return mymap;
	}
	
	
	public HashMap<String, Object> mypagelike(int uuid){
		HashMap<String, Object> mymap = new HashMap<>();
		//먼저 uuid 로 게시글 목록가지고 오기
		List<TravelVO> like = new ArrayList<TravelVO>();
		like=sql.selectList("mappers.placelistMapper.mypagelike",uuid);
		int count=0;
		for(int i=0;i<like.size();i++) {
			count+=like.get(i).getTslike();
		}
		
		mymap.put("count", count);
		mymap.put("like", like);
		return mymap;
	}
	
	
	
}
