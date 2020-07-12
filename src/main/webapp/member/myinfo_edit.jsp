<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<script type="text/javascript">
window.onload = function() {
	// 이벤트 달기
	$('input:radio[name=is_incheon_gyugo]').click(function(e){
		if(this.value=="Y") {
			$("#church_idx").removeAttr("disabled");
		} else {
			$("#church_idx").val("00000").prop("selected", true);
			$("#church_idx").attr("disabled", true);
			
		}
	});
}
</script>
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
        <section class="subVisual commu memb">
            <h2 class="blind">중심내용</h2>
            <div class="visBg">bg</div>
            <div class="artWrap">
                <article>
                    <h3>나의정보</h3>
                    <ul>
                        <li>홈</li>
                        <li>MEMBER</li>
                        <li>나의정보</li>
                    </ul>
                </article>
            </div>
        </section>
        <!-- //subVisual -->
        <!-- secWrap -->
        <form id="frm" name="frm" method="post" action="/member/update.do">
        <div class="secWrap">
            <!-- sec01 -->
            <section class="sec01 fin">
                <!-- secCont -->
                <div class="secCont">
                    <h3 class="blind">나의정보 내용</h3>
                    <%@include file="myinfo_tab.jsp" %>
                    <h3 class="ttl fl tb">회원정보 입력 </h3>
                    <!-- writeTable -->
                    <script>
                    var member_type_val = 1;
                    function changeMemberType(tp) {
                    	member_type_val = tp;
                    	switch(tp) {
                    	case 1:
                    		$("#tr_baptismal_name").show();
                    		$("#tr_festival").show();
                    		break;
                    	case 2:
                    	case 3:
                    		$("#tr_baptismal_name").hide();
                    		$("#tr_festival").hide();
                    		break;
                    	}
                    }
                    function changeIsIncheonGyugoo(tp) {
                    	member_type_val = tp;
                    	switch(tp) {
                    	case 'Y':
                    		$("#id church_idx:eq(0)").prop("selected", true);
                    		$("#church_idx").removeProp("readOnly");
                    		break;
                    	case 'N':
                    		$("#church_idx").prop("readOnly", true);
                    		break;
                    	}
                    }
                    
                    function update() {
                    	// ====== null check
                    	//if( $("#agreement1").is(":checked") == false) { alert("'회원의 개인정보 수집목적 및 이용'에 동의가 필요합니다."); $("#agreement1").focus(); return; }
                    	//if( $("#agreement2").is(":checked") == false) { alert("'개인정보 수집 및 이용에 관한 동의'에 동의가 필요합니다."); $("#agreement2").focus(); return; }
                    	//if( $("#id").val() == "") { alert("아이디를 입력해 주세요."); $("#id").focus(); return; }

						// 정규식 - 이메일 유효성 검사
						//var regEmail = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
						//if(!regEmail.test($("#id").val())) { alert('이메일 주소가 유효하지 않습니다'); $("#id").focus(); return; }
						
                    	if( $("#password").val() == "") { alert("패스워드를 입력해 주세요."); $("#password").focus(); return; }
                    	if( $("#password2").val() == "") { alert("패스워드 확인를 입력해 주세요."); $("#password2").focus(); return; }
                    	if( $("#password").val() != $("#password2").val()) { alert("암호가 일치하지 않습니다."); return; }
                    	
                    	/*
                    	var reg_pwd = /^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;
                    	if( !reg_pwd.test($("#password").val()) ) {
                    		alert("비밀번호 규칙에 맞지 않습니다."); return;
                    	} else if ( $("#password").val().length < 10 ) {
                    		alert("비밀번호는 12자리 이상이어야 합니다."); return;
                    	}
                    	*/
                    	
                    	if( $("#name").val() == "") { alert("이름을 입력해 주세요."); $("#name").focus(); return; }
                    	if( member_type_val==1 && $("#baptismal_name").val() == "") { alert("세례명을 입력해 주세요."); $("#baptismal_name").focus(); return; }
                    	
                    	//인천교구여부 체크된게 없다면,,,
                    	if( $("input[name='is_incheon_gyugo']:checked").length==0 ) { 
                    		alert("교구구분을 선택 해 주세요.") ; return  false; 
                    	}
                    	if($("input[name='is_incheon_gyugo']:checked").val()=="Y") {
                    		if( $("#church_idx option:selected").val()=="00000") { 
                    			alert("관할 본당을 선택 해 주세요.") ; return  false; 
                    		}
                    	} else {
                    		$("#church_idx").val("00000").prop("selected", true);
                    	}
                    	//if( $("#church_idx option:selected").val()=="00000") { alert("관할 본당을 선택 해 주세요.") ; return ; }
                    	
                    	if( member_type_val==1 && $("#festivalM option:selected").val()=="00") { alert("세레받은 월을 선택 해 주세요.") ; return ; }
                    	if( member_type_val==1 && $("#festivalD option:selected").val()=="00") { alert("세레받은 일을 선택 해 주세요.") ; return ; }

                    	if( $("#tel2").val() == "") { alert("전화번호를 입력해 주세요."); $("#tel2").focus(); return; }
                    	if( $("#tel3").val() == "") { alert("전화번호를 입력해 주세요."); $("#tel3").focus(); return; }

                    	// 정규식 -전화번호 유효성 검사
						var tel = $("#tel2").val() + $("#tel3").val();
						var regPhone = /^((0[1|6|7|8|9])[1-9]+[0-9]{6,7})|(010[1-9][0-9]{7})$/;
						    regPhone = /^([0-9]{7,8})$/;
						if(!regPhone.test(tel)) {
							alert('전화번호가 유효하지 않습니다'); return;
						}
						
						// send
                    	if( confirm("변경 하시겠습니까 ? ") ) {
                    		$("#frm").submit();
                    	}
                    }
                    </script>
                    <div class="writeTable">
                        <table class="shirine_st">
                        <caption>회원정보 입력</caption>
                            <tbody>
                                <tr>
                                    <th><i>*</i>신자구분</th>
                                    <td><!-- 4는 신자구분을 수정하지 못한다. -->
                                    	<c:set var="member_type_disabled" value="" />
                                    	<c:if test="${MEMINFO_MAP.MEMBERTYPE == '4' }">
                                    		<c:set var="member_type_disabled" value="disabled" />
                                        </c:if>
                                        <span class="chkForm">
                                            <input type="radio" id="member_type" name="member_type" value="1" onClick="changeMemberType(1)" ${member_type_disabled } <c:if test="${MEMINFO_MAP.MEMBERTYPE=='1' }" >checked="checked"</c:if>>
                                            <label for=""><span>신자</span></label>
                                        </span>
                                        <span class="chkForm">
                                            <input type="radio" id="member_type" name="member_type" value="2" onClick="changeMemberType(2)" ${member_type_disabled } <c:if test="${MEMINFO_MAP.MEMBERTYPE=='2' }" >checked="checked"</c:if>>
                                            <label for=""><span>예비신자</span></label>
                                        </span>
                                        <span class="chkForm">
                                            <input type="radio" id="member_type" name="member_type" value="3" onClick="changeMemberType(3)" ${member_type_disabled } <c:if test="${MEMINFO_MAP.MEMBERTYPE=='3' }" >checked="checked"</c:if>>
                                            <label for=""><span>비신자</span></label>
                                        </span>
                                        
                                    </td>
                                </tr>
                                <tr>
                                    <th>이메일(아이디)</th>
                                    <td>
                                        <span class="form">
                                            <span class="tt">${MEMINFO_MAP.ID }<input type="hidden" id="id" name="id" value="${MEMINFO_MAP.ID }"></span>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <th>패스워드</th>
                                    <td>
                                        <span class="form">
                                            <label for=""></label>
                                            <input type="password" id="password" name="pw">
                                        </span>
                                        <span class="redTxt">* 영문,숫자,특수문자를 포함하여 10자리 이상 입력하세요</span>
                                    </td>
                                </tr>
                                <tr>
                                    <th>패스워드 확인</th>
                                    <td>
                                        <span class="form">
                                            <label for=""></label>
                                            <input type="password" id="password2" name="pw2">
                                        </span>
                                        <span class="redTxt">* 작성하신 패스워드를 정확하게 입력하세요.</span>
                                    </td>
                                </tr>
                                <tr>
                                    <th><i>*</i>성명</th>
                                    <td>
                                        <span class="form">
                                            <label for=""></label>
                                            <input type="text" name="name" id="name" value="${MEMINFO_MAP.NAME }">
                                        </span>
                                        <span class="redTxt">* 정확한 실명을 작성하세요.</span>
                                    </td>
                                </tr>
                                <tr id="tr_baptismal_name">
                                    <th><i>*</i>세례명</th>
                                    <td>
                                        <span class="form">
                                            <label for=""></label>
                                            <input type="text" id="baptismal_name" name="baptismal_name" value="${MEMINFO_MAP.BAPTISMALNAME }">
                                        </span>
                                        <span class="redTxt">* 정확한 세레명을 작성하세요.</span>
                                    </td>
                                </tr>
                                <tr>
                                    <th><i>*</i>교구구분</th>
                                    <td>
                                        <span class="chkForm">
                                            <input type="radio" name="is_incheon_gyugo" id="is_incheon_gyugo" value="Y" ${member_type_disabled } onClick="changeIsIncheonGyugoo('Y')" <c:if test="${MEMINFO_MAP.IS_INCHEON_GYUGO=='Y' }">checked="checked"</c:if> >
                                            <label for=""><span>인천교구</span></label>
                                        </span>
                                        
                                        <span class="chkForm">
                                            <input type="radio" name="is_incheon_gyugo" id="is_incheon_gyugo" value="N" ${member_type_disabled } onClick="changeIsIncheonGyugoo('N')" <c:if test="${MEMINFO_MAP.IS_INCHEON_GYUGO=='N' }">checked="checked"</c:if> >
                                            <label for=""><span>타교구</span></label>
                                        </span>
                                        
                                    </td>
                                </tr>
                                <tr>
                                    <th><i>*</i>관할본당</th>
                                    <td>
                                        <span class="form">
                                            <label for=""></label>
                                            <select name="church_idx" id="church_idx" ${member_type_disabled } >
                                                <option value="00000">선택</option>
                                                <c:if test="${fn:length(CHURCH_LIST) > 0}">
	                                                <c:forEach var="entry" items="${CHURCH_LIST}" varStatus="status">
	                                                	<option value="${entry.CHURCH_IDX}" <c:if test = "${MEMINFO_MAP.CHURCH_IDX==entry.CHURCH_IDX }"> selected </c:if>>${entry.NAME}</option>
	                                                </c:forEach>
	                                            </c:if>
                                            </select>
                                        </span>
                                        <span class="redTxt">예비신자께서는 영세 받을 성당, 현재다니고 있는 성당 혹은 주소지에서 가장 가까운 성당의 이름을 선택하여 주시기 바랍니다.</span>
                                    </td>
                                </tr>
                                <tr id="tr_festival">
                                    <th><i>*</i>축일</th>
                                    <td>
                                        <span class="form calenr">
                                            <select name="festivalM" id="festivalM">
                                                <option value="00">선택</option>
                                                <option value="01" <c:if test = "${MEMINFO_MAP.festivalM=='01' }"> selected </c:if> >1 </option>
												<option value="02" <c:if test = "${MEMINFO_MAP.festivalM=='02' }"> selected </c:if> >2 </option>
												<option value="03" <c:if test = "${MEMINFO_MAP.festivalM=='03' }"> selected </c:if> >3 </option>
												<option value="04" <c:if test = "${MEMINFO_MAP.festivalM=='04' }"> selected </c:if> >4 </option>
												<option value="05" <c:if test = "${MEMINFO_MAP.festivalM=='05' }"> selected </c:if> >5 </option>
												<option value="06" <c:if test = "${MEMINFO_MAP.festivalM=='06' }"> selected </c:if> >6 </option>
												<option value="07" <c:if test = "${MEMINFO_MAP.festivalM=='07' }"> selected </c:if> >7 </option>
												<option value="08" <c:if test = "${MEMINFO_MAP.festivalM=='08' }"> selected </c:if> >8 </option>
												<option value="09" <c:if test = "${MEMINFO_MAP.festivalM=='09' }"> selected </c:if> >9 </option>
												<option value="10" <c:if test = "${MEMINFO_MAP.festivalM=='10' }"> selected </c:if> >10</option>
												<option value="11" <c:if test = "${MEMINFO_MAP.festivalM=='11' }"> selected </c:if> >11</option>
												<option value="12" <c:if test = "${MEMINFO_MAP.festivalM=='12' }"> selected </c:if> >12</option>
                                            </select>
                                            <label for=""><span class="textSel">월</span></label>
                                             <select name="festivalD" id="festivalD">
                                                <option value="00">선택</option>
                                                <option value="01" <c:if test = "${MEMINFO_MAP.festivalD=='01' }"> selected </c:if> >1 </option>
												<option value="02" <c:if test = "${MEMINFO_MAP.festivalD=='02' }"> selected </c:if> >2 </option>
												<option value="03" <c:if test = "${MEMINFO_MAP.festivalD=='03' }"> selected </c:if> >3 </option>
												<option value="04" <c:if test = "${MEMINFO_MAP.festivalD=='04' }"> selected </c:if> >4 </option>
												<option value="05" <c:if test = "${MEMINFO_MAP.festivalD=='05' }"> selected </c:if> >5 </option>
												<option value="06" <c:if test = "${MEMINFO_MAP.festivalD=='06' }"> selected </c:if> >6 </option>
												<option value="07" <c:if test = "${MEMINFO_MAP.festivalD=='07' }"> selected </c:if> >7 </option>
												<option value="08" <c:if test = "${MEMINFO_MAP.festivalD=='08' }"> selected </c:if> >8 </option>
												<option value="09" <c:if test = "${MEMINFO_MAP.festivalD=='09' }"> selected </c:if> >9 </option>
												<option value="10" <c:if test = "${MEMINFO_MAP.festivalD=='10' }"> selected </c:if> >10</option>
												<option value="11" <c:if test = "${MEMINFO_MAP.festivalD=='11' }"> selected </c:if> >11</option>
												<option value="12" <c:if test = "${MEMINFO_MAP.festivalD=='12' }"> selected </c:if> >12</option>
												<option value="13" <c:if test = "${MEMINFO_MAP.festivalD=='13' }"> selected </c:if> >13</option>
												<option value="14" <c:if test = "${MEMINFO_MAP.festivalD=='14' }"> selected </c:if> >14</option>
												<option value="15" <c:if test = "${MEMINFO_MAP.festivalD=='15' }"> selected </c:if> >15</option>
												<option value="16" <c:if test = "${MEMINFO_MAP.festivalD=='16' }"> selected </c:if> >16</option>
												<option value="17" <c:if test = "${MEMINFO_MAP.festivalD=='17' }"> selected </c:if> >17</option>
												<option value="18" <c:if test = "${MEMINFO_MAP.festivalD=='18' }"> selected </c:if> >18</option>
												<option value="19" <c:if test = "${MEMINFO_MAP.festivalD=='19' }"> selected </c:if> >19</option>
												<option value="20" <c:if test = "${MEMINFO_MAP.festivalD=='20' }"> selected </c:if> >20</option>
												<option value="21" <c:if test = "${MEMINFO_MAP.festivalD=='21' }"> selected </c:if> >21</option>
												<option value="22" <c:if test = "${MEMINFO_MAP.festivalD=='22' }"> selected </c:if> >22</option>
												<option value="23" <c:if test = "${MEMINFO_MAP.festivalD=='23' }"> selected </c:if> >23</option>
												<option value="24" <c:if test = "${MEMINFO_MAP.festivalD=='24' }"> selected </c:if> >24</option>
												<option value="25" <c:if test = "${MEMINFO_MAP.festivalD=='25' }"> selected </c:if> >25</option>
												<option value="26" <c:if test = "${MEMINFO_MAP.festivalD=='26' }"> selected </c:if> >26</option>
												<option value="27" <c:if test = "${MEMINFO_MAP.festivalD=='27' }"> selected </c:if> >27</option>
												<option value="28" <c:if test = "${MEMINFO_MAP.festivalD=='28' }"> selected </c:if> >28</option>
												<option value="29" <c:if test = "${MEMINFO_MAP.festivalD=='29' }"> selected </c:if> >29</option>
												<option value="30" <c:if test = "${MEMINFO_MAP.festivalD=='30' }"> selected </c:if> >30</option>
												<option value="31" <c:if test = "${MEMINFO_MAP.festivalD=='31' }"> selected </c:if> >31</option>
                                            </select>
                                            <label for=""><span class="textSel">일</span></label>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <th><i>*</i>전화번호</th>
                                    <td class="telPhone">
                                        <span class="form">
                                            <label for=""></label>
                                            <select name="tel1" id="tel1">
                                                <option value="010" <c:if test = "${MEMINFO_MAP.TEL1=='010' }"> selected </c:if> >010</option>
                                                <option value="011" <c:if test = "${MEMINFO_MAP.TEL1=='011' }"> selected </c:if> >011</option>
                                                <option value="016" <c:if test = "${MEMINFO_MAP.TEL1=='016' }"> selected </c:if> >016</option>
                                                <option value="017" <c:if test = "${MEMINFO_MAP.TEL1=='017' }"> selected </c:if> >017</option>
                                                <option value="018" <c:if test = "${MEMINFO_MAP.TEL1=='018' }"> selected </c:if> >018</option>
                                                <option value="019" <c:if test = "${MEMINFO_MAP.TEL1=='019' }"> selected </c:if> >019</option>
                                                <option value="02"  <c:if test = "${MEMINFO_MAP.TEL1=='02'  }"> selected </c:if> >02</option>
                                                <option value="031" <c:if test = "${MEMINFO_MAP.TEL1=='031' }"> selected </c:if> >031</option>
                                                <option value="032" <c:if test = "${MEMINFO_MAP.TEL1=='032' }"> selected </c:if> >032</option>
                                                <option value="070" <c:if test = "${MEMINFO_MAP.TEL1=='070' }"> selected </c:if> >070</option>
                                            </select>
                                            <span class="bar">-</span>
                                            <input type="tel" id="tel2" name="tel2" value="${MEMINFO_MAP.TEL2 }">
                                            <span class="bar">-</span>
                                            <input type="tel" id="tel3" name="tel3" value="${MEMINFO_MAP.TEL3 }">
                                        </span>
                                    </td>
                                </tr>
                                <c:if test="${MEMINFO_MAP.GROUPGUBUN eq '4' }">
                                <tr>
                                    <th>글쓰기 권한</th>
                                    <td class="retire">
                                        <span class="redTxt">
                                            * 회원님은 소속본당에서 글쓰기 권한이 있으십니다.
                                        </span>
                                    </td>
                                </tr>
                                </c:if>
                                <tr>
                                    <th>회원탈퇴</th>
                                    <td class="retire">
                                        <button type="button" onclick="retireMember();">회원탈퇴</button>
                                        <span class="redTxt">
                                            * 회원 탈퇴시 회원정보만 삭제되며, 작성하신 게시글은 삭제 되지 않습니다.
                                        </span>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <!-- //writeTable -->
                    <!-- btn -->
                    <ul class="btn">
                        <li><a href="javascript: update()">확인</a></li>
                        <li class="gray"><a href="#none">취소</a></li>
                    </ul>
                    <!-- //btn -->
                </div>
                <!-- //secCont -->
            </section>
            <!-- //sec01 -->
        </div>
        </form>
        <!-- //secWrap -->
        <!-- footer -->
        <footer>
            <%@include file="/_common/inc/footer.jsp" %>
        </footer>
        <!-- //footer -->
    </div>
    <!--// wrap -->
    <form id="frmLeave" name="frmLeave" method="post" action="/member/leave.do">
    <input type="hidden" name="id" value="${MEMINFO_MAP.ID}" />
    </form>
    <script>
    
        function retireMember(){
            if (confirm("정말 삭제하시겠습니까? \n\n회원탈퇴시 회원정보만 삭제되며, 작성하신 게시글은 삭제되지 않습니다.") == true){
                document.frmLeave.submit();
            }else{
                return;
            }
        }
    
    	<c:if test="${MEMINFO_MAP.MEMBERTYPE ne '' }">
    	changeMemberType(${MEMINFO_MAP.MEMBERTYPE});
    	</c:if>
    
    </script>
</body>

</html>
