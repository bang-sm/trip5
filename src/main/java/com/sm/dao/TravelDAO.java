package com.sm.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sm.domain.TravelInfoRootVO;
import com.sm.domain.TravelInfoVO;
import com.sm.domain.TravelReplyVO;
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
	
	
	/**
	 * @author smbang
	 * 일지 기본값 저장과 동시에 임시저장을 위한 하위 테이블 키값 미리 저장시키기!
	 */
	@Transactional
	public void travel_firstSave(TravelVO travelVO) {
		
		int dayPeriod=travelVO.getDatediff();
		//travel_story 테이블 먼저 insert
		sqlSession.insert("mappers.travelMapper.travel_firstSave",travelVO);
		
		int tsid=travelVO.getTsid();
		TravelInfoVO travelInfoVO=new TravelInfoVO();
		travelInfoVO.setTsid(tsid);
		//등록된 일지의 키값으로 기간 만큼 travel_story_info 미리저장
		for (int i = 0; i <= dayPeriod; i++) {
			travelInfoVO.setTsidDay(i+1);
			sqlSession.insert("mappers.travelMapper.travel_info_temp_save",travelInfoVO);
		}
	}
	
	//아이디 키값과 일지 키값을 이용해 일지 초기정보를 받아온다.
	public TravelVO getTravelStory(String tsid, int uuid) {
		HashMap<String , String> map=new HashMap<>();
		map.put("tsid", tsid);
		map.put("uuid", Integer.toString(uuid));
		
		return sqlSession.selectOne("mappers.travelMapper.getTravelStory",map);
	}

	//일지 키값을 가지고 기본 디테일 정보를 가져온다.
	public List<TravelInfoVO> getTravelInfo(String tsid) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("mappers.travelMapper.getTravelInfo",tsid);
	}

	//일지 임시데이터 저장
	public void tempSave(TravelInfoVO travelInfoVO, TravelInfoRootVO travelInfoRootVO) {
		
		List<TravelInfoVO> infoList=new ArrayList<>();
		for (int i = 1; i < travelInfoVO.getList().size(); i++) {
			infoList.add(travelInfoVO.getList().get(i));
		}
		
		List<TravelInfoRootVO> rootList=new ArrayList<>();
		if(travelInfoRootVO.getRootlist()!=null) {
			for (int i = 0; i < travelInfoRootVO.getRootlist().size(); i++) {
				rootList.add(travelInfoRootVO.getRootlist().get(i));
			}
		}
		HashMap<String , Object> map=new HashMap<>();
		map.put("infoList", infoList);
		map.put("rootList", rootList);
		
		sqlSession.insert("mappers.travelMapper.tempSaveTravleRoot",map);
		sqlSession.insert("mappers.travelMapper.tempSaveTravelInfo",map);
	}
	//루트삭제
	public void travel_root_delete(HashMap<String, Integer> map) {
		sqlSession.delete("mappers.travelMapper.travel_root_delete",map);
	}

	//루트정보
	public List<TravelInfoRootVO> getTravelRootInfo(String tsid) {
		return sqlSession.selectList("mappers.travelMapper.getTravelRootInfo",tsid);
	}

	
	//블로그데이터
	@Transactional
	public HashMap<String, Object> getTravelBlogData(HashMap<String, Integer> param) {
		
		List<TravelInfoVO> infoList=new ArrayList<TravelInfoVO>();
		infoList=sqlSession.selectList("mappers.travelMapper.getTravelInfo",param);
		TravelVO travelStory=new TravelVO();
		travelStory=sqlSession.selectOne("mappers.travelMapper.getTravelStory",param);
		List<TravelReplyVO> replyList=new ArrayList<TravelReplyVO>();
		replyList=sqlSession.selectList("mappers.travelMapper.getTravelReply",param);
		
		HashMap<String , Object> map=new HashMap<>();
		map.put("infoList", infoList);
		map.put("travelStory", travelStory);
		map.put("replyList", replyList);
		
		return map;
	}


	public void travel_reply_save(HashMap<String, Object> param) {
		sqlSession.insert("mappers.travelMapper.travel_reply_save",param);
	}


	public List<TravelReplyVO> travel_reply_list(int tsid) {
		return sqlSession.selectList("mappers.travelMapper.getTravelReply",tsid);
	}

}
