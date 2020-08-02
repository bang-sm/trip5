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


function getWeatherajax(){

  $.ajax({
    type : "POST",
    url: "/weather/weatherModal",
    dataType: 'JSON',
    // async: false,
    data: {
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

	  var location = weatherMap.localname;
	  var parantLocation = weatherMap.parentName;
      
		setTEMP(temp, $("#temp"));
		setSKY(sky, pty, $("#weathericon"));
		if(sky == 3 || sky == 4){
			$(".cloud:after").css('animation' , "");
		}

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
	var rainy = "weather-icon cloud";

	var skyimg;

	switch(PTY){
		
		case 0:

			switch(SKY){
				case 1:
					skyimg = sunny;
				break;

				case 3:
				case 4:
					skyimg = rainy;
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

	weathercard.css("height", "270px");
	hiddenbox.removeAttr("hidden");
	$("#maxIcon").attr("hidden", "hidden");
}