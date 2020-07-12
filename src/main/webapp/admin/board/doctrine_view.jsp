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
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
    //본 예제에서는 도로명 주소 표기 방식에 대한 법령에 따라, 내려오는 데이터를 조합하여 올바른 주소를 구성하는 방법을 설명합니다.
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
                document.getElementById('addr1').value = fullRoadAddr;
                document.getElementById('addr2').value = data.jibunAddress;

            }
        }).open();
    }
</script>
<script type="text/javascript">
function viewList() {
	document.form01.action = '/admin/board/doctrine_list.do';
	document.form01.submit();
	return false;
}

function insert_contents() {
	if( $(':radio[name="member_type"]:checked').val() != "1" && $(':radio[name="member_type"]:checked').val() != "2") {
		alert("신자구분을 입력해 주세요."); $("#member_type").focus(); return; 
	}
	if( $("#name").val() == "") { alert("성명을 입력해 주세요."); $("#name").focus(); return; }
	if( $(':radio[name="sex"]:checked').val() != "1" && $(':radio[name="sex"]:checked').val() != "2") {
		alert("성별을 입력해 주세요."); $("#sex").focus(); return; 
	}
	if( $("#church_idx").val() == "") { alert("소속본당을 입력해 주세요."); $("#church_idx").focus(); return; }
	if( $("#postcode").val() == "") { alert("주소를 입력해 주세요."); $("#postcode").focus(); return; }
	if( $("#addr1").val() == "") { alert("주소를 입력해 주세요."); $("#addr1").focus(); return; }
	
	var uploadFile = document.getElementById('thumbFile').value;
	
	if(uploadFile != "") {
		var fileExt = uploadFile.substring(uploadFile.lastIndexOf(".") + 1);
		var reg = /gif|jpg|jpeg|png/i;
		if(reg.test(fileExt) == false) {
			alert("첨부파일은 gif,jpg,png로 된 이미지만 가능합니다.");
			return;
		}
	}
	
	// 정규식 - 이메일 유효성 검사
	var regEmail = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	if(!regEmail.test($("#email").val())) { alert('이메일 주소가 유효하지 않습니다'); $("#email").focus(); return; }
		
	document.form01.action = '/admin/board/doctrine_insert.do';
	document.form01.submit();
	return false;
}

function modify_contents() {
	if( $(':radio[name="approve_yn"]:checked').val() != "Y" && $(':radio[name="approve_yn"]:checked').val() != "N") {
		alert("승인여부를 입력해 주세요."); $("#approve_yn").focus(); return; 
	}

	if( $(':radio[name="member_type"]:checked').val() != "1" && $(':radio[name="member_type"]:checked').val() != "2") {
		alert("신자구분을 입력해 주세요."); $("#member_type").focus(); return; 
	}
	if( $("#name").val() == "") { alert("성명을 입력해 주세요."); $("#name").focus(); return; }
	if( $(':radio[name="sex"]:checked').val() != "1" && $(':radio[name="sex"]:checked').val() != "2") {
		alert("성별을 입력해 주세요."); $("#sex").focus(); return; 
	}
	if( $("#church_idx").val() == "") { alert("소속본당을 입력해 주세요."); $("#church_idx").focus(); return; }
	if( $("#postcode").val() == "") { alert("주소를 입력해 주세요."); $("#postcode").focus(); return; }
	if( $("#addr1").val() == "") { alert("주소를 입력해 주세요."); $("#addr1").focus(); return; }
	
	var uploadFile = document.getElementById('thumbFile').value;
	
	if(uploadFile != "") {
		var fileExt = uploadFile.substring(uploadFile.lastIndexOf(".") + 1);
		var reg = /gif|jpg|jpeg|png/i;
		if(reg.test(fileExt) == false) {
			alert("첨부파일은 gif,jpg,png로 된 이미지만 가능합니다.");
			return;
		}
	}
	
	// 정규식 - 이메일 유효성 검사
	var regEmail = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	if(!regEmail.test($("#email").val())) { alert('이메일 주소가 유효하지 않습니다'); $("#email").focus(); return; }

	document.form01.action = '/admin/board/doctrine_modify.do';
	document.form01.submit();
	return false;
}

