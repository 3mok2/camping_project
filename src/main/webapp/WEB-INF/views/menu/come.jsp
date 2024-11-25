<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Dongle&family=Gugi&display=swap" rel="stylesheet">
</head>
<body>
<h1 align="center" style="font-family: 'Dongle', sans-serif; font-size:50px;"><b>오시는 길</b></h1>
<div class="container" id="map" style="width: 500px; height: 500px;"></div>
<br>
<h4 align="center" style="font-family: 'Dongle', sans-serif;">주소: 대구광역시 중구 경상감영길 177<br>
영업시간: 오후 7:00에 영업 종료 <br>
연락처: 000-0000-0000</h4>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=87fe57b8f4a51f353c0968d8ad8739b5">

</script>

<script type="text/javascript">
	
	var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
	var options = { //지도를 생성할 때 필요한 기본 옵션
		center: new kakao.maps.LatLng(35.872218, 128.597193), //지도의 중심좌표.
		level: 3 //지도의 레벨(확대, 축소 정도)
	};
	
	var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
	
	var markerPosition  = new kakao.maps.LatLng(35.872218, 128.597193); 

	// 마커를 생성합니다
	var marker = new kakao.maps.Marker({
	    position: markerPosition
	});
	
	// 마커가 지도 위에 표시되도록 설정합니다
	marker.setMap(map);

	// 아래 코드는 지도 위의 마커를 제거하는 코드입니다
	// marker.setMap(null);
	
</script>

</body>
</html>
<%@ include file="../includes/footer.jsp"%>