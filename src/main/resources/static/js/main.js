var buttonNum = 0;
var listMaxLength = 8;
var listsizeup = 1;
var privatelength = 8;

$(function(){
	getTravelList(0, listMaxLength);
});

function fnMove(seq) {
	var offset = $("#con" + seq).offset();
	$('html, body').animate({
		scrollTop : offset.top
	}, 400);
}

// 버튼 선택
$(document).on('click', '.listbutton', function(){
	buttonNum = $(this).val();
	getTravelList(buttonNum, listMaxLength);
});


// 더보기 선택 시
$(document).on('click', '.button_more', function(){
	sizeup();
	getTravelList(buttonNum, listMaxLength);
});


function getTravelList(buttonNum, listMaxLength){

	$.ajax({
		type : "POST",
		url: "/mainlist",
		async: false,
		data: {
			"buttonNum" : buttonNum
		},
		success: function(data){
		var htmlString = "";
		if(data.length <= listMaxLength){
			listMaxLength = data.length;
		}

		console.log("list 안 : " + listMaxLength);
		for(var i = 0; i < listMaxLength; i++){
			htmlString += "<div class='col-lg-3 col-md-4 col-sm-6 ap'>";
			htmlString += "<div class='h_gallery_item'>";
			if(data[i].photoMain != null){
				htmlString += "<img src='/resources/upload/" + data[i].photoMain + "' width='210' height='300'>";
			} else {
				htmlString += "<img src='/resources/upload/trip.jpg' width='210' height='300'>";
			}
			
			htmlString += "<div class='hover'>";
			htmlString += "<a href='/travel/travel_blog?uuid="+ data[i].uuid +"&amp;tsid="+ data[i].tsid +"'>";
			htmlString += "<h4>"+ data[i].tstitle +"</h4>";
			htmlString += "</a>";
			htmlString += "<a class='light' href='/travel/travel_blog?uuid="+ data[i].uuid +"&amp;tsid="+ data[i].tsid +"'>";
			htmlString += "<i class='fa fa-heart' style='font-size: 17px'> "+ data[i].tslike+" </i>";
			htmlString += "<i class='fa fa-bookmark' style='font-size: 17px'> "+ data[i].bookmark+" </i>";
			htmlString += "</a></div></div></div>";
		}

		var location = $("#listlocation");

		location.html(htmlString);
	
		}, error : function(request,status,error){
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
			  
	}); 
}

function sizeup(){

	listsizeup++;

	listMaxLength = listsizeup * privatelength;
	console.log(listsizeup);
	console.log(listMaxLength);

}