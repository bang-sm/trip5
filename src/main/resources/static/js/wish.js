

//첫번째 모델창 글 등록
$("#exampleModal").on('shown.bs.modal', function(){
	$('.map_regist').css("display", "block");
	var markers = [];

	var mapContainer = document.getElementById('map'), // 지도를 표시할 div
	mapOption = {
		center : new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
		level : 3
	// 지도의 확대 레벨
	};

	var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
	console.log("map.getLevel1 : "+map.getLevel());

	
	// 지도 확대, 축소 컨트롤에서 축소 버튼을 누르면 호출되어 지도를 확대하는 함수입니다
	$(document).on('click','.zoomin',function(){
		map.setLevel(map.getLevel() - 1);
	})

	$(document).on('click','.zoomout',function(){
		map.setLevel(map.getLevel() + 1);
	})

	
	setTimeout(function(){
		map.relayout(); }
	, 3000)

	// 장소 검색 객체를 생성합니다
	var ps = new kakao.maps.services.Places();

	// 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
	var infowindow = new kakao.maps.InfoWindow({
		zIndex : 1
	});
	searchPlacestwo();
	// 키워드로 장소를 검색합니다
	$('.map_btn1').click(function(){
		searchPlacesone();
		});
	$('.map_btn2').click(function(){
		searchPlacestwo();
		});
	// 키워드 검색을 요청하는 함수입니다
	function searchPlacesone() {

		var keyword = document.getElementById('keyword1').value;

		if (!keyword.replace(/^\s+|\s+$/g, '')) {
			alert('키워드를 입력해주세요!');
			return false;
		}

		// 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
		ps.keywordSearch(keyword, placesSearchCB);
	}
	function searchPlacestwo() {

		var keyword = document.getElementById('keyword2').value;

		if (!keyword.replace(/^\s+|\s+$/g, '')) {
			alert('키워드를 입력해주세요!');
			return false;
		}

		// 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
		ps.keywordSearch(keyword, placesSearchCB);
	}

	// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
	function placesSearchCB(data, status, pagination) {
		if (status === kakao.maps.services.Status.OK) {

			// 정상적으로 검색이 완료됐으면
			// 검색 목록과 마커를 표출합니다
			displayPlaces(data);

			// 페이지 번호를 표출합니다
			displayPagination(pagination);

		} else if (status === kakao.maps.services.Status.ZERO_RESULT) {

			alert('검색 결과가 존재하지 않습니다.');
			return;

		} else if (status === kakao.maps.services.Status.ERROR) {

			alert('검색 결과 중 오류가 발생했습니다.');
			return;

		}
	}

	// 검색 결과 목록과 마커를 표출하는 함수입니다
	function displayPlaces(places) {

		var listEl = document.getElementById('placesList'), menuEl = document
				.getElementById('menu_wrap'), fragment = document
				.createDocumentFragment(), bounds = new kakao.maps.LatLngBounds(), listStr = '';

		// 검색 결과 목록에 추가된 항목들을 제거합니다
		removeAllChildNods(listEl);

		// 지도에 표시되고 있는 마커를 제거합니다
		removeMarker();

		for (var i = 0; i < places.length; i++) {

			// 마커를 생성하고 지도에 표시합니다
			var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x)
			, marker = addMarker(placePosition, i),
			itemEl = getListItem(i, places[i]); // 검색 결과
																			// 항목
																			// Element를
																			// 생성합니다

			// 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
			// LatLngBounds 객체에 좌표를 추가합니다
			bounds.extend(placePosition);
			var areaname;
			// 마커와 검색결과 항목에 mouseover 했을때
			// 해당 장소에 인포윈도우에 장소명을 표시합니다
			// mouseout 했을 때는 인포윈도우를 닫습니다
			(function(marker, title) {
				kakao.maps.event.addListener(marker, 'mouseover', function() {
					displayInfowindow(marker, title);
					areaname = title;
				});

				kakao.maps.event.addListener(marker, 'mouseout', function() {
					infowindow.close();
				});
				
				 kakao.maps.event.addListener(marker, 'click', function() {
					 searchaddress(areaname);
					 });
				itemEl.onmouseover = function() {
					displayInfowindow(marker, title);
				};

				itemEl.onmouseout = function() {
					infowindow.close();
				};
			})(marker, places[i].place_name);

			fragment.appendChild(itemEl);
		}

		// 검색결과 항목들을 검색결과 목록 Elemnet에 추가합니다
		listEl.appendChild(fragment);
		menuEl.scrollTop = 0;

		// 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
		map.setBounds(bounds);
	}

	
	//장소 이름으로 리스트에 있는 주소값 가지고 오기
   function searchaddress(areaname){
	   var count = $('.item');
	   var name =count.children('.info').children('h5');
	   for(i=0;i<name.length;i++){
		if(name[i].innerHTML == areaname){
			console.log("정답 : "+ name[i].innerHTML + " , " + "areaname : " + areaname);
			var juso = count.children('.info').children('.jibun ');
			console.log(juso[i].innerHTML);
			
			$('input[name=placejuso]').val(juso[i].innerHTML);
			$('input[name=placename]').val(name[i].innerHTML);
			$('.alarm-name').text(name[i].innerHTML);
			$('.alarm').css("display","block");
			setTimeout(function() {
				$('.alarm').css("display","none");
				}, 1000);
			$('.btn_check_place').removeClass('btn-outline-danger');
			$('.btn_check_place').addClass('btn-danger');
			
		}   
	   }
   }
	
	
	// 검색결과 항목을 Element로 반환하는 함수입니다
	function getListItem(index, places) {

		var el = document.createElement('li'), 
		
		itemStr = '<span class="markerbg marker_'
				+ (index + 1)
				+ '"></span>'
				+ '<div class="info">'
				+ '   <h5>'
				+ places.place_name + '</h5>';

		if (places.road_address_name) {
			itemStr += '    <span>' + places.road_address_name + '</span>'
					+ '   <span class="jibun gray">' + places.address_name
					+ '</span>';
		} else {
			itemStr += '    <span>' + places.address_name + '</span>';
		}

		itemStr += '  <span class="tel">' + places.phone + '</span>' + '</div>';

		el.innerHTML = itemStr;
		el.className = 'item';
		// 음식점 클릭시 해당 가게 이름 현재 맛집으로 설정
		el.addEventListener('click',function(){
			$('.alarm-name').text(places.place_name);
			$('.alarm').css("display","block");
			setTimeout(function() {
				$('.alarm').css("display","none");
				}, 1000);
			$('.btn_check_place').removeClass('btn-outline-danger');
			$('.btn_check_place').addClass('btn-danger');
			$('input[name=placename]').val(places.place_name);
			$('input[name=placejuso]').val(places.address_name);
		});
		
		
		
		

		return el;
	}
	var geocoder = new kakao.maps.services.Geocoder();
	function searchDetailAddrFromCoords(coords, callback) {
	    // 좌표로 법정동 상세 주소 정보를 요청합니다
	    geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
	}
	
	

	// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
	function addMarker(position, idx, title) {
		var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커
																											// 이미지
																											// url,
																											// 스프라이트
																											// 이미지를
																											// 씁니다
		imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
		imgOptions = {
			spriteSize : new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
			spriteOrigin : new kakao.maps.Point(0, (idx * 46) + 10), // 스프라이트 이미지
																		// 중 사용할 영역의
																		// 좌상단 좌표
			offset : new kakao.maps.Point(13, 37)
		// 마커 좌표에 일치시킬 이미지 내에서의 좌표
		}, 
		markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions), 
		marker = new kakao.maps.Marker(
				{
					position : position, // 마커의 위치
					image : markerImage
				});

		marker.setMap(map); // 지도 위에 마커를 표출합니다
		markers.push(marker); // 배열에 생성된 마커를 추가합니다

		
		return marker;
	}

	// 지도 위에 표시되고 있는 마커를 모두 제거합니다
	function removeMarker() {
		for (var i = 0; i < markers.length; i++) {
			markers[i].setMap(null);
		}
		markers = [];
	}

	// 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
	function displayPagination(pagination) {
		var paginationEl = document.getElementById('pagination'), fragment = document
				.createDocumentFragment(), i;

		// 기존에 추가된 페이지번호를 삭제합니다
		while (paginationEl.hasChildNodes()) {
			paginationEl.removeChild(paginationEl.lastChild);
		}

		for (i = 1; i <= pagination.last; i++) {
			var el = document.createElement('a');
			el.href = "#";
			el.innerHTML = i;

			if (i === pagination.current) {
				el.className = 'on';
			} else {
				el.onclick = (function(i) {
					return function() {
						pagination.gotoPage(i);
					}
				})(i);
			}

			fragment.appendChild(el);
		}
		paginationEl.appendChild(fragment);
	}

	// 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
	// 인포윈도우에 장소명을 표시합니다
	function displayInfowindow(marker, title) {
		var content = '<div style="padding:5px;z-index:1;">' + title + '</div>';

		infowindow.setContent(content);
		infowindow.open(map, marker);
	}

	// 검색결과 목록의 자식 Element를 제거하는 함수입니다
	function removeAllChildNods(el) {
		while (el.hasChildNodes()) {
			el.removeChild(el.lastChild);
		}
	}
  });

