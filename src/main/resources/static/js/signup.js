$(document).ready(function() {
	$(".idCheck").click(function() {
		$.ajax({
			url : "/user/ajax/idCheck",
			type : "POST",
			data : {
				"memberemail" : $("#memberemail").val()
			},
			success : function(data) {
				$(".idCheckMsg").text("");
				switch(data){
				case 1:
					$(".idCheckNum").val("1");
					$(".result .msg").text("이미 사용중인 아이디 입니다.");
					$(".result .msg").attr("style", "color:#f00");
					break
				case 2:	
					$(".idCheckNum").val("2");
					$(".result .msg").text("공백은 사용할 수 없습니다.");
					$(".result .msg").attr("style", "color:#f00");
					break;
				case 3:
					$(".idCheckNum").val("3");
					$(".result .msg").text("카카오로그인된 아이디 입니다");
					$(".result .msg").attr("style", "color:#f00");
					break;
				case 4:
					$(".idCheckNum").val("4");
					$(".result .msg").text("이메일 형식이 아닙니다.");
					$(".result .msg").attr("style", "color:#f00");
					break;
				default:
					$(".idCheckNum").val("0");
					$(".result .msg").text("사용가능");
					$(".result .msg").attr("style", "color:#00f");
					break;
				} // end switch

			} // end function
		}); // ajax 끝
	});
});