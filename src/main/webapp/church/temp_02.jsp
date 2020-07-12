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
        <div class="secWrap">            
            <!-- sec01 -->
            <section class="sec01 fin">
                <!-- secCont -->
                <div class="secCont">
                    <!-- tabs -->
                    <ul class="tabs">
                        <li><a href="/church/temp_01.do?pageSize=20&pageNo=1&tabs=&qk=&church_idx=">미사시간</a></li>
                        <li class="on"><a href="/church/temp_02.do?pageSize=20&pageNo=1&tabs=&qk=&church_idx=">본당알림</a></li>
                        <li><a href="/church/temp_03.do?pageSize=8&pageNo=1&tabs=&qk=&church_idx=">본당앨범</a></li>
                    </ul>
                    <h3 class="ttl tb">본당알림</h3>
                    <!-- //tabs -->
                    <!-- srchTabs -->
                    <% pageContext.setAttribute("temp_no",   "02"); %> 
                    <%@ include file="/church/temp_tab.jsp" %>
                    <!-- //srchTabs -->
                    <!-- srchSel -->
                    <div class="srchSel noForm">
                    	<form name="churchSearch" id="churchSearch" action="/church/temp_02.do" method="get">
                    	<input type="hidden" name="pageSize" id="pageSize" value="20" />
                    	<input type="hidden" name="pageNo"   id="pageNo"   value="1" />
                    	<c:if test="${SS_WRITEYN eq 'Y' and SS_CHURCHIDX ne '' and SS_GROUPGUBUN eq '3'}">
                    	<!-- 글쓰기 권한이 있다면 버튼을 보이게 한다. START-->
                    	<dl>
                            <dd>
                            	<script>
                            	function gotoWriteNBoardByManager() {
                            		//location.href='/admin/board/church_view_for_user.jsp';
                            		location.href='/church/temp_02_view.jsp?mode=W';
                            	}
                            	</script>
                                <button type="button" id="btnBonNboarWrite"  onclick="javascript: gotoWriteNBoardByManager(); return false;">글쓰기</button>
                            </dd>
                        </dl>
                        <!-- 글쓰기 권한이 있다면 버튼을 보이게 한다. END -->
                        </c:if>
                    	<dl>
                            <dt>본당검색</dt>
                            <dd>
                                <label for=""></label>
                                <select  name="qc" id="qc">
                                    <option value="NAME">성당명</option>
                                </select>
                            </dd>
                            <dd>
                                <label for=""></label>
                                <input type="search" name="qk" id="qk" value="${qk}">
                                <button>검색</button>
                            </dd>
                        </dl>
                        <dl>
                            <dt>정렬</dt>
                            <dd>
                                <label for=""></label>
                                <select name="qo" id="qo">
                                    <option value="">공지사항</option>
                                </select>
                            </dd>
                            
                        </dl>
                        </form>
                    </div>
                    <!-- //srchSel -->
                    <!-- boardList  -->
                    <div class="boardList oflow v2">
                        <table>
                            <caption>본당소식 및 앨범 관련 리스트</caption>
                            <colgroup>
                                <col style="width:8%">
                                <col style="width:8%">
                                <col style="width:8%">
                                <col>
                                <col style="width:8%">
                                <col style="width:15%">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col">No.</th>
                                    <th scope="col">분류</th>
                                    <th scope="col">본당</th>
                                    <th scope="col">제목 </th>
                                    <th scope="col">작성일</th>
                                    <th scope="col">첨부파일</th>
                                </tr>
                            </thead>
                            <tbody>
                <c:choose>
					<c:when test="${fn:length(newsList) > 0}">
						<c:forEach items="${newsList}" var="list" varStatus="status">
										<c:set var="ntcTp" value="" />
                                     	<c:if test = "${list.NOTICE_TYPE eq '1'}">
	                                     	<c:if test = "${list.B_IDX eq '11'}"><c:set var="ntcTp" value="<i class='ico blue'>공지</i>" /></c:if>
	                                     	<c:if test = "${list.B_IDX eq '17'}"><c:set var="ntcTp" value="<i class='ico blue'>소식</i>" /></c:if>
                                     	</c:if>
                                     	<c:if test = "${list.NOTICE_TYPE eq '2'}">
                                     		<c:set var="ntcTp" value="${list.CATEGORY_NAME }" />
                                     	</c:if>
                                     	
								<tr>
                                    <th scope="row">${list.RNUM}</th>
                                    <td>${ntcTp}</td><!--  공지  or 소식 ntcTp ${list.CATEGORY_NAME} -->
                                    <td>${list.CHURCH_NAME}</td>
                                    <td><a href="/church/temp_02_view.do?churchIdx=${list.CHURCH_IDX}&b_idx=${list.B_IDX}&bl_idx=${list.BL_IDX}&bl_idx_pre=${newsList[status.index+1].BL_IDX}&bl_idx_post=${newsList[status.index-1].BL_IDX}">${list.TITLE}</a></td>
                                    <td>${list.REGDATE}</td>
                                    <td class="file">
                                    	<c:if test = "${list.FILE_CNT > 0}">
                                        <a href="javascript: attachedFile_Download ('${list.FILEPATH1}','${list.FILENAME1}','${list.STRFILENAME1}')"><img 
                                           src="/img/sub/_ico/board_download.png" alt=""></a>
                                        </c:if>
                                    </td>
                                </tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<li class="on">
											<tr class="question">
			                                    <th colspan=6>해당 지구 본당의 알림이 없습니다.</th>
			                                </tr>
	                                    </li>
									</c:otherwise>
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
