// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmEngageController.java

package kr.caincheon.church.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.community.service.CanaService;

@Controller
public class AdmEngageController extends CommonController
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="canaService")
    private CanaService canaService;

    /* bjm found */
	@RequestMapping(value = "/admin/board/engage_guide.do")
    public ModelAndView engageGuide(HttpServletRequest request)
    		throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/engage_guide");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        
        Map engage_guide = canaService.engageGuide(_params);
        
        mv.addObject("_params", _params);
        mv.addObject("engage_guide", engage_guide);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        return mv;
    }

	
    
}
