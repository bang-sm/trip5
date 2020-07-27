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
	
	// 회원가입
	public void kakaoJoin(MemberVO memberVO) {
		sql.insert(namespace + ".kakaoSignup",memberVO);
	}
	
	// 회원 수
	public void userCnt() {
		sql.selectOne(namespace + ".userCnt");
	}

	// kakaoOk 변경
	public void kakaoOk(String memberemail) {
		sql.insert(namespace + ".kakaoOk",memberemail);
	}

	// 아이디 가져오기
	public MemberVO getUserById(String memberemail) {
		return sql.selectOne(namespace + ".findById",memberemail);
	}
	
	// 아이디 중복체크, 카카오Ok 가져오기
	public MemberVO idCheck(String memberemail) throws Exception{
		return sql.selectOne(namespace + ".idCheck",memberemail);
	}
	
	// uuid 가저오기
	public int uuidCheck(String memberemail) throws Exception{
		return sql.selectOne(namespace + ".uuidCheck",memberemail);
	}
	
}
