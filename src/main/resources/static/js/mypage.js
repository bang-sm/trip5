/**
 * 
 */


$(document).ready(function() {

	btn = $('.following-box'); // 버튼 아이디 변수 선언

	layer = $('.following-list'); // 레이어 아이디 변수 선언

	btn.click(function() {

		layer.toggle(

		function() {
			layer.addClass('show')
		}, // 클릭하면 show클래스 적용되서 보이기

		function() {
			layer.addClass('hide')
		} // 한 번 더 클릭하면 hide클래스가 숨기기

		);

	});
	
	following();
	follower();
	bookmark();
	like();
	blacklist();
	reply();
	recently()
	register(1);
});

$(document).ready(function(){
	  $("#myInput").on("keyup", function() {
	    var value = $(this).val().toLowerCase();
	    $(".follower-tbody tr").filter(function() {
	      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	    });
	  });
	});


$(document).ready(function(){
	$("#myInputfollowing").on("keyup", function() {
		var value = $(this).val().toLowerCase();
		$(".following-tbody tr").filter(function() {
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		});
	});
});


$(document).ready(function(){
	$("#myInputbookmark").on("keyup", function() {
		var value = $(this).val().toLowerCase();
		$(".bookmark-tbody tr").filter(function() {
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		});
	});
});

$(document).ready(function(){
	$("#myInputblacklist").on("keyup", function() {
		var value = $(this).val().toLowerCase();
		$(".blacklist-tbody tr").filter(function() {
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		});
	});
});

$(document).ready(function(){
	$("#myInputreply").on("keyup", function() {
		var value = $(this).val().toLowerCase();
		$(".reply-tbody tr").filter(function() {
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		});
	});
});

$(document).on('click','.follower-btn',function(){
	var tr = $(this).parent().parent();
	var followuuid = tr.children('.td-1').children('.follower-uuid').val();
	console.log($(this));
	var button =$(this);
	$.ajax({
		url: "/wish/rest/mypage/follow/ok", 
		type : "POST",
		data : {
			"follow_uuid" : followuuid
		},
		success : function(data){
			console.log(data);
			$('.following-count').text(data+" 명");
			toastr.success("팔로잉 하셨습니다.");
			button.remove();
			following();
			
		}
	});
	
})


$(document).on('click','.following-btn',function(){
	var tr = $(this).parent().parent();
	var name =tr.find('.d-block').text();
	var del_uuid =tr.find('.following-uuid').val();
	console.log(name);
	swal({
		title : name+"회원님",
		text : "팔로잉 취소하시겠습니까?",
		icon : "error",
		closeOnClickOutside : false,
		buttons :{
			cancle : {
				text : "취소",
				value : false
			},
			confirm : {
				text : "삭제",
				value : true
			}
		}
	})
	.then((value) => {
		if(value){
			swal("취소하였습니다.");
			$.ajax({
				url: "/wish/rest/mypage/follow/del", 
				type : "POST",
				data : {
					"follow_uuid" : del_uuid
				},
				success : function(data){
					console.log("성공");
					console.log(data);
					console.log("취소");
					$('.following-count').text(data+" 명");
					tr.remove();
					follower();
				}
			});
		}else{
		}
		});
})

$(document).on('click','.blacklist-btn',function(){
	var tr = $(this).parent().parent();
	var name =tr.find('.d-block').text();
	var del_uuid =tr.find('.following-uuid').val();
	console.log(del_uuid);
	swal({
		title : name+"회원님",
		text : "블랙리스트 취소하시겠습니까?",
		icon : "info",
		closeOnClickOutside : false,
		buttons :{
			cancle : {
				text : "뒤로가기",
				value : false
			},
			confirm : {
				text : "네",
				value : true
			}
		}
	})
	.then((value) => {
		if(value){
			swal("취소하였습니다.");
			$.ajax({
				url: "/wish/rest/mypage/blacklist/del", 
				type : "POST",
				data : {
					"black_uuid" : del_uuid
				},
				success : function(data){
					console.log("성공");
					console.log(data);
					$('.blacklist-count').text(data+" 명");
					tr.remove();
					blacklist();
					
				}
			});
		}else{
		}
		});
})




