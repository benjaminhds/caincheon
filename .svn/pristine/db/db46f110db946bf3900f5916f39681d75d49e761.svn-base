<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<script type="text/javascript">
function file_download(filePath, fileName, strfileName) {
	var vfm = document.frmDown;
	vfm.fileName.value=fileName;
    vfm.filePath.value=filePath;
    vfm.strfileName.value=strfileName;
    vfm.submit();
    return false;
}

function goList() {
	var vfm = document.form01;
	vfm.action = '/samok/cure_list.do';
	vfm.c_idx.value = '${_params.c_idx}';
	vfm.submit();
    return false;
}

function goContents(c_idx,bl_idx) {
	if(bl_idx=='0'||bl_idx=='') {
		alert('내용이 없습니다.');
		//return false;
	} else {
		var vfm = document.form01;
		vfm.action = '/samok/cure_view.do';
		vfm.c_idx.value=c_idx;
		vfm.bl_idx.value=bl_idx;
		vfm.submit();
	    return false;
	}
}
</script>
<body>
<form  name="frmDown" action="/filedownload.jsp" method="POST">
<input type="hidden" name="fileName"    id="fileName"    value=""/>
<input type="hidden" name="filePath"    id="filePath"    value=""/>
<input type="hidden" name="strfileName" id="strfileName" value=""/>
</form>

<form name="form01" action="/samok/cure_view.do" method="POST">
<input type="hidden" name="c_idx"  id="c_idx" value="${c_idx}"/>
<input type="hidden" name="bl_idx" id="bl_idx"/>
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}"/>
</form>

