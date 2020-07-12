<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<html>
<head>
<script type="text/javascript">
function goPage(pageNo) {
	document.getElementById('pageNo').value=pageNo;
	document.form01.submit();
    return false;
}

function goSearch() {
	/*
	if(document.getElementById('searchText').value == '') {
		alert('검색할 내용을 입력하세요.');
		document.getElementById('searchText').focus();
		return false;
	}
	*/
	document.form01.submit();
    return false;
}

function viewMsg (type, m_idx) {
	document.form01.action = '/gyogu/ever_view.do';
	document.getElementById('type').value=type;
	document.getElementById('m_idx').value=m_idx;
	document.form01.submit();
    return false;
}

function viewAlbum (c_idx, bl_idx) {
	document.form01.action = '/gyogu/ever_view.do';
	document.getElementById('c_idx').value=c_idx;
	document.getElementById('bl_idx').value=bl_idx;
	document.form01.submit();
    return false;
}

</script>

</head>

<body>
<form  name="form01" action="/gyogu/ever_list.do" method="POST">
<input type="hidden" name="type" id="type" value="${type}"/>
<input type="hidden" name="bl_idx" id="bl_idx" value="${bl_idx}"/>
<input type="hidden" name="c_idx" id="c_idx" value="2"/>
<input type="hidden" name="b_idx" id="b_idx" value="${b_idx}"/>
<input type="hidden" name="m_idx" id="m_idx" value="${m_idx}"/>
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
                        <li><a href="/gyogu/ever_list.do?c_idx=1&b_idx=ALL&searchGubun=all">제 1대 교구장</a></li>
                        <li class="on"><a href="/gyogu/ever_list.do?c_idx=2&b_idx=ALL&searchGubun=all">제 2대 교구장</a></li>
                    </ul>
                    <!-- //tabs -->
                    <h3 class="ttl tb">제 2대 교구장</h3>
                    <article class="boxHalf">
                        <div>
                            <img src="/img/sub/gyogu_ppl2.jpg" alt="">
                            <p class="center">故 최기산 보니파시오 주교</p>
                        </div>
                        <div>
                            <ol class="history bdrLine">
                                <li><em>1948. 5. 16</em><span>김포 출생 </span></li>
                                <li><em>1974. 2.</em><span>가톨릭대학교 신학대학 신학과 졸업 　 </span></li>
                                <li><em>1974. 12.</em><span>부제서품</span></li>
                                <li><em>1975. 12. 6.</em><span>사제수품 </span></li>
                                <li><em>1975. 12. 20.</em><span>인천교구 부평1동 본당 보좌 신부</span></li>
                                <li><em>1976. 3. 1. ~ 1977. 2. 28.</em><span>인천박문 여자중학교 교사</span></li>
                                <li><em>1977. 1. 15.</em><span>인천교구 백령 본당 보좌 신부 </span></li>
                                <li><em>1977. 8. 11.</em><span>인천교구 김포 본당 주임 신부 </span></li>
                                <li><em>1981. 2. 25.</em><span>인천교구 해안 본당 주임 신부 </span></li>
                                <li><em>1982. 9.</em><span>단국대학교 대학원 교육학 석사</span></li>
                                <li><em>1983. 6. 1.</em><span>인천교구 심곡본동 본당 주임 신부 </span></li>
                                <li><em>1987. 2. 18.</em><span>인천교구청 사목국장 </span></li>
                                <li><em>1990. 2. 14. ~ 1994. 3. 31.</em><span>해외 교포 사목 </span></li>
                                <li><em>1994. 5.</em><span>미국 성 요셉대학교 종교학 석사 </span></li>
                                <li><em>1995.</em><span>미국 버클리 예수회 신학대학 영성신앙 연구소 수료 </span></li>
                                <li><em>1995. 6. 8.</em><span>천교구 산곡3동 본당 주임 신부 </span></li>
                                <li><em>1996. 2. 5.</em><span>인천가톨릭대학교 교수(영성처장, 겨레문화연구소장 겸임) </span></li>
                                <li><em>1997.</em><span>미국 성 루이스 토마스아퀴나스 설교 연구소 수료 </span></li>
                                <li><em>1999. 10. 29.</em><span>인천교구 부교구장 임명 </span></li>
                                <li><em>1999. 12. 27.</em><span>주교 수품 </span></li>
                                <li><em>2000. 1. 17. ~ 2001. 1. 15.</em><span>인천교구 관리국장 겸 사무처장 겸 총대리 겸 부교구장 주교</span></li>
                                <li><em>2001. 1. 16. ~ 2002. 4. 24.</em><span>인천교구 총대리 겸 부교구장 주교</span></li>
                                <li><em>2000. 3. 30. ~ 2004. 10. 15.</em><span>주교회의 교회일치와 종교간대화 위원회 위원장</span></li>
                                <li><em>2001. 3. 24. ~ 2004. 10. 14.</em><span>주교회의 교리주교위원회 위원 </span></li>
                                <li><em>2002. 4. 25.</em><span>인천교구 제2대 교구장 착좌 </span></li>
                                <li><em>2002. 4. 25. ~ 2010. 3. 10.</em><span>주교회의 성직주교위원회 위원 </span></li>
                                <li><em>2002. 9. ~ 2007. 11.</em><span>교황청 종교간대화평의회 위원 </span></li>
                                <li><em>2004. 10. 14. ~ 2010. 3. 10.</em><span>주교회의 정의평화위원회 위원장 </span></li>
                                <li><em>2004. 10. 14. ~ 2016. 3. 15.</em><span>교회의 사회주교위원회 위원 </span></li>
                                <li><em>2008.</em><span>제49차 세계성체대회 한국 대표 주교 </span></li>
                                <li><em>2010. 3. 10. ~ 2016. 3. 15.</em><span>주교회의 교육위원회 위원장 </span></li>
                                <li><em>2010. 3. 10. ~ 2016. 5. 30.</em><span>주교회의 성직주교위원회 위원장 </span></li>
                                <li><em>2011. 10. 12. ~ 2014. 10. 30.</em><span>한국천주교중앙협의회 감사 </span></li>
                                <li><em>2014.10. 30. ~ 2016. 5. 30.</em><span>주교회의 서기 </span></li>
                                <li><em>2014. 10.30. ~ 2016. 5. 30.</em><span>한국천주교중앙협의회 상임이사 </span></li>
                                <li><em>2016. 5. 30.</em><span>선종 </span></li>
                            </ol>
                        </div>
                    </article>
                    <article class="boxHalf">
                        <div class="minHei v4">
                            <img src="/img/sub/ever_02.jpg" alt="">
                        </div>
                        <div>
                            <div class="textBox bdrLine minHei v4">
                                <div class="text">
                                    <p class="fst">
                                    
		                            <c:if test="${fn:length(msgList) == 0}">
		                                <c:forEach items="${msgList}" var="list" varStatus="status">
		                                	<em>${list.SUB_TITLE }<c:if test="${list.SUB_TITLE eq ''}">${list.TITLE }</c:if></em>
		                                	<span id="dotdotdot${status.index }">${list.CONTENT_S }</span><BR /><BR />
		                                </c:forEach>
		                            </c:if>
		                            
		                            <em>“FIAT VOLUNTAS TUA” (주님 뜻대로 이루어지소서 – 루가 1,38) </em>
                                    <strong>* 전체적인 의미 </strong>
                                    “나는 길이요 진리요 생명이다(요한 14, 6)”의 성구를 형상화하여 구성됐다. <br>
					                                    성령으로 충만하신 예수 그리스도 홀로 우리의 길이요 진리요 생명이시다. 오직 예수 그리스도만을 인생의 방패로 삼아 그분의 뜻이 이루어지는 삶을 살기로 한다"로, 사목 의지를 함축하여 나타내고 있다.<br><br>
					                <strong>* 모양</strong>
					                                    상단의 흰 비둘기는 성령을 상징하며 바로 밑의 성광으로 빛나는 십자가는 예수 그리스도를 의미함과 동시에 그리스도가 십자가를 지듯이 최 주교가 자신의 주교직을 겸손하게 받아들임을 형상화했다.  <br>
					                                    오직 예수 그리스도만을 인생의 방패로 삼겠다는 의지로 방패 형상이 중앙에 위치했으며, 방패에 새겨진 길과 책, 물병 모양은 각각 '길'과 '진리', '생명'을 상징한다. <br>
					                                    상단의 모자와 방패 옆의 열두 매듭의 장식술, 그리고 하단의 지팡이는 주교의 위계를 뜻하며, 전체적으로 온화한 오렌지색의 배열은 따뜻한 주님의 사랑을 의미한다.<br>
					                                    또한 녹색은 주님의 종으로 더욱 헌신하겠다는 성실함을 나타내는 한편, 오렌지색과 녹색의 조화는 모든이가 일치하여 하느님 나라를 완성하자는 화함과 평화를 상징한다. <br></p>
		                            
		                            <c:if test="${fn:length(msgList) == 0}">
		                            	<script>
		                            	function executeDotdotdot( selectorID ) {
		                            		$("#"+selectorID).dotdotdot({ellipsis:' ... ', watch:true, wrap:'letter', height: 25, tolerance:20});
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
                    <!-- movieList -->
                    <div class="movieList">
                        <ul class="clearfix">
                            <c:choose>
                            <c:when test="${fn:length(LIST) > 0}">
                                <c:forEach items="${LIST}" var="list">
                                    <li><a href="javascript: viewAlbum ('${list.C_IDX}','${list.BL_IDX}')">
                                        <img src="${list.FILEPATH1}thumbnail/${list.STRFILENAME1}" alt="이미지">
                                        <span>${list.TITLE}</span>
                                        <em>${list.REGDATE}</em>
                                        </a>
                                    </li>                                
                                </c:forEach>
                                </c:when>
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
