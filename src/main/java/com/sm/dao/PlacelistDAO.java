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
import com.sm.domain.travelmembernickVO;

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
		List<Integer> flm =new ArrayList<Integer>();
		flm = sql.selectList("mappers.placelistMapper.followingmecheck",uuid);
	
		List<Integer> fly =new ArrayList<Integer>();
		fly = sql.selectList("mappers.placelistMapper.followingyoucheck",uuid);
		
		List<Integer> bm =new ArrayList<Integer>();
		bm = sql.selectList("mappers.placelistMapper.mypagebm",uuid);
		
		List<TravelVO> like = new ArrayList<TravelVO>();
		like=sql.selectList("mappers.placelistMapper.mypagelike",uuid);
		
		List<MypageVO> mypageVO = new ArrayList<MypageVO>();
		mypageVO = sql.selectList("mappers.placelistMapper.blacklistuuid",uuid);
		
		mymap.put("likecount", like.size());
		mymap.put("bookmarkcount", bm.size());
		mymap.put("followingcount", fly.size());
		mymap.put("followercount", flm.size());
		mymap.put("blacklistcount", mypageVO.size());
		System.out.println("mypage : "+ mypageVO.size());
		
		return  mymap;
	}
	
	public HashMap<String, Object> following(int uuid){
		HashMap<String, Object> mymap = new HashMap<>();
		List<Integer> fly =new ArrayList<Integer>();
		fly = sql.selectList("mappers.placelistMapper.followingyoucheck",uuid);
		List<MypageVO> following = new ArrayList<MypageVO>();
		for(int i =0;i<fly.size();i++) {
			try {
				MypageVO mypageVO = sql.selectOne("mappers.placelistMapper.following",fly.get(i));
				following.add(mypageVO);
			}catch(IndexOutOfBoundsException e) {
				System.out.println("꺼억");
			}
		}
		mymap.put("following", following);
		return mymap;
	}
	
	public HashMap<String, Object> follower(int uuid){
		HashMap<String, Object> mymap = new HashMap<>();
		HashMap<String, Object> myst = new HashMap<>();
		myst.put("uuid", uuid);
		List<MypageVO> follower = new ArrayList<MypageVO>();
		List<Integer> fly =new ArrayList<Integer>();
		fly = sql.selectList("mappers.placelistMapper.followermecheck",uuid);
		for(int i =0;i<fly.size();i++) {
			try {
				MypageVO mypageVO = sql.selectOne("mappers.placelistMapper.following",fly.get(i));
				myst.put("follow_uuid", fly.get(i));
				mypageVO.setStatus(sql.selectOne("mappers.placelistMapper.status",myst));
				myst.remove("follow_uuid");
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
		List<travelmembernickVO> bookmark = new ArrayList<travelmembernickVO>();
		for(int i=0;i<bm.size();i++) {
			try {
				travelmembernickVO travelmembernickVO = sql.selectOne("mappers.placelistMapper.mypagetravelstory",bm.get(i));
				bookmark.add(travelmembernickVO);
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
	
	public void followok(int uuid, int followuuid) {
		HashMap<String, Object> mymap = new HashMap<>();
		mymap.put("follow_uuid", followuuid);
		mymap.put("uuid", uuid);
		
		
		sql.insert("mappers.placelistMapper.followok",mymap);
	}
	
	public void followdel(int uuid, int followuuid) {
		HashMap<String, Object> mymap = new HashMap<>();
		mymap.put("follow_uuid", followuuid);
		mymap.put("uuid", uuid);
		
		
		sql.insert("mappers.placelistMapper.followdel",mymap);
	}
	
	
	public List<MypageVO> blacklistuuid(int uuid) {
		return sql.selectList("mappers.placelistMapper.blacklistuuid",uuid);
	}
	public MypageVO blacklist(int uuid){
		return sql.selectOne("mappers.placelistMapper.following",uuid);
	}
	
	public List<MypageVO> reply(int uuid){
		return sql.selectList("mappers.placelistMapper.reply",uuid);
	}
	
	
	
	
	
	
	public void blacklistdel(int uuid, int black_uuid) {
		HashMap<String, Object> mymap = new HashMap<>();
		mymap.put("uuid", uuid);
		mymap.put("black_uuid", black_uuid);
		sql.delete("mappers.placelistMapper.blacklistdel",mymap);
	}
	public int mypageline(int first,int end, int uuid) {
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
		return sql.selectOne("mappers.placelistMapper.mypageline",fmap);
	}
	
	
	public List<Integer> followingcount(int uuid) {
		return sql.selectList("mappers.placelistMapper.followermecheck",uuid);
	}
	
	public List<Integer>  followingyoucount(int uuid){
		return sql.selectList("mappers.placelistMapper.followingyoucheck",uuid);
	}
	
}
