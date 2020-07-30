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

function viewAlbum(bl_idx) {
	
	var sessionId	=	"${SS_MEM_ID}";
	var useYnLogin	=	"${bd_content.USEYN_LOGIN}";
	
	/*로그인 사용여부 체크*/
	if(useYnLogin == 'Y') {
		if(sessionId == '') {
			alert("로그인을 해주세요.");
			return false;
		}
	}
	
	var frm = document.form01;
	
	frm.action	  = '/n/board/alb_board_view.do';
	frm.i_sBlidx.value	= bl_idx;
	frm.submit();
}

window.onload = function () {
	selectChange();
}

</script>

<body>
<form name="form01" action="/n/board/alb_board_list.do" method="GET">
<input type="hidden" name="i_sBidx"  value="${_params.i_sBidx}" />
<input type="hidden" name="i_sBlidx"  value="${_params.i_sBlidx}" />
<input type="hidden" name="i_sCIdx"  value="${_params.i_sCIdx}" />
<input type="hidden" name="idx"	value="" />
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
					<c:if test="${bd_content.USEYN_JIGU eq 'Y'}">
						<%@ include file="/church/temp_tab.jsp" %>
					</c:if>
					<!-- tabs -->
					<ul class="tabs">
						<li <c:if test="${empty _params.i_sCIdx}">class="on"</c:if>><a href="/n/board/alb_board_list.do?i_sBidx=${bd_content.B_IDX}">전체보기</a></li>
						<c:forEach items="${category_list}" var="list">
							<li <c:if test="${_params.i_sCIdx eq  list.c_idx}">class="on"</c:if>><a href="/n/board/alb_board_list.do?i_sBidx=${bd_content.B_IDX}&i_sCIdx=${list.c_idx}">${list.name}
						</a></li>
						</c:forEach>
						
					</ul>
					<!-- //tabs -->
					<h3 class="ttl tb fl">${strCategoryName}</h3>
					<!-- srchSel -->
					<div class="srchSel fr">
						<c:if test="${SS_WRITEYN eq 'Y' and SS_GROUPGUBUN eq bd_content.B_TYPE and SS_ORGIDX eq bd_content.ORG_IDX}">
							<c:set var="isBoardMngmnt" value="T" />
						</c:if>
						
						<c:if test="${isBoardMngmnt eq 'T'}">
					
						<!-- 글쓰기 권한이 있다면 버튼을 보이게 한다. START-->
						<dl>
							<dd>
								<script>
								function gotoWriteNBoardByManager() {
									location.href='/n/board/alb_board_view.do?mode=W&i_sBidx=${bd_content.B_IDX}';
								}
								</script>
								<button type="button" id="btnBonNboarWrite"  onclick="javascript: gotoWriteNBoardByManager(); return false;">글쓰기</button>
							</dd>
						</dl>
						<!-- 글쓰기 권한이 있다면 버튼을 보이게 한다. END -->
						</c:if>
						<dl>
							<dt>검색</dt>
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
									<li><a href='javascript: viewAlbum(${list.BL_IDX})'>
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
