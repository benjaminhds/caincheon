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
	document.form01.action = '/admin/board/bef_priest_list.do';
	document.form01.submit();
	return false;
}

function insert_contents() {
	document.form01.action = '/admin/board/bef_priest_insert.do';
	//document.getElementById('brial_place_name').value=form01.brial_place.options[form01.brial_place.selectedIndex].text;
	document.form01.submit();
	return false;
}

function modify_contents(bp_idx) {
	//alert('modify_contents');
	document.form01.action = '/admin/board/bef_priest_modify.do';
	document.getElementById('bp_idx').value=bp_idx;
	document.form01.submit();
	return false;
}

function delete_contents(bp_idx) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/board/bef_priest_delete.do';
		document.getElementById('bp_idx').value=bp_idx;
		document.form01.submit();
	}
	return false;
}

function uploadPhoto(fileObj) {
	//enctype="multipart/form-data"
	var filePath = fileObj.value;
	var fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
	var fileKind = fileName.substring(fileName.lastIndexOf(".")+1); //fileName.split(".")[1];
	
	var form = $('form01')[0];
    var formData = new FormData(form);
    formData.append("isKeepOriginalNm", "Y");
    formData.append("uploadPath", "de_father/");
    formData.append("photo", $("#image")[0].files[0]);
    _fileUploadByAjax('/admin/board/uploadOk.jsp', formData, 'image', 'imgfile', null);/* admCommon.js */
}

function uploadPhoto2(fileObj) {
	//enctype="multipart/form-data"
	var filePath = fileObj.value;
	var fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
	var fileKind = fileName.substring(fileName.lastIndexOf(".")+1); //fileName.split(".")[1];
	
	var form = $('form01')[0];
    var formData = new FormData(form);
    formData.append("photo", $("#image2")[0].files[0]);
    $.ajax({
        url: '/admin/board/uploadOk.jsp',
                processData: false,
                contentType: false,
                data: formData,
                type: 'POST',
                success: function(result){
                    alert("업로드 성공!!");
                    document.getElementById('imgfile2').value = fileName;
                }
        });
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
	<h3 class="page-header">선종사제 등록</h3>
</c:when>
<c:otherwise>
	<h3 class="page-header">선종사제 수정</h3>	
</c:otherwise>
</c:choose>
					</div>
				<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
  <form class="well" name="form01" action="/admin/board/bef_priest_list.do" method="POST">
  	<input type="hidden" name="brial_place_name" id="brial_place_name" value=""/> 	
  	<input type="hidden" name="bp_idx" id="bp_idx" value="" />
  	<input type="hidden" name="pageNo" id="pageNo" value="${_params.pageNo }" />
  	
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2">
    			<label for="inputName">사제명</label>
    		</div>
    		<div class="col-md-4">
    			<input class="form-control" type="text"  id="name" placeholder="" name="name" value="${befPriestContents.NAME}">		
    		</div>
    		<div class="col-md-2">
    			<label for="inputBaptismal">세례명</label>
    		</div>
    		<div class="col-md-4" >
    			<input class="form-control" type="text"  id="christian_name" placeholder="" name="christian_name" value="${befPriestContents.CHRISTIAN_NAME}">		
    		</div>    	
    	</div>
    </div>
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2">
    			<label for="inputName">서품일</label>
    		</div>
    		<div class="col-md-4">
    			<input class="cal-month" type="text" id="ordination" name="ordination" value="${befPriestContents.ORDINATION}">		
    		</div>
    		<div class="col-md-2">
    			<label for="inputBaptismal">선종일</label>
    		</div>
    		<div class="col-md-4" >
    			<input class="cal-month" type="text"  id="dead" placeholder="" name="dead" value="${befPriestContents.DEAD}">		
    		</div>    	
    	</div>
    </div>    
        
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="inputName">묘소</label>
    		</div>
    		<div class="col-md-10" >
    			<select class="form-control" id="brial_place" name="brial_place">
			        <c:choose>
						<c:when test="${fn:length(brialPlaceList) > 0}">
						<c:forEach items="${brialPlaceList}" var="list">
							<option>${list.BRIAL_PLACE_NAME}</option>                       		
                        </c:forEach>
						</c:when>
					</c:choose>		
      			</select>		
    		</div>    		
    	</div>
    </div>   
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2">
    			<label for="inputName">생일</label>
    		</div>
    		<div class="col-md-4">
    			<input class="cal-month" type="text" id="birthday" name="birthday" value="${befPriestContents.BIRTHDAY}">		
    		</div>    		  	
    	</div>
    </div>  
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="inputName">이미지(사진)</label>
    		</div>
    		
    		<c:choose>
   				<c:when test="${_params.query_type == 'insert'}">
   					<input type="input" id="imgfile" name="imgfile"  value="" readOnly>
   				</c:when>
   				<c:otherwise>
   					<input type="input" id="imgfile" name="imgfile"  value="${befPriestContents.IMAGE}" readOnly>
   					<label class="checkbox-inline" for=""><input type="checkbox" name="delImg" id="delImg" value="Y">이전파일 삭제함</label>
   				</c:otherwise>
   			</c:choose>   
   			    		
    		<div class="col-md-6" >
    			<input type="file" id="image" name="image" aria-describedby="fileHelp" onchange="uploadPhoto(this)">    			
    		</div>    		 		   	
    	</div>
    </div>   
    <!-- 
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="inputName">이미지(상본)</label>
    		</div>
    		
    		<c:choose>
   				<c:when test="${_params.query_type == 'insert'}">
   					<input type="input" id="imgfile2" name="imgfile2"  value="" readOnly>
   				</c:when>
   				<c:otherwise>
   					<input type="input" id="imgfile2" name="imgfile2"  value="${befPriestContents.IMAGE2}" readOnly>
   					<label class="checkbox-inline" for=""><input type="checkbox" name="delImg2" id="delImg2" value="Y">이전파일 삭제함</label>
   				</c:otherwise>
   			</c:choose> 
   			
    		<div class="col-md-6" >
    			<input type="file" id="image2" name="image2" aria-describedby="fileHelp" onchange="uploadPhoto2(this)">    			
    		</div>    		 		   	
    	</div>
    </div>           
         -->
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="inputName">성구</label>
    		</div>
    		<div class="col-md-10" >
    			<input type="text" class="form-control" id="phrase" placeholder="" name="phrase" value="${befPriestContents.PHRASE}">		
    		</div>
    	</div>
    </div>
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2">
    			<label for="inputName">약력</label>
    		</div>
    		<div class="col-md-10">
    			<textarea id="profile" name="profile" cols="80" rows="10">${befPriestContents.PROFILE}</textarea>		
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
	<button type="button" id="btnModify" class="btn btn-default pull-right" onclick="javascript:modify_contents('${befPriestContents.BP_IDX}');return false;">수정</button>
	<button type="button" id="btnDelete" class="btn btn-default pull-right" onclick="javascript:viewList();return false;">취소</button>	
</c:otherwise>
</c:choose>
</p>
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->
<script>

window.onload = function() {
	onLoadLeftMenu('info_07');
	//alert("loading completed");
	//CKEDITOR.replace( 'contents' );
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