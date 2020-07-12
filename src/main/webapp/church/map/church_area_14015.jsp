<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="map" style="width:1200px;height:560px;"></div>
<p id="result"></p>
<script type="text/javascript" src="js/apis.daum.net_maps_maps3.js?apikey=253a049954b808428c02e7c8ce8663d9"></script>
<script>
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new daum.maps.LatLng(37.462498, 126.726516), // 지도의 중심좌표
        level: 5 // 지도의 확대 레벨  
    };

var map = new daum.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

// 다각형을 구성하는 좌표 배열입니다. 이 좌표들을 이어서 다각형을 표시합니다 
var polygonPath = [        
        new daum.maps.LatLng(37.4610177, 126.7201769),
new daum.maps.LatLng(37.4650163, 126.7210287),
new daum.maps.LatLng(37.465758,  126.7214168),
new daum.maps.LatLng(37.4652294, 126.7232768),
new daum.maps.LatLng(37.4648271, 126.7251467),
new daum.maps.LatLng(37.4647092, 126.7264144),
new daum.maps.LatLng(37.4646872, 126.7271949),
new daum.maps.LatLng(37.4645066, 126.7271402),
new daum.maps.LatLng(37.4643742, 126.7275713),
new daum.maps.LatLng(37.4643219, 126.7278319),
new daum.maps.LatLng(37.4641963, 126.7279236),
new daum.maps.LatLng(37.4638849, 126.7285601),
new daum.maps.LatLng(37.4636591, 126.7284832),
new daum.maps.LatLng(37.4628115, 126.7284354),
new daum.maps.LatLng(37.462441,  126.7282809),
new daum.maps.LatLng(37.4618433, 126.7292369),
new daum.maps.LatLng(37.4621521, 126.7295956),
new daum.maps.LatLng(37.4616241, 126.7301777),
new daum.maps.LatLng(37.4615622, 126.7303592),
new daum.maps.LatLng(37.4616028, 126.7310599),
new daum.maps.LatLng(37.4616545, 126.732077),
new daum.maps.LatLng(37.4617731, 126.7350723),
new daum.maps.LatLng(37.4616587, 126.7355144),
new daum.maps.LatLng(37.4604309, 126.7338195),
new daum.maps.LatLng(37.4593648, 126.7320439),
new daum.maps.LatLng(37.4589457, 126.7313471),
new daum.maps.LatLng(37.4584553, 126.7307868),
new daum.maps.LatLng(37.4575186, 126.7295074),
new daum.maps.LatLng(37.4590986, 126.7271506),
new daum.maps.LatLng(37.4597505, 126.7262168),
new daum.maps.LatLng(37.460096,  126.7252973),
new daum.maps.LatLng(37.4602883, 126.7243907),
new daum.maps.LatLng(37.4603997, 126.7235076),
new daum.maps.LatLng(37.4606332, 126.7220127),
new daum.maps.LatLng(37.4609745, 126.72046)        
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

var iwContent = '<div style="padding:5px;">만수3동 성당</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
    iwPosition = new daum.maps.LatLng(37.462498, 126.726516), //인포윈도우 표시 위치입니다
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
