<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    
    <%@ include file="/_common/inc/headSub.jsp" %>
    <title>천주교 인천교구</title>
    <script type="text/javascript">
    $(document).ready(function(e) {
        li_setting();

        // 연도 타임라인 제이쿼리
        var time_cur = 0;
        var time_cnt = 0;
        var time_prev = null;
        var time_ea = $(".period_selector .time_line .imgs li").size() - 1;

        $(".period_selector .time_line .imgs li").each(function(index) {
            $(this).data({
                "src_txt": $(this).find("a img").attr("src")
            });
            $(this).hover(function(e) {
                if ($(this).index() != time_cur) {
                    $(this).find("a img").attr({
                        "src": $(this).data("src_txt").replace("_off", "_on")
                    });
                };
            }, function(e) {
                if ($(this).index() != time_cur) {
                    $(this).find("a img").attr({
                        "src": $(this).data("src_txt").replace("_on", "_off")
                    });
                };
            });
        }).on("click", function(e) {
            time_prev = time_cur;
            time_cur = $(this).index();
        });

        $(".period_selector .time_line .imgs li").eq(0).find("a img").attr({
            "src": $(".period_selector .time_line .imgs li").eq(0).data("src_txt").replace("_off", "_on")
        });
    });

    function goNext(obj) {
        var time_ea = $(".period_selector .time_line .imgs li").size();
        $(".imgs li img").each(function() {
            $(this).attr("src", $(this).attr("src").replace("_on", "_off"));
        });

        $(obj).attr("src", $(obj).attr("src").replace("_off", "_on"));

    }

    function ajList(obj) {
        $.ajax({
            type: "POST",
            url: "ajaxList.jsp",
            datatype: "html",
            cache: false,
            data: {
                scategory: obj
            }
        }).done(function(html) {
            var re = html;
            document.getElementById("ajlist").innerHTML = re;

            // row_con 제이쿼리
            $(".row_con.more").data({
                "more_state": false
            }).css({
                "height": "60px"
            });
            $(".row_con.more a.more_bn").on("click", function(e) {
                if ($(this).parent().parent().data("more_state") == false) {
                    $(this).parent().parent().data({
                        "more_state": true
                    });
                    $(this).parent().parent().css({
                        "height": parseInt($(this).parent().parent().css("height")) + parseInt($(this).parent().parent().find(".more_con").css("height")) + 30
                    });
                    $(this).find("img").attr({
                        "src": "/img/li_con_history_bn_minus.gif"
                    });
                    TweenMax.to($("html, body"), 0.4, {
                        "scrollTop": $(window).scrollTop() + parseInt($(this).parent().parent().find(".more_con").css("height")) + 30,
                        "ease": "Expo.easeInOut"
                    });
                } else if ($(this).parent().parent().data("more_state") == true) {
                    $(this).parent().parent().data({
                        "more_state": false
                    });
                    $(this).parent().parent().css({
                        "height": "60px"
                    });
                    $(this).find("img").attr({
                        "src": "/img/li_con_history_bn_plus.gif"
                    });
                };
            });


        });
    }
</script>
</head>

