<%@ page session="false" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/_common/inc/head.jsp"%>
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
<script type="text/javascript">
var srchweek_no = 0;
/**
 * 교구일정 주별로  TOP 4 가져오기
 */
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
</script>
<body>
	<form name="form01" action="/news/alb_view.do" method="POST">
		<input type="hidden" name="idx" id="idx" value="${idx}" /> <input
			type="hidden" name="gubuncd" id="gubuncd" value="${gubuncd}" /> <input
			type="hidden" name="b_idx" id="b_idx" value="${b_idx}" /> <input
			type="hidden" name="bl_idx" id="bl_idx" value="" /> <input
			type="hidden" name="s_idx" id="s_idx" value="" /> <input
			type="text" name="todayContents" id="todayContents"
			value="${todayContents.H_DATE}" style="height:0; font-size:0; overflow:hidden;visibility:hidden; width:0; height:0;" />
	
		<!-- header -->
		<header>
			<%@include file="./_common/inc/gnb.jsp"%>
		</header>
		<!-- //header -->
		<!-- wrap -->
		<div class="section" id="wrap">
			<!-- visual -->
			
			<!-- //visual -->
			<!-- secWrap  -->
			<div class="secWrap" id="mainSection">
				<!-- secs  -->
				<div class="secs">
					<!-- sec01 -->
					<section class="sec01">
						<h2 class="blind">중심내용</h2>
						<h3 class="blind">내용1</h3>
						<div>
							<article class="remeber">
								<h5>Today: ${todayContents.H_DATE} ${todayContents.DESCRIPTION}</h5>
								<!--
								<ul>
									<li><a href="https://www.daum.net" target="_blank"><span><em>대림시기</em>
										</span></a></li>
								</ul>
								-->
								<div class="mainSliders">
									<div class="swiperSlider swiper-container topSlider">
										<ul class="swiper-wrapper">
											<li class="swiper-slide"><img src="/img/main/top_slider_01.png"></li>
											<li class="swiper-slide"><img src="/img/main/top_slider_02.png"></li>
											<li class="swiper-slide"><img src="/img/main/top_slider_03.png"></li>
											<li class="swiper-slide"><img src="/img/main/top_slider_04.png"></li>
										</ul>
										<div class="swiper-pagination"></div>
									</div>
								</div>
							</article>
							<script>	
							$(document).ready(function() {
								//베스트		
								new Swiper('.swiper-container', {
									loop : true,
									//centeredSlides: true,
									autoplay: {
										delay: 3000,
									  },
									pagination: {
										el: '.swiper-pagination',
									},
									  //navigation: {
										//nextEl: '.swiper-button-next',
										//prevEl: '.swiper-button-prev',
									  //},
									slidesPerView: "auto"
								});	
								
							});		
							</script>
							<article class="news">
								<h5>&nbsp;</h5>
								<div class="box">
									<div class="left">
										<h5>
											<i><img alt="" src="/img/main/ico/ca.png"></i><span><em>천주교 인천교구</em><span>DIOCESE OF INCHEON</span></span>
										</h5>
										<i><img alt="교회소식" src="/img/main/sec_01_church.png"></i>
										<ul>
											<li>전체</li>
											<li>교회</li>
											<li class="on">교구</li>
											<li>본당</li>
											<li>공동체</li>
										</ul>
									</div>
									<div class="right">
										<div class="clearfix tabCont">
											<div>
												<h5>전체소식</h5>
												<i class="more"><a href="/news/news_list.do?b_idx=ALL">more<img
														alt="더보기" src="/img/main/ico/more.png"></a></i>
												<ul class="clearfix">
													<c:choose>
														<c:when test="${fn:length(totalNewsList) > 0}">
															<c:forEach items="${totalNewsList}" var="list">
																<c:if test="${list.GUBUN eq '1' }">
																	<li class="on"><a
																		href="javascript:viewNews(${list.B_IDX},${list.BL_IDX})"><em>공지</em>${list.TITLE}</a><span>${list.REGDATE}</span>
																	</li>
																</c:if>
																<c:if test="${list.GUBUN eq '2' }">
																	<li><a
																		href="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">${list.TITLE}</a><span>${list.REGDATE}</span>
																	</li>
																</c:if>
															</c:forEach>
														</c:when>

													</c:choose>
												</ul>
											</div>
											<div>
												<h5>교회소식</h5>
												<i class="more"><a href="/news/news_list.do?b_idx=9">more<img
														alt="더보기" src="/img/main/ico/more.png"></a></i>
												<ul class="clearfix">
													<c:choose>
														<c:when test="${fn:length(chuchNewsList) > 0}">
															<c:forEach items="${chuchNewsList}" var="list">
																<c:if test="${list.GUBUN eq '1' }">
																	<li class="on"><a
																		href="javascript:viewNews(${list.B_IDX},${list.BL_IDX})"><em>공지</em>${list.TITLE}</a><span>${list.REGDATE}</span>
																	</li>
																</c:if>
																<c:if test="${list.GUBUN eq '2' }">
																	<li><a
																		href="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">${list.TITLE}</a><span>${list.REGDATE}</span>
																	</li>
																</c:if>
															</c:forEach>
														</c:when>

													</c:choose>
												</ul>
											</div>
											<div class="on">
												<h5>교구소식</h5>
												<i class="more"><a href="/news/news_list.do?b_idx=12">more<img
														alt="더보기" src="/img/main/ico/more.png"></a></i>
												<ul class="clearfix">
													<c:choose>
														<c:when test="${fn:length(parishList) > 0}">
															<c:forEach items="${parishList}" var="list">
																<c:if test="${list.GUBUN eq '1' }">
																	<li class="on"><a
																		href="javascript:viewNews(${list.B_IDX},${list.BL_IDX})"><em>공지</em>${list.TITLE}</a><span>${list.REGDATE}</span>
																	</li>
																</c:if>
																<c:if test="${list.GUBUN eq '2' }">
																	<li><a
																		href="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">${list.TITLE} <c:if test="${list.DEPART_NAME ne ''}">[${list.DEPART_NAME }]</c:if></a><span>${list.REGDATE}</span>
																	</li>
																</c:if>
															</c:forEach>
														</c:when>

													</c:choose>
												</ul>
											</div>
											<div>
												<h5>본당소식</h5>
												<i class="more"><a href="/news/news_list.do?b_idx=11">more<img
														alt="더보기" src="/img/main/ico/more.png"></a></i>
												<ul class="clearfix">
													<c:choose>
														<c:when test="${fn:length(bondangNewsList) > 0}">
															<c:forEach items="${bondangNewsList}" var="list">
																<c:if test="${list.GUBUN eq '1' }">
																	<li class="on"><a
																		href="javascript:viewNews(${list.B_IDX},${list.BL_IDX})"><em>공지</em>${list.TITLE}</a><span>${list.REGDATE}</span>
																	</li>
																</c:if>
																<c:if test="${list.GUBUN eq '2' }">
																	<li><a
																		href="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">${list.TITLE}</a><span>${list.REGDATE}</span>
																	</li>
																</c:if>
															</c:forEach>
														</c:when>
														<c:otherwise>
															<li class="on">No List!</li>
														</c:otherwise>
													</c:choose>
												</ul>
											</div>
											<div>
												<h5>공동체소식</h5>
												<i class="more"><a href="/news/news_list.do?b_idx=10">more<img
														alt="더보기" src="/img/main/ico/more.png"></a></i>
												<ul class="clearfix">
													<c:choose>
														<c:when test="${fn:length(unitList) > 0}">
															<c:forEach items="${unitList}" var="list">
																<c:if test="${list.GUBUN eq '1' }">
																	<li class="on"><a
																		href="javascript:viewNews(${list.B_IDX},${list.BL_IDX})"><em>공지</em>${list.TITLE}</a><span>${list.REGDATE}</span>
																	</li>
																</c:if>
																<c:if test="${list.GUBUN eq '2' }">
																	<li><a
																		href="javascript:viewNews(${list.B_IDX},${list.BL_IDX})">${list.TITLE}</a><span>${list.REGDATE}</span>
																	</li>
																</c:if>
															</c:forEach>
														</c:when>
														<c:otherwise>
															<li class="on">No List!</li>
														</c:otherwise>
													</c:choose>
												</ul>
											</div>
										</div>
										<div>

											<h5>
												<span class="ttl">월간 교구 일정</span> 
												<i class="moOnly more"><a href="/news/sch_list.do?gubuncd=1">more<img alt="더보기" src="/img/main/ico/more.png"></a></i> 
												<span class="arrSet">
												<i onclick="changeGyoguMonthlySchedule(-1)"><img alt="이전날짜" src="/img/main/ico/left.png"></i> 
												<span id="gyogu_monthly_schedule_week">${todayContents.YYMM}. ${todayContents.WEEK}주</span>
												<i onclick="changeGyoguMonthlySchedule(1)"><img alt="다음날짜" src="/img/main/ico/right.png"></i>
												</span>
											</h5>
											<ul class="sche on" id="gyogu_monthly_schedule">
												<c:if test="${fn:length(ggScheduls) > 0}">
												<c:forEach items="${ggScheduls}" var="list">
													<li><em>${list.GUBUN}</em><a
														href="javascript: viewSchedule('${list.GUBUN}','${ggScheduls.B_IDX}','${ggScheduls.S_IDX}')">${ggScheduls.TITLE}</a><span>${ggScheduls.START_DATE}</span>
													</li>
												</c:forEach>
												</c:if>
											</ul>
										</div>
									</div>
								</div>
							</article>
							<div class="albMov">
								<article class="album">
									<h4>교구앨범</h4>
									<span class="arr"><i class="prv"><img alt="이전으로"
											src="/img/main/ico/left_gray.png"></i> <i class="nxt"><img
											alt="다음으로" src="/img/main/ico/right_gray.png"></i></span>
									<ul>
										<c:choose>
											<c:when test="${fn:length(albList) > 0}">
												<c:forEach items="${albList}" var="list">
													<li><a href="javascript:viewAlb(${list.IDX})"><em><img
																alt="" src="${list.FILEPATH}${list.STRFILENAME}"></em><span>${list.TITLE}</span></a>
														</a></li>
												</c:forEach>
											</c:when>
										</c:choose>
									</ul>
								</article>
								<article class="movie">
									<h4>교구영상</h4>
									<span class="arr"><i class="prv"><img alt="이전으로"
											src="/img/main/ico/left_gray.png"></i> <i class="nxt"><img
											alt="다음으로" src="/img/main/ico/right_gray.png"></i></span>
									<ol>
										<li>
											<ul>
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
																		alt="" src="${YOUTUBE_THUMBNAIL_URL}"><span
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
										</li>
									</ol>
								</article>
							</div>
						</div>
					</section>
					<!-- //sec01 -->
					<!-- sec02 -->
	                <section class="sec02">
	                    <h3 class="blind">교구장동정</h3>
	                    <div class="bdr">
	                        border
	                    </div>
	                    <div class="slideWrap">
	                        <figure>
	                            <h4>교구장 동정</h4><!-- 
	                            <div class="bx-wrapper" style="max-width: 1257px; margin: 0px auto;">
	                            <div class="bx-viewport" style="width: 100%; overflow: hidden; position: relative; height: 220px;"> -->
	                            <span class="slides" style="width: 515%; position: relative; transition-duration: 0s; transform: translate3d(-1297.5px, 0px, 0px);">
	                            	
	                                <!--  loop start -->
	                                
	                                <c:forEach items="${parList}" var="row" varStatus="status">
	                                
	                                <c:set var="par_view_uri" value="/gyogu/par_view.do?pageNo=1&b_idx=${row.B_IDX}&bl_idx=${row.BL_IDX}" />
	                                
	                                <em <c:if test="${status.last or status.first }">class="bx-clone"</c:if> style="vertical-align:top">
	                                	<c:if test="${fn:length(row.STRFILENAME1) > 0}">
                                		<i><img src="${row.FILEPATH1}thumbnail/${row.STRFILENAME1}" width="335px" onClick="javascript: location.href='${par_view_uri}'; "></i>
	                                	</c:if>
	                                	<span class="center" onClick="javascript: location.href='${par_view_uri}'; ">
		                                	<span class="ttl">${row.TITLE}</span> 
		                                	<c:if test="${row.CONTENT ne ''}"><span class="txt">${row.CONTENT}</span></c:if>
	                                	</span>
	                                </em>
	                                
	                                </c:forEach>
	                                
	                            </span><!-- 
	                            </div>
	                            </div> -->
	                            <figcaption>교구장 동정 관련 축복식 등 관련 행사 사진</figcaption>
	                        </figure>
	                        <i class="prv"><img alt="이전" src="/img/main/sec_02_arr_l.png"></i> 
	                        <i class="nxt"><img alt="다음" src="/img/main/sec_02_arr_r.png"></i>
	                    </div>
	                </section>
					<!-- //sec02 -->
					<!-- sec03 -->
					<section class="sec03">
						<h3 class="blind">내용3</h3>
						<div>
							<ul>
								<li><a href="/gyogu/msg_view_recently.do"><em><img
											alt="" src="/img/main/sec_03_ico_over.png"></em><i><img
											alt="" src="/img/main/sec_03_ico_01.png"><span>사목교서</span></i></a>
								</li>
								<li><a href="/samok/cure_list.do?c_idx=ALL"><em><img
											alt="" src="/img/main/sec_03_ico_over.png"></em><i><img
											alt="" src="/img/main/sec_03_ico_02.png"><span>사목자료</span></i></a>
								</li>
								<li><a href="/church/temp_01.do?qk="><em><img
											alt="" src="/img/main/sec_03_ico_over.png"></em><i><img
											alt="" src="/img/main/sec_03_ico_03.png"><span>미사시간</span></i></a>
								</li>
								<li><a href="/intro/intro_03.jsp"><em><img alt=""
											src="/img/main/sec_03_ico_over.png"></em><i><img alt=""
											src="/img/main/sec_03_ico_04.png"><span>관할구역</span></i></a></li>
								<li><a href="/community/cana.do"><em><img
											alt="" src="/img/main/sec_03_ico_over.png"></em><i><img
											alt="" src="/img/main/sec_03_ico_05.png"><span>카나혼인강좌
												&amp; 약혼자 주말신청</span></i></a></li>
								<li><a href="/community/doctrine.jsp"><em><img
											alt="" src="/img/main/sec_03_ico_over.png"></em><i><img
											alt="" src="/img/main/sec_03_ico_06.png"><span>통신교리신청</span></i></a>
								</li>
								<li><a href="/news/mgz_list.do?pub_idx=3"><em><img
											alt="" src="/img/main/sec_03_ico_over.png"></em><i><img
											alt="" src="/img/main/sec_03_ico_07.png"><span>인천주보</span></i></a>
								</li>
								<li><a href="/intro/diocesan_12.do#MarriageAndFaith"><em><img
											alt="" src="/img/main/sec_03_ico_over.png"></em><i><img
											alt="" src="/img/main/sec_03_ico_08.png"><span>혼인과
												신앙</span></i></a></li>
							</ul>
							<article class="box">
								<h4>본당 찾기</h4>
								<span>아름다운 인천교구 지구별 본당을 찾아보세요.<br> 하단을 클릭하시면 해당 지구별
									본당 페이지로 이동합니다.
								</span>
								<ul>
								<c:forEach var="entry" items="${giguList}" varStatus="status">
									<li><a href="javascript:goTemple('${entry.key}')">${entry.value}</a></li>
								</c:forEach>
								</ul>
							</article>
						</div>
					</section>
					<!-- //sec03 -->
					<!-- sec04 -->
					<section class="sec04">
						<h3 class="blind">내용4</h3>
						<article>
							<h4>교구 성지</h4>
							<ul>
								<li><a href="/intro/shirine_03.jsp"><span><em>제물진두
												순교성지</em> <span>하느님께서 순교자들을 감싸는<br> 두 손 모양을 형상화한 순교성지
										</span></span> <i><img alt="" src="/img/main/sec_04_01.png"></i></a></li>
								<li><a href="/intro/shirine_05.jsp"><span><em>일만위
												순교자 현양동산</em> <span>크고 많은 역사적 의미와 문화적 유산을<br> 간직하고 있는 	역사의 보고
										</span></span> <i><img alt="" src="/img/main/sec_04_02.png"></i></a></li>
								<li><a href="/intro/shirine.jsp"><span><em>갑곶순교성지</em>
											<span>순교자들의 행적증거자<br> 박순집 베드로
										</span></span> <i><img alt="" src="/img/main/sec_04_03.png"></i></a></li>
							</ul>
							<ol>
								<li><a href="#none">가톨릭</a>
									<ul>
										<li>
											<ol>
												<li><a href="http://www.cbck.or.kr" target="_blank" title="새창">주교회의</a></li>
												<li><a href="http://www.catholic.or.kr" target="_blank" title="새창">굿뉴스</a></li>
											</ol>
										</li>
									</ul></li>
								<li><a href="#none">타교구</a>
									<ul>
										<li>
											<ol>
												<li><a href="http://aos.catholic.or.kr/" target="_blank" title="새창">서울대교구</a></li>
												<li><a href="http://www.casuwon.or.kr/" target="_blank" title="새창">수원교구</a></li>
												<li><a href="http://www.catholicbusan.or.kr/" target="_blank" title="새창">부산교구</a></li>
												<li><a href="http://www.gjcatholic.or.kr/" target="_blank" title="새창">광주대교구</a></li>
												<li><a href="http://www.cccatholic.or.kr/" target="_blank" title="새창">춘천교구</a></li>
												<li><a href="http://www.wjcatholic.or.kr/" target="_blank" title="새창">원주교구</a></li>
												<li><a href="http://cdcj.or.kr/" target="_blank" title="새창">청주교구</a></li>
												<li><a href="http://www.jcatholic.or.kr/" target="_blank" title="새창">전주교구</a></li>
												<li><a href="http://www.djcatholic.or.kr/" target="_blank" title="새창">대전교구</a></li>
												<li><a href="http://www.ucatholic.or.kr/" target="_blank" title="새창">의정부교구</a></li>
												<li><a href="http://cathms.kr/" target="_blank" title="새창">마산교구</a></li>
												<li><a href="http://www.diocesejeju.or.kr/" target="_blank" title="새창">제주교구</a></li>
												<li><a href="http://www.daegu-archdiocese.or.kr/" target="_blank" title="새창">대구대교구</a></li>
												<li><a href="http://www.acatholic.or.kr/" target="_blank" title="새창">안동교구</a></li>
												<li><a href="http://www.gunjong.or.kr/" target="_blank" title="새창">군종교구</a></li>
											</ol>
										</li>
									</ul></li>
								<li><a href="#none">부서</a>
									<ul>
										<li>
											<ol>
												<li><a href="http://youth.caincheon.or.kr/" target="_blank" title="새창">청소년사목국</a></li>
												<li><a href="http://www.inchung.net/" target="_blank" title="새창">청년부</a></li>
												<li><a href="http://www.sungso.net/" target="_blank" title="새창">성소국</a></li>
											</ol>
										</li>
									</ul></li>
								<li><a href="#none">단체</a>
									<ul>
										<li>
											<ol>
												<li><a href="http://www.legiom.com/" target="_blank" title="새창">바다의별 레지아</a></li>
												<li><a href="http://www.incheonme.net/" target="_blank" title="새창">인천ME </a></li>
												<li><a href="http://www.cen.or.kr/" target="_blank" title="새창">가톨릭환경연대</a></li>
											</ol>
										</li>
									</ul></li>
								<li><a href="#none">기관</a>
									<ul>
										<li>
											<ol>
												<li><a href="https://www.iccu.ac.kr/" target="_blank" title="새창">인천가톨릭대학교</a></li>
												<li><a href="http://www.cku.ac.kr/" target="_blank" title="새창">가톨릭관동대학교</a></li>
												<li><a href="http://www.caritasincheon.or.kr/" target="_blank" title="새창">인천가톨릭사회복지</a></li>
												<li><a href="http://www.yism.or.kr/" target="_blank" title="새창">가톨릭아동청소년재</a></li>
											</ol>
										</li>
									</ul></li>
							</ol>
						</article>
					</section>
					<!-- //sec04 -->
					<!-- footer -->
					<footer>
						<%@ include file="/_common/inc/footer.jsp"%>
					</footer>
					<!-- //footer -->
				</div>
				<!-- //secs  -->
			</div>
			<!-- //secWrap  -->
		</div>
		<!--// wrap -->
	</form>
</body>



<!-- ============= pop jsp START ============= -->
<%@ include file="/pop/popupHome.jsp"%>
<!-- ============= pop jsp END ============= -->

<!-- ============= banner jsp START ============= -->
<%@ include file="/banner/bannerHome.jsp"%>
<!-- ============= banner jsp END ============= -->

</html>
