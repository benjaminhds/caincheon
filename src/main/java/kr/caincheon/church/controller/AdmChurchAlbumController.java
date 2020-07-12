// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmChurchAlbumController.java

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
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.service.AdmBonNoticeService;

@Controller
public class AdmChurchAlbumController extends CommonController implements AdmChurchAlbumControllerConst
{

	private final Logger _logger = Logger.getLogger(getClass());
    
	@Resource(name="nBoardService")
	private NBoardServiceImpl nBoardService;
    
    @Resource(name="admBonNoticeService")
    private AdmBonNoticeService admBonNoticeService;

    /*
     * 본당앨범 목록
     */
    @RequestMapping(value = "/admin/church/church_album.do")
    public ModelAndView admChurchAlbumList(HttpServletRequest request) throws Exception
    {
        ModelAndView mv = new ModelAndView("/admin/church/church_album");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        
        
		//
		pnullPut(_params, "LV1", "02' AND LV2!='00' AND LV3!='000");
		pnullPut(_params, "TOP_COUNT", "100");
		callNBoardList(nBoardService, _params, mv, LEFT_MENU_DATA_PG, true, Integer.parseInt(ATTACHED_FILE_COUNT));
		
        
		// response
        String srchGubun2 = pnull(_params.get("schTextGubun"));
        String searchText = pnull(_params.get("schText"));
        
		HashMap<String, String> schTextGubunMap2 = new HashMap<String, String>();
        schTextGubunMap2.put("title",   srchGubun2.equals("title") ? "selected":"");
        schTextGubunMap2.put("content", srchGubun2.equals("content") ? "selected":"");
        schTextGubunMap2.put("all",     srchGubun2.equals("all") ? "selected":"");
        schTextGubunMap2.put("writer",  srchGubun2.equals("writer") ? "selected":"");
        
        mv.addObject("b_idx", pnull(_params.get("b_idx")));
        mv.addObject("searchText",       searchText);
        mv.addObject("schTextGubunMap2", schTextGubunMap2);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

    /*
     * 본당 앨범 보기 & 수정폼을 위한 조회
     */
    @RequestMapping(value = "/admin/church/church_album_view.do")
    public ModelAndView admChurchAlbumView(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/church/church_album_view");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        
        pnullPut(_params, "LV1", "02' AND LV2!='00' AND LV3!='000");//본당조회
        callNBoardContents(nBoardService, _params, mv, LEFT_MENU_DATA_PG, true, Integer.parseInt(ATTACHED_FILE_COUNT));
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

    @RequestMapping(value = "/admin/church/church_album_insert.do")
    public void admChurchAlbumInsert(Map map, HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/church/church_album");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );

        
        
        boolean success = false;
        try
        {
        	pnullPut(_params, "CONTEXT_URI_PATH", "/upload/church_album/");
        	pnullPut(_params, "CONTEXT_ROOT_PATH", request.getSession().getServletContext().getRealPath("/") + "upload/church_album/");
        	
            success = nBoardService.nboardInsert(_params, LEFT_MENU_DATA_PG, request);
        } catch(CommonException e) { 
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR", e);
        }
        
        if(success)
            response.sendRedirect("/admin/church/church_album.do");
        else
            response.sendRedirect("/admin/church/church_album_view.do");
    }

    @RequestMapping(value = "/admin/church/church_album_modify.do")
    public void admChurchAlbumModify(Map map, HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/church/church_album");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );

        
        
        boolean success = false;
        try
        {
        	pnullPut(_params, "CONTEXT_URI_PATH", "/upload/church_album/");
        	pnullPut(_params, "CONTEXT_ROOT_PATH", request.getSession().getServletContext().getRealPath("/") + "upload/church_album/");
            success = nBoardService.nboardModify(_params, LEFT_MENU_DATA_PG, request);
        } catch(CommonException e) { 
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR", e);
        }
        if(success)
            response.sendRedirect("/admin/church/church_album.do");
        else
            response.sendRedirect("/admin/church/church_album_view.do");
    }

    @RequestMapping(value = "/admin/church/church_album_delete.do")
    public void admChurchAlbumDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/church/church_album");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        
        boolean success = false;
        try
        {
            success = nBoardService.nboardDelete(_params, LEFT_MENU_DATA_PG);
        } catch(CommonException e) { 
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR", e);
        }
        if(success)
            response.sendRedirect("/admin/church/church_album.do");
    }

}
