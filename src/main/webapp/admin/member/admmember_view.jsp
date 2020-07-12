<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/admin/_common/inc/head.jsp" %>
<script type="text/javascript">
function viewList() {
	document.form01.action = '/admin/member/admmember_list.do';
	document.form01.submit();
	return false;
}

function insertContents() {
	
	if( $("#adm_id").val() == "") { alert("아이디를 입력해 주세요."); $("#adm_id").focus(); return; }
	if( $("#checkId").val() == "") { alert("아이디확인을 해 주세요."); return; }
	if( $("#checkId").val() == "N") { alert("아이디확인을 해 주세요."); return; }
/* 	if( $("#checkId").val() == "") { alert("아이디확인을 해 주세요."); return; }
	if( $("#checkId").val() == "N") { alert("아이디확인을 해 주세요."); return; }
	// 정규식 - 이메일 유효성 검사
	var regEmail = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	if(!regEmail.test($("#id").val())) { alert('이메일 주소가 유효하지 않습니다'); $("#id").focus(); return; }
	 */
	
	if( $("#password").val() == "") { alert("패스워드를 입력해 주세요."); $("#password").focus(); return; }
	if( $("#password2").val() == "") { alert("패스워드 확인를 입력해 주세요."); $("#password2").focus(); return; }
	if( $("#password").val() != $("#password2").val()) { alert("암호가 일치하지 않습니다."); return; }
	
	var reg_pwd = /^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;
	if( !reg_pwd.test($("#password").val()) ) {
		alert("비밀번호 규칙에 맞지 않습니다."); return;
	} else if ( $("#password").val().length < 10 ) {
		alert("비밀번호는 10자리 이상이어야 합니다."); return;
	}
	
	if( $("#adm_name").val() == "") { alert("이름을 입력해 주세요."); $("#adm_name").focus(); return; }
	
	if(document.getElementById("departCodeCnt").value=="") {
		document.getElementById("departCodeCnt").value = document.getElementById('tbodyDepartCode').rows.length;
	}

	document.form01.action = '/admin/member/admmember_insert.do';
	document.form01.submit();
    return false;
}

function modifyContents() {
	if( $("#adm_id").val() == "") { alert("아이디를 입력해 주세요."); $("#adm_id").focus(); return; }
	/* 	if( $("#checkId").val() == "") { alert("아이디확인을 해 주세요."); return; }
		if( $("#checkId").val() == "N") { alert("아이디확인을 해 주세요."); return; }
		// 정규식 - 이메일 유효성 검사
		var regEmail = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
		if(!regEmail.test($("#id").val())) { alert('이메일 주소가 유효하지 않습니다'); $("#id").focus(); return; }
		 */
		
 		//if( $("#password").val() == "") { alert("패스워드를 입력해 주세요."); $("#password").focus(); return; }
		if( $("#password").val() != "") {
			if( $("#password2").val() == "") { alert("패스워드 확인를 입력해 주세요."); $("#password2").focus(); return; }
			if( $("#password").val() != $("#password2").val()) { alert("암호가 일치하지 않습니다."); return; }
			
			var reg_pwd = /^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;
			if( !reg_pwd.test($("#password").val()) ) {
				alert("비밀번호 규칙에 맞지 않습니다."); return;
			} else if ( $("#password").val().length < 10 ) {
				alert("비밀번호는 10자리 이상이어야 합니다."); return;
			}
		}
		
	if( $("#adm_name").val() == "") { alert("이름을 입력해 주세요."); $("#adm_name").focus(); return; }
	
	if(document.getElementById("departCodeCnt").value=="") {
		document.getElementById("departCodeCnt").value = document.getElementById('tbodyDepartCode').rows.length;
	}

	document.form01.action = '/admin/member/admmember_modify.do';
	document.form01.submit();
    return false;
}

function deleteContents() {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/member/admmember_delete.do';
		document.form01.submit();
	}
    return false;
}

