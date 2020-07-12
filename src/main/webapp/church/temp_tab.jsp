<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

                    
                    
                    <!-- srchTabs -->
                    <script>
                    var goChangHref = function(fullURL){
                    	location.href = fullURL;
                    };
                    
                    function reloadPage(divCode) {
                    	//var xURL = "/church/temp_02.do?${fn:replace(qstring, 'qk=&', '')}&";
                    	var xURL = "/church/temp_${temp_no}.do?pageSize=12&pageNo=1&";
                    	switch(divCode) {
                    	case  0: xURL += "tabs=0&qk=&church_idx="; break;
                    	
                   	<c:forEach var="entry" items="${giguList}" varStatus="status">
                    	case  ${status.count}: xURL += "tabs=${entry.key}&qk=&church_idx=${mainHallByGigu[entry.value]}"; break;
					</c:forEach>
                    	}
                    	goChangHref(xURL);
                    }
                    </script>
                    
                    <ul class="srchTabs">
                        <li ${tabstyle0 }><a href="javascript: reloadPage(0)">전체보기(<c:out value="${mainHallByGigu['전체_CNT']}"/>)</a></li>
                    <c:forEach var="entry" items="${giguList}" varStatus="status">
                    	<c:if test = "${tabs eq entry.key}"><c:set var="tabstyle" value="class='on'" /></c:if>
                    	<c:set var="giguCnt" value="${entry.value}_CNT" />
                    	<li ${tabstyle}><a href="javascript: reloadPage(${entry.key})">${entry.value}(<c:out value="${mainHallByGigu[giguCnt]}"/>)</a></li>
					</c:forEach>
                        <li ${tabstyle13}><a href="javascript: reloadPage(13)">특수(<c:out value="${mainHallByGigu['특수_CNT']}"/>)</a></li>
                        <li class="vacancy"><a href="#none"> </a></li>
                        <li class="vacancy"><a href="#none"> </a></li>
						<li class="vacancy"><a href="#none"> </a></li>
						<li class="vacancy"><a href="#none"> </a></li>
                    </ul>
                    <!-- //srchTabs -->
                    
                    