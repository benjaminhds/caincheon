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
function insertContents() {
	document.form01.action = '/admin/member/admmember_insertForm.do';
	document.getElementById('query_type').value = "insert";
	document.form01.submit();
    return false;
}

function modifyContents(adm_id) {
	document.form01.action = '/admin/member/admmember_view.do';
	document.getElementById('query_type').value = "modify";
	document.getElementById('adm_id').value=adm_id;
	document.form01.submit();
    return false;
}

function deleteContents(adm_id) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/member/admmember_delete.do';
		document.getElementById('adm_id').value=adm_id;
		document.form01.submit();
	}
    return false;
}

function goList() {
 	if(document.getElementById('schText').value != '') {
 		if(document.getElementById('schTextGubun').value == '') {
 			alert('검색조건을 확인하세요.');
 			return false;
 		}
	} 

	document.form01.submit();
    return false;
}
</script>
<body>
<form class="form-group" name="form01" action="/admin/member/admmember_list.do" method="POST">
<input type="hidden" name="query_type" id="query_type" value="" />
<input type="hidden" name="adm_id" id="adm_id" value=""/>
    <div id="wrapper">
        <!-- top start(left menu include) -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- top end -->
        
        <!-- main start -->        
        <div id="page-wrapper">
        	
        	<!-- page title start -->
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">관리자목록</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- //.row(page title end -->
            
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                    
                    	<!-- search start -->
                        <div class="panel-heading">
                            <div class="form-group">
                            <table>
                            	<tr>
	                            	<td>
	                                	<label>검색조건</label>
	                                </td>
	                                <td>
	                                <select class="form-control" name="schTextGubun" id="schTextGubun">
	                                    <option value="">=선택=</option>
	                                    <option value="adm_id" <c:out value="${schTextGubunMap['adm_id']}"/>>아이디</option>
	                                    <option value="adm_name" <c:out value="${schTextGubunMap['adm_name']}"/>>이름/부서명</option>
	                                    <option value="adm_depart" <c:out value="${schTextGubunMap['adm_depart']}"/>>현임지</option>
	                                </select>
	                                </td>
	                                <td>
	                                <input class="form-control" name="schText" id="schText" value="${schText}">
	                                </td>
	                                <td>
	                                <button class="btn btn-default"  onClick="javascript:goList();return false;">검색</button>
	                                <button class="btn btn-default" onclick="javascript:insertContents();return false;">등록</button>
	                                </td>
                                </tr>
                            </table>
                            </div>
                        </div>
                    	<!-- //search end -->
                       
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <table width="100%" class="table" id="dataTables-example">
                                <thead>
                                    <tr>
                                        <th>NO</th>
                                        <th>ID</th>
                                        <th>이름</th>
                                        <th>현임지</th>
                                        <th>수정</th>
                                        <th>삭제</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:choose>
									<c:when test="${fn:length(list) > 0}">
									<c:forEach items="${list}" var="list">
	                                <tr>
	                                    <td>
	                                    ${list.RNUM}
	                                    </td>
	                                    <td><strong>
	                                    ${list.ID}
	                                    </strong></td>
	                                    <td>${list.NAME}/${list.BAPTISMALNAME }</td>
	                                    <td>${list.CHURCH_NAME}</td>
                                   	    <td><a href="javascript:modifyContents('${list.ADM_ID}')">수정</a></td>
	                                    <td><a href="javascript:deleteContents('${list.ADM_ID}')">삭제</a></td>
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
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->
</form>
</body>

</html>
