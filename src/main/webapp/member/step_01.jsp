<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %><%
//성당목록
//pageContext.setAttribute("CHURCH_LIST", kr.caincheon.church.common.TempleUtil.listMailhallInRegion(false));
//sns id 
String sns_id = request.getParameter("sns_id");
if(sns_id!=null && sns_id.length()>0) {
	sns_id = " value=\""+sns_id+"\" readOnly ";
} else {
	sns_id = "";
}
//login_type
String login_type = request.getParameter("login_type");
if(login_type==null || login_type.length() == 0) {
	login_type = "1";
}
%>
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
                    <h3>회원가입</h3>
                    <ul>
                        <li>홈</li>
                        <li>MEMBER</li>
                        <li>회원가입</li>
                    </ul>
                </article>
            </div>
        </section>
        <!-- //subVisual -->
        <!-- secWrap -->
        <form id="frm" name="frm" method="post" action="/member/join.do">
        <input type="hidden" id="login_type" name="login_type" value="${login_type}" />
        <div class="secWrap">
            <!-- sec01 -->
            <section class="sec01 fin">
                <!-- secCont -->
                <div class="secCont">
                    <h3 class="ttl">이용약관</h3>
                    <div class="personal">
                        <div>
                            <em>[제1장 총 칙]</em>
▷ 제1조 (목적)<BR>
이 이용약관(이하 '약관')은 재단법인 천주교 인천교구가 운영는 웹사이트 caincheon.or.kr (이하 '인천교구(Caincheon.or.kr)') 회원에게 제공하는 서비스의 이용조건 및 절차, 기타 필요 사항을 규정함을 목적으로 합니다. <BR>
<BR>
▷제2조 (정의)<BR>
이 약관에서 사용하는 용어의 정의는 다음과 같습니다.<BR>
1. 회원 : 인천교구(Caincheon.or.kr)에 개인정보를 제공하여 서비스를 이용하는 자 <BR>
2. 아이디(ID) : 회원 식별과 회원의 서비스 이용을 위하여 회원이 선정하고 인천교구(Caincheon.or.kr)가 확인하는 전자우편주소 <BR>
3. 비밀번호 : 회원이 통신상의 자신의 비밀을 보호하기 위해 선정한 문자와 숫자의 조합 <BR>
4. 서비스 : 인천교구(Caincheon.or.kr)가 회원에게 제공하는 모든 서비스 <BR>
5. 해지 : 인천교구(Caincheon.or.kr) 또는 회원이 서비스 이용 이후 그 이용계약을 종료시키는 의사표시<BR>
<BR>
▷제3조 (약관의 효력과 변경)<BR>
1. 본 약관은 온라인을 통해 개인정보를 제공하고 약관에 동의해 인천교구(Caincheon.or.kr)의 서비스를 이용하고자 의사를 밝힌 회원에게 효력이 발생됩니다. <BR>
2. 인천교구(Caincheon.or.kr)는 필요한 경우 관계법령에 위배되지 않는 범위 내에서 약관을 개정할 수 있으며, 변경된 약관은 적용일자 및 개정사유를 명시하여 온라인상의 공지나 전자 우편 등의 방법을 통해 회원에게 공지됩니다. <BR>
3. 회원이 제공한 정보를 이용하여 약관의 개정과 변경 내용에 대하여 공지하며, 이용자의 정보가 정확치 않은 경우, 인천교구(Caincheon.or.kr) 사이트 공지로 대신할 수 있습니다. <BR>
4. 변경된 약관에 동의하지 않는 경우 서비스 이용을 중단하고 탈퇴할 수 있으며, 약관이 변경된 이후에도 서비스를 계속적으로 이용하는 경우에는 회원이 약관의 변경사항에 동의한 것으로 간주됩니다.<BR>
<BR>
▷제4조 (약관 외 준칙)<BR>
본 약관에 명시되어 있지 않은 사항은 전기통신기본법, 전기통신사업법, 전자거래기본법 등 관계법령에 규정된 바에 따릅니다. <BR>
1. 인천교구(Caincheon.or.kr)는 회원의 개인정보를 인천교구(Caincheon.or.kr)에서 제공하는 서비스 이용과 관련한 목적 외로 이용하거나 타인 또는 타기관에 제공하지 않습니다. 그러나 보다 나은 서비스 제공을 위하여 회원의 개인정보를 제3자 파트너와 공유할 수 있습니다. 이 경우에도 개인정보 제공 이전에 회원에게 제3자 파트너가 누구인지 , 어떤 정보가 왜 필요한지 , 그리고 언제까지 어떻게 보호 , 관리되는지에 대해 개별적으로 전자우편을 통해 고지하여 동의를 구하는 절차를 거치게 되며 , 회원이 동의하지 않는 경우에는 추가적인 정보를 수집하거나 제3자 파트너와 공유하지 않습니다. <BR>
2. 다만 , 정보통신이용촉진 등에 관한 법률에 대한 특별한 규정이 있는 경우, 서비스제공에 필요한 경우 또는 통계목적 , 학술연구 등을 위하여 필요한 경우로서 특정 개인을 식별할 수 없는 형태로 개인 정보가 제공되는 경우는 예외입니다 .<BR>
<BR>
<em>[제2장 회원 가입과 서비스 이용]</em>
▷제5조 (이용계약의 성립)<BR>
1. 이용 계약은 이용자의 본 이용약관 내용에 대한 동의와 이용 신청에 대한 인천교구(Caincheon.or.kr)의 이용승낙으로 성립합니다. <BR>
2. 인천교구(Caincheon.or.kr)는 이용 승낙을 위해 이용자의 정보 확인을 위한 절차를 거칠 수 있습니다. <BR>
3. 본 이용약관에 대한 동의는 이용 신청 당시 해당 화면에서 '동의함' 버튼을 누름으로써 의사표시를 합니다.<BR>
<BR>
▷제6조 (서비스 이용신청)<BR>
1. 회원으로 가입하여 본 서비스를 이용하고자 하는 이용자는 인천교구(Caincheon.or.kr)에서 요청하는 제반정보(이름, 전자우편주소 등)를 제공하여야 하며, 인천교구(Caincheon.or.kr)가 서비스 개선 등의 목적으로 회원의 동의하에 추가적인 개인 정보를 요구할 수 있습니다. <BR>
2. 회원가입은 실명으로 가입하는 것을 원칙으로하며 인천교구(Caincheon.or.kr)는 필요한 경우 온라인 또는 오프라인을 통하여 실명 확인 조치를 할 수 있습니다. <BR>
3. 인천교구(Caincheon.or.kr)는 다음 각 호에 해당하는 이용 계약 신청에 대하여는 이를 승낙하지 아니합니다. 　<BR>
 ① 실명이 아니거나 타인의 명의를 사용하여 신청하였을 때 　<BR>
 ② 부정한 용도로 본 서비스를 이용하고자 하는 경우 　<BR>
 ③ 영리를 추구할 목적으로 본 서비스를 이용하고자 하는 경우 　<BR>
 ④ 이용 계약 신청서의 내용을 허위로 기재하여 신청한 경우 　<BR>
 ⑤ 사회의 안녕 질서 혹은 미풍양속을 저해할 목적으로 신청한 경우 <BR>
 ⑥ 기타 인천교구(Caincheon.or.kr)가 정한 이용 신청 요건이 미비된 경우<BR>
