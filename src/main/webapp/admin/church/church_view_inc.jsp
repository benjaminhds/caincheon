<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/admin/_common/inc/head.jsp" %><%

/*
 *
 * 이 프로그램은 church_view.jsp & church_view_for_member.jsp 에서 동시 사용하는 화면임 
 *
 */

String _URI_ROOT_ = "/admin/church", IS_SUPER_ADMIN="Y", IS_MEMBER_READONLY = "";
if(admSessionMemId==null||admSessionMemId.equals("")) {
	_URI_ROOT_ = "/church_member";
	IS_SUPER_ADMIN = "N";
	IS_MEMBER_READONLY = " readOnly ";
}
pageContext.setAttribute("_URI_ROOT_", _URI_ROOT_);

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

function CommonFormCheck() {

	// null check
	if( $("#name").val() == "") { alert("본당을 입력해 주세요."); $("#name").focus(); return; }
	if( $("#postcode").val() == "") { alert("주소를 입력해 주세요."); $("#postcode").focus(); return; }
	if( $("#addr1").val() == "") { alert("주소를 입력해 주세요."); $("#addr1").focus(); return; }
	
	// 정규식 - 유효성 검사
	var regExp = /^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9]{3,4}-?[0-9]{4}$/;
	/* if($("#tel").val() != "") {
		if( !regExp.test($("#tel").val())) { alert('전화번호가 유효하지 않습니다'); $("#tel").focus(); return; }
	} */
	/* if($("#fax").val() != "") {
		if( !regExp.test($("#fax").val())) { alert('팩스번호가 유효하지 않습니다'); $("#fax").focus(); return; }
	} */
	// email
	var regEmail = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	if(!regEmail.test($("#email").val())) { alert('이메일 주소가 유효하지 않습니다'); $("#email").focus(); return; }

}

<c:if test="${IS_SUPER_ADMIN eq 'Y'}">
function viewList() {
	document.form01.action = '/admin/church/church_list.do';
	//document.getElementById('idx').value=idx;
	document.form01.submit();
	return false;
}

function insert_contents() {
	CommonFormCheck();
	//
	document.form01.action = '/admin/church/church_insert.do';
	document.form01.submit();
	return false;
}

function delete_contents() {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/church/church_delete.do';
		document.form01.submit();
	}
	return false;
}

</c:if>

function modify_contents() {
	CommonFormCheck();
	//
	document.form01.action = '${_URI_ROOT_}/church_modify.do';
	document.form01.submit();
	return false;
}

/**
 * ajax로 개별 추가하기
 */
function insertMissa(church_idx, church_code, idx) {
	if( $("#query_type").val() == "insert") { alert("본당정보를 먼저 등록하세요."); return; }
	
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
	formData.append("church_idx"  , church_idx );
	formData.append("church_code" , church_code );
	formData.append("week"        , idx );
	formData.append("missa_hour"  , $("#misa_hour"+idx).val());
	formData.append("missa_min"   , $("#misa_min"+idx).val());
	formData.append("txt"         , $("#txt"+idx).val());
	
	_requestByAjax('/admin/church/churchMissa_insert.do', formData, function(status, responseData){
											console.log("status="+status);
											console.log("responseData="+JSON.stringify(responseData));
											if(responseData.status=="success") {
												alert(responseData.message);
												var xx = "<span onclick=\"deleteMissa("+church_idx+",this,"+idx+")\" data-id=\""+responseData.mi_idx+"\" id=\"mnamespan"+responseData.mi_idx+"\" style=\"color: #0074e8; \"><U><B>"+addedText+"</B></U></span>";
												$("#misa_name"+idx).html($("#misa_name"+idx).text() + ", " + xx);
											}
										});
}

/**
 * ajax로 개별 삭제하기
 */
function deleteMissa(church_idx, obj, idx) {
	if( $("#query_type").val() == "insert") { alert("본당정보를 먼저 등록하세요."); return; }
	
	var mi_idx = $(obj).data("id");
	var formData = new FormData($('form01')[0]);
	formData.append("church_idx"  , church_idx );
	formData.append("mi_idx" , mi_idx );
	
	if(!confirm("\n\n선택한 미사 정보를 삭제하시겠습니까 ?")) {
		return;
	}
	
	_requestByAjax('/admin/church/churchMissa_delete.do', formData, function(status, responseData){
											console.log("status="+status);
											console.log("responseData="+JSON.stringify(responseData));
											if(responseData.status=="success") {
												alert(responseData.message);
												$(obj).remove();
											}
										});
	return false;
}

