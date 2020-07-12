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
	}
 */
 	document.form01.submit();
    return false;
}

function insertContents() {
	document.form01.action = '/admin/board/priest_view.do';
	document.getElementById('query_type').value = "insert";
	document.form01.submit();
    return false;
}

function modifyContents(p_idx) {
	//alert("modifyContents");
	document.form01.action = '/admin/board/priest_view.do';
	document.getElementById('query_type').value = "modify";
	document.getElementById('p_idx').value=p_idx;
	document.form01.submit();
    return false;
}

function deleteContents(p_idx) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/board/priest_delete.do';
		document.getElementById('p_idx').value=p_idx;
		document.form01.submit();
	}
    return false;
}

function selectSort() {
	document.form01.action = '/admin/board/priest_list.do';
	document.form01.submit();
}

window.onload = function () {
	//alert("로딩 완료");
}
</script>

<body>
<form class="form-group" name="form01" action="/admin/board/priest_list.do" method="POST">
<input type="hidden" name="pageNo" id="pageNo" value=""/>
<input type="hidden" name="query_type" id="query_type" value=""/>
<input type="hidden" name="p_idx" id="p_idx" value=""/>
     <!-- top start(left menu include) -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- top end -->
        
        <!-- main start -->        
        <!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h3 class="page-header">사제목록</h3>
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
		    					<table width="100%" border="0">		
		    					<tr>
									<td>
										<label for="q_word">정렬 &nbsp; </label>
									</td>	
									<td>
										<select name="sortGubun" id="sortGubun" onChange="javascript:selectSort()">
    										<option value="1" <c:if test="${sortGubun=='1'}">selected="true" </c:if>>기본</option>
											<option value="2" <c:if test="${sortGubun=='2'}">selected="true" </c:if>>서품일</option>
											<option value="3" <c:if test="${sortGubun=='3'}">selected="true" </c:if>>축일</option>
											<option value="4" <c:if test="${sortGubun=='4'}">selected="true" </c:if>>현임지</option>
											<option value="5" <c:if test="${sortGubun=='5'}">selected="true" </c:if>>이름</option>
											<option value="6" <c:if test="${sortGubun=='6'}">selected="true" </c:if>>세례명</option>
										</select>
									</td>
									
									<td style="text-align: right;">
										<label for="inputName">검색 &nbsp; </label>
									</td>	
									<td>
										<select name="searchGubun" id="searchGubun" onChange="javascript:selectSearch()">
											<option value="1" <c:if test="${searchGubun=='1'}">selected="true"</c:if> >이름</option>
											<option value="2" <c:if test="${searchGubun=='2'}">selected="true"</c:if> >세례명</option>
											<option value="3" <c:if test="${searchGubun=='3'}">selected="true"</c:if> >임지</option>
											<option value="4" <c:if test="${searchGubun=='4'}">selected="true"</c:if> >호칭</option>
										</select>
									</td>
									
									<td>
										<input type="text" class="form-control" name="searchText" id="searchText" value="${searchText}">
									</td>	    						
									<td>
		    							<button type="button" class="btn btn-default" onclick="javascript:insertContents();return false;">등록</button>
										<button type="button" class="btn btn-default" onclick="javascript:goSearch();return false;">검색</button>
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
			              <table width="100%" class="table" id="dataTables-example">
						    <thead>
						      <tr>
						        <th>NO</th>
						        <th>사제코드</th>
						        <th>호칭</th>
						        <th>소속</th>
						        <th>원로</th>
						        <th>사제명</th>
						        <th>세례명</th>
						        <th>서품</th>
						        <th>축일</th>
						        <th>수정</th>
						        <th>삭제</th>			        
						      </tr>
						    </thead>
						    <tbody>
						      <c:choose>
									<c:when test="${fn:length(admPriestList) > 0}">
									<c:forEach items="${admPriestList}" var="list">
			                              <tr>
			                                  <td>${list.RNUM} </td>
			                                  <td>${list.P_IDX} </td>
			                                  <td>${list.APPELLATION_NAME} </td>
			                                  <td>${list.GUBUN_TEXT} </td>
			                                  <td>${list.WON_YN}</td>
			                                  <td>${list.NAME} </td>			                                  
			                                  <td>${list.CHRISTIAN_NAME} </td>
			                                  <td>${list.P_BIRTHDAY} </td>
			                                  <td>${list.NEW_BIRTHDAY} </td>
			                                  <td><a href="javascript:modifyContents('${list.P_IDX}')">수정</a></td>
			                                  <td><a href="javascript:deleteContents('${list.P_IDX}')">삭제</a></td>
			                              </tr>
			                              </c:forEach>
									</c:when>
								</c:choose>		      
						    </tbody>
						  </table>			
						  <div>
			                  <!-- arrow -->
			                  <%@ include file="/_common/inc/paging2.jsp" %>
			      			<!-- //arrow -->                                
			              </div>  
						</div>	
						<!-- /.panel-body -->						
				</div>
				<!-- panel panel-default -->
			</div>
			<!-- col-lg-12 -->
		</div>						
			<!-- row -->
			
        </div>
        <!-- /#page-wrapper -->

    </div>
    
    <!-- /#wrapper -->
</form>
</body>
<%@ include file="/admin/_common/inc/footer.jsp" %>
</html>
