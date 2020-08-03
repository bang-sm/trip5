package com.sm.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.sm.dao.MemberDAO;
import com.sm.domain.MemberVO;
import com.sm.domain.Role;
import com.sm.domain.VisitmembersVO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

	@Autowired
	MemberDAO memberDAO;

	@Autowired
	HttpServletRequest request;

	@Autowired
	private JavaMailSender mailSender;

	////////////////////////////////////////////////////////////////////////////////
	// 관리자 페이지
	////////////////////////////////////////////////////////////////////////////////
	// 일일접속자 Count
	public int[] adminUserCount() {
		 System.out.println(Integer.parseInt((memberDAO.adminUserCount().get("KAKAO")+"")));
		 int[] adminUserCount = new int[] {
				 Integer.parseInt((memberDAO.adminUserCount().get("KAKAO")+"")),
				 Integer.parseInt((memberDAO.adminUserCount().get("TRIP5")+""))
		 };
		return adminUserCount;
	}

	public int[] adminUserSignUp() {
		System.out.println(memberDAO.adminUserSignUp()  + "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
		List<Map<String, Object>> data = memberDAO.adminUserSignUp();
		int[] adminUserSignup = new int[data.size()];
		for (int i = 0; i < data.size(); i++) {
			adminUserSignup[i] = Integer.parseInt(data.get(i).get("cnt")+"");
		}
		System.out.println(adminUserSignup);
		return adminUserSignup;
	}
	

	// 회원가입한 유저 타입
	public ModelMap userTpye() {
		ModelMap modelMap = new ModelMap();
		System.out.println(memberDAO.userType());
		modelMap.put("userType", memberDAO.userType());
		return modelMap;
	}

	// 블랙리스트 추가
	public void addBlackList(String uuid, String msgBlackList) {
		Map<String, Object> map = new HashMap<>();
		map.put("uuid", Integer.parseInt(uuid));
		map.put("msgBlackList", msgBlackList);
		memberDAO.addBlackList(map);
	}

	// 관리자 유저 페이징
	public ModelMap memberList(int currentPage) {
		ModelMap modelMap = new ModelMap();

		// 페이지에 보여줄 행의 개수 ROW_PER_PAGE = 10으로 고정
		final int ROWPERPAGE = 15;

		// 페이지에 보여줄 첫번째 페이지 번호는 1로 초기화
		int startPageNum = 1;

		// 처음 보여줄 마지막 페이지 번호는 10
		int lastPageNum = ROWPERPAGE;

		// 현재 페이지가 ROW_PER_PAGE/2 보다 클 경우
		if (currentPage > (ROWPERPAGE / 2)) {
			// 보여지는 페이지 첫번째 페이지 번호는 현재페이지 - ((마지막 페이지 번호/2) -1 )
			// ex 현재 페이지가 6이라면 첫번째 페이지번호는 2
			startPageNum = currentPage - ((lastPageNum / 2) - 1);
			// 보여지는 마지막 페이지 번호는 현재 페이지 번호 + 현재 페이지 번호 - 1
			lastPageNum += (startPageNum - 1);
		}

		// Map Data Type 객체 참조 변수 map 선언
		// HashMap() 생성자 메서드로 새로운 객체를 생성, 생성된 객체의 주소값을 객체 참조 변수에 할당
		Map<String, Integer> map = new HashMap<String, Integer>();
		// 한 페이지에 보여지는 첫번째 행은 (현재페이지 - 1) * 10
		int startRow = (currentPage - 1) * ROWPERPAGE;
		// 값을 map에 던져줌
		map.put("startRow", startRow);
		map.put("rowPerPage", ROWPERPAGE);

		// DB 행의 총 개수를 구하는 getBoardAllCount() 메서드를 호출하여 double Date Type의 boardCount 변수에
		// 대입
		double count = memberDAO.userCnt();

		// 마지막 페이지번호를 구하기 위해 총 개수 / 페이지당 보여지는 행의 개수 -> 올림 처리 -> lastPage 변수에 대입
		int lastPage = (int) (Math.ceil(count / ROWPERPAGE));

		// 현재 페이지가 (마지막 페이지-4) 보다 같거나 클 경우
		if (currentPage >= (lastPage - 4)) {
			// 마지막 페이지 번호는 lastPage
			lastPageNum = lastPage;
		}

		int countMembers = memberDAO.userCnt();

		modelMap.put("list", memberDAO.memberList(map));
		modelMap.put("currentPage", currentPage);
		modelMap.put("lastPage", lastPage);
		modelMap.put("startPageNum", startPageNum);
		modelMap.put("lastPageNum", lastPageNum);
		modelMap.put("count", countMembers);

		return modelMap;
	}

	////////////////////////////////////////////////////////////////////////
	// 회원가입 시, 유효성 체크
	///////////////////////////////////////////////////////////////////////
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
	public MemberVO idCheck(String memberemail) throws Exception {
		return memberDAO.idCheck(memberemail);
	}

	public int idCheckNum(String memberemail, MemberVO idCheck) throws Exception {
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

	/////////////////////////////////////////////////////////////////////////
	// 로그인 할때 타는 서비스
	/////////////////////////////////////////////////////////////////////////
	/**
	 *
	 */
	@Override
	public UserDetails loadUserByUsername(String memberemail) throws UsernameNotFoundException {
		// System.out.println("넘어온 아이디 "+ memberemail);

		MemberVO user = memberDAO.getUserById(memberemail);
		List<GrantedAuthority> auth = new ArrayList<>();
		if (user == null) {
			System.out.println("들어왔니??");
			throw new UsernameNotFoundException("User Not Found");
		} else {
			HttpSession session = request.getSession(true);
			session.setAttribute("userInfo", user);
			session.setMaxInactiveInterval(60 * 60);
		}

		/*
		 * 아이디가 admin 인 계정에는 관리자권한 아니면 일반 멤버 권한
		 */
		if (("admin").equals(user.getMemberemail())) {
			auth.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
		} else {
			auth.add(new SimpleGrantedAuthority(Role.USER.getValue()));
		}

		///////////////////////////////////////////////////////////////////////////////////
		// 방문자수
		///////////////////////////////////////////////////////////////////////////////////
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

		// 아이피 가져오기
		System.out.println(ip + "아이피입니다.");

		int uuid = user.getUuid();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date currentTime = new Date (); // 로그인한 날짜
		String current = format.format(currentTime);
		
		Map<String, Object> map = new HashMap<>();	// sql문에 필요
		map.put("uuid", uuid);
		map.put("ipaddr", ip);
		map.put("kakaoOk", "N");
		map.put("visitdate", current);
		
		VisitmembersVO visitmembersVO = memberDAO.insertCondition(map);
		if (visitmembersVO == null) {
			System.out.println(visitmembersVO);
			memberDAO.insertUserCount(map);	// 방문기록이 없었으면 insert
		} else {
			
			String visitDate = visitmembersVO.getVisitdate(); // db에 저장된 로그인 정보
			

			
			Date date1;	// 전에 방문했던 날짜
			Date date2; // 방금 로그인한 날짜
			try {
				date1 = format.parse(visitDate);	// 전에 방문했던 날짜
				date2 = format.parse(current);		// 방금 로그인한 날짜
				
				// 비교
				// 전에 방문했던 날짜보다 방금 로그인한 날짜가 크면 1출력
				int result = date2.compareTo(date1);
				
				if(result == 1) {
					memberDAO.insertUserCount(map);	// 방문기록이 없거나 날짜가 다르면 insert
				}  // end inner if
			} catch (ParseException e) {
				e.printStackTrace();
			}
			

		} // end if

		

		return new User(user.getMemberemail(), user.getMemberpass(), auth);
	} // end UserDetails

	
	
	// 이메일 인증
	public String sendEmail(String email) {

		String EMAIL = email;
		Random r = new Random();
		int dice = r.nextInt(4589362) + 49311; // 이메일로 받는 인증코드 부분 (난수)

		try {
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");

			messageHelper.setSubject("안녕하세요 회원님 TRIP5 홈페이지를 찾아주셔서 감사합니다"); // 메일제목은 생략이 가능하다
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
