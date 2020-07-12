<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %><%@ page 
import="kr.caincheon.church.common.base.Const, javax.servlet.http.HttpServletRequest, kr.caincheon.church.common.*" 
%><%

Object id = session.getAttribute(Const.SESSION_KEY_MEM_ID);

if ( id!=null && "admin".equals(id.toString()) ) {
	request.getRequestDispatcher("/admin/main.jsp").forward(request, response);	
	
} else {
	request.getRequestDispatcher("login.jsp").forward(request, response);

}
%>