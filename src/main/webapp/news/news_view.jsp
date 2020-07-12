<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %><%

// 글쓰기 권한
String bb_idx = pnull(request, "b_idx");
if(bb_idx.length() > 0) {
	pageContext.setAttribute("b_idx", bb_idx);
}
%>
<script type="text/javascript">
/*
function file_download(filePath, fileName, strFileName) {    
    document.getElementById('fileName').value=fileName;
    document.getElementById('filePath').value=filePath;
    document.getElementById('strFilePath').value=strFilePath;
    document.frmDown.submit();
    return false;
}
*/

function goList() {
	document.form01.action = '/news/news_list.do';
	document.form01.submit();
    return false;
}

function goContents(b_idx,bl_idx) {
	if(bl_idx=='0'||bl_idx=='') {
		alert('내용이 없습니다.');
		//return false;
	} else {
		document.form01.action = '/news/news_view.do';
		document.getElementById('b_idx').value=b_idx;
		document.getElementById('bl_idx').value=bl_idx;
		document.form01.submit();
	    return false;
	}
}
</script>
<body>
<form  name="frmDown" action="/filedownload.jsp" method="POST">
<input type="hidden" name="fileName" id="fileName" value="${fileName}"/>
<input type="hidden" name="filePath" id="filePath" value="${filePath}"/>
<input type="hidden" name="strFilePath" id="strFilePath" value="${strFilePath}"/>
</form>

<form name="form01" action="/news/news_list.do" method="POST">
<input type="hidden" name="b_idx" id="b_idx" value="${b_idx}"/>
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}"/>
<input type="hidden" name="bl_idx" id="bl_idx"/>
</form>

