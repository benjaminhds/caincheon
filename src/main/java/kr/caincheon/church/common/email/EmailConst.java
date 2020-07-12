// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmailConst.java

package kr.caincheon.church.common.email;


public class EmailConst
{

	public static final String EMAIL_DORMANTCLEAR_URL = "http://www.caincheon.or.kr/member/dormantClearConfirm.do";

	// 휴면계정 알림 메일
    public static String getEMAIL_CONTENTS_DORMANT(String id)
    {
        return "\n <table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"margin-top:20px; padding:30px; border-top:10px solid #2368ad\"  width=\"600px\" bgcolor=\"#f5f5f5\" > "
        		+"\n    <tr> "
        		+"\n        <td colspan=\"2\" style=\"vertical-align:top; padding:0; margin:0;  padding-bottom:20px; border-bottom:1px dashed #acacac; font-size:24px; font-family:'Dotum'; letter-spacing:-0.75px; color:#2368ad;\" align=\"center\" >[천주교 인천교구] 계정의 휴면처리 알림 메일</td> "
        		+"\n    </tr> "
        		+"\n    <tr> "
        		+"\n         <td colspan=\"2\" style=\"vertical-align:top;  color: #333333; padding:0; margin:0; font-family:'Dotum';font-size:14px;  line-height:2;  padding-bottom:10px;padding-top:25px; \" align=\"center\" > "
        		+"\n             <BR><span style=\"color:#0095ff; font-family:'Dotum'; font-weight:bold; font-size:14px; display:block; text-align:center; \">천주교 인천교구에서 귀한의 계정 휴면되었음을 알립니다.</span> "
        		+"\n             <BR><span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block; text-align:center; \">계정을 안전하게 보호하기 위하여, 실행되고 1년 이상 로그인 하지 않은 아이디는 휴먼상태로 전환됩니다. <br/> "
        		+"\n             <BR>휴면해제는 담당자 이메일(webadmin@caincheon.or.kr)로 신청 메일을 보내 주세요. "
        		+"\n             </span> <BR> &nbsp; "
        		+"\n         </td> "
        		+"\n    </tr> "
//        		+"\n    <tr> "
//        		+"\n        <td colspan=\"2\"  style=\"vertical-align:top;  color: #ff0000; padding:30px 0; margin:0; font-family:'Dotum';\" align=\"center\"> "
//        		+"\n             <a style=\"border-radius:5px; border:0; background:#0a2966; color:#fff; width:100%; font-size:18px; cursor:pointer; width:90%; font-style:normal; padding:15px 0; display:block; font-weight:bold; font-style:normal; text-decoration:inherit; \">휴면해제는 이메일(webadmin@caincheon.or.kr)로 신청해 주세요.</a> "
//        		+"\n        </td> "
//        		+"\n    </tr> "
        		+"\n    <tr> "
        		+"\n        <td colspan=\"2\" style=\"vertical-align:top; padding:0; margin:0; padding-top:40px;\" align=\"center\"> "
        		+"\n            <img src=\"http://www.caincheon.or.kr/img/foot_logo.png\" > "
        		+"\n        </td> "
        		+"\n    </tr> "
        		+"\n </table> ";
    }
    
