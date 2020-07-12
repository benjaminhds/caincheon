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

function copyForm(code,code_inst,name,orderno,useyn,delyn) {
	$("#p_code").val(code);
	$("#p_code_inst").val(code_inst);
	$("#p_name").val(name);
	$("#p_orderno").val(orderno);
	$("#p_use_yn").val(useyn);
	$("#p_del_yn").val(delyn);
}

function insertFormSend() {
	var code     = document.getElementById("i_code").value
	 , code_inst = document.getElementById("i_code_inst").value
	 , name      = document.getElementById("i_name").value
	 , orderno   = document.getElementById("i_orderno").value
	 , use_yn    = document.getElementById("i_use_yn").value
	 , del_yn    = document.getElementById("i_del_yn").value
	 ;
	
	if(code_inst == "") {
		alert('코드인스턴스를 입력해주세요.');
		return false;
	} else if(name == "") {
		alert('코드인스턴스명를 입력해주세요.');
		return false;
	} else if(orderno == "") {
		alert('코드인스턴스 순서번호를 입력해주세요.');
		return false;
	} 
	copyForm(code,code_inst,name,orderno,use_yn,del_yn);
	var frm = document.form01; 
	frm.action = '/admin/org/welfareagent_code_mgmt_cud.do';
	frm.mode.value = 'i';
	frm.submit();
    return false;
}

function toggleUseyn() {
	if( !$("#u_use_yn").is(":checked") ) {
		$("#u_use_yn").val("N");
		$("#u_use_yn").prop("checked", false);
	} else {
		$("#u_use_yn").val("Y");
		$("#u_use_yn").prop("checked", true);
	}
}
function toggleDelyn() {
	if( !$("#u_del_yn").is(":checked") ) {
		$("#u_del_yn").val("N");
		$("#u_del_yn").prop("checked", false);
	} else {
		$("#u_del_yn").val("Y");
		$("#u_del_yn").prop("checked", true);
	}
}
function modifyFormSetting(code,code_inst,name,orderno,useyn,delyn) {
	chgDiv('2');
	$("#u_code").val(code);
	$("#u_code_inst").val(code_inst);
	$("#u_name").val(name);
	$("#u_orderno").val(orderno);
	
	$("#u_use_yn").val(useyn);
	$("#u_use_yn").prop("checked", (useyn=="Y"?true:false));
	
	$("#u_del_yn").val(delyn);
	$("#u_del_yn").prop("checked", (delyn=="Y"?true:false));
	
	copyForm(code,code_inst,name,orderno,useyn,delyn);
}

function modifyFormSend() {
	var code     = document.getElementById("u_code").value
	 , code_inst = document.getElementById("u_code_inst").value
	 , name      = document.getElementById("u_name").value
	 , orderno   = document.getElementById("u_orderno").value
	 , use_yn    = $("#u_use_yn").is(":checked") ? "Y":"N"
	 , del_yn    = $("#u_del_yn").is(":checked") ? "Y":"N"
	 ;
	
	if(code_inst == "") {
		alert('코드인스턴스를 입력해주세요.');
		return false;
	} else if(name == "") {
		alert('코드인스턴스명를 입력해주세요.');
		return false;
	} else if(u_orderno == "") {
		alert('코드인스턴스 순서번호를 입력해주세요.');
		return false;
	} else {
		copyForm(code,code_inst,name,orderno,use_yn,del_yn);
		var frm = document.form01; 
		frm.action = '/admin/org/welfareagent_code_mgmt_cud.do';
		frm.mode.value = 'u';
		frm.submit();
		return false;
	}
}

function deleteFormSend(code, code_inst) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		copyForm(code,code_inst,"","");
		var frm = document.form01;
		frm.action = '/admin/org/welfareagent_code_mgmt_cud.do';
		frm.mode.value='d';
		frm.code.value=code;
		frm.code_inst.value=code_inst;
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
		$("#u_code").val(_codeVal);
		$("#u_code_inst").val("");
		$("#u_name").val("");
		$("#u_orderno").val("");
		$("#u_use_yn").attr("checked", false);
		$("#u_del_yn").attr("checked", false);
		
	} else if(gubun == '1') { // 등록
		$("#i_code").val(_codeVal);
		$("#i_code_inst").val("");
		$("#i_name").val("");
		$("#i_orderno").val("");
		$("#i_use_yn").attr("checked", false);
		$("#i_del_yn").attr("checked", false);
		if(confirm('신규 코드 등록입니까?')) {
			$("#i_code_inst").val(_codeInstVal);
			$("#i_orderno").val(_ocdeOrdernoVal);
		}
	}
	chgDiv('1');
	
}

$(document).ready(function() {
	onLoadLeftMenu('org_05');
	chgDiv('1'); 
});
</script>

<body>

