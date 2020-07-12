<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<script type="text/javascript">


$(document).ready(function() {
	// for pc
	$("#btnUnifySearch").on('click', function(evt){
		var srchTextVal = $("#unifySrchTextXX").val();
		console.log("0통합검색 키워드(터치):"+srchTextVal);
		//alert("0통합검색 키워드(터치):"+srchTextVal);
		goUnifySearchM(srchTextVal);
	});
	// for mobile touch
	$("#btnUnifySearch").bind('touchend', function(evt){
		var srchTextVal = $("#unifySrchTextXX").val();
		console.log("1통합검색 키워드(터치, isMobile?"+isMobile+"):"+srchTextVal);
		//alert("1통합검색 키워드(터치, isMobile?"+isMobile+"):"+srchTextVal);
		goUnifySearchM(srchTextVal);
	});
	// for mobile keyboard enter
/*
	$("#btnUnifySearch").bind('keypress', function(evt){
		alert("3통합검색 키보드):"+evt.key);
		var srchTextVal = $("#unifySrchTextXX").val();

		if(evt.keyCode === 13) {
			goUnifySearchM(srchTextVal);
		}
	}); */

	$( "#btnUnifySearch" ).keypress(function() {
	  alert( "Handler for .keypress() called." );
	});

});

function goUnifySearchM( srchTextVal ) {
	if( srchTextVal.trim() != "" && srchTextVal.trim().length > 0 ) 
		location.href = "/search/unify_search.do?srchText="+srchTextVal;
}
isMobile=true;

</script>