<BR>
▷제7조 (서비스 이용)<BR>
1. 인천교구(Caincheon.or.kr)는 다음과 같이 서비스를 제공합니다. 　<BR>
 ① 인천교구(Caincheon.or.kr)가 제공하는 서비스는 기본적으로 무료로 합니다. 　<BR>
 ② 한국 천주교 및 인천교구와 관련된 소식과 정보를 공지합니다. <BR>
2. 인천교구(Caincheon.or.kr)는 시스템 등 장치의 보수점검 교체, 시스템의 고장, 통신의 두절, 기타 불가항력적 사유가 발생한 경우에는 서비스의 제공을 일시적으로 중단할 수 있습니다. <BR>
3. 인천교구(Caincheon.or.kr)는 제2항의 사유로 인하여 이용자 또는 제3자가 입은 손해에 대하여는 배상하지 아니합니다. <BR>
4. 인천교구(Caincheon.or.kr)는 회원에게 서비스 이용에 필요가 있다고 인정되는 각종 정보에 대해서 전자우편 등의 방법으로 회원에게 제공할 수 있습니다. <BR>
5. 14세 미만의 회원 가입은 관계법령에 의거하여 법적인 보호자의 동의 절차를 걸쳐야 회원가입이 가능합니다. 　<BR>
 ① 법적인 보호자의 동의를 받고자 하는 경우, 만14세 미만 회원이 회사와 서비스 이용계약을 맺기 위해 회원가입 절차를 밟는 과정에서 법정보호자가 이를 확인하고, 동의 여부를 표시하고 실명, 연락처 등이 작성된 서면을 통해 동의를 확인 받을 수 있습니다. 　<BR>
 ② ①항의 서면은 인천교구 팩스(032-765-6211) 또는 우편, 이메일 등의 방법으로 수신 확인 과정을 만14세 미만 회원의 회원 가입을 승인합니다. 　<BR>
 ③ 법정보호자는 언제든지 본인의 동의를 취소할 수 있으나, 동의를 취소하더라도 동의 기간 동안 회원의 서비스 이용에 대한 책임으로부터 면책되는 것은 아닙니다. <BR>
<BR>
▷제8조 (서비스의 변경 및 중단)<BR>
1. 인천교구(Caincheon.or.kr)는 특별한 사정이 없는 한 회원이 서비스를 계속적이고 안정적으로 이용할 수 있도록 합니다. <BR>
2. 서비스는 인천교구(Caincheon.or.kr)의 사정으로 인해 회원의 회원가입기간 중 일부 내용이 변경 또는 중단될 수 있습니다. <BR>
3. 인천교구(Caincheon.or.kr)는 서비스의 중단 등 중대한 변화가 있을 경우 회원에게 그 사실을 공지합니다. <BR>
<BR>
<em>[제3장 계약 해지 및 서비스 이용 제한]</em>
▷제9조 (계약 해지 및 제한)<BR>
회원의 자격은 다음의 경우에 상실되며, 그에 따른 서비스 이용에 제한이 생깁니다. <BR>
1. 회원이 이용 계약을 해지 하고자 하는 때에는 회원 본인이 직접 온라인을 통해 회원탈퇴 메뉴를 선택하셔서 해지 신청을 하여야 합니다. <BR>
2. 회원이 회원 탈퇴의 의사표시를 한 경우, 회원의 자격이 바로 상실됩니다. <BR>
3. 인천교구(Caincheon.or.kr)는 회원이 다음 사항에 해당하는 행위를 하였을 경우 사전 통지 없이 웹사이트 상의 이용계약을 해지하거나 또는 기간을 정하여 서비스 이용을 중지할 수 있습니다.<BR>
 ① 회원가입 시 허위 정보를 기재하는 경우 　<BR>
 ② 범죄적 행위에 관련되는 경우 　<BR>
 ③ 이용자가 사회적 공익을 저해할 목적으로 서비스 이용을 계획 또는 실행할 경우 　<BR>
 ④ 타인의 서비스 아이디 및 비밀번호를 도용하거나 다른 아이디로 이중 등록을 하는 경우 <BR>　
 ⑤ 타인의 명예를 손상시키거나 불이익을 주는 경우 　<BR>
 ⑥ 서비스에 위해를 가하는 등 서비스의 건전한 이용을 저해하는 경우 　<BR>
 ⑦ 기타 관련 법령이나 인천교구(Caincheon.or.kr)가 정한 이용 조건을 위배하는 경우 <BR>
 ⑧ 특정 사이트를 지속적으로 홍보하는 경우 <BR>
 ⑨ 사전 승낙 없이 서비스를 이용하여 영리행위를 하는 경우<BR>
