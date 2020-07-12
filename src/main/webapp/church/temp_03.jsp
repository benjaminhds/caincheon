<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<script type="text/javascript">
function goContents(b_idx, bl_idx, church_idx, qc,qk,qo) {
	var vfm = document.form00;
	vfm.action = '/church/temp_03_view.do';
	vfm.b_idx.value=b_idx;
	vfm.bl_idx.value=bl_idx;
	vfm.church_idx.value=church_idx;
	vfm.qc.value=qc;
	vfm.qk.value=qk;
	vfm.qo.value=qo;
	vfm.submit();
    return false;
}
</script>
<body>
<form name="form00" action="/church/temp_03.do" method="POST">
<input type="hidden" name="church_group" id="church_group" value="${tabs }" />"/>
<input type="hidden" name="church_idx" id="church_idx"/>
<input type="hidden" name="qc" id="qc"/>
<input type="hidden" name="qk" id="qk"/>
<input type="hidden" name="qo" id="qo"/>
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo }"/>
<input type="hidden" name="b_idx" id="b_idx"/>
<input type="hidden" name="bl_idx" id="bl_idx"/>
</form>
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
                        <li><a href="/church/temp_02.do?pageSize=20&pageNo=1&tabs=&qk=&church_idx=">본당알림</a></li>
                        <li class="on"><a href="/church/temp_03.do?pageSize=8&pageNo=1&tabs=&qk=&church_idx=">본당앨범</a></li>
                    </ul>
                    <h3 class="ttl tb">본당앨범</h3>
                    <!-- //tabs -->
                    <!-- srchTabs -->
                    <% pageContext.setAttribute("temp_no",   "03"); %>
                    <%@ include file="/church/temp_tab.jsp" %>
                    <!-- //srchTabs -->
                    <!-- srchSel -->
                    <div class="srchSel noForm">
                    	<form name="search" id="search" action="/church/temp_03.do" method="get">
                    	<input type="hidden" name="pageSize" id="pageSize" value="8" />
                    	<input type="hidden" name="pageNo"   id="pageNo"   value="${pageNo}" />
                    	<c:if test="${SS_WRITEYN eq 'Y' and SS_CHURCHIDX ne ''  and SS_GROUPGUBUN eq '3'}">
                    	<!-- 글쓰기 권한이 있다면 버튼을 보이게 한다. START-->
                    	<dl>
                            <dd>
                            	<script>
                            	function gotoWriteNBoardByManager() {
                            		//location.href='/admin/board/church_view_for_user.jsp';
                            		location.href='/church/temp_03_view.jsp?mode=W';
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
                    <!-- movieList -->
                    <div class="movieList">
                        <ul class="clearfix">
                        
                        <c:choose>
							<c:when test="${fn:length(LIST) > 0}">
								<c:forEach items="${LIST}" var="list">
                               	<li><a href="javascript: goContents('${list.B_IDX }','${list.BL_IDX }','${list.CHURCH_IDX}','${qc}','${qk}','${qo}')">
                               	<img src="${list.FILEPATH1 }thumbnail/${list.STRFILENAME1}"><span>
                               	<i class="blue">[${list.CHURCH_NAME} 성당]</i>
                               	 ${list.TITLE}</span><em>${list.EVENT_DATE}</em></a></li>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<li class="on">해당 지구 본당의 알림이 없습니다.</li>
							</c:otherwise>
						</c:choose>
                        </ul>
                    </div>
                    <!-- movieList -->
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
