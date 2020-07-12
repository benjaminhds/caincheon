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
function docApply(no, id) {
	document.form_apply.action = '/community/doctrine_apply.do';
	document.getElementById('doctrine_no').value=no;
	document.getElementById('id').value=id;
	document.form_apply.submit();
    return false;
}
</script>
<body>
<form name="form_apply" action="/myinfo/doctrine_apply.do" method="POST">
	<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}" />
	<input type="hidden" name="doctrine_no" id="doctrine_no" value="${doctrine_no}" />	
	<input type="hidden" name="id" id="id" value="${id}" />
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
						<c:when test="${fn:length(docList) > 0}">
	                    <h3 class="ttl tb">통신교리신청</h3>
	                    <!-- boardList -->
	                    <div class="boardList oflow v2">
	                        <table>
	                            <caption>교구 사제 리스트</caption>
	                            <colgroup>
	                            </colgroup>
	                            <thead>
	                                <tr>
	                                    <th scope="col">No.</th>
	                                    <th scope="col">신자구분</th>
	                                    <th scope="col">이름</th>
	                                    <th scope="col">세례명</th>
	                                    <th scope="col">소속본당</th>
	                                    <th scope="col">신청일</th>
	                                    <th scope="col">승인여부</th>
	                                </tr>
	                            </thead>
	                            <tbody>						
								<c:forEach items="${docList}" var="dto">
									<tr>
										<th scope="row">${dto.RNUM}</th>
										<td><a href="javascript:docApply('${dto.DOCTRINE_NO}','${dto.ID}')">${dto.MEMBER_TYPE_TEXT}</a></td>
										<td><a href="javascript:docApply('${dto.DOCTRINE_NO}','${dto.ID}')">${dto.NAME}</a></td>
										<td>${dto.BAPTISMAL_NAME}</td>
										<td>${dto.CHURCH_NAME}</td>
										<td>${dto.APPLY_DAY}</td>
	                                    <td class="icos disagree"><span>${dto.APPROVE_YN_TEXT}</span></td>
									</tr>
	                              </c:forEach>
		                            </tbody>
		                        </table>
		                    </div>
		                    <!-- arrow -->
		                    <%@ include file="/_common/inc/paging2.jsp" %>
		                    <!-- //arrow -->
		                    <ul class="btn v2">
                                <li><a href="/community/doctrine_register.do">신청하기</a></li>
                            </ul>
		                    <!-- //boardList -->
							</c:when>
							<c:otherwise>
			                    <!-- member -->
			                    <div class="member">
			                        <div class="other">
			                            <p class="logTit">통신교리신청</p>
			                            <p class="logTxt">신청내역이 없습니다!</p>
			                            <span class="txt">천주교 인천교구 통신교리 신청을 이용하실 분은 하단 신청하기 버튼을 클릭해주세요.</span>
			                            <ul class="btn v2">
			                                <li><a href="/community/doctrine_register.do">신청하기</a></li>
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
