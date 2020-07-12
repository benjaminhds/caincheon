<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<%
if(sessionMemId==null||sessionMemId.equals("")) {                // id가 Null 이거나 없을 경우
	response.sendRedirect("/member/login.jsp");    // 로그인 페이지로 리다이렉트 한다.
}
%>
<script>
function imgRefresh(){
    $("#captchaImg").attr("src", "/captcha?id=" + Math.random());
}

function changeMemberType(i, tp) {
	//alert(tp);
	switch(tp) {
	case 1:
		if(i<10) {
			$('#s_before_number0'+i).prop('disabled', false);
			$('#s_order_name0'+i).prop('disabled', false);
		} else {
			$('#s_before_number'+i).prop('disabled', false);
			$('#s_order_name'+i).prop('disabled', false);
		}
		break;
	case 2:
		if(i<10) {
			$('#s_before_number0'+i).prop('disabled', false);
			$('#s_order_name0'+i).prop('disabled', true);
		} else {
			$('#s_before_number'+i).prop('disabled', false);
			$('#s_order_name'+i).prop('disabled', true);
		}
		break;
	case 3:
		if(i<10) {
			$('#s_before_number0'+i).prop('disabled', true);
			$('#s_order_name0'+i).prop('disabled', true);
		} else {
			$('#s_before_number'+i).prop('disabled', true);
			$('#s_order_name'+i).prop('disabled', true);
		}
		break;
	}
}


function selectCheckId(idx) {
	var id = document.getElementById('s_id'+idx).value;
	if(id == '') {
		alert('아이디를 먼저 입력하세요.');
		document.getElementById('s_id'+idx).focus();
		return false;
	}
	
	$.ajax({
		url:'/admin/member/selectUserMemberInfo.do',
        type:'get',
        data : {"id":id},
        dataType:'text',
        success : function(responseData){
    		var data = JSON.parse(responseData);
    		alert(data.result);
    		if(data.result == '') {
    			alert('아이디가 존재하지 않습니다');
        		document.getElementById('s_checkId'+idx).value = 'X';
	   			//document.getElementById('id').value = '';
    		} else {
        		document.getElementById('s_checkId'+idx).value = 'Y';
    		}
         } ,
         error : function(){
     		document.getElementById('checkId').value = 'N';
              // alert('실패 ㅠㅠ');
         }
    });
}


function insert() {
	var euchaCnt = 0;
	for(var i = 1;i<=20; i++) {
		if(i<10) {
			if( $(':radio[name="s_gubun0'+i+'"]:checked').val() == "1" ) {
				if( $("#s_id0"+i).val() == "") { alert("아이디를 입력해 주세요."); $("#s_id0"+i).focus(); return; }
				if( $("#s_baptismal_name0"+i).val() == "") { alert("세례명을 입력해 주세요."); $("#s_baptismal_name0"+i).focus(); return; }
				if( $("#s_birthday0"+i).val() == "") { alert("생년월일을 입력해 주세요."); $("#s_birthday0"+i).focus(); return; }
				if( $("#s_m_tel0"+i).val() == "") { alert("전화번호를 입력해 주세요."); $("#s_m_tel0"+i).focus(); return; }
				if( $("#s_order_name0"+i).val() == "") { alert("수도회명을 입력해 주세요."); $("#s_order_name0"+i).focus(); return; }
				euchaCnt++;
			} else if( $(':radio[name="s_gubun0'+i+'"]:checked').val() == "2") {
				if( $("#s_id0"+i).val() == "") { alert("아이디를 입력해 주세요."); $("#s_id0"+i).focus(); return; }
				if( $("#s_baptismal_name0"+i).val() == "") { alert("세례명을 입력해 주세요."); $("#s_baptismal_name0"+i).focus(); return; }
				if( $("#s_birthday0"+i).val() == "") { alert("생년월일을 입력해 주세요."); $("#s_birthday0"+i).focus(); return; }
				if( $("#s_m_tel0"+i).val() == "") { alert("전화번호를 입력해 주세요."); $("#s_m_tel0"+i).focus(); return; }
				if( $("#s_before_number0"+i).val() == "") { alert("직전수여번호를 입력해 주세요."); $("#s_before_number0"+i).focus(); return; }
				euchaCnt++;
			} else if( $(':radio[name="s_gubun0'+i+'"]:checked').val() == "3") {
				if( $("#s_id0"+i).val() == "") { alert("아이디를 입력해 주세요."); $("#s_id0"+i).focus(); return; }
				if( $("#s_baptismal_name0"+i).val() == "") { alert("세례명을 입력해 주세요."); $("#s_baptismal_name0"+i).focus(); return; }
				if( $("#s_birthday0"+i).val() == "") { alert("생년월일을 입력해 주세요."); $("#s_birthday0"+i).focus(); return; }
				if( $("#s_m_tel0"+i).val() == "") { alert("전화번호를 입력해 주세요."); $("#s_m_tel0"+i).focus(); return; }
				euchaCnt++;
			} else {
//				alert('신청구분을 선택하세요.');
//				return;
			}
		} else {
			if( $(':radio[name="s_gubun'+i+'"]:checked').val() == "1" ) {
				if( $("#s_id"+i).val() == "") { alert("아이디를 입력해 주세요."); $("#s_id"+i).focus(); return; }
				if( $("#s_baptismal_name"+i).val() == "") { alert("세례명을 입력해 주세요."); $("#s_baptismal_name"+i).focus(); return; }
				if( $("#s_birthday"+i).val() == "") { alert("생년월일을 입력해 주세요."); $("#s_birthday"+i).focus(); return; }
				if( $("#s_m_tel"+i).val() == "") { alert("전화번호를 입력해 주세요."); $("#s_m_tel"+i).focus(); return; }
				if( $("#s_order_name"+i).val() == "") { alert("수도회명을 입력해 주세요."); $("#s_order_name"+i).focus(); return; }
				euchaCnt++;
			} else if( $(':radio[name="s_gubun'+i+'"]:checked').val() == "2") {
				if( $("#s_id"+i).val() == "") { alert("아이디를 입력해 주세요."); $("#s_id"+i).focus(); return; }
				if( $("#s_baptismal_name"+i).val() == "") { alert("세례명을 입력해 주세요."); $("#s_baptismal_name"+i).focus(); return; }
				if( $("#s_birthday"+i).val() == "") { alert("생년월일을 입력해 주세요."); $("#s_birthday"+i).focus(); return; }
				if( $("#s_m_tel"+i).val() == "") { alert("전화번호를 입력해 주세요."); $("#s_m_tel"+i).focus(); return; }
				if( $("#s_before_number"+i).val() == "") { alert("직전수여번호를 입력해 주세요."); $("#s_before_number"+i).focus(); return; }
				euchaCnt++;
			} else if( $(':radio[name="s_gubun'+i+'"]:checked').val() == "3") {
				if( $("#s_id"+i).val() == "") { alert("아이디를 입력해 주세요."); $("#s_id"+i).focus(); return; }
				if( $("#s_baptismal_name"+i).val() == "") { alert("세례명을 입력해 주세요."); $("#s_baptismal_name"+i).focus(); return; }
				if( $("#s_birthday"+i).val() == "") { alert("생년월일을 입력해 주세요."); $("#s_birthday"+i).focus(); return; }
				if( $("#s_m_tel"+i).val() == "") { alert("전화번호를 입력해 주세요."); $("#s_m_tel"+i).focus(); return; }
				euchaCnt++;
			} else {
				//alert('신청구분을 선택하세요.');
				//return;
			}
		}
	}
	if(euchaCnt == 0) {
		alert('등록할 신청자가 없습니다.');
		return;
	}
/* 	if( $(':radio[name="apply_type"]:checked').val() != "1" && $(':radio[name="apply_type"]:checked').val() != "2") {
		alert("신청구분을 입력해 주세요."); $("#apply_type").focus(); return; 
	}

	if( $("#lecture_apply_day").val() == "") { alert("강좌신청날짜를 입력해 주세요."); $("#lecture_apply_day").focus(); return; }

	if( $("#man_name").val() == "") { alert("성명(남자)을 입력해 주세요."); $("#man_name").focus(); return; }
	if( $("#woman_name").val() == "") { alert("성명(여자)을 입력해 주세요."); $("#woman_name").focus(); return; }

	if( $("#man_church_idx").val() == "") { alert("본당을 선택해 주세요."); $("#man_church_idx").focus(); return; }
	if( $("#woman_church_idx").val() == "") { alert("본당을 선택해 주세요."); $("#woman_church_idx").focus(); return; }
	
	if( $("#man_member_type").val() == "") { alert("신자구분을 선택해 주세요."); $("#man_member_type").focus(); return; }
	if( $("#woman_member_type").val() == "") { alert("신자구분을 선택해 주세요."); $("#woman_member_type").focus(); return; }

	if( $("#man_birthday").val() == "") { alert("주민등록상 생년월일(남자)을 입력해 주세요."); $("#man_birthday").focus(); return; }
	if( $("#woman_birthday").val() == "") { alert("주민등록상 생년월일(여자)을 입력해 주세요."); $("#woman_birthday").focus(); return; }

	if( $("#man_tel").val() == "") { alert("연락처(남자)을 입력해 주세요."); $("#man_tel").focus(); return; }
	if( $("#woman_tel").val() == "") { alert("연락처(여자)을 입력해 주세요."); $("#woman_tel").focus(); return; }

	// 정규식 - 이메일 유효성 검사
	var regEmail = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	if(!regEmail.test($("#email").val())) { alert('이메일 주소가 유효하지 않습니다'); $("#email").focus(); return; }
 */	
	if( $("#captcha").val() == "") { alert("보안문자를 입력해 주세요."); $("#captcha").focus(); return; }

	// send
	if( confirm("신청하시겠습니까 ? ") ) {
		$("#frm").submit();
	}
}