	// 휴면계정 해제
    public static String getEMAIL_CONTENTS_DORMANTCLEAR(String id, String token)
    {
        return "\n <table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"margin-top:20px; padding:30px; border-top:10px solid #2368ad\"  width=\"600px\" bgcolor=\"#f5f5f5\" > "
        		+"\n    <tr> "
        		+"\n        <td colspan=\"2\" style=\"vertical-align:top; padding:0; margin:0;  padding-bottom:20px; border-bottom:1px dashed #acacac; font-size:24px; font-family:'Dotum'; letter-spacing:-0.75px; color:#2368ad;\" align=\"center\" >[천주교 인천교구] 휴면계정 해제 인증 메일</td> "
        		+"\n    </tr> "
        		+"\n    <tr> "
        		+"\n         <td colspan=\"2\" style=\"vertical-align:top;  color: #333333; padding:0; margin:0; font-family:'Dotum';font-size:14px;  line-height:2;  padding-bottom:10px;padding-top:25px; \" align=\"center\" > "
        		+"\n             <span style=\"color:#0095ff; font-family:'Dotum'; font-weight:bold; font-size:14px; display:block; text-align:center; \">천주교 인천교구 계정의 휴면을 해제해주세요.</span> "
        		+"\n             <span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block; text-align:center; \">계정을 안전하게 보호하기 위하여 1년 이상 로그인 하지 않은 아이디는 휴먼상태로 전환됩니다. <br/> "
        		+"\n             서비스를 계속 이용하시려면 아래 ‘휴먼 해제하기‘ 버튼을 클릭해주세요. (7일간 유효합니다.)"
        		+"\n             </span> "
        		+"\n         </td> "
        		+"\n    </tr> "
        		+"\n    <tr> "
        		+"\n        <td colspan=\"2\"  style=\"vertical-align:top;  color: #ff0000; padding:30px 0; margin:0; font-family:'Dotum';\" align=\"center\"> "
        		+"\n             <a href=\""+EMAIL_DORMANTCLEAR_URL+"?stoken="+token+"&id="+id+"\" target=\"_blank\" title=\"Dormant Clear\"  style=\"border-radius:5px; border:0; background:#0a2966; color:#fff; width:100%; font-size:18px; cursor:pointer; width:90%; font-style:normal; padding:15px 0; display:block; font-weight:bold; font-style:normal; text-decoration:inherit; \">휴면해제하기</a> "
        		+"\n        </td> "
        		+"\n    </tr> "
        		+"\n    <tr> "
        		+"\n        <td colspan=\"2\" style=\"vertical-align:top; padding:0; margin:0; padding-top:40px;\" align=\"center\"> "
        		+"\n            <img src=\"http://www.caincheon.or.kr/img/foot_logo.png\" > "
        		+"\n        </td> "
        		+"\n    </tr> "
        		+"\n </table> ";
    }

    /*
     * 회원가입 이메일인증 메일발송 내용
     */
    public static String getEMAIL_CONTENTS_MEMBER_JOIN(String id, String token)
    {
        return "<table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"margin-top:20px; padding:30px; border-top:10px solid #2368ad\"  width=\"600px\" bgcolor=\"#f5f5f5\" >"
    			+ "\n    <tr>"
    			+ "\n        <td colspan=\"2\" style=\"vertical-align:top; padding:0; margin:0;  padding-bottom:20px; border-bottom:1px dashed #acacac; font-size:24px; font-family:'Dotum'; letter-spacing:-0.75px; color:#2368ad;\" align=\"center\" >[천주교 인천교구] 인증번호 안내 메일 입니다.</td>"
    			+ "\n    </tr>"
    			+ "\n    <tr>"
    			+ "\n         <td colspan=\"2\" style=\"vertical-align:top;  color: #333333; padding:0; margin:0; font-family:'Dotum';font-size:14px;  line-height:2;  padding-bottom:10px;padding-top:25px; \" align=\"center\" >"
    			+ "\n             <span style=\"color:#0095ff; font-family:'Dotum'; font-weight:bold; font-size:14px; display:block; text-align:center; \">회원가입 이메일 인증</span>  "
    			+ "\n             <span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block; text-align:center; \">"
    			+ "\n                 <span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block;\">안녕하세요. 천주교 인천교구 입니다. <br/>"
    			+ "\n                 회원가입을 위해 입력하신 이메일을 인증하고자 합니다. <br/>"
    			+ "\n                 아래 이메일 인증완료 버튼을 누르시면 회원 가입이 완료되며,<br/>"
    			+ "\n                 천주교 인천교구 홈페이지 로그인 페이지로 이동 됩니다."
    			+ "\n                 </span>"
    			+ "\n             </span>"
    			+ "\n         </td>"
    			+ "\n    </tr>"
    			+ "\n    <tr>"
    			+ "\n        <td colspan=\"2\"  style=\"vertical-align:top;  color: #ff0000; padding:30px 0; margin:0; font-family:'Dotum';\" align=\"center\">"
    			+ "\n             <a href=\"http://www.caincheon.or.kr/member/join_confirm.do?token="+token+"&id="+id+"\" target=\"_blank\" title=\"새창\"  style=\"border-radius:5px; border:0; background:#0a2966; color:#fff; width:100%; font-size:18px; cursor:pointer; width:90%; font-style:normal; padding:15px 0; display:block; font-weight:bold; font-style:normal; text-decoration:inherit; \">이메일 인증 완료</a>"
    			+ "\n        </td>"
    			+ "\n    </tr>"
    			+ "\n    <tr>"
    			+ "\n        <td colspan=\"2\" style=\"vertical-align:top; padding:0; margin:0; padding-top:40px;\" align=\"center\">"
    			+ "\n            <img src=\"http://www.caincheon.or.kr/img/foot_logo.png\" >"
    			+ "\n        </td>"
    			+ "\n    </tr>"
    			+ "\n </table>";
    }

