package com.sm.configuration;

import java.util.LinkedHashMap;

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


/**
 * Created by momentjin@gmail.com on 2019-12-11 Github :
 * http://github.com/momentjin
 */

@Service
public class MyOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {
	@Autowired
	HttpSession session;
	
	@Autowired
	MemberDAO memberDAO;
	
	@Override
	public void saveAuthorizedClient(OAuth2AuthorizedClient oAuth2AuthorizedClient, Authentication authentication) {
		String providerType = oAuth2AuthorizedClient.getClientRegistration().getRegistrationId();
		OAuth2AccessToken accessToken = oAuth2AuthorizedClient.getAccessToken();
		System.out.println(providerType + "//////////////////////////////////////////////////////////");
		System.out.println(accessToken + "//////////////////////////////////////////////////////////");
		
		
		OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
		System.out.println(oauth2User + "///////////////////////////////////////////////////////////");
		System.out.println(oauth2User.getName());
		
		// 유저 이메일(아이디)
		String memberemail = ((LinkedHashMap<?, ?>)oauth2User.getAttribute("kakao_account")).get("email") + "";
		System.out.println(memberemail + "///////////////////");
		
		// 유저 닉네임
		String membernick = ((LinkedHashMap<?, ?>)oauth2User.getAttribute("properties")).get("nickname") + "";
		System.out.println(membernick);
		
		// MemberDAO
		MemberVO memberVo = new MemberVO();
		memberVo.setMemberemail(memberemail);
		memberVo.setMembernick(membernick);
		memberVo.setMemberpass("kakaologin");
		
		// 카카오톡 토큰
		System.out.println(accessToken.getTokenValue());
		
		// 카카오로그인이 안되있을 때 밀어넣기
		try {
			if(memberDAO.idCheck(memberemail) == null){
				memberDAO.join(memberVo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 로그인 세션 저장
		session.setAttribute("userInfo", memberVo.getMemberemail());
		
	} // end Oauth2 시큐리티 다른 Client 로그인 탈때 중간에 나오는 거치는 곳
	
	@Override
	public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId,
			String principalName) {
		return null;
	}
	
	@Override
	public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
		
	}

}