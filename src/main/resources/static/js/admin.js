$(document).ready(function() {

	var data = [ $(".kakaoLogin").text(), $(".trip5Login").text() ];
	console.log(data);

	// 가운데가 빈 원형 차트
	userType = {
		datasets : [ {
			backgroundColor : [ 'yellow', 'green' ],
			data : data
		} ],
		// 라벨의 이름이 툴팁처럼 마우스가 근처에 오면 나타남
		labels : [ 'kakao', 'trip5' ]
	};

	var ctx2 = document.getElementById("myPieChart");
	var myDoughnutChart = new Chart(ctx2, {
		type : 'pie',
		data : userType,
		options : {
			maintainAspectRatio : false,
			showDatasetLabels : true,
			legend : {
				display : true,
				position : 'bottom',
				labels : {
					fontFamily : "myriadpro-regular",
					boxWidth : 15,
					boxHeight : 2
				}
				
			}
		}
	});
})
