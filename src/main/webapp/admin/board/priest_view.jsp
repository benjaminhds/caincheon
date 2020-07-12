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

	$("#form01").attr("action", "/admin/board/priest_list.do");
	$("#form01").submit();
	
	return false;
}

function CommFormCheck(gubun_iud) {
	switch(gubun_iud.charAt(0)) {
	case 'u':
	case 'U':
	case 'd':
	case 'D':
		if( $("#p_idx").val() == "") { 
			alert("사제코드를 입력해 주세요."); $("#p_idx").focus(); return false; 
		}
		break;
	}
	
	if( $("#p_idx").val() == "") { alert("사제코드를 입력해 주세요."); $("#p_idx").focus(); return false; }
	if( $(":input:radio[name=won_yn]:checked").val() == undefined) { alert("원로여부를 선택해 주세요."); $("#won_yn").focus(); return false; }
	if( $("#gubun").val() == "") { alert("소속을 입력해 주세요."); $("#gubun").focus(); return false; }
	if( $("#appellation").val() == "") { alert("호칭을 입력해 주세요."); $("#appellation").focus(); return false; }
	if( $("#name").val() == "") { alert("사제명을 입력해 주세요."); $("#name").focus(); return false; }
	return true;
}

function insert_contents() {
	if(!CommFormCheck("I")){
		return;
	}
	
	$("#form01").attr("action", "/admin/board/priest_insert.do");
	$("#form01").submit();
	
	return true;
}

function modify_contents() {
	if(!CommFormCheck("U")){
		return;
	}
	
	$("#form01").attr("action", "/admin/board/priest_modify.do");
	$("#form01").submit();
	
	return false;
}

function delete_contents() {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		$("#form01").attr("action", "/admin/board/priest_delete.do");
		$("#form01").submit();
	}
	
	return false;
}

