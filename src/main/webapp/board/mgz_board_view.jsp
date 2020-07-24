<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/admin/_common/inc/head.jsp" %><%
	if(admSessionMemId==null||admSessionMemId.equals("")) {                // id가 Null 이거나 없을 경우
	response.sendRedirect("/admin/login.jsp");    // 로그인 페이지로 리다이렉트 한다.
}

//
// 올해 년도와 날자 :: 파일 업로드 경로를 설정하기 위함.
// - 경로는 upload/magazine/201702 와 같이 생성하고 월단위로 매거진을 관리한다.
// 
String YYYYMM = kr.caincheon.church.common.utils.UtilDate.getDateFormat("yyyyMM");
request.setAttribute("TMP_YYYYMM", YYYYMM);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script type="text/javascript">
function chgPubdate() {
	///alert('chage pub date');
	//alert(document.getElementById('pubdate').value);
	
	var pubdate = document.getElementById('pubdate').value;
	
	var formData = new FormData($('form02')[0]);
	formData.append("pubdate", pubdate);
	
	_requestByAjax('/admin/board/mgz_title.do', formData, function(status, responseData){
				console.log("status="+status);
				console.log("responseData="+JSON.stringify(responseData));
				document.getElementById('description').value = responseData.result;
			});
}

function uploadPdf(fileObj) {
	//enctype="multipart/form-data"
	var filePath = fileObj.value;
	var fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
	var fileKind = fileName.substring(fileName.lastIndexOf(".")+1); //fileName.split(".")[1];
	if(fileKind != "pdf") {
		alert("pdf 파일을 선택해주세요.");
		return;
	}
	document.getElementById('pdffile').value = fileName;
	
	var form = $('form01')[0];
    var formData = new FormData(form);
    formData.append("isKeepOriginalNm", "Y");
    formData.append("uploadType", "pdf");
    formData.append("uploadPath", "magazine/${TMP_YYYYMM}");
    formData.append("pdf", $("#pdf")[0].files[0]);
    
    _fileUploadByAjax('/admin/board/uploadOk.jsp', formData, 'pdf', 'pdffile', null);/* admCommon.js */
    return;
}

function uploadMp3(fileObj) {
	//enctype="multipart/form-data"
	var filePath = fileObj.value;
	var fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
	var fileKind = fileName.split(".")[1];
	if(fileKind != "mp3") {
		alert("mp3 파일을 선택해주세요.");
		return;
	}
	document.getElementById('mp3file').value = fileName;
	
	var form = $('form01')[0];
    var formData = new FormData(form);
    formData.append("isKeepOriginalNm", "Y");
    formData.append("uploadType", "mp3");
    formData.append("uploadPath", "magazine/${TMP_YYYYMM}");
    formData.append("mp3", $("#mp3")[0].files[0]);
    
    _fileUploadByAjax('/admin/board/uploadOk.jsp', formData, 'mp3', 'mp3file', null);/* admCommon.js */
    return;
}

function viewList() {
	document.form01.action = '/admin/board/mgz_list.do';
	//document.getElementById('idx').value=idx;
	document.form01.submit();
	return false;
}

function insert_contents() {
	if( $("#pubdate").val() == "") { alert("발행일을 입력해 주세요."); $("#pubdate").focus(); return; }
	document.form01.action = '/admin/board/mgz_insert.do';
	document.form01.submit();
	return false;
}

function modify_contents(m_idx) {
	if( $("#pubdate").val() == "") { alert("발행일을 입력해 주세요."); $("#pubdate").focus(); return; }
	
	document.form01.action = '/admin/board/mgz_modify.do';
	document.getElementById('m_idx').value=m_idx;
	//document.getElementById('idx').value=idx;
	document.form01.submit();
	return false;
}

function delete_contents(m_idx) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/board/mgz_delete.do';
		document.getElementById('m_idx').value=m_idx;
		document.form01.submit();
	}
	return false;
}
</script>
<!-- <script src="/admin/_common/ckeditor/ckeditor.js"></script>-->