<BR>
▷제10조 (이용 제한의 해제 절차)<BR>
1. 인천교구(Caincheon.or.kr)는 제1조의 규정에 의하여 이용 제한을 하고자 하는 경우에는 그 사유를 댓글 또는 메일 등의 방법에 의하여 해당 회원 또는 대리인에게 통지합니다. 
다만, 인천교구(Caincheon.or.kr)가 긴급하게 이용을 정지할 필요가 있다고 인정하는 경우에는 그러하지 아니합니다. <BR>
2. 제1항의 규정에 의하여 이용 정지의 통지를 받은 이용자 또는 그 대리인은 그 이용 정지의 통지에 대하여 이의가 있을 때에는 이의 신청을 할 수 있습니다. <BR>
3. 인천교구(Caincheon.or.kr)는 제2항의 규정에 의한 이의 신청에 대하여 그 확인을 위한 기간까지 이용정지를 연기할 수 있습니다. <BR>
4. 인천교구(Caincheon.or.kr)는 이용 정지 기간 중에 그 이용 정지 사유가 해소된 것이 확인된 경우에는 이용 정지 조치를 해제합니다.<BR>
<BR>
<em>[제4장 책임과 의무]</em>
▷제11조 (개인정보의 보호 및 사용)<BR>
1. 인천교구(Caincheon.or.kr)는 회원의 개인정보를 보호하며, 그 내용을 인천교구(Caincheon.or.kr) 웹사이트 메인화면과 개인정보관리 페이지에 '개인정보 처리방침'이란 이름으로 공지합니다. <BR>
2. 인천교구(Caincheon.or.kr)는 회원의 정보 수집 시 필요한 최소한의 정보를 수집합니다. <BR>
3. 회원의 개인정보를 수집 또는 제공 시에는 당해 이용자의 동의를 받습니다. <BR>
4. 회원은 언제나 자신의 정보를 확인하거나 정정 및 삭제를 요청할 수 있으며, 인천교구(Caincheon.or.kr)는 요청을 받은 즉시 처리를 해야 합니다.<BR>
<BR>
▷제12조 (인천교구(Caincheon.or.kr)의 의무)<BR>
1. 인천교구(Caincheon.or.kr)는 이 약관에서 정한 바에 따라 계속적, 안정적으로 서비스를 제공할 의무가 있습니다. <BR>
2. 인천교구(Caincheon.or.kr)는 항상 회원의 개인 신상 정보의 보안에 대하여 기술적 안전 조치를 강구하고 관리에 만전을 기함으로써 회원의 정보 보안에 최선을 다하여야 합니다. <BR>
3. 인천교구(Caincheon.or.kr)는 회원의 개인 신상 정보를 본인의 승낙 없이 타인에게 누설, 배포하여서는 안 됩니다. 다만, 전기통신관련법령 등 관계법령에 의하여 관계 국가기관 등의 요구가 있는 경우에는 그러하지 아니합니다. <BR>
4. 인천교구(Caincheon.or.kr)는 회원으로부터 제기되는 의견이나 불만이 정당하다고 인정할 경우에는 즉시 처리하여야 합니다. 다만, 즉시 처리가 곤란한 경우에는 그 사유와 처리 일정을 통보하여야 합니다.<BR>
<BR>
▷제13조 (회원의 권리와 의무)<BR>
1. 회원은 인천교구(Caincheon.or.kr)가 수행하는 제반 사업에 대한 정보를 제공받을 수 있습니다. <BR>
2. 회원은 회원정보가 변경되었을 경우 변경된 정보를 인천교구(Caincheon.or.kr)에 알려주어야 합니다. <BR>
3. 회원은 회원가입 신청 또는 회원정보 변경 시 실명으로 모든 사항을 사실에 근거하여 작성하여야 하며, 허위 또는 타인의 정보를 등록할 경우 일체의 권리를 주장할 수 없습니다. <BR>
4. 회원은 본 약관에서 규정하는 사항과 기타 인천교구(Caincheon.or.kr)가 정한 제반 규정, 공지사항 등 인천교구(Caincheon.or.kr)가 공지하는 사항 및 관계법령을 준수하여야 하며, 기타 인천교구(Caincheon.or.kr)의 업무에 방해가 되는 행위, 인천교구(Caincheon.or.kr)의 명예를 손상시키는 행위를 해서는 안 됩니다. <BR>
5. 인천교구(Caincheon.or.kr)가 관계법령 및 '개인정보 보호정책' 에 의거하여 그 책임을 지는 경우를 제외하고 회원에게 부여된 ID 및 비밀번호 관리 소홀, 부정사용에 의하여 발생하는 모든 결과에 대한 책임은 회원에게 있습니다. <BR>
6. 회원은 인천교구(Caincheon.or.kr)의 명시적 동의가 없는 한 서비스의 이용 권한 및 기타 이용계약상의 지위를 타인에게 양도, 증여할 수 없으며 이를 담보로 제공할 수 없습니다. <BR>
7. 회원은 인천교구(Caincheon.or.kr) 및 제 3자의 지적 재산권을 침해해서는 안 됩니다. <BR>
8. 회원은 다음 각 호에 해당하는 행위를 하여서는 안 되며, 해당 행위를 하는 경우에 인천교구(Caincheon.or.kr)는 회원의 서비스 이용 제한 및 적법 조치를 포함한 제재를 가할 수 있습니다. 　<BR>
 ① 회원가입 신청 또는 회원정보 변경 시 허위 내용을 등록하는 행위 　<BR>
 ② 다른 회원의 ID, 비밀번호 등 개인정보를 도용하는 행위 　<BR>
 ③ 회원 ID를 타인과 거래하는 행위 　<BR>
 ④ 인천교구(Caincheon.or.kr)의 운영진, 직원 또는 관계자를 사칭하는 행위 　<BR>
 ⑤ 인천교구(Caincheon.or.kr)로부터 특별한 권리를 부여 받지 않고 인천교구(Caincheon.or.kr)의 클라이언트 프로그램을 변경하거나, 인천교구(Caincheon.or.kr)의 사이트를 해킹하거나, 사이트 또는 게시된 정보의 일부분 또는 전체를 임의로 변경 하는 행위 <BR>
 ⑥ 본 서비스를 통해 얻은 정보를 인천교구(Caincheon.or.kr)의 사전 승낙 없이 서비스 이용 외의 목적으로 복제하거나, 이를 출판 및 방송 등에 사용하거나, 제 3자에게 제공하는 행위 　<BR>
 ⑦ 공공질서 및 미풍양속에 위반되는 저속, 음란한 내용의 정보, 문장, 도형, 음향, 동영상을 전송, 게시, 전자우편 또는 기타의 방법으로 타인에게 유포하는 행위 　<BR>
 ⑧ 모욕적이거나 개인 신상에 대한 내용이어서 타인의 명예나 프라이버시를 침해할 수 있는 내용을 전송, 게시, 전자우편 또는 기타의 방법으로 타인에게 유포하는 행위<BR>
<BR>
▷제14조 (회원의 게시물)<BR>
1. 인천교구(Caincheon.or.kr)는 회원의 게시물이 다음 각 항에 해당되는 경우에는 사전 통지 없이 삭제합니다. 　<BR>
 ① 타인의 프라이버시 및 공표권과 같은 법적 권리를 훼손, 악용, 도용, 위협하거나 괴롭히거나 또는 달리 위반하는 행위 <BR>
 ② 부적절, 모독적, 명예 훼손적, 침해적, 음란, 상스럽거나 또는 불법적인 제목, 이름, 자료 또는 정보를 출판, 우송, 게시, 배포 또는 유포시키는 행위 　<BR>
 ③ 지적소유권법에 의하여 보호되는 소프트웨어 또는 기타 자료를 포함하는 파일을 업로드 하는 행위. 다만 이용자가 그에 대한 권리를 소유 또는 관리하는 경우, 또는 필요한 동의를 모두 얻은 경우는 제외합니다. 　<BR>
 ④ 타인의 컴퓨터를 손상시킬 수 있는 바이러스, 오염된 파일, 또는 기타 유사한 소프트웨어 또는 프로그램을 포함하는 자료를 업로드 하는 행위 　<BR>
 ⑤ 상업적인 목적으로 상품 또는 서비스를 광고 또는 판매하는 행위 　<BR>
 ⑥ 자료조사, 컨테스트, 피라미드 체계를 행하거나 행운의 편지를 보내는 행위 　<BR>
 ⑦ 다른 이용자가 적법하게 배포될 수 없는 것으로 인식할 수 있는 자료를 업로드하거나 다운로드하는 행위 　<BR>
 ⑧ 업로드된 파일에 포함된 소프트웨어 또는 기타 자료의 저자표시, 법률상 또는 기타 적절한 유의사항, 또는 상표명, 또는 그 출처 내지 근원이 되는 표식을 위조 또는 제거하는 행위 　<BR>
 ⑨ 다른 이용자가 서비스를 사용하거나 즐기는 것을 제한하거나 금지시키는 행위 　<BR>
 ⑩ 공공질서 및 미풍양속의 관행에 반하는 음란한 내용이나, 타 종교를 선전ㆍ선교하거나 비방하는 내용, 기타 지역감정을 유발시키는 등의 비양식적인 내용을 게재하는 행위<BR>
