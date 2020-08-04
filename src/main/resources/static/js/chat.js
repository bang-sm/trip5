var stompClient = null;
var userNick;
var nick;
var blackListv;
var blackUser = true;
var otherUUid;

var Now;
var NowHours;
var NowMinutes;

var outer = '';

var inUser = new Array();

var lastOne = false;

function getdate(){
	Now = new Date();
	NowHours = Now.getHours();
	NowMinutes = (Now.getMinutes() < 10) ? "0" + (Now.getMinutes()) : Now.getMinutes();
}

$(document).ready(function(){
	loadPage();
	connect();
	
	$(window).on("beforeunload", function () {
		if(lastOne){
			outUser();
		}
    });
});

document.addEventListener("keypress", function(e) {
	if (e.keyCode == 13) { //enter press
		send();
	}
});

function connect(event) {
	console.log('connect//////');

    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    console.log(socket);
    
    stompClient.connect({}, onConnected, onError);
//    event.preventDefault();
}

function onConnected() {
	console.log('onConnected//////');
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({uuid: $("#userSessionuuid").val(), sender: $("#userSessionId").val() , type: 'JOIN'})
    )
}

function onError(error) {
	console.log('onError//////');
}

function send(event) {
	console.log('sendMessage//////');
    var messageContent = $(".send-message-text").val();
    console.log(messageContent + " mc/////")
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: $("#userSessionId").val(),
            content: messageContent,
            uuid: $("#userSessionuuid").val(),
            type: 'CHAT'
        };
       
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        $("#message").val('');
    }
//  event.preventDefault();
}

function outUser(event){
	if(lastOne){
		var chatMessage = {
				sender: $("#userSessionId").val(),
				type: 'LEAVE'
		}
	} else {
		var chatMessage = {
				sender: outer,
				type: 'LEAVE'
		};
	}
	stompClient.send("/app/chat.outUser", {}, JSON.stringify(chatMessage));
}


function sendAlarm(){
	var alarmMessage = {
			type : 'ALARM',
			sender: nick,
			content: "쪽지가 도착했습니다!"
	};
	
	stompClient.send("/app/chat.sendAlarm", {}, JSON.stringify(alarmMessage));
}

