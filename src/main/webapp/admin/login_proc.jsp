<%@ page session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %><%@ 
page import="javax.servlet.http.*, kr.caincheon.church.common.*, kr.caincheon.church.common.base.*, kr.caincheon.church.common.utils.*"
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%

/* 1th. Set header for no cache */ 
response.setHeader( "pragma", "no-cache" ); 
response.setHeader( "cache-control", "no-cache" ); 
response.setHeader( "cache-control", "no-store" ); 
//response.setHeader( "expires", "0" );

/* 2th. Set header for no cache */ 
Cookie[] cookies = null;
Cookie   cookie  = null;

// 로그인 성공시 넘어 오는 정보
String admSessionId  = UtilString.pnull(request.getAttribute(Const.SESSION_KEY_ADM_MEM_ID));
String admSessionNm  = UtilString.pnull(request.getAttribute(Const.SESSION_KEY_ADM_MEM_NM));
Object admMeminfoMap = request.getAttribute("ADMMEMINFO_MAP");

// 로그인 성공시 지정한 URL로, 실퍠시에는 다시 로그인 화면으로..
String sessionURL = UtilString.pnull(request.getAttribute(Const.SESSION_KEY_GOTO), "/admin/main.do");
String errorMsg   = UtilString.pnull(request.getAttribute(Const.ERR_MSG));
String errorCd    = UtilString.pnull(request.getAttribute(Const.ERR_CODE));


System.out.println("------------- /admin/login_proc.jsp : "+Const.SESSION_KEY_ADM_MEM_ID+"=" + admSessionId);
System.out.println("------------- /admin/login_proc.jsp : "+Const.SESSION_KEY_ADM_MEM_NM+"=" + admSessionNm);
System.out.println("------------- /admin/login_proc.jsp : "+Const.SESSION_KEY_GOTO+"=" + sessionURL);
System.out.println("------------- /admin/login_proc.jsp : ADMMEMINFO_MAP=" + admMeminfoMap);
System.out.println("------------- /admin/login_proc.jsp : "+Const.ERR_MSG+"=" + errorMsg);
System.out.println("------------- /admin/login_proc.jsp : "+Const.ERR_CODE+"=" + errorCd);

// ----------------------------------------------------
// 로그인 성공 시
// ----------------------------------------------------
if (admSessionId.length() > 0 || admSessionNm.length() > 0 ) { 
	java.util.Map _MEM_MAP = (java.util.Map)admMeminfoMap;
	
    // 세션처리
    HttpSession session = request.getSession(true);
    session.setAttribute(Const.SESSION_KEY_ADM_MEM_ID,    admSessionId);
    session.setAttribute(Const.SESSION_KEY_ADM_MEM_NM,    admSessionNm);
    session.setAttribute(Const.SESSION_KEY_ADM_MEM_GROUP, UtilString.pnull(_MEM_MAP.get("ADM_GROUP")));
    
    session.setMaxInactiveInterval(60*60*10); // Session holding time is 10 minutes
    
	//GOTO URL
	if(sessionURL.indexOf("?") > 0 ) sessionURL = sessionURL.substring(0, sessionURL.indexOf("?"));
	
	// 세션에 지정한 값이 아닌 다른 정보를 jsp로 넘겨할 때, 이를 처리한다.
	
	// 
	if( sessionURL.indexOf(".do") == -1 ) sessionURL += ".do";
	
	//
	request.getRequestDispatcher(sessionURL).forward(request, response);
	
// ----------------------------------------------------
// 로그인 실패 시 :: 아래 루틴은 타지 않는다.
// ----------------------------------------------------
} else {
	
	sessionURL = "/admin/login.jsp?pid="+admSessionId;//+"&errmsg="+errorMsg;
	
	if(errorCd.length()>0)  sessionURL += "&errorCd="+errorCd;
	if(errorMsg.length()>0) sessionURL += "&errorMsg="+errorMsg;
	
	System.out.println("------------- errorCd  : " + errorCd);
	System.out.println("------------- errorMsg : " + errorMsg);

	//
	response.sendRedirect(sessionURL);
}

%>