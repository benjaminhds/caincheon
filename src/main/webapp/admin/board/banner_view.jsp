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
	document.form01.action = '/admin/board/banner_list.do';
	//document.getElementById('idx').value=idx;
	document.form01.submit();
	return false;
}

function insert_contents() {
	if( $("#title").val() == "") { alert("제목을 입력해 주세요."); $("#title").focus(); return; }
	if( $(":input:radio[name=postType]:checked").val() == undefined) { alert("게시기간을 선택해 주세요."); $("#postType").focus(); return; }
	if( $(":input:radio[name=postType]:checked").val() == '2') {
		if( $("#post_date_from").val() == "") { alert("게시간을 입력해 주세요."); $("#post_date_from").focus(); return; }
		if( $("#post_date_to").val() == "") { alert("게시간을 입력해 주세요."); $("#post_date_to").focus(); return; }
	}
	
	if( $(":input:radio[name=link_type]:checked").val() == undefined) { alert("링크옵션을 선택해 주세요."); $("#link_type").focus(); return; }
	
	if( $(":input:radio[name=open_yn]:checked").val() == undefined) { alert("노출여부를 선택해 주세요."); $("#open_yn").focus(); return; }

	document.form01.action = '/admin/board/banner_insert.do';
	document.form01.submit();
	return false;
}

function modify_contents() {
	if( $("#title").val() == "") { alert("제목을 입력해 주세요."); $("#title").focus(); return; }
	if( $(":input:radio[name=postType]:checked").val() == undefined) { alert("게시기간을 선택해 주세요."); $("#postType").focus(); return; }
	if( $(":input:radio[name=postType]:checked").val() == '2') {
		if( $("#post_date_from").val() == "") { alert("게시간을 입력해 주세요."); $("#post_date_from").focus(); return; }
		if( $("#post_date_to").val() == "") { alert("게시간을 입력해 주세요."); $("#post_date_to").focus(); return; }
	}
	if( $(":input:radio[name=link_type]:checked").val() == undefined) { alert("링크옵션을 선택해 주세요."); $("#link_type").focus(); return; }
	
	if( $(":input:radio[name=open_yn]:checked").val() == undefined) { alert("노출여부를 선택해 주세요."); $("#open_yn").focus(); return; }

	document.form01.action = '/admin/board/banner_modify.do';
	document.form01.submit();
	return false;
}

/* function delete_contents() {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/board/banner_delete.do';
		document.form01.submit();
	}
	return false;
} */

function chgImgfile(fileObj) {
	var filePath = fileObj.value;
	var fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
	document.getElementById('imgfile').value = fileName;
	
	return false;
}

function chgPostType(radio, value) {
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0!
	var yyyy = today.getFullYear();

	if(dd<10) {
	    dd='0'+dd
	} 
	if(mm<10) {
	    mm='0'+mm
	} 

	today = yyyy+'-'+mm+'-'+dd;
	
	if(document.querySelector('input[name = "postType"]:checked').value == '1') {
		document.getElementById('post_date_from').value = today;
		document.getElementById('post_date_to').value = '9999-12-31';	
	   	$("#divFromTo").hide();
	} else if(document.querySelector('input[name = "postType"]:checked').value == '2') {
	   	document.getElementById("post_date_from").value = "";
	   	document.getElementById("post_date_to").value = "";
	   	$("#divFromTo").show();
	} else {
	   	$("#divFromTo").hide();
	}
	document.getElementById('post_type').value=value;
	return false;
}

function initControl() {
	if(document.querySelector('input[name = "postType"]:checked').value == '1') {
	   	$("#divFromTo").hide();
	} else if(document.querySelector('input[name = "postType"]:checked').value == '2') {
	   	$("#divFromTo").show();
	} else {
	   	$("#divFromTo").hide();
	}
	return false;
}

