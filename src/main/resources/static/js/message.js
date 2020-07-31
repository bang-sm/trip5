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
				alert('삭제되었습니다!');
				location.reload();
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
					 alert('삭제되었습니다!');
					 location.reload();
				 }
			 }
		 });
	 } 
}