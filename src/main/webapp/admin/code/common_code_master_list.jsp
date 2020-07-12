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
//
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
var jsonData = {
	mode : '',
    code : '',
    name : '',
    type : '',
    use_yn : '',
    del_yn : '',
    eng_name : '',
};
//
function copyForm(mode,code,name,type,useyn,delyn,engName) {
	if( new String(useyn).length > 1  || useyn=='on' || delyn=='on') {
		alert('잘못된 값이 설정되었습니다.');
		return;
	}
	
	$("#p_code").val(code);
	$("#p_name").val(name);
	$("#p_type").val(type);
	$("#p_use_yn").val(useyn);
	$("#p_del_yn").val(delyn);
	$("#p_eng_name").val(engName);
	
	jsonData.mode = mode;
	jsonData.code = code;
	jsonData.name = name;
	jsonData.type = type;
	jsonData.use_yn = useyn;
	jsonData.del_yn = delyn;
	jsonData.eng_name = engName;
}
//
function insertFormSend() {
	var code    = document.getElementById("i_code").value
	 , name     = document.getElementById("i_name").value
	 , type     = document.getElementById("i_type").value
	 , useyn    = $("#i_use_yn").is(":checked") ? "Y":"N"
	 , delyn    = $("#i_del_yn").is(":checked") ? "Y":"N"
	 , engName  = document.getElementById("i_eng_name").value
	 ;
	

	if(code == "") {
		alert('코드를 입력해주세요.'); return false;
	} else if(name == "") {
		alert('코드명를 입력해주세요.'); return false;
	} else if(type == "" || !(type=='C' || type=='L') ) {
		alert('코드 타입을 입력해주세요.'); return false;
	} else if(engName == "") {
		alert('코드가 사용될 컬럼명를 입력해주세요.'); return false;
	} else {
		copyForm('i',code,name,type,useyn,delyn,engName);
		var frm = document.form01; 
		frm.action = '/admin/code/common_code_mgmt_cud.do';
		frm.mode.value = 'i';
		frm.submit();
	}
}
//modifyFormSetting ('${list.CODE}','${list.NAME}','${list.TYPE}','${list.USE_YN}','${list.DEL_YN}','${list.ENG_NAME}')"
function modifyFormSetting(code,name,type,useyn,delyn,engName) {
	// set to update
	chgDiv('2');
	$("#u_code").val(code);
	$("#u_name").val(name);
	$("#u_type").val(type);
	$("#u_use_yn").val(useyn); $("#u_use_yn").prop("checked", (useyn=="Y"?true:false));
	$("#u_del_yn").val(delyn); $("#u_del_yn").prop("checked", (delyn=="Y"?true:false));
	$("#u_eng_name").val(engName);
	copyForm('u',code,name,type,useyn,delyn,engName);
	
	// select code instances via restful
    callRest('POST', '/admin/code/common_code_inst_list.do', jsonData, function(resData) {

    	// table header
    	var MAX_ORDER_NO = 1;
    	var codeInstHtml = "";
    	codeInstHtml += "<table class='table'><tr style='background-color: #D5E6EB'><td colspan='6' style='font-weight:600;'>코드 인스턴스</td>";
    	codeInstHtml += "<td style='font-weight:600;text-align:center' "
                     +  " onMouseOver=\"this.style.backgroundColor='#FFF4E9'; this.style.cursor='pointer';\" onMouseOut=\"this.style.backgroundColor='';\" "
    	             +  " onClick='$(\"#codeInstances\").hide()'>닫기</td></tr>" ;
    	codeInstHtml += "<tr style='background-color: #F0F0F0; text-align: center; align: center;'><td>코드인스턴스</td>";
    	codeInstHtml += "<td>코드인스턴스명</td>";
    	codeInstHtml += "<td>사용여부</td>";
    	codeInstHtml += "<td>삭제여부</td>";
    	codeInstHtml += "<td>정렬순서</td>";
    	codeInstHtml += "<td colspan='2'>기능</td>";
    	codeInstHtml += "</tr>";
    	
    	// list
    	var i = 0;
    	if(resData.COUNT > 0) {
        	var rows = resData.LIST;
        	for (i in rows) {
        		if (rows.hasOwnProperty(i)) {
        			codeInstHtml += "<tr>";
        			codeInstHtml += "<form id='codeForm"+i+"' name='codeForm"+i+"' class='form-group'>";
        			codeInstHtml += "<input type='hidden' name='code"+i+"' id='code"+i+"' value='"+rows[i].CODE+"' />";        		
        			codeInstHtml += "<input type='hidden' name='code_inst"+i+"' id='code_inst"+i+"' value='"+rows[i].CODE_INST+"' />";        		
        			codeInstHtml += "<td><input class='form-control form-control-short' type='text' name='code_inst_aft"+i+"' id='code_inst_aft"+i+"' value='"+rows[i].CODE_INST+"' /></td>";
        			codeInstHtml += "<td><input class='form-control form-control-short' type='text' name='name"+i+"' id='name"+i+"' value='"+rows[i].NAME+"' /></td>";
        			codeInstHtml += "<td><select class='form-control form-control-short' name='use_yn' id='use_yn"+i+"'><option value='Y' "+(rows[i].USE_YN == 'Y' ? 'selected':'')+">사용</option><option value='N' "+(rows[i].USE_YN == 'N' ? 'selected':'')+">미사용</option></select></td>";
        			codeInstHtml += "<td><select class='form-control form-control-short' name='del_yn' id='del_yn"+i+"'><option value='Y' "+(rows[i].DEL_YN == 'Y' ? 'selected':'')+">삭제</option><option value='N' "+(rows[i].DEL_YN == 'N' ? 'selected':'')+">정상</option></select></td>";
        			codeInstHtml += "<td><input class='form-control form-control-short' type='text' name='order_no' id='order_no"+i+"' value='"+rows[i].ORDER_NO+"' /></td>";
        			codeInstHtml += "<td onMouseOver=\"this.style.backgroundColor='#FFF4E9'; this.style.cursor='pointer';\" onMouseOut=\"this.style.backgroundColor='';\" onclick='cudCodeInstance(1, "+i+")'>수정</td>";
        			codeInstHtml += "<td onMouseOver=\"this.style.backgroundColor='#FFF4E9'; this.style.cursor='pointer';\" onMouseOut=\"this.style.backgroundColor='';\" onclick='cudCodeInstance(2, "+i+")'>완전삭제</td>";
        			
        			if( (parseInt(rows[i].ORDER_NO)+1) > MAX_ORDER_NO ) {
        				MAX_ORDER_NO = parseInt(rows[i].ORDER_NO)+1;
        			}
            		codeInstHtml += "</form>";
            		codeInstHtml += "</tr>";
        		}
        	}
        }
    	i++;
    	// new
    	codeInstHtml += "<form name='codeForm"+i+"' id='codeForm"+i+"' method='post'>";
    	codeInstHtml += "<input type='hidden' name='code"+i+"' id='code"+i+"' value='"+code+"' />";        		
    	codeInstHtml += "<input type='hidden' name='code_inst_aft"+i+"' id='code_inst_aft"+i+"' value='' />";        		
		codeInstHtml += "<tr>";
  		codeInstHtml += "<td><input class='form-control form-control-short' type='text' name='code_inst"+i+"' id='code_inst"+i+"' value='' /></td>";
		codeInstHtml += "<td><input class='form-control form-control-short' type='text' name='name"+i+"' id='name"+i+"' value='' /></td>";
		codeInstHtml += "<td><select class='form-control form-control-short' name='use_yn"+i+"' id='use_yn"+i+"'><option value='Y' selected >사용</option><option value='N'>미사용</option></select></td>";
		codeInstHtml += "<td><select class='form-control form-control-short' name='del_yn"+i+"' id='del_yn"+i+"'><option value='Y' >삭제</option><option value='N' selected >정상</option></select></td>";
		codeInstHtml += "<td><input class='form-control form-control-short' type='text' name='order_no"+i+"' id='order_no"+i+"' value='"+MAX_ORDER_NO+"' /></td>";
		//codeInstHtml += "<td onclick='cudCodeInstance(0, document.newCodeForm)' onMouseOver=\"this.style.backgroundColor='#FFF4E9'; this.style.cursor='pointer';\" onMouseOut=\"this.style.backgroundColor='';\">신규등록</td>";
		codeInstHtml += "<td><input type='button' onClick='javascript: cudCodeInstance(0, "+i+")' value='신규등록'></td>";
		codeInstHtml += "<td></td>";
  		codeInstHtml += "</tr>";
  		codeInstHtml += "</form>";
  		
  		// table tail
    	codeInstHtml += "</table><br><br>&nbsp;<br>";
    	$("#codeInstances").html(codeInstHtml);
    	$("#codeInstances").show();
    });
}
//
function cudCodeInstance(mode, idx) {
	var formURL = "/admin/code/common_code_inst_cud.do";
	var mode_msg = mode==0? '등록':(mode==1?'수정':'삭제');
	var cudData = {
		code          : '',
	    code_inst     : '',
	    code_inst_aft : '',
	    name      : '',
	    use_yn    : '',
	    del_yn    : '',
	    orderno   : '',
	    memo      : '',
	    memo2     : '',
	    mode      : mode==0? 'i':(mode==1?'u':'d'),
	};
	cudData.code          = $("#code"+idx).val();
	cudData.code_inst     = $("#code_inst"+idx).val();
	cudData.code_inst_aft = $("#code_inst_aft"+idx).val();
	cudData.name      = $("#name"+idx).val();
	cudData.use_yn    = $("#use_yn"+idx).val();
	cudData.del_yn    = $("#del_yn"+idx).val();
	cudData.orderno   = $("#order_no"+idx).val();
	
	console.log(mode_msg);
	console.log(cudData);

	// register code instances via restful
    callRest('POST', formURL, cudData, function(resData) {
        if(resData.COUNT == 1) {//sucess
        	alert('코드인스턴스 '+mode_msg+' 성공입니다.');
        } else {
        	alert('코드인스턴스 '+mode_msg+' 실패되었어요.\nmsg:'+resData.MSG);
        }
    });
}
// 
function modifyFormSend() {
	var code    = document.getElementById("u_code").value
	 , name     = document.getElementById("u_name").value
	 , type     = document.getElementById("u_type").value
	 , useyn    = $("#u_use_yn").is(":checked") ? "Y":"N"
	 , delyn    = $("#u_del_yn").is(":checked") ? "Y":"N"
	 , engName  = document.getElementById("u_eng_name").value
	 ;
	
	if(code == "") {
		alert('코드를 입력해주세요.'); return false;
	} else if(name == "") {
		alert('코드명를 입력해주세요.'); return false;
	} else if(type == "" || !(type=='C' || type=='L') ) {
		alert('코드 타입을 입력해주세요.'); return false;
	} else if(engName == "") {
		alert('코드가 사용될 컬럼명를 입력해주세요.'); return false;
	} else {
		copyForm('u',code,name,type,useyn,delyn,engName);
		var frm = document.form01; 
		frm.action = '/admin/code/common_code_mgmt_cud.do';
		frm.mode.value = 'u';
		frm.submit();
	}
}
// 
function deleteFormSend(code) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		copyForm('d',code,"","","","","");
		var frm = document.form01;
		frm.action = '/admin/code/common_code_mgmt_cud.do';
		frm.mode.value='d';
		frm.code.value=code;
		frm.submit();
	}
}
//
function formInit(gubun) {
	copyForm("","","","","","","");

	if(gubun == '2') { // 수정
		$("#p_mode").val("u");
		$("#u_code").val("");
		$("#u_name").val("");
		$("#u_eng_name").val("");
		$("#u_use_yn").attr("checked", false);
		$("#u_del_yn").attr("checked", false);
		chgDiv('1');
		$("#btnNew").removeClass("hide");
		$("#btnReg").addClass("hide");
		
	} else if(gubun == '1') { // 등록
		$("#p_mode").val("i");
		$("#i_code").val("");
		$("#i_name").val("");
		$("#i_eng_name").val("");
		$("#i_use_yn").attr("checked", false);
		$("#i_del_yn").attr("checked", false);
		if(confirm('신규 코드 등록입니까?')) {
			$("#i_code").val(_codeVal2);
		}
		$("#btnNew").addClass("hide");
		$("#btnReg").removeClass("hide");
	}
}

