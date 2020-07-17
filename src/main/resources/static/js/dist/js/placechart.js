/**
 * 
 */
$(document).ready(function(){
	var ctx = $('#donutChart');
	data = {
		    datasets: [{
		        data: [10, 20, 30],
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
	
	/*var chart = new Chart(ctx, {
	    // The type of chart we want to create
	    type: 'line',

	    // The data for our dataset
	    data: {
	        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
	        datasets: [{
	            label: 'My First dataset',
	            backgroundColor: 'rgb(255, 99, 132)',
	            borderColor: 'rgb(255, 99, 132)',
	            data: [0, 10, 5, 2, 20, 30, 45]
	        }]
	    },

	    // Configuration options go here
	    options: {}
	});*/
})