window.onload = function() {
	onLoadLeftMenu('main_02');
	initControl();
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
							<h3 class="page-header">배너 등록</h3>
						</c:when>
						<c:otherwise>
							<h3 class="page-header">배너 수정</h3>	
						</c:otherwise>
						</c:choose>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
  <form class="well" name="form01" action="/admin/board/banner_list.do" method="POST" enctype="multipart/form-data">
  	<input type="hidden" name="query_type" id="query_type" value="${_params.query_type }"/>
  	<input type="hidden" name="banner_no" id="banner_no" value="${banner.BANNER_NO}"/>
  	<input type="hidden" name="id" id="id" value="<%=admSessionMemId%>"/>
  	<input type="hidden" name="post_type" id="post_type" value="${banner.POST_TYPE}"/>
  	<div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2">
    			<label for="title">제목</label>
    		</div>
    		<div class="col-md-10">
    			<input type="text" class="form-control" id="title" placeholder="" name="title" value="${banner.TITLE}">		
    		</div>
    	</div>
    </div>
        
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2">
    			<label for="title">게시기간</label>
    		</div>
    		<div class="col-md-10">
	       		<label class="radio-inline"><input type="radio" name="postType" id="postType" value="1" onchange="chgPostType(this, '1')" <c:if test="${banner.POST_TYPE=='1' }" >checked="checked"</c:if>>기간제한없음</label>
	       		<label class="radio-inline"><input type="radio" name="postType" id="postType" value="2" onchange="chgPostType(this, '2')" <c:if test="${banner.POST_TYPE=='2' }" >checked="checked"</c:if>>기간설정</label>
    		</div>
    	</div>
    	<div id="divFromTo" class="row">
    		<div class="col-md-2">
    			<label for="title"></label>
    		</div>
    		<div class="col-md-4">
    			<input type="text" name="post_date_from" id="post_date_from" maxlength="16" value="${banner.POST_DATE_FROM}"/>
    		</div>
    		<div class="col-md-1">
    			<label for="title">~</label>
    		</div>
    		<div class="col-md-4">
    			<input type="text" name="post_date_to" id="post_date_to" maxlength="16" value="${banner.POST_DATE_TO}"/>
    		</div>
    	</div>
    </div>        
        
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2">
    			<label for="title">배너이미지</label>
    		</div>
    		<div class="col-md-4">
    			<c:choose>
    				<c:when test="${_params.query_type == 'insert'}">
    					<input type="input" id="imgfile" name="imgfile"  value="" readOnly>
    				</c:when>
    				<c:otherwise>
    					<input type="input" id="imgfile" name="imgfile"  value="${banner.IMG}" readOnly>
    					<label class="checkbox-inline" for=""><input type="checkbox" name="delFile" id="delFile" value="Y">이전파일 삭제함</label>
    				</c:otherwise>
    			</c:choose>
    					
    		</div>
    		<div class="col-md-6">
    			<input type="file" id="img" name="img" class="upload-hidden" onchange="chgImgfile(this)" >		
    		</div>
    	</div>
    </div>    
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2">
    			<label for="title">링크정보</label>
    		</div>
    		<div class="col-md-10">
    			<input type="text" class="form-control" id="url" placeholder="" name="url" value="${banner.URL}">		
    		</div>
    	</div>
    	<div class="row">
    		<div class="col-md-2">
    			<label for="title"></label>
    		</div>
    		<div class="col-md-10">
        		<label class="radio-inline"><input type="radio" name="link_type" id="link_type" value="1" <c:if test="${banner.LINK_TYPE=='1' }" >checked="checked"</c:if>>링크없음</label>
        		<label class="radio-inline"><input type="radio" name="link_type" id="link_type" value="2" <c:if test="${banner.LINK_TYPE=='2' }" >checked="checked"</c:if>>현재창</label>
        		<label class="radio-inline"><input type="radio" name="link_type" id="link_type" value="3" <c:if test="${banner.LINK_TYPE=='3' }" >checked="checked"</c:if>>새창</label>
        		<%-- <label class="radio-inline"><input type="radio" name="link_type" id="link_type" value="4" <c:if test="${banner.LINK_TYPE=='4' }" >checked="checked"</c:if>>준비중</label> --%>
    		</div>
    	</div>
    </div>  
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2">
    			<label for="title">노출여부</label>
    		</div>
    		<div class="col-md-10">
	       		<label class="radio-inline"><input type="radio" name="open_yn" id="open_yn" value="Y" <c:if test="${banner.OPEN_YN=='Y' }" >checked="checked"</c:if>>노출</label>
	       		<label class="radio-inline"><input type="radio" name="open_yn" id="open_yn" value="N" <c:if test="${banner.OPEN_YN=='N' }" >checked="checked"</c:if>>비노출</label>
    		</div>
    	</div>
    </div> 

	<div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2">
    			<label for="title">노출순서</label>
    		</div>
    		<div class="col-md-4">
    			<input type="text" class="form-control col-sm-1" id="open_seq" placeholder="" name="open_seq" value="${banner.OPEN_SEQ}">        				
    		</div>
    		<div class="col-md-6">
    			<label class="control-label" for="open_seq">(오름차순으로 정렬)</label>
    		</div>
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
	<!-- <button type="button" id="btnDelete" class="btn btn-default pull-right" onclick="javascript:delete_contents();return false;">삭제</button> -->	
</c:otherwise>
</c:choose>
</p>
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->
</body>

</html>