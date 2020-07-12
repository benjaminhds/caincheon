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
                        <li><a href="/intro/diocesan_02.jsp">부서 위치안내</a></li>
                        <li><a href="/intro/diocesan_03.jsp">교구위원회</a></li>
                        <li <c:if test="${row.NAME eq '사무처'}">class="on"</c:if>     ><a href="/intro/diocesan_04.do?lv1=01&lv2=03&lv3=1xx">사무처</a></li>
                        <li <c:if test="${row.NAME eq '관리국'}">class="on"</c:if>     ><a href="/intro/diocesan_04.do?lv1=01&lv2=04&lv3=2xx">관리국</a></li>
                        <li <c:if test="${row.NAME eq '성직자국'}">class="on"</c:if>    ><a href="/intro/diocesan_04.do?lv1=01&lv2=04&lv3=3xx">성직자국</a></li>
                        <li <c:if test="${row.NAME eq '복음화사목국'}">class="on"</c:if> ><a href="/intro/diocesan_04.do?lv1=01&lv2=04&lv3=4xx">복음화사목국</a></li>
                        <li <c:if test="${row.NAME eq '사회사목국'}">class="on"</c:if>  ><a href="/intro/diocesan_04.do?lv1=01&lv2=04&lv3=5xx">사회사목국</a></li>
                        <li <c:if test="${row.NAME eq '청소년사목국'}">class="on"</c:if> ><a href="/intro/diocesan_04.do?lv1=01&lv2=04&lv3=6xx">청소년사목국</a></li>
                        <li <c:if test="${row.NAME eq '성소국'}">class="on"</c:if>     ><a href="/intro/diocesan_04.do?lv1=01&lv2=04&lv3=7xx">성소국</a></li>
                        <li <c:if test="${row.NAME eq '법인'}">class="on"</c:if>      ><a href="/intro/diocesan_04.do?lv1=03&lv2=0x&lv3=x00">법인</a></li>
                        <li><a href="/intro/diocesan_12.do">교구법원</a></li>
					</ul>
					<!-- //srchTabs -->
					
			
			<!-- Department VO START -->
				
				<c:set var="SUB_DEPART_NO" value="1" />
				<c:forEach var="row" items="${LIST}" varStatus="status">
					
					<c:set var="PART_PP" value="${fn:substring(row.LV3,1,3)}" />
					<c:set var="PART_CD" value="${row.LV3}" />
					<c:set var="PART_NM" value="&nbsp;" />
					
					<!-- layout design start -->
					<c:if test="${'000' ne row.LV3}">
						<c:set var="PART_NM" value="${row.NAME }" />
					</c:if>
					
					<!-- tbTxt -->
					<div class="tbTxt">
					
					<!-- XX000 :: 부서 -->
					<c:if test="${'00' eq PART_PP}">
					<h3 class="ttl">${row.NAME }</h3>
					
						<!-- box -->
						<div class="box">
							<h4 class="ttl">${row.NAME} </h4>
							<c:if test="${fn:length(row.INTRO)>0 }"><p class="txt v2">${row.INTRO }</p></c:if>
							<table class="shirine_st v4">
			                    <caption>교구청 ${PART_NM } 정보</caption>
								<tbody>
									<tr>
										<th scope="row">부서장</th>
										<td>${row.P_NAME1 }
										<BR> ${row.P_NAME2}<!-- <BR> ${row.P_NAME3 } -->
										</td>
										<th scope="row">웹사이트</th>
										<td>${row.HOMEPAGE1}</td>
									</tr>
									<tr>
										<th scope="row">전화</th>
										<td>${row.TEL}</td>
										<th scope="row">팩스</th>
										<td>${row.FAX}</td>
									</tr>
									<tr>
										<th scope="row">메일</th>
										<td>${row.MAIL}</td>
										<th scope="row">소재지</th>
										<td>${row.ADDR1}</td>
									</tr>
								</tbody>
							</table>	
						</div>
						<!-- //box -->
					</c:if>
					
					<!-- XX00? :: 부속 부서 -->
					<c:if test="${'00' ne PART_PP}">
						<!-- tbGray -->
						<div class="tbGray">
							<h4 class="ttl">${row.NAME} </h4>
							<c:if test="${fn:length(row.INTRO)>0 }"><p class="txt v2">${row.INTRO }</p></c:if>
							<table class="shirine_st v4">
			                    <caption>교구청 ${PART_NM } 정보</caption>
								<tbody>
									<tr>
										<th scope="row">부서장</th>
										<td>${row.P_NAME1 }<BR> ${row.P_NAME2}<!-- <BR> ${row.P_NAME3 } --></td>
										<th scope="row">웹사이트</th>
										<td>${row.HOMEPAGE1}</td>
									</tr>
									<tr>
										<th scope="row">전화</th>
										<td>${row.TEL}</td>
										<th scope="row">팩스</th>
										<td>${row.FAX}</td>
									</tr>
									<tr>
										<th scope="row">메일</th>
										<td>${row.MAIL}</td>
										<th scope="row">소재지</th>
										<td>${row.ADDR1}</td>
									</tr>
								</tbody>
							</table>	
							<!-- //비서실 -->
						</div>
						<!-- //tbGray -->
					</c:if>
					
					</div>
					<!-- //tbTxt -->
					
				</c:forEach>
			<!-- Department VO END -->
			
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
