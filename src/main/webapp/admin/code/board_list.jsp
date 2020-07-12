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
var formData = {
	b_idx: 0,
	b_nm: '',
	b_type: '', // type code
	useyn_comment       : '', // yn
	useyn_secret        : '', // yn
	useyn_comment_secret: '', // yn
	useyn_attachement   : '', // yn
	useyn_download_perm : '', // yn
	useyn_login         : '', // yn
	
	mode: '', // i/u/d
};

function copyForm( mode ) {
	// init
	formData.b_idx  = 0;
	formData.b_nm   = '';
	formData.b_type = '';
	formData.useyn_comment       = '';
	formData.useyn_secret        = '';
	formData.useyn_comment_secret= '';
	formData.useyn_attachement   = '';
	formData.useyn_download_perm = '';
	formData.useyn_login         = '';
	
	//
	formData.mode  = mode;
	if( mode == 'i' ) {
		formData.b_nm   = $("#i_b_nm").val(); // document.getElementById("i_b_nm").value;
		formData.b_type = $("#i_b_type").val();
		formData.useyn_comment       = $("#i_useyn_comment").is(":checked") ? "Y":"N";
		formData.useyn_secret        = $("#i_useyn_secret").is(":checked") ? "Y":"N";
		formData.useyn_comment_secret= $("#i_useyn_comment_secret").is(":checked") ? "Y":"N";
		formData.useyn_attachement   = $("#i_useyn_attachement").is(":checked") ? "Y":"N";
		formData.useyn_download_perm = $("#i_useyn_download_perm").is(":checked") ? "Y":"N";
		formData.useyn_login         = $("#i_useyn_login").is(":checked") ? "Y":"N";
		
	} else if( mode == 'u' ) {
		formData.b_idx  = $("#u_b_idx").val();
		formData.b_nm   = $("#u_b_nm").val();
		formData.b_type = $("#u_b_type").val();
		formData.useyn_comment       = $("#u_useyn_comment").is(":checked") ? "Y":"N";
		formData.useyn_secret        = $("#u_useyn_secret").is(":checked") ? "Y":"N";
		formData.useyn_comment_secret= $("#u_useyn_comment_secret").is(":checked") ? "Y":"N";
		formData.useyn_attachement   = $("#u_useyn_attachement").is(":checked") ? "Y":"N";
		formData.useyn_download_perm = $("#u_useyn_download_perm").is(":checked") ? "Y":"N";
		formData.useyn_login         = $("#u_useyn_login").is(":checked") ? "Y":"N";
		
	} else if( mode == 'd' ) {
		
	} else if( mode == 'init' ) {

		$("#u_b_idx").val('');
		$("#u_b_nm").val('');
		$("#u_b_type").val(''); // selectbox
		$("#u_useyn_comment").prop("checked", false);
		$("#u_useyn_secret").prop("checked", false);
		$("#u_useyn_comment_secret").prop("checked", false);
		$("#u_useyn_attachement").prop("checked", false);
		$("#u_useyn_download_perm").prop("checked", false);
		$("#u_useyn_login").prop("checked", false);
	}
	
	console.log("mode="+mode);
	console.log(formData);
	
	// set on the form of the sending 
	$("#p_mode").val(formData.mode);
	$("#p_b_idx").val(formData.b_idx);
	$("#p_b_nm").val(formData.b_nm);
	$("#p_b_type").val(formData.b_type);
	$("#p_useyn_comment").val(formData.useyn_comment);
	$("#p_useyn_secret").val(formData.useyn_secret);
	$("#p_useyn_comment_secret").val(formData.useyn_comment_secret);
	$("#p_useyn_attachement").val(formData.useyn_attachement);
	$("#p_useyn_download_perm").val(formData.useyn_download_perm);
	$("#p_useyn_login").val(formData.useyn_login);
	
}
// insert
function insertFormSend() {
	copyForm( 'i' );
	if(formData.b_nm == "") {
		alert('게시판 명를 입력해주세요.'); return false;
	}
	
	var frm = document.form01; 
	frm.action = '/admin/code/board_cud.do';
	frm.submit();
}
// update 
function setUpdForm(b_idx, b_nm, b_type, useyn_comment, useyn_secret, useyn_comment_secret, useyn_attachement, useyn_download_perm, useyn_login) {
	chgDiv('2');
	//copyForm( 'u' );
	
	// 
	$("#u_b_idx").val(b_idx);
	$("#u_b_nm").val(b_nm);
	$("#u_b_type").val(b_type); // selectbox
	if(useyn_comment=='Y') useyn_comment=true; else useyn_comment=false; 
	if(useyn_secret=='Y') useyn_secret=true; else useyn_secret=false;
	if(useyn_comment_secret=='Y') useyn_comment_secret=true; else useyn_comment_secret=false;
	if(useyn_attachement=='Y') useyn_attachement=true; else useyn_attachement=false;
	if(useyn_download_perm=='Y') useyn_download_perm=true; else useyn_download_perm=false;
	if(useyn_login=='Y') useyn_login=true; else useyn_login=false;
	
	//
	$("#u_useyn_comment").prop("checked", useyn_comment);
	$("#u_useyn_secret").prop("checked", useyn_secret);
	$("#u_useyn_comment_secret").prop("checked", useyn_comment_secret);
	$("#u_useyn_attachement").prop("checked", useyn_attachement);
	$("#u_useyn_download_perm").prop("checked", useyn_download_perm);
	$("#u_useyn_login").prop("checked", useyn_login);
	
	//
	copyForm( 'u' );
}

