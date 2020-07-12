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
String.prototype.replaceAll = function(org, dest) {
    return this.split(org).join(dest);
}

function copyForm(code,code_inst,name,orderno,useyn) {
	if(code!="") $("#p_code").val(code);
	if(code_inst!="") $("#p_code_inst").val(code_inst);
	if(name!="") $("#p_name").val(name);
	if(orderno!="") $("#p_orderno").val(orderno);
	if(useyn!="") $("#p_use_yn").val(useyn);
	//if(delyn!="") $("#p_del_yn").val(delyn);
}

function insertFormSend() {
	var code     = document.getElementById("i_code").value
	 , code_inst = document.getElementById("i_code_inst").value
	 , name      = document.getElementById("i_name").value
	 , orderno   = document.getElementById("i_orderno").value
	 , use_yn    = $("#i_use_yn").is(":checked") ? "Y":"N"
	 , memo      = $("#i_memo").val()
	 ;
	//alert(11);
	/* if(code_inst == "") {
		alert('코드를 입력해주세요.');
		return false;
	} else */ if(name == "") {
		alert('명칭을 입력해주세요.');
		return false;
	}
	//alert(22);
	copyForm(code,code_inst,name,orderno,use_yn);
	//alert(33);
	var frm = document.form01; 
	frm.action = '/admin/org/area_code_mgmt_cud.do';
	frm.mode.value = 'i';
	frm.memo.value = memo;
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
function makeQueryString( a ) {
    var o = "";
    var i = 0;
    //var a = this.serializeArray(); // 'this' 는 form object
    $.each(a, function() {
    	if ( i > 0 ) o += "&";
    	o += this.name + "=" + this.value ;
    	i++;
    });
    return o;
};

function modifyFormSetting(code,code_inst,name,orderno,useyn,delyn) {
	chgDiv('2');
	$("#u_code").val(code);
	$("#u_code_inst").val(code_inst);
	$("#u_name").val(name);
	$("#u_orderno").val(orderno);
	
	$("#u_use_yn").val(useyn);
	$("#u_use_yn").prop("checked", (useyn=="Y"?true:false));
	
	//$("#u_del_yn").val(delyn);
	//$("#u_del_yn").prop("checked", (delyn=="Y"?true:false));
	
	copyForm(code,code_inst,name,orderno,useyn);
	
	$("#p_mode").val("u");
	$("#p_code_inst_aft").val(code_inst);
	
	$("#p2_code").val(code);
	$("#p2_code_inst").val(code_inst);
	
	// 관할구역좌표(메모) 데이터 조회
	var _frmObj     = $("form[name=form02]");
	var _actionURL  = _frmObj.attr("ACTION");
	var _formArray  = _frmObj.serializeArray(); 
	
	/** Restful call에서 서블릿이 json으로 응답하지 못함. */
	$.post(_actionURL, _formArray, function (responseData) {  
		//alert("JUST TEST.01\n\n"+responseData);
		console.log(responseData);
		console.log(JSON.stringify(responseData));
		console.log(responseData.MEMO);
		console.log(responseData.MEMO2);
		
		$("#u_memo").val(responseData.MEMO.replaceAll('<BR>', '\n').replaceAll("'", "\""));
		$("#u_memo2").val(responseData.MEMO2.replaceAll('<BR>', '\n').replaceAll("'", "\""));
	});
	//$("#u_memo").val(_memoMap.get(code_inst).replaceAll('<BR>', '\n').replaceAll("'", "\""));
}

function modifyFormSend() {
	var code     = ""//$("#u_code").val()
	 , code_inst = $("#u_code_inst").val()
	 , name      = $("#u_name").val()
	 , orderno   = $("#u_orderno").val()
	 , use_yn    = $("#u_use_yn").is(":checked") ? "Y":"N"
	 //, del_yn    = $("#u_del_yn").is(":checked") ? "Y":"N"
	 , memo      = $("#u_memo").val()
	 , memo2     = $("#u_memo2").val()
	 ;
	
	if(code_inst == "" || code_inst.length != 2) {
		alert('코드인스턴스를 입력해주세요. \n\n(코드인스턴스는 2자리 입니다.)');
		return false;
	} else if(name == "") {
		alert('코드인스턴스명를 입력해주세요.');
		return false;
	} else if(u_orderno == "") {
		alert('코드인스턴스 순서번호를 입력해주세요.');
		return false;
	} else {
		//alert("modifyFormSend ---- \n"+code+", "+code_inst+", "+name+", "+orderno+", "+use_yn +", memo=" + memo );
		
		if($("#p_code_inst_aft").val()==code_inst) {
			copyForm(code,code_inst,name,orderno,use_yn);
		} else {
			copyForm(code,"",name,orderno,use_yn);
			$("#p_code_inst_aft").val(code_inst);
		}
		
		var frm = document.form01; 
		frm.action = '/admin/org/area_code_mgmt_cud.do';
		frm.mode.value = 'u';
		frm.memo.value = memo;
		frm.memo2.value = memo2;
		frm.submit();
		return true;
	}
}

function deleteFormSend(code, code_inst) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		copyForm(code,code_inst,"","");
		var frm = document.form01;
		frm.action = '/admin/org/area_code_mgmt_cud.do';
		frm.mode.value='d';
		frm.code.value=code;
		frm.code_inst.value=code_inst;
		frm.submit();
	}
    return false;
}

