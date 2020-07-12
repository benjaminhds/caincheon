<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<script type="text/javascript">
function goPage(pageNo) {
	var vfm = document.form01;
	vfm.pageNo.value=pageNo;
	vfm.submit();
    return false;
}

function goSearching(vfm, isCheck) {
	if(isCheck && vfm.schText.value == '') {
		alert('검색할 내용을 입력하세요.');
		vfm.schText.focus();
		return false;
	}
	
	vfm.pageNo.value="1";
	vfm.submit();
	return false;
}

function viewDetails(p_idx) {
	var vfm = document.form01;
	vfm.action = '/father/father_view.do';
	vfm.p_idx.value=p_idx;
	vfm.submit();
    return false;
}

// 체크박스 토글처리
function toggleSOSOK(tp,tp2) {
	
	if(tp == 1) {
		if( tp2==1 ) {
			if( $("#sosok_divA").is(":checked") ) {
				$("#sosok_div1").prop("checked",false);
				$("#sosok_div2").prop("checked",false);
				$("#sosok_div3").prop("checked",false);
			}
		} else {
			if( $("#sosok_div1").is(":checked") && $("#sosok_div2").is(":checked") && $("#sosok_div3").is(":checked")) {
				$("#sosok_divA").prop("checked",true);
				$("#sosok_div1").prop("checked",false);
				$("#sosok_div2").prop("checked",false);
				$("#sosok_div3").prop("checked",false);
			} else
				$("#sosok_divA").prop("checked",false);
		}
	
	} else if (tp == 2) {
		if( tp2==1 ) {
			if( $("#appellationA").is(":checked") ) {
				$("#appellation1").prop("checked",false);
				$("#appellation3").prop("checked",false);
				$("#appellation4").prop("checked",false);
			}
		} else {
			if( $("#appellation1").is(":checked") && $("#appellation3").is(":checked") && $("#appellation4").is(":checked")) {
				$("#appellationA").prop("checked",true);
				$("#appellation1").prop("checked",false);
				$("#appellation3").prop("checked",false);
				$("#appellation4").prop("checked",false);
			} else
				$("#appellationA").prop("checked",false);
		}
		
	} else if (tp == 3) {
		if( tp2==1 ) {
			if( $("#org_lv1A").is(":checked") ) {
				$("#org_lv11").prop("checked",false);
				$("#org_lv12").prop("checked",false);
			}
		} else {
			if( $("#org_lv11").is(":checked") && $("#org_lv12").is(":checked") ) {
				$("#org_lv1A").prop("checked",true);
				$("#org_lv11").prop("checked",false);
				$("#org_lv12").prop("checked",false);
			} else
				$("#org_lv1A").prop("checked",false);
		}
	}
	
	
	
	
}
</script>
<body>
<form name="form01" action="/father/father_list.do" method="POST">
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
                    <h3 class="ttl">교구사제</h3>   
                    <!-- srchForm -->
                    <div class="srchForm v2">
                        <dl>
                            <dt>검색</dt>
                            <dd>
                                <ol>
                                    <li class="chk">
                                        <span><!-- CODE_INSTANCE where code='000001' -->
                                            <input type="checkbox" name="sosok_divA" id="sosok_divA" value="1,2,3" onClick="toggleSOSOK(1,1); goSearching(this.form, false)" <c:if test="${_params.sosok_divA eq '1,2,3'}">checked</c:if> >
                                            <label for="">전체</label>
                                        </span>
                                        <span>
                                            <input type="checkbox" name="sosok_div1" id="sosok_div1" value="1" onClick="toggleSOSOK(1,2); goSearching(this.form, false)" <c:if test="${_params.sosok_div1 eq '1'}">checked</c:if> >
                                            <label for="">교구</label>
                                        </span>
                                        <span>
                                            <input type="checkbox" name="sosok_div2" id="sosok_div2" value="2" onClick="toggleSOSOK(1,2); goSearching(this.form, false)" <c:if test="${_params.sosok_div2 eq '2'}">checked</c:if> >
                                            <label for="">수도회</label>
                                        </span>
                                        <span>
                                            <input type="checkbox" name="sosok_div3" id="sosok_div3" value="3" onClick="toggleSOSOK(1,2); goSearching(this.form, false)" <c:if test="${_params.sosok_div3 eq '3'}">checked</c:if> >
                                            <label for="">타교구</label>
                                        </span>
                                    </li>
                                    <li class="chk">
                                        <span><!-- CODE_INSTANCE where code='000002' -->
                                            <input type="checkbox" name="appellationA" id="appellationA" value="01,02,03,04" onClick="toggleSOSOK(2,1); goSearching(this.form, false)" <c:if test="${_params.appellationA eq '01,02,03,04'}">checked</c:if> >
                                            <label for="">전체</label>
                                        </span>
                                        <span>
                                            <input type="checkbox" name="appellation1" id="appellation1" value="01" onClick="toggleSOSOK(2,2); goSearching(this.form, false)" <c:if test="${_params.appellation1 eq '01'}">checked</c:if> >
                                            <label for="">주교</label>
                                        </span>
                                        <span>
                                            <input type="checkbox" name="appellation3" id="appellation3" value="03" onClick="toggleSOSOK(2,2); goSearching(this.form, false)" <c:if test="${_params.appellation3 eq '03'}">checked</c:if> >
                                            <label for="">몬시뇰</label>
                                        </span>
                                        <span>
                                            <input type="checkbox" name="appellation4" id="appellation4" value="04" onClick="toggleSOSOK(2,2); goSearching(this.form, false)" <c:if test="${_params.appellation4 eq '04'}">checked</c:if> >
                                            <label for="">신부</label>
                                        </span>
                                    </li>
                                    <li class="chk">
                                        <span><!-- ORG_HIERARCHY where LV1='01','02' -->
                                            <input type="checkbox" name="org_lv1A" id="org_lv1A" value="01,02" onClick="toggleSOSOK(3,1); goSearching(this.form, false)" <c:if test="${_params.org_lv1A eq '01,02'}">checked</c:if> >
                                            <label for="">전체</label>
                                        </span>
                                        <span>
                                            <input type="checkbox" name="org_lv11" id="org_lv11" value="01" onClick="toggleSOSOK(3,2); goSearching(this.form, false)" <c:if test="${_params.org_lv11 eq '01'}">checked</c:if> >
                                            <label for="">교구청</label>
                                        </span>
                                        <span>
                                            <input type="checkbox" name="org_lv12" id="org_lv12" value="02" onClick="toggleSOSOK(3,2); goSearching(this.form, false)" <c:if test="${_params.org_lv12 eq '02'}">checked</c:if> >
                                            <label for="">본당</label>
                                        </span>
                                    </li>
                                </ol>
                            </dd>
                        </dl>
                    </div>
                    <!-- //srchForm -->
                    <!-- srchSel -->
                    <div class="srchSel">
                        <dl>
                            <dt>정렬</dt>
                            <dd>
                                <label for=""></label>
                                <select name="sortGubun" id="sortGubun">
                                    <option value="onum" <c:out value="${sortGubunMap['onum']}"/>>기본</option>
                                    <option value="p_birthday" <c:out value="${sortGubunMap['p_birthday']}"/>>서품일</option>
                                    <option value="new_birthday" <c:out value="${sortGubunMap['new_birthday']}"/>>축일</option>
                                    <option value="org_idx" <c:out value="${sortGubunMap['org_idx']}"/>>현임지</option>
                                    <option value="name" <c:out value="${sortGubunMap['name']}"/>>이름</option>
                                    <option value="christian_name" <c:out value="${sortGubunMap['christian_name']}"/>>세례명</option>
                                </select>
                            </dd>
                            <!-- <dd><a href="http://old.caincheon.or.kr/web/intro/priest_calendar.aspx" target="_blank" title="새창">사제달력old</a></dd> -->
                            <dd><a href="/father/father_cal.do">사제달력</a></dd>
                            <dd>
                                <label for=""></label>
                                <select name="schTextGubun" id="schTextGubun">
                                    <option value="p.name" <c:out value="${schTextGubunMap['name']}"/>>이름</option>
                                    <option value="p.christian_name" <c:out value="${schTextGubunMap['christian_name']}"/>>세례명</option>
                                    <option value="o.name" <c:out value="${schTextGubunMap['org_idx']}"/>>현임지</option>
                                </select>
                            </dd>
                            <dd>
                                <label for=""></label>
                                <input type="search" name="schText" id="schText" value="${_params.schText}">
                                <button onClick="goSearching(this.form, true)">검색</button>
                            </dd>
                        </dl>
                    </div>
                    <!-- //srchSel -->
                    <!-- boardList -->
                    <div class="boardList oflow v2">
                        <table>
                            <caption>교구 사제 리스트</caption>
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
                                    <th scope="col">No.</th>
                                    <th scope="col">성명</th>
                                    <th scope="col">세례명</th>
                                    <th scope="col">현임지</th>
                                    <th scope="col">서품</th>
                                    <th scope="col">축일</th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:choose>
								<c:when test="${fn:length(priestList) > 0}">
									<c:forEach items="${priestList}" var="list">
	                                <tr>
	                                    <th scope="row">${list.RNUM}</th>
	                                    <td class="file"><a href="javascript:viewDetails(${list.P_IDX})">${list.NAME}</a></td>
	                                    <td>${list.CHRISTIAN_NAME}</td>
	                                    <td>${list.ORG_NAME}</td>
	                                    <td>${list.P_BIRTHDAY}</td>
	                                    <td>${list.NEW_BIRTHDAY}</td>
	                                </tr>
                                </c:forEach>
								</c:when>
							</c:choose>
                            </tbody>
                        </table>
                    </div>
                    <!-- //boardList -->
                    <!-- arrow -->
					<%@ include file="/_common/inc/paging2.jsp" %>
                    <!-- //arrow -->
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
