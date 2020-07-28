package com.sm.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.dao.NoticeDAO;
import com.sm.domain.SlideNoticeVO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NoticeService {

	@Autowired
	NoticeDAO noticeDao;

	// 슬라이드 공지 출력
	public List<SlideNoticeVO> sNoticeNotice() throws Exception {

		List<SlideNoticeVO> snContent = noticeDao.sNoticeContent();

		return snContent;
	} // end slideNotice

	// 슬라이드 공지 추가
	public void sNoticeJoin() throws Exception {
		noticeDao.sNoticeJoin();
	}

	// 슬라이드 공지 삭제
	public void sNoticeDelete(int sNoticeUid) throws Exception {
		noticeDao.sNoticeDelete(sNoticeUid);
	}

	// 슬라이드 공지 수정
	public void sNoticeUpdate(SlideNoticeVO slideNoticeVO) throws Exception {
		noticeDao.sNoticeUpdate(slideNoticeVO);
	}

	// 슬라이드 공지 등록 안함
	public void sNoticeEnrollNo() throws Exception {
		noticeDao.sNoticeEnrollNo();
	}

	// 슬라이드 공지 등록 함
	public void sNoticeEnrollYes(HashMap<String, int[]> snId) throws Exception {
		noticeDao.sNoticeEnrollYes(snId);
	}

	// 메인페이지 슬라이드 공지 표시
	public List<String> sNoticeShow() throws Exception {
		return noticeDao.sNoticeShow();
	}
}
