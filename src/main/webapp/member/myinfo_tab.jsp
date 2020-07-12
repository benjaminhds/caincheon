<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%

/*
 * 본 파일은 /member/xxx.jsp 에서 사용되는 TAB 처리 jsp 임.
 *
 */

%>

                    <c:set var="myinfo_url" value="/member/myinfo.jsp" />
                    <c:if test = "${fn:length(sessionMemId) > 0 }">
                    	<c:set var="myinfo_url" value="/member/findWithSessionId.do?id=${sessionMemId}" />
                    </c:if>
					<%
					String __uri = request.getRequestURI();
					String _clzTab1="", _clzTab2="", _clzTab3="", _clzTab4="";
					if(__uri.contains("myinfo_edit.jsp"))      _clzTab1 = "class=\"on\"";
					else if(__uri.contains("my_doctrine.jsp")) _clzTab2 = "class=\"on\"";
					else if(__uri.contains("my_cana.jsp")) _clzTab3 = "class=\"lineTwo on\"";
					else if(__uri.contains("my_tale.jsp")) _clzTab4 = "class=\"on\"";
					%>
					
                    
                    <ul class="tabs ea5">
                    	<li <%=_clzTab1%>><a href="${myinfo_url}">개인정보 수정</a></li>
                        <li <%=_clzTab2%>><a href="/member/my_doctrine.do">통신교리신청</a></li>
                        <li <%=_clzTab3%>><a href="/member/my_cana.do">카나혼인강좌/약혼자 주말신청</a></li>
                        <li <%=_clzTab4%>><a href="/member/my_tale.do">나누고 싶은 이야기</a></li>
                    </ul> 
