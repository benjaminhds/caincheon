<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/admin/_common/inc/head.jsp" %>
<body>
<script type="text/javascript">
function goLogin() {
	var id = $("#pid").val();
	var pw = $("#ppw").val();
	if(id=="") { alert("아이디를 입력해 주세요."); $("#pid").focus(); return; }
	if(pw=="") { alert("암호를 입력해 주세요.");  $("#ppw").focus(); return; }
	$("#loginform").submit();
}

$(document).ready(function(){
	$('input').keydown(function(e) {
	    if (e.keyCode == 13) {
	    	goLogin();
	    }
	});
});
</script>
<form  method="post" name="loginform" id="loginform"  action="/admin/login.do">
<input type="hidden" name="gotoURL" value="${gotoURL}" >
	<div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Please Sign In</h3>
                    </div>
                    
                  	<div class="panel-body">
                        <fieldset>
                            <div class="form-group">
                                <input class="form-control" placeholder="아이디" id="pid" name="pid" type="text" autofocus>
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="Password" id="ppw" name="ppw" type="password" value="">
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input name="remember" type="checkbox" value="Remember Me">아이디 저장
                                </label>
                            </div>
                            <!-- Change this to a button or input when using this as a form -->
                            <a href="javascript:goLogin()" class="btn btn-lg btn-success btn-block">Login</a>
                        </fieldset>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>

     <c:if test = "${fn:length(ERR_MSG) > 0 }">
     <script>
     alert("${ERR_MSG}");
     </script>
     </c:if>
     
     
     <c:if test = "${fn:length(DORMANT_CLEAR_MAIL) > 0 }">
     <script>
     alert("${DORMANT_CLEAR_MAIL}");
     </script>
     </c:if>
</body>

</html>
