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
function viewList() {
	document.form01.action = '/admin/member/admauth_list.do';
	document.form01.submit();
	return false;
}

function goSearch() {
	if( $(":input:radio[name=schAdmgroup]:checked").val() == undefined) {
		alert('검색구분을 선택하세요.');
		return false;
	} else if( $(":input:radio[name=schAdmgroup]:checked").val()=="ALL") {
		document.getElementById('searchText').value = "";
	} else {
		if($("#searchText").val()=="") {
			alert('검색할 문자를 입력하세요.');
			document.getElementById('searchText').focus();
			return false;
		}
	}
 	document.form01.submit();
    return false;
}

function goSearch2() {
	if( $(":input:radio[name=schAdmgroup2]:checked").val() == undefined) {
		alert('검색구분을 선택하세요.');
		return false;
	} else if( $(":input:radio[name=schAdmgroup2]:checked").val()=="ALL") {
		document.getElementById('searchText2').value = "";
	} else {
		if($("#searchText2").val()=="") {
			alert('검색할 문자를 입력하세요.');
			document.getElementById('searchText2').focus();
			return false;
		}
	}
 	document.form01.submit();
    return false;
}

function insertContents(id, menu) {
	document.form01.action = '/admin/member/admauth_insert.do';
	document.getElementById('query_type').value = 'I';
	document.getElementById('id').value=id;
	document.getElementById('menu').value=menu;
	document.form01.submit();
    return false;
}

function deleteContents(id, menu) {
	if (confirm("정말 삭제하시겠습니까??") == true) {    //확인
		document.form01.action = '/admin/member/admauth_delete.do';
		document.getElementById('query_type').value='D';
		document.getElementById('id').value=id;
		document.getElementById('menu').value=menu;
		document.form01.submit();
	}
    return false;
}

window.onload = function () {
	onLoadLeftMenu('adm_02');
}
</script>

<body>
<form class="form-group" name="form01" action="/admin/member/admauth_view.do" method="POST">
<input type="hidden" name="query_type" id="query_type" value=""/>
<input type="hidden" name="id" id="id" value=""/>
<input type="hidden" name="title" id="title" value="${_params.title}"/>
<input type="hidden" name="menu" id="menu" value="${_params.menu}"/>

     <!-- top start(left menu include) -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- top end -->
        
        <!-- main start -->        
        <!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h3 class="page-header">게시판권한부여 등록/수정 > ${_params.title }</h3>
                	</div>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- //.row(page title end -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                        	관리자 검색
                        </div>
                       	<div class="panel-heading">
							<label class="radio-inline"><input type="radio" name="schAdmgroup" id="schAdmgroup" value="ALL" <c:if test="${contents.GROUPTYPE=='4' }" >checked="checked"</c:if>>관리자전체보기</label>
							<label class="radio-inline"><input type="radio" name="schAdmgroup" id="schAdmgroup" value="NAME" <c:if test="${contents.GROUPTYPE=='5' }" >checked="checked"</c:if>>검색</label>
							<input type="text" name="searchText" id="searchText" value="${_params.searchText}"/>
							<button type="button" onclick="javascript:goSearch();return false;">검색</button>
                        </div>
                        <!-- /input-group -->
                        <!-- /.panel-heading -->
   						<div class="panel-body" style="overflow-x:hidden;overflow-y:scroll;width:100%;height:200px;">
			              <table width="100%" class="table" id="dataTables-example">
						    <thead>
						      <tr>
						        <th>관리자명/부서명</th>
						        <th>현임지</th>
						        <th>추가</th>
						      </tr>
						    </thead>
						    <tbody>
						      <c:choose>
									<c:when test="${fn:length(memberNonList) > 0}">
									<c:forEach items="${memberNonList}" var="nlist">
			                              <tr>
			                                  <td>${nlist.ADM_NAME} </td>
			                                  <td>${nlist.DEPART_CODE} </td>
			                                  <td><a href="javascript:insertContents('${nlist.ADM_ID}','${_params.menu }')">추가</a></td>
			                              </tr>
			                              </c:forEach>
									</c:when>
								</c:choose>		      
						    </tbody>
						  </table>
						</div>
					</div>
						<!-- /.panel-body -->						
				<!-- panel panel-default -->
			</div>
			<!-- col-lg-12 -->
		</div>						
			<!-- row -->
		
		<!-- --------------- -->
		<!-- //.row(page title end -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                        	등록된 관리자
                        </div>
                       	<div class="panel-heading">
							<label class="radio-inline"><input type="radio" name="schAdmgroup2" id="schAdmgroup2" value="ALL" <c:if test="${contents.GROUPTYPE=='4' }" >checked="checked"</c:if>>관리자전체보기</label>
							<label class="radio-inline"><input type="radio" name="schAdmgroup2" id="schAdmgroup2" value="NAME" <c:if test="${contents.GROUPTYPE=='5' }" >checked="checked"</c:if>>검색</label>
							<input type="text" name="searchText2" id="searchText2" value="${_params.searchText2}"/>
							<button type="button" onclick="javascript:goSearch2();return false;">검색</button>
                        </div>
                        <!-- /input-group -->
                        <!-- /.panel-heading -->
   						<div class="panel-body" style="overflow-x:hidden;overflow-y:scroll;width:100%;height:200px;">
			              <table width="100%" class="table" id="dataTables-example">
						    <thead>
						      <tr>
						        <th>관리자명/부서명</th>
						        <th>현임지</th>
						        <th>제거</th>
						      </tr>
						    </thead>
						    <tbody>
						      <c:choose>
									<c:when test="${fn:length(memberAuthList) > 0}">
									<c:forEach items="${memberAuthList}" var="list">
			                              <tr>
			                                  <td>${list.ADM_NAME} </td>
			                                  <td>${list.DEPART_CODE} </td>
			                                  <td><a href="javascript:deleteContents('${list.ADM_ID}','${_params.menu }')">삭제</a></td>
			                              </tr>
			                              </c:forEach>
									</c:when>
								</c:choose>		      
						    </tbody>
						  </table>
						</div>
					</div>
						<!-- /.panel-body -->						
				<!-- panel panel-default -->
			</div>
			<!-- col-lg-12 -->
		</div>						
			<!-- row -->
		<!-- --------------- -->
		
		<p class="pull-right">
		<button type="button" id="btnList" class="btn btn-default btn-lg" onclick="javascript:viewList();return false;"><i class="fa fa-th-list"></i>목록</button>
		</p>
        </div>
        <!-- /#page-wrapper -->

    </div>
    
    <!-- /#wrapper -->
</form>
</body>
<c:if test = "${fn:length(MSG) > 0 }">
<script>
alert("${MSG}");
location.href = '/admin/member/admauth_view.do';
</script>
</c:if>
<c:if test = "${CDD == 'SUCC' }">
<script>
	location.href = '/admin/member/admauth_view.do';
</script>
</c:if>
</html>
