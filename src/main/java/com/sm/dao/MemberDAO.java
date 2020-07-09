package com.sm.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sm.domain.MemberVO;

@Repository
public class MemberDAO {
	
	@Autowired
	SqlSession sql;
	
	String namespace = "mappers.memberMapper";

	// 회원가입
	public void join(MemberVO memberVO) {
		sql.insert(namespace + ".signup",memberVO);
	}


	public MemberVO getUserById(String memberemail) {
		return sql.selectOne(namespace + ".findById",memberemail);
	}
	
	// 아이디 중복체크
	public MemberVO idCheck(String memberemail) throws Exception{
		return sql.selectOne(namespace + ".idCheck",memberemail);
	}

	// kakao 회원가입 중복체크
	public MemberVO kakaoCheck(String memberemail) throws Exception{
		return sql.selectOne(namespace + ".kakaoCheck",memberemail);
	}
	
	

}
