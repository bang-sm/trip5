package com.sm.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.sm.service.MemberService;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MemberService memberService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http    	
		.authorizeRequests()
			.antMatchers("/admin/**").hasRole("ADMIN") // /admin 으로 시작하는 경로는 ADMIN 롤을 가진 사용자만 접근 가능합니다.
	        .antMatchers("/user/myinfo").hasRole("MEMBER") // /user/myinfo 경로는 MEMBER 롤을 가진 사용자만 접근 가능합니다
	        .antMatchers("/**").permitAll()
	        .anyRequest().permitAll()
        .and()
	        .formLogin()
	        .loginPage("/user/login")  //로그인페이지
	        .defaultSuccessUrl("/index") // 성공했을때 이동되는 페이지
	        .usernameParameter("username")	// 로그인시 파라미터로 "id", "password"를 받습니다
	        .passwordParameter("password")	//
	        .permitAll()
        .and()
	        .logout()
	        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
	        .logoutSuccessUrl("/index")
	        .invalidateHttpSession(true)  //세션초기화
	    .and()
	    	//403예외
        	.exceptionHandling().accessDeniedPage("/user/denied")
        .and()
        	.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()	
        	.csrf().ignoringAntMatchers("/travel/**")
        .and()
        	.rememberMe()
        	.rememberMeParameter("remember-me")
        	.tokenValiditySeconds(604800)
        .and()
        	.csrf().ignoringAntMatchers("/ajax/idCheck");
		
		

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
