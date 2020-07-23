
$(document).ready(function(){
	
	
	
	$(".send").click(function(){
		$(".subject").text('보낸 쪽지함')
	});

	$(".receive").click(function(){
		$(".subject").text('받은 쪽지함')
	});

	$(".trash").click(function(){
		$(".subject").text('휴지통')
	});
});