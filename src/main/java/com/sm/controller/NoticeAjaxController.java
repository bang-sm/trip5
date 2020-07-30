package com.sm.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sm.domain.PopUpNoticeVO;
import com.sm.domain.SlideNoticeVO;
import com.sm.service.NoticeService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class NoticeAjaxController {
//	private static final Logger logger = LoggerFactory.getLogger(NoticeAjaxController.class);

	@Autowired
	NoticeService noticeService;

	// 슬라이드 팝업 삭제
	@ResponseBody
	@PostMapping("/adminNotice/ajax/sNoticeDelete")
	public void sNoticeDelete(int sNoticeUid) throws Exception {
		System.out.println("들어옴");
		noticeService.sNoticeDelete(sNoticeUid);
	}

	// 슬라이드 팝업 수정
	@ResponseBody
	@PostMapping("/adminNotice/ajax/sNoticeUpdate")
	public void sNoticeUpdate(String snId, String snContent) throws Exception {
		System.out.println("들어옴");
		System.out.println("uid : " + snId + "\n" + "내용 : " + snContent);
		SlideNoticeVO slideNoticeVO = new SlideNoticeVO(Integer.parseInt(snId), snContent);
		noticeService.sNoticeUpdate(slideNoticeVO);
	}

	// 슬라이드 팝업 등록
	@ResponseBody
	@PostMapping("/adminNotice/ajax/sNoticeEnrol")
	public void sNoticeEnrol(@RequestParam(value="snId[]") int[] snId) throws Exception {
		System.out.println("들어옴");
		System.out.println("uid : " + snId[0] + "\n");
		
		// N으로 모두 초기화
		noticeService.sNoticeEnrollNo();
		for (int i = 0; i < snId.length; i++) {
			System.out.println(snId[i]);
		}
		HashMap<String, int[]> hm = new HashMap<>();
		hm.put("snId", snId);
		noticeService.sNoticeEnrollYes(hm);
	}

	// 메인페이지 슬라이드 팝업 표시
	@ResponseBody
	@PostMapping("/adminNotice/ajax/sNoticeShow")
	public List<String> sNoticeEnrol() throws Exception {
		System.out.println(noticeService.sNoticeShow());
		
		return noticeService.sNoticeShow();
	}
	
	///////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////팝업///////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	/* 팝업 공지창 정보 전달 */
	@ResponseBody
	@PostMapping("/adminNotice/ajax/pNoticeData")
	public List<PopUpNoticeVO> pNoticeData(String pNoticeUid) throws Exception {
		System.out.println("들어옴");
		
		return noticeService.pNoticeData(Integer.parseInt(pNoticeUid));
	}

	/*메인페이지에  팝업 공지창 정보 전달 */
	@ResponseBody
	@PostMapping("/adminNotice/ajax/pNoticeContent")
	public List<PopUpNoticeVO> pNoticeContent() throws Exception {
		System.out.println("들어옴");
		
		return noticeService.pNoticeContent();
	}

	/* 팝업 공지 수정 */
	@ResponseBody
	@PostMapping("/adminNotice/ajax/pNoticeUpdate")
	public void pNoticeUpdate(int pnId, String pnHeader, String pnContent,
			String pnTop, String pnLeft, String pnWidth, String pnHeight, String pnDate) throws Exception {
		System.out.println("들어옴");
		System.out.println(pnId + pnHeader + pnContent +
				pnTop + pnLeft + pnWidth + pnHeight + pnDate);
		
		noticeService.pNoticeUpdate(pnId, pnHeader, pnContent,
				pnTop, pnLeft, pnWidth, pnHeight, pnDate);
	}
	
	/* 팝업 공지 삭제 */
	@ResponseBody
	@PostMapping("/adminNotice/ajax/pNoticeDelete")
	public void pNoticeDelete(String pNoticeUid) throws Exception {
		System.out.println("들어옴");
		System.out.println(pNoticeUid);
		noticeService.pNoticeDelete(Integer.parseInt(pNoticeUid));
	}
	
	// 팝업 공지 등록
	@ResponseBody
	@PostMapping("/adminNotice/ajax/pNoticeEnrol")
	public void pNoticeEnrol(@RequestParam(value="pnId[]") int[] pnId) throws Exception {
		System.out.println("들어옴");
		System.out.println("uid : " +pnId[0] + "\n");
		
		// N으로 모두 초기화
		noticeService.pNoticeEnrollNo();
		for (int i = 0; i < pnId.length; i++) {
			System.out.println(pnId[i]);
		}
		HashMap<String, int[]> hm = new HashMap<>();
		hm.put("pnId", pnId);
		noticeService.pNoticeEnrollYes(hm);
	}

} // end controller
