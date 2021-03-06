package com.sm.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sm.domain.PopUpNoticeVO;
import com.sm.domain.SlideNoticeVO;
import com.sm.service.MemberService;
import com.sm.service.NoticeService;

import lombok.AllArgsConstructor;


// 관리자 페이지
@Controller
@AllArgsConstructor
public class AdminController {

	@Autowired
	NoticeService noticeService;
	
	@Autowired
	MemberService memberService;
	
	//메인(통계) 페이지
	@GetMapping("/admin")
	public String dispAdmin(ModelMap modelMap, HttpServletRequest request) {
		
		modelMap.addAttribute("userTypes", memberService.userTpye());
		return "/admin/statistics";
	}
	
	//유저관리 페이지
	@GetMapping("/admin/adminMembers")
	public String dispAdminMembers(ModelMap modelMap, @RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
			String uuid, String msgBlackList) throws Exception {
		System.out.println("들어옴");
		System.out.println(uuid);
		System.out.println(msgBlackList);
		
		if(uuid != null) {
			memberService.addBlackList(uuid, msgBlackList);	
		} // end if
		
		modelMap.addAttribute("memberList", memberService.memberList(currentPage));
		
		return "/admin/adminMembers";
	}

	//공지관리 페이지
	@GetMapping("/admin/adminNotice")
	public String dispAdminNotice(Model model) throws Exception {
		
		List<SlideNoticeVO> snContent = noticeService.sNoticeContent();
		List<PopUpNoticeVO> pnContent = noticeService.pNoticeContent();
		
		model.addAttribute("sNoticeContent",snContent);
		model.addAttribute("pNoticeContent",pnContent);
		
		return "/admin/adminNotice";
	}
	
	////////////////////////////////////////////////////////////////////////////////
	//////////////////////////AjAX//////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////
	//메인(통계) 일일 접속자 DATA 페이지
	@ResponseBody
	@PostMapping("/admin/ajax/adminUserCount")
	public int[] ajaxUserCount() {
		return memberService.adminUserCount();
	}

	//메인(통계) 월별 가입자 DATA 페이지
	@ResponseBody
	@PostMapping("/admin/ajax/adminUserSignUp")
	public int[] adminUserSignUp() {
		System.out.println("월별 가입자 수 ");
		return memberService.adminUserSignUp();
	}

} // end controller
