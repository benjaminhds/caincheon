<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<body>
<script>
window.fbAsyncInit = function() {
    FB.init({
      appId      : '1884752561738506',
      xfbml      : true,
      version    : 'v2.9'
    });
    FB.AppEvents.logPageView();
};

(function(d, s, id){
     var js, fjs = d.getElementsByTagName(s)[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement(s); js.id = id;
     js.src = "//connect.facebook.net/en_US/sdk.js";
     fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

function myFacebookLogin() {  
	  FB.login(function(response) {
		console.log("페북응답:"+JSON.stringify(response));
	    if (response.status === 'connected') {
	   		getMyProfile();
	    } else if (response.status === 'not_authorized') {
	      // 페이스북에는 로그인 되어있으나, 앱에는 로그인 되어있지 않다.
	    } else {
	      // 페이스북에 로그인이 되어있지 않아서, 앱에 로그인 되어있는지 불명확하다.
	    }
	  } , {scope: "email"} ); //나는 유저의 아이디(이메일)과 생일 정보를 얻어오고 싶다.
} 
	  
function getMyProfile(){
	FB.api('/me?fields=email',function(user) {
		console.log("페북user:"+JSON.stringify(user));
		var myEmail = user.email;
		if(myEmail != ""){
			//alert(user.email);
			goJoinForm(user.email,'2');
		}
	});
	
	FB.api('/me/picture?type=large',function(data){
		console.log("페북user:"+ JSON.stringify(data));
	  	//var myImg = data.url;
	});
}

function goJoinForm(email,type) {
	document.frm.action = '/member/joinform.do';
	document.getElementById('email').value=email;
	document.getElementById('login_type').value=type;//type=2 --> sns 인증방식으로 로그인임.
	document.frm.submit();
    return false;
}

</script>
<form id="frm" name="frm" method="post">
<input type="hidden" id="login_type" name="login_type" value="" />
<input type="hidden" id="email" name="email" value=""/>
</form>
    <p class="skipNavi" tabindex="1">본문바로가기</p>
    <!-- wrap -->
    <div id="wrap">
        <!-- header -->
        <header>
            <%@include file="/_common/inc/gnb.jsp" %>
        </header>
        <!-- //header -->
        <!-- subVisual -->
        <section class="subVisual commu memb">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap">
                <article>
                    <h3>회원가입</h3>
                    <ul>
                        <li>홈</li>
                        <li>MEMBER</li>
                        <li>회원가입</li>
                    </ul>
                </article>
            </div>
        </section>
        <!-- //subVisual -->
        <!-- secWrap -->
        <div class="secWrap">
            <!-- sec01 -->
            <section class="sec01 fin">
                <!-- secCont -->
                <div class="secCont">
                    <h3 class="blind">회원가입 내용</h3>
                    <!-- member -->
                    <div class="member">
                        <div>
                            <p class="logTit">천주교 인천교구 일반 회원가입</p>
                            <p class="logTxt">메일인증 후, 천주교 인천교구 회원으로 가입합니다.</p>
                            <p class="log"><a href="javascript:goJoinForm('','1')">회원가입</a></p>
                            <p class="logTit v2">SNS 계정으로 Email 인증하기</p>
                            <p class="logTxt">SNS 아이디(Email)를 이용해서 천주교 인천교구 회원으로 가입합니다.</p>
                            <ul class="snsLog">
                                <li class="fb"><a href="javascript:myFacebookLogin()">FaceBook 아이디 인증</a></li>
<!--                                 <li class="ko"><a href="#none">KaKao 로그인</a></li>
                                <li class="nv"><a href="#none">NAVER 로그인</a></li>
-->
                                <li class="ko" id="kakao-logged-group"><div id="kakao-login-btn"></div><div id="kakao-profile"></div></li>
                                <li class="nv" id="naver_id_login"></li>
<!-- //네이버아이디로로그인 버튼 노출 영역 -->
<%@ include file="sns/inc_naver_login.jsp" %>
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
<%@ include file="sns/inc_authkeys_kakao.jsp" %>
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<script src="/member/sns/kakao_sdk.js"></script>
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
					console.log(res);
					console.log(JSON.stringify(res));
					if(res["kaccount_email_verified"] == true && res["kaccount_email"] != "" && res["kaccount_email"].indexOf("@") > 1) {
						console.log(res["kaccount_email"]);
						goJoinForm(res["kaccount_email"],"2");
					}
				},
				fail: function(error) {
					console.log(error);
				}
			});
		}
		function createKakaotalkLogin(){
			$("#kakao-logged-group .kakao-logout-btn,#kakao-logged-group .kakao-login-btn").remove();
			var loginBtn = $("<a/>",{"class":"kakao-login-btn","text":"카카오 아이디 인증"});
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
                        </div>
                    </div>
                    <!-- //member -->
                </div>
                <!-- //secCont -->
            </section>
            <!-- //sec01 -->
        </div>
        <!-- //secWrap -->
        <!-- footer -->
        <footer>
            <%@include file="/_common/inc/footer.jsp" %>
        </footer>
        <!-- //footer -->
    </div>
    <!--// wrap -->
</body>

</html>
