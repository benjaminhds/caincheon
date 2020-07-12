// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DgroupController.java

package kr.caincheon.church.controller;

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
import kr.caincheon.church.common.base.Paging;
import kr.caincheon.church.common.utils.UtilString;
import kr.caincheon.church.service.DgroupService;

/**
 * 지구코드관리 컨트롤러
 * TODO - 이하 component 삭제 대상
 * @author benjamin
 * @deprecated - AreaCodeMgmtController로 기능 이양
 */

@Controller
public class DgroupController extends CommonController
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="dgroupService")
    private DgroupService dgroupService;


    
    /* bjm found */
	@RequestMapping(value = "/admin/board/dgroup_list.do")
    public ModelAndView dgroupList(HttpServletRequest request)
    		throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/dgroup_list");
        build(request);
        _logger.info((new StringBuilder(" ===> [method=")).append(request.getMethod()).append("] _params ? ").append(_params.toString()).toString());
        if(UtilString.pnull(_params.get("pageNo")).equals(""))
            _params.put("pageNo", "1");
        java.util.List dgroupList = dgroupService.dgroupList(_params);
        int totalCount = dgroupService.dgroupListCount(_params);
        Paging paging = new Paging();
        paging.setPageNo(Integer.parseInt(UtilString.pnull(_params.get("pageNo"), "1")));
        paging.setPageSize(10);
        paging.setTotalCount(totalCount);
        mv.addObject("paging", paging);
        mv.addObject("dgroupList", dgroupList);
        return mv;
    }

	@RequestMapping(value = "/admin/board/dgroup_view.do")
    public ModelAndView dgroupView(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/dgroup_view");
        build(request);
        _logger.info((new StringBuilder(" ===> [inquire_view.do=")).append(request.getMethod()).append("] _params ? ").append(_params.toString()).toString());
        Map dgroupContents = dgroupService.dgroupContents(_params);
        mv.addObject("_params", _params);
        mv.addObject("dgroupContents", dgroupContents);
        return mv;
    }

	@RequestMapping(value = "/admin/board/dgroup_insert.do")
    public void dgroupInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/dgroup_list");
        build(request);
        _logger.info((new StringBuilder(" ===> [method=")).append(request.getMethod()).append("] _params ? ").append(_params.toString()).toString());
        if(UtilString.pnull(_params.get("pageNo")).equals(""))
            _params.put("pageNo", "1");
        boolean success = dgroupService.dgroupInsert(_params);
        if(success)
            response.sendRedirect("/admin/board/dgroup_list.do");
    }

	@RequestMapping(value = "/admin/board/dgroup_modify.do")
    public void dgroupModify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/dgroup_list");
        build(request);
        _logger.info((new StringBuilder(" ===> [method=")).append(request.getMethod()).append("] _params ? ").append(_params.toString()).toString());
        if(UtilString.pnull(_params.get("pageNo")).equals(""))
            _params.put("pageNo", "1");
        boolean success = dgroupService.dgroupModify(_params);
        if(success)
            response.sendRedirect("/admin/board/dgroup_list.do");
    }

	@RequestMapping(value = "/admin/board/dgroup_delete.do")
    public void inqDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/dgroup_list");
        build(request);
        _logger.info((new StringBuilder(" ===> [method=")).append(request.getMethod()).append("] _params ? ").append(_params.toString()).toString());
        if(UtilString.pnull(_params.get("pageNo")).equals(""))
            _params.put("pageNo", "1");
        boolean success = dgroupService.dgroupDelete(_params);
        if(success)
            response.sendRedirect("/admin/board/dgroup_list.do");
    }

    
}
