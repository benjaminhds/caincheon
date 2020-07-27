<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ 
	include file="/_common/inc/head_const.jsp" %><%@ 
	include file="/_common/inc/loginHandler.jsp" %><%
	//
	request.setAttribute("homeId", pnull(request.getAttribute("homeId")) );
	request.setAttribute("HID", "_common" + pnull(request.getAttribute("homeId")) );
	
	// 
	java.text.SimpleDateFormat sdfmmdd = new java.text.SimpleDateFormat("MM/dd");
	request.setAttribute("NOW_MMDD",  sdfmmdd.format(new java.util.Date()) );
%>
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
<meta http-equiv="Content-Type"    content="text/html; charset=utf-8">
<meta name="format-detection"      content="telephone=no, address=no, email=no">
<meta name="viewport"              content="width=device-width, initial-scale=1, shrink-to-fit=no">  <!-- responsive 하게 만들어주는 부분입니다 -->
<meta name="description"           content="천주교 인천교구">
<meta name="keywords"              content="천주교 인천교구" >
<meta property="og:type"           content="website" >
<meta property="og:title"          content="천주교 인천교구" >
<meta property="og:description"    content="천주교 인천교구" >
<meta property="og:image"          content="/img/og_img.jpg" >
<meta property="og:url"            content="/" >
<meta name="robots"                content="index, follow" >
<!--
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
-->
<title>천주교 인천교구</title>

<!-- CSS -->
<link rel="stylesheet" type="text/css" href="/_common${homeId}/css/base.css">
<link rel="stylesheet" type="text/css" href="/_common${homeId}/css/plugins.css">
<link rel="stylesheet" type="text/css" href="/_common${homeId}/css/common.css">
<link rel="stylesheet" type="text/css" href="/_common${homeId}/css/main.css">
<link rel="shortcut icon" href="/img/favic.png">
<!--[if lt IE 9]>
           <link href="/_common{homeId}/css/ie.css" type="text/css" rel="stylesheet">
      <![endif]-->
<!-- JS -->
<!-- 주의) headSub.js 와 아래 순서를 일치 시키지 말 것. 아래 순서가 바뀌면 ui 가 틀어지거나, 스크롤바가 사라짐. START -->
<script src="/_common${homeId}/js/jquery-1.12.4.min.js"></script>
<script src="/_common${homeId}/js/plugins.js"></script>
<script src="/_common${homeId}/js/jquery.fullpage.js"></script>
<script src="/_common${homeId}/js/common.js"></script>
<script src="/_common${homeId}/js/main.js"></script>
<script src="/_common${homeId}/js/dotdotdot.js"></script>
<script src="/admin/_common/js/admCommon.js"></script>
<!-- END -->
<!--[if lt IE 9]>
        <script type="text/javascript" src="/_common/js/ie8.js"></script>
  <![endif]-->

</head>
