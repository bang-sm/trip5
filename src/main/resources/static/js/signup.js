var authEmailNum;
var authNum;

$(document).ready(function() {
	// 이메일 인증메일 보내기 AJAX
	$(".authSubmit").click(function() {
		console.log("하이욤");
		$("#sendMsg").text("인증번호 전송 중입니다.");
		$.ajax({
			url : "/user/authEmail.do",
			type : "POST",
			data : {
				"email" : $("#email").val()
			},
			success : function(data) {
				$("#sendMsg").text("");
				alert("인증번호가 전송 성공했습니다.");
				authEmailNum = data;
			} // end success function
			,error: function(data) {
				console.log("인증번호 전송 실패");
				$("#sendMsg").text("");
				alert("인증번호 전송이 실패했습니다.");
				authEmailNum = data;
			} // end error function
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
		console.log("들어옴?");
		if($(".authmsg").text() == "인증성공"){
			console.log("들어왔어요임마")
			$("#memberemail").val($("#email").val());
		}
		authNum = "";
		$(".authText").val("");
		$(".authmsg").text("");
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
