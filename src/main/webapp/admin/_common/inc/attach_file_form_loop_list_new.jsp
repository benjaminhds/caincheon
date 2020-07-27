<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- //파일 업로드 폼 시작 -->
	
<c:forEach var="i" begin="1" end="${HEAD_CONST__attach_file_form__MAX_FILE_COUNT}" varStatus="status">
	<% /* 동적변수 할당 */ %>
	<c:set var="XFILENAME"    value="FILENAME${i}"    scope="page" />
	<c:set var="XFILEPATH"    value="FILEPATH${i}"    scope="page" />
	<c:set var="XSTRFILENAME" value="STRFILENAME${i}" scope="page" />

	<% /* 동적변수로 값을 추출 :: requestScope[동적변수] 로 추출 , MAP에서는 맵[변수] 로 추출한다. */ %>
	<c:if test="${fn:length(CONTENTS[XSTRFILENAME]) > 0 }">
	<div><a href="/filedownload.jsp?strfileName=${CONTENTS[XSTRFILENAME]}&fileName=${CONTENTS[XFILENAME]}&filePath=${CONTENTS[XFILEPATH]}">${CONTENTS[XFILENAME]}</a></div>
	</c:if>

</c:forEach>
FILENAME1

<!-- //파일 업로드 폼 끝 -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- //파일 업로드 폼 시작 -->
	
<c:forEach items="${attachList}" var="list">
	<% /* 동적변수로 값을 추출 :: requestScope[동적변수] 로 추출 , MAP에서는 맵[변수] 로 추출한다. */ %>
	<div>
		<a href="/filedownload.jsp?strfileName=${list.STRFILENAME}&fileName=${list.FILENAME}&filePath=${list.FILEPATH}">${CONTENTS[FILENAME]}</a>
	</div>
</c:forEach>

<!-- //파일 업로드 폼 끝 -->