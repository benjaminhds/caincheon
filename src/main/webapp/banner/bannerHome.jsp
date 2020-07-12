<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/_common/inc/head_const.jsp"%>

<!-- [banner] mobile only popup layer  -->
<style>
.floating{display:none;}

@media screen and (max-width:1024px){
.floating{display:inline-block; position:fixed; bottom:15%; left:0;  z-index:99}
}
</style>
<div class="floating">
<c:if test="${fn:length(bannerList) > 0}">
    <c:forEach items="${bannerList}" var="list" varStatus="status">
    	<c:if test="${list.LINK_TYPE eq '2'}"><c:set var="LinkType" value="target='_self'" /></c:if>
    	<c:if test="${list.LINK_TYPE eq '3'}"><c:set var="LinkType" value="target='_blank'" /></c:if>
    	<c:if test="${fn:length(list.URL) > 0 && list.LINK_TYPE ne '1'}">
    		<c:set var="LinkTagS" value="<a href='${list.URL}' ${LinkType}>" />
    		<c:set var="LinkTagE" value="</a>" />
    	</c:if>
    	<c:if test="${fn:length(list.IMG) > 0}">
		${LinkTagS }<img src="${list.IMG}" alt="${list.TITLE}">${LinkTagE }
		</c:if>
    </c:forEach>
</c:if>
</div>
<!-- //floating -->