<BR>
▷제15조 (게시물의 저작권)<BR>
1. 서비스에 게시된 게시물에 대한 권리와 책임은 게시물을 등록한 게시자에게 있습니다. <BR>
2. 게시물이 타인의 지적재산권을 침해한 경우 게시자가 전적인 책임을 집니다. <BR>
3. 인천교구(Caincheon.or.kr) 제공 게시물의 저작권 <BR>
 ① 인천교구(Caincheon.or.kr)에서 제공하는 모든 저작물의 저작권 및 기타 지적재산권은 인천교구(Caincheon.or.kr)에 귀속함을 원칙으로 하며, 인천교구(Caincheon.or.kr) 소속이 아닌 외부인의 자격으로 게시물을 등록할 경우 인천교구(Caincheon.or.kr)와 외부 게시자는 공동의 저작권 및 기타 지적재산권을 갖습니다. 　<BR>
 ② 회원은 사이트(이메일 서비스 포함)를 이용함으로써 얻은 정보를 인천교구(Caincheon.or.kr)의 사전 승낙 없이 복제, 송신, 출판, 재배포, 방송 기타 방법에 의하여 영리 목적으로 이용할 수 없습니다. (단, 이용자가 사적으로 이용하는 경우는 그러하지 아니합니다.) 　<BR>
 ③ 인천교구(Caincheon.or.kr)에서 링크를 통해 제공하는 다른 사이트의 비밀 보장 관행과 사이트의 내용에 관해서는 책임지지 않습니다. 　<BR>
 ④ 인천교구(Caincheon.or.kr)는 회원이 게시하거나 등록하는 서비스 내의 내용물, 게시 내용에 대해 제 13조 각 호에 해당된다고 판단되는 경우 사전통지 없이 삭제하거나 이동 또는 등록 거부할 수 있습니다.<BR>
<BR>
▷제16조 (게시물에 대한 인천교구(Caincheon.or.kr)의 책임)<BR>
1. 인천교구(Caincheon.or.kr)는 게시물 검열의 의무를 지지 않으며 법률, 규정, 법적 절차 또는 정부의 요청에 정보를 공개하거나, 또는 인천교구(Caincheon.or.kr)의 전적인 재량으로 정보 내지 자료의 전부 또는 일부를 편집할 수 있으며 송부 거절할 수 있는 권리를 갖습니다. <BR>
2. 회원은 모든 게시판 서비스가 공공적인 통신이므로 이용자의 통신이 귀하가 알지 못하게 다른 사람에 의하여 읽힐 수 있으므로 누구에 관한 것인지 식별할 수 있는 이용자 또는 이용자의 가족들에 관한 정보를 게시할 때는 항상 주의를 기울여야 합니다. <BR>
3. 인천교구(Caincheon.or.kr)는 사전 통지 없이 회원이 게시판에 등록한 게시물의 일부를 삭제할 수 있으며 또는 전부에 접속하는 것을 종료시킬 수 있는 권한을 갖습니다. <BR>
4. 인천교구(Caincheon.or.kr)는 게시물에 관한 책임 및 이용자가 게시판 서비스에 참여함으로써 발생하는 어떠한 사건에 대한 책임을 지지 않음을 명백히 알립니다.<BR>
<BR>
<em>[제5장 기 타]</em>
▷제17 조 (손해배상)<BR>
인천교구(Caincheon.or.kr)는 서비스에서 무료로 제공하는 서비스의 이용과 관련하여 개인정보보호정책에서 정하는 내용에 해당하지 않는 사항에 대하여는 어떠한 손해도 책임을 지지 않습니다. <BR>
<BR>
▷제18 조 (면책조항)<BR>
1. 인천교구(Caincheon.or.kr)는 천재지변, 전쟁 및 기타 이에 준하는 불가항력으로 인하여 서비스를 제공할 수 없는 경우에는 서비스 제공에 대한 책임이 면제됩니다. <BR>
2. 인천교구(Caincheon.or.kr)는 기가 통신 사업자의 과실이나 서비스용 설비의 보수, 교체, 정기점검, 공사 등 부득이한 사유로 발생한 손해에 대한 책임이 면제됩니다. <BR>
3. 인천교구(Caincheon.or.kr)는 회원의 귀책사유로 인한 서비스 이용의 장애 또는 손해에 대하여 책임을 지지 않습니다. <BR>
4. 인천교구(Caincheon.or.kr)는 이용자의 컴퓨터 오류에 의해 손해가 발생한 경우, 또는 회원이 신상정보 및 전자우편 주소를 부실하게 기재하여 손해가 발생한 경우 책임을 지지 않습니다. <BR>
5. 인천교구(Caincheon.or.kr)는 회원이 서비스를 이용하여 기대하는 수익을 얻지 못하거나 상실한 것에 대하여 책임을 지지 않습니다. <BR>
6. 인천교구(Caincheon.or.kr)는 회원이 서비스를 이용하면서 얻은 자료로 인한 손해에 대하여 책임을 지지 않습니다. 또한 인천교구(Caincheon.or.kr)는 회원이 서비스를 이용하며 타 회원으로 인해 입게 되는 정신적 피해에 대하여 보상할 책임을 지지 않습니다. <BR>
7. 인천교구(Caincheon.or.kr)는 회원이 서비스에 게재한 각종 정보, 자료, 사실의 신뢰도, 정확성 등 내용에 대하여 책임을 지지 않습니다. <BR>
<BR>
▷제19 조 (고충처리)<BR>
인천교구(Caincheon.or.kr)는 개인정보 관리, 서비스 이용 등에 대한 회원들의 불편 및 불만 사항, 기타 의견을 홈페이지, 전자우편, 전화, 서면 등을 통해 접수하고 처리합니다. <BR>
<BR>
▷제20조 (분쟁 해결 및 관할법원)<BR>
1. 본 약관에 규정된 것을 제외하고 발생하는 서비스 이용에 관한 제반 문제에 관한 분쟁은 최대한 합의에 의해 해결하도록 합니다. <BR>
2. 인천교구(Caincheon.or.kr) 및 회원은 개인정보에 관한 분쟁이 있는 경우 신속하고 효과적인 분쟁해결을 위하여 개인정보분쟁조정위원회에 분쟁의 조정을 신청할 수 있습니다. <BR>
3. 서비스 이용으로 발생한 분쟁에 대해 소송이 제기될 경우 인천교구(Caincheon.or.kr)의 소재지를 관할하는 법원 또는 대한민국의 민사소송법에 따른 법원을 관할법원으로 합니다. <BR>
<BR>
<BR>
부 칙<BR>
(시행일) 변경된 약관은 2014년 12월 01일자부터 적용되며, 종전의 약관은 본 약관으로 대체합니다.
                        </div>
                        <p><input type="checkbox" id="agreement1"><label for=""><span>상기 이용약관 내용을 읽었으며, 동의합니다.</span></label></p>
                    </div>
                    <h3 class="ttl tb">개인정보 수집 및 이용에 관한 동의</h3>
                    <div class="personal">
                        <div>
                          <em><천주교 인천교구>('www.caincheon.or.kr'이하 '인천교구')은(는) 개인정보 보호법 제30조에 따라 정보주체의 개인정보를 보호하고 이와 관련한 고충을 신속하고 원활하게 처리할 수 있도록 하기 위하여 다음과 같이 개인정보 처리지침을 수립 ㆍ 공개합니다. 천주교 인천교구에서 취급하는 모든 개인정보는 관련법령에 근거하거나 정보주체의 동의에 의하여 수집ㆍ보유 하고 있습니다.</em>
