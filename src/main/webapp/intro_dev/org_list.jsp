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
<form name="form01" action="/intro/org_list.do" method="POST">
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}"/>
<input type="hidden" name="code"   id="code"   value="${_params.code}"/>
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
                    <h3>기관/단체</h3>
                    <ul>
                        <li>홈</li>
                        <li>교구안내</li>
                        <li>기관/단체</li>
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
                    <ul class="tabs v7"><!-- TODO 현업에게 확인 후 코드를 정해야 함. code(w_code)를 쓸 것인지 lv1를 사용할지 확정 필요 -->
                        <li ${subTitleOn1}><a href="/intro/org_list.do?code=34&lv1x=05">피정의집</a></li><!-- W_CODE=34 -->
                        <li ${subTitleOn2}><a href="/intro/org_list.do?code=8&lv1x=06">특수사목</a></li><!-- W_CODE=8 -->
                        <li ${subTitleOn3}><a href="/intro/org_list.do?code=22&lv1x=09">단체</a></li><!-- W_CODE=22 -->
                        <li ${subTitleOn4}><a href="/intro/org_list.do?code=61&lv1x=0701">의료기관</a></li><!-- W_CODE=61 -->
                        <li ${subTitleOn5}><a href="/intro/org_list.do?code=5&lv1x=0702">교육기관</a></li><!-- W_CODE=5 -->
                        <li ${subTitleOn6}><a href="/intro/org_list.do?code=7&lv1x=0704">사회복지기관</a></li><!-- W_CODE=7 -->
                        <li ${subTitleOn7}><a href="/intro/org_list.do?code=1&lv1x=03">아동청소년재단</a></li><!-- W_CODE=1 -->
                    </ul>
                    <!-- //tabs -->
                    <h3 class="ttl tb fl">${listSubTitle}</h3>
                    <!-- srchSel -->
                    <div class="srchSel fr">
                        <dl>
                            <span>
                            	<c:if test="${_params.code eq '7'}">
	                        	<span>
	                        	<dt>분류</dt>
	                            <dd>
	                                <label for=""></label>
	                                <select name="org_lv2" id="org_lv2">
	                                <c:if test="${fn:length(orgList) > 0}">
									<c:forEach items="${orgList}" var="list">
										<option value="${list.ORG_IDX }">${list.NAME }</option>
									</c:forEach>
									</c:if>
									</select>
									<label for=""></label>
	                                <select name="wf_code" id="wf_code">
	                                <c:if test="${fn:length(welfareCodeList) > 0}">
									<c:forEach items="${welfareCodeList}" var="list">
										<option value="${list.CODE_INST }">${list.NAME }</option>
									</c:forEach>
									</c:if>
									</select>
	                            </dd>
	                            </span>
	                            </c:if>
	                            
	                            <dt>기관명</dt>
	                            <dd>
	                                <label for=""></label>
	                                <input type="search" name="searchText" id="searchText"  value="${_params.searchText}">
	                                <button name="button" onclick="javascript:goSearchList()">검색</button>
	                            </dd>
                            </span>
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
								<c:when test="${fn:length(LIST) > 0}">
									<c:forEach items="${LIST}" var="list">
	                                <tr>
	                                    <th scope="row">${list.RNUM}</th>
	                                    <td><c:if test="${list.HOMEPAGE ne ''}">
		                                    	<a href="${list.HOMEPAGE}" target="_blank" title="새창">
		                                    </c:if>
		                                    	${list.NAME} ${list.ETC_NAME }
		                                    <c:if test="${list.HOMEPAGE ne ''}">
		                                    	</a>
		                                    </c:if><c:if test="${list.P_NAME ne '' and list.P_NAME ne 'null' and fn:length(list.P_NAME)>0 }">[${list.P_NAME }]</c:if>
	                                    </td>
	                                    <!-- <td><a href="tel:${list.TEL}" class="sendTel"><span class="tels">${list.TEL}</span></a></td> -->
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
