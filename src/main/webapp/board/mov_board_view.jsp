<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<html>
<head>
</head>
<script type="text/javascript">

function file_download(filePath, fileName) {
	document.getElementById('fileName').value=fileName;
	document.getElementById('filePath').value=filePath;
	document.form02.submit();
    return false;
    
	//document.location.href="http://movie.caincheon.or.kr/movie/" + bl_idx + "/" + filename;
}
function viewMov(b_idx, bl_idx) {
	if(bl_idx=='0'||bl_idx=='') {
		alert('내용이 없습니다.');
		//return false;
	} else {
		var vfm = document.form01;
		vfm.action = '/n/board/mov_board_view.do';
		vfm.i_sBidx.value=b_idx;
		vfm.i_sBlidx.value=bl_idx;
		vfm.submit();
	    return false;
	}
}
function goList() {
	var vfm = document.form01;
	vfm.action = '/n/board/mov_board_list.do';
	vfm.submit();
	return false;
}
</script>
<body>
<form  name="form02" action="/filedownload.jsp" method="POST">
<input type="hidden" name="fileName" id="fileName" value="${fileName}"/>
<input type="hidden" name="filePath" id="filePath" value="${filePath}"/>
</form>

<form name="form01" action="/n/board/mov_board_list.do" method="POST">
<input type="hidden" name="i_sBidx" id="i_sBidx" value="${_params.i_sBidx}"/>
<input type="hidden" name="i_sBlidx" id="i_sBlidx" value="${_params.i_sBlidx}"/>
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
        <section class="subVisual news">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap"> 
                <article> 
                    <h3>${bd_content.B_NM}</h3>
                    <ul>
                        <li>홈</li>
                        <li>소식</li>
                        <li>${bd_content.B_NM}</li>
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
                    <div class="viewPage v2">
                        <table class="views">
                            <caption>상세 페이지</caption>
                            <thead>
                                <tr>
                                    <td>
                                        <span class="ttl">
                                            <i class="red">[미사]</i>
                                            <i>${movContents.TITLE}</i>
                                        </span>
                                         <span class="date">${movContents.REGDATE}</span>
                                    </td>
                                </tr>
                            </thead>                            
                            <tbody>
                                <tr>
                                    <td>
	                                    <span class="movie">
	                                    <c:set var="CONTENT_TMP" value="${fn:replace(movContents.CONTENT, ltl, ltTAG ) }" />
	                                    <c:set var="CONTENT_TMP" value="${fn:replace(CONTENT_TMP, gtl, gtTAG ) }" />
	                                    <c:set var="CONTENT_TMP" value="${fn:replace(CONTENT_TMP, quotl, quotTAG ) }" />
	                                    ${CONTENT_TMP}
										</span>
                                    </td>
                                </tr>
                            </tbody>  
                            <tfoot>
                                <tr>
                                    <td><!-- 
                                        <span class="file">
                                            <em>첨부파일 : </em>
                                            <c:choose>
                                               <c:when test="${fn:length(movContents.STRFILENAME) > 0}">
                                                  <li>
                                                      <a href="javascript: file_download ('${movContents.STRFILENAME}','${movContents.FILENAME}','${movContents.FILEPATH}')">원본 다운로드(${movContents.FILENAME}, ${movContents.FILESIZE} bytes) </a>
                                                  </li>
                                               </c:when>
                                           </c:choose>
                                        </span> -->
                                        <ul class="sns">
                                            <li><a href="javascript:goFacebook('/n/board/mov_board_view.do?i_sBidx=${_params.i_sBidx}&i_sBlidx=${_params.i_sBlidx}','인천교구')"><img src="/img/sub/_ico/ico_facebook.png" alt="페이스북"></a></li>
                                            <li><a href="javascript:goTweeter('/n/board/mov_board_view.do?i_sBidx=${_params.i_sBidx}&i_sBlidx=${_params.i_sBlidx}','인천교구')"><img src="/img/sub/_ico/ico_tweet.png" alt="트위터"></a></li>
                                            <li><a href="javascript:goKakao('/n/board/mov_board_view.do?i_sBidx=${_params.i_sBidx}&i_sBlidx=${_params.i_sBlidx}','인천교구')"><img src="/img/sub/_ico/ico_kakao.png" alt="카카오스토리"></a></li>
                                        </ul>
                                    </td>
                                </tr>
                            </tfoot>                          
                        </table>
                    </div>
                    <!-- btnLeft -->
                    <ul class="btn btnLeft">
                        <li class="gray"><a href="javascript:viewMov('${movContents.B_IDX}','${movContents.PRE_BL_IDX}')">이전</a></li>
                        <li class="gray"><a href="javascript:viewMov('${movContents.B_IDX}','${movContents.NEXT_BL_IDX}')">다음</a></li>
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
