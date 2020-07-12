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

function goSearch1() {
	document.form01.submit();
    return false;
}

function goSearch2() {
	//alert("goSearch2()");
	/*if(document.getElementById('searchText').value == '') {
		alert('검색할 문자를 입력하세요.');
		document.getElementById('searchText').focus();
		return false;
	}*/
	document.form01.submit();
    return false;
}

function modifyContents(inquire_no, id) {
	//alert("modifyContents");
	document.form01.action = '/admin/board/inquire_view.do';
	document.getElementById('inquire_no').value=inquire_no;
	document.getElementById('user_id').value=id;
	document.form01.submit();
    return false;
}

function deleteContents(inquire_no, id, deletedFlag) {
	//alert("deleteContents");
	document.form01.action = '/admin/board/inquire_delete.do';
	document.getElementById('inquire_no').value=inquire_no;
	document.getElementById('user_id').value=id;
	document.getElementById('adm_delete_flag').value=deletedFlag;
	document.getElementById('delete_y').checked = (deletedFlag=="Y" ? false:true);
	document.form01.submit();
    return false;
}

window.onload = function () {
	//alert("로딩 완료");
//	document.getElementById("delete_n").checked = true;
	if(document.getElementById('delete_y').checked == true) {
		document.getElementById('delete_y').checked = true;
	}
	else {
		document.getElementById('delete_n').checked = true;
	}
}
</script>

<body>
<form class="form-inline" name="form01" action="/admin/board/inquire_list.do" method="POST">
<input type="hidden" name="pageNo" id="pageNo" value=""/>
<input type="hidden" name="inquire_no" id="inquire_no" value="${inquire_no}"/>
<input type="hidden" name="user_id" id="user_id" value=""/>
<input type="hidden" name="adm_delete_flag" id="adm_delete_flag" value=""/>
    <div id="wrapper">
        <!-- Navigation -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- Page Content -->
        
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">나누고 싶은 이야기 목록</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">	
            <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">		  
						  <div class="form-group">
						  	<table style="width:100%">
								<tr>
									<th><label>삭제여부</label></th>
									<th>&nbsp;&nbsp;&nbsp;</th>
									<th>
										<label class="radio-inline">
										<input type="radio" id="delete_y" name="delete_yn" value="Y" <c:if test = "${delete_yn=='Y'}"> checked="checked" </c:if>>삭제
										</label>
									</th>
									<th>
										<label class="radio-inline">
										<input type="radio" id="delete_n" name="delete_yn" value="N" <c:if test = "${delete_yn=='N'}"> checked="checked" </c:if>>미삭제
										</label>
									</th>
									<th>		
										</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		
									</th>
									<th>
										<label for="sel1">검색</label>
									</th>
									<th>		
										</label>&nbsp;		
									</th>
									<th>
										<select name="searchGubun" id="searchGubun">
											<option value="title" <c:out value="${schTextGubunMap['title']}"/>>제목</option>
											<option value="name" <c:out value="${schTextGubunMap['name']}"/>>작성자</option>
											<option value="contents" <c:out value="${schTextGubunMap['contents']}"/>>내용</option>
										</select>
									</th>
									<th>
										<input type="text" name="searchText" id="searchText" value="${searchText}">
									</th>
									<th>		
										</label>&nbsp;		
									</th>
									<th>
										<button type="button" onclick="javascript:goSearch2();return false;">검색</button>
									</th>
								</tr>
							</table>
						  </div>
						</div>
            <!-- /.row -->
			            <div class="panel-body">
			              <table width="100%" class="table" id="dataTables-example">
						    <thead>
						      <tr>
						        <th>NO</th>
						        <th>제목</th>
						        <th>작성자</th>
						        <th>삭제여부</th>
						        <th>답변상태</th>
						        <th>등록일</th>
						        <th>수정</th>
						        <th>삭제</th>			        
						      </tr>
						    </thead>
						    <tbody>
						      <c:choose>
									<c:when test="${fn:length(inqList) > 0}">
									<c:forEach items="${inqList}" var="list">
			                              <tr>
			                                  <td>${list.RNUM} </td>
			                                  <td>${list.TITLE} </td>
			                                  <td>${list.NAME} </td>
			                                  <td><c:choose><c:when test="${list.DELETE_YN == 'Y'}">삭제</c:when><c:otherwise>-</c:otherwise></c:choose></td>
			                                  <td><c:choose>
			                                          <c:when test="${list.REPLYTYPE == '3'}">완료</c:when>
											          <c:when test="${list.REPLYTYPE == '2'}">미완료</c:when>
											          <c:otherwise>대기</c:otherwise>
												  </c:choose>
											  </td>
											  <td>${list.APPLY_DAY}</td>
			                                  <td><a href="javascript: modifyContents('${list.INQUIRE_NO}', '${list.ID}')">수정</a></td>
			                                  <td><a href="javascript: deleteContents('${list.INQUIRE_NO}', '${list.ID}', '${list.DELETE_YN}')">
			                                  		<c:choose><c:when test="${list.DELETE_YN == 'Y'}">복원</c:when><c:otherwise>삭제</c:otherwise></c:choose>
												</a></td>
			                              </tr>
			                              </c:forEach>
									</c:when>
								</c:choose>
						    </tbody>
						  </table>			
						  <div>
			                  <!-- arrow -->
			                  <%@ include file="/admin/_common/inc/paging2.jsp" %>
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
