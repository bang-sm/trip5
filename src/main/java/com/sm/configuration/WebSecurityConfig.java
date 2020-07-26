package com.sm.configuration;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.sm.service.MemberService;

@EnableConfigurationProperties(OAuth2ClientProperties.class)
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	final static String kakaoClientId="2d3554ca6fbdc8ff7fdc4b74f4b28dd8";
	final static String kakaoClientSecret="lI1VIyD61p6Ej0ZadJ7dt9iKuIoExRnZ";
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private DataSource dataSource;
	
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
				"/favicon.ico/**"
				)
			.permitAll()
			.anyRequest()
			.authenticated()
        .and()
        	.formLogin()
        	.loginPage("/user/login")  //로그인페이지
        	.defaultSuccessUrl("/index") // 성공했을때 이동되는 페이지
        	.usernameParameter("memberid")	//로그인시 파라미터로 "id", "password"를 받습니다
        	.passwordParameter("password")
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
	    	.csrf().ignoringAntMatchers("/user/ajax/idCheck")
    	.and()
	    	.csrf().ignoringAntMatchers("/weather/**")
		.and()
			.csrf().ignoringAntMatchers("/user/login")
		.and()	
			.csrf().ignoringAntMatchers("/travel/**")
		.and()
			.csrf().ignoringAntMatchers("/my/**")
		.and()
			.csrf().ignoringAntMatchers("/wish/**")
		.and()
			.csrf().ignoringAntMatchers("/user/authEmail.do")	
		.and()
			.csrf().ignoringAntMatchers("/adminNotice/ajax/**")	
		.and()
			.oauth2Login()	// Oauth2 로그인
		.and()
			.rememberMe()
			 .userDetailsService(memberService)
             .tokenRepository(tokenRepository()); // username, 토큰, 시리즈를 조합한 토큰 정보를 DB에 저장(rememberMe 쿠키랑 일치하는 지 확인하기 위함)
			;
       
        http.sessionManagement()
        	.invalidSessionUrl("/user/sessionExpire")
        	//유효하지 않은 세션으로 접근했을때 어디로 보낼것인지 URL을 설정하는 기능.
        	//로그아웃 했을경우 세션을 만료시킨다.
        	.maximumSessions(1) // 최대 세션 1로 유지
        	.maxSessionsPreventsLogin(true) // 중복로그인시 이전 로그인했던 세션 만료.
        	.expiredUrl("/user/sessionExpire")	// 중복 로그인시 타는 url
         	;
        									  
	}
	
    // tokenRepository의 구현체
    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }
	
	@Bean
	@ConditionalOnMissingBean(ClientRegistrationRepository.class)
	public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties oAuth2ClientProperties) {
		
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
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**","/chatting/**","/ws/**");
    }
    
    
    /**
     *프링 시큐리티에서 모든 인증은 AuthenticationManager를 통해 이루어진다.
     *로그인 처리 , 인증을 위해서는 userDetailsService를 통해서 필요한 정보들을 가져온다
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
    
    
    
    /*
     * 자기 자신이 로그인하고 재로그인시 로그인 안되는 문제 해결
     */
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }// Register HttpSessionEventPublisher

    @Bean
    public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }

}