function update() {
	var euchaCnt = 0;
	for(var i = 1;i<=20; i++) {
		if(i<10) {
			if( $(':radio[name="s_gubun0'+i+'"]:checked').val() == "1" ) {
				if( $("#s_id0"+i).val() == "") { alert("아이디를 입력해 주세요."); $("#s_id0"+i).focus(); return; }
				if( $("#s_baptismal_name0"+i).val() == "") { alert("세례명을 입력해 주세요."); $("#s_baptismal_name0"+i).focus(); return; }
				if( $("#s_birthday0"+i).val() == "") { alert("생년월일을 입력해 주세요."); $("#s_birthday0"+i).focus(); return; }
				if( $("#s_m_tel0"+i).val() == "") { alert("전화번호를 입력해 주세요."); $("#s_m_tel0"+i).focus(); return; }
				if( $("#s_order_name0"+i).val() == "") { alert("수도회명을 입력해 주세요."); $("#s_order_name0"+i).focus(); return; }
				euchaCnt++;
			} else if( $(':radio[name="s_gubun0'+i+'"]:checked').val() == "2") {
				if( $("#s_id0"+i).val() == "") { alert("아이디를 입력해 주세요."); $("#s_id0"+i).focus(); return; }
				if( $("#s_baptismal_name0"+i).val() == "") { alert("세례명을 입력해 주세요."); $("#s_baptismal_name0"+i).focus(); return; }
				if( $("#s_birthday0"+i).val() == "") { alert("생년월일을 입력해 주세요."); $("#s_birthday0"+i).focus(); return; }
				if( $("#s_m_tel0"+i).val() == "") { alert("전화번호를 입력해 주세요."); $("#s_m_tel0"+i).focus(); return; }
				if( $("#s_before_number0"+i).val() == "") { alert("직전수여번호를 입력해 주세요."); $("#s_before_number0"+i).focus(); return; }
				euchaCnt++;
			} else if( $(':radio[name="s_gubun0'+i+'"]:checked').val() == "3") {
				if( $("#s_id0"+i).val() == "") { alert("아이디를 입력해 주세요."); $("#s_id0"+i).focus(); return; }
				if( $("#s_baptismal_name0"+i).val() == "") { alert("세례명을 입력해 주세요."); $("#s_baptismal_name0"+i).focus(); return; }
				if( $("#s_birthday0"+i).val() == "") { alert("생년월일을 입력해 주세요."); $("#s_birthday0"+i).focus(); return; }
				if( $("#s_m_tel0"+i).val() == "") { alert("전화번호를 입력해 주세요."); $("#s_m_tel0"+i).focus(); return; }
				euchaCnt++;
			} else {
//				alert('신청구분을 선택하세요.');
//				return;
			}
		} else {
			if( $(':radio[name="s_gubun'+i+'"]:checked').val() == "1" ) {
				if( $("#s_id"+i).val() == "") { alert("아이디를 입력해 주세요."); $("#s_id"+i).focus(); return; }
				if( $("#s_baptismal_name"+i).val() == "") { alert("세례명을 입력해 주세요."); $("#s_baptismal_name"+i).focus(); return; }
				if( $("#s_birthday"+i).val() == "") { alert("생년월일을 입력해 주세요."); $("#s_birthday"+i).focus(); return; }
				if( $("#s_m_tel"+i).val() == "") { alert("전화번호를 입력해 주세요."); $("#s_m_tel"+i).focus(); return; }
				if( $("#s_order_name"+i).val() == "") { alert("수도회명을 입력해 주세요."); $("#s_order_name"+i).focus(); return; }
				euchaCnt++;
			} else if( $(':radio[name="s_gubun'+i+'"]:checked').val() == "2") {
				if( $("#s_id"+i).val() == "") { alert("아이디를 입력해 주세요."); $("#s_id"+i).focus(); return; }
				if( $("#s_baptismal_name"+i).val() == "") { alert("세례명을 입력해 주세요."); $("#s_baptismal_name"+i).focus(); return; }
				if( $("#s_birthday"+i).val() == "") { alert("생년월일을 입력해 주세요."); $("#s_birthday"+i).focus(); return; }
				if( $("#s_m_tel"+i).val() == "") { alert("전화번호를 입력해 주세요."); $("#s_m_tel"+i).focus(); return; }
				if( $("#s_before_number"+i).val() == "") { alert("직전수여번호를 입력해 주세요."); $("#s_before_number"+i).focus(); return; }
				euchaCnt++;
			} else if( $(':radio[name="s_gubun'+i+'"]:checked').val() == "3") {
				if( $("#s_id"+i).val() == "") { alert("아이디를 입력해 주세요."); $("#s_id"+i).focus(); return; }
				if( $("#s_baptismal_name"+i).val() == "") { alert("세례명을 입력해 주세요."); $("#s_baptismal_name"+i).focus(); return; }
				if( $("#s_birthday"+i).val() == "") { alert("생년월일을 입력해 주세요."); $("#s_birthday"+i).focus(); return; }
				if( $("#s_m_tel"+i).val() == "") { alert("전화번호를 입력해 주세요."); $("#s_m_tel"+i).focus(); return; }
				euchaCnt++;
			} else {
				//alert('신청구분을 선택하세요.');
				//return;
			}
		}
	}
	if(euchaCnt == 0) {
		alert('등록할 신청자가 없습니다.');
		return;
	}
	if( $("#captcha").val() == "") { alert("보안문자를 입력해 주세요."); $("#captcha").focus(); return; }

	// send
	if( confirm("수정하시겠습니까 ? ") ) {
		$("#frm").attr("action","/community/eucharist_modify.do");
		$("#frm").submit();
	}
}


