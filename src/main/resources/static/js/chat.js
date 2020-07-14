var ws;
var nick;
var blackListv;
var blackUser = true;
var otherUUid;
$(document).ready(function(){
	loadPage();
});

$(document).on('click',".othersMsg" ,function(){
	nick = $(this).attr('data-email');
	otherUUid = $(this).attr('data-uuid');
});

function loadPage(){
	data = "uuid="+$("#userSessionuuid").val();
	console.log(data);
	$.ajax({
		url : "/my/load",
		type : "POST",
		cache : false,
		data : data,
		success : function(data1, status){
			if(status == "success"){
				console.log("data1 = " + data1);
				blackListv = data1;
			}
		}
	});
}

function wsOpen() {
	ws = new WebSocket("ws://" + location.host + "/chating");
	wsEvt();
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
		blackUser = true;
		console.log("msg<< = " + msg);
		if (msg != null && msg.trim() != '') {
			var d = JSON.parse(msg);
			console.log(d.sessionId + " <--- d.sessionId 값");
			console.log(d.userName + " <--- d.userName 값");
			if (d.type == "getId") {
				console.log('getid')
				var si = d.sessionId != null ? d.sessionId : "";
				if (si != '') {
					if($("#sessionId").val() == ""){
						$("#sessionId").val(si);
						console.log("sessionId////// = " + $("#sessionId").val());
					}
				} 
			} else if (d.type == "message") {
				if (d.sessionId == $("#sessionId").val()) {
					if(d.msg.length == 0){
						alert("내용을 입력하세요!!")
					} else {
						$("#chating").append("<p class='me'>나 : " + d.msg + "</p>");
					}
				} else if((d.msg.length != 0)){
					for(i = 0; i< blackListv.length; i++){
						if(blackListv[i].blackuuid == d.userUuid){
							blackUser = false;
							break;
						}
					}
						if(blackUser){
						$("#chating").append(
							"<div class='dropdown others'>" +
							  "<a class='stretched-link othersMsg' href='#' role='button' id='dropdownMenuLink' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false' data-uuid='"+d.userUuid+"' data-email='" + d.userName + "'>"
							  + d.userName +
							  "</a>" +
							  "<div class='dropdown-menu' aria-labelledby='dropdownMenuLink'>"
							    +"<button class='dropdown-item' onclick='blackList()' data-email='" + d.userName + "'>차단하기</button>"+
							    "<button class='dropdown-item' data-toggle='modal' data-target='#exampleModal' onclick='sendMsg()' data-uuid='"+d.userUuid+"'>쪽지보내기</button>" +
							  "</div>" 
							  + " : " + d.msg + "</div>");
						}
				}
			} else if(d.type="inchat"){
				$("#chating").append("<p class='me'>" + d.userName + "님이 입장하셨습니다</p>");
			}	else {
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

function inChatt(){
	var option = {
			type : "inchat",
			sessionId : $("#sessionId").val(),
			userName : $('#userSessionId').val(),
			msg : $('#userSessionId').val() + "님이 입장하셨습니다."
	}
	ws.send(JSON.stringify(option)) 
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
		userUuid : $('#userSessionuuid').val(),
		msg : $("#chatting").val()
	}
	ws.send(JSON.stringify(option))
	$('#chatting').val("");
}


function blackList(){
	if(confirm("차단하시겠습니까?")){
		// 내 uuid 와 상대방 nickname request
		data = "uuid="+$("#userSessionuuid").val() + "&membernick="+nick
		console.log(data);
		$.ajax({
			url : "/my/black",
			type : "POST",
			cache : false,
			data : data,
			success : function(data, status){
				if(status == "success"){
					alert("차단되었습니다.");
					$("button[data-email="+nick+"]").text("차단해제");
					$("button[data-email="+nick+"]").removeAttr("onclick");
					$("button[data-email="+nick+"]").attr("onclick", "disBlackList()");
					
					loadPage();
				}
			}
		});
	} else {
		return ;
	}
}

function disBlackList(){
	if(confirm("차단을 푸시겠습니까?")){
		// 내 uuid 와 상대방 nickname request
		data = "&membernick="+nick
		console.log(data);
		$.ajax({
			url : "/my/disblack",
			type : "POST",
			cache : false,
			data : data,
			success : function(data, status){
				if(status == "success"){
					alert("차단해제 되었습니다.");
					$("button[data-email="+nick+"]").text("차단하기");
					$("button[data-email="+nick+"]").removeAttr("onclick");
					$("button[data-email="+nick+"]").attr("onclick", "blackList()");
					
					loadPage();
				}
			}
		});
	} else {
		return ;
	}
}

function sendMsg(){
	$(".recep").val(nick);
}

function sendToMsg(){
	
	$.ajax({
		url : "/my/sendMsg",
		type : "POST",
		cache : false,
		data : {
			"msgcontent" : $("#message-text").val(),
			"fromid" : otherUUid,
			"sendid" : $("#userSessionuuid").val(),
			"msgsubject" : $("#message-subject").val()
		},
		success : function(data,status){
			if(status == "success"){
				alert("쪽지가 보내졌습니다!.");
				$(".modal").hide();
				$("body > div.modal-backdrop.fade.show").remove();
			}
		}
	})
}









