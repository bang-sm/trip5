package com.sm.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.dao.PlacelistDAO;
import com.sm.domain.MypageVO;
import com.sm.domain.PlacelistVo;
import com.sm.domain.TravelVO;
import com.sm.domain.TravelViewVO;
import com.sm.domain.travelmembernickVO;

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
		HashMap<String, Integer> hmap = new HashMap<>();
		hmap.put("bookmark", bookmark);
		hmap.put("placeid", placeid);
		dao.bookmark(hmap);
	}
	
	public void checkbox(int placecheck,int placeid) throws Exception{
		HashMap<String, Integer> cmap = new HashMap<>();
		cmap.put("placecheck", placecheck);
		cmap.put("placeid", placeid);
		dao.checkbox(cmap);
	}
	
	public List<PlacelistVo> goplace(int placecheck,int uuid) throws Exception{
		HashMap<String, Integer> gmap = new HashMap<>();
		gmap.put("placecheck", placecheck);
		gmap.put("uuid", uuid);
		return dao.goplace(gmap);
	}
	
	public int barchart(int placecategory,int placecheck,int uuid) throws Exception{
		HashMap<String, Integer> bmap = new HashMap<>();
		bmap.put("placecategory", placecategory);
		bmap.put("placecheck", placecheck);
		bmap.put("uuid", uuid);
		return dao.barchart(bmap);
	}
	
	public void delete(int placeid) throws Exception{
		dao.delete(placeid);
	}
	public int  chartcount(int category,int uuid) throws Exception{
		HashMap<String, Integer> fmap = new HashMap<>();
		fmap.put("placecategory", category);
		fmap.put("uuid", uuid);
		return dao.chartcount(fmap);
	}
	
	public int linechart(int first,int end, int uuid) throws Exception{
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
		return dao.linechart(fmap);
	}
	
	public int area(int area, int uuid) throws Exception{
		String [] areaname = {"서울%","경기%","강원%","인천%","충북%","충남%","세종%","대전%","광주%","전북%","전남%","경북%","경남%","대구%","울산%","부산%","제주%"};
		HashMap<String, Object> fmap = new HashMap<>();
		fmap.put("areaname", areaname[area]);
		fmap.put("uuid", uuid);
		return dao.area(fmap);
	}
	
	public HashMap<String, Object> mypage(int uuid) throws Exception{
		HashMap<String, Object> mymap = new HashMap<>();
		List<Integer> flm =new ArrayList<Integer>();
		flm =dao.followingmecheck(uuid);
		List<Integer> fly =new ArrayList<Integer>();
		fly= dao.followingyoucount(uuid);
		List<Integer> bm =new ArrayList<Integer>();
		bm =dao.mypagebm(uuid);
		List<TravelVO> like = new ArrayList<TravelVO>();
		like=dao.mypagelike(uuid);
		
		List<MypageVO> mypageVO = new ArrayList<MypageVO>();
		mypageVO= dao.blacklistuuid(uuid);
		mymap.put("likecount", like.size());
		mymap.put("bookmarkcount", bm.size());
		mymap.put("followingcount", fly.size());
		mymap.put("followercount", flm.size());
		mymap.put("blacklistcount", mypageVO.size());
		return  mymap;
	}
	
	public HashMap<String, Object> following(int uuid) throws Exception{
		HashMap<String, Object> mymap = new HashMap<>();
		List<Integer> fly =new ArrayList<Integer>();
		fly=dao.followingyoucount(uuid);
		List<MypageVO> following = new ArrayList<MypageVO>();
		for(int i =0;i<fly.size();i++) {
			try {
				MypageVO mypageVO = dao.blacklist(fly.get(i));
				following.add(mypageVO);
			}catch(IndexOutOfBoundsException e) {
				System.out.println("꺼억");
			}
		}
		mymap.put("following", following);
		return mymap;
	}
	
	public HashMap<String, Object> follower(int uuid) throws Exception{
		HashMap<String, Object> mymap = new HashMap<>();
		HashMap<String, Object> myst = new HashMap<>();
		myst.put("follow_uuid", uuid);
		List<MypageVO> follower = new ArrayList<MypageVO>();
		List<Integer> fly =new ArrayList<Integer>();
		fly= dao.followingcount(uuid);
		
		for(int i =0;i<fly.size();i++) {
			try {
				MypageVO mypageVO = dao.blacklist(fly.get(i));
				myst.put("uuid", fly.get(i));
				String status = dao.status(myst);
				System.out.println("status : "+ status);
				mypageVO.setStatus(status);
				follower.add(mypageVO);
				myst.remove("uuid");
			}catch(IndexOutOfBoundsException e) {
				System.out.println("꺼억");
			}
		}
		mymap.put("follower", follower);
		return mymap;
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
		HashMap<String, Object> mymap = new HashMap<>();
		//먼저 uuid 로 게시글 목록가지고 오기
		
		List<Integer> bm =new ArrayList<Integer>();
		bm =dao.mypagebm(uuid);
		List<travelmembernickVO> bookmark = new ArrayList<travelmembernickVO>();
		
		for(int i=0;i<bm.size();i++) {
			try {
				travelmembernickVO travelmembernickVO = dao.mypagetravelstory(bm.get(i));
				bookmark.add(travelmembernickVO);
			}catch(IndexOutOfBoundsException e) {
				System.out.println("꺼억");
			}
		}
		mymap.put("bookmark",bookmark);
		return mymap;
	}
	
	public HashMap<String, Object> mypagelike(int uuid) throws Exception{
		HashMap<String, Object> mymap = new HashMap<>();
		List<TravelVO> like = new ArrayList<TravelVO>();
		like= dao.mypagelike(uuid);
		int count=0;
		for(int i=0;i<like.size();i++) {
			count+=like.get(i).getTslike();
		}
		mymap.put("count", count);
		mymap.put("like", like);
		return mymap;
	}
	
	public void followok(int uuid, int followuuid) throws Exception{
		HashMap<String, Object> mymap = new HashMap<>();
		mymap.put("follow_uuid", followuuid);
		mymap.put("uuid", uuid);
		dao.followok(mymap);
	}
	
	public void followdel(int uuid, int followuuid) throws Exception{
		HashMap<String, Object> mymap = new HashMap<>();
		mymap.put("follow_uuid", followuuid);
		mymap.put("uuid", uuid);
		dao.followdel(mymap);
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
		for(int i=0;i<mypageVO.size();i++) {
			mypageVO.get(i).setReplyRegdate(mypageVO.get(i).getReplyRegdate().substring(0,10));
		}
		mymap.put("reply",mypageVO);
		return mymap;
		
	}
	
	public void blacklistdel(int uuid,int black_uuid ) throws Exception{
		HashMap<String, Object> mymap = new HashMap<>();
		mymap.put("uuid", uuid);
		mymap.put("black_uuid", black_uuid);
		dao.blacklistdel(mymap);
	}
	
	public int blacklistcount(int uuid) throws Exception{
		List<MypageVO> mypageVO = new ArrayList<MypageVO>();
		mypageVO = dao.blacklistuuid(uuid);
		
		return mypageVO.size();
	}
	
	public int mypageline(int first,int end, int uuid) throws Exception{
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
		return dao.mypageline(fmap);
	}
	
	
	
	// paging
    public Map<String, Object> boardList(int currentPage,int uuid){
        
        // 페이지에 보여줄 행의 개수 ROW_PER_PAGE = 10으로 고정
        final int ROW_PER_PAGE = 10; 
        
        // 페이지에 보여줄 첫번째 페이지 번호는 1로 초기화
        int startPageNum = 1;
        
        // 처음 보여줄 마지막 페이지 번호는 10
        int lastPageNum = ROW_PER_PAGE;
        
        // 현재 페이지가 ROW_PER_PAGE/2 보다 클 경우
        if(currentPage > (ROW_PER_PAGE/2)) {
            // 보여지는 페이지 첫번째 페이지 번호는 현재페이지 - ((마지막 페이지 번호/2) -1 )
            // ex 현재 페이지가 6이라면 첫번째 페이지번호는 2
            startPageNum = currentPage - ((lastPageNum/2)-1);
            // 보여지는 마지막 페이지 번호는 현재 페이지 번호 + 현재 페이지 번호 - 1 
            lastPageNum += (startPageNum-1);
        }
        
        // Map Data Type 객체 참조 변수 map 선언
        // HashMap() 생성자 메서드로 새로운 객체를 생성, 생성된 객체의 주소값을 객체 참조 변수에 할당
        Map<String, Integer> map = new HashMap<String, Integer>();
        // 한 페이지에 보여지는 첫번째 행은 (현재페이지 - 1) * 10
        int startRow = (currentPage - 1)*ROW_PER_PAGE;
        // 값을 map에 던져줌
        map.put("startRow", startRow);
        map.put("rowPerPage", ROW_PER_PAGE);
        map.put("uuid",uuid);
        // DB 행의 총 개수를 구하는 getBoardAllCount() 메서드를 호출하여 double Date Type의 boardCount 변수에 대입
        double boardCount = Double.valueOf(dao.getBoardAllCount(uuid));
        
        // 마지막 페이지번호를 구하기 위해 총 개수 / 페이지당 보여지는 행의 개수 -> 올림 처리 -> lastPage 변수에 대입
        int lastPage = (int)(Math.ceil(boardCount/ROW_PER_PAGE));
        
        // 현재 페이지가 (마지막 페이지-4) 보다 같거나 클 경우
        if(currentPage >= (lastPage-4)) {
            // 마지막 페이지 번호는 lastPage
            lastPageNum = lastPage;
        }
        
        // 구성한 값들을 Map Date Type의 resultMap 객체 참조 변수에 던져주고 return
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("list", dao.boardList(map));
        resultMap.put("currentPage", currentPage);
        resultMap.put("lastPage", lastPage);
        resultMap.put("startPageNum", startPageNum);
        resultMap.put("lastPageNum", lastPageNum);
        return resultMap;
    }
    
    
