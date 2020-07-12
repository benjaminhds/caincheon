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
	if(document.getElementById('searchText').value == '') {
		//alert('검색할 문자를 입력하세요.');
		//document.getElementById('searchText').focus();
		//return false;
	}
	document.form01.submit();
    return false;
}

function insertContents() {
	document.form01.action = '/admin/board/sch_view.do';
	document.getElementById('query_type').value = "insert";
	document.form01.submit();
    return false;
}

function modifyContents(s_idx) {
	//alert("modifyContents");
	document.form01.action = '/admin/board/sch_view.do';
	document.getElementById('query_type').value = "modify";
	document.getElementById('s_idx').value = s_idx;
	document.form01.submit();
    return false;
}

function deleteContents(s_idx) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/board/sch_delete.do';
		document.getElementById('s_idx').value = s_idx;
		document.form01.submit();
	}
    return false;
}

function selectOrg() {
	var value = document.getElementById('s_org').value;
	document.getElementById('gubun').value = value;
}

function selectSearch() {
	var value = document.getElementById('s_search').value;
	document.getElementById('s_search').value = value;
}

window.onload = function () {
	//alert("로딩 완료");
//	document.getElementById("delete_n").checked = true;
}

</script>

<body>
<form class="form-inline" name="form01" action="/admin/board/sch_list.do" method="POST">
<input type="hidden" name="pageNo" id="pageNo" value="" />
<input type="hidden" name="query_type" id="query_type" value="" />
<input type="hidden" name="s_idx" id="s_idx" value=""/>
<input type="hidden" name="gubun" id="gubun" value=""/>
<input type="hidden" name="list_type" id="list_type" value="list"/>

    <div id="wrapper">
        <!-- Navigation -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- Page Content -->
        
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">교구장/총대리/교구 일정 목록</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">	
            <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">		  
						  <table style="width:100%" border="0">
						  		<tr>
								    <th width="50"><label>구분</label></th>
								    <th><select name="s_org" id="s_org">
								    	  <option value="1" selected>전체</option>
										  <option value="2">교구장</option>
										  <option value="3">총대리</option>
										  <option value="4">교구</option>
										  <option value="5">부서</option>
										</select>
									</th>
									<th width="50"><label>검색</label></th>
								    <th><select name="s_search" id="s_search">
								    	  <option value="title" selected>제목</option>
										  <option value="contents">내용</option>
										  <option value="all">제목+내용</option>
										</select>
									</th>
									<th>
										<input type="text" name="searchText" id="searchText" value="">
									</th>
									<th>
										<button type="button" onclick="javascript:goSearch();return false;">검색</button>
									</th>
								</tr>
							    <tr>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td><button type="button" onclick="javascript:insertContents();return false;">등록</button>
							    	</td>
							    </tr>
						  	</table>
						</div>
            <!-- /.row -->
			            <div class="panel-body">
			              <table width="100%" class="table" id="dataTables-example">
						    <thead>
						      <tr style="text-align:center">
						        <th>NO</th>
						        <th>행사명</th>
						        <th>교구장/총대리</th>
						        <th>주관부서</th>
						        <th>시작일</th>
						        <th>종료일</th>
						        <th>수정</th>
						        <th>삭제</th>			        
						      </tr>
						    </thead>
						    <tbody>
						      <c:choose>
									<c:when test="${fn:length(schList) > 0}">
									<c:forEach items="${schList}" var="list">
			                              <tr <c:if test="${list.DISPLAYYN ne 'Y' }">style="color:darkgray"</c:if> >
			                                  <td>${list.RNUM} </td>
			                                  <td>${list.TITLE} </td>
			                                  <td>${list.GUBUN} </td>
			                                  <td>${list.ORG_NAME} </td>
			                                  <td>${list.START_DATE} </td>
			                                  <td>${list.END_DATE} </td>			                                  
			                                  <td><a href="javascript:modifyContents('${list.S_IDX}')">수정</a></td>
			                                  <td><a href="javascript:deleteContents('${list.S_IDX}')">삭제</a></td>
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
</form>
</body>

</html>
