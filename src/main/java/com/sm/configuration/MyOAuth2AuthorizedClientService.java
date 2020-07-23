package com.sm.configuration;

import java.io.PrintWriter;
import java.util.LinkedHashMap;

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

@Service
public class MyOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {

	@Autowired
	HttpSession session;

	@Autowired
	MemberDAO memberDAO;
	
	@Autowired
	HttpServletResponse response;

	@Override
	public void saveAuthorizedClient(OAuth2AuthorizedClient oAuth2AuthorizedClient, Authentication authentication) {
		try {

//			String providerType = oAuth2AuthorizedClient.getClientRegistration().getRegistrationId();
			OAuth2AccessToken accessToken = oAuth2AuthorizedClient.getAccessToken();
//		System.out.println(providerType + "////////////////////////providerType//////////////////////////////////");
//		System.out.println(accessToken + "/////////////////////////accessToken/////////////////////////////////");

			OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
//		System.out.println(oauth2User + "//////////////////////////oauth2User/////////////////////////////////");
			System.out.println(oauth2User.getName());
			String principalName = oAuth2AuthorizedClient.getPrincipalName();
			System.out.println(principalName + "   /// getPrincipalName");

			// 유저 이메일(아이디)
//			String memberemail = ((LinkedHashMap<?, ?>) oauth2User.getAttribute("kakao_account")).get("email") + "";
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

			// 카카오로그인이 안되있을 때 밀어넣기
//			if(memberDAO.idCheck(memberemail) == null){
//				memberDAO.kakaoJoin(memberVo);
//			}else {
//				// 카카오로그인 Y 넣기	
//				memberDAO.kakaoOk(memberemail);
//			} // end if
			System.out.println("야야야야");
			try {
				if(memberDAO.idCheck(principalName).equals(null))
					memberDAO.kakaoJoin(memberVo);
				
			} catch (Exception e) {
				memberDAO.kakaoJoin(memberVo);
			}
			// 로그인 세션 저장
			session.setAttribute("userInfo", memberVo);
			session.setMaxInactiveInterval(60 * 60);
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
