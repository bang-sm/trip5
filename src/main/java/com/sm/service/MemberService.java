package com.sm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.sm.dao.MemberDAO;
import com.sm.domain.MemberVO;
import com.sm.domain.Role;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService{
	
	@Autowired
	MemberDAO memberDAO;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	private JavaMailSender mailSender;
	
	// 회원가입 시, 유효성 체크
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }
	
    @Transactional
    public void joinUser(MemberVO memberVO) {
        // 비밀번호 암호화
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		memberVO.setMemberpass(passwordEncoder.encode(memberVO.getMemberpass()));

        memberDAO.join(memberVO);
    }
    
    // 아이디 중복체크
    public MemberVO idCheck(String memberemail) throws Exception{
    	return memberDAO.idCheck(memberemail);
    }
    
    public int idCheckNum(String memberemail, MemberVO idCheck) throws Exception{
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
    } 
    // 아이디 중복체크 끝
    
    // 회원가입 처리
    public String signupAndValid(MemberVO memberVO, Errors errors, Model model, String idCheckNum) {
    	MemberService memberService = new MemberService(memberDAO, request, mailSender);
    	
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
			System.out.println("hasErrosr 들어옴");

			// 회원가입 실패시 입력데이터를 유지하려고 데이터 담아두기
			model.addAttribute("memberVO", memberVO);

			// 유효성 통과 못한 필드랑 메세지 핸들링을 위해서
			Map<String, String> validatorResult = memberService.validateHandling(errors);
			for (String key : validatorResult.keySet()) {
				model.addAttribute(key, validatorResult.get(key));
			} // end for

			return "/user/signup";
		} // end if

		// 에러도 없고 유효성도 통과하면 가입시켜!
		memberService.joinUser(memberVO);
		return "redirect:/user/login";
    }
    
    //로그인 할때 타는 서비스
	@Override
	public UserDetails loadUserByUsername(String memberemail) throws UsernameNotFoundException {
		//System.out.println("넘어온 아이디 "+ memberemail);
		
		MemberVO user= memberDAO.getUserById(memberemail);
		List<GrantedAuthority> auth=new ArrayList<>();
		if(user==null) {
			System.out.println("들어왔니??");
			throw new UsernameNotFoundException("User Not Found");
		}
		else {
			HttpSession session=request.getSession(true);
			session.setAttribute("userInfo", user);
			session.setMaxInactiveInterval(60*60);
//			System.out.println(user+"/////////////////////////////////////////////////////////////////////////////");
		}
		
		/*
		 * 아이디가 admin 인 계정에는 관리자권한 아니면 일반 멤버 권한
		 */
		if(("admin").equals(user.getMemberemail())) {
			auth.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
		}else {
			auth.add(new SimpleGrantedAuthority(Role.USER.getValue()));
		}
		
		return new User(user.getMemberemail(), user.getMemberpass(),auth);
	} // end UserDetails
	
	// 이메일 인증
	public String sendEmail(String email) {
		
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
		
		return dice + "";
	}
}
