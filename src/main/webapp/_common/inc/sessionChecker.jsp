<%@page import="kr.caincheon.church.common.base.Const"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %><%@ page import="javax.servlet.http.*"
%><%

/* 1th. Set header for no cache */ 
response.setHeader( "pragma", "no-cache" ); 
response.setHeader( "cache-control", "no-cache" ); 
response.setHeader( "cache-control", "no-store" ); 
response.setHeader( "expires", "-1" ); 

/* 2th. Set header for no cache */ 
Cookie[] cookies = null;
Cookie   cookie  = null;

String   sessionMemId    = ""; // 로그인 ID 
String   sessionMemNm    = ""; // 로그인 이름
String   sessionChrNm    = ""; // 로그인 세례명

/* get cookies for loing checking */
cookies = request.getCookies();
if (cookies != null) {
	for (int i = 0; i < cookies.length; i++) {
		cookie = cookies[i];
		
		if(cookie.getName().equals(Const.SESSION_KEY_MEM_ID)) {
			sessionMemId  = cookie.getValue();
		} else if(cookie.getName().equals(Const.SESSION_KEY_MEM_NM)) {
			sessionMemNm  = cookie.getValue();
		} else if(cookie.getName().equals(Const.SESSION_KEY_MEM_BAPNM)) {
			sessionMemNm  = cookie.getValue();
		}
	}
}

String sessionId   = "";
/** get a session object ** /
try {
	sessionId = (String)session.getAttribute("LOGIN_ID");
} catch(Exception el) {}

/** session time calculate ** /
long LOGIN_IDLE_TIME = session.getLastAccessedTime() - session.getCreationTime();

/** check the session of expire time or check the session id ** /
if(session.getId()==null) {
	// session 유휴 시간이 지났음. 다시 로그인을 해야함.
	session.invalidate(); // session 파기
	// page 이동
	response.sendRedirect("/");
}
/* */

/*
		// ...................................................................
		// 3.1th.Cookies setting for normal information
		// ...................................................................
		Cookie lcookie = new Cookie(MEM_DI, inUserId);
		lcookie.setMaxAge(-1);
		lcookie.setPath("/");
		response.addCookie(lcookie);
*/

%>
