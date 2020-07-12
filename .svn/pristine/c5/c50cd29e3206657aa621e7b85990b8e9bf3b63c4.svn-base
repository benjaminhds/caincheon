<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<%@ include file="inc_authkeys_kakao.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width"/>
<title>Login Kakao - TEST </title>
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<script src="kakao_sdk.js.js"></script>

</head>
<body>
<a id="kakao-login-btn"></a>
<a href="http://developers.kakao.com/logout"></a>
<script type='text/javascript'>


var recvData_accesstoken="";
var recvData_refreshtoken="";

var recvData_kaccount_id="431054833";
var recvData_kaccount_email="webadmin@caincheon.or.kr";
var recvData_kaccount_email_verified="true";
var recvData_kaccount_uuid="zvfP-cn6z_dR79_zwlg";


  //<![CDATA[
    // 사용할 앱의 JavaScript 키를 설정해 주세요.
    //Kakao.init('689d242c539bbadca087c0e8f3b47973');
    Kakao.init('<%=appkey_kakao%>');
    // 카카오 로그인 버튼을 생성합니다.
    /*
    // Get a access token
    Kakao.Auth.createLoginButton({
      container: '#kakao-login-btn',
      success: function(authObj) {
    	console.log("---------- 접근키 가져오기 --------------");
        console.log(JSON.stringify(authObj));
    	
        recvData_accesstoken =authObj["access_token"];
        recvData_refreshtoken=authObj["refresh_token"];
        recvData_refreshtoken=authObj["token_type"];
        recvData_refreshtoken=authObj["expires_in"];
        recvData_refreshtoken=authObj["scope"];
        
        
        console.log("[1]"+authObj["access_token"]);
        console.log("[2]"+authObj["refresh_token"]);
        console.log("[3]"+authObj["token_type"]);
        console.log("[4]"+authObj["expires_in"]);
        console.log("[5]"+authObj["scope"]);
        
        
        //var debugs = JSON.stringify(authObj) + "\n\naccess_token=" + recvData_accesstoken + "\nrefresh_token=" + refresh_token;
        //alert(debugs);
        
        //console.log(debugs);
        
      },
      fail: function(err) {
         alert(JSON.stringify(err));
      }
    });
    */
    
	
    
    // 사용할 앱의 JavaScript 키를 설정해 주세요.
    //Kakao.init('1cf4885573510d2b8cfa39ebe13a8a55');
    // Get a registered email in the kakao
    Kakao.Auth.createLoginButton({
      container: '#kakao-login-btn',
      success: function(authObj) {
        // 로그인 성공시, API를 호출합니다.
        Kakao.API.request({
          url: '/v1/user/me',
          success: function(res) {
            //alert(JSON.stringify(res));
            console.log("---------- 회원정보 가져오기 --------------");
            console.log(JSON.stringify(res));
        	
            recvData_accesstoken =res["id"];
            recvData_accesstoken =res["uuid"];
            recvData_accesstoken =res["kaccount_email"];
            recvData_refreshtoken=res["kaccount_email_verified"];
            

            console.log("[6]"+res["id"]);
            console.log("[7]"+res["uuid"]);
            console.log("[8]"+res["kaccount_email"]);
            console.log("[9]"+res["kaccount_email_verified"]);
            
            // 
            opener.gotoLoginWithSnsId( res["kaccount_email"] );
          },
          fail: function(error) {
            alert(JSON.stringify(error));
          }
        });
      },
      fail: function(err) {
        alert(JSON.stringify(err));
      }
    });
    
    
  //]]>
</script>

</body>
</html>