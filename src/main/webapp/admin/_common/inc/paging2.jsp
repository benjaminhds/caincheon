<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script language="javascript">

var loadPage = function(c){
	// queryString 은 페이징 관련 변수를 제외한 나머지
	
	var xUri = location.href;
	xUri = xUri.substring(0, xUri.indexOf(".do")+3);
	
	//alert( xUri );
	//alert( xUri + "?${queryString}&pageSize=${paging.pageSize}&pageNo="+c );
	
	$.ajax({
		type: 'GET',
		url: xUri + "?${_params.queryString}&pageSize=${paging.pageSize}&pageNo="+c,
		async:false,
		dataType: "text",
		contentType:"application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data) {
			$('html').html(data);
		},
		error: function(request, status, error) {
			alert(error);
		}
	});
};



var goToPageFormId = function( formId ){
	reloadBodyByURL( $("#"+formId).attr("action") +"?"+ $("#"+formId).serialize() );
}

var reloadBodyByURL = function(fullURL){
	// queryString 은 페이징 관련 변수를 제외한 나머지
	$.ajax({
		type: 'GET',
		url: fullURL,
		async:false,
		dataType: "text",
		contentType:"application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data) {
			$('body').html(data);
		},
		error: function(request, status, error) {
			alert(error);
		}
	});
};

</script>
<div class="paging" id="paging">
	<div class="pull-right">
		<div class="dataTables_paginate paging_simple_numbers">
		<ul class="pagination">
		    <li class="fst arr">
		        <a href="javascript:goPage(${paging.firstPageNo})"><img src="/img/sub/_ico/board_arr_first.png" alt="처음으로"></a>
		    </li>
		    <li class="prev arr">
		        <a href="javascript:goPage(${paging.prevPageNo})"><img src="/img/sub/_ico/board_arr_prev.png" alt="이전으로"></a>
		    </li>
		    <c:forEach var="i" begin="${paging.startPageNo}" end="${paging.endPageNo}" step="1">
		        <c:choose>
		            <c:when test="${i eq paging.pageNo}">
		            	<li class="num on"><a href="javascript:goPage(${i})" class="choice">${i}</a></li>
		            </c:when>
		            <c:otherwise>
		            	<li class="num"><a href="javascript:goPage(${i})">${i}</a></li>
		            </c:otherwise>
		        </c:choose>
		    </c:forEach>
		    <li class="next arr">
		        <a href="javascript:goPage(${paging.nextPageNo})"><img src="/img/sub/_ico/board_arr_next.png" alt="다음으로"></a>
		    </li>
		    <li class="lst arr">
		        <a href="javascript:goPage(${paging.finalPageNo})"><img src="/img/sub/_ico/board_arr_last.png" alt="마지막으로"></a>
		    </li>
		</ul>
		</div>
	</div>
</div>
<!-- 
<c:forTokens items="${_params.queryString}" delims="&" var="item">
	<c:set var="iLen" value='${fn: length(item) }' />
	<c:set var="iPos" value='${fn: indexOf(item,"=") }' />
    <input type="text" name='${fn: substring(item, 0, iPos) }' value='${fn: substring(item, iPos+1, iLen) }' />
</c:forTokens>
 -->
<!-- //페이징 -->