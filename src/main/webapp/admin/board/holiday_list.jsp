<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/admin/_common/inc/head.jsp" %>
<%
if(admSessionMemId==null||admSessionMemId.equals("")) {                // id가 Null 이거나 없을 경우
	response.sendRedirect("/admin/login.jsp");    // 로그인 페이지로 리다이렉트 한다.
}
%>
<script src="http://malsup.github.com/jquery.form.js"></script> 
<script type="text/javascript">
function goPage(pageNo) {
	document.getElementById('pageNo').value=pageNo;
	document.form01.submit();
    return false;
}

function goSearch() {
	document.form01.submit();
    return false;
}

function modifyContents(h_date) {
	document.form01.action = '/admin/board/holiday_view.do';
	document.getElementById('query_type').value = "modify";
	document.getElementById('h_date').value=h_date;
	document.form01.submit();
    return false;
}

function deleteContents(h_date) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/board/holiday_delete.do';
		document.getElementById('h_date').value=h_date;
		document.form01.submit();
	}
    return false;
}

function checkFileType(filePath) {
    var fileFormat = filePath.split(".");
    if (fileFormat.indexOf("xlsx") > -1) {
        return true;
    } else {
        return false;
    }

}

function downloadExcel() {
    document.getElementById('fileName').value='전례력양식.xlsx';
    document.getElementById('filePath').value='upload\\form';
    document.frmDown.submit();
    return false;	
}

function bulkUpload() {
    var file = $("#excelFile").val();
    if (file == "" || file == null) {
        alert("파일을 선택해주세요.");
        return false;
    } else if (!checkFileType(file)) {
        alert("엑셀 파일만 업로드 가능합니다.");
        return false;
    }

    if (confirm("전례력을 엑셀로 벌크로드 하시겠습니까?")) {
    	//alert( $("#excelUploadForm").attr("action"));
    	$("#excelUploadForm").submit();
    }
}

window.onload = function () {

}

</script>

<body>
     <!-- top start(left menu include) -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- top end -->
        
        <!-- main start -->        
        <!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h3 class="page-header">전례력/말씀관리</h3>
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
				            	<form id="excelUploadForm" name="excelUploadForm" enctype="multipart/form-data" method="POST" 
				                                action= "/admin/board/holiday_excelUploadAjax.do">
				                <tr>
				                	<th><label for="q_word">엑셀 양식</label></th>
				                	<td>
								        <button type="button" id="downloadExcelBtn" class="btn" onclick="downloadExcel()" ><span>양식 다운로드</span></button> 
				                	</td>
				                	<th><label for="q_word">엑셀 업로드</label></th>
				                	<td><input id="excelFile" type="file" name="excelFile" />
				                	</td>
				                	<td>
								        <button type="button" id="addExcelImpoartBtn" class="btn" onclick="bulkUpload()" ><span>추가</span></button>
				                	</td>

<!-- 				                	<td><label for="q_word">검색년도</label>
				                	</td>
				                	<td>
				                		<input class="form-control form-control-short" type="input" name="searchYear" id="searchYear" value="2017"/>
				                	</td>
				                	<td>
								        <button type="button" id="addExcelImpoartBtn" class="btn" onclick="goSearch()" ><span>검색</span></button>
				                	</td> -->
				                </tr>
								</form> 
							</table>
						</div>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- //.row(page title end -->
            <div class="page-list">
            	<div class="panel panel-default">
                       <!-- /.panel-heading -->
		            <div class="panel-body" style="overflow-x:hidden;overflow-y:scroll;width:100%;height:650px;">
		              <table width="100%" class="table" id="dataTables-example">
						    <thead>
						      <tr>
						        <th>NO</th>
						        <th>전례력</th>
						        <th>말씀</th>
						        <th>날짜</th>
						        <th>수정</th>
						        <th>삭제</th>			        
						      </tr>
						    </thead>
						    <tbody>
						      <c:choose>
									<c:when test="${fn:length(holidayList) > 0}">
									<c:forEach items="${holidayList}" var="list">
			                              <tr>
			                                  <td>${list.RNUM} </td>
			                                  <td>${list.DESCRIPTION} </td>
			                                  <td>${list.TALK} </td>
			                                  <td>${list.H_DATE} </td>
		                                   	  <td><a href="javascript:modifyContents('${list.H_DATE}')">수정</a></td>
			                                  <td><a href="javascript:deleteContents('${list.H_DATE}')">삭제</a></td>
			                              </tr>
			                              </c:forEach>
									</c:when>
								</c:choose>		      
						    </tbody>
						  </table>
						  <div>
                               <!-- paging start -->
                               <%@ include file="/admin/_common/inc/paging2.jsp" %>
                   			<!-- //paging end -->                                
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
</body>
<form  name="form01" id="form01" action="/admin/board/holiday_list.do" method="POST">
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}" />
<input type="hidden" name="query_type" id="query_type" value="" />
<input type="hidden" name="h_date" id="h_date" value=""/>
</form>
<form  name="frmDown" action="/filedownload.jsp" method="POST">
<input type="hidden" name="fileName" id="fileName" value=""/>
<input type="hidden" name="filePath" id="filePath" value=""/>
</form>

</html>
