var sel_file;

$(document).ready(function() {
	console.log("이미지 바꾸기")
	
	// 프로필 사진 미리보기
	$('#photo').on("change", handleImgFileSelect);
	
	// 사진 삭제
	$(".deletePhoto").click(function() {
		$('#img').attr('src', '/resources/upload/userProfile/profile.jpg');
		
		$('.isImgCheck').val("1");
	});
	


});

function handleImgFileSelect(e) {
	console.log("핸들러 탔나요?");
	var files = e.target.files;
	var filesArr = Array.prototype.slice.call(files);

	$('.isImgCheck').val("0");
	
	// 이미지 파일 유효성 검사
	filesArr.forEach(function(f) {
		if (!f.type.match("image.*")) {
			alert("확장자는 이미지 확장자만 가능합니다.");
			return false;
		}
		console.log("여기까지 핸들러 탔나요?");
		sel_file = f;

		// 이미지 파일 미리보게 입력
		var reader = new FileReader();
		reader.onload = function(e) {
			$('#img').attr('src', e.target.result);
		}
		reader.readAsDataURL(f);
	});
}