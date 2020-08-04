/**
 * 
 */

$(document).ready(function(){
	var newURL = window.location.pathname;
	console.log("sidebar 현재 url : "+newURL);
	
	switch(newURL){
	case "/user/inform" :
		$(".myinfo-title").addClass('active');
			break;
	case "/wish/place" :
	case "/wish/placechart" :
		$('.wish-title').addClass('active');
		break;
	case "/my/clipSend"	:
		$('.chat-title').addClass('active');
		break;
	case "/mypage":
		$('.my-title').addClass('active');
		break;
		default:
			break;
	}
	
})