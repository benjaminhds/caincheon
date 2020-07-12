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
<input type="hidden" name="t"     value="${_params.t}"/>
<input type="hidden" name="lv1"   value="${_params.lv1}"/>
<input type="hidden" name="lv2"   value="${_params.lv2}"/>
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
                    <ul class="tabs v7">
                        <li ${subTitleOn1}><a href="/intro/org_list.do?t=1&lv1=05">피정의집</a></li><!-- 05 -->
                        <li ${subTitleOn2}><a href="/intro/org_list.do?t=2&lv1=06&lv2=!09">특수사목</a></li><!-- 06, 09 제외 -->
                        <li ${subTitleOn3}><a href="/intro/org_list.do?t=3&lv1=09">단체</a></li><!-- 09 -->
                        <li ${subTitleOn4}><a href="/intro/org_list.do?t=4&lv1=11">의료기관</a></li><!-- 11 -->
                        <li ${subTitleOn5}><a href="/intro/org_list.do?t=5&lv1=10">교육기관</a></li><!-- 10 -->
                        <li ${subTitleOn6}><a href="/intro/org_list.do?t=6&lv1=12&lv=2">사회복지기관</a></li><!-- 12 -->
                        <li ${subTitleOn7}><a href="/intro/org_list.do?t=7&lv1=03&lv2=02">아동청소년재단</a></li><!-- 03, 02 -->
                    </ul>
                    <!-- //tabs -->
                    <h3 class="ttl tb fl">${listSubTitle}</h3>
                    <!-- srchSel -->
                    <div class="srchSel fr">
                        <dl>
                            <span>
                            	<c:if test="${_params.t eq '6'}">
	                        	<span>
	                        	<dt>분류</dt>
	                            <dd>
	                                <label for=""></label>
	                                <select name="org_lv2" id="org_lv2">
	                                	<option value="">전체</option>
		                                <c:if test="${fn:length(orgList) > 0}">
										<c:forEach items="${orgList}" var="list">
											<option value="${list.ORG_IDX }" <c:if test="${_params.org_lv2 eq list.ORG_IDX}">selected</c:if> >${list.NAME }</option>
										</c:forEach>
										</c:if>
									</select>
									<label for=""></label>
	                                <select name="wf_code" id="wf_code">
	                                	<option value="">전체</option>
		                                <c:if test="${fn:length(welfareCodeList) > 0}">
										<c:forEach items="${welfareCodeList}" var="list">
											<option value="${list.CODE_INST }" <c:if test="${_params.wf_code eq list.CODE_INST}">selected</c:if> >${list.NAME }</option>
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
	                                    <td><c:if test="${fn:length(list.HOMEPAGE) > 0}">
		                                    	<a href="${list.HOMEPAGE}" target="_blank" title="새창">
		                                    </c:if>
		                                    	${list.NAME}
		                                    <c:if test="${fn:length(list.HOMEPAGE) > 0}">
		                                    	</a>
		                                    </c:if><c:if test="${list.P_NAME ne '' and list.P_NAME ne 'null' and fn:length(list.P_NAME)>0 }"><BR>[${list.P_NAME }]</c:if> ${list.ETC_NAME }
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
