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
	vfm.pageNo.value = pageNo;
	vfm.q_type.value = "list";
	vfm.submit();
    return false;
}

function goSearch() {
	var vfm = document.form01;
 	if(vfm.schText.value != '') {
 		if(vfm.schTextGubun.value == '') {
 			alert('검색조건을 확인하세요.');
 			return false;
 		}
	} 

	vfm.q_type.value = "list";
	vfm.submit();
    return false;
}

function insertContents() {
	var vfm = document.form01;
	vfm.action = '/admin/board/oldAlb_view.do';
	vfm.q_type.value = "insert";
	vfm.submit();
    return false;
}

function modifyContents(bl_idx) {
	var vfm = document.form01;
	vfm.action = '/admin/board/oldAlb_view.do';
	vfm.q_type.value = "modify";
	vfm.bl_idx.value = bl_idx;
	vfm.submit();
    return false;
}

function deleteContents(bl_idx) {
	var vfm = document.form01;
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		vfm.action = '/admin/board/oldAlb_delete.do';
		vfm.q_type.value = "delete";
		vfm.bl_idx.value = bl_idx;
		vfm.submit();
	}
    return false;
}
window.onload = function () {
	// active gnb
	onLoadLeftMenu('board_05');
}
</script>
<body>
<form name="form01" action="/admin/board/oldAlb_list.do" method="POST">
<input type="hidden" name="q_type" id="q_type" value="${_params.q_type}" />
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}" />
<input type="hidden" name="bl_idx" id="bl_idx" value=""/>

        <!-- top start(left menu include) -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- top end -->
        
        <!-- main start -->        
        <!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h3 class="page-header">게시판관리 > 역대교구장 앨범목록</h3>
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
							<dl class="dl-horizontal">								
								<dt><label for="q_word">구분</label></dt>
								<dd>
									<div class="group">
										<select class="form-control form-control-short w-200 mr-10" name="c_idx" id="c_idx">
										  <option value="">전체</option>
										  <option value="1" <c:out value="${s_searchMap['1']}" /> >제1대 교구장</option>
										  <option value="2" <c:out value="${s_searchMap['2']}" /> >제2대 교구장</option>
										</select>
									</div>
								</dd>
								<dt><label for="q_word">검색조건</label></dt>
								<dd>
									<div class="row">
										<div class="group">
											<select class="form-control form-control-short w-200 mr-10" name="schTextGubun" id="schTextGubun">
			                                  <option value="">선택</option>
											  <option value="title" <c:out value="${s_searchMap['title']}"/>>제목</option>
											  <option value="content" <c:out value="${s_searchMap['content']}"/>>내용</option>
											</select>
			                                <input class="form-control form-control-short" type="text" name="schText" id="schText" value="${_params.schText}">
										</div>
										<div class="group">
										    <button type="button" class="btn btn-primary" onclick="javascript:insertContents();return false;"><i class="fa fa-pencil"></i>등록</button>
			                                <button type="button" class="btn btn-primary" onclick="javascript:goSearch();return false;"><i class="fa fa-search"></i>검색</button>											
										</div>
									</div>
								</dd>

							</dl>
						</div>
						<!--  Search : end //-->
					</div>
					<!-- @@@ Search FieldSet : End //-->

                    <div class="panel panel-default">
                        <!-- /.panel-heading -->
                        <div class="panel-body">
							<table class="table table-striped table-bordered table-hover">
								<colgroup>
									<col style="width:70px;">
									<col style="width:180px;">	
									<col>
									<col style="width:120px;">
									<col style="width:120px;">
									<col style="width:70px;">
									<col style="width:70px;">
									<col style="width:70px;">
								</colgroup>
                                <thead>
                                    <tr>
                                        <th>NO</th>
                                        <th>구분</th>
                                        <th>제목</th>
                                        <th>등록일</th>
                                        <th>썸네일</th>
                                        <th>조회수</th>
                                        <th>수정</th>
                                        <th>삭제</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:choose>
									<c:when test="${fn:length(LIST) > 0}">
									<c:forEach items="${LIST}" var="list">
	                                <tr <c:if test="${list.IS_VIEW eq 'N' }">style="color:darkgray; "</c:if> >
	                                    <td class="center">
	                                    <c:choose>
										    <c:when test="${list.IS_NOTICE == 'Y'}">
										        	<i class="ico brown">공지</i>
										    </c:when>
										    <c:otherwise>
										        	${list.RNUM}
										    </c:otherwise>
										</c:choose>
	                                    </td>
	                                    <td class="center"><strong>
	                                    <c:choose>
										    <c:when test="${list.C_IDX eq '1'}">
										        	1대 교구장
										    </c:when>
										    <c:otherwise>
										        	2대 교구장
										    </c:otherwise>
										</c:choose>
	                                    </strong></td>
	                                    <td>${list.TITLE}</td>
	                                    <td class="center">${list.REGDATE}</td>
	                                    <td class="center"><c:if test="${fn:length(list.STRFILENAME1) > 0 }"><a href="${list.FILEPATH1}${list.STRFILENAME1}" target="new"><img src="${list.FILEPATH1}thumbnail/${list.STRFILENAME1}" /></a></c:if> </td>
	                                    <td class="center">${list.HIT}</td>
	                                    <td class="center"><button name="btnEdit" type="button" class="btn40 btn-success btn-circle" title="Edit" onClick="javascript: modifyContents('${list.BL_IDX}')"><i class="fa fa-pencil"></i></button>
	                                    </td>
	                                    <td class="center"><button name="btnDel" type="button" class="btn40 btn-danger btn-circle" title="Delete" onClick="javascript: deleteContents('${list.BL_IDX}')"><i class="fa fa-times"></i></button>
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
