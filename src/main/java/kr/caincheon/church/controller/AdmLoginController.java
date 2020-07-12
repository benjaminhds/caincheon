// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmMemberController.java

package kr.caincheon.church.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.Const;
import kr.caincheon.church.service.AdmMemberService;

@Controller
public class AdmLoginController extends CommonController
{
	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="admMemberService")
    private AdmMemberService admMemberService;
    
    /*
     * 이 컨트롤러에서는 로그인외에는 사용하지 않는 기능들이다.
     * 모두 잘못 구현된 메서드들로써, deprecated 시켰음. 
     */
    
    
    /* bjm found */
    @RequestMapping(value = "/admin/login.do")
    public ModelAndView memberLogin(HttpServletRequest request)
    		throws ServletException, Exception
    {
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "memberLogin() [params:"+_params+"]" );
        
        String gotoURL  = pnull(_params.get("gotoURL"),  "/admin/main");
        String gotoBACK = pnull(_params.get("gotoBACK"), "/admin/login");
        ModelAndView mv = null;
        Map memberInfo = null;
        Map exceptionData = null;
        String errMsg = "아이디와 패스워드를 확인해 주세요.";
        String errCode = "ERR-L009";
        try
        {
            memberInfo = admMemberService.login(_params);
        }
        catch(CommonException e)
        {
            memberInfo = new HashMap();
            errMsg = e.getErrMessage();
            errCode = e.getErrCode();
            if(e.getData() != null) exceptionData = (Map)e.getData();
        }
        
        if(memberInfo != null && memberInfo.size() > 0)
        {
            mv = new ModelAndView("/admin/login_proc");
            mv.addObject(Const.SESSION_KEY_ADM_MEM_NM, memberInfo.get("ADM_NAME"));
            mv.addObject(Const.SESSION_KEY_ADM_MEM_ID, memberInfo.get("ADM_ID"));
            mv.addObject("GOTO_URL", gotoURL);
            mv.addObject("ADMMEMINFO_MAP", memberInfo);
            _logger.info((new StringBuilder(" ==> \uC815\uC0C1 \uB85C\uADF8\uC778 : ")).append(gotoURL).toString());
            _logger.info((new StringBuilder(" ==> \uC815\uC0C1 \uB85C\uADF8\uC778 : ")).append(memberInfo).toString());
        } else {
            if(!"ERR-D005".equals(errCode))
                mv = new ModelAndView(gotoBACK);
            mv.addObject(Const.SESSION_KEY_ADM_MEM_ID, pnull(_params.get("pid")));
            mv.addObject(Const.ERR_MSG, errMsg);
            mv.addObject(Const.ERR_CODE, errCode);
        }
        mv.addObject("pid", pnull(_params.get("pid")));
        
        D(_logger, Thread.currentThread().getStackTrace(), "memberLogin() Response Finished." );
        
        return mv;
    }
    
}