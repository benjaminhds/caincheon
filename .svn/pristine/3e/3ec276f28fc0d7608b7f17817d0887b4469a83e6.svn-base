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
	//alert("goSearch2()");
/* 	if(document.getElementById('searchText').value == '') {
		alert('검색할 문자를 입력하세요.');
		document.getElementById('searchText').focus();
		return false;
	} */
	document.form01.submit();
    return false;
}

function downloadExcel() {
	///excelDownload?target=books&id=b1
	document.form01.action = '/admin/excelDownload.do';
	form = document.form01;
	form.target = "hiddenifr";
	form.submit();
    return false;
}

function insertContents() {
	document.form01.action = '/admin/board/doctrine_view.do';
	document.getElementById('query_type').value = "insert";
	document.form01.submit();
    return false;
}

function modifyContents(doctrine_no, id) {
	document.form01.action = '/admin/board/doctrine_view.do';
	document.getElementById('query_type').value = "modify";
	document.getElementById('doctrine_no').value=doctrine_no;
	document.getElementById('id').value=id;
	document.form01.submit();
    return false;
}

function deleteContents(doctrine_no, id) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/board/doctrine_delete.do';
		document.getElementById('doctrine_no').value=doctrine_no;
		document.getElementById('id').value=id;
		document.form01.submit();
	}
    return false;
}

window.onload = function () {
	// active gnb
	onLoadLeftMenu('order_01');
}
</script>
<body>
<form class="form-inline" name="form01" action="/admin/board/doctrine_list.do" method="POST">
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}" />
<input type="hidden" name="query_type" id="query_type" value="" />
<input type="hidden" name="doctrine_no" id="doctrine_no" value="${doctrine_no}"/>
<input type="hidden" name="id" id="id" value=""/>
<input type="hidden" name="target" id="target" value="doctrine"/>
     	<!-- top start(left menu include) -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- top end -->
        
        <!-- main start -->        
        <!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
                    <h3 class="page-header">통신교리 신청관리</h3>
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
							<table width="100%">
								<tr>
								<th><label for="sel1">구분</label></th>
								<td>
									<select name="searchGubun1" id="searchGubun1">
									  <option value="">전체</option>
									  <option value="1" <c:out value="${schTextGubunMap1['1']}"/>>예비신자</option>
									  <option value="2" <c:out value="${schTextGubunMap1['2']}"/>>재교육자</option>
									</select>
								</td>
								<th><label for="sel1">검색</label></th>
								<td>
									<select name="searchGubun2" id="searchGubun2">
									  <option value="name" <c:out value="${schTextGubunMap2['name']}"/>>이름</option>
									  <option value="baptismal_name" <c:out value="${schTextGubunMap2['baptismal_name']}"/>>세례명</option>
									</select>
									<input type="text" class="form-control" name="searchText" id="searchText" value="${searchText}">
								</td>
								<td>
									<div class="group">
									<button type="button" class="btn btn-default" onclick="javascript:goSearch();return false;">검색</button>
								    <button type="button" class="btn btn-default" onclick="javascript:insertContents();return false;">등록</button>
								    <button type="button" class="btn btn-primary" onclick="javascript:downloadExcel();return false;">엑셀다운로드</button>
									</div>
								</td>
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
						    <thead>
						      <tr>
						        <th>NO</th>
						        <th>신자구분</th>
						        <th>이름</th>
						        <th>세례명</th>
						        <th>소속본당</th>
						        <th>신청일</th>
						        <th>승인여부</th>
						        <th>수정</th>
						        <th>삭제</th>			        
						      </tr>
						    </thead>
						    <tbody>
						      <c:choose>
									<c:when test="${fn:length(doctrineList) > 0}">
									<c:forEach items="${doctrineList}" var="list">
			                              <tr style="text-align: center">
			                                  <td>${list.RNUM} </td>
			                                  <td>${list.MEMBER_TYPE_TEXT} </td>
			                                  <td>${list.NAME} </td>
			                                  <td>${list.BAPTISMAL_NAME} </td>
			                                  <td>${list.CHURCH_NAME}</td>
			                                  <td>${list.APPLY_DAY}</td>
			                                  <td>${list.APPROVE_YN_TEXT}</td>			                                  
		                                   	  <td><a href="javascript:modifyContents('${list.DOCTRINE_NO}','${list.ID}')">수정</a></td>
			                                  <td><a href="javascript:deleteContents('${list.DOCTRINE_NO}','${list.ID}')">삭제</a></td>
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
<iframe id="hidden" name="hiddenifr" cellspacing="0" style="width: 0px; height: 0px; border=0" src=""></iframe>
</html>
