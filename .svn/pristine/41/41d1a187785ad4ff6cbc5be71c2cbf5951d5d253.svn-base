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
function viewList() {
	document.form01.action = '/admin/board/faq_list.do';
	//document.getElementById('idx').value=idx;
	document.form01.submit();
	return false;
}

function insert_contents() {
	if( $("#question").val() == "") { alert("질문을 입력해 주세요."); $("#question").focus(); return; }
	document.form01.action = '/admin/board/faq_insert.do';
	document.form01.submit();
	return false;
}

function modify_contents(faq_no) {
	if( $("#question").val() == "") { alert("질문을 입력해 주세요."); $("#question").focus(); return; }
	document.form01.action = '/admin/board/faq_modify.do';
	document.getElementById('faq_no').value = faq_no;
	document.form01.submit();
	return false;
}

function delete_contents(faq_no) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/board/faq_delete.do';
		document.getElementById('faq_no').value = faq_no;
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
	onLoadLeftMenu('etc_05');

	CKEDITOR.replace( 'answer', {
    	filebrowserUploadUrl: '/admin/board/etcImageUpload.do'
  	});
}
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
<h3 class="page-header">FAQ(교구법원)</h3>
					</div>
				<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
  <form class="well" name="form01" action="/admin/board/faq_list.do" method="POST">
  	<input type="hidden" name="displayYN" id="displayYN" value="${faqContents.DISPLAYYN}"/>
  	<input type="hidden" name="faq_no" id="faq_no" value=""/>
  	
  	<div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">질문</label>
    		</div>
    		<div class="col-md-10" >
    			<input type="text" class="form-control" id="question" placeholder="" name="question" value="${faqContents.QUESTION}">		
    		</div>
    	</div>
    </div>   
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">답변</label>
    		</div>
    		<div class="col-md-10" >
    			<textarea id="answer" name="answer" cols="80" rows="10">${faqContents.ANSWER}
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
		      		<c:when test="${faqContents.DISPLAYYN == 'Y'}">
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
	<button type="button" id="btnModify" class="btn btn-default pull-right" onclick="javascript:modify_contents('${faqContents.FAQ_NO}');return false;">수정</button>
	<button type="button" id="btnDelete" class="btn btn-default pull-right" onclick="javascript:delete_contents('${faqContents.FAQ_NO}');return false;">삭제</button>
</c:otherwise>
</c:choose>
</p>
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->
</body>

</html>