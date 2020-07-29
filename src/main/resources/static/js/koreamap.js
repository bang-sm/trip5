/**
 * 
 */

$(document).ready(function(){
		$.ajax({
		url: "/wish/rest/area", 
		type : "POST",
		data : {},
		success : function(data){
			console.log(data);
			$('.Gyeonggi-line').text("경기 : "+data[1]);
			$('.Seoul-line').text("서울 : "+data[0]);
			$('.Incheon-line').text("인천 : "+data[3]);
			$('.Gangwon-line').text("강원도 : "+data[2]);
			$('.North_Gyeongsang-line').text("경상북도 : "+data[11]);
			$('.North_Chungcheong-line').text("충청북도 : "+data[4]);
			$('.South_Chungcheong-line').text("충청남도 : "+data[5]);
			$('.Sejong-line').text("세종 : "+data[6]);
			$('.Daejeon-line').text("대전 : "+data[7]);
			$('.North_Jeolla-line').text("전라북도 : "+data[9]);
			$('.Gwangju-line').text("광주 : "+data[8]);
			$('.South_Jeolla-line').text("전라남도 : "+data[10]);
			$('.South_Gyeongsang-line').text("경상남도 : "+data[12]);
			$('.Ulsan-line').text("울산 : "+data[14]);
			$('.Daegu-line').text("대구 : "+data[13]);
			$('.Busan-line').text("부산 : "+data[15]);
			$('.Jeju-line').text("제주 : "+data[16]);
		}
	});
})