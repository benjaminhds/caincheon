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
	document.getElementById('pageNo').value=pageNo;
	document.form01.submit();
    return false;
}

function goSearch() {
 	if(document.getElementById('searchText').value != '') {
 		if(document.getElementById('searchGubun2').value == '') {
 			alert('검색조건을 확인하세요.');
 			return false;
 		}
	} 
	document.form01.submit();
    return false;
}

function insertContents() {
	document.form01.action = '/admin/gyogu/gyoguMov_view.do';
	document.getElementById('query_type').value = "insert";
	document.form01.submit();
    return false;
}

function modifyContents(b_idx, bl_idx) {
	document.form01.action = '/admin/gyogu/gyoguMov_view.do';
	document.getElementById('query_type').value = "modify";
	document.getElementById('b_idx').value=b_idx;
	document.getElementById('bl_idx').value=bl_idx;
	document.form01.submit();
    return false;
}

function deleteContents(b_idx, bl_idx) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/gyogu/gyoguMov_delete.do';
		document.getElementById('b_idx').value=b_idx;
		document.getElementById('bl_idx').value=bl_idx;
		document.form01.submit();
	}
    return false;
}
window.onload = function () {
	// active gnb
	onLoadLeftMenu('board_08');
}
</script>
<body>
<form class="form-inline" name="form01" action="/admin/gyogu/gyoguMov_list.do" method="POST">
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}" />
<input type="hidden" name="query_type" id="query_type" value="" />
<input type="hidden" name="b_idx" id="b_idx" value="${b_idx}"/>
<input type="hidden" name="bl_idx" id="bl_idx" value="${bl_idx}"/>
        <!-- top start(left menu include) -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- top end -->
        
        <!-- main start -->        
        <!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h3 class="page-header">교구영상 목록</h3>
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
								<dt><label for="q_word">분류</label></dt>
								<dd>
									<div class="group">
										<select class="form-control form-control-short w-200 mr-10" name="searchGubun1" id="searchGubun1">
										  <option value="">전체</option>
										  <option value="71" <c:out value="${schTextGubunMap1['71']}"/>>소식</option>
										  <option value="72" <c:out value="${schTextGubunMap1['72']}"/>>강좌</option>
										</select>
									</div>
								</dd>
								<dt><label for="q_word">검색조건</label></dt>
								<dd>
									<div class="row">
										<div class="group">
											<select class="form-control form-control-short w-200 mr-10" name="searchGubun2" id="searchGubun2">
			                                  <option value="">선택</option>
											  <option value="title" <c:out value="${schTextGubunMap2['title']}"/>>제목</option>
											  <option value="content" <c:out value="${schTextGubunMap2['content']}"/>>내용</option>
											  <option value="all" <c:out value="${schTextGubunMap2['all']}"/>>제목+내용</option>
											  <option value="writer" <c:out value="${schTextGubunMap2['writer']}"/>>작성자</option>
											</select>
			                                <input class="form-control form-control-short" type="text" name="searchText" id="searchText" value="${_params.searchText}">
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
									<col style="width:80px;">	
									<col>
									<col style="width:100px;">
									<col style="width:90px;">
									<col style="width:70px;">
									<col style="width:60px;">
									<col style="width:60px;">
								</colgroup>
                                <thead>
                                    <tr>
                                        <th>NO</th>
                                        <th>분류</th>
                                        <th>제목</th>
                                        <th>등록일</th>
                                        <th>공개여부</th>
                                        <th>조회수</th>
                                        <th>수정</th>
                                        <th>삭제</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:choose>
									<c:when test="${fn:length(LIST) > 0}">
									<c:forEach items="${LIST}" var="list">
	                                <tr <c:if test="${list.IS_VIEW eq 'N'}">style="color: darkgray;"</c:if> >
	                                    <td class="center">${list.RNUM}</td>
	                                    <td class="center">${list.CATEGORY_NAME}</td>
	                                    <td>${list.TITLE}</td>
	                                    <td class="center">${list.REGDATE}</td>
	                                    <td class="center">${list.IS_VIEW}</td>
	                                    <td class="center">${list.HIT}</td>
	                                    <td class="center"><button name="btnEdit" type="button" class="btn40 btn-success btn-circle" title="Edit" onClick="javascript:modifyContents('${list.B_IDX}','${list.BL_IDX}')"><i class="fa fa-pencil"></i></button>
	                                    </td>
	                                    <td class="center"><button name="btnDel" type="button" class="btn40 btn-danger btn-circle" title="Delete" onClick="javascript:deleteContents('${list.B_IDX}','${list.BL_IDX}')"><i class="fa fa-times"></i></button>
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
