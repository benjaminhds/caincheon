// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SchController.java

package kr.caincheon.church.controller;

import java.text.ParseException;
import java.util.HashMap;
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
import kr.caincheon.church.common.utils.UtilDate;
import kr.caincheon.church.common.utils.UtilString;
import kr.caincheon.church.service.SchService;

@Controller
public class SchController extends CommonController
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="schService")
    private SchService schService;
	
    

    /* bjm found */
	@RequestMapping(value = "/admin/board/sch_list.do")
    public ModelAndView schList(HttpServletRequest request)
    		throws ServletException, Exception
    {
		build(request);
		D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
		
        ModelAndView mv = new ModelAndView("/admin/board/sch_list");
        
        
        CommonDaoDTO dto = schService.schList(_params);
        //int totalCount = schService.schListCount(_params);
        
//        Paging paging = new Paging();
//        paging.setPageNo(ipnull(_params, "pageNo"));
//        paging.setPageSize(ipnull(_params, "pageSize"));
//        paging.setTotalCount(totalCount);
        
        mv.addObject("paging", dto.paging);
        mv.addObject("schList", dto.daoList);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/sch_cal.do")
    public ModelAndView schCal(HttpServletRequest request)
    	throws ServletException, ParseException, Exception
    {
		build(request);
		D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
		
        ModelAndView mv = new ModelAndView("/admin/board/sch_cal");

        String srch_ym = UtilString.pnull(_params.get("srch_ym"));
        if(srch_ym.length() == 0)
            srch_ym = UtilDate.getDateFormat("yyyy-MM");
        else
        if(srch_ym.length() != 7)
            throw new ParseException((new StringBuilder("\uC870\uD68C\uD558\uB294 \uB144\uC6D4\uC758 \uB370\uC774\uD130 \uD615\uC2DD\uC774 \uB9DE\uC9C0 \uC54A\uC74C [TYPE:YYYY-MM, ")).append(srch_ym).append("]").toString(), -1);
        srch_ym = srch_ym.replace(".", "-");
        _params.put("srch_ym", srch_ym);
        
        Map map = null;
        try {
            map = schService.scheduleByDiary(_params);
        } catch(Exception e) {
            map = new HashMap();
            _logger.error(e);
        }
        mv.addObject("L_DIARY", map.get("DIARY"));
        mv.addObject("M_SCHEDULE", map.get("SCHEDULE"));
        mv.addObject("srch_ym", srch_ym.replace("-", "."));
        mv.addObject("PREV_MONTH", UtilDate.moveMonthOfAddition("yyyy-MM", srch_ym, -1).replace("-", "."));
        mv.addObject("NEXT_MONTH", UtilDate.moveMonthOfAddition("yyyy-MM", srch_ym, 1).replace("-", "."));
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/sch_cview.do")
    public ModelAndView schCView(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
		build(request);
		D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
		
        ModelAndView mv = new ModelAndView("/admin/board/sch_cview");

        String query_type = UtilString.pnull(_params.get("query_type"));
        Map schContents = schService.schCView(_params);
        java.util.List _1x00xList = schService._1x00xList(_params);
        mv.addObject("_params", _params);
        mv.addObject("schContents", schContents);
        mv.addObject("_1x00xList", _1x00xList);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/sch_view.do")
    public ModelAndView schView(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
		build(request);
		D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
		
        ModelAndView mv = new ModelAndView("/admin/board/sch_view");
        String query_type = UtilString.pnull(_params.get("query_type"));
        CommonDaoDTO dto=null;
		try {
			dto = schService.schView(_params);
			
			mv.addObject("schContents", dto.daoDetailContent);
	        mv.addObject("_1x00xList",  dto.orgList);
		} catch (Exception e) {
			
			
			e.printStackTrace();
			
		}
        
        mv.addObject("_params", _params);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/sch_insert.do")
    public void schInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
		build(request);
		D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
		
        ModelAndView mv = new ModelAndView("/admin/board/sch_list");
        
        
        String list_type = UtilString.pnull(_params.get("list_type"));
        boolean success = schService.schInsert(_params);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response result >> \n\t\t"+success  );
        
        if(success)
            if(list_type.equals("calendar"))
                response.sendRedirect("/admin/board/sch_cal.do");
            else
                response.sendRedirect("/admin/board/sch_list.do");
    }

	@RequestMapping(value = "/admin/board/sch_modify.do")
    public void schModify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
		build(request);
		D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
		
        ModelAndView mv = new ModelAndView("/admin/board/sch_list");
        
        
        String list_type = UtilString.pnull(_params.get("list_type"));
        boolean success = schService.schModify(_params);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response result >> \n\t\t"+success  );
        
        if(success)
            if(list_type.equals("calendar"))
                response.sendRedirect("/admin/board/sch_cal.do");
            else
                response.sendRedirect("/admin/board/sch_list.do");
    }

	@RequestMapping(value = "/admin/board/sch_delete.do")
    public void schDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
		build(request);
		D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
		
        ModelAndView mv = new ModelAndView("/admin/board/sch_list");
        
        
        
        String list_type = UtilString.pnull(_params.get("list_type"));
        boolean success = schService.schDelete(_params);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response result >> \n\t\t"+success  );
        
        if(success)
            if(list_type.equals("calendar"))
                response.sendRedirect("/admin/board/sch_cal.do");
            else
                response.sendRedirect("/admin/board/sch_list.do");
    }

    
}
