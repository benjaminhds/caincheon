// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GyoguIntroAdmController.java

package kr.caincheon.church.intro;

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
import kr.caincheon.church.intro.service.GyoguIntroService;

@Controller
public class GyoguIntroAdmController extends CommonController
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="gyoguIntroService")
    private GyoguIntroService gyoguIntroService;



	/*
	 * 관리자 :: 기관/단체/수도회 목록조회
	 */
	@RequestMapping(value = "/admin/board/org_list.do")
    public ModelAndView admOrgList(HttpServletRequest request)
    		throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/org_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        String searchText = pnull(_params.get("searchText"));
        String searchGubun = pnull(_params.get("searchGubun"));

        
        CommonDaoDTO dto = gyoguIntroService.admOrganizationList(_params);
        
        mv.addObject("searchGubun", searchGubun);
        mv.addObject("searchText", searchText);
        mv.addObject("orgList", dto.daoList);
        mv.addObject("paging", dto.paging);

        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/*
	 * 관리자 :: 기관/단체/수도회 정보 조회하기
	 */
	@RequestMapping(value = "/admin/board/org_view.do")
    public ModelAndView admOrgView(HttpServletRequest request)
    		throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/org_view");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        // 기관/단체/수도회 정보
        Map orgContents = gyoguIntroService.admOrganizationView(_params);
        // 사제목록 & 세목코드목록
        CommonDaoDTO dto = gyoguIntroService.adm_priest_list(_params);
        
        
        mv.addObject("_params", _params);
        mv.addObject("orgContents", orgContents);
        mv.addObject("priestList", dto.daoList);
        mv.addObject("welfareCodeList", dto.otherList);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/*
	 * 관리자 :: 기관/단체/수도회  신규등록하기
	 */
	@RequestMapping(value = "/admin/board/org_insert.do")
    public void admOrgInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
		// request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );

        // service call
        
        boolean success = gyoguIntroService.organizationInsert(_params);

        // response
        if(success)
            response.sendRedirect("/admin/board/org_list.do");
    }

	/*
	 * 관리자 :: 기관/단체/수도회 정보 수정하기
	 */
	@RequestMapping(value = "/admin/board/org_modify.do")
    public void admOrgModify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
		// request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );

        // service call
        
        boolean success = gyoguIntroService.organizationModify(_params);
        
        // response
        if(success)
            response.sendRedirect("/admin/board/org_list.do");
    }

	/*
	 * 관리자 :: 기관/단체/수도회 삭제하기
	 */
	@RequestMapping(value = "/admin/board/org_delete.do")
    public void admOrgDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
		// request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );

        // service call
        
        boolean success = gyoguIntroService.organizationDelete(_params);

        // response
        if(success)
            response.sendRedirect("/admin/board/org_list.do");
    }

    
}
