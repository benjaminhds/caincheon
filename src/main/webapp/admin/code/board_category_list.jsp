<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/admin/_common/inc/head.jsp" %>
<%
if(admSessionMemId==null||admSessionMemId.equals("")) {                // id가 Null 이거나 없을 경우
	response.sendRedirect("/admin/login.jsp");    // 로그인 페이지로 리다이렉트 한다.
}
%>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">

function copyForm(c_idx,name,is_use,b_idx) {
	$("#p_c_idx").val(c_idx);
	$("#p_name").val(name);
	$("#p_is_use").val(is_use);
	$("#p_b_idx").val(b_idx);
}

function insertFormSend() {
	var c_idx    = document.getElementById("i_c_idx").value
	 , name      = document.getElementById("i_name").value
	 , is_use    = $("#i_is_use").is(":checked") ? "Y":"N"
	 , b_idx     = document.getElementById("i_b_idx").value
	 ;
	
	if(name == "") {
		alert('게시판 명를 입력해주세요.');
		return false;
	} else if(b_idx == "") {
		alert('참조 게시판 코드를 입력해주세요.');
		return false;
	} 
	copyForm(c_idx,name,is_use,b_idx);
	var frm = document.form01; 
	frm.action = '/admin/org/board_code_mgmt_cud.do';
	frm.mode.value = 'i';
	frm.submit();
    return false;
}

function toggleUseyn() {
	if( !$("#u_is_use").is(":checked") ) {
		$("#u_is_use").val("N");
		$("#u_is_use").prop("checked", false);
	} else {
		$("#u_is_use").val("Y");
		$("#u_is_use").prop("checked", true);
	}
}

function modifyFormSetting(c_idx,name,is_use,b_idx) {
	chgDiv('2');
	
	$("#u_c_idx").val(c_idx);
	$("#u_name").val(name);
	$("#u_b_idx").val(b_idx);
	
	$("#u_is_use").val(is_use);
	$("#u_is_use").prop("checked", (is_use=="Y"?true:false));
	
	copyForm(c_idx,name,is_use,b_idx);
}

function modifyFormSend() {
	var c_idx  = document.getElementById("u_c_idx").value
	 , name    = document.getElementById("u_name").value
	 , b_idx   = document.getElementById("u_b_idx").value
	 , is_use  = $("#u_is_use").is(":checked") ? "Y":"N"
	 ;
	
	if(name == "") {
		alert('명칭를 입력해주세요.');
		return false;
	} else if(b_idx == "") {
		alert('참조게시판 코드를 입력해주세요.');
		return false;
	} else {
		copyForm(c_idx,name,is_use,b_idx);
		var frm = document.form01; 
		frm.action = '/admin/org/board_code_mgmt_cud.do';
		frm.mode.value = 'u';
		frm.submit();
		return false;
	}
}

function deleteFormSend(c_idx) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		copyForm(c_idx,"","","");
		var frm = document.form01;
		frm.action = '/admin/org/board_code_mgmt_cud.do';
		frm.mode.value='d';
		frm.c_idx.value=c_idx;
		frm.submit();
	}
    return false;
}

function chgDiv(gubun) {
	if(gubun == '1') {
		$("#insCodeDiv").removeClass("hidden");//$("#insCodeDiv").show();
		$("#updCodeDiv").addClass("hidden");//$("#updCodeDiv").hide();
	} else {
		
		$("#insCodeDiv").addClass("hidden");//$("#insCodeDiv").hide();
		$("#updCodeDiv").removeClass("hidden");//$("#updCodeDiv").show();
	}
}
function formInit(gubun) {
	copyForm("","","","","","");
	$("#p_mode").val("");

	if(gubun == '2') { // 수정
		$("#u_c_idx").val(_codeVal);
		$("#u_name").val("");
		$("#u_b_idx").val("");
		$("#u_is_use").attr("checked", false);
		
	} else if(gubun == '1') { // 등록
		$("#i_c_idx").val(_codeVal);
		$("#i_name").val("");
		$("#i_b_idx").val("");
		$("#i_is_use").attr("checked", false);
	}
	chgDiv('1');
	
}

$(document).ready(function() {
	onLoadLeftMenu('org_08');
	chgDiv('1'); 
});

</script>

<body>