function onMessageReceived(payload) {
	blackUser = true;
	console.log('onMessageReceived//////')
    var message = JSON.parse(payload.body);
	console.log(message);
    
    if(message.type === 'JOIN') {
    	console.log(userNick + " <-- 멤버 닉네임")
    	lastOne = false;
    	inUser.push(message.participant[message.participant.length-1]) // 메세지 센더 이슈?
    	for(i = 0; i<message.participant.length; i++){
    		if(blackListv.length == 0){
    			if(!$("li[data-name='"+message.participant[i]+"']").length){
					$(".contacts").append("<li data-toggle='tab' data-target='#inbox-message-2' data-name='"+message.participant[i]+"'>"+
							"<img alt='' class='img-circle medium-image' src='../image/user.jpg' style='vertical-align: baseline'>"+
							"<div class='vcentered info-combo'>"+
							"<h3 class='no-margin-bottom name'>"+message.participant[i]+"</h3>"+
							"<h5>...</h5>"+
							"</div>"+
							"<div class='contacts-add'>"+
							($("#userSessionId").val() != message.participant[i] ? 
									"<svg class='othersMsg' width='1em' height='1em' viewBox='0 0 16 16' class='bi bi-three-dots-vertical' fill='currentColor' xmlns='http://www.w3.org/2000/svg' id='dropdownMenuLink' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false' data-email='" + message.participant[i] + "' style='float:right'>"+
									"<path fill-rule='evenodd' d='M9.5 13a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0zm0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0zm0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0z'/>"+
									"</svg>"+
									"<div class='dropdown-menu' aria-labelledby='dropdownMenuLink'>"
									+"<button class='dropdown-item' onclick='blackList()' data-email='" + message.participant[i] + "'><i class='fas fa-comment-slash'></i>차단하기</button>"+
									"<button class='dropdown-item' data-backdrop='static' data-toggle='modal' data-target='#exampleModal' onclick='sendMsg()'><i class='far fa-envelope' style='font-size:1.05rem'></i>쪽지보내기</button>" +
									"<button class='dropdown-item' data-backdrop='static' data-toggle='modal' data-target='#exampleModal' onclick='gotoBlog()'><i class='fab fa-blogger' style='font-size:1.05rem'></i>블로그 보기</button>" +
									"</div>"  
									: '') +
									"</div>"+
					"</li>")
				}
    		} else {
    			for(j = 0; j<blackListv.length; j++){
        			if(message.participant[i] == blackListv[j].membernick){
        				if(!$("li[data-name='"+message.participant[i]+"']").length){
        					$(".contacts").append("<li data-toggle='tab' data-target='#inbox-message-2' data-name='"+message.participant[i]+"'>"+
        							"<img alt='' class='img-circle medium-image' src='../image/user.jpg' style='vertical-align: baseline'>"+
        							"<div class='vcentered info-combo'>"+
        							"<h3 class='no-margin-bottom name'>"+message.participant[i]+"</h3>"+
        							"<h5>...</h5>"+
        							"</div>"+
        							"<div class='contacts-add'>"+
        							($("#userSessionId").val() != message.participant[i] ? 
        									"<svg class='othersMsg' width='1em' height='1em' viewBox='0 0 16 16' class='bi bi-three-dots-vertical' fill='currentColor' xmlns='http://www.w3.org/2000/svg' id='dropdownMenuLink' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false' data-email='" + message.participant[i] + "' style='float:right'>"+
        									"<path fill-rule='evenodd' d='M9.5 13a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0zm0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0zm0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0z'/>"+
        									"</svg>"+
        									"<div class='dropdown-menu' aria-labelledby='dropdownMenuLink'>"
        									+"<button class='dropdown-item' onclick='disBlackList()' data-email='" + message.participant[i] + "'><i class='fas fa-comment-slash'></i>차단해제</button>"+
        									"<button class='dropdown-item' data-backdrop='static' data-toggle='modal' data-target='#exampleModal' onclick='sendMsg()''><i class='far fa-envelope' style='font-size:1.05rem'></i>쪽지보내기</button>" +
        									"<button class='dropdown-item' data-backdrop='static' data-toggle='modal' data-target='#exampleModal' onclick='gotoBlog()'><i class='fab fa-blogger' style='font-size:1.05rem'></i>블로그 보기</button>" +
        									"</div>"  
        									: '') +
        									"</div>"+
        					"</li>")
        				}
        			} else{
        				if(!$("li[data-name='"+message.participant[i]+"']").length){
        					$(".contacts").append("<li data-toggle='tab' data-target='#inbox-message-2' data-name='"+message.participant[i]+"'>"+
        							"<img alt='' class='img-circle medium-image' src='../image/user.jpg' style='vertical-align: baseline'>"+
        							"<div class='vcentered info-combo'>"+
        							"<h3 class='no-margin-bottom name'>"+message.participant[i]+"</h3>"+
        							"<h5>...</h5>"+
        							"</div>"+
        							"<div class='contacts-add'>"+
        							($("#userSessionId").val() != message.participant[i] ? 
        									"<svg class='othersMsg' width='1em' height='1em' viewBox='0 0 16 16' class='bi bi-three-dots-vertical' fill='currentColor' xmlns='http://www.w3.org/2000/svg' id='dropdownMenuLink' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false' data-email='" + message.participant[i] + "' style='float:right'>"+
        									"<path fill-rule='evenodd' d='M9.5 13a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0zm0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0zm0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0z'/>"+
        									"</svg>"+
        									"<div class='dropdown-menu' aria-labelledby='dropdownMenuLink'>"
        									+"<button class='dropdown-item' onclick='blackList()' data-email='" + message.participant[i] + "'><i class='fas fa-comment-slash'></i>차단하기</button>"+
        									"<button class='dropdown-item' data-backdrop='static' data-toggle='modal' data-target='#exampleModal' onclick='sendMsg()''><i class='far fa-envelope' style='font-size:1.05rem'></i>쪽지보내기</button>" +
        									"<button class='dropdown-item' data-backdrop='static' data-toggle='modal' data-target='#exampleModal' onclick='gotoBlog()'><i class='fab fa-blogger' style='font-size:1.05rem'></i>블로그 보기</button>" +
        									"</div>"  
        									: '') +
        									"</div>"+
        					"</li>")
        				}
        			}
        		}
    		}
    		
    	}
    } else if (message.type === 'LEAVE') {
    	inUser.splice(inUser.indexOf(message.sender),1);
    	if(inUser.length == 1) lastOne = true;
    	console.log(message.sender + ' 메세지센더')
    	console.log($("li[data-name='"+message.sender+"']").attr('data-name') + ' 나간 사람 아이디');
    	outer = $("li[data-name='"+message.sender+"']").attr('data-name');
        $("li[data-name='"+message.sender+"']").remove();
        outUser();
    } else if (message.type === 'ALARM'){
    	if(message.sender == $("#userSessionId").val()){
    		$("#clip").html("<i class='far fa-envelope-open'></i><span style='margin-left:5px' id='count' class='badge badge-danger'>N</span>");
    		$(".navbar-toggler").html("<span class='navbar-toggler-icon'></span><span class='badge badge-danger navbar-badge' style='position: absolute; top: 4px;'>!</span>");
    	}
    } else {
    	getdate();
    	if(message.sender == $("#userSessionId").val()){
    	$(".chat-body").append(" <div class='message my-message'>" +
                "<img alt='' class='img-circle medium-image' src='../image/user.jpg' style='margin-right:15px'>"+
                "<div class='message-body'>" +
                    "<div class='message-body-inner'>"+
                        "<div class='message-info'>"+
                            "<h4>나</h4>"+
                            "<h5> <i class='far fa-clock'></i>"+NowHours + " : " +NowMinutes+ "</h5>"+
                        "</div>"+
                        "<hr>"+
                        "<div class='message-text'>"+message.content+"</div>"+
                    "</div>"+
                "</div>"+
                "<br>"+
            "</div>");
    	$("li[data-name='"+message.sender+"'] h5").text(message.content);
    	$(".chat-body").scrollTop($(".chat-body")[0].scrollHeight);
    	} else {
    		getdate();
    		
    		for(i = 0; i<blackListv.length; i++){
    			if(blackListv[i].blackuuid == message.uuid){
    				blackUser = false;
    				break;
    			}
    		}
    		
    		if(blackUser){
    			$(".chat-body").append(
    					"<div class='message info'>"+
    					"<img alt='' class='img-circle medium-image' src='../image/user.jpg'>"+
    					"<div class='message-body'>"+
    					"<div class='message-info'>"+
    					"<h4>" + "<div class='dropdown others'>" +
    					"<a class='stretched-link othersMsg' href='#' style='color:white' role='button' id='dropdownMenuLink' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false' data-uuid='"+message.uuid+"' data-email='" + message.sender + "'>"
    					+ message.sender +
    					"</a>" +
    					"<div class='dropdown-menu' aria-labelledby='dropdownMenuLink'>"
    					+"<button class='dropdown-item' onclick='blackList()' data-email='" + message.sender + "'>차단하기</button>"+
    					"<button class='dropdown-item' data-backdrop='static' data-toggle='modal' data-target='#exampleModal' onclick='sendMsg()' data-uuid='"+message.uuid+"'>쪽지보내기</button>" +
    					"</div>" 
    					+ "</div>" + "</h4>"+
    					"<h5> <i class='far fa-clock'></i> "+NowHours + " : " +NowMinutes+ "</h5>"+
    					"</div>"+
    					"<hr>"+
    					"<div class='message-text'>"+
    					message.content+
    					"</div>"+
    					"</div>"+
    					"<br>"+
    			"</div>");
    			$("li[data-name='"+message.sender+"'] h5").text(message.content);
    			$(".chat-body").scrollTop($(".chat-body")[0].scrollHeight);
    		}
    	}
    	$(".send-message-text").val('');
    }
}

