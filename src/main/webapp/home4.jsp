<%@ page session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%@ 
	include file="/_common/inc/head.jsp" %>
<html lang="en"><%
	//
	request.setAttribute("HID", "_common"+request.getAttribute("homeId"));

%>
	
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="${HID}/css/bootstrap.css">
    <link rel="stylesheet" href="${HID}/css/personal.css">
    <!-- fontawesome -->
    <script src="https://kit.fontawesome.com/99b6be88e2.js" crossorigin="anonymous"></script>
    <!--fontfile-->
    <link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR&display=swap" rel="stylesheet">
    <!-- Jquery -->
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <!-- lightslider -->
    <link rel="stylesheet"  href="${HID}/css/lightslider.css">
    <script src="${HID}/js/lightslider.js"></script>
  
  <script type="text/javascript">

	function viewAlb(idx) {
		document.form01.action = '/news/alb_view.do';
		document.getElementById('idx').value = idx;
		document.form01.submit();
		return false;
	}

	function viewMov(b_idx, bl_idx) {
		var vfm = document.form01;
		vfm.action = '/news/mov_view.do';
		vfm.b_idx.value = b_idx;
		vfm.bl_idx.value = bl_idx;
		vfm.submit();
		return false;
	}

	function viewNews(b_idx, bl_idx) {
		var vfm = document.form01;
		vfm.action = '/news/news_view.do';
		vfm.b_idx.value = b_idx;
		vfm.bl_idx.value = bl_idx;
		vfm.submit();
		return false;
	}

	function viewSchedule(gubuncd, b_idx, s_idx) {
		var vfm = document.form01;
		vfm.action = '/news/sch_view.do';
		vfm.gubuncd.value = (gubuncd=='교구장' ? 2 : (gubuncd=='총대리' ? 3 : (gubuncd=='교구' ? 4 : (gubuncd=='부서' ? 5 : 1 ) ) ) );
		vfm.b_idx.value = b_idx;
		vfm.s_idx.value = s_idx;
		vfm.submit();
		return false;
	}

	function goTemple(templeName) {
		//var url= "/church/church.do?gigu_nm="+encodeURI(encodeURIComponent(templeName));
		var url = "/church/church.do?gigu_nm=" + templeName;
		location.href = url;
	}

	/**
	 * 교구일정 주별로  TOP 4 가져오기
	 */
	var srchweek_no = 0;
	function changeGyoguMonthlySchedule(i){
		srchweek_no = srchweek_no + i;
		var formData = new FormData($('form01')[0]);
		formData.append("srchweek"  , srchweek_no );
		_requestByAjax('/home/gyogu_monthly_schedule.do', formData
				, function(status, responseData){
						//console.log("status="+status);
						//console.log("responseData 1="+responseData);
						//console.log("responseData 2="+JSON.stringify(responseData));
						if(responseData.status=="success") {
							var html = "";
							var arr = responseData.SCHEDULE;
							if(arr.length == 0) {
								html = "조회한 기간의 일정이 없습니다.";
							} else {
								for(i = 0 ; i < arr.length ; i++) {
									html += "<li><em>"+arr[i].GUBUN+"</em>";
									html += "<a href=\"javascript:viewSchedule('', '','"+arr[i].S_IDX+"')\">" + arr[i].TITLE + "</a>";
									html += "<span>"+arr[i].START_DATE+"</span></li>";
								}
							}
							$("#gyogu_monthly_schedule").html(html);
							$("#gyogu_monthly_schedule_week").text(responseData.THISWEEK);
						}
					});
	}
	
	//
	$(document).ready(function(){
		// 
	});
	</script>
	
  <body>
  <div class="subBg"></div>
