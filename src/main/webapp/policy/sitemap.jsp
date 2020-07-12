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
        <section class="subVisual srchBg">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap">
                <article>
                    <h3>사이트맵</h3>
                    <ul>
                        <li>홈</li>
                        <li>사이트맵</li>
                    </ul>
                </article>
            </div>
        </section>
        <!-- //subVisual -->
        <!-- secWrap -->
        <div class="secWrap">
            <!-- sec01 -->
            <section class="sec01 fin totalSearch">
                <!-- secCont -->
                <div class="secCont">
                    <h3 class="ttl">사이트맵</h3>
                    <div class="sitemap">
                        <dl>
                            <dt>교구안내</dt>
                            <dd>
                                <ul>
                                    <li><a href="/intro/intro.jsp">교구안내</a></li>
                                    <li><a href="/intro/history.do">온라인역사관</a></li>
                                    <li><a href="/intro/shirine.jsp">교구성지</a></li>
                                    <li><a href="/intro/diocesan.do">교구청</a></li>
                                    <li><a href="/intro/ordo_list.do?lv1=08">수도회</a></li>
                                    <li><a href="/intro/org_list.do?t=1&lv1=05">기관/단체</a></li>
                                </ul>
                            </dd>
                        </dl>
                        <dl>
                            <dt>교구장</dt>
                            <dd>
                                <ul>
                                    <li><a href="/gyogu/intro.jsp">소개</a></li>
                                    <li><a href="/gyogu/msg_list.do?type=ALL">메세지</a></li>
                                    <li><a href="/gyogu/par_list.do?b_idx=ALL">교구장동정</a></li>
                                    <li><a href="/gyogu/ever_list.do">역대교구장</a></li>
                                </ul>
                            </dd>
                        </dl>
                        <dl>
                            <dt>사제단</dt>
                            <dd>
                                <ul>
                                    <li><a href="/father/father_list.do">사제</a></li>
                                    <li><a href="/father/holy_list.do">선종사제</a></li>
                                </ul>
                            </dd>
                        </dl>
                        <dl>
                            <dt>본당</dt>
                            <dd>
                                <ul>
                                    <li><a href="/church/temp_01.do">본당 현황</a></li>
                                    <li><a href="/church/church.do">지구별</a></li>
                                    <li><a href="/church/vacancy.do">공소</a></li>
                                </ul>
                            </dd>
                        </dl>
                        <dl>
                            <dt>소식</dt>
                            <dd>
                                <ul>
                                    <li><a href="/news/news_list.do?b_idx=ALL">교구소식</a></li>
                                    <li><a href="/news/sch_list.do?gubuncd=1">교구일정</a></li>
                                    <li><a href="/news/alb_list.do?b_idx=ALL&c_idx=ALL">교구앨범</a></li>
                                    <li><a href="/news/mov_list.do">교구영상</a></li>
                                    <li><a href="/news/mgz_list.do?pub_idx=3">인천주보</a></li>
                                </ul>
                            </dd>
                        </dl>
                        <dl>
                            <dt>자료실</dt>
                            <dd>
                                <ul>
                                    <li><a href="http://bible.cbck.or.kr" target="_blank" title="새창">성경</a></li>
                                    <li><a href="http://www.cbck.or.kr/page/saint_kr_list.asp?p_code=K3400" target="_blank" title="새창">한국의성인</a></li>
                                    <li><a href="http://www.cbck.or.kr/page/page.asp?p_code=K4140" target="_blank" title="새창">주요기도문</a></li>
                                    <li><a href="http://missa.cbck.or.kr/liturgyinfo.asp" target="_blank" title="새창">전례력</a></li>
                                    <li><a href="http://missa.cbck.or.kr/" target="_blank" title="새창">매일미사</a></li>
                                    <li><a href="/samok/cure_list.do?c_idx=ALL">사목자료</a></li>
                                </ul>
                            </dd>
                        </dl>
                        <dl>
                            <dt>참여마당</dt>
                            <dd>
                                <ul>
                                    <li><a href="/community/doctrine_register.do">통신교리신청</a></li>
                                    <li><a href="/community/cana_register.do">카나혼인강좌 &amp; 약혼자 주말신청</a></li>                                    
                                    <li><a href="/community/tale.do">나누고 싶은 이야기</a></li>
                                </ul>
                            </dd>
                        </dl>
                        <!--dl>
                            <dt>회원</dt>
                            <dd>
                                <ul>
                                    <li><a href="/member/login.jsp">로그인</a></li>
                                    <li><a href="/member/myinfo.jsp">나의정보</a></li>                                    
                                    <li><a href="/member/register.jsp">회원가입</a></li>
                                </ul>
                            </dd>
                        </dl-->
                    </div>
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