/* 
function uploadPhoto(fileObj) {
	//enctype="multipart/form-data"
	var filePath = fileObj.value;
	var fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
	var fileKind = fileName.split(".")[1];
	
	var form = $('form01')[0];
    var formData = new FormData(form);
    formData.append("uploadType", "image");
    formData.append("uploadPath", "church_lookaround/"+document.form01.church_idx.value );
    formData.append("image", $("#thumbFile")[0].files[0]);
    
    _fileUploadByAjax('/admin/board/uploadOk.jsp', formData, 'image', 'imagefile', null);  // admCommon.js
}
*/

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
				<h3 class="page-header">본당 등록</h3>
			</c:when>
			<c:otherwise>
				<h3 class="page-header">본당 수정</h3>	
			</c:otherwise>
			</c:choose>
			</div>
			<!-- /.col-lg-12 -->
		</div>

<% if(IS_SUPER_ADMIN.equals("N")) { %>
		<div class="row" style="padding: 15px; text-align:right; right-margin: 20px">
			<button type="button" id="btnModify"  onclick="javascript:modify_contents();return false;">수 정 하 기</button>
		</div>
		<% } %>

<!-- /.row -->
<form id="form01" name="form01" enctype="multipart/form-data" method="POST" action="/admin/church/church_list.do">
<input type="hidden" name="depart_code" id="depart_code" value="${CONTENTS.DEPART_CODE}"/>
<input type="hidden" name="church_code" id="church_code" value="${CONTENTS.CHURCH_CODE}"/>
<input type="hidden" name="church_idx" id="church_idx" value="${CONTENTS.CHURCH_IDX}"/>
<input type="hidden" name="adminYn" id="adminYn" value="Y"/>
<input type="hidden" name="query_type" id="query_type" value="${_params.query_type }"/>
<input type="hidden" name="week" id="week" value=""/>
	<!-- Contents : Begin //-->		
	<div class="page-form">
		<div class="panel panel-default">
			<div class="panel-body">
				
				<table class="table tbl-form">
				<colgroup>
					<col style="width:120px;">
					<col>
					<col style="width:120px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th><label for="">지구  </label></th>
						<td colspan="3">
							
				    			<select class="form-control" name="gigu_code" id="gigu_code" <%=IS_MEMBER_READONLY%> >
				    				
									<!-- 수퍼관리자라면 -->
									<c:if test="${admSessionMemId ne ''}">
										<option value="00">선택</option>
				                        <c:if test="${fn:length(D_GROUP_LIST) > 0}">
				                          <c:forEach items="${D_GROUP_LIST}" var="row" varStatus="status">
				                         	<option value="${row.CODE_INST}" <c:if test = "${CONTENTS.GIGU_CODE==row.CODE_INST }"> selected </c:if>>${row.NAME}</option>
				                          </c:forEach>
				                        </c:if>
									</c:if>
										
									<!-- 본당관리자라면 -->
									<c:if test="${admSessionMemId eq '' and SS_CHURCHIDX ne ''}">
										<c:if test="${fn:length(D_GROUP_LIST) > 0}">
				                          <c:forEach items="${D_GROUP_LIST}" var="row" varStatus="status">
				                            <c:if test = "${CONTENTS.GIGU_CODE==row.CODE_INST }"> 
				                         	<option value="${row.CODE_INST}" selected >${row.NAME}</option>
				                         	</c:if>
				                          </c:forEach>
				                        </c:if>
									</c:if>
									
									
			                    </select>
				    		
						</td>
					</tr>
					<tr>
						<th><label for="">본당명 </label></th>
						<td colspan="3">
			    			<input class="form-control" type="text"  id="name" placeholder="" name="name" value="${CONTENTS.NAME}" readonly/>
						</td>
					</tr>
					<tr>
						<th><label for="">본당사제</label></th>
						<td colspan="3">
			    			<span>${fn:replace(CONTENTS.PRIESTNAMES, ',', '<BR>')}</span>		
						</td>
					</tr>
					<tr>
						<th><label for="">전화</label></th>
						<td>
			    			<input type="input" id="tel" name="tel" placeholder="대표전화번호" value="${CONTENTS.TEL}"/>		
						</td>
						<th><label for="">팩스</label></th>
						<td>
				    		<input type="text" id="fax" name="fax" placeholder="FAX번호" value="${CONTENTS.FAX}"/>		
						</td>
					</tr>
					<tr>
						<th><label for="">메일</label></th>
						<td>
			    			<input type="input" id="email" name="email" placeholder="이메일주소" value="${CONTENTS.EMAIL}">		
						</td>
						<th><label for="">홈페이지</label></th>
						<td>
				    		<input type="input" id="homepage" name="homepage" placeholder="홈페이지 주소" value="${CONTENTS.HOMEPAGE}">		
						</td>
					</tr>
					<tr>
						<th rowspan="2"><label for="">주소</label></th>
						<td colspan="3">
							<div class="row">
							<div class="col-md-4">
			    			<input type="text" class="form-control col-sm-1" id="postcode" placeholder="" name="postcode" value="${CONTENTS.POSTCODE}">		
			    			</div>
			    			<button type="button" onclick="execDaumPostcode()">우편번호 검색</button>
			    			</div>
						</td>
					</tr>
					<tr>
						<td colspan="3">
			    			<input type="text" class="form-control col-sm-1" id="addr1" placeholder="" name="addr1" value="${CONTENTS.ADDR1}">		
						</td>
					</tr>
					<tr>
						<th><label for="">본당 위치(좌표)값</label></th>
						<td colspan="3">
			    			<input type="text" class="form-control col-sm-1" id="map_point" name="map_point" placeholder="본당의 중심좌표, 형식) 33.452613, 126.570888" value="${CONTENTS.MAP_POINT}" >		
						</td>
					</tr>
					<tr>
						<th><label for="">관할구역 좌표값들</label></th>
						<td colspan="3">
			    			<textarea class="form-control h-50" id="map_controlarea" name="map_controlarea" cols="80" rows="5" placeholder="본당의 관할 구역, 형식) {name:'',path:[new daum.maps.LatLng(37.5446051,126.7128886),....]}" <%=IS_MEMBER_READONLY%> >${CONTENTS.MAP_CONTROLAREA}</textarea>		
						</td>
					</tr>
					<tr>
						<th><label for="">주보성인</label></th>
						<td>
			    			<input type="input" id="jubo_saint" name="jubo_saint" placeholder="" value="${CONTENTS.JUBO_SAINT}">		
						</td>
						<th><label for="">설립일</label></th>
						<td>
				    			<input type="text" id="establish_date" name="establish_date" maxlength="10" placeholder="" value="${CONTENTS.ESTABLISH_DATE}">		
						</td>
					</tr>
					<tr>
						<th><label for="">신자수</label></th>
						<td>
			    			<input type="input" id="christian_cnt" name="christian_cnt" placeholder="" value="${CONTENTS.CHRISTIAN_CNT}">		
						</td>
						<th><label for="">수도회</label></th>
						<td>
				    		<input type="text" id="r_order" name="r_order" placeholder="" value="${CONTENTS.R_ORDER}">
						</td>
					</tr>
					<tr>
						<th><label for="">성당소개</label></th>
						<td colspan="3">
			    			<textarea class="form-control h-200" id="intro" name="intro" cols="80" rows="10">${CONTENTS.INTRO}</textarea>
						</td>
					</tr>
					<tr>
						<th><label for="">본당둘러보기<br/>(성당이미지<br/>업로드)</label></th>
						<td colspan="3">
						<!-- 
	 						<div class="form-group input-group">
								<input class="form-control" type="file" name="thumbFile" id="thumbFile">
							</div>
							<div class="img_wrap"><p>이미지 미리보기</p><p><img id="imagePreview" /></p><p id="imagePreview_info"></p></div>
							
							<c:if test="${fn:length(CONTENTS.STRFILENAME) > 0 }">
								<img alt="" src="${CONTENTS.FILEPATH}thumbnail/${CONTENTS.STRFILENAME}" width="120px">${CONTENTS.FILENAME}
							</c:if>
							-->
							<input type="hidden" name="isKeepOriginalNm" value="Y" alt="이미지 원본 이름 유지 옵션"  />
							<% pageContext.setAttribute("HEAD_CONST__attach_file_form__MAX_FILE_COUNT", 10); /* 파일 10개까지 업로드 */ %>
							<%@ include file="/admin/_common/inc/attach_file_form_loop.jsp" %>
						</td>
					</tr>
					<tr>
						<th><label for="">연혁</label></th>
						<td colspan="3">
			    			<textarea class="form-control h-200" id="history" name="history" cols="80" rows="10">${CONTENTS.HISTORY}</textarea>		
						</td>
					</tr>
					<tr>
						<th><label for="">교통평(대중교통)</label></th>
						<td colspan="3">
			    			<textarea class="form-control h-200" id="traffic_mass" name="traffic_mass" cols="80" rows="10">${CONTENTS.TRAFFIC_MASS}</textarea>		
						</td>
					</tr>
					<tr>
						<th><label for="">교통편(자가용)</label></th>
						<td colspan="3">
			    			<textarea class="form-control h-200" id="traffic_self" name="traffic_self" cols="80" rows="10">${CONTENTS.TRAFFIC_SELF}</textarea>		
						</td>
					</tr>
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
									<c:when test="${fn:length(MISSA_LIST) > 0}">
									<c:forEach items="${MISSA_LIST}" var="list">
										<c:if test = "${list.WEEK_KEY ne '7' }">
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
			                                  <a href="javascript: insertMissa('${CONTENTS.CHURCH_IDX}','','${list.WEEK_KEY}')">미사추가</a>
			                                  (미사시간 선택하여 바로 삭제)
			                                  </td>
			                              </tr>
			                            </c:if>
										<c:if test = "${list.WEEK_KEY=='7' }">
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
			                               	  	<!-- <input type="text" id="upd_date" name="upd_date" maxlength="10" placeholder="" value="${CONTENTS.UPD_DATE}"/>-->
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
		<% if(IS_SUPER_ADMIN.equals("Y")) { %>
		<button type="button" id="btnList"   class="btn btn-default pull-right" onclick="javascript:viewList();return false;">목록</button>
		<c:choose>
		<c:when test="${_params.query_type == 'insert'}">
			<button type="button" id="btnInsert" class="btn btn-default pull-right" onclick="javascript:insert_contents();return false;">등록</button>	
		</c:when>
		<c:otherwise>
			<button type="button" id="btnModify" class="btn btn-default pull-right" onclick="javascript:modify_contents();return false;">수정</button>	
		</c:otherwise>
		</c:choose>
		<%
		} else {
		%>
		<button type="button" id="btnModify"  onclick="javascript:modify_contents();return false;">수 정 하 기</button>
		<% } %>
		</p>
		<!-- Buttons : End //-->

	</div>
	<!-- Contents : End //-->
			
