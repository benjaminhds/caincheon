<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<script type="text/javascript">
function goList() {
	document.form01.action = '/news/sch_list.do';
	document.form01.submit();
    return false;
}

function goContents(s_idx) {
	if(s_idx=='0'||s_idx=='') {
		alert('내용이 없습니다.');
		//return false;
	} else {
		document.form01.action = '/news/sch_view.do';
		document.getElementById('s_idx').value=s_idx;
		document.form01.submit();
	    return false;
	}
}
</script>
<body>
<form name="form01" action="/news/sch_view.do" method="POST">
<input type="hidden" name="s_idx" id="s_idx" value="${s_idx}"/>
<input type="hidden" name="gubuncd" id="gubuncd" value="${gubuncd}"/>
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
        <section class="subVisual news">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap"> 
                <article> 
                    <h3>교구일정</h3>
                    <ul>
                        <li>홈</li>
                        <li>본당</li>
                        <li>교구일정</li>
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
                        <li${subTitleOn1}><a href="/news/sch_list.do?gubuncd=1">전체보기</a></li>
                        <li${subTitleOn2}><a href="/news/sch_list.do?gubuncd=2">교구장</a></li>
                        <li${subTitleOn3}><a href="/news/sch_list.do?gubuncd=3">총대리</a></li>
                        <li${subTitleOn4}><a href="/news/sch_list.do?gubuncd=4">교구</a></li>
                        <li${subTitleOn5}><a href="/news/sch_list.do?gubuncd=5">부서</a></li>
                    </ul>
                    <!-- //tabs -->
                    <div class="viewPage">
                        <table class="views">
                            <caption>상세 페이지</caption>
                            <thead>
                                <tr>
                                    <td>
                                        <span class="ttl">
                                            <i class="red">${schContents.ORG_NAME } [${schContents.GUBUN}]</i>
                                            <i>${schContents.TITLE}</i>
                                        </span>
                                         <span class="date">${schContents.START_DATE} ~ ${schContents.END_DATE}</span>
                                    </td>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>
                                    ${schContents.CONTENT}
                                    </td>
                                </tr>
                            </tbody>
                            <tfoot>
                                <tr>
                                    <td>
                                        <span class="file">
                                            
                                        </span>
                                        <ul class="sns">
                                            <li><a href="javascript:goFacebook('/news/sch_view.do?gubuncd=${GUBUN}&s_idx=${s_idx}','인천교구')"><img src="/img/sub/_ico/ico_facebook.png" alt="페이스북"></a></li>
                                            <li><a href="javascript:goTweeter('/news/sch_view.do?gubuncd=${GUBUN}&s_idx=${s_idx}','인천교구')"><img src="/img/sub/_ico/ico_tweet.png" alt="트위터"></a></li>
                                            <li><a href="javascript:goKakao('/news/sch_view.do?gubuncd=${GUBUN}&s_idx=${s_idx}','인천교구')"><img src="/img/sub/_ico/ico_kakao.png" alt="카카오스토리"></a></li>
                                        </ul>
                                    </td>
                                </tr>
                            </tfoot>      
                        </table>
                    </div>
                    <!-- btnLeft -->
                    <ul class="btn btnLeft">
                        <li class="gray"><a href="javascript:goContents('${schContents.PRE_S_IDX}')">이전</a></li>
                        <li class="gray"><a href="javascript:goContents('${schContents.NEXT_S_IDX}')">다음</a></li>
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
