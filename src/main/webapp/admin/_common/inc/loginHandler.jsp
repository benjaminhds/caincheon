<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %><%@ page 
import="kr.caincheon.church.common.base.Const, javax.servlet.http.HttpServletRequest" %><%!

public String pnulls2(HttpServletRequest request, String key) {
	HttpSession session = request.getSession(true);
	if(session==null) return "";
	Object o = session.getAttribute(key);
	return o==null ? "":o.toString();
}

%><%
	/* 1th. Set header for no cache */ 
	response.setHeader( "pragma", "no-cache" ); 
	response.setHeader( "cache-control", "no-cache" ); 
	response.setHeader( "cache-control", "no-store" ); 
	 
	// 로그인되어 있다면 아래 3개 중 2개(세례명을 제외한 2개, 이것은 있을수도 있고 없을수도 있음)의 값이 세팅되어야한다. 
	String admSessionMemId="", admSessionMemNm="";
	
	// 세션 정보를 jstl에서 쓸 수 있게 설정
	java.util.Enumeration enm = request.getSession(true).getAttributeNames();
	while(enm.hasMoreElements()) {
		String sk  = enm.nextElement().toString();
		Object svo = request.getSession(true).getAttribute(sk);
		String sv  = svo.toString();
		
		pageContext.setAttribute(sk, sv);
		
		if(sk.equals("ADM_MEM_ID")) {
			admSessionMemId = sv;
		} else 	if(sk.equals("ADM_MEM_NM")) {
			admSessionMemNm = sv;
		}
		
		System.out.println("-------- /admin/_common/inc/loginHandler.jsp : "+sk+"="+sv);
	}
	
%>