    /*
     * 회원 암호 찾기
     */
    public static String getEMAIL_CONTENTS_FIND_PWD(String id, String pwd, String token)
    {
        return "\n <table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"margin-top:20px; padding:30px; border-top:10px solid #2368ad\"  width=\"600px\" bgcolor=\"#f5f5f5\" >"
    			+ "\n    <tr>"
    			+ "\n        <td colspan=\"2\" style=\"vertical-align:top; padding:0; margin:0;  padding-bottom:20px; border-bottom:1px dashed #acacac; font-size:22px; font-family:'Dotum'; letter-spacing:-0.75px; color:#2368ad; font-weight:bold;\" align=\"center\" >[천주교 인천교구] 아이디/패스워드 찾기 메일 입니다</td>"
    			+ "\n    </tr>"
    			+ "\n    <tr>"
    			+ "\n         <td colspan=\"2\" style=\"vertical-align:top;  color: #333333; padding:0; margin:0; font-family:'Dotum';font-size:14px;  line-height:2;  padding-bottom:10px;padding-top:25px; \" align=\"center\" >"
    			+ "\n             <span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block; text-align:center; \">"
    			+ "\n                 <span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block;\">안녕하세요, <br>"
    			+ "\n                   아이디 또는 비밀번호를 잊으셨나요?<br>"
    			+ "\n                   아래 부여되는 아이디/비밀번호로 로그인 해주세요.<br>"
    			+ "\n                 </span>"
    			+ "\n             </span><br>"
    			+ "\n             <span style=\"color:#2368ad; font-family:'Dotum';font-size:22px; display:block; font-weight:bold;\">회원정보"
    			+ "\n             </span>"
    			+ "\n         </td>"
    			+ "\n    </tr>"
    			+ "\n    <tr>"
    			+ "\n         <td style=\" border-top:2px solid #8899bb; vertical-align:top;  color: #fff;   border-bottom:1px solid #cccccc ;padding:15px 0; margin:0; font-family:'Dotum';font-size:12px;  line-height:14px; font-weight:bold; text-align:center;\" align=\"left\" bgcolor=\"#8899bb\">"
    			+ "\n                 ID"
    			+ "\n         </td>"
    			+ "\n         <td style=\"vertical-align:top;  border-top:2px solid #8899bb ; border-bottom:1px solid #cccccc ;color: #525d66; padding:15px 10px; margin:0; font-family:'Dotum';font-size:12px;  line-height:14px; \" align=\"left\">"
    			+ "\n             " + id
    			+ "\n         </td>"
    			+ "\n    </tr>"
    			+ "\n    <tr>"
    			+ "\n         <td style=\" vertical-align:top;  color: #fff;   border-bottom:2px solid #8899bb ;padding:15px 0; margin:0; font-family:'Dotum';font-size:12px;  line-height:14px; font-weight:bold; text-align:center;\" align=\"left\" bgcolor=\"#8899bb\">"
    			+ "\n                 Password"
    			+ "\n         </td>"
    			+ "\n         <td style=\"vertical-align:top;   border-bottom:2px solid #8899bb ;color: #525d66; padding:15px 10px; margin:0; font-family:'Dotum';font-size:12px;  line-height:14px; \" align=\"left\">"
    			+ "\n           " + pwd
    			+ "\n         </td>"
    			+ "\n    </tr>"
    			+ "\n    <tr>"
    			+ "\n        <td colspan=\"2\"  style=\"vertical-align:top;  color: #ff0000; padding:30px 0; margin:0; font-family:'Dotum';\" align=\"center\">"
    			+ "\n             <a href=\"http://www.caincheon.or.kr/\" target=\"_blank\" title=\"새창\"  style=\"border-radius:5px; border:0; background:#0a2966; color:#fff; width:100%; font-size:18px; cursor:pointer; width:100%; font-style:normal; padding:15px 0; display:block; font-weight:bold; font-style:normal; text-decoration:inherit; \">홈페이지로 이동</a>"
    			+ "\n        </td>"
    			+ "\n    </tr>"
    			+ "\n    <tr>"
    			+ "\n        <td colspan=\"2\" style=\"vertical-align:top; padding:0; margin:0; padding-top:40px;\" align=\"center\">"
    			+ "\n            <img src=\"http://www.caincheon.or.kr/img/foot_logo.png\" >"
    			+ "\n        </td>"
    			+ "\n    </tr>"
    			+ "\n </table>";
    }