<form name="form01" action="/news/alb_view.do" method="POST">
	<input type="hidden" name="idx" id="idx" value="${idx}" /> <input
		type="hidden" name="gubuncd" id="gubuncd" value="${gubuncd}" /> <input
		type="hidden" name="b_idx" id="b_idx" value="${b_idx}" /> <input
		type="hidden" name="bl_idx" id="bl_idx" value="" /> <input
		type="hidden" name="s_idx" id="s_idx" value="" /> <input
		type="text" name="todayContents" id="todayContents"
		value="${todayContents.H_DATE}" style="height:0; font-size:0; overflow:hidden;visibility:hidden; width:0; height:0;" />
  
  
    <!-- 메뉴부분 -->
    <nav class="navbar sticky-top navbar-expand-xl navbar-dark header menupadding">
      <a class="navbar-brand" href="#">
        <img src="${HID}/img/main_logo2.png" class="mainlogo">
      </a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mx-auto fontmenu ">
      <li class="nav-item dropdown mainmenu">
        <a class="nav-link dropdown-toggle active" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          교구안내
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
            <div class="row breaking">
              <div class="col-md-2-7">
                <span class="text-uppercase text-white">교구안내</span>
                 <ul class="nav flex-column">
                 <li class="nav-item">
                   <a class="nav-link active" href="/intro/intro.jsp">교구소개</a></li>
                 <li class="nav-item">
                   <a class="nav-link active" href="/intro/history.do">온라인역사관</a></li>
                 <li class="nav-item">
                   <a class="nav-link active" href="/intro/shirine.jsp">교구성지</a></li>
                 <li class="nav-item">
                   <a class="nav-link active" href="/intro/diocesan.do">교구청</a></li>
                 <li class="nav-item">
                   <a class="nav-link active" href="/intro/ordo_list.do?lv1=08">수도회</a></li>
                 <li class="nav-item">
                   <a class="nav-link active" href="/intro/org_list.do?t=1&lv1=05">기관/단체</a></li>
              </ul>
              </div>
              <!-- /.col-md-4  -->
              <div class="col-md-2-7">
                <span class="text-uppercase text-white">교구장</span>
                 <ul class="nav flex-column">
                 <li class="nav-item">
                   <a class="nav-link active" href="/gyogu/intro.jsp">소개</a></li>
                 <li class="nav-item">
                   <a class="nav-link active" href="/gyogu/msg_list.do?type=ALL">메시지</a></li>
                 <li class="nav-item">
                   <a class="nav-link active" href="#">교구장동정</a></li>
                 <li class="nav-item">
                   <a class="nav-link active" href="/gyogu/par_list.do?b_idx=ALL">교구장동정</a></li>
                 <li class="nav-item">
                   <a class="nav-link active" href="/gyogu/ever_list.do?e_idx=1&b_idx=ALL&searchGubun=all">역대교구장</a></li>
                 </li>
              </div>
              <!-- /.col-md-4  -->
              <div class="col-md-2-7">
                <span class="text-uppercase text-white" onclick="gotoURL('/father/father_list.do');">사제단</span>
                 <ul class="nav flex-column">
                 <li class="nav-item">
                   <a class="nav-link active" href="#">사제</a>
                 </li>
                 <li class="nav-item">
                   <a class="nav-link active" href="#">선종사제</a>
                 </li>
              </div>
              <div class="col-md-2-7">
                <span class="text-uppercase text-white">본당</span>
                 <ul class="nav flex-column">
                 <li class="nav-item">
                   <a class="nav-link active" href="#">본당현황</a>
                 </li>
                 <li class="nav-item">
                   <a class="nav-link active" href="#">지구별</a>
                 </li>
                 <li class="nav-item">
                   <a class="nav-link active" href="#">공소</a>
                 </li>
              </div>
              <div class="col-md-2-7">
                <span class="text-uppercase text-white">소식</span>
                 <ul class="nav flex-column">
                 <li class="nav-item">
                   <a class="nav-link active" href="#">교구소식</a>
                 </li>
                 <li class="nav-item">
                   <a class="nav-link active" href="#">교구일정</a>
                 </li>
                 <li class="nav-item">
                   <a class="nav-link active" href="#">교구앨범</a>
                 </li>
                 <li class="nav-item">
                   <a class="nav-link active" href="#">교구영상</a>
                 </li>
                 <li class="nav-item">
                   <a class="nav-link active" href="#">인천주보</a>
                 </li>
              </div>
              <div class="col-md-2-7">
                <span class="text-uppercase text-white">자료실</span>
                 <ul class="nav flex-column">
                 <li class="nav-item">
                   <a class="nav-link active" href="#">성경</a>
                 </li>
                 <li class="nav-item">
                   <a class="nav-link active" href="#">전례력 & 매일미사</a>
                 </li>
                 <li class="nav-item">
                   <a class="nav-link active" href="#">로마미사경본</a>
                 </li>
                 <li class="nav-item">
                   <a class="nav-link active" href="#">한국천주교주소록</a>
                 </li>
                 <li class="nav-item">
                   <a class="nav-link active" href="#">사목자료</a>
                 </li>
              </div>
              <div class="col-md-2-7">
                <span class="text-uppercase text-white">참여마당</span>
                 <ul class="nav flex-column">
                 <li class="nav-item">
                   <a class="nav-link active" href="#">통신교리 안내</a>
                 </li>
                 <li class="nav-item">
                   <a class="nav-link active" href="#">카나혼인강좌 & 약혼자 주말 신청</a>
                 </li>
                 <li class="nav-item">
                   <a class="nav-link active" href="#">나누고 싶은 이야기</a>
                 </li>
              </div>
              <!-- /.col-md-4  -->
            </div>
          <!--  /.container  -->

        </div>
      </li>
      <li class="nav-item dropdown mainmenu">
        <a class="nav-link dropdown-toggle active" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          교구장
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <div class="row breaking">
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">교구안내</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구소개</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">온라인역사관</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구성지</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구청</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">수도회</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">기관/단체</a>
               </li>
            </ul>
            </div>
            <!-- /.col-md-4  -->
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">교구장</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">소개</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">메시지</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구장동정</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구장동정</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">역대교구장</a>
               </li>
            </div>
            <!-- /.col-md-4  -->
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">사제단</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">사제</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">선종사제</a>
               </li>
            </div>
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">본당</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">본당현황</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">지구별</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">공소</a>
               </li>
            </div>
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">소식</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구소식</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구일정</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구앨범</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구영상</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">인천주보</a>
               </li>
            </div>
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">자료실</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">성경</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">전례력 & 매일미사</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">로마미사경본</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">한국천주교주소록</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">사목자료</a>
               </li>
            </div>
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">참여마당</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">통신교리 안내</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">카나혼인강좌 & 약혼자 주말 신청</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">나누고 싶은 이야기</a>
               </li>
            </div>
            <!-- /.col-md-4  -->
          </div>

          <!--  /.container  -->


        </div>
      </li>
      <li class="nav-item dropdown mainmenu">
        <a class="nav-link dropdown-toggle active" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          사제단
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">


          <div class="row breaking">
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">교구안내</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구소개</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">온라인역사관</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구성지</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구청</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">수도회</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">기관/단체</a>
               </li>
            </ul>
            </div>
            <!-- /.col-md-4  -->
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">교구장</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">소개</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">메시지</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구장동정</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구장동정</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">역대교구장</a>
               </li>
            </div>
            <!-- /.col-md-4  -->
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">사제단</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">사제</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">선종사제</a>
               </li>
            </div>
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">본당</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">본당현황</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">지구별</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">공소</a>
               </li>
            </div>
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">소식</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구소식</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구일정</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구앨범</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구영상</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">인천주보</a>
               </li>
            </div>
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">자료실</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">성경</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">전례력 & 매일미사</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">로마미사경본</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">한국천주교주소록</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">사목자료</a>
               </li>
            </div>
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">참여마당</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">통신교리 안내</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">카나혼인강좌 & 약혼자 주말 신청</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">나누고 싶은 이야기</a>
               </li>
            </div>
            <!-- /.col-md-4  -->
          </div>
          <!--  /.container  -->


        </div>
      </li>
      <li class="nav-item dropdown mainmenu">
        <a class="nav-link dropdown-toggle active" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          본당
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">


          <div class="row breaking">
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">교구안내</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구소개</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">온라인역사관</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구성지</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구청</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">수도회</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">기관/단체</a>
               </li>
            </ul>
            </div>
            <!-- /.col-md-4  -->
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">교구장</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">소개</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">메시지</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구장동정</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구장동정</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">역대교구장</a>
               </li>
            </div>
            <!-- /.col-md-4  -->
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">사제단</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">사제</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">선종사제</a>
               </li>
            </div>
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">본당</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">본당현황</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">지구별</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">공소</a>
               </li>
            </div>
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">소식</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구소식</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구일정</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구앨범</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구영상</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">인천주보</a>
               </li>
            </div>
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">자료실</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">성경</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">전례력 & 매일미사</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">로마미사경본</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">한국천주교주소록</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">사목자료</a>
               </li>
            </div>
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">참여마당</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">통신교리 안내</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">카나혼인강좌 & 약혼자 주말 신청</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">나누고 싶은 이야기</a>
               </li>
            </div>
            <!-- /.col-md-4  -->
          </div>
          <!--  /.container  -->


        </div>
      </li>
      <li class="nav-item dropdown mainmenu">
        <a class="nav-link dropdown-toggle active" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          소식
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">


          <div class="row breaking">
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">교구안내</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구소개</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">온라인역사관</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구성지</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구청</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">수도회</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">기관/단체</a>
               </li>
            </ul>
            </div>
            <!-- /.col-md-4  -->
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">교구장</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">소개</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">메시지</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구장동정</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구장동정</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">역대교구장</a>
               </li>
            </div>
            <!-- /.col-md-4  -->
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">사제단</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">사제</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">선종사제</a>
               </li>
            </div>
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">본당</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">본당현황</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">지구별</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">공소</a>
               </li>
            </div>
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">소식</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구소식</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구일정</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구앨범</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구영상</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">인천주보</a>
               </li>
            </div>
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">자료실</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">성경</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">전례력 & 매일미사</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">로마미사경본</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">한국천주교주소록</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">사목자료</a>
               </li>
            </div>
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">참여마당</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">통신교리 안내</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">카나혼인강좌 & 약혼자 주말 신청</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">나누고 싶은 이야기</a>
               </li>
            </div>
            <!-- /.col-md-4  -->
          </div>
          <!--  /.container  -->


        </div>
      </li>
      <li class="nav-item dropdown mainmenu">
        <a class="nav-link dropdown-toggle active" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          자료실
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">


          <div class="row breaking">
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">교구안내</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구소개</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">온라인역사관</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구성지</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구청</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">수도회</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">기관/단체</a>
               </li>
            </ul>
            </div>
            <!-- /.col-md-4  -->
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">교구장</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">소개</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">메시지</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구장동정</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구장동정</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">역대교구장</a>
               </li>
            </div>
            <!-- /.col-md-4  -->
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">사제단</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">사제</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">선종사제</a>
               </li>
            </div>
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">본당</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">본당현황</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">지구별</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">공소</a>
               </li>
            </div>
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">소식</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구소식</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구일정</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구앨범</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구영상</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">인천주보</a>
               </li>
            </div>
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">자료실</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">성경</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">전례력 & 매일미사</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">로마미사경본</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">한국천주교주소록</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">사목자료</a>
               </li>
            </div>
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">참여마당</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">통신교리 안내</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">카나혼인강좌 & 약혼자 주말 신청</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">나누고 싶은 이야기</a>
               </li>
            </div>
            <!-- /.col-md-4  -->
          </div>
          <!--  /.container  -->


        </div>
      </li>
      <li class="nav-item dropdown mainmenu ">
        <a class="nav-link dropdown-toggle active" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          참여마당
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">


          <div class="row breaking">
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">교구안내</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구소개</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">온라인역사관</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구성지</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구청</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">수도회</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">기관/단체</a>
               </li>
            </ul>
            </div>
            <!-- /.col-md-4  -->
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">교구장</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">소개</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">메시지</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구장동정</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구장동정</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">역대교구장</a>
               </li>
            </div>
            <!-- /.col-md-4  -->
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">사제단</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">사제</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">선종사제</a>
               </li>
            </div>
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">본당</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">본당현황</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">지구별</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">공소</a>
               </li>
            </div>
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">소식</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구소식</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구일정</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구앨범</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">교구영상</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">인천주보</a>
               </li>
            </div>
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">자료실</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">성경</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">전례력 & 매일미사</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">로마미사경본</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">한국천주교주소록</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">사목자료</a>
               </li>
            </div>
            <div class="col-md-2-7">
              <span class="text-uppercase text-white">참여마당</span>
               <ul class="nav flex-column">
               <li class="nav-item">
                 <a class="nav-link active" href="#">통신교리 안내</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">카나혼인강좌 & 약혼자 주말 신청</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link active" href="#">나누고 싶은 이야기</a>
               </li>
            </div>
            <!-- /.col-md-4  -->
          </div>
          <!--  /.container  -->


        </div>
      </li>

    </ul>
    <ul class="navbar-nav fontmenu ml-auto">
      <li class="nav-item">
          <a class="nav-link" href="#"><i class="fas fa-search" style="color:#FFFFFF;padding-right:40px;"></i></a>
      </li>
      <li class="nav-item">
          <a class="nav-link" href="#"><i class="fas fa-user" style="color:#FFFFFF;"></i></a>
      </li>
    </ul>
  </div>


