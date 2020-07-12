<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 
 --
 -- 화면에서 여러 탭을 사용할 경우 1번째 탭은 paging2.jsp 로 UI를 구성하고, 2번째부터 이후는 paging3.jsp 로 UI를 구성해야 함.
 -- 
 -->

<ol class="arrow">
    <li class="fst arr">
        <a href="javascript: goPage(${paging.firstPageNo})"><img src="/img/sub/_ico/board_arr_first.png" alt="처음으로"></a>
    </li>
    <li class="prev arr">
        <a href="javascript: goPage(${paging.prevPageNo})"><img src="/img/sub/_ico/board_arr_prev.png" alt="이전으로"></a>
    </li>
    <c:forEach var="i" begin="${paging.startPageNo}" end="${paging.endPageNo}" step="1">
        <c:choose>
            <c:when test="${i eq paging.pageNo}">
            	<li class="num on"><a href="javascript: goPage(${i})" class="choice">${i}</a></li>
            </c:when>
            <c:otherwise>
            	<li class="num"><a href="javascript: goPage(${i})">${i}</a></li>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    <li class="next arr">
        <a href="javascript: goPage(${paging.nextPageNo})"><img src="/img/sub/_ico/board_arr_next.png" alt="다음으로"></a>
    </li>
    <li class="lst arr">
        <a href="javascript: goPage(${paging.finalPageNo})"><img src="/img/sub/_ico/board_arr_last.png" alt="마지막으로"></a>
    </li>
</ol>
