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
	registchart();
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
					result+='<th scope="row" class="col-like-one">TOP</th>';
				}else{
					result+='<th scope="row" class="col-like-one">'+(i+1)+'</th>';
				}
				result+='<td class="col-like-two">'+like[i].tstitle+'</td>';
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
					result+='<img src="../image/'+blacklist[i].pname+'"class="user-img" alt="User Image"></td>';
					result+='<td class="td-2"><div class="info"><span class="d-block">'+blacklist[i].membernick+'</span></div></td>';
				    result+='<td class="td-3"><button type="button" class="btn btn-outline-dark blacklist-btn">봐준다</button></td>';
				    result+='</tr>';
				}
				var list =$('.blacklist-tbody');
				list.html(result);
			}
		});
	}



function registchart(){
	var line_ctx = $('#mypagelineChart');
	$.ajax({
		url: "/wish/rest/registchart", 
		type : "POST",
		data : {},
		success : function(data){
		
			var arr = new Array();
			
			for(i=0;i<data.length;i++){
				arr[i] =data[i];
			}
			var linemax = arr[0];
			for(i=0;i<arr.length-1;i++){
				if(linemax < arr[i+1]){
					linemax = arr[i+1];
				}
			}
			var lineChartData = {
					 labels: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
				        datasets: [{
				            label: ['월별 현황 그래프'],
				            data: arr,
				            backgroundColor: [
								'rgba(0, 0, 0, 0)'
						],
						borderColor: [
								'rgba(255, 99, 132, 1)',
								'rgba(54, 162, 235, 1)',
								'rgba(255, 206, 86, 1)',
								'rgba(75, 192, 192, 1)',
								'rgba(153, 102, 255, 1)',
								'rgba(255, 159, 64, 1)'
						],
						borderWidth: 2
				        }]
			};
			var myDoughnutChart2 = new Chart(line_ctx, {
				type: 'line',
				data: lineChartData,
				options: {
					maintainAspectRatio: false,
					responsive: false,
					scales: {
						xAxes: [{
							ticks:{
								fontColor : 'rgba(12, 13, 13, 1)',
								fontSize : 14
							},
							gridLines:{
								color: "rgba(87, 152, 23, 1)",
								lineWidth: 0
							}
						}],
						yAxes: [{
							ticks: {
								min: 0,
								max: linemax,
								stepSize : 1,
								fontSize : 14,
							}
						}]
					}
				}
			});
		}});
	
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
					result+='<td class="col-one">'+reply[i].tstitle+'</td>';
					result+='<td class="col-two">'+reply[i].tsReplyComment+'</td>';
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
				result+='<td  class="register-table">'+boardList[i].tsid+'</td>';
				result+='<td  class="register-table">';
				result+='<a href="/travel/travel_blog?tsid='+boardList[i].tsid+'&uuid='+boardList[i].uuid+'" >'+boardList[i].tstitle+'</a>';
				result+='</td>';
				result+='<td  class="register-table">'+boardList[i].tsregdate+'</td>';
				result+='<td  class="register-table">'+boardList[i].tsstartdate+'</td>';
				result+='<td  class="register-table">'+boardList[i].tsenddate+'</td>';
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
		}
		
		
	});
}


