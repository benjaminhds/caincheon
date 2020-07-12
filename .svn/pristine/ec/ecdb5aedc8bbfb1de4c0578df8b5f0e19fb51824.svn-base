<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


		
<div class="container-fluid" id="assign_table">
  <div class="row" id="orgrow_btn" style="font-weight:600; text-aling:center; ">
  	<div class="col-md-">
  	  <div><button type="button" class="btn40 btn-default btn-info pull-left" onclick="javascript:appendAssign();return false;">발령추가</button></div>
  	</div>
  	
    <div class="col-md-1">
      <div >현 임지</div>
    </div>
    <div class="col-md-1">
      <div >주 임지여부</div>
    </div>
    <div class="col-md-1">
      <div >직급</div>
    </div>
    <div class="col-md-1">
      <div >임지 시작일</div>
    </div>
    <div class="col-md-1">
      <div >임지 종료일</div>
    </div>
    <div class="col-md-2">
      <div >1-Level조직</div>
    </div>
    <div class="col-md-2">
      <div >2-Level조직</div>
    </div>
    <div class="col-md-2">
      <div >세부 조직</div>
    </div>
  	
  </div>
</div>


<script type="text/javascript">

// 조직 LV2만 조회
function loadOrgHeirarchyLV1(div_seq, org_lv1, org_lv1_nm, targetEle, clearEle) {
	
	org_lv1 = org_lv1.substring(0, org_lv1.indexOf("|"));
	//$("#orgrow_name"+div_seq).text(org_lv1_nm);
	
	$(clearEle).empty();
	
	// LV2 목록 가져오기
	var formData = new FormData($('formX'+div_seq)[0]);
	formData.append("LV1", org_lv1);
	formData.append("DEPTH_TYPE", 2);
	_requestByAjax('/admin/org/org_code_list_lv.do', formData, function(status, responseData){
				console.log("status="+status);
				console.log("responseData="+JSON.stringify(responseData));
				
				var optns = "<option value='' selected >선택</option>";
				$.each(responseData, function(idx, row) {
					optns += "<option value='"+row.LV2+"|"+row.ORG_IDX+"'>"+row.NAME+"</option>";
				});
				console.log("optns="+optns);
				$(targetEle).empty().append(optns);
				
			});
}

// 조직 LV3만 조회
function loadOrgHeirarchyLV2(div_seq, org_lv1, org_lv2,  org_lv2_nm, targetEle) {
	
	org_lv1 = org_lv1.substring(0, org_lv1.indexOf("|"));
	org_lv2 = org_lv2.substring(0, org_lv2.indexOf("|"));
	//$("#orgrow_name"+div_seq).text(org_lv2_nm);
	
	// LV3 목록 가져오기
	var formData = new FormData($('formX'+div_seq)[0]);
	formData.append("LV1", org_lv1);
	formData.append("LV2", org_lv2);
	formData.append("DEPTH_TYPE", 3);
	_requestByAjax('/admin/org/org_code_list_lv.do', formData, function(status, responseData){
				console.log("status="+status);
				console.log("responseData="+JSON.stringify(responseData));
				
				var optns = "<option value='' selected >선택</option>";
				$.each(responseData, function(idx, row) {
					optns += "<option value='"+row.LV3+"|"+row.ORG_IDX+"'>"+row.NAME+"</option>";
				});
				console.log("optns="+optns);
				$(targetEle).empty().append(optns);
				
			});
}


//임지발령을 DB에서 삭제
function deletePriestAssign(frm, div_seq, idx) {
	if(frm.idx.value == '') { // UI만 존재하고, DB미존재 div form 즉시 삭제
		$("#orgrow_"+div_seq).remove();
		return;
	}
	if(confirm("선택한 임지 발령 정보를 삭제하시겠습니까?")) {
		var data1 = $(frm).serializeArray();
		var formData = new FormData($(frm));
	    $.each(data1, function() {
	    	formData.append(this.name, this.value);
	    });
		
		_requestByAjax('/admin/board/priest_assing_delete.do', formData, function(status, responseData){
					console.log("status="+status);
					console.log("responseData="+JSON.stringify(responseData));
					if(responseData.status=='error') {
						
					} else {
						$("#orgrow_"+div_seq).remove();
					}
					alert(responseData.message);
				});
	}
}