<c:set var="PAGENO" value="1" />
<c:if test="${paging.pageNo ne null or paging.pageNo ne ''}"><c:set var="PAGENO" value="${paging.pageNo}" /></c:if>
<form name="form02" action="/news/news_list.do" method="POST" enctype="multipart/form-data">
<input type="hidden" name="b_idx"  id="b_idx"  value="${CONTENTS.B_IDX}"/>
<input type="hidden" name="bl_idx" id="bl_idx" value="${CONTENTS.BL_IDX}"/>
<input type="hidden" name="pageNo" id="pageNo" value="${PAGENO }" />
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
                    <h3>${strCategoryName}</h3>
                    <ul>
                        <li>홈</li>
                        <li>소식</li>
                        <li>${strCategoryName}</li>
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
                    	<li <c:if test="${_params.b_idx eq 'ALL' or _params.b_idx eq '' or _params.b_idx eq '9, 12, 10'}">class="on"</c:if>><a href="/news/news_list.do?b_idx=ALL">전체보기</a></li>
                    	<li <c:if test="${_params.b_idx eq  '9' or b_idx eq  '9'}">class="on"</c:if>><a href="/news/news_list.do?b_idx=9">교회소식</a></li>
                    	<li <c:if test="${_params.b_idx eq '12' or b_idx eq '12'}">class="on"</c:if>><a href="/news/news_list.do?b_idx=12">교구소식</a></li>
                    	<li <c:if test="${_params.b_idx eq '10' or b_idx eq '10'}">class="on"</c:if>><a href="/news/news_list.do?b_idx=10">공동체소식</a></li>
                    </ul>
                    <!-- //tabs -->
                    <!-- viewPage -->
                    <div class="viewPage v2">
                        <table class="views">
                            <caption>상세 페이지</caption>
                            <thead>
                                <tr>
                                    <td>
                                        <span class="ttl">
                                            <c:if test = "${CONTENTS.IS_NOTICE == 'Y'}">    
                                            <i class="red">[공지사항]</i>
                                            </c:if>
                                            <i class="blue">${CONTENTS.DEPART_NAME}</i>
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
                            </tbody>  
                            <tfoot>
                                <tr>
                                    <td>
                                        <span class="file">
					                        <c:if test="${CONTENTS.FILE_CNT > 0}">
						                        <em>첨부파일 : </em>
						                        <c:set var="HEAD_CONST__attach_file_form__MAX_FILE_COUNT" value="${CONTENTS.FILE_CNT}" />
						                        <%@ include file="/admin/_common/inc/attach_file_form_loop_list.jsp" %>
											</c:if>
                                        </span>
                                        <ul class="sns">
                                            <li><a href="javascript:goFacebook('/news/news_view.do?b_idx=${b_idx}&bl_idx=${_params.bl_idx}','인천교구')"><img src="/img/sub/_ico/ico_facebook.png" alt="페이스북"></a></li>
                                            <li><a href="javascript:goTweeter('/news/news_view.do?b_idx=${b_idx}&bl_idx=${_params.bl_idx}','인천교구')"><img src="/img/sub/_ico/ico_tweet.png" alt="트위터"></a></li>
                                            <li><a href="javascript:goKakao('/news/news_view.do?b_idx=${b_idx}&bl_idx=${_params.bl_idx}','인천교구')"><img src="/img/sub/_ico/ico_kakao.png" alt="카카오스토리"></a></li>
                                        </ul>
                                    </td>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                    <!-- //viewPage -->
                    <!-- 17-11-23  -->
                    <div class="writeTable" style="display:none;">
                        <h3 class="ttl tb">수정</h3>
                        <table class="shirine_st write">
                            <caption>수정하기 폼</caption>
                            <tbody>
                            	<tr>
                                    <th scope="row">작성자</th>
                                    <td>
                                    	<span>${_params.__SESSION_MAP__.SS_MEM_NM } ( ${_params.__SESSION_MAP__.SS_MEM_ID } )</span>
                                    	<input type="hidden" name="depart_idx" value="${SS_DEPARTIDX }" />
                                    	<input type="hidden" name="church_idx" value="${SS_CHURCHIDX }" />
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">제목</th>
                                    <td>
                                        <span class="form">
                                            <input class="form-control" type="text" name="title" id="title" value="${CONTENTS.TITLE }">
                                        </span><!-- 
                                        <span class="chkForm v2">
                                            <input type="checkbox" name="is_notice" id="is_notice" value="Y" <c:if test="${CONTENTS.IS_NOTICE eq 'Y' }"> checked="checked"</c:if>>
                                            <label class="checkbox-inline" for="">
                                                <span> 게시글을 최상단에 고정 노출함</span>
                                            </label>
                                        </span>
                                        <span class="chkForm v2">
                                            <input type="checkbox" name="is_view" id="is_view" value="Y" onclick="javascript:$('#is_view').val(this.checked ? 'Y':'N') " <c:if test="${CONTENTS.IS_VIEW eq 'Y' }"> checked="checked"</c:if>>
                                            <label class="checkbox-inline" for="">
                                                <span> 노출여부</span>
                                            </label>
                                        </span> -->
                                        <span class="date">${CONTENTS.REGDATE}</span>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">내용</th>
                                    <td>
                                        <div>
                                            <textarea class="form-control h-400" name="contents" id="contents">${CONTENTS.CONTENT }</textarea>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">이미지 첨부</th>
                                    <td>
                                        <div class="form-group input-group">
                                         <% pageContext.setAttribute("HEAD_CONST__attach_file_form__MAX_FILE_COUNT", 5); %>
                                            <%@ include file="/admin/_common/inc/attach_file_form_loop.jsp" %>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <!-- writeTable -->
                    <!-- //17-11-23 -->
                    <!-- btnLeft -->
                    <ul class="btn btnLeft">
                        <li class="gray"><a href="javascript:goContents('${CONTENTS.B_IDX}','${CONTENTS.PRE_BL_IDX}')">이전</a></li>
                        <li class="gray"><a href="javascript:goContents('${CONTENTS.B_IDX}','${CONTENTS.NEXT_BL_IDX}')">다음</a></li>
                    </ul>
                    <!-- //btnLeft -->
                    <!-- btn -->
                    <ul class="btn">
                        <!-- 17-11-23  -->
                        <li>
                        	<a href="#none" id="btnA2" style="width: 0;" onclick="javascript: goUpsertNBoard();">&nbsp;</a>
                        </li>
                        <c:if test="${SS_WRITEYN eq 'Y' and SS_CHURCHIDX ne '' and ((SS_GROUPGUBUN eq '2' and _params.b_idx eq '12') or (SS_GROUPGUBUN eq '4' and _params.b_idx eq '10'))}">
                        <c:if test="${mode ne 'W' and SS_MEM_ID eq CONTENTS.USER_ID}"><!-- 내가쓴글만수정하기 17.12.14 -->
                        <li>
                            <a href="#none" id="btn-mode-chg" onClick="javascript: changeBTN('btnA2'); doChangeUIToModifyMode();">수정모드</a>
                        </li>
                        </c:if>
                        </c:if>
                        <!-- 17-11-23  -->
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
</form>
<script src="/admin/_common/ckeditor/ckeditor.js"></script>
<script type="text/javascript">

