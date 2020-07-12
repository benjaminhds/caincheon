<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/admin/_common/inc/head.jsp" %>
<%
if(admSessionMemId==null||admSessionMemId.equals("")) {                // id가 Null 이거나 없을 경우
	response.sendRedirect("/admin/login.jsp");    // 로그인 페이지로 리다이렉트 한다.
}
%>
<script type="text/javascript">
function goPage(pageNo) {
	document.getElementById('pageNo').value=pageNo;
	document.form01.submit();
    return false;
}

function goSearch() {
/* 	if(document.getElementById('searchText').value == '') {
		alert('검색할 문자를 입력하세요.');
		document.getElementById('searchText').focus();
		return false;
	}
 */
 	document.form01.submit();
    return false;
}

function insertContents() {
	if(document.querySelector('input[name = "orgLevel"]:checked') == null) {
		alert('등록할 카테고리구분을 선택하세요.');
		return false;
	} else if(document.querySelector('input[name = "orgLevel"]:checked').value == '0') {
		if(document.getElementById("p_name").value == "") {
			alert('카테고리명을 입력하세요.');
			document.getElementById("p_name").focus();
			return false;
		} else {
			document.getElementById('p_level').value='1';
		}
	} else if(document.querySelector('input[name = "orgLevel"]:checked').value == '1') {
		if(document.getElementById("combo1").value == "") {
			alert('대분류카테고리를 선택하세요');
			document.getElementById("combo1").focus();
			return false;
		} else if(document.getElementById("p_name").value == "") {
			alert('카테고리명을 입력하세요.');
			document.getElementById("p_name").focus();
			return false;
		} else {
			document.getElementById('p_level').value='2';
			document.getElementById('p_code').value=document.getElementById("combo1").value;
		}
	} else if(document.querySelector('input[name = "orgLevel"]:checked').value == '2') {
		if(document.getElementById("combo1").value == "") {
			alert('대분류카테고리를 선택하세요');
			document.getElementById("combo1").focus();
			return false;
		} else if(document.getElementById("combo2").value == "") {
			alert('중분류카테고리를 선택하세요');
			document.getElementById("combo2").focus();
			return false;
		} else if(document.getElementById("p_name").value == "") {
			alert('카테고리명을 입력하세요.');
			document.getElementById("p_name").focus();
			return false;
		} else {
			document.getElementById('p_level').value='3';
			document.getElementById('p_code').value=document.getElementById("combo2").value;
		}
	}
	
	document.form01.action = '/admin/org/pos_manage.do';
	document.getElementById('query_type').value = 'I';
	document.form01.submit();
    return false;
}


function modifyContents(level,code,name1,name2) {
	chgDiv('2');
	document.getElementById('p_level').value=level;
	document.getElementById('p_orgCode').value = code;
	if(level=='1') {
		document.getElementById('p_orgName').value = name1;
	} else if(level == '2') {
		document.getElementById('p_orgName').value = name2;
	}
}

function modify_contents() {
	if(document.getElementById("p_orgCode").value == "") {
		alert('카테고리를 선택하세요.');
		return false;
	} else if(document.getElementById("p_orgName").value == "") {
		alert('카테고리를 선택하세요.');
		return false;
	} else {
		document.form01.action = '/admin/org/pos_manage.do';
		document.getElementById('query_type').value = 'U';
		document.form01.submit();
		return false;
	}
}

function deleteContents(level, code) {
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/org/pos_manage.do';
		document.getElementById('query_type').value='D';
		document.getElementById('p_level').value=level;
		document.getElementById('p_code').value=code;
		document.form01.submit();
	}
    return false;
}

function selectSort() {
	document.form01.action = '/admin/org/pos_list.do';
	document.form01.submit();
}

function clearForm() {
	$("input:radio[name='orgLevel']:radio[value='0']").prop("checked",false);
	$("input:radio[name='orgLevel']:radio[value='1']").prop("checked",false);
	
	document.getElementById("combo1").value = "";
	document.getElementById("p_name").value = "";
}

function chgLevel() {
	if(document.querySelector('input[name = "orgLevel"]:checked').value == '0') {
		document.getElementById("combo1").value = "";
		document.getElementById("combo1").disabled = true;
	} else if(document.querySelector('input[name = "orgLevel"]:checked').value == '1') {
		document.getElementById("combo1").disabled = false;
	} else {
		
	}
}

function setCombo2() {
	$('#combo2').find('option').remove();
	$('#combo2').append("<option value=''>선택</option>");

 	var srcObj = document.getElementById('combo3');
 	var tarObj = document.getElementById('combo2');
 	
 	alert(srcObj.options.length);
    for( i = 0, k = srcObj.options.length; i < k; i++ ){
        if(document.getElementById('combo1').value.substring(0,1) == srcObj[i].value.substring(0,1)){
        	tarObj.add(new Option(srcObj[i].text, srcObj[i].value, false, false));
        }
    }

}

function chgDiv(gubun) {
	if(gubun == '1') {
		$("#insCodeDiv").show();
		$("#updCodeDiv").hide();
		document.getElementById('p_level').value='';
		document.getElementById('p_orgCode').value = '';
		document.getElementById('p_orgName').value = '';
	} else {
		$("#insCodeDiv").hide();
		$("#updCodeDiv").show();
	}
}
window.onload = function () {
	chgDiv('1');
}
</script>

