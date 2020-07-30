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
			console.log("성공");
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
					tr.remove();
					follower();
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
			result="";
			for(i=0;i<following.length;i++){
				result+='<tr>';
				result+='<td class="td-1"><input class="following-uuid"type="hidden" name="uuid" value="'+following[i].uuid+'"/>';
				result+='<img src="../image/'+following[i].pname+'"class="user-img" alt="User Image"></td>';
				result+='<td class="td-2"><div class="info"><span class="d-block">'+following[i].membernick+'</span></div></td>';
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
			var follower  = data.follower;
			for(i=0;i<follower.length;i++){
				result+='<tr>';
				result+='<td class="td-1"><input class="follower-uuid"type="hidden" name="uuid" value="'+follower[i].uuid+'"/><img src="../image/'+follower[i].pname+'"class="user-img" alt="User Image"></td>';
				result+='<td class="td-2"><div class="info"><span class="d-block">'+follower[i].membernick+'</span></div></td>';
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
			var bookmark = data.bookmark;
			result="";
			for(i=0;i<bookmark.length;i++){
				result+='<tr>';
				result+='<td class="col-one">'+bookmark[i].tstitle+'</td>';
				result+='<td class="col-two">'+bookmark[i].membernick+'</td>';
			    result+='<td class="col-thr">'+bookmark[i].tsregdate+'</td>';
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
			$('.like-count').text(data.count);
			var like = data.like;
			result="";
			for(i=0;i<like.length;i++){
				result+='<tr>';
				if(i==0){
					result+='<th scope="row">TOP</th>';
				}else{
					result+='<th scope="row">'+(i+1)+'</th>';
				}
				result+='<td>'+like[i].tstitle+'</td>';
				result+='<td>'+like[i].tslike+'</td>';
			    result+='<td>'+like[i].tsregdate+'</td>';
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
