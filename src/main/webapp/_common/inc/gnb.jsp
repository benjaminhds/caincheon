﻿<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript">
var isMobile = /Android|webOS|iPhone|iPad|iPod|BlackBerry/i.test(navigator.userAgent) ? true : false; 
function goUnifySearch(srchTextVal) {
	if(isMobile==true) {
		return;
	}
	if(srchTextVal=="") {
		//alert("검색어를 입력해 주세요.");
		$("#srchBar_srchText").focus();
		return;
	} else {
		location.href = "/search/unify_search.do?srchText="+srchTextVal;
		/*
		var frm = document.unifySearchForm;
		frm.action = "/search/unify_search.do";
		frm.srchText.value = srchTextVal;
		frm.submit();
		*/
	}
}
</script>
<div class="heads clearfix">
    <h1><a href="/home.do"><img src="/img/logo.png" alt="home"></a></h1>
    <h2 class="blind">탑메뉴</h2>
    <ol class="topmenu">
    <c:if test = "${fn:length(sessionMemId) > 0 }">
        <li><a href="/member/findWithSessionId.do?id=${sessionMemId}">${sessionMemNm }<c:if test = "${fn:length(sessionMemBapNm) > 0 }">(${sessionMemBapNm}<c:if test="${SS_ORGNM ne ''}">, ${SS_ORGNM}</c:if>)</c:if></a></li>
        <li><a href="/member/findWithSessionId.do?id=${sessionMemId}">나의정보 </a></li>
        <c:if test = "${SS_GROUPGUBUN eq '3' and fn:length(SS_CHURCHIDX) > 0 and SS_WRITEYN eq 'Y' }">
	    <li><a href='/church_member/church_view.do?church_idx=${SS_CHURCHIDX}&query_type=modify'>본당관리(${SS_CHURCHNM }) </a></li>
	    </c:if>
        <li><a href="/member/logout.jsp">로그아웃</a></li>
    </c:if>
    <c:if test = "${fn:length(sessionMemId) == 0 }">
        <li><a href="/member/login.jsp?gotoBACK=${__URI}">로그인</a></li>
        <li><a href="/member/myinfo.jsp">나의정보</a></li>
        <li><a href="/member/register.jsp">회원가입</a></li>
    </c:if>
        <li><a href="/policy/location.jsp" >오시는길</a></li>
    </ol>
    <div class="zoomer"><!-- 모바일영역 -->
        <a href="/search/unify_search.jsp"><img src="/img/sub/_ico/zoomer.png" alt="찾기"></a>
    </div>
    
    <nav class="clearfix">
        <h2 class="blind">메인메뉴</h2>
        <div class="search"><!-- PC영역 -->
            <label for="srchBar"></label>
            <input type="srchBar_srchText" id="srchBar_srchText" placeholder="통합검색" 
            value="${_params.srchText }" 
            onKeyDown='javascript: if(event.keyCode==13) { goUnifySearch(this.value); } '
            onKeyPress='javascript: if(event.keyCode==13) { goUnifySearch(this.value); } '>
            <a href='#' onClick='javascript: console.log($("#srchBar_srchText").val()); goUnifySearch($("#srchBar_srchText").val())'><img src="/img/main/ico/zoom.png" alt=""></button>
        </div>
        <div class="hamburger"><!-- 모바일영역 -->
            <span></span>
            <span></span>
            <span></span>
            <span></span>
            <span></span>
            <span></span>
        </div>
        <ul>
            <li>
                <a href="/intro/intro.jsp">교구안내</a>
                <dl>
                    <dt>교구안내</dt>
                    <dd><a href="/intro/intro.jsp">교구소개</a></dd>
                    <dd><a href="/intro/history.do">온라인역사관</a></dd>
                    <dd><a href="/intro/shirine.jsp">교구성지</a></dd>
                    <dd><a href="/intro/diocesan.do">교구청</a></dd>
                    <dd><a href="/intro/ordo_list.do?lv1=08">수도회</a></dd>
                    <dd><a href="/intro/org_list.do?t=1&lv1=05">기관/단체</a></dd>
                </dl>
            </li>
            <li>
                <a href="/gyogu/intro.jsp">교구장</a>
                <dl>
                    <dt>교구장</dt>
                    <dd><a href="/gyogu/intro.jsp">소개</a></dd>
                    <dd><a href="/gyogu/msg_list.do?type=ALL">메시지</a></dd>
                    <dd><a href="/gyogu/par_list.do?b_idx=ALL">교구장동정</a></dd>
                    <dd><a href="/gyogu/ever_list.do?e_idx=1&b_idx=ALL&searchGubun=all">역대교구장</a></dd>
                </dl>
            </li>
            <li>
                <a href="/father/father_list.do?">사제단</a>
                <dl>
                    <dt>사제단</dt>
                    <dd><a href="/father/father_list.do?">사제</a></dd>
                    <dd><a href="/father/holy_list.do?">선종사제</a></dd>
                </dl>
            </li>
            <li>
                <a href="/church/temp_01.do?qk=">본당</a>
                <dl>
                    <dt>본당</dt>
                    <dd><a href="/church/temp_01.do?qk=">본당현황</a></dd>
                    <dd><a href="/church/church.do?qk=">지구별</a></dd>
                    <dd><a href="/church/vacancy.do?qk=">공소</a></dd>                    
                </dl>
            </li>
            <li>
                <a href="/news/news_list.do?b_idx=ALL">소식</a>
                <dl>
                    <dt>소식</dt>
                    <dd><a href="/news/news_list.do?b_idx=ALL">교구소식</a></dd>
                    <dd><a href="/news/sch_list.do?gubuncd=1">교구일정</a></dd>
                    <dd><a href="/news/alb_list.do?b_idx=ALL&c_idx=ALL">교구앨범</a></dd>
                    <dd><a href="/news/mov_list.do?b_idx=23&searchGubun=all">교구영상</a></dd>
                    <dd><a href="/news/mgz_list.do?pub_idx=3">인천주보</a></dd>
                </dl>
            </li>
            <li>
                <a href="/samok/cure_list.do?b_idx=ALL">자료실</a>
                <dl>
                    <dt>자료실</dt>
                    <dd><a href="http://bible.cbck.or.kr" target="_blank" title="새창">성경</a></dd>
                    <dd><a href="http://missa.cbck.or.kr/" target="_blank" title="새창">전례력&매일미사</a></dd>
                    <dd><a href="http://missale.cbck.or.kr/" target="_blank" title="새창">로마미사경본</a></dd>
                    <dd><a href="http://directory.cbck.or.kr/" target="_blank" title="새창">한국천주교주소록</a></dd>                    
                    <dd><a href="/samok/cure_list.do?c_idx=ALL">사목자료</a></dd>
                </dl>
            </li>            
            <li>
                <a href="/community/doctrine.jsp">참여마당</a>
                <dl>
                    <dt>참여마당</dt>
                    <dd><a href="/community/doctrine.jsp">통신교리 안내</a></dd>
                    <dd><a href="/community/cana_register.do">카나혼인강좌 &amp;<br> 약혼자 주말 신청</a></dd>
                    <dd><a href="/community/tale.do?">나누고 싶은 이야기</a></dd>
                </dl>
            </li>
        </ul>
    </nav>
</div>
<!-- subBg -->
<div class="subBg">서브배경</div>
<div class="layerBg"><img src="/img/dimmed_70.png" alt=""></div>
<!-- //subBg -->
