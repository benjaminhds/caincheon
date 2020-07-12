<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %><%@ 
	include file="/_common/inc/head_const.jsp" %><%@ 
	include file="/_common/inc/loginHandler.jsp" %><%
	
	request.setAttribute("__URI", request.getRequestURI().replace("//","/") );

%>
<!DOCTYPE html>
<html>
<head>
<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-136134881-1"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-136134881-1');
</script>

<!-- META -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="format-detection" content="telephone=no, address=no, email=no">
<meta content="천주교 인천교구" name="description">
<meta content="천주교 인천교구" name="keywords">
<meta content="website" property="og:type">
<meta content="천주교 인천교구" property="og:title">
<meta content="천주교 인천교구" property="og:description">
<meta content="/img/og_img.jpg" property="og:image">
<meta content="/" property="og:url">
<meta content="index, follow" name="robots">
<title>천주교 인천교구</title>

<!-- CSS -->
<link rel="stylesheet" type="text/css" href="/_common/css/base.css">
<link rel="stylesheet" type="text/css" href="/_common/css/plugins.css">
<link rel="stylesheet" type="text/css" href="/_common/css/common.css">
<link rel="stylesheet" type="text/css" href="/_common/css/sub.css">
<link rel="shortcut icon" href="/img/favic.png">

<!--[if lt IE 9]>
      <link href="/_common/css/ie.css" type="text/css" rel="stylesheet">
 <![endif]-->

<!-- JS -->
<!-- 주의) head.js 와 아래 순서를 일치 시키지 말 것. 아래 순서가 바뀌면 ui 가 틀어지거나, 스크롤바가 사라짐. START -->
<script src="/_common/js/jquery.fullpage.js"></script>
<script src="/_common/js/main.js"></script>
<script src="/_common/js/jquery-1.12.4.min.js"></script>
<script src="/_common/js/plugins.js"></script>
<script src="/_common/js/common.js"></script>
<script src="/admin/_common/js/admCommon.js"></script>
<script src="/_common/js/dotdotdot.js"></script>
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<!-- END -->
<script type="text/javascript">
var goChangeBody = function(fullURL) {
	// queryString 은 페이징 관련 변수를 제외한 나머지
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

// share for the facebook
function goFacebook(link, desc) {
	link = encodeURIComponent(location.host + link);
	desc = encodeURIComponent(desc);

	var url = 'http://www.facebook.com/sharer.php?u=' + link + '&t=' + desc;
	window.open(url,'','');
}

// share for the Tweeter
function goTweeter(link, desc) {
	link = encodeURIComponent(location.host + link);
	desc = encodeURIComponent(desc);

	var url = 'https://twitter.com/intent/tweet?text=' + desc + '&url=' + link;
	//var url = 'https://twitter.com/share?url=' + link + ' ' + desc;
	window.open(url,'','');
}

// // share for the Kakao
function goKakao(link, desc) {
	//link = encodeURIComponent(link);
	//desc = encodeURIComponent(desc);
	//Kakao.init('app key');
	Kakao.Story.share({
        url: location.host + link,
        text: desc
    });
}
</script>

<!--[if lt IE 9]>
      <script type="text/javascript" src="/_common/js/ie8.js"></script>
<![endif]-->
</head>
