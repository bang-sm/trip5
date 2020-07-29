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
	
	$.ajax({
		url: "/wish/rest/mypage/following", 
		type : "POST",
		data : {},
		success : function(data){
			console.log(data);
			var followingme = data.followingme;
			var followingyou = data.followingyou;
			result="";
			for(i=0;i<followingme.length;i++){
				result+='<h4>요청 보냄</h4>';
				result+='<li class="list-group-item">';
				result += '<div class="image">';
				result += '	<img src="../image/'+followingme[i].pname+'" class="img-circle img-circle-size" alt="User Image">';
				result += '</div>';
				result += '<div class="info">';
				result += '	<span class="followname">'+followingme[i].membernick+'</span>';
				result += '</div>';
				result += '<div class="send">';
				result +=  '<span class="send">요청을 보냈습니다</span>';
				result += '</div>';
				result += '	</li>';
			}
			var list =$('.followingme-list');
			list.html(result);
			
			result="";
			
			for(i=0;i<followingyou.length;i++){
				result+='<h4>요청 받음</h4>';
				result+='<li class="list-group-item">';
				result += '<div class="user-panel mt-3 pb-3 mb-3 d-flex">';
				result += '<div class="image">';
				result += '	<img src="../image/'+followingyou[i].pname+'" class="img-circle elevation-2" alt="User Image">';
				result += '</div>';
				result += '<div class="info">';
				result += '	<h4 class="d-block">'+followingyou[i].membernick+'</h4>';
				result += '</div>';
				result += '<div class="send">';
				result +=  '<button type="button" class="btn btn-primary">팔로우</button>';
				result += '</div>';
				result += '	</div>';
				result += '	</li>';
				
				
				
			}
				var list =$('.followingyou-list');
				list.html(result);
			
		}
	});
	
	$.ajax({
		url: "/wish/rest/mypage/follower", 
		type : "POST",
		data : {},
		success : function(data){
			console.log(data);
			result ="";
			var follower  = data.follower;
			for(i=0;i<follower.length;i++){
				result+='<tr>';
				result+='<td><div class="user-panel mt-3 pb-3 mb-3 d-flex"><div class="image"><img src="../image/'+follower[i].pname+'"class="img-circle elevation-2" alt="User Image"></div></div></td>';
				result+='<td><div class="info"><h4 class="d-block">'+follower[i].membernick+'</h4></div></td>';
			    result+='<td></td>';
			    result+='</tr>';
			}
			var list =$('.follower-tbody');
			list.html(result);
		}
	});
	
	
	$.ajax({
		url: "/wish/rest/mypage/bookmark", 
		type : "POST",
		data : {},
		success : function(data){
			console.log(data);
			var bookmark = data.bookmark;
			result="";
			for(i=0;i<bookmark.length;i++){
				result+='<tr>';
				result+='<th scope="row">'+(i+1)+'</th>';
				result+='<td>'+bookmark[i].tstitle+'</td>';
				result+='<td>'+bookmark[i].tslike+'</td>';
			    result+='<td>'+bookmark[i].tsregdate+'</td>';
			    result+='</tr>';
			}
			var list=$('.bookmark-tbody');
			list.html(result);
		}
	});
	
	$.ajax({
		url: "/wish/rest/mypage/like", 
		type : "POST",
		data : {},
		success : function(data){
			console.log(data);
			$('.like-count').text(data.count);
			var like = data.like;
			result="";
			for(i=0;i<like.length;i++){
				result+='<tr>';
				result+='<th scope="row">'+(i+1)+'</th>';
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
	

});




