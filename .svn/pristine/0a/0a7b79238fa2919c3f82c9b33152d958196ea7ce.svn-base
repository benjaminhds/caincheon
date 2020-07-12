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
	document.form01.action = '/admin/board/marry_list.do';
	//document.getElementById('idx').value=idx;
	document.form01.submit();
	return false;
}

function insert_contents() {
	if( $(':radio[name="apply_type"]:checked').val() != "1" && $(':radio[name="apply_type"]:checked').val() != "2") {
		alert("신청구분을 입력해 주세요."); $("#apply_type").focus(); return; 
	} else if($(':radio[name="apply_type"]:checked').val() == "1") {
		if( $("#lecture_apply_day").val() == "") { alert("강좌신청날짜를 입력해 주세요."); $("#lecture_apply_day").focus(); return; }
	} else if($(':radio[name="apply_type"]:checked').val() == "2") {
		if( $("#lecture_apply_day2").val() == "") { alert("강좌신청날짜를 입력해 주세요."); $("#lecture_apply_day2").focus(); return; }
	}

	if( $("#man_name").val() == "") { alert("성명(남자)을 입력해 주세요."); $("#man_name").focus(); return; }
	if( $("#woman_name").val() == "") { alert("성명(여자)을 입력해 주세요."); $("#woman_name").focus(); return; }

	if($("input:checkbox[id='man_other_region']").is(":checked") == false) {
		if( $("#man_church_idx").val() == "") { alert("본당을 선택해 주세요."); $("#man_church_idx").focus(); return; }
	}
	if($("input:checkbox[id='woman_other_region']").is(":checked") == false) {
		if( $("#woman_church_idx").val() == "") { alert("본당을 선택해 주세요."); $("#woman_church_idx").focus(); return; }
	}
	
	if( $("#man_member_type").val() == "") {
		alert("신자구분을 선택해 주세요."); 
		$("#man_member_type").focus(); 
		return; 
	} else if( $("#man_member_type").val() == "2"){
		if( $("#man_baptismal_name").val() == "") {
			alert("세례명란에 세례예정일 입력해주세요");
			$("#man_baptismal_name").focus();
			return;
		}
	}
	if( $("#woman_member_type").val() == "") { 
		alert("신자구분을 선택해 주세요."); $("#woman_member_type").focus(); return; 
	} else if( $("#woman_member_type").val() == "2"){
		if( $("#woman_baptismal_name").val() == "") {
			alert("세례명란에 세례예정일 입력해주세요");
			$("#woman_baptismal_name").focus();
			return;
		}
	}

	if( $("#man_birthday").val() == "") { alert("주민등록상 생년월일(남자)을 입력해 주세요."); $("#man_birthday").focus(); return; }
	if( $("#woman_birthday").val() == "") { alert("주민등록상 생년월일(여자)을 입력해 주세요."); $("#woman_birthday").focus(); return; }

	if( $("#man_tel").val() == "") { alert("연락처(남자)을 입력해 주세요."); $("#man_tel").focus(); return; }
	if( $("#woman_tel").val() == "") { alert("연락처(여자)을 입력해 주세요."); $("#woman_tel").focus(); return; }

	// 정규식 - 이메일 유효성 검사
	var regEmail = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	if(!regEmail.test($("#email").val())) { alert('이메일 주소가 유효하지 않습니다'); $("#email").focus(); return; }
	
	document.form01.action = '/admin/board/marry_insert.do';
	document.form01.submit();
	return false;
}