//2번쨰 모달창 갈때 장소 블로그 선택 여부

function statuscheck(){
	var blog = $('input[name=bloglink]').val();
	console.log("blog : "+blog);
	var place = $('input[name=placename]').val();
	console.log("place : "+place);
	result="";
	if(place== ""){
		result+='<img src="../image/icon/cancel30.png" data-toggle="tooltip" data-placement="top" title="저장된 장소가 없습니다."/>';
		$('.btn_check_place_status').html(result);
	}else{
		result+='<img src="../image/icon/check30.png" data-toggle="tooltip" data-placement="top" title="'+place+'"/>';
		$('.btn_check_place_status').html(result);
	}
	result="";
	if(blog ==""){
		result+='<img src="../image/icon/cancel30.png" data-toggle="tooltip" data-placement="top" title="저장된 블로그가 없습니다."/>';
		$('.btn_check_blog_status').html(result);
	}else{
		result+='<img src="../image/icon/check30.png" data-toggle="tooltip" data-placement="top" title="'+blog+'"/>';
		$('.btn_check_blog_status').html(result);
	}
}

// 지도에서 검색창 숨기고 펼치기
var hide_count =0;
$(document).on('click','.hide_btn',function(){
	if(hide_count==0){
		$('.place_map_all_list').width('0');
		$('.place_map_all_list').css("display","none");
		$('.kakaomap_title').css("display","none");
		$('.map_wrap').width('0');
		
		$('.hide_div').css('left','20px');
		$('.place_map_all_map').css('left','0');
		$('.place_map_all_map').css('width','1150px');
		hide_count=1;
	}else{
		$('.place_map_all_list').width('30%');
		$('.place_map_all_list').css("display","block");
		$('.kakaomap_title').css("display","block");
		$('.map_wrap').width('380px');
		
		$('.hide_div').css('left','400px');
		$('.place_map_all_map').css('left','380px');
		$('.place_map_all_map').css('width','810px');
		hide_count=0;
	}
	
})

