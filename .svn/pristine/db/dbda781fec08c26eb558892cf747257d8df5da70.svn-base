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

function goSearchList() {
	document.form01.submit();
    return false;
}

function viewDetails(bp_idx) {
	document.form01.action = '/father/holy_view.do';
	document.getElementById('bp_idx').value=bp_idx;
	document.form01.submit();
    return false;
}
</script>
<body>
<form name="form01" action="/father/holy_list.do" method="POST">
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}"/>
<input type="hidden" name="bp_idx" id="bp_idx"/>
    <p class="skipNavi" tabindex="1">본문바로가기</p>
    <!-- wrap -->
    <div id="wrap">
        <!-- header -->
        <header>
            <%@include file="/_common/inc/gnb.jsp" %>
        </header>
        <!-- //header -->
        <!-- subVisual -->
        <section class="subVisual priest">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap">
                <article> 
                    <h3>선종사제</h3>
                    <ul>
                        <li>홈</li>
                        <li>사제단</li> 
                        <li>선종사제</li>
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
                    <h3 class="ttl fl ">선종사제</h3>
                    <!-- srchSel -->
                    <div class="srchSel fr v2">
                        <dl>
                            <dt>검색</dt>
                            <dd>
                                <label for=""></label>
                                <select name="searchGubun" id="searchGubun">
                                    <option value="month" <c:out value="${searchGubunMap['month']}"/>>월별</option>
                                    <!-- option value="j_name">묘소별</option-->
                                </select>
                            </dd>
                            <dd>
                                <label for=""></label>
                                <select name="month" id="month" onChange="javascript:goSearchList()">
                                	<option value="" <c:out value="${monthMap['']}"/>>전체</option>
                                    <option value="1" <c:out value="${monthMap['1']}"/>>1</option>
                                    <option value="2" <c:out value="${monthMap['2']}"/>>2</option>
                                    <option value="3" <c:out value="${monthMap['3']}"/>>3</option>
                                    <option value="4" <c:out value="${monthMap['4']}"/>>4</option>
                                    <option value="5" <c:out value="${monthMap['5']}"/>>5</option>
                                    <option value="6" <c:out value="${monthMap['6']}"/>>6</option>
                                    <option value="7" <c:out value="${monthMap['7']}"/>>7</option>
                                    <option value="8" <c:out value="${monthMap['8']}"/>>8</option>
                                    <option value="9" <c:out value="${monthMap['9']}"/>>9</option>
                                    <option value="10" <c:out value="${monthMap['10']}"/>>10</option>
                                    <option value="11" <c:out value="${monthMap['11']}"/>>11</option>
                                    <option value="12" <c:out value="${monthMap['12']}"/>>12</option>
                                </select>
                            </dd>
                            <dd>
                                <label for=""></label>
                                <select name="j_name" id="j_name" style="display:none">
                                	<option value="">- 전 체 -</option>
                                    <option value="1">(미국)뉴욕 메리놀 본부</option>
                                    <option value="2">(미국)메리놀 뉴욕 본부</option>
                                    <option value="3">인천교구 하늘의 문 묘원</option>
                                    <option value="4">하늘의 문 묘원 성직자 묘역</option>
                                </select>
                            </dd>
                        </dl>
                    </div>
                    <!-- //srchSel -->
                    <div class="movieList">
                        <ul class="clearfix">
                        	<c:choose>
								<c:when test="${fn:length(holyList) > 0}">
									<c:forEach items="${holyList}" var="list">
			                           <li>
			                                <a href="javascript:viewDetails(${list.BP_IDX})">
			                                    <img src="/upload/de_father/${list.IMAGE}" alt="">
			                                    <span class="bod">${list.NAME} ${list.CHRISTIAN_NAME}</span>
			                                    <em>생일 : ${list.BIRTHDAY}
			                                        <br>서품일 : ${list.ORDINATION}
			                                        <br>선종일 : ${list.DEAD}
			                                        <br>묘소 : ${list.BRIAL_PLACE}
			                                    </em>
			                                </a>
			                            </li>
                                	</c:forEach>
								</c:when>
							</c:choose>

                        </ul>
                    </div>
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
