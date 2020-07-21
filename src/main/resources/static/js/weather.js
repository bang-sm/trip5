var MAX_DEPTH = 1;   // depth 0 ~ 1
var nowWeather = new Map();

var timeArr = new Array();
var dateArr = new Array();


var finalLocation = ""; // 나중에 DB로 보낼 locationuid

$(document).ready(function(){
	alert("로딩 완료");

	$("#first_local").click(function(){
		$("#hiddenOption1").hide();
	});

	$("#first_local").change(function(){ // 첫번째 칸 선택시

		$("#second_local").removeAttr("disabled");
		$("#location2").empty();

		var locationdiv = $("#location1");

		var localnx = $("#first_local > option:selected").attr("value1");
		var localny = $("#first_local > option:selected").attr("value2");
		var localuid = $("#first_local > option:selected").attr("value3");
		var localname = $("#first_local > option:selected").attr("value4");
		// alert("localnx = " + localnx + " localny = " + localny);

		$("#parentuid").val(localuid);
		$("#weatherlocalnx").val(localnx);
		$("#weatherlocalny").val(localny);
		getCategory(localuid);
		getWeatherAPI(localnx, localny);

		locationdiv.html(localname);
		finalLocation = localuid;

	});

	$("#second_local").click(function(){
		$("#hiddenOption2").hide();
	});

	$("#second_local").change(function(){

		$("#second_local").removeAttr("disabled");

		var localnx = $("#second_local > option:selected").attr("value1");
		var localny = $("#second_local > option:selected").attr("value2");
		var localname = $("#second_local > option:selected").attr("value3");
		var localuid = $("#second_local > option:selected").attr("value4");

		// alert("localnx = " + localnx + " localny = " + localny);

		var locationdiv = $("#location2");
		locationdiv.html(localname);

		$("#weatherlocalnx").val(localnx);
		$("#weatherlocalny").val(localny);
		getWeatherAPI(localnx, localny);

		finalLocation = localuid;

	});

	// getCategory(0,0);  // depth0 카테고리 읽기

});

function getCategory(localuid){

	if(parent == undefined) localparent = 0; 

	$.ajax({
			type : "POST",
			url: "/weather/depth2",
			dataType: 'JSON',
			data: {
				"weatherparentuid" : localuid
			},
			success: function(data){
					// for (var index = 0; index < data.length; index++) {
					// 	console.log(data[index].localname);
					// }	

					buildSelect(data);
					
				}
						
			}); 
	
} // end getCategory()	

	
function buildSelect(data){
	
		var elm = $("select#second_local");
			
		// alert("buildSelect");
		var list = data;
		
		// alert(list.length);
		var result = "<option id='hiddenOption2'> 지역을 선택하세요 </option>";
		
		for(i = 0; i < list.length; i++){
		
			result += "<option value1 = '" + list[i].localnx + "' value2 = '" + list[i].localny + "' value3 = '" + list[i].localname + "' value4 = '" + list[i].localuid +"'>";
			result += list[i].localname;
			result += "</option>";
			
		}
		
		// alert(result);
		elm.html(result);
	
	
} // end buildSelect()