<form class="form-group" name="form01" action="/admin/org/board_code_mgmt.do" method="POST">
<input class="form-control form-control-short" type="hidden" name="mode"     id="p_mode" />
<input class="form-control form-control-short" type="hidden" name="c_idx"    id="p_c_idx" />
<input class="form-control form-control-short" type="hidden" name="name"     id="p_name" />
<input class="form-control form-control-short" type="hidden" name="is_use"   id="p_is_use" />
<input class="form-control form-control-short" type="hidden" name="b_idx"    id="p_b_idx" />
</form>
		  		
    <!-- top start(left menu include) -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- top end -->
        
        <!-- main start -->        
        <!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12"><h3 class="page-header">게시판 카테고리 관리</h3></div>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- //.row(page title end -->
            <div class="page-list">
            	<div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div> &nbsp;&nbsp;&nbsp;&nbsp; 등록된 코드 개수 : ${rtn_paging.totalCount} 개 </div>
		            <div class="panel-body" style="overflow-x:hidden;overflow-y:scroll;width:100%;height:450px;">
		              <table style="width:100%" class="table" id="dataTables-example">
						    <thead>
						      <tr>
						        <th width="15%">분류 코드</th>
						        <th>명칭</th>
						        <th width="15%">활성여부</th>
						        <th width="15%">적용 보드 코드</th>
						        <th>수정</th>
						        <th>삭제</th>			        
						        <th style="display:none;">LEVEL</th>
						      </tr>
						    </thead>
						    <tbody>
							  <c:set var="code_val" value="000001" />
						      <c:choose>
									<c:when test="${fn:length(rtn_list) > 0}">
									<c:forEach items="${rtn_list}" var="list">
									      <c:set var="code_val"    value="${list.CODE}" />
			                              <tr data-row-id="${list.CODE}${list.CODE_INST}" id="${list.CODE}${list.CODE_INST}" <c:if test="${list.IS_USE eq 'N'}">style="color: darkgray"</c:if> >
			                                  <td class="center">${list.C_IDX} </td>
			                                  <td class="center">${list.NAME} </td>
			                                  <td class="center">${list.IS_USE} </td>
			                                  <td class="center">${list.B_IDX} </td>
			                                  <td class="center"><a href="javascript: modifyFormSetting ('${list.C_IDX}','${list.NAME}','${list.IS_USE}','${list.B_IDX}')">수정</a></td>
			                                  <td class="center"><a href="javascript: deleteFormSend ('${list.C_IDX}')">삭제</a></td>
			                                  <td style="display:none;"> </td>
			                              </tr>
			                              </c:forEach>
									</c:when>
								</c:choose>		      
						    </tbody>
						  </table>
					</div>
				<!-- panel panel-default -->
			</div>
			<!-- col-lg-12 -->
		</div>						
		
		<!-- row -->
		<div class="page-list">
			<div id="insCodeDiv" class="table-responsive">
				<!--  Search : begin //-->
				<table class="table">
			  	<tr style="vertical-align: middle;  font-weight: 600; ">
			  		<td style="text-align: right;">분류 코드</td>
			  		<td><input class="form-control form-control-short" type="text" name="i_c_idx"   id="i_c_idx" value="" placeholder="자동부여" readOnly /></td>
			  		<td style="text-align: right;">명칭</td>
			  		<td><input class="form-control form-control-short" type="text" name="i_name" id="i_name" value="" /></td>
			  		<td style="text-align: right;">적용 보드 코드</td>
			  		<td><input class="form-control form-control-short" type="text" name="i_b_idx" id="i_b_idx" value="" /></td>
			  		<td style="text-align: right;">옵션</td>
			  		<td><input type="checkbox" name="i_is_use" id="i_is_use" style="width: 50" onclick="javascript: document.getElementById('p_is_use').value=this.checked?'Y':'N'"/> 활성여부</td>
			  	</tr>
			  	<tr>
			  		<td colspan="8">
			  		<div style="text-align: right;">
						<div class="btn-group"><button type="button" class="btn btn-default" onclick="javascript: formInit ('1');return false;">초기화</button></div>			  		
						<div class="btn-group"><button type="button" class="btn btn-default" onclick="javascript: insertFormSend ();return false;">등록</button></div>
					</div>
			  		</td>
			  	</tr>
			  </table>
			</div>
			<!-- /.panel-body -->
			<div id="updCodeDiv" class="table-responsive">
			<!--  Search : begin //-->
			  <table class="table">
			  
			  	<tr style="vertical-align: middle;  font-weight: 600; ">
			  		<td style="text-align: right;">게시판 코드</td>
			  		<td><input class="form-control form-control-short" type="text" name="u_c_idx"   id="u_c_idx" value="" readOnly /></td>
			  		<td style="text-align: right;">명칭</td>
			  		<td><input class="form-control form-control-short" type="text" name="u_name" id="u_name" value="" /></td>
			  		<td style="text-align: right;">참조보드 코드</td>
			  		<td><input class="form-control form-control-short" type="text" name="u_b_idx" id="u_b_idx" value="" /></td>
			  		<td style="text-align: right;">옵션</td>
			  		<td><input type="checkbox" name="u_is_use" id="u_is_use" style="width: 50" onclick="javascript: document.getElementById('p_is_use').value=this.checked?'Y':'N'"/> 활성여부</td>
			  	</tr>
			  	<tr>
			  		<td colspan="8">
			  		<div style="text-align: right;">
						<div class="btn-group"><button type="button" class="btn btn-default" onclick="javascript: formInit ('2');return false;">초기화</button></div>
						<div class="btn-group"><button type="button" class="btn btn-default" onclick="javascript: modifyFormSend ();return false;">수정</button></div>
					</div>
			  		</td>
			  	</tr>
			  </table>
			</div>
			<!-- /.panel-body -->
		</div>
       </div>
       <!-- /#page-wrapper -->

   </div>
   
    <!-- /#wrapper -->

<script>
 <c:set var="cdInstVal" value="0" />
 <c:if test="${fn:length(code_inst_val) gt 0}">
   <c:set var="cdInstVal" value="${code_inst_val}" />
 </c:if>
 
 var _codeVal = "${code_val}";
 var _codeInstVal = <fmt:parseNumber value="${cdInstVal}" />+1;
 var _ocdeOrdernoVal = <fmt:parseNumber value="${cdInstVal}" />+1;
 
 if( 3 - (""+_codeInstVal).length > 0 ) {
	 _codeInstVal = "0"+_codeInstVal;
 }  
</script>

</body>

</html>
