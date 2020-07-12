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
	if(document.getElementById('schTextGubun').value == '' &&
		document.getElementById('searchText').value != '') {
		
		alert('검색구분을 선택하세요.');
		document.getElementById('schTextGubun').focus();
		return false;
	}
	if(document.getElementById('searchText').value == '' &&
		document.getElementById('schTextGubun').value != '') {
		
		alert('검색어를 입력하세요.');
		document.getElementById('searchText').focus();
		return false;
	}
	document.form01.submit();
    return false;
}

function goSearchM() {
	location.href="/admin/church/church_list.do";
}

function goSearchS() {
	location.href="/admin/church/gongso_list.do";
}

function insertContents() {
	document.form01.action = '/admin/church/gongso_view.do';
	document.getElementById('query_type').value = "insert";
	document.form01.submit();
    return false;
}

function modifyContents(g_idx) {
	document.form01.action = '/admin/church/gongso_view.do';
	document.getElementById('query_type').value = "modify";
	document.getElementById('g_idx').value = g_idx;
	document.form01.submit();
    return false;
}

function deleteContents(g_idx) {
	if(confirm("정말 삭제하시겠습니까?")==true) {
		document.form01.action = '/admin/church/gongso_delete.do';
		document.getElementById('g_idx').value=g_idx;
		document.form01.submit();
	}
    return false;
}

window.onload = function () {
	onLoadLeftMenu('info_04');
}

$(document).ready(function(){
 	$(".nav-tabs a").click(function(){
        $(this).tab('show');
    });

    $('.nav-tabs a').on('shown.bs.tab', function(event){
        var x = $(event.target).text();         // active tab
        var y = $(event.relatedTarget).text();  // previous tab
        
        if(x=="본당 관리") {
        	goSearchM();
        }
        else if(x=="공소 관리") {
        	goSearchS();
        }
    });
});
	
</script>

<body>
<form class="form-inline" name="form01" action="/admin/church/gongso_list.do" method="POST">
<input type="hidden" name="active" id="active" value="" />
<input type="hidden" name="query_type" id="query_type" value="" />
<input type="hidden" name="g_idx" id="g_idx" value="${g_idx}"/>
<input type="hidden" name="pageNo" id="pageNo" value="${pageNo}"/>
	<!-- top start(left menu include) -->
<%@ include file="/admin/_common/inc/top.jsp" %>
<!-- top end -->

<!-- main start -->        
<!-- Page Content -->
<div id="page-wrapper">
	<ul class="nav nav-tabs">
		<li><a data-toggle="tab" href="javascript:goSearchM()">본당 관리</a></li>
		<li class="active"><a data-toggle="tab" href="javascript:goSearchS()">공소 관리</a></li>
	</ul>
    <div class="tab-content">				   
	             <!-- Contents : Begin //-->
					<!-- @@@ Search FieldSet : begin //-->
					<!-- <div class="search">
						 Search : begin //
						<div class="well search-search"> -->
					<div class="panel panel-default">
                    	<div class="panel-heading">
	                   		<label for="q_word">검색</label>
	                   		<select class="form-control form-control-short w-200 mr-10" name="schTextGubun" id="schTextGubun">
	                   		  <option value="">전체</option>
	                   		  <option value="church" <c:if test="${_params.schTextGubun=='church' }" >selected</c:if>>관할성당</option>
							  <option value="gongso" <c:if test="${_params.schTextGubun=='gongso' }" >selected</c:if>>공소명</option>
							</select>
							<input class="form-control form-control-short" type="text" name="searchText" id="searchText" value="${_params.searchText}"/>
							<button type="button" class="btn btn-primary" onclick="javascript:insertContents();return false;"><i class="fa fa-pencil"></i>등록</button>
		                    <button type="button" class="btn btn-primary" onclick="javascript:goSearch();return false;"><i class="fa fa-search"></i>검색</button>													
						</div>
					</div>

						<!-- /.row -->
				        <div class="panel-body">
				        	<table width="100%" class="table" id="dataTables-example">
								<thead>
							    	<tr>
							        <th>NO</th>
							        <th>관할성당</th>
							        <th>공소명</th>
							        <th>공소회장</th>
							        <th>공소주소</th>
							        <th>전화번호</th>
							        <th>수정</th>
							        <th>삭제</th>			        
							      	</tr>
							    </thead>
								<tbody>
								<c:choose>
									<c:when test="${fn:length(admGongsoList) > 0}">
										<c:forEach items="${admGongsoList}" var="list">
					                    	<tr>
					                        <td>${list.RNUM} </td>
					                        <td>${list.CHURCH_NAME} </td>
					                        <td>${list.NAME} </td>
					                        <td>${list.CHIEF_NAME} </td>
					                        <td>${list.ADDR} </td>
					                        <td>${list.TEL} </td>
				                            <td><a href="javascript:modifyContents('${list.G_IDX}')">수정</a></td>
					                        <td><a href="javascript:deleteContents('${list.G_IDX}')">삭제</a></td>
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
				</div>	
	        </div>       
        	<!-- /#page-wrapper -->
        	
    <!-- /#wrapper -->
</form>
</body>

</html>
