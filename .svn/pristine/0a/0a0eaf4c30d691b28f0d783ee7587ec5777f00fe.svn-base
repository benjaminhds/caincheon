<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<style>
ONUM { display:none; width:0; }
</style>
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
        <section class="subVisual">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap">
                <article> 
                    <h3>본당현황</h3>
                    <ul>
                        <li>홈</li>
                        <li>본당</li>
                        <li>본당현황</li>
                    </ul>
                </article>
            </div>
        </section>
        <!-- //subVisual -->
        <!-- secWrap -->
        <form name="form01" id="form01" action="/church/temp_01.do" method="get">
        <input type="hidden" name="pageNo"   id="pageNo"   value="1" />
        <div class="secWrap">            
            <!-- sec01 -->
            <section class="sec01 fin">
                <!-- secCont -->
                <div class="secCont">
                    <!-- tabs -->
                    <ul class="tabs">
                        <li class="on"><a href="/church/temp_01.do?pageSize=20&pageNo=1&tabs=&qk=&church_idx=">미사시간</a></li>
                        <li><a href="/church/temp_02.do?pageSize=20&pageNo=1&tabs=&qk=&church_idx=">본당알림</a></li>
                        <li><a href="/church/temp_03.do?pageSize=8&pageNo=1&tabs=&qk=&church_idx=">본당앨범</a></li>
                    </ul>
                    <h3 class="ttl tb">미사시간</h3>
                    <!-- //tabs -->
                    <!-- srchTabs -->
                    <% pageContext.setAttribute("temp_no",   "01"); %>
                    <%@ include file="/church/temp_tab.jsp" %>
                    <!-- //srchTabs -->   
                    <!-- srchForm -->
                    <div class="srchForm">
                    <script>
                    // 
                    function checkSearchCondition(frmName) {
                    	if( $("#srchDiv option:selected").val() == "x" && $("#qk").val().trim()=="" ) { 
                    		if( !confirm("검색 조건 없이 검색하시겠습니까?") ) {
                    			return;
                    		}
                    	} 
                    	if( $("#srchDiv option:selected").val() == "x" && $("#qk").val().trim()!="" ) { 
                    		if( !confirm("미사시간 검색 조건 없이 검색하시겠습니까 ?") ) {
                    			return;
                    		}
                    	} 
                    	goToPageFormId(frmName);
                    	
                    }
                    // selectbox ctl
                    function changeSearchCondition(frmVal) {
                    	if(frmVal=="yoil") {
                    		$("#yoil").show();
                    		$("#time01").hide();
                    		$("#time02").hide();
                    		$("#time03").hide();
                    		$("#time04").hide();
                    		$("#time05").hide();
                    	} else if(frmVal=="time") {
                    		$("#yoil").hide();
                    		$("#time01").show();
                    		$("#time02").show();
                    		$("#time03").show();
                    		$("#time04").show();
                    		$("#time05").show();
                    	}
                    }
                    </script>
                        <dl>
                            <dt>미사시간 검색</dt>
                            <dd>
                                <ol>
                                <c:if test = "${srchDiv eq 'x'}"><c:set var="_x" value="selected='selected'" /><c:set var="_defaultSel" value="x" /></c:if>
                                <c:if test = "${srchDiv eq 'yoil'}"><c:set var="_yoil" value="selected='selected'" /><c:set var="_defaultSel" value="yoil" /></c:if>
                                <c:if test = "${srchDiv eq 'time'}"><c:set var="_time" value="selected='selected'" /><c:set var="_defaultSel" value="time" /></c:if>
                                    <li class="yoil">
                                        <label for=""></label>
                                        <select name="srchDiv" id="srchDiv" onChange="javascript:changeSearchCondition(this.value)">
                                            <option value="x" ${_x}>전체</option>
                                            <option value="yoil" ${_yoil}>요일별</option>
                                            <option value="time" ${_time}>시간대별</option>
                                        </select>
                                    </li>
                                    <li id="yoil" class="yoil">
                                        <label for=""></label>
                                        <select name="yoil" id="yoil">
                                            <option value="0" <c:if test = "${yoil  eq '0'}"><c:out value=" selected='selected'" /></c:if>>일요일</option>
                                            <option value="1" <c:if test = "${yoil  eq '1'}"><c:out value=" selected='selected'" /></c:if>>월요일</option>
                                            <option value="2" <c:if test = "${yoil  eq '2'}"><c:out value=" selected='selected'" /></c:if>>화요일</option>
                                            <option value="3" <c:if test = "${yoil  eq '3'}"><c:out value=" selected='selected'" /></c:if>>수요일</option>
                                            <option value="4" <c:if test = "${yoil  eq '4'}"><c:out value=" selected='selected'" /></c:if>>목요일</option>
                                            <option value="5" <c:if test = "${yoil  eq '5'}"><c:out value=" selected='selected'" /></c:if>>금요일</option>
                                            <option value="6" <c:if test = "${yoil  eq '6'}"><c:out value=" selected='selected'" /></c:if>>토요일</option>
                                        </select>
                                    </li>
                                    <li id="time01" class="hour fst">
                                        <label for=""></label>
                                        <select name="sHour" id="sHour">
                                            <option value="04" <c:if test = "${sHour  eq '04'}"><c:out value=" selected='selected'" /></c:if>>오전 4시</option>
                                            <option value="05" <c:if test = "${sHour  eq '05'}"><c:out value=" selected='selected'" /></c:if>>오전 5시</option>
                                            <option value="06" <c:if test = "${sHour  eq '06'}"><c:out value=" selected='selected'" /></c:if>>오전 6시</option>
                                            <option value="07" <c:if test = "${sHour  eq '07'}"><c:out value=" selected='selected'" /></c:if>>오전 7시</option>
                                            <option value="08" <c:if test = "${sHour  eq '08'}"><c:out value=" selected='selected'" /></c:if>>오전 8시</option>
                                            <option value="09" <c:if test = "${sHour  eq '09'}"><c:out value=" selected='selected'" /></c:if>>오전 9시</option>
                                            <option value="10" <c:if test = "${sHour  eq '10'}"><c:out value=" selected='selected'" /></c:if>>오전 10시</option>
                                            <option value="11" <c:if test = "${sHour  eq '11'}"><c:out value=" selected='selected'" /></c:if>>오전 11시</option>
                                            <option value="12" <c:if test = "${sHour  eq '12'}"><c:out value=" selected='selected'" /></c:if>>오후 12시</option>
                                            <option value="13" <c:if test = "${sHour  eq '13'}"><c:out value=" selected='selected'" /></c:if>>오후 1시</option>
                                            <option value="14" <c:if test = "${sHour  eq '14'}"><c:out value=" selected='selected'" /></c:if>>오후 2시</option>
                                            <option value="15" <c:if test = "${sHour  eq '15'}"><c:out value=" selected='selected'" /></c:if>>오후 3시</option>
                                            <option value="16" <c:if test = "${sHour  eq '16'}"><c:out value=" selected='selected'" /></c:if>>오후 4시</option>
                                            <option value="17" <c:if test = "${sHour  eq '17'}"><c:out value=" selected='selected'" /></c:if>>오후 5시</option>
                                            <option value="18" <c:if test = "${sHour  eq '18'}"><c:out value=" selected='selected'" /></c:if>>오후 6시</option>
                                            <option value="19" <c:if test = "${sHour  eq '19'}"><c:out value=" selected='selected'" /></c:if>>오후 7시</option>
                                            <option value="20" <c:if test = "${sHour  eq '20'}"><c:out value=" selected='selected'" /></c:if>>오후 8시</option>
                                            <option value="21" <c:if test = "${sHour  eq '21'}"><c:out value=" selected='selected'" /></c:if>>오후 9시</option>
                                            <option value="22" <c:if test = "${sHour  eq '22'}"><c:out value=" selected='selected'" /></c:if>>오후 10시</option>
                                            <option value="23" <c:if test = "${sHour  eq '23'}"><c:out value=" selected='selected'" /></c:if>>오후 11시</option>
                                        </select>
                                    </li>
                                    <li id="time02" class="min">
                                        <label for=""></label>
                                        <select name="sMin" id="sMin">
                                            <option value="00" <c:if test = "${sMin  eq '00'}"><c:out value=" selected='selected'" /></c:if>>00분</option>
                                            <option value="10" <c:if test = "${sMin  eq '10'}"><c:out value=" selected='selected'" /></c:if>>10분</option>
                                            <option value="20" <c:if test = "${sMin  eq '20'}"><c:out value=" selected='selected'" /></c:if>>20분</option>
                                            <option value="30" <c:if test = "${sMin  eq '30'}"><c:out value=" selected='selected'" /></c:if>>30분</option>
                                            <option value="40" <c:if test = "${sMin  eq '40'}"><c:out value=" selected='selected'" /></c:if>>40분</option>
                                            <option value="50" <c:if test = "${sMin  eq '50'}"><c:out value=" selected='selected'" /></c:if>>50분</option>
                                        </select>      
                                    </li>
                                    <li id="time03" class="wave">~</li>
                                    <li id="time04" class="hour">
                                        <label for=""></label>
                                        <select name="eHour" id="eHour">
                                            <option value="04" <c:if test = "${eHour  eq '04'}"><c:out value=" selected='selected'" /></c:if>>오전 4시</option>
                                            <option value="05" <c:if test = "${eHour  eq '05'}"><c:out value=" selected='selected'" /></c:if>>오전 5시</option>
                                            <option value="06" <c:if test = "${eHour  eq '06'}"><c:out value=" selected='selected'" /></c:if>>오전 6시</option>
                                            <option value="07" <c:if test = "${eHour  eq '07'}"><c:out value=" selected='selected'" /></c:if>>오전 7시</option>
                                            <option value="08" <c:if test = "${eHour  eq '08'}"><c:out value=" selected='selected'" /></c:if>>오전 8시</option>
                                            <option value="09" <c:if test = "${eHour  eq '09'}"><c:out value=" selected='selected'" /></c:if>>오전 9시</option>
                                            <option value="10" <c:if test = "${eHour  eq '10'}"><c:out value=" selected='selected'" /></c:if>>오전 10시</option>
                                            <option value="11" <c:if test = "${eHour  eq '11'}"><c:out value=" selected='selected'" /></c:if>>오전 11시</option>
                                            <option value="12" <c:if test = "${eHour  eq '12'}"><c:out value=" selected='selected'" /></c:if>>오후 12시</option>
                                            <option value="13" <c:if test = "${eHour  eq '13'}"><c:out value=" selected='selected'" /></c:if>>오후 1시</option>
                                            <option value="14" <c:if test = "${eHour  eq '14'}"><c:out value=" selected='selected'" /></c:if>>오후 2시</option>
                                            <option value="15" <c:if test = "${eHour  eq '15'}"><c:out value=" selected='selected'" /></c:if>>오후 3시</option>
                                            <option value="16" <c:if test = "${eHour  eq '16'}"><c:out value=" selected='selected'" /></c:if>>오후 4시</option>
                                            <option value="17" <c:if test = "${eHour  eq '17'}"><c:out value=" selected='selected'" /></c:if>>오후 5시</option>
                                            <option value="18" <c:if test = "${eHour  eq '18'}"><c:out value=" selected='selected'" /></c:if>>오후 6시</option>
                                            <option value="19" <c:if test = "${eHour  eq '19'}"><c:out value=" selected='selected'" /></c:if>>오후 7시</option>
                                            <option value="20" <c:if test = "${eHour  eq '20'}"><c:out value=" selected='selected'" /></c:if>>오후 8시</option>
                                            <option value="21" <c:if test = "${eHour  eq '21'}"><c:out value=" selected='selected'" /></c:if>>오후 9시</option>
                                            <option value="22" <c:if test = "${eHour  eq '22'}"><c:out value=" selected='selected'" /></c:if>>오후 10시</option>
                                            <option value="23" <c:if test = "${eHour  eq '23'}"><c:out value=" selected='selected'" /></c:if>>오후 11시</option>
                                        </select>
                                    </li>
                                    <li id="time05" class="min">
                                        <label for=""></label>
                                        <select name="eMin" id="eMin">
                                            <option value="00" <c:if test = "${eMin  eq '00'}"><c:out value=" selected='selected'" /></c:if>>00분</option>
                                            <option value="10" <c:if test = "${eMin  eq '10'}"><c:out value=" selected='selected'" /></c:if>>10분</option>
                                            <option value="20" <c:if test = "${eMin  eq '20'}"><c:out value=" selected='selected'" /></c:if>>20분</option>
                                            <option value="30" <c:if test = "${eMin  eq '30'}"><c:out value=" selected='selected'" /></c:if>>30분</option>
                                            <option value="40" <c:if test = "${eMin  eq '40'}"><c:out value=" selected='selected'" /></c:if>>40분</option>
                                            <option value="50" <c:if test = "${eMin  eq '50'}"><c:out value=" selected='selected'" /></c:if>>50분</option>
                                        </select>
                                    </li>
                                </ol>
                            </dd>
                        </dl>
                    </div>
                    <!-- //srchForm -->
                    <!-- srchSel -->
                    <div class="srchSel">
                        <dl>
                            <dt>본당검색</dt>
                            <dd>
                                <label for=""></label>
                                <select name="qc" id="qc">
                                    <option value="">성당명</option>
                                </select>
                            </dd>
                            <dd>
                                <label for=""></label>
                                <input type="search" name="qk" id="qk" value="${qk}">
                                <button onClick='checkSearchCondition("form01"); return false;'>검색</button>
                            </dd>
                        </dl>
                    </div>
                    <script>
                    changeSearchCondition("${_defaultSel}");
                    </script>
                    
                    <!-- //srchSel -->
                    <!-- BoardList -->
                    <div class="boardList oflow faq">
                        <table>
                            <caption>미사시간 faq</caption>
                            <colgroup>
                                <col style="width:12%">
                                <col style="width:15%">
                                <col style="width:15%">
                                <col>
                                <col style="width:15%">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col">성당명</th>
                                    <th scope="col">미사시간</th>
                                    <th scope="col">사제명</th>
                                    <th scope="col">주소</th>
                                    <th scope="col">전화번호</th>
                                </tr>
                            </thead>
                            <tbody>
	                            <c:choose>
									<c:when test="${fn:length(vacancyList) > 0}">
										<c:forEach items="${vacancyList}" var="list">
										
			                                <tr class="question">
			                                    <th><a href="/church/church.do?churchIdx=${list.CHURCH_IDX}">${list.CHURCH_NAME}</a></td>
			                                    <td><i class="icoMisa">미사시간 보기</i></td>
			                                    <td>${list.P_NAMES}</td>
			                                    <td>${list.ADDR}</td>
			                                    <td>${list.TEL}</td>
			                                </tr>
			                                
			                                <tr class="answer">
			                                    <td colspan="5">
			                                        <span>
				                                <!-- misa time print-out START -->
		                                        <c:forEach var="entry" items="${misaList}" varStatus="status">
			                                        <c:if test = "${entry.key == list.CHURCH_IDX}">
			                                        	<c:set var="misaInfoList" value="${entry.value}" />
			                                        	<c:forEach var="misaInfo" items="${misaInfoList}" varStatus="status">
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
		                                        <!-- misa time print-out END -->
			                                        </span>
			                                    </td>
			                                </tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<li class="on">
											<tr class="question">
			                                    <th colspan=5>No List!</th>
			                                </tr>
	                                    </li>
									</c:otherwise>
								</c:choose>	      
                            </tbody>
                            
                        </table>
                    </div>
                    <!-- //BoardList -->
                    <!-- arrow -->
					<%@ include file="/_common/inc/paging2.jsp" %>
                    <!-- //arrow -->
                </div>
                <!-- //secCont -->
            </section>
            <!-- //sec01 -->
        </div>
        </form>
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
