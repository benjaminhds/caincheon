<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="map" style="width:1200px;height:560px;"></div>
<p id="result"></p>
<script type="text/javascript" src="js/apis.daum.net_maps_maps3.js?apikey=253a049954b808428c02e7c8ce8663d9"></script>
<script>
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new daum.maps.LatLng(37.750842, 126.485060), // 지도의 중심좌표
        level: 8 // 지도의 확대 레벨  
    };

var map = new daum.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

// 다각형을 구성하는 좌표 배열입니다. 이 좌표들을 이어서 다각형을 표시합니다 
var polygonPath = [        
new daum.maps.LatLng(37.6904928,  126.5202588),
new daum.maps.LatLng(37.6901867,  126.5181305),
new daum.maps.LatLng(37.6923933,  126.5146987),
new daum.maps.LatLng(37.6950001,  126.5117152),
new daum.maps.LatLng(37.6954163,  126.5097587),
new daum.maps.LatLng(37.695003,   126.5077679),
new daum.maps.LatLng(37.695237,   126.505587),
new daum.maps.LatLng(37.6949053,  126.5047294),
new daum.maps.LatLng(37.6961961,  126.4996305),
new daum.maps.LatLng(37.6964858,  126.4913237),
new daum.maps.LatLng(37.6984064,  126.4882577),
new daum.maps.LatLng(37.6996788,  126.4811168),
new daum.maps.LatLng(37.7011037,  126.4667137),
new daum.maps.LatLng(37.701422, 126.463334),
new daum.maps.LatLng(37.700743, 126.461789),
new daum.maps.LatLng(37.701082, 126.458914),
new daum.maps.LatLng(37.701082, 126.456425),
new daum.maps.LatLng(37.700777, 126.455395),
new daum.maps.LatLng(37.699826, 126.454923),
new daum.maps.LatLng(37.699588, 126.454064),
new daum.maps.LatLng(37.699724, 126.453421),
new daum.maps.LatLng(37.699249, 126.449816),
new daum.maps.LatLng(37.699011, 126.449129),
new daum.maps.LatLng(37.699249, 126.447670),
new daum.maps.LatLng(37.698909, 126.446940),
new daum.maps.LatLng(37.698870, 126.445432),
new daum.maps.LatLng(37.698802, 126.444595),
new daum.maps.LatLng(37.698904, 126.444123),
new daum.maps.LatLng(37.698853, 126.443479),
new daum.maps.LatLng(37.699328, 126.442943),
new daum.maps.LatLng(37.699447, 126.442277),
new daum.maps.LatLng(37.699362, 126.441677),
new daum.maps.LatLng(37.700176, 126.441727),
new daum.maps.LatLng(37.699429, 126.441234),
new daum.maps.LatLng(37.698461, 126.442800),
new daum.maps.LatLng(37.698087, 126.444088),
new daum.maps.LatLng(37.696475, 126.445096),
new daum.maps.LatLng(37.695778, 126.444281),
new daum.maps.LatLng(37.694284, 126.444667),
new daum.maps.LatLng(37.692637, 126.444195),
new daum.maps.LatLng(37.690769, 126.444667),
new daum.maps.LatLng(37.689751, 126.444409),
new daum.maps.LatLng(37.689954, 126.442809),

new daum.maps.LatLng(37.689506, 126.442312),
new daum.maps.LatLng(37.690219, 126.439908),
new daum.maps.LatLng(37.696196, 126.436775),
new daum.maps.LatLng(37.699422, 126.435059),
new daum.maps.LatLng(37.705483, 126.432999),
new daum.maps.LatLng(37.706179, 126.431196),
new daum.maps.LatLng(37.708250, 126.431089),
new daum.maps.LatLng(37.708640, 126.429716),
new daum.maps.LatLng(37.709862, 126.429287),
new daum.maps.LatLng(37.710474, 126.427420),

new daum.maps.LatLng(37.7197326,  126.4412208),
new daum.maps.LatLng(37.7233051,  126.4375843),
new daum.maps.LatLng(37.7255748,  126.4374157),
new daum.maps.LatLng(37.7285338,  126.4417309),
new daum.maps.LatLng(37.7320648,  126.4414991),
new daum.maps.LatLng(37.7438199,  126.4377451),
new daum.maps.LatLng(37.7451301,  126.4431291),
new daum.maps.LatLng(37.7487921,  126.4494788),
new daum.maps.LatLng(37.7488348,  126.4421683),
new daum.maps.LatLng(37.7549435,  126.443943),
new daum.maps.LatLng(37.7571083,  126.4441393),
new daum.maps.LatLng(37.7583254,  126.4431684),
new daum.maps.LatLng(37.7604132,  126.4428208),
new daum.maps.LatLng(37.7654347,  126.4439755),
new daum.maps.LatLng(37.766969,   126.4462244),
new daum.maps.LatLng(37.7689714,  126.4443793),
new daum.maps.LatLng(37.7711786,  126.4412596),
new daum.maps.LatLng(37.773855,   126.4422207),
new daum.maps.LatLng(37.7758211,  126.4443728),
new daum.maps.LatLng(37.7809005,  126.4439372),
new daum.maps.LatLng(37.781311,   126.4414785),
new daum.maps.LatLng(37.7854208,  126.4414653),
new daum.maps.LatLng(37.7857558,  126.4426416),
new daum.maps.LatLng(37.7894113,  126.4402272),
new daum.maps.LatLng(37.7899734,  126.4385838),
new daum.maps.LatLng(37.7908696,  126.4340281),
new daum.maps.LatLng(37.7920249,  126.4302408),
new daum.maps.LatLng(37.7943033,  126.4310257),
new daum.maps.LatLng(37.7973075,  126.4323454),
new daum.maps.LatLng(37.8014767,  126.438919),
new daum.maps.LatLng(37.8051489,  126.4423651),
new daum.maps.LatLng(37.8086294,  126.444542),
new daum.maps.LatLng(37.8081791,  126.4465932),
new daum.maps.LatLng(37.8118239,  126.4510401),
new daum.maps.LatLng(37.8106845,  126.4526467),
new daum.maps.LatLng(37.807483,   126.4575084),
new daum.maps.LatLng(37.8042861,  126.462915),
new daum.maps.LatLng(37.8019533,  126.4682181),
new daum.maps.LatLng(37.7951844,  126.4774895),
new daum.maps.LatLng(37.79121,     126.4848129),
new daum.maps.LatLng(37.7884428,  126.4900295),
new daum.maps.LatLng(37.7859164,  126.4981497),
new daum.maps.LatLng(37.7823103,  126.5065564),
new daum.maps.LatLng(37.7793698,  126.5084126),
new daum.maps.LatLng(37.7762735,  126.5089082),
new daum.maps.LatLng(37.7713226,  126.5117901),
new daum.maps.LatLng(37.7702526,  126.5131669),
new daum.maps.LatLng(37.7669877,  126.5107579),
new daum.maps.LatLng(37.7639612,  126.5109798),
new daum.maps.LatLng(37.7609492,  126.5129272),
new daum.maps.LatLng(37.7588721,  126.5145895),
new daum.maps.LatLng(37.7555823,  126.5178117),
new daum.maps.LatLng(37.7509278,  126.5216872),
new daum.maps.LatLng(37.7485682,  126.5240793),
new daum.maps.LatLng(37.747507,   126.5265449),
new daum.maps.LatLng(37.7443273,  126.5256786),
new daum.maps.LatLng(37.7420818,  126.5244368),
new daum.maps.LatLng(37.7392761,  126.5252),	
new daum.maps.LatLng(37.7373084,  126.5226836),
new daum.maps.LatLng(37.7251291,  126.5147645),
new daum.maps.LatLng(37.7195686,  126.5137489),
new daum.maps.LatLng(37.7170391,  126.5130563),
new daum.maps.LatLng(37.7103592,  126.5161399),
new daum.maps.LatLng(37.7064287,  126.5203666),
new daum.maps.LatLng(37.7024379,  126.5260457),
new daum.maps.LatLng(37.7004838,  126.5251638),
new daum.maps.LatLng(37.6970735,  126.5225769),
new daum.maps.LatLng(37.6938201,  126.5215307)         
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



var iwContent = '<div style="padding:5px;">강화 성당</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
    iwPosition = new daum.maps.LatLng(37.750842, 126.485060), //인포윈도우 표시 위치입니다
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
