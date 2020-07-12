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
	document.form01.action = '/admin/board/msg_view.do';
	document.getElementById('query_type').value = "insert";
	document.form01.submit();
    return false;
}

function modifyContents(m_idx) {
	//alert("modifyContents");
	document.form01.action = '/admin/board/msg_view.do';
	document.getElementById('query_type').value = "modify";
	document.getElementById('m_idx').value = m_idx;
	document.form01.submit();
    return false;
}

function deleteContents(m_idx) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/board/msg_delete.do';
		document.getElementById('m_idx').value = m_idx;
		document.form01.submit();
	}
    return false;
}

function selectType() {
	var value = document.getElementById('s_type').value;
	document.getElementById('type').value = value;
}

function selectSearch() {
	var value = document.getElementById('s_search').value;
}

window.onload = function () {
	//alert("로딩 완료");
//	document.getElementById("delete_n").checked = true;
}

</script>

<body>
<form class="form-inline" name="form01" action="/admin/board/msg_list.do" method="POST">
<input type="hidden" name="query_type" id="query_type" value="" />
<input type="hidden" name="m_idx" id="m_idx" value=""/>
<input type="hidden" name="type" id="type" value=""/>

    <div id="wrapper">
        <!-- Navigation -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- Page Content -->
        
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">메시지 목록</h3>
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
								    <th><select name="s_type" id="s_type">
								    	  <option value="all" selected>전체</option>
										  <option value="1">교서</option>
										  <option value="2">서한</option>
										  <option value="3">담화문</option>
										</select>
									</th>
									<th width="50"><label>검색</label></th>
								    <th><select name="s_search" id="s_search">
								    	  <option value="title" selected>제목+부제목</option>
										  <option value="contents">내용</option>
										  <option value="all">제목+부제목+내용</option>
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
						      <tr>
						        <th>NO</th>
						        <th>구분</th>
						        <th>제목</th>
						        <th>부제목</th>
						        <th>서한날짜</th>
						        <th>수정</th>
						        <th>삭제</th>			        
						      </tr>
						    </thead>
						    <tbody>
						      <c:choose>
									<c:when test="${fn:length(msgList) > 0}">
									<c:forEach items="${msgList}" var="list">
			                              <tr  <c:if test="${list.IS_VIEW eq 'N'}">style="color: darkgray;"</c:if> >
			                                  <td>${list.RNUM} </td>
			                                  <td>${list.TYPE_TEXT} </td>
			                                  <td>${list.TITLE} </td>
			                                  <td>${list.SUB_TITLE} </td>
			                                  <td>${list.MDATE} </td>			                                  
			                                  <td><a href="javascript:modifyContents('${list.M_IDX}')">수정</a></td>
			                                  <td><a href="javascript:deleteContents('${list.M_IDX}')">삭제</a></td>
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
