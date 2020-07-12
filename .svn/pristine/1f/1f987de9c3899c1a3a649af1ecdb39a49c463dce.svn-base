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
	document.form02.action = '/admin/gyogu/gyoguMov_list.do';
	//document.getElementById('idx').value=idx;
	document.form02.submit();
	return false;
}

function check_contents() {
	if( $("#c_idx").val() == "") { alert("분류를 선택해 주세요."); $("#c_idx").focus(); return false; }
	if( $("#title").val() == "") { alert("제목을 입력해 주세요."); $("#title").focus(); return false; }
	if( $(":input:radio[name=is_view]:checked").val() == undefined) { alert("노출여부를 선택해 주세요."); $("#is_view").focus(); return false; }
	
	var uploadFile = document.getElementById('thumbFile').value;
	if(uploadFile != "") {
		var fileExt = uploadFile.substring(uploadFile.lastIndexOf(".") + 1);
		var reg = /gif|jpg|jpeg|png/i;
		if(reg.test(fileExt) == false) {
			alert("첨부파일은 gif,jpg,png로 된 이미지만 가능합니다.");
			return false;
		}
	}
	
	return true;
}

function insert_contents() {
	if(!check_contents()) {
		return;
	}
	
	document.form01.action = '/admin/gyogu/gyoguMov_insert.do';
	document.form01.submit();
	return false;
}

function modify_contents() {
	if(!check_contents()) {
		return;
	}
	document.form01.action = '/admin/gyogu/gyoguMov_modify.do';
	document.form01.submit();
	return false;
}

function delete_contents() {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form02.action = '/admin/gyogu/gyoguMov_delete.do';
		document.form02.submit();
	}
	return false;
}


</script>
<script src="http://malsup.github.com/jquery.form.js"></script> 
<!--script src="/admin/_common/ckeditor/ckeditor.js"></script-->
<script src="http://cdn.ckeditor.com/4.7.0/standard-all/ckeditor.js"></script>
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
						<h3 class="page-header">교구영상 등록</h3>
					</c:when>
					<c:otherwise>
						<h3 class="page-header">교구영상 수정</h3>	
					</c:otherwise>
					</c:choose>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
<form id="form01" name="form01" enctype="multipart/form-data" method="POST" action="/admin/gyogu/gyoguMov_list.do">
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
									<%-- <tr>
										<th><label for="">작성자 </label></th>
										<td>
											<label for=""><%=admSessionMemNm %> </label>
										</td>
									</tr> --%>
									<tr>
										<th><label for="">구분</label></th>
										<td>
											<select class="form-control" name="c_idx" id="c_idx">
												<option value="">선택</option>
												<option value="11" <c:if test="${CONTENTS.C_IDX=='11' }" >selected</c:if>>소식</option>
												<option value="12" <c:if test="${CONTENTS.C_IDX=='12' }" >selected</c:if>>강좌</option>
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
										<th><label for="">행사일</label></th>
										<td>
											<div class="row">
												<div class="col-xs-3">
											<input class="form-control" type="text" maxlength="10" name="event_date" id="event_date" value="${CONTENTS.EVENT_DATE }">
												</div>
											</div>
										</td>
									</tr>
<%-- 									<tr>
										<th><label for="">공지글 </label></th>
										<td>
											<label class="checkbox-inline" for=""><input type="checkbox" name="is_notice" id="is_notice" value="Y" <c:if test="${CONTENTS.IS_NOTICE=='Y' }" >checked="checked"</c:if>> 게시글을 최상단에 고정 노출함</label>
										</td>
									</tr> --%>
									<tr>
										<th><label for="">썸네일</label></th>
										<td>
											<div class="row">
												<div class="form-group input-group">
													<input class="form-control" type="file" name="thumbFile" id="thumbFile">
												</div>
												<div class="img_wrap"><p>이미지 미리보기</p><p><img id="imagePreview1" /></p><p id="imagePreview1_info"></p></div>
												<c:if test="${fn:length(CONTENTS.STRFILENAME1) > 0 }">
													<img alt="" src="${CONTENTS.FILEPATH1}thumbnail/${CONTENTS.STRFILENAME1}" width="120px">
													<label class="checkbox-inline" for=""><input type="checkbox" name="delFile1" id="delFile1" value="${CONTENTS.STRFILENAME1}">이전파일 삭제함</label>
												</c:if>
											</div>
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
<form id="form02" name="form02" method="POST" action="/admin/gyogu/gyoguMov_list.do">
 <input type="hidden" name="bl_idx" id="bl_idx" value="${CONTENTS.BL_IDX}"/>
</form>
<script type="text/javascript">
window.onload = function() {

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
	
	onLoadLeftMenu('board_08');
	
	$("#event_date").datepicker();
	$("input.event_date").datepicker().attr("maxlength", 10);
	
 	CKEDITOR.replace( 'contents', {
		extraPlugins: 'embed,autoembed,image2',
		height: 350,
		
		  // Load the default contents.css file plus customizations for this sample.
		contentsCss: [ CKEDITOR.basePath + 'contents.css', 'http://sdk.ckeditor.com/samples/assets/css/widgetstyles.css' ],
		  // Setup content provider. See http://docs.ckeditor.com/#!/guide/dev_media_embed
		embed_provider: '//ckeditor.iframe.ly/api/oembed?url={url}&callback={callback}',
		
		image2_alignClasses: [ 'image-align-left', 'image-align-center', 'image-align-right' ],
		image2_disableResizer: true,
    	filebrowserUploadUrl: '/admin/ckeditor/fileUploadInContent08.do'
  	});
	// thumbnail
	$("#thumbFile").on("change", {maxWidth:500, maxHeight:400, imgPreviewId:"imagePreview1"},  handleImageFileSelect);
  	
}
</script>
</html>