    /*
     * 통신교리 신청 메일
     */
    public static String getEMAIL_CONTENTS_DOCTRINE_REGISTER(String id, String token)
    {
        return "<table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"margin-top:20px; padding:30px; border-top:10px solid #2368ad\" width=\"600px\" bgcolor=\"#f5f5f5\">"
        		+ "<tr><td  style=\"vertical-align:top; padding:0; margin:0;  padding-bottom:20px; border-bottom:1px dashed #acacac; font-size:22px; font-family:'Dotum'; "
        		+ "letter-spacing:-0.75px; color:#2368ad; font-weight:bold;\" align=\"center\" >통신교리 신청</td></tr>"
        		+ "<tr><td  "
        		+ "style=\"vertical-align:top; color: #333333; padding:0; margin:0; font-family:'Dotum';font-size:14px;  line-height:2;  padding-bottom:10px;padding-top:25px; \" align=\"center\" >"
        		+ "<span style=\"color:#0095ff; font-family:'Dotum'; font-weight:bold; font-size:14px; display:block; text-align:center; \">안녕하세요. "+token+" 님. 인천교구 통신교리 담당자입니다.<BR/></span>"
        		+ "<span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block; text-align:center; \">"
        		+ "통신교리 신청을 진심으로 환영합니다!  <br>입금 확인 후 교리가 진행되오니, 수강료 입금 후 반드시 전화연락을 주시기 바랍니다.<br> 감사합니다. </span></td></tr>"
        		+ "<tr><td  style=\"vertical-align:top; padding:0; margin:0; padding-top:40px;\" align=\"center\"><img src=\"http://www.caincheon.or.kr/img/foot_logo.png\"></td></tr></table> ";
    }

