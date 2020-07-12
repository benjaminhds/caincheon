// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmNewsController.java

package kr.caincheon.church.controller;

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

import kr.caincheon.church.admin.serivce.NBoardServiceImpl;
import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.news.NewsControllerConst;
import kr.caincheon.church.service.SchService;

@Controller
public class AdmNewsController extends CommonController implements NewsControllerConst
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="nBoardService")
    private NBoardServiceImpl nBoardService;
	
	@Resource(name="schService")
    private SchService schService;
	
	private final String left_menu_data_pg = "board_02"; // 교회/교구/공동체
	
	private final int attachedFileCount = 5; 

	/*
	 * 본당알림 조회
	 */
	@RequestMapping(value = "/admin/board/news_list.do")  
    public ModelAndView newsList(HttpServletRequest request) throws Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/news_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "_params ? " + _params);
        
        
        
        /*
         * 목록조회 공통호출  
         */
		pnullPut(_params, "LV1", "02' AND LV2!='00' AND C.CHURCH_IDX IS NOT NULL  AND LV3!='000");
		pnullPut(_params, "TOP_COUNT", "100");
		callNBoardList(nBoardService, _params, mv, left_menu_data_pg, true, Integer.parseInt(ATTACHED_FILE_COUNT));
		_params.remove("TOP_COUNT");
		_params.remove("LV1");
		
		//
        String searchGubun1 = pnull(_params.get("searchGubun1"));
        String searchGubun2 = pnull(_params.get("searchGubun2"));
        String searchText   = pnull(_params.get("searchText"));
        
        Map<String, String> schTextGubunMap1 = new HashMap<String, String>();
        if(searchGubun1.equals("9"))  schTextGubunMap1.put("9",  "selected"); else schTextGubunMap1.put("9", "");
        if(searchGubun1.equals("12")) schTextGubunMap1.put("12", "selected"); else schTextGubunMap1.put("12", "");
        if(searchGubun1.equals("99")) schTextGubunMap1.put("99", "selected"); else schTextGubunMap1.put("99", "");
        
        Map<String, String> schTextGubunMap2 = new HashMap<String, String>();
        if(searchGubun2.equals("title"))   schTextGubunMap2.put("title",   "selected"); else schTextGubunMap2.put("title", "");
        if(searchGubun2.equals("content")) schTextGubunMap2.put("content", "selected"); else schTextGubunMap2.put("content", "");
        
        mv.addObject("b_idx", pnull(_params.get("b_idx")));
        mv.addObject("searchText", searchText);
        mv.addObject("schTextGubunMap1", schTextGubunMap1);
        mv.addObject("schTextGubunMap2", schTextGubunMap2);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/news_view.do")
    public ModelAndView admNewsContents(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/news_view");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "_params ? " + _params);
        
        CommonDaoDTO rtDTO = null;
        try {
        	pnullPut(_params, "LV1", "01' AND LV2!='00' AND D.DEPART_IDX IS NOT NULL AND LV3!='000");
        	rtDTO = nBoardService.nboardContents(_params, LEFT_MENU_DATA_PG, true, attachedFileCount);
            
        } catch(CommonException e) {
            e.printStackTrace();
        }
        
        //java.util.List xxxx_del_list = schService._1x00xList(_params);
        mv.addObject("_params", _params);
        mv.addObject("_1x00xList", rtDTO.orgList);
        mv.addObject("CONTENTS", rtDTO.daoDetailContent);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/news_insert.do")
    public ModelAndView admNewsInsert(Map map, HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/news_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "_params ? " + _params);
        
        
        
        boolean success = false;
        
        try {
            success = nBoardService.nboardInsert(_params, left_menu_data_pg, request);
        } catch(Exception e) {
        	e.printStackTrace();
        }
        
        if(!success) 
        	mv = new ModelAndView("/admin/board/news_view");
        else {
	        // 목록조회 공통호출 
    		pnullPut(_params, "LV1", "02' AND LV2!='00' AND LV3!='000");
    		pnullPut(_params, "TOP_COUNT", "100");
    		callNBoardList(nBoardService, _params, mv, left_menu_data_pg, true, Integer.parseInt(ATTACHED_FILE_COUNT));
        }
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/news_modify.do")
    public ModelAndView admNewsModify(Map map, HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/news_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "_params ? " + _params);
        
        
        
        boolean success = false;
        
        try {
            success = nBoardService.nboardModify(_params, left_menu_data_pg, request);
        } catch(CommonException e) {
            e.printStackTrace();
        }
        
        if(!success) 
        	mv = new ModelAndView("/admin/board/news_view");
        else{
	        // 목록조회 공통호출 
    		pnullPut(_params, "LV1", "02' AND LV2!='00' AND LV3!='000");
    		pnullPut(_params, "TOP_COUNT", "100");
    		callNBoardList(nBoardService, _params, mv, left_menu_data_pg, true, Integer.parseInt(ATTACHED_FILE_COUNT));
        }
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/news_delete.do")
    public ModelAndView admNewsDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/news_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "_params ? " + _params);
        
        
        
        boolean success = false;
        try {
            success = nBoardService.nboardDelete(_params, left_menu_data_pg);
        } catch(CommonException e) {
            e.printStackTrace();
        }

        // 목록조회 공통호출 
		pnullPut(_params, "LV1", "02' AND LV2!='00' AND LV3!='000");
		pnullPut(_params, "TOP_COUNT", "100");
		callNBoardList(nBoardService, _params, mv, left_menu_data_pg, true, Integer.parseInt(ATTACHED_FILE_COUNT));
        
        return mv;
    }

    
}
