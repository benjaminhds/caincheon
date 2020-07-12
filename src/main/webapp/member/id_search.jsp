<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
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
                    <h3>회원가입 완료</h3>
                    <ul>
                        <li>홈</li>
                        <li>MEMBER</li>
                        <li>회원가입 완료</li>
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
                    <!-- member -->
                    <script>
                    function sendPassword() {
                    	if( $("#name").val() == "") { alert("이름을 입력해 주세요."); $("#name").focus(); return; }
                    	if( $("#email").val() == "") { alert("이메일을 입력해 주세요."); $("#email").focus(); return; }
                    	if( confirm("가입 당시 메일 주소로 암호 전송을 요청하시겠습니까 ? ") ) {
                    		$("#frm").submit();
                    	}
                    }
                    </script>
        			<form id="frm" name="frm" method="post" action="/member/findPwd.do">
                    <div class="member">
                        <div>
                            <p class="logTit">아이디 찾기</p>
                            <p class="logTxt">가입 당시 입력한 이메일 주소를 <br/> 통해 아이디/패스워드를 재설정해주세요.</p> <br/><br/>
                            <div class="login">
                                <label for=""></label>
                                <input type="text" id="name" name="name" placeholder="이름">
                                <label for=""></label>
                                <input type="email" id="email" name="email" placeholder="이메일">
                            </div>
                            <p class="log v2"><a href="#none" onclick="sendPassword()">확인</a></p>
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
</body>

</html>
