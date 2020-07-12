<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="map" style="width:1200px;height:560px;"></div>
<p id="result"></p>
<script type="text/javascript" src="js/apis.daum.net_maps_maps3.js?apikey=253a049954b808428c02e7c8ce8663d9"></script>
<script>
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new daum.maps.LatLng(37.521092, 126.673199), // 지도의 중심좌표
        level: 5 // 지도의 확대 레벨  
    };

var map = new daum.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

// 다각형을 구성하는 좌표 배열입니다. 이 좌표들을 이어서 다각형을 표시합니다 
var polygonPath = [        
        new daum.maps.LatLng(37.52613, 126.6608655),
		new daum.maps.LatLng(37.5221285, 126.6609329),
		new daum.maps.LatLng(37.5218152, 126.6624981),
		new daum.maps.LatLng(37.5178299, 126.6623163),        			
        new daum.maps.LatLng(37.5136646, 126.6621592),
		new daum.maps.LatLng(37.5137408, 126.6652814),
		new daum.maps.LatLng(37.5136111, 126.6686095),
		new daum.maps.LatLng(37.513423, 126.6726285),
		new daum.maps.LatLng(37.5132603, 126.6764322),        			
        new daum.maps.LatLng(37.5158305, 126.6766302),
		new daum.maps.LatLng(37.5178902, 126.6772979),
		new daum.maps.LatLng(37.519865, 126.6787134),
		new daum.maps.LatLng(37.521233, 126.6797281),
		new daum.maps.LatLng(37.5220933, 126.6815973),        			
        new daum.maps.LatLng(37.5232346, 126.6850251),
		new daum.maps.LatLng(37.5235838, 126.6872959),
		new daum.maps.LatLng(37.5240438, 126.6873475),
		new daum.maps.LatLng(37.5249093, 126.6873721),
		new daum.maps.LatLng(37.5257025, 126.6860734),        			
        new daum.maps.LatLng(37.526308, 126.6863044),
		new daum.maps.LatLng(37.5267484, 126.6848171),
		new daum.maps.LatLng(37.5271722, 126.6848464),
		new daum.maps.LatLng(37.5273531, 126.6836335),
		new daum.maps.LatLng(37.5258316, 126.682575),        			
        new daum.maps.LatLng(37.525988, 126.6817358),
		new daum.maps.LatLng(37.5272846, 126.6802504),
		new daum.maps.LatLng(37.5293377, 126.6799676),
		new daum.maps.LatLng(37.5303109, 126.679923),
		new daum.maps.LatLng(37.5305767, 126.6779621),
		new daum.maps.LatLng(37.5293607, 126.6754969),
		new daum.maps.LatLng(37.5286009, 126.6751092),
		new daum.maps.LatLng(37.527818, 126.674314),
		new daum.maps.LatLng(37.527971, 126.673113),
		new daum.maps.LatLng(37.528601, 126.672190),
		new daum.maps.LatLng(37.529366, 126.671954),
		new daum.maps.LatLng(37.529690, 126.670302),
		new daum.maps.LatLng(37.530847, 126.669744),
		new daum.maps.LatLng(37.532293, 126.670195),
		new daum.maps.LatLng(37.533331, 126.670323),
		new daum.maps.LatLng(37.534488, 126.670924),
		new daum.maps.LatLng(37.535118, 126.670302),
		new daum.maps.LatLng(37.535390, 126.667748),
		new daum.maps.LatLng(37.534828, 126.665817),
		new daum.maps.LatLng(37.534573, 126.663199),
		new daum.maps.LatLng(37.534777, 126.661333),
		new daum.maps.LatLng(37.534335, 126.660689),
		new daum.maps.LatLng(37.533944, 126.660667),
		new daum.maps.LatLng(37.532991, 126.662041),
		new daum.maps.LatLng(37.530268, 126.662169),
		new daum.maps.LatLng(37.527784, 126.661526),
		new daum.maps.LatLng(37.526627, 126.661290),	
        new daum.maps.LatLng(37.5264562, 126.6611108)        
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

var iwContent = '<div style="padding:5px;">가정동 성당</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
    iwPosition = new daum.maps.LatLng(37.523695079948006, 126.66217193160746), //인포윈도우 표시 위치입니다
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
