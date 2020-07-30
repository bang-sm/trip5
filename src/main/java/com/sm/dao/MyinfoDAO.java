package com.sm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sm.domain.TravelVO;
import com.sm.domain.TravelViewVO;

@Repository
public class MyinfoDAO {

	private String mappername = "mappers.myinfoMapper.";
	
	@Autowired
	SqlSession sqlSession;
	
	/*
	 *  최근 열어본 글 
	 */
	
	// 유저누를때 정보 DB에 삽입
	public void insertTravelView(TravelViewVO travelViewVO) {
		sqlSession.insert(mappername + "insertTravelView", travelViewVO);
	}
	
	// 3일 이내로 본 글 내역 SELECT
	public List<TravelViewVO> selectTravelViewByUuid(int uuid){
		return sqlSession.selectList(mappername + "selectTravelViewByUuid", uuid);
	}
	
	// 3일 이내로 본 글 수 SELECT
	public int countTravelViewbyUuid(int uuid) {
		return sqlSession.selectOne(mappername + "countTravelViewbyUuid", uuid);
	}
	
	// 이미 본 글이라면, datetime을 현재로 초기화
	public void updateViewDateBytsid(Map<String, Integer> paramMap) {
		sqlSession.update(mappername + "updateViewDateBytsid", paramMap);
	}
	
	// 7일 이상 열지 않은 데이터 삭제
	public void deleteTravelViewAuto() {
		sqlSession.delete(mappername + "deleteTravelViewAuto");
	}
	
	public TravelVO selectTravelStoryByTsid(int tsid) {
		return sqlSession.selectOne(mappername + "selectTravelStoryByUuid", tsid);
	}
	
}
