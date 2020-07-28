var slideNotice = '';
var msg = '';

$(document).ready(
		function() {
			console.log("들어옴?");
			$.ajax({
				url : "/adminNotice/ajax/sNoticeShow",
				type : "POST",
				success : function(data) {
					console.log(data);
					console.log(data.length);
					for (var i = 0; i < data.length; i++) {
						slideNotice += '<p class="sNoticeMsg">' 
									+ data[i] 
									+ '</p>';
					}
					msg = '<MARQUEE scrollamount="10" class = "slideNotice">'
						+ slideNotice 
						+'</MARQUEE>';

					console.log(msg);
					$('.marquee').append(msg);
				}
			}) // Ajax 끝
		})

