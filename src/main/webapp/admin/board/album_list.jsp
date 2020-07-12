<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/admin/_common/inc/head.jsp" %>
<%
if(admSessionMemId==null||admSessionMemId.equals("")) {                // id가 Null 이거나 없을 경우
	response.sendRedirect("/admin/login.jsp");    // 로그인 페이지로 리다이렉트 한다.
}
%>
<script type="text/javascript">
function goPage(pageNo) {
	var vfm = document.form01;
	vfm.pageNo.value=pageNo;
	vfm.submit();
}

function goSearchAlbum0() {
	var vfm = document.form01;
 	if(vfm.schText.value != '') {
 		if(vfm.schTextGubun.value == '') {
 			vfm.schTextGubun.focus();
 			alert('검색조건을 확인하세요.');
 		} else {
	 		vfm.submit();
	 	}
	} else {
 		vfm.submit();
	}
}

function insertContents() {
	var vfm = document.form01;
	vfm.action = '/admin/board/album_view.do';
	vfm.query_type.value = "insert";
	vfm.submit();
}

function modifyContents(idx) {
	var vfm = document.form01;
	vfm.action = '/admin/board/album_view.do';
	vfm.query_type.value = "modify";
	vfm.idx.value=idx;
	vfm.submit();
}

function deleteContents(idx) {
	var vfm = document.form01;
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		vfm.action = '/admin/board/album_delete.do';
		vfm.idx.value=idx;
		vfm.submit();
	}
}
window.onload = function () {
	// active gnb
	onLoadLeftMenu('board_06');
}
</script>
<body>
<form class="form-inline" name="form01" action="/admin/board/album_list.do" method="POST">
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}" />
<input type="hidden" name="query_type" id="query_type" value="" />
<input type="hidden" name="idx" id="idx" value="${idx}"/>
        <!-- top start(left menu include) -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- top end -->
        
        <!-- main start -->        
        <!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h3 class="page-header">게시판관리 > 교구앨범목록</h3>
                	</div>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- //.row(page title end -->
            <!-- Contents : Begin //-->
				<div class="page-list">
					<!-- @@@ Search FieldSet : begin //-->
					<div class="search">
						<!--  Search : begin //-->
						<div class="well search-search">
							<table sytle="width:100%; padding: 5px;">
								<tr>
									<th>
										<label for="q_word">분류</label>
									</th>
									<th>
										<select class="form-control form-control-short w-200 mr-10" name="org_idx" id="org_idx">
										  <option value="">전체</option>	
										  	<c:choose>
												<c:when test="${fn:length(_1x00xList) > 0}">
													<c:forEach items="${_1x00xList}" var="list">	
														<option value="${list.ORG_IDX}">${list.NAME}</option>
													</c:forEach>
												</c:when>
											</c:choose>																							  
										</select>
									</th>
									<th>
										<select class="form-control form-control-short w-200 mr-10" name="c_idx" id="c_idx">
										  <option value="">전체</option>
										  <option value="1" <c:out value="${s_gubunMap['1']}"/>>미사/행사</option>
										  <option value="2" <c:out value="${s_gubunMap['2']}"/>>교육/사업</option>
										</select>
									</th>
									<th>
										<label for="q_word">검색</label>
									</th>
									<th>
										<select class="form-control form-control-short w-200 mr-10" name="schTextGubun" id="schTextGubun">
		                                  <option value="">선택</option>
										  <option value="title" <c:out value="${s_searchMap['title']}"/>>제목</option>
										  <option value="contents" <c:out value="${s_searchMap['contents']}"/>>내용</option>
										  <option value="all" <c:out value="${s_searchMap['all']}"/>>제목+내용</option>
										  <option value="writer" <c:out value="${s_searchMap['writer']}"/>>작성자</option>
										  
										</select>
									</th>
									<th>
										<input class="form-control form-control-short" type="text" name="schText" id="schText" value="${_params.schText}">
									</th>
									<th><div style="text-align: center">
										<div class="btn-group" ><button type="button" class="btn5 btn-primary" onclick="javascript: insertContents();"><i class="fa fa-pencil"></i>등록</button></div>
										<div class="btn-group" ><button type="button" class="btn5 btn-primary" onclick="javascript: goSearchAlbum0();"><i class="fa fa-search"></i>검색</button></div>
									</div>
									</th>									
								</tr>
							</table>
						</div>
						<!--  Search : end //-->
					</div>
					<!-- @@@ Search FieldSet : End //-->

                    <div class="panel panel-default">
                        <!-- /.panel-heading -->
                        <div class="panel-body">
							<table class="table table-striped table-bordered table-hover">
								<colgroup>
									<col>
									<col style="width: 15%;"><!-- 부서명 -->	
									<col><!-- 분류 -->
									<col style="width: 40%;"><!-- 제목 -->
									<col ><!-- 작성자 -->
									<col >
									<col >
									<col >
									<col >
								</colgroup>
                                <thead>
                                    <tr>
                                        <th>NO</th>
                                        <th>부서명</th>
                                        <th>분류</th>
                                        <th>제목</th>
                                        <th>작성자</th>
                                        <th>등록일</th>
                                        <th>조회수</th>
                                        <th>썸네일</th>
                                        <th>수정 / 삭제</th>
                                        <!-- <th>수정</th>
                                        <th>삭제</th> -->
                                    </tr>
                                </thead>
                                <tbody>
                                <c:choose>
									<c:when test="${fn:length(albList) > 0}">
									<c:forEach items="${albList}" var="list">
	                                <tr style="text-align: center; <c:if test="${list.IS_VIEW eq 'N'}">color: darkgray;</c:if>" >
	                                    <td >${list.RNUM}</td>
	                                    <td >${list.ORG_NAME}</td>
	                                    <td >${list.ALBUM_GUBUN_TEXT}</td>
	                                    <td style="text-align: left;">${list.TITLE}</td>
	                                    <td >${list.WRITER}</td>
	                                    <td >${list.REGDATE}</td>
	                                    <td >${list.HIT}</td>
	                                    <td ><img src="${list.FILEPATH}thumbnail/${list.STRFILENAME}" width=70></td>
	                                    <td > 
		                                    <div style="text-align: center;">
												<div class="btn-group"><button name="btnEdit" type="button" class="btn20 btn-success btn-circle" title="Edit" onClick="javascript: modifyContents ('${list.IDX}')"><i class="fa fa-pencil"></i></button></div>
												<div class="btn-group"><button name="btnDel" type="button" class="btn20 btn-danger btn-circle" title="Delete" onClick="javascript: deleteContents ('${list.IDX}')"><i class="fa fa-times"></i></button></div>
											</div>
	                                    </td>
	                                </tr> 
	                                </c:forEach>
									</c:when>
								</c:choose>
                                </tbody>
                            </table>
                            <!-- /.table-responsive -->
                            <div>
                                <!-- paging start -->
                                <%@ include file="/admin/_common/inc/paging2.jsp" %>
                    			<!-- //paging end -->
                            </div>
                        </div>
				<!-- Contents : End //-->

			</div>
			<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->
</form>
</body>

</html>