function delete_contents() {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/board/doctrine_delete.do';
		document.form01.submit();
	}
	return false;
}

function uploadPhoto(fileObj) {
	//enctype="multipart/form-data"
	var filePath = fileObj.value;
	var fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
	var fileKind = fileName.split(".")[1];
	
	alert("filePath=" + filePath);
	alert("fileName=" + fileName);
	alert("fileKind=" + fileKind);
	
	var form = $('form01')[0];
    var formData = new FormData(form);
    formData.append("photo", $("#photo")[0].files[0]);
    $.ajax({
        url: '/admin/board/uploadOk.jsp',
                processData: false,
                contentType: false,
                data: formData,
                type: 'POST',
                success: function(result){
                    alert("업로드 성공!!");
                }
        });
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
	$("#birthday").datepicker();
	onLoadLeftMenu('order_01');
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
		<h3 class="page-header">통신교리 등록</h3>
	</c:when>
	<c:otherwise>
		<h3 class="page-header">통신교리 수정</h3>	
	</c:otherwise>
	</c:choose>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<form id="form01" name="form01" enctype="multipart/form-data" method="POST" action="/admin/board/doctrine_list.do">
	  <input type="hidden" name="doctrine_no" id="doctrine_no" value="${docContents.DOCTRINE_NO}"/>
	  <input type="hidden" name="adminYn" id="adminYn" value="Y"/>
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
						<th><label for="">승인구분 </label></th>
						<td colspan="3">
				        		<label class="radio-inline"><input type="radio" name="approve_yn" id="approve_yn" value="Y" <c:if test="${docContents.APPROVE_YN=='Y' }" >checked="checked"</c:if>>승인</label>
				        		<label class="radio-inline"><input type="radio" name="approve_yn" id="approve_yn" value="N" <c:if test="${docContents.APPROVE_YN=='N' }" >checked="checked"</c:if>>미승인</label>
						</td>
					</tr>
					<tr>
						<th><label for="">신자구분 </label></th>
						<td colspan="3">
				         		<input type="radio" name="member_type"  id="member_type" value="1" <c:if test="${docContents.MEMBER_TYPE=='1' }" >checked="checked"</c:if>>
			                    <label for=""><span>예비신자</span></label>
			                    <input type="radio" name="member_type"  id="member_type" value="2" <c:if test="${docContents.MEMBER_TYPE=='2' }" >checked="checked"</c:if>>
			                    <label for=""><span>재교육자</span></label>
						</td>
					</tr>
					<tr>
						<th><label for="">성명</label></th>
						<td>
			    			<input class="form-control" type="text"  id="name" placeholder="" name="name" value="${docContents.NAME}">		
						</td>
						<th><label for="">세례명</label></th>
						<td>
				    			<input class="form-control" type="text"  id="baptismal_name" placeholder="" name="baptismal_name" value="${docContents.BAPTISMAL_NAME}">		
						</td>
					</tr>
					<tr>
						<th><label for="">성별</label></th>
						<td>
			    			<input type="radio" name="sex"  id="sex"  value="1" <c:if test="${docContents.SEX=='1' }" >checked="checked"</c:if>>
		                    <label for=""><span>남자</span></label>
		                    <input type="radio" name="sex"  id="sex"  value="2" <c:if test="${docContents.SEX=='2' }" >checked="checked"</c:if>>
		                    <label for=""><span>여자</span></label>
						</td>
						<th><label for="">직업</label></th>
						<td>
				    			<input type="text" class="form-control" id="job" placeholder="" name="job" value="${docContents.JOB}">		
						</td>
					</tr>					
					<tr>
						<th><label for="">소속본당</label></th>
						<td colspan="3">
							<div class="row">
							<div class="col-md-4">
			    			<select class="form-control" name="church_idx" id="church_idx">
		                        <option value="">선택</option>
		                        <c:if test="${fn:length(CHURCH_LIST) > 0}">
		                         <c:forEach var="entry" items="${CHURCH_LIST}" varStatus="status">
		                         	<option value="${entry.CHURCH_IDX}" <c:if test = "${docContents.CHURCH_IDX==entry.CHURCH_IDX }"> selected </c:if>>${entry.NAME}</option>
		                         </c:forEach>
		                     </c:if>
		                    </select>
		                    </div>
			    			예비신자께서는 영세 받을 성당, 현재다니고 있는 성당 혹은 주소지에서 
			 				가장 가까운 성당의 이름을 선택하여 주시기 바랍니다.
			 				</div>
						</td>
					</tr>
					<tr>
						<th><label for="">생년월일</label></th>
						<td colspan="3">
			    			<input type="radio" name="birth_type" id="birth_type" value="1" <c:if test="${docContents.BIRTH_TYPE=='1' }" >checked="checked"</c:if>>
		                    <label for=""><span>양력</span></label>
		                    <input type="radio" name="birth_type" id="birth_type" value="2" <c:if test="${docContents.BIRTH_TYPE=='2' }" >checked="checked"</c:if>>
		                    <label for=""><span>음력</span></label>
			    		<input  class="cal-month" type="text" id="birthday" name="birthday" value="${docContents.BIRTHDAY}" maxlength="10" />
						</td>
					</tr>
					<tr>
						<th><label for="">휴대전화</label></th>
						<td>
			    			<input type="input" id="m_tel" name="m_tel" placeholder="" value="${docContents.M_TEL}">		
						</td>
						<th><label for="">연락처2</label></th>
						<td>
				    			<input type="input" id="h_tel" name="h_tel" placeholder="" value="${docContents.H_TEL}">		
						</td>
					</tr>
					<tr>
						<th><label for="">이미지(사진)</label></th>
						<td>
	 						<div class="form-group input-group">
								<input class="form-control" type="file" name="thumbFile" id="thumbFile">
							</div>
							<c:if test="${fn:length(docContents.STRFILENAME) > 0 }">
								<img alt="" src="${docContents.STRFILENAME}" width="120px">${docContents.FILENAME}
								<label class="checkbox-inline" for=""><input type="checkbox" name="delFile" id="delFile" value="Y">이전파일 삭제함</label>
							</c:if>		
						</td>
						<th><label for="">이메일</label></th>
						<td>
			    			<input type="input" id="email" name="email" placeholder="" value="${docContents.EMAIL}">		
						</td>
					</tr>
					<tr>
						<th rowspan="2"><label for="">주소</label></th>
						<td colspan="3">
							<div class="row">
							<div class="col-md-4">
			    			<input type="text" class="form-control col-sm-1" id="postcode" placeholder="" name="postcode" value="${docContents.POSTCODE}">		
			    			</div>
			    			<button type="button" onclick="execDaumPostcode()">우편번호 검색</button>
			    			</div>
						</td>
					</tr>
					<tr>
						<td colspan="3">
			    			<input type="text" class="form-control col-sm-1" id="addr1" placeholder="" name="addr1" value="${docContents.ADDR1}">		
			    			<input type="text" class="form-control col-sm-1" id="addr2" placeholder="" name="addr2" value="${docContents.ADDR2}">
						</td>
					</tr>
					<tr>
						<th><label for="">남기실 말씀</label></th>
						<td colspan="3">
			    			<textarea class="form-control h-200" id="user_comment" name="user_comment" cols="80" rows="10">${docContents.USER_COMMENT}</textarea>		
						</td>
					</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- Buttons : Begin //-->
		<p class="pull-right">
		<button type="button" id="btnList"   class="btn btn-default pull-right" onclick="javascript:viewList();return false;">목록</button>
		<c:choose>
		<c:when test="${_params.query_type == 'insert'}">
			<button type="button" id="btnInsert" class="btn btn-default pull-right" onclick="javascript:insert_contents();return false;">등록</button>	
		</c:when>
		<c:otherwise>
			<button type="button" id="btnModify" class="btn btn-default pull-right" onclick="javascript:modify_contents();return false;">수정</button>
			<button type="button" id="btnDelete" class="btn btn-default pull-right" onclick="javascript:delete_contents();return false;">삭제</button>	
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

</html>
