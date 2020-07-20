<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/admin/_common/inc/head.jsp" %>
<%
if(admSessionMemId==null||admSessionMemId.equals("")) {				// id가 Null 이거나 없을 경우
	response.sendRedirect("/admin/login.jsp");	// 로그인 페이지로 리다이렉트 한다.
}
%>
<script type="text/javascript">
function goPage(pageNo) {
	document.getElementById('pageNo').value=pageNo;
	document.form01.submit();
	return false;
}

function goSearch() {
	document.form01.submit();
	return false;
}

function insertContents() {
	document.form01.action = '/n/admin/board/board_view.do';
	document.getElementById('query_type').value = "insert";
	document.form01.submit();
	return false;
}

function modifyContents(b_idx) {
	document.form01.action = '/n/admin/board/board_view.do';
	document.getElementById('query_type').value = "modify";
	document.getElementById('i_sBidx').value=b_idx;
	document.form01.submit();
	return false;
}

function deleteContents(b_idx, bl_idx) {
	if (confirm("정말 삭제하시겠습니까??") == true){	//확인
		document.getElementById('mode').value = "i";
		
		var vfm = document.form01;
		vfm.action = '/n/admin/board/board_delete.do';
		vfm.submit();
		return false;
	}
	return false;
}
window.onload = function () {
	// active gnb
	onLoadLeftMenu('board_01');
}
</script>
<body>
<form class="form-inline" name="form01" action="/n/admin/board/board_list.do" method="POST">
<input type="hidden" name="pageNo"		id="pageNo"		value="${paging.pageNo}" />
<input type="hidden" name="query_type"	id="query_type"	value="" />
<input type="hidden" name="i_sBidx"		id="i_sBidx"	value="" />

		<!-- top start(left menu include) -->
		<%@ include file="/admin/_common/inc/top.jsp" %>
		<!-- top end -->
		
		<!-- main start -->		
		<!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h3 class="page-header">게시판 관리</h3>
					</div>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- //.row(page title end -->
			<div class="row">	
			<div class="col-lg-12">
					<div class="panel panel-default">
						<table class="table">
						<tr>
							<td>
								<b style="margin-left:30px;margin-right:15px;">명칭</b>
								<input class="form-control form-control-short w-200" type="text" name="i_sText" id="schText" value="${_params.i_sText}">
								<button type="button" style="margin:0px 2px;" class="btn btn-primary" onclick="javascript:goSearch();return false;"><i class="fa fa-search"></i>검색</button>
								<button type="button" style="margin:0px 2px;" class="btn btn-primary" onclick="javascript:insertContents();return false;"><i class="fa fa-pencil"></i>등록</button>
							</td>
						</tr>
						</table>
					  
					</div>
			<!-- /.row -->
					<div class="panel panel-default">
						<!-- /.panel-heading -->
						<div class="panel-body">
							<table class="table table-striped table-bordered table-hover">
								<colgroup>
									<col style="width:200px;">
									<col style="width:70px;">	
									<col style="width:70px;">
									<col style="width:70px;">
									<col style="width:70px;">
									<col style="width:70px;">
									<%-- <col style="width:70px;"> --%>
								</colgroup>
  							<thead>
							  <tr>
								<th>명칭</th>
								<th>게시판종류</th>
								<th>게시판유형</th>
								<th>댓글암호여부</th>
								<th>카테고리사용여부</th>
								<th>설정</th>
								<!-- <th>삭제</th> -->
							  </tr>
							</thead>
							<tbody>
							  <c:choose>
									<c:when test="${fn:length(rtn_list) > 0}">
									<c:forEach items="${rtn_list}" var="list">
										  <tr  <c:if test="${list.IS_VIEW eq 'N'}">style="color: darkgray;"</c:if> >
											<td>${list.B_NM} </td>
											<td>${list.B_KIND_NM} </td>
											<td>${list.B_TYPE_NM} </td>
											<td>${list.USEYN_SECRET} </td>
											<td>
												<c:choose>
													<c:when test="${list.CATEGORY_CNT > 0}">
														Y
													</c:when>
													<c:otherwise>
														N
													</c:otherwise>
												</c:choose>
											</td>
											<td class="center"><button name="btnEdit" type="button" class="btn40 btn-success btn-circle" title="Edit" onClick="javascript:modifyContents('${list.B_IDX}')"><i class="fa fa-pencil"></i></button>
												</td>
											<%-- <td class="center"><button name="btnDel" type="button" class="btn40 btn-danger btn-circle" title="Delete" onClick="javascript:deleteContents('${list.B_IDX}')"><i class="fa fa-times"></i></button>
												</td> --%>
										  </tr>
										  </c:forEach>
									</c:when>
								</c:choose>			  
							</tbody>
							</table>
							<!-- /.table-responsive -->
							<div>
								<!-- paging start -->
								<%@ include file="/admin/_common/inc/paging2.jsp" %>
								<!-- //paging end -->
							</div>
						</div>
				<!-- Contents : End //-->

			</div>
			<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->
</form>
</body>

</html>
