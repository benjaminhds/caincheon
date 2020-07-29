<<<<<<< HEAD
<html lang="en">
<%@ page session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%@ 
	include file="/_common/inc/head.jsp" %>
<html lang="en">
    <title>천주교 인천교구</title>
    <!-- CSS -->
    <link rel="stylesheet" type="text/css" href="${HID}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${HID}/css/plugins.css">
    <link rel="stylesheet" type="text/css" href="${HID}/css/common.css">
    <link href="${HID}/css/main.css" rel="stylesheet" type="text/css">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="/css/bootstrap.css">
    <link rel="stylesheet" href="/css/personal.css">
    <link rel="shortcut icon" href="/img/favic.png">
    <!--[if lt IE 9]>
               <link href="/_common/css/ie.css" type="text/css" rel="stylesheet">
          <![endif]-->
    <!-- JS -->
    <!-- 주의) headSub.js 와 아래 순서를 일치 시키지 말 것. 아래 순서가 바뀌면 ui 가 틀어지거나, 스크롤바가 사라짐. START -->
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <!-- lightslider -->
    <script src="${HID}/js/jquery-1.12.4.min.js"></script>
    <script src="${HID}/js/plugins.js"></script>
    <script src="${HID}/js/jquery.fullpage.js"></script>
    <script src="${HID}/js/common.js"></script>
    <script src="${HID}/js/main.js"></script>
    <script src="${HID}/js/dotdotdot.js"></script>
    <script src="/admin/_common/js/admCommon.js"></script>
    <!-- END -->
    <!--[if lt IE 9]>
            <script type="text/javascript" src="/_common/js/ie8.js"></script>
      <![endif]-->

    <!-- fontawesome -->
    <script src="https://kit.fontawesome.com/99b6be88e2.js" crossorigin="anonymous"></script>
    <!--fontfile-->
    <link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR&display=swap" rel="stylesheet">
    <!-- Jquery -->
    <link rel="stylesheet"  href="/css/lightslider.css">
    <script src="/js/lightslider.js"></script>
  </head>
  <script src="http://cdnjs.cloudflare.com/ajax/libs/Swiper/4.5.0/js/swiper.min.js"></script>
  <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/Swiper/4.4.6/css/swiper.min.css">
  <script type="text/javascript">
  /*
  일반게시판
http://localhost:8080/n/board/normal_board_list.do?i_sBidx=5

앨범게시판
http://localhost:8080/n/board/alb_board_list.do?i_sBidx=5

영상게시판
http://localhost:8080/n/board/mov_board_list.do?i_sBidx=6

매거진
http://localhost:8080/n/board/mgz_board_list.do?i_sBidx=7
  */
  function viewAlb(idx) {
  	document.form01.action = '/n/board/alb_board_view.do';
  	document.getElementById('idx').value = idx;
  	document.getElementById('i_sBidx').value = idx;
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
	
  $(document).ready( function() {
  	// service call
  	 /* if ($('#todayContents').val() == "") {
  		$.ajax({
  					type : 'GET',
  					url : "/home.do",
  					async : false,
  					dataType : "text",
  					contentType : "application/x-www-form-urlencoded; charset=UTF-8",
  					success : function(data) {
  						$('body').html(data);
  					},
  					error : function(request, status, error) {
  						alert(error);
  					}
  				});
  	}  */


  });
  </script>

  <body>
      <!-- header -->
      <header>



  <script type="text/javascript">
  var isMobile = /Android|webOS|iPhone|iPad|iPod|BlackBerry/i.test(navigator.userAgent) ? true : false;
  function goUnifySearch(srchTextVal) {
    if(isMobile==true) {
      return;
    }
    if(srchTextVal=="") {
      //alert("검색어를 입력해 주세요.");
      $("#srchBar_srchText").focus();
      return;
    } else {
      location.href = "/search/unify_search.do?srchText="+srchTextVal;
      /*
      var frm = document.unifySearchForm;
      frm.action = "/search/unify_search.do";
      frm.srchText.value = srchTextVal;
      frm.submit();
      */
    }
  }
  </script>
  

  <div class="heads clearfix">
      <!-- --><h1><a href="home${homeId}.do"><img src="img/logo.png" alt="home"></a></h1>
      <!-- <h1><a href="home${homeId}.do"><img src="${HID}/img/main_logo2.png" alt="home"></a></h1>-->
      <h2 class="blind">탑메뉴</h2>
      <ol class="topmenu">


          <li><a href="/member/login.jsp?gotoBACK=${__URI}">로그인</a></li>
          <li><a href="/member/myinfo.jsp">나의정보</a></li>
          <li><a href="/member/register.jsp">회원가입</a></li>

          <li><a href="/policy/location.jsp" >오시는길</a></li>
      </ol>
      <div class="zoomer"><!-- 모바일영역 -->
          <a href="/search/unify_search.jsp"><img src="/img/sub/_ico/zoomer.png" alt="찾기"></a>
      </div>

      <nav class="clearfix">
          <h2 class="blind">메인메뉴</h2>
          <div class="search"><!-- PC영역 -->
              <label for="srchBar"></label>
              <input type="srchBar_srchText" id="srchBar_srchText" placeholder="통합검색"
              value=""
              onKeyDown='javascript: if(event.keyCode==13) { goUnifySearch(this.value); } '
              onKeyPress='javascript: if(event.keyCode==13) { goUnifySearch(this.value); } '>
              <a href='#' onClick='javascript: console.log($("#srchBar_srchText").val()); goUnifySearch($("#srchBar_srchText").val())'><img src="img/main/ico/zoom.png" alt=""></button>
          </div>
          <div class="hamburger"><!-- 모바일영역 -->
              <span></span>
              <span></span>
              <span></span>
              <span></span>
              <span></span>
              <span></span>
          </div>
          <ul>
              <li>
                  <a href="/intro/intro.jsp">교구안내</a>
                  <dl>
                      <dt>교구안내</dt>
                      <dd><a href="/intro/intro.jsp">교구소개</a></dd>
                      <dd><a href="/intro/history.do">온라인역사관</a></dd>
                      <dd><a href="/intro/shirine.jsp">교구성지</a></dd>
                      <dd><a href="/intro/diocesan.do">교구청</a></dd>
                      <dd><a href="/intro/ordo_list.do?lv1=08">수도회</a></dd>
                      <dd><a href="/intro/org_list.do?t=1&lv1=05">기관/단체</a></dd>
                  </dl>
              </li>
              <li>
                  <a href="/gyogu/intro.jsp">교구장</a>
                  <dl>
                      <dt>교구장</dt>
                      <dd><a href="/gyogu/intro.jsp">소개</a></dd>
                      <dd><a href="/gyogu/msg_list.do?type=ALL">메시지</a></dd>
                      <dd><a href="/gyogu/par_list.do?b_idx=ALL">교구장동정</a></dd>
                      <dd><a href="/gyogu/ever_list.do?e_idx=1&b_idx=ALL&searchGubun=all">역대교구장</a></dd>
                  </dl>
              </li>
              <li>
                  <a href="/father/father_list.do?">사제단</a>
                  <dl>
                      <dt>사제단</dt>
                      <dd><a href="/father/father_list.do?">사제</a></dd>
                      <dd><a href="/father/holy_list.do?">선종사제</a></dd>
                  </dl>
              </li>
              <li>
                  <a href="/church/temp_01.do?qk=">본당</a>
                  <dl>
                      <dt>본당</dt>
                      <dd><a href="/church/temp_01.do?qk=">본당현황</a></dd>
                      <dd><a href="/church/church.do?qk=">지구별</a></dd>
                      <dd><a href="/church/vacancy.do?qk=">공소</a></dd>
                  </dl>
              </li>
              <li>
                  <a href="/news/news_list.do?b_idx=ALL">소식</a>
                  <dl>
                      <dt>소식</dt>
                      <dd><a href="/news/news_list.do?b_idx=ALL">교구소식</a></dd>
                      <dd><a href="/news/sch_list.do?gubuncd=1">교구일정</a></dd>
                      <dd><a href="/news/alb_list.do?b_idx=ALL&c_idx=ALL">교구앨범</a></dd>
                      <dd><a href="/news/mov_list.do?b_idx=23&searchGubun=all">교구영상</a></dd>
                      <dd><a href="/news/mgz_list.do?pub_idx=3">인천주보</a></dd>
                  </dl>
              </li>
              <li>
                  <a href="/samok/cure_list.do?b_idx=ALL">자료실</a>
                  <dl>
                      <dt>자료실</dt>
                      <dd><a href="http://bible.cbck.or.kr" target="_blank" title="새창">성경</a></dd>
                      <dd><a href="http://missa.cbck.or.kr/" target="_blank" title="새창">전례력&매일미사</a></dd>
                      <dd><a href="http://missale.cbck.or.kr/" target="_blank" title="새창">로마미사경본</a></dd>
                      <dd><a href="http://directory.cbck.or.kr/" target="_blank" title="새창">한국천주교주소록</a></dd>
                      <dd><a href="/samok/cure_list.do?c_idx=ALL">사목자료</a></dd>
                  </dl>
              </li>
              <li>
                  <a href="/community/doctrine.jsp">참여마당</a>
                  <dl>
                      <dt>참여마당</dt>
                      <dd><a href="/community/doctrine.jsp">통신교리 안내</a></dd>
                      <dd><a href="/community/cana_register.do">카나혼인강좌 &amp;<br> 약혼자 주말 신청</a></dd>
                      <dd><a href="/community/tale.do?">나누고 싶은 이야기</a></dd>
                  </dl>
              </li>
          </ul>
      </nav>
  </div>
  <!-- subBg -->
  <div class="subBg">서브배경</div>
  <div class="layerBg"><img src="img/dimmed_70.png" alt=""></div>
  <!-- //subBg -->
</header>


	<form name="form01" action="/news/alb_view.do" method="POST">
		<input type="hidden" name="idx" id="idx" value="${idx}" />
		<input type="hidden" name="idx" id="i_sBidx" value="${b_idx}" /> <input
			type="hidden" name="gubuncd" id="gubuncd" value="${gubuncd}" /> <input
			type="hidden" name="b_idx" id="b_idx" value="${b_idx}" /> <input
			type="hidden" name="bl_idx" id="bl_idx" value="" /> <input
			type="hidden" name="s_idx" id="s_idx" value="" /> <input
			type="text" name="todayContents" id="todayContents"
			value="${todayContents.H_DATE}" style="height:0; font-size:0; overflow:hidden;visibility:hidden; width:0; height:0;" />
	</form>
	<!-- 메뉴끝 -->
  
<!-- mainSection START -->  
<div class="section" id="wrap">
    <div class="secWrap" id="mainSection">
    <section class="sec01">
    <div class="containerbox">
      <!-- 왼쪽 -->
      <div class="col-xl-8 leftsection">
      <!-- 슬라이드 START -->
        <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
        <ol class="carousel-indicators">
          <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
          <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
          <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
        </ol>

        <div class="carousel-inner">
          <div class="carousel-item active">
            <a href="">
            <img src="img/holy1.jpeg" class="d-block slideimg" alt="...">
            </a>
          </div>
          <div class="carousel-item">
            <a href="">
            <img src="img/holy1.jpeg" class="d-block slideimg" alt="...">
          </a>
          </div>
          <div class="carousel-item">
            <a href="">
            <img src="img/holy1.jpeg" class="d-block slideimg" alt="...">
          </a>
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
        <!-- 슬라이드 END -->

		<!-- 오늘의소식 START -->
        <div class = "newsbox">
            <p class="news"><span>Today ㅣ ${todayContents.H_DATE} </span>
            ${todayContents.DESCRIPTION}</p>
        </div>
        <!-- 오늘의소식 END -->


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
                <!-- 
                  <li>
                    <em class="gongji">2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                  -->
				
                <c:choose>
				<c:when test="${fn:length(mboardList) > 0}">
					<c:set var="EXIST_BOARD" value="N" />
					<c:forEach items="${mboardList}" var="list" varStatus="status">
						<c:set var="gongji" value="" />
						<c:set var="none_gongji" value="" />
						<c:if test="${status.index > -1 and list.B_NM eq '교구소식'}">
							<c:set var="EXIST_BOARD" value="Y" />
							<c:if test="${list.IS_NOTICE eq 'Y' }">
								<c:set var="gongji" value="class='gongji'" />
							</c:if>
							<c:if test="${list.IS_NOTICE eq 'N' }">
								<c:set var="gongji" value="" />
								<c:if test="${list.ORG_NM ne ''}">
									<c:set var="none_gongji" value="[${list.ORG_NM }]" />
								</c:if>
							</c:if>
							
							<li onClick="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">
		                        <em ${gongji}>${list.TITLE}</em>
		                        <a hre="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">${fn:substring(list.REGDATE,5,10)}</a>
		                    </li>
		                </c:if>
					</c:forEach>
					<c:if test="${EXIST_BOARD eq 'N' }">
					  <li>
	                    <em class="gongji">등록된 데이터가 없습니다.</em>
	                    <a href="#">${NOW_MMDD}</a>
	                  </li>
					</c:if>
				</c:when>
				<c:otherwise>
				  <li>
                    <em class="gongji">등록된 데이터가 없습니다.</em>
                    <a href="#">${NOW_MMDD}</a>
                  </li>
				</c:otherwise>
				</c:choose>

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

                <ul class="infocontent"><!-- 
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                  -->
                
                <c:choose>
				<c:when test="${fn:length(mboardList) > 0}">
					<c:set var="EXIST_BOARD" value="N" />
					<c:forEach items="${mboardList}" var="list" varStatus="status">
						<c:set var="gongji" value="" />
						<c:set var="none_gongji" value="" />
						<c:if test="${status.index > -1 and list.B_NM eq '교회소식'}">
							<c:set var="EXIST_BOARD" value="Y" />
							<c:if test="${list.IS_NOTICE eq 'Y' }">
								<c:set var="gongji" value="class='gongji'" />
							</c:if>
							<c:if test="${list.IS_NOTICE eq 'N' }">
								<c:set var="gongji" value="" />
								<c:if test="${list.ORG_NM ne ''}">
									<c:set var="none_gongji" value="[${list.ORG_NM }]" />
								</c:if>
							</c:if>
							
							<li onClick="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">
		                        <em ${gongji}>${list.TITLE}</em>
		                        <a hre="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">${fn:substring(list.REGDATE,5,10)}</a>
		                    </li>
		                </c:if>
					</c:forEach>
					<c:if test="${EXIST_BOARD eq 'N' }">
					  <li>
	                    <em class="gongji">등록된 데이터가 없습니다.</em>
	                    <a href="#">${NOW_MMDD}</a>
	                  </li>
					</c:if>
				</c:when>
				<c:otherwise>
				  <li>
                    <em class="gongji">등록된 데이터가 없습니다.</em>
                    <a href="#">${NOW_MMDD}</a>
                  </li>
				</c:otherwise>
				</c:choose>
				
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
                <!-- 
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                  -->
                
                <c:choose>
				<c:when test="${fn:length(mboardList) > 0}">
					<c:set var="EXIST_BOARD" value="N" />
					<c:forEach items="${mboardList}" var="list" varStatus="status">
						<c:set var="gongji" value="" />
						<c:set var="none_gongji" value="" />
						<c:if test="${status.index > -1 and list.B_NM eq '본당소식'}">
							<c:set var="EXIST_BOARD" value="Y" />
							<c:if test="${list.IS_NOTICE eq 'Y' }">
								<c:set var="gongji" value="class='gongji'" />
							</c:if>
							<c:if test="${list.IS_NOTICE eq 'N' }">
								<c:set var="gongji" value="" />
								<c:if test="${list.ORG_NM ne ''}">
									<c:set var="none_gongji" value="[${list.ORG_NM }]" />
								</c:if>
							</c:if>
							
							<li><a href="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">
		                        <em ${gongji}>${list.TITLE}</em> ${list.REGDATE}</a>
		                    </li>
		                </c:if>
					</c:forEach>
					<c:if test="${EXIST_BOARD eq 'N' }">
					  <li>
	                    <em class="gongji">등록된 데이터가 없습니다.</em>
	                    <a href="#">${NOW_MMDD}</a>
	                  </li>
					</c:if>
				</c:when>
				<c:otherwise>
				  <li>
                    <em class="gongji">등록된 데이터가 없습니다.</em>
                    <a href="#">${NOW_MMDD}</a>
                  </li>
				</c:otherwise>
				</c:choose>
				
				
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



                <ul class="infocontent"><!-- 
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                  -->
                
                <c:choose>
				<c:when test="${fn:length(mboardList) > 0}">
					<c:set var="EXIST_BOARD" value="N" />
					<c:forEach items="${mboardList}" var="list" varStatus="status">
						<c:set var="gongji"   value="" />
						<c:set var="none_gongji" value="" />
						<c:if test="${status.index > -1 and list.B_NM eq '공동체소식'}">
							<c:set var="EXIST_BOARD" value="Y" />
							<c:if test="${list.IS_NOTICE eq 'Y' }">
								<c:set var="gongji" value="class='gongji'" />
							</c:if>
							<c:if test="${list.IS_NOTICE eq 'N' }">
								<c:set var="gongji" value="" />
								<c:if test="${list.ORG_NM ne ''}">
									<c:set var="none_gongji" value="[${list.ORG_NM }]" />
								</c:if>
							</c:if>
							<li onClick="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">
		                        <em ${gongji}>${list.TITLE}</em>
		                        <a hre="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">${fn:substring(list.REGDATE,5,10)}</a>
		                    </li>
		                </c:if>
					</c:forEach>
					<c:if test="${EXIST_BOARD eq 'N' }">
					  <li>
	                    <em class="gongji">등록된 데이터가 없습니다.</em>
	                    <a href="#">${NOW_MMDD}</a>
	                  </li>
					</c:if>
				</c:when>
				</c:choose>
					
					
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
              <p class="innertitle">${todayContents.YYMM}월 ${todayContents.WEEK}주차 교구일정</p> <!-- 12월 4주 -->
              <div class="innerawesome">
                <a href="#"><i class="fas fa-chevron-left faw leftarw" style="color:#707070;"></i></a>
                <a href="#"><i class="fas fa-chevron-right faw rightarw" style="color:#707070;"></i></a>
              </div>
            </div>

            <div class="col-md-12">
              <hr class="dash">
            </div>
              <ul class="innercontent">
                <!-- <li>
                  <em>11/20</em>
                  <span>교구장</span>
                  <a href="#">가르멜 수도원 미사</a>
                </li>  5-rows -->
                
                
                <c:choose>
					<c:when test="${fn:length(ggScheduls) > 0}">
					<c:forEach items="${ggScheduls}" var="list">
						<li>
		                  <em>${ggScheduls.START_DATE}</em>
		                  <span>교구장</span>
		                  <a href="javascript: viewSchedule('${list.GUBUN}','${ggScheduls.B_IDX}','${ggScheduls.S_IDX}')">${ggScheduls.TITLE}</a>
		                </li>
					</c:forEach>
					</c:when>
					<c:otherwise>
					  <li>
	                    <em class="gongji">${NOW_MMDD}</em>
	                    <span>교구장</span>등록된 데이터가 없습니다.
	                  </li>
					</c:otherwise>
				</c:choose>

				
				
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
            <div class="firstcontent">
              <img src="img/holy2.jpg" class="gyoguimg">
              <p class="gyogutitle">일본 방문</p>
              <p class="gyogutext">교구장 정신철 요한 주교는 11월 24일부터 26일까지 일본을 방문하여 프란치스코 교황의 주례로 도쿄돔에서 봉헌된 장엄 미사에 참석하였다. </p>
            </div>
            <div class="col-md-12 gyoguline">
              <hr class="dash2">
            </div>
            <div class="secondcontent">
              <p class="gyogutitle">일본 방문</p>
              <p class="gyogutext">교구장 정신철 요한 주교는 11월 24일부터 26일까지 일본을 방문하여 프란치스코 교황의 주례로 도쿄돔에서 봉헌된 장엄 미사에 참석하였다. </p>
            </div>
            <div class="col-md-12 gyoguline">
              <hr class="dash2">
            </div>
            <div class="thirdcontent">
              <img src="img/holy2.jpg" class="gyoguimg">
              <p class="gyogutitle">일본 방문</p>
              <p class="gyogutext">교구장 정신철 요한 주교는 11월 24일부터 26일까지 일본을 방문하여 프란치스코 교황의 주례로 도쿄돔에서 봉헌된 장엄 미사에 참석하였다. </p>
            </div>
            <div class="col-md-12 gyoguline">
              <hr class="dash2">
            </div>
            <div class="fourthcontent">
              <img src="img/holy2.jpg" class="gyoguimg">
              <p class="gyogutitle">일본 방문</p>
              <p class="gyogutext">교구장 정신철 요한 주교는 11월 24일부터 26일까지 일본을 방문하여 프란치스코 교황의 주례로 도쿄돔에서 봉헌된 장엄 미사에 참석하였다. </p>
            </div>
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
                <!-- <li>
                  <img src="img/holy3.png" class="d-block light" alt="...">
                </li>
                <li>
                    <img src="img/holy3.png" class="d-block light" alt="...">
                </li>
                <li>
                    <img src="img/holy3.png" class="d-block light" alt="...">
                </li>
                <li>
                    <img src="img/holy3.png" class="d-block light" alt="...">
                </li>
                <li>
                    <img src="img/holy3.png" class="d-block light" alt="...">
                </li>
                <li>
                    <img src="img/holy3.png" class="d-block light" alt="...">
                </li>
                -->
                <c:choose>
				<c:when test="${fn:length(mboardList) > 0}">
					<c:forEach items="${mboardList}" var="list" varStatus="status">
						<c:if test="${status.index > -1 and list.B_NM eq '교구영상'}">
						<li onClick="javascript:viewAlb(${list.IDX})">
			              <img src="${list.FILEPATH}${list.STRFILENAME}" class="albumimg">
			              <p class="albumtxt">${list.TITLE}</p>
			              <p class="albumdate">${list.REGDATE}</p>
			            </li>
			            </c:if>
					</c:forEach>
				</c:when>
				</c:choose>
				
                <c:choose>
					<c:when test="${fn:length(movList) > 0}">
						<c:forEach items="${movList}" var="list" varStatus="status">
							<c:set var="YOUTUBE_THUMBNAIL_URL" value="/upload/gyogu_mov/${list.FILENAME}" />
							<c:if test="${list.YOUTUBE_THUMBNAIL_URL ne ''}">
								<c:set var="YOUTUBE_THUMBNAIL_URL" value="${list.YOUTUBE_THUMBNAIL_URL}" />
							</c:if>
							<c:if test="${status.count%3 eq 1}">
								<li class="big"><a
									href="javascript:viewMov('${list.B_IDX}','${list.BL_IDX}')"><img
										alt="" src="${YOUTUBE_THUMBNAIL_URL}" class="d-block light" ><span
										class="over"><img alt=""
											src="/img/dimmed_40.png"></span><i><span><img
												alt="" src="/img/main/ico/play.png"></span></i></a></li>
							</c:if>
							<c:if test="${status.count%3  eq 2}">
								<li class="sma"><a
									href="javascript:viewMov('${list.B_IDX}','${list.BL_IDX}')"><img
										alt="" src="${YOUTUBE_THUMBNAIL_URL}"><span
										class="over"><img alt=""
											src="/img/dimmed_40.png"></span><i><span><img
												alt="" src="/img/main/ico/play.png"></span></i></a></li>
							</c:if>
							<c:if test="${status.count%3 eq 0}">
								<li class="sma"><a
									href="javascript:viewMov('${list.B_IDX}','${list.BL_IDX}')"><img
										alt="" src="${YOUTUBE_THUMBNAIL_URL}"><span
										class="over"><img alt=""
											src="/img/dimmed_40.png"></span><i><span><img
												alt="" src="/img/main/ico/play.png"></span></i></a></li>
							</c:if>
						</c:forEach>
					</c:when>
				</c:choose>
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
                <a href="#"><i class="fas fa-plus" style="color:#707070;"></i></a>
              </div>
            </div>
          </div>





          <div class="col-md-12">
            <hr class="dash" style="margin-top:30.625px;">
          </div>

          <ul class="videocontent">
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
            </li>
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
              <button type="button" class="btn pdfbutton unavailable">듣기</button>
            </li>
            <li>
              <em>12/29</em>
              <span>예수, 마리아, 요셉의 성가절 축일</span>
              <button type="button" class="btn pdfbutton buttonmargin">PDF</button>
              <button type="button" class="btn pdfbutton unavailable">듣기</button>
            </li>
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
				<c:when test="${fn:length(mboardList) > 0}">
					<c:forEach items="${mboardList}" var="list" varStatus="status">
						<c:if test="${status.index > -1 and list.B_NM eq '교구앨범'}">
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
                  <img src="img/holy4.png" class="albumimg">
                  <p class="albumtxt">18기 선교사학교 수료 미사</p>
                  <p class="albumdate">19-12-10</p>
                </li>
                <li>
                  <img src="img/holy4.png" class="albumimg">
                  <p class="albumtxt">18기 선교사학교 수료 미사</p>
                  <p class="albumdate">19-12-10</p>
                </li>
                <li>
                  <img src="img/holy4.png" class="albumimg">
                  <p class="albumtxt">18기 선교사학교 수료 미사</p>
                  <p class="albumdate">19-12-10</p>
                </li> -->
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
				<c:when test="${fn:length(mboardList) > 0}">
					<c:forEach items="${mboardList}" var="list" varStatus="status">
						<c:if test="${status.index > -1 and list.B_NM eq '본당앨범'}">
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
                  <img src="img/holy4.png" class="albumimg">
                  <p class="albumtxt">18기 선교사학교 수료 미사</p>
                  <p class="albumdate">19-12-10</p>
                </li>
                <li>
                  <img src="img/holy4.png" class="albumimg">
                  <p class="albumtxt">18기 선교사학교 수료 미사</p>
                  <p class="albumdate">19-12-10</p>
                </li>
                <li>
                  <img src="img/holy4.png" class="albumimg">
                  <p class="albumtxt">18기 선교사학교 수료 미사</p>
                  <p class="albumdate">19-12-10</p>
                </li> -->
            </ul>
          </div>
        </div>
      </div>

      <div class="col-xl-12">
          <div class="iconbox">
            <div class="secondline text-center">
              <a class="icontag secondiconline sat"><img src="img/img6.png" class="icon"></a>
              <a class="icontag thirdiconline hana"><img src="img/img7.png" class="speicalicon"></a>
              <a class="icontag thirdiconline dul"><img src="img/img8.png" class="speicalicon"></a>
              <a class="icontag thirdiconline sat"><img src="img/img9.png" class="icon"></a>
            </div>
            <div class="firstline text-center">
              <a class="icontag firsticonline hana" href="#"><img src="img/img1.png" class="icon"></a>
              <a class="icontag firsticonline dul" href="#"><img src="img/img2.png" class="icon"></a>
              <a class="icontag firsticonline sat" href="#"><img src="img/img3.png" class="icon"></a>
              <a class="icontag secondiconline hana" href="#"><img src="img/img4.png" class="icon"></a>
              <a class="icontag secondiconline dul" href="#"><img src="img/img5.png" class="icon"></a>
            </div>
          </div>
      </div>

      <div class="col-xl-12 topmargin">
        <div class="card bg-dark text-white">
          <img class="card-img cardimg" src="img/holycard.jpg" alt="Card image">
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
                    <a class="dropdown-item" href="#">청라3동</a>
                  </div>
                </div>
                <div class="dropdown show div-inline">
                  <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    시흥안산지구
                  </a>
                  <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                    <a class="dropdown-item" href="#">대부</a>
                    <a class="dropdown-item" href="#">대야동</a>
                    <a class="dropdown-item" href="#">도창동</a>
                    <a class="dropdown-item" href="#">신천</a>
                    <a class="dropdown-item" href="#">영흥</a>
                    <a class="dropdown-item" href="#">은계</a>
                    <a class="dropdown-item" href="#">은행동</a>
                    <a class="dropdown-item" href="#">포동</a>
                  </div>
                </div>
                <div class="dropdown show div-inline">
                  <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    연수지구
                  </a>
                  <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                    <a class="dropdown-item" href="#">동춘동</a>
                    <a class="dropdown-item" href="#">선학동</a>
                    <a class="dropdown-item" href="#">송도2동</a>
                    <a class="dropdown-item" href="#">연수</a>
                    <a class="dropdown-item" href="#">옥련동 (구 송도)</a>
                    <a class="dropdown-item" href="#">인천가톨릭대학교</a>
                    <a class="dropdown-item" href="#">한국순교성인청학동</a>
                  </div>
                </div>
                <div class="dropdown show div-inline">
                  <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    중동구지구
                  </a>
                  <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                    <a class="dropdown-item" href="#">답동주교좌</a>
                    <a class="dropdown-item" href="#">대청도 (준)</a>
                    <a class="dropdown-item" href="#">덕적도</a>
                    <a class="dropdown-item" href="#">백령</a>
                    <a class="dropdown-item" href="#">송림4동</a>
                    <a class="dropdown-item" href="#">송림동</a>
                    <a class="dropdown-item" href="#">송현동</a>
                    <a class="dropdown-item" href="#">신공항</a>
                    <a class="dropdown-item" href="#">신도 (준)</a>
                    <a class="dropdown-item" href="#">연안</a>
                    <a class="dropdown-item" href="#">연평도</a>
                    <a class="dropdown-item" href="#">영종</a>
                    <a class="dropdown-item" href="#">용유 (준)</a>
                    <a class="dropdown-item" href="#">해안</a>
                    <a class="dropdown-item" href="#">화수동</a>
                  </div>
                </div>
                <div class="dropdown show div-inline">
                  <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    특수
                  </a>
                  <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                    <a class="dropdown-item" href="#">청언</a>

                  </div>
                </div>
              </div>
              <div class="text-center dongne">
                  <div class="dropdown show div-inline">
                    <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      강화지구
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                      <a class="dropdown-item" href="#">강화</a>
                      <a class="dropdown-item" href="#">내가</a>
                      <a class="dropdown-item" href="#">마니산 (준)</a>
                      <a class="dropdown-item" href="#">온수</a>
                      <a class="dropdown-item" href="#">하점</a>
                    </div>
                  </div>

                  <div class="dropdown show div-inline">
                    <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      김포지구
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                      <a class="dropdown-item" href="#">고춘</a>
                      <a class="dropdown-item" href="#">김포</a>
                      <a class="dropdown-item" href="#">대곶</a>
                      <a class="dropdown-item" href="#">마산동</a>
                      <a class="dropdown-item" href="#">불로동</a>
                      <a class="dropdown-item" href="#">사우2동</a>
                      <a class="dropdown-item" href="#">사우동</a>
                      <a class="dropdown-item" href="#">양곡</a>
                      <a class="dropdown-item" href="#">운양동</a>
                      <a class="dropdown-item" href="#">청수</a>
                      <a class="dropdown-item" href="#">통진</a>
                      <a class="dropdown-item" href="#">풍무동</a>
                      <a class="dropdown-item" href="#">하성</a>
                    </div>
                  </div>

                  <div class="dropdown show div-inline">
                    <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      계양지구
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                      <a class="dropdown-item" href="#">계산동</a>
                      <a class="dropdown-item" href="#">박촌동</a>
                      <a class="dropdown-item" href="#">서운동</a>
                      <a class="dropdown-item" href="#">작전2동 (구 계산1동)</a>
                      <a class="dropdown-item" href="#">작전동</a>
                    </div>
                  </div>
                  <div class="dropdown show div-inline">
                    <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      미주홀지구
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                      <a class="dropdown-item" href="#">도화동</a>
                      <a class="dropdown-item" href="#">숭의동</a>
                      <a class="dropdown-item" href="#">용현5동</a>
                      <a class="dropdown-item" href="#">용현동</a>
                      <a class="dropdown-item" href="#">제물포</a>
                      <a class="dropdown-item" href="#">주안1동</a>
                      <a class="dropdown-item" href="#">주안3동</a>
                      <a class="dropdown-item" href="#">주안5동</a>
                      <a class="dropdown-item" href="#">주안8동</a>
                      <a class="dropdown-item" href="#">학익동</a>
                    </div>
                  </div>
                  <div class="dropdown show div-inline">
                    <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      남동지구
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                      <a class="dropdown-item" href="#">간석2동</a>
                      <a class="dropdown-item" href="#">간석4동</a>
                      <a class="dropdown-item" href="#">고잔</a>
                      <a class="dropdown-item" href="#">구월1동</a>
                      <a class="dropdown-item" href="#">구월동</a>
                      <a class="dropdown-item" href="#">남촌동</a>
                      <a class="dropdown-item" href="#">논현동</a>
                      <a class="dropdown-item" href="#">만수1동</a>
                      <a class="dropdown-item" href="#">만수2동</a>
                      <a class="dropdown-item" href="#">만수3동</a>
                      <a class="dropdown-item" href="#">만수6동</a>
                      <a class="dropdown-item" href="#">모래내</a>
                      <a class="dropdown-item" href="#">서창2동</a>
                      <a class="dropdown-item" href="#">서창동</a>
                      <a class="dropdown-item" href="#">소래포구 (구 논현1동)</a>
                    </div>
                  </div>
                  <div class="dropdown show div-inline">
                    <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      부평지구
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                      <a class="dropdown-item" href="#">갈산동</a>
                      <a class="dropdown-item" href="#">부개2동</a>
                      <a class="dropdown-item" href="#">부개동</a>
                      <a class="dropdown-item" href="#">부평1동</a>
                      <a class="dropdown-item" href="#">부평2동</a>
                      <a class="dropdown-item" href="#">부평2동</a>
                      <a class="dropdown-item" href="#">부평4동</a>
                      <a class="dropdown-item" href="#">산곡3동</a>
                      <a class="dropdown-item" href="#">산곡동</a>
                      <a class="dropdown-item" href="#">삼산동</a>
                      <a class="dropdown-item" href="#">십정동</a>
                      <a class="dropdown-item" href="#">일신동</a>
                    </div>
                  </div>
                  <div class="dropdown show div-inline">
                    <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      부천1지구
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                      <a class="dropdown-item" href="#">범박동</a>
                      <a class="dropdown-item" href="#">상동</a>
                      <a class="dropdown-item" href="#">소사</a>
                      <a class="dropdown-item" href="#">소사본3동</a>
                      <a class="dropdown-item" href="#">소사본동</a>
                      <a class="dropdown-item" href="#">송내1동</a>
                      <a class="dropdown-item" href="#">심곡</a>
                      <a class="dropdown-item" href="#">심곡본동</a>
                      <a class="dropdown-item" href="#">역곡</a>
                      <a class="dropdown-item" href="#">역곡2동</a>
                      <a class="dropdown-item" href="#">옥길동</a>
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
                    <img class="card-img holyplace" src="img/holy7.png" alt="Card image">
                    <div class="card-img-overlay d-flex flex-column justify-content-end">
                      <a class="holytitle" href="/intro/shirine_03.jsp">제물진두 순교성지</a>
                      <a class="holytext" href="/intro/shirine_03.jsp">하느님께서 순교자들을 감싸는 두 손 모양을 형상화 한 순교성지</a>
                      <div class="text-center dongne">
                      </div>
                    </div>
                  </div>
                </li>
                <li>
                  <div class="card bg-dark text-white">
                    <img class="card-img holyplace" src="img/holy7.png" alt="Card image">
                    <div class="card-img-overlay d-flex flex-column justify-content-end">
                      <a class="holytitle" href="/intro/shirine_05.jsp">일만위 순교자 현양동산</a>
                      <a class="holytext" href="/intro/shirine_05.jsp">크고 많은 역사적 의미와 문화적 유산을<br> 간직하고 있는 	역사의 보고</a>
                      <div class="text-center dongne">
                      </div>
                    </div>
                  </div>
                </li>
                <li>
                  <div class="card bg-dark text-white">
                    <img class="card-img holyplace" src="img/holy7.png" alt="Card image">
                    <div class="card-img-overlay d-flex flex-column justify-content-end">
                      <a class="holytitle" href="/intro/shirine.jsp">갑곶순교성지</a>
                      <a class="holytext" href="/intro/shirine.jsp">순교자들의 행적증거자 박순집 베드로</a>
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
                <a class="dropdown-item" href="http://www.catholic.or.kr">굳뉴스</a>
              </div>
            </div>
        </div>

        <div class="col-xl-2dot4">
            <div class="dropdown show">
              <a class="btn btn-secondary dropdown-toggle holybutton" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                타교구
              </a>
              <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <a class="dropdown-item" href="http://aos.catholic.or.kr/">서울대교구</a>
                <a class="dropdown-item" href="http://www.casuwon.or.kr/">수원교구</a>
                <a class="dropdown-item" href="http://www.catholicbusan.or.kr/">부산교구</a>
                <a class="dropdown-item" href="http://www.gjcatholic.or.kr/">광주대교구</a>
                <a class="dropdown-item" href="http://www.cccatholic.or.kr/">춘천교구</a>
                <a class="dropdown-item" href="http://www.wjcatholic.or.kr/">원주교구</a>
                <a class="dropdown-item" href="http://cdcj.or.kr/">청주교구</a>
                <a class="dropdown-item" href="http://www.jcatholic.or.kr/">전주교구</a>
                <a class="dropdown-item" href="http://www.djcatholic.or.kr/">대전교구</a>
                <a class="dropdown-item" href="http://www.ucatholic.or.kr/">의정부교구</a>
                <a class="dropdown-item" href="http://cathms.kr/">마산교구</a>
                <a class="dropdown-item" href="http://www.diocesejeju.or.kr/">제주교구</a>
                <a class="dropdown-item" href="http://www.daegu-archdiocese.or.kr/">대구대교구</a>
                <a class="dropdown-item" href="http://www.acatholic.or.kr/">안동교구</a>
                <a class="dropdown-item" href="http://www.gunjong.or.kr/">군종교구</a>
              </div>
            </div>
        </div>

        <div class="col-xl-2dot4">
            <div class="dropdown show">
              <a class="btn btn-secondary dropdown-toggle holybutton" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                부서
              </a>
              <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <a class="dropdown-item" href="http://youth.caincheon.or.kr/">청소년사목국</a>
                <a class="dropdown-item" href="http://www.inchung.net/">청년부</a>
                <a class="dropdown-item" href="http://www.sungso.net/">성소국</a>
              </div>
            </div>
        </div>

        <div class="col-xl-2dot4">
            <div class="dropdown show">
              <a class="btn btn-secondary dropdown-toggle holybutton" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                단체
              </a>
              <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <a class="dropdown-item" href="http://www.legiom.com/">바다의별 레지아</a>
                <a class="dropdown-item" href="http://www.incheonme.net/">인천ME</a>
                <a class="dropdown-item" href="http://www.cen.or.kr/">가톨릭환경연대</a>
              </div>
            </div>
        </div>

        <div class="col-xl-2dot4 lastholybutton">
            <div class="dropdown show">
              <a class="btn btn-secondary dropdown-toggle holybutton" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                기관
              </a>
              <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <a class="dropdown-item" href="https://www.iccu.ac.kr/">인천가톨릭대학교</a>
                <a class="dropdown-item" href="http://www.cku.ac.kr/">가톨릭관동대학교</a>
                <a class="dropdown-item" href="http://www.caritasincheon.or.kr/">인천가톨릭사회복지회</a>
                <a class="dropdown-item" href="http://www.yism.or.kr/">가톨릭아동청소년재단</a>
              </div>
            </div>
        </div>
      </div>
  </div>





	<footer>
		<div id="footer_gotop" class="goTop">
		TOP
		</div>
	</footer>

</section>
</div>
</div>
<!-- mainSection START -->



<div class="footersection">
  <div class="col-xl-12">
    <div class="footercontainer">
      <div class="innerfooter">
          <div class="col-xl-6">
            <img src="img/main_logo.png" class="mr-auto footermain">
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



<script type="text/javascript">
window.onload = function() {
	/* 스크롤바가 전체 화면에서 사라지는 현상을 막기 위해서 강제처리 :: 2018-03-15 */
	if($(window).height() < $(document).height()) {
		var sh = $(document).prop("scrollHeight");
		var ch = $(document).prop("clientHeight");
		if( (sh==0 && ch==0) || (sh>ch) ) {
		//$("body").css("overflow","scroll");
		}
	}

	// if error happend
	setTimeout(function(){
			    var msg = "";
			    if(msg.length > 0)
			      alert(msg);
			  }, 500);
};
</script>
<script type="text/javascript">
function attachedFile_Download(filePath, fileName, strfileName) {
	if(fileName=="" || strfileName=="") {
		alert("다운로드 할 파일이 없습니다.");
	} else {
		var vfm = document.attachedFileDown;
		vfm.filePath.value    = filePath;
		vfm.fileName.value    = fileName;
		vfm.strfileName.value = strfileName;
		vfm.submit();
		return false;
	}
}
</script>
<!-- attached file download -->
<form  name="attachedFileDown" action="http://www.caincheon.or.kr/filedownload.jsp" method="POST" target="ifmAttachedFileDown">
	<input type="hidden" name="fileName" id="fileName" value=""/>
	<input type="hidden" name="filePath" id="filePath" value=""/>
	<input type="hidden" name="strfileName" id="strfileName" value=""/>
</form>
<iframe name="ifmAttachedFileDown" id="ifmAttachedFileDown" width=0 height=0 src="about:blank"></iframe>



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
    <script src="js/bootstrap.js"></script>
  </body>
</html>
=======
<html lang="en">
<%@ page session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%@ 
	include file="/_common/inc/head.jsp" %>
<html lang="en">
    <title>천주교 인천교구</title>
    <!-- CSS -->
    <link rel="stylesheet" type="text/css" href="${HID}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${HID}/css/plugins.css">
    <link rel="stylesheet" type="text/css" href="${HID}/css/common.css">
    <link href="${HID}/css/main.css" rel="stylesheet" type="text/css">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="/css/bootstrap.css">
    <link rel="stylesheet" href="/css/personal.css">
    <link rel="shortcut icon" href="/img/favic.png">
    <!--[if lt IE 9]>
               <link href="/_common/css/ie.css" type="text/css" rel="stylesheet">
          <![endif]-->
    <!-- JS -->
    <!-- 주의) headSub.js 와 아래 순서를 일치 시키지 말 것. 아래 순서가 바뀌면 ui 가 틀어지거나, 스크롤바가 사라짐. START -->
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <!-- lightslider -->
    <script src="${HID}/js/jquery-1.12.4.min.js"></script>
    <script src="${HID}/js/plugins.js"></script>
    <script src="${HID}/js/jquery.fullpage.js"></script>
    <script src="${HID}/js/common.js"></script>
    <script src="${HID}/js/main.js"></script>
    <script src="${HID}/js/dotdotdot.js"></script>
    <script src="/admin/_common/js/admCommon.js"></script>
    <!-- END -->
    <!--[if lt IE 9]>
            <script type="text/javascript" src="/_common/js/ie8.js"></script>
      <![endif]-->

    <!-- fontawesome -->
    <script src="https://kit.fontawesome.com/99b6be88e2.js" crossorigin="anonymous"></script>
    <!--fontfile-->
    <link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR&display=swap" rel="stylesheet">
    <!-- Jquery -->
    <link rel="stylesheet"  href="/css/lightslider.css">
    <script src="/js/lightslider.js"></script>
  </head>
  <script src="http://cdnjs.cloudflare.com/ajax/libs/Swiper/4.5.0/js/swiper.min.js"></script>
  <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/Swiper/4.4.6/css/swiper.min.css">
  <script type="text/javascript">
  /*
  일반게시판
http://localhost:8080/n/board/normal_board_list.do?i_sBidx=5

앨범게시판
http://localhost:8080/n/board/alb_board_list.do?i_sBidx=5

영상게시판
http://localhost:8080/n/board/mov_board_list.do?i_sBidx=6

매거진
http://localhost:8080/n/board/mgz_board_list.do?i_sBidx=7
  */
  function viewAlb(idx) {
  	document.form01.action = '/n/board/alb_board_view.do';
  	document.getElementById('idx').value = idx;
  	document.getElementById('i_sBidx').value = idx;
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
	
  $(document).ready( function() {
  	// service call
  	 /* if ($('#todayContents').val() == "") {
  		$.ajax({
  					type : 'GET',
  					url : "/home.do",
  					async : false,
  					dataType : "text",
  					contentType : "application/x-www-form-urlencoded; charset=UTF-8",
  					success : function(data) {
  						$('body').html(data);
  					},
  					error : function(request, status, error) {
  						alert(error);
  					}
  				});
  	}  */


  });
  </script>

  <body>
      <!-- header -->
      <header>



  <script type="text/javascript">
  var isMobile = /Android|webOS|iPhone|iPad|iPod|BlackBerry/i.test(navigator.userAgent) ? true : false;
  function goUnifySearch(srchTextVal) {
    if(isMobile==true) {
      return;
    }
    if(srchTextVal=="") {
      //alert("검색어를 입력해 주세요.");
      $("#srchBar_srchText").focus();
      return;
    } else {
      location.href = "/search/unify_search.do?srchText="+srchTextVal;
      /*
      var frm = document.unifySearchForm;
      frm.action = "/search/unify_search.do";
      frm.srchText.value = srchTextVal;
      frm.submit();
      */
    }
  }
  </script>
  

  <div class="heads clearfix">
      <!-- --><h1><a href="home${homeId}.do"><img src="img/logo.png" alt="home"></a></h1>
      <!-- <h1><a href="home${homeId}.do"><img src="${HID}/img/main_logo2.png" alt="home"></a></h1>-->
      <h2 class="blind">탑메뉴</h2>
      <ol class="topmenu">


          <li><a href="/member/login.jsp?gotoBACK=${__URI}">로그인</a></li>
          <li><a href="/member/myinfo.jsp">나의정보</a></li>
          <li><a href="/member/register.jsp">회원가입</a></li>

          <li><a href="/policy/location.jsp" >오시는길</a></li>
      </ol>
      <div class="zoomer"><!-- 모바일영역 -->
          <a href="/search/unify_search.jsp"><img src="/img/sub/_ico/zoomer.png" alt="찾기"></a>
      </div>

      <nav class="clearfix">
          <h2 class="blind">메인메뉴</h2>
          <div class="search"><!-- PC영역 -->
              <label for="srchBar"></label>
              <input type="srchBar_srchText" id="srchBar_srchText" placeholder="통합검색"
              value=""
              onKeyDown='javascript: if(event.keyCode==13) { goUnifySearch(this.value); } '
              onKeyPress='javascript: if(event.keyCode==13) { goUnifySearch(this.value); } '>
              <a href='#' onClick='javascript: console.log($("#srchBar_srchText").val()); goUnifySearch($("#srchBar_srchText").val())'><img src="img/main/ico/zoom.png" alt=""></button>
          </div>
          <div class="hamburger"><!-- 모바일영역 -->
              <span></span>
              <span></span>
              <span></span>
              <span></span>
              <span></span>
              <span></span>
          </div>
          <ul>
              <li>
                  <a href="/intro/intro.jsp">교구안내</a>
                  <dl>
                      <dt>교구안내</dt>
                      <dd><a href="/intro/intro.jsp">교구소개</a></dd>
                      <dd><a href="/intro/history.do">온라인역사관</a></dd>
                      <dd><a href="/intro/shirine.jsp">교구성지</a></dd>
                      <dd><a href="/intro/diocesan.do">교구청</a></dd>
                      <dd><a href="/intro/ordo_list.do?lv1=08">수도회</a></dd>
                      <dd><a href="/intro/org_list.do?t=1&lv1=05">기관/단체</a></dd>
                  </dl>
              </li>
              <li>
                  <a href="/gyogu/intro.jsp">교구장</a>
                  <dl>
                      <dt>교구장</dt>
                      <dd><a href="/gyogu/intro.jsp">소개</a></dd>
                      <dd><a href="/gyogu/msg_list.do?type=ALL">메시지</a></dd>
                      <dd><a href="/gyogu/par_list.do?b_idx=ALL">교구장동정</a></dd>
                      <dd><a href="/gyogu/ever_list.do?e_idx=1&b_idx=ALL&searchGubun=all">역대교구장</a></dd>
                  </dl>
              </li>
              <li>
                  <a href="/father/father_list.do?">사제단</a>
                  <dl>
                      <dt>사제단</dt>
                      <dd><a href="/father/father_list.do?">사제</a></dd>
                      <dd><a href="/father/holy_list.do?">선종사제</a></dd>
                  </dl>
              </li>
              <li>
                  <a href="/church/temp_01.do?qk=">본당</a>
                  <dl>
                      <dt>본당</dt>
                      <dd><a href="/church/temp_01.do?qk=">본당현황</a></dd>
                      <dd><a href="/church/church.do?qk=">지구별</a></dd>
                      <dd><a href="/church/vacancy.do?qk=">공소</a></dd>
                  </dl>
              </li>
              <li>
                  <a href="/news/news_list.do?b_idx=ALL">소식</a>
                  <dl>
                      <dt>소식</dt>
                      <dd><a href="/news/news_list.do?b_idx=ALL">교구소식</a></dd>
                      <dd><a href="/news/sch_list.do?gubuncd=1">교구일정</a></dd>
                      <dd><a href="/news/alb_list.do?b_idx=ALL&c_idx=ALL">교구앨범</a></dd>
                      <dd><a href="/news/mov_list.do?b_idx=23&searchGubun=all">교구영상</a></dd>
                      <dd><a href="/news/mgz_list.do?pub_idx=3">인천주보</a></dd>
                  </dl>
              </li>
              <li>
                  <a href="/samok/cure_list.do?b_idx=ALL">자료실</a>
                  <dl>
                      <dt>자료실</dt>
                      <dd><a href="http://bible.cbck.or.kr" target="_blank" title="새창">성경</a></dd>
                      <dd><a href="http://missa.cbck.or.kr/" target="_blank" title="새창">전례력&매일미사</a></dd>
                      <dd><a href="http://missale.cbck.or.kr/" target="_blank" title="새창">로마미사경본</a></dd>
                      <dd><a href="http://directory.cbck.or.kr/" target="_blank" title="새창">한국천주교주소록</a></dd>
                      <dd><a href="/samok/cure_list.do?c_idx=ALL">사목자료</a></dd>
                  </dl>
              </li>
              <li>
                  <a href="/community/doctrine.jsp">참여마당</a>
                  <dl>
                      <dt>참여마당</dt>
                      <dd><a href="/community/doctrine.jsp">통신교리 안내</a></dd>
                      <dd><a href="/community/cana_register.do">카나혼인강좌 &amp;<br> 약혼자 주말 신청</a></dd>
                      <dd><a href="/community/tale.do?">나누고 싶은 이야기</a></dd>
                  </dl>
              </li>
          </ul>
      </nav>
  </div>
  <!-- subBg -->
  <div class="subBg">서브배경</div>
  <div class="layerBg"><img src="img/dimmed_70.png" alt=""></div>
  <!-- //subBg -->
</header>


	<form name="form01" action="/news/alb_view.do" method="POST">
		<input type="hidden" name="idx" id="idx" value="${idx}" />
		<input type="hidden" name="idx" id="i_sBidx" value="${b_idx}" /> <input
			type="hidden" name="gubuncd" id="gubuncd" value="${gubuncd}" /> <input
			type="hidden" name="b_idx" id="b_idx" value="${b_idx}" /> <input
			type="hidden" name="bl_idx" id="bl_idx" value="" /> <input
			type="hidden" name="s_idx" id="s_idx" value="" /> <input
			type="text" name="todayContents" id="todayContents"
			value="${todayContents.H_DATE}" style="height:0; font-size:0; overflow:hidden;visibility:hidden; width:0; height:0;" />
	</form>
	<!-- 메뉴끝 -->
  
<!-- mainSection START -->  
<div class="section" id="wrap">
    <div class="secWrap" id="mainSection">
    <section class="sec01">
    <div class="containerbox">
      <!-- 왼쪽 -->
      <div class="col-xl-8 leftsection">
      <!-- 슬라이드 START -->
        <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
        <ol class="carousel-indicators">
          <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
          <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
          <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
        </ol>

        <div class="carousel-inner">
          <div class="carousel-item active">
            <a href="">
            <img src="img/holy1.jpeg" class="d-block slideimg" alt="...">
            </a>
          </div>
          <div class="carousel-item">
            <a href="">
            <img src="img/holy1.jpeg" class="d-block slideimg" alt="...">
          </a>
          </div>
          <div class="carousel-item">
            <a href="">
            <img src="img/holy1.jpeg" class="d-block slideimg" alt="...">
          </a>
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
        <!-- 슬라이드 END -->

		<!-- 오늘의소식 START -->
        <div class = "newsbox">
            <p class="news"><span>Today ㅣ ${todayContents.H_DATE} </span>
            ${todayContents.DESCRIPTION}</p>
        </div>
        <!-- 오늘의소식 END -->


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
                <!-- 
                  <li>
                    <em class="gongji">2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                  -->
				
                <c:choose>
				<c:when test="${fn:length(mboardList) > 0}">
					<c:set var="EXIST_BOARD" value="N" />
					<c:forEach items="${mboardList}" var="list" varStatus="status">
						<c:set var="gongji" value="" />
						<c:set var="none_gongji" value="" />
						<c:if test="${status.index > -1 and list.B_NM eq '교구소식'}">
							<c:set var="EXIST_BOARD" value="Y" />
							<c:if test="${list.IS_NOTICE eq 'Y' }">
								<c:set var="gongji" value="class='gongji'" />
							</c:if>
							<c:if test="${list.IS_NOTICE eq 'N' }">
								<c:set var="gongji" value="" />
								<c:if test="${list.ORG_NM ne ''}">
									<c:set var="none_gongji" value="[${list.ORG_NM }]" />
								</c:if>
							</c:if>
							
							<li onClick="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">
		                        <em ${gongji}>${list.TITLE}</em>
		                        <a hre="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">${fn:substring(list.REGDATE,5,10)}</a>
		                    </li>
		                </c:if>
					</c:forEach>
					<c:if test="${EXIST_BOARD eq 'N' }">
					  <li>
	                    <em class="gongji">등록된 데이터가 없습니다.</em>
	                    <a href="#">${NOW_MMDD}</a>
	                  </li>
					</c:if>
				</c:when>
				<c:otherwise>
				  <li>
                    <em class="gongji">등록된 데이터가 없습니다.</em>
                    <a href="#">${NOW_MMDD}</a>
                  </li>
				</c:otherwise>
				</c:choose>

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

                <ul class="infocontent"><!-- 
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                  -->
                
                <c:choose>
				<c:when test="${fn:length(mboardList) > 0}">
					<c:set var="EXIST_BOARD" value="N" />
					<c:forEach items="${mboardList}" var="list" varStatus="status">
						<c:set var="gongji" value="" />
						<c:set var="none_gongji" value="" />
						<c:if test="${status.index > -1 and list.B_NM eq '교회소식'}">
							<c:set var="EXIST_BOARD" value="Y" />
							<c:if test="${list.IS_NOTICE eq 'Y' }">
								<c:set var="gongji" value="class='gongji'" />
							</c:if>
							<c:if test="${list.IS_NOTICE eq 'N' }">
								<c:set var="gongji" value="" />
								<c:if test="${list.ORG_NM ne ''}">
									<c:set var="none_gongji" value="[${list.ORG_NM }]" />
								</c:if>
							</c:if>
							
							<li onClick="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">
		                        <em ${gongji}>${list.TITLE}</em>
		                        <a hre="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">${fn:substring(list.REGDATE,5,10)}</a>
		                    </li>
		                </c:if>
					</c:forEach>
					<c:if test="${EXIST_BOARD eq 'N' }">
					  <li>
	                    <em class="gongji">등록된 데이터가 없습니다.</em>
	                    <a href="#">${NOW_MMDD}</a>
	                  </li>
					</c:if>
				</c:when>
				<c:otherwise>
				  <li>
                    <em class="gongji">등록된 데이터가 없습니다.</em>
                    <a href="#">${NOW_MMDD}</a>
                  </li>
				</c:otherwise>
				</c:choose>
				
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
                <!-- 
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                  -->
                
                <c:choose>
				<c:when test="${fn:length(mboardList) > 0}">
					<c:set var="EXIST_BOARD" value="N" />
					<c:forEach items="${mboardList}" var="list" varStatus="status">
						<c:set var="gongji" value="" />
						<c:set var="none_gongji" value="" />
						<c:if test="${status.index > -1 and list.B_NM eq '본당소식'}">
							<c:set var="EXIST_BOARD" value="Y" />
							<c:if test="${list.IS_NOTICE eq 'Y' }">
								<c:set var="gongji" value="class='gongji'" />
							</c:if>
							<c:if test="${list.IS_NOTICE eq 'N' }">
								<c:set var="gongji" value="" />
								<c:if test="${list.ORG_NM ne ''}">
									<c:set var="none_gongji" value="[${list.ORG_NM }]" />
								</c:if>
							</c:if>
							
							<li><a href="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">
		                        <em ${gongji}>${list.TITLE}</em> ${list.REGDATE}</a>
		                    </li>
		                </c:if>
					</c:forEach>
					<c:if test="${EXIST_BOARD eq 'N' }">
					  <li>
	                    <em class="gongji">등록된 데이터가 없습니다.</em>
	                    <a href="#">${NOW_MMDD}</a>
	                  </li>
					</c:if>
				</c:when>
				<c:otherwise>
				  <li>
                    <em class="gongji">등록된 데이터가 없습니다.</em>
                    <a href="#">${NOW_MMDD}</a>
                  </li>
				</c:otherwise>
				</c:choose>
				
				
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



                <ul class="infocontent"><!-- 
                  <li>
                    <em>2020년 교구장 사목교서</em>
                    <a href="#">11/20</a>
                  </li>
                  -->
                
                <c:choose>
				<c:when test="${fn:length(mboardList) > 0}">
					<c:set var="EXIST_BOARD" value="N" />
					<c:forEach items="${mboardList}" var="list" varStatus="status">
						<c:set var="gongji"   value="" />
						<c:set var="none_gongji" value="" />
						<c:if test="${status.index > -1 and list.B_NM eq '공동체소식'}">
							<c:set var="EXIST_BOARD" value="Y" />
							<c:if test="${list.IS_NOTICE eq 'Y' }">
								<c:set var="gongji" value="class='gongji'" />
							</c:if>
							<c:if test="${list.IS_NOTICE eq 'N' }">
								<c:set var="gongji" value="" />
								<c:if test="${list.ORG_NM ne ''}">
									<c:set var="none_gongji" value="[${list.ORG_NM }]" />
								</c:if>
							</c:if>
							<li onClick="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">
		                        <em ${gongji}>${list.TITLE}</em>
		                        <a hre="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">${fn:substring(list.REGDATE,5,10)}</a>
		                    </li>
		                </c:if>
					</c:forEach>
					<c:if test="${EXIST_BOARD eq 'N' }">
					  <li>
	                    <em class="gongji">등록된 데이터가 없습니다.</em>
	                    <a href="#">${NOW_MMDD}</a>
	                  </li>
					</c:if>
				</c:when>
				</c:choose>
					
					
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
              <p class="innertitle">${todayContents.YYMM}월 ${todayContents.WEEK}주차 교구일정</p> <!-- 12월 4주 -->
              <div class="innerawesome">
                <a href="#"><i class="fas fa-chevron-left faw leftarw" style="color:#707070;"></i></a>
                <a href="#"><i class="fas fa-chevron-right faw rightarw" style="color:#707070;"></i></a>
              </div>
            </div>

            <div class="col-md-12">
              <hr class="dash">
            </div>
              <ul class="innercontent">
                <!-- <li>
                  <em>11/20</em>
                  <span>교구장</span>
                  <a href="#">가르멜 수도원 미사</a>
                </li>  5-rows -->
                
                
                <c:choose>
					<c:when test="${fn:length(ggScheduls) > 0}">
					<c:forEach items="${ggScheduls}" var="list">
						<li>
		                  <em>${ggScheduls.START_DATE}</em>
		                  <span>교구장</span>
		                  <a href="javascript: viewSchedule('${list.GUBUN}','${ggScheduls.B_IDX}','${ggScheduls.S_IDX}')">${ggScheduls.TITLE}</a>
		                </li>
					</c:forEach>
					</c:when>
					<c:otherwise>
					  <li>
	                    <em class="gongji">${NOW_MMDD}</em>
	                    <span>교구장</span>등록된 데이터가 없습니다.
	                  </li>
					</c:otherwise>
				</c:choose>

				
				
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
            <div class="firstcontent">
              <img src="img/holy2.jpg" class="gyoguimg">
              <p class="gyogutitle">일본 방문</p>
              <p class="gyogutext">교구장 정신철 요한 주교는 11월 24일부터 26일까지 일본을 방문하여 프란치스코 교황의 주례로 도쿄돔에서 봉헌된 장엄 미사에 참석하였다. </p>
            </div>
            <div class="col-md-12 gyoguline">
              <hr class="dash2">
            </div>
            <div class="secondcontent">
              <p class="gyogutitle">일본 방문</p>
              <p class="gyogutext">교구장 정신철 요한 주교는 11월 24일부터 26일까지 일본을 방문하여 프란치스코 교황의 주례로 도쿄돔에서 봉헌된 장엄 미사에 참석하였다. </p>
            </div>
            <div class="col-md-12 gyoguline">
              <hr class="dash2">
            </div>
            <div class="thirdcontent">
              <img src="img/holy2.jpg" class="gyoguimg">
              <p class="gyogutitle">일본 방문</p>
              <p class="gyogutext">교구장 정신철 요한 주교는 11월 24일부터 26일까지 일본을 방문하여 프란치스코 교황의 주례로 도쿄돔에서 봉헌된 장엄 미사에 참석하였다. </p>
            </div>
            <div class="col-md-12 gyoguline">
              <hr class="dash2">
            </div>
            <div class="fourthcontent">
              <img src="img/holy2.jpg" class="gyoguimg">
              <p class="gyogutitle">일본 방문</p>
              <p class="gyogutext">교구장 정신철 요한 주교는 11월 24일부터 26일까지 일본을 방문하여 프란치스코 교황의 주례로 도쿄돔에서 봉헌된 장엄 미사에 참석하였다. </p>
            </div>
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
                <!-- <li>
                  <img src="img/holy3.png" class="d-block light" alt="...">
                </li>
                <li>
                    <img src="img/holy3.png" class="d-block light" alt="...">
                </li>
                <li>
                    <img src="img/holy3.png" class="d-block light" alt="...">
                </li>
                <li>
                    <img src="img/holy3.png" class="d-block light" alt="...">
                </li>
                <li>
                    <img src="img/holy3.png" class="d-block light" alt="...">
                </li>
                <li>
                    <img src="img/holy3.png" class="d-block light" alt="...">
                </li>
                -->
                <c:choose>
				<c:when test="${fn:length(mboardList) > 0}">
					<c:forEach items="${mboardList}" var="list" varStatus="status">
						<c:if test="${status.index > -1 and list.B_NM eq '교구영상'}">
						<li onClick="javascript:viewAlb(${list.IDX})">
			              <img src="${list.FILEPATH}${list.STRFILENAME}" class="albumimg">
			              <p class="albumtxt">${list.TITLE}</p>
			              <p class="albumdate">${list.REGDATE}</p>
			            </li>
			            </c:if>
					</c:forEach>
				</c:when>
				</c:choose>
				
                <c:choose>
					<c:when test="${fn:length(movList) > 0}">
						<c:forEach items="${movList}" var="list" varStatus="status">
							<c:set var="YOUTUBE_THUMBNAIL_URL" value="/upload/gyogu_mov/${list.FILENAME}" />
							<c:if test="${list.YOUTUBE_THUMBNAIL_URL ne ''}">
								<c:set var="YOUTUBE_THUMBNAIL_URL" value="${list.YOUTUBE_THUMBNAIL_URL}" />
							</c:if>
							<c:if test="${status.count%3 eq 1}">
								<li class="big"><a
									href="javascript:viewMov('${list.B_IDX}','${list.BL_IDX}')"><img
										alt="" src="${YOUTUBE_THUMBNAIL_URL}" class="d-block light" ><span
										class="over"><img alt=""
											src="/img/dimmed_40.png"></span><i><span><img
												alt="" src="/img/main/ico/play.png"></span></i></a></li>
							</c:if>
							<c:if test="${status.count%3  eq 2}">
								<li class="sma"><a
									href="javascript:viewMov('${list.B_IDX}','${list.BL_IDX}')"><img
										alt="" src="${YOUTUBE_THUMBNAIL_URL}"><span
										class="over"><img alt=""
											src="/img/dimmed_40.png"></span><i><span><img
												alt="" src="/img/main/ico/play.png"></span></i></a></li>
							</c:if>
							<c:if test="${status.count%3 eq 0}">
								<li class="sma"><a
									href="javascript:viewMov('${list.B_IDX}','${list.BL_IDX}')"><img
										alt="" src="${YOUTUBE_THUMBNAIL_URL}"><span
										class="over"><img alt=""
											src="/img/dimmed_40.png"></span><i><span><img
												alt="" src="/img/main/ico/play.png"></span></i></a></li>
							</c:if>
						</c:forEach>
					</c:when>
				</c:choose>
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
                <a href="#"><i class="fas fa-plus" style="color:#707070;"></i></a>
              </div>
            </div>
          </div>





          <div class="col-md-12">
            <hr class="dash" style="margin-top:30.625px;">
          </div>

          <ul class="videocontent">
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
            </li>
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
              <button type="button" class="btn pdfbutton unavailable">듣기</button>
            </li>
            <li>
              <em>12/29</em>
              <span>예수, 마리아, 요셉의 성가절 축일</span>
              <button type="button" class="btn pdfbutton buttonmargin">PDF</button>
              <button type="button" class="btn pdfbutton unavailable">듣기</button>
            </li>
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
				<c:when test="${fn:length(mboardList) > 0}">
					<c:forEach items="${mboardList}" var="list" varStatus="status">
						<c:if test="${status.index > -1 and list.B_NM eq '교구앨범'}">
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
                  <img src="img/holy4.png" class="albumimg">
                  <p class="albumtxt">18기 선교사학교 수료 미사</p>
                  <p class="albumdate">19-12-10</p>
                </li>
                <li>
                  <img src="img/holy4.png" class="albumimg">
                  <p class="albumtxt">18기 선교사학교 수료 미사</p>
                  <p class="albumdate">19-12-10</p>
                </li>
                <li>
                  <img src="img/holy4.png" class="albumimg">
                  <p class="albumtxt">18기 선교사학교 수료 미사</p>
                  <p class="albumdate">19-12-10</p>
                </li> -->
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
				<c:when test="${fn:length(mboardList) > 0}">
					<c:forEach items="${mboardList}" var="list" varStatus="status">
						<c:if test="${status.index > -1 and list.B_NM eq '본당앨범'}">
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
                  <img src="img/holy4.png" class="albumimg">
                  <p class="albumtxt">18기 선교사학교 수료 미사</p>
                  <p class="albumdate">19-12-10</p>
                </li>
                <li>
                  <img src="img/holy4.png" class="albumimg">
                  <p class="albumtxt">18기 선교사학교 수료 미사</p>
                  <p class="albumdate">19-12-10</p>
                </li>
                <li>
                  <img src="img/holy4.png" class="albumimg">
                  <p class="albumtxt">18기 선교사학교 수료 미사</p>
                  <p class="albumdate">19-12-10</p>
                </li> -->
            </ul>
          </div>
        </div>
      </div>

      <div class="col-xl-12">
          <div class="iconbox">
            <div class="secondline text-center">
              <a class="icontag secondiconline sat"><img src="img/img6.png" class="icon"></a>
              <a class="icontag thirdiconline hana"><img src="img/img7.png" class="speicalicon"></a>
              <a class="icontag thirdiconline dul"><img src="img/img8.png" class="speicalicon"></a>
              <a class="icontag thirdiconline sat"><img src="img/img9.png" class="icon"></a>
            </div>
            <div class="firstline text-center">
              <a class="icontag firsticonline hana" href="#"><img src="img/img1.png" class="icon"></a>
              <a class="icontag firsticonline dul" href="#"><img src="img/img2.png" class="icon"></a>
              <a class="icontag firsticonline sat" href="#"><img src="img/img3.png" class="icon"></a>
              <a class="icontag secondiconline hana" href="#"><img src="img/img4.png" class="icon"></a>
              <a class="icontag secondiconline dul" href="#"><img src="img/img5.png" class="icon"></a>
            </div>
          </div>
      </div>

      <div class="col-xl-12 topmargin">
        <div class="card bg-dark text-white">
          <img class="card-img cardimg" src="img/holycard.jpg" alt="Card image">
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
                    <a class="dropdown-item" href="#">청라3동</a>
                  </div>
                </div>
                <div class="dropdown show div-inline">
                  <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    시흥안산지구
                  </a>
                  <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                    <a class="dropdown-item" href="#">대부</a>
                    <a class="dropdown-item" href="#">대야동</a>
                    <a class="dropdown-item" href="#">도창동</a>
                    <a class="dropdown-item" href="#">신천</a>
                    <a class="dropdown-item" href="#">영흥</a>
                    <a class="dropdown-item" href="#">은계</a>
                    <a class="dropdown-item" href="#">은행동</a>
                    <a class="dropdown-item" href="#">포동</a>
                  </div>
                </div>
                <div class="dropdown show div-inline">
                  <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    연수지구
                  </a>
                  <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                    <a class="dropdown-item" href="#">동춘동</a>
                    <a class="dropdown-item" href="#">선학동</a>
                    <a class="dropdown-item" href="#">송도2동</a>
                    <a class="dropdown-item" href="#">연수</a>
                    <a class="dropdown-item" href="#">옥련동 (구 송도)</a>
                    <a class="dropdown-item" href="#">인천가톨릭대학교</a>
                    <a class="dropdown-item" href="#">한국순교성인청학동</a>
                  </div>
                </div>
                <div class="dropdown show div-inline">
                  <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    중동구지구
                  </a>
                  <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                    <a class="dropdown-item" href="#">답동주교좌</a>
                    <a class="dropdown-item" href="#">대청도 (준)</a>
                    <a class="dropdown-item" href="#">덕적도</a>
                    <a class="dropdown-item" href="#">백령</a>
                    <a class="dropdown-item" href="#">송림4동</a>
                    <a class="dropdown-item" href="#">송림동</a>
                    <a class="dropdown-item" href="#">송현동</a>
                    <a class="dropdown-item" href="#">신공항</a>
                    <a class="dropdown-item" href="#">신도 (준)</a>
                    <a class="dropdown-item" href="#">연안</a>
                    <a class="dropdown-item" href="#">연평도</a>
                    <a class="dropdown-item" href="#">영종</a>
                    <a class="dropdown-item" href="#">용유 (준)</a>
                    <a class="dropdown-item" href="#">해안</a>
                    <a class="dropdown-item" href="#">화수동</a>
                  </div>
                </div>
                <div class="dropdown show div-inline">
                  <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    특수
                  </a>
                  <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                    <a class="dropdown-item" href="#">청언</a>

                  </div>
                </div>
              </div>
              <div class="text-center dongne">
                  <div class="dropdown show div-inline">
                    <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      강화지구
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                      <a class="dropdown-item" href="#">강화</a>
                      <a class="dropdown-item" href="#">내가</a>
                      <a class="dropdown-item" href="#">마니산 (준)</a>
                      <a class="dropdown-item" href="#">온수</a>
                      <a class="dropdown-item" href="#">하점</a>
                    </div>
                  </div>

                  <div class="dropdown show div-inline">
                    <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      김포지구
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                      <a class="dropdown-item" href="#">고춘</a>
                      <a class="dropdown-item" href="#">김포</a>
                      <a class="dropdown-item" href="#">대곶</a>
                      <a class="dropdown-item" href="#">마산동</a>
                      <a class="dropdown-item" href="#">불로동</a>
                      <a class="dropdown-item" href="#">사우2동</a>
                      <a class="dropdown-item" href="#">사우동</a>
                      <a class="dropdown-item" href="#">양곡</a>
                      <a class="dropdown-item" href="#">운양동</a>
                      <a class="dropdown-item" href="#">청수</a>
                      <a class="dropdown-item" href="#">통진</a>
                      <a class="dropdown-item" href="#">풍무동</a>
                      <a class="dropdown-item" href="#">하성</a>
                    </div>
                  </div>

                  <div class="dropdown show div-inline">
                    <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      계양지구
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                      <a class="dropdown-item" href="#">계산동</a>
                      <a class="dropdown-item" href="#">박촌동</a>
                      <a class="dropdown-item" href="#">서운동</a>
                      <a class="dropdown-item" href="#">작전2동 (구 계산1동)</a>
                      <a class="dropdown-item" href="#">작전동</a>
                    </div>
                  </div>
                  <div class="dropdown show div-inline">
                    <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      미주홀지구
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                      <a class="dropdown-item" href="#">도화동</a>
                      <a class="dropdown-item" href="#">숭의동</a>
                      <a class="dropdown-item" href="#">용현5동</a>
                      <a class="dropdown-item" href="#">용현동</a>
                      <a class="dropdown-item" href="#">제물포</a>
                      <a class="dropdown-item" href="#">주안1동</a>
                      <a class="dropdown-item" href="#">주안3동</a>
                      <a class="dropdown-item" href="#">주안5동</a>
                      <a class="dropdown-item" href="#">주안8동</a>
                      <a class="dropdown-item" href="#">학익동</a>
                    </div>
                  </div>
                  <div class="dropdown show div-inline">
                    <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      남동지구
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                      <a class="dropdown-item" href="#">간석2동</a>
                      <a class="dropdown-item" href="#">간석4동</a>
                      <a class="dropdown-item" href="#">고잔</a>
                      <a class="dropdown-item" href="#">구월1동</a>
                      <a class="dropdown-item" href="#">구월동</a>
                      <a class="dropdown-item" href="#">남촌동</a>
                      <a class="dropdown-item" href="#">논현동</a>
                      <a class="dropdown-item" href="#">만수1동</a>
                      <a class="dropdown-item" href="#">만수2동</a>
                      <a class="dropdown-item" href="#">만수3동</a>
                      <a class="dropdown-item" href="#">만수6동</a>
                      <a class="dropdown-item" href="#">모래내</a>
                      <a class="dropdown-item" href="#">서창2동</a>
                      <a class="dropdown-item" href="#">서창동</a>
                      <a class="dropdown-item" href="#">소래포구 (구 논현1동)</a>
                    </div>
                  </div>
                  <div class="dropdown show div-inline">
                    <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      부평지구
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                      <a class="dropdown-item" href="#">갈산동</a>
                      <a class="dropdown-item" href="#">부개2동</a>
                      <a class="dropdown-item" href="#">부개동</a>
                      <a class="dropdown-item" href="#">부평1동</a>
                      <a class="dropdown-item" href="#">부평2동</a>
                      <a class="dropdown-item" href="#">부평2동</a>
                      <a class="dropdown-item" href="#">부평4동</a>
                      <a class="dropdown-item" href="#">산곡3동</a>
                      <a class="dropdown-item" href="#">산곡동</a>
                      <a class="dropdown-item" href="#">삼산동</a>
                      <a class="dropdown-item" href="#">십정동</a>
                      <a class="dropdown-item" href="#">일신동</a>
                    </div>
                  </div>
                  <div class="dropdown show div-inline">
                    <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      부천1지구
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                      <a class="dropdown-item" href="#">범박동</a>
                      <a class="dropdown-item" href="#">상동</a>
                      <a class="dropdown-item" href="#">소사</a>
                      <a class="dropdown-item" href="#">소사본3동</a>
                      <a class="dropdown-item" href="#">소사본동</a>
                      <a class="dropdown-item" href="#">송내1동</a>
                      <a class="dropdown-item" href="#">심곡</a>
                      <a class="dropdown-item" href="#">심곡본동</a>
                      <a class="dropdown-item" href="#">역곡</a>
                      <a class="dropdown-item" href="#">역곡2동</a>
                      <a class="dropdown-item" href="#">옥길동</a>
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
                    <img class="card-img holyplace" src="img/holy7.png" alt="Card image">
                    <div class="card-img-overlay d-flex flex-column justify-content-end">
                      <a class="holytitle" href="/intro/shirine_03.jsp">제물진두 순교성지</a>
                      <a class="holytext" href="/intro/shirine_03.jsp">하느님께서 순교자들을 감싸는 두 손 모양을 형상화 한 순교성지</a>
                      <div class="text-center dongne">
                      </div>
                    </div>
                  </div>
                </li>
                <li>
                  <div class="card bg-dark text-white">
                    <img class="card-img holyplace" src="img/holy7.png" alt="Card image">
                    <div class="card-img-overlay d-flex flex-column justify-content-end">
                      <a class="holytitle" href="/intro/shirine_05.jsp">일만위 순교자 현양동산</a>
                      <a class="holytext" href="/intro/shirine_05.jsp">크고 많은 역사적 의미와 문화적 유산을<br> 간직하고 있는 	역사의 보고</a>
                      <div class="text-center dongne">
                      </div>
                    </div>
                  </div>
                </li>
                <li>
                  <div class="card bg-dark text-white">
                    <img class="card-img holyplace" src="img/holy7.png" alt="Card image">
                    <div class="card-img-overlay d-flex flex-column justify-content-end">
                      <a class="holytitle" href="/intro/shirine.jsp">갑곶순교성지</a>
                      <a class="holytext" href="/intro/shirine.jsp">순교자들의 행적증거자 박순집 베드로</a>
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
                <a class="dropdown-item" href="http://www.catholic.or.kr">굳뉴스</a>
              </div>
            </div>
        </div>

        <div class="col-xl-2dot4">
            <div class="dropdown show">
              <a class="btn btn-secondary dropdown-toggle holybutton" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                타교구
              </a>
              <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <a class="dropdown-item" href="http://aos.catholic.or.kr/">서울대교구</a>
                <a class="dropdown-item" href="http://www.casuwon.or.kr/">수원교구</a>
                <a class="dropdown-item" href="http://www.catholicbusan.or.kr/">부산교구</a>
                <a class="dropdown-item" href="http://www.gjcatholic.or.kr/">광주대교구</a>
                <a class="dropdown-item" href="http://www.cccatholic.or.kr/">춘천교구</a>
                <a class="dropdown-item" href="http://www.wjcatholic.or.kr/">원주교구</a>
                <a class="dropdown-item" href="http://cdcj.or.kr/">청주교구</a>
                <a class="dropdown-item" href="http://www.jcatholic.or.kr/">전주교구</a>
                <a class="dropdown-item" href="http://www.djcatholic.or.kr/">대전교구</a>
                <a class="dropdown-item" href="http://www.ucatholic.or.kr/">의정부교구</a>
                <a class="dropdown-item" href="http://cathms.kr/">마산교구</a>
                <a class="dropdown-item" href="http://www.diocesejeju.or.kr/">제주교구</a>
                <a class="dropdown-item" href="http://www.daegu-archdiocese.or.kr/">대구대교구</a>
                <a class="dropdown-item" href="http://www.acatholic.or.kr/">안동교구</a>
                <a class="dropdown-item" href="http://www.gunjong.or.kr/">군종교구</a>
              </div>
            </div>
        </div>

        <div class="col-xl-2dot4">
            <div class="dropdown show">
              <a class="btn btn-secondary dropdown-toggle holybutton" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                부서
              </a>
              <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <a class="dropdown-item" href="http://youth.caincheon.or.kr/">청소년사목국</a>
                <a class="dropdown-item" href="http://www.inchung.net/">청년부</a>
                <a class="dropdown-item" href="http://www.sungso.net/">성소국</a>
              </div>
            </div>
        </div>

        <div class="col-xl-2dot4">
            <div class="dropdown show">
              <a class="btn btn-secondary dropdown-toggle holybutton" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                단체
              </a>
              <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <a class="dropdown-item" href="http://www.legiom.com/">바다의별 레지아</a>
                <a class="dropdown-item" href="http://www.incheonme.net/">인천ME</a>
                <a class="dropdown-item" href="http://www.cen.or.kr/">가톨릭환경연대</a>
              </div>
            </div>
        </div>

        <div class="col-xl-2dot4 lastholybutton">
            <div class="dropdown show">
              <a class="btn btn-secondary dropdown-toggle holybutton" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                기관
              </a>
              <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <a class="dropdown-item" href="https://www.iccu.ac.kr/">인천가톨릭대학교</a>
                <a class="dropdown-item" href="http://www.cku.ac.kr/">가톨릭관동대학교</a>
                <a class="dropdown-item" href="http://www.caritasincheon.or.kr/">인천가톨릭사회복지회</a>
                <a class="dropdown-item" href="http://www.yism.or.kr/">가톨릭아동청소년재단</a>
              </div>
            </div>
        </div>
      </div>
  </div>





	<footer>
		<div id="footer_gotop" class="goTop">
		TOP
		</div>
	</footer>

</section>
</div>
</div>
<!-- mainSection START -->



<div class="footersection">
  <div class="col-xl-12">
    <div class="footercontainer">
      <div class="innerfooter">
          <div class="col-xl-6">
            <img src="img/main_logo.png" class="mr-auto footermain">
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



<script type="text/javascript">
window.onload = function() {
	/* 스크롤바가 전체 화면에서 사라지는 현상을 막기 위해서 강제처리 :: 2018-03-15 */
	if($(window).height() < $(document).height()) {
		var sh = $(document).prop("scrollHeight");
		var ch = $(document).prop("clientHeight");
		if( (sh==0 && ch==0) || (sh>ch) ) {
		//$("body").css("overflow","scroll");
		}
	}

	// if error happend
	setTimeout(function(){
			    var msg = "";
			    if(msg.length > 0)
			      alert(msg);
			  }, 500);
};
</script>
<script type="text/javascript">
function attachedFile_Download(filePath, fileName, strfileName) {
	if(fileName=="" || strfileName=="") {
		alert("다운로드 할 파일이 없습니다.");
	} else {
		var vfm = document.attachedFileDown;
		vfm.filePath.value    = filePath;
		vfm.fileName.value    = fileName;
		vfm.strfileName.value = strfileName;
		vfm.submit();
		return false;
	}
}
</script>
<!-- attached file download -->
<form  name="attachedFileDown" action="http://www.caincheon.or.kr/filedownload.jsp" method="POST" target="ifmAttachedFileDown">
	<input type="hidden" name="fileName" id="fileName" value=""/>
	<input type="hidden" name="filePath" id="filePath" value=""/>
	<input type="hidden" name="strfileName" id="strfileName" value=""/>
</form>
<iframe name="ifmAttachedFileDown" id="ifmAttachedFileDown" width=0 height=0 src="about:blank"></iframe>



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
    <script src="js/bootstrap.js"></script>
  </body>
</html>
>>>>>>> branch 'master' of https://github.com/benjaminhds/caincheon
