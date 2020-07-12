// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CureController.java

package kr.caincheon.church.samok;

import java.util.LinkedHashMap;
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
import kr.caincheon.church.common.base.Paging;
import kr.caincheon.church.samok.service.CureService;

@Controller
public class CureController extends CommonController implements CureControllerConst
{

    private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="cureService")
    private CureService cureService;

	@Resource(name="nBoardService")
    private NBoardServiceImpl nBoardService;
	
	/*
	 * 목록을 조회한다.
	 */
	private void commonCureList(ModelAndView mv, Map _params) throws Exception {
		// call a service
		CommonDaoDTO dto = cureService.cureList(_params, LEFT_MENU_DATA_PG);
		// response
		Paging paging = new Paging();
        paging.setPageNo(ipnull(_params, "pageNo"));
        paging.setPageSize(ipnull(_params, "pageSize"));
        paging.setTotalCount(dto.daoTotalCount);
        
        mv.addObject("cureList", dto.daoList);
        mv.addObject("paging", paging);
        
        setResponseAttributes( mv, _params) ;
	}
	
    /** front :: 사목자료 목록 */
	@RequestMapping(value = "/samok/cure_list.do")
	public ModelAndView cureList(HttpServletRequest request) throws Exception
	{
        // rquest
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        _params.put("b_idx",   "14");
        
        pnullPut(_params, "c_idx", "ALL");
        
        ModelAndView mv = new ModelAndView("/samok/cure_list");
        
        // service call
        commonCureList(mv, _params);
        
        // response
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/** front :: 사목자료 게시물 보기 */
	@RequestMapping(value = "/samok/cure_view.do")
    public ModelAndView cureContents(HttpServletRequest request) throws Exception
    {
		// request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        //pchange(_params, "b_idx", "c_idx");
        
        pnullPut(_params, "c_idx", "ALL");
        _params.put("b_idx",   "14");
        
        // service call
        CommonDaoDTO dto = cureService.cureContents(_params, LEFT_MENU_DATA_PG);
        
        // response
        ModelAndView mv = new ModelAndView("/samok/cure_view");
        setResponseAttributes( mv, _params ) ;
        
        mv.addObject("CONTENTS", dto.daoDetailContent);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }


	/** front :: 사목자료 게시물 수정 */
	@RequestMapping(value = "/samok/cure_view_update.do")
    public ModelAndView cureViewUpdate(HttpServletRequest request) throws Exception
    {
		// request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        pnullPut(_params, "c_idx", "ALL");
        _params.put("b_idx",   "14");
        
        // service call
        boolean isOk = nBoardService.nboardModify(_params, LEFT_MENU_DATA_PG, request);
        
        // list service call && response
        ModelAndView mv = new ModelAndView("/samok/cure_list");
        commonCureList(mv, _params);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );

        return mv;
    }
	
	/** front :: 사목자료 게시물 신규등록 */
	@RequestMapping(value = "/samok/cure_view_insert.do")
    public ModelAndView cureViewInsert(HttpServletRequest request) throws Exception
    {
		// request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        pnullPut(_params, "c_idx", "ALL");
        _params.put("b_idx",   "14");
        
        // service call
        boolean isOk = nBoardService.nboardInsert(_params, LEFT_MENU_DATA_PG, request); //(_params, LEFT_MENU_DATA_PG);

        // list service call && response
        ModelAndView mv = new ModelAndView("/samok/cure_list");
        commonCureList(mv, _params);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );

        return mv;
    }

	/** 응답 파라메터를 설정 */
	private  void  setResponseAttributes(ModelAndView mv, Map _params) {

        String schTextGubun = pnull(_params.get("schTextGubun"));
        String schText      = pnull(_params.get("schText"));
        String c_idx        = pnull(_params.get("c_idx"), "ALL");
        
        LinkedHashMap schTextGubunMap = new LinkedHashMap();
        schTextGubunMap.put("title",   pnull(_params, "title").length()   > 0 ? "selected":"");
        schTextGubunMap.put("content", pnull(_params, "content").length() > 0 ? "selected":"");
        schTextGubunMap.put("all",     pnull(_params, "all").length()     > 0 ? "selected":"");
        schTextGubunMap.put("writer",  pnull(_params, "writer").length()  > 0 ? "selected":"");
        
        String strCategoryName = "";
        String subTitleOn1 = "";
        String subTitleOn2 = "";
        String subTitleOn3 = "";
        String subTitleOn4 = "";
        String subTitleOn5 = "";
        String subTitleOn6 = "";
        String subTitleOn7 = "";
        
        if("ALL".equalsIgnoreCase(c_idx)) {
        	subTitleOn1 = " class=\"on\""; strCategoryName = "전체보기";
        } else {
        	int cidx = ipnull(_params, "c_idx", 0);
        	switch(cidx) {
        	case 5:
        		subTitleOn2 = " class=\"on\""; strCategoryName="전례";
        		break;
        	case 6:
        		subTitleOn3 = " class=\"on\""; strCategoryName="양식";
        		break;
        	case 7:
        		subTitleOn4 = " class=\"on\""; strCategoryName="교리";
        		break;
        	case 8:
        		subTitleOn5 = " class=\"on\""; strCategoryName="사목";
        		break;
        	case 9:
        		subTitleOn6 = " class=\"on\""; strCategoryName="선교";
        		break;
        	case 10:
        		subTitleOn7 = " class=\"on\""; strCategoryName="기타";
        		break;
    		default:
    			subTitleOn1 = " class=\"on\""; strCategoryName = "전체보기";
    			break;
        	}
        }
        
        //mv.addObject("b_idx",      pnull(_params.get("b_idx")));
        mv.addObject("c_idx",      pnull(_params.get("c_idx")));
        mv.addObject("bl_idx",     pnull(_params.get("bl_idx")));
        mv.addObject("strCategoryName", strCategoryName);
        mv.addObject("subTitleOn1", subTitleOn1);
        mv.addObject("subTitleOn2", subTitleOn2);
        mv.addObject("subTitleOn3", subTitleOn3);
        mv.addObject("subTitleOn4", subTitleOn4);
        mv.addObject("subTitleOn5", subTitleOn5);
        mv.addObject("subTitleOn6", subTitleOn6);
        //mv.addObject("subTitleOn7", subTitleOn7);
        mv.addObject("schTextGubun", schTextGubun);
        mv.addObject("schText",         schText);
        mv.addObject("schTextGubunMap", schTextGubunMap);
        mv.addObject("_params", _params);
	}
}
