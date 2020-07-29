<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<script type="text/javascript">
function goPage(pageNo) {
	var vfm = document.form01;
	vfm.pageNo.value=pageNo;
	vfm.submit();
    return false;
}

function goSearchLis() {
	var vfm = document.form01;
	if(vfm.srchGubun.value == 'date'){
		if(vfm.srchYearS.value == '') {
			alert('검색할 날짜를 입력하세요.');
			vfm.srchYearS.focus();
			return false;
		}
		if(vfm.srchYearE.value == '') {
			alert('검색할 날짜를 입력하세요.');
			vfm.srchYearE.focus();
			return false;
		}
	} else if(vfm.srchGubun.value == 'contents') {
		if(vfm.srchText.value == '') {
			alert('검색할 내용을 입력하세요.');
			vfm.srchText.focus();
			return false;
		}
	}
	vfm.submit();
    return false;
}

function selectChange() {
	var vfm = document.form01;
	if(vfm.srchGubun.value == 'all') {
		document.getElementById('srchText').style.display = "none";
		document.getElementById('srchYearS').style.display = "none";
		document.getElementById('dash').style.display = "none";
		document.getElementById('srchYearE').style.display = "none";
		vfm.srchText.value="";
		vfm.srchYearS.value="";
		vfm.srchYearE.value="";
ㅕ
	}else if(vfm.srchGubun.value == 'date') {
		document.getElementById('srchText').style.display = "none";
		document.getElementById('srchYearS').style.display = "block";
		document.getElementById('dash').style.display = "block";
		document.getElementById('srchYearE').style.display = "block";
		vfm.srchText.value="";
		
	} else if(vfm.srchGubun.value == 'contents') {
		document.getElementById('srchYearS').style.display = "none";
		document.getElementById('dash').style.display = "none";
		document.getElementById('srchYearE').style.display = "none";
		document.getElementById('srchText').style.display = "block";
		vfm.srchYearS.value="";
		vfm.srchYearE.value="";
		
	}
}

function viewMov(b_idx, bl_idx) {
	var vfm = document.form01;
	vfm.action = '/news/mov_view.do';
	vfm.b_idx.value=b_idx;
	vfm.bl_idx.value=bl_idx;
	vfm.submit();
    return false;
}

window.onload = function () {
	selectChange();
}
</script>

<body>
<form name="form01" action="/news/mov_list.do" method="POST">
<input type="hidden" name="c_idx" id="c_idx" value="${c_idx}"/>
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}"/>
<input type="hidden" name="bl_idx" id="bl_idx"/>
    <p class="skipNavi" tabindex="1">본문바로가기</p>
    <!-- wrap -->
    <div id="wrap">
        <!-- header -->
        <header>
            <%@include file="/_common/inc/gnb.jsp" %>
        </header>
        <!-- //header -->
        <!-- subVisual -->
        <section class="subVisual news">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap">
                <article>
                    <h3>교구영상</h3>
                    <ul>
                        <li>홈</li>
                        <li>소식</li>
                        <li>교구영상</li>
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
                    <h3 class="ttl fl">교구영상</h3>
                    <!-- srchSel -->
                    <div class="srchSel fr">
                        <dl>
                            <dt>검색</dt>
                            <dd>
                                <label for=""></label>
                                <select name="srchGubun" id="srchGubun" onChange="javascript:selectChange()">
                                	<option value="all" <c:out value="${schTextGubunMap['all']}"/>>전체보기</option>
                                    <option value="date" <c:out value="${schTextGubunMap['date']}"/>>년월</option>
                                    <option value="contents" <c:out value="${schTextGubunMap['contents']}"/>>제목 + 내용</option>
                                </select>
                            </dd>
                            <dd class="wave">
                                <label for=""></label>
                                <input type="date" name="srchYearS" id="srchYearS" min="1945-08-15" max="2030-12-31" value="${_params.srchYearS}">
                                <span name="dash" id="dash">~</span>
                                <label for=""></label>
                                <input type="date" name="srchYearE" id="srchYearE" min="1945-08-15" max="2030-12-31" value="${_params.srchYearE}">
                            </dd>
                            <dd>
                                <label for=""></label>
    	                        <input type="search" name="srchText" id="srchText" value="${_params.srchText}">
                                <button name="button" onclick="javascript: goSearchLis(); return false;">검색</button>
                            </dd>
                        </dl>
                    </div>
                    <!-- //srchSel -->
                    <!-- movList -->


                    <div class="movieList">
                        <ul class="clearfix">
                        <c:choose>
                            <c:when test="${fn:length(movList) > 0}">
                                <c:forEach items="${movList}" var="list">
                                	<c:set var="YOUTUBE_THUMBNAIL_URL" value="/upload/gyogu_mov/${list.FILENAME}" />
                                	<c:if test="${list.YOUTUBE_THUMBNAIL_URL ne ''}">
										<c:set var="YOUTUBE_THUMBNAIL_URL" value="${list.YOUTUBE_THUMBNAIL_URL}" />
									</c:if>

                                    <li>
                                        <a href="javascript:viewMov(${list.B_IDX},${list.BL_IDX})">
                                        <span>${list.TITLE}</span>
                                        <span><img alt="" src="${YOUTUBE_THUMBNAIL_URL}"></span>
                                        <em>${list.REGDATE}</em>
                                        </a>
                                    </li>
                                </c:forEach>
                                </c:when>
                            </c:choose>



                        </ul>
                    </div>
                    <!-- //movList -->
					<%@ include file="/_common/inc/paging2.jsp" %>
                    <!-- //arrow -->
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
</body>

</html>
