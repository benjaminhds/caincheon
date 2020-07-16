-<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/admin/_common/inc/head.jsp" %>
<%
if(admSessionMemId==null||admSessionMemId.equals("")) {                // id가 Null 이거나 없을 경우
	response.sendRedirect("/admin/login.jsp");    // 로그인 페이지로 리다이렉트 한다.
}
%>
<script type="text/javascript">

/* 버튼 이벤트 */
function addButtonEvent() {
	
	$(".i_sAdd").on("click",function(){
		
		var cgListText	=	$("#i_sCgList option:checked").text();
		var cgListVal	=	$("#i_sCgList option:checked").val();
		var booCt		=	false;
		/*카테고리를 val 이 없으면*/
		if(cgListText == "선택") {
			alert("카테고리를 선택해주세요.");
			return;
		}
		
		/*카테고리를 val 이 없으면*/
		$(".btn_del_box").each(function(){
			
			if(trim($(this).text()) == trim(cgListText)) {
				alert("중복된 카테고리 입니다.");
				booCt	=	true;
				return;
			}
		});
		
		/**/
		if(booCt) {
			return;
		}
		
		/* 버튼 추가 */
		var btn	=	$("<button>");
		
		btn.attr("name","i_arrCgList");
		btn.attr("type","button");
		btn.addClass("btn");
		btn.addClass("btn_del_box");
		btn.css("margin","5px 5px 5px 5px");
		btn.css("float","left");
		btn.text(cgListText + " ");
		
		var ai	=	$("<i>");
		ai.addClass("fa fa-times");
		
		btn.append(ai);
		
		var hInput = $("<input>");
		hInput.attr("type","hidden");
		hInput.attr("name","i_arrCgNameList");
		hInput.val(cgListText);
		
		var hInput2 = $("<input>");
		hInput2.attr("type","hidden");
		hInput2.attr("name","i_arrCgList");
		hInput2.val(cgListVal);
		
		btn.append(hInput);
		btn.append(hInput2);
		
		$(".categoryarea").append(btn);
	});
	
	/*버튼 자르기*/
	$(document).on("click",".btn_del_box",function(){
		$(this).remove();
	});
}

function viewList() {
	var vfm = document.searchForm;
	vfm.submit();
	return false;
}

function insert_contents() {
	if( $("#i_sTitle").val() == "") { alert("게시판 제목을 입력해 주세요."); $("#i_sTitle").focus(); return; }
	if( $("#i_sBoardKind").val() == "") { alert("게시판 유형을 입력해 주세요."); $("#i_sBoardKind").focus(); return; }
	
	document.getElementById('mode').value = "i";
	
	$.ajax({		 
		type:"POST"
		, url:"/n/admin/board/board_cud.do"
		, data:$("#form01").serialize()
		, dataType: "json"
		, cache: false
		, success:function(){
			alert("저장되었습니다.");
			
			var vfm = document.searchForm;
			vfm.submit();
			return false;
		}
		, error:function(xhr, textStatus) {
			alert("에러 입니다. 관리자에게 문의해주세요.");
		}
	});	
	
}

function modify_contents() {
	if( $("#i_sTitle").val() == "") { alert("게시판 제목을 입력해 주세요."); $("#i_sTitle").focus(); return;}
	if( $("#i_sBoardKind").val() == "") { alert("게시판 유형을 입력해 주세요."); $("#i_sBoardKind").focus(); return;}
	
	document.getElementById('mode').value = "u";
	
	$.ajax({		 
		type:"POST"
		, url:"/n/admin/board/board_cud.do"
		, data:$("#form01").serialize()
		, dataType: "json"
		, cache: false
		, success:function(){
			alert("저장되었습니다.");
			
			var vfm = document.searchForm;
			vfm.submit();
			return false;
		}
		, error:function(xhr, textStatus) {
			alert("에러 입니다. 관리자에게 문의해주세요.");
		}
	});	
}

function delete_contents() {
	document.getElementById('mode').value = "d";
	
	$.ajax({		 
		type:"POST"
		, url:"/n/admin/board/board_cud.do"
		, data:$("#form01").serialize()
		, dataType: "json"
		, cache: false
		, success:function(){
			alert("삭제되었습니다.");
			
			var vfm = document.searchForm;
			vfm.submit();
			return false;
		}
		, error:function(xhr, textStatus) {
			alert("에러 입니다. 관리자에게 문의해주세요.");
		}
	});	
}

</script>
<script src="http://malsup.github.com/jquery.form.js"></script> 

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
					<c:when test="${query_type == 'insert'}">
						<h3 class="page-header">게시판 등록</h3>
					</c:when>
					<c:otherwise>
						<h3 class="page-header">게시판 수정</h3>	
					</c:otherwise>
					</c:choose>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
