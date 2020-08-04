package com.sm.configuration;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.sm.dao.MemberDAO;
import com.sm.domain.MemberVO;
import com.sm.domain.VisitmembersVO;
import com.sm.service.MemberService;

@Service
public class MyOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {

	@Autowired
	HttpSession session;

	@Autowired
	MemberDAO memberDAO;

	@Autowired
	HttpServletResponse response;

	@Autowired
	HttpServletRequest request;

	@Override
	public void saveAuthorizedClient(OAuth2AuthorizedClient oAuth2AuthorizedClient, Authentication authentication) {
		try {

//		String providerType = oAuth2AuthorizedClient.getClientRegistration().getRegistrationId();
			OAuth2AccessToken accessToken = oAuth2AuthorizedClient.getAccessToken();
//		System.out.println(providerType + "////////////////////////providerType//////////////////////////////////");
//		System.out.println(accessToken + "/////////////////////////accessToken/////////////////////////////////");

			OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
//		System.out.println(oauth2User + "//////////////////////////oauth2User/////////////////////////////////");
			System.out.println(oauth2User.getName());
			String principalName = oAuth2AuthorizedClient.getPrincipalName();
			System.out.println(principalName + "   /// getPrincipalName");

			// 유저 이메일(아이디)
			String memberemail = ((LinkedHashMap<?, ?>) oauth2User.getAttribute("kakao_account")).get("email") + "";
//		System.out.println(memberemail + "//////////////////////////memberemail/////////////////////////////////");

			// 유저 닉네임
			String membernick = ((LinkedHashMap<?, ?>) oauth2User.getAttribute("properties")).get("nickname") + "";
			System.out.println(membernick + " membernick 유저닉네임");

			// MemberDAO
			MemberVO memberVo = new MemberVO();
//		memberVo.setMemberemail(memberemail);
			memberVo.setMemberemail(principalName);
//		memberVo.setMembernick(membernick);
			memberVo.setMembernick(principalName);
			memberVo.setMemberpass("kakaologin");
			memberVo.setKakaoOk("Y");
			// 카카오톡 토큰
			System.out.println(accessToken.getTokenValue());

			// 카카오회원가입 안되있을 때 밀어넣기
			try {
				if (memberDAO.idCheck(principalName).equals(null))
					memberDAO.kakaoJoin(memberVo);

			} catch (Exception e) {
				memberDAO.kakaoJoin(memberVo);
			}

			// uuid 값 넣기
			int uuid = memberDAO.uuidCheck(principalName);
			System.out.println(uuid);
			
			// 되어있다면
			memberVo = memberDAO.getUserById(memberemail);
		
			// 로그인 세션 저장
			session.setAttribute("userInfo", memberVo);
			session.setMaxInactiveInterval(60 * 60);

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
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date currentTime = new Date(); // 로그인한 날짜
			String current = format.format(currentTime);
			
			Map<String, Object> map = new HashMap<>(); // sql문에 필요
			map.put("uuid", uuid);
			map.put("ipaddr", ip);
			map.put("kakaoOk", "Y");
			map.put("visitdate", current);
			
			VisitmembersVO visitmembersVO = memberDAO.insertCondition(map);
			if (visitmembersVO == null) {
				System.out.println(visitmembersVO);
				memberDAO.insertUserCount(map); // 방문기록이 없었으면 insert
			} else {
				
				String visitDate = visitmembersVO.getVisitdate(); // db에 저장된 로그인 정보

				Date date1; // 전에 방문했던 날짜
				Date date2; // 방금 로그인한 날짜
				try {
					date1 = format.parse(visitDate); // 전에 방문했던 날짜
					date2 = format.parse(current); // 방금 로그인한 날짜

					// 비교
					// 전에 방문했던 날짜보다 방금 로그인한 날짜가 크면 1출력
					int result = date2.compareTo(date1);

					if (result == 1) {
						memberDAO.insertUserCount(map); // 방문기록이 없거나 날짜가 다르면 insert
					} // end inner if
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} // end if
		} catch (Exception e) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.println("<script>window.location.href = '/user/logout';</script>");
				out.flush();
			} catch (Exception er) {

			}
		}
	} // end Oauth2 시큐리티 다른 Client 로그인 탈때 중간에 나오는 거치는 곳

	@Override
	public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId,
			String principalName) {
//		System.out.println(principalName +" ///////// loadAuthorizedClient //////////");
		return null;
	}

	@Override
	public void removeAuthorizedClient(String clientRegistrationId, String principalName) {

	}

}
