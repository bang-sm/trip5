package com.sm.domain;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberVO {
	
	@NotBlank(message = "아이디는 필수 입력 값입니다.")
	private String userid;
	
	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
	@Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
	private String userPass;
 	
	private String userName;
	
	@NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
	private String userEmail;
	
	private String userPhon;
	private String userAddr1;
	private String userAddr2;
	private String userAddr3;
	private Date regiDate;
	private int verify;
	private int user_number;
	
	@Builder
    public MemberVO(String userid,String userEmail, String userPass) {
        this.userid = userid;
        this.userEmail = userEmail;
        this.userPass = userPass;
    }
}
