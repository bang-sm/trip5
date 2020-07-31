/* 관리자가 부여한 유저 채팅 블랙리스트 추가 */
$(document).on(
		"click",
		".addBlackList",
		function() {
			var msgBlackList = $(this).text().trim();	// 초기화 되기전에 값 저장
			 $(this).text("삭제");
			 
			 $(this).attr('class','btn-sm btn-block btn btn-outline-danger removeBlackList');
			 
			$.ajax({
				url : "/admin/adminMembers",
				data : {
					"uuid" : $(this).parents(".memberList")
							.children('.uuid').text(),
					"msgBlackList" : msgBlackList
				},
				type : "GET",
				success : function(data) {
					toastr.success("블랙리스트에 추가되었습니다.");
				}
			}) // Ajax 끝
		})

/* 관리자가 부여한 유저 채팅 블랙리스트 삭제*/
		$(document).on(
				"click",
				".removeBlackList",
				function() {
					var msgBlackList = $(this).text().trim() // 초기화 되기전에 값 저장
					$(this).text("추가");
					
					$(this).attr('class','btn-sm btn-block btn btn-outline-primary addBlackList');
					
					$.ajax({
						url : "/admin/adminMembers",
						data : {
							"uuid" : $(this).parents(".memberList")
									.children('.uuid').text(),
							"msgBlackList" : msgBlackList
						},
						type : "GET",
						success : function(data) {
							toastr.success("블랙리스트를 삭제했습니다.");
						}
					}) // Ajax 끝
				})