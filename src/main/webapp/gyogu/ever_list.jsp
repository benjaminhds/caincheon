<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<script type="text/javascript">
function goPage(pageNo) {
	document.getElementById('pageNo').value=pageNo;
	document.form01.submit();
    return false;
}

function goSearch() {
	document.form01.submit();
    return false;
}

/* function selectChange() {
	if(document.getElementById('searchGubun').value == 'all') {
		document.getElementById('searchText').style.display = "none";
		document.getElementById('searchYear1').style.display = "none";
		document.getElementById('dash').style.display = "none";
		document.getElementById('searchYear2').style.display = "none";
		
	}else if(document.getElementById('searchGubun').value == 'date') {
		document.getElementById('searchText').style.display = "none";
		document.getElementById('searchYear1').style.display = "block";
		document.getElementById('dash').style.display = "block";
		document.getElementById('searchYear2').style.display = "block";
		
	} else if(document.getElementById('searchGubun').value == 'contents') {
		document.getElementById('searchYear1').style.display = "none";
		document.getElementById('dash').style.display = "none";
		document.getElementById('searchYear2').style.display = "none";
		document.getElementById('searchText').style.display = "block";
	}
} */

function viewMsg (type, m_idx) {
	document.form01.action = '/gyogu/ever_view.do';
	document.getElementById('type').value=type;
	document.getElementById('m_idx').value=m_idx;
	document.form01.submit();
    return false;
}

function viewAlbum (bl_idx) {
	//document.form01.action = '/news/alb_view.do';
	document.form01.action = '/gyogu/ever_view.do';
	document.getElementById('bl_idx').value=bl_idx;
	document.form01.submit();
    return false;
}
/*
function viewAlbum (bl_idx, c_idx) {
	document.form01.action = '/gyogu/ever_view.do';
	document.getElementById('c_idx').value=c_idx;
	document.getElementById('bl_idx').value=bl_idx;
	document.form01.submit();
    return false;
}
*/
window.onload = function () {
//	 alert("로딩 완료");
//	selectChange();
}

