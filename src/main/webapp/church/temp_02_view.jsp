<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %><%


%>
<body>
    <p class="skipNavi" tabindex="1">본문바로가기</p>
    <!-- wrap -->
    <div id="wrap">
        <!-- header -->
        <header>
            <%@include file="/_common/inc/gnb.jsp" %>
        </header>
        <!-- //header -->
        <!-- subVisual -->
        <section class="subVisual">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap">
                <article>
                    <h3>본당현황</h3>
                    <ul>
                        <li>홈</li>
                        <li>본당</li>
                        <li>본당현황</li>
                    </ul>
                </article>
            </div>
        </section>
        <!-- //subVisual -->
        <!-- secWrap -->
        <a name="TOP_LINE"></a>
        <form id="form01" name="form01" action="" enctype="multipart/form-data" method="POST" >
        <input type="hidden" name="church_idx" value="${SS_CHURCHIDX }" />
        <input type="hidden" name="b_idx" value="${CONTENTS.B_IDX }" />
        <input type="hidden" name="bl_idx" value="${CONTENTS.BL_IDX }" />
        
        <div class="secWrap">
            <!-- sec01 -->
            <section class="sec01 fin">
                <!-- secCont -->
                <div class="secCont">
                    <!-- tabs -->
                    <ul class="tabs">
                        <li><a href="/church/temp_01.do?tabs=&qk=&church_idx=">미사시간</a></li>
                        <li class="on"><a href="/church/temp_02.do?tabs=&qk=&church_idx=">본당알림</a></li>
                        <li><a href="/church/temp_03.do?tabs=&qk=&church_idx=">본당앨범</a></li>
                    </ul>
                    <!-- //tabs -->
                    <h3 class="ttl tb">본당알림</h3>
                    <!-- srchTabs -->
                    <% pageContext.setAttribute("temp_no",   "02"); %> 
                    <%@ include file="/church/temp_tab.jsp" %>
                    <!-- //srchTabs --> 
                    <!-- viewPage -->
                    <div class="viewPage">
                        <table class="views">
                            <caption>상세 페이지</caption>
                            <thead>
                                <tr>
                                    <td>
                                        <span class="ttl">
                                            <i class="blue">[${CONTENTS.CHURCH_NAME}]</i>
                                            <i>${CONTENTS.TITLE}</i>
                                        </span>
                                        <span class="date">${CONTENTS.REGDATE}</span>&nbsp;&nbsp;&nbsp;
                                    </td>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>
                                    <div>${CONTENTS.CONTENT }</div>
                                    </td>
                                </tr>
                            </tbody>
                            <tfoot>
                                <tr>
                                    <td>
                                        <span class="file">
					                        
						                        <em>첨부파일 : </em>
						                        <% pageContext.setAttribute("HEAD_CONST__attach_file_form__MAX_FILE_COUNT", 5); %>
						                        <%@ include file="/admin/_common/inc/attach_file_form_loop_list.jsp" %>
											
                                        </span>
                                        <ul class="sns">
                                        	<c:set var="SNS_URL" value="/church/temp_02.do?${pageContext.request.queryString}" />
                                        	<c:set var="SNS_DES" value="${CONTENTS.CHURCH_NAME}앨범:${CONTENTS.TITLE}" />
                                            <li><a href="javascript:goFacebook('${SNS_URL}', '${SNS_DES}')"><img src="/img/sub/_ico/ico_facebook.png" alt="페이스북"></a></li>
                                            <li><a href="javascript:goTweeter('${SNS_URL}', '${SNS_DES}')"><img src="/img/sub/_ico/ico_tweet.png" alt="트위터"></a></li>
                                            <li><a href="javascript:goKakao('${SNS_URL}', '${SNS_DES}')"><img src="/img/sub/_ico/ico_kakao.png" alt="카카오스토리"></a></li>
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
                            <caption>신규등록/수정하기 폼</caption>
                            <tbody>
                                <tr>
                                    <th scope="row">제목</th>
                                    <td>
                                        <span class="form">
                                        	<input class="form-control" type="hidden" name="church_idx" value="${SS_CHURCHIDX}">
                                            <input class="form-control" type="text" name="title" id="title" value="${CONTENTS.TITLE }">
                                        </span><!-- 
                                        <span class="chkForm v2">
                                            <input type="checkbox" name="is_notice" id="is_notice" value="Y" <c:if test="${CONTENTS.IS_NOTICE=='Y' }"> checked="checked"</c:if>>
                                            <label class="checkbox-inline" for="">
                                                <span> 게시글을 최상단에 고정 노출함</span>
                                            </label>
                                        </span>
                                        <span class="chkForm v2">
                                            <input type="checkbox" name="is_tmp2" id="is_tmp2" value="Y" onclick="javascript:$('#is_view').val(this.checked ? 'Y':'N') " <c:if test="${CONTENTS.IS_VIEW=='Y' }"> checked="checked"</c:if>>
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
                    <c:set var="vPrevURI" value="/church/temp_02_view.do?churchIdx=${CONTENTS.CHURCH_IDX}&b_idx=${CONTENTS.B_IDX}&bl_idx=${CONTENTS.BEFORE_BL_IDX}" />
                    <c:set var="vPostURI" value="/church/temp_02_view.do?churchIdx=${CONTENTS.CHURCH_IDX}&b_idx=${CONTENTS.B_IDX}&bl_idx=${CONTENTS.NEXT_BL_IDX}" />
                    <ul class="btn btnLeft">
                        <li class="gray"><a href="${vPrevURI }">이전</a></li>
                        <li class="gray"><a href="${vPostURI }">다음</a></li>
                    </ul>
                    <!-- //btnLeft -->
                    <!-- btn -->
                    <ul class="btn">
                        <!-- 17-11-23  -->
                        <li>
                        	<a href="#none" id="btnA2" style="width: 0;" onclick="javascript: goUpsertNBoard();">&nbsp;</a>
                        </li>
                        <c:if test="${SS_CHURCHIDX ne '' and SS_WRITEYN eq 'Y' and SS_GROUPGUBUN eq '3'}">
                        <c:if test="${mode ne 'W' and SS_CHURCHIDX eq CONTENTS.CHURCH_IDX and SS_MEM_ID eq CONTENTS.USER_ID}"><!-- 자기 본당 것만 수정가능하게 -->
                        <li>
                            <a href="#none" id="btn-mode-chg" onClick="javascript: changeBTN('btnA2'); doChangeUIToModifyMode();">수정모드</a>
                        </li>
                        </c:if>
                        </c:if>
                        <!-- 17-11-23  -->
                        <li>
                        	<a href="/church/temp_02.do?${queryString}&tabs=${tabs}&b_idx=${CONTENTS.B_IDX}&pageNo=${pageNo}&pageSize=${pageSize}">목록</a>
                        </li>
                    </ul>
                    <!-- //btn -->
                </div>
                <!-- //secCont -->
            </section>
            <!-- //sec01 -->
        </div>
        <!-- //secWrap -->
        </form>
        
        <!-- footer -->
        <footer>
            <%@ include file="/_common/inc/footer.jsp" %>
        </footer>
        <!-- //footer -->
    </div>
    <!--// wrap -->
