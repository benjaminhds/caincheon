<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %><%
// if user loggined, redirect to member's information page.
if(sessionMemId.length() > 0 && sessionMemNm.length() > 0) {
	//request.getRequestDispatcher("/member/findWithSessionId.do?id="+sessionMemId).forward(request, response);
	request.getRequestDispatcher("/member/login.jsp").forward(request, response);
}
%>
<body>
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
                    <h3>나의정보</h3>
                    <ul>
                        <li>홈</li>
                        <li>MEMBER</li>
                        <li>나의정보</li>
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
                    <h3 class="blind">나의정보 내용</h3>
                    <ul class="tabs ea5">
                    	<li class="on"><a href="/member/myinfo.jsp">개인정보 수정</a></li>
                        <li><a href="/member/my_doctrine.do">통신교리신청</a></li>
                        <li class="lineTwo"><a href="/member/my_cana.do">카나혼인강좌/약혼자 주말신청</a></li>
                        <li><a href="/member/my_tale.do">나누고 싶은 이야기</a></li>
                    </ul>
                    <!-- member -->
                    <script>
                    var member_type_val = 1;
                    function login() {
                    	if( $("#id").val() == "") { alert("아이디(이메일)를 입력해 주세요."); $("#id").focus(); return; }
                    	if( $("#password").val() == "") { alert("패스워드를 입력해 주세요."); $("#password").focus(); return; }
                    	$("#frm").submit();
                    }
                    </script>
        			<form id="frm" name="frm" method="post" action="/member/myinfo_edit.do">
                    <div class="member">
                        <div>
                            <p class="logTxt">로그인을 하시면 개인정보를 수정 하실 수 있습니다. </p><br>
                            <div class="login">
                                <label for=""></label>
                                <input type="text" id="id" name="id" placeholder="아이디">
                                <label for=""></label>
                                <input type="password" id="password" name="pw" placeholder="비밀번호">
                            </div>
                            <p class="log"><a href="javascript: login()">로그인</a></p>
                            <!--ul class="snsLog">
                                <li class="fb"><a href="#none">FaceBook 로그인</a></li>
                                <li class="ko"><a href="#none">KaKao 로그인</a></li>
                                <li class="nv"><a href="#none">NAVER 로그인</a></li>
                            </ul--> 
                        </div>                       
                    </div>
                    </form>
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
