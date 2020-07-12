<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="map" style="width:1200px;height:560px;"></div>
<p id="result"></p>
<script type="text/javascript" src="js/apis.daum.net_maps_maps3.js?apikey=253a049954b808428c02e7c8ce8663d9"></script>
<script>
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new daum.maps.LatLng(37.486168, 126.723445), // 지도의 중심좌표
        level: 5 // 지도의 확대 레벨  
    };

var map = new daum.maps.Map(mapContainer, mapOption); // 지도를 생성합니다


// 다각형을 구성하는 좌표 배열입니다. 이 좌표들을 이어서 다각형을 표시합니다 
var polygonPath = [        
        new daum.maps.LatLng(37.4836537, 126.7152025),
		new daum.maps.LatLng(37.4834754, 126.7154984),
		new daum.maps.LatLng(37.4833794, 126.7159745),
		new daum.maps.LatLng(37.4819846, 126.72178),	
		new daum.maps.LatLng(37.4817268, 126.7236828),
		new daum.maps.LatLng(37.4812777, 126.7266734),
		new daum.maps.LatLng(37.4813671, 126.7293191),
		new daum.maps.LatLng(37.4819819, 126.7323893),
		new daum.maps.LatLng(37.4816588, 126.7325962),
		new daum.maps.LatLng(37.4820253, 126.7335199),
		new daum.maps.LatLng(37.4835102, 126.7345453),
		new daum.maps.LatLng(37.4834993, 126.7356539),
		new daum.maps.LatLng(37.486865, 126.7348278),
		new daum.maps.LatLng(37.4876371, 126.7343448),
		new daum.maps.LatLng(37.4881564, 126.7338192),
		new daum.maps.LatLng(37.4888158, 126.7326361),
		new daum.maps.LatLng(37.4893638, 126.7254589),
		new daum.maps.LatLng(37.4897209, 126.71946),	
		new daum.maps.LatLng(37.4894709, 126.7170872),
		new daum.maps.LatLng(37.4889964, 126.7162099),
		new daum.maps.LatLng(37.4876392, 126.7140862),
		new daum.maps.LatLng(37.4858104, 126.7115151),
		new daum.maps.LatLng(37.4853713, 126.711893),	
		new daum.maps.LatLng(37.4852028, 126.7123133),
		new daum.maps.LatLng(37.4847145, 126.7120696),
		new daum.maps.LatLng(37.4838294, 126.7118074),
		new daum.maps.LatLng(37.4829301, 126.7121109),
		new daum.maps.LatLng(37.4827845, 126.7132548),
		new daum.maps.LatLng(37.4836427, 126.7148972)        
];

// 지도에 표시할 다각형을 생성합니다
var polygon = new daum.maps.Polygon({
    path:polygonPath, // 그려질 다각형의 좌표 배열입니다
    strokeWeight: 3, // 선의 두께입니다
    strokeColor: '#9c0707', // 선의 색깔입니다
    strokeOpacity: 0.8, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
    strokeStyle: 'solid', // 선의 스타일입니다
    fillColor: '#EFFFED', // 채우기 색깔입니다
    fillOpacity: 0.2 // 채우기 불투명도 입니다
});

// 지도에 다각형을 표시합니다
polygon.setMap(map);

var iwContent = '<div style="padding:5px;">부평2동 성당</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
    iwPosition = new daum.maps.LatLng(37.486168, 126.723445), //인포윈도우 표시 위치입니다
    iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

// 인포윈도우를 생성하고 지도에 표시합니다
var infowindow = new daum.maps.InfoWindow({
    map: map, // 인포윈도우가 표시될 지도
    position : iwPosition, 
    content : iwContent,
    removable : iwRemoveable
});


// 다각형에 마우스오버 이벤트가 발생했을 때 변경할 채우기 옵션입니다
var mouseoverOption = { 
    fillColor: '#d5f0f7', // 채우기 색깔입니다
    fillOpacity: 0.2 // 채우기 불투명도 입니다        
};

// 다각형에 마우스아웃 이벤트가 발생했을 때 변경할 채우기 옵션입니다
var mouseoutOption = {
    fillColor: '#EFFFED', // 채우기 색깔입니다 
    fillOpacity: 0.2 // 채우기 불투명도 입니다        
};

// 다각형에 마우스오버 이벤트를 등록합니다
daum.maps.event.addListener(polygon, 'mouseover', function() { 

    // 다각형의 채우기 옵션을 변경합니다
    polygon.setOptions(mouseoverOption);

});   

daum.maps.event.addListener(polygon, 'mouseout', function() { 

    // 다각형의 채우기 옵션을 변경합니다
    polygon.setOptions(mouseoutOption);
    
}); 

// 다각형에 마우스다운 이벤트를 등록합니다
var downCount = 0;
daum.maps.event.addListener(polygon, 'mousedown', function() { 
    console.log(event);
    var resultDiv = document.getElementById('result');
    resultDiv.innerHTML = '다각형에 mousedown 이벤트가 발생했습니다!' + (++downCount);
}); 
</script>
