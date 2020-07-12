// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmEngageController.java

package kr.caincheon.church.community;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.community.service.CanaService;

@Controller
public class CanaController extends CommonController
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="canaService")
    private CanaService canaService;


	/*
	 * 홈페이지에서 약혼자주말안내의 `일정 조회`
	 */
	@RequestMapping(value = "/community/cana_manual.do")
    public ModelAndView communityCanaManual(HttpServletRequest request) throws Exception
    {
		ModelAndView mv = new ModelAndView("/community/cana_manual");
		build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        Map engage_guide = canaService.engageGuide(_params);
		mv.addObject("engage_guide", engage_guide);
        
		D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
		return mv;
    }
	
	
	
    
}
