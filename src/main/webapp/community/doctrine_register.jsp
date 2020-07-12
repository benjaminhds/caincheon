<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script src="/admin/_common/js/admCommon.js"></script>
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
                //document.getElementById('addr2').value = data.jibunAddress;

            }
        }).open();
    }
</script>
<script>
function imgRefresh(){
    $("#captchaImg").attr("src", "/captcha?id=" + Math.random());
}

function clearForm() {
	document.getElementById("title").value = "";
	document.getElementById("contents").value = "";
	document.getElementById("captcha").value  = "";
}

function chkDate(strValue) {
	var extractNumber = /[^0-9]/g;//숫자만추출
	var pattern = /[0-9][0-9][0-9][0-9][\-][0-9][0-9][\-][0-9][0-9]*$/;//날자패턴YYYY-MM-DD 형식여부 체크
	return pattern.test(strValue) && strValue.replace(extractNumber, "").length==8 && strValue.length==10;
}

function chkPhone(strValue) {
	//var pattern1 = /01[1|6|7|8|9][\-][0-9][0-9][0-9][\-][0-9][0-9][0-9][0-9]*$/;
	var pattern1 = /01[1-9][\-][0-9][0-9][0-9][\-][0-9][0-9][0-9][0-9]*$/;
	var pattern2 = /010[\-][0-9][0-9][0-9][0-9][\-][0-9][0-9][0-9][0-9]*$/;
	return pattern1.test(strValue) || pattern2.test(strValue);
}

function CheckField() {
	//신자구분
    if( $(':radio[name="member_type"]:checked').val() != "1" && $(':radio[name="member_type"]:checked').val() != "2") {
		alert("신자구분을 입력해 주세요."); $("#member_type").focus(); return false; 
	}
	//성명
	if( $("#name").val() == "") { alert("성명을 입력해 주세요."); $("#name").focus(); return false; }
	//성별
	if( $(':radio[name="sex"]:checked').val() != "1" && $(':radio[name="sex"]:checked').val() != "2") {
		alert("성별을 입력해 주세요."); $("#sex").focus(); return false; 
	}
	// 본당
	if( $("#church_idx option:selected").val() == "") { alert("소속본당을 선택해 주세요."); $("#church_idx").focus(); return false; }
	//생년월일
	if( $(':radio[name="birth_type"]:checked').val() != "1" && $(':radio[name="birth_type"]:checked').val() != "2") {
		alert("양력/음력을 선택해 주세요."); $("#birth_type").focus(); return false; 
	}
	var regNumber = /[^0-9]/g;
	if( $("#birthday").val() == "" ) {
		alert("생년월일을 입력해 주세요.");
		return false;
	} else {
		if( !chkDate($("#birthday").val()) ) {
			alert("생년월일 포멧이 맞지 않거나, 정확하지 않아요. \n\n다시 입력해 주세요.");
			return false;
		}
	}
	// 주소
	if( $("#postcode").val() == "") { alert("주소를 입력해 주세요."); $("#postcode").focus(); return false; }
	if( $("#addr1").val() == "") { alert("주소를 입력해 주세요."); $("#addr1").focus(); return false; }
	
	// 연락처
	if( !chkPhone($("#m_tel").val()) )  {
		alert("휴대폰을 패턴에 맞게 입력해 주세요."); $("#m_tel").focus(); return false; 
	}
	
	// 정규식 - 이메일 유효성 검사
	var regEmail = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	if(!regEmail.test($("#email").val())) { 
		alert('이메일 주소가 유효하지 않습니다'); $("#email").focus(); 
		return false; 
	}
	//
	var uploadFile = document.getElementById('thumbFile').value;
	if(uploadFile != "") {
		var fileExt = uploadFile.substring(uploadFile.lastIndexOf(".") + 1);
		var reg = /gif|jpg|jpeg|png/i;
		if(reg.test(fileExt) == false) {
			alert("첨부파일은 gif,jpg,png로 된 이미지만 가능합니다.");
			return false;
		}
	}
	
	if( $("#captcha").val() == "") { alert("보안문자를 입력해 주세요."); $("#captcha").focus(); return false; }
	
	if( !$("input:checkbox[id=chkAgree]").is(":checked") ) { alert("개인정보 수집 및 이용에 관한 동의를 체크해 주세요."); $("#chkAgree").focus(); return false; }
 
	if( $("#id").val() == "") { 
		if(confirm("비회원일 경우, 신청은 가능하나 마이페이지에서 신청현황을 확인 하실 수 없습니다. 신청내역은 기재해주신 메일로 발송됩니다. \n\n 비회원으로 신청하시겠습니까?")){
			return true;
		} else {
			return false;
		}
	}
	
	return true;
}