window.onload = function() {

	// bishop --> upload/gyogu_album/
	CKEDITOR.replace( 'contents', {
		height: 450,
    	filebrowserUploadUrl: '/admin/ckeditor/fileUploadInContent02.do'
  	});
	
};

<%
/*
 * SS_GROUPGUBUN : 1 (일반)
 * SS_GROUPGUBUN : 2 교구 게시판 관리자 -> b_idx=12
 * SS_GROUPGUBUN : 3 본당 게시판 관리자
 * SS_GROUPGUBUN : 4 공동체소식 게시판 관리자 -> b_idx=10
 */
%>
<c:set var="upsertMode" value="update" />
<c:if test="${mode eq 'W'}" >
	<c:set var="upsertMode" value="insert" />
</c:if>

<c:choose>
<c:when test="${SS_WRITEYN eq 'Y' and ( (SS_GROUPGUBUN eq '2' and _params.b_idx eq '12' and SS_CHURCHIDX ne '') or (SS_GROUPGUBUN eq '4' and _params.b_idx eq '10') ) }" >
	var isHide=true;
	function doChangeUIToModifyMode() {
		if(isHide) {
			$('.writeTable').show();
	        $('.viewPage').hide();
	        $("#btn-mode-chg").text("수정취소");
		} else {
	        $('.writeTable').hide();
	        $('.viewPage').show();
	        $("#btn-mode-chg").text("수정모드");
		}
		isHide = !isHide;
	}
	
	function changeBTN(btnid) {
		if($("#"+btnid).css("width") == "0px") {
			$("#"+btnid).text("저장");
			$("#"+btnid).css("width","");
			//$("#"+btnid).css("display","");//show
		} else {
			$("#"+btnid).html("&nbsp;");
			$("#"+btnid).css("width","0px");
			//$("#"+btnid).css("display","none");//hide
		}
	}
	
	function goUpsertNBoard() {
		var vfm = document.form02;
		if(vfm.title.value=='') {
			alert('제목을 입력해 주세요.'); vfm.title.focus(); return;
		}
		if( $("#is_view").val() == 'N' ) {
			if(!confirm('비노출이 맞습니까?')) {
				return;
			}
		}
		vfm.submit();
	}
	
	<c:if test="${_params.b_idx eq '12'}">
	document.form02.action="/news/news_view_gyogu_${upsertMode}.do";
	isHide=true;
	</c:if>
	<c:if test="${_params.b_idx eq '10'}">
	document.form02.action="/news/news_view_community_${upsertMode}.do";
	isHide=true;
	</c:if>
</c:when>
<c:otherwise>
	<c:if test="${_params.b_idx eq '12'}">
	document.form02.action="/news/news_view_gyogu_${upsertMode}.do";
	isHide=true;
	</c:if>
	<c:if test="${_params.b_idx eq '10'}">
	document.form02.action="/news/news_view_community_${upsertMode}.do";
	isHide=true;
	</c:if>
</c:otherwise>
</c:choose>

<c:if test="${mode eq 'W' or _params.mode eq 'W'}">
	doChangeUIToModifyMode();
	changeBTN('btnA2');
</c:if>



</script>
</body>

</html>
