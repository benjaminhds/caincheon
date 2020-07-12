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

function goSearchM() {
	if(document.getElementById('searchMText').value == '') {
		//alert('검색할 문자를 입력하세요.');
		//document.getElementById('searchText').focus();
		//return false;
	}
	document.form01.submit();
    return false;
}

function goSearchS() {
	//alert('goSearchS');
	if(document.getElementById('searchSText').value == '') {
		//alert('검색할 문자를 입력하세요.');
		//document.getElementById('searchText').focus();
		//return false;
	}
	document.getElementById('category_code').value = document.getElementById('categoryCode').value;
	document.getElementById('active').value = "tab2";
	document.form01.submit();
    return false;
}

function insertHistoryM() {
	document.form01.action = '/admin/board/historym_view.do';
	document.getElementById('query_type').value = "insert";
	document.form01.submit();
    return false;
}

function modifyHistoryM(category_code) {
	//alert("modifyContents");
	document.form01.action = '/admin/board/historym_view.do';
	document.getElementById('query_type').value = "modify";
	document.getElementById('category_code').value = category_code;
	document.form01.submit();
    return false;
}

function deleteHistoryM(category_code, slave_cnt) {
	//alert("deleteContents");
	if(slave_cnt != 0) {
		alert('연혁관리를 모두 삭제해야 카테고리를 삭제 할 수 있습니다.');
	} else {
		document.form01.action = '/admin/board/historym_delete.do';
		document.getElementById('category_code').value=category_code;
		document.form01.submit();
	}
    return false;
}

function insertHistoryS() {
	document.form01.action = '/admin/board/historys_view.do';
	document.getElementById('query_type').value = "insert";
	document.form01.submit();
    return false;
}

function modifyHistoryS(category_code, history_no) {
	//alert("modifyContents");
	document.form01.action = '/admin/board/historys_view.do';
	document.getElementById('query_type').value = "modify";
	document.getElementById('category_code').value = category_code;
	document.getElementById('history_no').value = history_no;
	document.form01.submit();
    return false;
}

function deleteHistoryS(category_code, history_no) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/board/historys_delete.do';
		document.getElementById('category_code').value=category_code;
		document.getElementById('history_no').value = history_no;
		document.form01.submit();
	}
    return false;
}


window.onload = function () {
	//alert("로딩 완료");
	//document.getElementById("delete_n").checked = true;
	onLoadLeftMenu('info_01');
}

$(document).ready(function(){
	$(".nav-tabs a").click(function(){
        $(this).tab('show');
    });

    $('.nav-tabs a').on('shown.bs.tab', function(event){
        var x = $(event.target).text();         // active tab
        var y = $(event.relatedTarget).text();  // previous tab
        
        if(x=="역사관 카테고리 관리") {
        	//alert("역사관");
        	goSearchM();
        }
        else if(x=="카테고리별 연혁관리") {
        	//alert("카테고리별");
        	goSearchS();
        }
    });
});
	
</script>

