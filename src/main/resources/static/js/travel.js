

$(document).ready(function() {
	
	$('form[name="travel_info"]').bind('submit',function(){  //form을 submit 하기 전에 이벤트
	   $('.tsicomment').each(function(i,item){
			$(item).val($(item).parent().find("#summernote").summernote('code'));
		})
	})
	
	//파일첨부 input 생성
	$(".add_img").click(function(){
		var file_input="";
		file_input+='<div class="row_box">';
		file_input+='<input type="file" name="mfiles" accept="image/*" style="width:80% !important ; display: inline-block;"/>';
		file_input+='<input type="hidden" name="photoId" accept="image/*" style="width:80% !important ; display: inline-block;"/>';
		file_input+='<button type="button" class="btn btn-info img_delete">삭제';
		file_input+='<i class="fa fa-minus"></i>';
		file_input+='</button>';
		file_input+='</div>';
		$(".file_box").append(file_input);
	});
	
	//파일첨부 삭제버튼
	$(document).on('click','.img_delete',function(){
		var imgbox=$(this).parent()
		
		$.ajax({
			type : "post",
			url : "/travel/imageDelete",
			data : {
				"photoId" : $(this).val(),
				"customName" : $(this).next().val()
			},
			error: function(xhr, status, error){
	        	toastr.success("실패하였습니다");
	        },
	        success : function(data){
	        	if(data==1){
	        		toastr.success("삭제되었습니다");
	        		$(imgbox).remove();
	        	}else if(data==2){
	        		$(imgbox).remove();
	        		toastr.success("실패하였습니다");
	        	}else{
	        		toastr.success("해당파일이 존재하지않습니다.");
	        		$(imgbox).remove();
	        	}
	        }
		});
	});
	
	//프로그레스 nav 이동
	$(".progress_index").click(function(){
		var index=$(this).attr("data-navindex");
		console.log(index);
		
		$('fieldset').each(function(i,item){
			//console.log($(item).attr('data-index'));
			var filedsetIndex=$(item).attr('data-index');
			if(index==filedsetIndex){
				console.log(filedsetIndex);
				$(item).css('display','block');
				$(item).css('transform','scale(1)');
				$(item).css('opacity','1');
			}else{
				$(item).css('display','none');
				$(item).css('opacity','0');
				$(item).css('left','0');
			}
			
		});
	});
	
	//팝업 경로 보여주기
	$(".travel_root_view").click(function(){
		$("#root_popup").show();
	})
	
	//팝업 경로 닫기
	$(".popup_close").click(function(){
		$("#root_popup").hide();
	})
	
	//일지 댓글 등록
	$(".reply_submit").click(function(){
		$.ajax({
	        type : 'post',
	        url : '/travel/travel_reply_save',
	        data : $(this.form).serialize(),
	        error: function(xhr, status, error){
	        	toastr.success("실패하였습니다");
	        },
	        success : function(data){
	        	if(null){
	        		toastr.error("실패하였습니다.");
	        	}else{
	        		toastr.success("등록되었습니다");
	        		$("#replyBox").empty();
	        		console.log($("#myKey").val());
	        		for (var i = 0; i < data.length; i++) {
	        			
						var replyList="";
						replyList+='<div class="comment-list dropdown">';
						replyList+='<div class="single-comment justify-content-between d-flex">';
						replyList+='<div class="user justify-content-between d-flex">';
						replyList+='<div class="thumb">';
						replyList+='<img src="https://placeimg.com/60/60" alt="">';
						replyList+='</div>';
						replyList+='<div class="desc">';
						replyList+='<h5>';
						replyList+='<span class="member_drop_menu">'+data[i].memberName+'</span>';
						replyList+='</h5>';
						replyList+='<p class="date">'+data[i].replyRegdate+'</p>';
						replyList+='<p class="comment" data-uid='+data[i].uuid+'>'+data[i].tsReplyComment+'</p>';
						replyList+='</div>';
						replyList+='</div>';
						
						if($("#myKey").val()==data[i].uuid){
							replyList+='<div class="reply-btn">';
							replyList+='<input type="hidden" value='+data[i].tsReplyId+' class="reply_id">';
							replyList+='<button type="button" data-uuid='+data[i].uuid+' class="btn-reply text-uppercase">삭제</button>';
							replyList+='</div>';
						}
						
						replyList+='</div>';
						replyList+='</div>';
						$("#replyBox").append(replyList);
					}
	        		$("#comment_count").html(data.length+" 개의 댓글");
	        	}
	        },
	    });
	});
	//좋아요
	$("#tslike").click(function(){
		$.ajax({
			type : 'post',
	        url : '/travel/travel_like',
	        data : {
	        	"tsid" : $("#tsid").val()
	        },
	        error: function(xhr, status, error){
	        	toastr.success("실패하였습니다");
	        },
	        success : function(data){
	        	toastr.success("추천 감사합니다");
	        	$("#likeNum").text(data);
	        }
		});
	});
	
	//북마크
	$("#bookmark").click(function(){
		var book_mark_div=$(this);
		$.ajax({
			type : 'post',
	        url : '/travel/bookmark',
	        data : {
	        	"tsid" : $("#tsid").val()
	        },
	        error: function(xhr, status, error){
	        	toastr.success("실패하였습니다");
	        },
	        success : function(data){
	        	//1 : 삭제 2: 인서트 999: 로그인필요
	        	if(data==1){
	        		toastr.error("북마크에서 제외되었습니다.");
	        		$("#bookmark_st").removeClass("fa fa-check");
	        	}else if(data==2){
	        		toastr.success("북마트 추가되었습니다.");
	        		$("#bookmark_st").addClass("fa fa-check");
	        	}else if(data==999){
	        		toastr.error("로그인이 필요합니다.");
	        	}else{
	        		toastr.error("잘못된요청입니다");
	        	}
	        }
		});
	});
	//팔로우
	$("#follow").click(function(){
		$.ajax({
			type : 'post',
	        url : '/travel/follow',
	        data : {
	        	"followId" : $("#ts_uuid").val(),
	        },
	        error: function(xhr, status, error){
	        	toastr.success("실패하였습니다");
	        },
	        success : function(data){
	        	//1 : 삭제 2: 인서트 999: 로그인필요
	        	if(data==1){
	        		toastr.error("팔로우에서 제외되었습니다");
	        		$("#follow_st").removeClass("fa fa-check");
	        	}else if(data==2){
	        		toastr.success("팔로우 완료!");
	        		$("#follow_st").addClass("fa fa-check");
	        	}else if(data==999){
	        		toastr.error("로그인이 필요합니다.");
	        	}else{
	        		toastr.error("잘못된요청입니다");
	        	}
	        }
		});
	});
	
	//삭제
	$(document).on('click','.btn-reply',function(){
		var remove_box=$(this).parents(".comment-list");
		console.log(remove_box);
		$.ajax({
	        type : 'post',
	        url : '/travel/travel_reply_delete',
	        data : {
	        	"uuid" : $(this).attr("data-uuid"),
	        	"ts_reply_id" : $(this).prev().val()
	        },
	        error: function(xhr, status, error){
	        	toastr.success("실패하였습니다");
	        },
	        success : function(data){
	        	if(data==1){
	        		toastr.success("삭제되었습니다");
	        		$(remove_box).remove();
	        	}else if(data==0){
	        		toastr.error("잘못된 접근입니다");
	        	}	
	        	
	        }
	    });
	});
	//로딩시 에디터에잇는 내용 가져와서 뿌려주기
	$.ajax({
        type : 'post',
        url : '/travel/travel_detail_data',
        data : {
        	"tsid" : $("#tsid").val()
        },
        error: function(xhr, status, error){
            console.log("데이터를 가져오지못했습니다..");
        },
        success : function(data){
        	summernote_print(data);
        },
    });
	
	function summernote_print(data){
		$(".summernote").each(function(i,item){
			$(item).summernote('code', data[i].tsicomment);
		});
	}
	
	//일지 데이터 가져오기
	
	//루트 드래그 인덱스 매기기
	$("#sort_item").sortable({
		placeholder:"itemBoxHighlight",
        start: function(event, ui) {
            ui.item.data('start_pos', ui.item.index());
        },
        stop: function(event, ui) {
            var spos = ui.item.data('start_pos');
            var epos = ui.item.index();
            reorder();
        }
	});
	$("#sort_item").disableSelection(); //안에 내용들은 드래그 되지않기
	
	//드래그 한후 인덱스 리로드 하기
	function reorder() {
	    $(".step").each(function(i, box) {
	        $(box).find("#stepcount").attr("data-step-id",i+1);
	        $(box).find(".rootorder").attr("name","rootlist["+i+"].tsirootorder");
	        $(box).find(".rootorder").val(i);
	        $(box).find(".tsid").attr("name","rootlist["+i+"].tsid");
	        $(box).find(".rootname").attr("name","rootlist["+i+"].tsirootname");
	        $(box).find("#tsirootvehicle").attr("name","rootlist["+i+"].tsirootvehicle");
	    });
	}
	
	//경로추가 함수
	$(".add_place").click(function(){
		var input_data="";
		input_data+='<div class="step active">';
		input_data+='<input type="hidden" class="tsid" value='+$("#tsid").val()+' name="rootlist['+$(this).next().children('.step').length+'].tsid">';
		input_data+='<h2 data-step-id="'+eval($(this).next().children('.step').length+1)+'" id="stepcount"><input type="text" class="rootname" name="rootlist['+$(this).next().children('.step').length+'].tsirootname"></h2>';
		input_data+='<p>';
		input_data+='<input class="rootorder" type="hidden" value="'+$(this).next().children('.step').length+'" name="rootlist['+$(this).next().children('.step').length+'].tsirootorder">';
		input_data+='<select id="tsirootvehicle" name="rootlist['+$(this).next().children('.step').length+'].tsirootvehicle">';
		input_data+='<option value="BIKE">자전거</option>';
		input_data+='<option value="CAR">자동차</option>';
		input_data+='<option value="TRAIN">기차</option>';
		input_data+='<option value="AIRPLANE">비행기</option>';
		input_data+='<option value="WALK">도보</option>';
		input_data+='<option value="ARRIVE">도착지</option>';
		input_data+='</select>';
		input_data+='</p>';
		input_data+='<button type="button" class="root_delete">삭제</button>';
		input_data+='</div>';
		
		$(this).next().append(input_data);
	});
	
	//경로 삭제 및 인덱스 재정렬
	$(document).on('click','.root_delete',function(){
		$(this).parent().remove();
		var order=eval($(this).parent().find("h2").attr("data-step-id")-1);
		$(".root_delete").parent().each(function(i,item){
				alert(i);
			 $(item).find("#stepcount").attr("data-step-id",i+1);
			 $(item).find(".rootname").attr("name","rootlist["+i+"].tsirootname");
			 $(item).find(".tsid").attr("name","rootlist["+i+"].tsid");
			 $(item).find("#tsirootvehicle").attr("name","rootlist["+i+"].tsirootvehicle");
			 $(item).find(".rootorder").attr("name","rootlist["+i+"].tsirootorder");
			 $(item).find(".rootorder").val(i);
		});
		 $.ajax({
	            type : 'post',
	            url : '/travel/travel_root_delete',
	            data : {
	            	"tsirootorder" : $(this).parent().find(".rootorder").val(),
	            	"tsid" : $("#tsid").val()
	            },
	            error: function(xhr, status, error){
	                alert(error);
	            },
	            success : function(data){
	            	console.log(data);
	            },
	        });
		
	});
	//임시저장
	$(".temp_save").click(function(){
		$('.tsicomment').each(function(i,item){
			$(item).val($(item).parent().find("#summernote").summernote('code'));
		})
		$.ajax({
            type : 'post',
            url : '/travel/travel_temp_save',
            data : $(this.form).serialize(),
            error: function(xhr, status, error){
                alert(error);
            },
            success : function(data){
            	toastr.success("임시저장 되었습니다");
            },
        });
	});
	
	//텍스트 에디터 세팅
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
	
    var current_fs, next_fs, previous_fs;
    var left, opacity, scale;
    var animating;
    $(".steps").validate({
        errorClass: 'invalid',
        errorElement: 'span',
        errorPlacement: function(error, element) {
            error.insertAfter(element.next('span').children());
        },
        highlight: function(element) {
            $(element).next('span').show();
        },
        unhighlight: function(element) {
            $(element).next('span').hide();
        }
    });
    $(".next").click(function() {
        $(".steps").validate({
            errorClass: 'invalid',
            errorElement: 'span',
            errorPlacement: function(error, element) {
                error.insertAfter(element.next('span').children());
            },
            highlight: function(element) {
                $(element).next('span').show();
            },
            unhighlight: function(element) {
                $(element).next('span').hide();
            }
        });
        if ((!$('.steps').valid())) {
            return true;
        }
        if (animating) return false;
        animating = true;
        current_fs = $(this).parent();
        next_fs = $(this).parent().next();
        $("#progressbar li").eq($("fieldset").index(next_fs)).addClass("active");
        next_fs.show();
        current_fs.animate({
            opacity: 0
        }, {
            step: function(now, mx) {
                scale = 1 - (1 - now) * 0.2;
                left = (now * 50) + "%";
                opacity = 1 - now;
                current_fs.css({
                    'transform': 'scale(' + scale + ')'
                });
                next_fs.css({
                    'left': left,
                    'opacity': opacity
                });
            },
            duration: 800,
            complete: function() {
                current_fs.hide();
                animating = false;
            },
            easing: 'easeInOutExpo'
        });
    });
    $(".submit").click(function() {
        $(".steps").validate({
            errorClass: 'invalid',
            errorElement: 'span',
            errorPlacement: function(error, element) {
                error.insertAfter(element.next('span').children());
            },
            highlight: function(element) {
                $(element).next('span').show();
            },
            unhighlight: function(element) {
                $(element).next('span').hide();
            }
        });
        if ((!$('.steps').valid())) {
            return false;
        }
        if (animating) return false;
        animating = true;
        current_fs = $(this).parent();
        next_fs = $(this).parent().next();
        $("#progressbar li").eq($("fieldset").index(next_fs)).addClass("active");
        next_fs.show();
        current_fs.animate({
            opacity: 0
        }, {
            step: function(now, mx) {
                scale = 1 - (1 - now) * 0.2;
                left = (now * 50) + "%";
                opacity = 1 - now;
                current_fs.css({
                    'transform': 'scale(' + scale + ')'
                });
                next_fs.css({
                    'left': left,
                    'opacity': opacity
                });
            },
            duration: 800,
            complete: function() {
                current_fs.hide();
                animating = false;
            },
            easing: 'easeInOutExpo'
        });
    });
    $(".previous").click(function() {
        if (animating) return false;
        animating = true;
        current_fs = $(this).parent();
        previous_fs = $(this).parent().prev();
        $("#progressbar li").eq($("fieldset").index(current_fs)).removeClass("active");
        previous_fs.show();
        current_fs.animate({
            opacity: 0
        }, {
            step: function(now, mx) {
                scale = 0.8 + (1 - now) * 0.2;
                left = ((1 - now) * 50) + "%";
                opacity = 1 - now;
                current_fs.css({
                    'left': left
                });
                previous_fs.css({
                    'transform': 'scale(' + scale + ')',
                    'opacity': opacity
                });
            },
            duration: 800,
            complete: function() {
                current_fs.hide();
                animating = false;
            },
            easing: 'easeInOutExpo'
        });
    });
});
jQuery(document).ready(function() {
    jQuery("#edit-submitted-acquisition-amount-1,#edit-submitted-acquisition-amount-2,#edit-submitted-cultivation-amount-1,#edit-submitted-cultivation-amount-2,#edit-submitted-cultivation-amount-3,#edit-submitted-cultivation-amount-4,#edit-submitted-retention-amount-1,#edit-submitted-retention-amount-2,#edit-submitted-constituent-base-total-constituents").keyup(function() {
        calcTotal();
    });
});
var modules = {
    $window: $(window),
    $html: $('html'),
    $body: $('body'),
    $container: $('.container'),
    init: function() {
        $(function() {
            modules.modals.init();
        });
    },
    modals: {
        trigger: $('.explanation'),
        modal: $('.modal'),
        scrollTopPosition: null,
        init: function() {
            var self = this;
            if (self.trigger.length > 0 && self.modal.length > 0) {
                modules.$body.append('<div class="modal-overlay"></div>');
                self.triggers();
            }
        },
        triggers: function() {
            var self = this;
            self.trigger.on('click', function(e) {
                e.preventDefault();
                var $trigger = $(this);
                self.openModal($trigger, $trigger.data('modalId'));
            });
            $('.modal-overlay').on('click', function(e) {
                e.preventDefault();
                self.closeModal();
            });
            modules.$body.on('keydown', function(e) {
                if (e.keyCode === 27) {
                    self.closeModal();
                }
            });
            $('.modal-close').on('click', function(e) {
                e.preventDefault();
                self.closeModal();
            });
        },
        openModal: function(_trigger, _modalId) {
            var self = this,
                scrollTopPosition = modules.$window.scrollTop(),
                $targetModal = $('#' + _modalId);
            self.scrollTopPosition = scrollTopPosition;
            modules.$html.addClass('modal-show').attr('data-modal-effect', $targetModal.data('modal-effect'));
            $targetModal.addClass('modal-show');
            modules.$container.scrollTop(scrollTopPosition);
        },
        closeModal: function() {
            var self = this;
            $('.modal-show').removeClass('modal-show');
            modules.$html.removeClass('modal-show').removeAttr('data-modal-effect');
            modules.$window.scrollTop(self.scrollTopPosition);
        }
    }
}

modules.init();