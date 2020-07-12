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
	document.form01.action = '/admin/board/inquire_list.do';
	//document.getElementById('idx').value=idx;
	document.form01.submit();
	return false;
}

function modify_contents() {
	//alert('modify_contents');
	document.form01.action = '/admin/board/inquire_modify.do';
	//document.getElementById('idx').value=idx;
	document.form01.submit();
	return false;
}

function delete_contents() {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/board/inquire_delete.do';
		//document.getElementById('inq_no').value=no;
		document.form01.submit();
	}
	return false;
}

function setCheck(chkbox, value) {
	if ( chkbox.checked == true ) {
		if(value=="3") {
			document.getElementById('replyType').value=value;
		}
		if(value=="2") {
			document.getElementById('replyType').value=value;
		}
	} else {
		document.getElementById('replyType').value="1";
	}
	return false;
}

window.onload = function() {
	onLoadLeftMenu('mem_02');
	//alert("loading completed");
	//CKEDITOR.replace( 'contents' );
	//CKEDITOR.replace( 'reply' );
	CKEDITOR.replace( 'contents', {
    	filebrowserUploadUrl: '/admin/board/memImageUpload.do'
  	});
	CKEDITOR.replace( 'reply', {
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
					<h3 class="page-header">나누고 싶은 이야기</h3>
					</div>
				<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
  <form class="well" name="form01" action="/admin/board/inquire_list.do" method="POST">
  	<input type="hidden" name="replyType" id="replyType" value="${inqContents.REPLYTYPE}"/>
  	<input type="hidden" name="inquire_no" id="inquire_no" value="${inqContents.INQUIRE_NO }"/>
  	<div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">제목</label>
    		</div>
    		<div class="col-md-10" >
    			<input type="text" class="form-control" id="title" placeholder="" name="title" value="${inqContents.TITLE}">		
    		</div>
    	</div>
    </div>
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">작성자</label>
    		</div>
    		<div class="col-md-10" >
    			<input type="text" class="form-control" id="name" placeholder="" name="name" value="${inqContents.NAME}" readonly>		
    		</div>
    	</div>
    </div>
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">이메일</label>
    		</div>
    		<div class="col-md-10" >
    			<input type="email" class="form-control" id="id" placeholder="" name="id" value="${inqContents.EMAIL}" readonly>		
    		</div>
    	</div>
    </div>
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">내용</label>
    		</div>
    		<div class="col-md-10" >
    			<textarea id="contents" name="contents" cols="80" rows="10">${inqContents.CONTENTS}
        		</textarea> 		
    		</div>
    	</div>
    </div>
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">답변</label>
    		</div>
    		<div class="col-md-10" >
    		<label class="checkbox-inline">
     			<input type="checkbox" id="reply_type" name="reply_type" value="3" onClick="setCheck(this,'3')" <c:if test="${inqContents.REPLYTYPE == '3'}">checked</c:if> >답변완료(문의자 확인 가능)
   			</label>
   			
			<c:if test="${inqContents.REPLYTYPE == '2'}">
    			<label class="checkbox-inline">
      				관리자 문의 사항 답변 준비 상태이며, 문의자는 글을 수정 할 수 없습니다.
    			</label>   
			</c:if>
    		</div>
    	</div>
    	<div class="row">
    		<div class="col-md-2" >
    			<label for=""></label>
    		</div>
    		<div class="col-md-10" >
    			<textarea id="reply" name="reply" cols="80" rows="10">${inqContents.REPLY}
        		</textarea>
    		</div>
   		</div>
    </div> 
</form>
<p>
<button type="button" id="btnList"   class="btn btn-default pull-right" onclick="javascript:viewList();return false;">목록</button>
<button type="button" id="btnModify" class="btn btn-default pull-right" onclick="javascript:modify_contents();return false;">수정</button>
<button type="button" id="btnDelete" class="btn btn-default pull-right" onclick="javascript:delete_contents();return false;">삭제</button>
</p>
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->
</body>

</html>