<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
    <%@ include file="/_common/inc/headSub.jsp" %>
<body class="loading">
    <p class="skipNavi" tabindex="1">본문바로가기</p>
    <!-- wrap -->
    <div id="wrap">
        <!-- header -->
        <header>
            <%@include file="/_common/inc/gnb.jsp" %>
        </header>
        <!-- //header -->
        <!-- subVisual -->
        <section class="subVisual intro">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap">
                <article>
                    <h3>온라인역사관</h3>
                    <ul>
                        <li>홈</li>
                        <li>교구안내</li>
                        <li>온라인역사관</li>
                    </ul>
                </article>
            </div>
        </section>
        <!-- //subVisual -->
        <!-- secWrap -->
        <div class="secWrap">
            <!-- sec01 -->
            <section class="sec01">
                <!-- secWide -->
                <div class="secWide">
                    <!-- visuals -->
                    <div class="visuals">
                        <div class="naviArea flexslider">
                            <ul class="navis slides">
                                <c:choose>
                                    <c:when test="${fn:length(categoryList) > 0}">
                                        <c:forEach items="${categoryList}" var="list" varStatus="status">
                                            <li <c:if test="${status.index eq 0 }">class="on"</c:if> >
                                                <a class="num${status.index}" href="#none">${list.CATEGORY_NAME}</a>
                                            </li>
                                        </c:forEach>
                                    </c:when>
                                </c:choose>
                            </ul>                            
                            <!-- <a href="#none" class="btnLeft bt-arr"><img src="/img/sub/slide_left.png" alt=""></a>
                            <a href="#none" class="btnRight bt-arr"><img src="/img/sub/slide_right.png" alt=""></a> -->
                        </div>
                        <div class="imgArea flexslider">
                            <ul class="vis slides">
                                <c:choose>
                                    <c:when test="${fn:length(categoryList) > 0}">
                                        <c:forEach items="${categoryList}" var="list" varStatus="status">
                                            <li>
                                                <img src="${list.FILE_PATH}${list.IMAGE}" alt="">
                                            </li>
                                        </c:forEach>
                                    </c:when>
                                </c:choose>
                            </ul>
                        </div>
                    </div>
                    <!-- //visuals -->
                </div>
                <!-- //secWide -->
            </section>
            <!-- //sec01 -->
            <!-- sec02 -->
            <section class="sec02 fin">
                <!-- secCont -->
                <div class="secCont histories">
                    <!-- yearWrapper -->
                    <div class="yearWrapper flexslider">
                        <ul class="years slides">
                        <c:choose>
                            <c:when test="${fn:length(historyYearList) > 0}">
                                <c:forEach items="${historyYearList}" var="list" varStatus="status">
                                    <li>
                                        <a href="#none">${list.EVENTYEAR}</a>
                                    </li>
                                </c:forEach>
                            </c:when>
                        </c:choose>
                        </ul>
                    </div>
                    <!-- //yearWrapper -->
                    <!-- yearWrap y0 -->
                    <div class="yearWrap on">
                        <!-- yearCont-->
                        <c:choose>
                            <c:when test="${fn:length(historyYearList) > 0}">
                                <c:forEach items="${historyYearList}" var="list" varStatus="status">
                                    <c:set value="${historyMap[list.EVENTYEAR]}" var="YEAR_ROWS" />
                        
                                    <div class="y${list.EVENTYEAR} yearCont secCont ">
                                        <dl>
                                            <dt>${list.EVENTYEAR}</dt>
                                            <dd>
                                                <ul>
                                                    <c:choose>
                                                        <c:when test="${fn:length(YEAR_ROWS) > 0}">
                                                            <c:forEach items="${YEAR_ROWS}" var="row" varStatus="status">
                                                                <li class='qus'>
                                                                    <font color="#2368ad">${row.EVENTMMDD}</font> &nbsp; ${row.TITLE }</li>
                                                                <li class='ans'>
                                                                    <c:if test="${fn:length(row.IMAGE) > 0}">
                                                                        <img src="${row.FILE_PATH}${row.IMAGE}">
                                                                    </c:if>${row.CONTENTS }</li>
                                                            </c:forEach>
                                                        </c:when>
                                                    </c:choose>
                                                </ul>
                                            </dd>
                                        </dl>
                                    </div>
                        
                                </c:forEach>
                            </c:when>
                        </c:choose>
                </div>
                <!-- //secCont -->
            </section>
            <!-- //sec02 -->
        </div>
        <!-- //secWrap -->
        <!-- footer -->
        <footer>
            <%@ include file="/_common/inc/footer.jsp" %>
        </footer>
        <!-- //footer -->
    </div>
    <!--// wrap -->
    <script src="/_common/js/jquery.flexslider-min.js"></script>
    <script src="/_common/js/slide_new.js"></script>
</body>

</html>