function chgDiv(gubun) {
	if(gubun == '1') { /* 신규등록 */
		$("#insCodeDiv").removeClass("hidden");//$("#insCodeDiv").show();
		$("#updCodeDiv").addClass("hidden");//$("#updCodeDiv").hide();
		
		$("#btn-i-new").addClass("hide");//버튼 숨기기
		$("#btn-i-reg").removeClass("hide");//버튼 보이기
	} else { /* 수정 */
		$("#insCodeDiv").addClass("hidden");//$("#insCodeDiv").hide();
		$("#updCodeDiv").removeClass("hidden");//$("#updCodeDiv").show();
		
		$("#btn-i-new").removeClass("hide");//버튼 보이기
		$("#btn-i-reg").addClass("hide");//버튼 숨기기
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
		//$("#u_del_yn").attr("checked", false);
		
	} else if(gubun == '1') { // 등록
		$("#i_code").val(_codeVal);
		$("#i_code_inst").val("");
		$("#i_name").val("");
		$("#i_orderno").val("");
		$("#i_use_yn").attr("checked", false);
		//$("#i_del_yn").attr("checked", false);
		if(confirm('신규 코드 등록입니까?')) {
			$("#i_code_inst").val(_codeInstVal);
			$("#i_orderno").val(_ocdeOrdernoVal);
		}
	}
	chgDiv('1');
	
}

$(document).ready(function(){ 
	chgDiv('1');
	$("#btn-i-new").removeClass("hide");
	$("#btn-i-reg").addClass("hide");
	
	onLoadLeftMenu('info_13');
	//var menuopen = onLoadLeftMenu('info_13');
	//setTimeout(menuopen, 100);
});

</script>

<body>
<form class="form-group" name="form01" action="/admin/org/area_code_list.do" method="POST">
	<input class="form-control form-control-short" type="hidden" name="mode"          id="p_mode" placeholder="모드 구분 : i u d" />
	<input class="form-control form-control-short" type="hidden" name="code"          id="p_code" placeholder="코드" />
	<input class="form-control form-control-short" type="hidden" name="code_inst_aft" id="p_code_inst_aft" placeholder="변경전 코드인스턴스" />
	<input class="form-control form-control-short" type="hidden" name="code_inst"     id="p_code_inst" placeholder="코드인스턴스" />
	<input class="form-control form-control-short" type="hidden" name="name"          id="p_name" placeholder="코드인스턴스명" />
	<input class="form-control form-control-short" type="hidden" name="memo"          id="p_memo" placeholder="지도에서 구휙을 표시할 좌표값들" />
	<input class="form-control form-control-short" type="hidden" name="memo2"         id="p_memo2" placeholder="지도에서 구휙의 중심 좌표값" />
	<input class="form-control form-control-short" type="hidden" name="orderno"       id="p_orderno" placeholder="정렬순서" />
	<input class="form-control form-control-short" type="hidden" name="use_yn"        id="p_use_yn" placeholder="사용여부  flag" />
	<!-- <input class="form-control form-control-short" type="hidden" name="del_yn"    id="p_del_yn" placeholder="삭제여부 flag" />-->
