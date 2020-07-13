package com.sm.controller;

import java.util.Map;

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
	MemberService merberService;

	// 아이디 중복체크
	@ResponseBody
	@PostMapping("/user/ajax/idCheck")
	public int postIdCheck(String memberemail) throws Exception {
		// 아이디 유무 값 저장
		MemberVO idCheck = merberService.idCheck(memberemail);
		System.out.println(memberemail);

		// 이메일 정규식
		String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
		System.out.println(memberemail.matches(regex));

		// 결과값
		int result = 0;

		// 아이디가 담겨있으면 1,3
		if (idCheck != null) {
			if (idCheck.getKakaoOk().trim().equals('Y' + "")) {
				result = 3; // 카카오로그인 아이디
			} else if (!(idCheck.getMemberemail().equals("null"))) {
				result = 1; // 기본아이디
			} else {
				result = 0;// end if
			}
		} else if (memberemail.trim().equals("")) {
			result = 2;
		} else if (!(memberemail.matches(regex))) {
			result = 4;
		} // end if

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

		// 에러메세지 저장 부분
		if (Integer.parseInt(idCheckNum) != 0) { // 중복체크 에러메시지
			model.addAttribute("valid_memberemail", "중복체크해주세요");
			return "/user/signup";

		}else if (Integer.parseInt(idCheckNum) == 3) { // 카카오로그인된 아이디
			if (memberVO.getMemberemail() != null && memberVO.getKakaoOk().equals("Y")) {
				model.addAttribute("valid_memberemail", "카카오로그인된 아이디 입니다.");
				System.out.println("getMemberemail 들어옴");
			}
			return "/user/signup";
		} else if (errors.hasErrors()) { // 벨리드 어노테이션 에러메시지 있을 때
			logger.info("@Valid 유효성 에러");
			System.out.println("hasErrosr 들어옴");

			// 회원가입 실패시 입력데이터를 유지하려고 데이터 담아두기
			model.addAttribute("memberVO", memberVO);

			// 유효성 통과 못한 필드랑 메세지 핸들링을 위해서
			Map<String, String> validatorResult = merberService.validateHandling(errors);
			for (String key : validatorResult.keySet()) {
				model.addAttribute(key, validatorResult.get(key));
			} // end for

			return "/user/signup";
		} // end if


	// 에러도 없고 유효성도 통과하면 가입시켜!
	merberService.joinUser(memberVO);return"redirect:/user/login";

	}

	// 로그인 페이지
	@GetMapping("/user/login")
	public String dispLogin() {
		System.out.println("들어옴");

		return "/user/login";
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

	// 어드민 페이지
	@GetMapping("/admin")
	public String dispAdmin() {
		return "/admin";
	}

} // end controller

//카카오로그인 페이지
//@GetMapping("/user/kakaologin")
//public String kakaologin(@RequestParam("code") String code, HttpSession session, HttpServletResponse response,
//		HttpServletRequest request) {
//	Cookie[] cookies = request.getCookies(); // 로그인 유지위한 쿠키
//	if (cookies == null) {
//		System.out.println("들어옴");
//		JsonNode accessToken;
//		System.out.println(session.getAttribute("username") + "하이욤");
//
//		// JsonNode트리형태로 토큰받아온다
//
//		// 여러 json객체 중 access_token을 가져온다
//
//		JsonNode jsonToken = KakaoAPI.getAccessToken(code);
//		accessToken = jsonToken.get("access_token");
//
//		System.out.println("access_token : " + accessToken);
//
//		// access_token을 통해 사용자 정보 요청
//		JsonNode userInfo = KakaoAPI.getKakaoUserInfo(accessToken);
//
//		// Get id
//		String id = userInfo.path("id").asText();
//		String name = null;
//		String email = null;
//
//		// 유저정보 카카오에서 가져오기 Get properties
//		JsonNode properties = userInfo.path("properties");
//		JsonNode kakao_account = userInfo.path("kakao_account");
//
//		name = properties.path("nickname").asText();
//		email = kakao_account.path("email").asText();
//
//		System.out.println("id : " + id);
//		System.out.println("name : " + name);
//		System.out.println("email : " + email);
//
//		if (email != null) {
//			session.setAttribute("username", email);
//			session.setAttribute("accessToken", accessToken);
//			Cookie cookie = new Cookie("username", email);
//			cookie.setMaxAge(24 * 60 * 60);
//			cookie.setPath("/");
//			cookie.setDomain("localhost");
//			response.addCookie(cookie);
//		} // end if
//	} else {
//		String cookie;
//		for (int i = 0; i < cookies.length; i++) {
//			String cookieName = cookies[i].getName();
//			if (cookieName.equals("username")) {
//				cookie = cookies[i].getValue();
//				System.out.println(cookie + "///////////////////////////////////////////");
//				session.setAttribute("username", cookie);
//			} // end if
//		} // end for
//	} // end if
//
//	return "/user/kakaologinOk";
//} // end 카카오로그인
//
//@GetMapping("/user/kakaologout")
//public String kakaologout(HttpSession session) {
//	String accessToken = session.getAttribute("accessToken") + "";
//	System.out.println(accessToken);
//	KakaoAPI.kakaoLogout(accessToken);
//	session.removeAttribute("username");
//	session.removeAttribute("accessToken");
//	return "index";
//} // end 카카오로그아웃
