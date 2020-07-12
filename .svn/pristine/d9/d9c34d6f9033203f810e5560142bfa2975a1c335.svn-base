<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="map" style="width:1200px;height:560px;"></div>
<p id="result"></p>
<script type="text/javascript" src="js/apis.daum.net_maps_maps3.js?apikey=253a049954b808428c02e7c8ce8663d9"></script>
<script>
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new daum.maps.LatLng(37.454643, 126.737833), // 지도의 중심좌표
        level: 5 // 지도의 확대 레벨  
    };

var map = new daum.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

// 다각형을 구성하는 좌표 배열입니다. 이 좌표들을 이어서 다각형을 표시합니다 
var polygonPath = [        
        new daum.maps.LatLng(37.4612229, 126.7378143),
new daum.maps.LatLng(37.4598655, 126.7397729),
new daum.maps.LatLng(37.4607995, 126.7406454),
new daum.maps.LatLng(37.4607228, 126.7413359),
new daum.maps.LatLng(37.4582537, 126.7484842),
new daum.maps.LatLng(37.4569897, 126.7481577),
new daum.maps.LatLng(37.4556919, 126.7481707),
new daum.maps.LatLng(37.4544466, 126.747957),
new daum.maps.LatLng(37.4532731, 126.7476748),
new daum.maps.LatLng(37.4519351, 126.747055),
new daum.maps.LatLng(37.4500006, 126.74617),
new daum.maps.LatLng(37.4506277, 126.7427493),
new daum.maps.LatLng(37.4498032, 126.7420906),
new daum.maps.LatLng(37.4493758, 126.7414957),
new daum.maps.LatLng(37.448931,  126.7410028),
new daum.maps.LatLng(37.4480707, 126.7403897),
new daum.maps.LatLng(37.4483077, 126.7338188),
new daum.maps.LatLng(37.4484694, 126.7295663),
new daum.maps.LatLng(37.4525728, 126.7299199),
new daum.maps.LatLng(37.4550442, 126.7301773),
new daum.maps.LatLng(37.4552539, 126.7284622),
new daum.maps.LatLng(37.456052,  126.7285218),
new daum.maps.LatLng(37.4570173, 126.7286646),
new daum.maps.LatLng(37.4575442, 126.7292923),
new daum.maps.LatLng(37.4590594, 126.7315156),
new daum.maps.LatLng(37.460358,  126.7336959),
new daum.maps.LatLng(37.4617046, 126.7356383),
new daum.maps.LatLng(37.46138,   126.7370098),
new daum.maps.LatLng(37.4612213, 126.7375655)        
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

var iwContent = '<div style="padding:5px;">만수1동 성당</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
    iwPosition = new daum.maps.LatLng(37.453585, 126.734038), //인포윈도우 표시 위치입니다
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
