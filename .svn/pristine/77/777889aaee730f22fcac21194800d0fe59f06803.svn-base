<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="map" style="width:1200px;height:560px;"></div>
<p id="result"></p>
<script type="text/javascript" src="js/apis.daum.net_maps_maps3.js?apikey=253a049954b808428c02e7c8ce8663d9"></script>
<script>
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new daum.maps.LatLng(37.545741, 126.727235), // 지도의 중심좌표
        level: 5 // 지도의 확대 레벨  
    };

var map = new daum.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

// 다각형을 구성하는 좌표 배열입니다. 이 좌표들을 이어서 다각형을 표시합니다 
var polygonPath = [        
       new daum.maps.LatLng(37.54552,  126.7108753),
new daum.maps.LatLng(37.5448558,126.7126369),
new daum.maps.LatLng(37.5441735,126.7143987),
new daum.maps.LatLng(37.5438232,126.7159418),
new daum.maps.LatLng(37.543725, 126.7174371),
new daum.maps.LatLng(37.5434444,126.7227263),
new daum.maps.LatLng(37.5432477,126.7284562),
new daum.maps.LatLng(37.5372961,126.7280421),
new daum.maps.LatLng(37.537752, 126.730222),
new daum.maps.LatLng(37.5382459,126.7327071),
new daum.maps.LatLng(37.5381493,126.7344851),
new daum.maps.LatLng(37.539809, 126.7346832),
new daum.maps.LatLng(37.5410313,126.7355197),
new daum.maps.LatLng(37.5451171,126.7387381),
new daum.maps.LatLng(37.5474726,126.7406104),
new daum.maps.LatLng(37.5475286,126.7374118),
new daum.maps.LatLng(37.5479705,126.7374503),
new daum.maps.LatLng(37.5479922,126.7366203),
new daum.maps.LatLng(37.5488107,126.7366346),
new daum.maps.LatLng(37.5489553,126.7359878),
new daum.maps.LatLng(37.5489999,126.7352345),
new daum.maps.LatLng(37.54848,  126.7277796),
new daum.maps.LatLng(37.5486573,126.7245741),
new daum.maps.LatLng(37.5493672,126.7228799),
new daum.maps.LatLng(37.5488049,126.7209839),
new daum.maps.LatLng(37.5477422,126.7197497),
new daum.maps.LatLng(37.5460029,126.7116513)   
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


var iwContent = '<div style="padding:5px;">계산동성당</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
    iwPosition = new daum.maps.LatLng(37.545741, 126.727235), //인포윈도우 표시 위치입니다
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