//지도 끝



function fnMove() {
	var offset = $(".wish_list").offset();
	$('html, body').animate({
		scrollTop : offset.top
	}, 500);
}

function food_regist() {
	$('.map_regist').css("display", "block");
}


var btn_check=0;

var slick_check=0;
//검색하고 싶은 맛집 검색하면 값 넘겨받아서 새로운 modal창으로 표현
function naversearch(){
	$('.food_icon').css("display","none");
	$('.navercardslide_for').css("display","block");
	$('.navercardslide_nav').css("display","block");
	
	var keyword = $('#naver').val();
	var result_for="";
	var result_nav="";
	$.ajax({
		url: "/wish/rest/naver", 
		type:"POST",
		data : {
			"keyword" : keyword
		},
		success : function(data){
			
			for(i=0;i<10;i++){
				result_nav+="<div class='search_nav shadow p-3 mb-5 bg-white rounded'>"+data.items[i].title+"</div>";
				result_for+="<div class='search_for shadow-lg p-3 mb-5 bg-white rounded'><a href='"+data.items[i].link+"' target='_blank'>";
				result_for+="<h2>"+data.items[i].title+"</h2>";
				result_for+="<p>"+data.items[i].description +"</p>";
				result_for+="<div> 볼로그 작성일 :"+data.items[i].postdate+"</div></a>";
				result_for+="<input type='hidden' name='hidden_link' value='"+data.items[i].link+"'/>"
				result_for+="<div ><img class='star' src='../image/star_off.png'/></div></div>";
			}
			$('.navercardslide_for').html(result_for);
			$('.navercardslide_nav').html(result_nav);

			if(slick_check == 1){
				$('.navercardslide_for').slick("unslick")
				$('.navercardslide_nav').slick("unslick")
				slick_check=0;
			}
			    $('.navercardslide_for').slick({
				  slidesToShow: 1,
				  slidesToScroll: 1,
				  arrows: false,
				  fade: true,
				  asNavFor: '.navercardslide_nav'
				});
				$('.navercardslide_nav').slick({
				  slidesToShow: 3,
				  slidesToScroll: 1,
				  asNavFor: '.navercardslide_for',
				  dots: true,
				  centerMode: true,
				  focusOnSelect: true
				});
				slick_check=1;
				
				$('.star').click(function(){
					var star =$(this).attr('src');
					var hidden = $('input[name=hidden_link]').val();
					
						if(star.match('off')){
							if(btn_check==0){
								$(this).attr('src','../image/star_on.png');	
								$('.btn_check_blog').removeClass('btn-outline-info');
								$('.btn_check_blog').addClass('btn-info');
								$('input[name=bloglink]').val(hidden);
								btn_check=1;
								$('.alarm-blog').css("display","block");
								setTimeout(function() {
									$('.alarm-blog').css("display","none");
									}, 1000);
							}else{
								alert("블로그 등록은 한개밖에 할수 없습니다. 등록을 해제해주세요!");
							}
						}else{
							$(this).attr('src','../image/star_off.png');	
							$('.btn_check_blog').removeClass('btn-info');
							$('.btn_check_blog').addClass('btn-outline-info');
							$('input[name=bloglink]').val("");
							btn_check=0;
						}
					
					
				})
		}//end success
	})
}
var food_icons= ["barbecue","burrito","chicken","cola","crab","beef","pork","dimsum","fish",
	"foodandwine","fruitbag","hamburger","icecream","kfc","noodles","octopus","omlette","pancake","pizza",
	"prawn","rice","roast","salad","sausages","spaghetti","steak","tapas",
	"restaurant"]
