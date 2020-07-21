var authEmailNum;
var authNum;

$(document).ready(function() {
	// 이메일 인증메일 보내기 AJAX
	$(".authSubmit").click(function() {
		$.ajax({
			url : "/user/authEmail.do",
			type : "POST",
			data : {
				"email" : $("#email").val()
			},
			success : function(data) {
				alert("인증번호가 전송되었습니다.");
				authEmailNum = data;
			} // end function
		}); // ajax 끝
	});

	// 이메일 인증 확인
	$(".authNum").click(function() {
		authNum = $(".authText").val();
		if(authEmailNum == authNum){
			$(".authmsg").text("인증성공");
			$(".authmsg").attr("style", "color:#00f");
		} else{
			$(".authmsg").text("인증실패");
			$(".authmsg").attr("style", "color:#f00");
		}
	});

	// 이메일 메인페이지 전달
	$(".success").click(function() {
		authNum = "";
		$(".authText").val("");
		$(".authmsg").text("");
		if($(".authmsg").text() == "인증성공"){
			$("#memberemail").val($("#email").val());
		}
	});
	
	// 이메일(아이디) 중복체크 AJAX
	$(".idCheck").click(function() {
		$.ajax({
			url : "/user/ajax/idCheck",
			type : "POST",
			data : {
				"memberemail" : $("#memberemail").val()
			},
			success : function(data) {
				$(".idCheckMsg").text("");
				switch (data) {
				case 1:
					$(".idCheckNum").val("1");
					$(".result .msg").text("이미 사용중인 아이디 입니다.");
					$(".result .msg").attr("style", "color:#f00");
					break
				case 2:
					$(".idCheckNum").val("2");
					$(".result .msg").text("이메일 인증을 진행해주세요.");
					$(".result .msg").attr("style", "color:#f00");
					break;
				default:
					$(".idCheckNum").val("0");
					$(".result .msg").text("사용가능");
					$(".result .msg").attr("style", "color:#00f");
					break;
				}
			} // end function
		}); // ajax 끝
	});
});
