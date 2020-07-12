// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmBonNoticeController.java

package kr.caincheon.church.controller;

import java.io.IOException;
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

/**
 * 본당알림 관리자 기능
 * @author benjamin
 */
@Controller
public class AdmBonNoticeController extends CommonController implements AdmBonNoticeControllerConst
{
	private final Logger _logger = Logger.getLogger(getClass());
	
//	@Resource(name="admBonNoticeService")
//    private AdmBonNoticeService admBonNoticeService;
	

	//
	@Resource(name="nBoardService")
    private NBoardServiceImpl nBoardService;
	
	/*
	 * 본당알림 목록 조회
	 */
	@RequestMapping(value = "/admin/board/church_list.do")
    public ModelAndView admBonNoticeList(HttpServletRequest request) throws Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/church_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );
        
        
        pnullPut(_params, "b_idx", "ALL");
        
        // 
        pnullPut(_params, "LV1", "02' AND LV2!='00' AND LV3!='000");//본당조직
        pnullPut(_params, "TOP_COUNT", "100");//상단고정 개수
        callNBoardList(nBoardService, _params, mv, LEFT_MENU_DATA_PG, true, Integer.parseInt(ATTACHED_FILE_COUNT));//조직조회 true
        
        //
        String searchGubun2 = pnull(_params.get("searchGubun2"));
        String searchText = pnull(_params.get("searchText"));
        
        Map<String, String> schTextGubunMap2 = new HashMap<String, String>();
        if(searchGubun2.equals("title"))   schTextGubunMap2.put("title", "selected");   else schTextGubunMap2.put("title", "");
        if(searchGubun2.equals("content")) schTextGubunMap2.put("content", "selected"); else schTextGubunMap2.put("content", "");
        if(searchGubun2.equals("writer"))  schTextGubunMap2.put("writer", "selected");  else schTextGubunMap2.put("writer", "");
        if(searchGubun2.equals("all"))     schTextGubunMap2.put("all", "selected");     else schTextGubunMap2.put("all", "");
        
        mv.addObject("b_idx", pnull(_params.get("b_idx")));
        mv.addObject("searchText", searchText);
        mv.addObject("schTextGubunMap2", schTextGubunMap2);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/*
	 * 본당알림 상세 조회
	 */
	@RequestMapping(value = "/admin/board/church_view.do")
    public ModelAndView admBonNoticeView(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/church_view");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        
        pnullPut(_params, "LV1", "02' AND LV2!='00' AND C.CHURCH_IDX IS NOT NULL AND LV3!='000");//본당조회
        callNBoardContents(nBoardService, _params, mv, LEFT_MENU_DATA_PG, true, Integer.parseInt(ATTACHED_FILE_COUNT));
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/church_insert.do")
    public ModelAndView admBonNoticeInsert(Map map, HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );

        
        
        boolean success = false;
        try {
            success = nBoardService.nboardInsert(_params, LEFT_MENU_DATA_PG, request);
        } catch(Exception e) { 
        	e.printStackTrace();
        }
        
        //
        ModelAndView mv = new ModelAndView("/admin/board/church_list");
        if(!success) {
        	mv = new ModelAndView("/admin/board/church_view", _params);
        }
        _params.remove("church_idx");
        _params.remove("query_type");
        _params.remove("bl_idx");
        _params.remove("p_church_idx");
        pnullPut(_params, "LV1", "02' AND LV2!='00' AND C.CHURCH_IDX IS NOT NULL AND LV3!='000");//본당조회
        callNBoardList(nBoardService, _params, mv, LEFT_MENU_DATA_PG, true, Integer.parseInt(ATTACHED_FILE_COUNT));

        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

    @RequestMapping(value = "/admin/board/church_modify.do")
    public ModelAndView admBonNoticeModify(Map map, HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        
        boolean success = false;
        try {
            success = nBoardService.nboardModify(_params, LEFT_MENU_DATA_PG, request);
        } catch(Exception e) { 
        	e.printStackTrace();
        }

        //
        ModelAndView mv = null;
        if(!success) {
        	mv = new ModelAndView("/admin/board/church_view", _params);
        } else {
        	_params.remove("bl_idx");
        	mv = new ModelAndView("/admin/board/church_list", _params);
        }
        pnullPut(_params, "LV1", "02' AND LV2!='00' AND LV3!='000");// 본당조회
        callNBoardList(nBoardService, _params, mv, LEFT_MENU_DATA_PG, true, Integer.parseInt(ATTACHED_FILE_COUNT));
        
//        try
//        {
//            success = admBonNoticeService.admBonNoticeModify(_params, request);
//        }
//        catch(CommonException e)
//        {
//            e.printStackTrace();
//        }
//        if(success)
//            response.sendRedirect("/admin/board/church_list.do");
//        else
//            response.sendRedirect("/admin/board/church_view.do");
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

    @RequestMapping(value = "/admin/board/church_delete.do")
    public ModelAndView admBonNoticeDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/church_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        
        try {
            nBoardService.nboardDelete(_params, LEFT_MENU_DATA_PG);
        } catch(Exception e) { 
        	e.printStackTrace();
        }
//        try
//        {
//            success = admBonNoticeService.admBonNoticeDelete(_params);
//        }
//        catch(CommonException e)
//        {
//            e.printStackTrace();
//        }
//        if(success)
//            response.sendRedirect("/admin/board/church_list.do");
        

        // 
        callNBoardList(nBoardService, _params, mv, LEFT_MENU_DATA_PG, true, Integer.parseInt(ATTACHED_FILE_COUNT));
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

    
}
