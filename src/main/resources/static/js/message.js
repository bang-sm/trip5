
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
				console.log(data);
				$(".tb-cl").html('');
				
				for(i = 0; i<data.length; i++){
					$(".tb-cl").append("<tr>" +
                    "<td>" +
                      "<div class='icheck-primary'>"+
                        "<input type='checkbox' value='' class='check1'>"+
                        "<label for='check1'></label>"+
                      "</div>"+
                    "</td>"+
                    "<td class='mailbox-name'>"+data[i].membernick+"</td>"+
                    "<td class='mailbox-subject'><a href='/my/clipread?msgid="+data[i].msgid+"&sendid="+data[i].sendid+"' class='alert-link' style='color:black'>"+data[i].msgsubject+"</a></td>"+
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
                        "<input type='checkbox' value='' class='check1'>"+
                        "<label for='check1'></label>"+
                      "</div>"+
                    "</td>"+
                    (data[i].msgunwrite == 0 ?"<td class='mailbox-star'><i class='fas fa-book-open'></i></td>": "<td class='mailbox-star'></td>")+
                    "<td class='mailbox-name'>"+data[i].membernick+"</td>"+
                    "<td class='mailbox-subject'><a href='/my/clipread?msgid="+data[i].msgid+"&sendid="+data[i].sendid+"&fromid="+data[i].fromid+"' class='alert-link' style='color:black'>"+data[i].msgsubject+"</a></td>"+
                    "<td class='mailbox-date'>"+data[i].msgregdate+"</td>"+
                  "</tr>");
				}
				
			}
		}
	});
}

function chkAll(){
	if($(".check1").is(":checked")){
		$(".check1").prop("checked", false);
	} else {
		$(".check1").prop("checked", true);
	}
}

function trash(){
	
}
