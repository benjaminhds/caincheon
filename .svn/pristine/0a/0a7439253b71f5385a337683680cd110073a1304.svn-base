<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
function inquireApply(no, id) {
	alert(no);
	document.form_apply.action = '/myinfo/inq_apply.do';
	document.getElementById('inquire_no').value=no;
	document.getElementById('id').value=id;
	document.form_apply.submit();
    return false;
}

function insert_contents() {
	alert('insert');
	document.form_apply.action = '/myinfo/inq_insert.do';
	//document.getElementById('idx').value=idx;
	document.form_apply.submit();
    return false;
}

function modify_contents() {
	alert('modify');
	document.form_apply.action = '/myinfo/inq_insert.do';
	//document.getElementById('idx').value=idx;
	document.form_apply.submit();
    return false;
}

function delete_contents() {
	alert('delete');
	document.form_apply.action = '/myinfo/inquire_insert.do';
	//document.getElementById('idx').value=idx;
	document.form_apply.submit();
    return false;
}
</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form name="form_apply" action="/myinfo/inquire_apply.do" method="POST">
	<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}" />
	<input type="hidden" name="inquire_no" id="inquire_no" value="${inquire_no}" />	
	<input type="hidden" name="id" id="id" value="${id}" />		
</form>	
	<table width="500" cellpadding="0" cellspacing="0" border="1">
		<tr>
		   <td>NO</td>
		   <td>제목</td>
			<td>작성자</td>
			<td>답변여부</td>
			<td>등록일</td>
			<td>수정</td>
			<td>삭제</td>			
		</tr>
		<c:forEach items="${inqList}" var="dto">
		<tr>
			<td><a href="javascript:inquireApply('${dto.INQUIRE_NO}','${dto.ID}')">${dto.RNUM}</a></td>
			<td>${dto.TITLE}</td>
			<td>${dto.NAME}</td>
			<td>${dto.REPLY_YN_TEXT}</td>
			<td>${dto.APPLY_DAY}</td>						
			<td><a href="javascript:modify_contents('${dto.INQUIRE_NO}','${dto.ID}')">수정</a></td>
			<td><a href="javascript:delete_contents('${dto.INQUIRE_NO}','${dto.ID}')">삭제</a></td>
		</tr>
		</c:forEach>
	</table>
	<p>&nbsp;</p>
<p>&nbsp;</p>
<button id="btnInsert" onclick="insert_contents();">추가</button>	
</body>
</html>