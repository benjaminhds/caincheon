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
// 등록
function insertContents() {
	var baseYear = new Date().getFullYear();
	var sdate = document.querySelector("#startDate").value.replace("-","").replace("-","");
	if(sdate=="" || sdate.length!=8) {
		alert("입력한 날자 형식이 올바르지 않습니다.");
		return false;
	} else if (parseInt(sdate.substring(0,4)) < baseYear) {
		alert("입력한 년도는 "+baseYear+"과 같은 년도이거나, "+baseYear+"년도 이후 이어야 합니다.");
		return false;
	}
	var vfm = document.form02;
	vfm.action = '/admin/board/marryguide_insert.do';
	vfm.edu_date_start.value = sdate;
	vfm.edu_date_end.value = sdate;
	if(vfm.type.value == "${_params.type}") { //<!-- 약혼자주말강좌라면 -->
		/* y = sdate.substring(0,4);
		m = sdate.substring(4,6);
		d = sdate.substring(6); */
		vfm.edu_date_end.value = sdate;
	}
	vfm.submit();
    return false;
}

// 수정
function modifyContents(marryguide_no, close_yn) {
	var vfm = document.form02;
	vfm.action = '/admin/board/marryguide_update.do';
	vfm.marryguide_no.value=marryguide_no;
	vfm.close_yn.value= close_yn=="Y" ? "N":"Y"; //마감 or 마감취소
	vfm.submit();
    return false;
}

// 삭제
function deleteContents(marryguide_no, type, del_yn, receiptCount) {
	if(del_yn == 'N' && receiptCount > 0) {
		alert("신청가 있다면, 삭제 할 수 없습니다.");
		return false;
	}
	var del_msg = del_yn == 'Y' ? "[삭제 취소] 하시겠습니까 ?":"[삭제] 하시겠습니까 ?";
	if (confirm(del_msg) == true){    //확인
		var vfm = document.form03;
		vfm.action = '/admin/board/marryguide_delete.do';
		vfm.type.value=type;
		vfm.marryguide_no.value=marryguide_no;
		vfm.del_yn.value=(del_yn == 'Y' ? "N":"Y");
		vfm.submit();
	}
    return false;
}

//카나혼인강좌
function goMarry() {
	var vfm = document.form01;
	vfm.type.value="1";
	vfm.submit();
	return false;
}
//주말약혼자강좌
function goEngage() {
	var vfm = document.form01;
	vfm.type.value="2";
	vfm.submit();
	return false;
}
// 강좌 시간표 관리 :: 카나혼과 주말약혼자 하루 강좌 스케쥴이 동일
function goTimeSchedule() {
	var vfm = document.form01;
	vfm.action = "/admin/board/marry_guide.do";
	vfm.type.value="2";
	vfm.submit();
	return false;
}

//
window.onload = function () {
	// active gnb
	onLoadLeftMenu('order_02');
	
	<c:if test="${fn:length(ERR_MSG) > 0}">
	// error message
	alert("${ERR_MSG}");
	</c:if>
}
</script>

<body>
<form class="form-group" name="form01" action="/admin/board/marryguide_list.do" method="POST">
<input type="hidden" name="type" value="${_params.type }" />
</form>
<form class="form-group" name="form02" action="/admin/board/marryguide_update.do" method="POST">
<input type="hidden" name="type" value="${_params.type }" />
<input type="hidden" name="marryguide_no" value="" />
<input type="hidden" name="edu_date_start" value="" />
<input type="hidden" name="edu_date_end" value="" />
<input type="hidden" name="recruitment_personnel" value="90" />
<input type="hidden" name="close_yn" value="N" />
<input type="hidden" name="del_yn" value="N" />
<input type="hidden" name="explain" value=" " />
<input type="hidden" name="edu_guide" value=" " />
</form>
<form class="form-group" name="form03" action="/admin/board/marryguide_delete.do" method="POST">
<input type="hidden" name="marryguide_no" value="" />
<input type="hidden" name="del_yn" value="Y" />
<input type="hidden" name="type" value="1" />
</form>
     	<!-- top start(left menu include) -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- top end -->
        
        <!-- main start -->        
        <!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
                    <h3 class="page-header"><c:if test="${_params.type eq '1'}">카나혼인</c:if><c:if test="${_params.type eq '2'}">약혼자주말</c:if> 강좌관리</h3>
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
							<table width="100%">
							<colgroup>
								<col style="width:120px;">
								<col>
								<col style="width:120px;">
								<col>
								<col>
							</colgroup>
								<tr>
		    						<th><label for="inputName">강좌 일정 추가 &nbsp; </label></th>
		    						<td colspan="2">
		    							
	    								<span>
	    								<input class="form-control" type="date" data-date-format="YYYY-MM-DD" id="startDate" name="startDate" size="10" value="" required pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}" />
	    								<button type="button" onclick="javascript: insertContents()">추가</button>
	    								</span>
		      							
		    						</td>
		    						<td colspan="2">
		    						<button type="button" class="btn btn-default" onclick="javascript: goTimeSchedule();">강의 시간표<br> 안내 수정</button>&nbsp;
		    						<button type="button" class="btn btn-default" onclick="javascript: goMarry();">카나혼인강좌<br> 교육안내수정</button>&nbsp;
	    							<button type="button" class="btn btn-default" onclick="javascript: goEngage();return false;">약혼자주말<br> 교육안내수정</button>&nbsp;
		    						</td>
	    						</tr>
	    						
    						</table>
    						</div>
						<!--  Search : end //-->
					</div>
					<!-- @@@ Search FieldSet : End //-->

                    <div class="panel panel-default">
                        <!-- /.panel-heading -->
                        <div class="panel-body">
							<table class="table table-striped table-bordered table-hover">
						    <thead>
						      <tr>
						        <th>NO</th>
						        <th>구분</th>
						        <th>강좌 날짜</th>
						        <th>모집 인원</th>
						        <th>상태</th>
						        <th>수정</th>
						        <th>삭제</th>
						      </tr>
						    </thead>
						    <tbody>
						    	<c:choose>
								<c:when test="${fn:length(LIST) > 0}">
								<c:forEach items="${LIST}" var="list">
		                              <tr  style="text-align: center; <c:if test='${list.CLOSE_YN eq "Y"}'>color: darkgray;</c:if> <c:if test='${list.DEL_YN eq "Y"}'>text-decoration: line-through; color: red</c:if>">
		                                  <td>${list.RNUM} </td>
		                                  <td>${list.TYPE_TEXT} </td>
		                                  <td>${list.EDU_DATE_START} </td>
		                                  <td>${list.RECEIPT_COUNT} / ${list.RECRUITMENT_PERSONNEL} </td>
		                                  <td>${list.CLOSE_YN_TEXT} </td>
	                                   	  <td><a href="javascript: modifyContents('${list.MARRY_GUIDE_NO}','${list.CLOSE_YN}')">${list.CLOSE_YN_TEXT} 취소</a></td>
		                                  <td><a href="javascript: deleteContents('${list.MARRY_GUIDE_NO}','${list.TYPE}','${list.DEL_YN}', ${list.RECEIPT_COUNT})">삭제 <c:if test='${list.DEL_YN eq "Y"}'>취소</c:if></a></td>
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
                    			<script type="text/javascript">$("#paging .pagination").hide()</script>
                            </div>
                        </div>
				<!-- Contents : End //-->

			</div>
			<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->

</body>
<iframe id="hidden" name="hiddenifr" cellspacing="0" style="width: 0px; height: 0px; border=0" src=""></iframe>
</html>
