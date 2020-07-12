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
            document.getElementById('addr1').value = fullRoadAddr;
            //document.getElementById('addr2').value = data.jibunAddress;
            //document.getElementById('roadAddress').value = fullRoadAddr;
            //document.getElementById('jibunAddress').value = data.jibunAddress;

        }
    }).open();
}

function viewList() {
	document.form01.action = '/admin/board/org_list.do';
	document.form01.submit();
	return false;
}

function insert_contents() {
	var regExp = /^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9]{3,4}-?[0-9]{4}$/;
	if($("#tel").val() != "") {
		if( !regExp.test($("#tel").val())) { alert('전화번호가 유효하지 않습니다'); $("#tel").focus(); return; }
	}
	if($("#fax").val() != "") {
		if( !regExp.test($("#fax").val())) { alert('팩스번호가 유효하지 않습니다'); $("#fax").focus(); return; }
	}
	if( $(":input:radio[name=display_yn]:checked").val() == undefined) { alert("노출여부를 선택해 주세요."); $("#display_yn").focus(); return; }
	
	document.form01.action = '/admin/board/org_insert.do';
	//document.getElementById('brial_place_name').value=form01.brial_place.options[form01.brial_place.selectedIndex].text;
	document.form01.submit();
	return false;
}

function modify_contents() {
	var regExp = /^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9]{3,4}-?[0-9]{4}$/;
	if($("#tel").val() != "") {
		//if( !regExp.test($("#tel").val())) { 
		if($("#tel").val().length < 10) { 
			alert('전화번호가 유효하지 않습니다'); $("#tel").focus(); return; 
		}
	}
	if($("#fax").val() != "") {
		//if( !regExp.test($("#fax").val())) { 
		if($("#fax").val().length < 10) { 
			alert('팩스번호가 유효하지 않습니다'); $("#fax").focus(); return; 
		}
	}
	
	if( $(":input:radio[name=display_yn]:checked").val() == undefined) { alert("노출여부를 선택해 주세요."); $("#display_yn").focus(); return; }
	
	//alert('modify_contents');
	document.form01.action = '/admin/board/org_modify.do';
	document.form01.submit();
	return false;
}

function delete_contents() {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/board/org_delete.do';
		document.form01.submit();
	}
	return false;
}

window.onload = function() {
	onLoadLeftMenu('info_05');
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
						<h3 class="page-header">수도회/기관단체 등록</h3>
					</c:when>
					<c:otherwise>
						<h3 class="page-header">수도회/기관단체 수정</h3>	
					</c:otherwise>
					</c:choose>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
  <form class="well" name="form01" action="/admin/board/org_list.do" method="POST">
  	<input type="hidden" name="depart_code" id="depart_code" value="${orgContents.DEPART_CODE}"/> 	
  	<input type="hidden" name="o_idx" id="o_idx" value="${orgContents.O_IDX}"/> 	
  	<input type="hidden" name="org_idx" id="org_idx" value="${orgContents.ORG_IDX}"/> 	
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
									<td><label for="">기관/단체명</label></td>
									<td colspan="3">
									<input class="form-control" type="text"  id="name" name="name" value="${fn:replace(orgContents.NAME, '"', quotTAG)}" placeholder="기관/단체명" >
									</td>
								</tr>
								<tr>
									<td><label for="">세목 분류</label></td>
									<td colspan="3">
									<select class="form-control" name="wf_code" id="wf_code">
										<option value="">지정 안함</option>
									<c:if test="${fn:length(welfareCodeList) > 0}">
									<c:forEach items="${welfareCodeList}" var="list">
										<option value="${list.CODE_INST }" <c:if test="${list.CODE_INST eq orgContents.WF_CODE }">selected</c:if> >${list.NAME }</option>
									</c:forEach>
									</c:if>
									</select>
									</td>
								</tr>
								<tr>
									<td><label for="inputName">전화</label>
									</td>
									<td><input class="input" type="text" id="tel" name="tel" value="${orgContents.TEL}"/>
									</td>
									<td><label for="inputName">팩스</label>
									</td>
									<td><input class="input" type="text" id="fax" name="fax" value="${orgContents.FAX}"/>
									</td>
								</tr>
								<tr>
									<td><label for="inputName">메일</label>
									</td>
									<td><input class="input" type="text" id="email" name="email" value="${orgContents.EMAIL}"/>
									</td>
									<td><label for="inputName">홈페이지</label>
									</td>
									<td><input class="input" type="text" id="homepage" name="homepage" value="${orgContents.HOMEPAGE}"/>
									</td>
								</tr>
								<tr>
									<th rowspan="2"><label for="">주소</label></th>
									<td colspan="3">
										<input type="text" id="postcode" name="postcode" placeholder="" value="${orgContents.POSTCODE}"/>
                						<button type="button" onclick="execDaumPostcode()">우편번호 검색</button>
									</td>
								</tr>
								<tr>
									<td colspan="3">
						                <input class="form-control" type="text" id="addr1"  name="addr1" placeholder="도로명주소"  value="${orgContents.ADDR1}"/>
									</td>
								</tr>
								<tr>
									<td><label for="inputName">사제</label>
									</td>
									<td>
									<select name="p_idx" id="p_idx">
										<option value="">지정 안함</option>
					    				<c:choose>
											<c:when test="${fn:length(priestList) > 0}">
												<c:forEach items="${priestList}" var="list">
													<option value=${list.P_IDX} <c:if test="${list.P_IDX eq orgContents.P_IDX}">selected</c:if> >${list.NAME}</option>
												</c:forEach>
											</c:when>
										</c:choose>													
									</select>
									</td>
									<td><label for="">호칭</label>
									</td>
									<td><input class="form-class" type="text" id="etc_name" name="etc_name" value="${orgContents.ETC_NAME}"/>
									</td>
								</tr>
								<tr>
									<td><label for="">노출</label>
									</td>
									<td colspan="3">
										<label class="radio-inline" for=""><input type="radio" name="display_yn" id="display_yn" value="Y" <c:if test="${orgContents.DISPLAY_YN=='Y' }" >checked="checked"</c:if>>노출</label>
										<label class="radio-inline" for=""><input type="radio" name="display_yn" id="display_yn" value="N" <c:if test="${orgContents.DISPLAY_YN=='N' }" >checked="checked"</c:if>>비노출</label>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
		</div>
<p>
<button type="button" id="btnList"   class="btn btn-default pull-right" onclick="javascript:viewList();return false;">목록</button>
<c:choose>
<c:when test="${_params.query_type == 'insert'}">
	<button type="button" id="btnInsert" class="btn btn-default pull-right" onclick="javascript:insert_contents();return false;">등록</button>	
</c:when>
<c:otherwise>
	<button type="button" id="btnModify" class="btn btn-default pull-right" onclick="javascript:modify_contents();return false;">수정</button>
		
</c:otherwise>
</c:choose>
</p>

</form>
</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->
</body>

</html>