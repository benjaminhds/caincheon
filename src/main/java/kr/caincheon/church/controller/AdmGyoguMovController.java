// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmGyoguMovController.java

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
import kr.caincheon.church.service.AdmBonNoticeService;

@Controller
public class AdmGyoguMovController extends CommonController implements AdmGyoguMovControllerConst
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="nBoardService")
    private NBoardServiceImpl nBoardService;
    
    @Resource(name="admBonNoticeService")
    private AdmBonNoticeService admBonNoticeService;

    /*
     * 교구영상 목록조회
     */
    @RequestMapping(value = "/admin/gyogu/gyoguMov_list.do")
    public ModelAndView admGyoguMovList(HttpServletRequest request) throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/gyogu/gyoguMov_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        pnullUpdate(_params, "pageSize", "12");
        //mv.addObject("b_idx", "22");

        // 
        try {
			//this.selectList(_params, mv);
            pnullPut(_params, "LV1", "01' AND LV2!='00' AND LV3!='000");//교구조직
            callNBoardList(nBoardService, _params, mv, LEFT_MENU_DATA_PG, true, Integer.parseInt(ATTACHED_FILE_COUNT));
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        /*
         * 화면처리
         */
        String searchGubun1 = pnull(_params.get("searchGubun1"));
        String searchGubun2 = pnull(_params.get("searchGubun2"));
        String searchText   = pnull(_params.get("searchText"));
        
        LinkedHashMap schTextGubunMap1 = new LinkedHashMap();
        schTextGubunMap1.put("11", searchGubun1.equals("11") ? "selected":"");
        schTextGubunMap1.put("12", searchGubun1.equals("12") ? "selected":"");
        
        LinkedHashMap schTextGubunMap2 = new LinkedHashMap();
        schTextGubunMap2.put("title"  , searchGubun2.equals("title") ? "selected":"");
        schTextGubunMap2.put("content", searchGubun2.equals("content") ? "selected":"");
        schTextGubunMap2.put("writer" , searchGubun2.equals("writer") ? "selected":"");
        schTextGubunMap2.put("all"    , searchGubun2.equals("all") ? "selected":"");
        
        //
        mv.addObject("searchText", searchText);
        mv.addObject("schTextGubunMap1", schTextGubunMap1);
        mv.addObject("schTextGubunMap2", schTextGubunMap2);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

    @RequestMapping(value = "/admin/gyogu/gyoguMov_view.do")
    public ModelAndView admGyoguMovView(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/gyogu/gyoguMov_view");
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        CommonDaoDTO rtDto = new CommonDaoDTO();
        try {
            
            rtDto = nBoardService.nboardContents( _params, LEFT_MENU_DATA_PG, false, Integer.parseInt(ATTACHED_FILE_COUNT) );
        } catch(Exception e) {
    		_E(_logger, Thread.currentThread().getStackTrace(), "ERROR", e);
        }
        
//        Map gyoguMovContents = null;
//        try {
//            gyoguMovContents = admNewsService.nboardContents(_params, LEFT_MENU_DATA_PG);
//        } catch(Exception e) {
//    		E(_logger, Thread.currentThread().getStackTrace(), "ERROR", e);
//        }
        mv.addObject("_params", _params);
        mv.addObject("CONTENTS", rtDto.daoDetailContent);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

    @RequestMapping(value = "/admin/gyogu/gyoguMov_insert.do")
    public void admGyoguMovInsert(Map map, HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/gyogu/gyoguMov_list");
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        
        boolean success = false;
        try {
//        	_params.put("CONTEXT_URI_PATH", "/"+uploadPath);
//        	_params.put("CONTEXT_ROOT_PATH", request.getSession().getServletContext().getRealPath("/")+uploadPath);
        	
            success = nBoardService.nboardInsert(_params, LEFT_MENU_DATA_PG, request);
        } catch(Exception e) {
    		_E(_logger, Thread.currentThread().getStackTrace(), "ERROR", e);
        }
        if(success)
            response.sendRedirect("/admin/gyogu/gyoguMov_list.do");
        else
            response.sendRedirect("/admin/gyogu/gyoguMov_view.do");
    }

    /*
     * 교구 동영상 수정하기
     */
    @RequestMapping(value = "/admin/gyogu/gyoguMov_modify.do")
    public void admGyoguMovModify(Map map, HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/gyogu/gyoguMov_list");
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        
        boolean success = false;
        try {
//        	_params.put("CONTEXT_URI_PATH", "/"+uploadPath);
//        	_params.put("CONTEXT_ROOT_PATH", request.getSession().getServletContext().getRealPath("/")+uploadPath);
        	
            success = nBoardService.nboardModify(_params, LEFT_MENU_DATA_PG, request);
        } catch(Exception e) {
    		_E(_logger, Thread.currentThread().getStackTrace(), "ERROR", e);
        }
        
        if(success)
            response.sendRedirect("/admin/gyogu/gyoguMov_list.do");
        else
            response.sendRedirect("/admin/gyogu/gyoguMov_view.do");
    }

    @RequestMapping(value = "/admin/gyogu/gyoguMov_delete.do")
    public void admGyoguMovDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/gyogu/gyoguMov_list");
        build(request);

        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        
        boolean success = false;
        try {
            success = nBoardService.nboardDelete(_params, LEFT_MENU_DATA_PG);
        } catch(Exception e) {
    		_E(_logger, Thread.currentThread().getStackTrace(), "ERROR", e);
        }
        
        response.sendRedirect("/admin/gyogu/gyoguMov_list.do");
    }

    
}
