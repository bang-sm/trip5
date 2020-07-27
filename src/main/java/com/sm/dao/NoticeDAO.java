package com.sm.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeDAO {
	
	@Autowired
	SqlSession sql;
	
	String namespace = "mappers.noticeMapper";

//	// 회원가입
//	public void join(MemberVO memberVO) {
//		sql.insert(namespace + ".signup",memberVO);
//	}
	
	// 슬라이드 공지 내용 가져오기
	public List<String> slideContent() throws Exception{
		return sql.selectList(namespace + ".slideContent");
	}

	// 슬라이드 공지 수
	public int slideCount() throws Exception{
		return sql.selectOne(namespace + ".slideCount");
	}
	
}