    /*
     * 카나혼 강좌 신청 접수
     */
//    public static String getEMAIL_CONTENTS_MARRY_REGISTER(String id, String token)
//    {
//        return "<table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"margin-top:20px; padding:30px; border-top:10px solid #2368ad\"  width=\"600px\" bgcolor=\"#f5f5f5\" >"
//        		+ "<tr><td style=\"vertical-align:top; padding:0; margin:0;  padding-bottom:20px; border-bottom:1px dashed #acacac; font-size:22px; font-family:'Dotum'; letter-spacing:-0.75px; color:#2368ad; font-weight:bold;\" align=\"center\" >"
//        		+ "카나혼인강좌/약혼자주말 신청</td>"
//        		+ "</tr>"
//        		+ "<tr><td style=\"vertical-align:top;  color: #333333; padding:0; margin:0; font-family:'Dotum';font-size:14px;  line-height:2;  padding-bottom:10px;padding-top:25px; \" align=\"center\" >"
//        		+ "<span style=\"color:#0095ff; font-family:'Dotum'; font-weight:bold; font-size:14px; display:block; text-align:center; \">  "
//        		+ "[ 카나강좌 or 약혼자주말]를(을) 신청해 주셔서  감사드립니다.</span>"
//        		+ "<span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block; text-align:center; \">"
//        		+ "<span style=\"color:#333333; font-family:'Dotum';font-size:14px; display:block; font-weight:bold;\">입금확인이 되어야 신청접수가 완료됩니다.</span><br>"
//        		+ "<span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block;\">"
//        		+ "<span style=\"width:3px; height:3px;  display:inline-block; vertical-align:middle; background:#0095ff\" ></span>"
//        		+ "<span style=\"display:inline-block; vertical-align:middle;\">강의시간: 13:30~18:30 (10-20분 여유를 두고 오세요)</span></span>"
//        		+ "<span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block;\">"
//        		+ "<span style=\"width:3px; height:3px;  display:inline-block; vertical-align:middle; background:#0095ff\" ></span>"
//        		+ "<span style=\"display:inline-block; vertical-align:middle;\">차량을 가져오시는 분들은 센터 내 주차장에 주차 하시면 됩니다.</span></span>"
//        		+ "<span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block;\">"
//        		+ "<span style=\"width:3px; height:3px;  display:inline-block; vertical-align:middle; background:#0095ff\" ></span>"
//        		+ "<span style=\"display:inline-block; vertical-align:middle;\">준비물: 뚜겅 없는 개인용컵(종이컵 없음), 미사준비(미사보, 봉헌금), 단정한 복장 </span></span>"
//        		+ "<span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block;\">"
//        		+ "<span style=\"width:3px; height:3px;  display:inline-block; vertical-align:middle; background:#0095ff\" ></span>"
//        		+ "<span style=\"display:inline-block; vertical-align:middle;\">장소사용 안내: 강당내에는 음식물 반입이 되지 않습니다.  </span></span>"
//        		+ "<span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block;\">"
//        		+ "<span style=\"width:3px; height:3px;  display:inline-block; vertical-align:middle; background:#0095ff\" ></span>"
//        		+ "<span style=\"display:inline-block; vertical-align:middle;\">(물포함), 개인적인 간식을 준비해 오셔서 드셔도 됩니다. </span></span>"
//        		+ "<span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block;\">"
//        		+ "<span style=\"width:3px; height:3px;  display:inline-block; vertical-align:middle; background:#0095ff\" ></span>"
//        		+ "<span style=\"display:inline-block; vertical-align:middle;\">강당 입구에 비치된 테이블에 컵과 간식을 비치해 두시고 쉬는 시간에 이용 하시면 됩니다. </span></span>"
//        		+ "<span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block;\">"
//        		+ "<span style=\"width:3px; height:3px;  display:inline-block; vertical-align:middle; background:#0095ff\" ></span>"
//        		+ "<span style=\"display:inline-block; vertical-align:middle;\">꼭 두 분이 함께 강의에 참석 하셔야 수강이 가능 합니다.</span></span>"
//        		+ "<span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block;\">"
//        		+ "<span style=\"width:3px; height:3px;  display:inline-block; vertical-align:middle; background:#0095ff\" ></span>"
//        		+ "<span style=\"display:inline-block; vertical-align:middle;\">중간에 가시면 수료증 발급이 안되오니 미리 일정 조정 부탁 드립니다.</span></span>"
//        		+ "<span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block;\">"
//        		+ "<span style=\"width:3px; height:3px;  display:inline-block; vertical-align:middle; background:#0095ff\" ></span>"
//        		+ "<span style=\"display:inline-block; vertical-align:middle;\">문의 : 가정복음화부 032-762-8888/ 010-5955-0641/ family@caincheon.or.kr</span></span>"
//        		+ "<span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block;\">"
//        		+ "<span style=\"width:3px; height:3px;  display:inline-block; vertical-align:middle; background:#0095ff\" ></span>"
//        		+ "<span style=\"display:inline-block; vertical-align:middle;\">근무 : 월~ 금/ 09:00~18:00/ 점심시간: 12:00~13:00)</span></span></span>"
//        		+ "</td></tr>"
//        		+ "<tr><td style=\"vertical-align:top;  color: #333333; padding:0; margin:0; font-family:'Dotum';font-size:14px;  line-height:2;  padding-bottom:10px;padding-top:25px; \" align=\"center\" >"
//        		+ "<span style=\"color:#0a2966; font-family:'Dotum'; font-weight:bold; font-size:14px; display:block; text-align:center; \"> 천주교 인천교구 가정복음화부? <br>Tel: 032-762-8888 / fax: 032-762-6888</span>"
//        		+ "</td></tr>"
//        		+ "<tr><td style=\"vertical-align:top; padding:0; margin:0; padding-top:40px;\" align=\"center\"><img src=\"http://www.caincheon.or.kr/img/foot_logo.png\"></td>"
//        		+ "</tr>"
//        		+ "</table>";
//    }

