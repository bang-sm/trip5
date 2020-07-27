/*$(document).on('click',".card" ,function(){
	alert($('.card').attr('data-url'))
	var url = $(this).attr('data-url');
	location.href = url;
});*/
$('.card').click(function(){
	alert($('.card').attr('data-url'))
	var url = $(this).attr('data-url');
	location.href = url;
});