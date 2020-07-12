<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="map" style="width:1200px;height:560px;"></div>
<p id="result"></p>
<script type="text/javascript" src="js/apis.daum.net_maps_maps3.js?apikey=253a049954b808428c02e7c8ce8663d9"></script>
<script>
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new daum.maps.LatLng(37.463602, 126.714395), // 지도의 중심좌표
        level: 5 // 지도의 확대 레벨  
    };

var map = new daum.maps.Map(mapContainer, mapOption); // 지도를 생성합니다


// 다각형을 구성하는 좌표 배열입니다. 이 좌표들을 이어서 다각형을 표시합니다 
var polygonPath = [        
        new daum.maps.LatLng(37.4568942, 126.7086755),
new daum.maps.LatLng(37.4604802, 126.7084795),
new daum.maps.LatLng(37.4648409, 126.708196),
new daum.maps.LatLng(37.4673361, 126.7079662),
new daum.maps.LatLng(37.4689771, 126.7080506),
new daum.maps.LatLng(37.4706178, 126.7080898),
new daum.maps.LatLng(37.4705777, 126.7088367),
new daum.maps.LatLng(37.4700264, 126.7099507),
new daum.maps.LatLng(37.470779,  126.7106213),
new daum.maps.LatLng(37.4709666, 126.7103706),
new daum.maps.LatLng(37.4715857, 126.7113027),
new daum.maps.LatLng(37.4711998, 126.7115442),
new daum.maps.LatLng(37.4705936, 126.7112113),
new daum.maps.LatLng(37.470425,  126.7116089),
new daum.maps.LatLng(37.4704278, 126.7120273),
new daum.maps.LatLng(37.4697529, 126.7135384),
new daum.maps.LatLng(37.4691739, 126.7145849),
new daum.maps.LatLng(37.4681856, 126.7150589),
new daum.maps.LatLng(37.4670504, 126.7151273),
new daum.maps.LatLng(37.4666831, 126.7181956),
new daum.maps.LatLng(37.4661731, 126.7187551),
new daum.maps.LatLng(37.4658785, 126.7205561),
new daum.maps.LatLng(37.4656832, 126.7210104),
new daum.maps.LatLng(37.4636892, 126.7207145),
new daum.maps.LatLng(37.4610265, 126.7201429),
new daum.maps.LatLng(37.4558658, 126.7193937),
new daum.maps.LatLng(37.4564603, 126.7138925),
new daum.maps.LatLng(37.4568775, 126.7088792)        
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

var iwContent = '<div style="padding:5px;">간석2동 성당</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
    iwPosition = new daum.maps.LatLng(37.462025, 126.714214), //인포윈도우 표시 위치입니다
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
