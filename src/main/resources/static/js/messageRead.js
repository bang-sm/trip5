function gotoTrash(){
	$.ajax({
		url: "/my/gotoTrashRead",
		type: 'POST',
		cache: false,
		data: "msgid="+$("#messageId").val(),
		success: function(data, status){
			if(status == "success"){
				alert('삭제되었습니다.')
				location.href = "/my/clipSend?sendid="+$("#userUuid").val();
			}
		}
	});
}