//window.onload = function () { chgDiv('1'); }
$(document).ready(function() {
	onLoadLeftMenu('org_06');
	chgDiv('1'); 
});
</script>

<body>

<form class="form-group" name="form01" action="/admin/org/pos_list.do" method="POST">
<input class="form-control form-control-short" type="hidden" name="mode"      id="p_mode" />
<input class="form-control form-control-short" type="hidden" name="code"      id="p_code" />
<input class="form-control form-control-short" type="hidden" name="name"      id="p_name" />
<input class="form-control form-control-short" type="hidden" name="type"      id="p_type" />
<input class="form-control form-control-short" type="hidden" name="use_yn"    id="p_use_yn" />
<input class="form-control form-control-short" type="hidden" name="del_yn"    id="p_del_yn" />
<input class="form-control form-control-short" type="hidden" name="eng_name"  id="p_eng_name" />
</form>
		  		
    <!-- top start(left menu include) -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- top end -->
        
        <!-- main start -->        
        <!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12"><h3 class="page-header">직급코드관리</h3></div>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- //.row(page title end -->
            <div class="page-list">
            	<div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div> &nbsp;&nbsp;&nbsp;&nbsp; 등록된 코드 개수 : ${rtn_paging.totalCount} 개 </div>
		            <div class="panel-body" style="overflow-x:hidden;overflow-y:scroll;width:100%;height:360px;">
		              <table style="width:100%" class="table" id="dataTables-example">
						    <thead>
						      <tr>
						        <th width="15%">코드</th>
						        <th width="25%">코드명</th>
						        <th width="25%">영문명</th>
						        <th>타입</th>
						        <th>사용여부</th>
						        <th>삭제여부</th>
						        <th>등록일</th>
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
		                              <tr data-row-id="${list.CODE}" 
		                                  onMouseOver="this.style.backgroundColor='#FFF4E9'; this.style.cursor='pointer';" onMouseOut="this.style.backgroundColor='';"
		                                  onclick="javascript: modifyFormSetting ('${list.CODE}','${list.NAME}','${list.TYPE}','${list.USE_YN}','${list.DEL_YN}','${list.ENG_NAME}')"
		                                  id="${list.CODE}">
		                                  
		                                  <td class="center">${list.CODE} </td>
		                                  <td class="center">${list.NAME} </td>
		                                  <td class="center">${list.ENG_NAME} </td>
		                                  <td class="center">${list.TYPE} </td>
		                                  <td class="center">${list.USE_YN} </td>
		                                  <td class="center">${list.DEL_YN} </td>
		                                  <td class="center">${list.INS_DATE} </td>
		                                  <td class="center"><a href="javascript: deleteFormSend ('${list.CODE}')">삭제</a></td>
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
					<col style="width:20%;">
					<col style="width:20%;">
					<col style="width:20%;">
					<col>
				</colgroup>
			  	<tr>
			  		<td><label for="q_word">코드</label></td>
			  		<td><label for="q_word">코드명</label></td>
			  		<td><label for="q_word">컬럼명</label></td>
			  		<td><label for="q_word">옵션</label></td>
			  	</tr>
			  	<tr>
			  		<td>
			  			<input class="form-control form-control-short" type="text" name="i_code"   id="i_code"  />
			  		</td>
			  		<td>
			  			<input class="form-control form-control-short" type="text" name="i_name" id="i_name"/>
			  		</td>
			  		<td>
			  			<input class="form-control form-control-short" type="text" name="i_eng_name" id="i_eng_name" style="width: 50"/>
			  		</td>
			  		<td>
			  			<label><select id="i_type" name="i_type"><option value="C">공통코드</option><option value="L">목록성 코드</option></select> 타입</label>&nbsp;
			  			<label><input type="checkbox" name="i_use_yn" id="i_use_yn" style="width: 50" onclick="javascript: toggleUseyn()"/> 사용여부</label>&nbsp;
			  			<label><input type="checkbox" name="i_del_yn" id="i_del_yn" style="width: 50" onclick="javascript: toggleDelyn()"/> 삭제여부</label>
			  		</td>
			  	</tr>
			  	<tr>
			  		<td colspan="4">
			  		<div style="text-align: right;">
						<div class="btn-group"><button type="button" id="btnNew" class="btn btn-default" onclick="javascript: formInit ('1');return false;">신규</button></div>			  		
						<div class="btn-group"><button type="button" id="btnReg" class="btn btn-default hide" onclick="javascript: insertFormSend ();return false;">등록</button></div>
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
					<col style="width:20%;">
					<col style="width:20%;">
					<col style="width:20%;">
					<col>
				</colgroup>
			  	<tr>
			  		<td><label for="q_word">코드</label></td>
			  		<td><label for="q_word">코드명</label></td>
			  		<td><label for="q_word">컬럼명</label></td>
			  		<td><label for="q_word">옵션</label></td>
			  	</tr>
			  	<tr>
			  		<td>
			  			<input class="form-control form-control-short" type="text" name="u_code" id="u_code" value="" readonly />
			  		</td>
			  		<td>
			  			<input class="form-control form-control-short" type="text" name="u_name" id="u_name" value="" />
			  		</td>
			  		<td>
			  			<input class="form-control form-control-short" type="text" name="u_eng_name" id="u_eng_name" value="" style="width: 50"/>
			  		</td>
			  		<td>
			  			<label><select id="u_type" name="u_type"><option value="C">공통코드</option><option value="L">목록성 코드</option></select> 타입</label>&nbsp;
			  			<label><input type="checkbox" name="u_use_yn" id="u_use_yn" style="width: 50" onclick="javascript: document.getElementById('p_use_yn').value=this.checked?'Y':'N'"/> 사용여부</label>&nbsp;
			  			<label><input type="checkbox" name="u_del_yn" id="u_del_yn" style="width: 50" onclick="javascript: document.getElementById('p_del_yn').value=this.checked?'Y':'N'"/> 삭제여부</label>
			  		</td>
			  	</tr>
			  	<tr>
			  		<td colspan="4" style="text-align: right;">
						<button type="button" id="btnInit" class="btn btn-default" onclick="javascript: formInit ('2');return false;">초기화</button>
						<button type="button" id="btnMod"  class="btn btn-default" onclick="javascript: modifyFormSend ();return false;">수정</button>
			  		</td>
			  	</tr>
			  </table>
			</div>
			<!-- /.panel-body -->
		</div>
		
		<br>
		<br>
		
		<!-- code instance list -->
	    <div id="codeInstances">
	       <form action="" method="post">
	       </form>
	    </div>
      </div>
      <!-- /#page-wrapper -->



	<script>
	var _codeVal   = "${code_val}";
	var _codeVal2  = <fmt:parseNumber value="${code_val}" />+1;
	var paddingLen = 6 - (_codeVal2 + "").length; 
	while(paddingLen-- > 0 ) {
		_codeVal2 = "0" + _codeVal2;
	}
	</script>

</body>
</html>