function addDepartCode() {
    var my_tbody = document.getElementById('tbodyDepartCode');

//    alert('현임지 len:' + my_tbody.rows.length );
/*     if(my_tbody.rows.length > 12){
    	alert('일정은 12개 이상 추가할 수 없습니다');
    } else {
 */	    var row = my_tbody.insertRow( my_tbody.rows.length ); // 하단에 추가
//	    var cell1 = row.insertCell(0);
	    var cell2 = row.insertCell(0);
	    var cell3 = row.insertCell(1);
	    var cell4 = row.insertCell(2);
//	    cell1.innerHTML = '<select style="width:100px" id="departLevel'+my_tbody.rows.length+'" name="departLevel'+my_tbody.rows.length+'" onchange="viewLevel(this.value)"><option value="1">1Level</option><option value="2">2Level</option><option value="3">3Level</option></select>';
	    cell2.innerHTML = '<select style="width:200px" class="form-control" id="departCode1'+my_tbody.rows.length+'" name="departCode1'+my_tbody.rows.length+'" onChange="setCombo2(\''+my_tbody.rows.length+'\')"></select>';
	    cell3.innerHTML = '<select style="width:200px" class="form-control" id="departCode2'+my_tbody.rows.length+'" name="departCode2'+my_tbody.rows.length+'"></select>';
	    cell4.innerHTML = '<select style="width:200px" class="form-control" id="departCode3'+my_tbody.rows.length+'" name="departCode3'+my_tbody.rows.length+'"></select>';
		
	    //document.getElementById('departCode1'+my_tbody.rows.length).find('option').remove();
		//$('#departCode1'+my_tbody.rows.length).append("<option value=''>선택</option>");
		//document.getElementById('departCode1'+my_tbody.rows.length).append("<option value=''>선택</option>");

	 	var srcObj = document.getElementById('combo1');
	 	var tarObj = document.getElementById('departCode1'+my_tbody.rows.length);
	 	
	    for( i = 0, k = srcObj.options.length; i < k; i++ ){
	        //if(document.getElementById('departCode1'+my_tbody.rows.length).value.substring(0,1) == srcObj[i].value.substring(0,1)){
	    	tarObj.add(new Option(srcObj[i].text, srcObj[i].value, false, false));
	        //}
	    }
	    
	    var srcObj = document.getElementById('combo2');
	 	var tarObj = document.getElementById('departCode2'+my_tbody.rows.length);
	 	
	    for( i = 0, k = srcObj.options.length; i < k; i++ ){
	        //if(document.getElementById('departCode1'+my_tbody.rows.length).value.substring(0,1) == srcObj[i].value.substring(0,1)){
	    	tarObj.add(new Option(srcObj[i].text, srcObj[i].value, false, false));
	        //}
	    }
	    
	    var srcObj = document.getElementById('combo3');
	 	var tarObj = document.getElementById('departCode3'+my_tbody.rows.length);
	 	
	    for( i = 0, k = srcObj.options.length; i < k; i++ ){
	        //if(document.getElementById('departCode1'+my_tbody.rows.length).value.substring(0,1) == srcObj[i].value.substring(0,1)){
	    	tarObj.add(new Option(srcObj[i].text, srcObj[i].value, false, false));
	        //}
	    }
	    
	    document.getElementById("departCodeCnt").value = my_tbody.rows.length;
}

function setCombo2(idx) {
	$('#departCode2'+idx).find('option').remove();
	$('#departCode2'+idx).append("<option value=''>선택</option>");

 	var srcObj = document.getElementById('combo2');
 	var tarObj = document.getElementById('departCode2'+idx);
 	
    for( i = 0, k = srcObj.options.length; i < k; i++ ){
        if(document.getElementById('departCode1'+idx).value.substring(0,1) == srcObj[i].value.substring(0,1)){
        	tarObj.add(new Option(srcObj[i].text, srcObj[i].value, false, false));
        }
    }

	$('#departCode3'+idx).find('option').remove();
	$('#departCode3'+idx).append("<option value=''>선택</option>");

 	var srcObj = document.getElementById('combo3');
 	var tarObj = document.getElementById('departCode3'+idx);
 	
    for( i = 0, k = srcObj.options.length; i < k; i++ ){
        if(document.getElementById('departCode1'+idx).value.substring(0,1) == srcObj[i].value.substring(0,1)){
        	tarObj.add(new Option(srcObj[i].text, srcObj[i].value, false, false));
        }
    }
}

window.onload = function () {
	onLoadLeftMenu('adm_01');
	
	if(document.getElementById('query_type').value=='modify') {
		$("input[name=adm_id]").attr("readonly",true);
		document.getElementById('confirmId').disabled = true;
	} else {
		$("input[name=adm_id]").attr("readonly",false);
		document.getElementById('confirmId').disabled = false;
	}
}


