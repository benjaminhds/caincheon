<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/admin/_common/inc/head.jsp" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script type="text/javascript">
function viewList() {
	document.form01.action = '/admin/board/dgroup_list.do';
	//document.getElementById('idx').value=idx;
	document.form01.submit();
	return false;
}

function insert_contents() {
	document.form01.action = '/admin/board/dgroup_insert.do';
	document.form01.submit();
	return false;
}

function modify_contents() {
	//alert('modify_contents');
	document.form01.action = '/admin/board/dgroup_modify.do';
	//document.getElementById('idx').value=idx;
	document.form01.submit();
	return false;
}

function delete_contents() {
	document.form01.action = '/admin/board/dgroup_delete.do';
	document.form01.submit();
	return false;
}

function setDisplay(radio, value) {
	if ( radio.checked == true ) {
		if(value == "Y") {
			document.form01.displayType[1].checked = false;
		}
		else if(value=="N") {
			document.form01.displayType[0].checked = false;
		}
		
		document.getElementById('displayYN').value=value;
	}
	return false;
}

window.onload = function() {
	onLoadLeftMenu('info_03');
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
		<h3 class="page-header">관할구역 등록</h3>
	</c:when>
	<c:otherwise>
		<h3 class="page-header">>관할구역 수정</h3>	
	</c:otherwise>
</c:choose>
					</div>
				<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
  <form class="well" name="form01" action="/admin/board/dgroup_list.do" method="POST">
  	<input type="hidden" name="displayYN" id="displayYN" value="${dgroupContents.displayYN}"/>
  	
  	<div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">순번</label>
    		</div>
    		<div class="col-md-10" >
    			<c:choose>
    				<c:when test="${_params.query_type == 'modify'}">
    					<input type="text" class="form-control" id="gigu_code" placeholder="" name="gigu_code" value="${dgroupContents.gigu_code}" readOnly>
    				</c:when>
    				<c:otherwise>
    					<input type="text" class="form-control" id="gigu_code" placeholder="" name="gigu_code" value="${dgroupContents.insert_temp_code}" readOnly>
   					</c:otherwise>
    			</c:choose>		
    		</div>
    	</div>
    </div>   
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">지구명</label>
    		</div>
    		<div class="col-md-10" >
    			<input type="text" class="form-control" id="d_group_name" placeholder="" name="d_group_name" value="${dgroupContents.d_group_name}">		
    		</div>
    	</div>
    </div>   
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">지구관할구역 좌표</label>
    		</div>
    		<div class="col-md-10" >
    			<textarea class="form-control" rows="4" id="d_group_pos" name="d_group_pos">${dgroupContents.d_group_pos}</textarea>
    		</div>
    	</div>
    </div>      
    
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-2" >
    			<label for="">노출</label>
    		</div>
    		<div class="col-md-10" >
    			<c:choose>
		      		<c:when test="${dgroupContents.displayYN == 'Y'}">
		        		<label class="radio-inline"><input type="radio" name="displayType" id="displayType1" value="Y" onClick="setDisplay(this,'Y')" checked="checked">노출</label>
		        		<label class="radio-inline"><input type="radio" name="displayType" id="displayType2" value="N" onClick="setDisplay(this,'N')">비노출</label>
		        	</c:when>
		        	<c:otherwise>
		        		<label class="radio-inline"><input type="radio" name="displayType" id="displayType1" value="Y" onClick="setDisplay(this,'Y')">노출</label>
		        		<label class="radio-inline"><input type="radio" name="displayType" id="displayType2" value="N" onClick="setDisplay(this,'N')" checked="checked">비노출</label>
		        	</c:otherwise>	
		       	</c:choose>		
    		</div>
    	</div>
    </div>   

</form>
	<p><button type="button" id="btnList"   class="btn btn-default pull-right" onclick="javascript:viewList();return false;">목록</button>
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