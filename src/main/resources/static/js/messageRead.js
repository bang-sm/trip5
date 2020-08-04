function gotoTrash(){
	if(confirm('정말로 삭제하시겠습니까?')){
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
}

function prevClip(){
	if($("#typeClip").val() == 'send'){
		$.ajax({
			url: "/my/prevClip",
			type: "POST",
			cache: false,
			data: "msgid="+$("#messageId").val()+"&sendid="+$("#userUuid").val(),
			success: function(data, status){
				if(status == "success"){
					if(data.length == 0){
						toastr.error('이전글이 없습니다!');
					} else {
						location.href = "/my/clipread?msgid="+data.msgid+"&sendid="+data.sendid
					}
				}
			}
		});
	} else {
			$.ajax({
				url: "/my/prevClip",
				type: "POST",
				cache: false,
				data: "msgid="+$("#messageId").val()+"&fromid="+$("#userUuid").val(),
				success: function(data, status){
					if(status == "success"){
						if(data.length == 0 ){
							toastr.error('이전글이 없습니다!');
						} else {
							location.href = "/my/clipread?msgid="+data.msgid+"&fromid="+data.fromid+"&sendid="+data.sendid
						}
					}
				}
			});
	}
}

function nextClip(){
	
	if($("#typeClip").val() == 'send'){
		$.ajax({
			url: "/my/nextClip",
			type: "POST",
			cache: false,
			data: "msgid="+$("#messageId").val()+"&sendid="+$("#userUuid").val(),
			success: function(data, status){
				if(status == "success"){
					if(data.length == 0){
						toastr.error('다음글이 없습니다!');
					} else {
						location.href = "/my/clipread?msgid="+data.msgid+"&sendid="+data.sendid
					}
				}
			}
		});
	} else {
			$.ajax({
				url: "/my/nextClip",
				type: "POST",
				cache: false,
				data: "msgid="+$("#messageId").val()+"&fromid="+$("#userUuid").val(),
				success: function(data, status){
					if(status == "success"){
						if(data.length == 0 ){
							toastr.error('다음글이 없습니다!');
						} else {
							location.href = "/my/clipread?msgid="+data.msgid+"&fromid="+data.fromid+"&sendid="+data.sendid
						}
					}
				}
			});
	}
}

function deleteForever(){
	var params = "msgid="+$("#messageId").val();
	
	 $.ajax({
		url: "/my/deleteOk",
		type: "POST",
		cache: false,
		data: params,
		success: function(data, status){
			if(status == "success"){
				alert('삭제했습니다!');
				location.href = "/my/clipSend?sendid="+$("#userUuid").val();
			}
		}
	 });

}


