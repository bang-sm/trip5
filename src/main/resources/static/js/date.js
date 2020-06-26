$(document).ready(function() {
			var datepicker = new Datepickk({
				container: document.querySelector('#datepicker'),
				inline: true,
				range: true,
			});
			datepicker.setDate = new Date(2020,6,1);
			datepicker.button = '적용하기';
			datepicker.onConfirm = function(){
				if(datepicker.selectedDates[0]==undefined && datepicker.selectedDates[1]==undefined){
					alert("날짜를 선택하셔야합니다");
				}
				else{
					var startDate=new Date(datepicker.selectedDates[0]);
					var endDate;
					var dateDiff;
					if(datepicker.selectedDates[1]==undefined){
						dateDiff=1;
						endDate=startDate;
					}else{
						endDate=new Date(datepicker.selectedDates[1]);
						dateDiff = Math.ceil((endDate.getTime()-startDate.getTime())/(1000*3600*24));
					}
					startDate=$.datepicker.formatDate('yy-mm-dd', new Date(startDate));
					endDate=$.datepicker.formatDate('yy-mm-dd', new Date(endDate));
					
					location.href = "/travel/regist?startDate="+startDate+"&endDate="+endDate+"&dateDiff="+dateDiff;

				}
			};
			
			//등록하지않고 뒤돌아가기
			$("#btn_cancle").click(function(){
				history.back();
			});
			
	})