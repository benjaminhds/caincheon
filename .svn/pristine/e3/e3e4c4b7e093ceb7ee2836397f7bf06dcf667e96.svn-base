<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %><%@ 
page import="java.net.URLEncoder, java.security.SecureRandom, java.math.BigInteger, kr.caincheon.church.common.CommonStorage" %><%@ 
include file="../sns/inc_authkeys_naver.jsp" %><% 
  
  String authtoken   = CommonStorage.getInstance().getOAuthKey();
  String redirecturi = URLEncoder.encode(callbackURL_naver_join, "utf-8"); 
  
  // build URL
  String naverLoginURL = apiURL_naver_login_auth_request
		  + "?response_type=code"
		  + "&scope=email"
		  + "&client_id="+clientid_naver
		  + "&redirect_uri="+redirecturi
		  + "&state="+authtoken
		  ;
  // 
  String naverProfileURL = apiURL_naver_profile 
		  + "?response_type=code"
		  + "&scope=email"
		  + "&client_id="+clientid_naver
		  + "&redirect_uri="+redirecturi
		  + "&state="+authtoken
		  ;

%><!-- 
  <pre>  
  1step) code값 받아오기 -> 네이버 로그인이 되면 바로 call back URL까지 호출되어 이메일이 검증되게 된다.
  
  2step) callback url 에서 수신된 code로  access token 을 요청하면 응답으로 token.json 을 수신한다. 파싱하여 access token 은 저장한다. 
  <a href="<%=naverLoginURL%>" target="new"><img height="70" src="http://static.nid.naver.com/oauth/small_g_in.PNG"/></a>
  </pre>
 -->
