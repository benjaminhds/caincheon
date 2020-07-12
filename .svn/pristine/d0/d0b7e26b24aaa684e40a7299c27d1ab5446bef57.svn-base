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
	if(document.getElementById('searchText').value == '') {
		//alert('검색할 문자를 입력하세요.');
		//document.getElementById('searchText').focus();
		//return false;
	}
	document.form01.submit();
    return false;
}

function insertContents() {
	document.form01.action = '/admin/board/dgroup_view.do';
	document.getElementById('query_type').value = "insert";
	document.form01.submit();
    return false;
}

function modifyContents(gigu_code) {
	//alert("modifyContents");
	document.form01.action = '/admin/board/dgroup_view.do';
	document.getElementById('query_type').value = "modify";
	document.getElementById('gigu_code').value = gigu_code;
	document.form01.submit();
    return false;
}

function deleteContents(gigu_code) {
	//alert("deleteContents");
	document.form01.action = '/admin/board/dgroup_delete.do';
	document.getElementById('gigu_code').value=gigu_code;
	document.form01.submit();
    return false;
}

window.onload = function () {
	//alert("로딩 완료");
//	document.getElementById("delete_n").checked = true;
	
}
</script>

<body>
<form class="form-inline" name="form01" action="/admin/board/dgroup_list.do" method="POST">
<input type="hidden" name="query_type" id="query_type" value="" />
<input type="hidden" name="gigu_code" id="gigu_code" value=""/>
    <div id="wrapper">
        <!-- Navigation -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- Page Content -->
        
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">지구코드관리</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">	
            <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">		  
						  <div class="form-group">						  	
						    <label for="sel1">지구명</label>
							<input type="text" class="form-control" name="searchText" id="searchText" value="${searchText}">
							&nbsp;&nbsp;&nbsp;		
						    <button type="button" class="btn btn-default" onclick="javascript:goSearch();return false;">검색</button>
						    <button type="button" class="btn btn-default" onclick="javascript:insertContents();return false;">추가</button>
						  </div>
						</div>
            <!-- /.row -->
			            <div class="panel-body">
			              <table width="100%" class="table" id="dataTables-example">
						    <thead>
						      <tr>
						        <th>NO</th>
						        <th>소속지구명</th>
						        <th>수정</th>
						        <th>삭제</th>			        
						      </tr>
						    </thead>
						    <tbody>
						      <c:choose>
									<c:when test="${fn:length(dgroupList) > 0}">
									<c:forEach items="${dgroupList}" var="list">
			                              <tr>
			                                  <td>${list.RNUM} </td>
			                                  <td>${list.d_group_name} </td>			                                  
			                                   <td><a href="javascript:modifyContents('${list.gigu_code}')">수정</a></td>
			                                   <td><a href="javascript:deleteContents('${list.gigu_code}')">삭제</a></td>
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
