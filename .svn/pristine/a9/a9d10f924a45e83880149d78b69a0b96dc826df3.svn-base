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
        <section class="subVisual gyogu">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap">
                <article>
                    <h3>소개</h3>
                    <ul>
                        <li>홈</li>
                        <li>교구장</li>
                        <li>소개</li>
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
                    <!-- tabs -->
                    <ul class="tabs">
                        <li><a href="/gyogu/intro.jsp">약력</a></li>
                        <li><a href="/gyogu/intro_02.jsp">문장</a></li>
                        <li><a href="/gyogu/intro_03.jsp">저서</a></li>
                        <li class="on"><a href="/gyogu/intro_04.do?srch_ym=${_params.srch_ym }">일정</a></li>
                    </ul>
                    <!-- //tabs -->
                    <!-- viewPage -->
                    <div class="viewPage">
                        <table class="views">
                            <caption>상세 페이지</caption>
                            <thead>
                                <tr>
                                    <td>
                                        <span class="ttl">
                                            <i class="blue">[교구장]</i>
                                            <i>${M_SCHEDULE['TITLE'] }</i>
                                        </span>
                                         <!-- <span class="date">작성자 : 김영민 &nbsp; &nbsp; 2016. 12. 12 ~ 2016. 12. 12 </span> -->
                                         <span class="date">일 정 : ${fn:substring(M_SCHEDULE['START_DATE'], 0, 10)} ~ ${fn:substring(M_SCHEDULE['END_DATE'], 0,10) }</span>
                                    </td>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>
                                        <!-- <img src="/img/sub/_ico/board_view_test.gif" alt="">-->
                                        
                                        <span class="ttl">
                                        <ul>
					                        <li > ${M_SCHEDULE['CONTENT'] }</li>
					                    </ul>
                                        </span>
                                        
                                    </td>
                                </tr>
                            </tbody>
                            <tfoot>
                                <tr>
                                    <td>
                                        <span class="file"> <!-- 
                                            <em>첨부파일 : </em>
                                            <a href="#none">file.zip</a>
                                            <a href="#none">file.zip</a>
                                            <a href="#none">file.zip</a>  -->
                                        </span>
                                        <ul class="sns">
                                        	<c:set var="REQ_URL" value="${pageContext.request.requestURI}" />
                                        	<c:set var="SNS_URL" value="${fn:replace(REQ_URL, 'jsp', 'do')}?${pageContext.request.queryString}" />
                                        	<c:set var="SNS_DES" value="교구장 일정:${M_SCHEDULE['TITLE']}" />
                                            <li><a href="javascript:goFacebook('${SNS_URL}', '${SNS_DES}')"><img src="/img/sub/_ico/ico_facebook.png" alt="페이스북"></a></li>
                                            <li><a href="javascript:goTweeter('${SNS_URL}', '${SNS_DES}')"><img src="/img/sub/_ico/ico_tweet.png" alt="트위터"></a></li>
                                            <li><a href="javascript:goKakao('${SNS_URL}', '${SNS_DES}')"><img src="/img/sub/_ico/ico_kakao.png" alt="카카오스토리"></a></li>
                                        </ul>
                                    </td>
                                </tr>
                            </tfoot>      
                        </table>
                    </div>
                    <!-- //viewPage -->
                </div>
                <!-- //secCont -->
            </section>
            <!-- //sec01 -->
        </div>
        <!-- //secWrap -->
        <!-- footer -->
        <footer>
            <%@ include file="/_common/inc/footer.jsp" %>
        </footer>
        <!-- //footer -->
    </div>
    <!--// wrap -->
</body>

</html>
