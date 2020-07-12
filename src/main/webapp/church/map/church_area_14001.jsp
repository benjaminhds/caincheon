<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="map" style="width:1200px;height:560px;"></div>
<p id="result"></p>
<script type="text/javascript" src="js/apis.daum.net_maps_maps3.js?apikey=253a049954b808428c02e7c8ce8663d9"></script>
<script>
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new daum.maps.LatLng(37.469578, 126.630567), // 지도의 중심좌표
        level: 5 // 지도의 확대 레벨  
    };

var map = new daum.maps.Map(mapContainer, mapOption); // 지도를 생성합니다


var iwContent = '<div style="padding:5px;">답동주교좌성당</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
    iwPosition = new daum.maps.LatLng(37.471077, 126.629826), //인포윈도우 표시 위치입니다
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
        new daum.maps.LatLng(37.4732967, 126.6295535),
        new daum.maps.LatLng(37.4742635, 126.6292708),
		new daum.maps.LatLng(37.4753566, 126.628998),
        new daum.maps.LatLng(37.4753202, 126.6265047),
        new daum.maps.LatLng(37.4752291,126.6263701),
		new daum.maps.LatLng(37.474737, 126.6262514),
        new daum.maps.LatLng(37.4747986, 126.6266578),
		new daum.maps.LatLng(37.4747684, 126.6268391),
		new daum.maps.LatLng(37.4746792, 126.6269759),
		new daum.maps.LatLng(37.4745714, 126.6270224),
		new daum.maps.LatLng(37.4742081, 126.6272584),
		new daum.maps.LatLng(37.4740731, 126.627277),
		new daum.maps.LatLng(37.4738291, 126.627195),
		new daum.maps.LatLng(37.4735481, 126.626989),
		new daum.maps.LatLng(37.4726908, 126.62623),
		new daum.maps.LatLng(37.4719287, 126.6255603),
		new daum.maps.LatLng(37.4706537, 126.6243991),
		new daum.maps.LatLng(37.4701498, 126.6239017),
		new daum.maps.LatLng(37.4693595, 126.6230741),
		new daum.maps.LatLng(37.468005, 126.625182),
		new daum.maps.LatLng(37.4666763, 126.6271085),
		new daum.maps.LatLng(37.4657168, 126.6265656),
		new daum.maps.LatLng(37.4642257, 126.6297039),
		new daum.maps.LatLng(37.4630591, 126.6328496),
		new daum.maps.LatLng(37.4664713, 126.6347778),
		//new daum.maps.LatLng(37.4736093, 126.6303867),
		new daum.maps.LatLng(37.4661715, 126.6356859),
		new daum.maps.LatLng(37.4664711, 126.6359765),
		new daum.maps.LatLng(37.4667021, 126.6361434),
		new daum.maps.LatLng(37.467163, 126.6363134),
		new daum.maps.LatLng(37.4679861, 126.6367054),
		new daum.maps.LatLng(37.4692116, 126.6372737),
		new daum.maps.LatLng(37.4687582, 126.6375107),
		new daum.maps.LatLng(37.4689683, 126.6378928),
		new daum.maps.LatLng(37.4695881, 126.6382306),
		new daum.maps.LatLng(37.4694962, 126.6386048),
		new daum.maps.LatLng(37.4701212, 126.6390274),
		new daum.maps.LatLng(37.4710989, 126.6371276),
		new daum.maps.LatLng(37.4727297, 126.6351976),
		new daum.maps.LatLng(37.4732387, 126.6345415),
		new daum.maps.LatLng(37.4736175, 126.6333496),
		new daum.maps.LatLng(37.4742461, 126.6324094),
		new daum.maps.LatLng(37.4740728, 126.6315292),
		new daum.maps.LatLng(37.473807, 126.6309216),			
        new daum.maps.LatLng(37.4733252, 126.6297567)        
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
