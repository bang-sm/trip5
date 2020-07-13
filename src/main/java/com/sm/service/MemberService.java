package com.sm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.sm.dao.MemberDAO;
import com.sm.domain.MemberVO;
import com.sm.domain.Role;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService{
	
	@Autowired
	MemberDAO memberDAO;
	
	@Autowired
	HttpServletRequest request;
	
	// 회원가입 시, 유효성 체크
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
    public MemberVO idCheck(String memberemail) throws Exception{
    	return memberDAO.idCheck(memberemail);
    }
    
    //로그인 할때 타는 서비스
	@Override
	public UserDetails loadUserByUsername(String memberemail) throws UsernameNotFoundException {
		//System.out.println("넘어온 아이디 "+ memberemail);
		
		MemberVO user= memberDAO.getUserById(memberemail);
		List<GrantedAuthority> auth=new ArrayList<>();
		if(user==null) {
			System.out.println("들어왔니??");
			throw new UsernameNotFoundException("User Not Found");
		}
		else {
			HttpSession session=request.getSession(true);
			session.setAttribute("userInfo", user);
			System.out.println(user+"/////////////////////////////////////////////////////////////////////////////");
		}
		
		/*
		 * 아이디가 admin 인 계정에는 관리자권한 아니면 일반 멤버 권한
		 */
		if(("admin").equals(user.getMemberemail())) {
			auth.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
		}else {
			auth.add(new SimpleGrantedAuthority(Role.USER.getValue()));
		}
		
		return new User(user.getMemberemail(), user.getMemberpass(),auth);
	} // end UserDetails
	
}
