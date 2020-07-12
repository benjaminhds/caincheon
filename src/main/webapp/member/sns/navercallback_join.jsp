<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%@ 
	include file="/_common/inc/headSub.jsp" %><%@ 
	include file="../sns/inc_authkeys_naver.jsp" %>	
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
    $("#email", opener.document).val( naver_id_login.getProfileData('email') );
    $("#login_type", opener.document).val( "2" );
    $("#frm", opener.document).attr("action", '/member/joinform.do');
    $("#frm", opener.document).submit();
    window.close();
  }
</script>