function modify_contents() {
	if( $(':radio[name="apply_type"]:checked').val() != "1" && $(':radio[name="apply_type"]:checked').val() != "2") {
		alert("신청구분을 입력해 주세요."); $("#apply_type").focus(); return; 
	} else if($(':radio[name="apply_type"]:checked').val() == "1") {
		if( $("#lecture_apply_day").val() == "") { alert("강좌신청날짜를 입력해 주세요."); $("#lecture_apply_day").focus(); return; }
	} else if($(':radio[name="apply_type"]:checked').val() == "2") {
		if( $("#lecture_apply_day2").val() == "") { alert("강좌신청날짜를 입력해 주세요."); $("#lecture_apply_day2").focus(); return; }
	}
	if( $("#man_name").val() == "") { alert("성명(남자)을 입력해 주세요."); $("#man_name").focus(); return; }
	if( $("#woman_name").val() == "") { alert("성명(여자)을 입력해 주세요."); $("#woman_name").focus(); return; }

	if($("input:checkbox[id='man_other_region']").is(":checked") == false) {
		if( $("#man_church_idx").val() == "") { alert("본당을 선택해 주세요."); $("#man_church_idx").focus(); return; }
	}
	if($("input:checkbox[id='woman_other_region']").is(":checked") == false) {
		if( $("#woman_church_idx").val() == "") { alert("본당을 선택해 주세요."); $("#woman_church_idx").focus(); return; }
	}
	
	if( $("#man_member_type").val() == "") {
		alert("신자구분을 선택해 주세요."); 
		$("#man_member_type").focus(); 
		return; 
	} else if( $("#man_member_type").val() == "2"){
		if( $("#man_baptismal_name").val() == "") {
			alert("세례명란에 세례예정일 입력해주세요");
			$("#man_baptismal_name").focus();
			return;
		}
	}
	if( $("#woman_member_type").val() == "") { 
		alert("신자구분을 선택해 주세요."); $("#woman_member_type").focus(); return; 
	} else if( $("#woman_member_type").val() == "2"){
		if( $("#woman_baptismal_name").val() == "") {
			alert("세례명란에 세례예정일 입력해주세요");
			$("#woman_baptismal_name").focus();
			return;
		}
	}

	if( $("#man_birthday").val() == "") { alert("주민등록상 생년월일(남자)을 입력해 주세요."); $("#man_birthday").focus(); return; }
	if( $("#woman_birthday").val() == "") { alert("주민등록상 생년월일(여자)을 입력해 주세요."); $("#woman_birthday").focus(); return; }

	if( $("#man_tel").val() == "") { alert("연락처(남자)을 입력해 주세요."); $("#man_tel").focus(); return; }
	if( $("#woman_tel").val() == "") { alert("연락처(여자)을 입력해 주세요."); $("#woman_tel").focus(); return; }

	// 정규식 - 이메일 유효성 검사
	var regEmail = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	if(!regEmail.test($("#email").val())) { alert('이메일 주소가 유효하지 않습니다'); $("#email").focus(); return; }
	
	document.form01.action = '/admin/board/marry_modify.do';
	document.form01.submit();
	return false;
}

function delete_contents() {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인	
		document.form01.action = '/admin/board/marry_delete.do';
		document.form01.submit();
	}
	return false;
}

function chgLectureDate() {
	if(document.querySelector('input[name = "apply_type"]:checked').value == '1') {
		document.getElementById("lecture_apply_day2").value = "";
		$("#divLecture1").show();
		$("#divLecture2").hide();
	} else if(document.querySelector('input[name = "apply_type"]:checked').value == '2') {
		document.getElementById("lecture_apply_day").value = "";
		$("#divLecture1").hide();
		$("#divLecture2").show();
	} else {
		$("#divLecture1").hide();
		$("#divLecture2").hide();
	}
}

function chkRegion(checkbox) {
	if(checkbox.checked == true) {
		document.getElementById('man_church_idx').value="";
		$("#man_church_idx").attr("disabled", "disabled");
	} else {
		$("#man_church_idx").attr("disabled", false);
	}
}

function chkRegionW(checkbox) {
	if(checkbox.checked == true) {
		document.getElementById('woman_church_idx').value="";
		$("#woman_church_idx").attr("disabled", "disabled");
	} else {
		$("#woman_church_idx").attr("disabled", false);
	}
}

