<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<script type="text/javascript">
function viewMsg(c_idx, pageNo, a_idx) {
	document.form01.action = '/gyogu/ever_view.do';
	document.getElementById('c_idx').value=c_idx;
	document.getElementById('a_idx').value=a_idx;
	document.getElementById('pageNo').value=pageNo;
	document.form01.submit();
    return false;
}
function goList() {
	document.form01.action = '/gyogu/ever_list.do';
	document.form01.submit();
	return false;
}
</script>
<body>
<form name="form01" action="/gyogu/msg_list.do" method="POST">
<input type="hidden" name="a_idx" id="a_idx" value="${_params.a_idx}"/>
<input type="hidden" name="b_idx" id="b_idx" value="${_params.b_idx}"/>
<input type="hidden" name="c_idx" id="c_idx" value="${_params.c_idx}"/>
<input type="hidden" name="m_idx" id="m_idx" value="${_params.m_idx}"/>
<input type="hidden" name="type" id="type" value="${_params.type}"/>
<input type="hidden" name="pageNo" id="pageNo" value="${_params.pageNo}"/>
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
                    <h3>역대교구장</h3>
                    <ul>
                        <li>홈</li>
                        <li>교구장</li>
                        <li>역대교구장</li>
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
                    	<li <c:if test="${_params.c_idx eq '1'}">class="on"</c:if>><a href="/gyogu/ever_list.do?c_idx=1">제 1대 교구장</a></li>
                    	<li <c:if test="${_params.c_idx eq '2'}">class="on"</c:if>><a href="/gyogu/ever_list.do?c_idx=2">제 2대 교구장</a></li>
                    </ul>
                    <!-- //tabs -->
                    <div class="viewPage">
                        <table class="views">
                            <caption>상세 페이지</caption>
                            <thead>
                                <tr>
                                    <td>
                                        <span class="ttl">
                                            <i>${CONTENTS.TITLE}</i>
                                        </span>
                                        <span class="date">${CONTENTS.REGDATE}</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="ttl">
                                            <i>${CONTENTS.SUB_TITLE}</i>
                                        </span>
                                    </td>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>
                                    	${fn:replace(CONTENTS.CONTENT , cn , br)}
                                    </td>
                                </tr>
                            </tbody>  
                            <tfoot>
                                <tr>
                                    <td>
                                        <span class="file">
                                        <c:if test="${CONTENTS.FILEPATH1 ne ''}"><p><img src="${CONTENTS.FILEPATH1}thumbnail/${CONTENTS.STRFILENAME1}" /></p></c:if>
                                        <c:if test="${CONTENTS.FILEPATH2 ne ''}"><p><img src="${CONTENTS.FILEPATH2}thumbnail/${CONTENTS.STRFILENAME2}" /></p></c:if>
                                        <c:if test="${CONTENTS.FILEPATH3 ne ''}"><p><img src="${CONTENTS.FILEPATH3}thumbnail/${CONTENTS.STRFILENAME3}" /></p></c:if>
                                        <c:if test="${CONTENTS.FILEPATH4 ne ''}"><p><img src="${CONTENTS.FILEPATH4}thumbnail/${CONTENTS.STRFILENAME4}" /></p></c:if>
                                        <c:if test="${CONTENTS.FILEPATH5 ne ''}"><p><img src="${CONTENTS.FILEPATH5}thumbnail/${CONTENTS.STRFILENAME5}" /></p></c:if>
                                        </span>
                                        <ul class="sns">
                                        	<c:set var="SNS_DES" value="역대교구장 앨범 - ${CONTENTS.TITLE}" />
                                            <li><a href="javascript:goFacebook('/gyogu/ever_view.do?type=${_params.type}&m_idx=${_params.m_idx}','${SNS_DES}')"><img src="/img/sub/_ico/ico_facebook.png" alt="페이스북"></a></li>
                                            <li><a href="javascript:goTweeter('/gyogu/ever_view.do?type=${_params.type}&m_idx=${_params.m_idx}','${SNS_DES}')"><img src="/img/sub/_ico/ico_tweet.png" alt="트위터"></a></li>
                                            <li><a href="javascript:goKakao('/gyogu/ever_view.do?type=${_params.type}&m_idx=${_params.m_idx}','${SNS_DES}')"><img src="/img/sub/_ico/ico_kakao.png" alt="카카오스토리"></a></li>
                                        </ul>
                                    </td>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                    <!-- btnLeft -->
                    <ul class="btn btnLeft">
                        <li class="gray"><a href="javascript: viewMsg('${_params.c_idx}','${_params.pageNo}','${CONTENTS.PRE_IDX}')">이전</a></li>
                        <li class="gray"><a href="javascript: viewMsg('${_params.c_idx}','${_params.pageNo}','${CONTENTS.NXT_IDX}')">다음</a></li>
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
            <? include_once "../_common/inc/footer.html"; ?>
        </footer>
        <!-- //footer -->
    </div>
    <!--// wrap -->
</body>

</html>