function following(){
$.ajax({
		url: "/wish/rest/mypage/following", 
		type : "POST",
		data : {},
		async :false,
		success : function(data){
			console.log(data);
			var following = data.following;
			$('.following-count').text(data.following.length+" 명");
			result="";
			for(i=0;i<following.length;i++){
				result+='<tr>';
				result+='<td class="td-1"><input class="following-uuid"type="hidden" name="uuid" value="'+following[i].uuid+'"/>';
				result+='<a href="/travel/share_travel?uuid='+following[i].uuid+'"><img src="/resources/upload/userProfile/'+following[i].photoPath+'"class="user-img" alt="User Image"></a></td>';
				result+='<td class="td-2"><a href="/travel/share_travel?uuid='+following[i].uuid+'"><div class="info"><span class="d-block d-block-yes">'+following[i].membernick+'</span></div></a></td>';
			    result+='<td class="td-3"><button type="button" class="btn btn-outline-dark following-btn">팔로잉</button></td>';
			    result+='</tr>';
			}
			var list =$('.following-tbody');
			list.html(result);
		}
	});
}

function follower(){
	$.ajax({
		url: "/wish/rest/mypage/follower", 
		type : "POST",
		data : {},
		async :false,
		success : function(data){
			console.log(data);
			result ="";
			$('.follower-count').text(data.follower.length+" 명");
			var follower  = data.follower;
			for(i=0;i<follower.length;i++){
				result+='<tr>';
				result+='<td class="td-1"><input class="follower-uuid"type="hidden" name="uuid" value="'+follower[i].uuid+'"/><a href="/travel/share_travel?uuid='+follower[i].uuid+'"><img src="/resources/upload/userProfile/'+follower[i].photoPath+'"class="user-img" alt="User Image"></a></td>';
				result+='<td class="td-2"><a href="/travel/share_travel?uuid='+follower[i].uuid+'"><div class="info"><span class="d-block d-block-yes">'+follower[i].membernick+'</span></div></a></td>';
			   if(follower[i].status=='N' || follower[i].status==null){
				   result+='<td class="td-3"><button type="button" class="btn btn-primary follower-btn">팔로우</button></td>';
			   }
			    result+='</tr>';
			}
			var list =$('.follower-tbody');
			list.html(result);
		}
	});
}
	
function bookmark(){
	$.ajax({
		url: "/wish/rest/mypage/bookmark", 
		type : "POST",
		data : {},
		async :false,
		success : function(data){
			console.log(data);
			$('.bookmark-count').text(data.bookmark.length+" 개");
			var bookmark = data.bookmark;
			result="";
			for(i=0;i<bookmark.length;i++){
				result+='<tr>';
				result+='<td class="col-one-bookmark"><a href="/travel/travel_blog?uuid='+bookmark[i].uuid+'&tsid='+bookmark[i].tsid+'">'+bookmark[i].tstitle+'</a></td>';
				result+='<td class="col-two-bookmark">'+bookmark[i].membernick+'</td>';
			    result+='<td class="col-thr-bookmark">'+bookmark[i].tsregdate+'</td>';
			    result+='</tr>';
			}
			var list=$('.bookmark-tbody');
			list.html(result);
		}
	});
}

function like(){
	$.ajax({
		url: "/wish/rest/mypage/like", 
		type : "POST",
		data : {},
		async :false,
		success : function(data){
			console.log(data);
			$('.like-count').text(data.count+" 개");
			var like = data.like;
			result="";
			for(i=0;i<like.length;i++){
				result+='<tr>';
				if(i==0){
					result+='<th scope="row" class="col-like-one">TOP</th>';
				}else{
					result+='<th scope="row" class="col-like-one">'+(i+1)+'</th>';
				}
				result+='<td class="col-like-two"><a href="/travel/travel_blog?uuid='+like[i].uuid+'&tsid='+like[i].tsid+'">'+like[i].tstitle+'</a></td>';
				result+='<td class="col-like-thr">'+like[i].tslike+'</td>';
			    result+='<td class="col-like-for">'+like[i].tsregdate+'</td>';
			    result+='</tr>';
				if(i==4){
					break;
				}
			}
			
			var list=$('.like-tbody');
			list.html(result);
			
		}
	});
}

function blacklist(){
	$.ajax({
			url: "/wish/rest/mypage/blacklist", 
			type : "POST",
			data : {},
			async :false,
			success : function(data){	
				console.log(data);
				var blacklist = data.blacklist;
				result="";
				for(i=0;i<blacklist.length;i++){
					result+='<tr>';
					result+='<td class="td-1"><input class="following-uuid"type="hidden" name="uuid" value="'+blacklist[i].uuid+'"/>';
					result+='<img src="/resources/upload/userProfile/'+blacklist[i].photoPath+'"class="user-img" alt="User Image"></td>';
					result+='<td class="td-2"><div class="info"><span class="d-block d-block-yes">'+blacklist[i].membernick+'</span></div></td>';
				    result+='<td class="td-3"><button type="button" class="btn btn-outline-dark blacklist-btn">봐준다</button></td>';
				    result+='</tr>';
				}
				var list =$('.blacklist-tbody');
				list.html(result);
			}
		});
	}





