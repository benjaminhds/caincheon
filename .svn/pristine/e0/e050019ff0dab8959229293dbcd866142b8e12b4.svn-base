<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %><%@ 
include file="../sns/inc_authkeys_naver.jsp" %><%

// token.json 을 수신하는데, 파싱해서 access_token 은 저장한다.
String accessTokenURL = "https://nid.naver.com/oauth2.0/token"
		  + "?grant_type=authorization_code"
		  + "&client_id="+clientid_naver
		  + "&client_secret="+clientsecet_naver
		  + "&code="+ request.getParameter("code")
		  + "&state="+ request.getParameter("state")
		  ;

// 응답 access token 파일 내용
/*
{
"access_token":"AAAAN65SELYmjtO86/vvvxSLg8OycuYI/9DSN+fXGYnt+dqcuAvBZ3kqopfx7Fy5lkhzN1WJHYZG8Z+Pe5oS+m+xnPY=",
"refresh_token":"Qm83dqMDhahdULLkrnoFrlEyap9HqxRAjITY0BlNGJD95sclJevzFe5QlD8IvYl1ZiicvoUJy1AvmBQvt0pZ6SzlIwMrhJzHAgqVRisNWkz3avPXMtNQgXIyzufjjipp6his",
"token_type":"bearer",
"expires_in":"3600"
}
*/

%>
<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
  var naver_id_login = new naver_id_login("<%=clientid_naver%>", "<%=callbackURL_naver_join%>");
  // 접근 토큰 값 출력
  //alert(naver_id_login.oauthParams.access_token);
  // 네이버 사용자 프로필 조회
  naver_id_login.get_naver_userprofile("naverSignInCallback()");
  // 네이버 사용자 프로필 조회 이후 프로필 정보를 처리할 callback function
  function naverSignInCallback() {
    //alert(naver_id_login.getProfileData('email'));
    //alert(naver_id_login.getProfileData('nickname'));
    //alert(naver_id_login.getProfileData('age'));
    
    window.opener.location.href = "/member/step_01.jsp?sns_id=" + naver_id_login.getProfileData('email') + "&login_type=4" ;
    window.close();
  }

</script>