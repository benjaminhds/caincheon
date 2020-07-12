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
function viewList() {
	document.form01.action = '/member/my_tale.do';
	//document.getElementById('idx').value=idx;
	document.form01.submit();
	return false;
}

function modify_contents() {
	document.form02.action = '/community/tale_edit.do';
	document.form02.submit();
	return false;
}

function delete_contents() {
	if(confirm("삭제하시겠습니까?")==true) {
		document.form02.action = '/member/inq_delete.do';
		document.form02.submit();
		return false;		
	}
}

window.onload = function() {
	//alert("loading completed");
}
</script>
<body>
<form name="form01" action="" method="POST">
	<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}" />	
	<input type="hidden" name="inq_no" id="inq_no" value="${inq_no}" />	
	<input type="hidden" name="id" id="id" value="${id}" />	
</form>	

<form name="form02" action="" accept-charset="utf-8" method="POST">
	<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}" />	
	<input type="hidden" name="inquire_no" id="inquire_no" value="${_params.inquire_no}" />	
	<input type="hidden" name="id" id="id" value="${_params.id}" />	
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
                    <ul class="tabs ea5">
                        <li><a href="/member/myinfo.jsp">개인정보 수정</a></li>
                        <li><a href="/member/my_doctrine.do">통신교리신청</a></li>
                        <li class="lineTwo"><a href="/member/my_cana.do">카나혼인강좌/약혼자 주말신청</a></li>
                        <li class="on"><a href="/member/my_tale.do">나누고 싶은 이야기</a></li>
                    </ul> 
                    <h3 class="ttl tb">나누고 싶은 이야기</h3>
                    <!-- writeTable -->
                    <div class="writeTable">
                        <table class="shirine_st write">
                            <caption>나누고싶은 이야기</caption>
                            <tbody>
                                <tr class="readOn">
                                    <th scope="row">제목</th>
                                    <td>${inqContents.TITLE}</td>
                                </tr>
                                <tr class="readOn">
                                    <th scope="row">작성자</th>
                                    <td>${inqContents.NAME}</td>
                                </tr>
                                <tr class="readOn">
                                    <th scope="row">이메일</th>
                                    <td>${inqContents.ID}</td>
                                </tr>
                                <tr class="readOn">
                                    <th scope="row">내용</th>
                                    <td>
                                    ${inqContents.CONTENTS}
                                    </td>
                                </tr>
                                <c:choose>
								    <c:when test="${inqContents.REPLYTYPE == 3}">
				                        <tr class="readOn">
		                                    <th scope="row">답변</th>
		                                    <td>${inqContents.REPLY}</td>
		                                </tr>
								    </c:when>
								</c:choose>
                                
                            </tbody>
                        </table>
                    </div>
                    <!-- //writeTable -->
                    <ul class="btn">
                        <li><a href="javascript:viewList();">목록</a></li>
					    <c:if test="${inqContents.REPLYTYPE == 1}">
	                        <li class="gray"><a href="javascript:modify_contents();">수정</a></li>
	                        <li class="gray"><a href="javascript:delete_contents();">삭제</a></li>
					    </c:if>
                    </ul>
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