var cafe_icons=["bingsu","bread","cafe","cafe_coffee","cake","coffe","croissant","milk",];
var place_icons=["basilica","bigben","bridge","church","gondola","mountain","sea","temple","top-of-a-hill",
				"tour-bus","tourlist","tourlist2","train","travel"];




function icon() {
	var check = $('select[name=placecategory]').val();
	if (check >= 0 || check <= 2) {
		$('.navercardslide_for').css("display", "none");
		$('.navercardslide_nav').css("display", "none");

		$('.food_icon').css("display", "block");
		if (slick_check == 1) {
			$('.navercardslide_for').slick("unslick")
			$('.navercardslide_nav').slick("unslick")
		}

		result = "";
		if(check ==0){
			for (i = 0; i < food_icons.length; i++) {
				result += "<div class='icon_main'><img class='icon_img' src='../image/icon/"
						+ food_icons[i] + ".png'/>";
				result += "<div>" + food_icons[i] + "</div></div>";
			}
		}else if(check==1){
			for (i = 0; i < cafe_icons.length; i++) {
				result += "<div class='icon_main'><img class='icon_img' src='../image/icon/"
						+ cafe_icons[i] + ".png'/>";
				result += "<div>" + cafe_icons[i] + "</div></div>";
			}
		}else{
			for (i = 0; i < place_icons.length; i++) {
				result += "<div class='icon_main'><img class='icon_img' src='../image/icon/"
						+ place_icons[i] + ".png'/>";
				result += "<div>" + place_icons[i] + "</div></div>";
			}
		}
		

		$('.food_icon').html(result);
	} else {
		alert("테마를 선택해주세요~");
	}

}

$(document).on("click",'.icon_main',function(){
	var iconname=$(this).text();
	var icon_check =confirm(iconname +" 아이콘으로 저장하시겠습니까?");
	if(icon_check){
		alert(iconname+" 아이콘이 저장되었습니다.");
		$('input[name=iconname]').val("/"+iconname);
		$('.btn-icon').removeClass('btn-outline-success');
		$('.btn-icon').addClass('btn-success');
		$('.food_icon').css("display","none");
		$('.my_check').css("display","block");
	}
	
	
})

