<%@ page session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %><%@ page import="javax.servlet.http.*"
%><%

/* 1th. Set header for no cache */ 
response.setHeader( "pragma", "no-cache" ); 
response.setHeader( "cache-control", "no-cache" ); 
response.setHeader( "cache-control", "no-store" ); 
response.setHeader( "expires", "-1" ); 



String gotoURL = request.getParameter("gotoURL");
//gotoURL = gotoURL==null || gotoURL.length()==0 ? request.getHeader("referer") : gotoURL;

// 로그아웃 시점 페이지로 이동
gotoURL = request.getHeader("referer").replace("http://","");
gotoURL = gotoURL.substring( gotoURL.indexOf("/") );


/* 2th. Set header for no cache */ 
/*
		// ...................................................................
		// 3.1th.Cookies setting for normal information
		// ...................................................................
		Cookie lcookie = new Cookie(MEM_DI, inUserId);
		lcookie.setMaxAge(-1);
		lcookie.setPath("/");
		response.addCookie(lcookie);
*/

if(gotoURL.length() == 0) {
	gotoURL = "/member/login.do";
}

// 모든 세션을 제거
HttpSession session = request.getSession(true);
session.invalidate();
response.sendRedirect(gotoURL);
%>
