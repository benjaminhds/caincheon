<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<script type="text/javascript">
function goPage(pageNo) {
	document.getElementById('pageNo').value=pageNo;
	document.form01.submit();
    return false;
}

function goSearch() {
	if(document.getElementById('searchGubun').value == 'date'){
		if(document.getElementById('searchYear1').value == '') {
			alert('검색할 날짜를 입력하세요.');
			document.getElementById('searchYear1').focus();
			return false;
		}
		if(document.getElementById('searchYear2').value == '') {
			alert('검색할 날짜를 입력하세요.');
			document.getElementById('searchYear2').focus();
			return false;
		}
	} else if(document.getElementById('searchGubun').value == 'contents') {
		if(document.getElementById('searchText').value == '') {
			alert('검색할 내용을 입력하세요.');
			document.getElementById('searchText').focus();
			return false;
		}
	}

	document.form01.submit();
    return false;
}

function selectChange() {
	if(document.getElementById('searchGubun').value == 'all') {
		document.getElementById('searchText').style.display = "none";
		document.getElementById('searchYear1').style.display = "none";
		document.getElementById('dash').style.display = "none";
		document.getElementById('searchYear2').style.display = "none";
		
	}else if(document.getElementById('searchGubun').value == 'date') {
		document.getElementById('searchText').style.display = "none";
		document.getElementById('searchYear1').style.display = "block";
		document.getElementById('dash').style.display = "block";
		document.getElementById('searchYear2').style.display = "block";
		
	} else if(document.getElementById('searchGubun').value == 'contents') {
		document.getElementById('searchYear1').style.display = "none";
		document.getElementById('dash').style.display = "none";
		document.getElementById('searchYear2').style.display = "none";
		document.getElementById('searchText').style.display = "block";
	}
}

function viewAlbum(idx, cidx_text) {
	console.log("cidx_text ? "+cidx_text);
	var frm = document.form01;
	frm.action      = '/news/alb_view.do';
	frm.c_idx.value = cidx_text;
	frm.idx.value   = idx;
	frm.submit();
}

window.onload = function () {
//	 alert("로딩 완료");
	selectChange();
}

</script>

<body>
<form name="form01" action="/news/alb_list.do" method="GET">
<input type="hidden" name="c_idx"  value="${_params.c_idx }" />
<input type="hidden" name="idx"    value="" />
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}" />
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
                    <!-- tabs -->
                    <ul class="tabs">
                    	<li <c:if test="${_params.c_idx eq 'ALL'}">class="on"</c:if>><a href="/news/alb_list.do?c_idx=ALL">전체</a></li>
                    	<li <c:if test="${_params.c_idx eq '1'}">class="on"</c:if>><a href="/news/alb_list.do?c_idx=1">미사/행사</a></li>
                    	<li <c:if test="${_params.c_idx eq '2'}">class="on"</c:if>><a href="/news/alb_list.do?c_idx=2">교육/사업</a></li>
                    </ul>
                    <!-- //tabs -->
                    <h3 class="ttl tb fl">${strCategoryName}</h3>
                    <!-- srchSel -->
                    <div class="srchSel fr">
                        <dl>
                            <dt>검색</dt>
                            <!-- <dd class="wave">
                                <label for=""></label>
                                <input type="date">
                                <span>~</span>
                                <label for=""></label>
                                <input type="date">
                            </dd> -->
                            <dd>
                                <label for=""></label>
                                <select name="searchGubun" id="searchGubun" onChange="javascript:selectChange()">
                                	<option value="all" <c:out value="${schTextGubunMap['all']}"/>>전체</option>
                                    <option value="date" <c:out value="${schTextGubunMap['date']}"/>>년월</option>
                                    <option value="contents" <c:out value="${schTextGubunMap['contents']}"/>>제목 + 내용</option>
                                </select>
                            </dd>
                            <dd class="wave">
                                <label for=""></label>
                                <input type="date" name="searchYear1" id="searchYear1" min="1979-12-31" max="2030-12-31" value="${searchYear1}">
                                <span name="dash" id="dash">~</span>
                                <label for=""></label>
                                <input type="date" name="searchYear2" id="searchYear2" min="1979-12-31" max="2030-12-31" value="${searchYear2}">
                            </dd>
                            <dd>
                                <label for=""></label>
    	                        <input type="search" name="searchText" id="searchText" value="${searchText}">
                                <button name="button" onclick="javascript:goSearch();return false;">검색</button>                                
                            </dd>
                        </dl>
                    </div>
                    <!-- //srchSel -->
                    <!-- movList -->
                    <div class="movieList">
                        <ul class="clearfix">
                        <c:choose>
                            <c:when test="${fn:length(albList) > 0}">
                                <c:forEach items="${albList}" var="list">
                                    <li><a href='javascript: viewAlbum(${list.IDX}, "${_params.c_idx}")'>
	                                    <c:choose>
	                                    	<c:when test="${fn:length(list.STRFILENAME) > 0}">
	                                    	<img src="${list.FILEPATH}${list.STRFILENAME}" />
	                                    	</c:when>
	                                    	<c:otherwise><img src="" width=200 border=0/></c:otherwise>
                                    	</c:choose>
                                        <span>${list.TITLE}</span>
                                        <em>${list.ALBUM_DATE}</em></a>
                                    </li>                                
                                </c:forEach>
                                </c:when>
                            </c:choose>                            
                        </ul>
                    </div>
                    <!-- //movList -->
                    <!-- arrow -->
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