</nav>
    <!-- 메뉴끝 -->

    <div class="containerbox">
      <!-- 왼쪽 -->
      <div class="col-xl-8 leftsection">
      <!-- 슬라이드 -->
        <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
        <ol class="carousel-indicators">
          <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
          <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
          <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
        </ol>

        <div class="carousel-inner">
          <div class="carousel-item active">
            <img src="${HID}/img/holy1.jpeg" class="d-block slideimg" alt="...">
          </div>
          <div class="carousel-item">
            <img src="${HID}/img/holy1.jpeg" class="d-block slideimg" alt="...">
          </div>
          <div class="carousel-item">
            <img src="${HID}/img/holy1.jpeg" class="d-block slideimg" alt="...">
          </div>
        </div>

        <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="sr-only">Next</span>
        </a>
        </div>

        <div class = "newsbox">
            <p class="news"><span>Today ㅣ 2020.01.02 </span>성 대 바실리오와 나지안조의 성 그레고리오 주교 학자 기념일</p>
        </div>

        <div class = "nomarginbox">
          <!-- 교구소식 -->
          <div class = "col-md-6 leftbox">
            <div class="box">
              <div class="colorbox">

                  <div class="row">
                    <div class="col-10 sosiktitle">
                      <p class="gyogusosik">교구소식</p>
                    </div>
                    <div class="col-2 sosikicon">
                      <a href="#"><i class="fas fa-plus" style="color:#FFF;"></i></a>
                    </div>
                  </div>

              </div>
                <ul class="infocontent">
                <c:choose>
					<c:when test="${fn:length(parishList) > 0}">
						<c:forEach items="${parishList}" var="list" varStatus="status">
                  <li>
		            <em>
		            <c:if test="${list.GUBUN eq '1' }"><font color="red">*</font></c:if>
                    2020년 교구장 사목교서
                    ${fn:substring(list.TITLE,0,23)}<c:if test="${fn:length(list.TITLE)>23}">..</c:if>
                    </em><a href="#" href="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">${fn:replace(list.REGDATE,".","/")}</a>
                  </li>
						</c:forEach>
					</c:when>
				</c:choose><!-- 
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li> -->
                </ul>
            </div>
          </div>
          <!-- 교회소식 -->
          <div class = "col-md-6 rightbox foripad">
            <div class="box">
              <div class="churchbox">
                <div class="row">
                  <div class="col-10 sosiktitle">
                    <p class="gyogusosik">교회소식</p>
                  </div>
                  <div class="col-2 sosikicon">
                      <a href="#"><i class="fas fa-plus" style="color:#FFF;"></i></a>
                  </div>
                </div>
              </div>

                <ul class="infocontent">
                <c:choose>
					<c:when test="${fn:length(chuchNewsList) > 0}">
						<c:forEach items="${chuchNewsList}" var="list" varStatus="status">
						  <li>
		                    <em>
		                    <c:if test="${list.GUBUN eq '1' }"><font color="red">*</font></c:if>
		                    ${fn:substring(list.TITLE,0,26)}<c:if test="${fn:length(list.TITLE)>26}">..</c:if>
		                    </em>
		                    <a href="#" href="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">${fn:replace(list.REGDATE,".","/")}</a>
		                  </li>
						</c:forEach>
					</c:when>
				</c:choose><!-- 
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li> -->
                </ul>
            </div>
          </div>
        </div>

        <!-- 본당소식 -->
        <div class = "nomarginbox">
          <div class = "col-md-6 leftbox">
            <div class="box">
              <div class="bondangcolor">
                <div class="row">
                  <div class="col-10 sosiktitle">
                    <p class="gyogusosik">본당소식</p>
                  </div>
                  <div class="col-2 sosikicon">
                      <a href="#"><i class="fas fa-plus" style="color:#FFF;"></i></a>
                  </div>
              </div>
            </div>



                <ul class="infocontent">
                <c:choose>
					<c:when test="${fn:length(bondangNewsList) > 0}">
						<c:forEach items="${bondangNewsList}" var="list" varStatus="status">
						  <li>
		                    <em>
		                    <c:if test="${list.GUBUN eq '1' }"><font color="red">*</font></c:if>
		                    ${fn:substring(list.TITLE,0,26)}<c:if test="${fn:length(list.TITLE)>26}">..</c:if>
		                    </em>
		                    <a href="#" href="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">${fn:replace(list.REGDATE,".","/")}</a>
		                  </li>
						</c:forEach>
					</c:when>
				</c:choose>
				<!-- 
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                   -->
                </ul>
            </div>
          </div>
          <!-- 공동체 소식 -->
          <div class = "col-md-6 rightbox foripad">
            <div class="box">
              <div class="gongdongbox">
                <div class="row">
                  <div class="col-10 sosiktitle">
                    <p class="gyogusosik">공동체소식</p>
                  </div>
                  <div class="col-2 sosikicon">
                      <a href="#"><i class="fas fa-plus" style="color:#FFF;"></i></a>
                  </div>
                </div>
              </div>



                <ul class="infocontent">
                <c:choose>
					<c:when test="${fn:length(unitList) > 0}">
						<c:forEach items="${unitList}" var="list" varStatus="status">
						  <li>
		                    <em>
		                    <c:if test="${list.GUBUN eq '1' }"><font color="red">*</font></c:if>
		                    ${fn:substring(list.TITLE,0,26)}<c:if test="${fn:length(list.TITLE)>26}">..</c:if>
		                    </em>
		                    <a href="#" href="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">${fn:replace(list.REGDATE,".","/")}</a>
		                  </li>
						</c:forEach>
					</c:when>
				</c:choose><!-- 
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li> -->
                </ul>
            </div>
          </div>
        </div>

      </div>
      <!-- 오른쪽-->
      <div class="col-xl-4 rightsection">
        <div class="nomarginbox">
        <div class="iljungbox">
          <div class="box">
            <div class="nocolorbox">
              <p class="innertitle">${todayContents.YYMM}월 ${todayContents.WEEK}주차 교구일정</p>
              <div class="innerawesome">
                <a href="#" onclick="changeGyoguMonthlySchedule(-1)" alt="이전날짜"><i class="fas fa-chevron-left faw leftarw" style="color:#707070;"></i></a>
                <a href="#" onclick="changeGyoguMonthlySchedule( 1)" alt="다음날짜"><i class="fas fa-chevron-right faw rightarw" style="color:#707070;"></i></a>
              </div>
            </div>

            <div class="col-md-12">
              <hr class="dash">
            </div>
              <ul class="innercontent">
               <c:choose>
				<c:when test="${fn:length(schList) > 0}">
					<c:forEach items="${schList}" var="list" varStatus="status">
					<li>
	                <em>${list.START_DATE}</em>
	                <span>${list.GUBUN} 교구장</span>
	                <a href="#javascript: viewSchedule('${schList.GUBUN}','${schList.B_IDX}','${schList.S_IDX}')">${list.TITLE}</a>
	                </li>
					</c:forEach>
				</c:when>
			  </c:choose><!-- 
                <li>
                  <em>11/20</em>
                  <span>교구장</span>
                  <a href="#">가르멜 수도원 미사</a>
                </li>
                <li>
                  <em>11/20</em>
                  <span>교구장</span>
                  <a href="#">가르멜 수도원 미사</a>
                </li> -->
              </ul>
          </div>
        </div>

        <div class="gyogubox">
          <div class="boxgyogu">
            <div class="nocolordongjungbox">
              <div class="row">
                <div class="col-10 sosiktitle">
                  <p class="innertitle">교구장 동정</p>
                </div>
                <div class="col-2 sosikicon">
                  <a href="#"><i class="fas fa-plus" style="color:#707070;"></i></a>
                </div>
              </div>
            </div>



          <div class="col-md-12">
            <hr class="dash" style="margin-top:30.6255px;">
          </div>

          <div class="gyogucontent">
          <c:forEach items="${parList}" var="row" varStatus="status">
          
          	<c:set var="par_view_uri" value="/gyogu/par_view.do?pageNo=1&b_idx=${row.B_IDX}&bl_idx=${row.BL_IDX}" />
          	
          	<c:if test="${status.index > 0}">
            <div class="col-md-12 gyoguline"><hr class="dash2"></div>
            </c:if>
            
            <div class="firstcontent" onClick="javascript: location.href='${par_view_uri}';" onMouseOver="style.cursor:pointer;" onMouseOut="style.cursor:default;">
              <img src="${HID}/img/holy2.jpg" class="gyoguimg" >
              <p class="gyogutitle">${row.TITLE}</p>
              <p class="gyogutext">${row.CONTENT}</p>
            </div>
            
          </c:forEach><!-- 
            <div class="firstcontent">
              <img src="${HID}/img/holy2.jpg" class="gyoguimg">
              <p class="gyogutitle">일본 방문</p>
              <p class="gyogutext">교구장 정신철 요한 주교는 11월 24일부터 26일까지 일본을 방문하여 프란치스코 교황의 주례로 도쿄돔에서 봉헌된 장엄 미사에 참석하였다. </p>
            </div>
            <div class="col-md-12 gyoguline">
              <hr class="dash2">
            </div>
            <div class="fourthcontent">
              <img src="${HID}/img/holy2.jpg" class="gyoguimg">
              <p class="gyogutitle">일본 방문</p>
              <p class="gyogutext">교구장 정신철 요한 주교는 11월 24일부터 26일까지 일본을 방문하여 프란치스코 교황의 주례로 도쿄돔에서 봉헌된 장엄 미사에 참석하였다. </p>
            </div>
             -->
          </div>



        </div>
      </div>
    </div>
      </div>

      <div class="col-md-6 leftsection">
        <div class="videobox">
          <div class="nocolorbox">
            <p class="innertitle">교구영상</p>
            <div class="innerawesome">
              <a id="goToPrevSlide"><i class="fas fa-chevron-left faw leftarw" style="color:#707070;"></i></a>
              <a id="goToNextSlide"><i class="fas fa-chevron-right faw rightarw" style="color:#707070;"></i></a>
            </div>
          </div>

          <div class="col-md-12">
            <hr class="dash">
          </div>

          <div class="video">
            <ul id="lightSlider" class="lightSlider">
            
			<c:choose>
				<c:when test="${fn:length(movList) > 0}">
					<c:forEach items="${movList}" var="list" varStatus="status">
						<c:set var="YOUTUBE_THUMBNAIL_URL" value="/upload/gyogu_mov/${list.FILENAME}" />
						<c:if test="${list.YOUTUBE_THUMBNAIL_URL ne ''}">
							<c:set var="YOUTUBE_THUMBNAIL_URL" value="${list.YOUTUBE_THUMBNAIL_URL}" />
						</c:if>
						
		                <li>
		                  <img src="${YOUTUBE_THUMBNAIL_URL}" class="d-block light" alt="${status.count}">
		                </li>
					</c:forEach>
				</c:when>
			</c:choose><!-- 
                <li>
                  <img src="${HID}/img/holy3.png" class="d-block light" alt="...">
                </li>
                <li>
                    <img src="${HID}/img/holy3.png" class="d-block light" alt="...">
                </li> -->
            </ul>
          </div>
        </div>
      </div>

      <div class="col-md-6 rightsection">
        <div class="videobox">
          <div class="nocolordongjungbox">
            <div class="row">
              <div class="col-10 sosiktitle">
                <p class="innertitle">인천주보</p>
              </div>
              <div class="col-2 sosikicon" >
                <a href="#"><img src=${HID}/img/plus.png class="icons"></a>
              </div>
            </div>
          </div>





          <div class="col-md-12">
            <hr class="dash" style="margin-top:30.625px;">
          </div>

          <ul class="videocontent">
          <c:choose>
		    <c:when test="${fn:length(jooboList) > 0}">
			  <c:forEach items="${jooboList}" var="list" varStatus="status">
			  <li>
                  <em>${fn:substring(list.PUBDATE,2,10)}</em>
                  <span>${list.DESCRIPTION}</span>
		          <button type="button" class="btn pdfbutton buttonmargin">PDF</button>
		          <button type="button" class="btn pdfbutton">듣기</button>
                 </li>
			  </c:forEach>
		    </c:when>
		  </c:choose><!-- 
            <li>
              <em>12/29</em>
              <span>예수, 마리아, 요셉의 성가절 축일</span>
              <button type="button" class="btn pdfbutton buttonmargin">PDF</button>
              <button type="button" class="btn pdfbutton">듣기</button>
            </li>
            <li>
              <em>12/29</em>
              <span>예수, 마리아, 요셉의 성가절 축일</span>
              <button type="button" class="btn pdfbutton buttonmargin">PDF</button>
              <button type="button" class="btn pdfbutton">듣기</button>
            </li> -->
          </ul>
        </div>
      </div>

      <div class="col-md-6 leftsection">
        <div class="videobox">
          <div class="nocolorbox  ">
            <p class="innertitle">교구앨범</p>
            <div class="innerawesome">
              <a id="goToPrevSlider"><i class="fas fa-chevron-left faw leftarw" style="color:#707070;"></i></a>
              <a id="goToNextSlider"><i class="fas fa-chevron-right faw rightarw" style="color:#707070;"></i></a>
            </div>
          </div>

          <div class="col-md-12">
            <hr class="dash">
          </div>

          <div class="video">
            <ul id="gyogualbum">
				<c:choose>
				<c:when test="${fn:length(albGgList) > 0}">
					<c:forEach items="${albGgList}" var="list" varStatus="status">
						<c:if test="${status.index > -1}">
						<li onClick="javascript:viewAlb(${list.IDX})">
			              <img src="${list.FILEPATH}${list.STRFILENAME}" class="albumimg">
			              <p class="albumtxt">${list.TITLE}</p>
			              <p class="albumdate">${list.REGDATE}</p>
			            </li>
			            </c:if>
					</c:forEach>
				</c:when>
				</c:choose><!-- 
                <li>
                  <img src="${HID}/img/holy4.png" class="albumimg">
                  <p class="albumtxt">18기 선교사학교 수료 미사</p>
                  <p class="albumdate">19-12-10</p>
                </li>
                <li>
                  <img src="${HID}/img/holy4.png" class="albumimg">
                  <p class="albumtxt">18기 선교사학교 수료 미사</p>
                  <p class="albumdate">19-12-10</p>
                </li>-->
            </ul>
          </div>
        </div>
      </div>

      <div class="col-md-6 rightsection">
        <div class="videobox">
          <div class="nocolorbox">
            <p class="innertitle">본당앨범</p>
            <div class="innerawesome">
              <a id="goToPrevSliders"><i class="fas fa-chevron-left faw leftarw" style="color:#707070;"></i></a>
              <a id="goToNextSliders"><i class="fas fa-chevron-right faw rightarw" style="color:#707070;"></i></a>
            </div>
          </div>

          <div class="col-md-12">
            <hr class="dash">
          </div>

          <div class="video">
            <ul id="bondangalbum">
                <c:choose>
				<c:when test="${fn:length(albBdList) > 0}">
					<c:forEach items="${albBdList}" var="list" varStatus="status">
						<c:if test="${status.index > -1}">
						<li onClick="javascript:viewAlb(${list.IDX})">
			              <img src="${list.FILEPATH}${list.STRFILENAME}" class="albumimg">
			              <p class="albumtxt">${list.TITLE}</p>
			              <p class="albumdate">${list.REGDATE}</p>
			            </li>
			            </c:if>
					</c:forEach>
				</c:when>
				</c:choose><!-- 
                <li>
                  <img src="${HID}/img/holy4.png" class="albumimg">
                  <p class="albumtxt">18기 선교사학교 수료 미사</p>
                  <p class="albumdate">19-12-10</p>
                </li>
                <li>
                  <img src="${HID}/img/holy4.png" class="albumimg">
                  <p class="albumtxt">18기 선교사학교 수료 미사</p>
                  <p class="albumdate">19-12-10</p>
                </li>
                <li>
                  <img src="${HID}/img/holy4.png" class="albumimg">
                  <p class="albumtxt">18기 선교사학교 수료 미사</p>
                  <p class="albumdate">19-12-10</p>
                </li> -->
            </ul>
          </div>
        </div>
      </div>

      <div class="col-xl-12">
          <div class="iconbox">
            <div class="firstline text-center">
              <a class="icontag firsticonline hana"  href="/gyogu/msg_view_recently.do" title="사목교서"><img src="${HID}/img/img1.png" class="icon"></a>
              <a class="icontag firsticonline dul"   href="/samok/cure_list.do?c_idx=ALL" title="사목자료"><img src="${HID}/img/img2.png" class="icon"></a>
              <a class="icontag firsticonline sat"   href="/church/temp_01.do?qk=" title="미사시간"><img src="${HID}/img/img3.png" class="icon"></a>
              <a class="icontag secondiconline hana" href="/intro/intro_03.jsp" title="관할구역"><img src="${HID}/img/img4.png" class="icon"></a>
              <a class="icontag secondiconline dul"  href="/community/doctrine.jsp" title="통신교리신청"><img src="${HID}/img/img5.png" class="icon"></a>
            </div>
            <div class="secondline text-center">
              <a class="icontag secondiconline sat" href="/news/mgz_list.do?pub_idx=3" title="인천주보"><img src="${HID}/img/img6.png" class="icon"></a>
              <a class="icontag thirdiconline hana" title="문한림 주교 강론 모음"><img src="${HID}/img/img7.png" class="speicalicon"></a>
              <a class="icontag thirdiconline dul"  href="/community/cana.do" title="카나혼인강좌 & 약혼자 주말신청"><img src="${HID}/img/img8.png" class="speicalicon"></a>
              <a class="icontag thirdiconline sat"  href="/intro/diocesan_12.do#MarriageAndFaith" title="혼인과 신앙"><img src="${HID}/img/img9.png" class="icon"></a>
              <!-- 관리자에서 임의의 링크 1개 -->
              
            </div>
          </div>
      </div>

      <div class="col-xl-12 topmargin">
        <div class="card bg-dark text-white">
          <img class="card-img cardimg" src="${HID}/img/holycard.jpg" alt="Card image">
          <div class="card-img-overlay d-flex flex-column">
            <div class="text-center">
              <p class="bondangtitle">본당 찾기</p>
              <p class="bondangtext">아름다운 인천교구 지구별 본당을 찾아보세요.<br>하단을 클릭하시면 해당 지구별 본당 페이지로 이동합니다.</p>
            </div>
              <div class="seconddongne text-center">
                <div class="dropdown show div-inline">
                  <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    부천2지구
                  </a>
                  <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
	                <c:choose>
					<c:when test="${fn:length(giguList) > 0}">
						<c:forEach items="${giguList}" var="row" varStatus="status">
							<c:if test="${row.GIGU_CODE eq '09'}"><a class="dropdown-item" href="/church/church.do?churchIdx=${row.CHURCH_IDX}&step1=01">${row.NAME}</a></c:if>
						</c:forEach>
					</c:when>
					</c:choose><!-- 
                    <a class="dropdown-item" href="#">고강동</a>
                    <a class="dropdown-item" href="#">삼정동</a>
                    <a class="dropdown-item" href="#">상1동</a>
                    <a class="dropdown-item" href="#">상3동</a>
                    <a class="dropdown-item" href="#">여월동</a>
                    <a class="dropdown-item" href="#">오정동</a>
                    <a class="dropdown-item" href="#">원미동</a>
                    <a class="dropdown-item" href="#">원종2동</a>
                    <a class="dropdown-item" href="#">중1동</a>
                    <a class="dropdown-item" href="#">중1동</a>
                    <a class="dropdown-item" href="#">중1동</a> -->
                  </div>
                </div>
                <div class="dropdown show div-inline">
                  <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    서구지구
                  </a>
                  <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
	                <c:choose>
					<c:when test="${fn:length(giguList) > 0}">
						<c:forEach items="${giguList}" var="row" varStatus="status">
							<c:if test="${row.GIGU_CODE eq '03'}"><a class="dropdown-item" href="/church/church.do?churchIdx=${row.CHURCH_IDX}&step1=01">${row.NAME}</a></c:if>
						</c:forEach>
					</c:when>
					</c:choose><!-- 
                    <a class="dropdown-item" href="#">가정3동</a>
                    <a class="dropdown-item" href="#">가정동</a>
                    <a class="dropdown-item" href="#">가좌동</a>
                    <a class="dropdown-item" href="#">검단동</a>
                    <a class="dropdown-item" href="#">검암동</a>
                    <a class="dropdown-item" href="#">마리스텔라(준)</a>
                    <a class="dropdown-item" href="#">마전동</a>
                    <a class="dropdown-item" href="#">석남동</a>
                    <a class="dropdown-item" href="#">연희동</a>
                    <a class="dropdown-item" href="#">오류동</a>
                    <a class="dropdown-item" href="#">원당동</a>
                    <a class="dropdown-item" href="#">청라</a>
                    <a class="dropdown-item" href="#">청라3동</a> -->
                  </div>
                </div>
                <div class="dropdown show div-inline">
                  <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    시흥안산지구
                  </a>
                  <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
	                <c:choose>
					<c:when test="${fn:length(giguList) > 0}">
						<c:forEach items="${giguList}" var="row" varStatus="status">
							<c:if test="${row.GIGU_CODE eq '10'}"><a class="dropdown-item" href="/church/church.do?churchIdx=${row.CHURCH_IDX}&step1=01">${row.NAME}</a></c:if>
						</c:forEach>
					</c:when>
					</c:choose><!-- 
                    <a class="dropdown-item" href="#">대부</a>
                    <a class="dropdown-item" href="#">대야동</a>
                    <a class="dropdown-item" href="#">도창동</a>
                    <a class="dropdown-item" href="#">신천</a>
                    <a class="dropdown-item" href="#">영흥</a>
                    <a class="dropdown-item" href="#">은계</a>
                    <a class="dropdown-item" href="#">은행동</a>
                    <a class="dropdown-item" href="#">포동</a>-->
                  </div>
                </div>
                <div class="dropdown show div-inline">
                  <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    연수지구
                  </a>
                  <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
	                <c:choose>
					<c:when test="${fn:length(giguList) > 0}">
						<c:forEach items="${giguList}" var="row" varStatus="status">
							<c:if test="${row.GIGU_CODE eq '05'}"><a class="dropdown-item" href="/church/church.do?churchIdx=${row.CHURCH_IDX}&step1=01">${row.NAME}</a></c:if>
						</c:forEach>
					</c:when>
					</c:choose>
                  </div>
                </div>
                <div class="dropdown show div-inline">
                  <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    중동구지구
                  </a>
                  <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
	                <c:choose>
					<c:when test="${fn:length(giguList) > 0}">
						<c:forEach items="${giguList}" var="row" varStatus="status">
							<c:if test="${row.GIGU_CODE eq '01'}"><a class="dropdown-item" href="/church/church.do?churchIdx=${row.CHURCH_IDX}&step1=01">${row.NAME}</a></c:if>
						</c:forEach>
					</c:when>
					</c:choose>
                  </div>
                </div>
                <div class="dropdown show div-inline">
                  <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    특수
                  </a>
                  <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
	                <c:choose>
					<c:when test="${fn:length(giguList) > 0}">
						<c:forEach items="${giguList}" var="row" varStatus="status">
							<c:if test="${row.GIGU_CODE eq '13'}"><a class="dropdown-item" href="/church/church.do?churchIdx=${row.CHURCH_IDX}&step1=01">${row.NAME}</a></c:if>
						</c:forEach>
					</c:when>
					</c:choose>

                  </div>
                </div>
              </div>
              <div class="text-center dongne">
                  <div class="dropdown show div-inline">
                    <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      강화지구
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
	                <c:choose>
					<c:when test="${fn:length(giguList) > 0}">
						<c:forEach items="${giguList}" var="row" varStatus="status">
							<c:if test="${row.GIGU_CODE eq '12'}"><a class="dropdown-item" href="/church/church.do?churchIdx=${row.CHURCH_IDX}&step1=01">${row.NAME}</a></c:if>
						</c:forEach>
					</c:when>
					</c:choose>
                    </div>
                  </div>

                  <div class="dropdown show div-inline">
                    <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      김포지구
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
	                <c:choose>
					<c:when test="${fn:length(giguList) > 0}">
						<c:forEach items="${giguList}" var="row" varStatus="status">
							<c:if test="${row.GIGU_CODE eq '11'}"><a class="dropdown-item" href="/church/church.do?churchIdx=${row.CHURCH_IDX}&step1=01">${row.NAME}</a></c:if>
						</c:forEach>
					</c:when>
					</c:choose>
                    </div>
                  </div>

                  <div class="dropdown show div-inline">
                    <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      계양지구
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
	                <c:choose>
					<c:when test="${fn:length(giguList) > 0}">
						<c:forEach items="${giguList}" var="row" varStatus="status">
							<c:if test="${row.GIGU_CODE eq '07'}"><a class="dropdown-item" href="/church/church.do?churchIdx=${row.CHURCH_IDX}&step1=01">${row.NAME}</a></c:if>
						</c:forEach>
					</c:when>
					</c:choose>
                    </div>
                  </div>
                  <div class="dropdown show div-inline">
                    <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      미주홀지구
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
	                <c:choose>
					<c:when test="${fn:length(giguList) > 0}">
						<c:forEach items="${giguList}" var="row" varStatus="status">
							<c:if test="${row.GIGU_CODE eq '02'}"><a class="dropdown-item" href="/church/church.do?churchIdx=${row.CHURCH_IDX}&step1=01">${row.NAME}</a></c:if>
						</c:forEach>
					</c:when>
					</c:choose>
                    </div>
                  </div>
                  <div class="dropdown show div-inline">
                    <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      남동지구
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
	                <c:choose>
					<c:when test="${fn:length(giguList) > 0}">
						<c:forEach items="${giguList}" var="row" varStatus="status">
							<c:if test="${row.GIGU_CODE eq '04'}"><a class="dropdown-item" href="/church/church.do?churchIdx=${row.CHURCH_IDX}&step1=01">${row.NAME}</a></c:if>
						</c:forEach>
					</c:when>
					</c:choose>
                    </div>
                  </div>
                  <div class="dropdown show div-inline">
                    <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      부평지구
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
	                <c:choose>
					<c:when test="${fn:length(giguList) > 0}">
						<c:forEach items="${giguList}" var="row" varStatus="status">
							<c:if test="${row.GIGU_CODE eq '06'}"><a class="dropdown-item" href="/church/church.do?churchIdx=${row.CHURCH_IDX}&step1=01">${row.NAME}</a></c:if>
						</c:forEach>
					</c:when>
					</c:choose>
                    </div>
                  </div>
                  <div class="dropdown show div-inline">
                    <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      부천1지구
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
	                <c:choose>
					<c:when test="${fn:length(giguList) > 0}">
						<c:forEach items="${giguList}" var="row" varStatus="status">
							<c:if test="${row.GIGU_CODE eq '08'}"><a class="dropdown-item" href="/church/church.do?churchIdx=${row.CHURCH_IDX}&step1=01">${row.NAME}</a></c:if>
						</c:forEach>
					</c:when>
					</c:choose>
                    </div>
                  </div>
              </div>
          </div>
        </div>
    </div>

    <div class="col-xl-12">
        <div class="holybox">
          <div class="innerbox">
            <p class="holyinnertitle">교구성지</p>
            <div class="innerawesome">
              <a id="prevsungjiSlider"><i class="fas fa-chevron-left faw leftarw" style="color:#707070;"></i></a>
              <a id="nextsungjiSlider"><i class="fas fa-chevron-right faw rightarw" style="color:#707070;"></i></a>
            </div>
          </div>

          <div class="col-md-12">
            <hr class="dash">
          </div>

          <div class="video">
            <ul id="gyogusungji">
                <li>
                  <div class="card bg-dark text-white">
                    <img class="card-img holyplace" src="${HID}/img/holy7.png" alt="Card image">
                    <div class="card-img-overlay d-flex flex-column justify-content-end">
                      <a class="holytitle" href="#">제물진두 순교성지</a>
                      <a class="holytext" href="#">하느님께서 순교자들을 감싸는 두 손 모양을 형상화 한 순교성지</a>
                      <div class="text-center dongne">
                      </div>
                    </div>
                  </div>
                </li>
                <li>
                  <div class="card bg-dark text-white">
                    <img class="card-img holyplace" src="${HID}/img/holy7.png" alt="Card image">
                    <div class="card-img-overlay d-flex flex-column justify-content-end">
                      <a class="holytitle" href="#">제물진두 순교성지</a>
                      <a class="holytext" href="#">하느님께서 순교자들을 감싸는 두 손 모양을 형상화 한 순교성지</a>
                      <div class="text-center dongne">
                      </div>
                    </div>
                  </div>
                </li>
                <li>
                  <div class="card bg-dark text-white">
                    <img class="card-img holyplace" src="${HID}/img/holy7.png" alt="Card image">
                    <div class="card-img-overlay d-flex flex-column justify-content-end">
                      <a class="holytitle" href="#">제물진두 순교성지</a>
                      <a class="holytext" href="#">하느님께서 순교자들을 감싸는 두 손 모양을 형상화 한 순교성지</a>
                      <div class="text-center dongne">
                      </div>
                    </div>
                  </div>
                </li>
            </ul>
          </div>

        </div>
    </div>

    <div class="col-xl-12">
      <div class="lastbox">
        <div class="firstholybutton col-xl-2dot4">
            <div class="dropdown show">
              <a class="btn btn-secondary dropdown-toggle holybutton" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                가톨릭
              </a>
              <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <a class="dropdown-item" href="http://www.cbck.or.kr">주교회의</a>
                <a class="dropdown-item" href="http://www.catholic.or.kr">굿뉴스</a>
              </div>
            </div>
        </div>

        <div class="col-xl-2dot4">
            <div class="dropdown show">
              <a class="btn btn-secondary dropdown-toggle holybutton" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                타교구
              </a>
              <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
				<a class="dropdown-item"  href="http://aos.catholic.or.kr/" target="_blank" title="서울대교구">서울대교구</a>
				<a class="dropdown-item"  href="http://www.casuwon.or.kr/" target="_blank" title="수원교구">수원교구</a>
				<a class="dropdown-item"  href="http://www.catholicbusan.or.kr/" target="_blank" title="부산교구">부산교구</a>
				<a class="dropdown-item"  href="http://www.gjcatholic.or.kr/" target="_blank" title="광주대교구">광주대교구</a>
				<a class="dropdown-item"  href="http://www.cccatholic.or.kr/" target="_blank" title="춘천교구">춘천교구</a>
				<a class="dropdown-item"  href="http://www.wjcatholic.or.kr/" target="_blank" title="원주교구">원주교구</a>
				<a class="dropdown-item"  href="http://cdcj.or.kr/" target="_blank" title="청주교구">청주교구</a>
				<a class="dropdown-item"  href="http://www.jcatholic.or.kr/" target="_blank" title="전주교구">전주교구</a>
				<a class="dropdown-item"  href="http://www.djcatholic.or.kr/" target="_blank" title="대전교구">대전교구</a>
				<a class="dropdown-item"  href="http://www.ucatholic.or.kr/" target="_blank" title="의정부교구">의정부교구</a>
				<a class="dropdown-item"  href="http://cathms.kr/" target="_blank" title="마산교구">마산교구</a>
				<a class="dropdown-item"  href="http://www.diocesejeju.or.kr/" target="_blank" title="제주교구">제주교구</a>
				<a class="dropdown-item"  href="http://www.daegu-archdiocese.or.kr/" target="_blank" title="대구대교구">대구대교구</a>
				<a class="dropdown-item"  href="http://www.acatholic.or.kr/" target="_blank" title="안동교구">안동교구</a>
				<a class="dropdown-item"  href="http://www.gunjong.or.kr/" target="_blank" title="군종교구">군종교구</a>
              </div>
            </div>
        </div>

        <div class="col-xl-2dot4">
            <div class="dropdown show">
              <a class="btn btn-secondary dropdown-toggle holybutton" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                부서
              </a>
              <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <a class="dropdown-item" target="new" href="http://youth.caincheon.or.kr/">청소년사목국</a>
                <a class="dropdown-item" target="new" href="http://www.inchung.net/">청년부</a>
                <a class="dropdown-item" target="new" href="http://www.sungso.net/">성소국</a>
              </div>
            </div>
        </div>

        <div class="col-xl-2dot4">
            <div class="dropdown show">
              <a class="btn btn-secondary dropdown-toggle holybutton" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                단체
              </a>
              <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <a class="dropdown-item" target="new" href="http://www.legiom.com/">바다의별 레지아</a>
                <a class="dropdown-item" target="new" href="http://www.incheonme.net/">인천ME</a>
                <a class="dropdown-item" target="new" href="http://www.cen.or.kr/">가톨릭환경연대</a>
              </div>
            </div>
        </div>

        <div class="col-xl-2dot4 lastholybutton">
            <div class="dropdown show">
              <a class="btn btn-secondary dropdown-toggle holybutton" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                기관
              </a>
              <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <a class="dropdown-item" target="new" href="https://www.iccu.ac.kr/">인천가톨릭대학교</a>
                <a class="dropdown-item" target="new" href="http://www.cku.ac.kr/">가톨릭관동대학교</a>
                <a class="dropdown-item" target="new" href="http://www.caritasincheon.or.kr/">인천가톨릭사회복지회</a>
                <a class="dropdown-item" target="new" href="http://www.yism.or.kr/">가톨릭아동청소년재단</a>
              </div>
            </div>
        </div>
      </div>
  </div>

    <div class="col-xl-12">
      <div class="footerbox">
      </div>
    </div>

  </div>