<body>
<form class="form-group" name="form01" action="/admin/org/pos_list.do" method="POST">
<input type="hidden" name="query_type" id="query_type" value=""/>
<input type="hidden" name="p_level" id="p_level" value=""/>
<input type="hidden" name="p_code" id="p_code" value=""/>
    <!-- top start(left menu include) -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- top end -->
        
        <!-- main start -->        
        <!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h3 class="page-header">직급코드관리</h3>
						</div>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- //.row(page title end -->
            <div class="page-list">
            	<div class="panel panel-default">
                       <!-- /.panel-heading -->
		            <div class="panel-body" style="overflow-x:hidden;overflow-y:scroll;width:100%;height:360px;">
		              <table width="100%" class="table" id="dataTables-example">
						    <thead>
						      <tr>
						        <th>NO</th>
						        <th>직급코드</th>
						        <th>대분류</th>
						        <th>중분류</th>
						        <th>수정</th>
						        <th>삭제</th>			        
						        <th style="display:none;">LEVEL</th>
						      </tr>
						    </thead>
						    <tbody>
						      <c:choose>
									<c:when test="${fn:length(posList) > 0}">
									<c:forEach items="${posList}" var="list">
			                              <tr>
			                                  <td class="center">${list.RNUM} </td>
			                                  <td class="center">${list.POS_CODE} </td>
			                                  <td class="center">${list.NAME1} </td>
			                                  <td class="center">${list.NAME2} </td>
			                                  <td><a href="javascript:modifyContents('${list.CODE_LEVEL}','${list.POS_CODE}','${list.NAME1}','${list.NAME2}')">수정</a></td>
			                                  <td class="center"><a href="javascript:deleteContents('${list.CODE_LEVEL}','${list.POS_CODE}')">삭제</a></td>
			                                  <td style="display:none;">${list.CODE_LEVEL}</td>
			                              </tr>
			                              </c:forEach>
									</c:when>
								</c:choose>		      
						    </tbody>
						  </table>
					</div>
				<!-- panel panel-default -->
			</div>
			<!-- col-lg-12 -->
		</div>						
			<!-- row -->
		<div class="page-list">
			<div id="insCodeDiv">
				<!--  Search : begin //-->
				  <table class="table">
				  	<colgroup>
						<col style="width:200px;">
						<col style="width:200px;">	
						<col style="width:140px;">
						<col>
					</colgroup>
						  	<tr>
						  		<td><label for="q_word">카테고리구분</label></td>
						  		<td>
						  		<input type="radio" name="orgLevel" id="orgLevel" value="0" onClick="chgLevel()"/>대분류
						  		<input type="radio" name="orgLevel" id="orgLevel" value="1" onClick="chgLevel()"/>중분류
						  		</td>
						  		<td><label for="q_word">대분류카테고리</label></td>
						  		<td>
						  			<select class="form-control form-control-short w-200 mr-10" name="combo1" id="combo1">
						  				<option value="">선택</option>
                                        <c:if test="${fn:length(posList) > 0}">
                                            <c:forEach var="entry" items="${posList}" varStatus="status">
                                            	<c:if test="${entry.CODE_LEVEL=='1'}">
                                             		<option value="${entry.POS_CODE}">${entry.NAME1}</option>
                                             	</c:if>
                                        	</c:forEach>
                                        </c:if>
						  			</select>
						  		</td>
						  	</tr>
						  	<tr>
						  		<td><label for="q_word">카테고리명</label></td>
						  		<td>
						  			<input class="form-control form-control-short" type="text" name="p_name" id="p_name"/>
						  		</td>
						  		<td colspan="2">
		  							<button type="button" class="btn btn-default" onclick="javascript:insertContents();return false;">등록</button>
		  							<button type="button" class="btn btn-default" onclick="javascript:clearForm();return false;">초기화</button>
						  		</td>
						  	</tr>
						  </table>
				</div>
				<!-- /.panel-body -->
				<div id="updCodeDiv">
				<!--  Search : begin //-->
				  <table class="table">
				  	<colgroup>
						<col style="width:200px;">
						<col style="width:200px;">	
						<col style="width:140px;">
						<col>
					</colgroup>
				  	<tr>
				  		<td><label for="q_word">직급코드</label></td>
				  		<td>
				  			<input class="form-control form-control-short" type="text" name="p_orgCode" id="p_orgCode" readonly/>
				  		</td>
				  		<td><label for="q_word">코드명</label></td>
				  		<td>
				  			<input class="form-control form-control-short" type="text" name="p_orgName" id="p_orgName"/>
				  		</td>
				  	</tr>
				  	<tr>
				  		<td colspan="3"></td>
				  		<td>
							<button type="button" class="btn btn-default" onclick="javascript:chgDiv('1');return false;">초기화</button>				  		
							<button type="button" class="btn btn-default" onclick="javascript:modify_contents();return false;">수정</button>
				  		</td>
				  	</tr>
				  </table>
				</div>
				<!-- /.panel-body -->
			</div>
        </div>
        <!-- /#page-wrapper -->

    </div>
    
    <!-- /#wrapper -->
</form>
</body>
<c:if test = "${fn:length(MSG) > 0 }">
<script>
//alert("${MSG}");
location.href = '/admin/org/pos_list.do';
</script>
</c:if>
<c:if test = "${CDD == 'SUCC' }">
<script>
	location.href = '/admin/org/pos_list.do';
</script>
</c:if>
</html>