// 임지발령을 DB에 등록/변경
var _tmp_last_name="";
function setTLN(nm){
	_tmp_last_name=nm;
}
function upsertPriestAssign(frm, div_idx) {
	
	if( frm.appointment_start_date.value.trim()=="") { alert("임지 발령 시작 일자는 필수입니다."); frm.appointment_start_date.focus(); return false;}
	if( frm.appointment_start_date.value.trim().length < 8) { alert("임지 발령 일자는 10자리입니다."); frm.appointment_start_date.focus(); return false;}
	
	if( frm.appointment_end_date.value.trim()=="") { alert("임지 발령 종료 일자는 필수입니다."); frm.appointment_end_date.focus(); return false;}
	if( frm.appointment_end_date.value.trim().length < 8) { alert("임지 발령 일자는 10자리입니다."); frm.appointment_end_date.focus(); return false;}
	
	if( frm.org_idx.value == "" ) {  
		if( frm.org_lv1.options[frm.org_lv1.selectedIndex].value == "" ) { alert("기관을 선택해 주세요."); return false; }
		if( frm.org_lv2.options[frm.org_lv2.selectedIndex].value == "" ) { alert("상위 부서 선택은 필수입니다."); return false; }
		if( frm.p_position.options[frm.p_position.selectedIndex].value == "" ) { alert("직급 선택도 필수입니다."); return false; }
	}
	
	//
	var data1 = $(frm).serializeArray();
	/* if($(frm).p_idx.value=="") {
		atert("사제를 등록후에 임지 발령을 등록 할 수 있습니다.");
		return;
	} */
	/* alert(JSON.stringify(data1));
	var dataObj = {};
    $.each(data1, function() {
    	dataObj[this.name] = this.value;
    }); */
    //var formData = new FormData($("#formX1")[0]);
    var formData = new FormData($(frm));
    $.each(data1, function() {
    	formData.append(this.name, this.value);
    });
	
	_requestByAjax('/admin/board/priest_assing_upsert.do', formData, function(status, responseData){
				console.log("status="+status);
				console.log("responseData="+JSON.stringify(responseData));
				if(responseData.status=='error') {
					
				} else {
					
				}
				frm.idx.value= responseData.idx;
				$("#orgrow_name"+div_idx).text(_tmp_last_name);
				alert(responseData.message);
			});
}

function getAppendAssgin(div_seq, idx, registeredName) {
	
	var p_idx = '${admPriestContents.P_IDX}';
	if(p_idx=='') {
		alert("사제 등록 후, 임지 발령을 등록 할 수 있습니다.");
		return;
	}
	
	var orglv1_options = "<option value=''>기관 선택</option>";
	<c:if test="${fn:length(DEPART_LIST1) > 0}">
	<c:forEach items="${DEPART_LIST1}" var="list">
	orglv1_options += "<option value='${list.LV1}|${list.ORG_IDX}'>${list.NAME}</option>";
	</c:forEach>
	</c:if>
	
	var pos_options = "";
	<c:if test="${fn:length(POS_LIST) > 0}">
	<c:forEach items="${POS_LIST}" var="list">
	pos_options += "<option value='${list.POS_CODE}'>${list.POS_NAME}</option>";
	</c:forEach>
	</c:if>

	var newDiv = '  <div class="row" id="orgrow_'+div_seq+'" data-idseq="'+div_seq+'" style="text-aling:center; ">';
	newDiv +=  '    <form id="formX'+div_seq+'" name="formX'+div_seq+'" action="">';
	newDiv +=  '    <input type="hidden" id="idx" name="idx" value="'+idx+'">';
	newDiv +=  '    <input type="hidden" id="p_idx" name="p_idx" value="${admPriestContents.P_IDX}">';
	newDiv +=  '    <input type="hidden" id="org_idx" name="org_idx" value="">';
	newDiv +=  '    <div class="col-md-1">';
	//newDiv +=  '      <div>';
	newDiv +=  '      <button type="button" class="btn40 btn-warning pull-left" onclick="javascript: upsertPriestAssign(this.form, '+div_seq+'); return false;">등록</button>';
	newDiv +=  '      <button type="button" class="btn40 btn-warning pull-left" data-delseq="'+div_seq+'" onclick="javascript: deletePriestAssign(this.form, '+div_seq+','+idx+'); return false;">삭제</button>';
	//newDiv +=  '      </div>';
	newDiv +=  '    </div>';
	newDiv +=  '    <div class="col-md-1">';
	newDiv +=  '      <div id="orgrow_name'+div_seq+'">'+registeredName+'</div>';
	newDiv +=  '    </div>';
	newDiv +=  '    <div class="col-md-1">';
	newDiv +=  '      <div><label class="radio-inline" for=""><input type="checkbox" name="main_assign_yn" id="main_assign_yn" value="Y" alter="주임지여부" onclick="javascript: this.value=this.checked ? \'Y\':\'N\'; "></label></div>';
	newDiv +=  '    </div>';
	newDiv +=  '    <div class="col-md-1">';
	newDiv +=  '      <div ><select id="p_position" name="p_position" class="form-control input " >';
	newDiv +=  '      <option value="">직급</option>';
	newDiv += pos_options;
	newDiv +=  '      </select></div>';
	newDiv +=  '    </div>';
	newDiv +=  '    <div class="col-md-1">';
	newDiv +=  '      <div ><input type="text" id="appointment_start_date" name="appointment_start_date" value="" placeholder="임지시작일자 YYYY-MM-DD" class="form-control input " style="width:100px" /></div>';
	newDiv +=  '    </div>';
	newDiv +=  '    <div class="col-md-1">';
	newDiv +=  '      <div ><input type="text" id="appointment_end_date" name="appointment_end_date" value="" placeholder="임지종료일자 YYYY-MM-DD" class="form-control input "  style="width:100px" /></div>';
	newDiv +=  '    </div>';
	newDiv +=  '    <div class="col-md-2">';
	newDiv +=  '      <div ><select id="org_lv1" name="org_lv1" class="form-control input " onChange="javascript: setTLN(this.options[this.selectedIndex].text); loadOrgHeirarchyLV1('+div_seq+', this.options[this.selectedIndex].value, this.options[this.selectedIndex].text, this.form.org_lv2, this.form.org_lv3)">'
	newDiv += orglv1_options;
	newDiv +=  '      </select></div>';
	newDiv +=  '    </div>';
	newDiv +=  '    <div class="col-md-2">';
	newDiv +=  '      <div ><select id="org_lv2" name="org_lv2" class="form-control input " onChange="javascript: setTLN(this.options[this.selectedIndex].text); loadOrgHeirarchyLV2('+div_seq+', this.form.org_lv1.options[this.form.org_lv1.selectedIndex].value, this.options[this.selectedIndex].value, this.options[this.selectedIndex].text, this.form.org_lv3)">';
	newDiv +=  '      <option value="">상위 부서</option>';
	newDiv +=  '      </select></div>';
	newDiv +=  '    </div>';
	newDiv +=  '    <div class="col-md-2">';
	newDiv +=  '      <div ><select id="org_lv3" name="org_lv3" class="form-control input " onChange="javascript: setTLN(this.options[this.selectedIndex].text);" >';
	newDiv +=  '      <option value="">부서</option>';
	newDiv +=  '      </select></div>';
	newDiv +=  '    </div>';
	newDiv +=  '    </form>';
	newDiv +=  '  </div>';
	
	return newDiv;
}