function reply(){
	$.ajax({
			url: "/wish/rest/mypage/reply", 
			type : "POST",
			data : {},
			async :false,
			success : function(data){
				console.log("reply 성공");
				console.log(data);
				var reply = data.reply;
				result="";
				for(i=0;i<reply.length;i++){
					result+='<tr>';
					result+='<td class="col-one"><a href="/travel/travel_blog?tsid='+reply[i].tsid+'&uuid='+reply[i].uuid+'">'+reply[i].tstitle+'</a></td>';
					result+='<td class="col-two reply-comment">'+reply[i].tsReplyComment+'</td>';
				    result+='<td class="col-thr">'+reply[i].replyRegdate+'</td>';
				    result+='</tr>';
				}
				var list =$('.reply-tbody');
				list.html(result);
			}
		});
	}

function recently(){
	$.ajax({
		url: "/wish/rest/selectView", 
		type : "POST",
		data : {},
		async :false,
		success : function(data){
			console.log("recently 성공");
			console.log(data);
			$('.recently-count').text(data.length+" 건");
			result="";
			for(i=0;i<data.length;i++){
				result+='<tr>';
				result+='<td class="col-one">'+data[i].viewDate+'</td>';
				result+='<td class="col-two"><a href='+ data[i].tsUrl+'>' +data[i].tstitle + '</a></td>';
			    result+='<td class="col-thr">'+data[i].membernick+'</td>';
			    result+='</tr>';
			}
			var list =$('.recently-tbody');
			list.html(result);
		}
	});
}

var count =1;
$(document).on('click','.left-button',function(){
	//현재 몇 페이지 ?
	var now_currentPage= $('input[name=currentPage]').val();
	console.log("current :"+now_currentPage)
	
	//1페이지일때 왼쪽클릭시 페이지 없는 창 등장
	
	if(now_currentPage==1){
		toastr.error("첫번째 페이지 입니다");
		return;
	}
	var prev_currentPage = Number(now_currentPage)-count;
	register(prev_currentPage);
	
})


$(document).on('click','.right-button',function(){
	var now_currentPage= $('input[name=currentPage]').val();
	var now_lastPage= $('input[name=lastPage]').val();
	
	if(now_currentPage==now_lastPage){
		toastr.error("마지막 페이지 입니다");
		return;
	}
	var next_currentPage=Number(now_currentPage)+count;
	console.log(next_currentPage);
	register(next_currentPage)
	
})
function register(page){
	$.ajax({
		url: "/wish/rest/mypage/register", 
		type : "POST",
		data : {
			"currentPage" : page
		},
		async :false,
		success : function(data){
			console.log(data);
			result="";
			var boardList = data.boardList;
			
			for(i=0;i<boardList.length;i++){
				result+='<tr>';
				result+='<td  class="register-table">';
				result+='<a href="/travel/travel_blog?tsid='+boardList[i].tsid+'&uuid='+boardList[i].uuid+'" >'+boardList[i].tstitle+'</a>';
				result+='</td>';
				result+='<td  class="register-table">'+boardList[i].tsregdate+'</td>';
				result+='<td  class="register-table">'+boardList[i].tsstartdate+'</td>';
				result+='<td  class="register-table">'+boardList[i].tsenddate+'</td>';
				if(boardList[i].tempsave=='Y'){
					result+='<td class="register-table"><a href="/travel/regist?tsid='+boardList[i].tsid+'"><i class="fas fa-times"></i></td>'
				}else{
					result+='<td class="register-table"><i class="fas fa-check"></i></td>'
				}
				result+='</tr>';
			}
			var list =$('.register-tbody');
			list.html(result);
			$('input[name=currentPage]').val(data.currentPage);
			$('input[name=lastPage]').val(data.lastPage);
			var end = Number(data.lastPage);
			if(end == 0 ){
				$('.pagingnow').text("(0/"+data.lastPage+")");
			}else{
				$('.pagingnow').text("("+data.currentPage+"/"+data.lastPage+")");
			}
			$('.register-count').text(data.totalpage+"개");
		}
		
		
	});
}


