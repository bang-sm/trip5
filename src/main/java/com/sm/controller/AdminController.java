package com.sm.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	// 어드민 메인(통계) 페이지
	@GetMapping("/admin")
	public String dispAdmin() {
		return "/admin/statistics";
	}

	// 어드민 공지관리 페이지
	@GetMapping("/admin/notice")
	public String dispAdminNotice() {
		return "/admin/notice";
	}

	// 관리자 페이지 자료들 불러올 때
	@PostMapping("/admin/ajax/userCnt")
	public String[] userCnt() {

		return null;
	}
} // end controller
