$(document).on('click',".current--card" ,function(){
	var url = $('.current--card').attr('data-url');
	location.href = url;
});