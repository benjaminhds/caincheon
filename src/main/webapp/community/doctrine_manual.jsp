<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/_common/inc/headSub.jsp" %>
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
        <section class="subVisual commu">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap">
                <article>
                    <h3>통신교리신청</h3>
                    <ul>
                        <li>홈</li>
                        <li>참여마당</li>
                        <li>통신교리신청</li>
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
                    <!-- tabs -->
                    <ul class="tabs">
                        <li><a href="/community/doctrine.jsp">통신교리안내</a></li>
                        <li class="on"><a href="/community/doctrine_manual.jsp">신청방법</a></li>
                        <li><a href="/community/doctrine_register.do?">신청하기</a></li>
                    </ul>
                    <!-- //tabs -->
                    <h3 class="ttl tb">수강신청 및 진행과정 안내</h3>
                    <!-- manual -->
                    <div class="manual">
                        <dl>
                            <dt><img src="/img/sub/ico_01.png" alt="신청서 작성 및 접수"></dt>                            
                            <dd class="txt">                                
                                <p class="ttl">수강신청과 진행과정 안내</p>
                                <ul>
                                    <li>수강신청은 우편 또는 팩스(032-772-9717)와 교구 홈페이지를 모두 이용할 수 있으며, 수강료는 온라인으로 입금하시면 됩니다.</li>
                                    <li>수강료는 교재 및 문제지비, 통신비를 포함하여 예비신자 2만8천원, 재교육신자 4만원입니다. </li>
                                    <li>입금자명은 공부하실 분의 이름을 기록합니다. <br> (다를 경우에는 전화로 연락함)</li>
                                    <li>수강료 입금하실 계좌 : 신협(온라인) 131-002-128014 / 예금주 : 선교국 </li>
                                    <li>입금 후 반드시 전화연락을 주시기 바랍니다.(Tel. 032-762-9717)</li>
                                    <li>신청서 보내실 곳 : (22573) 인천 동구 박문로 1 (송림동) <br> 천주교 인천교구청 선교사목부 통신교리 담당자 앞</li>
                                </ul>
                            </dd>
                        </dl>
                        <dl>
                            <dt><img src="/img/sub/ico_02.png" alt="교재 및 문제지 수령"></dt>                            
                            <dd class="txt">
                                <p class="ttl">교재 및 문제지 수령</p>
                                <ul>
                                    <li>신청서가 접수되고 수강료가 입금이 확인된 분께는 교재와 문제지를 보내드립니다.</li>
                                    <li>예비신자는 미래사목연구소 차동엽 신부의 ‘여기에 물이 있다(22과)’를 교재로 사용하고, 재교육을 원하시는 신자는 여기에 물이 있다’와 ‘밭에 묻힌 보물(9과)’ 2권을 모두 공부하시게 됩니다.</li>
                                </ul>
                            </dd>
                        </dl>
                        <dl>
                            <dt><img src="/img/sub/ico_03.png" alt="문제풀이 및 미사례"></dt>                            
                            <dd class="txt">
                                <p class="ttl">문제풀이 및 미사참례</p>
                                <ul>
                                    <li>한과씩 보내드리는 문제지에 답을 적어 우편으로 보내주시면 채점결과와 다음편 문제지를 함께 받아 보실 수 있습니다.</li>
                                    <li>매주일 미사에 참례하시고 '미사참례 확인서'를 문제지와 함께 보냅니다.</li>
                                </ul>
                            </dd>
                        </dl>
                        <dl>
                            <dt><img src="/img/sub/ico_04.png" alt="이수증 발급"></dt>                            
                            <dd class="txt">
                                <p class="ttl">이수증 발급</p>
                                <ul>
                                    <li>1과~22과를 모두 마치고, 주일미사에 빠짐없이 참례하신 분께는 이수증을 발급하여 드립니다.</li>
                                </ul>
                            </dd>
                        </dl>
                        <dl>
                            <dt><img src="/img/sub/ico_05.png" alt="세례식"></dt>
                            
                            <dd class="txt">
                                <p class="ttl">세례식</p>
                                <ul>
                                    <li>[통신교리 이수증]을 세례받을 본당 사무실에 제출합니다.</li>
                                    <li>필요할 경우 보충교리교육을 받습니다.</li>
                                    <li>본당의 세례식 일정에 따라 세례성사를 받게 됩니다.</li>
                                </ul>
                            </dd>
                        </dl>
                    </div>
                    <!-- //manual -->
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
</body>

</html>
