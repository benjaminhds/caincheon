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
                        <li class="on"><a href="/gyogu/intro_04.do">일정</a></li>
                    </ul>
                    <!-- //tabs -->
                    <h3 class="ttl tb v2">인천교구 교구장 일정</h3>
                    
                    <!-- calenar -->
                    <div class="calendar">
                        <ul>
                            <li><a href="/gyogu/intro_04.do?srch_ym=${PREV_MONTH }"><img src="/img/sub/_ico/cal_arr_l.png" alt=""></a></li>
                            <li><em>${srch_ym }</em></li>
                            <li><a href="/gyogu/intro_04.do?srch_ym=${NEXT_MONTH }"><img src="/img/sub/_ico/cal_arr_r.png" alt=""></a></li>
                        </ul>
                        <table>
                            <caption>달력</caption>
                            <thead>
                                <tr>
                                    <th scope="col" class="sun">일</th>
                                    <th scope="col">월</th>
                                    <th scope="col">화</th>
                                    <th scope="col">수</th>
                                    <th scope="col">목</th>
                                    <th scope="col">금</th>
                                    <th scope="col">토</th>
                                </tr>
                            </thead>
                            <tbody>
                            	
                           <c:choose>
							<c:when test="${fn:length(L_DIARY) > 0}">
								<c:forEach items="${L_DIARY}" var="DIARY">
								<tr>
                                    <td class="sun" ><span>${DIARY['일'] } <c:forEach var="entry" items="${M_SCHEDULE[DIARY['일']]}" varStatus="status"> <a href="/gyogu/intro_04_view.do?srch_ym=${srch_ym }&s_idx=${entry.key}">${entry.value}</a></c:forEach></span></td>
                                    <td class="gray"><span>${DIARY['월'] } <c:forEach var="entry" items="${M_SCHEDULE[DIARY['월']]}" varStatus="status"> <a href="/gyogu/intro_04_view.do?srch_ym=${srch_ym }&s_idx=${entry.key}">${entry.value}</a></c:forEach></span></td>
                                    <td class="gray"><span>${DIARY['화'] } <c:forEach var="entry" items="${M_SCHEDULE[DIARY['화']]}" varStatus="status"> <a href="/gyogu/intro_04_view.do?srch_ym=${srch_ym }&s_idx=${entry.key}">${entry.value}</a></c:forEach></span></td>
                                    <td class="gray"><span>${DIARY['수'] } <c:forEach var="entry" items="${M_SCHEDULE[DIARY['수']]}" varStatus="status"> <a href="/gyogu/intro_04_view.do?srch_ym=${srch_ym }&s_idx=${entry.key}">${entry.value}</a></c:forEach></span></td>
                                    <td class="gray"><span>${DIARY['목'] } <c:forEach var="entry" items="${M_SCHEDULE[DIARY['목']]}" varStatus="status"> <a href="/gyogu/intro_04_view.do?srch_ym=${srch_ym }&s_idx=${entry.key}">${entry.value}</a></c:forEach></span></td>
                                    <td class="gray"><span>${DIARY['금'] } <c:forEach var="entry" items="${M_SCHEDULE[DIARY['금']]}" varStatus="status"> <a href="/gyogu/intro_04_view.do?srch_ym=${srch_ym }&s_idx=${entry.key}">${entry.value}</a></c:forEach></span></td>
                                    <td class="gray"><span>${DIARY['토'] } <c:forEach var="entry" items="${M_SCHEDULE[DIARY['토']]}" varStatus="status"> <a href="/gyogu/intro_04_view.do?srch_ym=${srch_ym }&s_idx=${entry.key}">${entry.value}</a></c:forEach></span></td>
                                </tr>
                               </c:forEach>
							</c:when>
						</c:choose>
                            </tbody>
                        </table>
                    </div>
                    <!-- //calenar -->
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
