<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="map" style="width:1200px;height:560px;"></div>
<p id="result"></p>
<script type="text/javascript" src="js/apis.daum.net_maps_maps3.js?apikey=253a049954b808428c02e7c8ce8663d9"></script>
<script>
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new daum.maps.LatLng(37.470104, 126.653639), // 지도의 중심좌표
        level: 5 // 지도의 확대 레벨  
    };

var map = new daum.maps.Map(mapContainer, mapOption); // 지도를 생성합니다


var iwContent = '<div style="padding:5px;">도화동 성당</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
    iwPosition = new daum.maps.LatLng(37.470065, 126.652349), //인포윈도우 표시 위치입니다
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
        new daum.maps.LatLng(37.4759088, 126.6447458),
new daum.maps.LatLng(37.4771123, 126.6491429),
new daum.maps.LatLng(37.4764873, 126.6487089),
new daum.maps.LatLng(37.4757082, 126.6481635),
new daum.maps.LatLng(37.4750862, 126.648148),
new daum.maps.LatLng(37.4743506, 126.6486426),
new daum.maps.LatLng(37.4745637, 126.6494432),
new daum.maps.LatLng(37.4745219, 126.649896),
new daum.maps.LatLng(37.4741742, 126.6504315),
new daum.maps.LatLng(37.4737816, 126.6509901),
new daum.maps.LatLng(37.4736244, 126.6516817),
new daum.maps.LatLng(37.4737897, 126.6521096),
new daum.maps.LatLng(37.4734854, 126.6524071),
new daum.maps.LatLng(37.4731619, 126.6525578),
new daum.maps.LatLng(37.4727746, 126.6525848),
new daum.maps.LatLng(37.472062,  126.6525136),
new daum.maps.LatLng(37.4719402, 126.6518704),
new daum.maps.LatLng(37.4716753, 126.6513758),
new daum.maps.LatLng(37.4712104, 126.6506573),
new daum.maps.LatLng(37.4708568, 126.6503673),
new daum.maps.LatLng(37.4703441, 126.6505088),
new daum.maps.LatLng(37.4699054, 126.6509322),
new daum.maps.LatLng(37.4697148, 126.6519973),
new daum.maps.LatLng(37.4703847, 126.6523969),
new daum.maps.LatLng(37.4703607, 126.6528269),
new daum.maps.LatLng(37.4699655, 126.6530236),
new daum.maps.LatLng(37.4696638, 126.6524276),
new daum.maps.LatLng(37.4696174, 126.6522472),
new daum.maps.LatLng(37.4684806, 126.6520904),
new daum.maps.LatLng(37.4681693, 126.6539259),
new daum.maps.LatLng(37.4680656, 126.6596039),
new daum.maps.LatLng(37.4688778, 126.6610083),
new daum.maps.LatLng(37.4697008, 126.6601397),
new daum.maps.LatLng(37.4701771, 126.6599421),
new daum.maps.LatLng(37.4707136, 126.660592),
new daum.maps.LatLng(37.4705185, 126.6610465),
new daum.maps.LatLng(37.4712772, 126.6612642),
new daum.maps.LatLng(37.4711913, 126.6618646),
new daum.maps.LatLng(37.4714156, 126.6629929),
new daum.maps.LatLng(37.4711584, 126.6635839),
new daum.maps.LatLng(37.4714498, 126.663999),
new daum.maps.LatLng(37.4714079, 126.6644519),
new daum.maps.LatLng(37.4710696, 126.6650437),
new daum.maps.LatLng(37.4710901, 126.665394),
new daum.maps.LatLng(37.4714977, 126.6656722),
new daum.maps.LatLng(37.4712324, 126.6663989),
new daum.maps.LatLng(37.4709843, 126.6670011),
new daum.maps.LatLng(37.4700158, 126.6664351),
new daum.maps.LatLng(37.4691459, 126.6657775),
new daum.maps.LatLng(37.4685461, 126.6650831),
new daum.maps.LatLng(37.4663695, 126.6670183),
new daum.maps.LatLng(37.4666584, 126.6632608),
new daum.maps.LatLng(37.4668396, 126.6608502),
new daum.maps.LatLng(37.4668121, 126.6569831),
new daum.maps.LatLng(37.4668068, 126.6524712),
new daum.maps.LatLng(37.4668292, 126.649316),
new daum.maps.LatLng(37.4662609, 126.6492546),
new daum.maps.LatLng(37.4662502, 126.647762),
new daum.maps.LatLng(37.4668947, 126.6459002),
new daum.maps.LatLng(37.4672286, 126.6459529),
new daum.maps.LatLng(37.4686749, 126.6427927),
new daum.maps.LatLng(37.4692715, 126.6417907),
new daum.maps.LatLng(37.4697503, 126.6406883),
new daum.maps.LatLng(37.4700867, 126.6410916),
new daum.maps.LatLng(37.470397,  126.6409976),
new daum.maps.LatLng(37.4703594, 126.6420327),
new daum.maps.LatLng(37.4707359, 126.642362),
new daum.maps.LatLng(37.4705023, 126.6424665),
new daum.maps.LatLng(37.4711905, 126.6429053),
new daum.maps.LatLng(37.4720598, 126.6428389),
new daum.maps.LatLng(37.4727231, 126.6435777),
new daum.maps.LatLng(37.4725636, 126.643947),
new daum.maps.LatLng(37.4732274, 126.6441317),
new daum.maps.LatLng(37.4743059, 126.6443286),
new daum.maps.LatLng(37.4758225, 126.6446506)        
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
