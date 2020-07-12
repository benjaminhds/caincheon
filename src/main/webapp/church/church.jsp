<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/_common/inc/headSub.jsp"%>
<body>
	<p class="skipNavi" tabindex="1">본문바로가기</p>
	<!-- wrap -->
	<div id="wrap">
		<!-- header -->
		<header>
			<%@include file="/_common/inc/gnb.jsp"%>
		</header>
		<!-- //header -->
		<!-- subVisual -->
		<section class="subVisual">
			<h2 class="blind">중심내용</h2>
			<div class="visBg">bg</div>
			<div class="artWrap">
				<article>
					<h3>지구별</h3>
					<ul>
						<li>홈</li>
						<li>본당</li>
						<li>지구별</li>
						<li><span>${giguList[step1] }</span></li>
					</ul>
				</article>
			</div>
		</section>
		<!-- //subVisual -->
		<!-- secWrap -->
		<div class="secWrap">
			<!-- sec01 -->
			<section class="sec01">
				<!-- secCont -->
				<div class="secCont">
					<h3 class="ttl">지구별 본당 선택</h3>
					<div class="stepIco">
						<dl>
	<script>
	//var gigoo = ${giguList};
    var gigoo = ${giguCodeList};
    function changeStep1(elementID)
    {
    	// step1 : 모두 감추기
    	for(var i = 0, i2 = gigoo.length ; i < i2 ; i++ ) {
    		
    		_no = (gigoo[i] < 10 ? "0" : "") + gigoo[i];
    		
    		$("#step1_"+_no).removeClass("on");
    		$("#step1_"+_no).removeClass("off");
    		
    		console.log(" > mainHall_"+_no+" status > pre > " + $("#mainHall_"+_no).css('display') );
    		
    		$("#mainHall_"+_no).css('display', 'none');
    		
    		console.log(" > mainHall_"+_no+" status > after > " + $("#mainHall_"+_no).css('display') );
    	}
    	
    	// step2 : 선택한 것만 보이기
    	$("#step1_"+elementID).addClass("on");//선택한것만보이게
    	$("#mainHall_"+elementID).css('display', '');//선택한것만보이게
    }
    </script>
							<dt>
								<i><em>step<span>01</span></em></i>
							</dt>
							<dd>
								<ul>
								<c:forEach var="entry" items="${giguList}" varStatus="status">
									<li id="step1_${entry.key}" class=""><a href="javascript: changeStep1('${entry.key}')"><span>${entry.value}</span></a></li>
								</c:forEach>
								</ul>
								<c:if test="${step1 eq ''}">
									<c:set var="step1" value="지구" />
								</c:if>
								<script>changeStep1('${step1}')</script>
							</dd>
						</dl>
						<dl id="mainHallNames">
							<dt>
								<i><em>step<span>02</span></em></i>
							</dt>
							<dd>

								<!-- 지구별 성당수 START -->
								<c:forEach var="entry" items="${mainHallList}" varStatus="status">
									<c:set var="mainHallName" value="${entry.key}" />
									<c:set var="mainHallList" value="${entry.value}" />
									<c:set var="mainHallRegionCd" value="${giguListMixed[mainHallName]}" />

									<!-- step2 default view setting :: visible -->
									<c:if test="${fn:length(mainHallRegionCd)>0}">
									
									<ul id="mainHall_${mainHallRegionCd}" style="display: <c:if test="${mainHallRegionCd ne step1}">none</c:if>">
										<c:forEach var="mainHall" items="${mainHallList}" varStatus="status">
											<c:set var="classtext" value="" />
											<c:if test="${churchIdx eq mainHall.value}">
												<c:set var="classtext" value="class='on'" />
											</c:if>
											<li ${classtext}><a
												href="/church/church.do?churchIdx=${mainHall.value}&step1=${mainHallRegionCd}"><span>${mainHall.key}</span></a></li>
										</c:forEach>
									</ul>
									
									</c:if>
								</c:forEach>
								<!-- misa time print-out END -->

							</dd>
						</dl>
					</div>
				</div>
				<!-- //secCont -->
			</section>
			<!-- //sec01 -->
			<!-- sec02 -->
			<section class="sec02 blue">
				<!-- secWide -->
				<div class="secWide blue v2">
					<div class="secCont">
						<h3 class="ttl">본당 둘러보기</h3>
					</div>
					<!-- secCont -->
					<div class="secCont">
						<div class="slideOne"
							style="vertical-align: middle; text-align: center">
							<figure>
								<span class="slione"> <!-- Church Pintures START --> <c:forEach
										var="row" items="${mainPictures}" varStatus="status">
										<span><em><img src="${row.IMAGE_URI}"
												alt="${row.IMAGE_DESC}" width=980 height=500></em></span>
									</c:forEach>
									<!-- Church Pintures END -->
									<!-- 
	                                <span><em><img src="/img/sub/gyogu_visual1.jpg" alt="" width=980 height=500></em></span>
	                                <span><em><img src="/img/sub/gyogu_visual2.jpg" alt=""></em></span>
	                                <span><em><img src="/img/sub/gyogu_visual.jpg" alt=""></em></span> -->
								</span>
								<figcaption>본당 둘러보기 이미지</figcaption>
							</figure>
							<i class="prv"><img src="/img/sub/_ico/vis_arr_l.png"
								alt="이전으로"></i> <i class="nxt"><img
								src="/img/sub/_ico/vis_arr_r.png" alt="다음으로"></i>
						</div>
					</div>
					<!-- //secCont -->
				</div>
				<!-- //secWide -->
			</section>
			<!-- //sec02 -->
			<!-- sec03 -->
			<section class="sec03">
				<!-- secCont -->
				<div class="secCont">
					<h3 class="ttl">본당 안내</h3>
					<article class="box50">
						<div class="left">
							<h4>
								<i><img src="/img/sub/gyogu_ico_01.png" alt=""></i><span>성당정보</span>
							</h4>
							<ul>
								<li><em>본당사제</em><span> ${fn:replace(churchVO.PRIESTNAMES, ',', '<BR>')} </span></li>
								<li><em>소속지구</em><span>${giguList[step1] }</span></li>
								<li><em>주보성인</em><span>${churchVO.JUBO_SAINT }</span></li>
								<li><em>설립일</em><span>${fn:substring(churchVO.ESTABLISH_DATE, 0, 10) }</span></li>
								<li><em>신자수</em><span>${churchVO.CHRISTIAN_CNT } 명</span></li>
								<li><em>전화번호</em><span>${churchVO.TEL }</span></li>
								<li><em>팩스번호</em><span>${churchVO.FAX }</span></li>
								<li><em>이메일</em><span>${churchVO.EMAIL }</span></li>
								<li><em>홈페이지</em><span>${churchVO.HOMEPAGE }</span></li>
								<li><em>수도회</em><span>${churchVO.R_ORDER }</span></li>
							</ul>
						</div>
						<div class="right">
							<h4>
								<i><img src="/img/sub/gyogu_ico_02.png" alt=""></i><span>미사안내</span>
							</h4>
							<ul>

								<!-- misa time print-out START -->
								<c:forEach var="entry" items="${misaList}" varStatus="status">
									<c:if test="${entry.key == churchVO.CHURCH_IDX}">
										<c:set var="misaInfoList" value="${entry.value}" />
										<c:forEach var="misaInfo" items="${misaInfoList}"
											varStatus="status">
											<span> <c:set var="isSun" value="" /> <c:if
													test="${misaInfo.WEEK == 6}">
													<c:set var="isSun" value="class=\"sat\"" />
												</c:if> <c:if test="${misaInfo.WEEK == 7}">
													<c:set var="isSun" value="class=\"sun\"" />
												</c:if>
												<li><em ${isSun}>${misaInfo.WEEK_NAME}</em><span>${fn:replace(misaInfo.MNAME, cr, "<BR/>")}
														<c:if test="${misaInfo.MNAME ne ''}">
															<BR>
														</c:if> <c:if test="${misaInfo.WEEK eq 8}">
															<font color="darkgray">(${misaInfo.UPD_DATE})</font>
														</c:if>
												</span></li>
											</span>
										</c:forEach>
									</c:if>

								</c:forEach>
								<!-- misa time print-out END -->
							</ul>
						</div>
					</article>
				</div>
				<!-- //secCont -->
			</section>
			<!-- //sec03 -->
			<!-- sec04 -->
			<section class="sec04">
				<!-- secCont -->
				<div class="secCont">
					<h3 class="ttl">본당 소개</h3>
					<div class="textBox bdrLine">
						<div class="text">
							<c:if test="${churchVO.INTRO eq null || churchVO.INTRO eq '' }"> 등록된 소개 정보가 없습니다. </c:if>
							${fn: replace( fn:replace(churchVO.INTRO, cr, br) , cn, brbr)}
						</div>
					</div>
				</div>
				<!-- //secCont -->
			</section>
			<!-- //sec04 -->
			<!-- sec05 -->
			<section class="sec05">
				<!-- secCont -->
				<div class="secCont">
					<h3 class="ttl">연혁</h3>
					<ol class="history bdrLine">
						<c:if
							test="${churchVO.HISTORY eq null || churchVO.HISTORY eq '' }"> 등록된 연혁 정보가 없습니다. </c:if>
						${fn:replace(fn:replace(churchVO.HISTORY, cn, "<BR/>"), " ", "&nbsp;")}
					</ol>
				</div>
				<!-- //secCont -->
			</section>
			<!-- //sec05 -->
			<!-- sec06 -->
			<section class="sec06">
				<!-- secCont -->
				<div class="secCont">
					<h3 class="ttl">본당 소식</h3>
					<!-- boardList  -->
					<div class="boardList oflow v2">
						<table>
							<caption>본당소식 및 앨범 관련 리스트</caption>
							<colgroup>
								<col style="width: 8%">
								<col style="width: 8%">
								<col style="width: 8%">
								<col>
								<col style="width: 8%">
								<col style="width: 15%">
							</colgroup>
							<thead>
								<tr>
									<th scope="col">No.</th>
									<th scope="col">분류</th>
									<th scope="col">본당</th>
									<th scope="col">제목</th>
									<th scope="col">작성일</th>
									<th scope="col">첨부파일</th>
								</tr>
							</thead>
							<tbody>


								<c:choose>
									<c:when test="${fn:length(newsList) > 0}">
										<c:forEach items="${newsList}" var="list">


											<tr>
												<th scope="row">${list.RNUM}</th>
												<th scope="row"><i class="ico blue"><c:if
															test="${list.CATEGORY_NAME eq null}">소식</c:if>${list.CATEGORY_NAME}</i></th>
												<td>${list.CHURCH_NAME}</td>
												<td><a
													href="/church/temp_02_view.do?b_idx=${list.B_IDX}&bl_idx=${list.BL_IDX}">${list.TITLE}</a></td>
												<td>${list.REGDATE}</td>
												<td class="file"><c:if test="${ list.FILE_CNT ne '0' }">
														<a
															href="javascript: attachedFile_Download ('${list.FILEPATH}','${list.FILENAME}','${list.STRFILENAME}')"><img
															src="/img/sub/_ico/board_download.png" alt=""></a>
													</c:if> <c:if test="${ list.FILE_CNT == 0 }">첨부없음</c:if></td>
											</tr>

										</c:forEach>
									</c:when>
									<c:otherwise>
										<li class="on">
										<th colspan=6>등록된 소식이 없습니다.</th>
										</li>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
					<!-- //boardList  -->
					<!-- arrow -->
					<%@ include file="/_common/inc/paging2.jsp"%>
					<!-- //arrow -->
				</div>
				<!-- //secCont -->
			</section>
			<!-- //sec06 -->
			<!-- sec07 -->
			<section class="sec07">
				<!-- secWide -->
				<div class="blue secWide v2">
					<div class="secCont">
						<h3 class="ttl">관할구역 위치 및 교통편</h3>
					</div>
					<!-- secCont -->
					<div class="secCont">
						<!-- map -->
						<article class="map">
							<h4 class="blind">지도</h4>
							<div class="mapCont" id="map"></div>
							<script type="text/javascript"
								src="//apis.daum.net/maps/maps3.js?apikey=282cb9ea85fe24ca02ecfff9b7fe81b7"></script>
							<script src="/_common/js/maps3.js"></script>
							<script>
	                            //
	                            var polygon = [${churchVO.MAP_CONTROLAREA}];// 관리구역 폴리곤
	                            var mapContainer = document.getElementById('map'); // 지도를 표시할 div 
                                var mapOption    = { center: new daum.maps.LatLng(${churchVO.MAP_POINT}), level: 5 }; // 지도의 중심좌표 , 지도의 확대 레벨
	                            var map = new daum.maps.Map(mapContainer, mapOption);
	                            displayAreaPolygon(map, '${churchVO.NAME}', polygon, mapOption);
	                            
                            </script>
							<!-- <div class="mapCont">
                                <!-- * Daum 지도 - 지도퍼가기 -->
							<!-- 1. 지도 노드 -- >
                                <div id="daumRoughmapContainer1484720067087" class="root_daum_roughmap root_daum_roughmap_landing"></div>
                                <!-- 2. 설치 스크립트 : 지도 퍼가기 서비스를 2개 이상 넣을 경우, 설치 스크립트는 하나만 삽입합니다. -- >
                                <script class="daum_roughmap_loader_script" src="http://dmaps.daum.net/map_js_init/roughmapLoader.js"></script>
                                <!-- 3. 실행 스크립트 -- >
                                <script>
                                // mapScript : "timestamp": "1484720067087", "key": "fgno", "mapWidth": "1200","mapHeight": "560"
                                new daum.roughmap.Lander({${mapScript}}).render();
                                </script>
                            </div>
                             -->
							<ul>
								<li><i><img src="/img/sub/_ico/ico_subway.png" alt=""></i>
									<span> <em>대중교통 이용 시</em> <span>${churchVO.TRAFFIC_MASS}</span>
								</span></li>
								<li><i><img src="/img/sub/_ico/ico_car.png" alt=""></i>
									<span> <em>자가용 이용 시</em> <span>${churchVO.TRAFFIC_SELF }</span>
								</span></li>
							</ul>
						</article>
						<!-- //map -->
					</div>
					<!-- //secCont -->
				</div>
				<!-- //secWide -->
			</section>
			<!-- //sec07 -->
			<!-- sec08 -->
			<section class="sec08 fin">
				<!-- secCont  -->
				<div class="secCont">
					<h3 class="ttl">공소</h3>
					<!-- boardList  -->
					<div class="boardList oflow">
						<table>
							<caption>공소 관련 리스트</caption>
							<colgroup>
								<col style="width: 8%">
								<col style="width: 8%">
								<col style="width: 8%">
								<col style="width: 8%">
								<col>
								<col style="width: 30%">
								<col style="width: 10%">
							</colgroup>
							<thead>
								<tr>
									<th scope="col">No.</th>
									<th scope="col">관활성당</th>
									<th scope="col">공소명</th>
									<th scope="col">공소회장</th>
									<th scope="col">주소</th>
									<th scope="col">교통편</th>
									<th scope="col">약도</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(vacancyList) > 0}">
										<c:forEach items="${vacancyList}" var="list">
											<tr>
												<th scope="row">${list.RNUM}</th>
												<td class="bod">${list.CHURCH_NAME}</a></td>
												<td>${list.GONGSO_NAME}</td>
												<td>${list.CHIEF_NAME}</td>
												<td>${list.ADDR}</td>
												<td>${list.TRAFFIC_MASS}</td>
												<td class="zoom"><c:if
														test="${fn:length(list.MAP) > 0}">
														<a href="/${list.MAP}"><i><img
																src="/img/sub/_ico/board_pin.png" alt=""></i></a>
													</c:if></td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan=7>등록된 공소가 없습니다.</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
					<!-- //boardList  -->
					<!-- arrow -->


					<ol class="arrow">
						<li class="fst arr"><a
							href="javascript:goPage(${pagingGongso.firstPageNo})"><img
								src="/img/sub/_ico/board_arr_first.png" alt="처음으로"></a></li>
						<li class="prev arr"><a
							href="javascript:goPage(${pagingGongso.prevPageNo})"><img
								src="/img/sub/_ico/board_arr_prev.png" alt="이전으로"></a></li>
						<c:forEach var="i" begin="${pagingGongso.startPageNo}"
							end="${pagingGongso.endPageNo}" step="1">
							<c:choose>
								<c:when test="${i eq pagingGongso.pageNo}">
									<li class="num on"><a href="javascript:goPage(${i})"
										class="choice">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li class="num"><a href="javascript:goPage(${i})">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<li class="next arr"><a
							href="javascript:goPage(${pagingGongso.nextPageNo})"><img
								src="/img/sub/_ico/board_arr_next.png" alt="다음으로"></a></li>
						<li class="lst arr"><a
							href="javascript:goPage(${pagingGongso.finalPageNo})"><img
								src="/img/sub/_ico/board_arr_last.png" alt="마지막으로"></a></li>
					</ol>

					<!-- //arrow -->
				</div>
				<!-- //secCont -->
			</section>
			<!-- //sec08 -->
		</div>
		<!-- //secWrap -->
		<!-- footer -->
		<footer>
			<%@ include file="/_common/inc/footer.jsp"%>
		</footer>
		<!-- //footer -->
	</div>
	<!--// wrap -->
</body>

</html>
