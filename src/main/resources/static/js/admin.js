$(document).ready(function() {
	
	// /////////////////////////////// 일일접속자 막대 그래프
	$.ajax({
		url : "/admin/ajax/adminUserCount",
		type : "POST",
		success : function(data) {
			var ctx = document.getElementById("myBarChart");
			var adminUserCount = [ data[0], data[1] ];
			/*
			 * - Chart를 생성하면서, - ctx를 첫번째 argument로 넘겨주고, - 두번째 argument로 그림을
			 * 그릴때 필요한 요소들을 모두 넘겨줌
			 */
			var myChart = new Chart(ctx, {
			    type: 'bar',
			    data: {
			        labels: ["KAKAO", "TRIP5"],
			        datasets: [{
			            data: adminUserCount,
			            backgroundColor: [
			                'rgba(255, 206, 86, 0.2)',
			                'rgba(75, 192, 192, 0.2)',
			            ],
			            borderColor: [
			                'rgba(255, 206, 86, 1)',
			                'rgba(75, 192, 192, 1)',
			            ],
			            borderWidth: 1
			        }]
			    },
			    options: {
			        maintainAspectRatio: false, // default value. false일 경우 포함된
												// div의 크기에 맞춰서 그려짐.
					legend : {
						display : false,
					},
			        scales: {
			            yAxes: [{
			                ticks: {
			                    beginAtZero:true
			                }
			            }]
			        }

			    }
			})
		}
	}) // Ajax 끝
	
	
	
	// /////////////////////////////// 가입자 타입 PIE 그래프
	var data = [ $(".trip5Login").text(),$(".kakaoLogin").text() ];
	console.log(data);

	// 가운데가 빈 원형 차트
	userType = {
		datasets : [ {
			backgroundColor : [ 'skyblue', 'yellow' ],
			data : data
		} ],
		// 라벨의 이름이 툴팁처럼 마우스가 근처에 오면 나타남
		labels : [ 'trip5', 'kakao' ]
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
			}
		}
	});
	
	// /////////////////////////////// 월별 가입자수 꺽은선 그래프
	console.log("안녕");
	$.ajax({
		url : "/admin/ajax/adminUserSignUp",
		type : "POST",
		success : function(data) {
		    var ctx3 = document.getElementById('myLineChart');
		    var data = {
		        // The type of chart we want to create
		        type: 'line',
		        // The data for our dataset
		        data: {
		            labels: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
		            datasets: [{
		                backgroundColor: 'rgb(255, 99, 132)',
		                fill:false, // line의 아래쪽을 색칠할 것인가?
		                borderColor: 'rgb(255, 99, 132)',
		                lineTension:0.1, // 값을 높이면, line의 장력이 커짐.
		                data:data,
		            }]
		        },
		        options: {
					maintainAspectRatio : false,
					showDatasetLabels : true,
					legend : {
						display : false
					}
		        }
		    }
		    var chart = new Chart(ctx3, data);
		}
	}) // Ajax 끝
})
