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

function insertContents(id) {
	document.form01.action = '/admin/board/email_view.do';
	document.getElementById('query_type').value = "insert";
	document.getElementById('id').value=id;
	document.form01.submit();
    return false;
}

function modifyContents(email_no, id) {
	document.form01.action = '/admin/board/email_view.do';
	document.getElementById('query_type').value = "modify";
	document.getElementById('email_no').value=email_no;
	document.getElementById('id').value=id;
	document.form01.submit();
    return false;
}

function deleteContents(email_no, id) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/board/email_delete.do';
		document.getElementById('email_no').value=email_no;
		document.getElementById('id').value=id;
		document.form01.submit();
	}
    return false;
}

window.onload = function () {

	
}
</script>

<body>
<form class="form-inline" name="form01" action="/admin/board/email_list.do" method="POST">
<input type="hidden" name="query_type" id="query_type" value="" />
<input type="hidden" name="email_no" id="email_no" value="${email_no}"/>
<input type="hidden" name="id" id="id" value="${id}"/>
    <div id="wrapper">
        <!-- Navigation -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- Page Content -->
        
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">메일 보내기 목록</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">	
            <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">		  
						  <div class="form-group">						  	
						    <label for="sel1">검색:</label>
							<select name="searchGubun" id="searchGubun">
							  <option value="title" <c:out value="${schTextGubunMap['title']}"/>>제목</option>
							  <option value="name" <c:out value="${schTextGubunMap['name']}"/>>작성자</option>
							  <option value="contents" <c:out value="${schTextGubunMap['contents']}"/>>내용</option>
							</select>
							<input type="text" class="form-control" name="searchText" id="searchText" value="${searchText}">
							&nbsp;&nbsp;&nbsp;		
						    <button type="button" class="btn btn-default" onclick="javascript:goSearch();return false;">검색</button>
						    <button type="button" class="btn btn-default" onclick="javascript:insertContents();return false;">등록</button>
						  </div>
						</div>
            <!-- /.row -->
			            <div class="panel-body">
			              <table width="100%" class="table" id="dataTables-example">
						    <thead>
						      <tr>
						        <th>NO</th>
						        <th>메일제목</th>
						        <th>발송기간</th>
						        <th>작성자</th>
						        <th>현황</th>
						        <th>등록일</th>
						        <th>수정</th>
						        <th>삭제</th>			        
						      </tr>
						    </thead>
						    <tbody>
						      <c:choose>
									<c:when test="${fn:length(emailList) > 0}">
									<c:forEach items="${emailList}" var="list">
			                              <tr>
			                                  <td>${list.RNUM} </td>
			                                  <td>${list.TITLE} </td>
			                                  <c:choose>
			                                  	<c:when test="${list.EMAIL_TYPE == '2'}">
			                                  		<td>&nbsp; </td>
			                                  	</c:when>
			                                  	<c:when test="${list.EMAIL_TYPE == '1'}">
			                                  		<td>${list.SEND_DATE_FROM}~${list.SEND_DATE_TO} </td>
			                                  	</c:when>
			                                  	<c:otherwise>
			                                  		<td>&nbsp; </td>			                                  	
			                                  	</c:otherwise>
			                                  </c:choose>			                                  
			                                  <td>${list.NAME} </td>
			                                  <td>
			                                   	<c:choose>
											    <c:when test="${list.USE_YN == 'Y'}">
											        	사용
											    </c:when>
											    <c:otherwise>
											        	중지
											    </c:otherwise>
												</c:choose>
			                                   </td>	
											   <td>${list.REGISTDT}</td>
			                                   <td><a href="javascript:modifyContents('${list.EMAIL_NO}','${list.ID}')">수정</a></td>
			                                   <td><a href="javascript:deleteContents('${list.EMAIL_NO}','${list.ID}')">삭제</a></td>
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
