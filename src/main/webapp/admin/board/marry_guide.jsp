<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	document.form01.action = '/admin/board/marry_list.do';//old
	document.form01.action = '/admin/board/marryguide_list.do';//new
	//document.getElementById('idx').value=idx;
	document.form01.submit();
	return false;
}

function modify_contents() {
	/* if(document.getElementById("dateCnt").value=="") {
		document.getElementById("dateCnt").value = document.getElementById('tbodyLectureDate').rows.length;
	} */
	if(document.getElementById("timeCnt").value=="") {
		document.getElementById("timeCnt").value = document.getElementById('tbodyLectureTime').rows.length;
	}
	
	document.form01.action = '/admin/board/marry_guide_modify.do';
	document.form01.submit();
	return false;
}

function addDate() {
    var my_tbody = document.getElementById('tbodyLectureDate');

    if(my_tbody.rows.length > 12){
    	alert('일정은 12개 이상 추가할 수 없습니다');
    } else {
	    var row = my_tbody.insertRow( my_tbody.rows.length ); // 하단에 추가
	    var cell1 = row.insertCell(0);
	    cell1.innerHTML = '<input class="lecture_date_class" type="text" id="lecture_date'+my_tbody.rows.length+'" name="lecture_date'+my_tbody.rows.length+'" maxlength="10">';
		$("#lecture_date"+my_tbody.rows.length).datepicker();
		$("input.lecture_date"+my_tbody.rows.length).datepicker().attr("maxlength", 10);
	    document.getElementById("dateCnt").value = my_tbody.rows.length;
    }
}

function DelDate() {
	if (confirm("강좌일정을 삭제하시겠습니까??") == true){    //확인	
		document.form01.action = '/admin/board/marry_guide_Datedelete.do';
		document.form01.submit();
	}
	return false;
}

function addTime() {
	var my_tbody = document.getElementById('tbodyLectureTime');

    var row = my_tbody.insertRow( my_tbody.rows.length ); // 하단에 추가
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    var cell3 = row.insertCell(2);
    cell1.innerHTML = '<input type="hidden" name="time_sno'+my_tbody.rows.length+'" value="'+my_tbody.rows.length+'">\n<input class="lecture_date_class" type="text" id="lecture_time'+my_tbody.rows.length+'" name="lecture_time'+my_tbody.rows.length+'">';
	cell2.innerHTML = '내용';
	cell3.innerHTML = '<input type="text" id="lecture_contents'+my_tbody.rows.length+'" name="lecture_contents'+my_tbody.rows.length+'">';
	document.getElementById("timeCnt").value = my_tbody.rows.length;
}

function DelTime() {
	if (confirm("강의시간을 삭제하시겠습니까??") == true){    //확인	
		document.form01.action = '/admin/board/marry_guide_Timedelete.do';
		document.form01.submit();
	}
	return false;
}

window.onload = function() {
	//alert("loading completed");
	//CKEDITOR.replace( 'edu_guide' );
	//var today = new Date();
	// active gnb
	onLoadLeftMenu('order_02');
}

// $.datepicker.setDefaults({
//     dateFormat: 'yy-mm-dd',
//     prevText: '이전 달',
//     nextText: '다음 달',
//     monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
//     monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
//     dayNames: ['일', '월', '화', '수', '목', '금', '토'],
//     dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
//     dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
//     showMonthAfterYear: true,
//     yearSuffix: '년'
// });
	//한글설정
    $.datepicker.regional['ko'] = {
        closeText: '닫기',
        prevText: '이전달',
        nextText: '다음달',
        currentText: '오늘',
        monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
        monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
        dayNames: ['일','월','화','수','목','금','토'],
        dayNamesShort: ['일','월','화','수','목','금','토'],
        dayNamesMin: ['일','월','화','수','목','금','토'],
        weekHeader: 'Wk',
        dateFormat: 'yy-mm-dd',
        firstDay: 0,
        isRTL: false,
        duration:200,
        showAnim:'show',
        showMonthAfterYear: true,
        yearSuffix: ''
    };
    $.datepicker.setDefaults($.datepicker.regional['ko']);
    $( ".lecture_date_class" ).datepicker({
    	changeMonth: true,
        changeYear: true,
        dateFormat:'yy-mm-dd',
        showButtonPanel: true
    });

</script>
<script src="/admin/_common/ckeditor/ckeditor.js"></script>
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
						<h2>카나혼인강좌 교육안내 수정</h2>
						</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
