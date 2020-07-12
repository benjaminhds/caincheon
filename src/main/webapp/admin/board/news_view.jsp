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
	document.form02.action = '/admin/board/news_list.do';
	//document.getElementById('idx').value=idx;
	document.form02.submit();
	return false;
}

function changeIdx(sVal) {
	if(sVal == "12") {
		$("#tr_depart").show();
	} else {
		$("#tr_depart").hide();
		document.getElementById("depart_idx").value = "";		
	}
}

/*
 * 수정/등록 시 공통 체크
 */
function CommonCheck() {
	if( $("#b_idx").val() == "") { alert("구분를 선택해 주세요."); $("#b_idx").focus(); return; }
	if( $("#title").val() == "") { alert("제목을 입력해 주세요."); $("#title").focus(); return; }
	if( $("#b_idx").val() == "12" && $("#depart_idx").val() == "" ) {
		alert("소속을 선택해 주세요.");
		$("#depart_idx").focus();
		return;
	}
	if( $(":input:radio[name=is_view]:checked").val() == undefined) { alert("노출여부를 선택해 주세요."); $("#is_view").focus(); return; }
}

function insert_contents() {
	CommonCheck();
	document.form01.action = '/admin/board/news_insert.do';
	document.form01.submit();
	return false;
}

function modify_contents() {
	CommonCheck();
	document.form01.action = '/admin/board/news_modify.do';
	document.form01.submit();
	return false;
}

function delete_contents() {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form02.action = '/admin/board/news_delete.do';
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
						<h3 class="page-header">교회/교구/공동체 소식 등록</h3>
					</c:when>
					<c:otherwise>
						<h3 class="page-header">교회/교구/공동체 소식 수정</h3>	
					</c:otherwise>
					</c:choose>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
<form id="form01" name="form01" enctype="multipart/form-data" method="POST" action="/admin/board/news_list.do">
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
										<th><label for="">구분</label></th>
										<td>
											<select class="form-control" name="b_idx" id="b_idx" onChange="changeIdx(this.value);">
												<option value="">선택</option>
												<option value="9"  <c:if test="${CONTENTS.B_IDX eq '9' }" >selected</c:if>>교회</option>
												<option value="12" <c:if test="${CONTENTS.B_IDX eq '12'}" >selected</c:if>>교구</option>
												<option value="10" <c:if test="${CONTENTS.B_IDX eq '10'}" >selected</c:if>>공동체</option>
											</select>
										</td>
									</tr>
									<tr id="tr_depart">
										<th><label for="">소속</label></th>
										<td>
											<select class="form-control" name="depart_idx" id="depart_idx">
												<option value="">선택</option>
												<option value="1">교구</option>
												<c:if test="${fn:length(_1x00xList) > 0}">
													<c:forEach var="dlist" items="${_1x00xList}" varStatus="status">
													<c:if test="${dlist.DEPART_IDX ne ''}">
														<option value="${dlist.DEPART_IDX}" <c:if test = "${ dlist.DEPART_IDX eq CONTENTS.DEPART_IDX }"> selected </c:if> >${dlist.NAME}</option>
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
											<label class="radio-inline" for=""><input type="radio" name="is_view" id="is_view" value="Y" <c:if test="${CONTENTS.IS_VIEW=='Y' }" >checked="checked"</c:if>>노출</label>
											<label class="radio-inline" for=""><input type="radio" name="is_view" id="is_view" value="N" <c:if test="${CONTENTS.IS_VIEW=='N' }" >checked="checked"</c:if>>비노출</label>
										</td>
									</tr>
									<tr>
										<th><label for="">파일첨부</label></th>
										<td>
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
	onLoadLeftMenu('board_02');
	
/* 	$("#event_date").datepicker();
	$("input.event_date").datepicker().attr("maxlength", 10); */
	//CKEDITOR.replace('contents');
	
	CKEDITOR.replace( 'contents', {
    	filebrowserUploadUrl: '/admin/ckeditor/fileUploadInContent02.do'
  	});
	
	changeIdx('${CONTENTS.B_IDX}');
}

</script>
</script>
<form id="form02" name="form02" method="POST" action="/admin/board/news_list.do">
 <input type="hidden" name="bl_idx" id="bl_idx" value="${CONTENTS.BL_IDX}"/>
</form>
</html>