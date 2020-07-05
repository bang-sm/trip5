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
		inChatt();
	}

	ws.onmessage = function(data) {
		//메시지를 받으면 동작
		var msg = data.data;
		
		console.log("msg = " + msg);
		if (msg != null && msg.trim() != '') {
			var d = JSON.parse(msg);
			console.log(d.sessionId + " <-- d.sessionId 값");
			if (d.type == "getId") {
				var si = d.sessionId != null ? d.sessionId : "";
				if (si != '') {
					$("#sessionId").val(si);
					$("#chating").append("<p class='inChat'>" + $('#userSessionId').val() + "님이 입장하셨습니다</p>");
				}
			} else if (d.type == "message") {
				if (d.sessionId == $("#sessionId").val()) {
					if(d.msg.length == 0){
						alert("내용을 입력하세요!!")
					} else {
						$("#chating").append("<p class='me'>나 : " + d.msg + "</p>");
					}
				} else if(d.msg.length != 0){
						$("#chating").append(
							"<div class='dropdown others'>" +
							  "<a class='stretched-link' href='#' role='button' id='dropdownMenuLink' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>"
							  + d.userName +
							  "</a>" +
							  "<div class='dropdown-menu' aria-labelledby='dropdownMenuLink'>"
							    +"<a class='dropdown-item' href='#'>차단하기</a>"+
							    "<a class='dropdown-item' href='#'>쪽지보내기</a>" +
							  "</div>" 
							  + " : " + d.msg + "</div>");
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

function chatConn() {
	wsOpen();
	$('#chatinBtn').hide();
	$('.inputTable').show();
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

function inChatt(){
	var option = {
			type : "getId",
			sessionId : $("#sessionId").val(),
			userName : $('#userSessionId').val(),
			msg : $('#userSessionId').val() + "님이 입장하셨습니다."
		}
		ws.send(JSON.stringify(option))
}

function outChatt(){
	var option = {
			type : "outId",
			sessionId : $("#sessionId").val(),
			userName : $('#userSessionId').val(),
			msg : $('#userSessionId').val() + "님이 퇴장하셨습니다."
		}
		ws.send(JSON.stringify(option))
}




