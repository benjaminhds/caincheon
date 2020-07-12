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
<script type="text/javascript">
function viewList() {
	document.form02.action = '/admin/board/church_list.do';
	//document.getElementById('idx').value=idx;
	document.form02.submit();
	return false;
}

/* function changeIdx(sVal) {
	if(sVal == "12") {
		$("#tr_depart").show();
	} else {
		$("#tr_depart").hide();
		document.getElementById("depart_code").value = "";		
	}
} */

function insert_contents() {
	if( $("#notice_type").val() == "") { alert("구분를 선택해 주세요."); $("#notice_type").focus(); return; }
	if( $("#title").val() == "") { alert("제목을 입력해 주세요."); $("#title").focus(); return; }
	if( $("#church_idx").val() == "" ) {
		alert("소속을 선택해 주세요.");
		$("#church_idx").focus();
		return;
	}
	if( $(":input:radio[name=is_view]:checked").val() == undefined) { alert("노출여부를 선택해 주세요."); $("#is_view").focus(); return; }

	document.form01.action = '/admin/board/church_insert.do';
	document.form01.submit();
	return false;
}

function modify_contents() {
	if( $("#notice_type").val() == "") { alert("구분를 선택해 주세요."); $("#notice_type").focus(); return; }
	if( $("#title").val() == "") { alert("제목을 입력해 주세요."); $("#title").focus(); return; }
	if( $("#church_idx").val() == "" ) {
		alert("소속을 선택해 주세요.");
		$("#church_idx").focus();
		return;
	}
	if( $(":input:radio[name=is_view]:checked").val() == undefined) { alert("노출여부를 선택해 주세요."); $("#is_view").focus(); return; }

	document.form01.action = '/admin/board/church_modify.do';
	document.form01.submit();
	return false;
}

function delete_contents() {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form02.action = '/admin/board/church_delete.do';
		document.form02.submit();
	}
	return false;
}

$.datepicker.setDefaults({
    dateFormat: 'yy-mm-dd',
    prevText: '이전 달',
    nextText: '다음 달',
    monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    dayNames: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
    showMonthAfterYear: true,
    yearSuffix: '년'
});

</script>
<script src="http://malsup.github.com/jquery.form.js"></script> 
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
						<h3 class="page-header">본당알림 등록</h3>
					</c:when>
					<c:otherwise>
							<h3 class="page-header">본당알림 수정</h3>	
					</c:otherwise>
					</c:choose>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
<form id="form01" name="form01" enctype="multipart/form-data" method="POST" action="/admin/board/church_list.do">
 <input type="hidden" name="b_idx" id="b_idx" value="11" alt="고정 b_idx 가 11 임" />
 <input type="hidden" name="bl_idx" id="bl_idx" value="${CONTENTS.BL_IDX}"/>
 <input type="hidden" name="user_id" id="user_id" value="${admSessionMemId}"/>
 				<!-- Contents : Begin //-->
				<div class="page-form">
					<div class="panel panel-default">
						<div class="panel-body">
							
							<table class="table tbl-form">
								<colgroup>
									<col style="width:200px;">
									<col>
								</colgroup>
								<tbody>
									<tr>
										<th><label for="">작성자 </label></th>
										<td>
											<label for=""><%=admSessionMemNm %> </label>
										</td>
									</tr>
									<tr>
										<th><label for="">소속(본당)</label></th>
										<td>
											<select class="form-control" name="church_idx" id="church_idx">
												<option value="">선택</option>
												<c:if test="${fn:length(_1x00xList) > 0}">
													<c:forEach var="dlist" items="${_1x00xList}" varStatus="status">
														<c:if test="${dlist.CHURCH_IDX ne ''}">
														<option value="${dlist.CHURCH_IDX}" <c:if test = "${dlist.CHURCH_IDX eq CONTENTS.CHURCH_IDX}"> selected </c:if>>${dlist.NAME}</option>
														</c:if>
													</c:forEach>
												</c:if>
											</select>
										</td>
									</tr>
									<tr>
										<th><label for="">제목</label></th>
										<td>
											<input class="form-control" type="text" name="title" id="title" value="${CONTENTS.TITLE }">
										</td>
									</tr>
									<tr>
										<th><label for="">공지글 </label></th>
										<td>
											<label class="checkbox-inline" for=""><input type="checkbox" name="is_notice" id="is_notice" value="Y" <c:if test="${CONTENTS.IS_NOTICE=='Y' }" >checked="checked"</c:if>> 게시글을 최상단에 고정 노출함</label>
										</td>
									</tr>
									<tr>
										<th><label for="contents">내용</label></th>
										<td>
											<textarea class="form-control h-200" name="contents" id="contents">${CONTENTS.CONTENT }</textarea>
										</td>
									</tr>
									<tr>
										<th><label for="">노출</label></th>
										<td>
											<c:choose>
											<c:when test="${CONTENTS.IS_VIEW eq 'N'}">
												<label class="radio-inline" for=""><input type="radio" name="is_view" id="is_view" value="Y" >노출</label>
												<label class="radio-inline" for=""><input type="radio" name="is_view" id="is_view" value="N" checked >비노출</label>
											</c:when>
											<c:otherwise>
												<label class="radio-inline" for=""><input type="radio" name="is_view" id="is_view" value="Y" checked >노출</label>
												<label class="radio-inline" for=""><input type="radio" name="is_view" id="is_view" value="N" >비노출</label>
											</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<th><label for="">파일첨부</label></th>
										<td>
											<!--  <div class="row">
												<div class="form-group input-group">
													<input class="form-control" type="file" name="thumbFile" id="thumbFile">
												</div>
												<c:if test="${fn:length(churchContents.FILENAME) > 0 }">
													${CONTENTS.FILENAME}
													<label class="checkbox-inline" for=""><input type="checkbox" name="delFile" id="delFile" value="Y">이전파일 삭제함</label>
												</c:if>
											</div>-->
											<%@ include file="/admin/_common/inc/attach_file_form.jsp" %>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<!-- Buttons : Begin //-->
					<p class="pull-right">
					<button type="button" id="btnList" class="btn btn-default btn-lg" onclick="javascript:viewList();return false;"><i class="fa fa-th-list"></i>목록</button>
					<c:choose>
					<c:when test="${_params.query_type == 'insert'}">
						<button type="button" id="btnInsert" class="btn btn-primary btn-lg" onclick="javascript:insert_contents();return false;"><i class="fa fa-check"></i>등록</button>	
					</c:when>
					<c:otherwise>
						<button type="button" id="btnModify" class="btn btn-primary btn-lg" onclick="javascript:modify_contents();return false;"><i class="fa fa-check"></i>수정</button>
						<button type="button" id="btnDelete" class="btn btn-danger btn-lg" onclick="javascript:delete_contents();return false;"><i class="fa fa-times"></i>삭제</button>	
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
<script>
window.onload = function() {
	onLoadLeftMenu('board_03');
	
/* 	$("#event_date").datepicker();
	$("input.event_date").datepicker().attr("maxlength", 10); */
	//CKEDITOR.replace('contents');
	
	CKEDITOR.replace( 'contents', {
    	filebrowserUploadUrl: '/admin/ckeditor/fileUploadInContent03.do'
  	});
}

</script>
<form id="form02" name="form02" method="POST" action="/admin/board/church_list.do">
 <input type="hidden" name="bl_idx" id="bl_idx" value="${CONTENTS.BL_IDX}"/>
</form>
</html>