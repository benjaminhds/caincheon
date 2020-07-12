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

function insertContents() {
	document.form01.action = '/admin/member/member_insertForm.do';
	document.getElementById('query_type').value = "insert";
	document.form01.submit();
    return false;
}

function modifyContents(id) {
	document.form01.action = '/admin/member/member_view.do';
	document.getElementById('query_type').value = "modify";
	document.getElementById('id').value=id;
	document.form01.submit();
    return false;
}

function restoreContents(id) {
	if (confirm("복원 하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/member/member_restore.do';
		document.getElementById('id').value=id;
		document.form01.submit();
	}
    return false;
}

function deleteContents(id) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/member/member_delete.do';
		document.getElementById('id').value=id;
		document.form01.submit();
	}
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

function gotoList() {
 	if(document.getElementById('schText').value != '') {
 		if(document.getElementById('schTextGubun').value == '') {
 			alert('검색조건을 확인하세요.');
 			return false;
 		}
	} 
	
	document.form01.submit();
    return false;
}

function sendMailDormant(){
	var formData = new FormData($('form01')[0]);
	formData.append("type"  , "email" );
	formData.append("reason", "dormant" );
	_requestByAjax('/admin/member/send_mail_dormant.do', formData
			, function(status, responseData){
					console.log("status="+status);
					console.log("responseData 1="+responseData);
					console.log("responseData 2="+JSON.stringify(responseData));
					if(responseData.status=="success") {
						alert(responseData.count + "명에게 휴면 알림 메일을 보냈습니다.");
					}
				});
}

window.onload = function () {
	// active gnb
	onLoadLeftMenu('mem_01');
}
</script>
<body>
<form class="form-group" name="form01" action="/admin/member/member_list.do" method="POST">
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}" />
<input type="hidden" name="query_type" id="query_type" value="" />
<input type="hidden" name="id" id="id" value=""/>
<input type="hidden" name="target" id="target" value="member"/>
    <div id="wrapper">
        <!-- top start(left menu include) -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- top end -->
        
        <!-- main start -->        
        <div id="page-wrapper">
        	
        	<!-- page title start -->
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">정보관리 > 회원관리</h3>
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
	                                	<label>구분</label>
	                                </td>
	                                <td>
	                                <select class="form-control" name="memberType" id="memberType">
	                                    <option value="">=선택=</option>
	                                    <option value="1" <c:out value="${memberTypeMap['1']}"/>>신자</option>
	                                    <option value="2" <c:out value="${memberTypeMap['2']}"/>>예비신자</option>
	                                    <option value="3" <c:out value="${memberTypeMap['3']}"/>>비신자</option>
	                                    <option value="4" <c:out value="${memberTypeMap['4']}"/>>성당대표</option>
	                                    <option value="5" <c:out value="${memberTypeMap['5']}"/>>부서대표</option>
	                                    <option value="6" <c:out value="${memberTypeMap['6']}"/>>휴면계정</option>
	                                </select>
	                                </td>
	                            	<td>
	                                	<label>검색조건</label>
	                                </td>
	                                <td>
	                                <select class="form-control" name="schTextGubun" id="schTextGubun">
	                                    <option value="">=선택=</option>
	                                    <option value="id" <c:out value="${schTextGubunMap['id']}"/>>아이디</option>
	                                    <option value="name" <c:out value="${schTextGubunMap['name']}"/>>이름</option>
	                                    <option value="baptismalname" <c:out value="${schTextGubunMap['baptismalname']}"/>>세례명</option>
	                                </select>
	                                </td>
	                                <td>
	                                <input class="form-control" name="schText" id="schText" value="${schText}">
	                                </td>
	                                <td>
	                                <button class="btn btn-default"  onClick="javascript: sendMailDormant();return false;">휴면계정메일발송</button>
	                                <button class="btn btn-default"  onClick="javascript: downloadExcel();return false;">엑셀다운로드</button>
	                                <button class="btn btn-default"  onclick="javascript: insertContents();return false;">등록</button>
	                                <button class="btn btn-default"  onClick="javascript: gotoList();return false;">검색</button>
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
                                    	<th><input type="checkbox"/></th>
                                        <th>NO</th>
                                        <th>ID</th>
                                        <th>이름</th>
                                        <th>세례명</th>
                                        <th>회원유형</th>
                                        <th>그룹유형(관리권한)</th>
                                        <th>최근 접속일</th>
                                        <th>탈퇴여부</th>
                                        <th>정보변경</th>
                                        <th>유효처리</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:choose>
									<c:when test="${fn:length(list) > 0}">
									<c:forEach items="${list}" var="list">
									
	                                <tr <c:if test="${list.OUTYN eq 'Y' or list.OUTYN eq 'y'}"> style="color:#c4c4c4" </c:if>>
	                                	<td><input type="checkbox"/></td>
	                                    <td>${list.RNUM}</td>
	                                    <td><strong>${list.ID}</strong></td>
	                                    <td>${list.NAME}</td>
                                        <th>${list.BAPTISMALNAME}</th>
                                        <td><c:if test="${list.MEMBERTYPE eq '1'}">신자</c:if>
                                            <c:if test="${list.MEMBERTYPE eq '2'}">예비신자</c:if>
                                            <c:if test="${list.MEMBERTYPE eq '3'}">비신자</c:if>
                                        </td>
                                        <td><!--<c:if test="${list.IS_INCHEON_GYUGO eq 'Y'}">인천교구</c:if>
                                            <c:if test="${list.IS_INCHEON_GYUGO eq 'N'}">타교구</c:if>
                                            <c:if test="${list.IS_INCHEON_GYUGO ne 'Y' and list.IS_INCHEON_GYUGO ne 'N'}">교구없음</c:if>-->
                                            <c:if test="${list.GROUPGUBUN eq '1'}">일반회원</c:if>
                                            <c:if test="${list.GROUPGUBUN eq '2'}">교구게시판관리</c:if>
                                            <c:if test="${list.GROUPGUBUN eq '3'}">본당게시판관리</c:if>
                                            <c:if test="${list.GROUPGUBUN eq '4'}">공동체소식관리</c:if>
                                            <c:if test="${list.WRITEYN eq 'Y' }">(Y)</c:if>
                                        </td>
                                        <td>${list.LASTLOGINDT}</td>
                                        <th><c:if test="${list.OUTYN eq 'Y' or list.OUTYN eq 'y'}">탈퇴됨</c:if><c:if test="${list.DORMANTYN eq 'Y' or list.DORMANTYN eq 'y'}">휴면</c:if></th>
                                   	    <td><a href="javascript: modifyContents('${list.ID}')">수정</a></td>
	                                    <td><c:if test="${list.OUTYN eq 'Y' or list.OUTYN eq 'y'}"><a href="javascript: restoreContents('${list.ID}')" style="color:red">복원</a></c:if>
	                                        <c:if test="${list.OUTYN eq 'N' or list.OUTYN eq 'n'}"><a href="javascript: deleteContents('${list.ID}')">탈퇴</a></c:if></td>
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
<iframe id="hidden" name="hiddenifr" cellspacing="0" style="width: 0px; height: 0px; border=0" src=""></iframe>
</html>