function getWeatherAPI(weatherlocalnx, weatherlocalny){

	$.ajax({
		type : "POST",
			url: "/weather/api",
			dataType: 'JSON',
			data: {
				"weatherlocalnx" : weatherlocalnx,
				"weatherlocalny" : weatherlocalny
				},
			success: function(data){
				
				alert("api받아짐");
				sortNowWeatherData(data); // nowWeather map setting 완료!!
				console.log("sortNowWeatherData 완료! - Map 사용!");
				
				// 오늘 날씨 설정
				setSKY(nowWeather.get("nowSKY"), nowWeather.get("nowPTY"), $("#nowSKY"));
				setTEMP(nowWeather.get("nowTEMP") , $("#nowTEMP"));
				setPOP(nowWeather.get("nowPOP"), $("span#nowPOP"));
				setVEC(nowWeather.get("nowVEC"), $("span#nowVEC"));
				setWSD(nowWeather.get("nowWSD"), $("span#nowWSD"));
				
				// 이후 날씨 3hr 6칸 배열
				setTime(data);

			}

	}); // end ajax

} // end getWeatherAPI()

	
function sortNowWeatherData(data){

	var nowDate = data[0].fcstDate;
	var nowTime = data[0].fcstTime;
	
	for(var i = 0; i < data.length; i++){

		if(data[i].fcstDate == nowDate && data[i].fcstTime == nowTime){

			var category = data[i].category;

			switch(category){
				case "POP":
					nowWeather.set("nowPOP", data[i].fcstValue)
				break;
				case "REH":
					nowWeather.set("nowHUM" , data[i].fcstValue);
				break;
				case "PTY":
					if(data[i].fcstValue == 1 || data[i].fcstValue == 4){
						nowWeather.set("nowPTY" , 1); // 비오면 PTY 1 로 설정하고 break;
					} else if (data[i].fcstValue == 2 || data[i].fcstValue == 3) {
						nowWeather.set("nowPTY", 2); // PTY 2는 눈!! 
					} else {
						nowWeather.set("nowPTY", 0);
					}

				break;
				case "SKY":
					nowWeather.set("nowSKY" , data[i].fcstValue);
				break;
				case "T3H":
					nowWeather.set("nowTEMP" , data[i].fcstValue);
				break;
				// case "TMN":
				// 	nowWeather.set("nowMIN" , data[i].fcstValue);
				// break;
				// case "TMX":
				// 	nowWeather.set("nowMAX" , data[i].fcstValue);
				// break;
				case "VEC":
					nowWeather.set("nowVEC" , data[i].fcstValue);
				break;
				case "WSD":
					nowWeather.set("nowWSD" , data[i].fcstValue);
				break;
				
			}

		}

	}

}	

function setSKY(SKY, PTY, location){

	var sunny = "../image/weather/sunny.svg";
	var sun_cloud = "../image/weather/sun_cloud.svg";
	var cloudy = "../image/weather/cloudy.svg";
	var foggy = "../image/weather/foggy.svg";
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

function setTime(data){
	var startTime = parseInt(data[0].fcstTime);
	var startDate = data[0].fcstDate;
	var update = 0;
	
	var setTime = 0;
	var setTimeString = "";
	// console.log(startTime);
	// console.log(startDate);
	var fsctDateArr = getfsctDate(data);
	console.log("리턴 받은 배열의 길이 : " + fsctDateArr.length);
	console.log(fsctDateArr[0]);

	for(var index = 0; index < 6; index++){

		setTime = startTime + (300 * (index + 1));
		// console.log("if 문 전" + setTime);

		if(setTime >= 2400){
			setTime -= 2400;
			setTimeString = "내일 " +  String(setTime/100) + ":00";
			update = 1;
			
		} else {
			setTimeString = String (setTime/100) + ":00";
		}

		// console.log("if문 뒤" + setTime);
		$("#nowTime" + String(index + 1)).html(setTimeString);
		
		timeArr[index] = setTime;		
	
		dateArr[index] = fsctDateArr[update];
		// console.log(fsctDateArr[0]);

		update = 0;

	} // end for

	setWeatherArr(data, timeArr, dateArr);

}

function getfsctDate(data){

	var fsctDateArr = new Array();
	var startDate = data[0].fcstDate;
	// console.log(startDate);

	fsctDateArr[0] = startDate;

	for(var i = 0; i < data.length; i++){

		var date = data[i].fcstDate;

		if(startDate != date){
			// console.log(date);
			fsctDateArr.push(date);
			startDate = date;
		}
	}

	return fsctDateArr;
}

function setWeatherArr(data, timeArr, dateArr){

	for(var i = 0; i < 6; i++){

		var date = dateArr[i];
		var time = timeArr[i];
		
		for(var j = 0; j <data.length; j++){
			
			var valueSKY = 0;
			var valuePTY = 0;

			if(data[j].fcstDate == date && data[j].fcstTime == time){

				var category = data[j].category;
				var value = data[j].fcstValue;

				switch (category) {
					case "SKY":
						valueSKY = value;
					break;
					case "PTY":
						valuePTY = value;
					break;
					case "POP":
						setPOP(value, $("#nowPOP" + String( 1 + i )));
					break;
					case "T3H":
						setTEMP(value, $("#degree" + String( 1 + i)));
					break;
					default:
						break;
				}
			}

			setSKY(valueSKY, valuePTY, $("#SKY" + String( 1 + i )));

		} // end data for



	}// end 표출 arr for
}



