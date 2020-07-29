$(function(){

  
});

$("#modal").draggable({
  handle: ".modal-header"
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

      var weatherMap = new Map();

      var pop = weatherMap.get("POP")*1;
      console.log("pop : " + pop);
      var sky = weatherMap.get("SKY")*1;
      var 
      

      

    }
          
    }); 

} // end getWeatherajax        

function setSKY(SKY, PTY, location){

	var sunny = "../image/weather/sunny.svg";
	var sun_cloud = "../image/weather/sun_cloud.svg";
	var cloudy = "../image/weather/cloudy.svg";
	var rainy = "../image/weather/rainy.svg";
	var snowy = "../image/weather/snowy.svg";

	var skyimg;

	switch(PTY){
		case 0:

			switch(SKY){
				case 1:
					skyimg = sunny;
				break;
				case 3:
					skyimg = sun_cloud;
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

function setTEMP(TEMP, location){

	var inputTEMP = String(TEMP);
	var tempText ="";

	tempText += inputTEMP;
	tempText += "<sup>o</sup>C";

	location.html(tempText);
}

function setPOP(POP, location){
	var inputPOP = "";
	inputPOP += "<img src='../image/weather/icon-umberella.png' alt=''>";
	inputPOP += "&nbsp;";
	inputPOP += String (POP) + "%";

	location.html(inputPOP);
}

function setWSD(WSD, location){
	var inputWSD = "";
	inputWSD += "<img src='../image/weather/icon-wind.png' alt=''>";
	inputWSD += String(WSD) + "km/h";

	location.html(inputWSD);
}

function setVEC(VEC, location){
	var inputVEC = "";
	var result = parseInt(VEC/ 22.5);

	inputVEC += "<img src='../image/weather/icon-compass.png' alt=''>";

	switch(result){
		case 0:
		case 15:
			inputVEC += "N";
		break;

		case 1:
		case 2:
			inputVEC += "NE";
		break;

		case 3:
		case 4:
			inputVEC += "E";
		break;

		case 5:
		case 6:
			inputVEC += "SE";
		break;

		case 7:
		case 8:
			inputVEC += "S";
		break;

		case 9:
		case 10:
			inputVEC += "SW";
		break;

		case 11:
		case 12:
			inputVEC += "W";
		break;

		case 13:
		case 14:
			inputVEC += "NW";
		break;

	}

	location.html(inputVEC);
}