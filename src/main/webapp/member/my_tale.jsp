<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<%
if(sessionMemId==null||sessionMemId.equals("")) {                // id가 Null 이거나 없을 경우
	response.sendRedirect("/member/login.jsp");    // 로그인 페이지로 리다이렉트 한다.
}
%>
<script type="text/javascript">
function inquireApply(no, id) {
	document.form_apply.action = '/member/my_tale_edit.do';
	document.getElementById('inquire_no').value=no;
	document.getElementById('id').value=id;
	document.form_apply.submit();
    return false;
}

function modify_contents(no, id) {

	document.form_apply.action = '/community/tale_edit.do';
	document.getElementById('inquire_no').value=no;
	document.getElementById('id').value=id;
	document.form_apply.submit();
    return false;
}

function delete_contents(no, id) {
	if(confirm("삭제하시겠습니까?")==true) {
		document.form_apply.action = '/member/inq_delete.do';
		document.getElementById('inquire_no').value=no;
		document.getElementById('id').value=id;
		document.form_apply.submit();
	    return false;
	}
}

function goCommunityTale() {
	location.href='/community/tale.do';
}
</script>
<body>
<form name="form_apply" action="/member/my_tale.do" method="POST">
	<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}" />
	<input type="hidden" name="inquire_no" id="inquire_no" value="${inquire_no}" />	
	<input type="hidden" name="id" id="id" value="${id}" />	
    <p class="skipNavi" tabindex="1">본문바로가기</p>
    <!-- wrap -->
    <div id="wrap">
        <!-- header -->
        <header>
            <%@include file="/_common/inc/gnb.jsp" %>
        </header>
        <!-- //header -->
        <!-- subVisual -->
        <section class="subVisual commu memb">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap">
                <article>
                    <h3>나의정보</h3>
                    <ul>
                        <li>홈</li>
                        <li>MEMBER</li>
                        <li>나의정보</li>
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
                    <h3 class="blind">나의정보 내용</h3>
                    <%@include file="myinfo_tab.jsp" %>
                    <h3 class="ttl tb fl">나누고 싶은 이야기</h3>
                    <div class="srchSel fr">
                        <dl>
                            <dt>검색</dt>
                            <dd>
                                <label for=""></label>
                                <select name="schTextGubun" id="schTextGubun">
                                    <option value="title" <c:out value="${schTextGubunMap['title']}"/>>제목</option>
                                    <option value="writer" <c:out value="${schTextGubunMap['writer']}"/>>작성자</option>
                                    <option value="content" <c:out value="${schTextGubunMap['content']}"/>>내용</option>
                                </select>
                            </dd>
                            <dd>
                                <label for=""></label>
                                <input type="search" name="schText" id="schText" value="${searchText}">
                                <button onClick="this.form.submit()">검색</button>
                                <a href="#" onClick="goCommunityTale();">글쓰기</a>
                            </dd>
                        </dl>
                    </div>
                    <!-- boardList -->
                    <div class="boardList oflow v2">
                        <table>
                            <caption>교구소식 전체보기</caption>
                            <colgroup>
                                <col style="width:8%">
                                <col>
                                <col style="width:8%">
                                <col style="width:12%">
                                <col style="width:12%">
                                <col style="width:8%">
                                <col style="width:8%">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col">No.</th>
                                    <th scope="col">제목</th>
                                    <th scope="col">작성자</th>
                                    <th scope="col">등록일</th>
                                    <th scope="col">답변여부</th>
                                    <th scope="col">수정</th>
                                    <th scope="col">삭제</th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${inqList}" var="dto">
								<tr>
									<td>${dto.RNUM}</td>
									<td><a href="javascript:inquireApply('${dto.INQUIRE_NO}','${dto.ID}')">${dto.TITLE}</a></td>
									<td>${dto.NAME}</td>
									<td>${dto.APPLY_DAY}</td>						
									<c:choose>
									    <c:when test="${dto.REPLYTYPE == 3}">
									        	<td class="icos agree"><span>답변완료</span></td>
									    </c:when>
									    <c:when test="${dto.REPLYTYPE == 2}">
									        	<td class="icos agree"><span>담당자확인</span></td>
									    </c:when>
									    <c:otherwise>
									        	<td class="icos disagree"><span>담당자미확인</span></td>
									    </c:otherwise>
									</c:choose>
                                    <td class="editIco">
									    <c:if test="${dto.REPLYTYPE == 1}">
                                    		<a href="javascript:modify_contents('${dto.INQUIRE_NO}','${dto.ID}')"><img src="/img/sub/_ico/ico_edit.png" alt="수정하기"></a>
									    </c:if>
                                    </td>
                                    <td class="delIco">
                                    	<c:if test="${dto.REPLYTYPE == 1}">
                                    	<a href="javascript:delete_contents('${dto.INQUIRE_NO}','${dto.ID}')"><img src="/img/sub/_ico/ico_del.png" alt="삭제하기"></a>
                                    	</c:if>
                                    </td>
								</tr>
							</c:forEach>
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
            <%@include file="/_common/inc/footer.jsp" %>
        </footer>
        <!-- //footer -->
    </div>
    <!--// wrap -->
</form>
</body>

</html>
