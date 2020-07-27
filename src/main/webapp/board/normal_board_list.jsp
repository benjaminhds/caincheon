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
	/*
	if(document.getElementById('searchText').value == '') {
		alert('검색할 내용을 입력하세요.');
		document.getElementById('searchText').focus();
		return false;
	}
	*/
	
	document.form01.submit();
	return false;
}
/*view page 이동*/
function goView(b_idx, bl_idx) {
	
	var sessionId	=	"${SS_MEM_ID}";
	var useYnLogin	=	"${bd_content.USEYN_LOGIN}";
	
	/*로그인 사용여부 체크*/
	if(useYnLogin == 'Y') {
		if(sessionId == '') {
			alert("로그인을 해주세요.");
			return false;
		}
	}
	
	document.viewmove.action = '/n/board/normal_board_view.do';
	document.getElementById('i_sBidx').value=b_idx;
	document.getElementById('i_sBlidx').value=bl_idx;
	document.viewmove.submit();
	return false;
}

</script>
<body>

<form name="viewmove" action="/n/board/normal_board_view.do" method="POST">
	<input type="hidden" name="i_sBidx"		id="i_sBidx" value="${b_idx}"/>
	<input type="hidden" name="i_sBlidx"	id="i_sBlidx" value="${bl_idx}"/>
	<input type="hidden" name="schTextGubun" id="schTextGubun" value="${_params.schTextGubun}"/>
	<input type="hidden" name="schText" id="schText" value="${_params.schText}"/>
</form>
<form name="form01" action="/n/board/normal_board_list.do" method="POST">
<input type="hidden" name="c_idx" id="c_idx" value="${bd_content.i_sCIdx}"/>
<input type="hidden" name="b_idx" id="b_idx" value="${bd_content.i_sBidx}"/>
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
					<!-- tabs -->
					<ul class="tabs">
						<li <c:if test="${empty _params.i_sCIdx}">class="on"</c:if>><a href="/n/board/normal_board_list.do?i_sBidx=${bd_content.B_IDX}">전체보기</a></li>
						<c:forEach items="${category_list}" var="list">
					
							<li <c:if test="${_params.i_sCIdx eq  list.c_idx}">class="on"</c:if>><a href="/n/board/normal_board_list.do?i_sBidx=${bd_content.B_IDX}&i_sCIdx=${list.c_idx}">${list.name}
						</a></li>
						</c:forEach>
					</ul>
					<!-- //tabs -->
					<h3 class="ttl tb fl">${strCategoryName}</h3>
					<!-- srchSel -->
					<div class="srchSel fr">
						<!-- 글쓰기 권한이 있다면 버튼을 보이게 한다. START-->
						<c:set var="isBoardMngmnt" value="F" />
					<%-- 
					
						1. TEST 쓰기권한, 그룹, 소속 까지 다 맞는 경우 Check
						
						 --%>
						 <%-- ${SS_WRITEYN} 111 ${SS_GROUPGUBUN} 222 ${bd_content.B_TYPE} 333 ${SS_ORGIDX} 444 ${bd_content.ORG_IDX } --%>
						<c:if test="${SS_WRITEYN eq 'Y' and SS_GROUPGUBUN eq bd_content.B_TYPE and SS_ORGIDX eq bd_content.ORG_IDX}">
							<c:set var="isBoardMngmnt" value="T" />
						</c:if>
						
						<c:if test="${isBoardMngmnt eq 'T'}">
							<dl>
								<dd>
									<script>
									function gotoWriteNBoardByManager() {
										location.href='/n/board/normal_board_view.do?mode=W&i_sBidx=${bd_content.B_IDX}';
									}
									</script>
									<button type="button" id="btnBonNboarWrite"  onclick="javascript: gotoWriteNBoardByManager(); return false;">글쓰기</button>
								</dd>
							</dl>
						</c:if>
						<!-- 글쓰기 권한이 있다면 버튼을 보이게 한다. END -->
						<dl>
							<dt>검색</dt>
							<dd>
								<label for=""></label>
								<select name="schTextGubun" id="schTextGubun">
									<option value="title" <c:if test="${_params.schTextGu eq 'title'}">selected</c:if>>제목</option>
									<option value="content" <c:if test="${_params.schTextGu eq 'content'}">selected</c:if>>내용</option>
									<option value="all" <c:if test="${_params.schTextGu eq 'all'}">selected</c:if>>제목 + 내용</option>
									<option value="writer" <c:if test="${_params.schTextGu eq 'writer'}">selected</c:if>>작성자</option>
								</select>
							</dd>
							<dd>
								<label for=""></label>
								<input type="search" name="schText" id="schText" value="${_params.schText}">
								 <button onClick="this.form.submit()">검색</button>
							</dd>
						</dl>
					</div>
					<!-- //srchSel -->
					<!-- boardList -->
					<div class="boardList oflow v2">
						<table>
							<caption> 전체보기</caption>
							<colgroup>
								<col style="width:10%">
								<col style="width:12%">
								<col>
								<col style="width:8%">
								<col style="width:8%">
								<col style="width:10%">
							</colgroup>
							<thead>
								<tr>
									<th scope="col">No.</th>
									<th scope="col">사진</th>
									<th scope="col">분류</th>
									<th scope="col">제목</th>
									<th scope="col">작성자</th>
									<th scope="col">작성일</th>
									<th scope="col">첨부파일</th>
								</tr>
							</thead>
							<tbody>
						   	<c:choose>
								<c:when test="${fn:length(postList) > 0}">
									<c:forEach items="${postList}" var="list">
									<tr>
										<th scope="row">
										<c:choose>
											<c:when test="${list.IS_NOTICE eq 'Y'}">
													<i class="ico brown">공지</i>
											</c:when>
											<c:otherwise>
													${list.R_NUM}
											</c:otherwise>
										</c:choose>
										</th>
										<td>
											<c:choose>
												<c:when test="${fn:indexOf(list.STRFILENAME, 'JPG') > -1}">
													<img src="${list.FILEPATH}${list.STRFILENAME}" />
												</c:when>
												<c:otherwise><img src="/_common3/img/No-Image.gif" width=200 border=0/></c:otherwise>
										</c:choose>
										</td>
										<td><strong>
										${list.DEPART_NAME}<c:if test="${list.DEPART_NAME eq ''}">${list.ORG_NAME }</c:if>
										</strong></td>
										<td><a href="javascript: goView(${list.B_IDX},${list.BL_IDX})"><c:if test="${isBoardMngmnt eq 'T' and list.IS_VIEW ne 'Y'}"><font color="darkgray"></c:if>${list.TITLE}<c:if test="${isBoardMngmnt eq 'T' and list.IS_VIEW ne 'Y'}"></font></c:if></a></td>
										<td>${list.WRITER}</td>
										<td>${list.REGDATE}</td>
										<td class="file">
										<c:if test = "${list.FILE_CNT ne '0'}">	
											<a href="javascript: attachedFile_Download ('${list.FILEPATH1}','${list.FILENAME1}','${list.STRFILENAME1}')" 
											><img src="/img/sub/_ico/board_download.png" alt=""></a>
										</c:if>
										</td>
									</tr>
									</c:forEach>
									</c:when>
								</c:choose>
							</tbody>
						</table>
					</div>
					<!-- //boardList -->
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