○ 인천교구의 개인정보 보호정책은 다음과 같은 내용을 담고 있습니다. (개정 2018년 04월 01일)<BR>
<BR>
가. 개인정보의 수집 및 이용목적<BR>
나. 개인정보의 관리<BR>
다. 회원 및 법정대리인의 권리와 그 행사방법<BR>
라. 개인정보의 제공<BR>
마. 개인정보의 파기 절차 및 방법<BR>
바. 의무 및 책임<BR>
<BR>
가. 개인정보의 수집 및 이용목적<BR>
인천교구는 개인정보를 개인의 동의 없이 수집하지 않습니다. <BR>
인천교구의 개인정보보호정책 또는 이용약관의 내용에 대해 「동의」또는「취소」항목을 체크할 수 있는 절차를 마련하여 , 「동의」항목을 체크하면 개인정보 수집에 대해 동의한 것으로 봅니다.<BR>
<BR>
1. 개인정보의 수집목적 및 수집범위 <BR>
인천교구는 회원에게 기본적으로 제공되는 서비스 외 부가적인 서비스 제공을 위하여 필요한 최소한의 개인정보를 수집하고 있습니다.<BR>
　 ① 회원 관리<BR>
　 　 회원관리 및 서비스, 회원공지 및 뉴스레터 제공, 가입 및 탈퇴 의사 확인<BR>
<BR>
2. 개인정보 수집항목<BR>
인천교구는 회원가입, 상담, 서비스 신청 등을 위해 아래와 같은 개인정보를 수집하고 있습니다.<BR>
　 ① 필수 수집항목 <BR>
　 　 성명, 이메일, 비밀번호, 쿠키<BR>
　 ② 선택 수집항목<BR>
　 　 세례명, 소속본당, 생년월일, 축일, 주소 등<BR>
　 ③ 수집방법<BR>
　 　 홈페이지, 양식폼, 이메일, 팩스<BR>
<BR>
3. 쿠키(cookie)의 운영에 관한 사항<BR>
인천교구는 이용자들의 정보를 저장하고 수시로 불러오는 '쿠키(cookie)'를 사용합니다.<BR>
쿠키란 웹사이트를 운영하는데 이용되는 서버가 이용자의 브라우저에 보내는 아주 소량의 정보이며 이용자의 컴퓨터 하드디스크에 저장됩니다.<BR>
<BR>
이용자들이 인천교구에 접속한 후 로그인(LOG-IN)하여 회원서비스 등의 서비스를 이용하기 위해서는 쿠키를 허용하셔야 합니다. 인천교구는 이용자들에게 적합하고 보다 유용한 서비스를 제공하기 위해서 쿠키를 이용하여 아이디에 대한 정보를 찾아냅니다. 쿠키는 이용자의 컴퓨터는 식별하지만 이용자를 개인적으로 식별하지는 않습니다.<BR>
<BR>
이용자들은 쿠키에 대하여 사용여부를 선택할 수 있습니다. 웹브라우저에서 옵션을 설정함으로써 모든 쿠키를 허용할 수도 있고, 쿠키가 저장될 때마다 확인을 거치거나, 모든 쿠키의 저장을 거부할 수도 있습니다. 다만, 쿠키의 저장을 거부할 경우에는 로그인이 필요한 인천교구 일부 서비스는 이용할 수 없습니다.<BR>
<BR>
[쿠키 설정을 거부하는 방법]<BR>
　 - 쿠키 설치 허용 여부를 지정하는 방법<BR>
　 　 * Internet Explorer 를 사용하고 있는 경우 <BR>
　 　 1. [도구] 메뉴에서 [인터넷 옵션]을 선택합니다.<BR>
　 　 2. [개인정보 탭]을 클릭합니다.<BR>
　 　 3. [개인정보보호 수준]을 설정하시면 됩니다.<BR>
<BR>
　 - 받은 쿠키를 보는 방법<BR>
　 　 * Internet Explorer 를 사용하고 있는 경우 <BR>
　 　 1. [작업 표시줄]에서 [도구]를 클릭한 다음<BR>
　 　 2. [인터넷 옵션]을 선택합니다.<BR>
　 　 3. [일반 탭(기본 탭)]에서 [설정]을 클릭한 다음<BR>
　 　 4. [파일 보기]를 선택합니다.<BR>
<BR>
나. 개인정보의 관리<BR>
1. 개인정보의 정확성, 최신성을 확보하기 위한 대책<BR>
인천교구는 회원의 개인정보의 정확성 및 최신성을 확보하기 위하여 다음과 같은 절차를 마련하고 있습니다.<BR>
　 ① 개인정보를 정확하게 관리하기 위해 개인정보 입력시 입력내용을 확인하는 절차를 거치게 됩니다.<BR>
　 ② 오류 정보를 발견한 경우 이를 정정하거나 삭제할 수 있도록 하는 절차를 두고 있습니다.<BR>
　 ③ 회원은 개인정보에 대한 열람 및 정정 요구시 올바른 정보를 입력할 수 있는 절차나 방법을 마련하고 있습니다.<BR>
　 ④ 개인정보의 보유 및 보존 기간 또는 이용기간이 경과하거나 필요없게 된 개인정보를 정기적으로 파기처분하기 위한 절차를 마련하고 있습니다.<BR>
2. 개인정보보호를 위한 기술적 대책<BR>
인천교구는 회원의 개인정보를 취급함에 있어 개인정보가 분실, 도난, 누출, 변조 또는 훼손되지 않도록 안전성 확보를 위하여 다음과 같은 기술적 대응을 강구하고 있습니다. <BR>
　 ① 회원의 개인정보는 비밀번호에 의해 보호되며 중요한 데이터는 별도의 보안기능을 통해 보호되고 있습니다.<BR>
　 ② 해킹이나 각종 보안사고 방지를 위한 보안패치 작업을 실행하고 있습니다.<BR>
3. 개인정보보호를 위한 관리적 대책<BR>
인천교구는 개인정보의 안전성을 확보하기 위하여 개인정보에 대한 접근 및 관리에 필요한 절차를 마련하고 있으며 , 운영자 PC 의 무단사용 방지를 위하여 PC 별로 사용자를 지정한 후 비밀번호를 부여하고 정기적으로 갱신하고 있습니다. 또한 개인정보가 있는 컴퓨터에 접근할 수 있는 컴퓨터 (PC) 를 제한하고 있습니다 .<BR>
<BR>
4. 개인정보의 파기<BR>
인천교구는 회원이 회원탈퇴를 요청한 경우 또는 회원에게 사전 고지한 개인정보를 제공 받은 목적이 달성된 경우, 수집한 개인정보를 지체 없이 파기합니다.<BR>
<BR>
다. 회원 및 법정대리인의 권리와 그 행사방법<BR>
1. 회원 및 법정 대리인은 언제든지 등록되어 있는 자신 혹은 당해 만 14세 미만 아동의 개인정보를 조회하거나 수정할 수 있으며 가입해지를 요청할 수도 있습니다. 회원 혹은 만 14세 미만 아동의 '개인정보변경'(또는 '회원정보수정') 및 가입해지(동의철회)를 위해서는 개인정보관리 책임자에게 서면, 전화 또는 이메일로 연락하시면 지체없이 조치하겠습니다. 회원이 개인정보의 오류에 대한 정정을 요청하신 경우에는 정정을 완료하기 전까지 당해 개인정보를 이용 또는 제공하지 않습니다. 또한 잘못된 개인정보를 제3자에게 이미 제공한 경우에는 정정 처리결과를 제3자에게 지체없이 통지하여 정정이 이루어지도록 하겠습니다. 인천교구는 회원 혹은 법정 대리인의 요청에 의해 해지 또는 삭제된 개인정보는 "개인정보의 관리"에 명시된 바에 따라 처리하고 그 외의 용도로 열람 또는 이용할 수 없도록 처리하고 있습니다.<BR>
2. 회원은 개인정보에 대한 이용 , 처리와 관련하여 개인정보의 정확성 및 안전성 등을 확보함으로써 잘못된 정보로 인한 피해를 사전에 예방하기 위해 다음과 같은 권리를 행사할 수 있습니다.<BR>
　 ① 개인정보 확인 및 정정<BR>
　 　 회원은 인천교구를 통하여 언제든지 회원가입 등록되어 있는 본인의 개인정보 확인을 문의하거나 정정을 요청할 수 있습니다. <BR>
　 ② 의견수렴 및 불만처리<BR>
　 　 인천교구는 개인정보보호와 관련하여 회원의 의견과 불만을 제기할 수 있는 창구를 개설하고 있습니다. 개인정보와 관련한 불만이 있으신 분은 인천교구의 개인정보 관리 책임자 (아래 제 8조에서 규정함) 에게 의견을 주시면 접수 후 조치하여 처리결과를 통보 드립니다.<BR>
<BR>
라. 개인정보의 제공<BR>
인천교구는 회원의 개인정보를 원칙적으로 외부에 제공하지 않습니다. 다만, 아래의 경우에는 예외로 합니다.<BR>
　 ① 이용자들이이 사전에 동의한 경우<BR>
　 ② 법령의 규정에 의거하거나, 수사 목적으로 법령에 정해진 절차와 방법에 따라 수사기관의 요구가 있는 경우<BR>
<BR>
마. 개인정보의 파기절차 및 방법<BR>
인천교구는 원칙적으로 개인정보 수집 및 이용목적이 달성된 후에는 해당 정보를 지체 없이 파기합니다.<BR>
파기절차 및 방법은 다음과 같습니다.<BR>
1. 파기절차<BR>
회원이 입력한 정보는 목적이 달성된 후 내부 방침 및 기타 관련 법령에 의한 정보보호 사유에 따라(보유 및 이용기간 참조) 일정기간 저장된 후 파기되어집니다.<BR>
별도 DB로 옮겨진 개인정보는 법률에 의한 경우가 아니고서는 보유되어지는 목적 이외의 다른 용도로 사용되지 않습니다.<BR>
2. 파기방법<BR>
파일형태로 저장된 개인정보는 기록을 재생할 수 없는 기술적 방법을 사용하여 삭제합니다.<BR>
종이에 출력된 개인정보는 분쇄기로 분쇄하거나 소각을 통하여 파기합니다.<BR>
<BR>
바. 의무 및 책임<BR>
1. 인천교구는 개인정보의 누출 및 오 , 남용 등으로 인한 피해를 방지하고 , 회원의 개인정보를 보호하기 위하여 다음과 같이 개인정보 관리책임자 및 홈페이지 정보 관리자를 지정하고 있습니다.<BR>
　 근무시간 : 평일 09:30~18:00 <BR>
　 토요일 및 일요일, 공휴일은 휴무입니다.<BR>
<BR>
　 보안책임자 : 김태우 차장<BR>
　 정보관리자 : 최현진 과장<BR>
　 전자메일 : webadmin@caincheon.or.kr<BR>
　 전화 : 032-765-6983 팩스 : 032-765-6211<BR>
　 주소 : 인천광역시 동구 박문로 1 (송림동 103-25번지) 인천교구청 전산실<BR>

