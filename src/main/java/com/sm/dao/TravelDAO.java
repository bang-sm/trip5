package com.sm.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sm.domain.PhotoVO;
import com.sm.domain.TravelInfoRootVO;
import com.sm.domain.TravelInfoVO;
import com.sm.domain.TravelReplyVO;
import com.sm.domain.TravelVO;

@Repository
public class TravelDAO {

	@Autowired
	SqlSession sqlSession;

	// 일지 등록
	@Transactional
	public void storyRegist(TravelVO travelVO, TravelInfoVO travelinfoVO) {

		// 먼저 최상위 테이블 insert
		sqlSession.insert("mappers.travelMapper.storyRegist", travelVO);

		List<TravelInfoVO> travelinfoList = new ArrayList<>();
		for (int i = 0; i < travelinfoVO.getList().size(); i++) {
			travelinfoList.add(travelinfoVO.getList().get(i));
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("infoList", travelinfoList);
		System.out.println(map);
		sqlSession.insert("mappers.travelMapper.story_detail_Regist", map);
	}

	/**
	 * @author smbang 일지 기본값 저장과 동시에 임시저장을 위한 하위 테이블 키값 미리 저장시키기!
	 */
	@Transactional
	public void travel_firstSave(TravelVO travelVO) {

		int dayPeriod = travelVO.getDatediff();
		// travel_story 테이블 먼저 insert
		sqlSession.insert("mappers.travelMapper.travel_firstSave", travelVO);

		int tsid = travelVO.getTsid();
		TravelInfoVO travelInfoVO = new TravelInfoVO();
		travelInfoVO.setTsid(tsid);
		// 등록된 일지의 키값으로 기간 만큼 travel_story_info 미리저장
		for (int i = 0; i <= dayPeriod; i++) {
			travelInfoVO.setTsidDay(i + 1);
			sqlSession.insert("mappers.travelMapper.travel_info_temp_save", travelInfoVO);
		}
	}

	// 아이디 키값과 일지 키값을 이용해 일지 초기정보를 받아온다.
	public TravelVO getTravelStory(String tsid, int uuid) {
		HashMap<String, String> map = new HashMap<>();
		map.put("tsid", tsid);
		map.put("uuid", Integer.toString(uuid));

		return sqlSession.selectOne("mappers.travelMapper.getTravelStory", map);
	}

	// 일지 키값을 가지고 기본 디테일 정보를 가져온다.
	public List<TravelInfoVO> getTravelInfo(String tsid) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("mappers.travelMapper.getTravelInfo", tsid);
	}

	// 일지 임시데이터 저장
	public void tempSave(TravelInfoVO travelInfoVO, TravelInfoRootVO travelInfoRootVO) {

		List<TravelInfoVO> infoList = new ArrayList<>();
		List<TravelInfoRootVO> rootList = new ArrayList<>();
		HashMap<String, Object> map = new HashMap<>();
		
		
		for (int i = 1; i < travelInfoVO.getList().size(); i++) {
			infoList.add(travelInfoVO.getList().get(i));
		}
		map.put("infoList", infoList);
		sqlSession.insert("mappers.travelMapper.tempSaveTravelInfo", map);
		
		if (travelInfoRootVO.getRootlist() != null) {
			for (int i = 0; i < travelInfoRootVO.getRootlist().size(); i++) {
				rootList.add(travelInfoRootVO.getRootlist().get(i));
			}
			map.put("rootList", rootList);
			sqlSession.insert("mappers.travelMapper.tempSaveTravleRoot", map);
		}
	}

	// 루트삭제
	public void travel_root_delete(HashMap<String, Integer> map) {
		sqlSession.delete("mappers.travelMapper.travel_root_delete", map);
	}

	// 루트정보
	public List<TravelInfoRootVO> getTravelRootInfo(String tsid) {
		return sqlSession.selectList("mappers.travelMapper.getTravelRootInfo", tsid);
	}

	// 블로그데이터
	@Transactional
	public HashMap<String, Object> getTravelBlogData(HashMap<String, Integer> param) {

		List<TravelInfoVO> infoList = new ArrayList<TravelInfoVO>();
		infoList = sqlSession.selectList("mappers.travelMapper.getTravelInfo", param);
		TravelVO travelStory = new TravelVO();
		travelStory = sqlSession.selectOne("mappers.travelMapper.getTravelStory", param);
		List<TravelReplyVO> replyList = new ArrayList<TravelReplyVO>();
		replyList = sqlSession.selectList("mappers.travelMapper.getTravelReply", param);
		List<PhotoVO> photoList = new ArrayList<PhotoVO>();
		photoList = sqlSession.selectList("mappers.travelMapper.getTravelImage", param);
		List<TravelInfoRootVO> rootList = new ArrayList<TravelInfoRootVO>();
		rootList = sqlSession.selectList("mappers.travelMapper.getTravelRootList", param);

		HashMap<String, Object> map = new HashMap<>();
		map.put("infoList", infoList);
		map.put("travelStory", travelStory);
		map.put("replyList", replyList);
		map.put("photoList", photoList);
		map.put("rootList", rootList);

		return map;
	}

