// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NewsController.java

package kr.caincheon.church.news;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.caincheon.church.admin.serivce.NBoardServiceImpl;
import kr.caincheon.church.common.base.CommonController;

/**
 * 교구소식/공동체소식을 해당 게시판 관리자가 등록/수정 처리를 하는 컨트롤러
 * @author benjamin
 */
@Controller
public class NewsManagementController extends CommonController implements NewsControllerConst
{

	private final Logger _logger = Logger.getLogger(getClass());

	@Resource(name="nBoardService")
    private NBoardServiceImpl nBoardService;
	
	
	/*
	 * 교구소식 upsert
	 */
	@RequestMapping(value = "/news/news_view_gyogu_insert.do")
    public void insertGyoguNews(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
		// request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        // service call
	    _params.put("b_idx", "12");
        nBoardService.nboardInsert(_params, LEFT_MENU_DATA_PG, request);
        
        // response
	    String b_idx   = "b_idx="+pnull(_params, "b_idx", "12");
	    String bl_idx  = "bl_idx="+pnull(_params, "bl_idx");
	    String pageNo  = "pageNo="+pnull(_params, "pageNo");
        String gotoURL = "/news/news_list.do?"+pageNo+"&"+b_idx;
        response.sendRedirect(gotoURL);
        //request.getRequestDispatcher(gotoURL).forward(request, response);
    }

	/*
	 * 교구소식 upsert
	 */
	@RequestMapping(value = "/news/news_view_gyogu_update.do")
	public void updateGyoguNews(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
	    build(request);
	    D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
	    _params.put("b_idx", "12");

        // service call
	    nBoardService.nboardModify(_params, LEFT_MENU_DATA_PG, request);
	    
	    // response
	    String b_idx   = "b_idx="+pnull(_params, "b_idx", "12");
	    String bl_idx  = "bl_idx="+pnull(_params, "bl_idx");
	    String pageNo  = "pageNo="+pnull(_params, "pageNo");
	    String gotoURL = "/news/news_view.do?"+pageNo+"&"+b_idx+"&"+bl_idx;
        response.sendRedirect(gotoURL);
        
//	    ModelAndView mv = new ModelAndView("/news/news_view");
//	     
//	    mv.addObject("_params", _params);
//	    mv.addObject("b_idx", pnull(_params, "b_idx", "12"));
//	    mv.addObject("bl_idx", pnull(_params, "bl_idx"));
//	    mv.addObject("pageNo", pnull(_params, "pageNo", "1"));
//        mv.addObject("pageSize", "20");
	    
//	    D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
	    
//	    return mv;
	}

	/*
	 * 공동체소식 insert
	 */
	@RequestMapping(value = "/news/news_view_community_insert.do")
	public void insertCommunityNews(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
	    build(request);
	    D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
	
        // service call
	    _params.put("b_idx", "10");
        nBoardService.nboardInsert(_params, LEFT_MENU_DATA_PG, request);
        
        // response
	    String b_idx   = "b_idx="+pnull(_params, "b_idx", "10");
	    String bl_idx  = "bl_idx="+pnull(_params, "bl_idx");
	    String pageNo  = "pageNo="+pnull(_params, "pageNo");
        String gotoURL = "/news/news_list.do?"+pageNo+"&"+b_idx;
        //response.sendRedirect(gotoURL);
        request.getRequestDispatcher(gotoURL).forward(request, response);
	}

	/*
	 * 공동체소식 upsert
	 */
	@RequestMapping(value = "/news/news_view_community_update.do")
    public void updateCommunityNews(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );

        // service call
	    _params.put("b_idx", "10");

        // service call
        nBoardService.nboardModify(_params, LEFT_MENU_DATA_PG, request);
        
	    // response
	    String b_idx   = "b_idx="+pnull(_params, "b_idx", "10");
	    String bl_idx  = "bl_idx="+pnull(_params, "bl_idx");
	    String pageNo  = "pageNo="+pnull(_params, "pageNo");
	    String gotoURL = "/news/news_view.do?"+pageNo+"&"+b_idx+"&"+bl_idx;
        //response.sendRedirect(gotoURL);
	    request.getRequestDispatcher(gotoURL).forward(request, response);
	    
//	    ModelAndView mv = new ModelAndView("/news/news_list");
//	    ModelAndView mv = new ModelAndView("/news/news_view");
//	    mv.addObject("_params", _params);
//	    mv.addObject("b_idx", pnull(_params, "b_idx", "12"));
//	    mv.addObject("bl_idx", pnull(_params, "bl_idx"));
//	    mv.addObject("pageNo", pnull(_params, "pageNo", "1"));
//	    mv.addObject("pageSize", "20");
//        
//	    D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
//        
//	    return mv;
    }
}
