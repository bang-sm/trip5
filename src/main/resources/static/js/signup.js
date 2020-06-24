$(document).ready(function() {
	console.log("하위");
	$(".idCheck").click(function() {
		console.log("바위aasd");
	/*	var query = {
			userId : $("#memberid").val()
		};*/
		$.ajax({
			url : "/ajax/idCheck",
			type : "POST",
			data : {
				"userId" : $("#memberid").val()
			},
			success : function(data) {
				console.log(data);
				
				if (data == 1) {
					$(".result .msg").text("사용 불가");
					$(".result .msg").attr("style", "color:#f00");
				}else if(data == 2){
					$(".result .msg").text("공백은 사용할 수 없습니다");
					$(".result .msg").attr("style", "color:#f00");
				}
				else {
					$(".result .msg").text("사용 가능");
					$(".result .msg").attr("style", "color:#00f");
				}
				
			}
		}); // ajax 끝
	});
});