function selectCheckId() {
	var adm_id = document.getElementById('adm_id').value;
	if(adm_id == '') {
		alert('아이디를 먼저 입력하세요.');
		return false;
	}
	
	$.ajax({
		url:'/admin/member/selectAdminMemberInfo.do',
        type:'get',
        data : {"adm_id":adm_id},
        dataType:'text',
        success : function(responseData){
    		var data = JSON.parse(responseData);

    		if(data.result == '') {
        		alert('사용가능한 아이디입니다.');
        		document.getElementById('checkId').value = 'Y';
    		} else {
    			alert('이미 사용중인 아이디입니다.');
    			document.getElementById('checkId').value = '';
    			 document.getElementById('adm_id').value = '';
    		}
         } ,
         error : function(){
     		document.getElementById('checkId').value = 'N';
              // alert('실패 ㅠㅠ');
         }
    });
}


function setDepartCode(idx, depart_code) {
	document.getElementById('departCode1'+idx).value = depart_code.substring(0,1)+'0000';
	document.getElementById('departCode2'+idx).value = depart_code.substring(0,2)+'000';
	document.getElementById('departCode3'+idx).value = depart_code;
}
</script>
<body>
<form class="form-group" name="form01" action="/admin/member/admmember_list.do" method="POST">
<input type="hidden" name="query_type" id="query_type" value="${_params.query_type }" />
<input type="hidden" name="departCodeCnt" id="departCodeCnt" value=""/>
<input type="hidden" name="checkId" id="checkId"/>
<!-- top start(left menu include) -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- top end -->
        
        <!-- main start -->        
        <!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
                    <h3 class="page-header">
						<c:choose>
						<c:when test="${_params.query_type == 'insert'}">
							관리자 등록
						</c:when>
						<c:otherwise>
							관리자 수정
						</c:otherwise>
						</c:choose>
					</h3>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
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
									<td>
					    				<label for="inputName">관리자아이디</label>
					    			</td>
					    			<td>
							    		<c:choose>
										<c:when test="${_params.query_type == 'insert'}">
							    			<input class="form-control" type="text"  id="adm_id" placeholder="" name="adm_id" value="${contents.ADM_ID}">		
										</c:when>
										<c:otherwise>
							    			<input class="form-control" type="text"  id="adm_id" placeholder="" name="adm_id" readonly value="${contents.ADM_ID}">		
										</c:otherwise>
										</c:choose>
										<button type="button" id="confirmId" class="btn btn-default pull-right" onclick="selectCheckId();return false;">아이디확인</button>
									</td>
									<td><label for="inputBaptismal">관리자명/부서명</label></td>
									<td>
									<input class="form-control" type="text"  id="adm_name" placeholder="" name="adm_name" value="${contents.ADM_NAME}"/>
									</td>
								</tr>	
								<tr>
									<td><label for="inputName">비밀번호</label></td>
									<td><input class="form-control" type="password"  id="password" placeholder="" name="password" value=""/>
									</td>
									<td><label for="inputBaptismal">비밀번호확인</label></td>
									<td>
										<input class="form-control" type="password"  id="password2" placeholder="" name="password2" value=""/>
									</td>
								</tr>
								<tr>
									<td><label for="inputName">현임지</label></td>
									<td colspan="3">
									<table>
							    		<tr>
				   							<td colspan="4">
				    							<button type="button" class="btn btn-default" onclick="javascript:addDepartCode();return false;">추가</button>
				    						</td>
				    					</tr>
							    		<tbody id="tbodyDepartCode">
							    		<c:choose>
							   				<c:when test="${fn:length(admDepartCodeList)>0}">
							   					<c:forEach items="${admDepartCodeList}" var="tlist">
							   						<tr>
									    				<td>
										    			<select style="width:200px" class="form-control" id="departCode1${tlist.RNUM }" name="departCode1${tlist.RNUM }" onChange="setCombo2()">
										    				<option value="">= 선택  =</option>
										    				<c:choose>
																<c:when test="${fn:length(DEPART_LIST1) > 0}">
																<c:forEach items="${DEPART_LIST1}" var="list1">
																	<option value='${list1.DEPART_CODE}'>${list1.NAME}</option>
																</c:forEach>
																</c:when>
															</c:choose>
										      			</select>
									    				</td>
									    				<td>
											    			<select  style="width:200px" class="form-control" id="departCode2${tlist.RNUM }" name="departCode2${tlist.RNUM }">
											    				<option value="">= 선택  =</option>
											    				<c:choose>
																	<c:when test="${fn:length(DEPART_LIST2) > 0}">
																	<c:forEach items="${DEPART_LIST2}" var="list2">
																		<option value='${list2.DEPART_CODE}'>${list2.NAME}</option>
																	</c:forEach>
																	</c:when>
																</c:choose>
											      			</select>
									    				</td>
									    				<td>
											    			<select  style="width:200px" class="form-control" id="departCode3${tlist.RNUM }" name="departCode3${tlist.RNUM }">
											    				<option value="">= 선택  =</option>
											    				<c:choose>
																	<c:when test="${fn:length(DEPART_LIST3) > 0}">
																	<c:forEach items="${DEPART_LIST3}" var="list3">
																		<option value='${list3.DEPART_CODE}'>${list3.NAME}</option>
																	</c:forEach>
																	</c:when>
																</c:choose>
											      			</select>
									    				</td>				    				
									    			</tr>
									    			<script>
									    			setDepartCode('${tlist.RNUM }','${tlist.DEPART_CODE }');
									    			</script>
					   							</c:forEach>
						   					</c:when>
					   					</c:choose>
							    		</tbody>
							    		</table>
							    		<div style="display:none">
							    		<select name="combo1" id="combo1">	
							  			<c:choose>
											<c:when test="${fn:length(DEPART_LIST1) > 0}">
											<c:forEach items="${DEPART_LIST1}" var="list1">
												<option value='${list1.DEPART_CODE}'>${list1.NAME}</option>
											</c:forEach>
											</c:when>
										</c:choose>
							  			</select>
							  			<select name="combo2" id="combo2">	
							  			<c:choose>
											<c:when test="${fn:length(DEPART_LIST2) > 0}">
											<c:forEach items="${DEPART_LIST2}" var="list2">
												<option value='${list2.DEPART_CODE}'>${list2.NAME}</option>
											</c:forEach>
											</c:when>
										</c:choose>
							  			</select>
							  			<select name="combo3" id="combo3">	
							  			<c:choose>
											<c:when test="${fn:length(DEPART_LIST3) > 0}">
											<c:forEach items="${DEPART_LIST3}" var="list3">
												<option value='${list3.DEPART_CODE}'>${list3.NAME}</option>
											</c:forEach>
											</c:when>
										</c:choose>
							  			</select>
							  			</div>
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
					<button type="button" id="btnInsert" class="btn btn-default pull-right" onclick="javascript:insertContents();return false;">등록</button>	
				</c:when>
				<c:otherwise>
					<button type="button" id="btnModify" class="btn btn-default pull-right" onclick="javascript:modifyContents();return false;">수정</button>
					<button type="button" id="btnDelete" class="btn btn-default pull-right" onclick="javascript:deleteContents();return false;">삭제</button>	
				</c:otherwise>
				</c:choose>