window.onload = function() {
	if(document.getElementById('query_type').value='insert') {
		for(var i = 1;i<=20; i++) {
			if(i<10) {
				//$('#s_name0'+i).prop('disabled', true);
				//$('#s_baptismal_name0'+i).prop('disabled', true);
				//$('#s_birthday0'+i).prop('disabled', true);
				//$('#s_m_tel0'+i).prop('disabled', true);
				$('#s_before_number0'+i).prop('disabled', true);
				$('#s_order_name0'+i).prop('disabled', true);
			} else {
				//$('#s_name'+i).prop('disabled', true);
				//$('#s_baptismal_name'+i).prop('disabled', true);
				//$('#s_birthday'+i).prop('disabled', true);
				//$('#s_m_tel'+i).prop('disabled', true);
				$('#s_before_number'+i).prop('disabled', true);
				$('#s_order_name'+i).prop('disabled', true);
			}
		}		
	}
}
</script>
<body>
<form id="frm" name="frm" method="post" action="/community/eucharist_insert.do" method="POST">
<input type="hidden" name="eucha_mid" id="eucha_mid" value="${_params.eucha_mid}" />	
<input type="hidden" name="id" id="id" value="${sessionMemId }"/>
<input type="hidden" name="query_type" id="query_type" value="${query_type}"/>
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
                    <h3>성체분배권 신청</h3>
                    <ul>
                        <li>홈</li>
                        <li>참여마당</li>
                        <li>성체분배권 신청</li>
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
                    <ul class="tabs ea2">
                        <li><a href="/community/eucharist.jsp">성체분배권 수여교육 안내</a></li>
                        <li class="on"><a href="/community/eucharist_apply.do?">신청하기</a></li>
                    </ul>
                    <!-- //tabs -->
                    <h3 class="ttl tb">신청하기</h3>
                    <table class="shirine_st v2">
                        <caption>성체분배권 신청자 정보</caption>
                        <tbody>
                            <tr>
                                <th scope="row" rowspan="2">신청자</th>
                                <td>${MEMINFO_MAP.NAME }</td>
                                <th scope="row">메일</th>
                                <td>${MEMINFO_MAP.ID }</td>
                            </tr>
                            <tr>
                                <td>${MEMINFO_MAP.BAPTISMALNAME }</td>
                                <th scope="row">연락처</th>
                                <td>${MEMINFO_MAP.TEL }</td>
                            </tr>
                            <tr>
                                <th scope="row">소속본당 또는 현재 소임지</th>
                                <td colspan="3" class="inp"><label for=""></label><input type="text" name="church_name" id="church_name" value="${MEMINFO_MAP.CHURCH_NAME }"></td>
                            </tr>
                        </tbody>
                    </table>
                    <p class="blueTxt">* ‘${MEMINFO_MAP.NAME }(${MEMINFO_MAP.BAPTISMALNAME })’는(은) 청원자를 대신하여 온라인으로 2017 비정규 성체분배권 수여교육을 신청하오니 확인하여 주시기바랍니다.</p>
                    <!-- ovrTable -->
                    <div class="ovrTable boardList">
                        <table>
                            <caption>성체분배권 신청 폼</caption>
                            <colgroup>
                                <col style="width:5%;">
                                <col style="width:19%;">
                                <col style="width:22%;">
                                <col style="width:9%;">
                                <col style="width:9%;">
                                <col style="width:9%;">
                                <col style="width:9%;">
                                <col style="width:9%;">
                                <col style="width:9%;">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col">No</th>
                                    <th scope="col">구분</th>
                                    <th scope="col">아이디</th>
                                    <th scope="col">이름</th>
                                    <th scope="col">세례명</th>
                                    <th scope="col">생년월일</th>
                                    <th scope="col">휴대폰</th>
                                    <th scope="col">직전수여번호</th>
                                    <th scope="col">수도회명</th>
                                </tr>
                            </thead>
                            <tbody>
				<tr>
					<th scope="row">01</th>
					<td>
					<span class="form">
					<input type="radio" name="s_gubun01" id="s_gubun01" value="1" onClick="changeMemberType(1,1)" <c:if test="${euchaContents.s_gubun01=='1' }" >checked="checked"</c:if>>
					<label for="">수도</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun01" id="s_gubun01" value="2" onClick="changeMemberType(1,2)" <c:if test="${euchaContents.s_gubun01=='2' }" >checked="checked"</c:if>>
					<label for="">갱신	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun01" id="s_gubun01" value="3" onClick="changeMemberType(1,3)" <c:if test="${euchaContents.s_gubun01=='3' }" >checked="checked"</c:if>>
					<label for="">신규	</label>
					</span>
					</td>
					<td>
					<span class="inp">
					<label for="">
					</label>
					<input type="text" name="s_id01" id="s_id01" value="${euchaContents.s_id01 }">
					</span>
					<input type="hidden" name="s_checkId01" id="s_checkId01">
					<input type="hidden" name="s_eucha_sno01" id="s_eucha_sno01" value="${euchaContents.s_eucha_sno01 }">
					<button type="button" id="confirmId" class="btn btn-default pull-right" onclick="selectCheckId('01');return false;">아이디확인</button>
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_name01" id="s_name01" value="${euchaContents.s_name01 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_baptismal_name01" id="s_baptismal_name01" value="${euchaContents.s_baptismal_name01 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" maxlength="10" name="s_birthday01" id="s_birthday01" value="${euchaContents.s_birthday01 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_m_tel01" id="s_m_tel01" value="${euchaContents.s_m_tel01 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_before_number01" id="s_before_number01" value="${euchaContents.s_before_number01 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_order_name01" id="s_order_name01" value="${euchaContents.s_order_name01 }">
					</td>
					</tr>
				<tr>
					<th scope="row">02	</th>
					<td>
					<span class="form">
					<input type="radio" name="s_gubun02" id="s_gubun02" value="1" onClick="changeMemberType(2,1)" <c:if test="${euchaContents.s_gubun02=='1' }" >checked="checked"</c:if>>
					<label for="">수도	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun02" id="s_gubun02" value="2" onClick="changeMemberType(2,2)" <c:if test="${euchaContents.s_gubun02=='2' }" >checked="checked"</c:if>>
					<label for="">갱신	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun02" id="s_gubun02" value="3" onClick="changeMemberType(2,3)" <c:if test="${euchaContents.s_gubun02=='3' }" >checked="checked"</c:if>>
					<label for="">신규	</label>
					</span>
					</td>
					<td>
					<span class="inp">
					<label for="">
					</label>
					<input type="text" name="s_id02" id="s_id02" value="${euchaContents.s_id02 }">
					</span>
					<input type="hidden" name="s_checkId02" id="s_checkId02">
					<button type="button" id="confirmId" class="btn btn-default pull-right" onclick="selectCheckId('02');return false;">아이디확인</button>

					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_name02" id="s_name02" value="${euchaContents.s_name02 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_baptismal_name02" id="s_baptismal_name02" value="${euchaContents.s_baptismal_name02 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" maxlength="10" name="s_birthday02" id="s_birthday02" value="${euchaContents.s_birthday02 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_m_tel02" id="s_m_tel02" value="${euchaContents.s_m_tel02 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_before_number02" id="s_before_number02" value="${euchaContents.s_before_number02 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_order_name02" id="s_order_name02" value="${euchaContents.s_order_name02 }">
					</td>
					</tr>
				<tr>
					<th scope="row">03	</th>
					<td>
					<span class="form">
					<input type="radio" name="s_gubun03" id="s_gubun03" value="1" onClick="changeMemberType(3,1)" <c:if test="${euchaContents.s_gubun03=='1' }" >checked="checked"</c:if>>
					<label for="">수도	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun03" id="s_gubun03" value="2" onClick="changeMemberType(3,2)" <c:if test="${euchaContents.s_gubun03=='2' }" >checked="checked"</c:if>>
					<label for="">갱신	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun03" id="s_gubun03" value="3" onClick="changeMemberType(3,3)" <c:if test="${euchaContents.s_gubun03=='3' }" >checked="checked"</c:if>>
					<label for="">신규	</label>
					</span>
					</td>
					<td>
					<span class="inp">
					<label for="">
					</label>
					<input type="text" name="s_id03" id="s_id03" value="${euchaContents.s_id03 }">
					</span>
					<input type="hidden" name="s_checkId03" id="s_checkId03">
					<button type="button" id="confirmId" class="btn btn-default pull-right" onclick="selectCheckId('03');return false;">아이디확인</button>

					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_name03" id="s_name03" value="${euchaContents.s_name03 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_baptismal_name03" id="s_baptismal_name03" value="${euchaContents.s_baptismal_name03 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" maxlength="10" name="s_birthday03" id="s_birthday03" value="${euchaContents.s_birthday03 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_m_tel03" id="s_m_tel03" value="${euchaContents.s_m_tel03 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_before_number03" id="s_before_number03" value="${euchaContents.s_before_number03 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_order_name03" id="s_order_name03" value="${euchaContents.s_order_name03 }">
					</td>
					</tr>
				<tr>
					<th scope="row">04	</th>
					<td>
					<span class="form">
					<input type="radio" name="s_gubun04" id="s_gubun04" value="1" onClick="changeMemberType(4,1)" <c:if test="${euchaContents.s_gubun04=='1' }" >checked="checked"</c:if>>
					<label for="">수도	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun04" id="s_gubun04" value="2" onClick="changeMemberType(4,2)" <c:if test="${euchaContents.s_gubun04=='2' }" >checked="checked"</c:if>>
					<label for="">갱신	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun04" id="s_gubun04" value="3" onClick="changeMemberType(4,3)" <c:if test="${euchaContents.s_gubun04=='3' }" >checked="checked"</c:if>>
					<label for="">신규	</label>
					</span>
					</td>
					<td>
					<span class="inp">
					<label for="">
					</label>
					<input type="text" name="s_id04" id="s_id04" value="${euchaContents.s_id04 }">
					</span>
					<input type="hidden" name="s_checkId04" id="s_checkId04">
					<button type="button" id="confirmId" class="btn btn-default pull-right" onclick="selectCheckId('04');return false;">아이디확인</button>

					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_name04" id="s_name04" value="${euchaContents.s_name04 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_baptismal_name04" id="s_baptismal_name04" value="${euchaContents.s_baptismal_name04 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" maxlength="10" name="s_birthday04" id="s_birthday04" value="${euchaContents.s_birthday04 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_m_tel04" id="s_m_tel04" value="${euchaContents.s_m_tel04 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_before_number04" id="s_before_number04" value="${euchaContents.s_before_number04 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_order_name04" id="s_order_name04" value="${euchaContents.s_order_name04 }">
					</td>
					</tr>
				<tr>
					<th scope="row">05	</th>
					<td>
					<span class="form">
					<input type="radio" name="s_gubun05" id="s_gubun05" value="1" onClick="changeMemberType(5,1)" <c:if test="${euchaContents.s_gubun05=='1' }" >checked="checked"</c:if>>
					<label for="">수도	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun05" id="s_gubun05" value="2" onClick="changeMemberType(5,2)" <c:if test="${euchaContents.s_gubun05=='2' }" >checked="checked"</c:if>>
					<label for="">갱신	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun05" id="s_gubun05" value="3" onClick="changeMemberType(5,3)" <c:if test="${euchaContents.s_gubun05=='3' }" >checked="checked"</c:if>>
					<label for="">신규	</label>
					</span>
					</td>
					<td>
					<span class="inp">
					<label for="">
					</label>
					<input type="text" name="s_id05" id="s_id05" value="${euchaContents.s_id05 }">
					</span>
					<input type="hidden" name="s_checkId05" id="s_checkId05">
					<button type="button" id="confirmId" class="btn btn-default pull-right" onclick="selectCheckId('05');return false;">아이디확인</button>
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_name05" id="s_name05" value="${euchaContents.s_name05 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_baptismal_name05" id="s_baptismal_name05" value="${euchaContents.s_baptismal_name05 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" maxlength="10" name="s_birthday05" id="s_birthday05" value="${euchaContents.s_birthday05 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_m_tel05" id="s_m_tel05" value="${euchaContents.s_m_tel05 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_before_number05" id="s_before_number05" value="${euchaContents.s_before_number05 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_order_name05" id="s_order_name05" value="${euchaContents.s_order_name05 }">
					</td>
					</tr>
				<tr>
					<th scope="row">06	</th>
					<td>
					<span class="form">
					<input type="radio" name="s_gubun06" id="s_gubun06" value="1" onClick="changeMemberType(6,1)" <c:if test="${euchaContents.s_gubun06=='1' }" >checked="checked"</c:if>>
					<label for="">수도	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun06" id="s_gubun06" value="2" onClick="changeMemberType(6,2)" <c:if test="${euchaContents.s_gubun06=='2' }" >checked="checked"</c:if>>
					<label for="">갱신	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun06" id="s_gubu06" value="3" onClick="changeMemberType(6,3)" <c:if test="${euchaContents.s_gubun06=='3' }" >checked="checked"</c:if>>
					<label for="">신규	</label>
					</span>
					</td>
					<td>
					<span class="inp">
					<label for="">
					</label>
					<input type="text" name="s_id06" id="s_id06" value="${euchaContents.s_id06 }">
					</span>
					<input type="hidden" name="s_checkId06" id="s_checkId06">
					<button type="button" id="confirmId" class="btn btn-default pull-right" onclick="selectCheckId('06');return false;">아이디확인</button>

					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_name06" id="s_name06" value="${euchaContents.s_name06 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_baptismal_name06" id="s_baptismal_name06" value="${euchaContents.s_baptismal_name06 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" maxlength="10" name="s_birthday06" id="s_birthday06" value="${euchaContents.s_birthday06 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_m_tel06" id="s_m_tel06" value="${euchaContents.s_m_tel06 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_before_number06" id="s_before_number06" value="${euchaContents.s_before_number06 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_order_name06" id="s_order_name06" value="${euchaContents.s_order_name06 }">
					</td>
					</tr>
				<tr>
					<th scope="row">07	</th>
					<td>
					<span class="form">
					<input type="radio" name="s_gubun07" id="s_gubun07" value="1" onClick="changeMemberType(7,1)" <c:if test="${euchaContents.s_gubun07=='1' }" >checked="checked"</c:if>>
					<label for="">수도	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun07" id="s_gubun07" value="2" onClick="changeMemberType(7,2)" <c:if test="${euchaContents.s_gubun07=='2' }" >checked="checked"</c:if>>
					<label for="">갱신	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun07" id="s_gubun07" value="3" onClick="changeMemberType(7,3)" <c:if test="${euchaContents.s_gubun07=='3' }" >checked="checked"</c:if>>
					<label for="">신규	</label>
					</span>
					</td>
					<td>
					<span class="inp">
					<label for="">
					</label>
					<input type="text" name="s_id07" id="s_id07" value="${euchaContents.s_id07 }">
					</span>
					<input type="hidden" name="s_checkId07" id="s_checkId07" value="${euchaContents.s_id01 }">
					<button type="button" id="confirmId" class="btn btn-default pull-right" onclick="selectCheckId('07');return false;">아이디확인</button>

					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_name07" id="s_name07" value="${euchaContents.s_name07 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_baptismal_name07" id="s_baptismal_name07" value="${euchaContents.s_baptismal_name07 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" maxlength="10" name="s_birthday07" id="s_birthday07" value="${euchaContents.s_birthday07 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_m_tel07" id="s_m_tel07" value="${euchaContents.s_m_tel07 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_before_number07" id="s_before_number07" value="${euchaContents.s_before_number07 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_order_name07" id="s_order_name07" value="${euchaContents.s_order_name07 }">
					</td>
					</tr>
				<tr>
					<th scope="row">08	</th>
					<td>
					<span class="form">
					<input type="radio" name="s_gubun08" id="s_gubun08" value="1" onClick="changeMemberType(8,1)" <c:if test="${euchaContents.s_gubun08=='1' }" >checked="checked"</c:if>>
					<label for="">수도	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun08" id="s_gubun08" value="2" onClick="changeMemberType(8,2)" <c:if test="${euchaContents.s_gubun08=='2' }" >checked="checked"</c:if>>
					<label for="">갱신	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun08" id="s_gubun08" value="3" onClick="changeMemberType(8,3)" <c:if test="${euchaContents.s_gubun08=='3' }" >checked="checked"</c:if>>
					<label for="">신규	</label>
					</span>
					</td>
					<td>
					<span class="inp">
					<label for="">
					</label>
					<input type="text" name="s_id08" id="s_id08" value="${euchaContents.s_id08 }">
					</span>
					<input type="hidden" name="s_checkId08" id="s_checkId08">
					<button type="button" id="confirmId" class="btn btn-default pull-right" onclick="selectCheckId('08');return false;">아이디확인</button>

					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_name08" id="s_name08" value="${euchaContents.s_name08 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_baptismal_name08" id="s_baptismal_name08" value="${euchaContents.s_baptismal_name08 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" maxlength="10" name="s_birthday08" id="s_birthday08" value="${euchaContents.s_birthday08 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_m_tel08" id="s_m_tel08" value="${euchaContents.s_m_tel08 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_before_number08" id="s_before_number08" value="${euchaContents.s_before_number08 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_order_name08" id="s_order_name08" value="${euchaContents.s_order_name08 }">
					</td>
					</tr>
				<tr>
					<th scope="row">09	</th>
					<td>
					<span class="form">
					<input type="radio" name="s_gubun09" id="s_gubun09" value="1" onClick="changeMemberType(9,1)" <c:if test="${euchaContents.s_gubun09=='1' }" >checked="checked"</c:if>>
					<label for="">수도	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun09" id="s_gubun09" value="2" onClick="changeMemberType(9,2)" <c:if test="${euchaContents.s_gubun09=='2' }" >checked="checked"</c:if>>
					<label for="">갱신	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun09" id="s_gubun09" value="3" onClick="changeMemberType(9,3)" <c:if test="${euchaContents.s_gubun09=='3' }" >checked="checked"</c:if>>
					<label for="">신규	</label>
					</span>
					</td>
					<td>
					<span class="inp">
					<label for="">
					</label>
					<input type="text" name="s_id09" id="s_id09" value="${euchaContents.s_id09 }">
					</span>
					<input type="hidden" name="s_checkId09" id="s_checkId09">
					<button type="button" id="confirmId" class="btn btn-default pull-right" onclick="selectCheckId('09');return false;">아이디확인</button>

					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_name09" id="s_name09" value="${euchaContents.s_name09 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_baptismal_name09" id="s_baptismal_name09" value="${euchaContents.s_baptismal_name09 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" maxlength="10" name="s_birthday09" id="s_birthday09" value="${euchaContents.s_birthday09 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_m_tel09" id="s_m_tel09" value="${euchaContents.s_m_tel09 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_before_number09" id="s_before_number09" value="${euchaContents.s_before_number09 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_order_name09" id="s_order_name09" value="${euchaContents.s_order_name09 }">
					</td>
					</tr>
				<tr>
					<th scope="row">10	</th>
					<td>
					<span class="form">
					<input type="radio" name="s_gubun10" id="s_gubun10" value="1" onClick="changeMemberType(10,1)" <c:if test="${euchaContents.s_gubun10=='1' }" >checked="checked"</c:if>>
					<label for="">수도	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun10" id="s_gubun10" value="2" onClick="changeMemberType(10,2)" <c:if test="${euchaContents.s_gubun10=='2' }" >checked="checked"</c:if>>
					<label for="">갱신	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun10" id="s_gubun10" value="3" onClick="changeMemberType(10,3)" <c:if test="${euchaContents.s_gubun10=='3' }" >checked="checked"</c:if>>
					<label for="">신규	</label>
					</span>
					</td>
					<td>
					<span class="inp">
					<label for="">
					</label>
					<input type="text" name="s_id10" id="s_id10" value="${euchaContents.s_id10 }">
					</span>
					<input type="hidden" name="s_checkId10" id="s_checkId10">
					<button type="button" id="confirmId" class="btn btn-default pull-right" onclick="selectCheckId('10');return false;">아이디확인</button>

					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_name10" id="s_name10" value="${euchaContents.s_name10 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_baptismal_name10" id="s_baptismal_name10" value="${euchaContents.s_baptismal_name10 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" maxlength="10" name="s_birthday10" id="s_birthday10" value="${euchaContents.s_birthday10 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_m_tel10" id="s_m_tel10" value="${euchaContents.s_m_tel10 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_before_number10" id="s_before_number10" value="${euchaContents.s_before_number10 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_order_name10" id="s_order_name10" value="${euchaContents.s_order_name10 }">
					</td>
					</tr>
				<tr>
					<th scope="row">11	</th>
					<td>
					<span class="form">
					<input type="radio" name="s_gubun11" id="s_gubun11" value="1" onClick="changeMemberType(11,1)" <c:if test="${euchaContents.s_gubun11=='1' }" >checked="checked"</c:if>>
					<label for="">수도	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun11" id="s_gubun11" value="2" onClick="changeMemberType(11,2)" <c:if test="${euchaContents.s_gubun11=='2' }" >checked="checked"</c:if>>
					<label for="">갱신	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun11" id="s_gubun11" value="3" onClick="changeMemberType(11,3)" <c:if test="${euchaContents.s_gubun11=='3' }" >checked="checked"</c:if>>
					<label for="">신규	</label>
					</span>
					</td>
					<td>
					<span class="inp">
					<label for="">
					</label>
					<input type="text" name="s_id11" id="s_id11" value="${euchaContents.s_id11 }">
					</span>
					<input type="hidden" name="s_checkId11" id="s_checkId11">
					<button type="button" id="confirmId" class="btn btn-default pull-right" onclick="selectCheckId('11');return false;">아이디확인</button>

					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_name11" id="s_name11" value="${euchaContents.s_name11 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_baptismal_name11" id="s_baptismal_name11" value="${euchaContents.s_baptismal_name11 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" maxlength="10" name="s_birthday11" id="s_birthday11" value="${euchaContents.s_birthday11 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_m_tel11" id="s_m_tel11" value="${euchaContents.s_m_tel11 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_before_number11" id="s_before_number11" value="${euchaContents.s_before_number11 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_order_name11" id="s_order_name11" value="${euchaContents.s_order_name11 }">
					</td>
					</tr>
				<tr>
					<th scope="row">12	</th>
					<td>
					<span class="form">
					<input type="radio" name="s_gubun12" id="s_gubun12" value="1" onClick="changeMemberType(12,1)" <c:if test="${euchaContents.s_gubun12=='1' }" >checked="checked"</c:if>>
					<label for="">수도	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun12" id="s_gubun12" value="2" onClick="changeMemberType(12,2)" <c:if test="${euchaContents.s_gubun12=='2' }" >checked="checked"</c:if>>
					<label for="">갱신	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun12" id="s_gubun12" value="3" onClick="changeMemberType(12,3)" <c:if test="${euchaContents.s_gubun12=='3' }" >checked="checked"</c:if>>
					<label for="">신규	</label>
					</span>
					</td>
					<td>
					<span class="inp">
					<label for="">
					</label>
					<input type="text" name="s_id12" id="s_id12" value="${euchaContents.s_id12 }">
					</span>
					<input type="hidden" name="s_checkId12" id="s_checkId12">
					<button type="button" id="confirmId" class="btn btn-default pull-right" onclick="selectCheckId('12');return false;">아이디확인</button>

					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_name12" id="s_name12" value="${euchaContents.s_name12 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_baptismal_name12" id="s_baptismal_name12" value="${euchaContents.s_baptismal_name12 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" maxlength="10" name="s_birthday12" id="s_birthday12" value="${euchaContents.s_birthday12 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_m_tel12" id="s_m_tel12" value="${euchaContents.s_m_tel12 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_before_number12" id="s_before_number12" value="${euchaContents.s_before_number12 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_order_name12" id="s_order_name12" value="${euchaContents.s_order_name12 }">
					</td>
					</tr>
				<tr>
					<th scope="row">13	</th>
					<td>
					<span class="form">
					<input type="radio" name="s_gubun13" id="s_gubun13" value="1" onClick="changeMemberType(13,1)" <c:if test="${euchaContents.s_gubun13=='1' }" >checked="checked"</c:if>>
					<label for="">수도	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun13" id="s_gubun13" value="2" onClick="changeMemberType(13,2)" <c:if test="${euchaContents.s_gubun13=='2' }" >checked="checked"</c:if>>
					<label for="">갱신	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun13" id="s_gubun13" value="3" onClick="changeMemberType(13,3)" <c:if test="${euchaContents.s_gubun13=='3' }" >checked="checked"</c:if>>
					<label for="">신규	</label>
					</span>
					</td>
					<td>
					<span class="inp">
					<label for="">
					</label>
					<input type="text" name="s_id13" id="s_id13" value="${euchaContents.s_id13 }">
					</span>
					<input type="hidden" name="s_checkId13" id="s_checkId13">
					<button type="button" id="confirmId" class="btn btn-default pull-right" onclick="selectCheckId('13');return false;">아이디확인</button>

					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_name13" id="s_name13" value="${euchaContents.s_name13 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_baptismal_name13" id="s_baptismal_name13" value="${euchaContents.s_baptismal_name13 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" maxlength="10" name="s_birthday13" id="s_birthday13" value="${euchaContents.s_birthday13 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_m_tel13" id="s_m_tel13" value="${euchaContents.s_m_tel13 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_before_number13" id="s_before_number13" value="${euchaContents.s_before_number13 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_order_name13" id="s_order_name13" value="${euchaContents.s_order_name13 }">
					</td>
					</tr>
				<tr>
					<th scope="row">14	</th>
					<td>
					<span class="form">
					<input type="radio" name="s_gubun14" id="s_gubun14" value="1" onClick="changeMemberType(14,1)" <c:if test="${euchaContents.s_gubun14=='1' }" >checked="checked"</c:if>>
					<label for="">수도	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun14" id="s_gubun14" value="2" onClick="changeMemberType(14,2)" <c:if test="${euchaContents.s_gubun14=='2' }" >checked="checked"</c:if>>
					<label for="">갱신	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun14" id="s_gubun14" value="3" onClick="changeMemberType(14,3)" <c:if test="${euchaContents.s_gubun14=='3' }" >checked="checked"</c:if>>
					<label for="">신규	</label>
					</span>
					</td>
					<td>
					<span class="inp">
					<label for="">
					</label>
					<input type="text" name="s_id14" id="s_id14" value="${euchaContents.s_id14 }">
					</span>
					<input type="hidden" name="s_checkId14" id="s_checkId14">
					<button type="button" id="confirmId" class="btn btn-default pull-right" onclick="selectCheckId('14');return false;">아이디확인</button>

					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_name14" id="s_name14" value="${euchaContents.s_name14 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_baptismal_name14" id="s_baptismal_name14" value="${euchaContents.s_baptismal_name14 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" maxlength="10" name="s_birthday14" id="s_birthday14" value="${euchaContents.s_birthday14 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_m_tel14" id="s_m_tel14" value="${euchaContents.s_m_tel14 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_before_number14" id="s_before_number14" value="${euchaContents.s_before_number14 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_order_name14" id="s_order_name14" value="${euchaContents.s_order_name14 }">
					</td>
					</tr>
				<tr>
					<th scope="row">15	</th>
					<td>
					<span class="form">
					<input type="radio" name="s_gubun15" id="s_gubun15" value="1" onClick="changeMemberType(15,1)" <c:if test="${euchaContents.s_gubun15=='1' }" >checked="checked"</c:if>>
					<label for="">수도	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun15" id="s_gubun15" value="2" onClick="changeMemberType(15,2)" <c:if test="${euchaContents.s_gubun15=='2' }" >checked="checked"</c:if>>
					<label for="">갱신	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun15" id="s_gubun15" value="3" onClick="changeMemberType(15,3)" <c:if test="${euchaContents.s_gubun15=='3' }" >checked="checked"</c:if>>
					<label for="">신규	</label>
					</span>
					</td>
					<td>
					<span class="inp">
					<label for="">
					</label>
					<input type="text" name="s_id15" id="s_id15" value="${euchaContents.s_id15 }">
					</span>
					<input type="hidden" name="s_checkId15" id="s_checkId15">
					<button type="button" id="confirmId" class="btn btn-default pull-right" onclick="selectCheckId('15');return false;">아이디확인</button>

					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_name15" id="s_name15" value="${euchaContents.s_name15 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_baptismal_name15" id="s_baptismal_name15" value="${euchaContents.s_baptismal_name15 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" maxlength="10" name="s_birthday15" id="s_birthday15" value="${euchaContents.s_birthday15 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_m_tel15" id="s_m_tel15" value="${euchaContents.s_m_tel15 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_before_number15" id="s_before_number15" value="${euchaContents.s_before_number15 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_order_name15" id="s_order_name15" value="${euchaContents.s_order_name15 }">
					</td>
					</tr>
				<tr>
					<th scope="row">16	</th>
					<td>
					<span class="form">
					<input type="radio" name="s_gubun16" id="s_gubun16" value="1" onClick="changeMemberType(16,1)" <c:if test="${euchaContents.s_gubun16=='1' }" >checked="checked"</c:if>>
					<label for="">수도	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun16" id="s_gubun16" value="2" onClick="changeMemberType(16,2)" <c:if test="${euchaContents.s_gubun16=='2' }" >checked="checked"</c:if>>
					<label for="">갱신	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun16" id="s_gubun16" value="3" onClick="changeMemberType(16,3)" <c:if test="${euchaContents.s_gubun16=='3' }" >checked="checked"</c:if>>
					<label for="">신규	</label>
					</span>
					</td>
					<td>
					<span class="inp">
					<label for="">
					</label>
					<input type="text" name="s_id16" id="s_id16" value="${euchaContents.s_id16 }">
					</span>
					<input type="hidden" name="s_checkId16" id="s_checkId16">
					<button type="button" id="confirmId" class="btn btn-default pull-right" onclick="selectCheckId('16');return false;">아이디확인</button>

					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_name16" id="s_name16" value="${euchaContents.s_name16 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_baptismal_name16" id="s_baptismal_name16" value="${euchaContents.s_baptismal_name16 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" maxlength="10" name="s_birthday16" id="s_birthday16" value="${euchaContents.s_birthday16 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_m_tel16" id="s_m_tel16" value="${euchaContents.s_m_tel16 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_before_number16" id="s_before_number16" value="${euchaContents.s_before_number16 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_order_name16" id="s_order_name16" value="${euchaContents.s_order_name16 }">
					</td>
					</tr>
				<tr>
					<th scope="row">17	</th>
					<td>
					<span class="form">
					<input type="radio" name="s_gubun17" id="s_gubun17" value="1" onClick="changeMemberType(17,1)" <c:if test="${euchaContents.s_gubun17=='1' }" >checked="checked"</c:if>>
					<label for="">수도	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun17" id="s_gubun17" value="2" onClick="changeMemberType(17,2)" <c:if test="${euchaContents.s_gubun17=='2' }" >checked="checked"</c:if>>
					<label for="">갱신	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun17" id="s_gubun17" value="3" onClick="changeMemberType(17,3)" <c:if test="${euchaContents.s_gubun17=='3' }" >checked="checked"</c:if>>
					<label for="">신규	</label>
					</span>
					</td>
					<td>
					<span class="inp">
					<label for="">
					</label>
					<input type="text" name="s_id17" id="s_id17" value="${euchaContents.s_id17 }">
					</span>
					<input type="hidden" name="s_checkId17" id="s_checkId17">
					<button type="button" id="confirmId" class="btn btn-default pull-right" onclick="selectCheckId('17');return false;">아이디확인</button>

					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_name17" id="s_name17" value="${euchaContents.s_name17 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_baptismal_name17" id="s_baptismal_name17" value="${euchaContents.s_baptismal_name17 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" maxlength="10" name="s_birthday17" id="s_birthday17" value="${euchaContents.s_birthday17 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_m_tel17" id="s_m_tel17" value="${euchaContents.s_m_tel17 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_before_number17" id="s_before_number17" value="${euchaContents.s_before_number17 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_order_name17" id="s_order_name17" value="${euchaContents.s_order_name17 }">
					</td>
					</tr>
				<tr>
					<th scope="row">18	</th>
					<td>
					<span class="form">
					<input type="radio" name="s_gubun18" id="s_gubun18" value="1" onClick="changeMemberType(18,1)" <c:if test="${euchaContents.s_gubun18=='1' }" >checked="checked"</c:if>>
					<label for="">수도	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun18" id="s_gubun18" value="2" onClick="changeMemberType(18,2)" <c:if test="${euchaContents.s_gubun18=='2' }" >checked="checked"</c:if>>
					<label for="">갱신	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun18" id="s_gubun18" value="3" onClick="changeMemberType(18,3)" <c:if test="${euchaContents.s_gubun18=='3' }" >checked="checked"</c:if>>
					<label for="">신규	</label>
					</span>
					</td>
					<td>
					<span class="inp">
					<label for="">
					</label>
					<input type="text" name="s_id18" id="s_id18" value="${euchaContents.s_id18 }">
					</span>
					<input type="hidden" name="s_checkId18" id="s_checkId18">
					<button type="button" id="confirmId" class="btn btn-default pull-right" onclick="selectCheckId('18');return false;">아이디확인</button>

					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_name18" id="s_name18" value="${euchaContents.s_name18 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_baptismal_name18" id="s_baptismal_name18" value="${euchaContents.s_baptismal_name18 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" maxlength="10" name="s_birthday18" id="s_birthday18" value="${euchaContents.s_birthday18 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_m_tel18" id="s_m_tel18" value="${euchaContents.s_m_tel18 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_before_number18" id="s_before_number18" value="${euchaContents.s_before_number18 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_order_name18" id="s_order_name18" value="${euchaContents.s_order_name18 }">
					</td>
					</tr>
				<tr>
					<th scope="row">19	</th>
					<td>
					<span class="form">
					<input type="radio" name="s_gubun19" id="s_gubun19" value="1" onClick="changeMemberType(19,1)" <c:if test="${euchaContents.s_gubun19=='1' }" >checked="checked"</c:if>>
					<label for="">수도	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun19" id="s_gubun19" value="2" onClick="changeMemberType(19,2)" <c:if test="${euchaContents.s_gubun19=='2' }" >checked="checked"</c:if>>
					<label for="">갱신	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun19" id="s_gubun19" value="3" onClick="changeMemberType(19,3)" <c:if test="${euchaContents.s_gubun19=='3' }" >checked="checked"</c:if>>
					<label for="">신규	</label>
					</span>
					</td>
					<td>
					<span class="inp">
					<label for="">
					</label>
					<input type="text" name="s_id19" id="s_id19" value="${euchaContents.s_id19 }">
					</span>
					<input type="hidden" name="s_checkId19" id="s_checkId19">
					<button type="button" id="confirmId" class="btn btn-default pull-right" onclick="selectCheckId('19');return false;">아이디확인</button>

					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_name19" id="s_name19" value="${euchaContents.s_name19 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_baptismal_name19" id="s_baptismal_name19" value="${euchaContents.s_baptismal_name19 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" maxlength="10" name="s_birthday19" id="s_birthday19" value="${euchaContents.s_birthday19 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_m_tel19" id="s_m_tel19" value="${euchaContents.s_m_tel19 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_before_number19" id="s_before_number19" value="${euchaContents.s_before_number19 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_order_name19" id="s_order_name19" value="${euchaContents.s_order_name19 }">
					</td>
					</tr>
				<tr>
					<th scope="row">20	</th>
					<td>
					<span class="form">
					<input type="radio" name="s_gubun20" id="s_gubun20" value="1" onClick="changeMemberType(20,1)" <c:if test="${euchaContents.s_gubun20=='1' }" >checked="checked"</c:if>>
					<label for="">수도	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun20" id="s_gubun20" value="2" onClick="changeMemberType(20,2)" <c:if test="${euchaContents.s_gubun20=='2' }" >checked="checked"</c:if>>
					<label for="">갱신	</label>
					</span>
					<span class="form">
					<input type="radio" name="s_gubun20" id="s_gubun20" value="3" onClick="changeMemberType(20,3)" <c:if test="${euchaContents.s_gubun20=='3' }" >checked="checked"</c:if>>
					<label for="">신규	</label>
					</span>
					</td>
					<td>
					<span class="inp">
					<label for="">
					</label>
					<input type="text" name="s_id20" id="s_id20" value="${euchaContents.s_id20 }">
					</span>
					<input type="hidden" name="s_checkId20" id="s_checkId20">
					<button type="button" id="confirmId" class="btn btn-default pull-right" onclick="selectCheckId('20');return false;">아이디확인</button>

					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_name20" id="s_name20" value="${euchaContents.s_name20 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_baptismal_name20" id="s_baptismal_name20" value="${euchaContents.s_baptismal_name20 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" maxlength="10" name="s_birthday20" id="s_birthday20" value="${euchaContents.s_birthday20 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_m_tel20" id="s_m_tel20" value="${euchaContents.s_m_tel20 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_before_number20" id="s_before_number20" value="${euchaContents.s_before_number20 }">
					</td>
					<td>
					<label for="">
					</label>
					<input type="text" name="s_order_name20" id="s_order_name20" value="${euchaContents.s_order_name20 }">
					</td>
					</tr>
					
					
					</tbody>
                        </table>
                    </div>
                    <!-- //ovrTable -->
                    <!-- writeTable -->
                    <div class="writeTable ">
                        <table class="shirine_st write">
                            <caption>온라인 신청 폼</caption>
                            <tbody>
                                <tr class="readOn">
                                    <th scope="row">청원일</th>
                                    <td>
	                                    <c:if test="${query_type=='insert'}">
                                       		${APPLY_DAY}
                                       	</c:if>
                                       	<c:if test="${query_type=='update'}">
                                       		${euchaContents.APPLY_DAY}
                                       	</c:if>
									</td>
                                    <th scope="row">청원자</th>
                                    <td>주임신부 또는 수도회 정상</td>
                                </tr>
                                <tr>
                                    <th scope="row">보안문자</th>
                                    <td class="secure" colspan="3">
                                        <em><img src="/captcha" id="captchaImg" alt="captcha img"></em>&nbsp;
                                    	<input type="text" placeholder="보안문자를 입력하세요" name="captcha" id="captcha" value="">
                                        <button type="button" onClick="imgRefresh()"><img src="../img/reset.png" alt="보안문자 새로고침"></button>
                                        <!-- span class="redTxt">보안문자를 입력해주세요.</span -->
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <p class="blueTxt">* 교구장님, 위의 분(들)이 저희 공동체(본당, 수도회, 병원)에서 거룩한 미사 거행 중에 정규 집전자를 도와 성체를 분배할 수 있도록 특별 권한을 청하며 비정규 성체분배권 수여교육 참가자로 추천합니다.</p>
                    <!-- //writeTable -->
                    <ul class="btn">
                    	<c:choose>
						<c:when test="${query_type == 'insert'}">
	                        <li><a href="javascript:insert();">신청하기</a></li>
						</c:when>
						<c:otherwise>
	                        <li><a href="javascript:update();">신청하기</a></li>
						</c:otherwise>
						</c:choose>
                        <li class="gray"><a href="/home.do">취소</a></li>
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