<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<script type="text/javascript">
	function goPage(pageNo) {
		document.getElementById('pageNo').value = pageNo;
		document.form01.submit();
		return false;
	}

	function goSearch() {
		if (document.getElementById('searchText').value == '') {
			//alert('검색할 문자를 입력하세요.');
			//document.getElementById('searchText').focus();
			//return false;
		}
		document.form01.submit();
		return false;
	}

	function insertContents() {
		document.form01.action = '/admin/board/sch_cview.do';
		document.getElementById('query_type').value = "insert";
		document.form01.submit();
		return false;
	}

	function modifyContents(s_idx) {
		//alert("modifyContents");
		document.form01.action = '/admin/board/sch_cview.do';
		document.getElementById('query_type').value = "modify";
		document.getElementById('s_idx').value = s_idx;
		document.form01.submit();
		return false;
	}

	function deleteContents(s_idx) {
		//alert("deleteContents");
		document.form01.action = '/admin/board/sch_cview.do';
		document.getElementById('s_idx').value = s_idx;
		document.form01.submit();
		return false;
	}
</script>


<body>
<form name="form01" action="/father/father_cal.do" method="POST">
<input type="hidden" name="srch_ym" id="srch_ym" value=""/>
    <p class="skipNavi" tabindex="1">본문바로가기</p>
    <!-- wrap -->
    <div id="wrap">
        <!-- header -->
        <header>
            <%@include file="/_common/inc/gnb.jsp" %>
        </header>
        <!-- //header -->
        <!-- subVisual -->
        <section class="subVisual priest">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap">
                <article> 
                    <h3>사제</h3>
                    <ul>
                        <li>홈</li>
                        <li>사제단</li> 
                        <li>사제</li>
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
                    <h3 class="ttl">사제 달력</h3>   
                    <!-- srchForm -->
        
					<div class="row">
						<div class="col-lg-12">
							<div class="panel panel-default">
								<div class="panel-heading">
								</div>
								<!-- class="panel-heading" -->
	
								<div class="panel-body">
	
									<!-- calendar -->
									<div class="calendar">
										<ul>
											<input type="hidden" name="srch_ym" value="${_params.srch_ym }" />
											<li><a
												href="/father/father_cal.do?srch_ym=${_params.PREV_MONTH }"><img
													src="/img/sub/_ico/cal_arr_l.png" alt=""></a></li>
											<li><em>${fn:substring(_params.srch_ym,0,4) }년 ${fn:substring(_params.srch_ym,4,6) }월</em></li>
											<li><a
												href="/father/father_cal.do?srch_ym=${_params.NEXT_MONTH }"><img
													src="/img/sub/_ico/cal_arr_r.png" alt=""></a></li>
										</ul>
										<table>
											<caption>달력</caption>
											<thead>
												<tr>
													<th scope="col" class="sun">일</th>
													<th scope="col">월</th>
													<th scope="col">화</th>
													<th scope="col">수</th>
													<th scope="col">목</th>
													<th scope="col">금</th>
													<th scope="col">토</th>
												</tr>
											</thead>
											<tbody>
											<c:choose>
											<c:when test="${fn:length(L_DIARY) > 0}">
												<c:forEach items="${L_DIARY}" var="DIARY">
												<tr>
													<td class="sun"><span>${DIARY['일'] } 
													<c:forEach var="entry" items="${PRIEST[DIARY['일']]}" varStatus="status">
														<a href="/father/father_view.do?p_idx=${entry.key}">${entry.value}</a></li>
													</c:forEach></span></td>
													<td class="gray"><span>${DIARY['월'] } 
													<c:forEach var="entry" items="${PRIEST[DIARY['월']]}" varStatus="status">
														<li><a href="/father/father_view.do?p_idx=${entry.key}">${entry.value}</a></li>
													</c:forEach></span></td>
													<td class="gray"><span>${DIARY['화'] } 
													<c:forEach var="entry"  items="${PRIEST[DIARY['화']]}" varStatus="status">
														<li><a href="/father/father_view.do?p_idx=${entry.key}">${entry.value}</a></li>
													</c:forEach></span></td>
													<td class="gray"><span>${DIARY['수'] } 
													<c:forEach var="entry"  items="${PRIEST[DIARY['수']]}" varStatus="status">
														<li><a href="/father/father_view.do?p_idx=${entry.key}">${entry.value}</a></li>
													</c:forEach></span></td>
													<td class="gray"><span>${DIARY['목'] } 
													<c:forEach var="entry" items="${PRIEST[DIARY['목']]}" varStatus="status">
														<li><a href="/father/father_view.do?p_idx=${entry.key}">${entry.value}</a></li>
													</c:forEach></span></td>
													<td class="gray"><span>${DIARY['금'] } 
													<c:forEach var="entry" items="${PRIEST[DIARY['금']]}" varStatus="status">
														<li><a href="/father/father_view.do?p_idx=${entry.key}">${entry.value}</a></li>
													</c:forEach></span></td>
													<td class="gray"><span>${DIARY['토'] } 
													<c:forEach var="entry" items="${PRIEST[DIARY['토']]}" varStatus="status">
														<li><a href="/father/father_view.do?p_idx=${entry.key}">${entry.value}</a></li>
													</c:forEach></span></td>
												</tr>
												</c:forEach>
											</c:when>
											</c:choose>
											</tbody>
										</table>
									</div>
									<!-- class="calendar" -->
								</div>
								<!-- class="panel-body" -->
								<!-- arrow :: paging -->
								
								<!-- //arrow :: paging -->
							</div>
							<!-- class="panel panel-default" -->
						</div>
						<!-- div class="col-lg-12" -->
					</div>
					<!-- class="row" -->
			
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
</form>
</body>

</html>
