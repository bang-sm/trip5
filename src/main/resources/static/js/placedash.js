/**
 * 
 */

$(window).resize(function(){
	var width = parseInt($(this).width());
	console.log(width);
	if(width < 1000){
		$('body').addClass('sidebar-collapse');
	}
	
	if(width > 992){
		$('.pushmenu').css("display","none");
		$('.side-pushmenu').css("display","none");
		console.log(">>>992");
	}else{
		$('.pushmenu').css("display","block");
		$('.side-pushmenu').css("display","block");
		console.log(" <<992");
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