</form>
<form class="form-group" name="form02" action="/admin/org/area_code_mgmt_map.do" method="POST">
	<input class="form-control form-control-short" type="hidden" name="code"          id="p2_code" placeholder="코드" />
	<input class="form-control form-control-short" type="hidden" name="code_inst"     id="p2_code_inst" placeholder="코드인스턴스" />
</form>
		  		
    <!-- top start(left menu include) -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- top end -->
        
        <!-- main start -->
        <!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
					<h3 class="page-header">본당 지구 코드 관리</h3>
					<h5><font style="color:red; font-weight: 600">(지구코드의 노출여부 옵션만 실시간 반영되지 않음. 변경시 서버 재기동이 필요하며, 그 외에는 실시간 반영임.)</h5></font>
					</div>
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
						        <!-- <th>정렬번호</th>-->
						        <th width="20%">코드</th>
						        <th width="40%">코드명</th>
						        <th>노출순번</th>
						        <th>노출여부</th>
						        <!-- <th>삭제여부</th>-->
						        <th>등록일</th>
						        <th>수정</th>
						        <!--<th>삭제</th>-->
						        
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
			                              <tr data-row-id="${list.CODE}${list.CODE_INST}" id="${list.CODE}${list.CODE_INST}">
			                                  <td class="center">${list.CODE_INST} </td>
			                                  <td class="center">${list.NAME} </td>
			                                  <td class="center">${list.ORDER_NO} </td>
			                                  <td class="center">${list.USE_YN} </td>
			                                  <!-- <td class="center">${list.DEL_YN} </td>-->
			                                  <td class="center">${list.INS_DATE} </td>
			                                  <td class="center"><a href="javascript: modifyFormSetting ('${list.CODE}','${list.CODE_INST}','${list.NAME}','${list.ORDER_NO}','${list.USE_YN}','${list.DEL_YN}')">수정</a></td>
			                                  <!--<td class="center"><a href="javascript: deleteFormSend ('${list.CODE}','${list.CODE_INST}')">삭제</a></td>-->
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
			  	
			  	<tr>
			  		<td><label for="q_word">지구코드</label></td>
			  		<td>
			  			<input class="form-control form-control-short" type="hidden" name="i_code"   id="i_code" value="${code_val}" readonly />
			  			<input class="form-control form-control-short" type="text" name="i_code_inst" id="i_code_inst" value="" placeholder="자동채번됩니다." />
			  		</td>
			  		<td><label for="q_word">지구명</label></td>
			  		<td>
			  			<input class="form-control form-control-short" type="text" name="i_name" id="i_name"/>
			  		</td>
			  		<td><label for="q_word">노출순서</label></td>
			  		<td><input class="form-control" type="text" name="i_orderno" id="i_orderno" style="width: 50"/></td>
			  		<!--<td><label for="q_word">옵션</label></td>-->
			  		<td>
			  			<label><input type="checkbox" name="i_use_yn" id="i_use_yn" style="width: 50" checked onclick='javascript: $("#p_use_yn").val(this.checked?"Y":"N")'/> 노출여부</label><!-- 사용여부</label> -->
			  			<!-- <label><input type="checkbox" name="i_del_yn" id="i_del_yn" style="width: 50" onclick='javascript: $("#p_del_yn").val(this.checked?"Y":"N")'/> 삭제여부</label>-->
			  		</td>
			  	</tr>
			  	<tr>
			  		<td><label for="q_word">지구 좌표</label></td>
			  		<td colspan="6">
			  			<textarea class="form-control" type="text" name="i_memo2" id="i_memo2"  rows="2"></textarea>
			  		</td>
			  	</tr>
			  	<tr>
			  		<td><label for="q_word">지구 관할구역 좌표</label></td>
			  		<td colspan="6">
			  			<textarea class="form-control" type="text" name="i_memo" id="i_memo"  rows="11"></textarea>
			  		</td>
			  	</tr>
			  	<tr>
			  		<td colspan="7"><div style="text-align: right;">
						<div class="btn-group"><button type="button" id="btn-i-new" class="btn btn-default" onclick="javascript: formInit ('1'); $(this).addClass('hide'); return false;">신규</button></div>	
						<div class="btn-group"><button type="button" id="btn-i-reg" class="btn btn-default" onclick="javascript: insertFormSend (); $(this).addClass('hide'); return false;" >등록</button></div>
					</div>
			  		</td>
			  	</tr>
			  </table>
			</div>
			<!-- /.panel-body -->
			<div id="updCodeDiv">
			<!--  Search : begin //-->
			  <table class="table">
			  	<tr>
			  		<td><label for="q_word">지구코드</label></td>
			  		<td>
			  			<input class="form-control form-control-short" type="hidden" name="u_code" id="u_code" value="${code_val}" readonly />
			  			<input class="form-control form-control-short" type="text" name="u_code_inst" id="u_code_inst" />
			  		</td>
			  		<td><label for="q_word">지구명</label></td>
			  		<td>
			  			<input class="form-control form-control-short" type="text" name="u_name" id="u_name"/>
			  		</td>
			  		<td><label for="q_word">노출순서</label></td>
			  		<td><input class="form-control" type="text" name="u_orderno" id="u_orderno" style="width: 50"/></td>
			  		<!--<td><label for="q_word">옵션</label></td>-->
			  		<td>
			  			<label><input type="checkbox" name="u_use_yn" id="u_use_yn" style="width: 50" onclick="javascript: toggleUseyn()"/> 노출여부</label>
			  			<!-- <label><input type="checkbox" name="u_del_yn" id="u_del_yn" style="width: 50" onclick="javascript: toggleDelyn()"/> 삭제여부</label>-->
			  		</td>
			  	</tr>
			  	<tr>
			  		<td><label for="q_word">지구  좌표</label></td>
			  		<td colspan="6">
			  			<textarea class="form-control" type="text" name="u_memo2" id="u_memo2" rows="2"></textarea>
			  		</td>
			  	</tr>
			  	<tr>
			  		<td><label for="q_word">지구 관할구역 좌표</label></td>
			  		<td colspan="6">
			  			<textarea class="form-control" type="text" name="u_memo" id="u_memo" rows="11"></textarea>
			  		</td>
			  	</tr>
			  	<tr>
			  		<td colspan="7"><div style="text-align: right;">
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

// 코드 신규 등록시 코드인스턴스의 마지막 코드를 +1하여 생성한다.
var _codeVal = "${code_val}";
var _codeInstVal = <fmt:parseNumber value="${code_inst_val}" />+1;
var _ocdeOrdernoVal = <fmt:parseNumber value="${code_inst_val}" />+1;
if( 2 - (""+_codeInstVal).length > 0 ) {
	_codeInstVal = "0"+_codeInstVal;
}

// memo는 별도 맵에 등록
var _memoMap  = new Map();
var _memo2Map = new Map();  
<c:if test="${fn:length(rtn_list) > 0}">
  <c:forEach items="${rtn_list}" var="row">
    _memoMap.set("${row.CODE_INST}", "${row.MEMO}");
    _memo2Map.set("${row.CODE_INST}", "${row.MEMO2}");
  </c:forEach>
</c:if>

</script>

</body>

</html>
