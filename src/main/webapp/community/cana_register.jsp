<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/_common/inc/headSub.jsp" %>
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
<script>
function imgRefresh(){
    $("#captchaImg").attr("src", "/captcha?id=" + Math.random());
}
function CheckField() {
	if( $(':radio[name="apply_type"]:checked').val() != "1" && $(':radio[name="apply_type"]:checked').val() != "2") {
		alert("신청구분을 입력해 주세요."); $("#apply_type").focus(); return false; 
	} else if($(':radio[name="apply_type"]:checked').val() == "1") {
		if( $("#lecture_apply_day").val() == "") { alert("강좌신청날짜를 입력해 주세요."); $("#lecture_apply_day").focus(); return false; }
	} else if($(':radio[name="apply_type"]:checked').val() == "2") {
		if( $("#lecture_apply_day2").val() == "") { alert("강좌신청날짜를 입력해 주세요."); $("#lecture_apply_day2").focus(); return false; }
	}
	
	// man
	if( $("#man_name").val() == "") { alert("성명(남자)을 입력해 주세요."); $("#man_name").focus(); return false; }
	if($("input:checkbox[id='man_other_region']").is(":checked") == false) {
		if( $("#man_church_idx").val() == "") { alert("남자분의 소속 본당을 선택해 주세요."); $("#man_church_idx").focus(); return false; }
		
		if( $("#man_member_type").val() == "") {
			alert("남자분의 신자구분을 선택해 주세요."); 
			$("#man_member_type").focus(); 
			return; 
		} else if( $("#man_member_type").val() == "2"){
			if( $("#man_baptismal_name").val() == "") {
				alert("남자분의 세례명란에 세례예정일 입력해주세요");
				$("#man_baptismal_name").focus();
				return false;
			}
		}
	} else {
		if( $("#man_taregion input[name=man_taregion]").val() == "") {
			alert("남자분의 타지역명을 입력해주세요"); $("#man_taregion input[name=man_taregion]").focus(); return false;
		}
	}


	if( $("#man_birthday").val() == "") { alert("주민등록상 생년월일(남자)을 입력해 주세요."); $("#man_birthday").focus(); return false; }
	if( $("#man_birthday").val() != "" ) {
		if(chkDate($("#man_birthday").val())==false) {
			alert("생년월일(남자)을 정확히 입력해 주세요.");
			return false;
		}
	}
	if( $("#man_tel").val() == "") { alert("연락처(남자)을 입력해 주세요."); $("#man_tel").focus(); return false; }
	
	// woman
	if( $("#woman_name").val() == "") { alert("성명(여자)을 입력해 주세요."); $("#woman_name").focus(); return false; }
	if($("input:checkbox[id='woman_other_region']").is(":checked") == false) {
		if( $("#woman_church_idx").val() == "") { alert("여자분의 소속 본당을 선택해 주세요."); $("#woman_church_idx").focus(); return false; }

		if( $("#woman_member_type").val() == "") { 
			alert("여자분의 신자구분을 선택해 주세요."); $("#woman_member_type").focus(); return false; 
		} else if( $("#woman_member_type").val() == "2"){
			if( $("#woman_baptismal_name").val() == "") {
				alert("여자분의 세례명란에 세례예정일 입력해주세요"); $("#woman_baptismal_name").focus(); return false;
			}
		}
	} else {
		if( $("#woman_taregion input[name=woman_taregion]").val() == "") {
			alert("여자분의 타지역명을 입력해주세요"); $("#woman_taregion input[name=woman_taregion]").focus(); return false;
		}
	}
	
	if( $("#woman_birthday").val() == "") { alert("주민등록상 생년월일(여자)을 입력해 주세요."); $("#woman_birthday").focus(); return false; }
	if( $("#woman_birthday").val() != "" ) {
		if(chkDate($("#woman_birthday").val())==false) {
			alert("생년월일(여자)을 정확히 입력해 주세요.");
			return false;
		}
	}
	if( $("#woman_tel").val() == "") { alert("연락처(여자)을 입력해 주세요."); $("#woman_tel").focus(); return false; }

	// 정규식 - 이메일 유효성 검사
	var regEmail = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	if(!regEmail.test($("#email").val())) { alert('신청자의 이메일 주소가 유효하지 않습니다'); $("#email").focus(); return false; }
	
	if( $("#marry_day").val() != "" ) {
		if(chkDate($("#marry_day").val())==false) {
			alert("혼인날짜(예정일)를 정확히 입력해 주세요.");
			return false;
		}
	}
	
	//if( $("#id").val() == "") { alert("비회원일 경우,신청은 가능하나 마이페이지에서 신청현황을 확인 하실 수 없습니다. 신청내역은 기재해주신 메일로 발송됩니다."); }

	if( $("#captcha").val() == "") { alert("보안문자를 입력해 주세요."); $("#captcha").focus(); return false; }
	
	return true;
}

