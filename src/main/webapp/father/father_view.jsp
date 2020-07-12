<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<script type="text/javascript">
function goList() {
	document.form01.action = '/father/father_list.do';
	document.form01.submit();
    return false;
}

function goContents(p_idx) {
	if(p_idx=='0' || p_idx=='') {
		alert('사제가 없습니다.');
		//return false;
	} else {
		document.form01.action = '/father/father_view.do';
		document.getElementById('p_idx').value=p_idx;
		document.form01.submit();
	    return false;
	}
}

function PopWin(email,w,h,sb) {
	   var setting = "width="+w+", height="+h+", top=5, left=5, scrollbars="+sb;
	   var newWin=window.open("", "preistMailSend",setting);
	   var vfm = document.form02;
	   vfm.action = "/father/father_mail.jsp";
	   vfm.target = "preistMailSend";
	   vfm.email.value = email;
	   vfm.submit();
	   newWin.focus();
	}
</script>
<body>
<form name="form02" method="POST">
<input type="hidden" name="email" />
</form>
<form name="form01" method="POST">
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}"/>
<input type="hidden" name="p_idx" id="p_idx" value=""/>
    <p class="skipNavi" tabindex="1">본문바로가기</p>
    <!-- wrap -->
    <div id="wrap">
        <!-- header -->
        <header>
            <%@include file="/_common/inc/gnb.jsp" %>
        </header>
        <!-- //header -->
        <!-- subVisual -->
        <section class="subVisual priest">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap">
                <article>
                    <h3>사제</h3>
                    <ul>
                        <li>홈</li>
                        <li>사제단</li>
                        <li>사제</li>
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
                    <!-- viewPage -->
                    <div class="viewPage v2 ">
                        <table class="views">
                            <caption>상세 페이지</caption>
                            <thead>
                                <tr>
                                    <td>
                                        <span class="ttl center">
                                            <i>교구 사제 자세히보기</i>
                                        </span>
                                    </td>
                                </tr>
                            </thead>
                            <tbody class="view_50">
                                <tr>
                                    <td>
                                        <dl>
                                            <dt>
                                                <img src="/upload/father/photo/${priestContents.IMAGE }" alt="">
                                            </dt>
                                            <dd>
                                                <table class="shirine_st">
                                                <caption>교구 자세히보기</caption>
                                                    <tbody>
                                                        <tr>
                                                            <th>이름</th>
                                                            <td>${priestContents.NAME }</td>
                                                        </tr>
                                                        <tr>
                                                            <th>세례명</th>
                                                            <td>${priestContents.CHRISTIAN_NAME }</td>
                                                        </tr>
                                                        <tr>
                                                            <th>서품</th>
                                                            <td>${priestContents.P_BIRTHDAY }</td>
                                                        </tr>
                                                        <tr>
                                                            <th>축일</th>
                                                            <td>${priestContents.NEW_BIRTHDAY }</td>
                                                        </tr>
                                                        <tr>
                                                            <th>소속</th>
                                                            <td>${priestContents.GUBUN }</td>
                                                        </tr>
                                                        <tr>
                                                            <th>현임지 / 직급</th>
                                                            <td>
                                                            <c:forEach items="${orgPriestRelList}" var="list">
                                                            	${list.ORG_NAME } / ${list.POSITION_NAME }<br>
                                                            </c:forEach>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th>홈페이지 / SNS</th>
                                                            <td>${priestContents.HOMEPAGE }</td>
                                                        </tr>
                                                        <tr>
                                                            <th>소개글(성구)</th>
                                                            <td>${priestContents.PHRASE }</td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </dd>
                                        </dl>
                                        <ul class="btRight">
                                        	<!-- 상본이미지 처리 -->
                                        	<c:set var="a_tag" value="class=\"openLayer blue\"><a href='/upload/father/card/${priestContents.IMAGE2 }' "></c:set>
                                        	<c:if test="${priestContents.IMAGE2 eq ''}">
                                        		<c:set var="a_tag" value="class=\"blue\"><a  href=\"javascript:alert('상본 사진이 등록되지 않았습니다.');\" "></c:set>
                                        	</c:if>
                                            <li ${a_tag }>상본이미지</a></li>
											<c:if test="${fn:length(priestContents.EMAIL) > 0}">
												<c:choose>
												<c:when test="${fn:length(ADM_MEM_ID) == 0 and fn:length(SS_MEM_ID) == 0 }">
												<li><a href="javascript:alert('로그인 해 주세요.');">소식전하기</a></li>
												</c:when>
												<c:otherwise>
                                                <!-- <li><a href="javascript:PopWin('${priestContents.EMAIL}','800','640','no')">소식전하기</a></li> -->
                                                <li><a href="#none" class="openMail">소식전하기</a></li>
												</c:otherwise>
												</c:choose>
                                            </c:if>
                                        </ul>
                                    </td>
                                </tr>
                            </tbody>                            
                        </table>
                    </div>
                    <!-- //viewPage -->
                    <!-- btnLeft -->
                    <ul class="btn btnLeft">
                        <li class="gray"><a href="javascript:goContents('${priestContents.BEFORE_P_IDX}')">이전</a></li>
                        <li class="gray"><a href="javascript:goContents('${priestContents.NEXT_P_IDX}')">다음</a></li>
                    </ul>
                    <!-- //btnLeft -->
                    <!-- btn -->
                    <ul class="btn">
                        <li><a href="javascript:goList()">목록</a></li>
                    </ul>
                    <!-- //btn -->
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
</form>
        <!-- layerPop -->
        <div class="layers">
            <div class="bg">배경</div>
                <div class="layer btClose">              
                <div class="layerCont">
                    <p class="ico_close"><img src="/img/sub/pop_x.png" alt=""></p>
                    <img src="/upload/father/card/${priestContents.IMAGE2 }" alt="">
                </div>  
            </div>
        </div>
        <!-- //layerPop -->
        <!-- 17- 12 - 22 -->
        <!-- layerPop2 -->
        <div class="layers2">
        	<form id="form10" name="form10" method="post" action="" >
        	<input type="hidden" name="receiver"   value="${priestContents.EMAIL }">
        	<input type="hidden" name="senderNm"   value="${SS_MEM_NM }">
        	<input type="hidden" name="sender"     value="${SS_MEM_ID }">
            <div class="bg">배경</div>
            <div class="layer">
                <div class="layerCont">
                    <p class="ico_close btClose">
                        <img src="/img/sub/pop_x.png" alt="">
                    </p>
                    <div class="writeTable">
                        <table class="shirine_st write">
                            <caption>온라인 메일 폼</caption>
                            <tbody>
                                <tr>
                                    <th scope="row">제목</th>
                                    <td>
                                        <span class="form">
                                            <label for=""></label>
                                            <input type="text" name="title" id="title">
                                        </span>
                                    </td>
                                </tr>
                                <tr class="readOn">
                                    <th scope="row">보내는 사람</th>
                                    <td>${SS_MEM_NM } "${SS_MEM_ID }"</td>
                                </tr>
                                <tr>
                                    <th scope="row">내용</th>
                                    <td>
                                        <textarea name="contents" id="contents" cols="30" rows="10"></textarea>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <div class="btn noMg">
                            <ul>
                                <li id="btn_mail_send">
                                    <a href="#none" onclick="javascript: sendMail()" id="btn_mail_send_alink">보내기</a>
                                </li>
                                <li class="btClose gray" id="btn_mail_send_layer_cancel">
                                    <a href="#none" id="btn_mail_send_layer_cancel_alink">취소</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </form>
        </div>
    </div>
    <!--// wrap -->
    <!-- layer -->
    <script>
    function sendMail() {
    	var vfm = document.form10;
    	if(vfm.title.value == "") {
    		alert("제목이 누락되었습니다. 제목을 입력 해 주세요.");
    		vfm.title.focus();
    		return;
    	}
    	if(vfm.contents.value == "") {
    		alert("소식전하기 내용이 누락되었습니다. 소식을 입력 해 주세요.");
    		vfm.contents.focus();
    		return;
    	}
    	var formData = new FormData($('form10')[0]);
    	formData.append("senderNm", vfm.senderNm.value);
    	formData.append("sender"  , vfm.sender.value );
    	formData.append("receiver", vfm.receiver.value );
    	formData.append("title"   , vfm.title.value );
    	formData.append("contents", vfm.contents.value );
    	// 버튼 비활성화
    	//$("#btn_mail_send_layer_cancel").trigger("click");
    	// servlet call by ajax 
    	_requestByAjax('/father/father_mailSend.do'
    			, formData
    			, function(status, responseData) {
    					console.log("status = "+status);
    					console.log("responseData = "+responseData);
    					if(responseData.status=="success") {
    						alert(responseData.message);
    						$("#btn_mail_send_layer_cancel").trigger("click");//자동으로 팝업레이어 닫기
    					}
    				});
    }
     // 17- 12 - 22
     $(document).ready(function () {
         $('.openLayer').on('click', function (e) {
             e.preventDefault();
             $('.layers').find('.layer').stop().animate({'top': '20%','opacity': '1'}, 500)
             $('.layers').show(0);
         });
         $('.openMail').on('click', function (e) {
             e.preventDefault();
             $('.layers2').find('.layer').stop().animate({'top': '20%','opacity': '1'}, 500)
             $('.layers2').show(0);
         });
         $('.btClose').on('click', function (e) {
             e.preventDefault();
             //자동으로 팝업레이어 닫기
             $('.layers, .layers2').find('.layer').stop().animate({'top': '-20%','opacity': '0'}, 500)
             $('.layers, .layers2').delay(500).hide(0);
             //버튼 활성화
         });
     });
    </script>
    
</body>

</html>
