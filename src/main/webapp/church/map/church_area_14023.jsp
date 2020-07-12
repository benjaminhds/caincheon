<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="map" style="width:1200px;height:560px;"></div>
<p id="result"></p>
<script type="text/javascript" src="js/apis.daum.net_maps_maps3.js?apikey=253a049954b808428c02e7c8ce8663d9"></script>
<script>
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new daum.maps.LatLng(37.512201, 126.703302), // 지도의 중심좌표
        level: 5 // 지도의 확대 레벨  
    };

var map = new daum.maps.Map(mapContainer, mapOption); // 지도를 생성합니다


// 다각형을 구성하는 좌표 배열입니다. 이 좌표들을 이어서 다각형을 표시합니다 
var polygonPath = [        
        new daum.maps.LatLng(37.5039807, 126.7086992),
new daum.maps.LatLng(37.5082651, 126.7091519),
new daum.maps.LatLng(37.512684,  126.7095013),
new daum.maps.LatLng(37.5158408, 126.7098074),
new daum.maps.LatLng(37.5171671, 126.7100084),
new daum.maps.LatLng(37.5225427, 126.7105402),
new daum.maps.LatLng(37.5236159, 126.7106421),
new daum.maps.LatLng(37.5235231, 126.7048602),
new daum.maps.LatLng(37.5224952, 126.7048145),
new daum.maps.LatLng(37.522465,  126.7003335),
new daum.maps.LatLng(37.5224645, 126.6962821),
new daum.maps.LatLng(37.5226526, 126.6947863),
new daum.maps.LatLng(37.5213368, 126.6948118),
new daum.maps.LatLng(37.520935,  126.6953593),
new daum.maps.LatLng(37.5209505, 126.696321),
new daum.maps.LatLng(37.5205117, 126.6967331),
new daum.maps.LatLng(37.5205668, 126.698215),
new daum.maps.LatLng(37.5200499, 126.6990805),
new daum.maps.LatLng(37.5195068, 126.7000708),
new daum.maps.LatLng(37.5193529, 126.6999706),
new daum.maps.LatLng(37.5192431, 126.6997342),
new daum.maps.LatLng(37.5189665, 126.7001445),
new daum.maps.LatLng(37.5188681, 126.7002587),
new daum.maps.LatLng(37.5185536, 126.7003979),
new daum.maps.LatLng(37.5182381, 126.7004012),
new daum.maps.LatLng(37.5180118, 126.7002565),
new daum.maps.LatLng(37.5179206, 126.7000991),
new daum.maps.LatLng(37.5180538, 126.6997921),
new daum.maps.LatLng(37.5181053, 126.6994182),
new daum.maps.LatLng(37.5180387, 126.6988983),
new daum.maps.LatLng(37.517857,  126.6986853),
new daum.maps.LatLng(37.5173938, 126.6981697),
new daum.maps.LatLng(37.5172037, 126.6980473),
new daum.maps.LatLng(37.516968,  126.6978574),
new daum.maps.LatLng(37.5167235, 126.6976903),
new daum.maps.LatLng(37.5158619, 126.6969074),
new daum.maps.LatLng(37.5152831, 126.6966194),
new daum.maps.LatLng(37.514723,  126.6964331),
new daum.maps.LatLng(37.5144457, 126.6967416),
new daum.maps.LatLng(37.5143566, 126.6968896),
new daum.maps.LatLng(37.5143537, 126.6951471),
new daum.maps.LatLng(37.5142012, 126.6952506),
new daum.maps.LatLng(37.5140333, 126.6957389),
new daum.maps.LatLng(37.5138932, 126.6963514),
new daum.maps.LatLng(37.5137536, 126.6970205),
new daum.maps.LatLng(37.5135011, 126.6970119),
new daum.maps.LatLng(37.5133777, 126.6974093),
new daum.maps.LatLng(37.5134353, 126.6979292),
new daum.maps.LatLng(37.5133023, 126.6982587),
new daum.maps.LatLng(37.5131046, 126.6983401),
new daum.maps.LatLng(37.5128695, 126.6982294),
new daum.maps.LatLng(37.5120446, 126.697548),
new daum.maps.LatLng(37.5110756, 126.6968795),
new daum.maps.LatLng(37.5105322, 126.6964893),
new daum.maps.LatLng(37.5101227, 126.6972405),
new daum.maps.LatLng(37.5099571, 126.6980795),
new daum.maps.LatLng(37.5095224, 126.6977787),
new daum.maps.LatLng(37.5089945, 126.6970263),
new daum.maps.LatLng(37.5085374, 126.6974045),
new daum.maps.LatLng(37.5082326, 126.6976454),
new daum.maps.LatLng(37.5076478, 126.6977988),
new daum.maps.LatLng(37.5070879, 126.6976463),
new daum.maps.LatLng(37.5068695, 126.6973432),
new daum.maps.LatLng(37.5066774, 126.6969266),
new daum.maps.LatLng(37.5064504, 126.6966802),
new daum.maps.LatLng(37.5063746, 126.6961379),
new daum.maps.LatLng(37.5061742, 126.6958232),
new daum.maps.LatLng(37.5057841, 126.6954654),
new daum.maps.LatLng(37.5064878, 126.6942359),
new daum.maps.LatLng(37.506799,  126.6936103),
new daum.maps.LatLng(37.5076317, 126.692798),
new daum.maps.LatLng(37.5074463, 126.6920419),
new daum.maps.LatLng(37.5072183, 126.6916597),
new daum.maps.LatLng(37.5067657, 126.6913704),
new daum.maps.LatLng(37.5063209, 126.6922351),
new daum.maps.LatLng(37.5053394, 126.6923701),
new daum.maps.LatLng(37.5048616, 126.6923526),
new daum.maps.LatLng(37.5046455, 126.6923889),
new daum.maps.LatLng(37.5049335, 126.693653),
new daum.maps.LatLng(37.5048662, 126.6956789),
new daum.maps.LatLng(37.5046574, 126.6981136),
new daum.maps.LatLng(37.5043135, 126.7032311),
new daum.maps.LatLng(37.5040234, 126.7083368)       
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

var iwContent = '<div style="padding:5px;">산곡동 성당</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
    iwPosition = new daum.maps.LatLng(37.512201, 126.703302), //인포윈도우 표시 위치입니다
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
