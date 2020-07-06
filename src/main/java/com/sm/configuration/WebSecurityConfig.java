package com.sm.configuration;

import static com.sm.domain.SocialType.KAKAO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@EnableConfigurationProperties(OAuth2ClientProperties.class)
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/oauth2/**")
				.permitAll()
				.antMatchers("/kakao").hasAuthority(KAKAO.getRoleType())
				.anyRequest()
				.authenticated()
			.and()
				.oauth2Login();
	}
	//custom.oauth2.kakao.client-id=2d3554ca6fbdc8ff7fdc4b74f4b28dd8
	//custom.oauth2.kakao.client-secret=lI1VIyD61p6Ej0ZadJ7dt9iKuIoExRnZ
	@Bean
	@ConditionalOnMissingBean(ClientRegistrationRepository.class)
	public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties oAuth2ClientProperties) {
		
		String kakaoClientId="2d3554ca6fbdc8ff7fdc4b74f4b28dd8";
		String kakaoClientSecret="lI1VIyD61p6Ej0ZadJ7dt9iKuIoExRnZ";
		
		List<ClientRegistration> registrations = oAuth2ClientProperties.getRegistration().keySet().stream()
				.map(client -> getRegistration(oAuth2ClientProperties, client)).filter(Objects::nonNull)
				.collect(Collectors.toList());
		registrations.add(CustomOAuthProvider.KAKAO.getBuilder("kakao").clientId(kakaoClientId)
				.clientSecret(kakaoClientSecret).jwkSetUri("temp").build());
		
		return new InMemoryClientRegistrationRepository(registrations);
	}

	private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String client) {
		
		return null;
	}

}
