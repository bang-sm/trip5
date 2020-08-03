package com.sm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sm.domain.MypageVO;
import com.sm.domain.PlacelistVo;
import com.sm.domain.TravelVO;
import com.sm.domain.TravelViewVO;
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
	
	public void bookmark(HashMap<String, Integer> hmap) {
		sql.update("mappers.placelistMapper.bookmark",hmap);
	}
	
	public void checkbox(HashMap<String, Integer> cmap) {
	
		sql.update("mappers.placelistMapper.checkbox",cmap);
	}
	
	public List<PlacelistVo> goplace(HashMap<String, Integer> gmap){
		return sql.selectList("mappers.placelistMapper.goplace",gmap);
	}
	
	public int barchart(HashMap<String, Integer> bmap){
		return sql.selectOne("mappers.placelistMapper.barchart",bmap);
	}
	
	public void delete(int placeid) {
		sql.delete("mappers.placelistMapper.delete",placeid);
	}
	
	public int chartcount(HashMap<String, Integer> fmap) {
		return sql.selectOne("mappers.placelistMapper.chartcount",fmap);
	}
	
	public int linechart(HashMap<String, Object> fmap) {
		return sql.selectOne("mappers.placelistMapper.chartline",fmap);
	}
	
	public int area(HashMap<String, Object> fmap) {
		return sql.selectOne("mappers.placelistMapper.area",fmap);
	}
	
	public List<Integer> followingmecheck(int uuid){
		return sql.selectList("mappers.placelistMapper.followingmecheck",uuid);
	}
	
	
	public String status(HashMap<String, Object> myst){
		return  sql.selectOne("mappers.placelistMapper.status",myst);
	}
	
	public travelmembernickVO mypagetravelstory(int uuid){
		return sql.selectOne("mappers.placelistMapper.mypagetravelstory",uuid);
	}
	public  List<Integer> mypagebm(int uuid){
		return sql.selectList("mappers.placelistMapper.mypagebm",uuid);
	}
	
	public List<TravelVO> mypagelike(int uuid){
		return sql.selectList("mappers.placelistMapper.mypagelike",uuid);
	}
	
	
	public void followok(HashMap<String, Object> mymap) {
		sql.insert("mappers.placelistMapper.followok",mymap);
	}
	
	public void followdel(HashMap<String, Object> mymap) {
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
	
	public void blacklistdel(HashMap<String, Object> mymap) {
		sql.delete("mappers.placelistMapper.blacklistdel",mymap);
	}
	public int mypageline(HashMap<String, Object> fmap) {
		return sql.selectOne("mappers.placelistMapper.mypageline",fmap);
	}
	
	
	public List<Integer> followingcount(int uuid) {
		return sql.selectList("mappers.placelistMapper.followermecheck",uuid);
	}
	
	public List<Integer>  followingyoucount(int uuid){
		return sql.selectList("mappers.placelistMapper.followingyoucheck",uuid);
	}
	
	public int getBoardAllCount(int uuid) {
		return sql.selectOne("mappers.placelistMapper.mypagelistcount",uuid);
	}
	
	public List<MypageVO> boardList(Map<String, Integer> map){
		return sql.selectList("mappers.placelistMapper.boardList",map);
	}
	
	public void insertTravelView(TravelViewVO travelViewVO) {
		sql.insert( "mappers.placelistMapper.insertTravelView", travelViewVO);
	}
	
	// 3일 이내로 본 글 내역 SELECT
	public List<TravelViewVO> selectTravelViewByUuid(int uuid){
		return sql.selectList("mappers.placelistMapper.selectTravelViewByUuid", uuid);
	}
	
	// 3일 이내로 본 글 수 SELECT
	public int countTravelViewbyUuid(int uuid) {
		return sql.selectOne("mappers.placelistMapper.countTravelViewbyUuid", uuid);
	}
	
	// 이미 본 글이라면, datetime을 현재로 초기화
	public void updateViewDateBytsid(Map<String, Integer> paramMap) {
		sql.update("mappers.placelistMapper.updateViewDateBytsid", paramMap);
	}
	
	// 7일 이상 열지 않은 데이터 삭제
	public void deleteTravelViewAuto() {
		sql.delete("mappers.placelistMapper.deleteTravelViewAuto");
	}
	
	public TravelVO selectTravelStoryByTsid(int tsid) {
		return sql.selectOne("mappers.placelistMapper.selectTravelStoryByTsid", tsid);
	}
	
	public String selectmember(int uuid) {
		return sql.selectOne("mappers.placelistMapper.selectmember",uuid);
	}
	
	public int registercount(int uuid) {
		return sql.selectOne("mappers.placelistMapper.registercount", uuid);
	}
}
