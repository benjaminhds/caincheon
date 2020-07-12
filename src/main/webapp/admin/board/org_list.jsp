<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/admin/_common/inc/head.jsp" %>
<head>
</head>

<script type="text/javascript">
function goPage(pageNo) {
	document.getElementById('pageNo').value=pageNo;
	document.form01.submit();
    return false;
}

function goSearch() {
	//alert("goSearch2()");
	if(document.getElementById('searchText').value == '') {
		//alert('검색할 문자를 입력하세요.');
	}
	document.form01.submit();
    return false;
}

function insertContents(o_idx) {
	document.form01.action = '/admin/board/org_view.do';
	document.getElementById('query_type').value = "insert";
	document.getElementById('o_idx').value=o_idx;
	document.form01.submit();
    return false;
}

function modifyContents(o_idx) {
	//alert("modifyContents");
	document.form01.action = '/admin/board/org_view.do';
	document.getElementById('query_type').value = "modify";
	document.getElementById('o_idx').value=o_idx;
	document.form01.submit();
    return false;
}

function deleteContents(o_idx) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/board/org_delete.do';
		document.getElementById('o_idx').value=o_idx;
		document.form01.submit();
	}
    return false;
}

function selectSearch() {
}

window.onload = function () {
	onLoadLeftMenu('info_05');
}
</script>

<body>
<form class="form-group" name="form01" action="/admin/board/org_list.do" method="POST">
<input type="hidden" name="query_type" id="query_type" value=""/>
<input type="hidden" name="pageNo" id="pageNo" value=""/>
<input type="hidden" name="o_idx" id="o_idx" value=""/>
    <!-- top start(left menu include) -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- top end -->
        
        <!-- main start -->
        <!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h3 class="page-header">수도회/기관/단체 목록</h3>
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
		    					<table>		
		    					<tr>		
									<td>
										<label for="q_word">검색 </label>
									</td>	
									<td>
										<select class="form-control form-control-short w-200 mr-10" name="searchGubun" id="searchGubun" onChange="javascript:selectSearch()">
											<option value="1" <c:if test="${searchGubun eq '1'}">selected="true"</c:if> >단체명</option>
											<option value="2" <c:if test="${searchGubun eq '2'}">selected="true"</c:if> >구분</option>
										</select>
									</td>									
									<td>
										<input class="form-control form-control-short" type="text" name="searchText" id="searchText" value="${searchText}">
									</td>	    						
									<td>
		    							<!-- <button type="button" class="btn btn-default" onclick="javascript:insertContents();return false;">등록</button> -->
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
							<table class="table table-striped table-bordered table-hover">
						    <thead>
						      <tr>
						        <th>NO</th>
						        <th>구분</th>
						        <th>단체명</th>
						        <th>홈페이지</th>
						        <th>사제명</th>
						        <th>수정</th>
						        <th>삭제</th>			        
						      </tr>
						    </thead>
						    <tbody>
						      <c:choose>
								<c:when test="${fn:length(orgList) > 0}">
								<c:forEach items="${orgList}" var="list">
		                              <tr style="<c:if test="${list.DEL_YN eq 'Y'}">color: darkgray; text-decoration:line-through;</c:if><c:if test="${list.DISPLAY_YN ne 'Y' }">; color: darkgray; </c:if>" >
		                                  <td>${list.RNUM} </td>
		                                  <td>${list.ORG_LV1_NAME} </td>
		                                  <td>${list.NAME}</td>
		                                  <td><a href="${list.HOMEPAGE}" target="new">${list.HOMEPAGE}</a></td>
		                                  <td>${list.P_NAME }</td>			                                  
		                                  <td>
		                                  <c:choose>
		                                  <c:when test="${list.MODE=='I'}">
				                            <a href="javascript: insertContents('${list.O_IDX}')">등록</a>
										</c:when>
										<c:otherwise>
				                            <a href="javascript: modifyContents('${list.O_IDX}')">수정</a>
										</c:otherwise>
										</c:choose>
		                                  </td>
		                                  <td>
		                                  <c:choose>
										<c:when test="${list.MODE=='I'}">
				                            -
										</c:when>
										<c:otherwise>
											<c:if test="${list.DEL_YN eq 'Y'}">
												<a href='/admin/org/org_code_mgmt.do'><font style="color:#a8a8a8" title="임지코드 관리에서 관리합니다.">복원</font></a>
											</c:if>
											<c:if test="${list.DEL_YN eq 'N'}">
												<a href="javascript: deleteContents('${list.O_IDX}')">삭제</a>
											</c:if>
				                        </c:otherwise>
										</c:choose>			                                  
		                                  </td>
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

</html>
