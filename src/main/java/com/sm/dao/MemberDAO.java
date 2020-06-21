package com.sm.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sm.domain.MemberVO;

@Repository
public class MemberDAO {
	
	@Autowired
	SqlSession sql;

	public void join(MemberVO memberVO) {
		sql.insert("mappers.memberMapper.signup",memberVO);
	}

	public MemberVO getUserById(String userid) {
		// TODO Auto-generated method stub
		return sql.selectOne("mappers.memberMapper.findById",userid);
	}

}
