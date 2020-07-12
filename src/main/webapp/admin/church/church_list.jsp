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
	var vfm = document.form01 ;
	vfm.pageNo.value=pageNo;
	vfm.submit();
    return false;
}

function goSearch() {
	var vfm = document.form01 ;
	if(vfm.schTextGubun.value == '' && vfm.searchText.value != '') {
		alert('검색구분을 선택하세요.');
		vfm.schTextGubun.focus();
		return false;
	}
	if(vfm.searchText.value == '' && vfm.schTextGubun.value != '') {
		alert('검색어를 입력하세요.');
		vfm.searchText.focus();
		return false;
	}
	vfm.submit();
    return false;
}

function goSearchM() {
	location.href="/admin/church/church_list.do";
}

function goSearchS() {
	location.href="/admin/church/gongso_list.do";
}

function insertContents(church_code) {
	var vfm = document.form01 ;
	vfm.action = '/admin/church/church_view.do';
	vfm.query_type.value = "insert";
	vfm.church_code.value = church_code;
	vfm.submit();
    return false;
}

function modifyContents(church_idx, org_idx, church_code) {
	var vfm = document.form01 ;
	vfm.action = '/admin/church/church_view.do';
	vfm.query_type.value = "modify";
	vfm.church_idx.value = church_idx;
	vfm.org_idx.value = org_idx;
	vfm.church_code.value = church_code;
	vfm.submit();
    return false;
}

function deleteContents(church_idx, org_idx, church_code) {
	if(confirm("정말 삭제하시겠습니까?")==true) {
		var vfm = document.form01 ;
		vfm.action = '/admin/church/church_delete.do';
		vfm.church_idx.value = church_idx;
		vfm.org_idx.value = org_idx;
		vfm.church_code.value = church_code;
		vfm.submit();
	}
    return false;
}

window.onload = function () {
	onLoadLeftMenu('info_04');
}

$(document).ready(function(){
/* 	$(".nav-tabs a").click(function(){
        $(this).tab('show');
    });
 */
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

<!-- top start(left menu include) -->
<%@ include file="/admin/_common/inc/top.jsp" %>
<!-- top end -->


<form class="form-inline" name="form01" action="/admin/church/church_list.do" method="POST">
<input type="hidden" name="pageNo" id="pageNo" value="" />
<input type="hidden" name="active" id="active" value="" />
<input type="hidden" name="query_type" id="query_type" value="" />
<input type="hidden" name="church_idx" id="church_idx" value=""/>
<input type="hidden" name="org_idx" id="org_idx" value=""/>
<input type="hidden" name="church_code" id="church_code" value=""/>


<!-- main start -->        
<!-- Page Content -->
<div id="page-wrapper">
	<ul class="nav nav-tabs">
		<li class="active"><a data-toggle="tab" href="javascript:goSearchM()">본당 관리</a></li>
		<li><a data-toggle="tab" href="javascript:goSearchS()">공소 관리</a></li>
	</ul>
    <div class="tab-content">
	<!--  첫번째 탭 -->
				<!-- Contents : Begin //-->
<!-- 				<div class="page-list"> -->
					<!-- @@@ Search FieldSet : begin //-->
					<div class="panel panel-default">
                    	<div class="panel-heading">
                    		<label for="q_word">검색</label>
                    		<select class="form-control form-control-short w-200 mr-10" name="schTextGubun" id="schTextGubun">
							  <option value="">전체</option>
							  <option value="group" <c:if test="${_params.schTextGubun=='group' }" >selected</c:if>>소속지구</option>
							  <option value="church" <c:if test="${_params.schTextGubun=='church' }" >selected</c:if>>성당명</option>
							</select>
							<input class="form-control form-control-short" type="text" name="searchText" id="searchText" value="${_params.searchText}"/>
							<!-- <button type="button" class="btn btn-primary" onclick="javascript:insertContents();return false;"><i class="fa fa-pencil"></i>성당등록</button> -->
			                <button type="button" class="btn btn-primary" onclick="javascript:goSearch();return false;"><i class="fa fa-search"></i>검색</button>						
						</div>
					</div>
					<!-- @@@ Search FieldSet : end //-->

						<!-- /.row -->
				        <div class="panel-body">
				        	<table width="100%" class="table" id="dataTables-example">
								<thead>
							    	<tr>
							        <th>NO</th>
							        <th>소속지구</th>
							        <th>성당명</th>
							        <th>전화번호</th>
							        <th>FAX</th>
							        <th>이메일</th>
							        <th>수정</th>
							        <th>삭제</th>			        
							      	</tr>
							    </thead>
								<tbody>
								<c:choose>
									<c:when test="${fn:length(admChurchList) > 0}">
										<c:forEach items="${admChurchList}" var="list">
					                    	<tr <c:if test="${list.DEL_YN eq 'Y'}">style="color: darkgray; text-decoration:line-through;"</c:if> >
					                        <td>${list.RNUM} </td>
					                        <td>${list.RIDX_GIGUNAME} </td>
					                        <td>${list.NAME}</td>
					                        <td>${list.TEL} </td>
					                        <td>${list.FAX} </td>
					                        <td>${list.EMAIL} </td>
				                            <td>
				                            <c:choose>
											<c:when test="${list.MODE=='I'}">
					                            <a href="javascript: insertContents('${list.DEPART_CODE}')">등록</a>
											</c:when>
											<c:otherwise>
					                            <a href="javascript: modifyContents('${list.CHURCH_IDX}','${list.ORG_IDX}','${list.DEPART_CODE}')">수정</a>
											</c:otherwise>
											</c:choose>
				                            </td>
					                        <td>
					                        <c:choose>
											<c:when test="${list.MODE=='I'}">
					                            -
											</c:when>
											<c:otherwise>
												<c:if test="${list.DEL_YN eq 'Y'}">
													<a href='/admin/org/org_code_mgmt.do'><font style="color:#a8a8a8" title="임지코드 관리에서 관리합니다.">복원</font></a>
												</c:if>
												<c:if test="${list.DEL_YN ne 'Y'}">
													<a href="javascript:deleteContents('${list.CHURCH_IDX}','${list.ORG_IDX}','${list.DEPART_CODE}')">삭제</a>
												</c:if>
					                        </c:otherwise>
											</c:choose>
					                        </td>
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
