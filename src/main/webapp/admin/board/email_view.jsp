<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	document.form01.action = '/admin/board/email_list.do';
	//document.getElementById('idx').value=idx;
	document.form01.submit();
	return false;
}

function insert_contents() {
	document.form01.action = '/admin/board/email_insert.do';
	document.form01.submit();
	return false;
}

function modify_contents() {
	//alert('modify_contents');
	document.form01.action = '/admin/board/email_modify.do';
	//document.getElementById('idx').value=idx;
	document.form01.submit();
	return false;
}

function delete_contents() {
	alert('delete_contents');
	document.form01.action = '/admin/board/email_delete.do';
	//document.getElementById('inq_no').value=no;
	document.form01.submit();
	return false;
}

function setRadio(radio, value) {
	if ( radio.checked == true ) {
		if(value == "1") {
			document.form01.rcv_email.disabled = true;
			document.getElementById("send_date_from").disabled = false;
			document.getElementById("send_date_to").disabled = false;
		}
		else if(value=="2") {
			document.form01.rcv_email.disabled = false;
			document.getElementById("send_date_from").value = "";
			document.getElementById("send_date_to").value = "";
			document.getElementById("send_date_from").disabled = true;
			document.getElementById("send_date_to").disabled = true;
		}
	}
	return false;
}

window.onload = function() {
	onLoadLeftMenu('mem_03');
	//CKEDITOR.replace( 'contents' );
    $('#send_date_from').appendDtpicker({
    	"autodateOnStart": false
    });
    $('#send_date_to').appendDtpicker({
    	"autodateOnStart": false
    });
	CKEDITOR.replace( 'contents', {
    	filebrowserUploadUrl: '/admin/board/memImageUpload.do'
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
<h3 class="page-header">메일보내기</h3>
					</div>
				<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
  <form class="well" name="form01" action="/admin/board/email_list.do" method="POST">
  	<input type="hidden" name="id" id="id" value="<%=admSessionMemId%>"/>
  	<div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">제목</label>
    		</div>
    		<div class="col-md-10" >
    			<input type="text" class="form-control" id="title" placeholder="" name="title" value="${email.TITLE}">		
    		</div>
    	</div>
    </div>   
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">메일선택</label>
    		</div>
    		<div class="col-md-10" >
	       		<label class="radio-inline"><input type="radio" name="email_type" id="email_type" value="1" onClick="setRadio(this,'1')" <c:if test="${email.EMAIL_TYPE=='1' }" >checked="checked"</c:if>>축일</label>
	       		<label class="radio-inline"><input type="radio" name="email_type" id="email_type" value="2" onClick="setRadio(this,'2')" <c:if test="${email.EMAIL_TYPE=='2' }" >checked="checked"</c:if>>일반</label>	
    		</div>
    	</div>
    </div>   

	<div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">축일메일발송기간</label>
    		</div>
    		<div class="col-md-4" >
    			<input type="text" name="send_date_from" id="send_date_from" maxlength="16" value="${email.SEND_DATE_FROM}"/>
    		</div>
    		<div class="col-md-2" >
    			<label for="">~</label>
    		</div>
    		<div class="col-md-4" >
    			<input type="text" name="send_date_to" id="send_date_to" maxlength="16" value="${email.SEND_DATE_TO}"/>
    		</div>
    	</div>
    </div>   
      
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">받으시는 분</label>
    		</div>
    		<div class="col-md-10" >
    			<textarea id="rcv_email" name="rcv_email" cols="80" rows="10">${email.RCV_EMAIL}
    			</textarea>		
    		</div>
    	</div>
    </div>   
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">내용</label>
    		</div>
    		<div class="col-md-10" >
    			<textarea id="contents" name="contents" cols="80" rows="10">${email.CONTENTS}
        		</textarea>		
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
	<button type="button" id="btnModify" class="btn btn-default pull-right" onclick="javascript:modify_contents();return false;">수정</button>
</c:otherwise>
</c:choose>

<button type="button" id="btnDelete" class="btn btn-default pull-right" onclick="javascript:delete_contents();return false;">삭제</button>
</p>
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->
</body>

</html>