function insert() {
	if(!CheckField()) {
		return;
	}
	
	// send
	if( confirm("신청하시겠습니까 ? ") ) {
		$("#btn_insert").html("<a href='#'>신청 처리 중..</a>");
		$("#btn_insert").children("a").remove();//event unbind, if <input, then $("#").off() 
		$("#btn_cancel").children("a").remove();//event unbind if <input, then $("#").off()
		$("#btn_insert").delay(500).html("<a href='#'>메일 발송 중..</a>");
		
		console.log("전송데이터1:"+$("#frm").serialize());
		console.log("전송데이터2:"+$("form[name=frm]").serialize());

		_requestGet("frm", function(status, responseData) {
			console.log("status="+status);
			console.log("responseData="+JSON.stringify(responseData));
			console.log("RESULT_YN="+responseData.RESULT_YN);
			
			if(responseData.RESULT_YN == 'N' || status == 'error') {
				if(responseData.MESSAGE != "") 
					alert(responseData.MESSAGE);
				else 
					alert(JSON.stringify(responseData));
				
				$("#btn_insert").children("a").on("click", function(){ insert() } );
				$("#btn_insert").delay(1000).html("<a href='#'>관리자 확인 필요</a>");
				
			} else if(responseData.RESULT_YN == 'Y') {
				$("#btn_insert").delay(1000).html("<a href='#'>처리 완료 !!</a>");
				location.href="/community/cana_registerOk.jsp";
			}
		});
	}
}

function update() {
	if(!CheckField()) {
		return;
	}
	// send
	if( confirm("수정하시겠습니까 ? ") ) {
		$("#frm").attr("action","/community/marry_modify.do");
		$("#frm").submit();
	}
}

function chkDate(strValue) {
	var str = /[0-9][0-9][0-9][0-9][\-][0-9][0-9][\-][0-9][0-9]*$/;
	return str.test(strValue);
}

function chgLectureDate() {
	if( $(':radio[name="apply_type"]:checked').val() != "1" && $(':radio[name="apply_type"]:checked').val() != "2") {
		$("#divLecture1").hide();
		$("#divLecture2").hide();
	} else {
		if(document.querySelector('input[name = "apply_type"]:checked').value == '1') {
			document.getElementById("lecture_apply_day2").value = "";
			$("#divLecture1").show();
			$("#divLecture2").hide();
			$("#requestTR").show();
		} else if(document.querySelector('input[name = "apply_type"]:checked').value == '2') {
			document.getElementById("lecture_apply_day").value = "";
			$("#divLecture1").hide();
			$("#divLecture2").show();
			$("#requestTR").hide();
		} else {
			$("#divLecture1").hide();
			$("#divLecture2").hide();
		}
	}
}

function chkRegion(checkbox) {
	if(checkbox.checked == true) {
		document.getElementById('man_church_idx').value="";
		$("#man_church_idx").attr("disabled", "disabled");
		$("#man_taregion").css("display","");
	} else {
		$("#man_church_idx").attr("disabled", false);
		$("#man_taregion").css("display","none");
	}
}

function chkRegionW(checkbox) {
	if(checkbox.checked == true) {
		document.getElementById('woman_church_idx').value="";
		$("#woman_church_idx").attr("disabled", "disabled");
		$("#woman_taregion").css("display","");
	} else {
		$("#woman_church_idx").attr("disabled", false);
		$("#woman_taregion").css("display","none");
	}
}

function setYYYYMMDD(frm) {
	var v = frm.value;
	var i = frm.value.indexOf("-");
	var l = frm.value.length;
	var x = "";
	if(l == 0) {
		return;
	} else if(i>-1) {
		v = frm.value.replace("-","").replace("-","");
	}
	x = v.substring(0,4) + "-" + v.substring(4,6) + "-" + v.substring(6,8);
	frm.value = x;
}
function setCellphone(frm) {
	var v = frm.value.replace("-","").replace("-","").replace("+","");
	var l = frm.value.length;
	var i = 3;
	var x = v.substring(0,3);
	if (v.substring(0,2)=="82") v = substring(2);
	if (v.substring(0,2)=="02") i=2;
	
	frm.value = v.substring(0,i) + "-" + v.substring(i,l-4) + "-" + v.substring(l-4);
}


