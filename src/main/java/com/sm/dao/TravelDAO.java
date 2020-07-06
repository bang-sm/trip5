package com.sm.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

	//일지 등록
	@Transactional
	public void storyRegist(TravelVO travelVO, TravelInfoVO travelinfoVO) {
		
		//먼저 최상위 테이블 insert
		sqlSession.insert("mappers.travelMapper.storyRegist",travelVO);
		
		List<TravelInfoVO> travelinfoList=new ArrayList<>();
		for (int i = 0; i < travelinfoVO.getList().size(); i++) {
			travelinfoList.add(travelinfoVO.getList().get(i));
		}
		HashMap<String , Object> map=new HashMap<>();
		map.put("infoList", travelinfoList);
		System.out.println(map);
		sqlSession.insert("mappers.travelMapper.story_detail_Regist",map);
	}

}
