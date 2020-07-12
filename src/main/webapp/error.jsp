<%@ page session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<% response.setStatus(200); %>
<%@ include file="/_common/inc/head.jsp"%>
<pre>
[Error MSG]: <%=exception.getMessage() %>
 
[Error MSG]: ${ERR_MSG} 

[Error Stacks]: ${STACKS}


REQ Contents: <%=request.toString() %>

RES Contents: <%=response.toString() %>
</pre>