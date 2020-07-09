package com.sm.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sm.domain.MemberVO;
@Repository
public class MyDAO {

	@Autowired
	SqlSession sqlsession;
	
	String namespace = "mappers.MyMapper";
	
	public MemberVO selectUuid(MemberVO memberVO) {
		
		return sqlsession.selectOne("mappers.myMapper.selectUuid", memberVO );
	}
	
	public int insertBlack(int myuid, int otheruid) {
		HashMap<String, Integer> cmap = new HashMap<String, Integer>();
		cmap.put("myuid", myuid);
		cmap.put("otheruid", otheruid);
		
		return sqlsession.insert("mappers.myMapper.insertBlackList", cmap);
	}
	
	public int selectBlack(int myuid) {
		return sqlsession.insert("mappers.myMapper.selectBlackList", myuid);
	}
}
