package com.sm.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sm.dao.MemberDAO;
import com.sm.domain.MemberVO;
//import com.sm.service.KakaoAPI;
import com.sm.service.MemberService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	MemberDAO memberDAO;

	@Autowired
	MemberService memberService;

	// 아이디 중복체크
	@ResponseBody
	@PostMapping("/user/ajax/idCheck")
	public int postIdCheck(String memberemail) throws Exception {
		// 아이디 유무 값 저장
		MemberVO idCheck = memberService.idCheck(memberemail);
		System.out.println(memberemail);

		int result = memberService.idCheckNum(memberemail, idCheck);

		return result;
	} // end poasIdCheck

	// 회원가입 페이지
	@GetMapping("/user/signup")
	public String dispSignup(MemberVO memberVO) {
		logger.info("회원가입 겟 맵핑");
		return "/user/signup";
	}

	// 회원가입 처리
	@PostMapping("/user/signup")
	public String execSignup(@Valid MemberVO memberVO, Errors errors, Model model, String idCheckNum) {
		logger.info("회원가입 처리 부분");
		System.out.println(errors.hasErrors());
		System.out.println(idCheckNum);

		String result = memberService.signupAndValid(memberVO, errors, model, idCheckNum);
		 
		return result; 
	}

	// 로그인 페이지
	@GetMapping("/user/login")
	public String dispLogin() {
		System.out.println("dispLogin 들어옴");

		return "/user/login";
	}

	// 로그인 페이지
	@GetMapping("/user/sessionExpire")
	public String sessionExpireLogout() {
		System.out.println("sessionExpireLogout 들어옴");

		return "/user/sessionExpire";
	}

	// 접근 거부 페이지
	@GetMapping("/user/denied")
	public String dispDenied() {
		return "/user/denied";
	}

	// 내 정보 페이지
	@GetMapping("/user/info")
	public String dispMyInfo(HttpSession session, Model model) {

		model.addAttribute("member", session.getAttribute("userInfo"));

		return "/user/myinfo";
	}

	// 내 개인정보 페이지
	@GetMapping("/user/inform")
	public String dispMyInform(HttpSession session, Model model) {

		model.addAttribute("member", session.getAttribute("userInfo"));

		return "/user/inform";
	}
	
	/////////////////////////////////////////////////////////////////
	// 프로필 사진 변경
	/////////////////////////////////////////////////////////////////
	@PostMapping("/user/chageImg")
	public String chageImg(HttpSession session, Model model) {
		
		return null;
	}

	
	
	
	
	
	//--------------------------------------------------------------------------
	// 인증번호 -------------------------------------------------------------------
	//--------------------------------------------------------------------------
	@ResponseBody
	@PostMapping("/user/authEmail.do")
	public String sendEmailAction(String email)
			throws Exception {
		
		String authNum = memberService.sendEmail(email);
		
		return authNum;
	}

} // end controller
