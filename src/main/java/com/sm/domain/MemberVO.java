package com.sm.domain;

import java.util.Date;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberVO {

//	@NotBlank(message = "아이디는 필수 입력 값입니다.")
//	private String memberid;

	@NotBlank(message = "필수 정보입니다.")
	@Email(message = "이메일 형식에 맞지 않습니다.")
	private String memberemail;

	@NotBlank(message = "필수 정보입니다.")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "8~20자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
	private String memberpass;

	
	@NotBlank(message = "필수 정보입니다.")
	@Pattern(regexp = "[A-Za-z0-9가-힣]+.{1,10}", message = "특수문자를 제외한1~10자 단어를 사용하세요.")
	private String membernick;
	
	private int uuid;
	private Date memberregdate;
	private String accountrock;
	private String accountstatus;
	private String sessionlog;
	private String kakaoOk;
	private int blackuuid;
	private int weatherlocaluid;
	
//	// 프로필
//	private String prorgfilename;
//	private String prsavefilename;
//	private int prfilesize;
//	private String prcreaid;
//	private String prdelchk;
	
}
