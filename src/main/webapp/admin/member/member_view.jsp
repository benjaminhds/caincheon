<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/admin/_common/inc/head.jsp" %>
<%
if(admSessionMemId==null||admSessionMemId.equals("")) {                // id가 Null 이거나 없을 경우
	response.sendRedirect("/admin/login.jsp");    // 로그인 페이지로 리다이렉트 한다.
}
%>
<script type="text/javascript">
function selectCheckId() {
	var id = document.getElementById('id').value;
	if(id == '') {
		alert('아이디를 먼저 입력하세요.');
		return false;
	}
	// 정규식 - 이메일 유효성 검사
	var regEmail = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	if(!regEmail.test($("#id").val())) { alert('이메일 주소가 유효하지 않습니다'); $("#id").focus(); return; }
	
	// check request
	$.ajax({
		url:'/admin/member/member_view_dupcheck.do',
        type:'get',
        data : {"id":id},
        dataType:'text',
        success : function(responseData){
    		var data = JSON.parse(responseData);
    		
    		if(data.status == 'success' && data.result == '') {
        		alert(data.message);
    			document.getElementById('checkId').value = 'Y';
    		} else {
    			if(data.status == 'fail') {
        			alert(data.message);
    			} else {
    				alert('이미 사용중인 아이디입니다.');
    			}
    			document.getElementById('checkId').value = '';
    			document.getElementById('id').value = '';
   			}
         } ,
         error : function(){
     		document.getElementById('checkId').value = 'N';
              // alert('실패 ㅠㅠ');
         }
    });
}

function viewList() {
	document.form01.action = '/admin/member/member_list.do';
	document.form01.submit();
	return false;
}

function commonFormCheck(hasIdChecked) {
	if( $("#id").val() == "") { alert("아이디를 입력해 주세요."); $("#id").focus(); return false; }
	if(hasIdChecked) {
		if( $("#checkId").val() == "" || $("#checkId").val() == "N" ) { 
			alert("아이디 확인을 해 주세요."); return false; 
		}
	}

	// 정규식 - 이메일 유효성 검사
	var regEmail = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	if(!regEmail.test($("#id").val())) { alert('이메일 주소가 유효하지 않습니다'); $("#id").focus(); return false; }
	
	if( $("#password").val() == "") { alert("패스워드를 입력해 주세요."); $("#password").focus(); return false; }
	if( $("#password2").val() == "") { alert("패스워드 확인를 입력해 주세요."); $("#password2").focus(); return false; }
	if( $("#password").val() != $("#password2").val()) { alert("암호가 일치하지 않습니다."); return false; }
	
	var reg_pwd = /^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;
	/* if( !reg_pwd.test($("#password").val()) ) {
		alert("비밀번호 규칙에 맞지 않습니다."); return false;
	} else */if ( $("#password").val().trim().length < 1 ) {// :: 담당자요청에 따라 조건 제거
		alert("관리자는 최소 1자리 이상 입력해야 합니다."); return false;// :: 담당자요청에 따라 조건 제거
	}
	
	if( $("#name").val() == "") { alert("이름을 입력해 주세요."); $("#name").focus(); return false; }
	
	// 신자라면,,
	if( $("#memberType")==1 && $("#baptismalname").val() == "") { alert("세례명을 입력해 주세요."); $("#baptismalname").focus(); return false; }
	
	// 신자라면,, :: 담당자요청에 따라 제거
	//if( $("#memberType")==1 && $("#festivalM option:selected").val()=="00") { alert("세레받은 월을 선택 해 주세요.") ; return  false; }
	//if( $("#memberType")==1 && $("#festivalD option:selected").val()=="00") { alert("세레받은 일을 선택 해 주세요.") ; return  false; }
	
	//인천교구여부 체크된게 없다면,,,
	if( $("input[name='isIncheonGyugo']:checked").length==0 ) { 
		alert("교구구분을 선택 해 주세요.") ; return  false; 
	}
	if($("input[name='isIncheonGyugo']:checked").val()=="Y") {
		if( $("#church_idx option:selected").val()=="00000") { 
			alert("관할 본당을 선택 해 주세요.") ; return  false; 
		}
	}
	
	//
	if( $("#tel").val() == "") { $("#tel").val("032"); } // 관리자요청에따른 디폴트 값 적용
	if( $("#writeYN").val() == "") { alert("글쓰기제한을 입력해 주세요."); $("#writeYN").focus(); return false; }
	if( $("#groupGubun").val() == "") { alert("그룹을 입력해 주세요."); $("#groupGubun").focus(); return false; }

	// 정규식 -전화번호 유효성 검사
	/* var _tel = $("#tel1").val() + $("#tel2").val() + $("#tel3").val();
	    _tel = $("#tel2").val() + $("#tel3").val();
	var regPhone = /^((01[1|6|7|8|9])[1-9]+[0-9]{6,7})|(010[1-9][0-9]{7})$/;
	    regPhone = /^(0[1-9]+[0-9]{6,7})|(010[1-9][0-9]{7})$/;
	    
	if( $("#tel1").val().indexOf("010")==0 && _tel.length != 8 ) { // 010
		alert('전화번호가 유효하지 않습니다.'); return false;
	} else if( $("#tel1").val().indexOf("01")==0 && _tel.length < 7 ) { // 016~019
		alert('전화번호가 유효하지 않습니다..'); return false;
	} else if( $("#tel3").val().length != 4 ) {
		alert('전화번호가 유효하지 않습니다...'); return false;
	} */
	return true;
}
function insert_contents() {
	if(!commonFormCheck(true)) return;
	document.form01.action = '/admin/member/member_insert.do';
	document.form01.submit();
	return false;
}

