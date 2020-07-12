// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FaqController.java

package kr.caincheon.church.intro;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.caincheon.church.common.*;
import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.Paging;
import kr.caincheon.church.intro.service.FaqService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FaqController extends CommonController
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="faqService")
    private FaqService faqService;

	/*
	 * FAQ 목록을 조회하는 내부 메서드
	 */
	private void selectList(Map _params, ModelAndView mv) throws Exception {
		java.util.List faqList = faqService.faqList(_params);
        int totalCount = faqService.faqListCount(_params);
        
        Paging paging = new Paging();
        paging.setPageNo(ipnull(_params, "pageNo", 1));
        paging.setPageSize(ipnull(_params, "pageSize", 20));
        paging.setTotalCount(totalCount);
        
        mv.addObject("faqList", faqList);
        mv.addObject("paging", paging);
	}
	
	/*
	 * 
	 * Front URL Mapped Methods, Just one
	 * 
	 */

	@RequestMapping(value = "/intro/diocesan_12.do")
    public ModelAndView inquireList(HttpServletRequest request) throws Exception
    {
        ModelAndView mv = new ModelAndView("/intro/diocesan_12");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        _params.put("pageSize", "5");
        _params.put("displayYN", "Y");
        
        this.selectList(_params, mv);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }
	
	/*
	 * 
	 * 관리자 URL Mapped Methods
	 * 
	 * 
	 */
    
	@RequestMapping(value = "/admin/board/faq_list.do")
    public ModelAndView faqList(HttpServletRequest request) throws Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/faq_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        this.selectList(_params, mv);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/faq_view.do")
    public ModelAndView faqView(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException , Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/faq_view");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        Map faqContents = faqService.faqContents(_params);
        mv.addObject("_params", _params);
        mv.addObject("faqContents", faqContents);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/faq_insert.do")
    public ModelAndView faqInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException , Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/faq_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        
        boolean success = faqService.faqInsert(_params);
        if(success) {
        	this.selectList(_params, mv);
        }
        return mv;
    }

	@RequestMapping(value = "/admin/board/faq_modify.do")
    public ModelAndView faqModify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/faq_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        

        boolean success = faqService.faqModify(_params);
        if(success) {
        	this.selectList(_params, mv);
        }
        return mv;
    }

	@RequestMapping(value = "/admin/board/faq_delete.do")
    public ModelAndView inqDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/faq_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        
        boolean success = faqService.faqDelete(_params);
        if(success) {
        	this.selectList(_params, mv);
        }
        return mv;
    }

    
}
