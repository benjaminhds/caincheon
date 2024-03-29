// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NewsController.java

package kr.caincheon.church.news;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.news.service.NewsService;
import kr.caincheon.church.schedule.GyoguScheduleUtil;

@Controller
public class NewsController extends CommonController implements NewsControllerConst
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="newsService")
    private NewsService newsService;

	/*
	 * 
	 */
	private String sameCompare(Map _params, String key, String compareVal, String matchedVal, String notMatchedVal) {
		String s = pnull(_params, key);
		return s.equals(compareVal) ? matchedVal : notMatchedVal;
	}
	
	
	
    /* bjm found */
	@RequestMapping(value = "/news/news_list.do")
    public ModelAndView newsList(HttpServletRequest request)
    		throws ServletException, Exception
    {
		// request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        ModelAndView mv = new ModelAndView("/news/news_list");
        
        // service call
        
        
        CommonDaoDTO dto = newsService.newsList(_params);
        
        // response
        LinkedHashMap schTextGubunMap = new LinkedHashMap();
        schTextGubunMap.put("title",   sameCompare(_params, "schTextGubun", "title"  , "selected", ""));
        schTextGubunMap.put("content", sameCompare(_params, "schTextGubun", "content", "selected", ""));
        schTextGubunMap.put("all",     sameCompare(_params, "schTextGubun", "all"    , "selected", ""));
        
        mv.addObject("strCategoryName", getBoardTitle(pnull(_params,"b_idx"), ""));
        mv.addObject("schTextGubunMap", schTextGubunMap);
        
        mv.addObject("_params",  _params);
        mv.addObject("newsList", dto.daoList);
        mv.addObject("paging",   dto.paging);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }
	
	/*
	 * 교회/교구/공동체 소식
	 */
	@RequestMapping(value = "/news/news_view_iMode.do")
    public ModelAndView newNewsContents(HttpServletRequest request)
    		throws ServletException, Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );

        ModelAndView mv = new ModelAndView("/news/news_view");
        mv.addObject("_params", _params);
        mv.addObject("b_idx", pnull(_params.get("b_idx")));
        mv.addObject("mode" , pnull(_params.get("mode")));
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }
	
	/*
	 * 교회/교구/공동체 소식
	 */
	@RequestMapping(value = "/news/news_view.do")
    public ModelAndView viewNewsContents(HttpServletRequest request)
    		throws ServletException, Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );

        ModelAndView mv = new ModelAndView("/news/news_view");
        
        String schTextGubun = pnull(_params.get("schTextGubun"));
        String schText = pnull(_params.get("schText"));
        
        LinkedHashMap schTextGubunMap = new LinkedHashMap();
        schTextGubunMap.put("title",   sameCompare(_params, "schTextGubun", "title"  , "selected", ""));
        schTextGubunMap.put("content", sameCompare(_params, "schTextGubun", "content", "selected", ""));
        schTextGubunMap.put("all",     sameCompare(_params, "schTextGubun", "all"    , "selected", ""));
        
        String strCategoryName = "";
        if(pnull(_params, "c_idx").equals("9"))
            strCategoryName = "교회소식";
        else if(pnull(_params, "c_idx").equals("12"))
            strCategoryName = "교구소식";
        else if(pnull(_params, "c_idx").equals("10"))
            strCategoryName = "공동체소식";
        else
            strCategoryName = "전체보기";
        
        Map contents = null;
        try {
        	contents = newsService.newsContents(_params);
        } catch (CommonException e) {
        	contents = new HashMap();
			mv.addObject("ERR_CD", e.getErrCode());
			mv.addObject("ERR_MSG", e.getErrMessage());
		} catch (Exception e) {
			contents = new HashMap();
			mv.addObject("ERR_CD", "ERROR-000");
			mv.addObject("ERR_MSG", e.getMessage());
		}
        
        mv.addObject("_params", _params);
        mv.addObject("b_idx", pnull(_params.get("b_idx")));
        mv.addObject("strCategoryName", strCategoryName);
        mv.addObject("schTextGubun", schTextGubun);
        mv.addObject("schText", schText);
        mv.addObject("schTextGubunMap", schTextGubunMap);
        mv.addObject("CONTENTS", contents);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/**
	 * front :: 교구장/총대리/부서/교구 일정목록 조회
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/news/sch_list.do")
    public ModelAndView schList(HttpServletRequest request)
    		throws ServletException, Exception
    {
		// request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        ModelAndView mv = new ModelAndView("/news/sch_list");
        
        // service call
        
        CommonDaoDTO dto = newsService.schList(_params);
        
        // response
        tabButton(mv);
        mv.addObject("_params", _params);
        mv.addObject("schList", dto.daoList);
        mv.addObject("paging", dto.paging);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/**
	 * front :: 교구일정보기
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/news/sch_view.do")
    public ModelAndView schContents(HttpServletRequest request)
    		throws ServletException, Exception
    {
		// request
        ModelAndView mv = new ModelAndView("/news/sch_view");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        // service call
        Map schContents = newsService.schContents(_params);
        
        // response
        tabButton(mv);
        mv.addObject("_params", _params);
        mv.addObject("s_idx", pnull(_params.get("s_idx")));
        mv.addObject("schContents", schContents);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }
	
	/*
	 * 교구일정 TAB 처리 및 검색조건처리
	 */
	private void tabButton(ModelAndView mv) {
		// tab button
		String gubuncd = pnull(_params, "gubuncd");
        String strCategoryName = GyoguScheduleUtil.convert2GubunText(gubuncd);
        String subTitleOn1 = "";
        String subTitleOn2 = "";
        String subTitleOn3 = "";
        String subTitleOn4 = "";
        String subTitleOn5 = "";
         
        if(gubuncd.equals("1")) { strCategoryName = "전체보기"; subTitleOn1 = " class=\"on\""; }
        else if(gubuncd.equals("2")) subTitleOn2 = " class=\"on\"";
        else if(gubuncd.equals("3")) subTitleOn3 = " class=\"on\"";
        else if(gubuncd.equals("4")) subTitleOn4 = " class=\"on\"";
        else subTitleOn5 = " class=\"on\"";
        
        mv.addObject("gubuncd", pnull(_params.get("gubuncd")));
        mv.addObject("strCategoryName", strCategoryName);
        mv.addObject("subTitleOn1", subTitleOn1);
        mv.addObject("subTitleOn2", subTitleOn2);
        mv.addObject("subTitleOn3", subTitleOn3);
        mv.addObject("subTitleOn4", subTitleOn4);
        mv.addObject("subTitleOn5", subTitleOn5);
        
        // search
        String schText = pnull(_params.get("schText"));
        String schTextGubun = pnull(_params.get("schTextGubun"));
        LinkedHashMap schTextGubunMap = new LinkedHashMap();
        schTextGubunMap.put("title",   sameCompare(_params, "schTextGubun", "title"  , "selected", ""));
        schTextGubunMap.put("content", sameCompare(_params, "schTextGubun", "content", "selected", ""));
        schTextGubunMap.put("all",     sameCompare(_params, "schTextGubun", "all"    , "selected", ""));
        
        mv.addObject("schText", schText);
        mv.addObject("schTextGubun", schTextGubun);
        mv.addObject("schTextGubunMap", schTextGubunMap);
	}

    
}
