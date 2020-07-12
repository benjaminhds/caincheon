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
	/* if(document.getElementById('searchText').value == '') {
		alert('검색할 문자를 입력하세요.');
		document.getElementById('searchText').focus();
		return false;
	} */
	document.form01.submit();
    return false;
}

function insertContents(id) {
	document.form01.action = '/admin/board/banner_view.do';
	document.getElementById('query_type').value = "insert";
	document.getElementById('id').value=id;
	document.form01.submit();
    return false;
}

function modifyContents(banner_no, id) {
	//alert("modifyContents");
	document.form01.action = '/admin/board/banner_view.do';
	document.getElementById('query_type').value = "modify";
	document.getElementById('banner_no').value=banner_no;
	document.getElementById('id').value=id;
	document.form01.submit();
    return false;
}

function deleteContents(banner_no, id, flag) {
	word = flag=="Y"? "재개":"중지";
	if (confirm("정말 "+word+"하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/board/banner_delete.do';
		document.getElementById('banner_no').value=banner_no;
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

function testURL(url) {
	 window.open(url, "BannerTest");
	 return false;
}
window.onload = function () {
	//alert("로딩 완료");
//	document.getElementById("delete_n").checked = true;
	
}
</script>

<body>
<form class="form-inline" name="form01" action="/admin/board/banner_list.do" method="POST">
<input type="hidden" name="pageNo" id="pageNo" value="" />
<input type="hidden" name="query_type" id="query_type" value="" />
<input type="hidden" name="banner_no" id="banner_no" value="${banner_no}"/>
<input type="hidden" name="id" id="id" value="${id}"/>
<input type="hidden" name="use_yn" id="use_yn" value=""/>
    <!-- top start(left menu include) -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- top end -->
        
        <!-- main start -->        
        <!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h3 class="page-header">배너 목록</h3>
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
						  <div class="form-group">						  	
						    <label for="sel1">검색:</label>
							<select name="searchGubun" id="searchGubun" onChange="javascript:changeGubun();return false;">
							  <option value="">선택</option>
							  <option value="title" <c:out value="${searchGubunMap['title']}"/>>제목</option>
							  <option value="url" <c:out value="${searchGubunMap['url']}"/>>링크</option>
							</select>
							<input type="text" class="form-control" name="searchText" id="searchText" value="${searchText}">
							&nbsp;&nbsp;&nbsp;		
						    <button type="button" class="btn btn-default" onclick="javascript:goSearch();return false;">검색</button>
						    <button type="button" class="btn btn-default" onclick="javascript:insertContents();return false;">등록</button>
						  </div>
						<!--  Search : end //-->
					</div>
					<!-- @@@ Search FieldSet : End //-->

                    <div class="panel panel-default">
                        <!-- /.panel-heading -->
                        <div class="panel-body">
			              <table width="100%" class="table" id="dataTables-example" border="0">
						    <thead>
						      <tr>
						        <th>NO</th>
						        <th>제목</th>
						        <th>링크정보</th>
						        <th>게시기간</th>
						        <th>노출순서</th>
						        <th>작성자</th>
						        <th>노출여부</th>
						        <th>서비스상태</th>
						        <th>수정</th>
						      </tr>
						    </thead>
						    <tbody>
						      <c:choose>
									<c:when test="${fn:length(bannerList) > 0}">
									<c:forEach items="${bannerList}" var="list">
			                              <tr>
			                                  <td>${list.RNUM} </td>
			                                  <td>${list.TITLE} </td>
			                                  <c:choose>
			                                  	<c:when test="${list.LINK_TYPE == '1'}">
			                                  		<td>링크없음</td>
			                                  	</c:when>	
			                                  	<c:otherwise>
			                                  		<td>${list.URL}&nbsp;<button type="button" class="btn btn-default btn-xs" onClick="javascipt:testURL('${list.URL}');return false;"/>TEST</button></td>
			                                  	</c:otherwise>		                                  	
			                                  </c:choose>			        
			                                  <td>${list.POST_DATE_FROM}~${list.POST_DATE_TO} </td>
			                                  <td>${list.OPEN_SEQ} </td>
			                                  <td>${list.NAME} </td>
			                                  <td><c:choose><c:when test="${list.OPEN_YN == 'Y'}">노출</c:when><c:otherwise>미노출</c:otherwise></c:choose></td>
			                                  <td><a href="javascript:deleteContents('${list.BANNER_NO}','${list.ID}'<c:choose><c:when test="${list.USE_YN == 'Y'}">,'N')">사용</c:when><c:otherwise>,'Y')">중지</c:otherwise></c:choose></td>
			                                  <td><a href="javascript:modifyContents('${list.BANNER_NO}','${list.ID}')">수정</a></td>
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