<div class="footersection">
  <div class="col-xl-12">
    <div class="footercontainer">
      <div class="innerfooter">
          <div class="col-xl-6">
            <img src="${HID}/img/main_logo.png" class="mr-auto">
            <div class="footertext ml-auto">
              <p>인천광역시 동구 박문로1 (송림동 103-25)</a>
            </div>
          </div>
          <div class="col-xl-6">
            <div align="right">
              <a href="/policy/personal.jsp" class="footerrighttext">개인보호정책</a>
              <p style="display:inline;" class="footerrighttext">|</p>
              <a href="/policy/email.jsp" class="footerrighttext">무단 이메일 수집 거부</a>
              <p style="display:inline;" class="footerrighttext">|</p>
              <a href="/policy/terms.jsp" class="footerrighttext">이용약관</a>
              <p style="display:inline;" class="footerrighttext">|</p>
              <a href="/policy/sitemap.jsp" class="footerrighttext">사이트맵</a>
              <p class="footerrighttext">Copyright © Diocese of Incheon. All rights reserved.</p>
            </div>
          </div>
      </div>
    </div>
  </div>
</div>






    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script>
    slider = $('#lightSlider').lightSlider({
        controls: false,
        loop: true,
        item:2,
        slideMargin:18.75,
        responsive : [
          {
            breakpoint:1024,
            settings: {
              item:1,
              slideMove:1,
              slideMargin:18.75,
            }
          }
        ]
    });
    $('#goToPrevSlide').on('click', function () {
        slider.goToPrevSlide();
    });
    $('#goToNextSlide').on('click', function () {
        slider.goToNextSlide();
    });
    </script>
    <script>
    slider1 = $('#gyogualbum').lightSlider({
        controls: false,
        loop: true,
        item:2,
        slideMargin:18.75,
        responsive : [
          {
            breakpoint:1024,
            settings: {
              item:1,
              slideMove:1,
              slideMargin:18.75,
            }
          }
        ]
    });
    $('#goToPrevSlider').on('click', function () {
        slider1.goToPrevSlide();
    });
    $('#goToNextSlider').on('click', function () {
        slider1.goToNextSlide();
    });
    </script>
    <script>
    slider2 = $('#bondangalbum').lightSlider({
        controls: false,
        loop: true,
        item:2,
        slideMargin:18.75,
        responsive : [
          {
            breakpoint:1024,
            settings: {
              item:1,
              slideMove:1,
              slideMargin:18.75,
            }
          }
        ]
    });
    $('#goToPrevSliders').on('click', function () {
        slider2.goToPrevSlide();
    });
    $('#goToNextSliders').on('click', function () {
        slider2.goToNextSlide();
    });
    </script>
    <script>
    slider3 = $('#gyogusungji').lightSlider({
        controls: false,
        loop: true,
        item:3,
        slideMargin:18.75,
        responsive : [
          {
            breakpoint:1024,
            settings: {
              item:1,
              slideMove:1,
              slideMargin:18.75,
            }
          }
        ]

    });
    $('#prevsungjiSlider').on('click', function () {
        slider3.goToPrevSlide();
    });
    $('#nextsungjiSlider').on('click', function () {
        slider3.goToNextSlide();
    });
    </script>
    <script>
          $(document).ready(function() {
       // executes when HTML-Document is loaded and DOM is ready

      // breakpoint and up
      $(window).resize(function(){
      	if ($(window).width() >= 980){

            // when you hover a toggle show its dropdown menu
            $(".navbar .dropdown-toggle").hover(function () {
               $(this).parent().toggleClass("show");
               $(this).parent().find(".dropdown-menu").toggleClass("show");
             });

              // hide the menu when the mouse leaves the dropdown
            $( ".navbar .dropdown-menu" ).mouseleave(function() {
              $(this).removeClass("show");
            });

      		// do something here
      	}
      });
      // document ready
      });
    </script>
    <script src="_common/js/bootstrap.js"></script>
  </body>
</html>
