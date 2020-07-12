<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
function viewList() {
	document.form01.action = '/myinfo/marry_list.do';
	//document.getElementById('idx').value=idx;
	document.form01.submit();
	return false;
}

function modify_contents() {
	alert('modify_contents');
	document.form02.action = '/myinfo/marry_modify.do';
	//document.getElementById('idx').value=idx;
	document.form02.submit();
	return false;
}

function delete_contents() {
	document.form02.action = '/myinfo/marry_delete.do';
	//document.getElementById('marry_no').value=no;
	document.form02.submit();
	return false;
}

window.onload = function() {
	alert("loading completed");
}
</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<form name="form01" action="/myinfo/marry_insert.do" method="POST">
	<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}" />	
	<input type="hidden" name="marry_no" id="marry_no" value="${marry_no}" />	
	<input type="hidden" name="id" id="id" value="${id}" />	
</form>	

<form name="form02" action="/myinfo/marry_modify.do" accept-charset="utf-8" method="POST">
	<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}" />	
	<input type="hidden" name="marry_no" id="marry_no" value="${_params.marry_no}" />	
	<input type="hidden" name="id" id="id" value="${_params.id}" />	
<table style="height: 20px;" width="680">
<tbody>
<tr>
<td>승인구분</td>
<td>
	<c:choose>
		<c:when test="${marryContents.APPROVE_YN eq 'Y'}" >
			<input type="radio" name="approve_yn" id="approve_yn" value="Y" checked="checked" />승인 
			<input type="radio" name="approve_yn" id="approve_yn" value="N" />미승인
		</c:when>
		<c:otherwise>
			<input type="radio" name="approve_yn"  id="approve_yn" value="Y" />승인 
			<input type="radio" name="approve_yn"  id="approve_yn" value="N" checked="checked" />미승인
		</c:otherwise>
	</c:choose>	
</td>
</tr>

<tr>
<td>신청구분</td>
<td>
	<c:choose>
		<c:when test="${marryContents.APPLY_TYPE eq '1'}" >
			<input type="radio" name="apply_type" id="apply_type" value="1" checked="checked" />카나혼인강좌 
			<input type="radio" name="apply_type" id="apply_type" value="2" />약혼자주말
		</c:when>
		<c:otherwise>
			<input type="radio" name="apply_type"  id="apply_type" value="1" />카나혼인강좌 
			<input type="radio" name="apply_type"  id="apply_type" value="2" checked="checked" />약혼자주말
		</c:otherwise>
	</c:choose>	
</td>
</tr>

<tr>
<td>강좌신청날짜</td>
<td>
	<input type="date" name="lecture_apply_day" id="lecture_apply_day" value="${marryContents.LECTURE_APPLY_DAY}">
</tr>

<tr>
<td>성명</td>
<td>
	<input  type="text" name="man_name" id="man_name"  value="${marryContents.MAN_NAME }"/>
	<input  type="text" name="woman_name" id="woman_name"  value="${marryContents.WOMAN_NAME }"/>
</td>	
</tr>



  
<tr>
<td>신자구분</td>
<td>
	<c:choose>
		<c:when test="${docContents.MEMBER_TYPE eq '2'}" >
			<input type="radio" name="member_type"  id="member_type" value="2" checked="checked" />예비신자 
			<input type="radio" name="member_type"  id="member_type" value="3" />재교육자
		</c:when>
		<c:when test="${docContents.MEMBER_TYPE eq '3'}" >
			<input type="radio" name="member_type"  id="member_type" value="2" />예비신자 
			<input type="radio" name="member_type"  id="member_type" value="3" checked="checked" />재교육자
		</c:when>
	</c:choose>	
</td>
</tr>
  

  
<tr>
<td>성명</td>
<td>
	<input  type="text" name="name" id="name"  value="${docContents.NAME }"/>
<td>세례명</td>
<td>
	<input  type="text" name="baptismal_name" id="baptismal_name"  value="${docContents.BAPTISMAL_NAME }"/>
</tr>
  
<tr>
<td>성별</td>
<td>
	<c:choose>
		<c:when test="${docContents.SEX eq '1'}" >
			<input type="radio" name="sex"  id="sex"  value="1" checked="checked" />남자 
			<input type="radio" name="sex"  id="sex"  value="2" />여자
		</c:when>
		<c:otherwise>
			<input type="radio" name="sex"  id="sex"  value="1"  />남자 
			<input type="radio" name="sex"  id="sex"  value="2" checked="checked"/>여자
		</c:otherwise>
	</c:choose>
	
</td>
<td>직업</td>
<td>
	<input  type="text" name="job" id="job" value="${docContents.JOB}"/>
</tr>
  
  <tr>
<td>소속본당</td>
<td><select name="church_idx" id="church_idx">
    <option value="14001">답동주교좌</option>
    <option value="14002">가정동</option>
    <option value="14003">가정3동</option>
    <option value="14004">가좌동</option>
</select>
  </tr>
  
<tr>
<td>생년월일</td>
<td>
	<c:choose>
		<c:when test="${docContents.BIRTH_TYPE eq '1'}" >
			<input type="radio" name="birth_type" id="birth_type" value="1" checked="checked"/>양력 
			<input type="radio" name="birth_type" id="birth_type" value="2" />음력
		</c:when>
		<c:otherwise>
			<input type="radio" name="birth_type" id="birth_type" value="1" />양력 
			<input type="radio" name="birth_type" id="birth_type" value="2" checked="checked"/>음력
		</c:otherwise>
	</c:choose>
</td>
<td>
	<input type="date" name="birthday" id="birthday" value="${docContents.BIRTHDAY}">
</tr>
  
<tr>
<td>휴대전화</td>
<td>
	<input type="tel" name="m_tel" id="m_tel" value="${docContents.M_TEL}">
<td>자택전화</td>
<td>
	<input  type="tel" name="h_tel" id="h_tel" value="${docContents.H_TEL}" />
</tr>
  
<tr>
<td>직장전화</td>
<td>
	<input type="tel" name="o_tel" id="o_tel" value="${docContents.O_TEL}">
<td>이메일</td>
<td>
	<input  type="email" name="email" id="email" value="${docContents.EMAIL}" />
</tr>
  
<tr>
<td>주소</td>
<td><input type="text" name="postcode" id="postcode" value="${docContents.POSTCODE}">
<td><input  type="text" name="addr1" id="addr1" value="${docContents.ADDR1}"/>
 <td><input  type="text" name="addr2" id="addr2" value="${docContents.ADDR2}"/>
  </tr>
  
  <tr>
<td>남기실 말씀</td>
<td><input type="text" name="user_comment" id="user_comment" value="${docContents.USER_COMMENT}">     
  </tr>
  
    <tr>
<td>관리자 코멘트</td>
<td><input type="text" name="admin_comment" id="admin_comment" value="${docContents.ADMIN_COMMENT}">     
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