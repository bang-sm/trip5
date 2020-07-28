var curentPage = window.location.pathname;
$(document).ready(
		function() {
		
		// 클릭된 메뉴 표시
		for (var i = 1; i < 4; i++) {
			console.log($('.menu' + i).attr('data-url'));
			if($('.menu' + i).attr('data-url') == curentPage){
				console.log("들어옴?");
				$('.menu' + i).addClass('active');
			}
		}
})