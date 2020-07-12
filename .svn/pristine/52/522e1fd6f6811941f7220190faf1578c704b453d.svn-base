<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/admin/_common/inc/head.jsp" %>
<%
if(admSessionMemId==null||admSessionMemId.equals("")) {                // id가 Null 이거나 없을 경우
	response.sendRedirect("/admin/login.jsp");    // 로그인 페이지로 리다이렉트 한다.
}
%>
<script type="text/javascript">
function viewList() {
	document.form01.action = '/admin/board/popup_list.do';
	document.form01.submit();
	return false;
}

function insert_contents() {
	if( $("#title").val() == "") { alert("제목을 입력해 주세요."); $("#title").focus(); return; }
	if( $(":input:radio[name=popup_type]:checked").val() == undefined) { alert("팝업타입을 선택해 주세요."); $("#popup_type").focus(); return; }	
	if( $(":input:radio[name=open_yn]:checked").val() == undefined) { alert("노출여부를 선택해 주세요."); $("#open_yn").focus(); return; }
	if( $("#contents").val() == "") { alert("내용을 입력해 주세요."); $("#contents").focus(); return; }

	document.form01.action = '/admin/board/popup_insert.do';
	document.form01.submit();
	return false;
}

function modify_contents() {
	//alert('modify_contents');
	document.form01.action = '/admin/board/popup_modify.do';
	//document.getElementById('idx').value=idx;
	document.form01.submit();
	return false;
}

/* function delete_contents() {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/board/popup_delete.do';
		//document.getElementById('inq_no').value=no;
		document.form01.submit();
	}
	return false;
} */

window.onload = function() {
	onLoadLeftMenu('main_01');
	CKEDITOR.replace( 'contents', {
    	filebrowserUploadUrl: '/admin/board/mainImageUpload.do'
  	});
}

$(function() {
    $('#post_date_from').appendDtpicker({
    	"autodateOnStart": false
    });
    $('#post_date_to').appendDtpicker({
    	"autodateOnStart": false
    });
});
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
	<h3 class="page-header">팝업 등록</h3>
</c:when>
<c:otherwise>
	<h3 class="page-header">팝업 수정</h3>	
</c:otherwise>
</c:choose>
					</div>
				<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
  <form class="well" name="form01" action="/admin/board/popup_list.do" method="POST">
  	<input type="hidden" name="query_type" id="query_type" value="${_params.query_type }"/>	
	<input type="hidden" name="popup_no" id="popup_no" value="${popup.POPUP_NO }"/>
	<input type="hidden" name="id" id="id" value="<%=admSessionMemId %>"/>
	<!-- Contents : Begin //-->
				<div class="page-form">
					<div class="panel panel-default">
						<div class="panel-body">
							
							<table class="table tbl-form">
								<colgroup>
									<col style="width:120px;">
									<col>
									<col style="width:120px;">
									<col>
								</colgroup>
								<tbody>
								<tr>
									<td><label for="">제목</label></td>
									<td colspan="3"><input type="text" class="form-control" id="title" placeholder="" name="title" value="${popup.TITLE}"/></td>
								</tr>
								<tr>
									<td><label for="">게시기간</label></td>
									<td colspan="3">
									<div class="col-md-4">
									<input type="text" name="post_date_from" id="post_date_from" maxlength="16" value="${popup.POST_DATE_FROM}"/>
						    		</div>
						    		<div class="col-md-1">
						    			<label for="title">~</label>
						    		</div>
						    		<div class="col-md-4">
	    							<input type="text" name="post_date_to" id="post_date_to" maxlength="16" value="${popup.POST_DATE_TO}"/>
									</div>
									</td>
								</tr>
								<tr>
									<td><label for="">팝업타입</label></td>
									<td>
						        		<label class="radio-inline"><input type="radio" name="popup_type" id="popup_type" value="1" <c:if test="${popup.POPUP_TYPE=='1' }" >checked="checked"</c:if>>레이어</label>
						        		<label class="radio-inline"><input type="radio" name="popup_type" id="popup_type" value="2" <c:if test="${popup.POPUP_TYPE=='2' }" >checked="checked"</c:if>>새창</label>
									</td>
									<td><label for="">노출여부</label></td>
									<td>
						        		<label class="radio-inline"><input type="radio" name="open_yn" id="open_yn" value="Y" <c:if test="${popup.OPEN_YN=='Y' }" >checked="checked"</c:if>>노출</label>
						        		<label class="radio-inline"><input type="radio" name="open_yn" id="open_yn" value="N" <c:if test="${popup.OPEN_YN=='N' }" >checked="checked"</c:if>>비노출</label>
									</td>
								</tr>
								<tr>
									<td><label for="">창위치</label></td>
									<td><div class="col-md-2" >
						    			<label for="">좌측</label>		
						    		</div>
						    		<div class="col-md-3" >
						    			<input class="control-input" type="text" class="form-control" id="window_pos_left" placeholder="" name="window_pos_left" value="${popup.WINDOW_POS_LEFT}"/>		
						    		</div></td>
									<td><label for="">상단</label></td>
									<td>
						    			<input class="control-input" type="text" class="form-control" id="window_pos_top" placeholder="" name="window_pos_top" value="${popup.WINDOW_POS_TOP}"/>
									</td>
								</tr>
								<tr>
									<td><label for="">창크기</label></td>
									<td>
							    		<div class="col-md-2" >
							    			<label for="">너비</label>		
							    		</div>
							    		<div class="col-md-3" >
							    			<input class="control-input" type="text" class="form-control" id="window_size_width" placeholder="" name="window_size_width" value="${popup.WINDOW_SIZE_WIDTH}"/>		
							    		</div>
									</td>
									<td><label for="">높이</label></td>
									<td>    			<input class="control-input" type="text" class="form-control" id="window_size_height" placeholder="" name="window_size_height" value="${popup.WINDOW_SIZE_HEIGHT}"/>		
									</td>
								</tr>
								<tr>
									<td><label for="">내용</label></td>
									<td colspan="3">
	    			<textarea id="contents" name="contents" cols="80" rows="10">${popup.CONTENTS}
	        		</textarea>		
									</td>
								</tr> 
								
								</tbody>
							</table>
						</div>
					</div>
</form>
<p>
<button type="button" id="btnList"   class="btn btn-default pull-right" onclick="javascript:viewList();return false;">목록</button>
<c:choose>
<c:when test="${_params.query_type == 'insert'}">
	<button type="button" id="btnInsert" class="btn btn-default pull-right" onclick="javascript:insert_contents();return false;">등록</button>	
</c:when>
<c:otherwise>
	<button type="button" id="btnModify" class="btn btn-default pull-right" onclick="javascript:modify_contents();return false;">수정</button>
	<!-- <button type="button" id="btnDelete" class="btn btn-default pull-right" onclick="javascript:delete_contents();return false;">중지</button> -->	
</c:otherwise>
</c:choose>
</p>
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->
</body>

<script type="text/javascript">
$(document).ready(function() {
    $('.form_datetime').datetimepicker({
        language:  'ko',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0,
        showMeridian: 1
    });
	$('.form_date').datetimepicker({
        language:  'ko',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
    });
	$('.form_time').datetimepicker({
        language:  'ko',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 1,
		minView: 0,
		maxView: 1,
		forceParse: 0
    });
});
</script>

</html>