<body>
<form class="form-inline" name="form01" action="/admin/board/history_list.do" method="POST">
<input type="hidden" name="active" id="active" value="" />
<input type="hidden" name="query_type" id="query_type" value="" />
<input type="hidden" name="category_code" id="category_code" value="${category_code}"/>
<input type="hidden" name="history_no" id="history_no" value=""/>

    <div class="container" id="wrapper">
        <!-- Navigation -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- Page Content -->
        
        <div id="page-wrapper">
        	<ul class="nav nav-tabs">
        		<c:choose>
					<c:when test="${_params.active == 'tab2'}">
						<li><a data-toggle="tab" href="#tab1">역사관 카테고리 관리</a></li>
					    <li class="active"><a data-toggle="tab" href="#tab2">카테고리별 연혁관리</a></li>					    
				    </c:when>
				    <c:otherwise>
				    	<li class="active"><a data-toggle="tab" href="#tab1">역사관 카테고리 관리</a></li>
					    <li><a data-toggle="tab" href="#tab2">카테고리별 연혁관리</a></li>
				    </c:otherwise>
			    </c:choose>
	  		</ul>
	        
	        <div class="tab-content">
	        	<!--  첫번째 탭 -->
       	<c:choose>
			<c:when test="${_params.active == 'tab2'}">
	        	<div id="tab1" class="tab-pane fade">
	        </c:when>
	        <c:otherwise>
	        	<div id="tab1" class="tab-pane fade in active">
	        </c:otherwise>
	    </c:choose>    	
					<div class="col-lg-12">
				    	<h3 class="page-header">역사관 카테고리 관리</h3>
				    </div>
        
					<div class="row">	
				    	<div class="col-lg-12">
				        	<div class="panel panel-default">
				            	<div class="panel-heading">		  
									<div class="form-group btn-group">						  	
								    	<span>
								    	<label for="sel1">검색</label>
										<input type="text" class="form-control" placeholder="" name="searchMText" id="searchMText" value="${_params.searchMText}">
								    	<button type="button" class="btn btn-default" onclick="javascript:goSearchM();return false;">검색</button>
								    	<button type="button" class="btn btn-default" onclick="javascript:insertHistoryM();return false;">등록</button>
								    	</span>
								  	</div>
								</div>
          						<!-- /.row -->
						        <div class="panel-body">
						        	<table width="100%" class="table" id="dataTables-example">
										<thead>
									    	<tr>
									        <th>NO</th>
									        <th>카테고리</th>
									        <th>노출여부</th>
									        <th>노출순서</th>
									        <th>등록일</th>
									        <th>수정</th>
									        <th>삭제</th>			        
									      	</tr>
									    </thead>
									<tbody>
										<c:choose>
											<c:when test="${fn:length(historyList) > 0}">
												<c:forEach items="${historyList}" var="list">
							                    	<tr>
							                        <td>${list.RNUM} </td>
							                        <td>${list.CATEGORY_NAME} </td>
							                        <td>${list.DISPLAYYN_TEXT} </td>
							                        <td>${list.DISPLAYNO} </td>
							                        <td>${list.REGISTDT} </td>
						                            <td><a href="javascript:modifyHistoryM('${list.CATEGORY_CODE}')">수정</a></td>
							                        <td><a href="javascript:deleteHistoryM('${list.CATEGORY_CODE}', ${list.SLAVE_CNT})">삭제</a></td>
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
	            				
				<!--  두번쨰 탭 -->
				<!-- <div id="tab2" class="tab-pane fade"> -->
				
		<c:choose>
			<c:when test="${_params.active == 'tab2'}">
	        	<div id="tab2" class="tab-pane fade in active">
	        </c:when>
	        <c:otherwise>
	        	<div id="tab2" class="tab-pane fade">
	        </c:otherwise>
	    </c:choose>   
				
	                <div class="col-lg-12">
	                    <h3 class="page-header">카테고리별 연혁관리</h3>
	                </div>
	                <!-- /.col-lg-12 -->
	            
		            <div class="row">	
		            	<div class="col-lg-12">
		                    <div class="panel panel-default">
		                        <div class="panel-heading">		
		                        	<div class="form-group">
		                        		<label for="">정렬 </label>		                        			
										<select name="categoryCode" id="categoryCode">
											<option value="ALL">전체</option>
											<c:forEach items="${categoryList}" var="list">
		                              			<option value="${list.CATEGORY_CODE}" <c:if test = "${list.CATEGORY_CODE==_params.categoryCode }"> selected </c:if>>${list.CATEGORY_NAME}</option>
		                              		</c:forEach>
										</select>															  	
									    <label for="">검색 </label>
									    <select name="searchGubun" id="searchGubun">
											<option value="TITLE">제목</option>
											<option value="CONTENTS">내용</option>
										</select>	
										
										<input type="text" class="form-control" placeholder="" name="searchSText" id="searchSText" value="${_params.searchSText}">
										&nbsp;&nbsp;&nbsp;		
									    <button type="button" class="btn btn-default" onclick="javascript:goSearchS();return false;">검색</button>
									    <button type="button" class="btn btn-default" onclick="javascript:insertHistoryS();return false;">등록</button>
									</div>
								</div>
		            			<!-- /.row -->
					            <div class="panel-body">
					              <table width="100%" class="table" id="dataTables-example">
								    <thead>
								      <tr>
								        <th>NO</th>
								        <th>카테고리</th>
								        <th>제목</th>
								        <th>노출여부</th>
								        <th>행사일</th>			        
								        <th>수정</th>
								        <th>삭제</th>
								      </tr>
								    </thead>
								    <tbody>
								      <c:choose>
											<c:when test="${fn:length(historyList) > 0}">
											<c:forEach items="${historyList}" var="list">
					                              <tr>
					                                  <td>${list.RNUM} </td>
					                                  <td>${list.CATEGORY_NAME} </td>
					                                  <td>${list.TITLE} </td>
					                                  <td>${list.DISPLAYYN_TEXT} </td>
					                                  <td>${list.EVENTDT} </td>										                                  
					                                  <td><a href="javascript:modifyHistoryS('${list.CATEGORY_CODE}','${list.HISTORY_NO}')">수정</a></td>
					                                  <td><a href="javascript:deleteHistoryS('${list.CATEGORY_CODE}','${list.HISTORY_NO}')">삭제</a></td>
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
