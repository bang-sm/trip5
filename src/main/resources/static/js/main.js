function fnMove(seq) {
	var offset = $("#con" + seq).offset();
	$('html, body').animate({
		scrollTop : offset.top
	}, 400);
}

var buttonNum = $(".listbutton").val();
var listMaxLength = 10;
console.log(buttonNum);

function getTravelList(buttonNum){

	$.ajax({
		type : "POST",
		url: "/weather/weatherModal",
		dataType: 'JSON',
		// async: false,
		data: {
			"buttonNum" : buttonNum
		},
		success: function(data){
		
		console.log(data);
	
		var htmlString = "";

		if(data.length <= listMaxLength){
			listMaxLength = data.length;
		}

		for(var i = 0; i < listMaxLength; i++){

			htmlString += "<div class='col-lg-3 col-md-4 col-sm-6 ap'>";
			htmlString += "<div class='h_gallery_item'>";
			htmlString += "<img src='" + data[i] + "' width='210' height='300'>";
			htmlString += "<div class='hover'>";
			htmlString += "<a href='/travel/travel_blog?uuid=28&amp;tsid=1126'>";
			htmlString += "<h4>새로운일지</h4>";
			htmlString += "</a>";
			htmlString += "<a class='light' href='/travel/travel_blog?uuid=28&amp;tsid=1126'>";
			htmlString += "<i class='fa fa-heart' style='font-size: 17px'> 41 </i>";
			htmlString += "<i class='fa fa-bookmark' style='font-size: 17px'> 1 </i>";
			htmlString += "</a></div></div></div>";

		}
	
		}, error : function(request,status,error){
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
			  
		}); 

}