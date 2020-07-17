var MAX_DEPTH = 1;   // depth 0 ~ 1

// $("#first_local").on("click").emtpy();

$(document).ready(function(){
	alert("로딩 완료");
	getCategory(0,0);  // depth0 카테고리 읽기
});

function getCategory(localdepth, localparent){

	if(parent == undefined) localparent = 0; 

	$.ajax({
			type : "POST",
			url: "weather/weather",
			dataType: 'JSON',
			data: {
				"localdepth" : localdepth,
				"localparent" : localparent
			},
			cache : false,
			success: function(data){
					buildSelect(localdepth, data);
				}
						
			}); 
	
} // end getCategory()	
	
	
function buildSelect(localdepth, jsonObj){
	
		var elm = $("div#localCate input:nth-child(" + localdepth + ") datalist");
		
		elm.attr("disabled", false);
		elm.off("change");
		elm.empty();
		
	//	$("#second_local_select").find("option").remove().end();
		
		var list = jsonObj.data;
		
		var result = "<option value1 = 0, value2 = 0, value3 = 0 > 전체 </option>";
		
		for(i = 0; i < list.length; i++){
		
			result += "<option value1 = '" + list[i].uid + "' value2 = '" + list[i].localnx + "' value3 = '" + list[i].localny + "'>";
			result += list[i].localname;
			result += "</option>";
			
		}
		
		elm.html(result);
		
		elm.change(function(){
			
			if(depth < MAX_DEPTH){
				
				for(var d = depth + 1; d <= MAX_DEPTH; d++){
				
					var e = $("div#localCate input:nth-child(" + d + ") select");
					e.off("change");
					e.empty();
					e.attr("disabled", true);
			}
		
		}
	
	});
	
} // end buildSelect()
	
	
// datalist 클릭 완료시 value 뒷단으로 보내기









