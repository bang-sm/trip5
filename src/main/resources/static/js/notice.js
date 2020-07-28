var pnId = 0;

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
			$("input:checkbox[class='nSlideCheck']:checked").parents(
					".sNoticecontent").children('.sNoticeUid').each(function() {
				snId.push($(this).text());
			})

			if (snId.length != 0) { // 값이 있을 때
				console.log("???????")
				var data = {
					"snId" : snId
				//배열 저장
				};
			} else { // 값이 없을 때
				console.log("???")
				snId.push(-1);
				var data = {
					"snId" : snId
				}
			}

			$.ajax({
				url : "/adminNotice/ajax/sNoticeEnrol",
				data : data,
				type : "POST",
				success : function(data) {
					toastr.success("공지를 등록했습니다.");
				}
			}) // Ajax 끝
		})

/*------------------------------------------------------------*/
/*								팝업							*/
/*------------------------------------------------------------*/
/* 팝업 공지창 정보 전달 */
$(document).on(
		"click",
		".nPopUpInput",
		function() {
			pnId = $(this).parents(".nPopUpcontent").children('.pNoticeUid').text()
			
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
			$('.'+pnId).parents(".nPopUpcontent").children('.nPopUpInput').val("제목 : " + $('.title').val());
			
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
			$(this).parents(".nPopUpcontent").children('.nPopUpInput').val("제목 : ")

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