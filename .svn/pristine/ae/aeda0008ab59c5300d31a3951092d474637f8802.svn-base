<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<script type="text/javascript">
function file_download(p_idx, filename) {
    document.location.href="http://movie.caincheon.or.kr/movie/" + bl_idx + "/" + filename;
}
function viewMsg(type, m_idx) {
	if(m_idx == '0' || m_idx == '') {
		alert('내용이 없습니다.');	
	} else {
		document.form01.action = '/gyogu/msg_view.do';
		document.getElementById('type').value=type;
		document.getElementById('m_idx').value=m_idx;
		document.form01.submit();
	    return false;
	}
}
function goList() {
	document.form01.action = '/gyogu/msg_list.do';
	document.getElementById('type').value="ALL";
	document.form01.submit();
	return false;
}
</script>

<body>
<form name="form01" action="/gyogu/msg_list.do" method="POST">
<input type="hidden" name="m_idx" id="m_idx" value="${m_idx}"/>
<input type="hidden" name="type" id="type" value="${type}"/>
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}"/>
    <p class="skipNavi" tabindex="1">본문바로가기</p>
    <!-- wrap -->
    <div id="wrap">
        <!-- header -->
        <header>
            <%@ include file="/_common/inc/gnb.jsp" %>
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
						    <c:when test="${_params.type eq 'ALL'}">
						       	<li class="on"><a href="/gyogu/msg_list.do?type=ALL">전체보기</a></li>
						    </c:when>
						    <c:otherwise>
						       	<li><a href="/gyogu/msg_list.do?type=ALL">전체보기</a></li>
						    </c:otherwise>
						</c:choose>
						<c:choose>
						    <c:when test="${_params.type eq '1'}">
						       	<li class="on"><a href="/gyogu/msg_list.do?type=1">교서</a></li>
						    </c:when>
						    <c:otherwise>
						       	<li><a href="/gyogu/msg_list.do?type=1">교서</a></li>
						    </c:otherwise>
						</c:choose>
						<c:choose>
						    <c:when test="${_params.type eq '2'}">
						       	<li class="on"><a href="/gyogu/msg_list.do?type=2">서한</a></li>
						    </c:when>
						    <c:otherwise>
						       	<li><a href="/gyogu/msg_list.do?type=2">서한</a></li>
						    </c:otherwise>
						</c:choose>
						<c:choose>
						    <c:when test="${_params.type eq '3'}">
						       	<li class="on"><a href="/gyogu/msg_list.do?type=3">담화문</a></li>
						    </c:when>
						    <c:otherwise>
						       	<li><a href="/gyogu/msg_list.do?type=3">담화문</a></li>
						    </c:otherwise>
						</c:choose>
                    </ul>
                    <!-- //tabs -->
                    <div class="viewPage">
                        <table class="views">
                            <caption>상세 페이지</caption>
                            <thead>
                                <tr>
                                    <td>
                                        <span class="ttl">
                                        <c:choose>
										    <c:when test="${_params.type eq '1'}">
										       	<i class="red">[교서]</i>
										    </c:when>
										    <c:when test="${_params.type eq '2'}">
										       	<i class="red">[서한]</i>
										    </c:when>
										    <c:when test="${_params.type eq '3'}">
										       	<i class="red">[담화문]</i>
										    </c:when>
										    <c:otherwise>
										    </c:otherwise>
										</c:choose>
                                        	
                                            <i>${msgContents.TITLE}</i>
                                        </span>
                                         <span class="date">${msgContents.REGDATE}</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="ttl">
                                            <i>${msgContents.SUB_TITLE}</i>
                                        </span>
                                    </td>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>
                                        ${fn:replace(msgContents.CONTENT , cn , br)}
                                    </td>
                                </tr>
                            </tbody>  
                            <tfoot>
                                <tr>
                                    <td>
                                        <span class="file">
                                                                                        
                                        </span>
                                        <ul class="sns">
                                        	<c:set var="SNS_DES" value="교구장 메시지 - ${msgContents.TITLE}" />
                                            <li><a href="javascript:goFacebook('/gyogu/msg_view.do?type=${_params.type}&m_idx=${_params.m_idx}','${SNS_DES}')"><img src="/img/sub/_ico/ico_facebook.png" alt="페이스북"></a></li>
                                            <li><a href="javascript:goTweeter('/gyogu/msg_view.do?type=${_params.type}&m_idx=${_params.m_idx}','${SNS_DES}')"><img src="/img/sub/_ico/ico_tweet.png" alt="트위터"></a></li>
                                            <li><a href="javascript:goKakao('/gyogu/msg_view.do?type=${_params.type}&m_idx=${_params.m_idx}','${SNS_DES}')"><img src="/img/sub/_ico/ico_kakao.png" alt="카카오스토리"></a></li>
                                        </ul>
                                    </td>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                    <!-- btnLeft -->
                    <ul class="btn btnLeft">
                        <li class="gray"><a href="javascript:viewMsg('${_params.type}','${msgContents.before_p_idx}')">이전</a></li>
                        <li class="gray"><a href="javascript:viewMsg('${_params.type}','${msgContents.next_p_idx}')">다음</a></li>
                    </ul>
                    <!-- //btnLeft -->
                    <!-- btn -->
                    <ul class="btn">
                        <li><a href="javascript:goList()">목록</a></li>
                    </ul>
                    <!-- //btn -->
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
