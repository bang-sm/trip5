/**
 * 
 */
$(document).ready(function(){
	
	
	
	$.ajax({
		url: "/wish/rest/count", 
		type : "POST",
		data : {},
		success : function(data){
			
			var ctx = $('#donutChart');
			data = {
				    datasets: [{
				        data: [data.foodcount, data.cafecount, data.placecount],
				        backgroundColor : ['rgba(255,0,0,1)','rgba(0,255,0,1)','rgba(0,0,255,1)']
				    }],

				    // These labels appear in the legend and in the tooltips when hovering different arcs
				    labels: [
				    	'음식',
				        '관광지',
				        '카페'
				    ]
				};
			var myDoughnutChart = new Chart(ctx, {
			    type: 'doughnut',
			    data: data,
			    options: {}
			});
		}
	});
	
	var bar_ctx = $('#barChart');
	
	
	$.ajax({
		url: "/wish/rest/bar", 
		type : "POST",
		data : {},
		success : function(data){
			var max = data[0];
			for(i=0;i<data.length-1;i++){
				if(max< data[i+1]){
					max= data[i+1]
				}
			}
			var barChartData = {
					 labels: ['음식','카페','관광지'],
				        datasets: [{
				            label: ['방문 O'],
				            data: [
				              data.visitfood,data.visitcafe,data.visitcafe
				            ],
				            borderColor: "rgba(255, 201, 14, 1)",
				            backgroundColor: [
				            	"rgba(153, 102, 255, 0.5)",
				            	"rgba(153, 102, 255, 0.5)",
				            	"rgba(153, 102, 255, 0.5)"],
				            fill: false,
				            categoryPercentage: 1,
				            barPercentage: 0.2,
				        },
				        {
				            label: ['방문 X'],
				            data: [
				            	data.planfood,data.plancafe,data.planplace
				            ],
				            borderColor: "rgba(255, 201, 14, 1)",
				            backgroundColor: [
				            	"rgba(153, 153, 153, 0.5)",
				            	"rgba(153, 153, 153, 0.5)",
				            	"rgba(153, 153, 153, 0.5)"],
				            fill: false,
				            categoryPercentage: 1,
				            barPercentage: 0.2,
				        }
				        ]
			};
			var myDoughnutChart1 = new Chart(bar_ctx, {
				type: 'bar',
				data: barChartData,
				options: {
					maintainAspectRatio:false,
			        responsive: true,
			        title: {
			            display: true,
			            text: '테마별 현황 그래프'
			        },
			        tooltips: {
			            mode: 'index',
			            intersect: false,
			            callbacks: {
			                title: function(tooltipItems, data) {
			                    return data.labels[tooltipItems[0].datasetIndex];
			                }
			            }
			        },
			        hover: {
			            mode: 'nearest',
			            intersect: true
			        },
			        scales: {
			            xAxes: [{
			                display: true,
			                scaleLabel: {
			                    display: true,
			                },
			                ticks: {
			                    autoSkip: false
			                }
			            }],
			            yAxes: [{
			                display: true,
			                ticks: {
								min: 0,
								max: max,
								stepSize : 1,
								fontSize : 14,
							},
			                scaleLabel: {
			                    display: true,
			                    labelString: '등록 수'
			                }
			            }]
			        }
			    }
			});
		}
	});
	
	
	
	var line_ctx = $('#lineChart');
	$.ajax({
		url: "/wish/rest/line", 
		type : "POST",
		data : {},
		success : function(data){
		
			var arr = new Array();
			
			for(i=0;i<data.length;i++){
				arr[i] =data[i];
			}
			var linemax = arr[0];
			for(i=0;i<arr.length-1;i++){
				if(linemax < arr[i+1]){
					linemax = arr[i+1];
				}
			}
			var lineChartData = {
					 labels: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
				        datasets: [{
				            label: ['월별 현황 그래프'],
				            data: arr,
				            backgroundColor: [
								'rgba(0, 0, 0, 0)'
						],
						borderColor: [
								'rgba(255, 99, 132, 1)',
								'rgba(54, 162, 235, 1)',
								'rgba(255, 206, 86, 1)',
								'rgba(75, 192, 192, 1)',
								'rgba(153, 102, 255, 1)',
								'rgba(255, 159, 64, 1)'
						],
						borderWidth: 2
				        }]
			};
			var myDoughnutChart2 = new Chart(line_ctx, {
				type: 'line',
				data: lineChartData,
				options: {
					responsive: false,
					scales: {
						xAxes: [{
							ticks:{
								fontColor : 'rgba(12, 13, 13, 1)',
								fontSize : 14
							},
							gridLines:{
								color: "rgba(87, 152, 23, 1)",
								lineWidth: 0
							}
						}],
						yAxes: [{
							ticks: {
								min: 0,
								max: linemax,
								stepSize : 1,
								fontSize : 14,
							}
						}]
					}
				}
			});
		}});
	
	//지역별로 데이터 가지고 오기
	$.ajax({
		url: "/wish/rest/area", 
		type : "POST",
		data : {},
		success : function(data){
			console.log(data);
		}
	});
	
	
	
	
	
})