/**
 * 
 */

$(window).resize(function(){
	var width = parseInt($(this).width());
	
	if(width < 1000){
		$('body').addClass('sidebar-collapse');
	}
}).resize();
