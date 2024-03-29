<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %><%@ page 
import="kr.caincheon.church.common.base.Const, javax.servlet.http.*" %><%!

public String pnull(HttpServletRequest request, String key) {
	Object o = request.getParameter(key);
	return o==null ? "":o.toString();
}

public String pnull(Object o) {
	return o==null ? "":o.toString();
}

public String pnullSession(HttpServletRequest request, String key, String defaultVal) {
	HttpSession session = request.getSession(true);
	if(session==null) {
		Object o = request.getAttribute(key);
		return o==null ? defaultVal : o.toString();
	}
	Object o = session.getAttribute(key);
	return o==null ? defaultVal:o.toString();
}

%><%


	/* 1th. Set header for no cache */ 
	response.setHeader( "pragma", "no-cache" );
	response.setHeader( "cache-control", "no-cache" );
	response.setHeader( "cache-control", "no-store" );
	
	// 세션 정보를 jstl에서 쓸 수 있게 설정
	String __uri = request.getRequestURI();
	java.util.Enumeration _enm = request.getSession(true).getAttributeNames();
	while(_enm.hasMoreElements()) {
		String sk  = _enm.nextElement().toString();
		Object svo = request.getSession(true).getAttribute(sk);
		String sv  = svo.toString();
		
		pageContext.setAttribute(sk, sv);
		
		System.out.println("-------- /_common/inc/loginHandler.jsp ("+__uri+") : "+sk+"="+sv);
	}
	
	
	// 로그인되어 있다면 아래 3개 중 2개(세례명을 제외한 2개, 이것은 있을수도 있고 없을수도 있음)의 값이 세팅되어야한다. 
	String sessionMemId="", sessionMemNm="", sessionMemBapNm = "";
	String SS_WRITEYN="N", SS_GROUPGUBUN="";
	String SS_CHURCHIDX = "";
	
	// 글쓰기 권한이 있는 사용자인지 여부
	boolean HAS_MEMADM_PERM = false;
	
	// 세션 처리 및 로그인 정보를 세팅...
	sessionMemId    = pnullSession(request, Const.SESSION_KEY_MEM_ID, "");
	sessionMemNm    = pnullSession(request, Const.SESSION_KEY_MEM_NM, "");
	sessionMemBapNm = pnullSession(request, Const.SESSION_KEY_MEM_BAPNM, "");
	pageContext.setAttribute("sessionMemId",    sessionMemId);
	pageContext.setAttribute("sessionMemNm",    sessionMemNm);
	pageContext.setAttribute("sessionMemBapNm", sessionMemBapNm);
	/*
	// 글쓰기 권한관련 정보 설정 
	SS_GROUPGUBUN = pnullSession(request, Const.SESSION_KEY_GROUPGUBUN         , "");  // 글쓰기 권한 그룹 2(교구),3(본당),4(공동체소식)
	SS_WRITEYN    = pnullSession(request, Const.SESSION_KEY_BOARDWRITEYN       , "N"); // 글을 쓸 수 있는지 여부 (Y/N)
	SS_CHURCHIDX  = pnullSession(request, Const.SESSION_KEY_CHURCHIDX          , "");  // 글을 쓸 수 있는 church idx -> SESSION_KEY_GROUPGUBUN(3)
	
	// 글쓰기 관리자 권한 설정
	*/
	if("Y".equalsIgnoreCase( pnullSession(request, Const.SESSION_KEY_BOARDWRITEYN, "N") )) { // 글을 쓸 수 있는지 여부 (Y/N)
		pageContext.setAttribute("SS_WRITEYN",   "Y");
	}
	
	/*
	 * SS_GROUPGUBUN
	 * 2: 교구게시판관리 -> 관리 권한  :: 사목자료게시판 & 교구소식 게시판 
	 * 3: 본당게시판관리 -> 관리 권한  :: 본당소식 & 본당앨범 & 본당정보 관리
	 * 4: 공동체소식관리 -> 관리 권한  :: 공동체소식 관리
	 *
	 */
	// 본당관리자는 본당IDX가 있음.
	// 부서관리자는... ????
	// 기타부서관리자는.. ????
	// 본당 수정은 관리자 페이지로 이동하기 위해서,,, 본당정보 수정을 위해서..
	SS_CHURCHIDX  = pnullSession(request, Const.SESSION_KEY_CHURCHIDX, "");  // 글을 쓸 수 있는 church idx -> SESSION_KEY_GROUPGUBUN(3)
	if ( SS_CHURCHIDX.length() > 0 	&& "3".equals(SS_GROUPGUBUN) && "Y".equals(SS_WRITEYN)  ) {
		pageContext.setAttribute("admSessionMemId", sessionMemId);
		pageContext.setAttribute("admSessionMemNm", sessionMemNm);
		
		HAS_MEMADM_PERM = true;
	}

	// 글쓰기 권한
	String mode = pnull(request, "mode");
	if(mode.length() > 0) {
		//request.setAttribute("mode",   mode);
		pageContext.setAttribute("mode", mode);
	} else {
		//request.removeAttribute("mode");
		pageContext.removeAttribute("mode");
	}
%>