<form id="searchForm" name="searchForm" method="POST" action="/n/admin/board/board_list.do">
	<input type="hidden" name="i_sText"	id="i_sText"	value="${_params.i_sText}"/>
</form>
<form id="form01" name="form01" enctype="multipart/form-data" method="POST" action="/admin/board/par_list.do">
 <input type="hidden" name="i_sBidx"	id="i_sBidx"	value="${bd_content.B_IDX}"/>
 <input type="hidden" name="user_id"	id="user_id"	value="${admSessionMemId}"/>
 <input type="hidden" name="mode"		id="mode"		value=""/>
 <input type="hidden" name="i_sText"	id="i_sText"	value=""/>
 				
 				<!-- Contents : Begin //-->
				<div class="page-form">
					<div class="panel panel-default">
						<div class="panel-body">
							
							<table class="table tbl-form">
								<colgroup>
									<col style="width:20%;">
									<col style="width:30%;">
									<col style="width:20%;">
									<col style="width:30%;">
								</colgroup>
								<tbody>
									<tr>
										<th>작성자</th>
										<td>
											<%=admSessionMemNm %>
										</td>
									</tr>	
									<tr>
										<th>게시판 제목</th>
										<td colspan="3">
											<input class="form-control" type="text" name="i_sTitle" id="i_sTitle" value="${bd_content.B_NM }">
										</td>
									</tr>
									<tr>
										<th>게시판 종류</th>
										<td>
											<select class="form-control" name="i_sBoardKind" id="i_sBoardKind">
												<option value="">선택</option>
												<c:forEach items="${boardkind_list}" var="list">
													<option value="${list.CODE_INST}"  <c:if test="${list.CODE_INST eq bd_content.B_KIND}">selected</c:if>>${list.NAME}</option>
												</c:forEach>
											</select>
											
										</td>
									</tr>
									<tr>
										<th>게시판 유형</th>
										<td>
											<select class="form-control" name="i_sBoardType" id="i_sBoardType">
												<option value="">선택</option>
												<c:forEach items="${boardtype_list}" var="list">
													<option value="${list.CODE_INST}"  <c:if test="${list.CODE_INST eq bd_content.B_TYPE}">selected</c:if>>${list.NAME}</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<th>카테고리 <button type="button" name="i_sAdd" class="i_sAdd" id="i_sAdd">추가</button></th>
										<td>
											<select class="form-control" name="i_sCgList" id="i_sCgList">
												<option value="">선택</option>
												<c:forEach items="${cateCodegory_list}" var="list">
													<option value="${list.CODE_INST}">${list.NAME}</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<td class="categoryarea" colspan="2">
											<c:forEach items="${category_list}" var="list">
												<button name="i_arrCgList" type="button" class="btn btn_del_box" style="margin: 5px; float: left;">${list.name} <i class="fa fa-times"></i>
													<input type="hidden" name="i_arrCgNameList" value="${list.name}">
													<input type="hidden" name="i_arrCgList" value="${list.c_idx}">
												</button>
											</c:forEach>
										</td>
									</tr>
									<tr>
										<th>댓글사용여부</th>
										<td>
											<label class="radio-inline" for="i_sUseYnComment_Y"><input type="radio" name="i_sUseYnComment" id="i_sUseYnComment_Y" value="Y" <c:if test="${bd_content == null || bd_content.USEYN_COMMENT=='Y' }" >checked="checked"</c:if>>Y</label>
											<label class="radio-inline" for="i_sUseYnComment_N"><input type="radio" name="i_sUseYnComment" id="i_sUseYnComment_N" value="N" <c:if test="${bd_content.USEYN_COMMENT=='N' }" >checked="checked"</c:if>>N</label>
										</td>
									</tr>
									<tr>
										<th>암호사용여부</th>
										<td>
											<label class="radio-inline" for="i_sUseYnSecret_Y"><input type="radio" name="i_sUseYnSecret" id="i_sUseYnSecret_Y" value="Y" <c:if test="${bd_content == null || bd_content.USEYN_SECRET=='Y' }" >checked="checked"</c:if>>Y</label>
											<label class="radio-inline" for="i_sUseYnSecret_N"><input type="radio" name="i_sUseYnSecret" id="i_sUseYnSecret_N" value="N" <c:if test="${bd_content.USEYN_SECRET=='N' }" >checked="checked"</c:if>>N</label>
										</td>
									</tr>
									<tr>
										<th>댓글암호사용여부</th>
										<td>
											<label class="radio-inline" for="i_sUseYnCommentSecret_Y"><input type="radio" name="i_sUseYnCommentSecret" id="i_sUseYnCommentSecret_Y" value="Y" <c:if test="${bd_content == null || bd_content.USEYN_COMMENT_SECRET=='Y' }" >checked="checked"</c:if>>Y</label>
											<label class="radio-inline" for="i_sUseYnCommentSecret_N"><input type="radio" name="i_sUseYnCommentSecret" id="i_sUseYnCommentSecret_N" value="N" <c:if test="${bd_content.USEYN_COMMENT_SECRET=='N' }" >checked="checked"</c:if>>N</label>
										</td>
									</tr>
									<tr>
										<th>첨부사용여부</th>
										<td>
											<label class="radio-inline" for="i_sUseYnAttachMent_Y"><input type="radio" name="i_sUseYnAttachMent" id="i_sUseYnAttachMent_Y" value="Y" <c:if test="${bd_content == null || bd_content.USEYN_ATTACHEMENT=='Y' }" >checked="checked"</c:if>>Y</label>
											<label class="radio-inline" for="i_sUseYnAttachMent_N"><input type="radio" name="i_sUseYnAttachMent" id="i_sUseYnAttachMent_N" value="N" <c:if test="${bd_content.USEYN_ATTACHEMENT=='N' }" >checked="checked"</c:if>>N</label>
										</td>
									</tr>
									<tr>
										<th>로그인사용여부</th>
										<td>
											<label class="radio-inline" for="i_sUseYnLogin_Y"><input type="radio" name="i_sUseYnLogin" id="i_sUseYnLogin_Y" value="Y" <c:if test="${bd_content == null or bd_content.USEYN_LOGIN == 'Y' }" >checked="checked"</c:if>>Y</label>
											<label class="radio-inline" for="i_sUseYnLogin_N"><input type="radio" name="i_sUseYnLogin" id="i_sUseYnLogin_N" value="N" <c:if test="${bd_content.USEYN_LOGIN=='N' }" >checked="checked"</c:if>>N</label>
										</td>
									</tr>
									<tr>
										<th>첨부다운로드권한</th>
										<td>
											<label class="radio-inline" for="i_sUseYnDownloadPerm_Y"><input type="radio" name="i_sUseYnDownloadPerm" id="i_sUseYnDownloadPerm_Y" value="Y" <c:if test="${bd_content == null || bd_content.USEYN_DOWNLOAD_PERM=='Y' }" >checked="checked"</c:if>>Y</label>
											<label class="radio-inline" for="i_sUseYnDownloadPerm_N"><input type="radio" name="i_sUseYnDownloadPerm" id="i_sUseYnDownloadPerm_N" value="N" <c:if test="${bd_content.USEYN_DOWNLOAD_PERM=='N' }" >checked="checked"</c:if>>N</label>
										</td>
									</tr>
									<tr>
										<th>소식메뉴 VIEW 여부</th>
										<td>
											<label class="radio-inline" for="i_sViewFlag_Y"><input type="radio" name="i_sViewFlag" id="i_sViewFlag_Y" value="Y" <c:if test="${bd_content == null || bd_content.VIEW_FLAG=='Y' }" >checked="checked"</c:if>>Y</label>
											<label class="radio-inline" for="i_sViewFlag_N"><input type="radio" name="i_sViewFlag" id="i_sViewFlag_N" value="N" <c:if test="${bd_content.VIEW_FLAG=='N' }" >checked="checked"</c:if>>N</label>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<!-- Buttons : Begin //-->
					<p class="pull-right">
					<button type="button" id="btnList" style="margin-left:4px;" class="btn btn-default btn-lg" onclick="javascript:viewList();return false;"><i class="fa fa-th-list"></i>목록</button>
					<c:choose>
					<c:when test="${query_type == 'insert'}">
						<button type="button" id="btnInsert" class="btn btn-primary btn-lg" onclick="javascript:insert_contents();return false;"><i class="fa fa-check"></i>저장</button>	
					</c:when>
					<c:otherwise>
						<button type="button" id="btnModify" style="margin-left:4px;" class="btn btn-primary btn-lg" onclick="javascript:modify_contents();return false;"><i class="fa fa-check"></i>수정</button>
						<button type="button" id="btnDelete" style="margin-left:4px;" class="btn btn-danger btn-lg" onclick="javascript:delete_contents();return false;"><i class="fa fa-times"></i>삭제</button>	
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
<script> 


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
	// menu 선택되게 처리
	onLoadLeftMenu('board_01');
	// 버튼이벤트
	addButtonEvent();
	
	// calendar
	$("#event_date").datepicker();
	$("input.event_date").datepicker().attr("maxlength", 10);
	
}

</script>
<form id="form02" name="form02" method="POST" action="/admin/board/par_list.do">
 <input type="hidden" name="bl_idx" id="bl_idx" value="${CONTENTS.BL_IDX}"/>
</form>
</html>