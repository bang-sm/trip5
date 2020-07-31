package com.sm.service;

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
	
}
