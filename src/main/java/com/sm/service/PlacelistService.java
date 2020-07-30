package com.sm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;

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
	
	public int followingcount(int uuid) throws Exception{
		
		List<Integer> fly =new ArrayList<Integer>();
		fly = dao.followingcount(uuid);
		
		return fly.size();
	}
	
	public int followingyoucount(int uuid) throws Exception{
		List<Integer> flm = new ArrayList<Integer>();
		flm = dao.followingyoucount(uuid);
		
		return flm.size();
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
	
	public HashMap<String, Object> blacklist(int uuid) throws Exception{
		System.out.println("service uuid : "+uuid);
		HashMap<String, Object> mymap = new HashMap<>();
		List<MypageVO> mypageVO = new ArrayList<MypageVO>();
		mypageVO = dao.blacklistuuid(uuid);
		System.out.println("크기 : "+mypageVO.size());
		List<MypageVO> blacklist = new ArrayList<MypageVO>();
		for(int i=0;i<mypageVO.size();i++) {
			System.out.println("black uuid : "+ mypageVO.get(i).getBlackUuid());
			MypageVO my =dao.blacklist(mypageVO.get(i).getBlackUuid());
			System.out.println("my : "+my);
			blacklist.add(my);
		}
		mymap.put("blacklist", blacklist);
		return mymap;
		
	}
	
	public HashMap<String, Object> reply(int uuid) throws Exception{
		System.out.println("service uuid : "+uuid);
		HashMap<String, Object> mymap = new HashMap<>();
		List<MypageVO> mypageVO = new ArrayList<MypageVO>();
		mypageVO = dao.reply(uuid);
		mymap.put("reply",mypageVO);
		return mymap;
		
	}
	
	public void blacklistdel(int uuid,int black_uuid ) throws Exception{
		dao.blacklistdel(uuid,black_uuid);
	}
	
	public int blacklistcount(int uuid) throws Exception{
		List<MypageVO> mypageVO = new ArrayList<MypageVO>();
		mypageVO = dao.blacklistuuid(uuid);
		
		return mypageVO.size();
	}
	
	public int mypageline(int first,int end, int uuid) throws Exception{
		return dao.mypageline(first,end,uuid);
	}
	
	
}