2. 회원은 인천교구의 서비스를 이용하며 발생하는 모든 개인정보보호 관련 민원을 개인정보관리 책임자 혹은 담당부서로 신고하실 수 있습니다. 천주교 인천교구는 회원의 신고사항에 대해 신속하게 충분한 답변을 드릴 것입니다.<BR>
3. 기타 개인정보침해에 대한 신고나 상담이 필요하신 경우에는 아래 기관에 문의하시기 바랍니다.<BR>
　 개인정보침해신고센터  (국번 없이)118 http://privacy.kisa.or.kr  <BR>
　 대검찰청 사이버범죄수사단  02-3480-3571 cybercid@spo.go.kr  <BR>
　 경찰청 사이버테러대응센터  (국번 없이)182 http://cyberbureau.police.go.kr  <BR>
                        </div>
                        <p><input type="checkbox"  id="agreement2"><label for=""><span>상기 개인정보 취급 및 이용에 관한 내용을 읽었으며, 동의합니다.</span></label></p>
                    </div>
                    <h3 class="ttl fl tb v5">회원정보 입력 </h3>
                    <p class="starTxt fr">*이메일 인증 후 회원가입이 가능합니다.</p>
                    <!-- writeTable -->
                    
                    <div class="writeTable">
                        <table class="shirine_st">
                        <caption>회원정보 입력</caption>
                            <tbody>
                                <tr>
                                    <th><i>*</i>신자구분</th>
                                    <td>
                                        <span class="chkForm">
                                            <input type="radio" id="member_type1" name="member_type" value="1" onClick="changeMemberType(1)" checked="checked">
                                            <label for=""><span>신자</span></label>
                                        </span>
                                        <span class="chkForm">
                                            <input type="radio" id="member_type2" name="member_type" value="2" onClick="changeMemberType(2)" >
                                            <label for=""><span>예비신자</span></label>
                                        </span>
                                        <span class="chkForm">
                                            <input type="radio" id="member_type3" name="member_type" value="3" onClick="changeMemberType(3)" >
                                            <label for=""><span>비신자</span></label>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <th><i>*</i>이메일(아이디)</th>
                                    <td>
                                        <span class="form">
                                            <label for=""></label>
                                            <input type="email" id="id" name="id" value="${id}" onBlur="javascript:isDupMemberId()">
                                        </span>
                                        <span class="redTxt">* 이메일은 본인 인증수단으로 활용되오니 정확하게 입력하세요. <span id="id_check_msg" class="redTxt" style="font-weight: 700;"></span></span>
                                    </td>
                                </tr>
                                <tr>
                                    <th>패스워드</th>
                                    <td>
                                        <span class="form">
                                            <label for=""></label>
                                            <input type="password" id="password" name="password">
                                        </span>
                                        <span class="redTxt">* 영문,숫자,특수문자를 포함하여 10자리 이상 입력하세요</span>
                                    </td>
                                </tr>
                                <tr>
                                    <th>패스워드 확인</th>
                                    <td>
                                        <span class="form">
                                            <label for=""></label>
                                            <input type="password" id="password2" name="password2">
                                        </span>
                                        <span class="redTxt">* 작성하신 패스워드를 정확하게 입력하세요.</span>
                                    </td>
                                </tr>
                                <tr>
                                    <th><i>*</i>성명</th>
                                    <td>
                                        <span class="form">
                                            <label for=""></label>
                                            <input type="text" name="name" id="name">
                                        </span>
                                        <span class="redTxt">* 정확한 실명을 작성하세요.</span>
                                    </td>
                                </tr>
                                <tr id="tr_baptismal_name">
                                    <th><i>*</i>세례명</th>
                                    <td>
                                        <span class="form">
                                            <label for=""></label>
                                            <input type="text" id="baptismal_name" name="baptismal_name">
                                        </span>
                                        <span class="redTxt">* 정확한 세레명을 작성하세요.</span>
                                    </td>
                                </tr>
                                <tr>
                                    <th>교구구분</th>
                                    <td>
                                        <span class="chkForm">
                                            <input type="radio" name="is_incheon_gyugo" value="Y" onClick="gyoguSelection('Y')" checked="checked">
                                            <label for=""><span>인천교구</span></label>
                                        </span>
                                        <span class="chkForm">
                                            <input type="radio" name="is_incheon_gyugo" value="N" onClick="gyoguSelection('N')" >
                                            <label for=""><span>타교구</span></label>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <th><i>*</i>관할본당</th>
                                    <td>
                                        <span class="form">
                                            <label for=""></label>
                                            <select name="church_idx" id="church_idx">
                                                <option value="0">선택</option>
                                                <c:if test="${fn:length(CHURCH_LIST) > 0}">
	                                                <c:forEach var="entry" items="${CHURCH_LIST}" varStatus="status">
	                                                	<option value="${entry.CHURCH_IDX}">${entry.NAME}</option>
	                                                </c:forEach>
	                                            </c:if>
                                            </select>
                                        </span>
                                        <span class="redTxt">예비신자께서는 영세 받을 성당, 현재다니고 있는 성당 혹은 주소지에서 가장 가까운 성당의 이름을 선택하여 주시기 바랍니다.</span>
                                    </td>
                                </tr>
                                <tr  id="tr_festival">
                                    <th><i>*</i>축일</th>
                                    <td>
                                        <span class="form calenr">
                                            <select name="festivalM" id="festivalM">
                                                <option value="00">선택</option>
                                                <option value="01">1</option>
                                                <option value="02">2</option>
                                                <option value="03">3</option>
                                                <option value="04">4</option>
                                                <option value="05">5</option>
                                                <option value="06">6</option>
                                                <option value="07">7</option>
                                                <option value="08">8</option>
                                                <option value="09">9</option>
                                                <option value="10">10</option>
                                                <option value="11">11</option>
                                                <option value="12">12</option>
                                            </select><label for=""><span class="textSel">월</span></label>
                                            <select name="festivalD" id="festivalD">
                                                <option value="00">선택</option>
                                                <option value="01">1 </option>
												<option value="02">2 </option>
												<option value="03">3 </option>
												<option value="04">4 </option>
												<option value="05">5 </option>
												<option value="06">6 </option>
												<option value="07">7 </option>
												<option value="08">8 </option>
												<option value="09">9 </option>
												<option value="10">10</option>
												<option value="11">11</option>
												<option value="12">12</option>
												<option value="13">13</option>
												<option value="14">14</option>
												<option value="15">15</option>
												<option value="16">16</option>
												<option value="17">17</option>
												<option value="18">18</option>
												<option value="19">19</option>
												<option value="20">20</option>
												<option value="21">21</option>
												<option value="22">22</option>
												<option value="23">23</option>
												<option value="24">24</option>
												<option value="25">25</option>
												<option value="26">26</option>
												<option value="27">27</option>
												<option value="28">28</option>
												<option value="29">29</option>
												<option value="30">30</option>
												<option value="31">31</option>
                                            </select><label for=""><span class="textSel">일</span></label>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <th><i>*</i>전화번호</th>
                                    <td class="telPhone">
                                        <span class="form">
                                            <label for=""></label>
                                            <select name="tel1" id="tel1">
                                                <option value="010">010</option>
                                                <option value="011">011</option>
                                                <option value="016">016</option>
                                                <option value="017">017</option>
                                                <option value="018">018</option>
                                                <option value="019">019</option>
                                                <option value="02" >02</option>
                                                <option value="031">031</option>
                                                <option value="032">032</option>
                                                <option value="070">070</option>
                                            </select>
                                            <span class="bar">-</span>
                                            <input type="tel" id="tel2" name="tel2" maxlength="4" onkeypress="return okayDigit(event)">
                                            <span class="bar">-</span>
                                            <input type="tel" id="tel3" name="tel3" maxlength="4" onkeypress="return okayDigit(event)">
                                        </span>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <!-- //writeTable -->
                    <!-- btn -->
                    <ul class="btn">
                    	<c:set var="btn_text" value="인증하기" />
                    	<c:if test="${login_type eq '2'}"><c:set var="btn_text" value="가입하기" /></c:if>
                        <li id="btn_insert"><a href="javascript:regsite()">${btn_text}</a></li>
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
</body>

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

