<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<body class="loading">
    <p class="skipNavi" tabindex="1">본문바로가기</p>
    <!-- wrap -->
    <div id="wrap">
        <!-- header -->
        <header>
            <%@include file="/_common/inc/gnb.jsp" %>
        </header>
        <!-- //header -->
        <!-- subVisual -->
        <section class="subVisual intro">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap">
                <article>
                    <h3>온라인역사관</h3>
                    <ul>
                        <li>홈</li>
                        <li>교구안내</li>
                        <li>온라인역사관</li>
                    </ul>
                </article>
            </div>
        </section>
        <!-- //subVisual -->
        <!-- secWrap -->
        <div class="secWrap">
            <!-- sec01 -->
            <section class="sec01">
                <!-- secWide -->
                <div class="secWide">
                    <!-- visuals -->
                    <div class="visuals">
                        <div class="naviArea flexslider">
                            <ul class="navis slides">
                            <c:choose>
                            <c:when test="${fn:length(categoryList) > 0}">
                              <c:forEach items="${categoryList}" var="list" varStatus="status">
                                <li <c:if test="${status.index eq 0 }">class="on"</c:if> >
                                  <a id="${list.CATEGORY_CODE}" alt="num${status.index}" href="#none" onclick="javascript: queryHistoryEventsYears( ${list.CATEGORY_CODE} )">${list.CATEGORY_NAME}</a>
                                </li>
                              </c:forEach>
                            </c:when>
                            </c:choose>
                            </ul>
                            <!-- <a href="#none" class="btnLeft bt-arr"><img src="/img/sub/slide_left.png" alt=""></a>
                            <a href="#none" class="btnRight bt-arr"><img src="/img/sub/slide_right.png" alt=""></a> -->
                        </div>
                        <div class="imgArea flexslider">
                            <ul class="vis slides">
                                <c:choose>
                                <c:when test="${fn:length(categoryList) > 0}">
                                  <c:forEach items="${categoryList}" var="list" varStatus="status">
                                    <li>
                                      <img src="${list.FILE_PATH}${list.IMAGE}" alt="">
                                    </li>
                                  </c:forEach>
                                </c:when>
                                </c:choose>
                            </ul>
                        </div>
                    </div>
                    <!-- //visuals -->
                </div>
                <!-- //secWide -->
            </section>
            <!-- //sec01 -->
            <!-- sec02 -->
            <section class="sec02 fin">
                <!-- secCont -->
                <div class="secCont histories">
                    <!-- yearWrapper -->
                    <div class="yearWrapper flexslider">
                        
                        
                        	<c:if test="${fn:length(categoryList) > 0}">
                              <c:forEach items="${categoryList}" var="list" varStatus="status">
                                <c:set value="${list.CATEGORY_CODE}"      var="CATE_CODE" />
                                <c:set value="${historyYears[CATE_CODE]}" var="YEAR_ROWS" />
                                <c:if test="${status.index == 0}"><c:set value="${list.CATEGORY_CODE}" var="CATEGORY_CODE_1TH" /></c:if>
                                
                                <!-- year list per category -->
                                <ul class="years slides" id="CATEYEAR_${CATE_CODE}">
                                <c:if test="${fn:length(YEAR_ROWS) > 0}">
                                	<c:forEach items="${YEAR_ROWS}" var="list_YEAR" varStatus="status">
                                	<li><a href="#none">${list_YEAR.EVENTYEAR}</a></li>
                                	</c:forEach>
                                </c:if>
                                </ul>
                                <script> $("#CATEYEAR_${CATE_CODE}").hide();</script>
                                
                              </c:forEach>
                            </c:if>
                        
                    </div>
                    <!-- //yearWrapper -->
                    <!-- yearWrap y0 -->
                    <div class="yearWrap on">
                        <!-- yearCont-->
                        
                        
                        <c:if test="${fn:length(categoryList) > 0}">
                          <c:forEach items="${categoryList}" var="list" varStatus="status1">
                            
                            <c:set value="${list.CATEGORY_CODE}"      var="aCATEGORY_CODE" />
                            <c:set value="${historyYears[aCATEGORY_CODE]}"      var="aYEAR_MAP" />
	                        	<c:forEach items="${aYEAR_MAP}" var="aROW" varStatus="status2">
	                        		<c:set value="${ aCATEGORY_CODE}_${aROW.EVENTYEAR }"   var="eventListKEY" />
	                        		
	                        		<c:set value="${ historyEventsList[eventListKEY]}"     var="EVENT_LIST" />
	                        		
	                        		<div class="yearCont secCont " id="years_${eventListKEY }" data-category="${aCATEGORY_CODE}" data-year="${aROW.EVENTYEAR}">
	                        		
	                        		<c:forEach items="${EVENT_LIST}" var="aROW" varStatus="status3">
	                        			<dl>
                                            <dt>${aROW.EVENTYEAR}</dt>
                                            <dd>
                                                <ul>
                                                    <c:choose>
                                                        <c:when test="${fn:length(EVENT_LIST) > 0}">
                                                            <c:forEach items="${EVENT_LIST}" var="evt_row" varStatus="status">
                                                                <li class='qus' id="li_qus_years_${eventListKEY }">
                                                                    <font color="#2368ad">${evt_row.EVENTMMDD}</font> &nbsp; ${evt_row.TITLE }</li>
                                                                <li class='ans'>
                                                                    <c:if test="${fn:length(evt_row.IMAGE) > 0}">
                                                                        <img src="${evt_row.FILE_PATH}${evt_row.IMAGE}">
                                                                    </c:if>${evt_row.CONTENTS }</li>
                                                            </c:forEach>
                                                        </c:when>
                                                    </c:choose>
                                                </ul>
                                            </dd>
                                        </dl>
	                        		</c:forEach>
	                        		</div>
	                        		
	                            </c:forEach>
	                      </c:forEach>
	                    </c:if>
                        
						<!-- //--------------- -->
                </div>
                <!-- //secCont -->
            </section>
            <!-- //sec02 -->
        </div>
        <!-- //secWrap -->
        <!-- footer -->
        <footer>
            <%@ include file="/_common/inc/footer.jsp" %>
        </footer>
        <!-- //footer -->
    </div>
    <!--// wrap -->
    <script src="/_common/js/jquery.flexslider-min.js"></script>
    <script src="/_common/js/slide_new.js"></script>
    <script>
    var CATEYEAR_NUMS = [
		<c:if test="${fn:length(categoryList) > 0}">
        <c:forEach items="${categoryList}" var="list" varStatus="status">
        <c:if test="${status.index > 0}">,</c:if> ${list.CATEGORY_CODE}
        </c:forEach>
        </c:if>
	];
    var CATEYEAR_YEAR_ALL = [
		<c:set var="comma" value=""/>
		<c:if test="${fn:length(categoryList) > 0}">
        <c:forEach items="${categoryList}" var="listC" varStatus="status1">
        	<c:set var="CATE"  value="${listC.CATEGORY_CODE}" /> <c:set var="Y_MAP" value="${historyYears[CATE]}" />
       		<c:forEach items="${Y_MAP}" var="yearM" varStatus="status2">
           		${comma} "years_${yearM.CATEGORY_CODE}_${yearM.EVENTYEAR}"
   				<c:set var="exitForEach" value="true"/> <c:set var="comma" value=","/>
       		</c:forEach>
        </c:forEach>
        </c:if>
	];
	var CATEYEAR_YEAR_FIRST = [
		<c:set var="comma" value=""/>
		<c:if test="${fn:length(categoryList) > 0}">
        <c:forEach items="${categoryList}" var="listC" varStatus="status1">
        	<c:set var="CATE"  value="${listC.CATEGORY_CODE}" /> <c:set var="Y_MAP" value="${historyYears[CATE]}" />
        	<c:set var="exitForEach" value="false"/>
       		<c:forEach items="${Y_MAP}" var="yearM" varStatus="status2">
       			<c:if test="${exitForEach ne true}">
       				${comma} "years_${yearM.CATEGORY_CODE}_${yearM.EVENTYEAR}"
       				<c:set var="exitForEach" value="true"/> <c:set var="comma" value=","/>
       			</c:if>
       		</c:forEach>
        </c:forEach>
        </c:if>
	];
	
	// 
    function queryHistoryEventsYears(cate_code) {
    	// but, 현재(180329) 기준으로 전체 조회 결과에서 카테고리별로 처리
    	for(i=0; i<CATEYEAR_NUMS.length ; i++) {
    		$("#CATEYEAR_"+CATEYEAR_NUMS[i]).hide();
    	}
    	var cyn = "#CATEYEAR_"+cate_code;
    	$(cyn).show();
    	
    	// 해당년도의 목록을 보여준다. :: years_6_2011
    	
    	for(i=0; i<CATEYEAR_YEAR_ALL.length ; i++) {
    		$("#"+CATEYEAR_YEAR_ALL[i]).removeClass("ons");
			$("#li_qus_"+CATEYEAR_YEAR_ALL[i]).removeClass("on");//memo) 180401 : 해당 함수가 적용되지 않고 있음.(css에서 적용되고 있음.)
			console.log(" x1 -> " + "#li_qus_"+CATEYEAR_YEAR_ALL[i] + " : removeClass on" );
    	}
    	for(i=0; i<CATEYEAR_YEAR_FIRST.length ; i++) {
    		if(CATEYEAR_YEAR_FIRST[i].indexOf("years_"+cate_code+"_")==0) {
    			$("#"+CATEYEAR_YEAR_FIRST[i]).addClass("ons");
    			$("#li_qus_"+CATEYEAR_YEAR_FIRST[i]).addClass("on");
    			console.log(" x2 -> " + "#li_qus_"+CATEYEAR_YEAR_FIRST[i] + " : addClass on" );
    			break;
    		}
    	}
    }
    // selected at loaded
	queryHistoryEventsYears( CATEYEAR_NUMS[0] );
    </script>
</body>

</html>
