function fnMove(seq) {
	var offset = $("#day" + seq).offset();
	$('html, body').animate({
		scrollTop : offset.top
	}, 400);
}
$(document).ready(function(){
	$(document).on('click','ul.tabs li',function(){
		var tab_id = $(this).attr('data-tab');

		$('ul.tabs li').removeClass('current');
		$('.tab-content').removeClass('current');

		$(this).addClass('current');
		$("#"+tab_id).addClass('current');
		
		$('.summernote').summernote({
			 placeholder: '추억을 작성하세요',
			 height:400,
			 maxHeight:400,
			 minHeight: 400, 
			 focus: true,
			 lang: "ko-KR",	
			  toolbar: [
				    // [groupName, [list of button]]
				    ['style', [ 'italic', 'underline', 'clear']],
				    ['font', ['strikethrough', 'superscript', 'subscript']],
				    ['fontsize', ['fontsize']],
				    ['color', ['color']],
				    ['para', ['ul', 'ol', 'paragraph']],
				  ]
		});
		
	});
	var count=0;
	$(".add_day").click(function(){
		
		var day_content="";
		var day_btn="";
		day_content+='<div class="col-lg-12 tab-content" id="day-'+count+'">';
		day_content+='<div class="md-form input-group mb-3">';
		day_content+='<input type="text" class="form-control" placeholder="머릿말을 입력하세요" name="list['+count+'].tsititle">';
		day_content+='</div>';
		day_content+='<textarea class="summernote" name="list['+count+'].tsicomment"></textarea>';
		
		day_content+='<div class="root_box" class="timeline timeline-animated">';
		day_content+='<div class="timeline-item">';
		day_content+='<button type="button" class="btn btn-success waves-effect add_root">경로추가</button>';
		day_content+='</div>';
		day_content+='</div>';
		
		day_content+='</div>';
		
		day_btn+='<li class="nav-item add_day'+count+'" data-tab="day-'+count+'" >';
		day_btn+='<a class="nav-link active" href="#">'+count+'day</a>';
		day_btn+='<input type="hidden" value="'+count+'" name="list['+count+'].tsidDay">';
		day_btn+='</li>';
		
		
		$("#add_day_ul").append(day_btn);
		$('.add_day+'+count+'').trigger("click");
		$("#ts_story").append(day_content);
		
		count++;
		
	});
	
	//경로 추가햇을떄
	var root_order=0;
	$(document).on('click','.add_root',function(){
		
		var div=$(this).parent();
		$(this).parent().empty();
		var root_name="";
		var next_root="";
		root_name+='<span class="timeline-date" name="tsirootorder">'+root_order+'번째 장소</span>';
		root_name+='<h3 class="timeline-title" name="tsirootname">역삼역</h3>';
		root_name+='<input type="hidden" name="tsirootorder" value="'+root_order+'">';
		root_name+='<input type="hidden" name="tsirootname" value="역삼역">';
		next_root+='<div class="timeline-item">';
		next_root+='<button type="button" class="btn btn-success waves-effect add_root">경로추가</button>';
		next_root+='</div>';
		//일단 부모 비우기
		console.log(root_name);
		$(div).append(root_name);
		$(div).parent().append(next_root);
		
		root_order++;
	});
})