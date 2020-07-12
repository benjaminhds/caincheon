<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
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
                    <h3>회원가입 인증 이메일 발송</h3>
                    <ul>
                        <li>홈</li>
                        <li>MEMBER</li>
                        <li>회원가입 인증 이메일 발송</li>
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
                    <div class="member">
                        <div class="other">
                            <p class="logTit">회원가입 인증 이메일 발송 완료</p>
                            <p class="logTxt">등록하신 이메일 주소로 인증 메일을 발송하였습니다. <br/>수신 하신 인증 메일의 인증버튼을 클릭해주셔야 회원가입이 완료됩니다.</p>
                            <ol class="listTxt">
                                <li>메일이 오지 않을 경우 메일 활성화 여부를 확인 바랍니다.</li>
                                <li>휴면 메일의 경우 메일이 전송 되지 않을 수 있습니다.</li>
                                <li>스팸 메일함도 확인하여 주세요.</li>
                            </ol>
                            <p class="log"><a href="/home.do">메인으로</a></p>
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
