/*$(document).on('click',".card" ,function(){
	alert($('.card').attr('data-url'))
	var url = $(this).attr('data-url');
	location.href = url;
});*/
$('.card').click(function(){
	var url = $(this).attr('data-url');
	location.href = url;
});

$('.cardss').click(function(){
	location.href = "/my/clipSend?sendid="+$("#userUuid").val();
});