<script src="/admin/_common/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
window.onload = function() {

	CKEDITOR.replace( 'contents', {
		height: 450,
    	filebrowserUploadUrl: '/admin/ckeditor/fileUploadInContent03.do'
  	});
	
};

<c:choose>
<c:when test="${SS_WRITEYN eq 'Y' and SS_CHURCHIDX ne '' and SS_GROUPGUBUN eq '3'}">
	var isHide=true;
	function doChangeUIToModifyMode() {
	    // 17-11-23
		if (isHide) {
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
		var vfm = document.form01;
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
	
	<c:if test="${mode eq 'W'}">
	document.form01.action="/church/temp_02_view_insert.do";
	</c:if>
	<c:if test="${mode ne 'W'}">
	document.form01.action="/church/temp_02_view_update.do";
	</c:if>
	isHide=true;
</c:when>
<c:otherwise>
	<c:if test="${mode eq 'W'}">
	document.form01.action="/church/temp_02_view_insert.do";
	</c:if>
	<c:if test="${mode ne 'W'}">
	document.form01.action="/church/temp_02_view_update.do";
	</c:if>
	//isHide=false;
</c:otherwise>
</c:choose>
	

<c:if test="${mode eq 'W'}">
	doChangeUIToModifyMode();	
	changeBTN('btnA2');
</c:if>


</script>
</body>
</html>