<script type="text/javascript">
/* 
$(document).ready(function() {
    $('.cal-month').datetimepicker({
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
}); */
</script>
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
			<h3 class="page-header">간행물 등록</h3>
		</c:when>
		<c:otherwise>
			<h3 class="page-header">간행물 수정</h3>	
		</c:otherwise>
	</c:choose>
						</div>
				<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
  
  <form class="well" name="form02" action="/admin/board/mgz_title.do" method="POST"></form>
  <form class="well" name="form01" action="/admin/board/mgz_list.do" method="POST">
  	<input type="hidden" name="m_idx" id="m_idx" value=""/>
  	<input type="hidden" name="uploadType" id="uploadType" value=""/>
  	<input type="hidden" name="testField" id="testField" value="test"/>
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
								<td colspan="3"><input type="text" class="form-control" id="description" placeholder="" name="description" value="${mgzContents.DESCRIPTION}" readOnly/></td>
								</tr>
								<tr>
								<td><label for="inputName">발행일</label></td>
								<td><input class="cal-month" type="text" id="pubdate" name="pubdate" value="${mgzContents.PUBDATE}" onchange="chgPubdate()"/></td>
								<td><label for="inputBaptismal">발행호</label></td>
								<td>
								<c:choose>
					<c:when test="${_params.query_type == 'insert'}">
    					<input class="form-control" type="text"  id="mgz_no" placeholder="" name="mgz_no" value="${max_no}" readOnly/>
    				</c:when>
    				<c:otherwise>
    					<input class="form-control" type="text"  id="mgz_no" placeholder="" name="mgz_no" value="${mgzContents.NO}" readOnly/>
    				</c:otherwise>
    			</c:choose>
								</td>
								</tr>
								<tr>
								<td><label for="title">PDF업로드</label></td>
								<td colspan="3">
    			<c:choose>
    				<c:when test="${_params.query_type == 'insert'}">
    					<input type="input" id="pdffile" name="pdffile"  value="" readOnly style="width: 450px;"><span id="pdffile_span"></span>
    				</c:when>
    				<c:otherwise>
    					<input type="input" id="pdffile" name="pdffile"  value="${mgzContents.PDF}" readOnly>
    					<label class="checkbox-inline" for=""><input type="checkbox" name="delPdf" id="delPdf" value="Y">이전파일 삭제함</label>
    				</c:otherwise>
    			</c:choose>    		
    		<div class="col-md-6">
    			<input type="file" id="pdf" name="pdf" class="upload-hidden" onchange="uploadPdf(this)" style="width: 450px;">		
    		</div>								
								</td>
								</tr>
								<tr>
								<td><label for="title">소리주보 업로드(mp3)</label></td>
								<td colspan="3">
    			<c:choose>
    				<c:when test="${_params.query_type == 'insert'}">
    					<input type="input" id="mp3file" name="mp3file" value="" readOnly style="width: 450px;"><span id="mp3file_span"></span>
    				</c:when>
    				<c:otherwise>
    					<input type="input" id="mp3file" name="mp3file" value="${mgzContents.MP3}" readOnly>
    				</c:otherwise>
    			</c:choose>
    		<div class="col-md-6">
    			<input type="file" id="mp3" name="mp3" class="upload-hidden" onchange="uploadMp3(this)" style="width: 450px;">		
    		</div>
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
	<button type="button" id="btnModify" class="btn btn-default pull-right" onclick="javascript:modify_contents('${mgzContents.M_IDX}');return false;">수정</button>
	<button type="button" id="btnDelete" class="btn btn-default pull-right" onclick="javascript:delete_contents('${mgzContents.M_IDX}');return false;">삭제</button>
</c:otherwise>
</c:choose>
</p>
<p id="test1"></p>

    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->
<script type="text/javascript">
window.onload = function() {
	onLoadLeftMenu('etc_04');
	//alert("loading completed");
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

$(function() {
    $("#pubdate").datepicker();
});
</script>
</body>

</html>