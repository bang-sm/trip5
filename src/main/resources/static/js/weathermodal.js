$(function(){

	getWeatherajax();
	$("#weathermodalbutton").click();
	  
});

$(".weather-wrapper").draggable();

// $("#myModal").draggable();

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

function modalMinize(SKY, PTY){

	// location
	var weathericon = $("#weathericon");
	var weathercard = $(".weather-card");

	var skyimg = "";

	var cloudy = "<i class='fas fa-cloud'></i>";
	var sunny = "<i class='fas fa-sun'></i>";
	var rainy = "<i class='fas fa-cloud-showers-heavy'></i>";
	var snowy = "<i class='fas fa-snowflake'></i>";
	var maximize = "<i class='far fa-window-maximize'></i>";


	switch(PTY){
		case 0:

			switch(SKY){
				case 1:
					skyimg = sunny;
				break;
				case 3:
					skyimg = cloudy;
				break;
				case 4:
					skyimg = cloudy;
				break;
			}

		break;
		case 1:
			skyimg = rainy;
		break;
		case 2:
			skyimg = snowy;
		break;

	}
	location.attr("src", skyimg);
	
	
}