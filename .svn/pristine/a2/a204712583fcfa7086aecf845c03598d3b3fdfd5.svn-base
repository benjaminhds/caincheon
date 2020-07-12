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
                        <li <c:if test="${depart_idx eq '11'}">class="on"</c:if>><a href="/intro/diocesan_04.do?depart_idx=11">사무처</a></li>
                        <li <c:if test="${depart_idx eq '12'}">class="on"</c:if>><a href="/intro/diocesan_04.do?depart_idx=12">관리국</a></li>
                        <li <c:if test="${depart_idx eq '13'}">class="on"</c:if>><a href="/intro/diocesan_04.do?depart_idx=13">성직자국</a></li>
                        <li <c:if test="${depart_idx eq '14'}">class="on"</c:if>><a href="/intro/diocesan_04.do?depart_idx=14">복음화사목국</a></li>
                        <li <c:if test="${depart_idx eq '15'}">class="on"</c:if>><a href="/intro/diocesan_04.do?depart_idx=15">사회사목국</a></li>
                        <li <c:if test="${depart_idx eq '16'}">class="on"</c:if>><a href="/intro/diocesan_04.do?depart_idx=16">청소년사목국</a></li>
                        <li <c:if test="${depart_idx eq '17'}">class="on"</c:if>><a href="/intro/diocesan_04.do?depart_idx=17">성소국</a></li>
                        <li <c:if test="${depart_idx eq '18'}">class="on"</c:if>><a href="/intro/diocesan_04.do?depart_idx=18">법인</a></li>
                        <li><a href="/intro/diocesan_12.do">교구법원</a></li>
					</ul>
					<!-- //srchTabs -->
					
			
			<!-- Department VO START -->
				<!-- find a top depart name -->
				<c:set var="PART_NM" value="000" />
				<c:forEach var="row" items="${LIST}" varStatus="status">
					<c:set var="PART_CD" value="${fn:substring(row.DEPART_CODE, 2, 5)}" />
					<c:if test="${'000' eq PART_CD}">
						<c:set var="PART_NM" value="${row.NAME }" />
					</c:if>
				</c:forEach>
				
					
					
				<!-- layout design start -->
					<h3 class="ttl">${PART_NM }</h3>
					<!-- tbTxt -->
					<div class="tbTxt">
				
				
				<c:set var="SUB_DEPART_NO" value="1" />
				<c:forEach var="row" items="${LIST}" varStatus="status">
					<!-- XX000 :: 부서 -->
					<c:set var="PART_CD" value="${fn:substring(row.DEPART_CODE, 2, 5)}" />
					
					<c:if test="${'000' eq PART_CD}">
						<!-- box -->
						<div class="box">
							<p class="txt">${row.DEPART_TEXT}</p>
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
					<c:if test="${'000' ne PART_CD}">
						<c:if test="${status.index == 1}">
							<!-- tbGray -->
							<div class="tbGray">
						</c:if>
					
					
							<!-- 비서실 -->
							<h4 class="ttl">${row.NAME}</h4>
							<c:if test="${row.DEPART_TEXT ne NULL && row.DEPART_TEXT ne ''}">
							<p class="txt v2">${row.DEPART_TEXT}</p>
							</c:if>
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
					
						<c:if test="${status.index == fn:length(LIST)-1 }">
							</div>
							<!-- //tbGray -->
						</c:if>
					</c:if>
					
				</c:forEach>
			<!-- Department VO END -->
			
					</div>
					<!-- //tbTxt -->
					
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