var preSelectedVal = "0";
function gyoguSelection(tp) {
	switch(tp) {
	case 'Y'://인천교구
		$("#church_idx").val(preSelectedVal).prop("selected", true);//비활성화직전 선택된값을 선택세팅
		$("#church_idx").prop("disabled","");//활성화
		break;
	case 'N'://타교구
		//preSelectedVal = $("#church_idx option:selected").val();//선택된값을임시저장
		$("#church_idx").val("0").prop("selected", true);//리셋선택
		$("#church_idx").prop("disabled","none");//비활성화
		break;
	}
	preSelectedVal = tp;//선택된값을임시저장
}
//
function CheckField(){
	
	// ====== null check
	if( $("#agreement1").is(":checked") == false) { alert("'이용약관'에 동의가 필요합니다."); $("#agreement1").focus(); return false; }
	if( $("#agreement2").is(":checked") == false) { alert("'개인정보 수집 및 이용에 관한 동의'에 동의가 필요합니다."); $("#agreement2").focus(); return false; }
	if( $("#id").val() == "") { alert("아이디를 입력해 주세요."); $("#id").focus(); return false; }
	if (id_checked != true) { alert("아이디 중복 체크를 해 주세요."); $("#id").focus(); return false; }
	
	// 정규식 - 이메일 유효성 검사
	var regEmail = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	if(!regEmail.test($("#id").val())) { alert('이메일 주소가 유효하지 않습니다'); $("#id").focus(); return false; }
	
	if( $("#password").val() == "") { alert("패스워드를 입력해 주세요."); $("#password").focus(); return false; }
	if( $("#password2").val() == "") { alert("패스워드 확인를 입력해 주세요."); $("#password2").focus(); return false; }
	if( $("#password").val() != $("#password2").val()) { alert("암호가 일치하지 않습니다."); return false; }
	
	var reg_pwd = /^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;
	if ( $("#password").val().length < 10 ) {
		alert("비밀번호는 10자리 이상이어야 합니다."); return false;
	} else if( !reg_pwd.test($("#password").val()) ) {
		alert("비밀번호 규칙에 맞지 않습니다."); return false;
	}
	
	if( $("#name").val() == "") { alert("이름을 입력해 주세요."); $("#name").focus(); return; }
	
	// 신자라면,,
	if( member_type_val==1 && $("#baptismal_name").val() == "") { alert("세례명을 입력해 주세요."); $("#baptismal_name").focus(); return false; }
	
	if( $("#church_idx option:selected").val()=="0" && preSelectedVal == 'Y') { alert("관할 본당을 선택 해 주세요.") ; return false ; }
	
	// 신자라면,,
	if( member_type_val==1 && $("#festivalM option:selected").val()=="00") { alert("세레받은 월을 선택 해 주세요.") ; return false ; }
	// 신자라면,,
	if( member_type_val==1 && $("#festivalD option:selected").val()=="00") { alert("세레받은 일을 선택 해 주세요.") ; return false ; }
	
	if( $("#tel2").val() == "") { alert("전화번호를 입력해 주세요."); $("#tel2").focus(); return false; }
	if( $("#tel3").val() == "") { alert("전화번호를 입력해 주세요."); $("#tel3").focus(); return false; }
	
	// 정규식 -전화번호 유효성 검사
	var _tel = $("#tel1").val() + $("#tel2").val() + $("#tel3").val();
	_tel = $("#tel2").val() + $("#tel3").val();
	var regPhone = /^((01[1|6|7|8|9])[1-9]+[0-9]{6,7})|(010[1-9][0-9]{7})$/;
	regPhone = /^(0[1-9]+[0-9]{6,7})|(010[1-9][0-9]{7})$/;
	
	if( $("#tel1").val().indexOf("010")==0 && _tel.length != 8 ) { // 010
		alert('전화번호가 유효하지 않습니다.'); return false;
	} else if( $("#tel1").val().indexOf("01")==0 && _tel.length < 7 ) { // 016~019
		alert('전화번호가 유효하지 않습니다..'); return false;
	} else if( $("#tel3").val().length != 4 ) {
		alert('전화번호가 유효하지 않습니다...'); return false;
	}
	return true;
}