    /*
     * 카나강좌 약혼자주말 신청입 입금 확인 메일
     */
//    public static String getEMAIL_CONTENTS_MARRY_APPLY(String id, String token)
//    {
//        return "<table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"margin-top:20px; padding:30px; border-top:10px solid #2368ad\"  width=\"600px\" bgcolor=\"#f5f5f5\" >"
//        		+ "<tr><td colspan=\"2\" style=\"vertical-align:top; padding:0; margin:0;  padding-bottom:20px; border-bottom:1px dashed #acacac; font-size:22px; font-family:'Dotum'; letter-spacing:-0.75px; color:#2368ad; font-weight:bold;\" align=\"center\" >카나혼인강좌/약혼자주말 신청</td></tr>"
//        		+ "<tr><td colspan=\"2\" style=\"vertical-align:top; color: #333333; padding:0; margin:0; font-family:'Dotum';font-size:14px;  line-height:2;  padding-bottom:10px;padding-top:25px; \" align=\"center\" >"
//        		+ "<span style=\"color:#0095ff; font-family:'Dotum'; font-weight:bold; font-size:14px; display:block; text-align:center; \"> [카나강좌 or 약혼자주말] 신청비 입금 확인이 되었습니다.</span>"
//        		+ "<span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block; text-align:center; \">"
//        		+ "<span style=\"color:#333333; font-family:'Dotum';font-size:14px; display:block; font-weight:bold;\">접수처리가 완료되었습니다.  <br>강좌때 뵙겠습니다. </span><br>"
//        		+ "<span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block;\">"
//        		+ "<span style=\"width:3px; height:3px;  display:inline-block; vertical-align:middle; background:#0095ff\" ></span>"
//        		+ "<span style=\"display:inline-block; vertical-align:middle;\">강좌 신청일 : "+token+"</span></span>"
//        		+ "<span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block;\">"
//        		+ "<span style=\"width:3px; height:3px;  display:inline-block; vertical-align:middle; background:#0095ff\" ></span>"
//        		+ "<span style=\"display:inline-block; vertical-align:middle;\">장소 : 인천가톨릭 청소년 센터 / 구) 박문여자 중학교 ? 인천시 동구 박문로 1(송림로)</span></span></span></td></tr>"
//        		+ "<tr><td colspan=\"2\" style=\"vertical-align:top;  color: #333333; padding:0; margin:0; font-family:'Dotum';font-size:14px;  line-height:2;  padding-bottom:10px;padding-top:25px; \" align=\"center\" >"
//        		+ "<span style=\"color:#0a2966; font-family:'Dotum'; font-weight:bold; font-size:14px; display:block; text-align:center; \">감사합니다. <br><br>천주교 인천교구 가정복음화부? <br>Tel: 032-762-8888 / fax: 032-762-6888</span></td></tr>"
//        		+ "<tr><td colspan=\"2\" style=\"vertical-align:top; padding:0; margin:0; padding-top:40px;\" align=\"center\"><img src=\"http://www.caincheon.or.kr/img/foot_logo.png\"></td></tr></table>";
//    }

