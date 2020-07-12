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
                        <li><a href="/community/cana.do">카나혼인강좌 안내</a></li>
                        <li class="on"><a href="/community/cana_manual.do">약혼자주말 안내</a></li>
                        <li><a href="/community/cana_register.do?">신청하기</a></li>
                    </ul>
                    <!-- //tabs -->
                    <!-- imgOnTxt -->
                    <div class="imgOnTxt v2">                        
                        <p class="txt">
                            <em class="ttl">약혼자주말이란?</em>
                            <span>가톨릭 약혼자 주말은 혼인을 앞둔 예비부부 혹은 혼인한지 1년 미만의 신혼 부부가 건강하고 행복한 혼인 생활을 해나갈 수 있도록 돕는 프로그램입니다. 
                            참가자들은 대화를 통해 혼인의 진정한 의미를 깨닫고 부부 관계 안에서의 성장을 직접 체험하게 됩니다. 이러한 시간을 통해 혼인을 준비하고, 신혼생활을 해나가는 과정에서 서로가 주고 받았던 상처를 치유하여 더 큰 사랑을 이루어 갈 수 있도록 도움을 줍니다.

                            </span>
                        </p>
                        <span><img src="../img/sub/cana_manual.png" alt=""></span>
                    </div>
                    <!-- //imgOnTxt -->
                    <h3 class="ttl tb">교육안내</h3>
                    <!-- canaEdu -->
                    <div class="canaEdu">
                        <ul>
                            <li>
                                <em>대상</em>
                                <span>혼인을 앞둔 부부와 1년 미만의 신혼부부</span>
                            </li>
                            <li>
                                <em>일시</em>
                                <span>교육 일정 참조</span>
                            </li>
                            <li>
                                <em>장소</em>
                                <span>심조이 바르바라 피정의 집(인천교구청 내) <br>
                                (주소: 인천 동구 박문로 1 (구)박문여중고교)</span>
                            </li>   
                        </ul>
                        <ul>
                            <li>
                                <em>신청마감</em>
                                <span>해당 주말 3일 전 화요일까지 <br>신청 및 참가비 입금 완료</span>
                            </li> 
                            <li>
                                <em>신청방법</em>
                                <span>인천교구 홈페이지 내 온라인 신청</span>
                            </li>
                            <li>
                                <em>신청비</em>
                                <span>한 커플당 30만원 </span>
                            </li> 
                        </ul> 
                        <ul>                            
                            <li>
                                <em>입금계좌</em>
                                <span>신협  131-011-583641 / 예금주 : 가정사목국</span>
                            </li>
                            <li>
                                <em>입금방법</em>
                                <span>참가자 커플 중 한 분 실명+해당월 <br>예) 홍길동3월 </span>
                            </li> 
                            <li>
                                <em>참가인원</em>
                                <span>선착순 15쌍 </span>
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
                    <ul class="dotTx v2">
                        <li>주말 참석 전에 꼭 온라인으로 접수하신 후 참가비를 입금해주시기 바랍니다.</li>
                        <li>문의 : 가정복음화부 032-762-8888(근무 : 월 ~ 금, 오전 9시 ~ 오후 6시 / 점심시간 : 오후 12시 ~ 1시</li>
                        <li class="blue">현장 접수는 받지 않으며, 신청완료 후 취소, 연기하실 경우에는 사무실로 연락하셔야 합니다. 참가비는 환불되지 않습니다.</li>
                    </ul>
                    
                    <!-- //imgOnTxt -->
                    <h3 class="ttl tb">교육일정</h3>
                    <!-- canaEdu -->
                    <div class="canaEdu">
                    	
                        <ul>
                        	<c:if test="${fn:length(engage_guide.FDATE1) > 0 }">
                            <li>
                                <em>+</em>
                                <span>${engage_guide.FDATE1 } ~ ${engage_guide.TDATE1 }</span>
                            </li>
                            </c:if>
                            <c:if test="${fn:length(engage_guide.FDATE2) > 0 }">
                            <li>
                                <em>+</em>
                                <span>${engage_guide.FDATE2 } ~ ${engage_guide.TDATE2 }</span>
                            </li>
                            </c:if>
                            <c:if test="${fn:length(engage_guide.FDATE3) > 0 }">
                            <li>
                                <em>+</em>
                                <span>${engage_guide.FDATE3 } ~ ${engage_guide.TDATE3 }</span>
                            </li>
                            </c:if>   
                        </ul>
                        
                        <ul>
                        	<c:if test="${fn:length(engage_guide.FDATE4) > 0 }">
                            <li>
                                <em>+</em>
                                <span>${engage_guide.FDATE4 } ~ ${engage_guide.TDATE4 }</span>
                            </li>
                            </c:if>
                            <c:if test="${fn:length(engage_guide.FDATE5) > 0 }">
                            <li>
                                <em>+</em>
                                <span>${engage_guide.FDATE5 } ~ ${engage_guide.TDATE5 }</span>
                            </li>
                            </c:if>
                            <c:if test="${fn:length(engage_guide.FDATE6) > 0 }">
                            <li>
                                <em>+</em>
                                <span>${engage_guide.FDATE6 } ~ ${engage_guide.TDATE6 }</span>
                            </li>
                            </c:if>   
                        </ul>
                    </div>
                    <!-- //canaEdu -->
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
                            <p class="blueTxt">· 심조이 바르바라 피정의 집(인천교구청 내) / 인천시 동구 박문로1 (송림동)</p>
                            <ul>
                                <li>
                                    <i><img src="/img/sub/_ico/ico_subway.png" alt=""></i>
                                    <span>
                                        <em>전철 이용 시</em>
                                        <span>경인1호선 제물포역 하차</span>
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