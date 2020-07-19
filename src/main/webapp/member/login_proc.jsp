<%@ page session="false" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%><%@ page
	import="javax.servlet.http.*, kr.caincheon.church.common.*, java.util.*, kr.caincheon.church.common.base.*, kr.caincheon.church.common.utils.*"
	%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%><%!

	public String pnull(Object o) {
		return o == null ? "" : o.toString();
	}

	public String pnull(Object o, String defaultVal) {
		return o == null ? defaultVal : o.toString();
	}

	public String pnull(Map m, String k) {
		Object o = m.get(k);
		return o == null ? "" : o.toString();
	}

	
%><%

	/* 1th. Set header for no cache */
	response.setHeader("pragma", "no-cache");
	response.setHeader("cache-control", "no-cache");
	response.setHeader("cache-control", "no-store");
	//response.setHeader( "expires", "0" );

	/* 2th. Set header for no cache */
	Cookie[] cookies = null;
	Cookie cookie = null;

	// 로그인 성공시 넘어 오는 회원정보 속성들
	String user_id  = pnull(request.getAttribute("id"));
	Map MEMINFO_MAP = (Map) request.getAttribute("MEMINFO_MAP");

	// 로그인 성공시 지정한 URL로, 실퍠시에는 다시 로그인 화면으로..
	String sessionURL = pnull(request.getAttribute(Const.SESSION_KEY_GOTO), "/");
	String errorMsg   = pnull(request.getAttribute(Const.ERR_MSG));
	String errorCd    = pnull(request.getAttribute(Const.ERR_CODE));

	System.out.println("------------- member/login_proc.jsp : " + Const.SESSION_KEY_GOTO + "=" + sessionURL); 
	System.out.println("------------- member/login_proc.jsp : MEMINFO_MAP=" + MEMINFO_MAP);
	System.out.println("------------- member/login_proc.jsp : " + Const.ERR_MSG + "=" + errorMsg);
	System.out.println("------------- member/login_proc.jsp : " + Const.ERR_CODE + "=" + errorCd);

	// ----------------------------------------------------
	// 로그인 성공 시
	// ----------------------------------------------------
	if (MEMINFO_MAP !=null && MEMINFO_MAP.size() > 0 ) { // .length() > 0 || sessionNm.length() > 0) {

		// 세션처리
		HttpSession session = request.getSession(true);
		session.setAttribute(Const.SESSION_KEY_MEM_ID, pnull(request.getAttribute(Const.SESSION_KEY_MEM_ID)));
		session.setAttribute(Const.SESSION_KEY_MEM_NM, pnull(request.getAttribute(Const.SESSION_KEY_MEM_NM)));
		session.setAttribute(Const.SESSION_KEY_MEM_BAPNM, pnull(request.getAttribute(Const.SESSION_KEY_MEM_BAPNM)));//new String(sessionBap.getBytes("utf-8"))

		/*
		 * SS_GROUPGUBUN
		 * 2: 교구게시판관리 -> 관리 권한  :: 사목자료게시판 & 교구소식 게시판 
		 * 3: 본당게시판관리 -> 관리 권한  :: 본당소식 & 본당앨범 & 본당정보 관리
		 * 4: 공동체소식관리 -> 관리 권한  :: 공동체소식 관리
		 *
		 */

		// 글쓰기 권한 있는 관리자라면,,, :: 글쓰기 권한관련 정보 설정 
		String DEPART_IDX = pnull(MEMINFO_MAP, "DEPART_IDX");
		String DEPART_NAME= pnull(MEMINFO_MAP, "DEPART_NAME");
		String CHURCH_IDX = pnull(MEMINFO_MAP, "CHURCH_IDX");
		String CHURCH_NM  = pnull(MEMINFO_MAP, "CHRUCH_NAME");
		String ORG_IDX    = pnull(MEMINFO_MAP, "ORG_IDX");
		String ORG_NM     = pnull(MEMINFO_MAP, "ORG_NAME");
		String WRITEYN    = pnull(MEMINFO_MAP, "WRITEYN");
		String GROUPGUBUN = pnull(MEMINFO_MAP, "GROUPGUBUN");
		String BOARD_IDX  = pnull(MEMINFO_MAP, "WRITERABLE_BOARD");

		if (DEPART_IDX.length() > 0)
			session.setAttribute(Const.SESSION_KEY_DEPARTIDX, DEPART_IDX);// 회원의 소속 교구부서코드 
		if (DEPART_NAME.length() > 0)
			session.setAttribute(Const.SESSION_KEY_DEPARTNM, DEPART_NAME);// 회원의 소속 교구부서명 
		if (CHURCH_IDX.length() > 0)
			session.setAttribute(Const.SESSION_KEY_CHURCHIDX, CHURCH_IDX);// 회원의 소속 본당(글을 쓸 수 있는) church idx 
		if (CHURCH_NM.length() > 0)
			session.setAttribute(Const.SESSION_KEY_CHURCHNM, CHURCH_NM);// 소속 본당명
		if (ORG_IDX.length() > 0)
			session.setAttribute(Const.SESSION_KEY_ORGIDX, ORG_IDX);// 로그인(글쓰는) 자의 소속 조직의 org idx 
		if (ORG_NM.length() == 0) {
			ORG_NM = DEPART_NAME.length() > 0 ? DEPART_NAME:CHURCH_NM;
		}
		session.setAttribute(Const.SESSION_KEY_ORGNM, ORG_NM);// 소속 조직명
		
		if (WRITEYN.length() > 0)
			session.setAttribute(Const.SESSION_KEY_BOARDWRITEYN, WRITEYN); // 글을 쓸 수 있는 권한 'Y' 
		if (GROUPGUBUN.length() > 0)
			session.setAttribute(Const.SESSION_KEY_GROUPGUBUN, GROUPGUBUN);// 글쓰기 권한 그룹 2(교구),3(본당),4(공동체소식) 
		if (BOARD_IDX.length() > 0)
			session.setAttribute(Const.SESSION_KEY_WRITABLEBOARD_IDX, BOARD_IDX); // 글을 쓸 수 있는 church idx 

		session.setMaxInactiveInterval(60 * 60 * 10); // Session holding time is 10 minutes

		//GOTO URL
		if (sessionURL.indexOf("?") > 0)
			sessionURL = sessionURL.substring(0, sessionURL.indexOf("?"));

		// 
		if (sessionURL.indexOf(".do")==-1 && sessionURL.indexOf(".jsp") == -1)
			sessionURL += ".jsp";

		//
		System.out.println("------------- member/login_proc.jsp : " + sessionURL + "=" + sessionURL);
		
		//RequestDispatcher dispatcher = request.getRequestDispatcher(sessionURL);
		request.getRequestDispatcher(sessionURL).forward(request, response);

		// ----------------------------------------------------
		// 로그인 실패 시 :: 아래 루틴은 타지 않는다.
		// ----------------------------------------------------
	} else {

		sessionURL = "/member/login.jsp?id="+user_id;//+"&errmsg="+errorMsg;

		if (errorCd.length() > 0)
			sessionURL += "&errorCd=" + errorCd;
		if (errorMsg.length() > 0)
			sessionURL += "&errorMsg=" + errorMsg;

		//
		response.sendRedirect(sessionURL);
	}
%>