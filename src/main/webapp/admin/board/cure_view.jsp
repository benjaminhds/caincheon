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
	document.form02.action = '/admin/board/cure_list.do';
	//document.getElementById('idx').value=idx;
	document.form02.submit();
	return false;
}

function insert_contents() {
	if( $("#c_idx").val() == "") { alert("구분를 선택해 주세요."); $("#c_idx").focus(); return; }
	if( $("#title").val() == "") { alert("제목을 입력해 주세요."); $("#title").focus(); return; }
	if( $(":input:radio[name=is_view]:checked").val() == undefined) { alert("노출여부를 선택해 주세요."); $("#is_view").focus(); return; }
	
	document.form01.action = '/admin/board/cure_insert.do';
	document.form01.submit();
	return false;
}

function modify_contents() {
	if( $("#c_idx").val() == "") { alert("구분를 선택해 주세요."); $("#c_idx").focus(); return; }
	if( $("#title").val() == "") { alert("제목을 입력해 주세요."); $("#title").focus(); return; }
	if( $(":input:radio[name=is_view]:checked").val() == undefined) { alert("노출여부를 선택해 주세요."); $("#is_view").focus(); return; }

	/* 
	if(document.getElementById('befFile').value != "") {
		if(document.getElementById('confirm("기존파일이 있습니다. 변경하시겠습니까?") == false) {
			return;
		}
	} */
	document.form01.action = '/admin/board/cure_modify.do';
	document.form01.submit();
	return false;
}

function delete_contents() {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form02.action = '/admin/board/cure_delete.do';
		document.form02.submit();
	}
	return false;
}


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
						<h3 class="page-header">사목자료 등록</h3>
					</c:when>
					<c:otherwise>
						<h3 class="page-header">사목자료 수정</h3>	
					</c:otherwise>
					</c:choose>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
<form id="form01" name="form01" enctype="multipart/form-data" method="POST" action="/admin/board/cure_list.do">
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
											<select class="form-control" name="c_idx" id="c_idx">
												<option value="">선택</option>
												  <option value="5" <c:if test="${CONTENTS.C_IDX=='5' }" >selected</c:if>>전례</option>
												  <option value="6" <c:if test="${CONTENTS.C_IDX=='6' }" >selected</c:if>>양식</option>
												  <option value="7" <c:if test="${CONTENTS.C_IDX=='7' }" >selected</c:if>>교리</option>
												  <option value="8" <c:if test="${CONTENTS.C_IDX=='8' }" >selected</c:if>>사목</option>
												  <option value="9" <c:if test="${CONTENTS.C_IDX=='9' }" >selected</c:if>>선교</option>
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
										<th><label for="">파일첨부</label></th>
										<td>
											<%@ include file="/admin/_common/inc/attach_file_form.jsp" %>
										</td>
									</tr>
									<tr>
										<th><label for="">다운로드권한</label></th>
										<td>
											<label class="radio-inline" for=""><input type="radio" name="down_level" id="down_level" value="A" <c:if test="${CONTENTS.DOWN_LEVEL!='M' }" >checked="checked"</c:if>>전체</label>
											<label class="radio-inline" for=""><input type="radio" name="down_level" id="down_level" value="M" <c:if test="${CONTENTS.DOWN_LEVEL=='M' }" >checked="checked"</c:if>>회원</label>
										</td>
									</tr>
									<tr>
										<th><label for="">노출</label></th>
										<td>
											<label class="radio-inline" for=""><input type="radio" name="is_view" id="is_view" value="Y" <c:if test="${CONTENTS.IS_VIEW!='N' }" >checked="checked"</c:if>>노출</label>
											<label class="radio-inline" for=""><input type="radio" name="is_view" id="is_view" value="N" <c:if test="${CONTENTS.IS_VIEW=='N' }" >checked="checked"</c:if>>비노출</label>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<!-- Buttons : Begin //-->
					<p class="pull-right">
					<button type="button" id="btnList" class="btn btn-default btn-lg" onclick="javascript: viewList();return false;"><i class="fa fa-th-list"></i>목록</button>
					<c:choose>
					<c:when test="${_params.query_type == 'insert'}">
						<button type="button" id="btnInsert" class="btn btn-primary btn-lg" onclick="javascript: insert_contents();return false;"><i class="fa fa-check"></i>등록</button>	
					</c:when>
					<c:otherwise>
						<button type="button" id="btnModify" class="btn btn-primary btn-lg" onclick="javascript: modify_contents();return false;"><i class="fa fa-check"></i>수정</button>
						<button type="button" id="btnDelete" class="btn btn-danger btn-lg" onclick="javascript: delete_contents();return false;"><i class="fa fa-times"></i>삭제</button>	
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

<form id="form02" name="form02" method="POST" action="/admin/board/cure_list.do">
 <input type="hidden" name="bl_idx" id="bl_idx" value="${CONTENTS.BL_IDX}"/>
</form>

<script>
<c:if test = "${fn:length(ERR_MSG) > 0 }">
	// Error Handling
	setTimeout( alert("${ERR_MSG}") , 1000 );
</c:if>

// active gnb
onLoadLeftMenu('board_04');

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

window.onload = function () {
	// 
	CKEDITOR.replace( 'contents', {
    	filebrowserUploadUrl: '/admin/ckeditor/fileUploadInContent04.do'
  	});
}

</script>

</body>

</html>