function fnMove(seq) {
	var offset = $("#day" + seq).offset();
	$('html, body').animate({
		scrollTop : offset.top
	}, 400);
}