var __assign_div_count = 1;
function appendAssign() {
	if(__assign_div_count>8) {
		alert('현재 겸직이 너무 많습니다.');
		return;
	}
	
	// html append 방식 , close방식도 있음. 
	var cloneDivHTML = getAppendAssgin(__assign_div_count, '', '');
	$(cloneDivHTML).appendTo( $('#assign_table') );
	
	//
	var fmnm1 = "#formX"+__assign_div_count + " #appointment_start_date";
	var fmnm2 = "#formX"+__assign_div_count + " #appointment_end_date";
	$(fmnm1).datepicker();
	$(fmnm2).datepicker();
	
	__assign_div_count++;
}


<c:if test="${fn:length(admOrgPriestRel)>0}">
<c:forEach items="${admOrgPriestRel}" var="list">
	//
	regDivHTML = getAppendAssgin(__assign_div_count, '${list.IDX}', '${list.ORG_NAME}');
	$(regDivHTML).appendTo( $('#assign_table') );
	$("#formX"+__assign_div_count + " #org_idx").val("${list.ORG_IDX}");
	$("#formX"+__assign_div_count + " #appointment_start_date").val("${list.APPOINTMENT_START_DATE}");
	$("#formX"+__assign_div_count + " #appointment_end_date").val("${list.APPOINTMENT_END_DATE}");
	$("#formX"+__assign_div_count + " #appointment_start_date").datepicker();
	$("#formX"+__assign_div_count + " #appointment_end_date").datepicker();
	$("#formX"+__assign_div_count + " #p_position").val("${list.P_POSITION}").prop("selected", true);
	
	<c:set var="is_main_checked" value="false" />
	<c:if test="${list.MAIN_ASSIGN_YN eq 'Y'}"><c:set var="is_main_checked" value="true" /></c:if>
	$("#formX"+__assign_div_count + " #main_assign_yn").val("${list.MAIN_ASSIGN_YN}").attr("checked", ${is_main_checked});
	//$("#orgrow_name"+__assign_div_count).text("${list.ORG_NAME}");
	
	__assign_div_count++;
	
</c:forEach>
</c:if>


</script>
