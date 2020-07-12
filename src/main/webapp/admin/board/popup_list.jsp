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
 		if(document.getElementById('searchGubun').value == '') {
 			alert('검색조건을 확인하세요.');
 			return false;
 		}
	} 
	document.form01.submit();
    return false;
}

function insertContents(id) {
	document.form01.action = '/admin/board/popup_view.do';
	document.getElementById('query_type').value = "insert";
	document.getElementById('id').value=id;
	document.form01.submit();
    return false;
}

function modifyContents(popup_no, id) {
	document.form01.action = '/admin/board/popup_view.do';
	document.getElementById('query_type').value = "modify";
	document.getElementById('popup_no').value=popup_no;
	document.getElementById('id').value=id;
	document.form01.submit();
    return false;
}

function deleteContents(popup_no, id, flag) {
	word = flag=="Y" ? "재개":"중지";
	if (confirm("서비스가 "+word+" 됩니다. \n진행 하시겠습니까?") == true){    //확인
		document.form01.action = '/admin/board/popup_delete.do';
		document.getElementById('popup_no').value=popup_no;
		document.getElementById('id').value=id;
		document.getElementById('use_yn').value=flag;
		document.form01.submit();
	}
    return false;
}

function changeGubun() {
	if(document.getElementById('searchGubun').value == '') {
		document.getElementById('searchText').value = '';
	}
}

window.onload = function () {
	//alert("로딩 완료");
//	document.getElementById("delete_n").checked = true;
	
}
</script>

<body>
<form class="form-inline" name="form01" action="/admin/board/popup_list.do" method="POST">
<input type="hidden" name="query_type" id="query_type" value="" />
<input type="hidden" name="popup_no" id="popup_no" value="${popup_no}"/>
<input type="hidden" name="id" id="id" value="${id}"/>
<input type="hidden" name="use_yn" id="use_yn" value=""/>
    <div id="wrapper">
        <!-- Navigation -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- Page Content -->
        
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">팝업 목록</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">	
            <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">		  
						  <div class="form-group">						  	
						    <label for="sel1">검색:</label>
							<select name="searchGubun" id="searchGubun"  onChange="javascript:changeGubun();return false;">
							  <option value="">선택</option>
							  <option value="title" <c:out value="${searchGubunMap['title']}"/>>제목</option>
							  <option value="name" <c:out value="${searchGubunMap['name']}"/>>작성자</option>
							  <option value="contents" <c:out value="${searchGubunMap['contents']}"/>>내용</option>
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
						        <th>제목</th>
						        <th>게시기간</th>
						        <th>작성자</th>
						        <th>등록일</th>
						        <th>노출여부</th>
						        <th>서비스상태</th>
						        <th>수정</th>			        
						      </tr>
						    </thead>
						    <tbody>
						      <c:choose>
									<c:when test="${fn:length(popupList) > 0}">
									<c:forEach items="${popupList}" var="list">
			                              <tr>
			                                  <td>${list.RNUM} </td>
			                                  <td>${list.TITLE} </td>
  	                                  		  <td>${list.POST_DATE_FROM}~${list.POST_DATE_TO} </td>
			                                  <td>${list.NAME} </td>
			                                  <td>${list.REGISTDT}</td>
			                                  <td><c:choose><c:when test="${list.OPEN_YN == 'Y'}">노출</c:when><c:otherwise>숨김</c:otherwise></c:choose></td>
											  <td><a href="javascript:deleteContents('${list.POPUP_NO}','${list.ID}'<c:choose><c:when test="${list.USE_YN == 'Y'}">,'N')">사용</c:when><c:otherwise>,'Y')">중지</c:otherwise></c:choose></a></td>
			                                  <td><a href="javascript:modifyContents('${list.POPUP_NO}','${list.ID}')">수정</a></td>
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
