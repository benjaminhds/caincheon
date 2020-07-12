<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<%
if(sessionMemId==null||sessionMemId.equals("")) {                // id가 Null 이거나 없을 경우
	response.sendRedirect("/member/login.jsp?gotoBACK=/community/tale.jsp");    // 로그인 페이지로 리다이렉트 한다.
}
%>
<script>
function imgRefresh(){
    $("#captchaImg").attr("src", "/captcha?id=" + Math.random());
}

function clearForm() {
	document.getElementById("title").value = "";
	document.getElementById("contents").value = "";
	document.getElementById("captcha").value  = "";
}

function insert() {
	if( $("#user_id").val() == "") { alert("로그인이 필요한 페이지입니다.\n\n로그인 페이지로 이동합니다."); location.href="/member/login.jsp?gotoBACK=/community/tale.jsp"; return; }
	if( $("#title").val() == "") { alert("제목을 입력해 주세요."); $("#title").focus(); return; }
	if( $("#contents").val() == "") { alert("내용을 입력해 주세요."); $("#contents").focus(); return; }
	
	if( $("#captcha").val() == "") { alert("보안문자를 입력해 주세요."); $("#captcha").focus(); return; }

	// send
	if( confirm("신청하시겠습니까 ? ") ) {
		$("#frm").submit();
		$("#btn_insrt").children("a").remove();
	}
}

function update() {
	if( $("#user_id").val() == "") { alert("로그인이 필요한 페이지입니다.\n\n로그인 페이지로 이동합니다."); location.href="/member/login.jsp?gotoBACK=/community/tale.jsp"; return; }
	if( $("#title").val() == "") { alert("제목을 입력해 주세요."); $("#title").focus(); return; }
	if( $("#contents").val() == "") { alert("내용을 입력해 주세요."); $("#contents").focus(); return; }
	
	if( $("#captcha").val() == "") { alert("보안문자를 입력해 주세요."); $("#captcha").focus(); return; }

	// send
	if( confirm("수정하시겠습니까 ? ") ) {
		$("#frm").attr("action","/community/tale_modify.do");
		$("#frm").submit();
		$("#btn_updt").children("a").remove();
	}
}
</script>
<body>
<form id="frm" name="frm" method="post" action="/community/tale_insert.do">
<input type="hidden" name="inquire_no" id="inquire_no" value="${_params.inquire_no}" />	
<input type="hidden" name="user_id" id="user_id" value="${sessionMemId }"/>
    <p class="skipNavi" tabindex="1">본문바로가기</p>
    <!-- wrap -->
    <div id="wrap">
        <!-- header -->
        <header>
            <%@include file="/_common/inc/gnb.jsp" %>
        </header>
        <!-- //header -->
        <!-- subVisual -->
        <section class="subVisual commu">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap">
                <article>
                    <h3>나누고 싶은 이야기</h3>
                    <ul>
                        <li>홈</li>
                        <li>참여마당</li>
                        <li>나누고 싶은 이야기</li>
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
                    <h3 class="ttl fl tb v5 v6">1:1 문의하기</h3>
                    <p class="starTxt fr v2 v3">*이곳은 교구에 건의하고 싶은 내용이나 의견을 올리시는 곳으로 교구와 작성자님만의 공간입니다.  <br>내용확인은 "마이페이지"에서 하시기 바랍니다.</p>
                    <!-- writeTable -->
                    <div class="writeTable">
                        <table class="shirine_st write">
                            <caption>온라인 신청 폼</caption>
                            <tbody>
                                <tr class="readOn">
                                    <th scope="row">작성자</th>
                                    <td>
                                    	<input type="text" name="writer" id="writer" value="${sessionMemNm}" readonly>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">제목</th>
                                    <td>
                                        <span class="form">
                                            <label for=""></label>
                                            <input type="text" name="title" id="title" value="${inqContents.TITLE}">
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">내용</th>
                                    <td>
                                        <textarea name="contents" id="contents" cols="30" rows="10">${inqContents.CONTENTS}</textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">보안문자</th>
                                    <td class="secure">
                                    	<em><img src="/captcha" id="captchaImg" alt="captcha img"></em>&nbsp;
                                    	<input type="text" placeholder="보안문자를 입력하세요" name="captcha" id="captcha" value="">
                                        <button type="button" onClick="imgRefresh()"><img src="../img/reset.png" alt="보안문자 새로고침"></button>
                                        <!-- span class="redTxt">보안문자를 입력해주세요.</span -->
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <!-- //writeTable -->
                    <ul class="btn">
                    <c:choose>
					<c:when test="${query_type == 'insert'}">
                        <li id="btn_insrt"><a href="javascript:insert();">신청하기</a></li>
					</c:when>
					<c:otherwise>
                        <li id="btn_updt"><a href="javascript:update();">수정하기</a></li>
					</c:otherwise>
					</c:choose>
                        <li class="gray" id="btn_cncl"><a href="javascript:clearForm();">취소</a></li>
                    </ul>
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
</form>
</body>
</html>