//usernameForm.addEventListener('submit', connect, true)
//messageForm.addEventListener('submit', sendMessage, true)

$(document).on('click',".othersMsg" ,function(){
	nick = $(this).attr('data-email');
	otherUUid = $(this).attr('data-uuid');
	
	console.log(nick + "///nick 클릭시")
});

function loadPage(){
	userNick = $("#userSessionId").val()
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

function blackList(){
	if(confirm("차단하겠습니까?")){
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
					$("div.dropdown-menu button[data-email="+nick+"]").html("<i class='fas fa-comment-slash'></i>차단해제");
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
					$("div.dropdown-menu button[data-email="+nick+"]").html("차단하기");
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

function sendToOther(){
	console.log(nick + "///// 이것은 nick이다")
	$.ajax({
		url: "/my/sendOther",
		type: "POST",
		cache: false,
		data : "&membernick="+nick,
		success: function(data, status){
			if(status == "success"){
				otherUUid = data;
			}
		}
	});
	$(".recep").val(nick);
}

function sendToMsg(){
	
	if($("#message-subject").val().trim().length == 0){
		toastr.success("제목은 반드시 입력해야 합니다", "오류", {"iconClass": 'customer-info'});
	} else {
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
					sendAlarm();
					$("#message-text").val(''),
					$("#message-subject").val(''),
					$(".modal").hide();
					$("body > div.modal-backdrop.fade.show").remove();
				}
			}
		})
	}
}

function gotoBlog(){
	$.ajax({
		url:"/my/gotoBlog",
		type:"POST",
		cache : false,
		data : "membernick="+nick,
		success:function(data,status){
			if(status == "success"){
				location.href = "/travel/share_travel?uuid="+data;
			}
		}
	})
}

/*
 * stopm 와 socketjs 를 사용하지 않은 단순 websocket버전 채팅 ( 이전 버전 )
 * 
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