// 
function modifyFormSend() {
	copyForm( 'u' );
	
	if(formData.b_nm == "") {
		alert('명칭를 입력해주세요.'); return false;
	} else if(formData.b_idx == "" || formData.b_idx == 0) {
		alert('필수 게시판 코드 누락입니다.'); return false;
	} else {
		var frm = document.form01;
		frm.action = '/admin/code/board_cud.do';
		frm.submit();
	}
}

/* function deleteFormSend(c_idx) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		copyForm( 'd' );
		var frm = document.form01;
		frm.action = '/admin/code/board_cud.do';
		frm.c_idx.value=c_idx;
		frm.submit();
	}
    return false;
} */


/* function toggleuseyn() {
	if( !$("#u_is_use").is(":checked") ) {
		$("#u_is_use").val("n");
		$("#u_is_use").prop("checked", false);
	} else {
		$("#u_is_use").val("y");
		$("#u_is_use").prop("checked", true);
	}
} */


// 
function formInit(gubun) {
	copyForm('init');

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

//
function chgDiv(gubun) {
	if(gubun == '1') {
		$("#insCodeDiv").removeClass("hidden");//$("#insCodeDiv").show();
		$("#updCodeDiv").addClass("hidden");//$("#updCodeDiv").hide();
	} else {
		
		$("#insCodeDiv").addClass("hidden");//$("#insCodeDiv").hide();
		$("#updCodeDiv").removeClass("hidden");//$("#updCodeDiv").show();
	}
}
// 
$(document).ready(function() {
	onLoadLeftMenu('org_07');
	chgDiv('1'); 
});

</script>

<body>

<form class="form-group" name="form01" action="" method="POST">
<input class="form-control form-control-short" type="hidden" name="mode"     id="p_mode" />
<input class="form-control form-control-short" type="hidden" name="b_idx"    id="p_b_idx" />
<input class="form-control form-control-short" type="hidden" name="b_nm"     id="p_b_nm" />
<input class="form-control form-control-short" type="hidden" name="b_type"   id="p_b_type" />
<input class="form-control form-control-short" type="hidden" name="useyn_comment"        id="p_useyn_comment" />
<input class="form-control form-control-short" type="hidden" name="useyn_secret"         id="p_useyn_secret" />
<input class="form-control form-control-short" type="hidden" name="useyn_comment_secret" id="p_useyn_comment_secret" />
<input class="form-control form-control-short" type="hidden" name="useyn_attachement"    id="p_useyn_attachement" />
<input class="form-control form-control-short" type="hidden" name="useyn_download_perm"  id="p_useyn_download_perm" />
<input class="form-control form-control-short" type="hidden" name="useyn_login"          id="p_useyn_login" />	 
</form>
		  		
    <!-- top start(left menu include) -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- top end -->
        
        <!-- main start -->        
        <!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12"><h3 class="page-header">게시판  관리</h3></div>
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
						        <th>게시판코드</th>
						        <th width="15%">명칭</th>
						        <th>게시판유형</th>
						        <th>댓글여부</th>
						        <th>암호여부</th>
						        <th>댓글암호여부</th>
						        <th>첨부여부</th>
						        <th>첨부다운권한여부</th>
						        <th>로그인여부</th>
						        <th>생성일</th>
						        <th>변경일</th>
						      </tr>
						    </thead>
						    <tbody>
							  <c:set var="code_val" value="000003" />
						      <c:choose>
								<c:when test="${fn:length(rtn_list) > 0}">
								<c:forEach items="${rtn_list}" var="list">
							      <c:set var="b_idx"    value="${list.B_IDX}" />
	                              <tr data-row-id="${list.B_IDX}" id="${list.B_IDX}" 
	                                  onMouseOver="this.style.backgroundColor='#FFF4E9'; this.style.cursor='pointer';" onMouseOut="this.style.backgroundColor='';"
	                                  onclick="javascript: setUpdForm(${list.B_IDX},'${list.B_NM}','${list.B_TYPE}','${list.USEYN_COMMENT}','${list.USEYN_SECRET}','${list.USEYN_COMMENT_SECRET}','${list.USEYN_ATTACHEMENT}','${list.USEYN_LOGIN}','${list.USEYN_DOWNLOAD_PERM}')">
	                                  <td class="center">${list.B_IDX} </td>
	                                  <td class="center">${list.B_NM} </td>
	                                  <td class="center"><c:set var="type_name" value="${list.B_TYPE}" />
	                                  	<c:forEach items="${rtn_b_type}" var="row">
								            <c:if test="${row.CODE_INST eq list.B_TYPE}"><c:set var="type_name" value="${row.NAME} " /></c:if>
										</c:forEach>${type_name }</td>
	                                  
	                                  <td class="center">${list.USEYN_COMMENT} </td>
	                                  <td class="center">${list.USEYN_SECRET} </td>
	                                  <td class="center">${list.USEYN_COMMENT_SECRET} </td>
	                                  <td class="center">${list.USEYN_ATTACHEMENT} </td>
	                                  <td class="center">${list.USEYN_LOGIN} </td>
	                                  <td class="center">${list.USEYN_DOWNLOAD_PERM} </td>
	                                  
	                                  <td style="display:none;"> </td>
	                                  <td class="center">${fn:substring(list.REG_DT,0,16)} </td>
	                                  <td class="center">${fn:substring(list.UPD_DT,0,16)} </td>
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
			  		<table class="table" style="width: 100%">
			  		<tr>
				        <th>게시판코드</th>
				        <th width="15%">명칭</th>
				        <th>게시판유형</th>
				        <th>댓글여부</th>
				        <th>암호여부</th>
				        <th>댓글암호여부</th>
				        <th>첨부여부</th>
				        <th>첨부다운권한여부</th>
				        <th>로그인여부</th>
			  		</tr>
			  		<tr>
				  		<td><input class="form-control form-control-short" type="text" name="i_b_idx" id="i_b_idx" value="" placeholder="자동부여" readOnly /></td>
				  		<td><input class="form-control form-control-short" type="text" name="i_b_nm"  id="i_b_nm" value="" /></td>
				  		<td><select class="form-control form-control-short" name="i_b_type" id="i_b_type">
				  			<c:forEach items="${rtn_b_type}" var="row">
				  				<option value="${row.CODE_INST}" >${row.NAME}</option>
				  			</c:forEach>
				  			</select></td>
				  		<td><input type="checkbox" name="i_useyn_comment" id="i_useyn_comment" style="width: 50" onclick="javascript: document.getElementById('p_useyn_comment').value=this.checked?'Y':'N'"/> Y</td>
				  		<td><input type="checkbox" name="i_useyn_secret" id="i_useyn_secret" style="width: 50" onclick="javascript: document.getElementById('p_useyn_secret').value=this.checked?'Y':'N'"/> Y</td>
				  		<td><input type="checkbox" name="i_useyn_comment_secret" id="i_useyn_comment_secret" style="width: 50" onclick="javascript: document.getElementById('p_useyn_comment_secret').value=this.checked?'Y':'N'"/> Y</td>
				  		<td><input type="checkbox" name="i_useyn_attachement" id="i_useyn_attachement" style="width: 50" onclick="javascript: document.getElementById('p_useyn_attachement').value=this.checked?'Y':'N'"/> Y</td>
				  		<td><input type="checkbox" name="i_useyn_download_perm" id="i_useyn_download_perm" style="width: 50" onclick="javascript: document.getElementById('p_useyn_download_perm').value=this.checked?'Y':'N'"/> Y</td>
				  		<td><input type="checkbox" name="i_useyn_login" id="i_useyn_login" style="width: 50" onclick="javascript: document.getElementById('p_useyn_login').value=this.checked?'Y':'N'"/> Y</td>
			  		</tr>
			  		</table>
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
			  		<table class="table" style="width: 100%">
			  		<tr>
				        <th>게시판코드</th>
				        <th width="15%">명칭</th>
				        <th>게시판유형</th>
				        <th>댓글여부</th>
				        <th>암호여부</th>
				        <th>댓글암호여부</th>
				        <th>첨부여부</th>
				        <th>첨부다운권한여부</th>
				        <th>로그인여부</th>
			  		</tr>
			  		<tr>
				  		<td><input class="form-control form-control-short" type="text" name="u_b_idx" id="u_b_idx" value="" placeholder="자동부여" readOnly /></td>
				  		<td><input class="form-control form-control-short" type="text" name="u_b_nm"  id="u_b_nm" value="" /></td>
				  		<td><select class="form-control form-control-short" name="u_b_type" id="u_b_type">
				  				<c:forEach items="${rtn_b_type}" var="row">
					  				<option value="${row.CODE_INST}" >${row.NAME}</option>
					  			</c:forEach>
				  			</select></td>
				  		<td><input type="checkbox" name="u_useyn_comment" id="u_useyn_comment" style="width: 50" onclick="javascript: document.getElementById('p_useyn_comment').value=this.checked?'Y':'N'"/> Y</td>
				  		<td><input type="checkbox" name="u_useyn_secret" id="u_useyn_secret" style="width: 50" onclick="javascript: document.getElementById('p_useyn_secret').value=this.checked?'Y':'N'"/> Y</td>
				  		<td><input type="checkbox" name="u_useyn_comment_secret" id="u_useyn_comment_secret" style="width: 50" onclick="javascript: document.getElementById('p_useyn_comment_secret').value=this.checked?'Y':'N'"/> Y</td>
				  		<td><input type="checkbox" name="u_useyn_attachement" id="u_useyn_attachement" style="width: 50" onclick="javascript: document.getElementById('p_useyn_attachement').value=this.checked?'Y':'N'"/> Y</td>
				  		<td><input type="checkbox" name="u_useyn_download_perm" id="u_useyn_download_perm" style="width: 50" onclick="javascript: document.getElementById('p_useyn_download_perm').value=this.checked?'Y':'N'"/> Y</td>
				  		<td><input type="checkbox" name="u_useyn_login" id="u_useyn_login" style="width: 50" onclick="javascript: document.getElementById('p_useyn_login').value=this.checked?'Y':'N'"/> Y</td>
			  		</tr>
			  		</table>
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
