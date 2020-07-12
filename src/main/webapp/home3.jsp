<%@ page session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%@ 
	include file="/_common/inc/head_const.jsp" %><%@ 
	include file="/_common/inc/loginHandler.jsp" %>
<html lang="en">
  <head>
  	<!-- Global site tag (gtag.js) - Google Analytics -->
	<script async src="https://www.googletagmanager.com/gtag/js?id=UA-136134881-1"></script>
	<script>
	  window.dataLayer = window.dataLayer || [];
	  function gtag(){dataLayer.push(arguments);}
	  gtag('js', new Date());
	
	  gtag('config', 'UA-136134881-1');
	</script>

    <!-- Required meta tags -->
    <meta charset="utf-8">
    
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
	<title>천주교 인천교구</title>
	
	<!-- jquery -->
	<script src="/_common/js/jquery-1.12.4.min.js"></script>
	<script src="/_common/js/plugins.js"></script>
	<script src="/_common/js/jquery.fullpage.js"></script>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="_common3/css/bootstrap.css">
    <link rel="stylesheet" href="_common3/css/personal.css">
    <!-- fontawesome -->
    <script src="https://kit.fontawesome.com/99b6be88e2.js" crossorigin="anonymous"></script>
    <!--fontfile-->
    <link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR&display=swap" rel="stylesheet">
    
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.5.0/js/swiper.min.js"></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.4.6/css/swiper.min.css">
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
  </head>
  <body>
  
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
        <img src="_common3/img/main_logo2.png" class="mainlogo">
      </a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav fontmenu mx-auto">
          <li class="nav-item dropdown mainmenu">
                  <a class="nav-link dropdown-toggle active" href="/intro/intro.jsp" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    교구안내
                  </a>
                  <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <ul class="list-inline">
                      <li><a class="dropdown-item" href="/intro/intro.jsp">교구소개</a></li>
                      <li><a class="dropdown-item" href="/intro/history.do">온라인역사관</a></li>
                      <li><a class="dropdown-item" href="/intro/shirine.jsp">교구성지</a></li>
                      <li><a class="dropdown-item" href="/intro/diocesan.do">교구청</a></li>
                      <li><a class="dropdown-item" href="/intro/ordo_list.do?lv1=08">수도회</a></li>
                      <li><a class="dropdown-item" href="/intro/org_list.do?t=1&lv1=05">기관/단체</a></li>
                    </ul>
                  </ul>
            </li>
            <li class="nav-item dropdown mainmenu">
                    <a class="nav-link dropdown-toggle active" href="/gyogu/intro.jsp" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      교구장
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                      <ul class="list-inline">
                        <li><a class="dropdown-item" href="/gyogu/intro.jsp">소개</a></li>
                        <li><a class="dropdown-item" href="/gyogu/msg_list.do?type=ALL">메시지</a></li>
                        <li><a class="dropdown-item" href="/gyogu/par_list.do?b_idx=ALL">교구장동정</a></li>
                        <li><a class="dropdown-item" href="/gyogu/ever_list.do?e_idx=1&b_idx=ALL&searchGubun=all">역대교구</a></li>
                      </ul>
                    </ul>
              </li>
              <li class="nav-item dropdown mainmenu">
                      <a class="nav-link dropdown-toggle active" href="/father/father_list.do" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        사제단
                      </a>
                      <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <ul class="list-inline">
                          <li><a class="dropdown-item" href="/father/father_list.do">사제</a></li>
                          <li><a class="dropdown-item" href="/father/holy_list.do">선종사제</a></li>
                        </ul>
                      </ul>
                </li>
                <li class="nav-item dropdown mainmenu">
                        <a class="nav-link dropdown-toggle active" href="/church/temp_01.do?qk=" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                          본당
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                          <ul class="list-inline">
                            <li><a class="dropdown-item" href="/church/temp_01.do?qk=">본당현황</a></li>
                            <li><a class="dropdown-item" href="/church/church.do?qk=">지구별</a></li>
                            <li><a class="dropdown-item" href="/church/vacancy.do?qk=">공소</a></li>
                          </ul>
                        </ul>
                  </li>
                  <li class="nav-item dropdown mainmenu">
                          <a class="nav-link dropdown-toggle active" href="/news/news_list.do?b_idx=ALL" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            소식
                          </a>
                          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <ul class="list-inline">
                              <li><a class="dropdown-item" href="/news/news_list.do?b_idx=ALL">교구소식</a></li>
                              <li><a class="dropdown-item" href="/news/sch_list.do?gubuncd=1">교구일정</a></li>
                              <li><a class="dropdown-item" href="/news/alb_list.do?b_idx=ALL&c_idx=ALL">교구앨범</a></li>
                              <li><a class="dropdown-item" href="/news/mov_list.do?b_idx=23&searchGubun=all">교구영상</a></li>
                              <li><a class="dropdown-item" href="/news/mgz_list.do?pub_idx=3">인천주보</a></li>
                            </ul>
                          </ul>
                    </li>
                    <li class="nav-item dropdown mainmenu">
                            <a class="nav-link dropdown-toggle active" href="/samok/cure_list.do?b_idx=ALL" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                              자료실
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                              <ul class="list-inline">
                                <li><a class="dropdown-item" href="http://bible.cbck.or.kr">성경</a></li>
                                <li><a class="dropdown-item" href="http://missa.cbck.or.kr/">전례력&매일미사</a></li>
                                <li><a class="dropdown-item" href="http://missale.cbck.or.kr/">로마미사경본</a></li>
                                <li><a class="dropdown-item" href="http://directory.cbck.or.kr/">한국천주교주소록</a></li>
                                <li><a class="dropdown-item" href="/samok/cure_list.do?c_idx=ALL">사목자료</a></li>
                              </ul>
                            </ul>
                      </li>
                      <li class="nav-item dropdown mainmenu">
                              <a class="nav-link dropdown-toggle active" href="/community/doctrine.jsp" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                참여마당
                              </a>
                              <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <ul class="list-inline">
                                  <li><a class="dropdown-item" href="/community/doctrine.jsp">통신교리 안내</a></li>
                                  <li><a class="dropdown-item" href="/community/cana_register.do">카나혼인강좌 & 약혼자 주말 신청</a></li>
                                  <li><a class="dropdown-item" href="/community/tale.do?">나누고 싶은 이야기</a></li>
                                </ul>
                              </ul>
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
            <img src="_common3/img/holy1.jpeg" class="d-block slideimg" alt="...">
          </div>
          <div class="carousel-item">
            <img src="_common3/img/holy1.jpeg" class="d-block slideimg" alt="...">
          </div>
          <div class="carousel-item">
            <img src="_common3/img/holy1.jpeg" class="d-block slideimg" alt="...">
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
              <div class="innerbox">
                <p class="innertitle">교구소식</p>
                  <a href="#"><img src=_common3/img/plus.png class="icons vertical-align:middle;"></a>
              </div>

              <div class="col-md-12">
                <hr class="dash">
              </div>
                <ul class="infocontent">
                <c:choose>
					<c:when test="${fn:length(parishList) > 0}">
						<c:forEach items="${parishList}" var="list" varStatus="status">
						  <li>
		                    <em>
		                    <c:if test="${list.GUBUN eq '1' }"><font color="red">*</font></c:if>
		                    ${fn:substring(list.TITLE,0,26)}<c:if test="${fn:length(list.TITLE)>26}">..</c:if>
		                    </em>
		                    <a href="#" href="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">${list.REGDATE}</a>
		                  </li>
						</c:forEach>
					</c:when>
				</c:choose>
                </ul>
            </div>
          </div>
          <!-- 교회소식 -->
          <div class = "col-md-6 rightbox">
            <div class="box">
              <div class="innerbox">
                <p class="innertitle">교회소식</p>
                <a href="#"><img src=_common3/img/plus.png class="icons vertical-align:middle;"></a>
              </div>

              <div class="col-md-12">
                <hr class="dash">
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
		                    <a href="#" href="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">${list.REGDATE}</a>
		                  </li>
						</c:forEach>
					</c:when>
				</c:choose>
                </ul>
            </div>
          </div>
        </div>

        <!-- 본당소식 -->
        <div class = "nomarginbox">
          <div class = "col-md-6 leftbox">
            <div class="box">
              <div class="innerbox">
                <p class="innertitle">본당소식</p>
                <a href="#"><img src=_common3/img/plus.png class="icons vertical-align:middle;"></a>
              </div>

              <div class="col-md-12">
                <hr class="dash">
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
		                    <a href="#" href="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">${list.REGDATE}</a>
		                  </li>
						</c:forEach>
					</c:when>
				</c:choose>
                </ul>
            </div>
          </div>
          <!-- 공동체 소식 -->
          <div class = "col-md-6 rightbox">
            <div class="box">
              <div class="innerbox">
                <p class="innertitle">공동체소식</p>
                <a href="#"><img src=_common3/img/plus.png class="icons vertical-align:middle;"></a>
              </div>

              <div class="col-md-12">
                <hr class="dash">
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
		                    <a href="#" href="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">${list.REGDATE}</a>
		                  </li>
						</c:forEach>
					</c:when>
				</c:choose>
                </ul>
            </div>
          </div>
        </div>

      </div>
      <!-- 오른쪽-->
      <div class="col-xl-4 rightsection ipad">
        <div class="box">
          <div class="innerbox">
            <p class="innertitle">12월 4주차 교구일정</p>
            <div class="innerawesome">
              <a href="#" onclick="changeGyoguMonthlySchedule(-1)"><i class="fas fa-chevron-left faw leftarw" style="color:#707070;"></i></a>
              <a href="#" onclick="changeGyoguMonthlySchedule( 1)"><i class="fas fa-chevron-right faw rightarw" style="color:#707070;"></i></a>
            </div>
          </div>

          <div class="col-md-12">
            <hr class="dash">
          </div>

            <ul class="innercontent" id="gyogu_monthly_schedule">
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
			  </c:choose>
              <li>
                <em>11/20</em>
                <span>교구장</span>
                <a href="#">가르멜 수도원 미사</a>
              </li>
            </ul>
        </div>

        <div class="gyogubox">
          <div class="innerbox">
            <p class="innertitle">교구장 동정</p>
            <div class="innerawesome">
              <a href="#"><img src=_common3/img/plus.png class="icons vertical-align:middle;"></a>
            </div>
          </div>

          <div class="col-md-12">
            <hr class="dash">
          </div>

          <div class="gyogucontent">
            <div class="firstcontent">
              <img src="_common3/img/holy2.jpg" class="gyoguimg">
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
              <img src="_common3/img/holy2.jpg" class="gyoguimg">
              <p class="gyogutitle">일본 방문</p>
              <p class="gyogutext">교구장 정신철 요한 주교는 11월 24일부터 26일까지 일본을 방문하여 프란치스코 교황의 주례로 도쿄돔에서 봉헌된 장엄 미사에 참석하였다. </p>
            </div>
            <div class="col-md-12 gyoguline">
              <hr class="dash2">
            </div>
            <div class="fourthcontent">
              <img src="_common3/img/holy2.jpg" class="gyoguimg">
              <p class="gyogutitle">일본 방문</p>
              <p class="gyogutext">교구장 정신철 요한 주교는 11월 24일부터 26일까지 일본을 방문하여 프란치스코 교황의 주례로 도쿄돔에서 봉헌된 장엄 미사에 참석하였다. </p>
            </div>
          </div>



        </div>

      </div>

      <div class="col-md-6 leftsection">
        <div class="videobox">
          <div class="innerbox">
            <p class="innertitle">교구영상</p>
            <div class="innerawesome">
              <a href="#"><i class="fas fa-chevron-left faw leftarw" style="color:#707070;"></i></a>
              <a href="#"><i class="fas fa-chevron-right faw rightarw" style="color:#707070;"></i></a>
            </div>
          </div>

          <div class="col-md-12">
            <hr class="dash">
          </div>

          <div class="video">
          	<c:choose>
			<c:when test="${fn:length(movList) > 0}">
				<c:forEach items="${movList}" var="list" varStatus="status">
					<c:set var="YOUTUBE_THUMBNAIL_URL" value="/upload/gyogu_mov/${list.FILENAME}" />
					<c:if test="${list.YOUTUBE_THUMBNAIL_URL ne ''}">
						<c:set var="YOUTUBE_THUMBNAIL_URL" value="${list.YOUTUBE_THUMBNAIL_URL}" />
					</c:if>
					
					<c:if test="${status.index%2  eq 0 && status.index == 0}">
						<div class="col-xl-6 left">
			              <img src="${YOUTUBE_THUMBNAIL_URL}" class="videoimg">
			            </div>
					</c:if>
					
					<c:if test="${status.index%2 eq 1 && status.index == 1}">
						<div class="col-xl-6 right">
			              <img src="${YOUTUBE_THUMBNAIL_URL}" class="videoimg">
			            </div>
					</c:if>
					
				</c:forEach>
			</c:when>
			</c:choose>
          </div>
        </div>
      </div>

      <div class="col-md-6 rightsection">
        <div class="videobox">
          <div class="innerbox">
            <p class="innertitle">인천주보</p>
            <a href="#"><img src=_common3/img/plus.png class="icons vertical-align:middle;"></a>
          </div>

          <div class="col-md-12">
            <hr class="dash">
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
		  </c:choose>
		
          </ul>
        </div>
      </div>

      <div class="col-md-6 leftsection">
        <div class="videobox">
          <div class="innerbox">
            <p class="innertitle">교구앨범</p>
            <div class="innerawesome">
              <a href="#"><i class="fas fa-chevron-left faw leftarw" style="color:#707070;"></i></a>
              <a href="#"><i class="fas fa-chevron-right faw rightarw" style="color:#707070;"></i></a>
            </div>
          </div>

          <div class="col-md-12">
            <hr class="dash">
          </div>

          <div class="video">
			<c:choose>
			<c:when test="${fn:length(albList) > 0}">
				<c:forEach items="${albList}" var="list" varStatus="status">
					<c:if test="${status.index < 2}">
					<div class="col-xl-6 right" onClick="javascript:viewAlb(${list.IDX})">
		              <img src="${list.FILEPATH}${list.STRFILENAME}" class="albumimg">
		              <p class="albumtxt">${list.TITLE}</p>
		              <p class="albumdate">${list.TITLE}</p>
		            </div>
		            </c:if>
				</c:forEach>
			</c:when>
			</c:choose>
			
          </div>
        </div>
      </div>

      <div class="col-md-6 rightsection">
        <div class="videobox">
          <div class="innerbox">
            <p class="innertitle">본당앨범</p>
            <div class="innerawesome">
              <a href="#"><i class="fas fa-chevron-left faw leftarw" style="color:#707070;"></i></a>
              <a href="#"><i class="fas fa-chevron-right faw rightarw" style="color:#707070;"></i></a>
            </div>
          </div>

          <div class="col-md-12">
            <hr class="dash">
          </div>

          <div class="video">
            <div class="col-xl-6 right">
              <img src="_common3/img/holy4.png" class="albumimg">
              <p class="albumtxt">18기 선교사학교 수료 미사</p>
              <p class="albumdate">19-12-10</p>
            </div>
            <div class="col-xl-6 left">
              <img src="_common3/img/holy4.png" class="albumimg">
              <p class="albumtxt">18기 선교사학교 수료 미사</p>
              <p class="albumdate">19-12-10</p>
            </div>
          </div>
        </div>
      </div>

      <div class="col-xl-12">
          <div class="iconbox">
            <div class="firstline text-center">
              <a class="icontag firsticonline hana" href="/gyogu/msg_view_recently.do" title="사목교서"><img src="_common3/img/img1.png" class="icon"></a>
              <a class="icontag firsticonline dul" href="/samok/cure_list.do?c_idx=ALL" title="사목자료"><img src="_common3/img/img2.png" class="icon"></a>
              <a class="icontag firsticonline sat" href="/church/temp_01.do?qk=" title="미사시간"><img src="_common3/img/img3.png" class="icon"></a>
              <a class="icontag secondiconline hana" href="/intro/intro_03.jsp" title="관할구역"><img src="_common3/img/img4.png" class="icon"></a>
              <a class="icontag secondiconline dul" href="/community/doctrine.jsp" title="통신교리신청"><img src="_common3/img/img5.png" class="icon"></a>
            </div>

            <div class="secondline text-center">
              <a class="icontag secondiconline sat" href="/news/mgz_list.do?pub_idx=3" title="인천주보"><img src="_common3/img/img6.png" class="icon"></a>
              <a class="icontag thirdiconline hana" title="문한림 주교 강론 모음"><img src="_common3/img/img7.png" class="speicalicon"></a>
              <a class="icontag thirdiconline dul" href="/community/cana.do" title="카나혼인강좌 & 약혼자 주말신청"><img src="_common3/img/img8.png" class="speicalicon"></a>
              <a class="icontag thirdiconline sat" href="/intro/diocesan_12.do#MarriageAndFaith" title="혼인과 신앙"><img src="_common3/img/img9.png" class="icon"></a>
            </div>
          </div>
      </div>

      <div class="col-xl-12 topmargin">
        <div class="card bg-dark text-white">
          <img class="card-img cardimg" src="_common3/img/holycard.jpg" alt="Card image">
          <div class="card-img-overlay d-flex flex-column">
            <div class="text-center">
              <p class="bondangtitle">본당 찾기</p>
              <p class="bondangtext">아름다운 인천교구 지구별 본당을 찾아보세요.<br>하단을 클릭하시면 해당 지구별 본당 페이지로 이동합니다.</p>
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

              <div class="seconddongne text-center">
                <div class="dropdown show div-inline">
                  <a class="btn btn-light dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    부천2지구
                  </a>
                  <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
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
                    <a class="dropdown-item" href="#">중1동</a>
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
          </div>
        </div>
    </div>

    <div class="col-xl-12">
        <div class="holybox">
          <div class="innerbox">
            <p class="holyinnertitle">교구성지</p>
            <div class="innerawesome">
              <a href="#"><i class="fas fa-chevron-left faw leftarw" style="color:#707070;"></i></a>
              <a href="#"><i class="fas fa-chevron-right faw rightarw" style="color:#707070;"></i></a>
            </div>
          </div>

          <div class="col-md-12">
            <hr class="dash">
          </div>

          <div class="video">
            <div class="col-md-4 leftimg">
              <div class="card bg-dark text-white">
                <img class="card-img holyplace" src="_common3/img/holy7.png" alt="Card image">
                <div class="card-img-overlay d-flex flex-column justify-content-end">
                  <p class="holytitle">제물진두 순교성지</p>
                  <p class="holytext">하느님께서 순교자들을 감싸는 두 손 모양을 형상화 한 순교성지</p>
                  <div class="text-center dongne">
                  </div>
                </div>
              </div>
            </div>

            <div class="col-md-4">
              <div class="card bg-dark text-white">
                <img class="card-img holyplace" src="_common3/img/holy8.png" alt="Card image">
                <div class="card-img-overlay d-flex flex-column justify-content-end">
                  <p class="holytitle">일만위 순교자 현양동산</p>
                  <p class="holytext">크고 많은 역사적 의미와 문화적 유산을 간직하고 있는 역사의 보고</p>
                  <div class="text-center dongne">
                  </div>
                </div>
              </div>
            </div>

            <div class="col-md-4 rightimg">
              <div class="card bg-dark text-white">
                <img class="card-img holyplace" src="_common3/img/holy9.jpg" alt="Card image">
                <div class="card-img-overlay d-flex flex-column justify-content-end">
                  <p class="holytitle">갑곶 순교성지</p>
                  <p class="holytext">선교사들의 행적 증거자 박순집 베드로</p>
                  <div class="text-center dongne">
                  </div>
                </div>
              </div>
            </div>
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
                <a class="dropdown-item" href="http://www.cbck.or.kr" target="_blank">주교회의</a>
                <a class="dropdown-item" href="http://www.catholic.or.kr" target="_blank">굳뉴스</a>
              </div>
            </div>
        </div>

        <div class="col-xl-2dot4">
            <div class="dropdown show">
              <a class="btn btn-secondary dropdown-toggle holybutton" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                타교구
              </a>
              <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <a class="dropdown-item" href="http://aos.catholic.or.kr/" target="_blank" title="새창">서울대교구</a>
				<a class="dropdown-item" href="http://www.casuwon.or.kr/" target="_blank" title="새창">수원교구</a>
				<a class="dropdown-item" href="http://www.catholicbusan.or.kr/" target="_blank" title="새창">부산교구</a>
				<a class="dropdown-item" href="http://www.gjcatholic.or.kr/" target="_blank" title="새창">광주대교구</a>
				<a class="dropdown-item" href="http://www.cccatholic.or.kr/" target="_blank" title="새창">춘천교구</a>
				<a class="dropdown-item" href="http://www.wjcatholic.or.kr/" target="_blank" title="새창">원주교구</a>
				<a class="dropdown-item" href="http://cdcj.or.kr/" target="_blank" title="새창">청주교구</a>
				<a class="dropdown-item" href="http://www.jcatholic.or.kr/" target="_blank" title="새창">전주교구</a>
				<a class="dropdown-item" href="http://www.djcatholic.or.kr/" target="_blank" title="새창">대전교구</a>
				<a class="dropdown-item" href="http://www.ucatholic.or.kr/" target="_blank" title="새창">의정부교구</a>
				<a class="dropdown-item" href="http://cathms.kr/" target="_blank" title="새창">마산교구</a>
				<a class="dropdown-item" href="http://www.diocesejeju.or.kr/" target="_blank" title="새창">제주교구</a>
				<a class="dropdown-item" href="http://www.daegu-archdiocese.or.kr/" target="_blank" title="새창">대구대교구</a>
				<a class="dropdown-item" href="http://www.acatholic.or.kr/" target="_blank" title="새창">안동교구</a>
				<a class="dropdown-item" href="http://www.gunjong.or.kr/" target="_blank" title="새창">군종교구</a>
              </div>
            </div>
        </div>

        <div class="col-xl-2dot4">
            <div class="dropdown show">
              <a class="btn btn-secondary dropdown-toggle holybutton" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                부서
              </a>
              <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <a class="dropdown-item" href="http://youth.caincheon.or.kr/" target="_blank" >청소년사목국</a>
                <a class="dropdown-item" href="http://www.inchung.net/" target="_blank">청년부</a>
                <a class="dropdown-item" href="http://www.sungso.net/" target="_blank">성소국</a>
              </div>
            </div>
        </div>

        <div class="col-xl-2dot4">
            <div class="dropdown show">
              <a class="btn btn-secondary dropdown-toggle holybutton" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                단체
              </a>
              <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <a class="dropdown-item" href="http://www.legiom.com/" target="_blank">바다의별 레지아</a>
                <a class="dropdown-item" href="http://www.incheonme.net/" target="_blank">인천ME</a>
                <a class="dropdown-item" href="http://www.cen.or.kr/" target="_blank">가톨릭환경연대</a>
              </div>
            </div>
        </div>

        <div class="col-xl-2dot4 lastholybutton">
            <div class="dropdown show">
              <a class="btn btn-secondary dropdown-toggle holybutton" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                기관
              </a>
              <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <a class="dropdown-item" href="https://www.iccu.ac.kr/" target="_blank">인천가톨릭대학교</a>
                <a class="dropdown-item" href="http://www.cku.ac.kr/" target="_blank">가톨릭관동대학교</a>
                <a class="dropdown-item" href="http://www.caritasincheon.or.kr/" target="_blank">인천가톨릭사회복지회</a>
                <a class="dropdown-item" href="http://www.yism.or.kr/" target="_blank">가톨릭아동청소년재단</a>
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
            <img src="_common3/img/main_logo.png" class="mr-auto">
            <div class="footertext ml-auto">
              <p>인천광역시 동구 박문로1 (송림동 103-25)</a>
            </div>
          </div>
          <div class="col-xl-6">
            <div align="right">
              <a href="/policy/personal.jsp" title="개인정보보호정책" class="footerrighttext">개인보호정책</a>
              <p style="display:inline;" class="footerrighttext">|</p>
              <a href="/policy/email.jsp" title="무단 이메일 수집거부" class="footerrighttext">무단 이메일 수집 거부</a>
              <p style="display:inline;" class="footerrighttext">|</p>
              <a href="/policy/terms.jsp" title="이용약관" class="footerrighttext">이용약관</a>
              <p style="display:inline;" class="footerrighttext">|</p>
              <a href="/policy/sitemap.jsp" title="사이트맵" class="footerrighttext">사이트맵</a>
              <p class="footerrighttext">Copyright © Diocese of Incheon. All rights reserved.</p>
            </div>
          </div>
      </div>
    </div>
  </div>
</div>





    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>


  </form>
  </body>
</html>
