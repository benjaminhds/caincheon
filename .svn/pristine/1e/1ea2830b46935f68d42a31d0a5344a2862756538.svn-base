<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
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
                    <h3>공소</h3>
                    <ul>
                        <li>홈</li>
                        <li>본당</li>
                        <li>공소</li>
                    </ul>
                </article>
            </div>
        </section>
        <!-- //subVisual -->
        <!-- secWrap -->
        <form name="form01" id="form01" action="/church/vacancy.do" method="get">
        <input type="hidden" name="pageNo"   id="pageNo"   value="1" />
        <div class="secWrap">            
            <!-- sec01 -->
            <section class="sec01 fin">
                <!-- secCont -->
                <div class="secCont">
                    <h3 class="ttl fl">공소정보</h3>                  
                    <!-- srchSel -->
                    <div class="srchSel">
                        <dl>
                            <dt>정렬</dt>
                            <dd>
                                <label for=""></label>
                                <select name="qo" id="qo">
                                    <option value="B.NAME">관할본당</option>
                                    <option value="A.NAME">공소명</option>
                                </select>
                            </dd>
                            <dt>본당검색</dt>
                            <dd>
                                <label for=""></label>
                                <select name="qc" id="qc">
                                    <option value="CHURCH_NAME">성당명</option>
                                </select>
                            </dd>
                            <dd>
                                <label for=""></label>
                                <input type="search" name="qk" id="qk" value="${qk}">
                                <button onClick='goToPageFormId("form01"); return false;'>검색</button>
                            </dd>
                        </dl>
                    </div>
                    <!-- //srchSel -->
                    <!-- boardList  -->
                    <div class="boardList oflow faq">
                        <table>
                            <caption>공소 리스트</caption>
                            <colgroup>
                                <col style="width:4%">
                                <col style="width:15%">
                                <col style="width:7%">
                                <col style="width:7%">
                                <col style="width:7%">
                                <col style="width:25%">
                                <col>
                                <col style="width:12%">
                                <col style="width:5%">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col">No.</th>
                                    <th scope="col">미사시간</th>
                                    <th scope="col">관할본당</th>
                                    <th scope="col">공소명</th>
                                    <th scope="col">공소회장</th>
                                    <th scope="col">공소주소</th>
                                    <th scope="col">전화번호</th>
                                    <th scope="col">비고</th>
                                    <th scope="col">위치</th>
                                </tr>
                            </thead>
                            <tbody>
	                            <c:choose>
								<c:when test="${fn:length(vacancyList) > 0}">
								<c:forEach items="${vacancyList}" var="row">
							
								<tr class="question">
                                    <th scope="row">${row.RNUM}</th>
                                    
                                    <c:forEach items="${misaList}" var="entry" varStatus="status2">
                                    <c:if test = "${entry.key eq row.G_IDX}">
	                                    <c:if test = "${fn:length(entry.value) == 0}"><td> </td></c:if>
	                                    <c:if test = "${fn:length(entry.value)  > 0}"><td><i class="icoMisa">미사시간 보기</i></td></c:if>
                                    </c:if>
                                    </c:forEach>
                                    
                                    <td><a href="church.do?churchIdx=${row.CHURCH_IDX}">${row.CHURCH_NAME}</a></td>
                                    <td>${row.GONGSO_NAME}</td>
                                    <td>${row.CHIEF_NAME}</td>
                                    <td>${row.ADDR}</td>
                                    <td>${row.TEL}</td>
                                    <td>${row.ETC}</td>
                                    <td class="file"><c:if test="${fn:length(row.TRAFFIC) > 0}"><a 
                                    href="${row.TRAFFIC}" target="_new"><img src="/img/sub/_ico/board_pin.png" alt=""></a></c:if></td>
                                </tr>
                                 
                                       
                                <tr class="answer">
                                    <td colspan="9">
                                    	<span>
                                    	<c:forEach var="entry" items="${misaList}" varStatus="status2">
                                         	<!-- misa time print-out START -->
                                        	<c:if test = "${entry.key eq row.G_IDX and fn:length(entry.value) > 0}">
                                        	<c:forEach var="misaInfo" items="${entry.value}" varStatus="status3">
                                     	    <c:if test = "${misaInfo.MNAME ne '' and misaInfo.MNAME ne 'null'}">
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
                                		    <!-- misa time print-out END -->
                                 			</c:forEach>
                                        </span>
                                    </td>
                                </tr>
                            </c:forEach>
							</c:when>
							</c:choose>
                            </tbody>
                        </table>
                    </div>
                    <!-- //boardList  -->
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