</script>
<body>
<form name="form01" action="/gyogu/ever_list.do" method="POST">
<input type="hidden" name="bl_idx" id="bl_idx"/>
<input type="hidden" name="c_idx"  id="c_idx" value="1"/>
<input type="hidden" name="b_idx"  id="b_idx" value="${b_idx}"/>
<input type="hidden" name="m_idx"  id="m_idx" value="${m_idx}"/>
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}"/>

    <p class="skipNavi" tabindex="1">본문바로가기</p>
    <!-- wrap -->
    <div id="wrap">
        <!-- header -->
        <header>
            <%@include file="/_common/inc/gnb.jsp" %>
        </header>
        <!-- //header -->
        <!-- subVisual -->
        <section class="subVisual gyogu">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap">
                <article>
                    <h3>역대교구장</h3>
                    <ul>
                        <li>홈</li>
                        <li>교구장</li>
                        <li>역대교구장</li>
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
                        <li class="on"><a href="/gyogu/ever_list.do?c_idx=1&b_idx=ALL&searchGubun=all">제 1대 교구장</a></li>
                        <li><a href="/gyogu/ever_list.do?c_idx=2&b_idx=ALL&searchGubun=all">제 2대 교구장</a></li>
                    </ul>
                    <!-- //tabs -->
                    <h3 class="ttl tb">제 1대 교구장</h3>
                    <article class="boxHalf">
                        <div>
                            <img src="/img/sub/gyogu_ppl.jpg" alt="">
                            <p class="center">윌리엄 제이 맥 나흐톤 굴리엘모 주교</p>
                        </div>
                        <div>
                            <ol class="history bdrLine">
                                <li><em>1926. 12. 7.</em><span>출생</span></li>
                                <li><em>1953. 6. 4.</em><span>메니뇰 대신학교 종교교육 석사</span></li>
                                <li><em>1953. 6. 13.</em><span>사제수품</span></li>
                                <li><em>1954. 7. 22.</em><span>내한(부산도착)</span></li>
                                <li><em>1954. 8. 13. ~ 1955. 9. 1.</em><span>충북 장호원 성당 보좌 신부</span></li>
                                <li><em>1955. 9. 2. ~ 1955. 9. 27.</em><span>북 북문로 성당 보좌 신부 </span></li>
                                <li><em>1955. 9. 28. ~ 1957. 8. 14.</em><span>충북 북문로 성당 주임 신부</span></li>
                                <li><em>1957. 8. 15. ~ 1960. 7. 15.</em><span>충북 내덕동 성당 주임 신부</span></li>
                                <li><em>1961. 6. 6.</em><span>인천대목구장 주교 임명(투부르보 명의주교)</span></li>
                                <li><em>1961. 8. 24.</em><span>주교수품 </span></li>
                                <li><em>1962. 2. 5. ~ 1964. 2. 4.</em><span>재단법인 인천 박문여자중학교 이사 </span></li>
                                <li><em>1962. 3. 10.</em><span>인천교구장 전보일 </</span>
                                <li><em>1962. 11. 14.</em><span>재단법인 인천교구 천주교 유지재단 이사장 </span></li>
                                <li><em>1964. 2. 5.</em><span>학교법인 인천가톨릭교육재단 이사장</span></li>
                                <li><em>1965. 2. ~ 1981. 5.</em><span>주교회의 총무</</span>
                                <li><em>1965. 2. ~ 1970. 10.</em><span>주교회의 전례위원회 위원장</span></li>
                                <li><em>1970. 10. ~ 1973. 10.</em><span>주교회의 일치위원회 위원</span></li>
                                <li><em>1973. 10. ~ 1978. 4.</em><span>주교회의 일치위원회 위원장 </span></li>
                                <li><em>1978. ~ 2002 4. 25.</em><span>주교회의 성직자양성위원회(성직주교위원회) 위원 </span></li>
                                <li><em>1978. 4. ~ 1984. 11.</em><span>주교회의 교리교육위원회 위원장</span></li>
                                <li><em>1994. 9. 12.</em><span>재단법인 인천가톨릭학원 이사장</span></li>
                                <li><em>1996. 12. 30.</em><span>재단법인 인천가톨릭청소년회 이사장 </span></li>
                                <li><em>2002. 4. 25. </em><span>인천교구장 사임, 은퇴</span></li>
                            </ol>
                        </div>
                    </article>
                    <article class="boxHalf">
                        <div class="minHei v3">
                            <img src="/img/sub/ever_01.jpg" alt="">
                        </div>
                        <div>
                            <div class="textBox bdrLine minHei v3">
                                <div class="text">
                                    <p class="fst">
		                            <c:if test="${fn:length(msgList) == 0}">
		                                <c:forEach items="${msgList}" var="list" varStatus="status">
		                                	<em>${list.SUB_TITLE }<c:if test="${list.SUB_TITLE eq ''}">${list.TITLE }</c:if></em>
		                                	<span id="dotdotdot${status.index }">${list.CONTENT_S }</span><a href="javascript:showMessage(${list.M_IDX}, ${list.TYPE})"></a><BR /><BR />
		                                </c:forEach>
		                            </c:if>
		                            <em>"UT OMNES UNMMSINT" (모든 이가 하나 되기를. 요한17,21) </em>
		                                                              한국의 국화인 무궁화, 한국 교회의 주보인 성모 마리아, <br>  한국 교회의 첫 사제인 김대건 신부, <br>그리고 인천 항구가 상징적으로 표현되었다.</p>
		                            
		                            
		                            <c:if test="${fn:length(msgList) == 0}">
		                            	<script>
		                            	function executeDotdotdot( selectorID ) {
		                            		$("#"+selectorID).dotdotdot(
		                            			{	ellipsis:' ..... ', watch:true, wrap:'letter', height: 25, tolerance:20
			                            			, callback: function(sTruncated, orgContent) {
			                            				console.log("dotdotdot("+selectorID+") => sTruncated="+sTruncated + ", orgContent="+orgContent );
			                            			}
		                            			}
		                            		);
		                            	}
		                            	<c:forEach items="${msgList}" var="list" varStatus="status">
		                            	setTimeout( executeDotdotdot("dotdotdot${status.index }") , 500 + 10*${status.index }  );
		                            	</c:forEach>
		                            	</script>
		                            </c:if>
                                </div>
                            </div>
                        </div>
                    </article>
                    <h3 class="ttl tb fl">교구장앨범</h3>
                    <!-- srchSel -->
                    <div class="srchSel fr">
                        <dl>
                            <dt>검색</dt>
                            <dd>
                                <label for=""></label>
                                <select name="schTextGubun" id="schTextGubun">
                                    <option value="title" <c:out value="${schTextGubunMap['title']}"/>>제목</option>
                                    <option value="content" <c:out value="${schTextGubunMap['content']}"/>>내용</option>
                                    <option value="all" <c:out value="${schTextGubunMap['all']}"/>>제목 + 내용</option>
                                </select>
                            </dd>
                            <dd>
                                <label for=""></label>
                                <input type="search" name="schText" id="schText" value="${schText}">
                                 <button onClick="this.form.submit()">검색</button>
                            </dd>
                        </dl>
                    </div>
                    <!-- //srchSel -->
                    <!-- albList -->
                    <div class="movieList">
                        <ul class="clearfix">
                            <c:choose>
                            <c:when test="${fn:length(LIST) > 0}">
                                <c:forEach items="${LIST}" var="list">
                                    <li><a href="javascript: viewAlbum(${list.BL_IDX})">
                                        <img src="${list.FILEPATH1}thumbnail/${list.STRFILENAME1}" alt="이미지">
                                        <span>${list.TITLE}</span>
                                        <em>${list.REGDATE}</em>
                                        </a>
                                    </li>
                                </c:forEach>
                                </c:when>
                                <c:otherwise>
                                
                                </c:otherwise>
                            </c:choose>  
                        </ul>
                    </div>
                    <!-- //movieList -->
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
