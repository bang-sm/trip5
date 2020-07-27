package com.sm.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.sm.service.NoticeService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class NoticeAjaxController {
	private static final Logger logger = LoggerFactory.getLogger(NoticeAjaxController.class);

	@Autowired
	NoticeService noticeService;

	// 관리자 페이지 자료들 불러올 때
	@PostMapping("/adminNotice/ajax/slideNotice")
	public String[] userCnt() {

		noticeService.slideNotice();
		return null;
	}
	
} // end controller