//즐겨찾기(별 체크)
$(document).on('click','.star_img',function(){
	var check = $(this).attr("src");
	var id=$(this).parent('td').children('input[name=placeid]').val();
	console.log(id);
	if(check.match("off")){
		$(this).attr("src","../image/icon/star_on.png");
		$.ajax({
			url: "/wish/rest/bookmark", 
			type : "POST",
			data : {
				"bookmark" : 1,
				"placeid": id
			},
			success : function(){
			}
		});
	}else{
		$(this).attr("src","../image/icon/star_off.png");
		$.ajax({
			url: "/wish/rest/bookmark", 
			type : "POST",
			data : {
				"bookmark" : 0,
				"placeid": id
			},
			success : function(){
			}
		});
	}
})


//테마에 따른 테이블 재생성
 $('.btn-filter').on('click', function() {
	var $target = $(this).data('target');
	$('.table').html("");
	var uuid = $('input[name=uuid]').val();
	console.log(uuid);
	result="";
	if ($target == 0) {//음식
		$.ajax({
			url: "/wish/rest/buttoncategory",
			type:"POST",
			data : {
				"placecategory" : $target,
				"placecheck": 0,
				"uuid": uuid
			},
			success : function(data){
				var count = data.length;
				console.log(data);
				result+="<tbody class='tbody'>";
				for(i=0;i<count;i++){
					result+="<tr  class='tabletr'>";
					result+="<td class=''>";
						result+="<div class='ckbox '>";
						if(data[i].placecheck ==0){
							result+="<input type='checkbox'  class='checkbox1' id='"+data[i].placeid+"'>";
						}else{
							result+="<input type='checkbox'  class='checkbox1' id='"+data[i].placeid+"' checked>";
						}
						result+="<label th:for='"+data[i].placeid+"'></label>";
						result+="</div>";
						result+="</td>";
						result+="<td class='td_two'>";
						result+="<input type='hidden' class='placeid'name='placeid' value='"+data[i].placeid+"'/>";
						if(data[i].bookmark == 0){
							result+="<img class='star_img' src='../image/icon/star_off.png'/>";
						}else{
							result+="<img class='star_img' src='../image/icon/star_on.png'/>";
						}
						result+="</td>";
						result+="<td class=''>";
						result+="<div class='media'>";
						result+="<img src='../image/icon"+data[i].iconname+".png' class='media-photo'>";
						result+="<div class='media-body'>";
						result+="<span class='media-meta pull-right'>"+data[i].placeregdate+"</span>";
						if(data[i].placecategory ==0){
							result+="<button type='button' class='btn btn-outline-success btn_category'>음식</button>";
						}else if (data[i].placecategory ==1){
							result+="<button type='button' class='btn btn-outline-info btn_category'>카페</button>";
						}else{
							result+="<button type='button' class='btn btn-outline-danger btn_category'>관광지</button>";
						}
						result+="<h4 class='title'>"+data[i].placename+"</h4>";
						result+="<span  class='summary'>"+data[i].placejuso+"</span>";
						result+="</div>";
						result+="<button class='media_look'>블로그 보기</button>";
						result+="<button class='media_update' data-toggle='modal' data-target='#exampleModal1'>글 수정</button>";
						result+="<button class='media_delete'>글 삭제</button>";
						result+="<img src='../image/icon/cancel.png' class='media_cancel'>";
						result+="</div>";
						result+="</td>";
						result+="</tr>";
				}					
				result+="</tbody>";
				$('.table').html(result).trigger("create");
			}
		});
	} else if ($target == 1) {//카페
		$.ajax({
			url: "/wish/rest/buttoncategory", 
			type:"POST",
			data : {
				"placecategory" : $target,
				"placecheck": 0,
				"uuid": uuid
			},
			success : function(data){
				var count = data.length;
				console.log(data);
				result+="<tbody class='tbody'>";
				for(i=0;i<count;i++){
				result+="<tr  class='tabletr'>";
				result+="<td class=''>";
					result+="<div class='ckbox '>";
					if(data[i].placecheck ==0){
						result+="<input type='checkbox'  class='checkbox1' id='"+data[i].placeid+"'>";
					}else{
						result+="<input type='checkbox'  class='checkbox1' id='"+data[i].placeid+"' checked>";
					}
					result+="<label th:for='"+data[i].placeid+"'></label>";
					result+="</div>";
					result+="</td>";
					result+="<td class='td_two'>";
					result+="<input type='hidden' class='placeid' name='placeid' value='"+data[i].placeid+"'/>";
					if(data[i].bookmark == 0){
						result+="<img class='star_img' src='../image/icon/star_off.png'/>";
					}else{
						result+="<img class='star_img' src='../image/icon/star_on.png'/>";
					}
					result+="</td>";
					result+="<td class=''>";
					result+="<div class='media'>";
					result+="<img src='../image/icon"+data[i].iconname+".png' class='media-photo'>";
					result+="<div class='media-body'>";
					result+="<span class='media-meta pull-right'>"+data[i].placeregdate+"</span>";
					if(data[i].placecategory ==0){
						result+="<button type='button' class='btn btn-outline-success btn_category'>음식</button>";
					}else if (data[i].placecategory ==1){
						result+="<button type='button' class='btn btn-outline-info btn_category'>카페</button>";
					}else{
						result+="<button type='button' class='btn btn-outline-danger btn_category'>관광지</button>";
					}
					result+="<h4 class='title'>"+data[i].placename+"</h4>";
					result+="<span  class='summary'>"+data[i].placejuso+"</span>";
					result+="</div>";
					result+="<button class='media_look'>블로그 보기</button>";
					result+="<button class='media_update' data-toggle='modal' data-target='#exampleModal1'>글 수정</button>";
					result+="<button class='media_delete'>글 삭제</button>";
					result+="<img src='../image/icon/cancel.png' class='media_cancel'>";
					result+="</div>";
					result+="</td>";
					result+="</tr>";
				}					
				result+="</tbody>";
				$('.table').html(result).trigger("create");
			}
		});
	} else if ($target == 2) {//관광지
		$.ajax({
			url: "/wish/rest/buttoncategory", 
			type:"POST",
			data : {
				"placecategory" : $target,
				"placecheck": 0,
				"uuid": uuid
			},
			success : function(data){
				var count = data.length;
				console.log(data);
				result+="<tbody class='tbody'>";
				for(i=0;i<count;i++){
					result+="<tr  class='tabletr'>";
					result+="<td class=''>";
						result+="<div class='ckbox '>";
						if(data[i].placecheck ==0){
							result+="<input type='checkbox'  class='checkbox1' id='"+data[i].placeid+"'>";
						}else{
							result+="<input type='checkbox'  class='checkbox1' id='"+data[i].placeid+"' checked>";
						}
						result+="<label th:for='"+data[i].placeid+"'></label>";
						result+="</div>";
						result+="</td>";
						result+="<td class='td_two'>";
						result+="<input type='hidden' class='placeid' name='placeid' value='"+data[i].placeid+"'/>";
						if(data[i].bookmark == 0){
							result+="<img class='star_img' src='../image/icon/star_off.png'/>";
						}else{
							result+="<img class='star_img' src='../image/icon/star_on.png'/>";
						}
						result+="</td>";
						result+="<td class=''>";
						result+="<div class='media'>";
						result+="<img src='../image/icon"+data[i].iconname+".png' class='media-photo'>";
						result+="<div class='media-body'>";
						result+="<span class='media-meta pull-right'>"+data[i].placeregdate+"</span>";
						
						if(data[i].placecategory ==0){
							result+="<button type='button' class='btn btn-outline-success btn_category'>음식</button>";
						}else if (data[i].placecategory ==1){
							result+="<button type='button' class='btn btn-outline-info btn_category'>카페</button>";
						}else{
							result+="<button type='button' class='btn btn-outline-warning btn_category'>관광지</button>";
						}
						result+="<h4 class='title'>"+data[i].placename+"</h4>";
						result+="<span  class='summary'>"+data[i].placejuso+"</span>";
						result+="</div>";
						result+="<button class='media_look'>블로그 보기</button>";
						result+="<button class='media_update' data-toggle='modal' data-target='#exampleModal1'>글 수정</button>";
						result+="<button class='media_delete'>글 삭제</button>";
						result+="<img src='../image/icon/cancel.png' class='media_cancel'>";
						result+="</div>";
						result+="</td>";
						result+="</tr>";
				}					
				result+="</tbody>";
				$('.table').html(result).trigger("create");
			}
		});
	} else if ($target == 3) {//갔던곳
		$.ajax({
			url: "/wish/rest/goplace", 
			type:"POST",
			data : {
				"placecheck": 1,
				"uuid": uuid
			},
			success : function(data){
				var count = data.length;
				console.log(data);
				result+="<tbody class='tbody'>";
				for(i=0;i<count;i++){
					result+="<tr  class='tabletr'>";
					result+="<td class=''>";
						result+="<div class='ckbox '>";
						if(data[i].placecheck ==0){
							result+="<input type='checkbox'  class='checkbox1' id='"+data[i].placeid+"'>";
						}else{
							result+="<input type='checkbox'  class='checkbox1' id='"+data[i].placeid+"' checked>";
						}
						result+="<label th:for='"+data[i].placeid+"'></label>";
						result+="</div>";
						result+="</td>";
						result+="<td class='td_two'>";
						result+="<input type='hidden' class='placeid' name='placeid' value='"+data[i].placeid+"'/>";
						if(data[i].bookmark == 0){
							result+="<img class='star_img' src='../image/icon/star_off.png'/>";
						}else{
							result+="<img class='star_img' src='../image/icon/star_on.png'/>";
						}
						result+="</td>";
						result+="<td class=''>";
						result+="<div class='media'>";
						result+="<img src='../image/icon"+data[i].iconname+".png' class='media-photo'>";
						result+="<div class='media-body'>";
						result+="<span class='media-meta pull-right'>"+data[i].placeregdate+"</span>";
						
						if(data[i].placecategory ==0){
							result+="<button type='button' class='btn btn-outline-success btn_category'>음식</button>";
						}else if (data[i].placecategory ==1){
							result+="<button type='button' class='btn btn-outline-info btn_category'>카페</button>";
						}else{
							result+="<button type='button' class='btn btn-outline-warning btn_category'>관광지</button>";
						}
						result+="<h4 class='title'>"+data[i].placename+"</h4>";
						result+="<span  class='summary'>"+data[i].placejuso+"</span>";
						result+="</div>";
						result+="<button class='media_look'>블로그 보기</button>";
						result+="<button class='media_update' data-toggle='modal' data-target='#exampleModal1'>글 수정</button>";
						result+="<button class='media_delete'>글 삭제</button>";
						result+="<img src='../image/icon/cancel.png' class='media_cancel'>";
						result+="</div>";
						result+="</td>";
						result+="</tr>";
				}					
				result+="</tbody>";
				$('.table').html(result).trigger("create");
			}
		});
	} else {
		$.ajax({
			url: "/wish/rest/goplace", 
			type:"POST",
			data : {
				"placecheck": 0,
				"uuid": uuid
			},
			success : function(data){
				var count = data.length;
				console.log(data);
				result+="<tbody class='tbody'>";
				for(i=0;i<count;i++){
				result+="<tr  class='tabletr'>";
				result+="<td class=''>";
					result+="<div class='ckbox '>";
					if(data[i].placecheck ==0){
						result+="<input type='checkbox'  class='checkbox1' th:id='"+data[i].placeid+"'>";
					}else{
						result+="<input type='checkbox'  class='checkbox1' th:id='"+data[i].placeid+"' checked>";
					}
					result+="<label th:for='"+data[i].placeid+"'></label>";
					result+="</div>";
					result+="</td>";
					result+="<td class='td_two'>";
					result+="<input type='hidden' class='placeid' name='placeid' value='"+data[i].placeid+"'/>";
					if(data[i].bookmark == 0){
						result+="<img class='star_img' src='../image/icon/star_off.png'/>";
					}else{
						result+="<img class='star_img' src='../image/icon/star_on.png'/>";
					}
					result+="</td>";
					result+="<td class=''>";
					result+="<div class='media'>";
					result+="<img src='../image/icon"+data[i].iconname+".png' class='media-photo'>";
					result+="<div class='media-body'>";
					result+="<span class='media-meta pull-right'>"+data[i].placeregdate+"</span>";
				
					if(data[i].placecategory ==0){
						result+="<button type='button' class='btn btn-outline-success btn_category'>음식</button>";
					}else if (data[i].placecategory ==1){
						result+="<button type='button' class='btn btn-outline-info btn_category'>카페</button>";
					}else{
						result+="<button type='button' class='btn btn-outline-warning btn_category'>관광지</button>";
					}
					result+="<h4 class='title'>"+data[i].placename+"</h4>";
					result+="<span  class='summary'>"+data[i].placejuso+"</span>";
					result+="</div>";
					result+="<button class='media_look'>블로그 보기</button>";
					result+="<button class='media_update' data-toggle='modal' data-target='#exampleModal1'>글 수정</button>";
					result+="<button class='media_delete'>글 삭제</button>";
					result+="<img src='../image/icon/cancel.png' class='media_cancel'>";
					result+="</div>";
					result+="</td>";
					result+="</tr>";
				}					
				result+="</tbody>";
				$('.table').html(result).trigger("create");
			}
		});
	}
	
	var btn = $(this);
	btn.attr("disabled", true);
	setTimeout(function() {
		btn.removeAttr("disabled");
	}, 1000);
	
	
});





