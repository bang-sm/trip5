var pnId = 0;	// 팝업공지 수
var cnt = 0;	// 비어있는 내용 수

/* 슬라이드 공지 삭제 */
$(document).on(
		"click",
		".sNoticeDelete",
		function() {
			$(this).parents(".sNoticecontent").children('.nSlideInput').val("")
			$.ajax({
				url : "/adminNotice/ajax/sNoticeDelete",
				data : {
					"sNoticeUid" : $(this).parents(".sNoticecontent").children(
							'.sNoticeUid').text()
				},
				type : "POST",
				success : function(data) {
					toastr.success("공지를 삭제했습니다.");
				}
			}) // Ajax 끝
		})

/* 슬라이드 공지 수정 */
$(document).on(
		"click",
		".sNoticeUpdate",
		function() {
			console.log("들어옴");
			$.ajax({
				url : "/adminNotice/ajax/sNoticeUpdate",
				data : {
					"snId" : $(this).parents(".sNoticecontent").children(
							'.sNoticeUid').text(),
					"snContent" : $(this).parents(".sNoticecontent").children(
							'.nSlideInput').val()
				},
				type : "POST",
				success : function(data) {
					toastr.success("공지를 수정했습니다.");
				}
			}) // Ajax 끝
		})

/* 슬라이드 공지 등록 */
$(document).on(
		"click",
		".enrollmentSNotice",
		function() {
			var snId = []; // 배열선언
			cnt = 0; // 초기화
			$("input:checkbox[class='nSlideCheck']:checked").parents(
					".sNoticecontent").children('.sNoticeUid').each(function() {
				snId.push($(this).text());
				if($(this).parents(".sNoticecontent").children(".nSlideInput").val().trim() == ""){
					cnt++;
				} // end if
			})

			if (snId.length != 0) { // 값이 있을 때
				var data = {
					"snId" : snId
				// 배열 저장
				};
			} else { // 값이 없을 때
				console.log("???")
				snId.push(-1);
				cnt = -2;
				var data = {
					"snId" : snId
				}
			}


			if(cnt == 0){
				$.ajax({
					url : "/adminNotice/ajax/sNoticeEnrol",
					data : data,
					type : "POST",
					success : function(data) {
						toastr.success("공지를 등록했습니다.");
					},
					error:function(request,status,error){
						toastr.error("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
					}
				}) // Ajax 끝
			} else{
				if(cnt == -2){
					toastr.error("등록할 공지를 신청하세요.");				
				} else{
					toastr.error("공백은 공지로 등록할 수 없습니다.");				
				} // end if
			} // end if
			
		})

/*------------------------------------------------------------*/
/* 팝업 */
/*------------------------------------------------------------*/
/* 팝업 공지창 정보 전달 */
$(document).on(
		"click",
		".nPopUpInput",
		function() {
			pnId = $(this).parents(".nPopUpcontent").children('.pNoticeUid')
					.text()

			console.log(pnId);

			$.ajax({
				url : "/adminNotice/ajax/pNoticeData",
				data : {
					"pNoticeUid" : $(this).parents(".nPopUpcontent").children(
							'.pNoticeUid').text()
				},
				type : "POST",
				success : function(data) {
					$('.title').val(data[0].pnHeader);
					$('.textPopUpNotice').val(data[0].pnContent);
				}
			}) // Ajax 끝

		})

/* 팝업 공지 수정 */
$(document).on(
		"click",
		".pNoticeUpdate",
		function() {
			console.log("들어옴");
			$('.' + pnId).parents(".nPopUpcontent").children('.nPopUpInput')
					.val("제목 : " + $('.title').val());

			$.ajax({
				url : "/adminNotice/ajax/pNoticeUpdate",
				data : {
					"pnId" : pnId,
					"pnHeader" : $('.title').val(),
					"pnContent" : $('.textPopUpNotice').val()
				},
				type : "POST",
				success : function(data) {
					toastr.success("공지를 수정했습니다.");
				}
			}) // Ajax 끝
		})

/* 팝업 공지 삭제 */
$(document).on(
		"click",
		".pNoticeDelete",
		function() {
			$(this).parents(".nPopUpcontent").children('.nPopUpInput').val(
					"제목 : ")

			$.ajax({
				url : "/adminNotice/ajax/pNoticeDelete",
				data : {
					"pNoticeUid" : $(this).parents(".nPopUpcontent").children(
							'.pNoticeUid').text()
				},
				type : "POST",
				success : function(data) {
					toastr.success("공지를 삭제했습니다.");
				}
			}) // Ajax 끝
		})

/* 슬라이드 공지 등록 */
$(document).on(
		"click",
		".enrollmentPNotice",
		function() {
			var pnId = []; // 배열선언
			cnt = 0; // 초기화
			$("input:checkbox[class='nPopUpCheck']:checked").parents(
					".nPopUpcontent").children('.pNoticeUid').each(function() {
				pnId.push($(this).text());
				if($(this).parents(".nPopUpcontent").children(".nPopUpInput").val().trim() == ""){
					cnt++;
				}
			})

			if (pnId.length != 0) { // 값이 있을 때
				console.log("enrollmentPNotice 들어옴")
				var data = {
					"pnId" : pnId
				// 배열 저장
				};
			} else { // 값이 없을 때
				console.log("pnId 값이 없음")
				pnId.push(-1);
				cnt = -2;
				var data = {
					"pnId" : pnId
				}
			} // end if
			
			if(cnt == 0){
				$.ajax({
					url : "/adminNotice/ajax/pNoticeEnrol",
					data : data,
					type : "POST",
					success : function(data) {
						toastr.success("공지를 등록했습니다.");
					},
				    error:function(request,status,error){
					   toastr.error("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				    }
				}) // Ajax 끝
			} else{
				if(cnt == -2){
					toastr.error("등록할 공지를 신청하세요.");				
				} else{
					toastr.error("공백은 공지로 등록할 수 없습니다.");				
				} // end if
			} // end if

		})
