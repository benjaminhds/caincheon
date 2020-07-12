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
                        <li class="on"><a href="/intro/diocesan.do">조직도</a></li>
                        <li><a href="/intro/diocesan_02.jsp">부서 위치안내</a></li>
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
                    <h3 class="ttl">교구청 조직도</h3>
                    <!-- oraganization -->
					<div class="organization">
						<ul class="fstLine">
							<li>교구장</li>
							<li>총대리</li>
						</ul>
						<div class="scnLine">
							<ul>
								<li>
									<dl>
										<dt>사무처</dt>
										<dd>비서실</dd>
										<dd>전산홍보실</dd>
									</dl>
									<dl>
										<dt>관리국</dt>
										<dd>관리과</dd>
										<dd>경리과</dd>
									</dl>
									<dl>
										<dt>사무처</dt>
										<dd>비서실</dd>
										<dd>전산홍보실</dd>
									</dl>
									<p>성직자국</p>
									<p>성소국</p>
								</li>
								<li>
									<dl>
										<dt>사회사목국</dt>
										<dd>환경사목부</dd>
										<dd>노동사목부</dd>
										<dd>경찰사목부</dd>
										<dd>교정사목부</dd>
										<dd>이주·해양사목부</dd>
										<dd>우리농촌살리기운동본부</dd>
										<dd>생명사랑운동본부</dd>
									</dl>
									<dl>
										<dt>복음화사목국</dt>
										<dd>선교사목부</dd>
										<dd>가정복음화부</dd>
										<dd>노인사목부</dd>
										<dd>병원사목부</dd>
									</dl>
									<p>인천가톨릭교육재단</p>
									<p>가톨릭아동청소년재단</p>
									<p>인천가톨릭사회복지회</p>
								</li>
								<li class="special">
									<ol>
										<li>참사회</li>
										<li>사제평의회</li>
										<li>재무평의회</li>
										<li>사목평의회</li>
										<li>교구법원</li>
									</ol>
								</li>
							</ul>
						</div>
					</div>
                    <!-- //oraganization -->
                    <!-- s:shirine_st -->
<!--                     
                    <table class="shirine_st v2">
                        <caption>조직도 관련 정보</caption>
                        <colgroup>
                        	<col style="width:10%";>
                        	<col style="width:30%";>
                        	<col style="width:30%";>
                        	<col style="width:30%";>
                        </colgroup>
                        <thead>
                        	<th scope="col">NO</th>
                        	<th scope="col">부서명</th>
                        	<th scope="col">부서장</th>
                        	<th scope="col">연락처</th>
                        </thead>
						<tbody>
							<tr>
								<td>10</td>
								<td>사무처</td>
								<td>홍길동</td>
								<td><span class="tels">032-567-8910</span><a href="tel:0325678910" class="sendTel">032-567-8910</a></td>
							</tr>
							<tr>
								<td>9</td>
								<td>관리국</td>
								<td>홍길동</td>
								<td><span class="tels">032-567-8910</span><a href="tel:0325678910" class="sendTel">032-567-8910</a></td>
							</tr>
							<tr>
								<td>8</td>
								<td>성직자통합사목국</td>
								<td>홍길동</td>
								<td><span class="tels">032-567-8910</span><a href="tel:0325678910" class="sendTel">032-567-8910</a></td>
							</tr>
							<tr>
								<td>7</td>
								<td>복음화사목국</td>
								<td>홍길동</td>
								<td><span class="tels">032-567-8910</span><a href="tel:0325678910" class="sendTel">032-567-8910</a></td>
							</tr>
							<tr>
								<td>6</td>
								<td>청소년사목국</td>
								<td>홍길동</td>
								<td><span class="tels">032-567-8910</span><a href="tel:0325678910" class="sendTel">032-567-8910</a></td>
							</tr>
							<tr>
								<td>5</td>
								<td>관리국</td>
								<td>홍길동</td>
								<td><span class="tels">032-567-8910</span><a href="tel:0325678910" class="sendTel">032-567-8910</a></td>
							</tr>
							<tr>
								<td>4</td>
								<td>성직자통합사목국</td>
								<td>홍길동</td>
								<td><span class="tels">032-567-8910</span><a href="tel:0325678910" class="sendTel">032-567-8910</a></td>
							</tr>
							<tr>
								<td>3</td>
								<td>복음화사목국</td>
								<td>홍길동</td>
								<td><span class="tels">032-567-8910</span><a href="tel:0325678910" class="sendTel">032-567-8910</a></td>
							</tr>
							<tr>
								<td>2</td>
								<td>청소년사목국</td>
								<td>홍길동</td>
								<td><span class="tels">032-567-8910</span><a href="tel:0325678910" class="sendTel">032-567-8910</a></td>
							</tr>
							<tr>
								<td>1</td>
								<td>청소년사목국</td>
								<td>홍길동</td>
								<td><span class="tels">032-567-8910</span><a href="tel:0325678910" class="sendTel">032-567-8910</a></td>
							</tr>							
						</tbody>
					</table>
-->
					<!-- e:shirine_st -->
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
