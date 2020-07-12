<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<!-- META -->
<meta content="IE=edge" http-equiv="X-UA-Compatible">
<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link href="/admin/_common/css/sub.css" rel="stylesheet">
<link href="/admin/_common/css/sub2.css" rel="stylesheet">
<link href="/admin/_common/css/jquery.simple-dtpicker.css" rel="stylesheet">
<!-- Bootstrap Core CSS -->
<link href="/admin/_common/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<!-- MetisMenu CSS -->
<link href="/admin/_common/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="/admin/_common/dist/css/sb-admin-2.css" rel="stylesheet">
<link href="/admin/_common/dist/css/sb-admin-2.min.css" rel="stylesheet">
<link href="/admin/_common/css/admin.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="/admin/_common/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

<!-- footer script -->
<!-- jQuery -->
<link href="/admin/_common/vendor/jquery/jquery-ui.css" rel="stylesheet">
<script src="/admin/_common/vendor/jquery/jquery.min.js"></script>
<script src="/admin/_common/vendor/jquery/jquery-ui.min.js"></script>
<script src="/admin/_common/js/jquery.simple-dtpicker.js"></script>
   
<!-- Bootstrap Core JavaScript -->
<script src="/admin/_common/vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="/admin/_common/vendor/metisMenu/metisMenu.min.js"></script>

<!-- Custom Theme JavaScript -->
<script src="/admin/_common/dist/js/sb-admin-2.js"></script>
<script src="/admin/_common/js/admCommon.js"></script>
<!-- /.footer script -->

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->

<script type="text/javascript">

//쿠키 생성
function setCookie(cName, cValue, cDay){
 cName = "${CHANNEL}_" + cName;
 var expire = new Date();
 var aeskey = document.domain;
 expire.setDate(expire.getDate() + cDay);
 cValue = escape(cValue); // 한글 깨짐을 막기위해 escape(cValue)를 합니다.
 cValue = CryptoJS.AES.encrypt(cValue, aeskey).toString();//AES암호화
 cookies = cName + '=' + cValue + '; path=/ ;'; 
 if(typeof cDay != 'undefined') cookies += 'expires=' + expire.toGMTString() + ';';// expires를 주지 않으면, 브라우져가 닫히면 즉시 쿠키가 삭제됨.
 document.cookie = cookies;
}
//쿠키 가져오기
function getCookie(cName) {
 cName = "${CHANNEL}_" + cName;
 cName = cName + '=';
 var cookieData = document.cookie;
 var start = cookieData.indexOf(cName);
 var aeskey = document.domain;
 var cValue = '';
 if(start != -1){
     start += cName.length;
     var end = cookieData.indexOf(';', start);
     if(end == -1)end = cookieData.length;
     cValue = cookieData.substring(start, end);
 }
 try {
     cValue = CryptoJS.AES.decrypt(cValue, aeskey).toString(CryptoJS.enc.Utf8);//AES복호화
 } catch (e) {}
 return unescape(cValue);//한글처리
}

//req(text/html) & res(application/json)
function callAjaxWeb(pActionMethod, pUrl, dataJson, pCallbackFunc ) {
 var querystring = JSON.stringify(dataJson).replace(/[{\"\'"}]/gi,"").replace(/,/gi,"&").replace(/:/gi,"=");
 $.ajax({
     type : 'GET',
     url : pUrl + "?" + querystring , 
     dataType : 'json',
     success : function(resData){
         console.log(resData);
         pCallbackFunc(resData);
     },
     error : function(request, status, error) {
         console.log(request);
         console.log(status);
         console.log(error);
         alert("[Error] \n-status: " + status +"\n-error: " +error );
     } 
 });//end-ajax
}
//req(application/json) & res(application/json)
function callRest(pActionMethod, pUrl, dataJson, pCallbackFunc ) {
 $.ajax({
     type : pActionMethod,
     url : pUrl, 
     dataType : 'json',
     data : JSON.stringify(dataJson), 
     beforeSend : function(xhr){
         xhr.setRequestHeader("Content-type","application/json");
     },
     success : function(resData){
         console.log(resData);
         pCallbackFunc(resData);
     },
     error : function(request, status, error) {
         alert("[Error] \n-status: " + status +"\n-error: " +error);
     } 
 });//end-ajax
}
//ajax call
function callAJAX(actionMethod, restfulURL, dataJson, successFunc) {
	var REQUEST_BASE_URL = "${BASE_HOST}";
	$.ajax({
	    type : actionMethod,
	    url : REQUEST_BASE_URL + restfulURL,
	    dataType : 'json',
	    data : JSON.stringify(dataJson), 
	    beforeSend : function(xhr){
	        xhr.setRequestHeader("Content-type","application/json");
	    },
	    success : function(resData){
	      console.log(resData);
	      successFunc(resData);
	    },
	    error : function(request, status, error) {
	      console.log(request);
	      console.log(status);
	      console.log(error);
	      
	      if (request.status == '200') {
	   	    var msg = 'The result is a normal but no data';
	        console.log(msg);
	        toastr.options = toastr_options;
	        toastr.success(msg);
	      } else {
	   	    var msg = 'code:' + request.status + '\nmessage:'+ request.responseText + '\nerror:' + error;
	        console.log(msg);
	        toastr.options = toastr_options;
	        toastr.success(msg);
	      }
	    } 
	});//end-ajax
}
//util
function trim(str) {
	return str.replace(/^\s+|\s+$/g,"");
}
// 
var ajaxChangeBody = function(fullURL) {
	$.ajax({
		type: 'GET',
		url: fullURL,
		async: false,
		dataType: "text",
		contentType:"application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data) {
			$('body').html(data);
		},
		error: function(request, status, error) {
			alert(error);
		}
	});
};

</script>
</head>