<c:set var="PAGENO" value="1" />
<c:if test="${paging.pageNo ne null or paging.pageNo ne ''}"><c:set var="PAGENO" value="${paging.pageNo}" /></c:if>
<form name="form02" action="/samok/cure_view_upsert.do" method="POST" enctype="multipart/form-data">
<input type="hidden" name="b_idx"  id="b_idx"  value="${CONTENTS.B_IDX}"/>
<input type="hidden" name="bl_idx" id="bl_idx" value="${CONTENTS.BL_IDX}"/>
<input type="hidden" name="pageNo" id="pageNo" value="${PAGENO }" />
    <p class="skipNavi" tabindex="1">본문바로가기</p>
    <!-- wrap -->
    <div id="wrap">
        <!-- header -->
        <header>
            <%@include file="/_common/inc/gnb.jsp" %>
        </header>
        <!-- //header -->
        <!-- subVisual -->
        <section class="subVisual support">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap"> 
                <article> 
                    <h3>사목자료</h3>
                    <ul>
                        <li>홈</li>
                        <li>자료실</li>
                        <li>사목자료</li>
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
                    <ul class="tabs v6">
                    	<li${subTitleOn1} <c:if test="${c_idx eq 'ALL'}"> class="on"</c:if> ><a href="/samok/cure_list.do?c_idx=ALL">전체보기</a></li>
                        <li${subTitleOn2}><a href="/samok/cure_list.do?c_idx=5">전례</a></li>
                        <li${subTitleOn3}><a href="/samok/cure_list.do?c_idx=6">양식</a></li>
                        <li${subTitleOn4}><a href="/samok/cure_list.do?c_idx=7">교리</a></li>
                        <li${subTitleOn5}><a href="/samok/cure_list.do?c_idx=8">사목</a></li>
                        <li${subTitleOn6}><a href="/samok/cure_list.do?c_idx=9">선교</a></li>
                    </ul>
                    <!-- //tabs -->
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
                                        <em>첨부파일 : </em>
                                        <c:choose>
                                        	<c:when test="${CONTENTS.DOWN_LEVEL eq 'A' or sessionMemId ne ''}" >
	                                        	<% pageContext.setAttribute("HEAD_CONST__attach_file_form__MAX_FILE_COUNT", 5); %>
						                        <%@ include file="/admin/_common/inc/attach_file_form_loop_list.jsp" %>
                                        	</c:when>
                                        	<c:otherwise>
                                        		<c:if test="${fn:length(CONTENTS.FILEPATH1) > 0}"><p>${CONTENTS.FILENAME1}</p></c:if>
	                                            <c:if test="${fn:length(CONTENTS.FILEPATH2) > 0}"><p>${CONTENTS.FILENAME2}</p></c:if>
	                                            <c:if test="${fn:length(CONTENTS.FILEPATH3) > 0}"><p>${CONTENTS.FILENAME3}</p></c:if>
	                                            <c:if test="${fn:length(CONTENTS.FILEPATH4) > 0}"><p>${CONTENTS.FILENAME4}</p></c:if>
	                                            <c:if test="${fn:length(CONTENTS.FILEPATH5) > 0}"><p>${CONTENTS.FILENAME5}</p></c:if>
                                        	</c:otherwise>
                                        </c:choose>
                                        </span>
                                        <ul class="sns">
                                        	<li><a href="javascript:goFacebook('/samok/cure_view.do?c_idx=${c_idx}&bl_idx=${bl_idx}','인천교구')"><img src="/img/sub/_ico/ico_facebook.png" alt="페이스북"></a></li>
                                            <li><a href="javascript:goTweeter('/samok/cure_view.do?c_idx=${c_idx}&bl_idx=${bl_idx}','인천교구')"><img src="/img/sub/_ico/ico_tweet.png" alt="트위터"></a></li>
                                            <li><a href="javascript:goKakao('/samok/cure_view.do?c_idx=${c_idx}&bl_idx=${bl_idx}','인천교구')"><img src="/img/sub/_ico/ico_kakao.png" alt="카카오스토리"></a></li>
                                        </ul>
                                    </td>
                                </tr>
                            </tfoot>              
                        </table>
                    </div>
                    <!-- //viewPage -->
                    <!-- 17-12-10  -->
                    <div class="writeTable" style="display:none;">
                        <h3 class="ttl tb">수정</h3>
                        <table class="shirine_st write">
                            <caption>수정하기 폼</caption>
                            <tbody>
                            	<tr>
                                    <th scope="row">작성자</th>
                                    <td>
                                    	<span>${_params.__SESSION_MAP__.SS_MEM_NM } ( ${_params.__SESSION_MAP__.SS_MEM_ID } )</span>
                                    	
                                    </td>
                                </tr>
                            	<tr>
                                    <th scope="row">분류</th>
                                    <td>
                                    	<select class="form-control" name="c_idx" id="c_idx">
                                    		<option value="5">전례</option>
                                    		<option value="6">양식</option>
                                    		<option value="7">교리</option>
                                    		<option value="8">사목</option>
                                    		<option value="9">선교</option>
                                    	</select>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">제목</th>
                                    <td>
                                        <span class="form ttl">
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
                                        </span>-->
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
                    <!-- //17-12-10 -->
                    <!-- btnLeft -->
                    <ul class="btn btnLeft">
                        <li class="gray"><a href="javascript:goContents('${CONTENTS.C_IDX}','${CONTENTS.PRE_BL_IDX}')">이전</a></li>
                        <li class="gray"><a href="javascript:goContents('${CONTENTS.C_IDX}','${CONTENTS.NEXT_BL_IDX}')">다음</a></li>
                    </ul>
                    <!-- //btnLeft -->
                    <!-- btn -->
                    <ul class="btn">
                        <!-- 17-12-10  -->
                        <li>
                        	<a href="#none" id="btnA2" style="width: 0;" onclick="javascript: goUpsertNBoard();">&nbsp;</a>
                        </li>
                        <c:if test="${SS_WRITEYN eq 'Y' and SS_DEPARTIDX ne '' and SS_GROUPGUBUN eq '2' and CONTENTS.USER_ID eq SS_MEM_ID}">
                        <c:if test="${mode ne 'W'}">
                        <li><!-- SS_MEM_ID eq CONTENTS.USER_ID -->
                            <a href="#none" id="btn-mode-chg" onClick="javascript: changeBTN('btnA2'); doChangeUIToModifyMode();">수정모드</a>
                        </li>
                        </c:if>
                        </c:if>
                        <!-- 17-12-10  -->
                        <li><a href="javascript: goList()">목록</a></li>
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
    	filebrowserUploadUrl: '/admin/ckeditor/fileUploadInContent04.do'
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
<c:when test="${SS_WRITEYN eq 'Y' and SS_DEPARTIDX ne '' and SS_GROUPGUBUN eq '2' }">
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
		/* if( $("#is_view").val() == 'N' ) {
			if(!confirm('비노출이 맞습니까?')) {
				return;
			}
		} */
		vfm.submit();
	}
	
	<c:if test="${_params.b_idx eq '14'}">
	document.form02.action="/samok/cure_view_${upsertMode}.do";
	isHide=true;
	</c:if>
</c:when>
<c:otherwise>
	<c:if test="${_params.b_idx eq '14'}">
	document.form02.action="/samok/cure_view_${upsertMode}.do";
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
