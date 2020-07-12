// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HolidayController.java

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
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.utils.UtilDate;
import kr.caincheon.church.service.HolidayFileService;

@Controller
public class HolidayController extends CommonController
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="holidayFileService")
    private HolidayFileService holidayFileService;

    /* bjm found */
	@RequestMapping(value = "/admin/board/holiday_list.do")
    public ModelAndView holydayList(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, Exception
    {
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/admin/board/holiday_list");
        
        // service call
        _params.put("searchYear", UtilDate.getDateFormat("yyyy"));
        
        CommonDaoDTO dto = holidayFileService.holidayList(_params);
        
        // response
        mv.addObject("holidayList", dto.daoList);
        mv.addObject("paging", dto.paging);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/holiday_view.do")
    public ModelAndView admNewsView(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
		// request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/admin/board/holiday_view");
        
        // service call
        Map holidayContents = null;
        try {
            holidayContents = holidayFileService.holidayContents(_params);
        } catch(CommonException e) {
            e.printStackTrace();
        }
        
        // response
        mv.addObject("_params", _params);
        mv.addObject("holidayContents", holidayContents);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/holiday_modify.do")
    public void admNewsModify(Map map, HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
		// request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/admin/board/holiday_list");
        
        // service call
        
        boolean success = false;
        try {
            success = holidayFileService.holidayModify(_params);
        } catch(CommonException e) {
            e.printStackTrace();
        }
        
        // response
        if(success)
            response.sendRedirect("/admin/board/holiday_list.do");
        else
            response.sendRedirect("/admin/board/holiday_view.do");
    }

	@RequestMapping(value = "/admin/board/holiday_delete.do")
    public void admNewsDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
		// request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/admin/board/holiday_list");
        
        // service call
        
        boolean success = false;
        try {
            success = holidayFileService.holidayDelete(_params);
        } catch(CommonException e) {
            e.printStackTrace();
        }
        
        // response
        if(success)
            response.sendRedirect("/admin/board/holiday_list.do");
    }

    
}