function modify_contents() {
	if(!commonFormCheck(false)) return;
	document.form01.action = '/admin/member/member_modify.do';
	document.form01.submit();
	return false;
}

function delete_contents() {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/member/member_delete.do';
		document.form01.submit();
	}
    return false;
}

window.onload = function() {
	onLoadLeftMenu('mem_01');
	
	if(document.getElementById('query_type').value=='update') {
		$("input[name=id]").attr("readonly",true);
		document.getElementById('confirmId').disabled = true;
	} else {
		$("input[name=id]").attr("readonly",false);
		document.getElementById('confirmId').disabled = false;
	}
	
	// 이벤트 달기
	$('input:radio[name=isIncheonGyugo]').click(function(e){
		if(this.value=="Y") {
			$("#church_idx").removeAttr("disabled");
		} else {
			$("#church_idx").attr("disabled", true);
		}
	});
}
</script>
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
						<h3 class="page-header">회원 등록</h3>
					</c:when>
					<c:otherwise>
						<h3 class="page-header">회원 수정</h3>	
					</c:otherwise>
					</c:choose>
					</div>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- //.row(page title end -->
	  <form class="well" name="form01" action="/admin/member/member_list.do" method="POST">
	  <input type="hidden" name="checkId" id="checkId"/>
	  <input type="hidden" name="query_type" id="query_type" value="${query_type }"/>
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
							  		<td><label for="inputName">아이디</label>
							    	</td>
							  		<td colspan="3">
							  			<input class="form-control form-control-short w-200" type="text"  id="id" placeholder="" name="id" value="${contents.ID}">&nbsp;
							    		<button type="button" id="confirmId" class="btn40 btn-default " onclick="selectCheckId();return false;">아이디확인</button>
							  		</td>
							  	</tr>
							  	<tr>
							  		<td><label for="inputName">비밀번호</label>
							    	</td>
							  		<td>
							  			<input class="form-control" type="text"  id="password" placeholder="" name="password" value="${contents.PASSWORD }">
							    	</td>
							  		<td><label for="inputName">비밀번호확인</label>
							    	</td>
							  		<td>
							  			<input class="form-control" type="text"  id="password2" placeholder="" name="password2" value="${contents.PASSWORD }">
							    	</td>
							  	</tr>
							  	<tr>
							  		<td><label for="inputName">신자구분</label>
							    	</td>
							  		<td>
							  			<label class="radio-inline"><input type="radio" name="memberType" id="memberType" value="1" <c:if test="${contents.MEMBERTYPE=='1' }" >checked="checked"</c:if>>신자</label>
							  			<label class="radio-inline"><input type="radio" name="memberType" id="memberType" value="2" <c:if test="${contents.MEMBERTYPE=='2' }" >checked="checked"</c:if>>예비신자</label>
							  			<label class="radio-inline"><input type="radio" name="memberType" id="memberType" value="3" <c:if test="${contents.MEMBERTYPE=='3' }" >checked="checked"</c:if>>비신자</label>
							  			<label class="radio-inline"><input type="radio" name="memberType" id="memberType" value="4" <c:if test="${contents.MEMBERTYPE=='4' }" >checked="checked"</c:if>>본당&부서</label><!-- 최고어드민에서만 보이는 속성 -->
							    	</td>
							    	<!-- 
							    	<td><label for="inputName">회원구분2</label></td>
							  		<td>
							  				<label class="radio-inline"><input type="radio" name="groupType" id="groupType" value="4" <c:if test="${contents.GROUPTYPE=='4' }" >checked="checked"</c:if>>성당대표</label>
							  				<label class="radio-inline"><input type="radio" name="groupType" id="groupType" value="5" <c:if test="${contents.GROUPTYPE=='5' }" >checked="checked"</c:if>>부서대표</label>
							  				<label class="radio-inline"><input type="radio" name="groupType" id="groupType" value="0" <c:if test="${contents.GROUPTYPE=='0' }" >checked="checked"</c:if>>해당없음</label>
							  		</td>
							    	 -->
							  		<td><label for="inputName">인천교구여부</label></td>
							  		<td>
							  			<label class="radio-inline"><input type="radio" name="isIncheonGyugo" id="isIncheonGyugo" value="Y" <c:if test="${contents.IS_INCHEON_GYUGO=='Y' }">checked="checked"</c:if> >인천교구</label>
							  			<label class="radio-inline"><input type="radio" name="isIncheonGyugo" id="isIncheonGyugo" value="N" <c:if test="${contents.IS_INCHEON_GYUGO=='N' }">checked="checked"</c:if> >타교구</label>
							  		</td>
							  	</tr>
							  	<tr>
							  		<td><label for="inputName">성명</label></td>
							  		<td>
							    		<input class="form-control" type="text"  id="name" placeholder="" name="name" value="${contents.NAME}">
							  		</td>
							  		<td><label for="inputName">세례명</label></td>
							  		<td>
						    			<input class="form-control" type="text"  id="baptismalname" placeholder="" name="baptismalname" value="${contents.BAPTISMALNAME}">
							  		</td>
							  	</tr>
							  	<tr>
							  		<td><label for="inputName">휴대전화</label></td>
							  		<td>
							    		<input class="form-control" type="text"  id="tel" placeholder="" name="tel" value="${contents.TEL}">
							  		</td>
							  		<td><label for="inputName">소속성당</label></td>
							  		<td>
							    			<select class="form-control" name="church_idx" id="church_idx">
						                        <option value="00000">선택</option>
						                        <c:if test="${fn:length(CHURCH_LIST) > 0}">
						                         <c:forEach var="entry" items="${CHURCH_LIST}" varStatus="status">
						                         	<option value="${entry.CHURCH_IDX}" <c:if test = "${contents.CHURCH_IDX eq entry.CHURCH_IDX }"> selected </c:if>>${entry.NAME}</option>
						                         </c:forEach>
						                     </c:if>
						                    </select>
							  		</td>
							  	</tr>
							  	<tr>
							  		<td><label for="inputName">축일</label></td>
							  		<td><span class="row">
						                    <select class="form-control form-control-short w-100" name="festivalM" id="festivalM">
						                        <option value="00">선택(월)</option>
						                        <option value="01" <c:if test = "${contents.festivalM=='01' }"> selected </c:if> >1월</option>
												<option value="02" <c:if test = "${contents.festivalM=='02' }"> selected </c:if> >2월</option>
												<option value="03" <c:if test = "${contents.festivalM=='03' }"> selected </c:if> >3월</option>
												<option value="04" <c:if test = "${contents.festivalM=='04' }"> selected </c:if> >4월</option>
												<option value="05" <c:if test = "${contents.festivalM=='05' }"> selected </c:if> >5월</option>
												<option value="06" <c:if test = "${contents.festivalM=='06' }"> selected </c:if> >6월</option>
												<option value="07" <c:if test = "${contents.festivalM=='07' }"> selected </c:if> >7월</option>
												<option value="08" <c:if test = "${contents.festivalM=='08' }"> selected </c:if> >8월</option>
												<option value="09" <c:if test = "${contents.festivalM=='09' }"> selected </c:if> >9월</option>
												<option value="10" <c:if test = "${contents.festivalM=='10' }"> selected </c:if> >10월</option>
												<option value="11" <c:if test = "${contents.festivalM=='11' }"> selected </c:if> >11월</option>
												<option value="12" <c:if test = "${contents.festivalM=='12' }"> selected </c:if> >12월</option>
						                    </select>
						                     <select class="form-control form-control-short w-100" name="festivalD" id="festivalD">
						                        <option value="00">선택(일)</option>
						                        <option value="01" <c:if test = "${contents.festivalD=='01' }"> selected </c:if> >1 일</option>
												<option value="02" <c:if test = "${contents.festivalD=='02' }"> selected </c:if> >2 일</option>
												<option value="03" <c:if test = "${contents.festivalD=='03' }"> selected </c:if> >3 일</option>
												<option value="04" <c:if test = "${contents.festivalD=='04' }"> selected </c:if> >4 일</option>
												<option value="05" <c:if test = "${contents.festivalD=='05' }"> selected </c:if> >5 일</option>
												<option value="06" <c:if test = "${contents.festivalD=='06' }"> selected </c:if> >6 일</option>
												<option value="07" <c:if test = "${contents.festivalD=='07' }"> selected </c:if> >7 일</option>
												<option value="08" <c:if test = "${contents.festivalD=='08' }"> selected </c:if> >8 일</option>
												<option value="09" <c:if test = "${contents.festivalD=='09' }"> selected </c:if> >9 일</option>
												<option value="10" <c:if test = "${contents.festivalD=='10' }"> selected </c:if> >10일</option>
												<option value="11" <c:if test = "${contents.festivalD=='11' }"> selected </c:if> >11일</option>
												<option value="12" <c:if test = "${contents.festivalD=='12' }"> selected </c:if> >12일</option>
												<option value="13" <c:if test = "${contents.festivalD=='13' }"> selected </c:if> >13일</option>
												<option value="14" <c:if test = "${contents.festivalD=='14' }"> selected </c:if> >14일</option>
												<option value="15" <c:if test = "${contents.festivalD=='15' }"> selected </c:if> >15일</option>
												<option value="16" <c:if test = "${contents.festivalD=='16' }"> selected </c:if> >16일</option>
												<option value="17" <c:if test = "${contents.festivalD=='17' }"> selected </c:if> >17일</option>
												<option value="18" <c:if test = "${contents.festivalD=='18' }"> selected </c:if> >18일</option>
												<option value="19" <c:if test = "${contents.festivalD=='19' }"> selected </c:if> >19일</option>
												<option value="20" <c:if test = "${contents.festivalD=='20' }"> selected </c:if> >20일</option>
												<option value="21" <c:if test = "${contents.festivalD=='21' }"> selected </c:if> >21일</option>
												<option value="22" <c:if test = "${contents.festivalD=='22' }"> selected </c:if> >22일</option>
												<option value="23" <c:if test = "${contents.festivalD=='23' }"> selected </c:if> >23일</option>
												<option value="24" <c:if test = "${contents.festivalD=='24' }"> selected </c:if> >24일</option>
												<option value="25" <c:if test = "${contents.festivalD=='25' }"> selected </c:if> >25일</option>
												<option value="26" <c:if test = "${contents.festivalD=='26' }"> selected </c:if> >26일</option>
												<option value="27" <c:if test = "${contents.festivalD=='27' }"> selected </c:if> >27일</option>
												<option value="28" <c:if test = "${contents.festivalD=='28' }"> selected </c:if> >28일</option>
												<option value="29" <c:if test = "${contents.festivalD=='29' }"> selected </c:if> >29일</option>
												<option value="30" <c:if test = "${contents.festivalD=='30' }"> selected </c:if> >30일</option>
												<option value="31" <c:if test = "${contents.festivalD=='31' }"> selected </c:if> >31일</option>
						                        </select>
						                    </span>
							  		</td>
							  		<td><label for="inputName">메일 인증 여부</label></td>
							  		<td><input type="checkbox" name="mailConfirmYN" id="mailConfirmYN" 
							  		onclick="javascript:$('#label_mailConfirmYN').text(this.checked?'Y':'N')" 
							  		value="Y" <c:if test="${contents.MAILCONFIRMYN eq 'Y' }" >checked="checked"</c:if> >
							  		<label class="radio-inline" id="label_mailConfirmYN">${contents.MAILCONFIRMYN}</label>
							  		</td>
							  	</tr>
							  	<tr>
							  		<td><label for="inputName">글쓰기가능여부</label></td>
							  		<td>
							  			<label class="radio-inline"><input type="radio" name="writeYN" id="writeYN" value="Y" <c:if test="${contents.WRITEYN eq 'Y' }" >checked="checked"</c:if>>Y</label>
							  			<label class="radio-inline"><input type="radio" name="writeYN" id="writeYN" value="N" <c:if test="${contents.WRITEYN eq 'N' }" >checked="checked"</c:if>>N</label>
							  		</td>
							  		<td><label for="inputName">그룹</label></td>
							  		<td><select class="form-control" name="groupGubun" id="groupGubun">
						                        <option value="">선택</option>
						                        <option value="1" <c:if test = "${contents.GROUPGUBUN=='1' }"> selected </c:if> >본당용</option>
												<option value="2" <c:if test = "${contents.GROUPGUBUN=='2' }"> selected </c:if> >부서용</option><!-- 관리 권한  :: 사목자료게시판 & 교구소식 게시판 -->
												<option value="3" <c:if test = "${contents.GROUPGUBUN=='3' }"> selected </c:if> >사제용</option><!-- 관리 권한  :: 본당소식 & 본당앨범 & 본당정보 관리 -->
												<option value="4" <c:if test = "${contents.GROUPGUBUN=='4' }"> selected </c:if> >단체용</option><!-- 관리 권한  :: 공동체소식 관리 -->
						                    </select>
								  	</td>
								</tr>
								    
							  	<tr>
							  		<td><label for="inputName">최근접속일자</label></td>
							  		<td> ${contents.LASTLOGINDT}</td>
							  		<td><label for="inputName">휴면여부</label></td>
							  		<td> <input type="checkbox" name="cb_dormantYN" id="cb_dormantYN" 
							  		onclick="javascript: $('#label_dormantYN').text(this.checked?'휴면':'정상'); $('#dormantYN').val(this.checked?'Y':'N') " 
							  		value="Y" <c:if test="${contents.DORMANTYN eq 'Y' }" >checked="checked"</c:if> >
							  		<label class="radio-inline" id="label_dormantYN"><c:if test="${contents.DORMANTYN eq 'Y' or contents.DORMANTYN eq 'y'}">휴면</c:if><c:if test="${contents.DORMANTYN ne 'Y' and contents.DORMANTYN ne 'y'}">정상</c:if></label>
							  		
							  		<input type="hidden" name="old_DormantYN" value="${contents.DORMANTYN }">
							  		<input type="hidden" name="dormantYN" id="dormantYN" value="${contents.DORMANTYN }">
							  		</td>
								</tr>
								
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
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->
</body>

</html>