<body>
    <p class="skipNavi" tabindex="1">본문바로가기</p>
    <!-- wrap -->
    <div id="wrap">
        <!-- header -->
        <header>
            <%@include file="/_common/inc/gnb.jsp" %>
        </header>
        <!-- //header -->
        <!-- subVisual -->
        <section class="subVisual intro">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap">
                <article>
                    <h3>온라인역사관</h3>
                    <ul>
                        <li>홈</li>
                        <li>교구안내</li>
                        <li>온라인역사관</li>
                    </ul>
                </article>
            </div>
        </section>
        <!-- //subVisual -->
        <!-- secWrap -->
        <div class="secWrap">
            <!-- sec01 -->
            <section class="sec01 blue">
                <!-- secWide -->
                <div class="secWide blue v2">
                    <!-- secCont -->
                    <div class="secCont">
                        <div class="sub_container" id="container">
                            <div class="his_visual">
                                <p class="vis_txt"><img src="KB%EC%86%90%ED%95%B4%EB%B3%B4%ED%97%98_files/li_con05_visual_txt.png" alt=""></p>
                                <div class="img_slide">
                                    <ul>
                                        <li class="li_09"><img src="KB%EC%86%90%ED%95%B4%EB%B3%B4%ED%97%98_files/li_con05_visual_img_09.jpg" alt=""></li>
                                        <li class="li_01"><img src="KB%EC%86%90%ED%95%B4%EB%B3%B4%ED%97%98_files/li_con05_visual_img_01.jpg" alt=""></li>
                                        <li class="li_02" style="opacity: 0.6;"><img src="KB%EC%86%90%ED%95%B4%EB%B3%B4%ED%97%98_files/li_con05_visual_img_02.jpg" alt=""></li>
                                        <li class="li_03" style="opacity: 0.6;"><img src="KB%EC%86%90%ED%95%B4%EB%B3%B4%ED%97%98_files/li_con05_visual_img_03.jpg" alt=""></li>
                                        <li class="li_04" style="opacity: 0.6;"><img src="KB%EC%86%90%ED%95%B4%EB%B3%B4%ED%97%98_files/li_con05_visual_img_04.jpg" alt=""></li>
                                        <li class="li_05" style="opacity: 0.6;"><img src="KB%EC%86%90%ED%95%B4%EB%B3%B4%ED%97%98_files/li_con05_visual_img_05.jpg" alt=""></li>
                                    </ul>
                                </div>
                                <div class="tick_line">
                                    <div class="ticker" style="width: 100px;"></div>
                                </div>
                                <div class="txt_slide">
                                    <ul>
                                        <li class="li_01 on" style="width: 100px;">
                                            <a href="javascript:">
                                                <p>2003.10.10</p>
                                                <span>LG화재 한마음 대축제</span>
                                            </a>
                                            <div class="blinder" style="display: none; opacity: 0;"></div>
                                        </li>
                                        <li class="li_02" style="width: 100px;">
                                            <a href="javascript:">
                                                <p>2004.1.27</p>
                                                <span>인재니움 수원연수원 개관</span>
                                            </a>
                                            <div class="blinder" style="display: none; opacity: 0;"></div>
                                        </li>
                                        <li class="li_03" style="width: 100px;">
                                            <a href="javascript:">
                                                <p>2005.1.26</p>
                                                <span>'매직카', 2005 퍼스트 브랜드대상 수상</span>
                                            </a>
                                            <div class="blinder" style="display: none; opacity: 0;"></div>
                                        </li>
                                        <li class="li_04" style="width: 100px;">
                                            <a href="javascript:">
                                                <p>2005.10.11</p>
                                                <span>엘플라워봉사단 출범</span>
                                            </a>
                                            <div class="blinder" style="display: none; opacity: 0;"></div>
                                        </li>
                                        <li class="li_05" style="width: 100px;">
                                            <a href="javascript:">
                                                <p>2006.2.17</p>
                                                <span>서울 역삼동 LIG 타워 준공 및 본사 이전</span>
                                            </a>
                                            <div class="blinder" style="display: none; opacity: 0;"></div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="periods">
                                <div class="period_wrap">
                                    <div class="bn bn_left">
                                        <a href="javascript:"><img src="KB%EC%86%90%ED%95%B4%EB%B3%B4%ED%97%98_files/li_con_visual_bn_left.gif" alt="이전"></a>
                                        <div class="blinder" style="display: none; opacity: 0;"></div>
                                    </div>
                                    <div class="time_line">
                                        <ul class="imgs" style="width: 1560px;">
                                            <li class="li_01 on">
                                                <a href="javascript:ajList('2003')"><img src="KB%EC%86%90%ED%95%B4%EB%B3%B4%ED%97%98_files/li_time_txt_2003_on.gif" onclick="goNext(this);" alt="2003"></a>
                                            </li>
                                            <li class="li_02 ">
                                                <a href="javascript:ajList('2004')"><img src="KB%EC%86%90%ED%95%B4%EB%B3%B4%ED%97%98_files/li_time_txt_2004_off.gif" onclick="goNext(this);" alt="2004"></a>
                                            </li>
                                            <li class="li_03 ">
                                                <a href="javascript:ajList('2005')"><img src="KB%EC%86%90%ED%95%B4%EB%B3%B4%ED%97%98_files/li_time_txt_2005_off.gif" onclick="goNext(this);" alt="2005"></a>
                                            </li>
                                            <li class="li_04 ">
                                                <a href="javascript:ajList('2006')"><img src="KB%EC%86%90%ED%95%B4%EB%B3%B4%ED%97%98_files/li_time_txt_2006_off.gif" onclick="goNext(this);" alt="2006"></a>
                                            </li>
                                            <li class="li_05 ">
                                                <a href="javascript:ajList('2007')"><img src="KB%EC%86%90%ED%95%B4%EB%B3%B4%ED%97%98_files/li_time_txt_2007_off.gif" onclick="goNext(this);" alt="2007"></a>
                                            </li>
                                            <li class="li_06 ">
                                                <a href="javascript:ajList('2008')"><img src="KB%EC%86%90%ED%95%B4%EB%B3%B4%ED%97%98_files/li_time_txt_2008_off.gif" onclick="goNext(this);" alt="2008"></a>
                                            </li>
                                        </ul>
                                    </div>
                                    <div class="bn bn_right">
                                        <a href="javascript:"><img src="KB%EC%86%90%ED%95%B4%EB%B3%B4%ED%97%98_files/li_con_visual_bn_right.gif" alt="다음"></a>
                                        <div class="blinder" style="display: none; opacity: 0;"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="his_content" id="ajlist">
                                <div class="year_tit">2003</div>
                                <div class="con_wrap">
                                    <!-- 년도별 내용 컨텐츠 시작 -->
                                    <div class="his_con his_con_01">
                                        <div class="right_content">
                                            <div class="row_con more" style="height: 60px;">
                                                <div class="tit_area">
                                                    <span class="date"><b>02.</b>23</span>
                                                    <p class="title">중국 상해사무소 개설</p>
                                                    <a class="more_bn" href="javascript:"><img src="KB%EC%86%90%ED%95%B4%EB%B3%B4%ED%97%98_files/li_con_history_bn_plus.gif" alt="더보기"></a>
                                                </div>
                                                <div class="more_con">
                                                    <img title="20030223.jpg" alt="" src="KB%EC%86%90%ED%95%B4%EB%B3%B4%ED%97%98_files/1433901289096_850.jpg">
                                                    <br style="clear: both;">
                                                    <p>&nbsp;</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- 년도별 내용 컨텐츠 끝 -->
                                </div>
                            </div>
                            <div class="his_content" id="ajlist">
                                <div class="year_tit">2003</div>
                                <div class="con_wrap">
                                    <!-- 년도별 내용 컨텐츠 시작 -->
                                    <div class="his_con his_con_01">
                                        <div class="right_content">
                                            <div class="row_con more" style="height: 60px;">
                                                <div class="tit_area">
                                                    <span class="date"><b>02.</b>23</span>
                                                    <p class="title">중국 상해사무소 개설</p>
                                                    <a class="more_bn" href="javascript:"><img src="KB%EC%86%90%ED%95%B4%EB%B3%B4%ED%97%98_files/li_con_history_bn_plus.gif" alt="더보기"></a>
                                                </div>
                                                <div class="more_con">
                                                    <img title="20030223.jpg" alt="" src="KB%EC%86%90%ED%95%B4%EB%B3%B4%ED%97%98_files/1433901289096_850.jpg">
                                                    <br style="clear: both;">
                                                    <p>&nbsp;</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- 년도별 내용 컨텐츠 끝 -->
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- //secCont -->
                </div>
                <!-- //secWide -->
            </section>
            <!-- //sec01 -->
            <!-- sec02 -->
            <section class="sec02 fin">
                <!-- secCont -->
                <div class="secCont">
                </div>
                <!-- //secCont -->
            </section>
            <!-- //sec02 -->
        </div>
        <!-- //secWrap -->
        <!-- footer -->
        <footer>
            <? include_once "../_common/inc/footer.html"; ?>
        </footer>
        <!-- //footer -->
    </div>
    <!--// wrap -->
    <script>
    </script>
</body>

</html>
