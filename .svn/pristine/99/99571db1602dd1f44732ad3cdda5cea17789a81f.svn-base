<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %><%

String gotoBACK = request.getParameter("gotoBACK");
if(gotoBACK != null) {
	request.setAttribute("gotoBACK", gotoBACK);
	request.setAttribute("gotoURL", gotoBACK);
}
%>
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
	FB.api('/me?fields=email',function(user){
		  
		var myEmail = user.email;
		  
		if(myEmail != ""){
			goJoinForm(user.email,'2');
		}
	  
	});
	  FB.api('/me/picture?type=large',function(data){
	  	var myImg = data.data.url;
	  });
}

function goJoinForm(email,type) {
	document.getElementById('pid').value=email;
	return false;
}

</script>
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
                    <h3>로그인</h3>
                    <ul>
                        <li>홈</li>
                        <li>MEMBER</li>
                        <li>로그인</li>
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
                    <h3 class="blind">로그인 내용</h3>
                    <!-- member -->
                    <div class="member">
                        <div>
                            <div class="login">
                        <script>
                        function goLogin() {
                        	var id = $("#pid").val();
                        	var pw = $("#ppw").val();
                        	if(id=="") { alert("아이디를 입력해 주세요."); $("#pid").focus(); return; }
                        	if(pw=="") { alert("암호를 입력해 주세요.");  $("#ppw").focus(); return; }
                        	$("#loginform").submit();
                        }
                        
                        function press(f) {
                        	if(f.keyCode == 13){
                        		//javascript에서는 13이 enter키를 의미함 
                        		goLogin();
                        	}
                        }
                        </script>
                        <form  method="post" name="loginform" id="loginform"  action="/member/login.do">
                        	<c:choose>
                        		<c:when test="${fn:length(gotoURL) > 0 }">
                        			<c:set var="idx" value="${fn:indexOf(gotoURL, '?') }" />
			                    	<c:set var="gotoURL" value="${fn:substring(gotoURL, 0, fn:indexOf(gotoURL, '?')) }" />
                        		</c:when>
                        		<c:otherwise>
                        			<c:set var="gotoURL" value="${gotoURL}" />
                        		</c:otherwise>
                        	</c:choose>
		                    	<input type="hidden" name="gotoURL" id="gotoURL" value="${gotoURL}" >
                                <label for=""></label>
                                <input type="text" id="pid" name="id" value="${id}" placeholder="아이디"/>
                                <label for=""></label>
                                <input type="password" id="ppw" name="pw" value="" placeholder="비밀번호"/>
                                <p class="log"><a href="javascript: goLogin()">로그인</a></p>
                                <script>
                                // 이전 페이지로 자동으로 찾아가는 센스
                                var defineUri = "${gotoURL}";
                                if(defineUri=="" || defineUri=="/home.do") {
                                	$("#gotoURL").val(document.referrer.replace($(location).attr("host"),"").replace("http://",""));
                                }
                        		</script>
                        </form>
                            </div>
                            
                            <% /* include file="/member/sns/inc_sns_button_login.jsp" */ %>
                             
                            <ol class="logSerch">
                                <li><a href="/member/id_search.jsp">아이디/비밀번호 찾기</a></li>
                                <!--<li><a href="/member/pass_search.jsp">비밀번호 찾기</a></li> -->
                                <li><a href="/member/register.jsp">회원가입</a></li>
                            </ol>
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
     <c:if test = "${fn:length(ERR_MSG) > 0 }">
     <script>
     alert("${ERR_MSG}");
     </script>
     </c:if>
     
     
     <c:if test = "${fn:length(DORMANT_CLEAR_MAIL) > 0 }">
     <script>
     alert("${DORMANT_CLEAR_MAIL}");
     </script>
     </c:if>
</body>

</html>
