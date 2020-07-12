<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
function viewList() {
	document.form01.action = '/myinfo/inq_list.do';
	//document.getElementById('idx').value=idx;
	document.form01.submit();
	return false;
}

function modify_contents() {
	//alert('modify_contents');
	document.form02.action = '/myinfo/inq_modify.do';
	//document.getElementById('idx').value=idx;
	document.form02.submit();
	return false;
}

function delete_contents() {
	document.form02.action = '/myinfo/inq_delete.do';
	//document.getElementById('inq_no').value=no;
	document.form02.submit();
	return false;
}

window.onload = function() {
	//alert("loading completed");
}
</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form name="form01" action="/myinfo/inq_insert.do" method="POST">
	<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}" />	
	<input type="hidden" name="inq_no" id="inq_no" value="${inq_no}" />	
	<input type="hidden" name="id" id="id" value="${id}" />	
</form>	

<form name="form02" action="/myinfo/inq_modify.do" accept-charset="utf-8" method="POST">
	<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}" />	
	<input type="hidden" name="inquire_no" id="inquire_no" value="${_params.inquire_no}" />	
	<input type="hidden" name="id" id="id" value="${_params.id}" />	
<table style="height: 20px;" width="680">
<tbody>
<tr>
<td>제목</td>
<td>${inqContents.TITLE}</td>
</tr>
<tr>
<td>작성자</td>
<td>${inqContents.NAME}</td>
</tr>
<tr>
<td>이메일</td>
<td>${inqContents.EMAIL}</td>
</tr>
<tr>
<td>내용</td>
<td>${inqContents.CONTENTS}</td>
</tr>
<tr>
<td>답변</td>
<td>${inqContents.REPLY}</td>
</tr>

</tbody>
</table>
</form>
	<p>&nbsp;</p>
<p>&nbsp;</p>
<button id="btnList" onclick="viewList();">목록</button>
<button id="btnModify" onclick="modify_contents();">수정</button>
<button id="btnDelete" onclick="delete_contents();">삭제</button>
</body>
</html>