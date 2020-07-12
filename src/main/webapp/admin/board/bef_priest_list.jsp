<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/admin/_common/inc/head.jsp" %>
<%
if(admSessionMemId==null||admSessionMemId.equals("")) {                // id가 Null 이거나 없을 경우
	response.sendRedirect("/admin/login.jsp");    // 로그인 페이지로 리다이렉트 한다.
}
%>
<style>
    .panel-layer {
        display: none;
        position: absolute;
        top: 25%;
        left: 25%;
        width: 50%;
        height: 50%;
        padding: 16px;
        border: 16px solid orange;
        background-color: white;
        z-index:1002;
        overflow: auto;
    }
</style>
</head>

<script type="text/javascript">
function goPage(pageNo) {
	document.getElementById('pageNo').value=pageNo;
	document.form01.submit();
    return false;
}

function goSearch() {
	//alert("goSearch2()");
	//if(document.getElementById('searchText').value == '') {
	//	alert('검색할 문자를 입력하세요.');
	//	document.getElementById('searchText').focus();
	//	return false;
	//}
	document.form01.submit();
    return false;
}

function show_Tomb() {
	document.getElementById('panel-layer').style.display='block';
}

function hide_Tomb() {
	document.getElementById('panel-layer').style.display='none';
}

// 선종사제 >> 묘소 추가를 위한  table row 동적 UI 생성
function add_Tomb_row(){
	// pre_set 에 있는 내용을 읽어와서 처리..
	var my_tbody = document.getElementById('tbody-brial');
    // var row = my_tbody.insertRow(0); // 상단에 추가
    // 한번에 하나의 row만 추가하기 위해서...
    var check = document.getElementById('add-row');
    if (check==null) {
    	var row = my_tbody.insertRow( my_tbody.rows.length ); // 하단에 추가
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);
        var cell4 = row.insertCell(3);

        cell1.innerHTML = my_tbody.rows.length;

        cell2.align     = "left";
        cell2.innerHTML = "<input  type=text  name=new_brial_name align=absmiddle >";
        //cell3.innerHTML = "<button type=button class=btn btn-default onclick=javascript:del_brial();return false;>삭제</button> "
        cell3.innerHTML = "<a id=\"add-row\" href=\"javascript:insert_Tomb()\">저장</a>"
       	cell4.innerHTML = "<a href=\"javascript: del_Tomb_row(this)\">삭제</a>"

        //cell3.innerHTML = "<a href="javascript:del_brial('${list.bp_idx}')">삭제</a>";
    }
}
//선종사제 >> 묘소 목록에서 삭제
function del_Tomb_row(x) {
	//var v = $(this).closest('tr').index();
	//alert(v);
    var my_tbody = document.getElementById('tbody-brial');
    if (my_tbody.rows.length < 1) return;
    alert(my_tbody.rows.length);
    alert(x.rowIndex);
    //my_tbody.deleteRow(0); // 상단부터 삭제
    my_tbody.deleteRow( my_tbody.rows.length-1 ); // 하단부터 삭제
    //x.deleteRow(x.rowIndex);//현재 row삭제
}
//선종사제 >> 묘소 추가를 위한  table row 동적 UI에서 묘소정보 DB 등록 요청 event
function insert_Tomb() {
	document.form01.action = '/admin/board/tomb_insert.do';
	document.form01.submit();
	return false;
}
//선종사제 >> 묘소삭제
function delete_Tomb(no) {
	if(confirm("정말 삭제하시겠습니까?")==true) {
		document.form01.action = '/admin/board/tomb_delete.do';
		document.getElementById('brial_place_no').value = no;
		document.form01.submit();
	}
	return false;
}

function insertContents() {
	document.form01.action = '/admin/board/bef_priest_view.do';
	document.getElementById('query_type').value = "insert";
	document.form01.submit();
    return false;
}

function modifyContents(bp_idx) {
	//alert("modifyContents");
	document.form01.action = '/admin/board/bef_priest_view.do';
	document.getElementById('query_type').value = "modify";
	document.getElementById('bp_idx').value=bp_idx;
	document.form01.submit();
    return false;
}

function deleteContents(bp_idx) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/board/bef_priest_delete.do';
		document.getElementById('bp_idx').value=bp_idx;
		document.form01.submit();
	}
    return false;
}

window.onload = function () {
	//alert("로딩 완료");
//	document.getElementById("delete_n").checked = true;
	document.getElementById('panel-layer').style.display='none';

}
</script>