//
function regsite() {
	if(!CheckField()) {
		return false;
	}

	// send
	if( confirm("신청하시겠습니까 ? ") ) {
		console.log("전송데이터1:"+$("#frm").serialize());
		console.log("전송데이터2:"+$("form[name=frm]").serialize());

		$("#btn_insert").html("<a href='#'>가입 처리 중..</a>");
		$("#btn_insert").children("a").remove();//event unbind, if <input, then $("#").off() 
		$("#btn_insert").delay(500).html("<a href='#'>메일 발송 중..</a>");
		
		$("#frm").attr("action","/member/join.do");
		_requestPost("frm", function(status, responseData) {
			console.log("response: status="+status);
			console.log("response: responseData="+JSON.stringify(responseData));
			console.log("RESULT_YN="+responseData.RESULT_YN);
			
			if(responseData.RESULT_YN == 'N' || status == 'error') {
				if(responseData.MESSAGE != "") 
					alert(responseData.MESSAGE);
				else 
					alert(JSON.stringify(responseData));
				
				$("#btn_insert").children("a").on("click", function(){ insert() } );
				$("#btn_insert").delay(8).html("<a href='#'>관리자 확인 필요</a>");
				
			} else if(responseData.RESULT_YN == 'Y') {
				$("#btn_insert").delay(8).html("<a href='#'>가입 완료 !!</a>").delay(1000);
				if( $("#login_type").val() == "2" ) {
					alert("회원가입이 완료되었습니다.");
					location.href="/member/login.jsp?gotoBACK=/member/myinfo.jsp";
				} else {
					location.href="/member/step_01_03.jsp";
				}
			}
		});
	}
}

// just digit
function okayDigit(event) {
	var code = event.which ? event.which : event.keyCode;
	if(code < 48 || code > 57) {
		return false;
	}
}

// id dup check
var id_checked = false;
function isDupMemberId() {
	
	var formData = new FormData($('frm')[0]);
	formData.append("id", $("#id").val());
	
	_requestByAjax('/member/idDupCheck.do', formData, function(status, responseData){
		console.log("status="+status);
		console.log("responseData="+JSON.stringify(responseData));
		console.log("RESULT_YN="+responseData.RESULT_YN);
		
		if(responseData.RESULT_YN == 'Y') {
			$("#id_check_msg").html("<BR><font color='blue'>* 등록가능한 ID 입니다.</font>");
			id_checked = true;
		} else if(status == 'error' || responseData.RESULT_YN == 'N') {
			if(responseData.MESSAGE != "") 
				$("#id_check_msg").html("<BR>* 이미 존재하는 ID 입니다.");
			else 
				alert(JSON.stringify(responseData));
		}
	});
}
</script>

</html>