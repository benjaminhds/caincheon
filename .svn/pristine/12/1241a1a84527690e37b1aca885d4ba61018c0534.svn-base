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
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script type="text/javascript">
function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 도로명 조합형 주소 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
               extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraRoadAddr !== ''){
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }
            // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
            if(fullRoadAddr !== ''){
                fullRoadAddr += extraRoadAddr;
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postcode').value = data.zonecode; //5자리 새우편번호 사용
            document.getElementById('roadAddress').value = fullRoadAddr;
            document.getElementById('jibunAddress').value = data.jibunAddress;

        }
    }).open();
}

function uploadPhoto(fileObj) {
	//enctype="multipart/form-data"
	var filePath = fileObj.value;
	var fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
	var fileKind = fileName.split(".")[1];
	
	//
    var formData = new FormData($('form01')[0]);
    formData.append("uploadType", "image");
    formData.append("uploadPath", "department");
    formData.append("image", $("#image")[0].files[0]);
    
    _fileUploadByAjax('/admin/board/uploadOk.jsp', formData, 'image', 'imgfile', null);/* admCommon.js */
}

function viewList() {
	document.form01.action = '/admin/board/departdc_list.do';
	//document.getElementById('idx').value=idx;
	document.form01.submit();
	return false;
}

function insert_contents() {
	document.form01.action = '/admin/board/departdc_insert.do';
	document.form01.submit();
	return false;
}

function modify_contents() {
	//alert('modify_contents');
	document.form01.action = '/admin/board/departdc_modify.do';
	//document.getElementById('idx').value=idx;
	document.form01.submit();
	return false;
}

function delete_contents() {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/board/departdc_delete.do';
		document.form01.submit();
	}
	return false;
}

function selectCode() {
	
	//document.getElementById('depart_code').value = $("#departLV2>option:selected").val();
	
	/*
    $.ajax({
	      type: 'POST',
	      url: '/admin/board/departdc_getPList.do',
	      //async:false,
	      data:{departLV2:$("#departLV2>option:selected").val()},
	      dataType: 'json',
	      contentType:"application/json",
	      success: function(data) {
	    	  alert(data);
	         //$('body').html(data);
	      },
	      error: function(request, status, error) {
	      }
	   }); 
	*/
}

function setDisplay(radio, value) {
	if ( radio.checked == true ) {
		if(value == "1") {
			document.form01.viewType[1].checked = false;
		}
		else if(value=="0") {
			document.form01.viewType[0].checked = false;
		}
		document.getElementById('is_view').value=value;
	}
	return false;
}

window.onload = function() {
	onLoadLeftMenu('info_02');

	//selectCode();
	//jQuery("#departLV2").val("${rtVO.DEPART_CODE}").attr("selected","selected");
	//document.getElementById('depart_code').value = "${rtVO.DEPART_CODE}";
	//alert("loading completed");
//	CKEDITOR.replace( 'intro' );
	CKEDITOR.replace( 'intro', {
    	filebrowserUploadUrl: '/admin/ckeditor/fileUploadInContent09.do'
  	});
}

