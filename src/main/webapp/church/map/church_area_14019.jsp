<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="map" style="width:1200px;height:560px;"></div>
<p id="result"></p>
<script type="text/javascript" src="js/apis.daum.net_maps_maps3.js?apikey=253a049954b808428c02e7c8ce8663d9"></script>
<script>
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new daum.maps.LatLng(37.483118, 126.708971), // 지도의 중심좌표
        level: 5 // 지도의 확대 레벨  
    };

var map = new daum.maps.Map(mapContainer, mapOption); // 지도를 생성합니다


// 다각형을 구성하는 좌표 배열입니다. 이 좌표들을 이어서 다각형을 표시합니다 
var polygonPath = [        
        new daum.maps.LatLng(37.4830914, 126.7146881),
new daum.maps.LatLng(37.4825541, 126.7152027),
new daum.maps.LatLng(37.4820793, 126.7156487),
new daum.maps.LatLng(37.4817325, 126.7163536),
new daum.maps.LatLng(37.4817274, 126.7169531),
new daum.maps.LatLng(37.4819913, 126.7173236),
new daum.maps.LatLng(37.4820832, 126.7175941),
new daum.maps.LatLng(37.4820087, 126.7185902),
new daum.maps.LatLng(37.4817471, 126.7185703),
new daum.maps.LatLng(37.4815827, 126.7182327),
new daum.maps.LatLng(37.4811939, 126.7180558),
new daum.maps.LatLng(37.4808772, 126.7178668),
new daum.maps.LatLng(37.4804988, 126.7178934),
new daum.maps.LatLng(37.4803332, 126.7173861),
new daum.maps.LatLng(37.4807003, 126.7170204),
new daum.maps.LatLng(37.4805171, 126.7165812),
new daum.maps.LatLng(37.4808583, 126.716374),
new daum.maps.LatLng(37.4812831, 126.7151933),
new daum.maps.LatLng(37.4813772, 126.7144345),
new daum.maps.LatLng(37.4817508, 126.7136955),
new daum.maps.LatLng(37.4818826, 126.7131851),
new daum.maps.LatLng(37.4819613, 126.712811),
new daum.maps.LatLng(37.4818506, 126.7124389),
new daum.maps.LatLng(37.4816684, 126.7121468),
new daum.maps.LatLng(37.4812973, 126.7119132),
new daum.maps.LatLng(37.479643,  126.7111841),
new daum.maps.LatLng(37.4771802, 126.7122278),
new daum.maps.LatLng(37.4770781, 126.7131337),
new daum.maps.LatLng(37.4768765, 126.713984),
new daum.maps.LatLng(37.4765807, 126.7142359),
new daum.maps.LatLng(37.4763228, 126.7147589),
new daum.maps.LatLng(37.4762082, 126.7151446),
new daum.maps.LatLng(37.4762832, 126.7155849),
new daum.maps.LatLng(37.4761252, 126.7162199),
new daum.maps.LatLng(37.4759131, 126.7168668),
new daum.maps.LatLng(37.4755304, 126.7175946),
new daum.maps.LatLng(37.4750194, 126.7180183),
new daum.maps.LatLng(37.4740224, 126.7185489),
new daum.maps.LatLng(37.473621,  126.7191864),
new daum.maps.LatLng(37.4733995, 126.7197655),
new daum.maps.LatLng(37.4728334, 126.7200202),
new daum.maps.LatLng(37.4719927, 126.7210241),
new daum.maps.LatLng(37.4713807, 126.7211662),
new daum.maps.LatLng(37.4708948, 126.7212843),
new daum.maps.LatLng(37.4703186, 126.7213921),
new daum.maps.LatLng(37.469359,  126.7221145),
new daum.maps.LatLng(37.4691722, 126.7225009),
new daum.maps.LatLng(37.4686673, 126.7224835),
new daum.maps.LatLng(37.468072,  126.7224218),
new daum.maps.LatLng(37.4679415, 126.721756),
new daum.maps.LatLng(37.4678384, 126.7211577),
new daum.maps.LatLng(37.4681132, 126.720465),
new daum.maps.LatLng(37.4683682, 126.7194899),
new daum.maps.LatLng(37.4684284, 126.7190595),
new daum.maps.LatLng(37.4689824, 126.7183187),
new daum.maps.LatLng(37.4703739, 126.7174787),
new daum.maps.LatLng(37.4719856, 126.7172244),
new daum.maps.LatLng(37.4734816, 126.7171862),
new daum.maps.LatLng(37.4744157, 126.7166788),
new daum.maps.LatLng(37.474986,  126.7156889),
new daum.maps.LatLng(37.4752073, 126.7150872),
new daum.maps.LatLng(37.4755459, 126.7144956),
new daum.maps.LatLng(37.4761664, 126.7129284),
new daum.maps.LatLng(37.476412,  126.7119079),
new daum.maps.LatLng(37.4764882, 126.711172),
new daum.maps.LatLng(37.4762495, 126.7105299),
new daum.maps.LatLng(37.4758342, 126.7104211),
new daum.maps.LatLng(37.4755287, 126.7105601),
new daum.maps.LatLng(37.4745663, 126.7108755),
new daum.maps.LatLng(37.4736839, 126.7110092),
new daum.maps.LatLng(37.4737706, 126.7091536),
new daum.maps.LatLng(37.4738946, 126.7088243),
new daum.maps.LatLng(37.4736676, 126.7085779),
new daum.maps.LatLng(37.474282,  126.7074518),
new daum.maps.LatLng(37.4740344, 126.7068211),
new daum.maps.LatLng(37.4741668, 126.7064012),
new daum.maps.LatLng(37.4745054, 126.7058209),
new daum.maps.LatLng(37.4750286, 126.7058832),
new daum.maps.LatLng(37.475201,  126.7047165),
new daum.maps.LatLng(37.4752591, 126.7039638),
new daum.maps.LatLng(37.475384,  126.7037702),
new daum.maps.LatLng(37.4754044, 126.7034533),
new daum.maps.LatLng(37.4776718, 126.7035311),
new daum.maps.LatLng(37.4795148, 126.7035058),
new daum.maps.LatLng(37.4795132, 126.7032684),
new daum.maps.LatLng(37.479404,  126.7030999),
new daum.maps.LatLng(37.4789467, 126.702788),
new daum.maps.LatLng(37.478674,  126.7024573),
new daum.maps.LatLng(37.478564,  126.7021757),
new daum.maps.LatLng(37.4787913, 126.7018001),
new daum.maps.LatLng(37.4786698, 126.7011567),
new daum.maps.LatLng(37.479563,  126.7006326),
new daum.maps.LatLng(37.4792852, 126.6995384),
new daum.maps.LatLng(37.4800793, 126.6990041),
new daum.maps.LatLng(37.4803996, 126.6990459),
new daum.maps.LatLng(37.4809347, 126.6988762),
new daum.maps.LatLng(37.4821825, 126.70078),
new daum.maps.LatLng(37.485583,  126.7064954),
new daum.maps.LatLng(37.4860524, 126.7072766),
new daum.maps.LatLng(37.4866317, 126.7076267),
new daum.maps.LatLng(37.4870989, 126.7074125),
new daum.maps.LatLng(37.4872543, 126.7043738),
new daum.maps.LatLng(37.4887994, 126.7036052),
new daum.maps.LatLng(37.4891757, 126.7046136),
new daum.maps.LatLng(37.4889804, 126.7050512),
new daum.maps.LatLng(37.4892742, 126.7058455),
new daum.maps.LatLng(37.4895775, 126.706719),
new daum.maps.LatLng(37.4903805, 126.7088541),
new daum.maps.LatLng(37.4902906, 126.7095733),
new daum.maps.LatLng(37.4898883, 126.7100696),
new daum.maps.LatLng(37.4901485, 126.7105646),
new daum.maps.LatLng(37.490541,  126.7112901),
new daum.maps.LatLng(37.49081,   126.7117567),
new daum.maps.LatLng(37.4909721, 126.7130841),
new daum.maps.LatLng(37.4908138,126.7130122),
new daum.maps.LatLng(37.4909028,126.713509),
new daum.maps.LatLng(37.4906404, 126.7133534),
new daum.maps.LatLng(37.490103, 126.7132007),
new daum.maps.LatLng(37.4896301, 126.7132396),
new daum.maps.LatLng(37.4893996, 126.7131458),
new daum.maps.LatLng(37.488919, 126.7127041),
new daum.maps.LatLng(37.4884875, 126.7121996),
new daum.maps.LatLng(37.4875387, 126.7111746),
new daum.maps.LatLng(37.4871259, 126.7107661),
new daum.maps.LatLng(37.4868545, 126.7106219),
new daum.maps.LatLng(37.4865569, 126.7106024),
new daum.maps.LatLng(37.4863142, 126.7107011),
new daum.maps.LatLng(37.4860274, 126.7109473),
new daum.maps.LatLng(37.4859067, 126.7110843),
new daum.maps.LatLng(37.4867296, 126.7128402),
new daum.maps.LatLng(37.4865466, 126.7131079),
new daum.maps.LatLng(37.4856775, 126.7125345),
new daum.maps.LatLng(37.4852818, 126.7126631),
new daum.maps.LatLng(37.4844952, 126.7123094),
new daum.maps.LatLng(37.4837322, 126.7120968),
new daum.maps.LatLng(37.4833173,126.712056),
new daum.maps.LatLng(37.4829711, 126.7121783),
new daum.maps.LatLng(37.4828006, 126.7129719),
new daum.maps.LatLng(37.4826601, 126.7135332),
new daum.maps.LatLng(37.4828718, 126.714187),
new daum.maps.LatLng(37.4830908, 126.7145919)
                
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

var iwContent = '<div style="padding:5px;">부평3동 성당</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
    iwPosition = new daum.maps.LatLng(37.483118, 126.708971), //인포윈도우 표시 위치입니다
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