<body>
    <p class="skipNavi" tabindex="1">본문바로가기</p>
    <!-- wrap -->
    <div id="wrap">
        <!-- header -->
        <header>
            <%@include file="/_common/inc/gnb.jsp" %>
        </header>
        <!-- //header -->
        <!-- subVisual -->
        <section class="subVisual srchBg">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap">
                <article>
                    <h3>통합검색</h3>
                    <ul>
                        <li>홈</li>
                        <li>통합검색</li>
                    </ul>
                </article>
            </div>
        </section>
        <!-- //subVisual -->
        <!-- secWrap -->
        <div class="secWrap">
            <!-- sec01 -->
            <section class="sec01 fin totalSearch">
                <!-- secCont -->
                <div class="secCont">
                    <div class="srchForm">
                        <dl>
                            <dd>
                                <div class="srchSel">
                                    <dl>
                                        <dd>
                                            <label for=""></label>
                                            <input type="search" name="unifySrchTextXX" id="unifySrchTextXX" placeholder="통합검색" 
                                            value="${_params.srchText }" 
                                            onKeyDown='javascript: if(event.keyCode==13) { goUnifySearch(this.value); } '
                                            style="width: 60%"
                                            >
                                            <button id="btnUnifySearch">검색</button>
                                        </dd>
                                    </dl>
                                </div>
                            </dd>
                        </dl>
                    </div>
                    
                    <!--  -->
                    <c:if test="${fn:length(DATA_PRIEST)>0}">
                    <h3 class="ttl tb fl">사제</h3>
                        <p class="tbMore fr"><a href="#"><c:if test="${fn:length(DATA_PRIEST)>0}"> <!-- + 더보기 --></c:if></a></p>
                        <div class="boardList oflow v2">
                            <table>
                                <caption>교구 사제 리스트</caption>
                                <colgroup>
                                    <col style="width:16.66%">
                                    <col style="width:16.66%">
                                    <col style="width:16.66%">
                                    <col style="width:16.66%">
                                    <col style="width:16.66%">
                                    <col style="width:16.66%">
                                </colgroup>
                                <thead>
                                    <tr>
                                        <th scope="col">No.</th>
                                        <th scope="col">성명</th>
                                        <th scope="col">세례명</th>
                                        <th scope="col">현임지</th>
                                        <th scope="col">서품</th>
                                        <th scope="col">축일</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${DATA_PRIEST}" var="row">
                                    <tr>
                                        <th scope="row">${row.RNUM }</th>
                                        <td class="file"><a href="/father/father_view.do?p_idx=${row.P_IDX }">${row.NAME}</a></td>
                                        <td>${row.CHRISTIAN_NAME }</td>
                                        <td>${row.ORG_NAME}</td>
                                        <td>${row.P_BIRTHDAY}</td>
                                        <td>${row.NEW_BIRTHDAY}</td>
                                    </tr>
                                	</c:forEach>
                                	<!-- 
                                    <tr>
                                        <th scope="row">5</th>
                                        <td class="file"><a href="priest_view.html">강의선</a></td>
                                        <td>힐라리오</td>
                                        <td>은퇴</td>
                                        <td>2016. 12. 12</td>
                                        <td>01-10</td>
                                    </tr> -->
                                </tbody>
                            </table>
                        </div>
                     </c:if>
                        
                    <!--  -->
                    <c:if test="${fn:length(DATA_GYOGU)>0}">
                    <h3 class="ttl tb fl">교구청</h3>
                    <p class="tbMore fr"><a href="#"><c:if test="${fn:length(DATA_GYOGU)>0}"> <!-- + 더보기 --></c:if></a></p>
                    <div class="boardList oflow faq v2">
                        <table>
                        	<caption>공소 리스트</caption>
                        	<colgroup>
                                <col style="width:4%">
                                <col style="width:15%">
                                <col style="width:5%">
                                <col style="width:7%">
                                <col style="width:7%">
                                <col style="width:25%">
                                <col>
                                <col style="width:12%">
                                <col style="width:7%">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col">No.</th>
                                    <th scope="col">부서명</th>
                                    <th scope="col">부서장</th>
                                    <th scope="col">웹사이트</th>
                                    <th scope="col">전화번호</th>
                                    <th scope="col">FAX</th>
                                    <th scope="col">소재지</th>
                                    <th scope="col">메일</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${DATA_GYOGU}" var="row">
                                <tr>
                                    <td scope="col">${row.RNUM }</td>
                                    <td scope="col">${row.NAME }</td>
                                    <!--<td scope="col">${row.POS1 }</td>-->
                                    <td scope="col">${row.HOMEPAGE1 }</td>
                                    <td scope="col">${row.TEL }</td>
                                    <td scope="col">${row.FAX }</td>
                                    <td scope="col">${row.ADDR1 } ${row.ADDR2 }</td>
                                    <td scope="col">${row.MAIL }</td>
                                </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    </c:if>
                    
                    <!--  -->
                    <c:if test="${fn:length(DATA_CHURCH_L)>0 or fn:length(DATA_GONGSO_L)>0}">
                    <h3 class="ttl tb fl">본당 / 공소</h3>
                    <p class="tbMore fr"><a href="#"><c:if test="${fn:length(DATA_CHURCH_L)>0}"> <!-- + 더보기 --></c:if></a></p>
                    <div class="boardList oflow faq v2">
                        <table>
                            <caption>공소 리스트</caption>
                            <colgroup>
                                <col style="width:4%">
                                <col style="width:15%">
                                <col style="width:5%">
                                <col style="width:7%">
                                <col style="width:7%">
                                <col style="width:25%">
                                <col>
                                <col style="width:12%">
                                <col style="width:7%">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col">No.</th>
                                    <th scope="col">미사시간</th>
                                    <th scope="col">관할본당</th>
                                    <th scope="col">공소명</th>
                                    <th scope="col">본당사제</th>
                                    <th scope="col">본당주소</th>
                                    <!-- <th scope="col">교통편</th>-->
                                    <th scope="col">전화번호</th>
                                    <!-- <th scope="col">위치</th>-->
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${DATA_CHURCH_L}" var="row">
                                <tr class="question">
                                    <th scope="row">${row.RNUM }</th>
                                    <td><i class="icoMisa">미사시간 보기</i></td>
                                    <td><a href="/church/church.do?churchIdx=${row.CHURCH_IDX }">${row.CHURCH_NAME }</a></td>
                                    <td>${row.GONGSO_NAME }</td>
                                    <td>${row.JUBO_SAINT }${row.CHIEF_NAME }</td>
                                    <td>${row.ADDR }</td>
                                    <!-- <td>${row.TRAFFIC }</td>-->
                                    <td><span class="tels">${row.TEL }</span><a href="tel:${row.TEL }" class="sendTel">${row.TEL }</a></td>
                                    <!-- <td class="file"><c:if test="${fn:length(row.MAP) > 0}"><a href="${row.MAP }"><img src="/img/sub/_ico/board_pin.png" alt=""></a></c:if></td>-->
                                </tr>
                                <tr class="answer" style="display: none;">
                                    <td colspan="9">
                                        <span>
                                        	<c:forEach var="entry" items="${DATA_CHURCH_M}" varStatus="status2">
                                        		<c:if test = "${entry.key == row.CHURCH_IDX}">
	                                        	<c:forEach var="misaInfo" items="${entry.value}" varStatus="status">
	                                        	<span>
	                                        		<c:set var="isSun" value="" />
	                                        		<c:if test = "${misaInfo.WEEK == 7}">
	                                        			<c:set var="isSun" value="class=\"sun\"" />
	                                        		</c:if>
	                                        		<c:if test = "${misaInfo.WEEK == 6}">
	                                        			<c:set var="isSun" value="class=\"sat\"" />
	                                        		</c:if>
	                                                <i ${isSun}>${misaInfo.WEEK_NAME}</i>
	                                                <em><c:out value="${misaInfo.MNAME}" /><c:if test="${misaInfo.MNAME ne ''}"><BR></c:if>
	                                                <c:if test="${misaInfo.WEEK eq 8}"><font color="darkgray">(${misaInfo.UPD_DATE})</font></c:if></em>
	                                            </span>
	                                            </c:forEach>
	                                            </c:if>
                                        	</c:forEach>
                                        </span>
                                    </td>
                                </tr>
                                </c:forEach>
                                <c:forEach items="${DATA_GONGSO_L}" var="row">
                                <tr class="question">
                                    <th scope="row">${row.RNUM }</th>
                                    <c:forEach items="${DATA_GONGSO_M}" var="entry" varStatus="status2">
                                    <c:if test = "${entry.key eq row.G_IDX}">
	                                    <c:if test = "${fn:length(entry.value) == 0}"><td>-</td></c:if>
	                                    <c:if test = "${fn:length(entry.value)  > 0}"><td><i class="icoMisa">미사시간 보기</i></td></c:if>
                                    </c:if>
                                    </c:forEach>
                                    <td><a href="/church/church.do?churchIdx=${row.CHURCH_IDX }">${row.CHURCH_NAME }</a></td>
                                    <td>${row.GONGSO_NAME }</td>
                                    <td>${row.JUBO_SAINT }${row.CHIEF_NAME }</td>
                                    <td>${row.ADDR }</td>
                                    <td>${row.TRAFFIC }</td>
                                    <td><span class="tels">${row.TEL }</span><a href="tel:${row.TEL }" class="sendTel">${row.TEL }</a></td>
                                    <td class="file"><c:if test="${fn:length(row.MAP) > 0}"><a href="${row.MAP }"><img src="/img/sub/_ico/board_pin.png" alt=""></a></c:if></td>
                                </tr>
                                <tr class="answer" style="display: none;">
                                    <td colspan="9">
                                        <span>
                                        	<c:forEach var="entry" items="${DATA_GONGSO_M}" varStatus="status2">
	                                        	<c:if test = "${entry.key eq row.G_IDX and fn:length(entry.value) > 0}">
	                                        	<c:forEach var="misaInfo" items="${entry.value}" varStatus="status3">
	                                     	    <c:if test = "${misaInfo.MNAME ne '' and misaInfo.MNAME ne 'null'}"><!-- 요일별 존재하는 것만 출력 -->
	                                        	<span>
	                                        		<c:set var="isSun" value="" />
	                                        		<c:if test = "${misaInfo.WEEK == 0}"><c:set var="isSun" value="class=\"sun\"" /></c:if>
	                                        		<c:if test = "${misaInfo.WEEK == 6}"><c:set var="isSun" value="class=\"sat\"" /></c:if>
	                                                <i ${isSun}>${misaInfo.WT}</i>
	                                                <em><c:out value="${misaInfo.MNAME}" /><c:if test="${misaInfo.MNAME ne ''}"><BR></c:if>
	                                                <c:if test="${(misaInfo.WT eq '비고' or fn:length(entry.value) == (status3.index+1) ) and fn:length(misaInfo.UPD_DATE) > 0}"><font color="darkgray">(마지막 업데이트 : ${misaInfo.UPD_DATE})</font></c:if>
	                                                </em>
	                                            </span>
	                                  		    </c:if>
	                                  		    </c:forEach>
	                                		    </c:if>
                                        	</c:forEach>
                                        </span>
                                    </td>
                                </tr>
                                </c:forEach><!-- 
                                <tr class="question">
                                    <th scope="row">4</th>
                                    <td><i class="icoMisa">미사시간 보기</i></td>
                                    <td><a href="temple.html">강화</a></td>
                                    <td>냉정리</td>
                                    <td>김OO</td>
                                    <td>인천시 강화군 선원면 철종외가길 44번길 6</td>
                                    <td>시내버스(일반) : 3, 4, 5, 6, 17-1, 22, 23, 33, 41, 45, 46&nbsp;</td>
                                    <td><span class="tels">032 . 762 . 7613</span><a href="tel:0327627613" class="sendTel">032. 762. 7613</a></td>
                                    <td class="file"><a href="#none"><img src="/img/sub/_ico/board_pin.png" alt=""></a></td>
                                </tr>
                                <tr class="answer" style="display: none;">
                                    <td colspan="9">
                                        <span>
                                            <span>
                                                <i class="sun">일</i>
                                                <em>오전 10시 (평일) , 오후 3시 (초등부) , 오후 7시 (중고등부)</em>
                                            </span>
                                            <span>
                                                <i>월</i>
                                                <em>오전 10시 (평일) , 오후 3시 (초등부) , 오후 7시 (중고등부)</em>
                                            </span>
                                            <span>
                                                <i>화</i>
                                                <em>오전 10시 (평일) , 오후 3시 (초등부) , 오후 7시 (중고등부)</em>
                                            </span>
                                            <span>
                                                <i>수</i>
                                                <em>오전 10시 (평일) , 오후 3시 (초등부) , 오후 7시 (중고등부)</em>
                                            </span>
                                            <span>
                                                <i>목</i>
                                                <em>오전 10시 (평일) , 오후 3시 (초등부) , 오후 7시 (중고등부)</em>
                                            </span>
                                            <span>
                                                <i>금</i>
                                                <em>오전 10시 (평일) , 오후 3시 (초등부) , 오후 7시 (중고등부)</em>
                                            </span>
                                            <span>
                                                <i class="sat">토</i>
                                                <em>오전 10시 (평일) , 오후 3시 (초등부) , 오후 7시 (중고등부)</em>
                                            </span>
                                            <span>
                                                <i>비고</i>
                                                <em>첫째주 토요일은 성모신심, 유아영성체는 매달 첫주 일요일</em>
                                            </span>
                                        </span>
                                    </td>
                                </tr>
                                 -->
                            </tbody>
                        </table>
                    </div>
                    </c:if>
                     
                    <!--  -->
                    <c:if test="${fn:length(DATA_ORGA)>0}">
                    <h3 class="ttl tb fl">기관 / 단체</h3>
                    <p class="tbMore fr"><a href="#"><c:if test="${fn:length(DATA_ORGA)>0}"> <!-- + 더보기 --></c:if></a></p>
                    <div class="boardList oflow v3">
                        <table>
                            <caption>수도회/선교회 리스트 전체보기</caption>
                            <colgroup>
                                <col style="width:8%">
                                <col>
                                <col style="width:12%">
                                <col style="width:12%">
                                <col style="width:30%">
                                <col style="width:10%">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col">No.</th>
                                    <th scope="col">기관명</th>
                                    <th scope="col">전화번호</th>
                                    <th scope="col">팩스</th>
                                    <th scope="col">주소</th>
                                    <th scope="col">메일</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${DATA_ORGA}" var="row">
                                <tr>
                                    <th scope="row">${row.RNUM }</th>
                                    <td><c:if test="${row.HOMEPAGE ne ''}"><a href="${row.HOMEPAGE}" target="_blank" title="새창"></c:if>${row.NAME }</a></td>
                                    <td><span class="tels">${row.TEL }</span><a href="tel:${row.TEL }" class="sendTel">${row.TEL }</a></td>
                                    <td>${row.FAX }</td>
                                    <td>${row.ADDR }</td>
                                    <td class="file"><c:if test="${fn:length(row.EMAIL) > 0}"><a href="mailto: ${row.EMAIL }"><img src="/img/sub/_ico/board_mail.png" alt=""></a></c:if></td>
                                </tr>
                                </c:forEach><!-- 
                                <tr>
                                    <th scope="row">4</th>
                                    <td>꼰벤뚜알 프란치스코회(인천성글라라수도원)</td>
                                    <td><span class="tels">032-542-2625</span><a href="tel:032-542-2625" class="sendTel">032-542-2625</a></td>
                                    <td>032-542-2625</td>
                                    <td>인천광역시 계양구 계양산로 35번길 12-62 (계산2동)</td>
                                    <td class="file"><a href="mailto:name@naver.com"><img src="/img/sub/_ico/board_mail.png" alt=""></a></td>
                                </tr>
                                 -->
                            </tbody>
                        </table>
                    </div>
                    </c:if>
                    
                    <!--  -->
                    <c:if test="${fn:length(DATA_OLDPRIEST)>0}">
                    <h3 class="ttl tb fl">선종사제</h3>
                    <p class="tbMore fr"><a href="#"><c:if test="${fn:length(DATA_OLDPRIEST)>0}"> <!-- + 더보기 --></c:if></a></p>
                    <div class="movieList">
                        <ul class="clearfix">
                           <c:forEach items="${DATA_OLDPRIEST}" var="row">
                           <li>
                                <a href="/father/holy_view.do?bp_idx=${row.BP_IDX }">
                                    <img src="/upload/de_father/${row.IMAGE}" alt="">
                                    <span class="bod">${row.NAME } ${row.CHRISTIAN_NAME }</span>
                                    <em>생일 : ${row.BIRTHDAY }
                                        <br>서품일 : ${row.ORDINATION }
                                        <br>선종일 : ${row.DEAD }
                                        <br>묘소 : ${row.BRIAL_PLACE }
                                    </em>
                                </a>
                            </li>
                            </c:forEach>
                            <!-- 
                            <li>
                                <a href="holy_view.html">
                                    <img src="/img/sub/_ico/board_list_test.gif" alt="테스트용 이미지">
                                    <span class="bod">속주석 안셀모</span>
                                    <em>생일 : 1941-03-13
                                        <br>서품일 : 1965-12-27 
                                        <br>선종일 : 2016-08-24
                                        <br>묘소 : 하늘의 문 묘원 성직자 묘역
                                    </em>
                                </a>
                            </li>
                             -->
                        </ul>
                    </div>
                    </c:if>
                    
                </div>
                <!-- //secCont -->
            </section>
            <!-- //sec01 -->
        </div>
        <!-- //secWrap -->
        <!-- footer -->
        <footer>
            <%@ include file="/_common/inc/footer.jsp" %>
        </footer>
        <!-- //footer -->
    </div>
    <!--// wrap -->

</body>

</html>
