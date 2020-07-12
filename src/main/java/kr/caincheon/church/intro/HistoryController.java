// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HistoryController.java

package kr.caincheon.church.intro;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
import kr.caincheon.church.common.base.Paging;
import kr.caincheon.church.intro.service.HistoryService;

@Controller
public class HistoryController extends CommonController implements HistoryControllerConst
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="historyService")
    private HistoryService historyService;

	
	// ==================== for front page
	
    
	@RequestMapping(value = "/intro/history.do")
    public ModelAndView introOnlineHistoryMain(HttpServletRequest request) throws Exception
    {
        ModelAndView mv = new ModelAndView("/intro/history");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        
        // 
        CommonDaoDTO dto = historyService.categoryFullList(_params);

        // 
        mv.addObject("_params", _params);
        mv.addObject("categoryList",      dto.daoList);//list
        mv.addObject("historyYears",      dto.otherData);//map{categorycode,list[years]}
        mv.addObject("historyEventsList", dto.otherData1);//map{categorycode_year, list[map]}
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }
	
	/*
	 * 카테고리를 선택하면 상세 이벤트 목록들과 내용을 불러오기
	 */
	@RequestMapping(value = "/intro/history_events_per_category.do")
    public Map<String, Object> introHistoryEventsPerCategory(HttpServletRequest request) throws Exception
    {
        ModelAndView mv = new ModelAndView("/intro/history");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        // return
        Map<String, Object> eventsMap = new HashMap<String, Object>();

        // 
        List historyYearList = historyService.historyYearList(_params, pnull(_params, "category_code"));
        // front에서 연혁 출력을 위한 로직
        _params.put("TMP_LIST", historyYearList);
        LinkedHashMap<String, List> historyMap = (LinkedHashMap<String, List>)historyService.historyEventsList(_params, false);
        
        eventsMap.put("_params", _params);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return eventsMap;
    }

	// ==================== for admin page
	
	@RequestMapping(value = "/admin/board/history_list.do")
    public ModelAndView admHistoryList(HttpServletRequest request) throws Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/history_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        int totalCount = 0;
        List historyList = null;
        List categoryList = null;
        String active = pnull(_params.get("active"));
        
        if(active.equals("tab2")) { /* 카테고리별 연혁관리 */
            categoryList = historyService.categoryList(_params);
            historyList  = (List)historyService.historyEventsList(_params, true);
            totalCount   = historyService.historyEventsListCount(_params);
        } else { /* 역사관카테고리 */
            historyList = historyService.historymList(_params);
            totalCount  = historyService.historymListCount(_params);
        }
        
        Paging paging = new Paging();
        paging.setPageNo(ipnull(_params, "pageNo", 1));
        paging.setPageSize(ipnull(_params, "pageSize", 20));
        paging.setTotalCount(totalCount);
        
        mv.addObject("_params", _params);
        mv.addObject("daoTotalCount", Integer.valueOf(totalCount));
        mv.addObject("paging", paging);
        mv.addObject("categoryList", categoryList);
        mv.addObject("historyList", historyList);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/historym_view.do")
    public ModelAndView admHistoryMasterView(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/historym_view");
        build(request);
        
        List categoryList = historyService.categoryList(_params);
        Map historyContents = historyService.historymContents(_params);
        mv.addObject("_params", _params);
        //mv.addObject("categoryList", categoryList);
        mv.addObject("historyContents", historyContents);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/historym_insert.do")
    public ModelAndView admHistoryMasterInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, Exception
    {
        //ModelAndView mv = new ModelAndView("/admin/board/history_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        

        historyService.historymInsert(_params);
        
        return admHistoryList(request);//response.sendRedirect("/admin/board/history_list.do");
    }

	@RequestMapping(value = "/admin/board/historym_modify.do")
    public ModelAndView admHistoryMasterModify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, Exception
    {
        //ModelAndView mv = new ModelAndView("/admin/board/history_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        pnullPut(_params, "WWWROOT", request.getSession().getServletContext().getRealPath("/"));

        historyService.historymModify(_params);
        return admHistoryList(request);//response.sendRedirect("/admin/board/history_list.do");
    }

	@RequestMapping(value = "/admin/board/historym_delete.do")
    public ModelAndView admHistoryMasterDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, Exception
    {
        //ModelAndView mv = new ModelAndView("/admin/board/history_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        pnullPut(_params, "WWWROOT", request.getSession().getServletContext().getRealPath("/"));

        historyService.historymDelete(_params);
        return admHistoryList(request);//response.sendRedirect("/admin/board/history_list.do");
    }

	@RequestMapping(value = "/admin/board/historys_view.do")
    public ModelAndView admHistorySlaveView(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/historys_view");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        List categoryList = historyService.categoryList(_params);
        Map historyContents = historyService.historysContents(_params);
        mv.addObject("_params", _params);
        mv.addObject("categoryList", categoryList);
        mv.addObject("historyContents", historyContents);

        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/historys_insert.do")
    public void admHistorySlaveInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, Exception
    {
        //ModelAndView mv = new ModelAndView("/admin/board/history_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        
        historyService.historysInsert(_params);
        response.sendRedirect("/admin/board/history_list.do?category_code=ALL&active=tab2");
    }

	@RequestMapping(value = "/admin/board/historys_modify.do")
    public void admHistorySlaveModify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, Exception
    {
        //ModelAndView mv = new ModelAndView("/admin/board/history_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        pnullPut(_params, "WWWROOT", request.getSession().getServletContext().getRealPath("/"));

        historyService.historysModify(_params);
        response.sendRedirect("/admin/board/history_list.do?category_code=ALL&active=tab2");
    }

	@RequestMapping(value = "/admin/board/historys_delete.do")
    public void admHistorySlaveDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, Exception
    {
        //ModelAndView mv = new ModelAndView("/admin/board/history_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        pnullPut(_params, "WWWROOT", request.getSession().getServletContext().getRealPath("/"));

        historyService.historysDelete(_params);
        response.sendRedirect("/admin/board/history_list.do?category_code=ALL&active=tab2");
    }

}
