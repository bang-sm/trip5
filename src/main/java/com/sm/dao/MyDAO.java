package com.sm.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sm.domain.MemberVO;
import com.sm.domain.MessageVO;
@Repository
public class MyDAO {

	@Autowired
	SqlSession sqlsession;
	
	
	public MemberVO selectUuid(MemberVO memberVO) {
		return sqlsession.selectOne("mappers.myMapper.selectUuid", memberVO );
	}
	
	public int insertBlack(int myuid, int otheruid) {
		HashMap<String, Integer> cmap = new HashMap<String, Integer>();
		cmap.put("myuid", myuid);
		cmap.put("otheruid", otheruid);
		
		return sqlsession.insert("mappers.myMapper.insertBlackList", cmap);
	}
	
	public List<MemberVO> selectBlack(int uuid) {
		return sqlsession.selectList("mappers.myMapper.selectBlackList", uuid);
	}
	
	public int deleteBlackList(int otheruid) {
		return sqlsession.delete("mappers.myMapper.deleteBlackList",otheruid);
	}
	
	public int sendToMsg(MessageVO messageVO) {
		return sqlsession.insert("mappers.myMapper.sendToMsg", messageVO);
	}
	
	public List<MessageVO> sendMessage(MessageVO messageVO) {
		return sqlsession.selectList("mappers.myMapper.sendMessage", messageVO);
	}
}
