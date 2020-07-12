<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<body>      
<script>
var main_sending = false;
function sendRequestDormantClearMail() {
	if( $("#id").val() == "" ) {
		alert('정확한 이메일을 입력해 주세요.');
		return;
	}
	if(main_sending == false) {
		main_sending = true;
		$("#frm").submit();
	} else {
		alert("메일을 전송 중이니 잠시만 기다려 주세요.");
	}
}
</script>

            <!-- 메일 발송 -->
            <form id="frm" name="frm" action="/member/dormantClearRequest.do" method="post">
            <table cellpadding="0" cellspacing="0" align="center" style="margin-top:20px; padding:30px; border-top:10px solid #2368ad"  width="600px" bgcolor="#f5f5f5" >
               <tr>
                   <td colspan="2" style="vertical-align:top; padding:0; margin:0;  padding-bottom:20px; border-bottom:1px dashed #acacac; font-size:24px; font-family:'Dotum'; letter-spacing:-0.75px; color:#2368ad;" align="center" >휴면계정안내</td>
               </tr>
               <tr>
                    <td colspan="2" style="vertical-align:top;  color: #333333; padding:0; margin:0; font-family:'Dotum';font-size:14px;  line-height:2;  padding-bottom:10px; padding-top:25px; " align="center" >
                        <span style="color:#0095ff; font-family:'Dotum';font-size:14px; display:inline-block; vertical-align:top; "><!-- <c:if test = "${fn:length(name) > 0 }">${name }님은 </c:if>-->${id }</span> 아이디로 1년 이상 로그인 되지 않아 휴면 상태로 전환되었습니다.
                        휴면을 해제하시려면, 메일 인증을 해주세요.
                    </td>
               </tr>
               <tr>
                    <td style="padding-top:10px;" align="center">
                      <input type="text" name="id" value="${id }" maxlength="35" size="50" placeholder="이메일" style="height:50px; width:100%; border-radius:5px; font-size:14px; border:1px solid #cccccc; text-indent:10px">
                    </td>
               </tr>
               <tr>
                   <td colspan="2"  style="vertical-align:top;  color: #ff0000; padding-top:20px; margin:0; font-family:'Dotum';" align="center">
                        <input type="submit" value="메일 인증하기" onClick="sendRequestDormantClearMail()" style="border-radius:5px; border:0; background:#0a2966; color:#fff; width:100%; font-size:18px; cursor:pointer; height:50px; font-weight:bold;">
                   </td>
               </tr>
               <tr>
                   <td colspan="2" style="vertical-align:top; padding:0; margin:0; padding-top:40px;" align="center">
                         <img src="http://www.caincheon.or.kr/img/foot_logo.png" alt="천주교인천교구">
                   </td>
               </tr>
            </table>
            </form>
            <!-- //메일 발송 -->
</body>
</html>
