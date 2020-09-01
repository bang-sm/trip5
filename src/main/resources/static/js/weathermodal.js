$(function(){
	getWeatherajax();
	
});



$(".weather-wrapper").draggable();

	$("#weather-button").click( function(){
		modalMinimize();
	});
	
	$("#maxIcon").click(function(){
		modalMaximize();
	});

	function getCurrentTime(){
		var currentDate = new Date();
		var msg = "";
		var hour = currentDate.getHours() + "";
		var minute = currentDate.getMinutes() + "";
	
		if(hour.length == 1){
			msg += "0";
			msg += hour;
		} else {
			msg += hour;
		}
	
		if(minute.length == 1){
			msg += "0";
			msg += minute;
		} else {
			msg += minute;
		}
	
		return msg;
	}
	
	function getCurrentDate(){
	
		var currentDate = new Date();
		var month = currentDate.getMonth()+1 + "";
		var day = currentDate.getDate() + "";
	
		var msg  = currentDate.getFullYear() + "";
		
		if(month.length == 1){
			msg += "0";
			msg += month;
		} else {
			
			msg += month;
		}
	
		if(day.length == 1){
			msg += "0";
			msg += day;
		} else {
			
			msg += day;
		}
	
	
		console.log(msg);
		
	
		return msg;
	}

function getWeatherajax(){

	var currentDate = "";
	var currentTime = ""; 

	currentDate = getCurrentDate();
	currentTime = getCurrentTime();

  $.ajax({
    type : "POST",
    url: "/weather/weatherModal",
    dataType: 'JSON',
    // async: false,
    data: {
		"currentDate" : currentDate,
		"currentTime" : currentTime
    },
    success: function(data){

	console.log(data);

	  var weatherMap = new Map();
	  weatherMap = data;

      var pop = weatherMap.POP*1;
      console.log("pop : " + pop);
      var sky = weatherMap.SKY*1;
	  var pty = weatherMap.PTY*1;
	  var temp = weatherMap.TEMP;
	  console.log("temp : " + temp);
	  console.log("pty : " + pty);
	  console.log(typeof(pty));
	  var location = weatherMap.localname;
	  var parantLocation = weatherMap.parentName;
      
		setTEMP(temp, $("#temp"));
		setSKY(sky, pty, $("#weathericon"));
		
		if(parantLocation == null || parantLocation == ""){
			$("#location1").html(location);
		} else if (parantLocation != null || parantLocation != ""){
			$("#location1").html(parantLocation);
			$("#location2").html(location);
		}

    }, error : function(request,status,error){
		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	}
          
    }); 

} // end getWeatherajax        

function setSKY(SKY, PTY, location){

	var sunny = "weather-icon sun";
	var rainy = "weather-icon rainy";
	var cloud = "weather-icon cloud";

	var skyimg;

	switch(PTY){
		
		case 0:

			switch(SKY){
				case 1:
					skyimg = sunny;
				break;

				case 3:
				case 4:
					skyimg = cloud;
				break;
			}

		break;

		case 1:
		case 2:
			skyimg = rainy;
		break;

	}
	location.attr("class", skyimg);
}

function setTEMP(TEMP, location){

	var inputTEMP = String(TEMP);
	var tempText ="";

	tempText += inputTEMP;
	tempText += "<sup>o</sup>C";

	location.html(tempText);
}

function modalMinimize(){

	var hiddenbox = $("#hiddenbox");
	var weathercard = $(".weather-card");

	hiddenbox.attr("hidden", "hidden");
	weathercard.css("height", "30px");
	$("#maxIcon").removeAttr("hidden");
}

function modalMaximize(){

	var hiddenbox = $("#hiddenbox");
	var weathercard = $(".weather-card");

	weathercard.css("height", "180px");
	hiddenbox.removeAttr("hidden");
	$("#maxIcon").attr("hidden", "hidden");
}