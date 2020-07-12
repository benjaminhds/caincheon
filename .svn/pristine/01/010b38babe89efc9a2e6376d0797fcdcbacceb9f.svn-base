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

function viewDetails(gubuncd,s_idx) {
	document.form01.action = '/news/sch_view.do';
	document.getElementById('gubuncd').value=gubuncd;
	document.getElementById('s_idx').value=s_idx;
	document.form01.submit();
    return false;
}
</script>
<body>
<form name="form01" method="POST">
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}"/>
<input type="hidden" name="gubuncd" id="gubuncd"/>
<input type="hidden" name="s_idx" id="s_idx"/>
    <p class="skipNavi" tabindex="1">본문바로가기</p>
    <!-- wrap -->
    <div id="wrap">
        <!-- header -->
        <header>
            <%@include file="/_common/inc/gnb.jsp" %>
        </header>
        <!-- //header -->
        <!-- subVisual -->
        <section class="subVisual news">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap">
                <article> 
                    <h3>교구일정</h3>
                    <ul>
                        <li>홈</li>
                        <li>소식</li> 
                        <li>교구일정</li>
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
                        <li${subTitleOn1}><a href="/news/sch_list.do?gubuncd=1">전체보기</a></li><!-- display_yn='Y' -->
                        <li${subTitleOn2}><a href="/news/sch_list.do?gubuncd=2">교구장</a></li><!-- display_yn='Y' and gubun=1 -->
                        <li${subTitleOn3}><a href="/news/sch_list.do?gubuncd=3">총대리</a></li><!-- display_yn='Y' and gubun=2 -->
                        <li${subTitleOn4}><a href="/news/sch_list.do?gubuncd=4">교구</a></li><!-- display_yn='Y' and org_idx=1 -->
                        <li${subTitleOn5}><a href="/news/sch_list.do?gubuncd=5">부서</a></li><!-- display_yn='Y' and org_idx!=1 -->
                    </ul>
                    <!-- //tabs -->
                    <h3 class="ttl tb fl">${strCategoryName}</h3>                  
                    <!-- srchSel -->
                    <div class="srchSel fr">
                        <dl>
                            <dt>검색</dt>
                            <dd>
                                <label for=""></label>
                                <select name="schTextGubun" id="schTextGubun">
                                    <option value="title" <c:out value="${schTextGubunMap['title']}"/>>제목</option>
                                    <option value="content" <c:out value="${schTextGubunMap['content']}"/>>내용</option>
                                    <option value="all" <c:out value="${schTextGubunMap['all']}"/>>제목 + 내용</option>
                                </select>
                            </dd>
                            <dd>
                                <label for=""></label>
                                <input type="search" name="schText" id="schText" value="${searchText}">
                                 <button onClick="this.form.submit()">검색</button>
                            </dd>
                        </dl>
                    </div>
                    <!-- //srchSel -->
                    <!-- boardList -->
                    <div class="boardList oflow v2">
                        <table>
                            <caption>교구일정 전체보기</caption>
                            <colgroup>
                                <col style="width:10%">
                                <col>
                                <col style="width:10%">
                                <col style="width:10%">
                                <col style="width:8%">
                                <col style="width:8%">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col">No.</th>
                                    <th scope="col">행사명</th>
                                    <th scope="col">교구장/총대리</th>
                                    <th scope="col">주관부서</th>
                                    <th scope="col">시작일</th>
                                    <th scope="col">종료일</th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:choose>
								<c:when test="${fn:length(schList) > 0}">
									<c:forEach items="${schList}" var="list">
									<tr>
	                                    <th scope="row">${list.RNUM}</th>
	                                    <td><a href="javascript:viewDetails('${gubuncd}','${list.S_IDX}')">${list.TITLE}</a></td>
	                                    <td><strong>[${list.GUBUN}]</strong></td>
	                                    <td><strong>${list.ORG_NAME}<c:if test="${fn:length(list.ORG_NAME) eq 0}">교구</c:if></strong></td>
	                                    <td>${list.START_DATE}</td>
	                                    <td>${list.END_DATE}</td>
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
