package kr.caincheon.church.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.admin.PopupControllerConst;
import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.Paging;
import kr.caincheon.church.service.PopupService;

@Controller
public class PopupController extends CommonController implements PopupControllerConst
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	//@Resource(name="popupService")
	@Autowired
    private PopupService popupService;

    /* bjm found */
	@RequestMapping(value = "/admin/board/popup_list.do")
    public ModelAndView popupList(HttpServletRequest request)
    		throws ServletException, Exception
    {
		// request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/admin/board/popup_list");
        
        
        
        // service call
        CommonDaoDTO dto = popupService.admPopupList(_params);
        //int totalCount = popupService.admPopupListCount(_params);
        Paging paging = new Paging();
        paging.setPageNo(ipnull(_params, "pageNo", 1));
        paging.setPageSize(ipnull(_params, "pageSize", 20));
        paging.setTotalCount(dto.daoTotalCount);
        
        // response
        String schTextGubun = pnull(_params.get("searchGubun"));
        String searchText   = pnull(_params.get("searchText"));
        LinkedHashMap searchGubunMap = new LinkedHashMap();
        searchGubunMap.put("title"  , "");
        searchGubunMap.put("content", "");
        searchGubunMap.put("name"   , "");
        searchGubunMap.put(schTextGubun, "selected");
        
        mv.addObject("schTextGubun", schTextGubun);
        mv.addObject("searchText", searchText);
        mv.addObject("searchGubunMap", searchGubunMap);
        mv.addObject("_params", _params);
        mv.addObject("popupList", dto.daoList);
        mv.addObject("paging", paging);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/popup_view.do")
    public ModelAndView admInqView(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
		// request
        build(request);
        ModelAndView mv = new ModelAndView("/admin/board/popup_view");
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        // service call
        Map popup = popupService.admPopupContents(_params);
        
        
        // response
        mv.addObject("query_type", "update");
        mv.addObject("_params", _params);
        mv.addObject("popup", popup);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/popup_insert.do")
    public void popupInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/admin/board/popup_list");
        
        // service call
        
        
        boolean success = popupService.admPopupInsert(_params);
        
        // reponse
        if(success)
            response.sendRedirect("/admin/board/popup_list.do");
    }

	@RequestMapping(value = "/admin/board/popup_modify.do")
    public void popupModify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/admin/board/popup_list");

        // service call
        
        
        boolean success = popupService.admPopupModify(_params);
        
        // response
        if(success)
            response.sendRedirect("/admin/board/popup_list.do");
    }

	@RequestMapping(value = "/admin/board/popup_delete.do")
    public void inqDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/admin/board/popup_list");
        
        // service call
        
        
        boolean success = popupService.admPopupDelete(_params);
        
        // response
        if(success)
            response.sendRedirect("/admin/board/popup_list.do");
    }

    
}
