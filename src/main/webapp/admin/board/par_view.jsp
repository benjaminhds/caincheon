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
	document.form02.action = '/admin/board/par_list.do';
	document.form02.submit();
	return false;
}

function insert_contents() {
	if( $("#c_idx").val() == "") { alert("분류를 선택해 주세요."); $("#c_idx").focus(); return; }
	if( $("#title").val() == "") { alert("제목을 입력해 주세요."); $("#title").focus(); return; }
	if( $(":input:radio[name=is_view]:checked").val() == undefined) { alert("노출여부를 선택해 주세요."); $("#is_view").focus(); return; }
	
	var vfm = document.form01;
	vfm.action = '/admin/board/par_insert.do';
	vfm.submit();
	return false;
}

function modify_contents() {
	if( $("#c_idx").val() == "") { alert("분류를 선택해 주세요."); $("#c_idx").focus(); return; }
	if( $("#title").val() == "") { alert("제목을 입력해 주세요."); $("#title").focus(); return; }
	if( $(":input:radio[name=is_view]:checked").val() == undefined) { alert("노출여부를 선택해 주세요."); $("#is_view").focus(); return; }

	//if( $("#captcha").val() == "") { alert("보안문자를 입력해 주세요."); $("#captcha").focus(); return; }
	var vfm = document.form01;
	vfm.action = '/admin/board/par_modify.do';
	vfm.submit();
	return false;
}

function delete_contents() {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form02.action = '/admin/board/par_delete.do';
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
						<h3 class="page-header">동정/강론 등록</h3>
					</c:when>
					<c:otherwise>
						<h3 class="page-header">동정/강론 수정</h3>	
					</c:otherwise>
					</c:choose>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
<form id="form01" name="form01" enctype="multipart/form-data" method="POST" action="/admin/board/par_list.do">
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
										<th><label for="">분류</label></th>
										<td>
											<select class="form-control" name="c_idx" id="c_idx">
												<option value="">선택</option>
												<option value="3" <c:if test="${CONTENTS.C_IDX=='3' }" >selected</c:if>>동정</option>
												<option value="4" <c:if test="${CONTENTS.C_IDX=='4' }" >selected</c:if>>강론</option>
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
										<th><label for="">행사날짜</label></th>
										<td>
											<div class="row">
												<div class="col-xs-3">
											<input class="form-control" type="text" maxlength="10" name="event_date" id="event_date" value="${CONTENTS.EVENT_DATE }">
												</div>
											</div>
										</td>
									</tr>
									<!-- dl, checkbox 예시 -->	
									<tr>
										<th><label for="">공지글 </label></th>
										<td>
											<label class="checkbox-inline" for=""><input type="checkbox" name="is_notice" id="is_notice" value="Y" <c:if test="${CONTENTS.IS_NOTICE=='Y' }" >checked="checked"</c:if>> 게시글을 최상단에 고정 노출함</label>
										</td>
									</tr>
									<tr>
										<th><label for="">파일첨부</label></th>
										<td>
											<% pageContext.setAttribute("HEAD_CONST__attach_file_form__MAX_FILE_COUNT", 1); // for jstl 구문 사용을 위해서 context에 변수를 세팅한다. %>
											<%@ include file="/admin/_common/inc/attach_file_form_loop.jsp" %>
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
									<!-- 
	                                <tr>
	                                    <th>보안문자</th>
	                                    <td class="secure"><script>var gCaptchaId = Math.random();</script>
	                                        <em><img src="/captcha" id="captchaImg" alt="captcha img"></em>&nbsp;
	                                    	<input type="text" placeholder="보안문자를 입력하세요" name="captcha" id="captcha" value="">
	                                        <button type="button" onClick='javascript: $("#captchaImg").attr("src", "/captcha?id="+Math.random());'><img src="../img/reset.png" alt="보안문자 새로고침"></button>
	                                    </td>
	                                </tr>
	                                 -->
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

window.onload = function() {
	// menu 선택되게 처리
	onLoadLeftMenu('board_01');
	
	// calendar
	$("#event_date").datepicker();
	$("input.event_date").datepicker().attr("maxlength", 10);
	
	// editor
	CKEDITOR.replace( 'contents', {
    	filebrowserUploadUrl: '/admin/ckeditor/fileUploadInContent01.do'
  	});
	
}

</script>
<form id="form02" name="form02" method="POST" action="/admin/board/par_list.do">
 <input type="hidden" name="bl_idx" id="bl_idx" value="${CONTENTS.BL_IDX}"/>
</form>
</html>