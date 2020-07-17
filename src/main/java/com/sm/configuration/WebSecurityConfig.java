package com.sm.configuration;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.sm.service.MemberService;

@EnableConfigurationProperties(OAuth2ClientProperties.class)
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MemberService memberService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        	.antMatchers("/",
        		"/oauth2/**", 
				"/user/**", 
				"/css/**", 
				"/image/**", 
				"/js/**", 
				"/console/**", 
				"/favicon.ico/**",
m				"/chatting/**",
				"/weather/**")
			.permitAll()
			.anyRequest()
			.authenticated()
        .and()
        	.formLogin()
        	.loginPage("/user/login")  //로그인페이지
        	.defaultSuccessUrl("/index") // 성공했을때 이동되는 페이지
        	.usernameParameter("memberid")	//로그인시 파라미터로 "id", "password"를 받습니다
        	.passwordParameter("password")
        	.failureUrl("/user/login?error")
        	.permitAll()
        .and()
	        .logout()
	        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
	        .logoutSuccessUrl("/index")		// 성공했을때 이동되는 페이지
	        .invalidateHttpSession(true) 	//세션초기화
	    .and()
	    	//403예외
	    	.exceptionHandling().accessDeniedPage("/user/denied")
	    .and()
	    	.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
	    .and()
	    	.rememberMe()
	    	.rememberMeParameter("remember-me")
	    	.tokenValiditySeconds(604800)
	    .and()
	    	.csrf().ignoringAntMatchers("/user/ajax/idCheck")
		.and()
			.csrf().ignoringAntMatchers("/user/login")
		.and()	
			.csrf().ignoringAntMatchers("/travel/**")
		.and()
			.csrf().ignoringAntMatchers("/my/**")	
		.and()
			.csrf().ignoringAntMatchers("/weather/**")	
		.and()

			.csrf().ignoringAntMatchers("/black/**")	
		.and()
			.csrf().ignoringAntMatchers("/wish/**")	
		.and()
			.oauth2Login()
//			.successHandler()
			.loginPage("/user/login");
		//		.antMatchers("/kakao")
		// .hasAuthority(KAKAO.getRoleType())
//			.oauth2Login()	// Oauth2 로그인
//			;
        http.sessionManagement()
        	.invalidSessionUrl("/user/login")
        	//유효하지 않은 세션으로 접근했을때 어디로 보낼것인지 URL을 설정하는 기능.
        	//로그아웃 했을경우 세션을 만료시킨다.
        	.maximumSessions(1) // 최대 세션 1로 유지
        	.maxSessionsPreventsLogin(false) // 중복로그인시 이전 로그인했던 세션 만료.
        	;
	}
	
	
	@Bean
	@ConditionalOnMissingBean(ClientRegistrationRepository.class)
	public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties oAuth2ClientProperties) {
		
		String kakaoClientId="2d3554ca6fbdc8ff7fdc4b74f4b28dd8";
		String kakaoClientSecret="lI1VIyD61p6Ej0ZadJ7dt9iKuIoExRnZ";
		
		List<ClientRegistration> registrations = oAuth2ClientProperties.getRegistration().keySet().stream()
				.map(client -> getRegistration(oAuth2ClientProperties, client)).filter(Objects::nonNull)
				.collect(Collectors.toList());
		registrations.add(CustomOAuthProvider.KAKAO.getBuilder("kakao")
				.clientId(kakaoClientId)
				.clientSecret(kakaoClientSecret)
				.jwkSetUri("temp")
				.build());
		
		return new InMemoryClientRegistrationRepository(registrations);
	}

	private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String client) {
		
		return null;
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
    @Override
    public void configure(WebSecurity web) throws Exception{
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }
    
    
    /**
     *프링 시큐리티에서 모든 인증은 AuthenticationManager를 통해 이루어진다.
     *로그인 처리 , 인증을 위해서는 userDetailsService를 통해서 필요한 정보들을 가져온다
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
    

}



