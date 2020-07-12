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
                    <h3>교구성지</h3>
                    <ul>
                        <li>홈</li>
                        <li>교구안내</li>
                        <li>교구성지</li>
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
                    <!-- <h3 class="ttl">지구별 본당 선택</h3> -->
                    <ul class="tabs v7">
                        <li><a href="/intro/shirine.jsp">갑곶순교성지</a></li>
                        <li><a href="/intro/shirine_02.jsp">반주골(이승훈 묘)</a></li>
                        <li><a href="/intro/shirine_03.jsp">제물진두성지</a></li>
			<li><a href="/intro/shirine_04.jsp">진무영 순교성지</a></li>
			<li class="sml_txt"><a href="/intro/shirine_05.jsp">일만위순교자 현양동산</a></li>
			<li><a href="/intro/shirine_06.jsp">성체성지</a></li>
			<li class="on"><a href="/intro/shirine_07.jsp">인천교구 성모당</a></li>
                    </ul>
					<!--s:table_shirine_style-->
					<table class="shirine_st">
                        <caption>교구성지 관련 정보</caption>
						<tbody>
							<tr>
								<th scope="row">홈페이지</th>
								<td>http://old.caincheon.or.kr</td>
								<th scope="row">연락처</th>
								<td></td>
							</tr>
							<tr>
								<th scope="row">주소</th>
								<td>인천시 동구 박문로 1</td>
								<th scope="row">교구</th>
								<td>인천교구</td>
							</tr>
							<tr>
								<th scope="row">지리좌표</th>
								<td colspan="3">북위 37°47′03.3″동경 126°65′09.2″</td>
							</tr>
							<tr>
								<th scope="row">문의처</th>
								<td colspan="3"></td>
							</tr>
							<tr>
								<th scope="row">찾아가는 길</th>
								<td colspan="3"></td>
							</tr>
						</tbody>
					</table>
					<!--e:table_shirine_style-->
					<!--s:shirine_img_box-->
					<div class="shirine_img_box">
						<div class="st_100"><img src="/img/sub/holy_07_01.jpg" alt="인천교구 성모당 전경 이미지"></div>
						<div class="st_100"><img src="/img/sub/holy_07_02.jpg" alt="인천교구 성모당 측면 이미지"></div>
					</div>
					<!--e:shirine_img_box-->
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
넀