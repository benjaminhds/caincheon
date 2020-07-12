<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
function docApply(no, id) {
	alert(no);
	document.form_apply.action = '/myinfo/doctrine_apply.do';
	document.getElementById('doctrine_no').value=no;
	document.getElementById('id').value=id;
	document.form_apply.submit();
    return false;
}

function insert_contents() {
	alert('insert');
	document.form_apply.action = '/myinfo/doctrine_insert.do';
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
<form name="form_apply" action="/myinfo/doctrine_apply.do" method="POST">
	<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}" />
	<input type="hidden" name="doctrine_no" id="doctrine_no" value="${doctrine_no}" />	
	<input type="hidden" name="id" id="id" value="${id}" />		
</form>	
	<table width="500" cellpadding="0" cellspacing="0" border="1">
		<tr>
		   <td>번호</td>
		   <td>신자구분</td>
			<td>이름</td>
			<td>세례명</td>
			<td>소속본당</td>
			<td>신청일</td>
			<td>승인여부</td>
		</tr>
		<c:forEach items="${docList}" var="dto">
		<tr>
			<td><a href="javascript:docApply('${dto.DOCTRINE_NO}','${dto.ID}')">${dto.RNUM}</a></td>
			<td>${dto.MEMBER_TYPE_TEXT}</td>
			<td>${dto.NAME}</td>
			<td>${dto.BAPTISMAL_NAME}</td>
			<td>${dto.CHURCH_NAME}</td>
			<td>${dto.APPLY_DAY}</td>
			<td>${dto.APPROVE_YN_TEXT}</td>			
		</tr>
		</c:forEach>
	</table>
	<p>&nbsp;</p>
<p>&nbsp;</p>
<button id="btnInsert" onclick="insert_contents();">추가</button>	
</body>
</html>