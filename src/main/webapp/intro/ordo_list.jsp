<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<script type="text/javascript">
function goPage(pageNo) {
	document.getElementById('pageNo').value=pageNo;
	document.form01.submit();
    return false;
}

function goSearch() {
	/*
	if(document.getElementById('searchText').value == '') {
		alert('검색할 내용을 입력하세요.');
		document.getElementById('searchText').focus();
		return false;
	}
	*/
	
	document.form01.submit();
    return false;
}

</script>
<body>
<form name="form01" action="/intro/ordo_list.do" method="POST">
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}"/>
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
                    <h3>수도회</h3>
                    <ul>
                        <li>홈</li>
                        <li>교구안내</li>
                        <li>수도회</li>
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
                    	<li><a href="/intro/ordo_list.do?lv1=08">전체보기</a></li>
						<li><a href="/intro/ordo_list.do?lv1=08&lv2=01">남자</a></li>
                        <li><a href="/intro/ordo_list.do?lv1=08&lv2=02">여자</a></li>
                    </ul>
                    <!-- //tabs -->
                    <h3 class="ttl tb fl">수도회/선교회</h3>                  
                    <!-- srchSel -->
                    <div class="srchSel fr">
                        <dl>
                            <dt>기관명</dt>
                            <dd>
                                <label for=""></label>
                                <input type="search" name="searchText" id="searchText" value="${searchText}">
                                <button name="button" onclick="javascript:goSearch()">검색</button>
                            </dd>
                        </dl>
                    </div>
                    <!-- //srchSel -->
                    <!-- boardList -->
                    <div class="boardList oflow v3">
                        <table>
                            <caption>수도회/선교회 리스트 전체보기</caption>
                            <colgroup>
                                <col style="width:8%">
                                <col>
                                <col style="width:12%">
                                <col style="width:12%">
                                <col style="width:30%">
                                <col style="width:10%">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col">No.</th>
                                    <th scope="col">기관명</th>
                                    <th scope="col">전화번호</th>
                                    <th scope="col">팩스</th>
                                    <th scope="col">주소</th>
                                    <th scope="col">메일</th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:choose>
								<c:when test="${fn:length(ordoList) > 0}">
									<c:forEach items="${ordoList}" var="list">
	                                <tr>
	                                    <th scope="row">${list.RNUM}</th>
	                                    <td><c:if test="${list.HOMEPAGE ne ''}">
		                                    	<a href="${list.HOMEPAGE}" target="_blank" title="새창">
		                                    </c:if>
		                                    	${list.NAME}
		                                    <c:if test="${list.HOMEPAGE ne ''}">
		                                    	</a>
		                                    </c:if>
	                                    </td>
	                                    <td>${list.TEL}</td>
	                                    <td>${list.FAX}</td>
	                                    <td>${list.ADDR1} ${list.ADDR2}</td>
	                                    <td class="file">
	                                    <c:if test="${list.EMAIL ne ''}">
	                                    	<a href="mailto:${list.EMAIL}"><img src="/img/sub/_ico/board_mail.png" alt=""></a>
	                                    </c:if>
	                                    </td>
	                                </tr>
	                                </c:forEach>
									</c:when>
								</c:choose>
                            </tbody>
                        </table>
                    </div>
                    <!-- //boardList -->
                    <!-- arrow -->
                    <%@ include file="/_common/inc/paging2.jsp" %>
                    <!-- //arrow -->
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
</form>
</body>

</html>
