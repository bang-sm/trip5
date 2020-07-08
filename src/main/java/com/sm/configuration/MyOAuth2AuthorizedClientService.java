package com.sm.configuration;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.sm.dao.MemberDAO;


/**
 * Created by momentjin@gmail.com on 2019-12-11 Github :
 * http://github.com/momentjin
 */

@Service
public class MyOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {
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
		String useremail = ((LinkedHashMap<?, ?>)oauth2User.getAttribute("kakao_account")).get("email") + "";
		System.out.println(useremail);
		
		// 카카오톡 토큰
		System.out.println(accessToken.getTokenValue());
//		if(memberDAO.kakaoCheck(oauth2User.get)  == null){
//			memberDAO.jjjj
//		}
				
	}
	
	@Override
	public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId,
			String principalName) {
		return null;
	}
	
	@Override
	public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
		
	}

}