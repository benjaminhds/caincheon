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
<script type="text/javascript">
function chgStartDate() {
	var start_date = document.getElementById('start_date').value;
	var end_date   = document.getElementById('end_date').value;
	
	if(!end_date) {
		document.getElementById('end_date').value = start_date;
	}
	return false;
}

function viewList() {
	if($("#list_type").val()=="calendar") {
		document.form01.action = '/admin/board/sch_cal.do';
	}
	else {
		document.form01.action = '/admin/board/sch_list.do';
	}
	
	//document.getElementById('idx').value=idx;
	document.form01.submit();
	return false;
}

function insert_contents() {
	if( $(":input:checkbox[name=gubun]:checked").val() == undefined) { alert("교구장/총대리를 선택해 주세요."); $("#gubun").focus(); return; }
	//if( $("#part").val() == "") { alert("주관을 선택해 주세요."); $("#part").focus(); return; }
	if( $("#title").val() == "") { alert("제목을 입력해 주세요."); $("#title").focus(); return; }
	if( $("#start_date").val() == "") { alert("기간을 입력해 주세요."); $("#start_date").focus(); return; }
	if( $("#end_date").val() == "") { alert("기간을 입력해 주세요."); $("#end_date").focus(); return; }
	if( $(":input:radio[name=displayType]:checked").val() == undefined) { alert("노출여부를 선택해 주세요."); $("#displayType1").focus(); return; }
	
	document.form01.action = '/admin/board/sch_insert.do';
	document.form01.submit();
	return false;
}

function modify_contents(s_idx) {
	if( $(":input:checkbox[name=gubun]:checked").val() == undefined) { alert("교구장/총대리를 선택해 주세요."); $("#gubun").focus(); return; }
	//if( $("#part").val() == "") { alert("주관을 선택해 주세요."); $("#part").focus(); return; }
	if( $("#title").val() == "") { alert("제목을 입력해 주세요."); $("#title").focus(); return; }
	if( $("#start_date").val() == "") { alert("기간을 입력해 주세요."); $("#start_date").focus(); return; }
	if( $("#end_date").val() == "") { alert("기간을 입력해 주세요."); $("#end_date").focus(); return; }
	if( $(":input:radio[name=displayType]:checked").val() == undefined) { alert("노출여부를 선택해 주세요."); $("#displayType1").focus(); return; }
	
	document.form01.action = '/admin/board/sch_modify.do';
	document.getElementById('s_idx').value = s_idx;
	document.form01.submit();
	return false;
}

function delete_contents(s_idx) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/board/sch_delete.do';
		document.getElementById('s_idx').value = s_idx;
		document.form01.submit();
	}
	return false;
}

function setDisplay(radio, value) {
	if ( radio.checked == true ) {
		if(value == "Y") {
		//	alert('1');
			document.form01.displayType[1].checked = false;
		}
		else if(value=="N") {
		//	alert('0');
			document.form01.displayType[0].checked = false;
		}
		
		document.getElementById('displayYN').value=value;
	}
	return false;
}

window.onload = function() {
	onLoadLeftMenu('etc_03');
	
	CKEDITOR.replace( 'content', {
    	filebrowserUploadUrl: '/admin/board/etcImageUpload.do'
  	});
}

$(document).ready(function() {
    //라디오 요소처럼 동작시킬 체크박스 그룹 셀렉터
    $('input[type="checkbox"][name="gubun"]').click(function(){
        //클릭 이벤트 발생한 요소가 체크 상태인 경우
        if ($(this).prop('checked')) {
            //체크박스 그룹의 요소 전체를 체크 해제후 클릭한 요소 체크 상태지정
            $('input[type="checkbox"][name="gubun"]').prop('checked', false);
            $(this).prop('checked', true);
        }
        else {
        	// 교구장이나 총대리중 아무것도 선택되지 않을때, 주관에 있는 코드를 삭제한다.
        	$("#part").val("00000").attr("selected", "selected");
        }
    });
});


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