function insert() {
	if(!CheckField()) {
		return false;
	}

	// send
	if( confirm("신청하시겠습니까 ? ") ) {
		$("#frm").attr("action","/community/doctrine_insert.do");

		$("#btn_insert").html("<a href='#'>신청 처리 중..</a>");
		$("#btn_insert").children("a").remove();//event unbind, if <input, then $("#").off() 
		$("#btn_cancel").children("a").remove();//event unbind if <input, then $("#").off()
		$("#btn_insert").delay(500).html("<a href='#'>메일 발송 중..</a>");
		
		console.log("전송데이터1:"+$("#frm").serialize());
		console.log("전송데이터2:"+$("form[name=frm]").serialize());

		_requestMultipart("frm", function(status, responseData) {
			console.log("response: status="+status);
			console.log("response: responseData="+JSON.stringify(responseData));
			console.log("RESULT_YN="+responseData.RESULT_YN);
			
			if(responseData.RESULT_YN == 'N' || status == 'error') {
				if(responseData.MESSAGE != "") 
					alert(responseData.MESSAGE);
				else 
					alert(JSON.stringify(responseData));
				
				$("#btn_insert").children("a").on("click", function(){ insert() } );
				$("#btn_insert").delay(8).html("<a href='#'>관리자 확인 필요</a>");
				
			} else if(responseData.RESULT_YN == 'Y') {
				$("#btn_insert").delay(8).html("<a href='#'>처리 완료 !!</a>").delay(1000);
				location.href="/community/doctrine_registerOk.jsp";
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
		$("#frm").attr("action","/community/doctrine_modify.do");
		$("#frm").submit();
	}
}

window.onload = function() {
	//CKEDITOR.replace('contents');
	
	// thumbnail
	$("#thumbFile").on("change", {maxWidth:200, maxHeight:150, imgPreviewId:"imagePreview"},  handleImageFileSelect);
}
</script>
<body>
<form id="frm" name="frm" method="post" enctype="multipart/form-data" action="/community/doctrine_insert.do">
<input type="hidden" name="id" id="id" value="${sessionMemId }"/>
<input type="hiddne" name="doctrine_no" id="doctrine_no" value="${_params.doctrine_no}"/>
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
                    <h3>통신교리신청</h3>
                    <ul>
                        <li>홈</li>
                        <li>참여마당</li>
                        <li>통신교리신청</li>
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
                        <li><a href="/community/doctrine.jsp">통신교리안내</a></li>
                        <li><a href="/community/doctrine_manual.jsp">신청방법</a></li>
                        <li class="on"><a href="/community/doctrine_register.do?">신청하기</a></li>
                    </ul>
                    <!-- //tabs -->
                    <h3 class="ttl tb">수강료 안내</h3>
                    <!-- feeLine -->
                    <div class="feeLine">
                        <p><em>수강료 <span> : 예비신지-28,000원 / 재교육신자-40,000원</span></em></p>
                        <p><em>수강료 입금하실 계좌 <span> : 신협(온라인) 131-002-128014 / 예금주 : 선교국</span></em></p>
                        <span>입금 후 반드시 전화연락을 주시기 바랍니다. (Tel. 032-762-9717)</span>
                    </div>
                    <!-- //feeLine -->
                    <h3 class="ttl tb">온라인 신청</h3>
                    <!-- wrtieTable -->
                    <div class="writeTable">
                        <table class="shirine_st write">
                            <caption>온라인 신청 폼</caption>
                            <tbody>
                                <tr>
                                    <th scope="row"><i>*</i>신자구분</th>
                                    <td>
                                        <span class="chkForm">
                                            <input type="radio" name="member_type"  id="member_type" value="1" <c:if test="${docContents.MEMBER_TYPE=='1' }" >checked="checked"</c:if>>
                                            <label for=""><span>예비신자</span></label>
                                        </span>
                                        <span class="chkForm">
                                            <input type="radio" name="member_type"  id="member_type" value="2" <c:if test="${docContents.MEMBER_TYPE=='2' }" >checked="checked"</c:if>>
                                            <label for=""><span>재교육자</span></label>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row"><i>*</i>성명</th>
                                    <td>
                                        <span class="form">
                                            <label for=""></label>
                                            <input type="text" name="name" id="name" value="${docContents.NAME }">
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row"><i>*</i>성별</th>
                                    <td>
                                        <span class="chkForm">
                                            <input type="radio" name="sex"  id="sex"  value="1" <c:if test="${docContents.SEX=='1' }" >checked="checked"</c:if>>
                                            <label for=""><span>남자</span></label>
                                        </span>
                                        <span class="chkForm">
                                            <input type="radio" name="sex"  id="sex"  value="2" <c:if test="${docContents.SEX=='2' }" >checked="checked"</c:if>>
                                            <label for=""><span>여자</span></label>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">세례명</th>
                                    <td>
                                        <span class="form">
                                            <label for=""></label>
                                            <input  type="text" name="baptismal_name" id="baptismal_name" value="${docContents.BAPTISMAL_NAME }">
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">직업</th>
                                    <td>
                                        <span class="form">
                                            <label for=""></label>
                                            <input  type="text" name="job" id="job" value="${docContents.JOB }">
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row"><i>*</i>소속본당</th>
                                    <td>
                                        <span class="form">
                                            <label for=""></label>
                                            <select name="church_idx" id="church_idx">
                                                <option value="">선택</option>
                                                <c:if test="${fn:length(CHURCH_LIST) > 0}">
	                                                <c:forEach var="entry" items="${CHURCH_LIST}" varStatus="status">
	                                                	<option value="${entry.CHURCH_IDX}" <c:if test = "${docContents.CHURCH_IDX==entry.CHURCH_IDX }"> selected </c:if>>${entry.NAME}</option>
	                                                </c:forEach>
	                                            </c:if>
                                            </select>
                                        </span>
                                        <span class="redTxt">예비신자께서는 영세 받을 성당, 현재다니고 있는 성당 혹은 주소지에서 가장 가까운 성당의 이름을 선택하여 주시기 바랍니다.</span>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="rowgroup" rowspan="2"><i>*</i>생년월일</th>
                                    <td>
                                        <span class="chkForm">
                                            <input type="radio" name="birth_type" id="birth_type" value="1" <c:if test="${docContents.BIRTH_TYPE=='1' }" >checked="checked"</c:if>>
                                            <label for=""><span>양력</span></label>
                                        </span>
                                        <span class="chkForm">
                                            <input type="radio" name="birth_type" id="birth_type" value="2" <c:if test="${docContents.BIRTH_TYPE=='2' }" >checked="checked"</c:if>>
                                            <label for=""><span>음력</span></label>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="years">
                                        <span class="form calenr v2">
                                            <input type="text" name="birthday" id="birthday" maxlength="10" value="${docContents.BIRTHDAY}">  
                                        </span>
                                        <span class="redTxt">(예:2017-01-01)</span>
                                    </td>
                                </tr>
                                
                                <tr>
                                    <th scope="row"><i>*</i>주소</th>
                                    <td class="addr">
                                        <span class="form">
                                            <label for=""></label>
                                            <input type="text" name="postcode" id="postcode" onclick="execDaumPostcode()" placeholder="우편번호" value="${docContents.POSTCODE}">
                                            <button type="button" onclick="execDaumPostcode()">우편번호 검색</button>
                                        </span>
                                        <span class="form">
                                            <label for=""></label>
                                            <input type="text" name="addr1" id="addr1" placeholder="도로명주소" value="${docContents.ADDR1}">
                                        </span>
                                        <span class="form">
                                            <label for=""></label>
                                            <input type="text" name="addr2" id="addr2" placeholder="나머지주소" value="${docContents.ADDR2}">
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <th><i>*</i>연락처</th>
                                    <td class="telPhone">
                                        <span class="form v2">
                                        	<input type="text" name="m_tel" id="m_tel" value="${docContents.M_TEL}">
                                        	<div class="redTxt">(예:010-1004-1004)</div>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <th>연락처2</th>
                                    <td class="telPhone">
                                        <span class="form v2">
                                        	<input type="text" name="h_tel" id="h_tel" value="${docContents.H_TEL}">
                                        	<div class="redTxt">(예:032-123-1004)</div>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <th><i>*</i>이메일</th>
                                    <td class="telPhone emails">
                                    	<c:set var="USER_EMAIL" value="${docContents.EMAIL}"></c:set>
                                    	<c:if test="${sessionMemId ne ''}">
                                    		<c:set var="USER_EMAIL" value="${sessionMemId}"></c:set>
                                    	</c:if>
                                    	<input  type="email" name="email" id="email" value="${USER_EMAIL}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <th>이미지(사진)</th>
                                    <td>
                                    	<div class="form-group input-group">
											<input class="form-control" type="file" name="thumbFile" id="thumbFile">
										</div>
										<div class="img_wrap"><p><img id="imagePreview" /></p><p id="imagePreview_info"></p></div>
										<c:if test="${fn:length(docContents.STRFILENAME) > 0 }">
											<img alt="" src="/upload/${docContents.STRFILENAME}" width="120px">${docContents.FILENAME}
										</c:if>
                                    </td>
                                </tr>
                                <tr>
                                    <th>남기실 말씀</th>
                                    <td>
                                        <textarea name="user_comment" id="user_comment" cols="30" rows="10">${docContents.USER_COMMENT }</textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <th>보안문자</th>
                                    <td class="secure">
                                    	<em><img src="/captcha" id="captchaImg" alt="captcha img"></em>&nbsp;
                                    	<input type="text" placeholder="보안문자를 입력하세요" name="captcha" id="captcha" value="">
                                        <button type="button" onClick="imgRefresh()"><img src="../img/reset.png" alt="보안문자 새로고침"></button>
                                        <!-- span class="redTxt">보안문자를 입력해주세요.</span -->
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
	                        <li id="btn_cancel" class="gray"><a href="javascript:clearForm();">취소</a></li>
						</c:when>
						<c:otherwise>
		                    <c:choose>
			                    <c:when test="${docContents.APPROVE_YN == 'N'}">
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
