<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<script type="text/javascript">
function file_download(p_idx, filename) {
    document.location.href="http://movie.caincheon.or.kr/movie/" + bl_idx + "/" + filename;
}

function goContents(bl_idx) {
	if(bl_idx=='0' || bl_idx == '') {
		alert('내용이 없습니다.');
		//return false;
	} else {
	    document.form01.action = '/gyogu/par_view.do';
	    document.getElementById('bl_idx').value=bl_idx;
	    document.form01.submit();
	    return false;
	}
}
function goList() {
	document.form01.action = '/gyogu/par_list.do';
	document.form01.submit();
	return false;
}
</script>
<body>
<form name="form01" action="/gyogu/par_list.do" method="POST">
<input type="hidden" name="idx" id="idx"/>
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}"/>
<input type="hidden" name="c_idx" id="c_idx" value="${c_idx}"/>
<input type="hidden" name="bl_idx" id="bl_idx" value="${bl_idx}"/>
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
                    <div class="viewPage">
                        <table class="views">
                            <caption>상세 페이지</caption>
                            <thead>
                                <tr>
                                    <td>
                                        <span class="ttl">
                                            <c:if test = "${CONTENTS.IS_NOTICE == 'Y'}">    
                                            <i class="red">[공지사항]</i>
                                            </c:if>
                                            <i>${CONTENTS.TITLE}</i>
                                        </span>
                                         <span class="date">작성자 : ${CONTENTS.WRITER} &nbsp;&nbsp; ${CONTENTS.REGDATE}</span>
                                    </td>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>
                                    	${CONTENTS.CONTENT}
                                    </td>
                                </tr>
                                <c:if test="${fn:length(parContents.STRFILENAME) > 0 }">
                                <tr>
                                    <td><img src="${CONTENTS.STRFILENAME}" /></td>
                                </tr>
                                </c:if>
                            </tbody>  
                            <tfoot>
                                <tr>
                                    <td>
                                        <span class="file"></span>
                                        <ul class="sns">
                                        	<li><a href="javascript:goFacebook('/gyogu/par_view.do?c_idx=${c_idx}&bl_idx=${bl_idx}','인천교구')"><img src="/img/sub/_ico/ico_facebook.png" alt="페이스북"></a></li>
                                            <li><a href="javascript:goTweeter('/gyogu/par_view.do?c_idx=${c_idx}&bl_idx=${bl_idx}','인천교구')"><img src="/img/sub/_ico/ico_tweet.png" alt="트위터"></a></li>
                                            <li><a href="javascript:goKakao('/gyogu/par_view.do?c_idx=${c_idx}&bl_idx=${bl_idx}','인천교구')"><img src="/img/sub/_ico/ico_kakao.png" alt="카카오스토리"></a></li>
                                        </ul>
                                    </td>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                    <!-- btnLeft -->
                    <ul class="btn btnLeft">
                        <li class="gray"><a href="javascript:goContents('${CONTENTS.PRE_BL_IDX}')">이전</a></li>
                        <li class="gray"><a href="javascript:goContents('${CONTENTS.NEXT_BL_IDX}')">다음</a></li>
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
