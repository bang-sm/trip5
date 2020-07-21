package com.sm.controller;

import java.util.Map;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
			if (!(idCheck.getMemberemail().equals("null"))) {
				result = 1; // 기본아이디
			} else {
				result = 0;// end if
			}
		} else if (memberemail.trim().equals("")) {
			result = 2;
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

		} else if (Integer.parseInt(idCheckNum) == 3) { // 카카오로그인된 아이디
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
		merberService.joinUser(memberVO);
		return "redirect:/user/login";

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

	// 어드민 페이지
	@GetMapping("/admin")
	public String dispAdmin() {
		return "/admin";
	}

	// 관리자 페이지 자료들 불러올 때
	@PostMapping("/admin/ajax/userCnt")
	public String[] userCnt() {

		return null;
	}

	// 인증번호 -------------------------------------------------------------------
	@Autowired
	private JavaMailSender mailSender;

	@ResponseBody
	@PostMapping("/user/authEmail.do")
	public String sendEmailAction(String email)
			throws Exception {

		System.out.println(email+"ㅋ");
		
		String EMAIL = email;
        Random r = new Random();
        int dice = r.nextInt(4589362) + 49311; //이메일로 받는 인증코드 부분 (난수)
        
		try {
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");

			messageHelper.setSubject( "안녕하세요 회원님 TRIP5 홈페이지를 찾아주셔서 감사합니다"); // 메일제목은 생략이 가능하다
			messageHelper.setText("인증번호는 " + dice + " 입니다.");
			messageHelper.setTo(EMAIL);
			msg.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(EMAIL));
			mailSender.send(msg);

		} catch (MessagingException e) {
			System.out.println("MessagingException");
			e.printStackTrace();
		}
		
		return dice +"";
	}

} // end controller
