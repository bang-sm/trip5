package com.sm.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.dao.NoticeDAO;
import com.sm.domain.PopUpNoticeVO;
import com.sm.domain.SlideNoticeVO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NoticeService {

	@Autowired
	NoticeDAO noticeDao;

	// 슬라이드 공지 출력
	public List<SlideNoticeVO> sNoticeContent() throws Exception {

		List<SlideNoticeVO> snContent = noticeDao.sNoticeContent();

		return snContent;
	} // end slideNotice

	// 슬라이드 공지 추가
	public void sNoticeJoin() throws Exception {
		noticeDao.sNoticeJoin();
	} // end sNoticeJoin

	// 슬라이드 공지 삭제
	public void sNoticeDelete(int sNoticeUid) throws Exception {
		noticeDao.sNoticeDelete(sNoticeUid);
	} // end sNoticeDelete

	// 슬라이드 공지 수정
	public void sNoticeUpdate(SlideNoticeVO slideNoticeVO) throws Exception {
		noticeDao.sNoticeUpdate(slideNoticeVO);
	} // end sNoticeUpdate

	// 슬라이드 공지 등록 안함
	public void sNoticeEnrollNo() throws Exception {
		noticeDao.sNoticeEnrollNo();
	} // end sNoticeEnrollNo

	// 슬라이드 공지 등록 함
	public void sNoticeEnrollYes(HashMap<String, int[]> snId) throws Exception {
		noticeDao.sNoticeEnrollYes(snId);
	} // end sNoticeEnrollYes

	// 메인페이지 슬라이드 공지 표시
	public List<String> sNoticeShow() throws Exception {
		return noticeDao.sNoticeShow();
	} // end sNoticeShow
	
	///////////////////////////팝업////////////////////////////////
	// 팝업 공지 출력
	public List<PopUpNoticeVO> pNoticeContent() throws Exception {

		List<PopUpNoticeVO> pnContent = noticeDao.pNoticeContent();

		return pnContent;
	} // end slideNotice

	// 특정 pnid 슬라이드 공지 내용
	public List<PopUpNoticeVO> pNoticeData(int sNoticeUid) throws Exception {
		
		List<PopUpNoticeVO> pnContent = noticeDao.pNoticeData(sNoticeUid);
		
		return pnContent;
	} // end slideNotice
	
	// 특정 pnid 슬라이드 공지 내용 수정
	public void pNoticeUpdate(int pnId, String pnHeader, String pnContent) throws Exception {
		
		PopUpNoticeVO popUpNoticeVO = new PopUpNoticeVO(pnId, pnHeader, pnContent);
		System.out.println(popUpNoticeVO + "dkdfnmaksdfnlkdasnflkdsnalk");
		noticeDao.pNoticeUpdate(popUpNoticeVO);
		
	} // end pNoticeUpdate
	
	// 팝업 공지 삭제
	public void pNoticeDelete(int pNoticeUid) throws Exception {
		noticeDao.pNoticeDelete(pNoticeUid);
	} // end sNoticeDelete
	
	// 팝업 공지 등록 안함
	public void pNoticeEnrollNo() throws Exception {
		noticeDao.pNoticeEnrollNo();
	} // end sNoticeEnrollNo
	
	// 팝업 공지 등록 함
	public void pNoticeEnrollYes(HashMap<String, int[]> pnId) throws Exception {
		noticeDao.pNoticeEnrollYes(pnId);
	} // end sNoticeEnrollYes
}
