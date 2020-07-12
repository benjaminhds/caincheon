<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="map" style="width:1200px;height:560px;"></div>
<p id="result"></p>
<script type="text/javascript" src="js/apis.daum.net_maps_maps3.js?apikey=253a049954b808428c02e7c8ce8663d9"></script>
<script>
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new daum.maps.LatLng(37.949800, 124.674592), // 지도의 중심좌표
        level: 8 // 지도의 확대 레벨  
    };

var map = new daum.maps.Map(mapContainer, mapOption); // 지도를 생성합니다


var iwContent = '<div style="padding:5px;">백령도 성당</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
    iwPosition = new daum.maps.LatLng(37.972616, 124.719926) //인포윈도우 표시 위치입니다
    iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

// 인포윈도우를 생성하고 지도에 표시합니다
var infowindow = new daum.maps.InfoWindow({
    map: map, // 인포윈도우가 표시될 지도
    position : iwPosition, 
    content : iwContent,
    removable : iwRemoveable
});


// 다각형을 구성하는 좌표 배열입니다. 이 좌표들을 이어서 다각형을 표시합니다 
var polygonPath = [        
        new daum.maps.LatLng(37.9820045, 124.6190911),
        new daum.maps.LatLng(37.9721235, 124.6395101),
		new daum.maps.LatLng(37.9874589, 124.6895526),
        new daum.maps.LatLng(37.9798701, 124.7342833),
        new daum.maps.LatLng(37.9657885, 124.7362688),
		new daum.maps.LatLng(37.9583006, 124.7489313),
        new daum.maps.LatLng(37.9535378, 124.7425545),		
		new daum.maps.LatLng(37.9472231, 124.7315033),
		new daum.maps.LatLng(37.9496528, 124.7244937),
		new daum.maps.LatLng(37.933378, 124.7069042),
		new daum.maps.LatLng(37.9278874, 124.706384),
		new daum.maps.LatLng(37.9202937, 124.7023038),
		new daum.maps.LatLng(37.912195, 124.6891452),
		new daum.maps.LatLng(37.9195736, 124.6364527),
		new daum.maps.LatLng(37.9526413, 124.6209675),
		new daum.maps.LatLng(37.9606464, 124.6064522),					
        new daum.maps.LatLng(37.9801691, 124.6147918)        
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
