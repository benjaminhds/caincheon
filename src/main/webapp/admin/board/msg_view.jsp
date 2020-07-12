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

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script type="text/javascript">
function viewList() {
	document.form01.action = '/admin/board/msg_list.do';
	document.form01.submit();
	return false;
}

function insert_contents() {
	if( $("#type").val() == "") { alert("메시지 구분을 선택해 주세요."); $("#type").focus(); return; }
	if( $("#title").val() == "") { alert("제목을 입력해 주세요."); $("#title").focus(); return; }
	//if( $("#mdate").val() == "") { alert("서한날짜를 입력해 주세요."); $("#mdate").focus(); return; }
	//if( $(":input:radio[name=displayYN]:checked").val() == undefined) { alert("노출여부를 선택해 주세요."); $("#displayYN1").focus(); return; }
	if( $("#displayYN").val() == 0 ) { alert("노출여부를 선택해 주세요."); $("#displayYN1").focus(); return; }
	document.form01.action = '/admin/board/msg_insert.do';
	document.form01.submit();
	return false;
}

function modify_contents(m_idx) {
	if( $("#type").val() == "") { alert("메시지 구분을 선택해 주세요."); $("#type").focus(); return; }
	if( $("#title").val() == "") { alert("제목을 입력해 주세요."); $("#title").focus(); return; }
	//if( $("#mdate").val() == "") { alert("서한날짜를 입력해 주세요."); $("#mdate").focus(); return; }
	//if( $(":input:radio[name=displayYN]:checked").val() == undefined) { alert("노출여부를 선택해 주세요."); $("#displayYN1").focus(); return; }
	if( $("#displayYN").val() == 0 ) { alert("노출여부를 선택해 주세요."); $("#displayYN1").focus(); return; }
	document.form01.action = '/admin/board/msg_modify.do';
	document.getElementById('m_idx').value = m_idx;
	document.form01.submit();
	return false;
}

function delete_contents(m_idx) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/board/msg_delete.do';
		document.getElementById('m_idx').value = m_idx;
		document.form01.submit();
	}
	return false;
}

function setDisplay(radio, value) {
	$("#displayYN").val(value);
	/*
	if ( radio.checked == true ) {
		if(value == "Y") {
			document.form01.displayYN[1].checked = false;
		} else if(value=="N") {
			document.form01.displayYN[0].checked = false;
		}
		document.getElementById('displayYN').value=value;
	}*/
	return false;
}

window.onload = function() {
	onLoadLeftMenu('etc_01');

    $("#mdate").datepicker();
	$("input.mdate").datepicker().attr("maxlength", 10);
	
	CKEDITOR.replace( 'content', {
    	filebrowserUploadUrl: '/admin/board/etcImageUpload.do'
  	});
}

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
  	<h3 class="page-header">메시지 등록</h3>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->

  	<form class="well" name="form01" action="/admin/board/msg_list.do" method="POST">
  	<input type="hidden" name="displayYN" id="displayYN" value="${msgContents.DISPLAYYN}"/>
  	<input type="hidden" name="m_idx" id="m_idx" value=""/> 	
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">구분</label>
    		</div>
    		<div class="col-md-10">
    			<select name="type" id="type">
					<option value="">선택</option>
		    	  	<option value="1" <c:if test="${msgContents.TYPE=='1'}"> selected </c:if> >교서</option>
					<option value="2" <c:if test="${msgContents.TYPE=='2'}"> selected </c:if> >서한</option>
					<option value="3" <c:if test="${msgContents.TYPE=='3'}"> selected </c:if> >담화문</option>
				</select>    		
    		</div>
    	</div>
    </div>   
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">공지글</label>
    		</div>
    		<div class="col-md-10" >
    			<div class="checkbox">
					<label><input type="checkbox" name="noticeYN" id="noticeYN" value="Y" <c:if test="${msgContents.NOTICEYN=='Y'}"> checked </c:if> >게시글을 최상단에 고정 노출함</label>
				</div>    		
    		</div>
    	</div>
    </div> 
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">제목</label>
    		</div>
    		<div class="col-md-10" >
    			<input type="text" class="form-control" id="title" placeholder="" name="title" value="${msgContents.TITLE}">		
    		</div>
    	</div>
    </div>  
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">부제목</label>
    		</div>
    		<div class="col-md-10" >
    			<input type="text" class="form-control" id="sub_title" placeholder="" name="sub_title" value="${msgContents.SUB_TITLE}">		
    		</div>
    	</div>
    </div>  
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2">
    			<label for="inputName">서한날짜</label>
    		</div>
    		<div class="col-md-4">
    			<input class="cal-month" type="text" id="mdate" name="mdate" maxlength="10" value="${msgContents.MDATE}"/>		
    		</div>    		
    	</div>
    </div>
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">내용</label>
    		</div>
    		<div class="col-md-10" >
    			<textarea id="content" name="content" cols="80" rows="10">${msgContents.CONTENT}
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
		      		<c:when test="${msgContents.DISPLAYYN == 'Y'}">
		        		<label class="radio-inline"><input type="radio" name="displayYN" id="displayYN1" value="Y" onClick="setDisplay(this,'Y')" checked="checked">노출</label>
		        		<label class="radio-inline"><input type="radio" name="displayYN" id="displayYN2" value="N" onClick="setDisplay(this,'N')">비노출</label>
		        	</c:when>
		        	<c:when test="${msgContents.DISPLAYYN == 'N'}">
		        		<label class="radio-inline"><input type="radio" name="displayYN" id="displayYN1" value="Y" onClick="setDisplay(this,'Y')">노출</label>
		        		<label class="radio-inline"><input type="radio" name="displayYN" id="displayYN2" value="N" onClick="setDisplay(this,'N')" checked="checked">비노출</label>
		        	</c:when>
		        	<c:otherwise>
		        		<label class="radio-inline"><input type="radio" name="displayYN" id="displayYN1" value="Y" onClick="setDisplay(this,'Y')">노출</label>
		        		<label class="radio-inline"><input type="radio" name="displayYN" id="displayYN2" value="N" onClick="setDisplay(this,'N')">비노출</label>
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
	<button type="button" id="btnModify" class="btn btn-default pull-right" onclick="javascript:modify_contents('${msgContents.M_IDX}');return false;">수정</button>
	<button type="button" id="btnDelete" class="btn btn-default pull-right" onclick="javascript:delete_contents('${msgContents.M_IDX}');return false;">삭제</button>
</c:otherwise>
</c:choose>
</p>
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->
</body>

</html>