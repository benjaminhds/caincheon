// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmailController.java

package kr.caincheon.church.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.Paging;
import kr.caincheon.church.common.utils.UtilString;
import kr.caincheon.church.service.EmailService;

@Controller
public class EmailController extends CommonController
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="emailService")
    private EmailService emailService;

    /* bjm found */
	@RequestMapping(value = "/admin/board/email_list.do")
    public ModelAndView emailList(HttpServletRequest request)
    		throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/email_list");
        build(request);
        _logger.info((new StringBuilder(" ===> [method=")).append(request.getMethod()).append("] _params ? ").append(_params.toString()).toString());
        if(UtilString.pnull(_params.get("pageNo")).equals(""))
            _params.put("pageNo", "1");
        java.util.List emailList = emailService.emailList(_params);
        int totalCount = emailService.emailListCount(_params);
        Paging paging = new Paging();
        paging.setPageNo(Integer.parseInt(UtilString.pnull(_params.get("pageNo"), "1")));
        paging.setPageSize(20);
        paging.setTotalCount(totalCount);
        mv.addObject("emailList", emailList);
        mv.addObject("paging", paging);
        return mv;
    }

	@RequestMapping(value = "/admin/board/email_view.do")
    public ModelAndView emailView(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/email_view");
        build(request);
        _logger.info((new StringBuilder(" ===> [inquire_view.do=")).append(request.getMethod()).append("] _params ? ").append(_params.toString()).toString());
        Map email = emailService.emailContents(_params);
        mv.addObject("_params", _params);
        mv.addObject("email", email);
        return mv;
    }

	@RequestMapping(value = "/admin/board/email_insert.do")
    public void emailInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/email_list");
        build(request);
        _logger.info((new StringBuilder(" ===> [method=")).append(request.getMethod()).append("] _params ? ").append(_params.toString()).toString());
        if(UtilString.pnull(_params.get("pageNo")).equals(""))
            _params.put("pageNo", "1");
        boolean success = emailService.emailInsert(_params);
        if(success)
            response.sendRedirect("/admin/board/email_list.do");
    }

	@RequestMapping(value = "/admin/board/email_modify.do")
    public void emailModify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/email_list");
        build(request);
        _logger.info((new StringBuilder(" ===> [method=")).append(request.getMethod()).append("] _params ? ").append(_params.toString()).toString());
        if(UtilString.pnull(_params.get("pageNo")).equals(""))
            _params.put("pageNo", "1");
        boolean success = emailService.emailModify(_params);
        if(success)
            response.sendRedirect("/admin/board/email_list.do");
    }

	@RequestMapping(value = "/admin/board/email_delete.do")
    public void inqDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/email_list");
        build(request);
        _logger.info((new StringBuilder(" ===> [method=")).append(request.getMethod()).append("] _params ? ").append(_params.toString()).toString());
        if(UtilString.pnull(_params.get("pageNo")).equals(""))
            _params.put("pageNo", "1");
        boolean success = emailService.emailDelete(_params);
        if(success)
            response.sendRedirect("/admin/board/email_list.do");
    }

    
}
