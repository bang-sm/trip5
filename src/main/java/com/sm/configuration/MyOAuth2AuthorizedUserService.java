package com.sm.configuration;
  
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
/**
 * 소셜로 로그인 한 후 처리
 */
@Component
public class MyOAuth2AuthorizedUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {
    
	
	@Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        Assert.notNull(userRequest, "userRequest cannot be null");
        OidcUserInfo userInfo = null;
        Set<GrantedAuthority> authorities = setGrantedAuthorities(userRequest, userInfo);
        
        OidcUser user;
		
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        
        System.out.println(userNameAttributeName);
        if (StringUtils.hasText(userNameAttributeName)) {
            user = new DefaultOidcUser(authorities, userRequest.getIdToken(), userInfo, userNameAttributeName);
        } else {
            user = new DefaultOidcUser(authorities, userRequest.getIdToken(), userInfo);
        }
        return user;
    }

    private Set<GrantedAuthority> setGrantedAuthorities(OidcUserRequest userRequest, OidcUserInfo userInfo) {
        Set<GrantedAuthority> authorities = new LinkedHashSet<>();
        authorities.add(new OidcUserAuthority(userRequest.getIdToken(), userInfo));
        
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        
        return authorities;
    }
}
