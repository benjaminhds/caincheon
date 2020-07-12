<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<script type="text/javascript">
function file_download(strfilename, filename, filepath) {
    //document.location.href="http://movie.caincheon.or.kr/movie/" + bl_idx + "/" + filename;
	location.href = "/filedownload.jsp?filePath="+filepath+"&fileName="+filename+"&strfileName="+strfilename;
}
function viewAlb(idx) {
	if(idx=='0'||idx=='') {
		alert('내용이 없습니다.');
		//return false;
	} else {
		document.form01.action = '/news/alb_view.do';
	    document.getElementById('idx').value=idx;
	    document.form01.submit();
	    return false;
	}
    
}
function goList() {
	document.form01.action = '/news/alb_list.do';
	document.getElementById('b_idx').value="ALL";
	document.form01.submit();
	return false;
}
</script>

<body>
<form name="form01" action="/news/news_list.do" method="POST">
<input type="hidden" name="idx" id="idx" value="${idx}"/>
<input type="hidden" name="b_idx" id="b_idx" value="${b_idx}"/>
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}"/>
<input type="hidden" name="bl_idx" id="bl_idx"/>
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
                    <h3>교구앨범</h3>
                    <ul>
                        <li>홈</li>
                        <li>소식</li>
                        <li>교구앨범</li>
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
                	
                	<c:set var="tab_name" value="전체" />
                	<c:choose>
                	<c:when test="${albContents.C_IDX eq '1'}"><c:set var="tab_name" value="[미사/행사]" /></c:when>
                	<c:when test="${albContents.C_IDX eq '2'}"><c:set var="tab_name" value="[교육/사업]" /></c:when>
                	<c:otherwise><c:set var="tab_name" value="" /></c:otherwise>
                	</c:choose>
                	
                    <ul class="tabs">
                    	<li <c:if test="${albContents.C_IDX eq 0}">class="on"</c:if>><a href="/news/alb_list.do?c_idx=ALL">전체</a></li>
                    	<li <c:if test="${albContents.C_IDX eq 1}">class="on"</c:if>><a href="/news/alb_list.do?c_idx=1">미사/행사</a></li>
                    	<li <c:if test="${albContents.C_IDX eq 2}">class="on"</c:if>><a href="/news/alb_list.do?c_idx=2">교육/사업</a></li>
                    </ul>
                    <div class="viewPage v2">
                        <table class="views">
                            <caption>상세 페이지</caption>
                            <thead>
                                <tr>
                                    <td>
                                        <span class="ttl">
                                            <i class="red">${tab_name}</i>
                                            <i>${albContents.TITLE}</i>
                                        </span>
                                         <span class="date">${albContents.REGDATE}</span>
                                    </td>
                                </tr>
                            </thead>                            
                            <tbody>
                                <tr>
                                    <td>
                                        <span class="album">
                                        <img id="imgthumbnail" src="${albContents.FILEPATH}${albContents.STRFILENAME}"  alt="${albContents.FILENAME}" allowfullscreen></span>
                                        ${albContents.CONTENT}
                                    </td>
                                </tr>
                            </tbody>  
                            <tfoot>
                                <tr>
                                    <td>
                                        <span class="file">
                                           <em>첨부파일 : </em>
                                           <c:choose>
                                               <c:when test="${fn:length(albContents.STRFILENAME) > 0}">
                                                   <c:forEach items="${albDList}" var="albDList">
                                                   </c:forEach>
                                                       <li>
                                                           <a href="javascript: file_download ('${albContents.STRFILENAME}','${albContents.FILENAME}','${albContents.FILEPATH}')">원본 다운로드(${albContents.FILENAME}, ${albContents.FILESIZE} bytes) </a>
                                                       </li>
                                                   
                                               </c:when>
                                           </c:choose>
                                        </span>
                                        <ul class="sns">
                                        	<li><a href="javascript:goFacebook('/news/alb_view.do?idx=${idx}','인천교구')"><img src="/img/sub/_ico/ico_facebook.png" alt="페이스북"></a></li>
                                            <li><a href="javascript:goTweeter('/news/alb_view.do?idx=${idx}','인천교구')"><img src="/img/sub/_ico/ico_tweet.png" alt="트위터"></a></li>
                                            <li><a href="javascript:goKakao('/news/alb_view.do?idx=${idx}','인천교구')"><img src="/img/sub/_ico/ico_kakao.png" alt="카카오스토리"></a></li>
                                        </ul>
                                    </td>
                                </tr>
                            </tfoot>                          
                        </table>
                    </div>
                    <!-- btnLeft -->
                    <ul class="btn btnLeft">
                        <li class="gray"><a href="javascript:viewAlb('${albContents.BEFORE_P_IDX}')">이전</a></li>
                        <li class="gray"><a href="javascript:viewAlb('${albContents.NEXT_P_IDX}')">다음</a></li>
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