	public void travel_reply_save(HashMap<String, Object> param) {
		sqlSession.insert("mappers.travelMapper.travel_reply_save", param);
	}

	public List<TravelReplyVO> travel_reply_list(int tsid) {
		return sqlSession.selectList("mappers.travelMapper.getTravelReply", tsid);
	}

	// 댓글삭제
	public void travel_reply_delete(HashMap<String, Integer> param) {
		sqlSession.delete("mappers.travelMapper.delete_my_reply", param);
	}

	// 좋아요 추가
	public void travel_like(int tsid) {
		sqlSession.update("mappers.travelMapper.travel_like", tsid);
	}

	// 좋아요개수
	public int likeCount(int tsid) {
		return sqlSession.selectOne("mappers.travelMapper.likeCount", tsid);
	}

	// 북마크 되어있는지 안되어있는지 체크
	public int bookmarkCheck(HashMap<String, Integer> param) {
		return sqlSession.selectOne("mappers.travelMapper.bookmarkCheck", param);
	}

	// 북마크
	public void bookmark(HashMap<String, Integer> param) {
		sqlSession.insert("mappers.travelMapper.bookmark", param);
	}

	// 북마크삭제
	public void bookmarkDelete(HashMap<String, Integer> param) {
		sqlSession.delete("mappers.travelMapper.bookmarkDelete", param);
	}

	// 팔로우하기
	public void follow(HashMap<String, Integer> param) {
		// TODO Auto-generated method stub
		sqlSession.insert("mappers.travelMapper.follow", param);
	}

	// 팔로우 되어있는지 체크
	public int followCheck(HashMap<String, Integer> param) {

		return sqlSession.selectOne("mappers.travelMapper.followCheck", param);
	}

	// 팔로우 삭제
	public void follow_delete(HashMap<String, Integer> param) {
		sqlSession.delete("mappers.travelMapper.followDelete", param);
	}

	public int tempTravelCheck(int uuid) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("mappers.travelMapper.tempTravelCheck", uuid);
	}

	// 이미지업로드
	public void photo_insert(Map<String, Object> map) {
		// TODO Auto-generated method stub
		sqlSession.insert("mappers.travelMapper.photo_insert", map);
	}

	// 이미지리스트
	public List<PhotoVO> getTravelImage(String tsid) {
		return sqlSession.selectList("mappers.travelMapper.getTravelImage", tsid);
	}

	// 이미지삭제
	public void imageDelete(int photoId) {
		sqlSession.delete("mappers.travelMapper.imageDelete",photoId);
	}

	// 최종저장
	@Transactional
	public void finalSave(HashMap<String, Object> map,int tsid) {
		sqlSession.insert("mappers.travelMapper.tempSaveTravleRoot",map);
		sqlSession.insert("mappers.travelMapper.tempSaveTravelInfo",map);
		sqlSession.update("mappers.travelMapper.tempComplete",tsid);
		
	}
	//일지 대표사진과 리스트
	public List<TravelVO> getMyTravelList(int uuid) {
		return sqlSession.selectList("mappers.travelMapper.getMyTravelList",uuid);
	}
	
	//나의 일지 top3
	public List<TravelVO> getMyTravelTop3(int uuid) {
		return sqlSession.selectList("mappers.travelMapper.getMyTravelTop3",uuid);
	}

	//유저찾기
	public int findUser(int uuid) {
		sqlSession.selectOne("mappers.travelMapper.findUser",uuid);
		return sqlSession.selectOne("mappers.travelMapper.findUser",uuid);
	}

	//나의 팔로우 좋아요 일지 카운트
	public int getMyFollow(int uuid) {
		return sqlSession.selectOne("mappers.travelMapper.getMyFollow",uuid);
	}
	public int getMyTotalLike(int uuid) {
		return sqlSession.selectOne("mappers.travelMapper.getMyTotalLike",uuid);
	}
	public int getMyTravelCount(int uuid) {
		return sqlSession.selectOne("mappers.travelMapper.getMyTravelCount",uuid);
	}

	// 정세헌 건드림 - 메인
	public List<TravelVO> mainTravleList() {
		return sqlSession.selectList("mappers.travelMapper.mainTravleList");
	}
	
	public List<TravelVO> selectmaintravelListOrderbyTslike() {
//		return sqlSession.selectList("mappers.travelMapper.selectmaintravelListOrderbyTslike");
		return sqlSession.selectList("mappers.weatherlocalMapper.selectmaintravelListOrderbyTslike");
	}
	
	public List<TravelVO> selectmaintravelListOrderbyTsView() {
//		return sqlSession.selectList("mappers.travelMapper.selectmaintravelListOrderbyTsView");
		return sqlSession.selectList("mappers.weatherlocalMapper.selectmaintravelListOrderbyTsView"); //  수정 후 삭제
	}
	
}