</form>
<form id="form02" name="form02" method="POST" action="/admin/church/church_list.do">
<input type="hidden" name="adminYn" id="adminYn" value="Y"/>
<input type="hidden" name="query_type" id="query_type" value="${_params.query_type }"/>
<input type="hidden" name="church_code" id="church_code" value="${CONTENTS.CHURCH_CODE}"/>
<input type="hidden" name="church_idx" id="church_idx" value="${CONTENTS.CHURCH_IDX}"/>
<input type="hidden" name="week" id="week" value=""/>
</form>
	</div>
	<!-- /#page-wrapper -->

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
	onLoadLeftMenu('info_04');
	
	$("#establish_date").datepicker();
	$("input.establish_date").datepicker().attr("maxlength", 10);
	$("#upd_date").datepicker();
	$("input.upd_date").datepicker().attr("maxlength", 10);
	
	CKEDITOR.replace( 'history', {
    	filebrowserUploadUrl: '/admin/board/infoImageUpload.do'
  	});
	
	CKEDITOR.replace( 'intro', {
    	filebrowserUploadUrl: '/admin/board/infoImageUpload2.do'
  	});
	
	// thumbnail
	$("#thumbFile").on("change", {maxWidth:500, maxHeight:400, imgPreviewId:"imagePreview"},  handleImageFileSelect);
}

</script>

</div>
<!-- /#wrapper -->
</body>

</html>