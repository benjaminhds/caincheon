// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BannerController.java

package kr.caincheon.church.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
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
import kr.caincheon.church.common.utils.UtilString;
import kr.caincheon.church.service.BannerService;

@Controller
public class BannerController extends CommonController
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="bannerService")
    private BannerService bannerService;

	@RequestMapping(value = "/admin/board/banner_list.do")
    public ModelAndView bannerList(HttpServletRequest request)
    		throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/banner_list");
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        pnullPut(_params, "pageNo", "1");
        pnullPut(_params, "pageSize", "20");
        
        String schTextGubun = "";
        String searchText = "";
        schTextGubun = UtilString.pnull(_params.get("searchGubun"));
        searchText = UtilString.pnull(_params.get("searchText"));
        LinkedHashMap<String, String> searchGubunMap = new LinkedHashMap<String, String>();
        if(schTextGubun.equals("title")) searchGubunMap.put("title", "selected");
        if(schTextGubun.equals("url"))   searchGubunMap.put("url", "selected");
        
        // service call
        CommonDaoDTO dto = null;
		try {
			dto = bannerService.admBannerList(_params);
			
			// response
			mv.addObject("bannerList", dto.daoList);
	        mv.addObject("paging",     dto.paging);
		} catch (CommonException e) {
			e.printStackTrace();
		}
        
        // response
        mv.addObject("_params", _params);
        mv.addObject("schTextGubun",   schTextGubun);
        mv.addObject("searchText",     searchText);
        mv.addObject("searchGubunMap", searchGubunMap);
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/banner_view.do")
    public ModelAndView bannerView(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/banner_view");
        build(request);
        
        pnullPut(_params, "pageNo", "1");
        pnullPut(_params, "pageSize", "20");
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        
		try {
			Map banner = bannerService.admBannerContents(_params);
			mv.addObject("banner", banner);
		} catch (CommonException e) {
			e.printStackTrace();
		}
        mv.addObject("query_type", "update");
        mv.addObject("_params", _params);
        
        return mv;
    }

	/*
	 * 멀티파트
	 */
	@RequestMapping(value = "/admin/board/banner_insert.do")
    public void bannerInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/banner_list");
        build(request);
        
        pnullPut(_params, "pageNo", "1");
        pnullPut(_params, "pageSize", "20");
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        boolean success = false;
		try {
			success = bannerService.admBannerInsert(_params, request);
		} catch (CommonException e) {
			e.printStackTrace();
		}
        
        if(success)
            response.sendRedirect("/admin/board/banner_list.do");
    }

	/*
	 * 멀티파트
	 */
	@RequestMapping(value = "/admin/board/banner_modify.do")
    public void bannerModify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/banner_list");
        build(request);
        
        pnullPut(_params, "pageNo", "1");
        pnullPut(_params, "pageSize", "20");
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        boolean success = false;
		try {
			success = bannerService.admBannerModify(_params, request);
		} catch (CommonException e) {
			e.printStackTrace();
		}
        
        if(success)
            response.sendRedirect("/admin/board/banner_list.do");
    }

	@RequestMapping(value = "/admin/board/banner_delete.do")
    public void inqDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/banner_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        pnullPut(_params, "pageNo", "1");
        pnullPut(_params, "pageSize", "20");
        
        boolean success = false;
		try {
			success = bannerService.admBannerDelete(_params);
		} catch (CommonException e) {
			e.printStackTrace();
		}
        
        if(success)
            response.sendRedirect("/admin/board/banner_list.do");
    }

    
}
