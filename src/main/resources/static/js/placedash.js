/**
 * 
 */

$(window).resize(function(){
	var width = parseInt($(this).width());
	
	if(width < 1000){
		$('body').addClass('sidebar-collapse');
	}
}).resize();

$('.pushmenu').click(function(){
	console.log("click");	
	$('.main-sidebar').toggleClass('.button-sidebar');
	if($('.main-sidebar').hasClass('.button-sidebar')==true){
		$('.main-sidebar').css("margin","0px");
		$('.side-pushmenu').css("display","block");
		$('.pushmenu').css("display","none");
	}else{
		$('.main-sidebar').css("margin","");
		$('.side-pushmenu').css("display","none");
		$('.pushmenu').css("display","block");
	}

})
$('.side-pushmenu').click(function(){
	console.log("click");	
	$('.main-sidebar').toggleClass('.button-sidebar');
	if($('.main-sidebar').hasClass('.button-sidebar')==true){
		$('.main-sidebar').css("margin","0px");
		$('.side-pushmenu').css("display","block");
		$('.pushmenu').css("display","none");
	}else{
		$('.main-sidebar').css("margin","");
		$('.side-pushmenu').css("display","none");
		$('.pushmenu').css("display","block");
		
	}
	
})
