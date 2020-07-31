package com.sm.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		String ip = null;
	      ip = request.getHeader("X-Forwarded-For");
	        
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("Proxy-Client-IP"); 
	        } 
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("WL-Proxy-Client-IP"); 
	        } 
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("HTTP_CLIENT_IP"); 
	        } 
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("X-Real-IP"); 
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("X-RealIP"); 
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("REMOTE_ADDR");
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getRemoteAddr(); 
	        }
	         
		System.out.println(ip + "아이피입니다.");
		
		System.out.println(request.getRemoteAddr()+ " ddddddddddddddd");
		System.out.println(request.getHeader("User-Agent"));
		System.out.println(request.getHeader("referer"));
		
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

} // end controller
