package com.sm.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sm.domain.PopUpNoticeVO;
import com.sm.domain.SlideNoticeVO;

@Repository
public class NoticeDAO {
	
	@Autowired
	SqlSession sql;
	
	String namespace = "mappers.noticeMapper";
	
	// 슬라이드 공지 내용 가져오기
	public List<SlideNoticeVO> sNoticeContent() throws Exception{
		return sql.selectList(namespace + ".sNoticeContent");
	}
	
	// 슬라이드 공지 추가
	public int sNoticeJoin() throws Exception{
		return sql.insert(namespace + ".slideJoin");
	}

	// 슬라이드 공지 삭제
	public void sNoticeDelete(int sNoticeUid) throws Exception{
		sql.update(namespace + ".slideDelete",sNoticeUid);
	}

	// 슬라이드 공지 수정
	public void sNoticeUpdate(SlideNoticeVO slideNoticeVO) throws Exception{
		sql.update(namespace + ".sNoticeUpdate",slideNoticeVO);
	}

	// 슬라이드 공지 등록 안함
	public void sNoticeEnrollNo() throws Exception{
		sql.update(namespace + ".sNoticeEnrollNo");
	}

	// 슬라이드 공지 등록 함
	public void sNoticeEnrollYes(HashMap<String, int[]> snId) throws Exception{
		sql.update(namespace + ".sNoticeEnrollYes",snId);
	}

	// 메인페이지 슬라이드 공지 표시
	public List<String> sNoticeShow() throws Exception{
		return sql.selectList(namespace + ".sNoticeShow");
	}

	//////////////////////팝업////////////////////////////
	// 팝업 공지 내용 가져오기
	public List<PopUpNoticeVO> pNoticeContent() throws Exception{
		return sql.selectList(namespace + ".pNoticeContent");
	}

	// 특정 ipnd 팝업 공지 내용 가져오기
	public List<PopUpNoticeVO> pNoticeData(int pnId) throws Exception{
		return sql.selectList(namespace + ".pNoticeData",pnId);
	}

	// 특정 ipnd 팝업 공지 내용 수정
	public void pNoticeUpdate(PopUpNoticeVO popUpNoticeVO) throws Exception{
		sql.update(namespace + ".pNoticeUpdate",popUpNoticeVO);
		
	}
	
	// 팝업 공지 삭제
	public void pNoticeDelete(int pNoticeUid) throws Exception{
		sql.update(namespace + ".pNoticeDelete",pNoticeUid);
	}
	
	// 팝업 공지 등록 안함
	public void pNoticeEnrollNo() throws Exception{
		sql.update(namespace + ".pNoticeEnrollNo");
	}

	// 팝업 공지 등록 함
	public void pNoticeEnrollYes(HashMap<String, int[]> pnId) throws Exception{
		sql.update(namespace + ".pNoticeEnrollYes",pnId);
	}
	
} // end class
