$(document).ready(function() {
	$(".idCheck").click(function() {
		$.ajax({
			url : "/user/ajax/idCheck",
			type : "POST",
			data : {
				"memberemail" : $("#memberemail").val()
			},
			success : function(data) {
				/* console.log(data); */
				console.log($("#memberemail").val());
				console.log(data);
				if (data == 1) {
					$(".idCheckNum").val("1");
					$(".result .msg").text("이미 사용중인 아이디 입니다.");
					$(".result .msg").attr("style", "color:#f00");
				} else if (data == 2) {
					$(".idCheckNum").val("2");
					$(".result .msg").text("공백은 사용할 수 없습니다.");
					$(".result .msg").attr("style", "color:#f00");
				} else if (data == 3) {
					$(".idCheckNum").val("3");
					$(".result .msg").text("카카오로그인된 아이디 입니다");
					$(".result .msg").attr("style", "color:#f00");
				} else if (data == 4) {
					$(".idCheckNum").val("4");
					$(".result .msg").text("이메일 형식이 아닙니다.");
					$(".result .msg").attr("style", "color:#f00");
				} else if ((data == 0)) {
					$(".idCheckNum").val("0");
					$(".result .msg").text("사용가능");
					$(".result .msg").attr("style", "color:#00f");
				} // end if

			} // end function
		}); // ajax 끝
	});
});