window.onload = function() {
	// active gnb
	onLoadLeftMenu('order_02');
	chgLectureDate();
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
	<h3 class="page-header">카나혼인강좌,약혼자주말신청 등록</h3>
</c:when>
<c:otherwise>
	<h3 class="page-header">카나혼인강좌,약혼자주말신청 수정</h3>	
</c:otherwise>
</c:choose>
					</div>
				<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
<form name="form01" id="form01" action="/admin/board/marry_insert.do" method="POST">
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}" />	
<input type="hidden" name="marry_no" id="marry_no" value="${marryContents.MARRY_NO}" />	
<input type="hidden" name="id" id="id" value="${marryContents.ID}" />
<input type="hidden" name="adminYn" id="adminYn" value="Y"/>
  	<!-- Contents : Begin //-->
				<div class="page-form">
					<div class="panel panel-default">
						<div class="panel-body">
							
							<table class="table tbl-form">
								<colgroup>
									<col style="width:150px;">
									<col>
								</colgroup>
								<tbody>
            <tr>
                <th scope="row">접수상태</th>
                <td>
                    <span class="chkForm">
                    	<input type="radio" name="process_status"  id="process_status" value="1" <c:if test="${marryContents.PROCESS_STATUS=='1' }" >checked="checked"</c:if>>
                        <label for=""><span>입금확인중</span></label>
                    </span>
                    <span class="chkForm">
                    	<input type="radio" name="process_status"  id="process_status" value="2" <c:if test="${marryContents.PROCESS_STATUS=='2' }" >checked="checked"</c:if>>
                        <label for=""><span>접수완료</span></label>
                    </span>
                    <span class="chkForm">
                    	<input type="radio" name="process_status"  id="process_status" value="3" <c:if test="${marryContents.PROCESS_STATUS=='3' }" >checked="checked"</c:if>>
                        <label for=""><span>접수취소</span></label>
                    </span>
                </td>
            </tr>
            <tr>
              <th scope="row">신청구분</th>
              <td>
                  <span class="chkForm">
                  	<input type="radio" name="apply_type"  id="apply_type" value="1" <c:if test="${marryContents.APPLY_TYPE=='1' }" >checked="checked"</c:if> onClick="javascript:chgLectureDate();">
                      <label for=""><span>카나혼인강좌</span></label>
                  </span>
                  <span class="chkForm">
                  	<input type="radio" name="apply_type"  id="apply_type" value="2" <c:if test="${marryContents.APPLY_TYPE=='2' }" >checked="checked"</c:if>  onClick="javascript:chgLectureDate();">
                                  <label for=""><span>약혼자주말</span></label>
                              </span>
                          </td>
                      </tr>
                      <tr>
                          <th scope="row"><i>*</i>강좌신청날짜</th>
                          <td>
                          
                          	<div id="divLecture1">
                          	<span class="form">
                          	<select name="lecture_apply_day" id="lecture_apply_day">
                                  <option value="">선택</option>
                                  <c:if test="${fn:length(LECTURE_LIST) > 0}">
                       <c:forEach var="entry" items="${LECTURE_LIST}" varStatus="status">
                       	<option value="${entry.EKEY}" <c:if test = "${marryContents.LECTURE_APPLY_DAY==entry.EKEY }"> selected </c:if>>${entry.EKEY}</option>
                       </c:forEach>
                   </c:if>
                  </select>
                  </span>
                  </div>
                  <div id="divLecture2">
                  <span class="form">
                  <select name="lecture_apply_day2" id="lecture_apply_day2">
                      <option value="">선택</option>
                      <c:if test="${fn:length(LECTURE_LIST2) > 0}">
                       <c:forEach var="entry" items="${LECTURE_LIST2}" varStatus="status">
                       	<option value="${entry.EKEY}" <c:if test = "${marryContents.LECTURE_APPLY_DAY2==entry.EKEY }"> selected </c:if>>${entry.EKEY}</option>
                       </c:forEach>
                   </c:if>
                  </select>
                  </span>
                  </div>
              
              </td>
          </tr>
                     <tr class="men">
                         <th scope="row"><i>*</i>성명(남자)</th>
                         <td>
                             <span class="form">
                                 <label for=""></label>
