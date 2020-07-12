<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%@ 
	include file="/admin/_common/inc/head.jsp" %><%
	
	
	// id가 Null 이거나 없을 경우 -> 로그인페이지로 이동
	if(admSessionMemId==null||admSessionMemId.equals("")) {
		response.sendRedirect("/admin/login.jsp");
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

function insertContents(depart_code, depart_idx, lv1, lv2) {
	var vfm = document.form01;
	vfm.action = '/admin/board/departdc_view.do';
	vfm.query_type.value = 'insert';
	vfm.depart_code.value = depart_code;
	vfm.depart_idx.value = depart_idx;
	vfm.lv1.value = lv1;
	vfm.lv2.value = lv2;
	vfm.submit();
    return false;
}

function modifyContents(depart_idx, org_idx, lv1, lv2, lv3) {
	var vfm = document.form01;
	vfm.action = '/admin/board/departdc_view.do';
	vfm.query_type.value = "modify";
	vfm.depart_idx.value = depart_idx;
	vfm.org_idx.value = org_idx;
	vfm.lv1.value = lv1;
	vfm.lv2.value = lv2;
	vfm.lv3.value = lv3;
	vfm.submit();
    return false;
}

function deleteContents(depart_idx, org_idx) {
	var vfm = document.form01;
	vfm.action = '/admin/board/departdc_delete.do';
	vfm.depart_idx.value = depart_idx;
	vfm.org_idx.value = org_idx;
	vfm.submit();
    return false;
}

window.onload = function () {
	//alert("로딩 완료");
	onLoadLeftMenu('info_02');
	
}

//취소선처리
function stikeline() { 
	return $(this).children("td").each(function (index) { 
		$(this).children().attr("disabled", "disabled").children().attr("disabled", "disabled"); 
		if (index == 0) {
			//중요한 부분 
			$(this).children("*:first").before("<div style='position:absolute;width:100%;padding-top: 12px;'><div style='outline:#000 solid 1px; width:96%;'></div></div>");
			//5 
		} 
		}); 
}
</script>

<body>
<form class="form-inline" name="form01" action="/admin/board/departdc_list.do" method="POST">
<input type="hidden" name="pageNo"  id="pageNo" value="" />
<input type="hidden" name="query_type"  id="query_type" value="" />
<input type="hidden" name="depart_code" id="depart_code" value=""/>
<input type="hidden" name="depart_idx"  id="depart_idx" value=""/>
<input type="hidden" name="org_idx"     id="org_idx" value=""/>
<input type="hidden" name="lv1" value=""/>
<input type="hidden" name="lv2" value=""/>
<input type="hidden" name="lv3" value=""/>
    <div id="wrapper">
        <!-- Navigation -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- Page Content -->
        
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">교구청 부서목록</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">	
            <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">		  
						  <div class="form-group">						  	
						    <label for="sel1">검색</label>
							<input type="text" class="form-control" placeholder="부서명" name="searchText" id="searchText" value="${searchText}">
							&nbsp;&nbsp;&nbsp;		
						    <button type="button" class="btn btn-default" onclick="javascript:goSearch();return false;">검색</button>
						    ${fn:length(departdcList)}
						    <!-- <button type="button" class="btn btn-default" onclick="javascript:insertContents();return false;">등록</button> -->
						  </div>
						</div>
            <!-- /.row -->
			            <div class="panel-body">
			              <table width="100%" class="table" id="dataTables-example">
						    <thead>
						      <tr>
						        <th>NO</th>
						        <th>1Depth</th>
						        <th>2Depth</th>
						        <th>3Depth</th>
						        <th>부서명</th>
						        <th>수정</th>
						        <th>삭제</th>			        
						      </tr>
						    </thead>
						    <tbody>
						      <c:choose>
									<c:when test="${fn:length(departdcList) > 0}">
									<c:forEach items="${departdcList}" var="list">
			                              <tr <c:if test="${list.DEL_YN eq 'Y'}">style="color: darkgray; text-decoration:line-through; "</c:if> >
			                                  <td>${list.ORG_IDX} </td>
			                                  <td>${list.LV1} </td>
			                                  <td>${list.LV2} </td>
			                                  <td>${list.LV3} </td>
			                                  <td>${list.NAME} </td>
			                                   <td>
			                                   <c:choose>
												<c:when test="${list.MODE=='I'}">
						                            <a href="javascript: insertContents('${list.DEPART_IDX}', '${list.LV1}', '${list.LV2}')">등록</a>
												</c:when>
												<c:otherwise>
													<c:if test="${list.DEPART_CODE ne '000'}">
													<a href="javascript: modifyContents('${list.DEPART_IDX}', '${list.ORG_IDX}', '${list.LV1}', '${list.LV2}', '${list.LV3}')">수정</a>
													</c:if>
						                            <c:if test="${list.DEPART_CODE eq '000'}">
						                            <font style="color:#a8a8a8" title="임지코드 관리에서 관리합니다.">카테고리</font>
						                            </c:if>
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
													<c:if test="${list.DEL_YN eq 'N'}">
														<c:if test="${list.DEPART_CODE ne '000'}">
														<a href="javascript: deleteContents('${list.DEPART_IDX}', '${list.ORG_IDX}')">삭제</a>
														</c:if>
						                            	<c:if test="${list.DEPART_CODE eq '000'}">
						                            	<font style="color:#a8a8a8" title="임지코드 관리에서 관리합니다.">..</font>
						                            	</c:if>
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

<script>
<c:if test = "${fn:length(ERR_MSG) > 0 }">
	// Error Handling
	setTimeout( alert("${ERR_MSG}") , 1000 );
</c:if>
</script>

</html>