$(document).ready(function () {
    
	
	// use a stack
	setTimeout(function() {  
		if( $("#divLecture1 option").size() > 0 ) {
			$(':radio[name="apply_type"]:input[value=1]').trigger('click');
			$("#divLecture2").hide();
			$("#divLecture1").show();
		} else {
			$(':radio[name="apply_type"]:input[value=2]').trigger('click');
			$("#divLecture1").hide();
			$("#divLecture2").show();
		}
	}, 100);
});
</script>
<body>
<form name="frm" id="frm" action="/community/marry_insert.do" method="POST">
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}" />	
<input type="hidden" name="marry_no" id="marry_no" value="${param.marry_no}" />	
<input type="hidden" name="id" id="id" value="${sessionMemId}" />
    <p class="skipNavi" tabindex="1">본문바로가기</p>
    <!-- wrap -->
    <div id="wrap">
        <!-- header -->
        <header>
            <%@include file="/_common/inc/gnb.jsp" %>
        </header>
        <!-- //header -->
        <!-- subVisual -->
        <section class="subVisual commu">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap">
                <article>
                    <h3>카나혼인강좌&amp;약혼자주말 신청</h3>
                    <ul>
                        <li>홈</li>
                        <li>참여마당</li>
                        <li>카나혼인강좌&amp;약혼자주말 신청</li>
                    </ul>
                </article>
            </div>
        </section>
        <!-- //subVisual -->
        <!-- secWrap -->
        <div class="secWrap">
            <!-- sec01 -->
            <section class="sec01 fin">
                <!-- secCont -->
                <div class="secCont">
                    <!-- tabs -->
                    <ul class="tabs">
                        <li><a href="/community/cana.do">카나혼인강좌 안내</a></li>
                        <li><a href="/community/cana_manual.do">약혼자주말 안내</a></li>
                        <li class="on"><a href="/community/cana_register.do?">신청하기</a></li>
                    </ul>
                    <!-- //tabs -->
                    <h3 class="ttl tb fl v5">온라인 신청</h3>
                    <p class="starTxt fr v2">*수료증에 성명, 세례명, 생년월일, 본당이름이 들어갑니다. <br> 정확하게 적어주시길 부탁드립니다. (예비자는 수료증에 성함과 본당명만 기재 됩니다.)
                    </p>
                    <!-- wrtieTable -->
                    <div class="writeTable">
                        <table class="shirine_st write">
                            <caption>온라인 신청 폼</caption>
                            <tbody>
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
                                    <div class="row">
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
                                            <select name="man_member_type" id="man_member_type" onChange='if($("#man_member_type option:selected").val()==3) { $("#man_baptismal_name").val(" "); $("#man_baptismal_name").prop("readOnly","true") } else { $("#man_baptismal_name").val(""); $("#man_baptismal_name").removeProp("readOnly") }'>
                                            	<option value="">선택</option>
					                            <option value="1" <c:if test = "${marryContents.MAN_MEMBER_TYPE=='1'}"> selected </c:if>>신자</option>
					                            <option value="2" <c:if test = "${marryContents.MAN_MEMBER_TYPE=='2'}"> selected </c:if>>예비신자</option>
					                            <option value="3" <c:if test = "${marryContents.MAN_MEMBER_TYPE=='3'}"> selected </c:if>>비신자</option>
                                            </select>
                                            <span class="other">
                                                <input type="checkbox" name="man_other_region" id="man_other_region" value="1" onClick="javascript: chkRegion(this);"/>
                                                <label for=""><span>타지역</span></label><span id="man_taregion" style="display:none"><input type="text" name="man_taregion" placeholder="지역명을 입력하세요." /></span>
                                            </span>
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
                                    	<div class="row">
                                        <span class="form calenr v2">
											<input type="date" name="man_birthday" id="man_birthday" maxlength="10" onBlur="setYYYYMMDD(this)" value="${marryContents.MAN_BIRTHDAY}">
                                        </span>
                                        <span class="redTxt">(숫자만 입력, 예:2017-01-01)</span>
                                        </div>
                                    </td>
                                </tr>
                                <tr class="men">
                                    <th><i>*</i>연락처(남자)</th>
                                    <td class="telPhone">
                                        <span class="form">
                                        	<input type="tel" name="man_tel" id="man_tel" onBlur="setCellphone(this)" value="${marryContents.MAN_TEL}">
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
                                            <select name="woman_member_type" id="woman_member_type" onChange='if($("#woman_member_type option:selected").val()==3) { $("#woman_baptismal_name").val(" "); $("#woman_baptismal_name").prop("readOnly","true") } else { $("#woman_baptismal_name").val(""); $("#woman_baptismal_name").removeProp("readOnly") }'>
                                            	<option value="">선택</option>
					                            <option value="1" <c:if test = "${marryContents.MAN_MEMBER_TYPE=='1'}"> selected </c:if>>신자</option>
					                            <option value="2" <c:if test = "${marryContents.MAN_MEMBER_TYPE=='2'}"> selected </c:if>>예비신자</option>
					                            <option value="3" <c:if test = "${marryContents.MAN_MEMBER_TYPE=='3'}"> selected </c:if>>비신자</option>
                                            </select>
                                            <span class="other">
                                                <input type="checkbox" name="woman_other_region" id="woman_other_region" value="1"  onClick="javascript:chkRegionW(this);"/>
                                                <label for=""><span>타지역</span></label><span id="woman_taregion" style="display:none"><input type="text" name="woman_taregion" placeholder="지역명을 입력하세요." /></span>
                                            </span>
                                        </span>
                                    </td>
                                </tr>
                                <tr class="women">
                                    <th scope="row"><i></i>세례명(여자)</th>
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
                                    	<div class="row">
                                        <span class="form calenr v2">
                                        	<input type="date" id="woman_birthday" name="woman_birthday" maxlength="10" onBlur="setYYYYMMDD(this)" value="${marryContents.WOMAN_BIRTHDAY}">
                                        </span>
                                        <span class="redTxt">(숫자만 입력, 예:2017-01-01)</span>
                                        </div>
                                    </td>
                                </tr>
                                <tr class="women">
                                    <th><i>*</i>연락처(여자)</th>
                                    <td class="telPhone">
                                        <span class="form">
                                        	<input type="tel" name="woman_tel" id="woman_tel" onBlur="setCellphone(this)" value="${marryContents.WOMAN_TEL}">
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
                                    	<div class="row">
                                        <span class="form">
                                        <c:choose>
										<c:when test="${query_type == 'insert'}">
											<input  type="email" name="email" id="email" value="${sessionMemId}"/>
										</c:when>
										<c:otherwise>
                                        	<input  type="email" name="email" id="email" value="${marryContents.EMAIL}"/>
										</c:otherwise>
										</c:choose>
                                        </span>
                                        </div>
                                    </td>
                                </tr>
                                
                                <tr style="display:none">
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
                                    	<div class="row">
                                        <span class="form">
                                        	<input type="date" name="marry_day" id="marry_day" maxlength="10" onBlur="setYYYYMMDD(this)" value="${marryContents.MARRY_DAY}">
                                        </span><span class="redTxt">(숫자만 입력, 예:2017-01-01)</span>
                                        </div>
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
                                <tr id="requestTR">
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
                                <tr>
                                    <th>보안문자</th>
                                    <td class="secure">
                                        <em><img src="/captcha" id="captchaImg" alt="captcha img"></em>&nbsp;
                                    	<input type="text" placeholder="보안문자를 입력하세요" name="captcha" id="captcha" value="">
                                        <button type="button" onClick="imgRefresh()"><img src="../img/reset.png" alt="보안문자 새로고침"></button>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <!-- //wrtieTable -->
                    <h3 class="ttl tb">개인정보 수집 및 이용에 관한 동의</h3>
                    <div class="personal">
                        <div>
                          천주교 인천교구는 개인정보 보호법 제 30조에 따라 정보주체의 개인정보를 보호하고 이와 관련한 고충을 신속하고 원활하게 처리할 수 있도록 하기 위하여 <a href="/policy/personal.jsp">개인정보 처리지침</a>을 수립 ㆍ 공개합니다. 천주교 인천교구에서 취급하는 모든 개인정보는 관련법령에 근거하거나 정보주체의 동의에 의하여 수집ㆍ보유 하고 있습니다. 
                        </div>
                        <p><input type="checkbox" name="chkAgree" id="chkAgree" value="Y"><label for=""><span>상기 신청을 위한 개인정보 제공에 동의합니다.</span></label></p>
                    </div>
                    <ul class="btn">
                    	<c:choose>
						<c:when test="${query_type == 'insert'}">
	                        <li id="btn_insert"><a href="javascript:insert();">신청하기</a></li>
	                        <li id="btn_cancel"class="gray"><a href="javascript:clearForm();">취소</a></li>
						</c:when>
						<c:otherwise>
							<c:choose>
	                    		<c:when test="${marryContents.APPROVE_YN == 'N'}">
		                        <li id="btn_modify"><a href="javascript:update();">수정하기</a></li>
		                        <li id="btn_cancel2" class="gray"><a href="/member/my_doctrine.do">취소</a></li>
								</c:when>
		                    </c:choose>
						</c:otherwise>
						</c:choose>
                     </ul>
                </div>
                <!-- //secCont -->
            </section>
            <!-- //sec01 -->
        </div>
        <!-- //secWrap -->
        <!-- footer -->
        <footer>
            <%@ include file="/_common/inc/footer.jsp" %>
        </footer>
        <!-- //footer -->
    </div>
    <!--// wrap -->
</form>
</body>

</html>
