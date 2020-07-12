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
	if(document.getElementById('searchText').value == '') {
		alert('검색할 내용을 입력하세요.');
		document.getElementById('searchText').focus();
		return false;
	}
	
	document.form01.submit();
    return false;
}

function selectChange() {
	
}

function viewContents(b_idx, bl_idx) {
	document.form01.action = '/gyogu/par_view.do';
	document.getElementById('b_idx').value=b_idx;
	document.getElementById('bl_idx').value=bl_idx;
	document.form01.submit();
    return false;
}

window.onload = function () {
//	 alert("로딩 완료");
	selectChange();
}
</script>

<body>
<form  name="form01" action="/gyogu/par_list.do" method="POST">
<input type="hidden" name="idx" id="idx" value="${idx}"/>
<input type="hidden" name="b_idx" id="b_idx"/>
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
        <section class="subVisual gyogu">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap">
                <article> 
                    <h3>교구장동정</h3>
                    <ul>
                        <li>홈</li>
                        <li>교구장</li> 
                        <li>교구장동정</li>
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
                    	<c:choose>
						    <c:when test="${c_idx eq 'ALL'}">
						        	<li class="on"><a href="/gyogu/par_list.do?c_idx=ALL">전체보기</a></li>
						    </c:when>
						    <c:otherwise>
						        	<li><a href="/gyogu/par_list.do?c_idx=ALL">전체보기</a></li>
						    </c:otherwise>
						</c:choose>
						<c:choose>
						    <c:when test="${c_idx eq '3'}">
						        	<li class="on"><a href="/gyogu/par_list.do?c_idx=3">동정</a></li>
						    </c:when>
						    <c:otherwise>
		                        <li><a href="/gyogu/par_list.do?c_idx=3">동정</a></li>
						    </c:otherwise>
						</c:choose>
						<c:choose>
						    <c:when test="${c_idx eq '4'}">
						        	<li class="on"><a href="/gyogu/par_list.do?c_idx=4">강론</a></li>
						    </c:when>
						    <c:otherwise>
		                        <li><a href="/gyogu/par_list.do?c_idx=4">강론</a></li>
						    </c:otherwise>
						</c:choose>				
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
                                <input type="search" name="schText" id="schText" value="${schText}">
                                <button onClick="this.form.submit()">검색</button>
                            </dd>
                        </dl>
                    </div>
                    <!-- //srchSel -->
                    <!-- boardList -->
                    <div class="boardList oflow v2">
                        <table>
                            <caption>메시지 리스트 전체보기</caption>
                            <colgroup>
                                <col style="width:12%">
                                <col style="width:10%">
                                <col>
                                <col style="width:15%">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col">No.</th>
                                    <th scope="col">분류</th>
                                    <th scope="col">제목</th>
                                    <th scope="col">등록일</th>
                                </tr>
                            </thead>
                            <tbody>
                           	<c:choose>
								<c:when test="${fn:length(LIST) > 0}">
									<c:forEach items="${LIST}" var="list">
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
	                                    <c:choose>
										    <c:when test="${list.C_IDX eq '3'}">
										        	동정
										    </c:when>
										    <c:otherwise>
										        	강론
										    </c:otherwise>
										</c:choose>
	                                    </strong></td>
	                                    <td><a href="javascript:viewContents(${list.B_IDX},${list.BL_IDX})">${list.TITLE}</a></td>
	                                    <td>${list.REGDATE}</td>
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
</body>

</html>
