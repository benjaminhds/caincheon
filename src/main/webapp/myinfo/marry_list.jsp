<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
function marryApply(no, id) {
	alert(no);
	document.form_apply.action = '/myinfo/marry_apply.do';
	document.getElementById('marry_no').value=no;
	document.getElementById('id').value=id;
	document.form_apply.submit();
    return false;
}

function insert_contents() {
	alert('insert');
	document.form_apply.action = '/myinfo/marry_insert.do';
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
<form name="form_apply" action="/myinfo/marry_apply.do" method="POST">
	<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}" />
	<input type="hidden" name="marry_no" id="marry_no" value="${marry_no}" />	
	<input type="hidden" name="id" id="id" value="${id}" />		
</form>	
	<table width="500" cellpadding="0" cellspacing="0" border="1">
		<tr>
		   <td>��ȣ</td>
		   <td>����</td>
			<td>���½�û��¥</td>
			<td>ȥ�ο�����</td>
			<td>����(��)</td>
			<td>���ʸ�</td>
			<td>����(��)</td>
			<td>���ʸ�</td>
			<td>��û��</td>
			<td>���ο���</td>
		</tr>
		<c:forEach items="${marryList}" var="dto">
		<tr>
			<td><a href="javascript:marryApply('${dto.MARRY_NO}','${dto.ID}')">${dto.RNUM}</a></td>
			<td>${dto.APPLY_TYPE_TEXT}</td>
			<td>${dto.LECTURE_APPLY_DAY}</td>
			<td>${dto.MARRY_DAY}</td>
			<td>${dto.MAN_NAME}</td>
			<td>${dto.MAN_BAPTISMAL_NAME}</td>
			<td>${dto.WOMAN_NAME}</td>
			<td>${dto.WOMAN_BAPTISMAL_NAME}</td>
			<td>${dto.APPLY_DAY}</td>
			<td>${dto.APPROVE_YN_TEXT}</td>					
		</tr>
		</c:forEach>
	</table>
	<p>&nbsp;</p>
<p>&nbsp;</p>
<button id="btnInsert" onclick="insert_contents();">�߰�</button>	
</body>
</html>