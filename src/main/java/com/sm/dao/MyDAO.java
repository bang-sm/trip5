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
	
	public int countMessage(MessageVO messageVO) {
		return sqlsession.selectOne("mappers.myMapper.countMessage", messageVO);
	}
	
	public List<MessageVO> receiveMessage(MessageVO messageVO){
		return sqlsession.selectList("mappers.myMapper.receive", messageVO);
	}
	
	public MessageVO clipRead(MessageVO messageVO) {
		return sqlsession.selectOne("mappers.myMapper.clipread", messageVO);
	}
	
	public int readed(MessageVO messageVO) {
		return sqlsession.update("mappers.myMapper.readed", messageVO);
	}
	
	public List<MessageVO> clipTrash(MessageVO messageVO){
		return sqlsession.selectList("mappers.myMapper.clipTrash", messageVO);
	}
	
	public int gotoTrash(String [] msgid, String sendid) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("messageid", msgid);
		map.put("sendid", sendid);
		
		return sqlsession.update("mappers.myMapper.gotoTrash", map);
	}
	
	public String selectNick(int uuid) {
		return sqlsession.selectOne("mappers.myMapper.selectNick", uuid);
	}
	
	public MessageVO selectByMsgid(int msgid) {
		return sqlsession.selectOne("mappers.myMapper.selectByMsgid", msgid);
	}
	
	public int gotoTrashRead(MessageVO messageVO) {
		return sqlsession.update("mappers.myMapper.gotoTrashRead", messageVO);
	}
	
	public MessageVO prevClip(MessageVO messageVO) {
		return sqlsession.selectOne("mappers.myMapper.prevClip", messageVO);
	}
	
	public MessageVO nextClip(MessageVO messageVO) {
		return sqlsession.selectOne("mappers.myMapper.nextClip", messageVO);
	}
	
	public List<MessageVO> selectByDelete(String [] msgid){
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("msgid", msgid);
		return sqlsession.selectList("mappers.myMapper.selectByDelete", map);
	}
	
	public int deleteOk(MessageVO messageVO) {
		return sqlsession.update("mappers.myMapper.deleteOk", messageVO);
	}
	
	public int deleteForClip(MessageVO messageVO) {
		return sqlsession.delete("mappers.myMapper.deleteForClip" ,messageVO);
	}
}
