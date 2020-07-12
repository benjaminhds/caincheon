<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%@ 
	include file="/admin/_common/inc/head.jsp" %><%
	
	
	// id가 Null 이거나 없을 경우 -> 로그인페이지로 이동
	if(admSessionMemId==null||admSessionMemId.equals("")) {
		response.sendRedirect("/admin/login.jsp");
	}
%>


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
						<h3 class="page-header">임지코드관리</h3>
						</div>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- //.row(page title end -->
            <div class="page-list">
            	<div class="row">
	            	<div class="col-lg-6 panel panel-default">
	                    <!-- /.panel-heading -->
			            <div class="panel-body" style="overflow-x:hidden;overflow-y:scroll;width:100%;height:700px;">


<!-- new js tree ui -->
<script type="text/javascript" src="../_common/js/bjm_map.js"></script>
<script type="text/javascript" src="../_common/js/code-organization-tree-v0.1.js"></script>
<script type="text/javascript">
function initForm() {
	document.orgMgnt.reset();
	$("#org_parent_name").html("");
	$("#span_orglv1").html("");
	$("#span_orglv2").html("");
	$("#btnNew").html("<font color='black'>신규</font>");
	$("#btnUpd").show();
	$("#btnDel").show();
	$("#btnNew").html("신규");
	$("#btnNew").removeClass("btn-success");
	__newOrgMode = false;
}
var __newOrgMode = false;
function newOrg() {
	if($("#org_lv1").val()=="") {
		alert("상위 조직이 선택되지 않았습니다."); return;
	}
	if(__newOrgMode == false) {
		__newOrgMode = true;
		$("#org_parent_name").html($("#org_parent_name").html() + " > "+ $("#org_name").val());
		$("#btnNew").addClass("btn-success");
		$("#btnUpd").hide();
		$("#btnDel").hide();
		$("#org_name").val("");
		$("#org_lv3").val("");
		$("#btnNew").html("추가");
		return;
	}
	if($("#org_lv1").val()=="") {
		alert("추가하려는 상위 조직을 선택해 주세요."); return;
	}
	if($("#org_name").val()=="") {
		alert("조직명을 입력해 주세요.");  $("#org_name").focus();  return;
	}
	if($("#org_lv3").val()=="") {
		if(confirm("입력되지 않아 자동 부여 하시겠습니까? \n  확인 - 자동 부여\n  취소 - 직접 입력")) {
			$("#org_lv3").val("000");
		} else {
			$("#org_lv3").focus();  return;
		}
	}
	
	var vfm = document.orgMgnt;
	vfm.org_mode.value="I";
	vfm.submit();
}
function updOrg() {
	if($("#org_name").val()=="") {
		alert("조직명을 입력해 주세요.");  $("#org_name").focus();  return;
	}
	if($("#org_lv3").val()=="") {
		alert("조직 코드를 입력해 주세요.");  $("#org_lv3").focus();  return;
	}
	//if($("#org_orderno").val()=="") {
		//alert("정렬 순서를 입력해 주세요.");  $("#org_orderno").focus();  return;
	//}
	var vfm = document.orgMgnt;
	vfm.org_mode.value="U";
	vfm.submit();
}
function delOrg() {
	var childrens = $("#selected_org_info").data("childrens");
	var del_yn    = $("#selected_org_info").data("del_yn");
	
	var vfm = document.orgMgnt;
	
	if(del_yn=="Y") {
		if(confirm("사용하도록 복원 하시겠습니까 ?")) {
			vfm.org_mode.value="R";
			vfm.submit();
		} else {
			return;
		}
	} else {
		if(childrens > 0) {
			alert("현재 하위 조직이 있어 선택한 조직은 삭제 할 수 없습니다.");
			return;
		}
		if(!confirm("현재 선택된 조직을 삭제 하시겠습니까?")) {
			return;
		}
		vfm.org_mode.value="D";
		vfm.submit();
	}
	
}
</script>
<script type="text/javascript">
	/* SAMPLE_TREE.print(); */
	function caincheon_org_tree(){ /* Tree-Factory */
		var root = new Tree( "ROOT", "[인천 교구 전체 조직 구조]", "00", "00", "000", 0, "N" ); /* ROOT-Node is not exists in DB */
	
	/* 1level node */
	<c:if test="${fn:length(rtn_groupby1) > 0}">
	  <c:forEach items="${rtn_groupby1}" var="row">
	    root.add( new Tree( "${row.LV1}", "${row.NAME}", "${row.LV1}", "00", "000", ${row.ORG_IDX}, "${row.DEL_YN}" ) );/* LV1 CHILD_COUNT:${row.CHILD_COUNT}*/
	    var node${row.LV1} = root.nodes["${row.LV1}"];
	  </c:forEach>
	</c:if>
		root.visible = true;
	
	/* 2level node */
	<c:if test="${fn:length(rtn_groupby2) > 0}">
	  <c:forEach items="${rtn_groupby2}" var="row">
	    <c:if test="${row.LV2 ne '00'}">node${row.LV1}.add( new Tree( "${row.LV1}${row.LV2}", "${row.NAME}", "${row.LV1}", "${row.LV2}", "000", ${row.ORG_IDX}, "${row.DEL_YN}" ) ); </c:if>/* LV2 CHILD_COUNT:${row.CHILD_COUNT} */
	    <c:if test="${row.CHILD_COUNT > 0}">var node${row.LV1}${row.LV2} = node${row.LV1}.nodes["${row.LV1}${row.LV2}"];</c:if>
	  </c:forEach>
	</c:if>
	
	/* 3level node */
	<c:if test="${fn:length(rtn_list) > 0}">
	  <c:forEach items="${rtn_list}" var="row">
	  node${row.LV1}${row.LV2}.add( new Tree( "${row.LV1}${row.LV2}${row.LV3}", "${row.NAME}" , "${row.LV1}","${row.LV2}","${row.LV3}", ${row.ORG_IDX}, "${row.DEL_YN}" ) );</c:forEach>
	</c:if>
	
	return root;
}
var SAMPLE_TREE = caincheon_org_tree();
SAMPLE_TREE.print();
</script>
            
			              
						</div>
					<!-- panel panel-default -->
				    </div>
				    <div class="col-lg-6 panel panel-default">
	                    <!-- /.panel-heading -->
			            <div class="panel-body" style="width:100%;height:360px;">
			            	<p></p>
				            <label for="popupContextMenu"><font color=blue>[선택 조직 정보]</font></label><br>
							<div id="selected_org_info" ></div>
							<p></p>
							<div id="popupContextMenu" data-treeid="" data-parentTreeid="">
								<form id="orgMgnt" name="orgMgnt" action="/admin/org/org_code_mgmtUp.do" method="post" class="form-inline">
								<input type="hidden" id="org_idx"  name="org_idx"  value="" placeholder="idx">
								<input type="hidden" id="org_mode" name="org_mode" value="" placeholder="mode">
								<input type="hidden" id="org_lv1"  name="org_lv1"  value="" >
								<input type="hidden" id="org_lv2"  name="org_lv2"  value="" >
								
								<p><div class="form-group">
									<label for="popupContextMenu">상위조직명 : </label>&nbsp;
									<span id="org_parent_name"></span>
								</div></p>
								
								<p>
								<div class="form-group">
									<label for="popupContextMenu">조 직 명 : </label>&nbsp;
									<input type="text" id="org_name" name="org_name" value="" class="form-control" placeholder="조직명입니다."> 
								</div>
								</p>
								
								<p>
								<div class="form-group">
									<label for="popupContextMenu">조직코드 : </label>&nbsp;
									<span id="span_orglv1" ></span><span id="span_orglv2" ></span>
									<input type="text" id="org_lv3" name="org_lv3" value="" class="form-control" placeholder="조직코드입니다.">
								</div>
								</p>
								
								<p style="display:none">
									<div class="form-group">
										<label for="popupContextMenu">순 서 : </label>&nbsp;
										<input type="text" id="org_orderno" name="org_orderno" value="" class="form-control" placeholder="정렬순서입니다.">
									</div>
									</p>
									<p>
									<div class="btn-group"><button type="button" id="btnInit" class="btn btn-default" onclick="javascript: initForm();">초기화</button></div>
									<div class="btn-group"><button type="button" id="btnNew"  class="btn btn-default" onclick="javascript: newOrg();">신규</button></div> 
									<div class="btn-group"><button type="button" id="btnUpd"  class="btn btn-default" onclick="javascript: updOrg();">수정</button></div>
									<div class="btn-group"><button type="button" id="btnDel"  class="btn btn-default" onclick="javascript: delOrg();">삭제</button></div>
									</div>
								</p>
								</form>
							</div>
			            
			            </div>
			        </div>
		        </div>
			<!-- col-lg-12 -->
		    </div>						
			<!-- row -->
		
        <!-- /#page-wrapper -->

    </div>
    
    <!-- /#wrapper -->
</form>

<script type="text/javascript">
$(document).ready(function() {
	onLoadLeftMenu('org_03');
});
</script>

<script>
<c:if test = "${fn:length(ERR_MSG) > 0 }">
	// Error Handling
	setTimeout( alert("${ERR_MSG}") , 1000 );
</c:if>
</script>

</body>

</html>
