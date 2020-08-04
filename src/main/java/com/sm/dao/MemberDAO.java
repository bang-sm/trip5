package com.sm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sm.domain.MemberVO;
import com.sm.domain.VisitmembersVO;

@Repository
public class MemberDAO {
	
	@Autowired
	SqlSession sql;
	
	String namespace = "mappers.memberMapper";
	
	// 회원 수
	public int userCnt() {
		return sql.selectOne(namespace + ".userCnt");
	}

	// 회원 페이징 정보
	public List<MemberVO> memberList(Map<String, Integer> map) {
		return sql.selectList(namespace + ".memberList", map);
	}
	
	
	// 회원가입
	public void join(MemberVO memberVO) {
		sql.insert(namespace + ".signup",memberVO);
	}
	
	// 회원가입
	public void kakaoJoin(MemberVO memberVO) {
		sql.insert(namespace + ".kakaoSignup",memberVO);
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

	//////////////////////////////////////////////////////////////////////
	// ADMIN
	//////////////////////////////////////////////////////////////////////
	// 회원 블랙리스트 추가
	public void addBlackList(Map<String, Object> map) {
		sql.update(namespace + ".addBlackList", map);
	}

	// 회원 가입타입 통계
	public List<Object> userType() {
		return sql.selectList(namespace + ".userType");
	}
	
	// 가입 타입별 일일 접속자 통계
	public void insertUserCount(Map<String, Object> map) {
		sql.insert(namespace + ".insertUserCount", map);
	}
	
	// 가입자 타입 정보
	public VisitmembersVO insertCondition(Map<String, Object> map) {
		return sql.selectOne(namespace + ".insertCondition", map);
	}

	// uid 일일접속자 정보
	public Map<String, Object> adminUserCount() {
		System.out.println(sql.selectOne(namespace + ".adminUserCount") + " ddididi");
		return sql.selectOne(namespace + ".adminUserCount");
	}

	// 월별 가입자 정보
	public  List<Map<String, Object>> adminUserSignUp() {
		return sql.selectList(namespace + ".adminUserSignUp");
	}
	
	//////////////////////////////////////////////////////////////////////
	// MYINFORM
	//////////////////////////////////////////////////////////////////////
	// 사진 입력
	public void profileImg(Map<String, Object> map) {
		sql.update(namespace + ".profileImg", map);
	}
}