<input  type="text" name="man_name" id="man_name"  value="${marryContents.MAN_NAME }"/>
                    </span>
                </td>
            </tr>
            <tr class="men">
                <th scope="row"><i>*</i>본당 및 신자구분(남자)</th>
                <td class="saint">
                    <span class="form">
                        <label for=""></label>
                        <select name="man_church_idx" id="man_church_idx">
                            <option value="">선택</option>
                            <c:if test="${fn:length(CHURCH_LIST) > 0}">
                             <c:forEach var="entry" items="${CHURCH_LIST}" varStatus="status">
                             	<option value="${entry.CHURCH_IDX}" <c:if test = "${marryContents.MAN_CHURCH_IDX==entry.CHURCH_IDX }"> selected </c:if>>${entry.NAME}</option>
                             </c:forEach>
                         </c:if>
                        </select>
                        <label for=""></label>
                        <select name="man_member_type" id="man_member_type" >
                        	<option value="">선택</option>
                            <option value="1" <c:if test = "${marryContents.MAN_MEMBER_TYPE=='1'}"> selected </c:if>>신자</option>
                            <option value="2" <c:if test = "${marryContents.MAN_MEMBER_TYPE=='2'}"> selected </c:if>>예비신자</option>
                            <option value="3" <c:if test = "${marryContents.MAN_MEMBER_TYPE=='3'}"> selected </c:if>>비신자</option>
                        </select>
                        <span class="other">
                            <input type="checkbox" name="man_other_region" id="man_other_region" value="1" <c:if test="${marryContents.MAN_OTHER_REGION eq '1'}">checked</c:if> onClick="javascript:chkRegion(this);"/>
                            <label for=""><span>타지역</span></label>
                        </span>
                        <span class="other"><input type="text" name="man_taregion" id="man_taregion" value="${marryContents.MAN_TAREGION }" /></span>
                    </span>
                </td>
            </tr>
            <tr class="men">
                <th scope="row"><i></i>세례명(남자)</th>
                <td>
                    <span class="form">
                        <label for=""></label>
                        <input  type="text" name="man_baptismal_name" id="man_baptismal_name"  value="${marryContents.MAN_BAPTISMAL_NAME }"/>
                             </span>
                         </td>
                     </tr>
                     <tr class="men">
                         <th scope="row"><i>*</i>주민등록상 생년월일(남자)</th>
                         <td class="years">
                             <span class="form calenr v2">