<form class="well" name="form01" action="/admin/board/marry_guide.do" method="POST">
	<input type="hidden" name="id" id="id" value="${admSessionMemId}"/>
	<input type="hidden" name="gno" id="gno" value="${marryGContents.MARRY_GUIDE_NO }"/>
	<input type="hidden" name="dateCnt" id="dateCnt" value=""/>
	<input type="hidden" name="timeCnt" id="timeCnt" value=""/>
  				<!-- Contents : Begin //-->
				<div class="page-form">
					<div class="panel panel-default">
						<div class="panel-body">
							
							<table class="table tbl-form">
								<colgroup>
									<col style="width:100px;">
									<col>
								</colgroup>
								<tbody>
									<!-- 
									<tr>
										<th><label for="">카나혼인강좌</label></th>
										<td>
											<textarea class="form-control h-100" id="explain" name="explain" rows="3">${marryGContents.EXPLAIN }</textarea>
										</td>
									</tr>
									<tr>
										<th><label for="">교육안내</label></th>
										<td>
											<textarea class="form-control h-200"  id="edu_guide" name="edu_guide" rows="10">${marryGContents.EDU_GUIDE }</textarea>
										</td>
									</tr>
									 -->
									<!-- 
									<tr>
										<th><label for="">강좌일정</label></th>
										<td><table id="tblLectureDate" border="1">
						   						<tr>
						   							<td>
						    							<button type="button" class="btn btn-default" onclick="javascript:DelDate();return false;">삭제</button>
						    							<button type="button" class="btn btn-default" onclick="javascript:addDate();return false;">추가</button>
						    						</td>
						    					</tr>
						    					<tbody id="tbodyLectureDate">
							      				<c:choose>
									   				<c:when test="${fn:length(marry_date)>0}">
									   					<c:forEach items="${marry_date}" var="list">
									   						<tr>
									   							<td>
									   								<input class="lecture_date_class" type="text" id="lecture_date${list.RNUM }" name="lecture_date${list.RNUM }" value="${list.LECTURE_DATE}">
									   							</td>
									   						</tr>		   						
							   							</c:forEach>
								   					</c:when>
							   					</c:choose>
							   					</tbody>
							      			</table>
							      		</td>
							      	</tr> -->
							      	<tr>
										<th><label for="">강의시간</label></th>
										<td><table id="tblLectureTime" border="1">
							   					<tr>
						   							<td>
						    							<button type="button" class="btn btn-default" onclick="javascript:DelTime();return false;">삭제</button>
						    							<button type="button" class="btn btn-default" onclick="javascript:addTime();return false;">추가</button>
						    						</td>
						    					</tr>
						    					<tbody id="tbodyLectureTime">
						      				<c:choose>
								   				<c:when test="${fn:length(marry_time)>0}">
								   					<c:forEach items="${marry_time}" var="tlist">
								   						<tr>
								   							<td>
								   								<input class="lecture_time_class" type="hidden" id="time_sno${tlist.RNUM }" maxlength="40" name="time_sno${tlist.RNUM }" value="${tlist.TIME_SNO}">
								   								<input class="lecture_time_class" type="text"   id="lecture_time${tlist.RNUM }" maxlength="40" name="lecture_time${tlist.RNUM }" value="${tlist.LECTURE_TIME}">
								   							</td>
								   							<td>내용</td>
								   							<td><input type="text"  id="lecture_contents${tlist.RNUM }" placeholder="" name="lecture_contents${tlist.RNUM }" value="${tlist.CONTENTS}"> </td>
								   						</tr>		   						
						   							</c:forEach>
							   					</c:when>
						   					</c:choose>
						   						</tbody>
						      			</table>
						      		</td>
						      	</tr>
						      	</tbody>
							</table>
						</div>
					</div>
					<!-- Buttons : Begin //-->
					<p class="pull-right">
						<button type="button" id="btnList"   class="btn btn-default pull-right" onclick="javascript: viewList();return false;">목록</button>
						<c:choose>
						<c:when test="${_params.query_type == 'insert'}">
							<button type="button" id="btnInsert" class="btn btn-default pull-right" onclick="javascript: insert_contents();return false;">등록</button>	
						</c:when>
						<c:otherwise>
							<button type="button" id="btnModify" class="btn btn-default pull-right" onclick="javascript: modify_contents();return false;">수정</button>
						</c:otherwise>
						</c:choose>
					</p>
					<!-- Buttons : End //-->

				</div>
				<!-- Contents : End //-->
				
	</form>
			
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->
</body>
</html>