<body>
<form class="form-group" name="form01" action="/admin/board/bef_priest_list.do" method="POST">
<input type="hidden" name="brial_place_no" id="brial_place_no" value=""/>
<input type="hidden" name="query_type" id="query_type" value=""/>
<input type="hidden" name="bp_idx" id="bp_idx" value=""/>
<input type="hidden" name="pageNo" id="pageNo" value="${_params.pageNo }"/>
     <!-- top start(left menu include) -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- top end -->

        <!-- main start -->
        <!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h3 class="page-header">선종사제목록</h3>
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
		    					<table width="100%" border="0">
		    					<tr>
									<td>
										<label for="q_word">검색 &nbsp; </label>
									</td>
									<td>
										<select name="searchGubun" id="searchGubun" onChange="javascript:selectGubun()">
		    								<c:choose>
		    									<c:when test="${searchGubun=='1'}">
		    										<option value="1" selected="true" >이름</option>
													<option value="2" >세례명</option>
		    									</c:when>
		    									<c:when test="${searchGubun=='2'}">
		    										<option value="1" >이름</option>
													<option value="2" selected="true" >세례명</option>
		    									</c:when>
		    									<c:otherwise>
		    										<option value="1" selected="true" >이름</option>
													<option value="2" >세례명</option>
		    									</c:otherwise>
		    								</c:choose>
										</select>
									</td>
									<td>
										<input class="form-control form-control-short" type="text" name="searchText" id="searchText" value="${searchText}">
									</td>
									<td>
		    							<button type="button" class="btn btn-default" onclick="javascript:show_Tomb();return false;">묘소관리</button>
		    							<button type="button" class="btn btn-default" onclick="javascript:insertContents();return false;">등록</button>
										<button type="button" class="btn btn-default" onclick="javascript:goSearch();return false;">검색</button>
									</td>
		    					</tr>
		    					</table>
	    					</div>
  					    </div>
  					    <div class="panel-layer" id="panel-layer">
  					    	<div class="container" style="width:550px;">
		    					<table width="100%" border="0">
			    					<tr>
										<td>
											<label for="">묘소목록 &nbsp; </label>
										</td>
										<td align="right">
											<button type="button" class="btn btn-default" onclick="javascript:add_Tomb_row();return false;">추가</button>
											<button type="button" class="btn btn-default" onclick="javascript:hide_Tomb();return false;">닫기</button>
										</td>
			    					</tr>
		    					</table>
	    					</div>
							<table width="100%" class="table" id="brial_place">
							    <thead>
							      <tr>
							        <th>NO</th>
							        <th>묘소명</th>
							        <th>저장</th>
							        <th>삭제</th>
							      </tr>
							    </thead>
							    <tbody id="tbody-brial">

							    	<c:choose>
										<c:when test="${fn:length(brialPlaceList) > 0}">
										<c:forEach items="${brialPlaceList}" var="list">
				                              <tr>
				                                  <td>${list.RNUM} </td>
				                                  <td>${list.BRIAL_PLACE_NAME} </td>
				                                  <td>&nbsp;</td>
				                                  <td><a href="javascript:delete_Tomb('${list.BRIAL_PLACE_NO}')">삭제</a></td>
				                              </tr>
				                              </c:forEach>
										</c:when>
									</c:choose>
							    </tbody>
							  </table>
							  <div class="container" style="width:550px;">
		    					<table width="100%" border="0">
			    					<tr>
										<td align="right">
											<button type="button" class="btn btn-default" onclick="javascript:hide_Tomb();return false;">취소</button>
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
			              <table width="100%" class="table" id="dataTables-example">
						    <thead>
						      <tr>
						        <th>NO</th>
						        <th>사제명</th>
						        <th>세례명</th>
						        <th>묘소</th>
						        <th>서품일</th>
						        <th>선종일</th>
						        <th>수정</th>
						        <th>삭제</th>
						      </tr>
						    </thead>
						    <tbody>
						      <c:choose>
									<c:when test="${fn:length(befPriestList) > 0}">
									<c:forEach items="${befPriestList}" var="list">
			                              <tr>
			                                  <td>${list.RNUM} </td>
			                                  <td>${list.NAME} </td>
			                                  <td>${list.CHRISTIAN_NAME} </td>
			                                  <td>${list.BRIAL_PLACE} </td>
			                                  <td>${list.ORDINATION} </td>
			                                  <td>${list.DEAD} </td>
			                                  <td><a href="javascript:modifyContents('${list.BP_IDX}')">수정</a></td>
			                                  <td><a href="javascript:deleteContents('${list.BP_IDX}')">삭제</a></td>
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
