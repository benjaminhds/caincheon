<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %><%@ 
page import="java.net.URLEncoder, java.security.SecureRandom, java.math.BigInteger, kr.caincheon.church.common.CommonStorage" %>

<!-- //네이버아이디로로그인 버튼 노출 영역 -->
<%@ include file="../sns/inc_naver_login.jsp" %>
<script type="text/javascript" src="sns/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
  	var naver_id_login = new naver_id_login("<%=clientid_naver%>", "<%=callbackURL_naver_join%>");
  	var state = naver_id_login.getUniqState();
  	naver_id_login.setButton("g", 5, 40);
  	naver_id_login.button_width = 440;
  	naver_id_login.button_height = 50;
  	naver_id_login.setDomain("<%=clientserviceurl_naver%>");
  	naver_id_login.setState(state);
  	naver_id_login.setPopup();
  	naver_id_login.init_naver_id_login();
</script>

<!-- //카카오로그인 버튼 노출 영역 -->
<%@ include file="../sns/inc_authkeys_kakao.jsp" %>
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<script src="kakao_sdk.js"></script>
<script>
	$(document).ready(function(){
		Kakao.init("<%=appkey_kakao%>");
		function getKakaotalkUserProfile(){
			Kakao.API.request({
				url: '/v1/user/me',
				success: function(res) {
					//$("#kakao-profile").append(res.properties.nickname);
					//$("#kakao-profile").append($("<img/>",{"src":res.properties.profile_image,"alt":res.properties.nickname+"님의 프로필 사진"}));
		            //alert("[6]"+res["id"]);
		            //alert("[8]"+res["kaccount_email"]);
		            //alert("[9]"+res["kaccount_email_verified"]);
		            alert( "?"+res["kaccount_email_verified"] );
		            if(res["kaccount_email_verified"] == true) {
		            	window.location.href = "/member/step_01.jsp?login_type=3&sns_id=" + res["kaccount_email"] ;
		            }
				},
				fail: function(error) {
					console.log(error);
				}
			});
		}
		function createKakaotalkLogin(){
			$("#kakao-logged-group .kakao-logout-btn,#kakao-logged-group .kakao-login-btn").remove();
			var loginBtn = $("<a/>",{"class":"kakao-login-btn","text":"카카오 아이디로 로그인"});
			loginBtn.click(function(){
				Kakao.Auth.login({
					persistAccessToken: true,
					persistRefreshToken: true,
					success: function(authObj) {
						getKakaotalkUserProfile();
						createKakaotalkLogout();
					},
					fail: function(err) {
						console.log(err);
					}
				});
			});
			$("#kakao-logged-group").prepend(loginBtn)
		}
		function createKakaotalkLogout(){
			$("#kakao-logged-group .kakao-logout-btn,#kakao-logged-group .kakao-login-btn").remove();
			var logoutBtn = $("<a/>",{"class":"kakao-logout-btn","text":"KAKAO 로그아웃"});
			logoutBtn.click(function(){
				Kakao.Auth.logout();
				createKakaotalkLogin();
				$("#kakao-profile").text("");
			});
			$("#kakao-logged-group").prepend(logoutBtn);
		}
		if(Kakao.Auth.getRefreshToken()!=undefined&&Kakao.Auth.getRefreshToken().replace(/ /gi,"")!=""){
			createKakaotalkLogout();
			getKakaotalkUserProfile();
		}else{
			createKakaotalkLogin();
		}
	});
</script>