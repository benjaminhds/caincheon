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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
function uploadPhoto(fileObj) {
	//enctype="multipart/form-data"
	var filePath = fileObj.value;
	var fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
	var fileKind = fileName.split(".")[1];
	
	var form = $('form01')[0];
    var formData = new FormData(form);
    formData.append("image", $("#image")[0].files[0]);
    formData.append("uploadPath", "/online_history/");
    $("#btnModify").hide();
    $.ajax({
        url: '/admin/board/uploadOk.jsp',
                processData: false,
                contentType: false,
                data: formData,
                type: 'POST',
                error:function(XHR, textStatus, error) { alert("업로드 실패!!\n\n" + textStatus + "\n - " + error); } ,
    			fail :function() { alert("인터넷 연결 상태를 확인해주세요."); } ,
                success: function(result) {
                	if(result.status=='normal') {
                		alert("업로드 성공!!\n\n"+result.message);// + JSON.stringify(result));
                        $("#imgfile").val(result.uploaded);
                        $("#imgfileDiv").html("Uploaded File Name : " + result.uploaded);
                	} else {
                		alert("업로드 실패!!\n\n"+result.message);// + JSON.stringify(result));
                	}
                	$("#btnModify").show();
                }
        });
}

function viewList() {
	document.form01.reset();
	document.form01.action = '/admin/board/history_list.do';
	document.getElementById('active').value = "tab2";
	//document.getElementById('idx').value=idx;
	document.form01.submit();
	return false;
}

function insert_contents() {
	document.form01.action = '/admin/board/historys_insert.do';
	document.getElementById('category_code').value=document.getElementById('categoryCode').value;
	document.form01.submit();
	return false;
}

function modify_contents(history_no) {
	//alert('modify_contents');
	document.form01.action = '/admin/board/historys_modify.do';
	document.getElementById('category_code').value=document.getElementById('categoryCode').value;
	document.getElementById('history_no').value=history_no;
	document.form01.submit();
	return false;
}

function delete_contents(history_no) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/board/historys_delete.do';
		document.getElementById('history_no').value=history_no;
		document.form01.submit();
	}
	return false;
}

function setDisplay(radio, value) {
	if ( radio.checked == true ) {
		if(value == "Y") {
		//	alert('1');
			document.form01.displayType[1].checked = false;
		}
		else if(value=="N") {
		//	alert('0');
			document.form01.displayType[0].checked = false;
		}
		
		document.getElementById('displayYN').value=value;
	}
	return false;
}

function setCategory(radio, value) {
	if ( radio.checked == true ) {
		document.getElementById('category_code').value=value;
	}
	return false;
}

$(document).ready(function() {
	
});

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
	onLoadLeftMenu('info_01');
    //$("#eventDT").datepicker();
	//$("input.eventDT").datepicker().attr("maxlength", 10);
    //CKEDITOR.replace( 'contents' );
	CKEDITOR.replace( 'contents', {
    	filebrowserUploadUrl: '/admin/board/infoImageUpload.do'
  	});
   /* CKEDITOR.replace('myTextArea',{
	   maxlength: '100'
	});
    */
	onLoadLeftMenu('info_01');
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
		<h3 class="page-header">연혁등록관리 등록</h3>
	</c:when>
	<c:otherwise>
		<h3 class="page-header">연혁등록관리 수정</h3>	
	</c:otherwise>
</c:choose>
					</div>
				<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
  <form class="well" id="form01" name="form01" action="/admin/board/history_list.do" method="POST">
  	<input type="hidden" name="active" id="active" value="" />
  	<input type="hidden" name="category_code" id="category_code" value="" />
  	<input type="hidden" name="displayYN" id="displayYN" value="${historyContents.DISPLAYYN}"/>
  	<input type="hidden" name="history_no" id="history_no" value=""/>
  	
  	<div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">카테고리 선택</label>
    		</div>
    		<div class="col-md-10" >
    			<select name="categoryCode" id="categoryCode">
					<c:forEach items="${categoryList}" var="list">
						<c:choose>
							<c:when test="${list.CATEGORY_CODE eq historyContents.CATEGORY_CODE}" >
								<option value="${list.CATEGORY_CODE}" selected>${list.CATEGORY_NAME}</option>
							</c:when>
							<c:otherwise>
								<option value="${list.CATEGORY_CODE}">${list.CATEGORY_NAME}</option>
							</c:otherwise>	
						</c:choose>					
              		</c:forEach>
				</select>			
    		</div>
    	</div>
    </div>   
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">제목</label>
    		</div>
    		<div class="col-md-10" >
    			<input type="text" class="form-control" id="title" placeholder="" name="title" value="${historyContents.TITLE}">		
    		</div>
    	</div>
    </div>   
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2">
    			<label for="title">행사일자</label>
    		</div>
    		<div class="col-md-10">
    			<input type="text" id="eventDT" maxlength="10" name="eventDT" value="${historyContents.EVENTDT}">
    			<label for="">(YYYY-MM-DD)</label>		
    		</div>
    	</div>
    </div> 
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2">
    			<label for="title">이미지</label>
    		</div>
    		<div class="col-md-2">
    			<input type="hidden" id="imgfile" name="imgfile" placeholder="업로드된 파일명" value="${historyContents.IMAGE}" >
    			<label class="checkbox-inline" for=""><input type="checkbox" name="delImg" id="delImg" value="Y">이전파일 삭제함</label>
    			<c:if test="${historyContents.IMAGE ne ''}"><img src="${historyContents.FILE_PATH}thumbnail/${historyContents.IMAGE}"></c:if>
    		</div>
    		<div class="col-md-6">
    			<input type="file" id="image" name="image" class="upload-hidden" onchange="uploadPhoto(this)">
    			<div id="imgfileDiv" class="show" style="font-size:0.9em; weight:600"></div>
    		</div>
    	</div>
    </div> 
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">상세글(100자 이내로 입력)</label>
    		</div>
    		<div class="col-md-10" >
    			<textarea id="contents" name="contents" cols="80" rows="10" maxlength="100">${historyContents.CONTENTS}
        		</textarea>		
    		</div>
    	</div>
    </div>       
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">노출</label>
    		</div>
    		<div class="col-md-10" >
    			<c:choose>
		      		<c:when test="${historyContents.DISPLAYYN == 'Y'}">
		        		<label class="radio-inline"><input type="radio" name="displayType" id="displayType1" value="Y" onClick="setDisplay(this,'Y')" checked="checked">노출</label>
		        		<label class="radio-inline"><input type="radio" name="displayType" id="displayType2" value="N" onClick="setDisplay(this,'N')">비노출</label>
		        	</c:when>
		        	<c:otherwise>
		        		<label class="radio-inline"><input type="radio" name="displayType" id="displayType1" value="Y" onClick="setDisplay(this,'Y')">노출</label>
		        		<label class="radio-inline"><input type="radio" name="displayType" id="displayType2" value="N" onClick="setDisplay(this,'N')" checked="checked">비노출</label>
		        	</c:otherwise>	
		       	</c:choose>		
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
	<button type="button" id="btnModify" class="btn btn-default pull-right" onclick="javascript:modify_contents('${historyContents.HISTORY_NO}');return false;">수정</button>
	<!-- <button type="button" id="btnDelete" class="btn btn-default pull-right" onclick="javascript:delete_contents('${historyContents.HISTORY_NO}');return false;">삭제</button> -->
</c:otherwise>
</c:choose>
</p>
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->
</body>

</html>