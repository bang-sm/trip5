$(document).ready(function(){
  $("#searchClip").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $(".searchTable tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});


function chkAll(){
	if($(".check1").is(":checked")){
		$(".check1").prop("checked", false);
	} else {
		$(".check1").prop("checked", true);
	}
}

function goToTrashSend(){
	var params = "";
	
	 $('.check1:checked').each(function() {
		 params += "msgid="+$(this).val()+"&";
	 });
	 params = params.substring(0, params.length-1);
	 params += "&sendid="+$("#userUuid").val();
	 console.log("parmas = "  + params);
	 
	 if(confirm('정말로 삭제하시겠습니까??')){
	 $.ajax({
		url: "/my/gototrash",
		type: "POST",
		cache: false,
		data : params,
		success: function(data, status){
			if(status == "success"){
				swal("삭제를 성공했습니다!", {
					  buttons: {
					    확인: true,
					  },
					})
					.then((value) => {
					  switch (value) {
					 
					    case "확인":
					    location.reload();
					    break;
					  }
					});
//				location.reload();
			}
		}
	 });
	}
}

function goToTrashReceive(){
	var params = "";
	
	 $('.check1:checked').each(function() {
		 params += "msgid="+$(this).val()+"&";
	 });
	 
	 
	 params = params.substring(0, params.length-1);
	 console.log("parmas = "  + params);
	 
	 
	 if(confirm('정말로 삭제하시겠습니까??')){
		 $.ajax({
			 url: "/my/gototrash",
			 type: "POST",
			 cache: false,
			 data : params,
			 success: function(data, status){
				 if(status == "success"){
						swal("삭제를 성공했습니다!", {
							  buttons: {
							    확인: true,
							  },
							})
							.then((value) => {
							  switch (value) {
							 
							    case "확인":
							    location.reload();
							    break;
							  }
							});
					}
		 }
	 }); 
}
}
function deleteOk(){
	
	var params = "";
	
	 $('.check1:checked').each(function() {
		 params += "msgid="+$(this).val()+"&";
	 });
	 
	 params = params.substring(0, params.length-1);
	 console.log("parmas = "  + params);
	 
	 $.ajax({
		url: "/my/deleteOk",
		type: "POST",
		cache: false,
		data: params,
		success: function(data, status){
			if(status == "success"){
				swal("삭제를 성공했습니다!", {
					  buttons: {
					    확인: true,
					  },
					})
					.then((value) => {
					  switch (value) {
					 
					    case "확인":
					    location.reload();
					    break;
					  }
					});
			}
		}
	 });
}










