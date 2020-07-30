package com.sm.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sm.domain.PopUpNoticeVO;
import com.sm.domain.SlideNoticeVO;
import com.sm.service.NoticeService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	NoticeService noticeService;
	
	// 어드민 메인(통계) 페이지
	@GetMapping("/admin")
	public String dispAdmin() {

		
		return "/admin/statistics";
	}

	// 어드민 공지관리 페이지
	@GetMapping("/admin/adminNotice")
	public String dispAdminNotice(Model model) throws Exception {
		
		List<SlideNoticeVO> snContent = noticeService.sNoticeContent();
		List<PopUpNoticeVO> pnContent = noticeService.pNoticeContent();
		
		
		model.addAttribute("sNoticeContent",snContent);
		model.addAttribute("pNoticeContent",pnContent);
		
		return "/admin/adminNotice";
	}

} // end controller
