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

function viewMsg(type, m_idx) {
	document.form01.action = '/gyogu/msg_view.do';
	document.getElementById('type').value=type;
	document.getElementById('m_idx').value=m_idx;
	document.form01.submit();
    return false;
}
</script>

<body>
<form name="form01" action="/gyogu/msg_list.do" method="POST">
<input type="hidden" name="type" id="type" value="${type}"/>
<input type="hidden" name="m_idx" id="m_idx" value="${m_idx}"/>
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
                    <h3>메시지</h3>
                    <ul>
                        <li>홈</li>
                        <li>교구장</li> 
                        <li>메시지</li>
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
						    <c:when test="${type eq 'ALL'}">
						        	<li class="on"><a href="/gyogu/msg_list.do?type=ALL">전체보기</a></li>
						    </c:when>
						    <c:otherwise>
						        	<li><a href="/gyogu/msg_list.do?type=ALL">전체보기</a></li>
						    </c:otherwise>						    
						</c:choose>    
						<c:choose>
						    <c:when test="${type eq '1'}">
						        	<li class="on"><a href="/gyogu/msg_list.do?type=1">교서</a></li>
						    </c:when>
						    <c:otherwise>
						        	<li><a href="/gyogu/msg_list.do?type=1">교서</a></li>
						    </c:otherwise>						    
						</c:choose>    
						<c:choose>
						    <c:when test="${type eq '2'}">
						        	<li class="on"><a href="/gyogu/msg_list.do?type=2">서한</a></li>
						    </c:when>
						    <c:otherwise>
						        	<li><a href="/gyogu/msg_list.do?type=2">서한</a></li>
						    </c:otherwise>						    
						</c:choose>    
						<c:choose>
						    <c:when test="${(type eq '3') or (type eq '4')}">
						        	<li class="on"><a href="/gyogu/msg_list.do?type=3">담화문</a></li>
						    </c:when>
						    <c:otherwise>
						        	<li><a href="/gyogu/msg_list.do?type=3">담화문</a></li>
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
								<c:when test="${fn:length(msgList) > 0}">
									<c:forEach items="${msgList}" var="list">
									<tr>
	                                    <th scope="row">${list.RNUM}</th>
	                                    <td>
	                                    	<strong>
		                                    <c:choose>
											    <c:when test="${list.TYPE eq '1'}">
											        	교서
											    </c:when>
											    <c:when test="${list.TYPE eq '2'}">
											        	서한
											    </c:when>
											    <c:when test="${(list.TYPE eq '3') or (list.TYPE eq '4')}">
											        	담화문
											    </c:when>
											</c:choose>
	                                    	</strong>
	                                    </td>
	                                    <td><a href="javascript:viewMsg(${list.TYPE},${list.M_IDX})">${list.TITLE}</a></td>
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
