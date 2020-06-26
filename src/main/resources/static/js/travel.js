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
			  toolbar: [
				    // [groupName, [list of button]]
				    ['style', ['bold', 'italic', 'underline', 'clear']],
				    ['font', ['strikethrough', 'superscript', 'subscript']],
				    ['fontsize', ['fontsize']],
				    ['color', ['color']],
				    ['para', ['ul', 'ol', 'paragraph']],
				    ['height', ['height']]
				  ]
		});
		
	});
	var count=0;
	$(".add_day").click(function(){
		count++;
		var day_content="";
		var day_btn="";
		day_content+='<div class="col-lg-12 tab-content" id="day-'+count+'">';
		day_content+='<div class="md-form input-group mb-3">';
		day_content+='<input type="text" class="form-control" placeholder="머릿말을 입력하세요" name="ts_title">';
		day_content+='</div>';
		day_content+='<div class="summernote" name="tsi_comment"></div>';
		day_content+='</div>';
		
		day_btn+='<li class="nav-item add_day" data-tab="day-'+count+'" >';
		day_btn+='<a class="nav-link active" href="#">'+count+'day</a>';
		day_btn+='</li>';
		
		$("#add_day_ul").append(day_btn);
		$("#ts_story").append(day_content);
	});
})