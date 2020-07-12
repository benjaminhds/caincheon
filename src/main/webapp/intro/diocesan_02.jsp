<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
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
        <section class="subVisual intro">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap">
                <article>
                    <h3>교구청</h3>
                    <ul>
                        <li>홈</li>
                        <li>교구안내</li>
                        <li>교구청</li>
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
					<!-- srchTabs -->
                	<ul class="srchTabs v2">
                        <li><a href="/intro/diocesan.do">조직도</a></li>
                        <li class="on"><a href="/intro/diocesan_02.jsp">부서 위치안내</a></li>
                        <li><a href="/intro/diocesan_03.jsp">교구위원회</a></li>
                        <li><a href="/intro/diocesan_04.do?lv1=01&lv2=03&lv3=1xx">사무처</a></li>
                        <li><a href="/intro/diocesan_04.do?lv1=01&lv2=04&lv3=2xx">관리국</a></li>
                        <li><a href="/intro/diocesan_04.do?lv1=01&lv2=04&lv3=3xx">성직자국</a></li>
                        <li><a href="/intro/diocesan_04.do?lv1=01&lv2=04&lv3=4xx">복음화사목국</a></li>
                        <li><a href="/intro/diocesan_04.do?lv1=01&lv2=04&lv3=5xx">사회사목국</a></li>
                        <li><a href="/intro/diocesan_04.do?lv1=01&lv2=04&lv3=6xx">청소년사목국</a></li>
                        <li><a href="/intro/diocesan_04.do?lv1=01&lv2=04&lv3=7xx">성소국</a></li>
                        <li><a href="/intro/diocesan_04.do?lv1=03&lv2=0x&lv3=x00">법인</a></li>
                        <li><a href="/intro/diocesan_12.do">교구법원</a></li>                        
                    </ul>
                    <!-- //srchTabs -->
                    <h3 class="ttl">부서위치안내</h3>
                    <!-- location -->
					<div class="location">
						<div>
							<img src="/img/sub/intro_place.jpg" alt="">
						</div>
						<div>
		                    <!-- s:shirine_st -->
		                    <table class="shirine_st v3">
		                        <caption>부서위치 관련 정보</caption>
								<tbody>
									<tr>
										<th scope="row">천주교 인천교구청<span>인천 동구 박문로 1</span></th>
										<td>
											사무처, 관리국, 성직자국, 복음화사목국(사목부,
											노인사목부, 가정사목부, 선교사목부), 성소국, 전산실,
											홍보실, 교구법원, 인천가톨릭교육재단
										</td>
									</tr>	
									<tr>
										<th scope="row">천주교 인천교구 가톨릭 청소년센터<span>인천 동구 박문로 1 (인천교구청 옆건물)</span></th>
										<td>
											청소년교육국(유소년부, 청소년부, 청년부, 대학사목부), 가톨릭아동청소년재단
										</td>
									</tr>
									<tr>
										<th scope="row">천주교 인천교구 가톨릭 사회사목센터<span>인천 중구 우현로 50번길 2</span></th>
										<td>
											사회사목국(환경사목부, 경찰사목부, 교정사목부, 이주사목부, 생명사랑운동본부, 우리농촌살리기운동본부)
										</td>
									</tr>
									<tr>
										<th scope="row">천주교 인천교구 가톨릭 사회복지센터<span>인천 중구 답동로 23</span></th>
										<td>인천가톨릭사회복지회</td>
									</tr>	
									<tr>
										<th scope="row">천주교 인천교구 가톨릭 노동자센터<span>인천 부평구 경인로 671</span></th>
										<td>노동사목부</td>
									</tr>				
								</tbody>
							</table>
							<!-- e:shirine_st -->
						</div>
					</div>
                    <!-- //location -->
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
