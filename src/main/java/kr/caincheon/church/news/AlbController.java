// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AlbController.java

package kr.caincheon.church.news;

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

import kr.caincheon.church.church.AlbControllerConst;
import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.Paging;
import kr.caincheon.church.news.service.AlbService;

@Controller
public class AlbController extends CommonController implements AlbControllerConst
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="albService")
    private AlbService albService;

	@RequestMapping(value = "/news/alb_list.do")
    public ModelAndView albList(HttpServletRequest request) throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/news/alb_list");
        build(request, true);
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );
        
        String searchGubun = pnull(_params.get("searchGubun"));
        String searchText = "";
        String searchYear1 = "";
        String searchYear2 = "";
        if(searchGubun.equals("all")) {
            _params.put("searchText", "");
            _params.put("searchYear1", "");
            _params.put("searchYear2", "");
        } else if(searchGubun.equals("date")) {
            _params.put("searchText", "");
            searchText = pnull(_params.get("searchText"));
            searchYear1 = pnull(_params.get("searchYear1"));
            searchYear2 = pnull(_params.get("searchYear2"));
        } else if(searchGubun.equals("contents")) {
            _params.put("searchYear1", "");
            _params.put("searchYear2", "");
            searchText = pnull(_params.get("searchText"));
            searchYear1 = pnull(_params.get("searchYear1"));
            searchYear2 = pnull(_params.get("searchYear2"));
        }
        
        LinkedHashMap schTextGubunMap = new LinkedHashMap();
        if(searchGubun.equals("all"))
            schTextGubunMap.put("all", "selected");
        else
            schTextGubunMap.put("all", "");
        if(searchGubun.equals("date"))
            schTextGubunMap.put("date", "selected");
        else
            schTextGubunMap.put("date", "");
        if(searchGubun.equals("contents"))
            schTextGubunMap.put("contents", "selected");
        else
            schTextGubunMap.put("contents", "");
        String strCategoryName = "";
        if(pnull(_params.get("b_idx")).equals("1"))
            strCategoryName = "\uBBF8\uC0AC";
        else if(pnull(_params.get("b_idx")).equals("2"))
            strCategoryName = "\uAD50\uC721";
        else if(pnull(_params.get("b_idx")).equals("3"))
            strCategoryName = "\uD589\uC0AC";
        else
            strCategoryName = "\uC804\uCCB4\uBCF4\uAE30";

        
        
        java.util.List albList = albService.albList(_params);
        int totalCount = albService.albListCount(_params);
        Paging paging = new Paging();
        paging.setPageNo(Integer.parseInt(pnull(_params.get("pageNo"), "1")));
        paging.setPageSize(20);
        paging.setTotalCount(totalCount);
        
        mv.addObject("_params", _params);
        mv.addObject("b_idx", pnull(_params.get("b_idx")));
        mv.addObject("strCategoryName", strCategoryName);
        mv.addObject("albList", albList);
        mv.addObject("paging", paging);
        mv.addObject("schTextGubunMap", schTextGubunMap);
        mv.addObject("searchText", searchText);
        mv.addObject("searchYear1", searchYear1);
        mv.addObject("searchYear2", searchYear2);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/news/alb_view.do")
    public ModelAndView albContents(HttpServletRequest request) throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/news/alb_view");
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );

        Map albContents = albService.albContents(_params);
        mv.addObject("_params", _params);
        mv.addObject("idx", pnull(_params.get("idx")));
        mv.addObject("albContents", albContents);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv.getModelMap() );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/album_list.do")
    public ModelAndView albumList(HttpServletRequest request) throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/album_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );

        String s_gubun = pnull(_params.get("s_gubun"));
        String s_search = pnull(_params.get("s_search"));
        String searchText = pnull(_params.get("searchText"));
        LinkedHashMap s_gubunMap = new LinkedHashMap();
        if(s_gubun.equals("41"))
            s_gubunMap.put("41", "selected");
        else
            s_gubunMap.put("41", "");
        if(s_gubun.equals("42"))
            s_gubunMap.put("42", "selected");
        else
            s_gubunMap.put("42", "");
        LinkedHashMap s_searchMap = new LinkedHashMap();
        if(s_search.equals("title"))
            s_searchMap.put("title", "selected");
        else
            s_searchMap.put("title", "");
        if(s_search.equals("contents"))
            s_searchMap.put("contents", "selected");
        else
            s_searchMap.put("contents", "");
        
        
        
        //java.util.List albList = albService.albumList(_params);
        //int totalCount = albService.albumListCount(_params);
        CommonDaoDTO dto = albService.albumList(_params);
        
//        java.util.List _1x00xList = albService._1x00xList(_params);
//        Paging paging = new Paging();
//        paging.setPageNo(ipnull(_params, "pageNo", 1));
//        paging.setPageSize(10);
//        paging.setTotalCount(dto.daoTotalCount);
        
        mv.addObject("paging",     dto.paging);
        mv.addObject("albList",    dto.daoList);
        mv.addObject("_1x00xList", dto.orgList);//_1x00xList);
        
        mv.addObject("_params", _params);
        mv.addObject("s_searchMap", s_searchMap);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/album_view.do")
    public ModelAndView albumView(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/album_view");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );

        Map albContents = albService.albumContents(_params);
        java.util.List _1x00xList = albService._1x00xList(_params);
        mv.addObject("_params", _params);
        mv.addObject("CONTENTS", albContents);
        mv.addObject("_1x00xList", _1x00xList);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	// 교구앨범
	@RequestMapping(value = "/admin/board/album_insert.do")
    public void albumInsert(Map map, HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        //ModelAndView mv = new ModelAndView("/admin/board/album_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );

        
        
        boolean success = false;
        try {
            success = albService.albumInsert(_params, request);
        } catch(Exception exception) { }
        if(success)
            response.sendRedirect("/admin/board/album_list.do");
        else
            response.sendRedirect("/admin/board/album_view.do");
    }

	@RequestMapping(value = "/admin/board/album_modify.do")
    public void albumModify(Map map, HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/album_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );

        
        
        boolean success = false;
        try {
            success = albService.albumModify(_params, request);
        } catch(Exception e) {
            e.printStackTrace();
        }
        if(success)
            response.sendRedirect("/admin/board/album_list.do");
        else
            response.sendRedirect("/admin/board/album_view.do");
    }

	@RequestMapping(value = "/admin/board/album_delete.do")
    public void albumDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/album_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );

        
        
        boolean success = false;
        try {
            success = albService.albumDelete(_params, request);
        } catch(Exception e) {
            e.printStackTrace();
        }
        if(success)
            response.sendRedirect("/admin/board/album_list.do");
    }

    
}