function uploadImage(fileObj, imgName, idx) {
	var filePath = fileObj.value;
	var fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
	var fileKind = fileName.split(".")[1];
	
	document.getElementById('imageFile'+idx).value = fileName;
	
    var uploadPath = "father/" + ( idx == 1 ? "photo/": ( idx == 2 ? "card/": "" ) );
	var form = $('form01')[0];
    var formData = new FormData(form);
    formData.append("isKeepOriginalNm", "Y");
    formData.append("uploadType", fileKind);
    formData.append("uploadPath", uploadPath);
    formData.append("image", $("#image"+idx)[0].files[0]);
    
    _fileUploadByAjax('/admin/board/uploadOk.jsp', formData, 'image', 'imageFile'+idx, function(result){
    	//alert("===>"+JSON.stringify(result));
    	
    	//$("#imageFile3").val(result.uploaded);
    });/* admCommon.js */
    return;
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
					<h3 class="page-header">사제 등록</h3>
				</c:when>
				<c:otherwise>
					<h3 class="page-header">사제 수정</h3>	
				</c:otherwise>
				</c:choose>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
	
	<form class="well" name="form01" id="form01" action="/admin/board/priest_list.do"  method="POST">
	<input type="hidden" name="query_type" id="query_type" value="${_params.query_type}" />
	<input type="hidden" name="uploadType" id="uploadType" value=""/>
	<input type="hidden" name="departCodeCnt" id="departCodeCnt" value=""/>
	<input type="hidden" name="adm_id" id="adm_id" value="${admSessionMemId}"/>
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
									<th><label for="inputName">사제코드</label></th>
									<td>
					    			<input class="form-control" type="text" id="p_idx" placeholder="" name="p_idx" value="${admPriestContents.P_IDX}"/>		
    								</td>
    								<th><label for="inputName">원로</label></th>
									<td>
										<label class="radio-inline" for=""><input type="radio" name="won_yn" id="won_yn" value="Y" <c:if test="${admPriestContents.WON_YN=='Y' }" >checked="checked"</c:if>>Y</label>
										<label class="radio-inline" for=""><input type="radio" name="won_yn" id="won_yn" value="N" <c:if test="${admPriestContents.WON_YN=='N' }" >checked="checked"</c:if>>N</label>
    								</td>
								</tr>	
								<tr>
									<th><label for="inputName">소속</label></th>
									<td>
						    			<select name="gubun" id="gubun">
						    				<option value="">선택</option>
						    				<c:forEach items="${admCodeList1}" var="list">
						    				<option value="${list.CODE_INST }" <c:if test="${admPriestContents.GUBUN==list.CODE_INST }" >selected</c:if> >${list.NAME }</option>
						    				</c:forEach>
										</select>											
									</td>
									<th><label for="inputBaptismal">호칭</label></th>
									<td>
										<select name="appellation" id="appellation">
											<option value="">선택</option>
											<c:forEach items="${admCodeList2}" var="list">
						    				<option value="${list.CODE_INST }" <c:if test="${admPriestContents.APPELLATION==list.CODE_INST }" >selected</c:if> >${list.NAME }</option>
						    				</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th><label for="inputName">사제명</label></th>
									<td>
									<input class="form-control" type="text" id="name" name="name" value="${admPriestContents.NAME}"/>
									</td>
									<th><label for="inputName">세례명</label></th>
									<td>
									<input class="form-control" type="text" id="christian_name" name="christian_name" value="${admPriestContents.CHRISTIAN_NAME}"/>
									</td>
								</tr>
								<tr>
									<th><label for="inputName">축일</label></th>
									<td>
									<input class="form-control form-control-short w-100" type="text" id="new_birthday" name="new_birthday" placeholder="월-일" maxlength="5" value="${admPriestContents.NEW_BIRTHDAY}"/>		
    								(입력예 : 01-01)
									</td>
									<th><label for="inputName">서품일</label></th>
									<td>
									<input class="form-control form-control-short w-100" type="text" id="p_birthday" name="p_birthday" maxlength="10" value="${admPriestContents.P_BIRTHDAY}"/>
    								(입력예 : 2017-01-01)
									</td>
								</tr>
								<tr>
									<th><label for="inputName">현임지</label></th>
									<td colspan="3">
										<%@ include file="/admin/board/priest_view_assign.jsp" %>
									</td>
								</tr>
								<tr>
									<th><label for="inputName">이메일</label></th>
									<td>
					    			<input class="input" type="text" id="email" name="email" value="${admPriestContents.EMAIL}"/>	
    								</td>
    								<th><label for="inputName">홈페이지/SNS</label></th>
									<td>
					    			<input class="input" type="text" id="homepage" name="homepage" value="${admPriestContents.HOMEPAGE}"/>
    								</td>
								</tr>
								<tr>
									<th><label for="inputName">성구</label></th>
									<td colspan="3">
					    			<input class="form-control" type="text" id="phrase" name="phrase" value="${fn:escapeXml(admPriestContents.PHRASE)}"/>		
    								</td>
								</tr>
								<tr>
									<th><label for="inputName">이미지(사진)</label></th>
									<td colspan="3">
						    		<div class="col-md-6">
						    			<input type="file" id="image1" name="image1" class="upload-hidden" onchange="uploadImage(this,'image1','1')">		
						    			<c:choose>
						    				<c:when test="${_params.query_type == 'insert'}">
						    					<input class="form-control form-control-short w-200" type="input" id="imageFile1" name="imageFile1" value="" readOnly>
						    				</c:when>
						    				<c:otherwise>
						    					<input class="form-control form-control-short w-200" type="input" id="imageFile1" name="imageFile1" value="${admPriestContents.IMAGE}" readOnly>
						    					<label class="checkbox-inline" for=""><input type="checkbox" name="delImage1" id="delImage1" value="Y">이전파일 삭제함</label>
						    				</c:otherwise>
						    			</c:choose>
						    		</div>
    								</td>
    							</tr>
    							<tr>
    								<th><label for="inputName">이미지(상본)</label></th>
									<td colspan="3">
						    		<div class="col-md-6">
						    			<input type="file" id="image2" name="image2" class="upload-hidden" onchange="uploadImage(this,'image2','2')">		
						    			<c:choose>
						    				<c:when test="${_params.query_type == 'insert'}">
						    					<input class="form-control form-control-short w-200" type="input" id="imageFile2" name="imageFile2" value="" readOnly>
						    				</c:when>
						    				<c:otherwise>
						    					<input class="form-control form-control-short w-200" type="input" id="imageFile2" name="imageFile2" value="${admPriestContents.IMAGE2}" readOnly>
						    					<label class="checkbox-inline" for=""><input type="checkbox" name="delImage2" id="delImage2" value="Y">이전파일 삭제함</label>
						    				</c:otherwise>
						    			</c:choose>
						    		</div>
    								</td>
								</tr>
								<tr>
									<th><label for="inputName">메인노출이미지</label></th>
									<td colspan="3">
						    		<div class="col-md-6">
						    			<input type="file" id="image3" name="image3" class="upload-hidden" onchange="uploadImage(this,'image3','3')">		
						    			<c:choose>
						    				<c:when test="${_params.query_type == 'insert'}">
						    					<input class="form-control form-control-short w-200" type="input" id="imageFile3" name="imageFile3" value="" readOnly>
						    				</c:when>
						    				<c:otherwise>
						    					<input class="form-control form-control-short w-200" type="input" id="imageFile3" name="imageFile3" value="${admPriestContents.IMAGE3}" readOnly>
						    					<label class="checkbox-inline" for=""><input type="checkbox" name="delImage3" id="delImage3" value="Y">이전파일 삭제함</label>
						    				</c:otherwise>
						    			</c:choose>
						    		</div>
    								</td>
    							</tr>
    							<tr>
    								<th><label for="inputName">기본정렬번호</label></th>
									<td colspan="3">
					    			<input class="input" type="text" id="onum" name="onum" value="${admPriestContents.ONUM}"/>		
    								</td>
								</tr>
								</tbody>
							</table>
						</div>
					</div>
	</form>
	<p>
		<div><button type="button" id="btnList"   class="btn btn-default pull-right" onclick="javascript: viewList();">목록</button></div>
	<c:choose>
	<c:when test="${_params.query_type == 'insert'}">
		<div><button type="button" id="btnInsert" class="btn btn-default pull-right" onclick="javascript: insert_contents();">등록</button></div>
	</c:when>
	<c:otherwise>
		<div><button type="button" id="btnModify" class="btn btn-default pull-right" onclick="javascript: modify_contents();">수정</button></div>
		<div><button type="button" id="btnCancel" class="btn btn-default pull-right" onclick="javascript: history.back();">취소</button></div>
		<div><button type="button" id="btnDelete" class="btn btn-default pull-right" onclick="javascript: delete_contents();">삭제</button></div>
	</c:otherwise>
	</c:choose>
	</p>
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->
<script type="text/javascript">


window.onload = function() {
	onLoadLeftMenu('info_06');
}

$(document).ready(function() {
	//한글설정
    $.datepicker.regional['ko'] = {
        closeText: '닫기',
        prevText: '이전달',
        nextText: '다음달',
        currentText: '오늘',
        monthNames: ['1월','2월','3월','4월','5월','6월',
        '7월','8월','9월','10월','11월','12월'],
        monthNamesShort: ['1월','2월','3월','4월','5월','6월',
        '7월','8월','9월','10월','11월','12월'],
        dayNames: ['일','월','화','수','목','금','토'],
        dayNamesShort: ['일','월','화','수','목','금','토'],
        dayNamesMin: ['일','월','화','수','목','금','토'],
        weekHeader: 'Wk',
        dateFormat: 'yy-mm-dd',
        firstDay: 0,
        isRTL: false,
        duration:200,
        showAnim:'show',
        showMonthAfterYear: true,
        yearSuffix: ''
    };
    $.datepicker.setDefaults($.datepicker.regional['ko']);
    
    $(".cal-month").datepicker({
    	changeMonth: true,
        changeYear: true,
        dateFormat:'yy-mm-dd',
        showButtonPanel: true
    });   
});

</script>
</body>

</html>