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
	document.form02.action = '/admin/board/album_list.do';
	//document.getElementById('idx').value=idx;
	document.form02.submit();
	return false;
}

function insert_contents() {
	if( $("#s_org_idx").val() == "") { alert("소속(부서)를 선택해 주세요."); $("#s_org_idx").focus(); return; }
	if( $("#c_idx").val() == "") { alert("구분을 선택해 주세요."); $("#c_idx").focus(); return; }
	if( $("#title").val() == "") { alert("제목을 선택해 주세요."); $("#title").focus(); return; }
		 
	var uploadFile = document.getElementById('thumbFile').value;
	
	if(uploadFile != "") {
		var fileExt = uploadFile.substring(uploadFile.lastIndexOf(".") + 1);
		var reg = /gif|jpg|jpeg|png/i;
		if(reg.test(fileExt) == false) {
			alert("첨부파일은 gif,jpg,png로 된 이미지만 가능합니다.");
			return;
		}
	}
	var filename = uploadFile.substring(uploadFile.lastIndexOf("\\") + 1);
	
	document.form01.action = '/admin/board/album_insert.do';
	document.getElementById('filename').value = filename;
	document.form01.submit();
	return false;
}

function modify_contents() {
	if( $("#s_org_idx").val() == "") { alert("소속(부서)를 선택해 주세요."); $("#s_org_idx").focus(); return; }
	if( $("#c_idx").val() == "") { alert("구분을 선택해 주세요."); $("#c_idx").focus(); return; }
	if( $("#title").val() == "") { alert("제목을 선택해 주세요."); $("#title").focus(); return; }

 	var uploadFile = document.getElementById('thumbFile').value;
	
	if(uploadFile != "") {
		var fileExt = uploadFile.substring(uploadFile.lastIndexOf(".") + 1);
		var reg = /gif|jpg|jpeg|png/i;
		if(reg.test(fileExt) == false) {
			alert("첨부파일은 gif,jpg,png로 된 이미지만 가능합니다.");
			return;
		}
		
 		/* if(document.getElementById('befFile').value != "") {
			if(document.getElementById('confirm("기존파일이 있습니다. 변경하시겠습니까?") == false) {
				return;
			}
		} */
	}
	var filename = uploadFile.substring(uploadFile.lastIndexOf("\\") + 1);
	
	document.form01.action = '/admin/board/album_modify.do';
	document.getElementById('filename').value = filename;
	document.form01.submit();
	return false;
}

function delete_contents(idx) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/board/album_delete.do';
		document.getElementById('idx').value = idx;
		document.form01.submit();
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

window.onload = function() {
	onLoadLeftMenu('board_06');
	CKEDITOR.replace( 'contents', {
    	filebrowserUploadUrl: '/admin/ckeditor/fileUploadInContent06.do'
  	});
	// thumbnail
	$("#thumbFile").on("change", {maxWidth:400, maxHeight:300, imgPreviewId:"imagePreview"},  handleImageFileSelect);
}

$(function() {
    $("#album_date").datepicker();
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
						<h3 class="page-header">교구 앨범 등록</h3>
					</c:when>
					<c:otherwise>
						<h3 class="page-header">교구 앨범 수정</h3>	
					</c:otherwise>
					</c:choose>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
<form id="form01" name="form01" enctype="multipart/form-data" method="POST" action="/admin/board/album_list.do">
 <input type="hidden" name="idx" id="idx" value="${CONTENTS.IDX}"/>
 <input type="hidden" name="user_id" id="user_id" value="${admSessionMemId}"/>
 <input type="hidden" name="filename" id="filename" value="${CONTENTS.FILENAME}"/>
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
										<th><label for="">소속(부서) </label></th>
										<td>
											<select class="form-control form-control-short w-200 mr-10" name="s_org_idx" id="s_org_idx">
											<option value="">선택</option>
									  		<c:choose>
												<c:when test="${fn:length(_1x00xList) > 0}">
													<c:forEach items="${_1x00xList}" var="row">	
														<option value="${row.ORG_IDX}" <c:if test="${CONTENTS.ORG_IDX==row.ORG_IDX}"> selected </c:if>>${row.NAME}</option>
													</c:forEach>
												</c:when>
											</c:choose>																							  
											</select>
										</td>
									</tr>								
									<tr>
										<th><label for="">구분</label></th>
										<td>
											<select class="form-control form-control-short w-200 mr-10" name="c_idx" id="c_idx">
												<option value="">선택</option>												
												<c:choose>
													<c:when test="${CONTENTS.C_IDX == '1'}">
														<option value="1" selected>미사/행사</option>
														<option value="2" >교육/사업</option>
													</c:when>
													<c:when test="${CONTENTS.C_IDX == '2'}">
														<option value="1" >미사/행사</option>
														<option value="2" selected>교육/사업</option>
													</c:when>
													<c:otherwise>
														<option value="1" >미사/행사</option>
														<option value="2" >교육/사업</option>
													</c:otherwise>
												</c:choose>
												
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
										<th><label for="">행사일</label>
										<td>
											<input class="cal-month" type="text" id="album_date" name="album_date" maxlength="10" value="${CONTENTS.ALBUM_DATE}">		
										</td>
									</tr>
																	
									<!-- dl, checkbox 예시 -->
									<tr>
										<th><label for="">썸네일</label></th>
										<td>
												<div class="form-group input-group">
													<input class="form-control" type="file" name="thumbFile" id="thumbFile">
													<div class="img_wrap"><p>이미지 미리보기</p><p><img id="imagePreview" /></p><p id="imagePreview_info"></p></div>
												</div>
												<c:if test="${fn:length(CONTENTS.STRFILENAME) > 0 }">
													<p><label class="checkbox-inline" for=""><input type="checkbox" name="delFile" id="delFile" value="Y">이전파일 삭제함</label></p>
													<p><img src="${CONTENTS.FILEPATH}thumbnail/${CONTENTS.STRFILENAME}" /></p>
													<input type="hidden" name="delFile_buidx" value="${CONTENTS.FILE_BUIDX}" />
												</c:if>
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
						<button type="button" id="btnDelete" class="btn btn-danger btn-lg" onclick="javascript:delete_contents('${CONTENTS.IDX}');return false;"><i class="fa fa-times"></i>삭제</button>	
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
<form id="form02" name="form02" method="POST" action="/admin/board/album_list.do">
 <input type="hidden" name="idx" id="idx" value="${CONTENTS.IDX}"/>
</form>
</html>