<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/admin/_common/inc/head.jsp" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script type="text/javascript">
function chgDate1() {
	var start_date = document.getElementById('fdate1').value;
	var end_date   = document.getElementById('tdate1').value;
	
	if(start_date!= "" && end_date!= "") {
		if(start_date>end_date) {
			alert("끝나는 날짜는 시작하는 날짜보다 나중이어야 합니다.");
			document.getElementById('tdate1').value = null;
		}
	}
	
	return false;
}

function chgDate2() {
	var start_date = document.getElementById('fdate2').value;
	var end_date   = document.getElementById('tdate2').value;
	
	if(start_date!= "" && end_date!= "") {
		if(start_date>end_date) {
			alert("끝나는 날짜는 시작하는 날짜보다 나중이어야 합니다.");
			document.getElementById('tdate2').value = null;
		}
	}
	
	return false;
}

function chgDate3() {
	var start_date = document.getElementById('fdate3').value;
	var end_date   = document.getElementById('tdate3').value;
	
	if(start_date!= "" && end_date!= "") {
		if(start_date>end_date) {
			alert("끝나는 날짜는 시작하는 날짜보다 나중이어야 합니다.");
			document.getElementById('tdate3').value = null;
		}
	}
	
	return false;
}

function chgDate4() {
	var start_date = document.getElementById('fdate4').value;
	var end_date   = document.getElementById('tdate4').value;
	
	if(start_date!= "" && end_date!= "") {
		if(start_date>end_date) {
			alert("끝나는 날짜는 시작하는 날짜보다 나중이어야 합니다.");
			document.getElementById('tdate4').value = null;
		}
	}
	
	return false;
}

function chgDate5() {
	var start_date = document.getElementById('fdate5').value;
	var end_date   = document.getElementById('tdate5').value;
	
	if(start_date!= "" && end_date!= "") {
		if(start_date>end_date) {
			alert("끝나는 날짜는 시작하는 날짜보다 나중이어야 합니다.");
			document.getElementById('tdate5').value = null;
		}
	}
	
	return false;
}

function chgDate6() {
	var start_date = document.getElementById('fdate6').value;
	var end_date   = document.getElementById('tdate6').value;
	
	if(start_date!= "" && end_date!= "") {
		if(start_date>end_date) {
			alert("끝나는 날짜는 시작하는 날짜보다 나중이어야 합니다.");
			document.getElementById('tdate6').value = null;
		}
	}
	
	
	return false;
}


function viewList() {
	document.form01.action = '/admin/board/marry_list.do';
	//document.getElementById('idx').value=idx;
	document.form01.submit();
	return false;
}

function modify_contents() {
	chgDate1();
	chgDate2();
	chgDate3();
	chgDate4();
	chgDate5();
	chgDate6();
	
	document.form01.action = '/admin/board/engage_guide_modify.do';
	//document.getElementById('idx').value=idx;
	document.form01.submit();
	return false;
}

window.onload = function() {
	//alert("loading completed");
	//CKEDITOR.replace( 'edu_guide' );
	var today = new Date();
	// active gnb
	onLoadLeftMenu('order_02');
}

$(function() {
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
						<h2>약혼자 주말 교육안내 수정</h2>
						</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
  <form class="well" name="form01" action="/admin/board/engage_guide.do" method="POST">
  	<input type="hidden" name="approve_yn" id="approve_yn" value="N"/>
  	<input type="hidden" name="member_type" id="member_type" value="1"/>
  	<input type="hidden" name="sex" id="sex" value="1"/>
  	<input type="hidden" name="birth_type" id="birth_type" value="1"/>
  	<input type="hidden" name="id" id="id" value="${admSessionMemId}"/>
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
										<th><label for="">약혼자주말이란?</label></th>
										<td>
											<textarea class="form-control h-200"  rows="3" readonly>${engage_guide.EXPLAIN}</textarea>
										</td>
									</tr>
									<tr>
										<th><label for="">교육안내</label></th>
										<td>
											<textarea class="form-control h-200"  id="edu_guide" name="edu_guide" rows="10">${engage_guide.EDU_GUIDE}</textarea>
										</td>
									</tr>
									 -->
									<tr>
										<th><label for="">교육일정</label></th>
										<td><table border="0">
											<tr>
					   							<td>
					   								<input class="lecture_date_class" type="text" id="fdate1" name="fdate1" maxlength="10" value="${engage_guide.FDATE1}" onchange="chgDate1()">
					   								<label for="">  ~  </label>
				   									<input class="lecture_date_class" type="text" id="tdate1" name="tdate1" maxlength="10" value="${engage_guide.TDATE1}" onchange="chgDate1()">
					   							</td>
					   						</tr>	
					   						<tr>
					   							<td>
					   								<input class="lecture_date_class" type="text" id="fdate2" name="fdate2" maxlength="10" value="${engage_guide.FDATE2}" onchange="chgDate2()">
					   								<label for="">  ~  </label>
				   									<input class="lecture_date_class" type="text" id="tdate2" name="tdate2" maxlength="10" value="${engage_guide.TDATE2}" onchange="chgDate2()">
					   							</td>
					   						</tr>
					   						<tr>	
					   							<td>
					   								<input class="lecture_date_class" type="text" id="fdate3" name="fdate3" maxlength="10" value="${engage_guide.FDATE3}" onchange="chgDate3()">
					   								<label for="">  ~  </label>
				   									<input class="lecture_date_class" type="text" id="tdate3" name="tdate3" maxlength="10" value="${engage_guide.TDATE3}" onchange="chgDate3()">
					   							</td>
					   						</tr>
					   						<tr>	
					   							<td>
					   								<input class="lecture_date_class" type="text" id="fdate4" name="fdate4" maxlength="10" value="${engage_guide.FDATE4}" onchange="chgDate4()">
					   								<label for="">  ~  </label>
				   									<input class="lecture_date_class" type="text" id="tdate4" name="tdate4" maxlength="10" value="${engage_guide.TDATE4}" onchange="chgDate4()">
					   							</td>
					   						</tr>
					   						<tr>	
					   							<td>
					   								<input class="lecture_date_class" type="text" id="fdate5" name="fdate5" maxlength="10" value="${engage_guide.FDATE5}" onchange="chgDate5()">
					   								<label for="">  ~  </label>
				   									<input class="lecture_date_class" type="text" id="tdate5" name="tdate5" maxlength="10" value="${engage_guide.TDATE5}" onchange="chgDate5()">
					   							</td>
					   						</tr>
					   						<tr>	
					   							<td>
					   								<input class="lecture_date_class" type="text" id="fdate6" name="fdate6" maxlength="10" value="${engage_guide.FDATE6}" onchange="chgDate6()">
					   								<label for="">  ~  </label>
				   									<input class="lecture_date_class" type="text" id="tdate6" name="tdate6" maxlength="10" value="${engage_guide.TDATE6}" onchange="chgDate6()">
					   							</td>
					   						</tr>	
							      			</table>
							      		</td>
							      	</tr>							      	
						      	</tbody>
							</table>
						</div>
					</div>
					<!-- Buttons : Begin //-->
					<p class="pull-right">
						<button type="button" id="btnList"   class="btn btn-default pull-right" onclick="javascript:viewList();return false;">목록</button>
						<c:choose>
						<c:when test="${_params.query_type == 'insert'}">
							<button type="button" id="btnInsert" class="btn btn-default pull-right" onclick="javascript:insert_contents();return false;">등록</button>	
						</c:when>
						<c:otherwise>
							<button type="button" id="btnModify" class="btn btn-default pull-right" onclick="javascript:modify_contents();return false;">수정</button>
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