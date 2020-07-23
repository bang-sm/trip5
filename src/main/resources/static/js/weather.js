var MAX_DEPTH = 1;   // depth 0 ~ 1
var nowWeather = new Map();

var timeArr = new Array();
var dateArr = new Array();
var finalLocation = ""; // 나중에 DB로 보낼 locationuid

var defaultlocaluid = 0;
// var defaultlocalny = 0;

$(document).ready(function(){

	// Session 에서 uuid 가져오기
	getDefaultLocal();

	// 로딩시 default 값으로 페이지 로딩

	if(defaultlocaluid == 0){

		setDefaultWeather1(1);

	} else {
		
		getDefaultLocalInfo(defaultlocaluid);
	}


	var now = new Date();

	var nowMonth = parseInt(now.getMonth()) + 1 ;
	var nowDate = parseInt(now.getDate());
	
	$("#nowDate").html(nowMonth + " 월 " + nowDate + " 일");

	$("#first_local").click(function(){
		$("#hiddenOption1").hide();
	});

	$("#first_local").change(function(){ // 첫번째 칸 선택시

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


	$("#saveuid").click(function(){
		saveDefaultLocal(finalLocation);
	});

});

function getCategory(localuid){

	if(parent == undefined) localparent = 0; 

	$.ajax({
			type : "POST",
			url: "/weather/depth2",
			dataType: 'JSON',
			async: false,
			data: {
				"weatherparentuid" : localuid
			},
			success: function(data){
					buildSelect(data);
				}
						
			}); 
	
	$("#second_local").removeAttr("disabled");
	
} // end getCategory()	

	
function buildSelect(data){
	
		var elm = $("select#second_local");
		var list = data;

		var result = "<option id='hiddenOption2'> 지역을 선택하세요 </option>";
		for(i = 0; i < list.length; i++){
			result += "<option value1 = '" + list[i].localnx + "' value2 = '" + list[i].localny + "' value3 = '" + list[i].localname + "' value4 = '" + list[i].localuid +"'>";
			result += list[i].localname;
			result += "</option>";
		}
		
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
				
				sortNowWeatherData(data); // nowWeather map setting 완료!!
				// console.log("sortNowWeatherData 완료! - Map 사용!");
				
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
	var fsctDateArr = getfsctDate(data);

	// console.log("리턴 받은 배열의 길이 : " + fsctDateArr.length);
	// console.log(fsctDateArr[0]);

	for(var index = 0; index < 6; index++){

		setTime = startTime + (300 * (index + 1));

		if(setTime >= 2400){
			setTime -= 2400;
			setTimeString = "내일 " +  String(setTime/100) + ":00";
			update = 1;
			
		} else {
			setTimeString = String (setTime/100) + ":00";
		}

		$("#nowTime" + String(index + 1)).html(setTimeString);
		
		timeArr[index] = setTime;		
		dateArr[index] = fsctDateArr[update];
		update = 0; // 초기화

	} // end for

	setWeatherArr(data, timeArr, dateArr);

}

function getfsctDate(data){

	var fsctDateArr = new Array();
	var startDate = data[0].fcstDate;

	fsctDateArr[0] = startDate;

	for(var i = 0; i < data.length; i++){

		var date = data[i].fcstDate;

		if(startDate != date){
			fsctDateArr.push(date);
			startDate = date;
		}
	}

	return fsctDateArr;
}

function setWeatherArr(data, timeArr, dateArr){
	
	var weatherMap = new Map();
	
	for(var i = 0; i < 6; i++){

		var date = dateArr[i];
		var time = timeArr[i];
		
		for(var j = 0; j <data.length; j++){
			
			if(data[j].fcstDate == date && data[j].fcstTime == time){

				var category = data[j].category;
				var value = data[j].fcstValue;

				var tempSKY = -1;
				var tempPTY = -1;
				
				switch (category) {
					
					case "SKY":
						tempSKY = value;
					break;
					case "PTY":
						tempPTY = value;
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

				if(tempPTY != -1){
					weatherMap.set("valuePTY", parseInt(value));
				}

				if(tempSKY != -1){
					weatherMap.set("valueSKY", parseInt(value));
				}
				
			} //end if
			
		} // end data for

		// console.log("date :" + date);
		// console.log("time :" + time);
		// console.log("PTY :" + weatherMap.get("valuePTY"));
		// console.log("SKY :" + weatherMap.get("valueSKY"));

		setSKY(weatherMap.get("valueSKY"), weatherMap.get("valuePTY"), $("#SKY" + String( 1 + i )));

		weatherMap.set("valuePTY", -1);
		weatherMap.set("valueSKY", -1);

	} // end 표출 arr for

} // end setWeatherArr()

function saveDefaultLocal(finalLocation){

	if(finalLocation != null || finalLocation != ""){
		
		$.ajax({
			type : "POST",
			url: "/weather/updateuid",
			async: false,
			data: {
				"weatherlocaluid" : finalLocation
			},
			success: function(data){
				if(data == "OK"){
					toastr.success("등록되었습니다"); 
				}
				
			},
			error : function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
	
		}); // end ajax

	}
	
}

function getDefaultLocal(){
	
		$.ajax({
			type : "POST",
			url: "/weather/getuid",
			async: false,
			success: function(data){
				// Session 에 uuid 없으면, 

				if(data == 0){
					defaultlocaluid = 0; // default: 서울
				} else {
					
					defaultlocaluid = (data*1);
				}

			},
			error : function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		}); // end ajax
} // end getDefaultLocal

function setDefaultWeather1(defaultlocaluid){

	$("#first_local > option[value3="+ defaultlocaluid +"]").attr("selected", "selected");

	var localnx = $("#first_local > option:selected").attr("value1");
	var localny = $("#first_local > option:selected").attr("value2");
	var localname = $("#first_local > option:selected").attr("value4");

	var locationdiv = $("#location1");

	getWeatherAPI(localnx, localny);
	locationdiv.html(localname);
	getCategory(defaultlocaluid);
	finalLocation = defaultlocaluid;

} // end setDefaultWeather()


function setDefaultWeather2(defaultlocaluid, parentuid){

	$("#first_local > option[value3="+ parentuid +"]").attr("selected", "selected");
	var localname1 = $("#first_local > option:selected").attr("value4");
	var locationdiv1 = $("#location1");
	
	getCategory(parentuid);
	locationdiv1.html(localname1);

	$("#second_local > option[value4 = "+ defaultlocaluid +"]").attr("selected", "selected");

	var localnx = $("#second_local > option:selected").attr("value1");
	var localny = $("#second_local > option:selected").attr("value2");
	var localname = $("#second_local > option:selected").attr("value3");
	var localuid = $("#second_local > option:selected").attr("value4");

	var locationdiv = $("#location2");
	locationdiv.html(localname);

	$("#weatherlocalnx").val(localnx);
	$("#weatherlocalny").val(localny);
	getWeatherAPI(localnx, localny);

	finalLocation = localuid;

} // end setDefaultWeather()


function getDefaultLocalInfo(defaultlocaluid){

	if(defaultlocaluid != 0 || defaultlocaluid != null){

		$.ajax({
			type : "POST",
			url: "/weather/getlocalinfo",
			// async: false,
			data: {
				"localuid" : defaultlocaluid
			},
			success: function(data){
				
				// depth 구별 - 0 일 경우
				//				1 일 경우
				var depth = data.localdepth;
				var parent = data.localparent;

				switch (depth) {
					case 0:
						setDefaultWeather1(defaultlocaluid);
						break;
					case 1:
						setDefaultWeather2(defaultlocaluid, parent);
						break;
				
					default:
						break;
				}
				
			},
			error : function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
	
		}); // end ajax
	}
} // end getDefaultLocalInfo()

//////////////////////////////////////////////////////////////////////////////////////////////
// CHART JS ///////////////////////////////////// CHART JS ///////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////

// var gradientChartOptionsConfigurationWithTooltipPurple = {
// 	maintainAspectRatio: false,
// 	legend: {
// 	  display: false
// 	},

// 	tooltips: {
// 	  backgroundColor: '#f5f5f5',
// 	  titleFontColor: '#333',
// 	  bodyFontColor: '#666',
// 	  bodySpacing: 4,
// 	  xPadding: 12,
// 	  mode: "nearest",
// 	  intersect: 0,
// 	  position: "nearest"
// 	},
// 	responsive: true,
// 	scales: {
// 	  yAxes: [{
// 		barPercentage: 1.6,
// 		gridLines: {
// 		  drawBorder: false,
// 		  color: 'rgba(29,140,248,0.0)',
// 		  zeroLineColor: "transparent",
// 		},
// 		ticks: {
// 		  suggestedMin: 60,
// 		  suggestedMax: 125,
// 		  padding: 20,
// 		  fontColor: "#2380f7"
// 		}
// 	  }],

// 	  xAxes: [{
// 		barPercentage: 1.6,
// 		gridLines: {
// 		  drawBorder: false,
// 		  color: 'rgba(29,140,248,0.1)',
// 		  zeroLineColor: "transparent",
// 		},
// 		ticks: {
// 		  padding: 20,
// 		  fontColor: "#2380f7"
// 		}
// 	  }]

// 	}
// };

// var gradientChartOptionsConfigurationWithTooltipBlue = {
// 	maintainAspectRatio: false,
// 	legend: {
// 	  display: false
// 	},

// 	tooltips: {
// 	  backgroundColor: '#f5f5f5',
// 	  titleFontColor: '#333',
// 	  bodyFontColor: '#666',
// 	  bodySpacing: 4,
// 	  xPadding: 12,
// 	  mode: "nearest",
// 	  intersect: 0,
// 	  position: "nearest"
// 	},
// 	responsive: true,
// 	scales: {
// 	  yAxes: [{
// 		barPercentage: 1.6,
// 		gridLines: {
// 		  drawBorder: false,
// 		  color: 'rgba(29,140,248,0.0)',
// 		  zeroLineColor: "transparent",
// 		},
// 		ticks: {
// 		  suggestedMin: 60,
// 		  suggestedMax: 125,
// 		  padding: 20,
// 		  fontColor: "#2380f7"
// 		}
// 	  }],

// 	  xAxes: [{
// 		barPercentage: 1.6,
// 		gridLines: {
// 		  drawBorder: false,
// 		  color: 'rgba(29,140,248,0.1)',
// 		  zeroLineColor: "transparent",
// 		},
// 		ticks: {
// 		  padding: 20,
// 		  fontColor: "#2380f7"
// 		}
// 	  }]
// 	}
// };

var  gradientBarChartConfiguration = {
	maintainAspectRatio: false,
	legend: {
	  display: false
	},

	tooltips: {
	  backgroundColor: '#f5f5f5',
	  titleFontColor: '#333',
	  bodyFontColor: '#666',
	  bodySpacing: 4,
	  xPadding: 12,
	  mode: "nearest",
	  intersect: 0,
	  position: "nearest"
	},
	responsive: true,
	scales: {
	  yAxes: [{

		gridLines: {
		  drawBorder: false,
		  color: 'rgba(29,140,248,0.1)',
		  zeroLineColor: "transparent",
		},
		ticks: {
		  suggestedMin: 60,
		  suggestedMax: 120,
		  padding: 20,
		  fontColor: "#9e9e9e",
		  beginAtZero:true
		}
	  }],

	  xAxes: [{

		gridLines: {
		  drawBorder: false,
		  color: 'rgba(29,140,248,0.1)',
		  zeroLineColor: "transparent",
		},
		ticks: {
		  padding: 20,
		  fontColor: "#9e9e9e"
		}
	  }]
	}
};

// var chart_labels = ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEP', 'OCT', 'NOV', 'DEC'];
var chart_labels = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20];
var chart_data = [100, 70, 90, 70, 85, 60, 75, 60, 90, 80, 110, 100];


var ctx = document.getElementById("chartBig1").getContext('2d');

var gradientStroke = ctx.createLinearGradient(0, 230, 0, 50);

gradientStroke.addColorStop(1, 'rgba(72,72,176,0.1)');
gradientStroke.addColorStop(0.4, 'rgba(72,72,176,0.0)');
gradientStroke.addColorStop(0, 'rgba(119,52,169,0)'); //purple colors

var config = {
	type: 'bar',
	responsive: true,
	legend: {
	  display: false
	},
	data: {
	  labels: ['USA', 'GER', 'AUS', 'UK', 'RO', 'BR'],
	  datasets: [{
		label: "Countries",
		fill: true,
		backgroundColor: gradientStroke,
		hoverBackgroundColor: gradientStroke,
		borderColor: '#1f8ef1',
		borderWidth: 2,
		borderDash: [],
		borderDashOffset: 0.0,
		data: [53, 20, 10, 80, 100, 45],
	  }]
	},
	options: gradientBarChartConfiguration
};

var myChartData = new Chart(ctx, config);

$("#0").click(function() {
  var data = myChartData.config.data;
  data.datasets[0].data = chart_data;
  data.labels = chart_labels;
  myChartData.update();
});

$("#1").click(function() {
  var chart_data = [80, 120, 105, 110, 95, 105, 90, 100, 80, 95, 70, 120];
  var data = myChartData.config.data;
  data.datasets[0].data = chart_data;
  data.labels = chart_labels;
  myChartData.update();
});

$("#2").click(function() {
  var chart_data = [60, 80, 65, 130, 80, 105, 90, 130, 70, 115, 60, 130];
  var data = myChartData.config.data;
  data.datasets[0].data = chart_data;
  data.labels = chart_labels;
  myChartData.update();
});



