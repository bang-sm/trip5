package com.sm.controller;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

} // end controller
