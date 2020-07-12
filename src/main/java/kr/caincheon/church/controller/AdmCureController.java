// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmCureController.java

package kr.caincheon.church.controller;

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

import kr.caincheon.church.admin.serivce.NBoardServiceImpl;
import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.Paging;
import kr.caincheon.church.samok.CureControllerConst;

@Controller
public class AdmCureController extends CommonController implements CureControllerConst
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="nBoardService")
    private NBoardServiceImpl nBoardService;
	
	/*
	 * 
	 */
	private void selectList(Map _params, ModelAndView mv) throws Exception {
        // 
        CommonDaoDTO rtDto = null;//new CommonDaoDTO();
		try {
			pnullPut(_params, "TOP_COUNT", "100");
			//rtDto = nBoardService.nboardList(_params, LEFT_MENU_DATA_PG, false, Integer.parseInt(ATTACHED_FILE_COUNT));
			rtDto = callNBoardList(nBoardService, _params, mv, LEFT_MENU_DATA_PG, false, Integer.parseInt(ATTACHED_FILE_COUNT));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 
		Paging paging = new Paging();
        paging.setPageNo(ipnull(_params, "pageNo", 1));
        paging.setPageSize(ipnull(_params, "pageSize", 10));
        paging.setTotalCount(rtDto.daoTotalCount);
        
        //
        mv.addObject("_params", _params);
        mv.addObject("newsList", rtDto.daoList);
        mv.addObject("paging", paging);
	}
	
	@RequestMapping(value = "/admin/board/cure_list.do")
    public ModelAndView cureList(HttpServletRequest request) throws Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/cure_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );
        
        String searchGubun1 = pnull(_params.get("searchGubun1"));
        String searchGubun2 = pnull(_params.get("searchGubun2"));
        String searchText   = pnull(_params.get("searchText"));
        if(searchGubun1.length()>0) {
        	
        }
        String b_idx   = pnull(_params.get("b_idx"));
        
        // 분류(c_idx)
        LinkedHashMap schTextGubunMap1 = new LinkedHashMap();
        if(searchGubun1.equals("5")) schTextGubunMap1.put("5", "selected"); else schTextGubunMap1.put("5", "");
        if(searchGubun1.equals("6")) schTextGubunMap1.put("6", "selected"); else schTextGubunMap1.put("6", "");
        if(searchGubun1.equals("7")) schTextGubunMap1.put("7", "selected"); else schTextGubunMap1.put("7", "");
        if(searchGubun1.equals("8")) schTextGubunMap1.put("8", "selected"); else schTextGubunMap1.put("8", "");
        if(searchGubun1.equals("9")) schTextGubunMap1.put("9", "selected"); else schTextGubunMap1.put("9", "");
        if(searchGubun1.equals("10")) schTextGubunMap1.put("10", "selected"); else schTextGubunMap1.put("10", "");
        
        LinkedHashMap schTextGubunMap2 = new LinkedHashMap();
        if(searchGubun2.equals("title"))    schTextGubunMap2.put("title", "selected");   else schTextGubunMap2.put("title", "");
        if(searchGubun2.equals("contents")) schTextGubunMap2.put("contents", "selected"); else schTextGubunMap2.put("contents", "");
        
        
        pnullPut(_params, "c_idx", "ALL");
        
        // 
        this.selectList(_params, mv);
        
        //
        mv.addObject("b_idx", pnull(_params.get("b_idx")));
        mv.addObject("searchText", searchText);
        mv.addObject("schTextGubunMap1", schTextGubunMap1);
        mv.addObject("schTextGubunMap2", schTextGubunMap2);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/cure_view.do")
    public ModelAndView admNewsView(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/admin/board/cure_view");
        
        
        // 
        CommonDaoDTO rtDto = new CommonDaoDTO();
		try {
			if( !"insert".equalsIgnoreCase(pnull(_params, "query_type")) ) {
				rtDto = nBoardService.nboardContents(_params, LEFT_MENU_DATA_PG, false, Integer.parseInt(ATTACHED_FILE_COUNT));
			}
		} catch (CommonException e) {
			rtDto = new CommonDaoDTO();
			e.printStackTrace();
		}
        
        
//        Map newsContents = null;
//        try
//        {
//        	if("insert".equalsIgnoreCase(pnull(_params, "query_type"))) {
//        		newsContents = new HashMap();
//        	} else {
//        		newsContents = nBoardService.nboardContents(_params, menu);
//        	}
//        }
//        catch(CommonException e)
//        {
//            e.printStackTrace();
//        }
        mv.addObject("_params", _params);
        mv.addObject("CONTENTS", rtDto.daoDetailContent);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/cure_insert.do")
    public ModelAndView admNewsInsert(Map map, HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );
        
        
        
        boolean success = false;
        try {
            success = nBoardService.nboardInsert(_params, LEFT_MENU_DATA_PG, request);
        } catch(Exception e) { 
        	e.printStackTrace();
        }

        //
        ModelAndView mv = new ModelAndView("/admin/board/cure_list");
        if(!success) {
        	mv = new ModelAndView("/admin/board/cure_view", _params);
        }
        this.selectList(_params, mv);

        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/cure_modify.do")
    public ModelAndView admNewsModify(Map map, HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );
        
        
        
        boolean success = false;
        try {
            success = nBoardService.nboardModify(_params, LEFT_MENU_DATA_PG, request);
        } catch(CommonException e) {
            e.printStackTrace();
        }
        

        //
        ModelAndView mv = new ModelAndView("/admin/board/cure_list");
        if(!success) {
        	mv = new ModelAndView("/admin/board/cure_view", _params);
        }
        this.selectList(_params, mv);

        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/cure_delete.do")
    public ModelAndView admNewsDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/cure_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );
        
        try {
            nBoardService.nboardDelete(_params, LEFT_MENU_DATA_PG);
        } catch(CommonException e) {
            e.printStackTrace();
        }
        
        // 
        this.selectList(_params, mv);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

    
}
