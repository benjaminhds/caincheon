<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<body>
    <p class="skipNavi" tabindex="1">본문바로가기</p>
    <!-- wrap -->
    <div id="wrap">
        <!-- header -->
        <header>
            <%@include file="/_common/inc/gnb.jsp" %>
        </header>
        <!-- //header -->
        <!-- subVisual -->
        <section class="subVisual commu">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap">
                <article>
                    <h3>카나혼인강좌&amp;약혼자주말 신청</h3>
                    <ul>
                        <li>홈</li>
                        <li>참여마당</li>
                        <li>카나혼인강좌&amp;약혼자주말 신청</li>
                    </ul>
                </article>
            </div>
        </section>
        <!-- //subVisual -->
        <!-- secWrap -->
        <div class="secWrap">
            <!-- sec01 -->
            <section class="sec01">
                <!-- secCont -->
                <div class="secCont">
                    <!-- tabs -->
                    <ul class="tabs">
                        <li class="on"><a href="/community/cana.do">카나혼인강좌 안내</a></li>
                        <li><a href="/community/cana_manual.do">약혼자주말 안내</a></li>
                        <li><a href="/community/cana_register.do?">신청하기</a></li>
                    </ul>
                    <!-- //tabs -->
                    <!-- imgOnTxt -->
                    <div class="imgOnTxt">
                        <p class="txt">
                            <em class="ttl">카나혼인강좌란?</em>
                            <span>카나 혼인 강좌는 혼인을 앞둔 이들에게 혼인의 의미 및 이에 관한 교회의 가르침을 전하고, 인간의 생명과 가정의 소중함, 행복한 부부 생활을 교육하여 기초가 튼튼한 가정, 행복이 넘치는 성가정을 이끌어 갈 수 있도록 돕는 교육입니다.
                            </span>
                        </p>
                        <span><img src="../img/sub/cana.png" alt=""></span>
                    </div>
                    <!-- //imgOnTxt -->
                    <h3 class="ttl tb">교육안내</h3>
                    <!-- canaEdu -->
                    <div class="canaEdu">
                        <ul>
                            <li>
                                <em>대상</em>
                                <span>혼인을 앞둔 부부 및 혼인 장애 부부</span>
                            </li>
                            <li>
                                <em>일시</em>
                                <span>매월 둘째 주 / 2018년 일정 참조</span>
                            </li>
                            <li>
                                <em>장소</em>
                                <span>인천교구청 대 강의실 복자 이 안나홀 <br>
                                (주소: 인천 동구 박문로 1 (송림동), (구)박문여중고교)</span>
                            </li>   
                        </ul>
                        <ul>
                            <li>
                                <em>신청마감</em>
                                <span>본 강좌 3일전 목요일까지 <br>
                                신청 및 참가비 입금 완료</span>
                            </li> 
                            <li>
                                <em>신청방법</em>
                                <span>인천교구 홈페이지 내 온라인 신청</span>
                            </li>
                            <li>
                                <em>참가비</em>
                                <span>한 커플당 4만원 </span>
                            </li> 
                        </ul> 
                        <ul>                            
                            <li>
                                <em>입금계좌</em>
                                <span>신협  131-011-583641 / 예금주: 가정사목국</span>
                            </li>
                            <li>
                                <em>입금방법</em>
                                <span>참가자 커플 중 한 분 실명+수강월 <br>예) 홍길동5월</span>
                            </li> 
                            <li>
                                <em>참가인원</em>
                                <span>선착순 100쌍</span>
                            </li> 
                        </ul>
                        <ul>
                            <li>
                                <em>장소</em>
                                <span>하단 지도 참조</span>
                            </li>                            
                        </ul>
                    </div>
                    <!-- //canaEdu -->
                    <ul class="dotTx">
                        <li>강좌 참석 전에 꼭 온라인으로 접수하신 후 참가비를 입금해주시기 바랍니다.</li>
                        <li>강좌 일정에 따라 시간을 잘 지켜서 교육에 임해주시길 바랍니다.</li>
                        <li>파견미사 전 고해성사를 받으실 수 있으며, 수료증은 파견미사 후에 수여됩니다.</li>
                        <li class="blue">현장 접수는 받지 않으며, 신청완료 후 취소, 연기하실 경우에는 사무실로 연락하셔야 합니다. 참가비는 환불되지 않습니다.</li>
                        <li>문의: 가정복음화부 032-762-8888 (근무:월~금 / 09:00~18:00 // 점심시간: 12:00~13:00)</li>                        
                    </ul>
                    <h3 class="ttl tb">카나강좌 일정안내</h3>
                    <div class="daily">
                        <!-- shirine_st v2 -->
                        <table class="shirine_st v2">
                            <caption>2018년 카나강좌 일정</caption>
                            <thead>
                                <tr>
                                    <th scope="colgroup" colspan="2">2018 강좌 일정</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<tr>
                            		<!-- 반반 출력하기 위해서 연산하기 -->
                            		<c:set var="XHalf0" value="${fn:length(marry_date) / 2}" />
                                   	<fmt:parseNumber var="XHalf" type="number" integerOnly="true" value="${XHalf0}"/>
                                   	<c:set var="XEup" value="${fn:length(marry_date) % 2 > 0 ? 1 : 0 }" />
                                   	<c:set var="XSdn" value="${fn:length(marry_date) % 2 > 0 ? 0 : 1 }" />
                                    <!-- 왼쪽에 반 출력 -->
                                    <td>
	                                    <table border=0 width="100%">
											<c:if test="${fn:length(marry_date) > 0}">
											<c:forEach items="${marry_date}" var="list" varStatus="status" begin="0" end="${XHalf - XSdn}" step="1">
				                            	<tr><td>${list.LECTURE_DATE} ${list.YOIL}</td></tr>
											</c:forEach>
											</c:if>
	                                    </table>
                                    </td>
                                    <!-- 오른쪽에 나머지 반 출력 -->
                                    <td>
                                    	<table border=0 width="100%">
											<c:if test="${fn:length(marry_date) > 0}">
											<c:forEach items="${marry_date}" var="list" varStatus="status" begin="${XHalf + XEup}" step="1">
				                            	<tr><td>${list.LECTURE_DATE} ${list.YOIL}</td></tr>
											</c:forEach>
											</c:if>
											<c:if test="${XEup > 0}">
												<tr><td><!-- 빈칸출력 --></td></tr>
											</c:if>
	                                    </table>
                                    </td>
                                </tr>
								<!-- 
                                <tr>
                                    <td>1월08일(주일)</td>
                                    <td>7월02일(주일)</td>
                                </tr>
                                <tr>
                                    <td>2월05일(주일)</td>
                                    <td>8월20일(주일)</td>
                                </tr>
                                <tr>
                                    <td>3월05일(주일)</td>
                                    <td>9월03일(주일)</td>
                                </tr>
                                <tr>
                                    <td>4월02일(주일)</td>
                                    <td>10월15일(주일)</td>
                                </tr>
                                <tr>
                                    <td>5월07일(주일)</td>
                                    <td>11월05일(주일)</td>
                                </tr>
                                <tr>
                                    <td>6월11일(주일)</td>
                                    <td>12월03일(주일)</td>
                                </tr>
                                 -->
                            </tbody>
                        </table>
                        <!-- //shirine_st v2 -->
                    </div>
                    <h3 class="ttl tb">강의시간표</h3>
                    <div class="daily">
                        <!-- shirine_st v2 -->
                        <table class="shirine_st v2">
                            <caption>강의시간표</caption>
                            <thead>
                                <tr>
                                    <th scope="col">시간</th>
                                    <th scope="col">내용</th>
                                </tr>
                            </thead>
                            <tbody>
								<c:if test="${fn:length(marry_time) > 0}">
								<c:forEach items="${marry_time}" var="list" varStatus="status">
									<tr>
	                                    <td>${list.LECTURE_TIME }</td>
	                                    <td>${list.CONTENTS }</td>
	                                </tr>
								</c:forEach>
								</c:if>
								<!-- 
                                <tr>
                                    <td>오후 1:30 - 2:00</td>
                                    <td>오리엔테이션</td>
                                </tr>
                                <tr>
                                    <td>오후 2:00 - 2:40</td>
                                    <td>첫 번째 시간 : 만남</td>
                                </tr>
                                <tr>
                                    <td>오후 2:40 - 2:50</td>
                                    <td>휴식</td>
                                </tr>
                                <tr>
                                    <td>오후 2:50 -3:30</td>
                                    <td>두 번째 시간 : 혼인설계</td>
                                </tr>
                                <tr>
                                    <td>오후 3:30 - 3:45</td>
                                    <td>휴식 &amp; 간식</td>
                                </tr>
                                <tr>
                                    <td>오후 3:45 - 4:25</td>
                                    <td>세 번째 시간: 혼인성사</td>
                                </tr>
                                <tr>
                                    <td>오후 4:25 – 4:40</td>
                                    <td>휴식 </td>
                                </tr>
                                <tr>
                                    <td>오후 4:40 - 5:20</td>
                                    <td>네 번째 시간: 생명</td>
                                </tr>
                                <tr>
                                    <td>오후 5:20 – 5:40 </td>
                                    <td>고해성사 및 미사 준비</td>
                                </tr>
                                <tr>
                                    <td>오후 5:40 – 6:30</td>
                                    <td>파견미사</td>
                                </tr>
                                 -->
                            </tbody>
                        </table>
                        <!-- //shirine_st v2 -->
                    </div>
                </div>
                <!-- //secCont -->
                <!-- secWide -->
                <div class="blue secWide v2">
                    <div class="secCont">
                        <h3 class="ttl">오시는 길</h3>
                    </div>
                    <!-- secCont -->
                    <div class="secCont">
                        <!-- map -->
                        <article class="map">
                            <h4 class="blind">지도</h4>
                            <div class="maps" style="display:block; width:100%; height:400px;">
                                <!-- * Daum 지도 - 지도퍼가기 -->
                                <!-- 1. 지도 노드 -->
                                <div id="daumRoughmapContainer1511331094636" class="root_daum_roughmap root_daum_roughmap_landing"></div>                                
                                <!-- 2. 설치 스크립트
                                     * 지도 퍼가기 서비스를 2개 이상 넣을 경우, 설치 스크립트는 하나만 삽입합니다.                                
                                -->                                
                                <script charset="UTF-8" class="daum_roughmap_loader_script" src="http://dmaps.daum.net/map_js_init/roughmapLoader.js"></script>
                                <!-- 3. 실행 스크립트 -->
                                <script charset="UTF-8">
                                     new daum.roughmap.Lander({
                                     "timestamp": "1511331094636",
                                     "key": "kkgc",
                                     "mapWidth": "1024"
                                     }).render();
                                </script>
                            </div>
                            <p class="blueTxt">· 인천교구청 대 강의실 복자 이 안나홀 / 인천시 동구 박문로1 (송림동)</p>
                            <ul>
                                <li>
                                    <i><img src="/img/sub/_ico/ico_subway.png" alt=""></i>
                                    <span>
                                        <em>전철 이용 시</em>
                                        <span>경인 1호선 제물포역 하차</span>
                                    </span>
                                </li>
                                <li>
                                    <i><img src="/img/sub/_ico/ico_bus.png" alt=""></i>
                                    <span>
                                        <em>버스 이용 시</em>
                                        <span>(간선) 10, 13, 2, 29, 46, 6, 62(지선), 510 번 이용</span>
                                    </span>
                                </li>
                            </ul>                         
                        </article>
                        <!-- //map -->
                    </div>
                    <!-- //secCont -->
                </div>
                <!-- //secWide -->
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