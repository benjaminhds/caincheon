<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<script>
function insert() {
	if( $("#mailTitle").val() == "") { alert("제목을 입력해 주세요."); $("#mailTitle").focus(); return; }
	if( $("#mailContents").val() == "") { alert("내용을 입력해 주세요."); $("#mailContents").focus(); return; }
	// send
	if( confirm("신청하시겠습니까 ? ") ) {
		$("#frm").submit();
	}
}
</script>
<body>
<%=request.getParameter("email") %>
<form id="frm" name="frm" method="post" action="/father/father_mailSend.do">
<input type="hidden" name="receiver" id="receiver" value="<%=request.getParameter("email") %>" />	
<input type="hidden" name="sender" id="sender" value="${sessionMemId }"/>
        <div class="layers2">
            <div class="bg"></div>
            <div class="layer">
                <div class="layerCont">
                    <p class="ico_close btClose"><img src="/img/sub/pop_x.png" alt=""></p>
                    <div class="writeTable">
                        <table class="shirine_st write">
                            <caption>온라인 메일 폼</caption>
                            <tbody>
                            <tr>
                                    <th scope="row">제목</th>
                                    <td>
                                        <span class="form">
                                                <label for=""></label>
                                                <input type="text" id="mailTitle" name="mailTitle">
                                            </span>
                                    </td>
                                </tr>
                                <tr class="readOn">
                                    <th scope="row">보내는 사람</th>
                                    <td>${sessionMemId }</td>
                                </tr>
                                <tr>
                                    <th scope="row">내용</th>
                                    <td>
                                        <textarea name="mailContents" id="mailContents" cols="30" rows="10"></textarea>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <div class="btn noMg">
                            <ul>
                                <li><a href="javascript:insert();">보내기</a></li>
                                <li class="btClose gray"><a href="javascript:self.close();">취소</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
<script type="text/javascript">
$(".layers2").show();
$(".layers").show();
$(".shirine_st .write").show();
</script>
</body>
</html>
