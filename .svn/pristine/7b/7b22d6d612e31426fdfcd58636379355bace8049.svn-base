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
	//alert("goSearch()");
	
	//if(document.getElementById('searchText').value == '') {
	//	alert('검색할 문자를 입력하세요.');
	//	document.getElementById('searchText').focus();
	//	return false;
	//}
	document.form01.action = '/admin/board/marry_list.do';
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
function insertContents(id) {
	document.form01.action = '/admin/board/marry_view.do';
	document.getElementById('query_type').value = "insert";
	document.getElementById('id').value=id;
	document.form01.submit();
    return false;
}

function modifyContents(marry_no, id) {
	//alert("modifyContents");
	document.form01.action = '/admin/board/marry_view.do';
	document.getElementById('query_type').value = "modify";
	document.getElementById('marry_no').value=marry_no;
	document.getElementById('id').value=id;
	document.form01.submit();
    return false;
}

function deleteContents(marry_no, id) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인	
		document.form01.action = '/admin/board/marry_delete.do';
		document.getElementById('marry_no').value=marry_no;
		document.getElementById('id').value=id;
		document.form01.submit();
	}
    return false;
}

function goSearchGubunInit() {
	/* document.getElementById('pageType').value='admin';
	
	document.getElementById('searchGubun1').value='1';
	document.getElementById('searchGubun2').value='1';
	document.getElementById('searchGubun3').value='1'; */
}

function selectGubun1() {
	
}

function selectGubun2() {
	
}

function selectGubun3() {
	
}

// 카나혼인강좌
function goMarry() {
	var vfm = document.form02;//document.form01;
	//vfm.action = '/admin/board/marry_guide.do';
	vfm.action = '/admin/board/marryguide_list.do';
	vfm.type.value="1";
	vfm.submit();
    return false;
}
// 주말약혼자강좌
function goEngage() {
	var vfm = document.form02;//document.form01;
	//vfm.action = '/admin/board/engage_guide.do';
	vfm.action = '/admin/board/marryguide_list.do';
	vfm.type.value="2";
	vfm.submit();
    return false;
}

window.onload = function () {
	//alert("로딩 완료");
	//selectChange();
	var today = new Date();
	
	goSearchGubunInit();
	// active gnb
	onLoadLeftMenu('order_02');
}
</script>

<body>
<form class="form-group" name="form02" action="/admin/board/marryguide_list.do" method="POST">
<input type="hidden" name="type" id="type" value="" />
</form>
<form class="form-group" name="form01" action="/admin/board/marry_list.do" method="POST">
<input type="hidden" name="pageNo" id="pageNo" value="" />
<input type="hidden" name="query_type" id="query_type" value="" />
<input type="hidden" name="marry_no" id="marry_no" value="${marry_no}"/>
<input type="hidden" name="id" id="id" value="${admSessionMemId}"/>
<input type="hidden" name="page_type" id="page_type" value="admin"/>
<input type="hidden" name="target" id="target" value="marry"/>
     	<!-- top start(left menu include) -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- top end -->
        
        <!-- main start -->        
        <!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
                    <h3 class="page-header">카나혼인강좌,약혼자주말 신청관리</h3>
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
							<colgroup>
								<col style="width:120px;">
								<col>
								<col style="width:120px;">
								<col>
								<col>
							</colgroup>
								<tr>
		    						<th><label for="inputName">날짜검색 &nbsp; </label></th>
		    						<td colspan="2">
		    							<div class="hero-unit" id="mypick" data-date="${today}" data-date-format="yyyy-MM-dd" data-link-field="searchDate">
		    							<span>
		    							<select name="searchGubun1" id="searchGubun1" onChange="javascript:selectGubun1()">
		    								<option value="2" <c:if test="${search_gubun1=='2'}">selected="true"</c:if> >혼인예정일</option>
		    								<option value="1" <c:if test="${search_gubun1=='1'}">selected="true"</c:if> >강좌신청날짜</option>
										</select>
										</span>
	    								<span><input class="form-control" type="date" data-date-format="YYYY-MM-DD" id="searchDate" name="searchDate" size="10" value="${search_date}" 	></span>
	    								<span><button type="button" id="reset-date">Reset</button></span>
		      							</div>
		    						</td>
		    						<td colspan="2">
		    						<button type="button" class="btn btn-default" onclick="javascript: goMarry();">카나혼인강좌<br> 교육안내수정</button>
	    							&nbsp;<button type="button" class="btn btn-default" onclick="javascript: goEngage();return false;">약혼자주말<br> 교육안내수정</button>
	    							&nbsp;<button type="button" class="btn btn-default" onclick="javascript: downloadExcel();return false;">엑셀다운로드</button>
		    						</td>
	    						</tr>   
	    						<tr>
	    							<th><label for="inputName">구분&nbsp;</label></th>
	    							<td>
	    								<select name="searchGubun2" id="searchGubun2" onChange="javascript:selectGubun2()">
	    								<option value=""  <c:if test="${search_gubun2==''}">selected="true"</c:if> >전체</option>
										<option value="1" <c:if test="${search_gubun2=='1'}">selected="true"</c:if> >카나혼인강좌</option>
										<option value="2" <c:if test="${search_gubun2=='2'}">selected="true"</c:if> >약혼자주말</option>
										</select>
									</td>
	    							<th><label for="inputName">검색&nbsp;</label></th>
	    							<td>
	    								<div class="row">
	    								<select name="searchGubun3" id="searchGubun3" onChange="javascript:selectGubun3()">
	    									<option value="1" <c:if test="${search_gubun3=='1'}">selected="true"</c:if> >이름</option>
	    									<option value="2" <c:if test="${search_gubun3=='2'}">selected="true"</c:if> >세례명</option>
										</select>
										<input type="text" class="form-control" name="searchText" id="searchText" value="${search_text}">
										</div>
									</td>
	    							<td><button type="button" class="btn btn-default" onclick="javascript:goSearch();return false;">검색</button>
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
						        <th>구분</th>
						        <th>강좌신청날짜</th>
						        <th>혼인예정일</th>
						        <th>성명(남)</th>
						        <th>세례명</th>
						        <th>성명(여)</th>
						        <th>세례명</th>
						        <th>신청일</th>			        
						        <th>승인여부</th>
						        <th>수정</th>
						        <th>삭제</th>
						        <!-- <th>엑셀</th> -->
						      </tr>
						    </thead>
						    <tbody>
						      <c:choose>
									<c:when test="${fn:length(marryList) > 0}">
									<c:forEach items="${marryList}" var="list">
			                              <tr>
			                                  <td>${list.RNUM} </td>
			                                  <td>${list.APPLY_TYPE_TEXT} </td>
			                                  <td>${fn:substring(list.LECTURE_APPLY_DAY, 0, 10)} </td>
			                                  <td>${list.MARRY_DAY} </td>
			                                  <td>${list.MAN_NAME} </td>
			                                  <td>${list.MAN_BAPTISMAL_NAME} </td>
			                                  <td>${list.WOMAN_NAME} </td>
			                                  <td>${list.WOMAN_BAPTISMAL_NAME} </td>
			                                  <td>${list.APPLY_DAY} </td>
			                                  <td>${list.PROCESS_STATUS_TEXT} </td>			                                                                  
		                                   	  <td><a href="javascript:modifyContents('${list.MARRY_NO}','${list.ID}')">수정</a></td>
			                                  <td><a href="javascript:deleteContents('${list.MARRY_NO}','${list.ID}')">삭제</a></td>
			                                  <%-- <td><a href="javascript:excelDownload('${list.MARRY_NO}','${list.ID}')">엑셀</a></td> --%>
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
