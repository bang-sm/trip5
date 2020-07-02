package com.sm.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sm.domain.TravelInfoVO;
import com.sm.domain.TravelVO;
@Repository
public class TravelDAO {
	
	@Autowired
	SqlSession sqlSession;

	@Transactional
	public void storyRegist(TravelVO travelVO, TravelInfoVO travelinfoVO) {
		// TODO Auto-generated method stub
		sqlSession.insert("mappers.travelMapper.storyRegist",travelVO);
		sqlSession.insert("mappers.travelMapper.story_detail_Regist",travelinfoVO);
	}

}
