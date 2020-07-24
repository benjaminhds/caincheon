<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- //파일 업로드 폼 시작 -->
<c:forEach items="${attachList}" var="list">
		<div>
			<c:choose>
				<c:when test="${bd_content.USEYN_DOWNLOAD_PERM eq 'Y'}">
					<a href="/filedownload.jsp?strfileName=${list.STRFILENAME}&fileName=${list.FILENAME}&filePath=${list.FILEPATH}">${list.FILENAME}</a>
				</c:when>
				<c:otherwise>
					<a href="javascript:;">${list.FILENAME}</a>
				</c:otherwise>
			</c:choose>
		</div>
</c:forEach>