public void insertTravelView(TravelViewVO travelViewVO, int uuid) {
		
		List<TravelViewVO> tvList = new ArrayList<TravelViewVO>();
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		int updateCheck = 0;
		
		tvList = dao.selectTravelViewByUuid(uuid);
		
		for (int i = 0; i < tvList.size(); i++) {
			
			int dbTsid = tvList.get(i).getTsid();
			int paramTsid = travelViewVO.getTsid();
			
			if (dbTsid == paramTsid) {
				paramMap.put("uuid", uuid);
				paramMap.put("tsid", paramTsid);
				
				dao.updateViewDateBytsid(paramMap);
				updateCheck = 1;
			}
		}
		if (updateCheck == 0) {
			
			dao.insertTravelView(travelViewVO);  
		}
	}
	
	// select 
	public List<TravelViewVO> selectTravelView(int uuid) {
		List<TravelViewVO> tvList = new ArrayList<TravelViewVO>();
		tvList = dao.selectTravelViewByUuid(uuid);
		System.out.println("tvList : " +tvList.get(0).getMembernick());
		for(int i=0;i<tvList.size();i++) {
			String name=dao.selectmember(tvList.get(i).getUuid());
			System.out.println(name);
			tvList.get(i).setMembernick(name);
		}
		return tvList;
	}
	
	// delete
	public void deleteDataAuto() {
		dao.deleteTravelViewAuto();
	}
	
	public TravelVO selectTravelStoryByTsid(int tsid) {
		return dao.selectTravelStoryByTsid(tsid);
	}
	
	
	public int registercount(int uuid) {
		return dao.registercount(uuid);
	}
}
