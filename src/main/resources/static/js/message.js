
$(document).ready(function(){
	
	$(".send").click(function(){
		sendMsg();
		$(".subject").text('보낸 쪽지함')
	});

	$(".receive").click(function(){
		cntMsg();
		receiveMsg();
		$(".subject").text('받은 쪽지함')
	});

	$(".trash").click(function(){
		$(".subject").text('휴지통')
	});
});


function sendMsg(){
	$.ajax({
		url : "/my/message",
		type : "POST",
		cahe : false,
		data : "sendid="+$("#userUuid").val(),
		success : function(data, status){
			if(status == "success"){
				toastr.success('성공');
				console.log(data);
				$(".tb-cl").html('');
				
				for(i = 0; i<data.length; i++){
					$(".tb-cl").append("<tr>" +
                    "<td>" +
                      "<div class='icheck-primary'>"+
                        "<input type='checkbox' value='' id='check1'>"+
                        "<label for='check1'></label>"+
                      "</div>"+
                    "</td>"+
                    "<td class='mailbox-star'><a href='#'></a></td>"+
                    "<td class='mailbox-name'><a href='read-mail.html'>"+data[i].membernick+"</a></td>"+
                    "<td class='mailbox-subject'>"+data[i].msgsubject+"</td>"+
                    "<td class='mailbox-attachment'></td>"+
                    "<td class='mailbox-date'>"+data[i].msgregdate+"</td>"+
                  "</tr>");
				}
			}
		}
	});
}

function cntMsg(){
	$.ajax({
		url : "/my/cntMsg",
		type : "POST",
		cahe : false,
		data : "fromid="+$("#userUuid").val(),
		success : function(data, status){
			if(status == "success"){
				toastr.success('성공2');
				console.log(data);
				$(".cntMsg").text(data);
			}
		}
	});
}

function receiveMsg(){
	$.ajax({
		url : "/my/receive",
		type : "POST",
		cahe : false,
		data : "fromid="+$("#userUuid").val(),
		success : function(data, status){
			if(status == "success"){
				$(".tb-cl").html('');
				
				for(i = 0; i<data.length; i++){
					$(".tb-cl").append("<tr>" +
                    "<td>" +
                      "<div class='icheck-primary'>"+
                        "<input type='checkbox' value='' id='check1'>"+
                        "<label for='check1'></label>"+
                      "</div>"+
                    "</td>"+
                    "<td class='mailbox-star'><a href='#'></a></td>"+
                    "<td class='mailbox-name'><a href='read-mail.html'>"+data[i].membernick+"</a></td>"+
                    "<td class='mailbox-subject'>"+data[i].msgsubject+"</td>"+
                    "<td class='mailbox-attachment'></td>"+
                    "<td class='mailbox-date'>"+data[i].msgregdate+"</td>"+
                  "</tr>");
				}
				
			}
		}
	});
}
