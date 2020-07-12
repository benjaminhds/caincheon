<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<script type="text/javascript">
function goList() {
	document.form01.action = '/father/holy_list.do';
	document.form01.submit();
    return false;
}

function goContents(bp_idx) {
	if(bp_idx=='0' || bp_idx=='') {
		alert('사제가 없습니다.');
		//return false;
	} else {
		document.form01.action = '/father/holy_view.do';
		document.getElementById('bp_idx').value=bp_idx;
		document.form01.submit();
	    return false;
	}
}
</script>
<body>
<form name="form01" method="POST">
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}"/>
<input type="hidden" name="bp_idx" id="bp_idx" value=""/>
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
                    <h3>선종사제</h3>
                    <ul>
                        <li>홈</li>
                        <li>사제단</li>
                        <li>선종사제</li>
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
                                        <span class="ttl">
                                            <i>${holyContents.NAME } ${holyContents.CHRISTIAN_NAME }</i>
                                        </span>
                                        <span class="date">${holyContents.DEAD }</span>
                                    </td>
                                </tr>
                            </thead>
                            <tbody class="view_50">
                                <tr>
                                    <td>
                                        <dl>
                                            <dt>
                                            	<img src="/upload/de_father/${holyContents.IMAGE }" alt="">
                                            </dt>
                                            <dd>
                                                <table class="shirine_st">
                                                <caption>교구 자세히보기</caption>
                                                    <tbody>
                                                        <tr>
                                                            <th>이름</th>
                                                            <td>${holyContents.NAME } ${holyContents.CHRISTIAN_NAME }</td>
                                                        </tr>
                                                        <tr>
                                                            <th>생일</th>
                                                            <td>${holyContents.BIRTHDAY }</td>
                                                        </tr>
                                                        <tr>
                                                            <th>서품</th>
                                                            <td>${holyContents.ORDINATION }</td>
                                                        </tr>
                                                        <tr>
                                                            <th>선종일</th>
                                                            <td>${holyContents.DEAD }</td>
                                                        </tr>
                                                        <tr>
                                                            <th>묘소</th>
                                                            <td>${holyContents.BRIAL_PLACE }</td>
                                                        </tr>
                                                        <tr>
                                                            <th>성구</th>
                                                            <td>${holyContents.PHRASE }</td>
                                                        </tr>
                                                        <tr>
                                                            <th>약력</th>
                                                            <td>${holyContents.PROFILE }</td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </dd>
                                        </dl>
                                        <ul class="btRight">
                                        	<li class="openLayer blue"><a href="/upload/de_father/card/${holyContents.IMAGE }">상본이미지</a></li>
                                        </ul>
                                    </td>
                                </tr>
                            </tbody>                            
                        </table>
                    </div>
                    <!-- //viewPage -->
                    <!-- btnLeft -->
                    <ul class="btn btnLeft">
                        <li class="gray"><a href="javascript:goContents('${holyContents.BEFORE_P_IDX}')">이전</a></li>
                        <li class="gray"><a href="javascript:goContents('${holyContents.NEXT_P_IDX}')">다음</a></li>
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
        <!-- layerPop -->
        <div class="layers">
            <div class="bg">배경</div>
                <div class="layer btClose">              
                <div class="layerCont">
                    <p class="ico_close"><img src="/img/sub/pop_x.png" alt=""></p>
                    <img src="/upload/de_father/card/${holyContents.IMAGE }" alt="">
                </div>  
            </div>
        </div>
        <!-- //layerPop -->
        <!-- 17-11-23 -->
        <!-- layerPop2 -->
        <div class="layers2">
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
                                            <input type="text">
                                        </span>
                                    </td>
                                </tr>
                                <tr class="readOn">
                                    <th scope="row">보내는 사람</th>
                                    <td>choihi77@nate.com</td>
                                </tr>
                                <tr>
                                    <th scope="row">내용</th>
                                    <td>
                                        <textarea name="" id="" cols="30" rows="10"></textarea>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <div class="btn noMg">
                            <ul>
                                <li>
                                    <a href="#none">보내기</a>
                                </li>
                                <li class="btClose gray">
                                    <a href="#none">취소</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--// wrap -->
    <!-- layer -->
    <script>
        // 17-11-23
        $(document).ready(function () {
                $('.openLayer').on('click', function (e) {
                    e.preventDefault();
                    $('.layers').find('.layer').stop().animate({
                        'top': '20%',
                        'opacity': '1'
                    }, 500)
                    $('.layers').show(0);
                });
                $('.openMail').on('click', function (e) {
                    e.preventDefault();
                    $('.layers2').find('.layer').stop().animate({
                        'top': '20%',
                        'opacity': '1'
                    }, 500)
                    $('.layers2').show(0);
                });
                $('.btClose').on('click', function (e) {
                    e.preventDefault();
                    $('.layers, .layers2').find('.layer').stop().animate({
                        'top': '-20%',
                        'opacity': '0'
                    }, 500)
                    $('.layers, .layers2').delay(500).hide(0);
                });
            });
    </script>
</form>

</body>

</html>
