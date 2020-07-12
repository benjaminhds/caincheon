// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GyoguIntroAdmController.java

package kr.caincheon.church.intro;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.Paging;
import kr.caincheon.church.intro.service.GyoguIntroService;

@Controller
public class GyoguIntroController extends CommonController
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="gyoguIntroService")
    private GyoguIntroService gyoguIntroService;

    /* 수도회기관단체 목록 조회 */
	@RequestMapping(value = "/intro/ordo_list.do")
    public ModelAndView organizationList(HttpServletRequest request)
    		throws ServletException, Exception
    {
		// request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        // service call
        CommonDaoDTO dto = gyoguIntroService.organizationList(_params);
        
        // response
        ModelAndView mv = new ModelAndView("/intro/ordo_list");
        Paging paging = new Paging();
        paging.setPageNo(ipnull(_params, "pageNo", 1));
        paging.setPageSize(ipnull(_params, "pageSize", 12));
        paging.setTotalCount(dto.daoTotalCount);
        
        mv.addObject("code", pnull(_params.get("code")));
        mv.addObject("searchText", pnull(_params.get("searchText")));
        mv.addObject("ordoList", dto.daoList);
        mv.addObject("paging", paging);
        mv.addObject("_params", _params);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/*
	 * front :: 기간/단체/수도회 목록조회
	 */
	@RequestMapping(value = "/intro/org_list.do")
    public ModelAndView orgList(HttpServletRequest request)
    		throws ServletException, Exception
    {
		// request
        build(request, true);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        // service call
        CommonDaoDTO dto = gyoguIntroService.organizationList(_params);
        
        // response
        ModelAndView mv = new ModelAndView("/intro/org_list");
        Paging paging = new Paging();
        paging.setPageNo(ipnull(_params, "pageNo", 1));
        paging.setPageSize(ipnull(_params, "pageSize", 20));
        paging.setTotalCount(dto.daoTotalCount);
        
        mv.addObject("_params",  _params);
        mv.addObject("LIST",     dto.daoList);
        mv.addObject("paging",   paging);
        mv.addObject("orgList",  dto.orgList);
        mv.addObject("welfareCodeList", dto.otherList);
        
        //

		String tab = pnull(_params, "t");
        if(tab.equals("1")) {
        	mv.addObject("listSubTitle", "피정의집");
            mv.addObject("subTitleOn1", "class=\"on\"");
        } else if(tab.equals("2")) {
        	mv.addObject("listSubTitle", "특수사목");
            mv.addObject("subTitleOn2", "class=\"on\"");
        } else if(tab.equals("3")) {
        	mv.addObject("listSubTitle", "단체");
            mv.addObject("subTitleOn3", "class=\"on\"");
        } else if(tab.equals("4")) {
        	mv.addObject("listSubTitle", "의료기관");
            mv.addObject("subTitleOn4", "class=\"on\"");
        } else if(tab.equals("5")) {
        	mv.addObject("listSubTitle", "교육기관");
            mv.addObject("subTitleOn5", "class=\"on\"");
        } else if(tab.equals("6")) {
        	mv.addObject("listSubTitle", "사회복지기관");
            mv.addObject("subTitleOn6", "class=\"on\"");
        } else if(tab.equals("7")) {
            mv.addObject("listSubTitle", "아동청소년재단");
            mv.addObject("subTitleOn7", "class=\"on\"");
        }
        
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }


    /**
     * 교구 소개 > 교구 관할구여 좌표 조회
     * @param request
     * @return
     */
	@RequestMapping(value = "/intro/intro_03.do")
    public ModelAndView intro_intro_03(HttpServletRequest request)
    		throws ServletException, Exception
    {
		// request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );

        
        // service call
        CommonDaoDTO dto = null;
		try {
			dto = gyoguIntroService.jurisdictionCoordinate(_params);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        // response
		_params.remove("__SESSION_MAP__");
		
        ModelAndView mv = new ModelAndView("/intro/intro_03");
        mv.addObject("LIST_TOTAL", dto.daoList);
        mv.addObject("LIST_SEARCH", dto.otherList);
        mv.addObject("GPS_CENTER", dto.otherData);
        mv.addObject("_params", _params);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	
}
