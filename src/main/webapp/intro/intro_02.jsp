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
        <section class="subVisual intro">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap">
                <article>
                    <h3>교구소개</h3>
                    <ul>
                        <li>홈</li>
                        <li>교구안내</li>
                        <li>교구소개</li>
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
                        <li><a href="/intro/intro.jsp">소개</a></li>
                        <li class="on"><a href="/intro/intro_02.jsp">현황</a></li>
                        <li><a href="/intro/intro_03.do">관할구역</a></li>
                        <li><a href="/intro/intro_04.jsp">심볼/주보성인</a></li>
                    </ul>
                    <!-- //tabs -->
                    <h3 class="ttl ">교구 현황</h3>  
                    <div class="oflow">
                    <!-- shirine_st v2 -->
                    <table class="shirine_st v2 v5">
                        <caption>교구관할지역정보</caption>
                        <colgroup>
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                        </colgroup>
                        <thead>
                            <tr>
                                <th scope="col" rowspan="2">면적(km<sup>2</sup>)</th>
                                <th scope="col" rowspan="2">인구(명)</th>
                                <th scope="col" rowspan="2">신자(명)</th>
                                <th scope="col" rowspan="2">신자비율(%)</th>
                                <th scope="col" colspan="3">교구성직자</th>
                                <th scope="col" rowspan="2">사제 1인당<br>
                                신자수(명)</th>
                                <th scope="col" rowspan="2">본당</th>
                                <th scope="col" rowspan="2">공소</th>
                            </tr>
                            <tr>
                                <th scope="col">주교(은퇴)</th>
                                <th scope="col">몬시뇰(은퇴)</th>
                                <th scope="col">신부(은퇴)</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>1,469.42</td>
                                <td>4,361,610</td>
                                <td>469,364</td>
                                <td>11.380%</td>
                                <td>3(1)</td>
                                <td>4(1)</td>
                                <td>302(15)</td>
                                <td>1,666</td>
                                <td>122</td>
                                <td>36</td>
                            </tr>              
                        </tbody>
                    </table>
                    <!-- //shirine_st v2 -->
                    </div>
                    <p class="tbTxt">* 인천광역시 2,983,484  / 부천시 869,165 / 김포시 366,773 / 시흥시(일부) 134,237 / 안산시(일부) 7,951</p>
                    <!-- start 신자와 본당 -->
                    <h3 class="ttl">신자와 본당</h3>  
                    <h4 class="ttl v2">인구 대비 신자 비율</h4>
                    <div class="oflow">
                    <!-- shirine_st v2 -->
                    <table class="shirine_st v2 v5">
                        <caption>신자와 본당</caption>
                        <colgroup>
                            <col style="width:9%">
                            <col style="width:9%">
                            <col style="width:9%">
                            <col style="width:9%">
                            <col style="width:9%">
                            <col style="width:9%">
                            <col style="width:9%">
                            <col style="width:9%">
                            <col style="width:9%">
                            <col style="width:9%">
                            <col style="width:9%">
                        </colgroup>
                        <thead>
                            <tr>
                                <th scope="col">2006</th>
                                <th scope="col">2007</th>
                                <th scope="col">2008</th>
                                <th scope="col">2009</th>
                                <th scope="col">2010</th>
                                <th scope="col">2011</th>
                                <th scope="col">2012</th>
                                <th scope="col">2013</th>
                                <th scope="col">2014</th>
                                <th scope="col">2015</th>
                                <th scope="col">2016</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>9.7</td>
                                <td>10.2</td>
                                <td>10.0</td>
                                <td>10.2</td>
                                <td>10.1</td>
                                <td>10.2</td>
                                <td>10.3 </td>
                                <td>10.7</td>
                                <td>11.4</td>
                                <td>11.4</td>
                                <td>11.6 </td>
                            </tr>              
                        </tbody>
                    </table>
                    <!-- //shirine_st v2 -->
                    </div>
                    <h4 class="ttl fl">신자 전년 대비 증감률 추이 변화</h4>
                    <p class="tbT fr">증감률(%)</p>
                    <div class="oflow">                    
                    <!-- shirine_st v2 -->
                    <table class="shirine_st v2 v5">
                        <caption>신자 전년 대비 증감률 추이 변화</caption>
                        <colgroup>
                            <col style="width:14.2%">
                            <col style="width:14.2%">
                            <col style="width:14.2%">
                            <col style="width:14.2%">
                            <col style="width:14.2%">
                            <col style="width:14.2%">
                            <col style="width:14.2%">
                        </colgroup>
                        <thead>
                            <tr>                            
                                <th scope="col">2010</th>
                                <th scope="col">2011</th>
                                <th scope="col">2012</th>
                                <th scope="col">2013</th>
                                <th scope="col">2014</th>
                                <th scope="col">2015</th>
                                <th scope="col">2016</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>2.0</td>
                                <td>2.0</td>
                                <td>2.3</td>
                                <td>2.0</td>
                                <td>2.6</td>
                                <td>1.9</td>
                                <td>1.6</td>
                            </tr>              
                        </tbody>
                    </table>
                    <!-- //shirine_st v2 -->
                    </div>
                    <h4 class="ttl">2016 타교구로의 전출입 신자 수와 비율</h4>
                    <div class="oflow">
                    <!-- shirine_st v2 -->
                    <table class="shirine_st v2 v5">
                        <caption>2016 타교구로의 전출입 신자 수와 비율</caption>
                        <colgroup>
                            <col style="width:16.66%">
                            <col style="width:16.66%">
                            <col style="width:16.66%">
                            <col style="width:16.66%">
                            <col style="width:16.66%">
                            <col style="width:16.66%">
                        </colgroup>
                        <thead>
                            <tr>                            
                                <th scope="col" colspan="3">타교구 전입</th>
                                <th scope="col" colspan="3">타교구 전출</th>
                            </tr>
                            <tr>                            
                                <th scope="col">신자 수(명)</th>
                                <th scope="col">신자 비율(%)</th>
                                <th scope="col">교구 신자 대비 전입률(%)</th>
                                <th scope="col">신자 수(명)</th>
                                <th scope="col">신자 비율(%)</th>
                                <th scope="col">교구 신자 대비 전출률(%)</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>8,252</td>
                                <td>10.0</td>
                                <td>1.6</td>
                                <td>6,435</td>
                                <td>8.1</td>
                                <td>1.3</td>
                            </tr>              
                        </tbody>
                    </table>
                    <!-- //shirine_st v2 -->
                    </div>
                     <h4 class="ttl">본당 수 변화</h4>
                     <div class="oflow">                    
                    <!-- shirine_st v2 -->
                    <table class="shirine_st v2 v5">
                        <caption>본당 수 변화</caption>
                        <colgroup>
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                        </colgroup>
                        <thead>
                            <tr>                            
                                <th scope="col">2007</th>
                                <th scope="col">2008</th>
                                <th scope="col">2009</th>
                                <th scope="col">2010</th>
                                <th scope="col">2011</th>
                                <th scope="col">2012</th>
                                <th scope="col">2013</th>
                                <th scope="col">2014</th>
                                <th scope="col">2015</th>
                                <th scope="col">2016</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>104</td>
                                <td>111</td>
                                <td>113</td>
                                <td>114</td>
                                <td>116</td>
                                <td>118</td>
                                <td>119</td>
                                <td>122</td>
                                <td>122</td>
                                <td>122</td>
                            </tr>              
                        </tbody>
                    </table>
                    <!-- //shirine_st v2 -->
                    </div>
                    <h4 class="ttl">공소 수 변화</h4>
                    <div class="oflow">
                    <!-- shirine_st v2 -->
                    <table class="shirine_st v2 v5">
                        <caption>공소 수 변화</caption>
                        <colgroup>
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                            <col style="width:10%">
                        </colgroup>
                        <thead>
                            <tr>                            
                                <th scope="col">2007</th>
                                <th scope="col">2008</th>
                                <th scope="col">2009</th>
                                <th scope="col">2010</th>
                                <th scope="col">2011</th>
                                <th scope="col">2012</th>
                                <th scope="col">2013</th>
                                <th scope="col">2014</th>
                                <th scope="col">2015</th>
                                <th scope="col">2016</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>29</td>
                                <td>35</td>
                                <td>35</td>
                                <td>35</td>
                                <td>36</td>
                                <td>36</td>
                                <td>36</td>
                                <td>36</td>
                                <td>36</td>
                                <td>36</td>
                            </tr>              
                        </tbody>
                    </table>
                    <!-- //shirine_st v2 -->
                    </div>
                    <!-- end 신자와 본당 -->
                    <!-- start 교구 사제 -->
                    <h3 class="ttl">교구 사제</h3>  
                    <ul class="dots v3">
                        <li>2016년말 기준 인천교구 성직자는 주교(은퇴1명 포함) 2명, 교구 신부 314명이다.</li>
                    </ul>
                    <h4 class="ttl v3">년도별 새수품 신부 수</h4>
                    <div class="oflow">
                    <!-- shirine_st v2 -->
                    <table class="shirine_st v2 v5">
                        <caption>년도별 새수품 신부 수</caption>
                        <colgroup>
                            <col style="width:9%">
                            <col style="width:9%">
                            <col style="width:9%">
                            <col style="width:9%">
                            <col style="width:9%">
                            <col style="width:9%">
                            <col style="width:9%">
                            <col style="width:9%">
                            <col style="width:9%">
                            <col style="width:9%">
                            <col style="width:9%">
                        </colgroup>
                        <thead>
                            <tr>
                                <th scope="col">2006</th>
                                <th scope="col">2007</th>
                                <th scope="col">2008</th>
                                <th scope="col">2009</th>
                                <th scope="col">2010</th>
                                <th scope="col">2011</th>
                                <th scope="col">2012</th>
                                <th scope="col">2013</th>
                                <th scope="col">2014</th>
                                <th scope="col">2015</th>
                                <th scope="col">2016</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>12</td>
                                <td>1</td>
                                <td>18</td>
                                <td>16</td>
                                <td>22</td>
                                <td>-</td>
                                <td>11</td>
                                <td>11</td>
                                <td>7</td>
                                <td>7</td>
                                <td>12</td>
                            </tr>              
                        </tbody>
                    </table>
                    <!-- //shirine_st v2 -->
                    </div>
                     <ul class="dots v2">
                        <li>전국 교구 신부 1인당 평균 신자 수는 1,347명으로 전년도의 1,350명에 비해 3명이 감소하였다(수도회 사제를 포함 신부 1인당 평균 신자 수는 1,112명). 인천교구는 사제 1인당 신자 수가 1,600명으로 전국에서 수원교구과 서울대교구에 이어 3번째로 높게 나타났다. </li>
                    </ul>
                    <h4 class="ttl">신부 1인당 평균 신자 수 변화</h4>
                    <div class="oflow">
                    <!-- shirine_st v2 -->
                    <table class="shirine_st v2 v5">
                        <caption>신부 1인당 평균 신자 수 변화</caption>
                        <colgroup>
                            <col style="width:14.2%">
                            <col style="width:14.2%">
                            <col style="width:14.2%">
                            <col style="width:14.2%">
                            <col style="width:14.2%">
                            <col style="width:14.2%">
                            <col style="width:14.2%">
                        </colgroup>
                        <thead>
                            <tr>                            
                                <th scope="col">2010</th>
                                <th scope="col">2011</th>
                                <th scope="col">2012</th>
                                <th scope="col">2013</th>
                                <th scope="col">2014</th>
                                <th scope="col">2015</th>
                                <th scope="col">2016</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>1,399</td>
                                <td>1,400</td>
                                <td>1,345</td>
                                <td>1,604</td>
                                <td>1,613</td>
                                <td>1,622</td>
                                <td>1,600</td>
                            </tr>              
                        </tbody>
                    </table>
                    <!-- //shirine_st v2 -->
                    </div>
                    <!-- end 교구 사제 -->
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