$(function() {
    $("#start_date").datepicker();
    $("#end_date").datepicker();
});


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
					<h3 class="page-header">교구일정 <c:if test="${fn:length(schContents.GUBUN_CODE) > 0}">수정</c:if><c:if test="${fn:length(schContents.GUBUN_CODE) == 0}">등록</c:if></h3>
					</div>
				<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
  	
  	<form class="well" name="form01" action="/admin/board/sch_list.do" method="POST">
  	<input type="hidden" name="displayYN" id="displayYN" value="${schContents.DISPLAYYN}"/>
  	<input type="hidden" name="s_idx" id="s_idx" value=""/>
  	<input type="hidden" name="list_type" id="list_type" value="${_params.list_type}"/>
  	
  	<div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">교구장/총대리</label>
    		</div>
    		<div class="col-md-10" >
    			<c:if test="${schContents.GUBUN_CODE eq '1'}"><c:set var="gubun1" value="checked" /></c:if>
    			<c:if test="${schContents.GUBUN_CODE eq '2'}"><c:set var="gubun2" value="checked" /></c:if>
    			<c:if test="${schContents.GUBUN_CODE eq '3'}"><c:set var="gubun3" value="checked" /></c:if>
    			<c:if test="${schContents.GUBUN_CODE eq '4'}"><c:set var="gubun4" value="checked" /></c:if>
    			
    			<label class="checkbox-inline">
			      <input type="checkbox" name="gubun" id="gubun" value="1" ${gubun1} >(교구장/총대리)
			    </label>
			    <label class="checkbox-inline">
			      <input type="checkbox" name="gubun" id="gubun" value="2" ${gubun2} >교구장
			    </label>
			    <label class="checkbox-inline">
			      <input type="checkbox" name="gubun" id="gubun" value="3" ${gubun3} >총대리
			    </label>
			    <label class="checkbox-inline">
			      <input type="checkbox" name="gubun" id="gubun" value="4" ${gubun4} >(-) <!-- 교구(1) or 부서(1이외) -->
			    </label>
			    
    		</div>
    	</div>
    </div>   
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">주관</label>
    		</div>
    		<div class="col-md-10">
    			<select name="org_idx" id="org_idx">
					<option value="">선택</option>
					<option value="1">교구</option>
					<c:if test="${fn:length(_1x00xList) > 0}">
						<c:forEach var="list" items="${_1x00xList}" varStatus="status">
							<option value="${list.ORG_IDX}" <c:if test = "${list.ORG_IDX==schContents.ORG_IDX }"> selected </c:if>>${list.NAME}</option>
						</c:forEach>
					</c:if>
				</select>
    		</div>
    	</div>
    </div>   
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">제목</label>
    		</div>
    		<div class="col-md-10" >
    			<input type="text" class="form-control" id="title" placeholder="" name="title" value="${schContents.TITLE}">		
    		</div>
    	</div>
    </div>  
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2">
    			<label for="inputName">기간</label>
    		</div>
    		<div class="col-md-4">
    			<input class="cal-month" type="text" id="start_date" name="start_date" value="${schContents.START_DATE}" onchange="chgStartDate()">		
    		</div>
    		<div class="col-md-2">
    			<label for="inputName">~</label>
    		</div>
    		<div class="col-md-4">
    			<input class="cal-month" type="text" id="end_date" name="end_date" value="${schContents.END_DATE}" >		
    		</div>
    	</div>
    </div>
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">내용</label>
    		</div>
    		<div class="col-md-10" >
    			<textarea id="content" name="content" cols="80" rows="10">${schContents.CONTENT}
        		</textarea>		
    		</div>
    	</div>
    </div>   
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">노출</label>
    		</div>
    		<div class="col-md-10" >
    			<c:choose>
		      		<c:when test="${schContents.DISPLAYYN == 'Y'}">
		        		<label class="radio-inline"><input type="radio" name="displayType" id="displayType1" value="Y" onClick="setDisplay(this,'Y')" checked="checked">노출</label>
		        		<label class="radio-inline"><input type="radio" name="displayType" id="displayType2" value="N" onClick="setDisplay(this,'N')">비노출</label>
		        	</c:when>
		        	<c:otherwise>
		        		<label class="radio-inline"><input type="radio" name="displayType" id="displayType1" value="Y" onClick="setDisplay(this,'Y')">노출</label>
		        		<label class="radio-inline"><input type="radio" name="displayType" id="displayType2" value="N" onClick="setDisplay(this,'N')" checked="checked">비노출</label>
		        	</c:otherwise>	
		       	</c:choose>		
    		</div>
    	</div>
    </div>  
    
    	
</form>
	<p>
<button type="button" id="btnList"   class="btn btn-default pull-right" onclick="javascript:viewList();return false;">목록</button>
<c:choose>
<c:when test="${_params.query_type == 'insert'}">
	<button type="button" id="btnInsert" class="btn btn-default pull-right" onclick="javascript:insert_contents();return false;">등록</button>
</c:when>
<c:otherwise>
	<button type="button" id="btnModify" class="btn btn-default pull-right" onclick="javascript:modify_contents('${schContents.S_IDX}');return false;">수정</button>
	<button type="button" id="btnDelete" class="btn btn-default pull-right" onclick="javascript:delete_contents('${schContents.S_IDX}');return false;">삭제</button>
</c:otherwise>
</c:choose>
</p>

    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->
</body>

</html>