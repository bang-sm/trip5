function fnMove(seq) {
	var offset = $("#con" + seq).offset();
	$('html, body').animate({
		scrollTop : offset.top
	}, 400);
}

