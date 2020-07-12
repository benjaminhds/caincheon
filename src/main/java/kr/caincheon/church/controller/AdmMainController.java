// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmMainController.java

package kr.caincheon.church.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.Const;

@Controller
public class AdmMainController extends CommonController
{

	private final Logger _logger = Logger.getLogger(getClass());

	/*
	 * super 관리자,  
	 */
	@RequestMapping(value = "/admin/main.do")	
    public ModelAndView main(HttpServletRequest request)throws ServletException, Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );
        
        String admSessionId = pnull(request.getAttribute(Const.SESSION_KEY_ADM_MEM_ID));
        
        D(_logger, Thread.currentThread().getStackTrace(), "[request.getAttribute():Const.SESSION_KEY_ADM_MEM_ID "+admSessionId+"]" );
        
        ModelAndView mv = null;
        if(admSessionId == null || admSessionId == "")
            mv = new ModelAndView("redirect:/admin/login.jsp");
        else
            mv = new ModelAndView("/admin/main");
        return mv;
    }

	@RequestMapping(value = "/admin/common/SessionCheck.do")
    public ModelAndView sessionCheck(HttpServletRequest request)throws ServletException, Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );
        
        String admSessionId = pnull(request.getAttribute(Const.SESSION_KEY_ADM_MEM_ID));
        
        D(_logger, Thread.currentThread().getStackTrace(), "[request.getAttribute():Const.SESSION_KEY_ADM_MEM_ID "+admSessionId+"]" );
        
        ModelAndView mv = new ModelAndView();
        
        if(admSessionId != null)
            mv.addObject("check", "ok");
        
        return mv;
    }

    
}
