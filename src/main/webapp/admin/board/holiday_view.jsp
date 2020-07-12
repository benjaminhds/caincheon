<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/admin/_common/inc/head.jsp" %>
<%
if(admSessionMemId==null||admSessionMemId.equals("")) {                // id가 Null 이거나 없을 경우
	response.sendRedirect("/admin/login.jsp");    // 로그인 페이지로 리다이렉트 한다.
}
%>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script type="text/javascript">
function viewList() {
	document.form01.action = '/admin/board/holiday_list.do';
	document.form01.submit();
	return false;
}

function modify_contents() {

	if( $("#description").val() == "") { alert("전례력을 입력해 주세요."); $("#description").focus(); return; }

	document.form01.action = '/admin/board/holiday_modify.do';
	document.form01.submit();
	return false;
}

function delete_contents() {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/board/holiday_delete.do';
		document.form01.submit();
	}
	return false;
}

window.onload = function() {
	onLoadLeftMenu('main_03');
}
</script>
<script src="/admin/_common/ckeditor/ckeditor.js"></script>
<body>
<!-- top start(left menu include) -->
<%@ include file="/admin/_common/inc/top.jsp" %>
<!-- top end -->

<!-- main start -->        
<!-- Page Content -->
<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
	<c:choose>
	<c:when test="${_params.query_type == 'insert'}">
		<h3 class="page-header">전례력/말씀관리 등록</h3>
	</c:when>
	<c:otherwise>
		<h3 class="page-header">전례력/말씀관리 수정</h3>	
	</c:otherwise>
	</c:choose>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<form id="form01" name="form01" enctype="multipart/form-data" method="POST" action="/admin/board/holiday_list.do">
	  <input type="hidden" name="adminYn" id="adminYn" value="Y"/>
	<!-- Contents : Begin //-->
	<div class="page-form">
		<div class="panel panel-default">
			<div class="panel-body">
				
				<table class="table tbl-form">
				<colgroup>
					<col style="width:120px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th><label for="">날짜 </label></th>
						<td>
			    			<input class="form-control" type="text"  id="h_date" name="h_date" value="${holidayContents.H_DATE}" readonly />
						</td>
					</tr>
					<tr>
						<th><label for="">전례력 </label></th>
						<td>
			    			<input class="form-control" type="text"  id="description" name="description" value="${holidayContents.DESCRIPTION}" />
						</td>
					</tr>
					<tr>
						<th><label for="">말씀 </label></th>
						<td>
			    			<input class="form-control" type="text"  id="talk" name="talk" value="${holidayContents.TALK}" />
						</td>
					</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- Buttons : Begin //-->
		<p class="pull-right">
		<button type="button" id="btnList"   class="btn btn-default pull-right" onclick="javascript:viewList();return false;">목록</button>
		<c:choose>
		<c:when test="${_params.query_type == 'insert'}">
		</c:when>
		<c:otherwise>
			<button type="button" id="btnModify" class="btn btn-default pull-right" onclick="javascript:modify_contents();return false;">수정</button>
			<button type="button" id="btnDelete" class="btn btn-default pull-right" onclick="javascript:delete_contents();return false;">삭제</button>	
		</c:otherwise>
		</c:choose>
		</p>
		<!-- Buttons : End //-->

	</div>
	<!-- Contents : End //-->
			
</form>
		
	</div>
	<!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->
</body>

</html>
