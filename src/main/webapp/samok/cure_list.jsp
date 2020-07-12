<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<script type="text/javascript">

function file_download(filePath, fileName) {
    //var fileName1 = encodeURI(encodeURIComponent(fileName));
    //var fff = "http://192.168.0.8:9088/" + filePath + "/" + fileName1;
    //alert(fff);
    //document.location.href="http://192.168.0.8:9088/" + filePath + "/" + encodeURI(encodeURIComponent(fileName));
    //document.location.href="/filedownload.jsp?filePath=" + filePath + "&fileName=" + fileName;
    //document.location.href="/filedownload.jsp?filePath=" + filePath + "&fileName=" + fileName;
    //alert('aaa');
    document.getElementById('fileName').value=fileName;
    document.getElementById('filePath').value=filePath;
    document.frmDown.submit();
    return false;

    //<a href="http://192.168.0.8:9088/${filePath}/${list.FILENAME}" download="${list.FILENAME}"><img src="/img/sub/_ico/board_download.png" alt=""></a>
}

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

function viewDetails(c_idx,bl_idx) {
	document.form01.action = '/samok/cure_view.do';
	document.getElementById('c_idx').value=c_idx;
	document.getElementById('bl_idx').value=bl_idx;
	document.form01.submit();
    return false;
}
</script>
<body>
<form  name="frmDown" action="/filedownload.jsp" method="POST">
<input type="hidden" name="fileName" id="fileName" value="${fileName}"/>
<input type="hidden" name="filePath" id="filePath" value="${filePath}"/>
</form>

<form name="form01" action="/samok/cure_list.do" method="POST">
<input type="hidden" name="idx" id="idx" value="${idx}"/>
<input type="hidden" name="c_idx" id="c_idx"/>
<input type="hidden" name="bl_idx" id="bl_idx"/>
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
        <section class="subVisual support">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap">
                <article>
                    <h3>사목자료</h3>
                    <ul>
                        <li>홈</li>
                        <li>자료실</li>
                        <li>사목자료</li>
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
                    <ul class="tabs v6">
                        <li${subTitleOn1} <c:if test="${c_idx eq 'ALL'}"> class="on"</c:if> ><a href="/samok/cure_list.do?c_idx=ALL">전체보기</a></li>
                        <li${subTitleOn2}><a href="/samok/cure_list.do?c_idx=5">전례</a></li>
                        <li${subTitleOn3}><a href="/samok/cure_list.do?c_idx=6">양식</a></li>
                        <li${subTitleOn4}><a href="/samok/cure_list.do?c_idx=7">교리</a></li>
                        <li${subTitleOn5}><a href="/samok/cure_list.do?c_idx=8">사목</a></li>
                        <li${subTitleOn6}><a href="/samok/cure_list.do?c_idx=9">선교</a></li>
                    </ul>
                    <!-- //tabs -->
                    <h3 class="ttl tb fl">사목자료</h3>
                    <!-- srchSel -->
                    <div class="srchSel fr">
                        <!-- 글쓰기 권한이 있다면 버튼을 보이게 한다. START-->
                    	<c:set var="isBoardMngmnt" value="F" />
                    	<c:if test="${SS_WRITEYN eq 'Y' and SS_DEPARTIDX ne '' and (SS_GROUPGUBUN eq '2' or SS_GROUPGUBUN eq '4')}">
                    		<c:set var="isBoardMngmnt" value="T" />
                    	</c:if>
                    	<c:if test="${isBoardMngmnt eq 'T' }">
                    	<dl>
                            <dd>
                            	<script>
                            	function gotoWriteNBoardByManager() {
                            		location.href='/samok/cure_view.do?mode=W&c_idx=${c_idx}&b_idx=${_params.b_idx}';
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
                                <col style="width:8%">
                                <col style="width:8%">
                                <col>
                                <col style="width:10%">
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
								<c:when test="${fn:length(cureList) > 0}">
									<c:forEach items="${cureList}" var="list">
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
	                                    	${list.CATEGORY_NAME}
	                                    </strong></td>
	                                    <td><a href="javascript:viewDetails('${c_idx}','${list.BL_IDX}')">${list.TITLE}</a></td>
	                                    <td>${list.WRITER}</td>
	                                    <td>${list.REGDATE}</td>
	                                    <td><c:if test = "${list.FILE_CNT ne '0'}"><img src="/img/sub/_ico/board_download.png" alt=""></c:if><%
                                        // <a href="javascript:file_download('${list.FILEPATH1}','${list.FILENAME}')" ><img src="/img/sub/_ico/board_download.png" alt=""></a>
                                        %></td>
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