</p>			
				<script type="text/javascript">
				function viewLevel(varLevel) {
					
					var vSelect1 = document.getElementById("departCode1");
					var vSelect2 = document.getElementById("departCode2");
					var vSelect3 = document.getElementById("departCode3");
					if(varLevel=='1') {
						vSelect1.style.display = 'block';
						vSelect2.style.display = 'none';
						vSelect3.style.display = 'none';
					} else if(varLevel=='2') {
						vSelect1.style.display = 'block';
						vSelect2.style.display = 'block';
						vSelect3.style.display = 'none';						
					} else {
						vSelect1.style.display = 'block';
						vSelect2.style.display = 'block';
						vSelect3.style.display = 'block';						
					}
				}
				$(document).ready(function() {
					//viewLevel('1');
					 /* <c:choose>
						<c:when test="${fn:length(DEPART_LIST1) > 0}">
						<c:forEach items="${DEPART_LIST1}" var="list">
							$('#departCode1').append("<option value='"+${list.DEPART_CODE}+"'>"+${list.NAME}+"</option>");
						</c:forEach>
						</c:when>
					</c:choose> */
				});
				</script>      
            <!--  main END -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->
<c:if test = "${fn:length(ERR_MSG) > 0 }">
<script>
alert("${ERR_MSG}");
</script>
</c:if>
</body>

</html>