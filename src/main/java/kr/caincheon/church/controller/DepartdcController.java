// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DepartdcController.java

package kr.caincheon.church.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
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
import kr.caincheon.church.common.base.Paging;
import kr.caincheon.church.service.DepartdcService;

@Controller
public class DepartdcController extends CommonController
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="departdcService")
    private DepartdcService departdcService;

	@RequestMapping(value = "/admin/board/departdc_list.do")
    public ModelAndView departdcList(HttpServletRequest request)
    		throws ServletException, Exception
    {
        
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/admin/board/departdc_list");
        
        // if not exists, put
        
        
        /* bjm modify */
        int cPageNo = ipnull(_params, "pageNo");
        List rtList = new ArrayList();
        int totalCount = 0;
		try {
			//list
			List departdcList = departdcService.departdcList(_params);
			
			//전체갯수
			//daoTotalCount = departdcService.departdcListCount(_params);//old code
			totalCount = departdcList.size();
			
			//paging
			int remainStartNo = (cPageNo - 1) * 20, remainEndNo = cPageNo * 20 ;
			if(totalCount > 0) {
				int i=0;
				while(i < totalCount) {
					if (i >= remainStartNo && i < remainEndNo  ) {
						rtList.add(departdcList.get(i));
					}
					i++;
				}
				departdcList.clear();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("ERR_MSG", e.getMessage());
		}
        
        //
        Paging paging = new Paging();
        paging.setPageNo(cPageNo);
        paging.setPageSize(20);
        paging.setTotalCount(totalCount);
        mv.addObject("_params", _params);
        mv.addObject("paging", paging);
        mv.addObject("departdcList", rtList);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/departdc_view.do")
    public ModelAndView departdcView(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/admin/board/departdc_view");
        
        List priestList = null;
        
        // if insert-mode, 
        if("insert".equals(pnull(_params, "query_type"))) {
            priestList = departdcService.priestList(_params);
            mv.addObject("priestList", priestList);
        }
        
        // select a department information
        Map departdcContents=null;
		try {
			departdcContents = departdcService.departdcContents(_params);
		} catch (CommonException e) {
			e.printStackTrace();
			mv.addObject("ERR_MSG", e.getMessage());
		}

		// Hierarchy of the selected department
        //List _1x000List = departdcService._1x000List(_params); // TODO Must delete
		mv.addObject("rtGroupVO1", departdcContents.remove(Const.ADM_MAPKEY_GROUPLIST+"1")); // 교구청 Org Hierarchy Only
        mv.addObject("rtGroupVO2", departdcContents.remove(Const.ADM_MAPKEY_GROUPLIST+"2")); // 교구청 Org Hierarchy Only
        mv.addObject("rtVO", departdcContents); // Selected Org Contents
        mv.addObject("_params", _params);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/departdc_getPList.do")
    public @ResponseBody List<Map> departdcViewGetPriestList(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        //ModelAndView mv = new ModelAndView("/admin/board/departdc_view");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        //
        List<Map> priestList = null;
        if(!pnull(_params, "departCode").equals(""))
        {
            priestList = departdcService.departdcViewGetPriestList(_params);
            //mv.addObject(priestList);
        } else {
        	priestList = new ArrayList();
        }
        
        //return mv;
        return priestList;
    }

	@RequestMapping(value = "/admin/board/departdc_insert.do")
    public void departdcInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/admin/board/departdc_list");
        
        
        
        boolean success = departdcService.departdcInsert(_params);
        if(success)
            response.sendRedirect("/admin/board/departdc_list.do");
    }

	@RequestMapping(value = "/admin/board/departdc_modify.do")
    public void departdcModify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );

        
        
        boolean success=false;
		try {
			success = departdcService.departdcModify(_params);
		} catch (CommonException e) {
			e.printStackTrace();
			
		}
		// old code :: why.. ?
		//ModelAndView mv = new ModelAndView("/admin/board/departdc_list");
//        if(success) response.sendRedirect("/admin/board/departdc_list.do");
		// new code
		request.getRequestDispatcher("/admin/board/departdc_list.do").forward(request, response);
    }

	@RequestMapping(value = "/admin/board/departdc_delete.do")
    public void inqDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/departdc_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );

        
        
        boolean success = departdcService.departdcDelete(_params);
        if(success)
            response.sendRedirect("/admin/board/departdc_list.do");
    }

    
}
