// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MemberController.java

package kr.caincheon.church.member;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.Const;
import kr.caincheon.church.common.utils.UtilCharSet;
import kr.caincheon.church.common.utils.UtilString;
import kr.caincheon.church.member.service.MemberService;
import kr.caincheon.church.service.AdmBonNoticeService;

@Controller
public class MemberController extends CommonController
{
	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="memberService")
	private MemberService memberService;
	
	@Resource(name="admBonNoticeService")
    private AdmBonNoticeService admBonNoticeService;
    
	/*
	 * front::사용자 로그인
	 */
	@RequestMapping(value = "/member/login.do")
    public ModelAndView memberLogin(HttpServletRequest request) throws Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        String gotoURL = pnull(_params.get("gotoURL"), "/home.do");
        String gotoBACK = pnull(_params.get("gotoBACK"), "/member/login");
        
        ModelAndView mv = null;
        Map memberInfo = null;
        Map exceptionData = null;
        String errMsg = "아이디와 패스워드를 확인해 주세요.";
        String errCode = "ERR-L009";
        
        try {
            memberInfo = memberService.login(_params);
        } catch(CommonException e) {
        	
            memberInfo = new HashMap();
            errMsg = e.getErrMessage();
            errCode = e.getErrCode();
            if(e.getData() != null) exceptionData = (Map)e.getData();
        } catch(Exception e) {
//        	e.printStackTrace();
        	memberInfo = new HashMap();
            errMsg = e.getMessage();
            errCode = "ERR-LOGIN";
        }
        
        if(memberInfo != null && memberInfo.size() > 0)
        {
        	if(pnull(_params,"id").length() == 0) {
        		gotoURL = gotoBACK;
        	} else if(pnull(_params,"id").length() > 0 || "".equalsIgnoreCase(gotoURL) || "/member/login.do".equalsIgnoreCase(gotoURL)) {
        		gotoURL = "/home.do";
        	}
        	// 세션처리
			mv = new ModelAndView("/member/login_proc");
            mv.addObject(Const.SESSION_KEY_MEM_NM, memberInfo.get("NAME"));
            mv.addObject(Const.SESSION_KEY_MEM_BAPNM, memberInfo.get("BAPTISMALNAME"));
            mv.addObject(Const.SESSION_KEY_MEM_ID, memberInfo.get("ID"));
            mv.addObject("GOTO_URL", gotoURL);
            mv.addObject("MEMINFO_MAP", memberInfo);
            _logger.info((new StringBuilder(" ==> 정상 로그인 : ")).append(gotoURL).toString());
            _logger.info((new StringBuilder(" ==> 정상 로그인 : ")).append(memberInfo).toString());
        } else {
            if("ERR-D005".equals(errCode)) {
                mv = new ModelAndView("/member/email_form_1");
                if(exceptionData != null) {
                    mv.addObject("name", pnull(exceptionData.get("NAME")));
                    mv.addObject("baptismalName", pnull(exceptionData.get("BAPTISMALNAME")));
                }
            } else {
                mv = new ModelAndView(gotoBACK);
            }
            mv.addObject(Const.SESSION_KEY_MEM_ID, pnull(_params.get("id")));
            mv.addObject(Const.ERR_MSG, errMsg);
            mv.addObject(Const.ERR_CODE, errCode);
        }
        mv.addObject("id", pnull(_params.get("id")));
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  ); 
        return mv;
    }

	/*
	 * 회원가입 화면 
	 */
	@RequestMapping(value = "/member/joinform.do")
    public ModelAndView memberJoinForm(HttpServletRequest request) throws Exception
    {
        build(request);
        ModelAndView mv = new ModelAndView("/member/step_01");
        mv.addObject("login_type", _params.get("login_type"));
        mv.addObject("id", _params.get("email"));
        _params.put("LEVEL", "2");
        java.util.List _1x00xList = admBonNoticeService._1x00xList(_params);
        mv.addObject("CHURCH_LIST", _1x00xList);

        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  ); 
        return mv;
    }

	/*
	 * 회원가입 process :: ajax 처리
	 */
	@RequestMapping(value = "/member/join.do")
    public @ResponseBody Map<String, Object> memberJoin(HttpServletRequest request) throws Exception
    {
		// parameters
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        // response
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("GOTO_URL", "/member/step_01_03.jsp");
        try {
        	// service call
            boolean tf = memberService.join(_params);
            resMap.put("RESULT_YN", tf ? "Y" : "N");
            resMap.put("MESSAGE"  , tf ? "Y" : "N");
        } catch(CommonException e) {
        	e.printStackTrace();
        	resMap.put("GOTO_URL", "/member/error_page.do");
        	resMap.put("RESULT_YN","N");
        	resMap.put("MESSAGE",  e.getErrMessage());
        	resMap.put("ERR_MSG",  e.getErrMessage());
        	resMap.put("ERR_CODE", e.getErrCode());
        }

        D(_logger, Thread.currentThread().getStackTrace(), "Response Map >> \n\t\t"+resMap  ); 
        
        return resMap;
    }

	@RequestMapping(value = "/member/joinByNaver.do")
    public ModelAndView memberJoinWithSNSByNaver(HttpServletRequest request) throws Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/member/step_01_03");
        String clientid = "VOl2BAxX5HeaViUpSmuw";
        String token = "";
        try
        {
            String apiURL = "https://openapi.naver.com/v1/nid/me";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", (new StringBuilder("Bearer ")).append(token).toString());
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode == 200)
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            else
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            StringBuffer response = new StringBuffer();
            String inputLine;
            while((inputLine = br.readLine()) != null) 
                response.append(inputLine);
            br.close();
            System.out.println(response.toString());
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }

        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  ); 
        return mv;
    }

	@RequestMapping(value = "/member/join_confirm.do")
    public ModelAndView memberJoinEmailConfirm(HttpServletRequest request) throws Exception
    {
        build(request);
        ModelAndView mv = new ModelAndView("/member/step_end");
        boolean isUpdated = false;
        try
        {
            isUpdated = memberService.joinFinish(_params);
        }
        catch(CommonException e)
        {
            e.printStackTrace();
            mv = new ModelAndView("/member/error_page");
            mv.addObject("ERR_MSG", e.getErrMessage());
            mv.addObject("ERR_CODE", e.getErrCode());
        }

        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  ); 
        return mv;
    }

	/**
	 * 휴면해제 요청 ( 휴면 알림 메일에서 처리 )
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/member/dormantClearRequest.do")
    public ModelAndView memberDormantClearRequest(HttpServletRequest request) throws Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/member/login");
        mv.addObject("id", _params.get("id"));
        Map memberInfo = null;
        try
        {
            memberInfo = memberService.memberDormantClearRequest(_params);
            String email = UtilString.pnull(_params.get("id"));
            int idx = email.indexOf("@");
            email = (new StringBuilder(String.valueOf(email.substring(0, 2)))).append(UtilCharSet.RPadding("", "*", idx - 2)).append(email.substring(idx + 1)).toString();
            mv.addObject("DORMANT_CLEAR_MAIL", (new StringBuilder("\uD734\uBA74 \uD574\uC81C \uC694\uCCAD \uBA54\uC77C\uC774 \uBC1C\uC1A1\uB418\uC5C8\uC2B5\uB2C8\uB2E4.\\n\\n '")).append(email).append("' \uBA54\uC77C\uC744 \uD655\uC778 \uD574 \uC8FC\uC138\uC694.").toString());
        }
        catch(CommonException e)
        {
        	e.printStackTrace();
            memberInfo = new HashMap();
            mv.addObject("DORMANT_CLEAR_MAIL", e.getErrMessage() != null ? ((Object) (e.getErrMessage())) : ((Object) (e.getMessage())));
            mv.addObject("ERR_MSG", e.getErrMessage() != null ? ((Object) (e.getErrMessage())) : ((Object) (e.getMessage())));
            mv.addObject("ERR_CODE", e.getErrCode());

        }

        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  ); 
        return mv;
    }

	/**
	 * 휴면 해제 처리
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/member/dormantClearConfirm.do")
    public ModelAndView memberDormantClearConfirm(HttpServletRequest request) throws Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/member/login");
        try
        {
            boolean tf = memberService.memberDormantClearConfirm(_params);
            if(tf) {
                mv.addObject("DORMANT_CLEAR_MAIL", "휴면 상태가 해제되었습니다. \n\n7일 이내 로그인을 하셔야 합니다.");
                mv.addObject("id", _params.get("id"));
                mv.addObject("gotoURL", "/member/findWithSessionId.do?id="+_params.get("id"));
                
            } else {
                mv.addObject("ERR_MSG", "휴면 상태가 해제되지 않았습니다. 관리자에게 문의 해 주세요.");
            }
        }
        catch(CommonException e)
        {
        	e.printStackTrace();
            mv.addObject("ERR_MSG", e.getErrMessage());
            mv.addObject("ERR_CODE", e.getErrCode());
        }

        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  ); 
        return mv;
    }

	/**
	 * id중복 코딩, 처리하는 AJAX 응답
	 */
	@RequestMapping(value = "/member/idDupCheck.do")
    public @ResponseBody Map<String , Object> memberIdDupChecker(HttpServletRequest request) throws Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        Map<String , Object> resData = new HashMap<String , Object>();
        Map memberInfo = null;
        try {
            memberInfo = memberService.memberFindId(_params);
            if(memberInfo != null && memberInfo.containsKey("ID")) {
            	resData.put("RESULT_YN", "N"); // ID 중복됨
            } else 
            	resData.put("RESULT_YN", "Y"); // 중복 안됨, ID 등록 가능
        } catch(CommonException e) {
        	resData.put("RESULT_YN", "Y"); // 중복 안됨, ID 등록 가능
        	resData.put("MESSAGE", e.getData());
        }

        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+memberInfo  ); 
        D(_logger, Thread.currentThread().getStackTrace(), "Response resData >> \n\t\t"+resData  ); 
        return resData;
    }
	
	/**
	 * ID를 잃어버렸을때 ID를 찾는 OP
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/member/findId.do")
    public ModelAndView memberFindId(HttpServletRequest request) throws Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/member/lost/find_id");
        Map memberInfo = null;
        try
        {
            memberInfo = memberService.memberFindId(_params);
        }
        catch(CommonException e)
        {
        	e.printStackTrace();
            memberInfo = new HashMap();
            mv.addObject("ERR_MSG", e.getErrMessage());
            mv.addObject("ERR_CODE", e.getErrCode());
        }

        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  ); 
        return mv;
    }

	@RequestMapping(value = "/member/findPwd.do")
    public ModelAndView memberFindPwd(HttpServletRequest request) throws Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/member/login");
        Map memberInfo = null;
        try
        {
            memberInfo = memberService.memberFindPwd(_params);
        }
        catch(CommonException e)
        {
        	e.printStackTrace();
            memberInfo = new HashMap();
            mv.addObject("ERR_MSG", e.getErrMessage());
            mv.addObject("ERR_CODE", e.getErrCode());
        }

        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  ); 
        return mv;
    }

	/**
	 * 상단 > 나의정보 :: 보기 메뉴
	 */
	@RequestMapping(value = "/member/findWithSessionId.do")
    public ModelAndView memberFindWithSessionId(HttpServletRequest request) throws Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/member/myinfo_edit");
        Map memberInfo = null;
        String errMsg = "";
        String errCode = "";
        try
        {
            memberInfo = memberService.memberFindId(_params);
            mv.addObject("MEMINFO_MAP", memberInfo);
            _params.put("LEVEL", "2");
            java.util.List _1x00xList = admBonNoticeService._1x00xList(_params);
            mv.addObject("CHURCH_LIST", _1x00xList);
        }
        catch(CommonException e)
        {
        	e.printStackTrace();
            mv = new ModelAndView("/member/myinfo");
            memberInfo = new HashMap();
            errMsg = e.getErrMessage();
            errCode = e.getErrCode();
        }

        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  ); 
        return mv;
    }

    @RequestMapping(value = "/member/myinfo_edit.do")
    public ModelAndView memberFindMyinfoEdit(HttpServletRequest request) throws Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/member/login_proc");
        Map memberInfo = null;
        String errMsg = "";
        String errCode = "";
        try
        {
            _params.put("LEVEL", "2");
            java.util.List _1x00xList = admBonNoticeService._1x00xList(_params);
            mv.addObject("CHURCH_LIST", _1x00xList);
            memberInfo = memberService.login(_params);
            mv.addObject("MEMINFO_MAP", memberInfo);
            mv.addObject(Const.SESSION_KEY_MEM_NM, memberInfo.get("NAME"));
            mv.addObject(Const.SESSION_KEY_MEM_BAPNM, memberInfo.get("BAPTISMALNAME"));
            mv.addObject(Const.SESSION_KEY_MEM_ID, memberInfo.get("ID"));
            mv.addObject("GOTO_URL", "/member/myinfo_edit");
        }
        catch(CommonException e)
        {
        	e.printStackTrace();
            mv = new ModelAndView("/member/myinfo");
            memberInfo = new HashMap();
            errMsg = e.getErrMessage();
            errCode = e.getErrCode();
        }

        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  ); 
        return mv;
    }

    @RequestMapping(value = "/member/update.do")
    public ModelAndView memberModifyInformation(HttpServletRequest request) throws Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/member/myinfo_edit");
        try
        {
            _params.put("LEVEL", "2");
            java.util.List _1x00xList = admBonNoticeService._1x00xList(_params);
            mv.addObject("CHURCH_LIST", _1x00xList);
            Map meminfo = memberService.memberModifyInformation(_params);
            mv.addObject("MEMINFO_MAP", meminfo);
        }
        catch(CommonException e)
        {
        	e.printStackTrace();
            mv.addObject("ERR_MSG", e.getErrMessage());
            mv.addObject("ERR_CODE", e.getErrCode());
            mv.addObject("MEMINFO_MAP", e.getData());
        }

        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  ); 
        return mv;
    }

    /*
     * 회원이 나의정보에서 탈퇴버튼을 누를때 처리
     */
    @RequestMapping(value = "/member/leave.do")
    public void memberLeave(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/home");
        try {
            boolean tf = memberService.memberLeave(_params);
            if(tf) {
                mv.addObject("RESULT_YN", "Y");
            } else
                mv.addObject("RESULT_YN", "N");
        } catch(CommonException e) {
        	e.printStackTrace();
            mv.addObject("ERR_MSG", e.getErrMessage());
            mv.addObject("ERR_CODE", e.getErrCode());
        }

        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );

        // goto jsp page 
        request.getSession(true).invalidate();
        response.sendRedirect("/home.do");//request.getRequestDispatcher("/member/logout.jsp").forward(request, response);//return mv;
    }

    
}