    /**
     * 성체권분배 접수 완료 안내 메일
     * @deprecated
     */
    public static String getEMAIL_CONTENTS_EUCHA_REGISTER(String id, String token)
    {
        return "<table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"margin-top:20px; padding:30px; border-top:10px solid #2368ad\"  width=\"600px\" bgcolor=\"#f5f5f5\" >"
        		+ "<tr><td colspan=\"2\" style=\"vertical-align:top; padding:0; margin:0;  padding-bottom:20px; border-bottom:1px dashed #acacac; font-size:22px; font-family:'Dotum'; letter-spacing:-0.75px; color:#2368ad; font-weight:bold;\" align=\"center\" >"
        		+ "성체권분배 수여교육</td></tr><tr><td colspan=\"2\" style=\"vertical-align:top;  color: #333333; padding:0; margin:0; font-family:'Dotum';font-size:14px;  line-height:2;  padding-bottom:10px;padding-top:25px; \" align=\"center\" >"
        		+ "<span style=\"color:#0095ff; font-family:'Dotum'; font-weight:bold; font-size:14px; display:block; text-align:center; \">  "
        		+ "[ 성체권분배 수여교육]를(을) 신청해 주셔서 감사드립니다.</span>"
        		+ "<span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block; text-align:center; \">"
        		+ "<span style=\"color:#333333; font-family:'Dotum';font-size:14px; display:block; font-weight:bold;\">입금확인이 되어야 신청접수가 완료됩니다.</span><br>"
        		+ "<span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block;\">"
        		+ "<span style=\"width:3px; height:3px;  display:inline-block; vertical-align:middle; background:#0095ff\" ></span>"
        		+ "<span style=\"display:inline-block; vertical-align:middle;\"> 일시 : 2017년 **월 **일 (*요일) 10:00-16:00</span></span>"
        		+ "<span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block;\">"
        		+ "<span style=\"width:3px; height:3px;  display:inline-block; vertical-align:middle; background:#0095ff\" ></span>"
        		+ "<span style=\"display:inline-block; vertical-align:middle;\"> 장소 : 교구청 </span></span>"
        		+ "<span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block;\">"
        		+ "<span style=\"width:3px; height:3px;  display:inline-block; vertical-align:middle; background:#0095ff\" ></span>"
        		+ "<span style=\"display:inline-block; vertical-align:middle;\"> 대상 : 인천교구내 수도자, 평신도(신규/갱신) </span></span>"
        		+ "<span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block;\">"
        		+ "<span style=\"width:3px; height:3px;  display:inline-block; vertical-align:middle; background:#0095ff\" ></span>"
        		+ "<span style=\"display:inline-block; vertical-align:middle;\">  준비물 : 개인필기구 </span>\n</span>"
        		+ "<span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block;\">"
        		+ "<span style=\"width:3px; height:3px;  display:inline-block; vertical-align:middle; background:#0095ff\" ></span>"
        		+ "<span style=\"display:inline-block; vertical-align:middle;\">  \uAD50\uC721\uBE44 : \uC628\uB77C\uC778 \uC811\uC218 \uD6C4, \uAD50\uC721\uBE44 \uC785\uAE08\uC774 \uD655\uC778\uB418\uBA74 \uC811\uC218 \uC644\uB8CC\uB429\uB2C8\uB2E4. </span>\n</span>"
        		+ "<span style=\"color:#333333; font-family:'Dotum';font-size:12px; display:block;\">\n<span style=\"width:3px; height:3px;  display:inline-block; vertical-align:middle; background:#0095ff\" ></span>"
        		+ "<span style=\"display:inline-block; vertical-align:middle;\">  \uACC4\uC88C\uC548\uB0B4 : \uC2E0\uD611  131-002-127064 / \uC608\uAE08\uC8FC: \uC0AC\uBAA9\uAD6D </span></span></span></td></tr>"
        		+ "<tr>\n<td colspan=\"2\" style=\"vertical-align:top;  color: #333333; padding:0; margin:0; font-family:'Dotum';font-size:14px;  line-height:2;  padding-bottom:10px;padding-top:25px; \" align=\"center\" >"
        		+ "<span style=\"color:#0a2966; font-family:'Dotum'; font-weight:bold; font-size:14px; display:block; text-align:center; \">\uCC9C\uC8FC\uAD50 \uC778\uCC9C\uAD50\uAD6C \uAC00\uC815\uBCF5\uC74C\uD654\uBD80? "
        		+ "<br>Tel: 032-762-8888 / fax: 032-762-6888</span>\n</td>\n</tr>\n"
        		+ "<tr>\n<td colspan=\"2\" style=\"vertical-align:top; padding:0; margin:0; padding-top:40px;\" align=\"center\"><img src=\"http://www.caincheon.or.kr/img/foot_logo.png\" ></td></tr></table>";
    }

    
}
