var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
	console.log('connect//////');
    username = document.querySelector('#name').value.trim();

    if(username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}


function onConnected() {
	
	console.log('onConnected//////');
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    )
    connectingElement.classList.add('hidden');
}

function onError(error) {
	console.log('onError//////');
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
	console.log('sendMessage//////');
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
	console.log('onMessageReceived//////')
    var message = JSON.parse(payload.body);
	console.log(message);
    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') { 
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)



/*var ws;
var nick;
var blackListv;
var blackUser = true;
var otherUUid;

var Now = new Date();
var NowHours = Now.getHours();
var NowMinutes = (Now.getMinutes() < 10) ? "0" + (Now.getMinutes()) : Now.getMinutes();

$(document).ready(function(){
	loadPage();
	chatConn();
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
						$(".chat-body").append(" <div class='message my-message'>" +
                            "<img alt='' class='img-circle medium-image' src='https://bootdey.com/img/Content/avatar/avatar1.png'>"+
                            "<div class='message-body'>" +
                                "<div class='message-body-inner'>"+
                                    "<div class='message-info'>"+
                                        "<h4>나</h4>"+
                                        "<h5> <i class='far fa-clock'></i>"+NowHours + " : " +NowMinutes+ "</h5>"+
                                    "</div>"+
                                    "<hr>"+
                                    "<div class='message-text'>"+d.msg+"</div>"+
                                "</div>"+
                            "</div>"+
                            "<br>"+
                        "</div>");
					}
				} else if((d.msg.length != 0)){
					for(i = 0; i< blackListv.length; i++){
						if(blackListv[i].blackuuid == d.userUuid){
							blackUser = false;
							break;
						}
					}
						if(blackUser){
							$(".chat-body").append(
									"<div class='message info'>"+
                            "<img alt='' class='img-circle medium-image' src='https://bootdey.com/img/Content/avatar/avatar1.png'>"+
                            "<div class='message-body'>"+
                                "<div class='message-info'>"+
                                    "<h4>" + "<div class='dropdown others'>" +
      							  "<a class='stretched-link othersMsg' href='#' style='color:white' role='button' id='dropdownMenuLink' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false' data-uuid='"+d.userUuid+"' data-email='" + d.userName + "'>"
    							  + d.userName +
    							  "</a>" +
    							  "<div class='dropdown-menu' aria-labelledby='dropdownMenuLink'>"
    							    +"<button class='dropdown-item' onclick='blackList()' data-email='" + d.userName + "'>차단하기</button>"+
    							    "<button class='dropdown-item' data-backdrop='static' data-toggle='modal' data-target='#exampleModal' onclick='sendMsg()' data-uuid='"+d.userUuid+"'>쪽지보내기</button>" +
    							  "</div>" 
    							  + "</div>" + "</h4>"+
                                    "<h5> <i class='far fa-clock'></i>"+NowHours + " : " +NowMinutes+ "</h5>"+
                                "</div>"+
                                "<hr>"+
                                "<div class='message-text'>"+
                                    d.msg+
                                "</div>"+
                            "</div>"+
                            "<br>"+
                        "</div>");
					}
				}
			} else if(d.type="inchat"){
				$(".contacts").append("<li data-toggle='tab' data-target='#inbox-message-2' data-sessionId='"+ $("#sessionId").val() +"'>"+
				"<img alt='' class='img-circle medium-image' src='../image/user.jpg' style='vertical-align: baseline'>"+
					"<div class='vcentered info-combo'>"+
						"<h3 class='no-margin-bottom name'>"+d.userName+"</h3>"+
						"<h5>Of course, just call me before that, in case I forget.</h5>"+
					"</div>" +
					"<div class='contacts-add'>"+
						"<span class='message-time'>" +NowHours + "시 " +NowMinutes+ "분</span>"+
					"</div>"+
				"</li>")
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

function inChatt(){
	var option = {
			type : "inchat",
			sessionId : $("#sessionId").val(),
			userName : $('#userSessionId').val(),
			msg : $('#userSessionId').val() + "님이 입장하셨습니다."
	}
	ws.send(JSON.stringify(option));
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
		msg : $(".send-message-text").val(),
	}
	ws.send(JSON.stringify(option))
	$('.send-message-text').val("");
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
				alert("쪽지를 보냈습니다!");
				$(".modal").hide();
				$("body > div.modal-backdrop.fade.show").remove();
			}
		}
	})
}

*/





