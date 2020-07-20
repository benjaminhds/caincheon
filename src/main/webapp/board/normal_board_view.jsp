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

/*view에서 등록할때 쓰는 버튼 이벤트*/
function viewButtonEvent() {
	$("#btn-mode-chg").on("click",function(){
		/*암호사용여부 체크 여부에 따라 로직*/
		var i_sUseYnSc			= "${bd_content.USEYN_SECRET}";
		
		if(i_sUseYnSc == 'Y') {
			var post_password		= $("input[name=post_password]").val();
			var post_password_write	= $("input[name=post_password_write]").val();
			
			if(post_password_write == '') {
				alert("암호를 입력해주세요.");
				return false;
			}
			
			if(post_password_write != post_password) {
				alert("암호를 확인해주세요.")
				return false;
			}
			
		}
		
		doChangeUIToModifyMode();
		changeBTN('btnA2');
	});
}

function goList() {
	document.form01.action = '/n/board/normal_board_list.do';
	document.form01.submit();
	return false;
}

function goContents(b_idx,bl_idx) {
	if(bl_idx=='0' || bl_idx=='') {
		alert('내용이 없습니다.');
		//return false;
	} else {
		document.form01.action = 'n/board/normal_board_view.do';
		document.getElementById('i_sBidx').value=b_idx;
		document.getElementById('i_sBlidx').value=bl_idx;
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

<form name="form01" action="/n/board/normal_board_list.do" method="POST">
<input type="hidden" name="i_sBidx" id="i_sBidx" value="${bd_content.B_IDX}"/>
<input type="hidden" name="i_sCidx" id="i_sCidx" value="${post_content.C_IDX}"/>
<input type="hidden" name="schTextGubun" id="schTextGubun" value="${_params.schTextGubun}"/>
<input type="hidden" name="schText" id="schText" value="${_params.schText}"/>
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}"/>
<input type="hidden" name="i_sBlidx" id="i_sBlidx"/>
</form>

<c:set var="PAGENO" value="1" />
<c:if test="${paging.pageNo ne null or paging.pageNo ne ''}"><c:set var="PAGENO" value="${paging.pageNo}" /></c:if>
<form name="form02" id="form02" action="/n/board/normal_board_list.do" method="POST" enctype="multipart/form-data">
<input type="hidden" name="i_sBidx"		id="i_sBidx"	value="${bd_content.B_IDX}"/>
<input type="hidden" name="i_sBlidx"	id="i_sBlidx"	value="${post_content.BL_IDX}"/>
<input type="hidden" name="i_sCIdx"		id="i_sCIdx"	value="${post_content.C_IDX}"/>
<input type="hidden" name="pageNo"		id="pageNo"		value="${PAGENO}"/>
<input type="hidden" name="mode"		id="mode"		value="${_params.mode}"/>

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
					<!-- tabs -->
				   <ul class="tabs">
						<li <c:if test="${_params.i_sCIdx == null}">class="on"</c:if>><a href="/n/board/normal_board_list.do?i_sBidx=${bd_content.B_IDX}">전체보기</a></li>
						<c:forEach items="${category_list}" var="list">
							<li <c:if test="${_params.i_sCIdx eq  list.c_idx}">class="on"</c:if>><a href="/n/board/normal_board_list.do?i_sBidx=${bd_content.B_IDX}&i_sCIdx=${list.c_idx}">${list.name}
						</a></li>
						</c:forEach>
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
											<c:if test = "${post_content.IS_NOTICE == 'Y'}">	
											<i class="red">[공지사항]</i>
											</c:if>
											<i class="blue">${post_content.DEPART_NAME}</i>
											<i>${post_content.TITLE}</i>
										</span>
										 <span class="date">작성자 : ${post_content.WRITER} &nbsp;&nbsp; ${post_content.REGDATE}</span>
									</td>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>
									${post_content.CONTENT}
									</td>
								</tr>
							</tbody>  
							<tfoot>
								<tr>
									<td>
										<c:if test="${bd_content.USEYN_ATTACHEMENT eq 'Y' and bd_content.USEYN_DOWNLOAD_PERM eq 'Y'}">
											<span class="file">
												<c:if test="${post_content.FILE_CNT > 0}">
													<em>첨부파일 : </em>
													<c:set var="HEAD_CONST__attach_file_form__MAX_FILE_COUNT" value="${post_content.FILE_CNT}" />
													<%@ include file="/admin/_common/inc/attach_file_form_loop_list.jsp" %>
												</c:if>
											</span>
										</c:if>
										<ul class="sns">
											<li><a href="javascript:goFacebook('/n/board/normal_board_view.do?i_sBidx=${bd_content.B_IDX}&i_sBlidx=${post_content.BL_IDX}','인천교구')"><img src="/img/sub/_ico/ico_facebook.png" alt="페이스북"></a></li>
											<li><a href="javascript:goTweeter('/n/board/normal_board_view.do?i_sBidx=${bd_content.B_IDX}&i_sBlidx=${post_content.BL_IDX}','인천교구')"><img src="/img/sub/_ico/ico_tweet.png" alt="트위터"></a></li>
											<li><a href="javascript:goKakao('/n/board/normal_board_view.do?i_sBidx=${bd_content.B_IDX}&i_sBlidx=${post_content.BL_IDX}','인천교구')"><img src="/img/sub/_ico/ico_kakao.png" alt="카카오스토리"></a></li>
										</ul>
									</td>
								</tr>
							</tfoot>
						</table>
						<c:if test="${bd_content.USEYN_COMMENT eq 'Y'}">	<!-- 댓글 사용여부에 따라 -->
							<%@ include file="/board/common_comment.jsp" %>
						</c:if>
					</div>
					<!-- //viewPage -->
					<!-- 17-11-23  -->
					<div class="writeTable" style="display:none;">
						<c:choose>
							<c:when test="${_params.mode eq 'W'}">
								<h3 class="ttl tb">등록</h3>
							</c:when>
							<c:otherwise>
								<h3 class="ttl tb">수정</h3>
							</c:otherwise>
						</c:choose>
						<table class="shirine_st write">
							<caption>수정하기 폼</caption>
							<tbody>
								<tr>
									<th scope="row">작성자</th>
									<td>
										<span>${SS_MEM_NM } ( ${SS_MEM_ID} )</span>
										<input type="hidden" name="depart_idx" value="${SS_DEPARTIDX }" />
										<input type="hidden" name="church_idx" value="${SS_CHURCHIDX }" />
									</td>
								</tr>
								<c:if test="${bd_content.USEYN_SECRET eq 'Y'}">
									<tr class="area_password">
										<th scope="row">암호</th>
										<td>
											<input class="form-control" type="password" name="write_password" id="write_password" value="">
										</td>
									</tr>
								</c:if>
								<tr>
									<th>카테고리</th>
									<td>
										<select class="form-control" name="i_sCategory" id="i_sCategory">
											<option value="">선택</option>
											<c:forEach items="${category_list}" var="list">
												<option value="${list.code_inst}">${list.name}</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th scope="row">제목</th>
									<td>
										<span class="form">
											<input class="form-control" type="text" name="title" id="title" value="${post_content.TITLE }">
										</span>
										<span class="date">${post_content.REGDATE}</span>
									</td>
								</tr>
								<tr>
									<th scope="row">내용</th>
									<td>
										<div>
											<textarea class="form-control h-400" name="contents" id="contents">${post_content.CONTENT }</textarea>
										</div>
									</td>
								</tr>
								<c:if test="${bd_content.USEYN_ATTACHEMENT eq 'Y'}">
								<tr>
									<th scope="row">이미지 첨부</th>
									<td>
										<div class="form-group input-group">
										 <% pageContext.setAttribute("HEAD_CONST__attach_file_form__MAX_FILE_COUNT", 5); %>
											<%@ include file="/admin/_common/inc/attach_file_form_loop.jsp" %>
										</div>
									</td>
								</tr>
								</c:if>
							</tbody>
						</table>
					</div>
					<!-- writeTable -->
					<!-- //17-11-23 -->
					<!-- btnLeft -->
					<ul class="btn btnLeft">
						<li class="gray"><a href="javascript:goContents('${post_content.B_IDX}','${post_content.PRE_BL_IDX}')">이전</a></li>
						<li class="gray"><a href="javascript:goContents('${post_content.B_IDX}','${post_content.NEXT_BL_IDX}')">다음</a></li>
					</ul>
					<!-- //btnLeft -->
					<!-- btn -->
					<ul class="btn">
						<!-- 17-11-23  -->
						<li style="width:160px;">
							<a href="javascript:;" id="btnA2" style="width: 0;" onclick="javascript: goUpsertNBoard();">&nbsp;</a>
						</li>
						<!--
							1. 쓰기권한
							2. 그룹권한
							3. 소속성당
							
							3가지 비교해서 하나로 안될 시 수정 X
							
							충족되더라도 자기가 쓴거 아니면 수정 x
							
							추가 권한 필요 시 추가해야됨.
						 -->
						<c:if test="${bd_content.USEYN_SECRET eq 'Y'}">
							<li style="width:180px;">
									<input style="margin-right: 30px;" class="form-control" type="text" name="post_password_write" id="post_password_write">
									<input type="hidden" name="post_password" id="post_password" value="${post_content.PWD}">
							</li>
						</c:if>
						<c:if test="${SS_WRITEYN eq 'Y' and SS_GROUPGUBUN eq bd_content.B_TYPE and SS_ORGIDX eq bd_content.ORG_IDX}">
							<c:if test="${mode ne 'W' and SS_MEM_ID eq post_content.USER_ID}">
								<li>
									<a href="javascript:;" id="btn-mode-chg" style="width:100px;">수정모드</a>
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
		
		viewButtonEvent();
		
		var mode	=	"${_params.mode}"
	
	};

	var isHide=true;
	/*버튼 체인지*/
	function doChangeUIToModifyMode() {
		
		var	sUseYnSc			= "${bd_content.USEYN_SECRET}";
		
		if(isHide) {
			$(".writeTable").show();
			$(".viewPage").hide();
			$("#btn-mode-chg").text("수정취소");
			$("input[name=post_password]").hide();
			$("input[name=post_password_write]").hide();
		} else {
			$('.writeTable').hide();
			$('.viewPage').show();
			$("#btn-mode-chg").text("수정모드");
			
			if(sUseYnSc == 'Y') {
				$("input[name=post_password]").show();
				$("input[name=post_password_write]").show();
			}
		}
		isHide = !isHide;
	}
	/*버튼변경*/
	function changeBTN(btnid) {
		if($("#"+btnid).css("width") == "0px") {
			$("#"+btnid).text("저장");
			$("#"+btnid).css("width","");
			$("#post_password_write").hide();
		} else {
			$("#"+btnid).html("&nbsp;");
			$("#"+btnid).css("width","0px");
			$("input[name=post_password_write]").show();
		}
	}
	/*crud*/
	function goUpsertNBoard() {
		var vfm = document.form02;
		
		if(vfm.title.value=='') {
			alert('제목을 입력해 주세요.'); vfm.title.focus(); return;
		}
		
		/*비밀번호 길이 체크*/
		var passWrite	=	$("input[name=post_password_write]").val();
		
		if(passWrite.length > 10) {
			alert("비밀번호는 10글자 이내로 입력해주세요.");
			return false;
		}
		
		$.ajax({		 
			type:"POST"
			, url:"/n/board/normal_board_iudboard.do"
			, data:$("#form02").serialize()
			, dataType: "json"
			, cache: false
			, success:function(){
				alert("저장되었습니다.");
				
				var vfm = document.form01;
				vfm.submit();
				
				return false;
			}
			, error:function(xhr, textStatus) {
				alert("에러 입니다. 관리자에게 문의해주세요.");
			}
		});	
		
		
		vfm.submit();
	}
	
	<c:if test="${mode eq 'W' or _params.mode eq 'W'}">
		doChangeUIToModifyMode();
		changeBTN('btnA2');
	</c:if>
	/* document.form02.action="/news/news_view_gyogu_${upsertMode}.do";
	isHide=true;
	 */
</script>
</body>

</html>
