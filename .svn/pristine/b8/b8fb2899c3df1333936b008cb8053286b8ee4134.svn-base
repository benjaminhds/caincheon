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
	document.form01.action = '/admin/board/faq_view.do';
	document.getElementById('query_type').value = "insert";
	document.form01.submit();
    return false;
}

function modifyContents(faq_no) {
	//alert("modifyContents");
	document.form01.action = '/admin/board/faq_view.do';
	document.getElementById('query_type').value = "modify";
	document.getElementById('faq_no').value = faq_no;
	document.form01.submit();
    return false;
}

function deleteContents(faq_no) {
	//alert("deleteContents");
	document.form01.action = '/admin/board/faq_view.do';
	document.getElementById('faq_no').value=faq_no;
	document.form01.submit();
    return false;
}

window.onload = function () {
	//alert("로딩 완료");
	//document.getElementById("delete_n").checked = true;
	onLoadLeftMenu('etc_05');
}

</script>

<body>
<form class="form-inline" name="form01" action="/admin/board/faq_list.do" method="POST">
<input type="hidden" name="query_type" id="query_type" value="" />
<input type="hidden" name="faq_no" id="faq_no" value=""/>

    <div class="container" id="wrapper">
        <!-- Navigation -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- Page Content -->
        
        <div id="page-wrapper">
        	<div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">FAQ(교구법원)</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
	        
	        <div class="row">	
            <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">		  
						  <div class="form-group">			
						  	<table style="width:100%" border="0">
						  		<tr>
								    <th width="50"><label>검색</label></th>
								    <th><select name="searchGubun" id="searchGubun">
										  <option value="question">질문</option>
										  <option value="answer">답변</option>
										  <option value="all">질문+답변</option>
										</select>
									</th>
									<th>
										<input type="text" name="searchText" id="searchText" value="${searchText}">
									</th>
									<th>
										<button type="button" onclick="javascript:goSearch();return false;">검색</button>
									</th>
								</tr>
							    <tr>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td><button type="button" onclick="javascript:insertContents();return false;">등록</button>
							    	</td>
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
						        <th>Q/A</th>
						        <th>제목</th>						        		        
						      </tr>
						    </thead>
						    <tbody>
						      	<c:choose>
									<c:when test="${fn:length(faqList) > 0}">
									<c:forEach items="${faqList}" var="list">
			                            <tr>
			                            	<td rowspan="2"><a href="javascript:modifyContents('${list.FAQ_NO}')">${list.RNUM}</a> </td>
			                                <td>Q </td>
			                                <td><a href="javascript:modifyContents('${list.FAQ_NO}')">${list.QUESTION}</a></td>
										</tr>
										<tr>
			                                <td>A</td>
			                                <td>${list.ANSWER} </td>
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
			
</form>
</body>

</html>
