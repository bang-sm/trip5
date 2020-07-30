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
	 
	 $.ajax({
		url: "/my/gototrash",
		type: "POST",
		cache: false,
		data : params,
		success: function(data, status){
			if(status == "success"){
				
			}
		}
	 });
}

function goToTrashReceive(){
	var params = "";
	
	 $('.check1:checked').each(function() {
		 params += "msgid="+$(this).val()+"&";
	 });
	 params = params.substring(0, params.length-1);
	 console.log("parmas = "  + params);
	 
	 $.ajax({
		url: "/my/gototrash",
		type: "POST",
		cache: false,
		data : params,
		success: function(data, status){
			if(status == "success"){
				
			}
		}
	 });
}