// just digit
function okayDigit(event) {
	var code = event.which ? event.which : event.keyCode;
	if( code==45 || (code <= 57 && code >= 48) ){
		return true;
	}
	return false;
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
							<h3 class="page-header">교구청 부서 등록</h3>
						</c:when>
						<c:otherwise>
							<h3 class="page-header">교구청 부서 수정</h3>	
						</c:otherwise>
					</c:choose>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
  <form class="well" name="form01" action="/admin/board/departdc_modify.do" method="POST">
  	<input type="hidden" name="depart_idx" id="depart_idx" value="${rtVO.DEPART_IDX}"/>
  	<input type="hidden" name="org_idx" id="org_idx" value="${_params.org_idx}"/>
  	<input type="hidden" name="lv1" id="lv1" value="${_params.lv1}"/>
  	<input type="hidden" name="lv2" id="lv2" value="${_params.lv2}"/>
  	<input type="hidden" name="lv3" id="lv3" value="${_params.lv3}"/>
  	
  	<div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">부서선택</label>
    		</div>
    		<div class="col-md-3">
    			<!-- <label for="">교구청(01)</label>-->
    			<select name="departLV1" id="departLV1"  disabled="true" >
    				<c:choose>
						<c:when test="${fn:length(rtGroupVO1) > 0}">
							<c:forEach items="${rtGroupVO1}" var="list">
                            	<option value="${list.ORG_IDX}">${list.LV1} ${list.NAME}</option>
                            </c:forEach>
						</c:when>
					</c:choose>
    			</select>
    		</div>
    		<div class="col-md-3">
    			<select name="departLV2" id="departLV2" onChange="javascript: selectCode();" disabled="true" >
    				<c:choose>
						<c:when test="${fn:length(rtGroupVO2) > 0}">
							<c:forEach items="${rtGroupVO2}" var="list">
                            	<option value="${list.ORG_IDX}" <c:if test="${_params.lv2 eq list.LV2}">selected</c:if> >${list.LV2} ${list.NAME}</option>
                            </c:forEach>
						</c:when>
					</c:choose>				
				</select>		
    		</div>
    	</div>
    </div>   
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">부서코드/부서명</label>
    		</div>
    		<div class="col-md-3" >
    			<input type="text" class="form-control form-control-short w-200" id="depart_code" name="depart_code" maxlength="3" value="${_params.lv1}${_params.lv2}${_params.lv3}" readonly>		
    		</div>
    		<div class="col-md-3" >
    			<input type="text" class="form-control form-control-short w-200" id="depart_name" name="depart_name" value="${rtVO.NAME}" readonly>
    		</div>
    	</div>
    </div>   
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">부서장</label>
    		</div>
    		<div class="col-md-10" >
    			<input type="text" class="form-control" id="depart_topName" placeholder="" name="depart_topName" value="${rtVO.PRIESTNAMELIST }" readOnly>			
    		</div>
    	</div>
    </div>      
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">부서소개</label>
    		</div>
    		<div class="col-md-10" >
    			<textarea id="intro" name="intro" cols="80" rows="10">${rtVO.INTRO}</textarea>					
    		</div>
    	</div>
    </div>   
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2">
    			<label for="inputName">홈페이지</label>
    		</div>
    		<div class="col-md-4">
    			<input class="form-control" type="text"  id="homepage1" placeholder="" name="homepage1" value="${rtVO.HOMEPAGE1}">		
    		</div>
    		<div class="col-md-2">
    			<label for="inputBaptismal">메일</label>
    		</div>
    		<div class="col-md-4" >
    			<input class="form-control" type="text"  id="mail" placeholder="" name="mail" value="${rtVO.MAIL}">		
    		</div>    	
    	</div>
    </div>   

	<div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2">
    			<label for="inputName">전화</label>
    		</div>
    		<div class="col-md-4">
    			<input type="tel" data-format="ddd-ddd-dddd" id="tel" placeholder="" name="tel" maxlength="13" value="${rtVO.TEL}" onkeypress="return okayDigit(event)"/>		
    		</div>
    		<div class="col-md-2">
    			<label for="inputBaptismal">팩스</label>
    		</div>
    		<div class="col-md-4" >
    			<input type="tel" data-format="ddd-ddd-dddd" id="fax" placeholder="" name="fax" value="${rtVO.FAX}"  maxlength="13" onkeypress="return okayDigit(event)"/>
    		</div>    	
    	</div>
    </div> 
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="inputName">주소</label>
    		</div>
    		<div class="col-md-10" >
    			<input type="text" id="postcode" name="postcode" placeholder="" value="${rtVO.POSTCODE}">
                <button type="button" onclick="execDaumPostcode()">우편번호 검색</button>		
    		</div>
    	</div>	
    </div>
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="inputName"></label>
    		</div>
    		<div class="col-md-5" >
                <input class="form-control" type="text" id="roadAddress"  name="addr1" placeholder="도로명주소" value="${rtVO.ADDR1}">(도로)
    		</div>   
    		<div class="col-md-5" >
                <input class="form-control" type="text" id="jibunAddress" name="addr2" placeholder="지번주소" value="${rtVO.ADDR2}">(지번)
    		</div>    		    	
    	</div>
    </div>   
    
    <!--
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2">
    			<label for="title">이미지(사진)</label>
    		</div>
    		
    		<div class="col-md-6">
	    		<c:choose>
	   				<c:when test="${_params.query_type == 'insert'}">
	   					<input type="input" id="imgfile" name="imgfile"  value="" readOnly style="width: 450px;">
	   				</c:when>
	   				<c:otherwise>
	   					<input type="input" id="imgfile" name="imgfile"  value="${rtVO.IMAGE}" readOnly style="width: 450px;">
	   					<label class="checkbox-inline" for=""><input type="checkbox" name="delImg" id="delImg" value="Y">이전파일 삭제함</label>
	   				</c:otherwise>
	   			</c:choose>  
    		
    			<input type="file" id="image" name="image" class="upload-hidden" onchange="uploadPhoto(this)" style="width: 450px;"><span id="imgfile_span"></span>
    		</div>
    	</div>
    </div>
    -->
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">부서안내 노출 여부</label>
    		</div>
    		<div class="col-md-10" >
    			<c:choose>
		      		<c:when test="${rtVO.IS_VIEW eq '2'}">
		        		<label class="radio-inline"><input type="radio" name="viewType" id="viewType1" value="1" onClick="setDisplay(this,'1')">노출</label>
		        		<label class="radio-inline"><input type="radio" name="viewType" id="viewType2" value="0" onClick="setDisplay(this,'0')" checked="checked">비노출</label>
		        	</c:when>
		        	<c:otherwise>
		        		<label class="radio-inline"><input type="radio" name="viewType" id="viewType1" value="1" onClick="setDisplay(this,'1')" checked="checked">노출</label>
		        		<label class="radio-inline"><input type="radio" name="viewType" id="viewType2" value="0" onClick="setDisplay(this,'0')">비노출</label>
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
	<button type="button" id="btnModify" class="btn btn-default pull-right" onclick="javascript:modify_contents();return false;">수정</button>
	<!-- <button type="button" id="btnDelete" class="btn btn-default pull-right" onclick="javascript:delete_contents();return false;">삭제</button> -->
</c:otherwise>
</c:choose>
</p>
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->


<script>
<c:if test = "${fn:length(ERR_MSG) > 0 }">
	// Error Handling
	setTimeout( alert("${ERR_MSG}") , 1000 );
</c:if>
</script>


</body>

</html>