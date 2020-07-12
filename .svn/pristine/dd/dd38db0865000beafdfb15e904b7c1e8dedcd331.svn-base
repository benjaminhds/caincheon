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

function viewNews(b_idx, bl_idx) {
	document.form01.action = '/news/news_view.do';
	document.getElementById('b_idx').value=b_idx;
	document.getElementById('bl_idx').value=bl_idx;
	document.form01.submit();
    return false;
}
</script>
<body>

<form name="form01" action="/news/news_list.do" method="POST">
<input type="hidden" name="c_idx" id="c_idx" value="${b_idx}"/>
<input type="hidden" name="b_idx" id="b_idx" value="${b_idx}"/>
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}"/>
<input type="hidden" name="bl_idx" id="bl_idx"/>
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
                    <h3>교구소식</h3>
                    <ul>
                        <li>홈</li>
                        <li>소식</li> 
                        <li>교구소식</li>
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
                    	<li <c:if test="${_params.b_idx eq 'ALL' or _params.b_idx eq '' or _params.b_idx eq '9, 12, 10'}">class="on"</c:if>><a href="/news/news_list.do?b_idx=ALL">전체보기</a></li>
                    	<li <c:if test="${_params.b_idx eq  '9'}">class="on"</c:if>><a href="/news/news_list.do?b_idx=9">교회소식</a></li>
                    	<li <c:if test="${_params.b_idx eq '12'}">class="on"</c:if>><a href="/news/news_list.do?b_idx=12">교구소식</a></li>
                    	<li <c:if test="${_params.b_idx eq '10'}">class="on"</c:if>><a href="/news/news_list.do?b_idx=10">공동체소식</a></li>
                    </ul>
                    <!-- //tabs -->
                    <h3 class="ttl tb fl">${strCategoryName}</h3>                  
                    <!-- srchSel -->
                    <div class="srchSel fr">
                    	<!-- 글쓰기 권한이 있다면 버튼을 보이게 한다. START-->
                    	<c:set var="isBoardMngmnt" value="F" />
                    	<c:if test="${SS_WRITEYN eq 'Y' and ((SS_GROUPGUBUN eq '2' and _params.b_idx eq '12' and SS_CHURCHIDX ne '') or (SS_GROUPGUBUN eq '4' and _params.b_idx eq '10'))}">
                    		<c:set var="isBoardMngmnt" value="T" />
                    	</c:if>
                    	<c:if test="${isBoardMngmnt eq 'T' }">
                    	<dl>
                            <dd>
                            	<script>
                            	function gotoWriteNBoardByManager() {
                            		location.href='/news/news_view_iMode.do?mode=W&b_idx=${_params.b_idx}';
                            	}
                            	</script>
                                <button type="button" id="btnBonNboarWrite"  onclick="javascript: gotoWriteNBoardByManager(); return false;">글쓰기</button>
                            </dd>
                        </dl>
                        </c:if>
                        <!-- 글쓰기 권한이 있다면 버튼을 보이게 한다. END -->
                        <dl>
                            <dt>검색</dt>
                            <dd>
                                <label for=""></label>
                                <select name="schTextGubun" id="schTextGubun">
                                    <option value="title" <c:out value="${schTextGubunMap['title']}"/>>제목</option>
                                    <option value="content" <c:out value="${schTextGubunMap['content']}"/>>내용</option>
                                    <option value="all" <c:out value="${schTextGubunMap['all']}"/>>제목 + 내용</option>
                                    <option value="writer" <c:out value="${schTextGubunMap['writer']}"/>>작성자</option>
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
                            <caption>교구소식 전체보기</caption>
                            <colgroup>
                                <col style="width:10%">
                                <col style="width:12%">
                                <col>
                                <col style="width:8%">
                                <col style="width:8%">
                                <col style="width:10%">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col">No.</th>
                                    <th scope="col">분류</th>
                                    <th scope="col">제목</th>
                                    <th scope="col">작성자</th>
                                    <th scope="col">작성일</th>
                                    <th scope="col">첨부파일</th>
                                </tr>
                            </thead>
                            <tbody>
                           	<c:choose>
								<c:when test="${fn:length(newsList) > 0}">
									<c:forEach items="${newsList}" var="list">
	                                <tr>
	                                    <th scope="row">
	                                    <c:choose>
										    <c:when test="${list.NOTICE_TYPE == 1}">
										        	<i class="ico brown">공지</i>
										    </c:when>
										    <c:otherwise>
										        	${list.RNUM}
										    </c:otherwise>
										</c:choose>
	                                    </th>
	                                    <td><strong>
	                                    ${list.DEPART_NAME}<c:if test="${list.DEPART_NAME eq ''}">${list.ORG_NAME }</c:if>
	                                    </strong></td>
	                                    <td><a href="javascript: viewNews(${list.B_IDX},${list.BL_IDX})"><c:if test="${isBoardMngmnt eq 'T' and list.IS_VIEW ne 'Y'}"><font color="darkgray"></c:if>${list.TITLE}<c:if test="${isBoardMngmnt eq 'T' and list.IS_VIEW ne 'Y'}"></font></c:if></a></td>
	                                    <td>${list.WRITER}</td>
	                                    <td>${list.REGDATE}</td>
	                                    <td class="file">
	                                    <c:if test = "${list.FILE_CNT ne '0'}">    
                                            <a href="javascript: attachedFile_Download ('${list.FILEPATH1}','${list.FILENAME1}','${list.STRFILENAME1}')" 
                                            ><img src="/img/sub/_ico/board_download.png" alt=""></a>
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
