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
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
    //본 예제에서는 도로명 주소 표기 방식에 대한 법령에 따라, 내려오는 데이터를 조합하여 올바른 주소를 구성하는 방법을 설명합니다.
    function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 도로명 조합형 주소 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }
                // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
                if(fullRoadAddr !== ''){
                    fullRoadAddr += extraRoadAddr;
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('postcode').value = data.zonecode; //5자리 새우편번호 사용
                document.getElementById('addr1').value = fullRoadAddr;
                document.getElementById('addr2').value = data.jibunAddress;

            }
        }).open();
    }
</script>
<script type="text/javascript">
function viewList() {
	document.form02.action = '/admin/church/gongso_list.do';
	document.form02.submit();
	return false;
}

function insert_contents() {
	if( $("#church_idx").val() == "") { alert("소속본당을 입력해 주세요."); $("#church_idx").focus(); return; }
	if( $("#name").val() == "") { alert("성명을 입력해 주세요."); $("#name").focus(); return; }
	if( $("#postcode").val() == "") { alert("주소를 입력해 주세요."); $("#postcode").focus(); return; }
	if( $("#addr1").val() == "") { alert("주소를 입력해 주세요."); $("#addr1").focus(); return; }
		
	document.form01.action = '/admin/church/gongso_insert.do';
	document.form01.submit();
	return false;
}

function modify_contents() {
	if( $("#church_idx").val() == "") { alert("소속본당을 입력해 주세요."); $("#church_idx").focus(); return; }
	if( $("#name").val() == "") { alert("성명을 입력해 주세요."); $("#name").focus(); return; }
	if( $("#postcode").val() == "") { alert("주소를 입력해 주세요."); $("#postcode").focus(); return; }
	if( $("#addr1").val() == "") { alert("주소를 입력해 주세요."); $("#addr1").focus(); return; }		

	document.form01.action = '/admin/church/gongso_modify.do';
	document.form01.submit();
	return false;
}

function delete_contents() {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/church/gongso_delete.do';
		document.form01.submit();
	}
	return false;
}
/* 
function insertMissa(g_idx,idx) {
	if( $("#query_type").val() == "insert") { alert("공소를 먼저 등록하세요."); return; }
	if( $("#upd_date").val() == "") { alert("미사시간 최종업데이트일자를 등록하세요."); return; }

	document.form01.action = '/admin/church/gongsoMissa_insert.do';
	document.getElementById('week').value = idx;
	document.form01.submit();
}

function deleteMissa(g_idx,idx) {
	if( $("#query_type").val() == "insert") { alert("공소를 먼저 등록하세요."); return; }
	
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form02.action = '/admin/church/gongsoMissa_delete.do';
		document.getElementById('week').value = idx;
		document.form02.submit();
	}
	return false;
} */


/**
 * ajax로 개별 추가하기
 */
function insertMissa(g_idx, idx) {
	//if( $("#query_type").val() == "insert") { alert("공소정보를 먼저 등록하세요."); return; }
	if( g_idx == "") { alert("공소정보를 먼저 등록하세요."); return; }
	
	if( $("#misa_hour"+idx).val()=='' ) {
		alert("미사 시간의 시간을 선택해주세요."); return;
	}
	if( $("#misa_min"+idx).val()=='' ) {
		alert("미사 시간의 분을 선택해주세요."); return;
	}
	
	//if( $("#upd_date").val() == "") { alert("미사시간 최종업데이트일자를 등록하세요."); return; }
	var imisaHoure = parseInt($("#misa_hour"+idx).val());
	var addedText = "";
	addedText += (imisaHoure < 12 ? "오전 "+imisaHoure:"오후 " +(imisaHoure>12 ? imisaHoure-12:12)) + "시 ";
	addedText += $("#misa_min"+idx).val()=="" || $("#misa_min"+idx).val()=="00" ? "": $("#misa_min"+idx).val()+"분 ";
	addedText += $("#txt"+idx).val()=="" ? "" : "("+$("#txt"+idx).val()+")";
	
	var formData = new FormData($('form01')[0]);
	formData.append("g_idx"       , g_idx );
	formData.append("week"        , idx );
	formData.append("missa_hour"  , $("#misa_hour"+idx).val());
	formData.append("missa_min"   , $("#misa_min"+idx).val());
	formData.append("txt"         , $("#txt"+idx).val());
	
	_requestByAjax('/admin/church/gongsoMissa_insert.do', formData, function(status, responseData){
											console.log("status="+status);
											console.log("responseData="+JSON.stringify(responseData));
											if(responseData.status=="success") {
												alert(responseData.message);
												var xx = "<span onclick=\"deleteMissa("+g_idx+",this,"+idx+")\" data-id=\""+responseData.gmi_idx+"\" id=\"mnamespan"+responseData.gmi_idx+"\" style=\"color: #0074e8; \"><U><B>"+addedText+"</B></U></span>";
												$("#misa_name"+idx).html($("#misa_name"+idx).text() + ", " + xx);
											}
										});
}

