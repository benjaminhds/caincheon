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
function marryApply(no, id) {
	document.form_apply.action = '/community/marry_apply.do';
	document.getElementById('marry_no').value=no;
	document.getElementById('id').value=id;
	document.form_apply.submit();
    return false;
}

function insert_contents() {
	alert('insert');
	document.form_apply.action = '/myinfo/marry_insert.do';
	//document.getElementById('idx').value=idx;
	document.form_apply.submit();
    return false;
}
</script>
<body>
<form name="form_apply" action="/community/marry_apply.do" method="POST">
	<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}" />
	<input type="hidden" name="marry_no" id="marry_no" value="${marry_no}" />	
	<input type="hidden" name="id" id="id" value="<%=sessionMemId %>" />		
</form>
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
                    <c:choose>
						<c:when test="${fn:length(marryList) > 0}">
	                    <h3 class="ttl tb">카나혼인강좌&amp;약혼자주말신청</h3>
	                    <!-- boardList -->
	                    <div class="boardList oflow v4">
	                        <table>
	                            <caption>카나혼인강좌&amp;약혼자주말신청 리스트</caption>
	                            <colgroup>
	                            </colgroup>
	                            <thead>
	                                <tr>
	                                    <th scope="col">No.</th>
	                                    <th scope="col">구분</th>
	                                    <th scope="col">강좌신청날짜</th>
	                                    <th scope="col">혼인예정일</th>
	                                    <th scope="col">성명(남)</th>
	                                    <th scope="col">세례명</th>
	                                    <th scope="col">성명(여)</th>
	                                    <th scope="col">세례명</th>
	                                    <th scope="col">신청일</th>
	                                    <th scope="col">접수상태</th>
	                                </tr>
	                            </thead>
	                            <tbody>						
								<c:forEach items="${marryList}" var="dto">
									<tr>
										<th scope="row">${dto.RNUM}</th>
										<td><a href="javascript:marryApply('${dto.MARRY_NO}','${dto.ID}')"><strong>${dto.APPLY_TYPE_TEXT}</strong></a></td>
										<td><a href="javascript:marryApply('${dto.MARRY_NO}','${dto.ID}')">${dto.LECTURE_APPLY_DAY}</a></td>
										<td><a href="javascript:marryApply('${dto.MARRY_NO}','${dto.ID}')">${dto.MARRY_DAY}</a></td>
										<td><strong>${dto.MAN_NAME}</strong></td>
										<td>${dto.MAN_BAPTISMAL_NAME}</td>
										<td><strong>${dto.WOMAN_NAME}</strong></td>
										<td>${dto.WOMAN_BAPTISMAL_NAME}</td>
										<td>${dto.APPLY_DAY}</td>
										<c:choose>
										    <c:when test="${dto.PROCESS_STATUS == '1'}">
										        	<td class="icos disagree"><span>${dto.PROCESS_STATUS_TEXT}</span></td>
										    </c:when>
										    <c:otherwise>
										        	<td class="icos agree"><span>${dto.PROCESS_STATUS_TEXT}</span></td>
										    </c:otherwise>
										</c:choose>
									</tr>
	                              </c:forEach>
		                            </tbody>
		                        </table>
		                    </div>
		                    <!-- arrow -->
		                    <%@ include file="/_common/inc/paging2.jsp" %>
		                    <!-- //arrow -->
		                    <!-- //boardList -->
							</c:when>
							<c:otherwise>
			                    <!-- member -->
			                    <div class="member">
			                        <div class="other">
			                            <p class="logTit">카나혼인강좌 &amp; 약혼자주말신청</p>
			                            <p class="logTxt">신청내역이 없습니다!</p>
			                            <span class="txt">천주교 인천교구 카나혼인강좌&amp;약혼자주말신청을 이용하실 분은 하단 신청하기 버튼을 클릭해주세요.</span>
			                            <ul class="btn v2">
			                                <li><a href="/community/cana_register.do">신청하기</a></li>
			                                <li class="gray"><a href="/home.do">홈</a></li>
			                            </ul>
			                        </div>                       
			                    </div>
			                    <!-- //member -->
							</c:otherwise>
						</c:choose>
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
</body>

</html>
