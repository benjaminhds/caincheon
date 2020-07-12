<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>

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
			$('body').html(data);
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

function goPage( pageno ){
	document.form01.pageNo.value=pageno;
	document.form01.submit();
}

</script>
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
<%
	{
	Object ooo = request.getAttribute("_params");
	if(ooo!=null) {
		java.util.Map _paramsPg = (java.util.Map)ooo;
		String queryStringXX = String.valueOf(_paramsPg.get("queryString"));
		if(queryStringXX!=null && !"null".equals(queryStringXX) && queryStringXX.length() > 0) {
	java.util.ArrayList<String> qa = kr.caincheon.church.common.utils.UtilString.toArray(queryStringXX, "&", true);
	for(String element : qa) {
		int ixx = element.indexOf("=");
		if(ixx != -1) {
			String keyxx = element.substring(0, ixx);
			String valxx = element.substring(ixx+1);
			if( !"keyxx".equalsIgnoreCase("idx") || !"keyxx".equalsIgnoreCase("bl_idx") || !"keyxx".equalsIgnoreCase("b_idx") 
			 || !"keyxx".equalsIgnoreCase("pageNo") || !"keyxx".equalsIgnoreCase("p_idx")   || !"keyxx".equalsIgnoreCase("m_idx")  ) {
				out.println("<input type='hidden' name='"+keyxx+"' value='"+valxx+"'>");
			}
		}
	}
		}
	}
}
%>