var ws;

function wsOpen() {
	ws = new WebSocket("ws://" + location.host + "/chating");
	wsEvt();
	console.log($('#userSessionId').val());
}

function wsEvt() {
	ws.onopen = function(data) {
		//소켓이 열리면 동작
		console.log('소켓 오픈');
	}

	ws.onmessage = function(data) {
		//메시지를 받으면 동작
		var msg = data.data;
		
		console.log(msg);
		if (msg != null && msg.trim() != '') {
			var d = JSON.parse(msg);
			console.log(d.sessionId + " <-- d.sessionId 값");
			if (d.type == "getId") {
				var si = d.sessionId != null ? d.sessionId : "";
				if (si != '') {
					$("#sessionId").val(si);
				}
			} else if (d.type == "message") {
				if (d.sessionId == $("#sessionId").val()) {
					if(d.msg.length == 0){
						alert("내용을 입력하세요!!")
					} else {
					$("#chating").append("<p class='me'>나 : " + d.msg + "</p>");
					}
				} else {
					$("#chating").append(
							"<p class='others'>" + d.userName + " : " + d.msg
									+ "</p>");
				}
			} else {
				console.warn("unknown type!")
			}
		}
	}

	document.addEventListener("keypress", function(e) {
		if (e.keyCode == 13) { //enter press
			send();
		}
	});
}

function chatName() {
	var userName = $("#userName").val();
	if (userName == null || userName.trim() == "") {
		alert("사용자 이름을 입력해주세요.");
		$("#userName").focus();
	} else {
		wsOpen();
		$("#yourName").hide();
		$("#yourMsg").show();
	}
}

function send() {
	var option = {
		type : "message",
		sessionId : $("#sessionId").val(),
		userName : $('#userSessionId').val(),
		msg : $("#chatting").val()
	}
	ws.send(JSON.stringify(option))
	$('#chatting').val("");
}