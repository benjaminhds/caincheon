<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<body>
    <p class="skipNavi" tabindex="1">본문바로가기</p>
    <!-- wrap -->
    <div id="wrap">
        <!-- header -->
        <header>
            <%@ include file="/_common/inc/gnb.jsp" %>
        </header>
        <!-- //header -->
        <!-- subVisual -->
        <section class="subVisual intro">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap">
                <article>
                    <h3>교구소개</h3>
                    <ul>
                        <li>홈</li>
                        <li>교구안내</li>
                        <li>교구소개</li>
                    </ul>
                </article>
            </div>
        </section>
        <!-- //subVisual -->
        <!-- secWrap -->
        <div class="secWrap">
            <!-- sec01 -->
            <section class="sec01 fin">
                <!-- secCont -->
                <div class="secCont">
                    <!-- tabs -->
                    <ul class="tabs">
                        <li><a href="/intro/intro.jsp">소개</a></li>
                        <li><a href="/intro/intro_02.jsp">현황</a></li>
                        <li class="on"><a href="/intro/intro_03.do">관할구역</a></li>
                        <li><a href="/intro/intro_04.jsp">심볼/주보성인</a></li>
                    </ul>
                    <!-- //tabs -->
                    <h3 class="ttl ">교구 관할 지도</h3>
                    <div>
                    <form id="form01" action="/intro/intro_03.do">
                    <span><input type="radio" name="code_inst" value="" onClick="javascript: this.form.submit();"><label for="">교구전체</label></span>
                    <c:forEach items="${LIST_TOTAL}" var="row" varStatus="status">
                      <c:if test="${row.NAME ne '특수'}">
                    	 <span><input type="radio" name="code_inst" value="${row.CODE_INST }" <c:if test="${row.CODE_INST eq _params.code_inst }">checked</c:if> onClick="javascript: this.form.submit();"><label for="">${row.NAME}</label></span>
                      </c:if>
                    </c:forEach>
                    </form>
                    </div>
                    <!-- map -->
                    <article class="map">
                        <h4 class="blind">지도</h4>
                        <div class="mapCont" id="map">
                        </div>
                        <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=689d242c539bbadca087c0e8f3b47973"></script>
                        <!--<script type="text/javascript" src="//apis.daum.net/maps/maps3.js?apikey=282cb9ea85fe24ca02ecfff9b7fe81b7"></script>-->
                        <script src="/_common/js/maps3.js"></script>
                        <script>
                            // 지도에 폴리곤으로 표시할 영역데이터 배열입니다 
                            var areas = [
                            	<c:set var="idx" value="0" />
                            	<c:forEach items="${LIST_SEARCH}" var="row" varStatus="status">
                            		<c:if test="${fn:length(row.MEMO) > 7 && row.NAME ne '특수'}">
	                            		<c:if test="${idx > 0}"> , </c:if>
	                            		${row.MEMO}
	                            		<c:set var="idx" value="${idx + 1}" />
                            		</c:if>
                            	</c:forEach>
                            ];
                            
                            //
                            <c:set var="zoon_level" value="6" />
                            <c:if test="${_params.code_inst eq ''}">
                            	<c:set var="zoon_level" value="9" />
                            </c:if>
                            var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
                                mapOption = { 
                                    center: new daum.maps.LatLng(${GPS_CENTER}),// 지도의 중심좌표
                                    level: ${zoon_level} // 지도의 확대 레벨
                                };

                            var map = new daum.maps.Map(mapContainer, mapOption),
                                customOverlay = new daum.maps.CustomOverlay({}),
                                infowindow = new daum.maps.InfoWindow({removable: true});

                            // 지도에 영역데이터를 폴리곤으로 표시합니다 
                            for (var i = 0, len = areas.length; i < len; i++) {
                                displayArea(areas[i]);
                            }

                            //본당위치
                           	<c:forEach items="${LIST_SEARCH}" var="row" varStatus="status">
                           		<c:if test="${fn:length(row.MEMO2) > 4 && row.NAME ne '특수'}">
                           	        // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
                           		    var iwContent = '<div class="info"><span>${row.NAME}</span><img class="pin" src="/img/sub/_ico/ico_marker.png" alt="" /></div>', 
                                        iwPosition = new daum.maps.LatLng(${row.MEMO2}), //인포윈도우 표시 위치입니다
                                        iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다
                                    var infowindow = new daum.maps.InfoWindow({
                                        map: map, // 인포윈도우가 표시될 지도
                                        position : iwPosition, 
                                        content : iwContent,
                                        removable : iwRemoveable
                                    });
                           		</c:if>
                           	</c:forEach>
                           	/*
                            var iwContent = '<div class="info"><span>서운동성당</span><img class="pin" src="/img/sub/_ico/ico_marker.png" alt="" /></div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
                                iwPosition = new daum.maps.LatLng(37.536181, 126.744844), //인포윈도우 표시 위치입니다
                                iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다
                            var infowindow = new daum.maps.InfoWindow({
                                map: map, // 인포윈도우가 표시될 지도
                                position : iwPosition, 
                                content : iwContent,
                                removable : iwRemoveable
                            });
                            */
                            
                            // 다각형을 생상하고 이벤트를 등록하는 함수입니다
                            function displayArea(area) {

                                // 다각형을 생성합니다 
                                var polygon = new daum.maps.Polygon({
                                    map: map, // 다각형을 표시할 지도 객체
                                    path: area.path,
                                    strokeWeight: 2,
                                    strokeColor: '#800000',
                                    strokeOpacity: 0.7,
                                    fillColor: '#fff',
                                    fillOpacity: 0.3 
                                });

                                // 다각형에 mouseover 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 변경합니다 
                                // 지역명을 표시하는 커스텀오버레이를 지도위에 표시합니다
                                daum.maps.event.addListener(polygon, 'mouseover', function(mouseEvent) {
                                    polygon.setOptions({fillColor: '#09f'});

                                    customOverlay.setContent('<div class="area">' + /*area.name + */'</div>');
                                    
                                    customOverlay.setPosition(mouseEvent.latLng); 
                                    customOverlay.setMap(map);
                                });

                                // 다각형에 mousemove 이벤트를 등록하고 이벤트가 발생하면 커스텀 오버레이의 위치를 변경합니다 
                                daum.maps.event.addListener(polygon, 'mousemove', function(mouseEvent) {
                                    
                                    customOverlay.setPosition(mouseEvent.latLng); 
                                });

                                // 다각형에 mouseout 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 원래색으로 변경합니다
                                // 커스텀 오버레이를 지도에서 제거합니다 
                                daum.maps.event.addListener(polygon, 'mouseout', function() {
                                    polygon.setOptions({fillColor: '#fff'});
                                    customOverlay.setMap(null);
                                }); 

                                // 다각형에 click 이벤트를 등록하고 이벤트가 발생하면 다각형의 이름과 면적을 인포윈도우에 표시합니다 
                                daum.maps.event.addListener(polygon, 'click', function(mouseEvent) {
                                    var content = '<div class="infos">' + 
                                                '   <div class="title">' + area.name + '</div>' +
                                                '   <div class="size">총 면적 : 약 ' + Math.floor(polygon.getArea()) + ' m<sup>2</sup></area>' +
                                                '</div>';

                                    infowindow.setContent(content); 
                                    infowindow.setPosition(mouseEvent.latLng); 
                                    infowindow.setMap(map);
                                });       
                                                           
                            }
                        </script>          
                    </article>
                    <!-- //map -->
                    <h3 class="ttl tb">교구 관할 지역 정보</h3>
                    <p class="txt v3"><b>관할 지역 : </b> 인천광역시, 부천시, 김포시 전 지역, 시흥시(신천동, 대야동, 미산동, 은행동, 계수동, 무지동, 과림동, 도창동, 금이동, 매화동, 포동, 방산동) 일부, 안산시(대부동) 일부 <span class="indent"><i>*</i> 인천광역시 2,983,484  / 부천시 869,165 / 김포시 366,773 / 시흥시(일부) 134,237 / 안산시(일부) 7,951</span></p>
                    <!-- shirine_st v2 -->
                    <div class="oflow">
                    <table class="shirine_st v2 v5">
                        <caption>교구관할지역정보</caption>
                        <colgroup>
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                        </colgroup>
                        <thead>
                            <tr>
                                <th scope="col" rowspan="2">면적(km<sup>2</sup>)</th>
                                <th scope="col" rowspan="2">인구(명)</th>
                                <th scope="col" rowspan="2">신자(명)</th>
                                <th scope="col" rowspan="2">신자비율(%)</th>
                                <th scope="col" colspan="3">교구성직자</th>
                                <th scope="col" rowspan="2">사제 1인당<br>
                                신자수(명)</th>
                                <th scope="col" rowspan="2">본당</th>
                                <th scope="col" rowspan="2">공소</th>
                            </tr>
                            <tr>
                                <th scope="col">주교(은퇴)</th>
                                <th scope="col">몬시뇰(은퇴)</th>
                                <th scope="col">신부(은퇴)</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>1,469.42</td>
                                <td>4,361,610</td>
                                <td>469,364</td>
                                <td>11.380%</td>
                                <td>3(1)</td>
                                <td>4(1)</td>
                                <td>302(15)</td>
                                <td>1,666</td>
                                <td>122</td>
                                <td>36</td>
                            </tr>              
                        </tbody>
                    </table>
                    </div>
                    <!-- //shirine_st v2 -->
                </div>
                <!-- //secCont -->
            </section>
            <!-- //sec01 -->
        </div>
        <!-- //secWrap -->
        <!-- footer -->
        <footer>
            <%@ include file="/_common/inc/footer.jsp" %>
        </footer>
        <!-- //footer -->
    </div>
    <!--// wrap -->
</body>

</html>
