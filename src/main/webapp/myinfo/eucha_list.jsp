<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
function euchaApply(no, id) {
	alert(no);
	document.form_apply.action = '/myinfo/eucha_apply.do';
	document.getElementById('eucha_no').value=no;
	document.getElementById('id').value=id;
	document.form_apply.submit();
    return false;
}

function insert_contents() {
	alert('insert');
	document.form_apply.action = '/myinfo/eucha_insert.do';
	//document.getElementById('idx').value=idx;
	document.form_apply.submit();
    return false;
}
</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<form name="form_apply" action="/myinfo/eucha_apply.do" method="POST">
	<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}" />
	<input type="hidden" name="eucha_no" id="eucha_no" value="${eucha_no}" />	
	<input type="hidden" name="id" id="id" value="${id}" />		
</form>	
	<table width="500" cellpadding="0" cellspacing="0" border="1">
		<tr>
		   <td>번호</td>
		   <td>소속본당/소임지</td>
			<td>신청자</td>
			<td>세례명</td>
			<td>연락처</td>
			<td>이메일</td>
			<td>승인여부</td>			
		</tr>
		<c:forEach items="${euchaList}" var="dto">
		<tr>
			<td><a href="javascript:euchaApply('${dto.EUCHA_MID}','${dto.ID}')">${dto.RNUM}</a></td>
			<td>${dto.CHURCH_NAME}</td>
			<td>${dto.NAME}</td>
			<td>${dto.BAPTISMAL_NAME}</td>
			<td>${dto.TEL}</td>
			<td>${dto.EMAIL}</td>
			<td>${dto.APPROVE_YN_TEXT}</td>				
		</tr>
		</c:forEach>
	</table>
	<p>&nbsp;</p>
<p>&nbsp;</p>
<button id="btnInsert" onclick="insert_contents();">추가</button>	
</body>
</html>