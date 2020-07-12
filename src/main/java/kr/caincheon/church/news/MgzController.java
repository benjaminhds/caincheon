// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MgzController.java

package kr.caincheon.church.news;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.Paging;
import kr.caincheon.church.news.service.MgzService;

@Controller
public class MgzController extends CommonController
{

    private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="mgzService")
    private MgzService mgzService;

	private void selectMgzList(Map _params, ModelAndView mv) throws Exception {
		
		java.util.List mgzList = mgzService.mgzList(_params);
        int totalCount = mgzService.mgzListCount(_params);
        
        Paging paging = new Paging();
        paging.setPageNo(ipnull(_params, "pageNo", 1));
        paging.setPageSize(ipnull(_params, "pageSize", 12));
        paging.setTotalCount(totalCount);
        
        mv.addObject("_params", _params);
        mv.addObject("mgzList",  mgzList);
        mv.addObject("paging",   paging);
	}
	
    /* bjm found */
	@RequestMapping(value = "/news/mgz_list.do")
    public ModelAndView mgzList(HttpServletRequest request) throws Exception 
    {
        ModelAndView mv = new ModelAndView("/news/publication");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );

        selectMgzList(_params, mv);
        mv.addObject("pub_idx", pnull(_params.get("pub_idx")));
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/news/mgz_view.do")
    public ModelAndView mgzContents(HttpServletRequest request) throws Exception 
    {
        ModelAndView mv = new ModelAndView("/news/mgz_view");
        build(request);
        String strCategoryName = "";
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        Map mgzContents = mgzService.mgzContents(_params);
        mv.addObject("_params", _params);
        mv.addObject("mgzContents", mgzContents);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/mgz_list.do")
    public ModelAndView admMgzList(HttpServletRequest request) throws Exception 
    {
        ModelAndView mv = new ModelAndView("/admin/board/mgz_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        _params.put("pub_idx", "3");

        selectMgzList(_params, mv);
        
        mv.addObject("pub_idx", pnull(_params.get("pub_idx")));
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/mgz_view.do")
    public ModelAndView admMgzContents(HttpServletRequest request) throws Exception 
    {
        ModelAndView mv = new ModelAndView("/admin/board/mgz_view");
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        Map    mgzContents = mgzService.mgzContents(_params);
        String max_no = mgzService.mgzMaxNo(_params);
        
        mv.addObject("_params", _params);
        mv.addObject("mgzContents", mgzContents);
        mv.addObject("max_no", max_no);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/*
	 * Restfull Response
	 */
	@RequestMapping(value = "/admin/board/mgz_title.do")
    public @ResponseBody Map<String , Object> admMgzTitle(HttpServletRequest request) throws Exception 
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        String mgzTitle = mgzService.mgzTitle(_params);
        
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("result", mgzTitle);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response String (by JSON) >> \n\t\t"+json  );
        
        return json;
    }

	@RequestMapping(value = "/admin/board/mgz_insert.do")
    public ModelAndView mgzInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, Exception 
    {
        ModelAndView mv = new ModelAndView("/admin/board/mgz_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );

        mgzService.mgzInsert(_params);
        
        selectMgzList(_params, mv);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response String (by Restful) >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/mgz_modify.do")
    public ModelAndView mgzModify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/mgz_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );

        mgzService.mgzModify(_params);

        selectMgzList(_params, mv);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response String (by JSON) >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/mgz_delete.do")
    public ModelAndView inqDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/mgz_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        mgzService.mgzDelete(_params);

        selectMgzList(_params, mv);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response String (by JSON) >> \n\t\t"+mv  );
        
        return mv;
    }

}