<input type="date" name="man_birthday" id="man_birthday" value="${marryContents.MAN_BIRTHDAY}">
                    </span>
                </td>
            </tr>
            <tr class="men">
                <th><i>*</i>연락처(남자)</th>
                <td class="telPhone">
                    <span class="form">
                    	<input type="tel" name="man_tel" id="man_tel" value="${marryContents.MAN_TEL}">
                    </span>
                </td>
            </tr>
            <tr class="women">
                <th scope="row"><i>*</i>성명(여자)</th>
                <td>
                    <span class="form">
                        <label for=""></label>
                        <input  type="text" name="woman_name" id="woman_name"  value="${marryContents.WOMAN_NAME }"/>
                    </span>
                </td>
            </tr>
            <tr class="women">
                <th scope="row"><i>*</i>본당 및 신자구분(여자)</th>
                <td class="saint">
                	<span class="form">
                        <label for=""></label>
                        <select name="woman_church_idx" id="woman_church_idx">
                            <option value="">선택</option>
                            <c:if test="${fn:length(CHURCH_LIST) > 0}">
                             <c:forEach var="entry" items="${CHURCH_LIST}" varStatus="status">
                             	<option value="${entry.CHURCH_IDX}" <c:if test = "${marryContents.WOMAN_CHURCH_IDX==entry.CHURCH_IDX }"> selected </c:if>>${entry.NAME}</option>
                             </c:forEach>
                         </c:if>
                        </select>
                        <label for=""></label>
                        <select name="woman_member_type" id="woman_member_type" >
                        	<option value="">선택</option>
                            <option value="1" <c:if test = "${marryContents.WOMAN_MEMBER_TYPE=='1'}"> selected </c:if>>신자</option>
                            <option value="2" <c:if test = "${marryContents.WOMAN_MEMBER_TYPE=='2'}"> selected </c:if>>예비신자</option>
                            <option value="3" <c:if test = "${marryContents.WOMAN_MEMBER_TYPE=='3'}"> selected </c:if>>비신자</option>
                        </select>
                        <span class="other">
                            <input type="checkbox" name="woman_other_region" id="woman_other_region" value="1" <c:if test="${marryContents.WOMAN_OTHER_REGION==1}">checked</c:if>  onClick="javascript:chkRegion(this);"/>
                            <label for=""><span>타지역</span></label>
                        </span>
                        <span class="other"><input type="text" name="woman_taregion" id="woman_taregion" value="${marryContents.WOMAN_TAREGION }" /></span>
                    </span>
                </td>
            </tr>
            <tr class="women">
                <th scope="row"><i>*</i>세례명(여자)</th>
                <td>
                    <span class="form">
                        <label for=""></label>
                        <input  type="text" name="woman_baptismal_name" id="woman_baptismal_name"  value="${marryContents.WOMAN_BAPTISMAL_NAME }"/>
                    </span>
                </td>
            </tr>
            <tr class="women">
                <th scope="row"><i>*</i>주민등록상 생년월일(여자)</th>
                <td class="years">
                    <span class="form calenr v2">
                    	<input type="date" name="woman_birthday" id="woman_birthday" value="${marryContents.WOMAN_BIRTHDAY}">
                    </span>
                </td>
            </tr>
            <tr class="women">
                <th><i>*</i>연락처(여자)</th>
                <td class="telPhone">
                    <span class="form">
                    	<input type="tel" name="woman_tel" id="woman_tel" value="${marryContents.WOMAN_TEL}">
                        <!-- <label for=""></label>
                        <select name="" id="">
                            <option value=""></option>
                        </select>
                        <span class="bar">-</span>
                        <label for=""></label>
                        <input type="tel">
                        <span class="bar">-</span>
                        <label for=""></label>
                        <input type="tel"> -->
                    </span>
                </td>
            </tr>
            <tr>
                <th><i>*</i>신청자 이메일</th>
                <td class="telPhone emails">
                    <span class="form">
                    	<input  type="email" name="email" id="email" value="${marryContents.EMAIL}"  />
                    </span>
                </td>
            </tr>
            
            <tr>
                <th scope="row">우편물 수령가능 주소</th>
                <td class="addr">
                    <span class="form">
                        <label for=""></label>
                        <input type="text" name="postcode" id="postcode" placeholder="우편번호" value="${marryContents.POSTCODE}">
                        <button type="button" onclick="execDaumPostcode()">우편번호 검색</button>
                    </span>
                    <span class="form">
                        <label for=""></label>
                        <input type="text" name="addr1" id="addr1" placeholder="도로명주소" value="${marryContents.ADDR1}">
                    </span>
                    <span class="form">
                        <label for=""></label>
                        <input type="text" name="addr2" id="addr2" placeholder="지번주소" value="${marryContents.ADDR2}">
                    </span>
                </td>
            </tr>
            <tr>
                <th scope="row">혼인날짜(예정일)</th>
                <td class="years">
                    <span class="form">
                    	<input type="date" name="marry_day" id="marry_day" value="${marryContents.MARRY_DAY}">
                    </span>
                </td>
            </tr>
            <tr>
                <th>혼인장소</th>
                <td class="telPhone">
                    <span class="chkForm">
                        <input type="radio" name="marry_place" id="marry_place" value="1" <c:if test="${marryContents.MARRY_PLACE=='1' }" >checked="checked"</c:if>>
                        <label for=""><span>성당</span></label>
                    </span>
                    <span class="chkForm">
                        <input type="radio" name="marry_place" id="marry_place" value="2" <c:if test="${marryContents.MARRY_PLACE=='2' }" >checked="checked"</c:if>>
                        <label for=""><span>예식장</span></label>
                    </span>
                </td>
            </tr>
            <tr>
                <th>신청동기</th>
                <td class="telPhone">
                    <span class="chkForm">
                        <input type="radio" name="apply_reason" id="apply_reason" value="1" <c:if test="${marryContents.APPLY_REASON=='1' }" >checked="checked"</c:if>>
                        <label for=""><span>성사/관면</span></label>
                    </span>
                    <span class="chkForm">
                        <input type="radio" name="apply_reason" id="apply_reason" value="2" <c:if test="${marryContents.APPLY_REASON=='2' }" >checked="checked"</c:if>>
                        <label for=""><span>혼인장애 해소</span></label>
                    </span>
                </td>
            </tr>
								</tbody>
							</table>
						</div>
					</div>
</form>
<p>
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
<script type="text/javascript">
$(document).ready(function() {
    $('.form_datetime').datetimepicker({
        language:  'ko',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0,
        showMeridian: 1
    });
	$('.form_date').datetimepicker({
        language:  'ko',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
    });
	$('.form_time').datetimepicker({
        language:  'ko',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 1,
		minView: 0,
		maxView: 1,
		forceParse: 0
    });
});
</script>
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->
</body>

</html>