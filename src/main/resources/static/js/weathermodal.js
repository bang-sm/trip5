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
      var sky = weatherMap.SKY + "";
	  var pty = weatherMap.PTY + "";
	  var temp = weatherMap.TEMP;
	  console.log("temp : " + temp);


	  var location = weatherMap.localname;
	  var parantLocation = weatherMap.parentName;
      
		setTEMP(temp, $("#temp"));

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

	var sunny = "";
	var cloudy = "";
	var rainy = "";

	var skyimg;

	switch(PTY){
		
		case 0:

			switch(SKY){
				case 1:
					skyimg = sunny;
				break;

				case 3:
				case 4:
					skyimg = cloudy;
				break;
			}

		break;

		case 1:
		case 2:
			skyimg = rainy;
		break;

	}
	location.attr("src", skyimg);
}

function setTEMP(TEMP, location){

	var inputTEMP = String(TEMP);
	var tempText ="";

	tempText += inputTEMP;
	tempText += "<sup>o</sup>C";

	location.html(tempText);
}