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
	
	//alert("filePath=" + filePath);
	//alert("fileName=" + fileName);
	//alert("fileKind=" + fileKind);
	
	var form = $('form01')[0];
    var formData = new FormData(form);
    if($("#image")[0].files[0]=="") {
    	return;
    }
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
                    alert("파일 업로드 성공!!");// + JSON.stringify(result));
                    $("#btnModify").show();
                    $("#imgfile").val(result.uploaded);
                    $("#imgfileDiv").html("Uploaded File Name : " + result.uploaded);
                }
        });
}

function viewList() {
	var vfm = document.form01;
	vfm.reset();
	vfm.action = '/admin/board/history_list.do';
	vfm.submit();
	return false;
}

function insert_contents() {
	var vfm = document.form01;
	vfm.action = '/admin/board/historym_insert.do';
	if(vfm.category_name.value=="") {
		alert("카테고리명을 입력해주세요.");
		category_name.focus();
		return false;
	}
	if($("#imgfileDiv").html()=="") {
		alert("파일을 업로드해야 합니다.");
		return false;
	}
	if(vfm.displayNo.value=="") {
		alert("노출 순서를 입력해주세요.");
		displayNo.focus();
		return false;
	}
	vfm.submit();
	return false;
}

function modify_contents() {
	var vfm = document.form01;
	vfm.action = '/admin/board/historym_modify.do';
	vfm.submit();
	return false;
}

function delete_contents() {
	var vfm = document.form01;
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		vfm.action = '/admin/board/historym_delete.do';
		vfm.submit();
	}
	return false;
}

function setDisplay(radio, value) {
	var vfm = document.form01;
	if ( radio.checked == true ) {
		if(value == "Y") {
		//	alert('1');
			vfm.displayType[1].checked = false;
		}
		else if(value=="N") {
		//	alert('0');
			vfm.displayType[0].checked = false;
		}
		
		document.getElementById('displayYN').value=value;
	}
	return false;
}

window.onload = function() {
	onLoadLeftMenu('info_01');
//	jQuery("#departCode").val("${departdcContents.depart_code}").attr("selected","selected");
//	document.getElementById('depart_code').value = "${departdcContents.depart_code}";
	//alert("loading completed");
	//CKEDITOR.replace( 'contents' );
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
		<h3 class="page-header">역사관 카테고리 등록</h3>
	</c:when>
	<c:otherwise>
		<h3 class="page-header">>역사관 카테고리 수정</h3>	
	</c:otherwise>
</c:choose>
					</div>
				<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
  <form class="well" id="form01" name="form01" action="/admin/board/history_list.do" METHOD="POST">
  	<input type="hidden" name="displayYN" id="displayYN" value="${historyContents.DISPLAYYN}"/>
  	
  	<div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">고유번호</label>
    		</div>
    		<div class="col-md-10" >
    			<input type="text" class="form-control" id="category_code" placeholder="자동채번" name="category_code" value="${historyContents.CATEGORY_CODE}" readOnly >		
    		</div>
    	</div>
    </div>   
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">역사관 카테고리명</label>
    		</div>
    		<div class="col-md-10" >
    			<input type="text" class="form-control" id="category_name" placeholder="" name="category_name" value="${historyContents.CATEGORY_NAME}">		
    		</div>
    	</div>
    </div>   
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2">
    			<label for="title">이미지</label>
    		</div>
    		<div class="col-md-6">
	    		<input type="hidden" id="imgfile" name="imgfile" placeholder="업로드된 파일명"  value="${historyContents.IMAGE}">
	    		<c:choose>
	   				<c:when test="${_params.query_type == 'insert'}">
	   				</c:when>
	   				<c:otherwise>
	   					<label class="checkbox-inline" for=""><input type="checkbox" name="delImg" id="delImg" value="Y">이전파일 삭제함</label>
	   					<c:if test="${historyContents.IMAGE ne ''}"><img src="${historyContents.FILE_PATH}thumbnail/${historyContents.IMAGE}"></c:if>
	   				</c:otherwise>
	   			</c:choose>
    			<input type="file" id="image" name="image" class="upload-hidden" onchange="uploadPhoto(this)"><font color="gray" style="font-size:0.8em">파일 선택 즉시 업로드 됩니다.</font></image>
    			<div id="imgfileDiv" class="show" style="font-size:0.9em; weight:600"></div>		
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
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">노출순서</label>
    		</div>
    		<div class="col-md-10" >
    			<input type="number" class="form-control" id="displayNo" placeholder="숫자만 입력합니다." name="displayNo" value="${historyContents.DISPLAYNO}">		
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
	<!-- <button type="button" id="btnDelete" class="btn btn-default pull-right" onclick="javascript:delete_contents();return false;">삭제</button>-->
</c:otherwise>
</c:choose>
</p>
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->
</body>

</html>