//$('.checkbox1').on('click',function(){
$(document).on('click','.checkbox1',function(){
	var checkboxid= $(this).attr("id");
	var changeplace =$(this).parents("tr");
	if($(this).is(":checked")){
		$.ajax({
			url: "/wish/rest/checkbox", 
			type : "POST",
			data : {
				"placecheck" : 1,
				"placeid": checkboxid
			},
			success : function(){
			}
		});
	}else{
		$.ajax({
			url: "/wish/rest/checkbox", 
			type : "POST",
			data : {
				"placecheck" : 0,
				"placeid": checkboxid
			},
			success : function(){
			}
		});
	}
	
	var check = $(this).parents("tr");
	check.animate({
		opacity:"0.1",
	},1000,
	function(){
		check.remove();
	});
	
	
})

//테마 선택에 따른 아이콘 이름 

$('.custom-select').change(function(){
	var name = $(this).val();
	
	if(name ==0){
		console.log("음식");
		icon();
		$('.btn-icon-name').html("음식 아이콘");
	}else if(name ==1){
		console.log("카페");
		icon();
		$('.btn-icon-name').html("카페 아이콘");
	}else{
		console.log("관광지");
		icon();
		$('.btn-icon-name').html("관광지 아이콘");
	}
})


//등록 검증
function wish_regist(){
	var map = $('input[name=placename]').val();
	var link= $('input[name=bloglink]').val();
	var icon = $('input[name=iconname]').val();
//1. 지도 
//2. 블로그
//3. 아이콘 
	if(map ==""){
		alert("장소를 선택해주세요");
		return false;
	}else if(link ==""){
		alert("블로그를 선택해주세요");
		return false;
	}else if(icon ==""){
		alert("아이콘을 선택해주세요");
		return false;
	}
	$('#regist_form').submit();

}
//클릭시 블로그 보기 , 글 수정 , 글 삭제 버튼으로 전환
$(document).on('click','.media-body',function(){
	
	var friend = $(this).parents('.media');
	var look= friend.children('.media_look');
	var update=friend.children('.media_update');
	var del=friend.children('.media_delete');
	var can=friend.children('.media_cancel');
	console.log(look);
	$(this).animate({
		opacity:"0",
	},1000,
	function(){
		$(this).children('.title').css('fontSize','0');
		$(this).children('.summary').css('fontSize','0');
		friend.children('.media_look').css('display','block');
		friend.children('.media_update').css('display','block');
		friend.children('.media_delete').css('display','block');
		friend.children('.media_cancel').css('display','block');
	})
	
})


//글 삭제
$(document).on('click','.media_delete',function(){
	var theme = $(this).parents('.tabletr').children('.td_two');
	var del = theme.children('.placeid').val();
	var check =confirm("삭제 하시겠습니까?");
   
   if(check){
	   $.ajax({
			url: "/wish/rest/delete", 
			type: "POST",
			data : {
				"placeid": del
			},
			success : function(){
				location.reload();
			}
		});
   }
})
//취소 버튼

$(document).on('click','.media_cancel',function(){
	var pop = $(this).parents('.media');
	$(this).parents('.media').children('.media-body').children('.title').css('fontSize','16px');
	pop.children('.summary').css('fontSize','12px');
	pop.children('.media_look').css("display","none");
	pop.children('.media_update').css("display","none");
	pop.children('.media_delete').css("display","none");
	pop.children('.media_cancel').css("display","none");
	$(this).parents('.media').children('.media-body').animate({
		opacity:"1",
	},1000)
})




