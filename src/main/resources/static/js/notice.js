var slideNotice = '';
var msg = '';

$(document)
		.ready(
				function() {

					// 슬라이드 공지
					$.ajax({
								url : "/adminNotice/ajax/sNoticeShow",
								type : "POST",
								success : function(data) {
									msg = ''; // 초기화
									for (var i = 0; i < data.length; i++) {
										slideNotice += '<p class="sNoticeMsg">'
												+ data[i] + '</p>';
									}
									msg = '<MARQUEE scrollamount="10" class = "slideNotice">'
											+ slideNotice + '</MARQUEE>';

									$('.marquee').append(msg);
								}
							}) // Ajax 끝

					// 팝업공지 공지
					$.ajax({
								url : "/adminNotice/ajax/pNoticeContent",
								type : "POST",
								success : function(data) {
									msg = ''; // 초기화
									for (var i = 0; i < data.length; i++) {
										if(data[i].pnEnrollment == 'Y'){
											msg += '<div data-popup_id="popup"'; 
											msg += 'data-open_to= "' + data[i].pnDate + '" '; 
											msg += 'data-t="' + data[i].pnTop + '" '; 
											msg += 'data-l="' + data[i].pnLeft + '" ';
											msg += 'data-w="' + data[i].pnWidth + '" '; 
											msg += 'data-h="' + data[i].pnHeight + '" ';
											msg += 'class="popup_layer">';
											msg += '<button class="popup_close_btn"> &times; </button>';
											msg += '<div class = "popup_header"> 제목 : '	+ data[i].pnHeader + '</div><hr>'
											msg += '<div class = "popup_content">' + data[i].pnContent + '</div>'
											msg += '	<div class="popup_footer">';
											msg += '	<button class="close_with_cookie_btn" data-expired="1">';
											msg += '	<span>ⓧ</span>하루동안 이창을 열지 않음';
											msg += '	</button>';
											msg += '	</div>';
											msg += '</div>';
										}
									}
									$('body').append(msg);

									// 팝업창을 찾아서 띄울지 말지 판단하고 팝업창을 보이게 함
									$('[data-popup_id]')
											.each(
													function() {
														var el = $(this);
														var data = el.data();
														console.log("들어옵니까?");
														// 쿠키에 저장된 값이 없으면 팝업창을
														// 띄움
														if (!Cookies.get(data.popup_id)) {
															var today = new Date();
															var d = (el
																	.data('open_to') || '')
																	.split(/[\s,\-:]+/);
															var open_date = new Date(
																	+d[0],
																	+d[1] - 1,
																	+d[2],
																	+d[3] || 23,
																	+d[4] || 59,
																	+d[5] || 59);
															if (today < open_date) {
																el.css({
																			position : 'fixed',
																			backgroundColor : 'white',
																			zIndex : 3,
																			top : data.t,
																			left : data.l,
																			width : data.w,
																			height : data.h
																		}).show();
															} 
														} else {  // 날짜 넘었을 때
															console.log("왜 안됨");
															el.css({
																display : 'none'
															})
														}
													});

									// 오늘하루 열지 않음 처리
									$('.close_with_cookie_btn')
											.on('click',
												function(e) {
													e.preventDefault();
	
													var el = $(this);
													var popup = el
															.parents('[data-popup_id]');
													var id = popup
															.data('popup_id');
													var expired = +(el
															.data('expired') || '1'); // 1일뒤에
																						// 만료되는
													// 쿠키
	
													// 쿠키가 언제까지 유지될지 설정한 다음에
													// 팝업창을 닫는다.
													Cookies
															.set(id,
																'popup_closed',
																{
																	expires : expired
																});
													popup.hide();
												});
	
									// 팝업창 닫기 버튼 처리
									$('.popup_close_btn').on(
											'click',
											function(e) {
												e.preventDefault();

												$(this).parents(
														'[data-popup_id]')
														.hide();
											});
								}
							}) // Ajax 끝
				})