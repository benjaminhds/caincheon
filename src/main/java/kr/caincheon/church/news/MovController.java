// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MovController.java

package kr.caincheon.church.news;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.admin.serivce.NBoardServiceImpl;
import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.controller.AdmGyoguMovControllerConst;
import kr.caincheon.church.news.service.MovService;

@Controller
public class MovController extends CommonController implements AdmGyoguMovControllerConst
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="movService")
    private MovService movService;

	@Resource(name="nBoardService")
    private NBoardServiceImpl nBoardService;
	
    /*
     * 홈 > 소식 > 교구영상 : 목록
     */
	@RequestMapping(value = "/news/mov_list.do")
    public ModelAndView moveList(HttpServletRequest request) throws Exception
    {
		// request
        ModelAndView mv = new ModelAndView("/news/mov_list");
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        _params.put("b_idx", "23");
        _params.put("pageSize", "12");
        _params.put("is_view", "Y");
        
        // service call
        //CommonDaoDTO  dto = nBoardService.nboardList(_params, LEFT_MENU_DATA_PG, false, 1);
        CommonDaoDTO dto = movService.youtubeList(_params, pnull(_params, "pageNo"), pnull(_params, "pageSize"));
        
        // Response
        String strCategoryName = "교구영상";
        String srchGubun = pnull(_params.get("srchGubun"));
        
        Map<String, String> schTextGubunMap = new HashMap<String, String>();
        if(srchGubun.equals("all"))  schTextGubunMap.put("all", "selected");  else schTextGubunMap.put("all", "");
        if(srchGubun.equals("date")) schTextGubunMap.put("date", "selected"); else schTextGubunMap.put("date", "");
        if(srchGubun.equals("contents")) schTextGubunMap.put("contents", "selected"); else schTextGubunMap.put("contents", "");
        
        //
        mv.addObject("strCategoryName", strCategoryName);
        mv.addObject("schTextGubunMap", schTextGubunMap);
        
        mv.addObject("movList", dto.daoList);// movList);
        mv.addObject("paging", dto.paging);// paging);
        mv.addObject("_params", _params);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/news/mov_view.do")
    public ModelAndView movContents(HttpServletRequest request) throws Exception
    {
        ModelAndView mv = new ModelAndView("/news/mov_view");
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        String strCategoryName = "";
        
        //
        _params.put("b_idx", "23");
        //Map movContents = movService.movContents(_params);
        java.util.List movDList = movService.movDownloads(_params).daoList;
        
        CommonDaoDTO  dto = nBoardService.nboardContents(_params, LEFT_MENU_DATA_PG, false, 1);
        
        //
        mv.addObject("_params", _params);
        mv.addObject("b_idx",           pnull(_params.get("b_idx")));
        mv.addObject("bl_idx",          pnull(_params.get("bl_idx")));
        mv.addObject("strCategoryName", strCategoryName);
        
        mv.addObject("movContents",     dto.daoDetailContent);//movContents);
        mv.addObject("movDList",        movDList);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

    
}
