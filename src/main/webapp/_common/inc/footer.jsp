<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="footer_policy">
    <h2><img src="/img/foot_logo.png" alt="하단내용"></h2>
    <ol>
        <li><a href="/policy/personal.jsp" title="개인정보보호정책">개인정보보호정책</a></li>
        <li><a href="/policy/email.jsp" title="무단 이메일 수집거부">무단 이메일 수집거부</a></li>
        <li><a href="/policy/terms.jsp" title="이용약관">이용약관</a></li>
        <li><a href="/policy/sitemap.jsp" title="사이트맵">사이트맵</a></li>
    </ol>
    <address>
        <span>인천광역시 동구 박문로 1 (송림동 103-25)</span>
        <em>Copyright &copy; Diocese of incheon. All rights reserved.</em>
    </address>
</div>
<div id="footer_gotop" class="goTop">
    TOP
</div>

<script>
window.onload = function() {
    /* 스크롤바가 전체 화면에서 사라지는 현상을 막기 위해서 강제처리 :: 2018-03-15 */
    if($(window).height() < $(document).height()) {
    	var sh = $(document).prop("scrollHeight");
    	var ch = $(document).prop("clientHeight");
    	if( (sh==0 && ch==0) || (sh>ch) ) {
    		//$("body").css("overflow","scroll");
    	}
    }
    
    // if error happend
    setTimeout(function(){
			    	var msg = "${CONTROLLER_ERROR}";
			    	if(msg.length > 0)
			    		alert(msg);
			    }, 500);
};
</script>
<%@ include file="/_common/inc/bottom.jsp" %>