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
 	if(document.getElementById('schText').value != '') {
 		if(document.getElementById('schTextGubun').value == '') {
 			alert('검색조건을 확인하세요.');
 			return false;
 		}
	} 
	document.form01.submit();
    return false;
}

function insertContents() {
	document.form01.action = '/admin/board/par_view.do';
	document.getElementById('query_type').value = "insert";
	document.form01.submit();
    return false;
}

function modifyContents(b_idx, bl_idx) {
	document.form01.action = '/admin/board/par_view.do';
	document.getElementById('query_type').value = "modify";
	document.getElementById('b_idx').value=b_idx;
	document.getElementById('bl_idx').value=bl_idx;
	document.form01.submit();
    return false;
}

function deleteContents(b_idx, bl_idx) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/board/par_delete.do';
		document.getElementById('b_idx').value=b_idx;
		document.getElementById('bl_idx').value=bl_idx;
		document.form01.submit();
	}
    return false;
}
window.onload = function () {
	// active gnb
	onLoadLeftMenu('board_01');
}
</script>
<body>
<form class="form-inline" name="form01" action="/admin/board/par_list.do" method="POST">
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}" />
<input type="hidden" name="query_type" id="query_type" value="" />
<input type="hidden" name="b_idx" id="b_idx" value="${b_idx}"/>
<input type="hidden" name="bl_idx" id="bl_idx" value="${bl_idx}"/>
        <!-- top start(left menu include) -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- top end -->
        
        <!-- main start -->        
        <!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h3 class="page-header">동정/강론 목록</h3>
                	</div>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- //.row(page title end -->
            <div class="row">	
            <div class="col-lg-12">
                    <div class="panel panel-default">
                       	
					  	<table class="table" style="width: 100%; vertical-align: middle;  font-weight: 600; ">
					  	<tr>
					  		<td style="text-align: right;">구분</td>
					  		<td>
								
									<select class="form-control form-control-short w-100" name="c_idx" id="c_idx">
									  <option value="ALL">전체</option>
									  <option value="3" <c:out value="${schTextGubunMap1['3']}"/>>동정</option>
									  <option value="4" <c:out value="${schTextGubunMap1['4']}"/>>강론</option>
									</select>
							</td>
							<td style="text-align: right;">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
							<td style="text-align: right;">검색조건</td>
							<td>
								<select class="form-control form-control-short w-100" name="schTextGubun" id="schTextGubun" onChange='javascript: if(this.selectedIndex==0) { $("#schText").val(""); }'>
                                  <option value="">선택</option>
								  <option value="title" <c:out value="${schTextGubunMap2['title']}"/>>제목</option>
								  <option value="content" <c:out value="${schTextGubunMap2['contents']}"/>>내용</option>
								</select>
							</td>
							<td><input class="form-control form-control-short w-200" type="text" name="schText" id="schText" value="${schText}"></td>
							<td>		
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		
							</td>
							<td>
						  		<div style="text-align: right;">
									<div class="btn-group"><button type="button" class="btn btn-primary" onclick="javascript:goSearch();return false;"><i class="fa fa-search"></i>검색</button></div>
									<div class="btn-group"><button type="button" class="btn btn-primary" onclick="javascript:insertContents();return false;"><i class="fa fa-pencil"></i>등록</button>
			                    </div>
							</td>
						</tr>
						</table>
					  
					</div>
            <!-- /.row -->
                    <div class="panel panel-default">
                        <!-- /.panel-heading -->
                        <div class="panel-body">
							<table class="table table-striped table-bordered table-hover">
								<colgroup>
									<col style="width:70px;">
									<col style="width:180px;">	
									<col>
									<col style="width:120px;">
									<col style="width:70px;">
									<col style="width:70px;">
									<col style="width:70px;">
								</colgroup>
                                <thead>
                                    <tr>
                                        <th>NO</th>
                                        <th>분류</th>
                                        <th>제목</th>
                                        <th>등록일</th>
                                        <th>조회수</th>
                                        <th>수정</th>
                                        <th>삭제</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:choose>
									<c:when test="${fn:length(LIST) > 0}">
									<c:forEach items="${LIST}" var="list">
	                                <tr <c:if test="${list.IS_VIEW eq 'N'}">style="color: darkgray;"</c:if> >
	                                    <td class="center">
	                                    <c:choose>
										    <c:when test="${list.NOTICE_TYPE == 1}">
										        	<i class="ico brown">공지</i>
										    </c:when>
										    <c:otherwise> ${list.RNUM} </c:otherwise>
										</c:choose>
	                                    </td>
	                                    <td class="center"><strong>
	                                    <c:choose>
										    <c:when test="${list.C_IDX eq '3'}"> 동정    </c:when>
										    <c:otherwise> 강론 </c:otherwise>
										</c:choose>
	                                    </strong></td>
	                                    <td>${list.TITLE}</td>
	                                    <td class="center">${list.REGDATE}</td>
	                                    <td class="center">${list.HIT}</td>
	                                    <td class="center"><button name="btnEdit" type="button" class="btn40 btn-success btn-circle" title="Edit" onClick="javascript:modifyContents('${list.B_IDX}','${list.BL_IDX}')"><i class="fa fa-pencil"></i></button>
	                                    </td>
	                                    <td class="center"><button name="btnDel" type="button" class="btn40 btn-danger btn-circle" title="Delete" onClick="javascript:deleteContents('${list.B_IDX}','${list.BL_IDX}')"><i class="fa fa-times"></i></button>
	                                    </td>
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
				<!-- Contents : End //-->

			</div>
			<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->
</form>
</body>

</html>
