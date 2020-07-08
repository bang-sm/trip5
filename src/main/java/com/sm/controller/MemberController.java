package com.sm.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.sm.domain.MemberVO;
//import com.sm.service.KakaoAPI;
import com.sm.service.MemberService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	MemberService merberService;

	@ResponseBody
	@PostMapping("/ajax/idCheck")
	public int postIdCheck(String userId) throws Exception {
		logger.info("post idCheck");
		System.out.println("들어옴?");

		MemberVO idCheck = merberService.idCheck(userId);

		int result = 0;

		if (idCheck != null) {
			result = 1;
		} else if (userId.trim() == "") {
			result = 2;
		}

		return result;
	}

	// 회원가입 페이지
	@GetMapping("/user/signup")
	public String dispSignup(MemberVO memberVO) {
		logger.info("회원가입 겟 맵핑");
		return "/user/signup";
	}

	// 회원가입 처리
	@PostMapping("/user/signup")
	public String execSignup(@Valid MemberVO merberVO, Errors errors, Model model) {
		logger.info("회원가입 처리 부분");

		if (errors.hasErrors()) {
			logger.info("@Valid 유효성 에러");
			// 회원가입 실패시 입력데이터를 유지하려고 데이터 담아두기
			model.addAttribute("memberVO", merberVO);

			// 유효성 통과 못한 필드랑 메세지 핸들링을 위해서
			Map<String, String> validatorResult = merberService.validateHandling(errors);
			for (String key : validatorResult.keySet()) {
				model.addAttribute(key, validatorResult.get(key));
				return "/user/signup";
			}
		}
		// 에러도 없고 유효성도 통과하면 가입시켜!
		merberService.joinUser(merberVO);
		return "redirect:/user/login";
	}

//	 카카오로그인 페이지
//	@GetMapping("/user/kakaologin")
//	public String kakaologin(@RequestParam("code") String code, HttpSession session, HttpServletResponse response,
//			HttpServletRequest request) {
//		Cookie[] cookies = request.getCookies(); // 로그인 유지위한 쿠키
//		if (cookies == null) {
//			System.out.println("들어옴");
//			JsonNode accessToken;
//			System.out.println(session.getAttribute("username") + "하이욤");
//
//			// JsonNode트리형태로 토큰받아온다
//
//			// 여러 json객체 중 access_token을 가져온다
//
//			JsonNode jsonToken = KakaoAPI.getAccessToken(code);
//			accessToken = jsonToken.get("access_token");
//
//			System.out.println("access_token : " + accessToken);
//
//			// access_token을 통해 사용자 정보 요청
//			JsonNode userInfo = KakaoAPI.getKakaoUserInfo(accessToken);
//
//			// Get id
//			String id = userInfo.path("id").asText();
//			String name = null;
//			String email = null;
//
//			// 유저정보 카카오에서 가져오기 Get properties
//			JsonNode properties = userInfo.path("properties");
//			JsonNode kakao_account = userInfo.path("kakao_account");
//
//			name = properties.path("nickname").asText();
//			email = kakao_account.path("email").asText();
//
//			System.out.println("id : " + id);
//			System.out.println("name : " + name);
//			System.out.println("email : " + email);
//
//			if (email != null) {
//				session.setAttribute("username", email);
//				session.setAttribute("accessToken", accessToken);
//				Cookie cookie = new Cookie("username", email);
//				cookie.setMaxAge(24 * 60 * 60);
//				cookie.setPath("/");
//				cookie.setDomain("localhost");
//				response.addCookie(cookie);
//			} // end if
//		} else {
//			String cookie;
//			for (int i = 0; i < cookies.length; i++) {
//				String cookieName = cookies[i].getName();
//				if (cookieName.equals("username")) {
//					cookie = cookies[i].getValue();
//					System.out.println(cookie + "///////////////////////////////////////////");
//					session.setAttribute("username", cookie);
//				} // end if
//			} // end for
//		} // end if
//
//		return "/user/kakaologinOk";
//	} // end 카카오로그인
//
//	@GetMapping("/user/kakaologout")
//	public String kakaologout(HttpSession session) {
//		String accessToken = session.getAttribute("accessToken") + "";
//		System.out.println(accessToken);
//		KakaoAPI.kakaoLogout(accessToken);
//		session.removeAttribute("username");
//		session.removeAttribute("accessToken");
//		return "index";
//	} // end 카카오로그아웃

	// 로그인 페이지
	@GetMapping("/user/login")
	public String dispLogin() {

		return "/user/login";
	}

	// 로그인 결과 페이지
	@GetMapping("/user/login/result")
	public String dispLoginResult() {
		return "/user/loginSuccess";
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

}