/**
 * ajax로 개별 삭제하기
 */
function deleteMissa(g_idx, obj, idx) {
	var gmi_idx = $(obj).data("id");
	if(g_idx == "" || gmi_idx=="") {
		alert("삭제 할 정보가 명확하지 않습니다.");
		return;
	}
	
	var formData = new FormData($('form01')[0]);
	formData.append("g_idx"   , g_idx );
	formData.append("gmi_idx" , gmi_idx );
	
	if(!confirm("\n\n선택한 미사 정보를 삭제하시겠습니까 ?")) {
		return;
	}
	
	_requestByAjax('/admin/church/gongsoMissa_delete.do', formData, function(status, responseData){
											console.log("status="+status);
											console.log("responseData="+JSON.stringify(responseData));
											if(responseData.status=="success") {
												alert(responseData.message);
												$(obj).remove();
											}
										});
	return false;
}
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
	<c:choose>
	<c:when test="${_params.query_type == 'insert'}">
		<h3 class="page-header">공소 등록</h3>
	</c:when>
	<c:otherwise>
		<h3 class="page-header">공소 수정</h3>	
	</c:otherwise>
	</c:choose>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<form id="form02" name="form02" method="POST" action="/admin/church/gongso_list.do">
<input type="hidden" name="pageNo" id="pageNo" value="${_params.pageNo }"/>
</form>
<form id="form01" name="form01" method="POST" action="/admin/church/gongso_list.do">
<input type="hidden" name="adminYn" id="adminYn" value="Y"/>
<input type="hidden" name="query_type" id="query_type" value="${_params.query_type }"/>
<input type="hidden" name="adm_id" id="adm_id" value="${admSessionMemId}"/>
<input type="hidden" name="week" id="week" value=""/>
	<!-- Contents : Begin //-->
	<div class="page-form">
		<div class="panel panel-default">
			<div class="panel-body">
				
				<table class="table tbl-form">
				<colgroup>
					<col style="width:120px;">
					<col>
				</colgroup>
				<tbody>
					<c:choose>
					<c:when test="${_params.query_type == 'modify'}">
					<tr>
						<th><label for="">공소순번 </label></th>
						<td>
						  <input type="text" name="g_idx" id="g_idx" value="${admGongsoContents.G_IDX}" readonly/>
						</td>
					</tr>
					</c:when>
					</c:choose>
					<tr>
						<th><label for="">소속본당</label></th>
						<td>
							<div class="row">
							<div class="col-md-4">
			    			<select class="form-control" name="church_idx" id="church_idx">
		                        <option value="">선택</option>
		                        <c:if test="${fn:length(_1x00xList) > 0}">
		                         <c:forEach var="dlist" items="${_1x00xList}" varStatus="status">
		                         	<option value="${dlist.CHURCH_IDX}" <c:if test = "${admGongsoContents.CHURCH_IDX==dlist.CHURCH_IDX }"> selected </c:if>>${dlist.NAME}</option>
		                         </c:forEach>
		                     </c:if>
		                    </select>
		                    </div>
			 				</div>
						</td>
					</tr>
					<tr>
						<th><label for="">공소명</label></th>
						<td>
			    			<input class="form-control" type="text"  id="name" placeholder="" name="name" value="${admGongsoContents.NAME}">		
						</td>
					</tr>
					<tr>
						<th><label for="">공소회장명</label></th>
						<td>
			    			<input class="form-control" type="text"  id="chief_name" placeholder="" name="chief_name" value="${admGongsoContents.CHIEF_NAME}">		
						</td>
					</tr>
					<tr>
						<th><label for="">비고</label></th>
						<td>
			    			<textarea class="form-control h-200" id="etc" name="etc" cols="80" rows="3">${admGongsoContents.ETC}</textarea>		
						</td>
					</tr>
					<tr>
						<th rowspan="2"><label for="">주소</label></th>
						<td>
							<div class="row">
							<div class="col-md-4">
			    			<input type="text" class="form-control col-sm-1" id="postcode" placeholder="" name="postcode" value="${admGongsoContents.POSTCODE}">		
			    			</div>
			    			<button type="button" onclick="execDaumPostcode()">우편번호 검색</button>
			    			</div>
						</td>
					</tr>
					<tr>
						<td>
			    			<input type="text" class="form-control col-sm-1" id="addr1" placeholder="" name="addr1" value="${admGongsoContents.ADDR1}">		
						</td>
					</tr>
					<tr>
					<th><label for="">위치(약도)</label></th>
						<td>
			    			<textarea class="form-control h-200" id="traffic" name="traffic" cols="80" rows="5">${admGongsoContents.TRAFFIC}</textarea>		
						</td>
					</tr>
					<!-- 
					<tr>
						<th><label for="">미사시간</label></th>
						<td colspan="3">
			    			<table>
			    			<tbody>
						      <c:choose>
									<c:when test="${fn:length(admGMissaList) > 0}">
									<c:forEach items="${admGMissaList}" var="list">
										<c:if test = "${list.WEEK_KEY=='7' }">
										   <tr>
			                                  <td>${list.WT} </td>
			                                  <td colspan="4">
			                                  	<input class="form-control" type="text" name="txt${list.WEEK_KEY }" id="txt${list.WEEK_KEY }"  value="${list.MNAME }"/>
			                                  </td>
			                               </tr>
			                               <tr>
			                               	  <td>최종업데이트일자</td>
			                               	  <td colspan="4">
			                               	  	<input type="text" id="upd_date" name="upd_date" maxlength="10" placeholder="" value="${admGongsoContents.UPD_DATE}">
			                               	  </td>
			                               </tr>
										</c:if>
										<c:if test = "${list.WEEK_KEY ne '7' }">
			                              <tr>
			                                  <td>${list.WT} </td>
			                                  <td>${list.MNAME} </td>
			                                  <td> 
			                                  	<select name="misa_hour${list.WEEK_KEY }" id="misa_hour${list.WEEK_KEY }">
			                                  		<option value="">시</option>
			                                  		<option value="00">00</option>
													<option value="01">01</option>
													<option value="02">02</option>
													<option value="03">03</option>
													<option value="04">04</option>
													<option value="05">05</option>
													<option value="06">06</option>
													<option value="07">07</option>
													<option value="08">08</option>
													<option value="09">09</option>
													<option value="10">10</option>
													<option value="11">11</option>
													<option value="12">12</option>
													<option value="13">13</option>
													<option value="14">14</option>
													<option value="15">15</option>
													<option value="16">16</option>
													<option value="17">17</option>
													<option value="18">18</option>
													<option value="19">19</option>
													<option value="20">20</option>
													<option value="21">21</option>
													<option value="22">22</option>
													<option value="23">23</option>
			                                  	</select>
			                                  	<select name="misa_min${list.WEEK_KEY }" id="misa_min${list.WEEK_KEY }">
			                                  		<option value="">분</option>
			                                  		<option value="00">00</option>
			                                  		<option value="10">10</option>
			                                  		<option value="20">20</option>
			                                  		<option value="30">30</option>
			                                  		<option value="40">40</option>
			                                  		<option value="50">50</option>
			                                  	</select>
			                                  </td>
			                                  <td>
			                                  	<input type="text" name="txt${list.WEEK_KEY }" id="txt${list.WEEK_KEY }"/>
			                                  </td>
			                                  <td>
			                                  <a href="javascript:insertMissa('${admGongsoContents.G_IDX}','${list.WEEK_KEY}')">미사추가</a>
			                                  <a href="javascript:deleteMissa('${admGongsoContents.G_IDX}','${list.WEEK_KEY}')">미사삭제</a>
			                                  </td>
			                              </tr>
			                              </c:if>
			                              </c:forEach>
									</c:when>
								</c:choose>		      
						    </tbody>
			    			</table>		
						</td>
					</tr>
					-->
					<tr>
						<th><label for="">미사시간</label></th>
						<td colspan="3">
			    			<table width="100%" cellpadding="30px">
			    			<tbody>
			    				<tr>
                                  <td>&nbsp;</td>
                                  <td colspan="4"><label style="color:Orange">요일별 미사 시간만 즉시 반영됩니다.</label></td>
                               </tr>
						      <c:choose>
									<c:when test="${fn:length(admGMissaList) > 0}">
									<c:forEach items="${admGMissaList}" var="list">
										<c:if test = "${list.WEEK_KEY ne '8' }">
			                              <tr>
			                                  <th align="center"><span style="background-color:lightGray; width:80%"> &nbsp; ${list.WT} &nbsp;</sapn></th>
			                                  <td><span id="misa_name${list.WEEK_KEY }">${list.MNAME}</span></td>
			                                  <td> 
			                                  	<select name="misa_hour${list.WEEK_KEY }" id="misa_hour${list.WEEK_KEY }">
			                                  		<option value="">시</option>
			                                  		<option value="00">00</option>
													<option value="01">01</option>
													<option value="02">02</option>
													<option value="03">03</option>
													<option value="04">04</option>
													<option value="05">05</option>
													<option value="06">06</option>
													<option value="07">07</option>
													<option value="08">08</option>
													<option value="09">09</option>
													<option value="10">10</option>
													<option value="11">11</option>
													<option value="12">12</option>
													<option value="13">13</option>
													<option value="14">14</option>
													<option value="15">15</option>
													<option value="16">16</option>
													<option value="17">17</option>
													<option value="18">18</option>
													<option value="19">19</option>
													<option value="20">20</option>
													<option value="21">21</option>
													<option value="22">22</option>
													<option value="23">23</option>
			                                  	</select>
			                                  	<select name="misa_min${list.WEEK_KEY }" id="misa_min${list.WEEK_KEY }">
			                                  		<option value="">분</option>
			                                  		<option value="00">00</option>
			                                  		<option value="10">10</option>
			                                  		<option value="20">20</option>
			                                  		<option value="30">30</option>
			                                  		<option value="40">40</option>
			                                  		<option value="50">50</option>
			                                  	</select>
			                                  </td>
			                                  <td>
			                                  	<input type="text" name="txt${list.WEEK_KEY }" id="txt${list.WEEK_KEY }"/>
			                                  </td>
			                                  <td>
			                                  <a href="javascript: insertMissa('${admGongsoContents.G_IDX}','${list.WEEK_KEY}')">미사추가</a>
			                                  (미사시간 선택하여 바로 삭제)
			                                  </td>
			                              </tr>
			                            </c:if>
										<c:if test = "${list.WEEK_KEY=='8' }">
										   <tr>
			                                  <th>${list.WT}</th>
			                                  <td colspan="4">
			                                    <textarea rows="3" cols="" class="form-control" name="txt${list.WEEK_KEY }" id="txt${list.WEEK_KEY }" >${list.MNAME }</textarea>
			                                  	<span>전체 수정 저장해야 비고는 반영됩니다.</span>
			                                  </td>
			                               </tr>
			                               <tr>
			                               	  <th>미사시간</th>
			                               	  <td colspan="4"><span>최종 업데이트 일자 : </span>
			                               	  	<!-- <input type="text" id="upd_date" name="upd_date" maxlength="10" placeholder="" value="${admGongsoContents.UPD_DATE}"/>-->
			                               	  	&nbsp;<span>${MISSA_LAST_UPD }</span>
			                               	  </td>
			                               </tr>
										</c:if>
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
		<button type="button" id="btnList"   class="btn btn-default pull-right" onclick="javascript:viewList();return false;">목록</button>
		<c:choose>
		<c:when test="${_params.query_type == 'insert'}">
			<button type="button" id="btnInsert" class="btn btn-default pull-right" onclick="javascript:insert_contents();return false;">등록</button>	
		</c:when>
		<c:otherwise>
			<button type="button" id="btnModify" class="btn btn-default pull-right" onclick="javascript:modify_contents();return false;">수정</button>
			<button type="button" id="btnDelete" class="btn btn-default pull-right" onclick="javascript:delete_contents();return false;">삭제</button>	
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

<script type="text/javascript">


$.datepicker.setDefaults({
    dateFormat: 'yy-mm-dd',
    prevText: '이전 달',
    nextText: '다음 달',
    monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    dayNames: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
    showMonthAfterYear: true,
    yearSuffix: '년'
});

window.onload = function() {
	$("#upd_date").datepicker();
	$("input.upd_date").datepicker().attr("maxlength", 10);
	
	onLoadLeftMenu('info_04');
}
</script>
</body>

</html>
