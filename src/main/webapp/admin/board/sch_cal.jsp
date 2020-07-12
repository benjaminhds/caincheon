<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/admin/_common/inc/head.jsp" %>
<%
if(admSessionMemId==null||admSessionMemId.equals("")) {                // id가 Null 이거나 없을 경우
	response.sendRedirect("/admin/login.jsp");    // 로그인 페이지로 리다이렉트 한다.
}
%>

<script type="text/javascript">
function goPage(pageNo) {
	document.getElementById('pageNo').value=pageNo;
	document.form01.submit();
    return false;
}

function goSearch() {
	if(document.getElementById('searchText').value == '') {
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
	document.getElementById('s_idx').value=s_idx;
	document.form01.submit();
    return false;
}

</script>


<body>
<form class="form-inline" name="form01" action="/admin/board/sch_cal.do" method="POST">
<input type="hidden" name="query_type" id="query_type" value="" />
<input type="hidden" name="list_type" id="list_type" value="calendar" />
<input type="hidden" name="" id="" value=""/>

<div class="container" id="wrapper">
	<!-- Navigation -->
	<%@ include file="/admin/_common/inc/top.jsp" %>
<!-- Page Content -->

	<div id="page-wrapper">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">교구장 일정</h3>
			</div>
		</div>

		<div class="row">	
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">		  
						<div class="form-group">			
							<table style="width:100%" border="0">
							<tr>
								<th width="50"><label>검색</label></th>
								<th>
									<select name="searchGubun" id="searchGubun">
										<option value="title">제목</option>
										<option value="content">내용</option>
									</select>
								</th>
								<th>
									<input type="text" name="searchText" id="searchText" value="${searchText}">
								</th>
								<th>
									<button type="button" onclick="javascript:goSearch();return false;">검색</button>
								</th>
							</tr>
							<!-- <tr>
								<td></td>
								<td></td>
								<td></td>
								<td><button type="button" onclick="javascript:insertContents();return false;">등록</button>
								</td>
							</tr> -->
							</table>


						</div> <!-- class="form-group" -->
					</div> <!-- class="panel-heading" -->
					
					<div class="panel-body">
				
						<!-- calendar -->
						<div class="calendar">
							<ul><input type="hidden" name="srch_ym" value="${srch_ym }" />
								<li><a href="/admin/board/sch_cal.do?srch_ym=${PREV_MONTH }"><img src="/img/sub/_ico/cal_arr_l.png" alt=""></a></li>
								<li><em>${srch_ym }</em></li>
								<li><a href="/admin/board/sch_cal.do?srch_ym=${NEXT_MONTH }"><img src="/img/sub/_ico/cal_arr_r.png" alt=""></a></li>
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
											<td class="sun" ><span>${DIARY['일'] } <c:forEach var="entry" items="${M_SCHEDULE[DIARY['일']]}" varStatus="status"> <a href="/admin/board/sch_view.do?list_type=calendar&query_type='modify'&s_idx=${entry.key}">${entry.value}</a></c:forEach></span></td>
											<td class="gray"><span>${DIARY['월'] } <c:forEach var="entry" items="${M_SCHEDULE[DIARY['월']]}" varStatus="status"> <a href="/admin/board/sch_view.do?list_type=calendar&query_type='modify'&s_idx=${entry.key}">${entry.value}</a></c:forEach></span></td>
											<td class="gray"><span>${DIARY['화'] } <c:forEach var="entry" items="${M_SCHEDULE[DIARY['화']]}" varStatus="status"> <a href="/admin/board/sch_view.do?list_type=calendar&query_type='modify'&s_idx=${entry.key}">${entry.value}</a></c:forEach></span></td>
											<td class="gray"><span>${DIARY['수'] } <c:forEach var="entry" items="${M_SCHEDULE[DIARY['수']]}" varStatus="status"> <a href="/admin/board/sch_view.do?list_type=calendar&query_type='modify'&s_idx=${entry.key}">${entry.value}</a></c:forEach></span></td>
											<td class="gray"><span>${DIARY['목'] } <c:forEach var="entry" items="${M_SCHEDULE[DIARY['목']]}" varStatus="status"> <a href="/admin/board/sch_view.do?list_type=calendar&query_type='modify'&s_idx=${entry.key}">${entry.value}</a></c:forEach></span></td>
											<td class="gray"><span>${DIARY['금'] } <c:forEach var="entry" items="${M_SCHEDULE[DIARY['금']]}" varStatus="status"> <a href="/admin/board/sch_view.do?list_type=calendar&query_type='modify'&s_idx=${entry.key}">${entry.value}</a></c:forEach></span></td>
											<td class="gray"><span>${DIARY['토'] } <c:forEach var="entry" items="${M_SCHEDULE[DIARY['토']]}" varStatus="status"> <a href="/admin/board/sch_view.do?list_type=calendar&query_type='modify'&s_idx=${entry.key}">${entry.value}</a></c:forEach></span></td>
										</tr>
									</c:forEach>
									</c:when>
									</c:choose>
								</tbody>
							</table>
						</div> <!-- class="calendar" -->
					</div> <!-- class="panel-body" -->
					<!-- arrow -->
					<%@ include file="/_common/inc/paging2.jsp" %>
					<!-- //arrow -->   
				</div>  <!-- class="panel panel-default" -->
			</div>	<!-- div class="col-lg-12" -->
		</div> <!-- class="row" -->
	</div> <!-- id="page-wrapper" -->
</div> <!-- class="container" id="wrapper" -->
</form>
<script type="text/javascript">

window.onload = function () {
	onLoadLeftMenu('etc_02');
}

</script>
</body>

</html>