<form class="form-group" name="form01" action="/admin/org/welfareagent_code_list.do" method="POST">
<input class="form-control form-control-short" type="hidden" name="mode"      id="p_mode" />
<input class="form-control form-control-short" type="hidden" name="code"      id="p_code" />
<input class="form-control form-control-short" type="hidden" name="code_inst" id="p_code_inst" />
<input class="form-control form-control-short" type="hidden" name="name"      id="p_name" />
<input class="form-control form-control-short" type="hidden" name="orderno"   id="p_orderno" />
<input class="form-control form-control-short" type="hidden" name="use_yn"    id="p_use_yn" />
<input class="form-control form-control-short" type="hidden" name="del_yn"    id="p_del_yn" />
</form>
		  		
    <!-- top start(left menu include) -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- top end -->
        
        <!-- main start -->        
        <!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12"><h3 class="page-header">복지기관 세목코드</h3></div>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- //.row(page title end -->
            <div class="page-list">
            	<div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div> &nbsp;&nbsp;&nbsp;&nbsp; 등록된 코드 개수 : ${rtn_paging.totalCount} 개 </div>
		            <div class="panel-body" style="overflow-x:hidden;overflow-y:scroll;width:100%;height:360px;">
		              <table width="100%" class="table" id="dataTables-example">
						    <thead>
						      <tr>
						        <th>정렬번호</th>
						        <th width="20%">코드</th>
						        <th width="40%">코드명</th>
						        <th>사용여부</th>
						        <th>삭제여부</th>
						        <th>등록일</th>
						        <th>수정</th>
						        <th>삭제</th>			        
						        <th style="display:none;">LEVEL</th>
						      </tr>
						    </thead>
						    <tbody>
							  <c:set var="code_val" value="000003" />
							  <c:set var="code_inst_val" value="000" />
						      <c:choose>
									<c:when test="${fn:length(rtn_list) > 0}">
									<c:forEach items="${rtn_list}" var="list">
									      <c:set var="code_val"      value="${list.CODE}" />
									      <c:set var="code_inst_val" value="${list.CODE_INST}" />
			                              <tr data-row-id="${list.CODE}${list.CODE_INST}" id="${list.CODE}${list.CODE_INST}" <c:if test="${list.USE_YN eq 'N' or list.DEL_YN eq 'Y'}">style="color: darkgray; text-decoration:line-through"</c:if> >
			                                  <td class="center">${list.ORDER_NO} </td>
			                                  <td class="center">${list.CODE_INST} </td>
			                                  <td class="center">${list.NAME} </td>
			                                  <td class="center">${list.USE_YN} </td>
			                                  <td class="center">${list.DEL_YN} </td>
			                                  <td class="center">${list.INS_DATE} </td>
			                                  <td class="center"><a href="javascript: modifyFormSetting ('${list.CODE}','${list.CODE_INST}','${list.NAME}','${list.ORDER_NO}','${list.USE_YN}','${list.DEL_YN}')">수정</a></td>
			                                  <td class="center"><a href="javascript: deleteFormSend ('${list.CODE}','${list.CODE_INST}')">삭제</a></td>
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
			<div id="insCodeDiv">
				<!--  Search : begin //-->
				<table class="table">
			  	<colgroup>
					<col style="width:150px;">
					<col style="width:150px;">
					<col style="width:150px;">
					<col style="width:150px;">
				</colgroup>
			  	<tr>
			  		<td><label for="q_word">직급코드</label></td>
			  		<td>
			  			<input class="form-control form-control-short" type="hidden" name="i_code"   id="i_code" value="${code_val}" readonly />
			  			<input class="form-control form-control-short" type="text" name="i_code_inst" id="i_code_inst" value=""  />
			  			
			  		</td>
			  		<td><label for="q_word">직급명</label></td>
			  		<td>
			  			<input class="form-control form-control-short" type="text" name="i_name" id="i_name"/>
			  		</td>
			  		<td><label for="q_word">정렬번호</label></td>
			  		<td>
			  			<input class="form-control form-control-short" type="text" name="i_orderno" id="i_orderno" style="width: 50"/>
			  			
			  		</td>
			  		<td><label for="q_word">옵션</label></td>
			  		<td>
			  			<label><input type="checkbox" name="i_use_yn" id="i_use_yn" style="width: 50" onclick="javascript: document.getElementById('p_use_yn').value=this.checked?'Y':'N'"/> 활성여부</label>&nbsp;
			  			<label><input type="checkbox" name="i_del_yn" id="i_del_yn" style="width: 50" onclick="javascript: document.getElementById('p_del_yn').value=this.checked?'Y':'N'"/> 삭제여부</label>
			  		</td>
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
			<div id="updCodeDiv">
			<!--  Search : begin //-->
			  <table class="table">
			  	<colgroup>
					<col style="width:150px;">
					<col style="width:150px;">
					<col style="width:150px;">
					<col style="width:150px;">
				</colgroup>
			  	<tr>
			  		
			  		<td><label for="q_word">직급코드</label></td>
			  		<td>
			  			<input class="form-control form-control-short" type="hidden" name="u_code" id="u_code" value="${code_val}" readonly />
			  			<input class="form-control form-control-short" type="text" name="u_code_inst" id="u_code_inst" style="width: 120" readonly/>
			  		</td>
			  		<td><label for="q_word">직급명</label></td>
			  		<td>
			  			<input class="form-control form-control-short" type="text" name="u_name" id="u_name"/>
			  		</td>
			  		<td><label for="q_word">정렬번호</label></td>
			  		<td>
			  			<input class="form-control form-control-short" type="text" name="u_orderno" id="u_orderno" style="width: 50"/>
			  		</td>
			  		<td><label for="q_word">옵션</label></td>
			  		<td>
			  			<label><input type="checkbox" name="u_use_yn" id="u_use_yn" style="width: 50" onclick="javascript: toggleUseyn()"/> 활성여부</label>&nbsp;
			  			<label><input type="checkbox" name="u_del_yn" id="u_del_yn" style="width: 50" onclick="javascript: toggleDelyn()"/> 삭제여부</label>
			  		</td>
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
 
 var _codeVal = "${code_val}";
 var _codeInstVal = <fmt:parseNumber value="${code_inst_val}" />+1;
 var _ocdeOrdernoVal = <fmt:parseNumber value="${code_inst_val}" />+1;
 
 if( 3 - (""+_codeInstVal).length > 0 ) {
	 _codeInstVal = "0"+_codeInstVal;
 }  
</script>

</body>

</html>
