// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PriestController.java

package kr.caincheon.church.father;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.admin.serivce.CodeService;
import kr.caincheon.church.admin.serivce.OrgHierarchyService;
import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.Const;
import kr.caincheon.church.father.service.PriestServiceImpl;
import kr.caincheon.church.service.CommonUtilService;

@Controller
public class PriestController extends CommonController
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="priestService")
    private PriestServiceImpl priestService;
	
	@Resource(name="commonUtilService")
    private CommonUtilService commonUtilService;


    @Resource(name="orgHierarchyService")
    private OrgHierarchyService orgHierarchyService;
	

    @Resource(name="codeService")
    private CodeService codeService;
	
    
    
    
    /**
     * 관리자 > 사제목록
     */
	@RequestMapping(value = "/father/father_list.do")
    public ModelAndView priestList(HttpServletRequest request) throws Exception
    {
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/father/father_list");
        
        // service call
        
        CommonDaoDTO dto = priestService.priestList(_params);
        
        
        // response
        String sortGubun    = pnull(_params.get("sortGubun"));
        String schTextGubun = pnull(_params.get("schTextGubun"));
        
        LinkedHashMap sortGubunMap = new LinkedHashMap();
        if(sortGubun.indexOf("onum")>-1)           sortGubunMap.put("onum", "selected"); else sortGubunMap.put("onum", "");
        if(sortGubun.indexOf("p_birthday")>-1)     sortGubunMap.put("p_birthday", "selected"); else sortGubunMap.put("p_birthday", "");
        if(sortGubun.indexOf("new_birthday")>-1)   sortGubunMap.put("new_birthday", "selected"); else sortGubunMap.put("new_birthday", "");
        if(sortGubun.indexOf("org_idx")>-1)        sortGubunMap.put("org_idx", "selected"); else sortGubunMap.put("org_idx", "");
        if(sortGubun.indexOf("name")>-1)           sortGubunMap.put("name", "selected"); else sortGubunMap.put("name", "");
        if(sortGubun.indexOf("christian_name")>-1) sortGubunMap.put("christian_name", "selected"); else sortGubunMap.put("christian_name", "");
        
        LinkedHashMap schTextGubunMap = new LinkedHashMap();
        if(schTextGubun.indexOf("name")>-1)           schTextGubunMap.put("name", "selected"); else schTextGubunMap.put("name", "");
        if(schTextGubun.indexOf("christian_name")>-1) schTextGubunMap.put("christian_name", "selected"); else schTextGubunMap.put("christian_name", "");
        if(schTextGubun.indexOf("org_idx")>-1)        schTextGubunMap.put("org_idx", "selected"); else schTextGubunMap.put("org_idx", "");
        
        
        mv.addObject("sortGubunMap", sortGubunMap);
        mv.addObject("schTextGubunMap", schTextGubunMap);
        mv.addObject("priestList", dto.daoList);
        mv.addObject("paging", dto.paging);
        mv.addObject("_params", _params);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/*
	 * 프론트에서 사제 상세보기 OP
	 */
	@RequestMapping(value = "/father/father_view.do")
    public ModelAndView priestContents(HttpServletRequest request) throws Exception
    {
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/father/father_view");
        
        // service call
        CommonDaoDTO dto = priestService.priestContents(_params);
        
        // response
        mv.addObject("_params", _params);
        mv.addObject("priestContents",   dto.daoDetailContent); //사제정보
        mv.addObject("orgPriestRelList", dto.daoList); //현재발령목록
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/*
	 * 사제 상세에서, 사제에게 메일 보내기 웹메일..
	 */
	@RequestMapping(value = "/father/father_mailSend.do")
    public @ResponseBody Map<String,Object> priestMailSend(HttpServletRequest request) throws Exception
    {
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        // service call
        boolean success = priestService.priestMailSend(_params);
        
        // response
//        if(success)
//        {
//            mv = new ModelAndView("/father/father_mailOk");
//            mv.addObject("RESULT_YN", "Y");
//        } else {
//            mv = new ModelAndView("/father/father_mail");
//            mv.addObject("RESULT_YN", "N");
//        }
//        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        Map json = getJson(success, "소식 전하기 메일을 발송했습니다.");
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t"+json  );
        
        return json;
    }

	/*
	 * 선종사제 목록
	 */
	@RequestMapping(value = "/father/holy_list.do")
    public ModelAndView holyList(HttpServletRequest request) throws Exception
    {
        
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/father/holy_list");
        
        
        
        String searchGubun = "";
        String month = "";
        String j_name = "";
        searchGubun = pnull(_params.get("searchGubun"));
        month = pnull(_params.get("month"));
        j_name = pnull(_params.get("j_name"));
        LinkedHashMap searchGubunMap = new LinkedHashMap();
        if(searchGubun.equals("month"))
            searchGubunMap.put("month", "selected");
        else
            searchGubunMap.put("month", "");
        if(searchGubun.equals("j_name"))
            searchGubunMap.put("j_name", "selected");
        else
            searchGubunMap.put("j_name", "");
        LinkedHashMap monthMap = new LinkedHashMap();
        if(month.equals("1"))
            monthMap.put("1", "selected");
        else
            monthMap.put("1", "");
        if(month.equals("2"))
            monthMap.put("2", "selected");
        else
            monthMap.put("2", "");
        if(month.equals("3"))
            monthMap.put("3", "selected");
        else
            monthMap.put("3", "");
        if(month.equals("4"))
            monthMap.put("4", "selected");
        else
            monthMap.put("4", "");
        if(month.equals("5"))
            monthMap.put("5", "selected");
        else
            monthMap.put("5", "");
        if(month.equals("6"))
            monthMap.put("6", "selected");
        else
            monthMap.put("6", "");
        if(month.equals("7"))
            monthMap.put("7", "selected");
        else
            monthMap.put("7", "");
        if(month.equals("8"))
            monthMap.put("8", "selected");
        else
            monthMap.put("8", "");
        if(month.equals("9"))
            monthMap.put("9", "selected");
        else
            monthMap.put("9", "");
        if(month.equals("10"))
            monthMap.put("10", "selected");
        else
            monthMap.put("10", "");
        if(month.equals("11"))
            monthMap.put("11", "selected");
        else
            monthMap.put("11", "");
        if(month.equals("12"))
            monthMap.put("12", "selected");
        else
            monthMap.put("12", "");
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        

        // service call
        CommonDaoDTO dto = priestService.holyList(_params);
        
        // response
        mv.addObject("searchGubunMap", searchGubunMap);
        mv.addObject("monthMap", monthMap);
        mv.addObject("holyList", dto.daoList);
        mv.addObject("paging", dto.paging);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/*
	 * 프론트 > 사제 상세보기 
	 */
	@RequestMapping(value = "/father/holy_view.do")
    public ModelAndView holyContents(HttpServletRequest request) throws Exception
    {
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/father/holy_view");
        
        Map holyContents = priestService.holyContents(_params);
        mv.addObject("_params", _params);
        mv.addObject("holyContents", holyContents);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/**
	 * 관리자 > 선종사제 상세보기
	 */
	@RequestMapping(value = "/admin/board/bef_priest_view.do")
    public ModelAndView befPriestView(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, Exception
    {
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/admin/board/bef_priest_view");

        // service call
        CommonDaoDTO dto = priestService.befPriestContents(_params);
        
        // response
        mv.addObject("_params", _params);
        mv.addObject("befPriestContents", dto.daoDetailContent);
        mv.addObject("brialPlaceList", dto.otherData);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/**
	 * 관리자 > 선종사제 등록
	 */
	@RequestMapping(value = "/admin/board/bef_priest_insert.do")
    public void befPriestInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, Exception
    {
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        boolean success = priestService.befPriestInsert(_params);
        if(success)
            response.sendRedirect("/admin/board/bef_priest_list.do?pageNo=1");
    }

	/**
	 * 관리자 > 선종사제 수정
	 */
	@RequestMapping(value = "/admin/board/bef_priest_modify.do")
    public void befPriestModify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException , Exception
    {
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        boolean success = priestService.befPriestModify(_params);
        if(success)
            response.sendRedirect("/admin/board/bef_priest_list.do?pageNo="+pnull(_params, "pageNo"));
    }

	/**
	 * 관리자 > 선종사제 삭제
	 */
	@RequestMapping(value = "/admin/board/bef_priest_delete.do")
    public void befPriestDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, Exception
    {
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        boolean success = priestService.befPriestDelete(_params);
        if(success)
            response.sendRedirect("/admin/board/bef_priest_list.do?pageNo=1");
    }
	
	// 선종사제 묘소 추가 등록
	@RequestMapping(value = "/admin/board/tomb_insert.do")
    public void befPriestTombInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException , Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/brial_insert");
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        boolean success = priestService.befPriestTombInsert(_params);
        if(success)
            response.sendRedirect("/admin/board/bef_priest_list.do");
    }

	// 선종사제 묘소 삭제
	@RequestMapping(value = "/admin/board/tomb_delete.do")
    public void befPriestTombDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException , Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/brial_delete");
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        boolean success = priestService.befPriestTombDelete(_params);
        
        if(success)
            response.sendRedirect("/admin/board/bef_priest_list.do");
    }

	/*
	 * 관리자 > 선종사제 목록
	 */
	@RequestMapping(value = "/admin/board/bef_priest_list.do")
	public ModelAndView admBefPriestList(HttpServletRequest request) throws Exception
	{
	    // request
	    build(request);
	    D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
	    ModelAndView mv = new ModelAndView("/admin/board/bef_priest_list");
	    
	    
	    // service call
	    CommonDaoDTO dto = priestService.befPriestList(_params);
	    
	    // response
	    mv.addObject("_params", _params);
	    mv.addObject("paging", dto.paging);
	    mv.addObject("befPriestList", dto.daoList);
	    mv.addObject("brialPlaceList", dto.otherData);
	    
	    mv.addObject("searchGubun", pnull(_params.get("searchGubun")));
	    mv.addObject("searchText", pnull(_params.get("searchText")));
	    
	    D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
	    
	    return mv;
	}

	/*
	 * (관리자)
	 * 사제 목록조회
	 */
	@RequestMapping(value = "/admin/board/priest_list.do")
    public ModelAndView admPriestList(HttpServletRequest request) 
    		throws ServletException, Exception
    {
        // request
        build(request);
        ModelAndView mv = new ModelAndView("/admin/board/priest_list");
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        _params.put("pageSize", "20");
        
        // service call
        try {
			CommonDaoDTO dto = priestService.admPriestList(_params);
			
			// response
			mv.addObject("paging", dto.paging);
			mv.addObject("admPriestList", dto.daoList);
			
			mv.addObject("sortGubun",   pnull(_params.get("sortGubun")));
			mv.addObject("searchGubun", pnull(_params.get("searchGubun")));
			mv.addObject("searchText",  pnull(_params.get("searchText")));
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("CONTROLLER_ERROR", e.getStackTrace()[0].toString()+": "+e.getMessage());
		}
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/*
	 * 관리자) 사제 상세보기
	 */
	@RequestMapping(value = "/admin/board/priest_view.do")
    public ModelAndView admPriestView(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException , Exception
    {
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/admin/board/priest_view");
        
        
        // service call
        Map admPriestContents   = null;
        List admPriestDCodeList = null;
        List admOrgPriestRel    = null;
        try {
	        admPriestContents  = priestService.admPriestContents(_params);
	        admPriestDCodeList = priestService.admPriestDCodeList(_params);
	        admOrgPriestRel    = priestService.selectOrgDepartmentPriestRel(_params);//발령정보
	        
	        // code select
	        CommonDaoDTO dto1 = null, dto2 = null;
	        Map codeMap = new HashMap();
	        codeMap.put("code", "000002");//
	        dto2 = codeService.listCodeInstances(codeMap);
	        codeMap.put("code", "000001");//
	        dto1 = codeService.listCodeInstances(codeMap);
        
        	// 조직 LV1 별, LV2 별 조회
        	orgHierarchyService.selectOrgHierarchyGroupby(_params);
        	
            mv.addObject("DEPART_LIST1", _params.remove(Const.ADM_MAPKEY_GROUPLIST+"1"));
            mv.addObject("DEPART_LIST2", _params.remove(Const.ADM_MAPKEY_GROUPLIST+"2"));
            mv.addObject("DEPART_LIST3", commonUtilService.getDepartList("3"));
            mv.addObject("POS_LIST", commonUtilService.getPosList(""));
            mv.addObject("admCodeList1", dto1.daoList);
            mv.addObject("admCodeList2", dto2.daoList);
            
        } catch(Exception e) { 
        	e.printStackTrace(); 
        }
        
        // response
        mv.addObject("_params", _params);
        mv.addObject("admOrgPriestRel", admOrgPriestRel);
        mv.addObject("admPriestContents", admPriestContents);
        mv.addObject("admPriestDCodeList", admPriestDCodeList);
        
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/*
	 * 사제 등록
	 */
	@RequestMapping(value = "/admin/board/priest_insert.do")
    public void admPriestInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException , Exception
    {
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/admin/board/priest_list");
        
        
        boolean success = priestService.admPriestInsert(_params);
        if(success)
            response.sendRedirect("/admin/board/priest_list.do");
    }

	/*
	 * 관리자에서 사제 정보 수정하기
	 */
	@RequestMapping(value = "/admin/board/priest_modify.do")
    public void admPriestModify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException , Exception
    {
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/admin/board/priest_list");
        
        
        boolean success = priestService.admPriestModify(_params);
        if(success)
            response.sendRedirect("/admin/board/priest_list.do");
    }

	/*
	 * 사제 삭제
	 */
	@RequestMapping(value = "/admin/board/priest_delete.do")
    public void admPriestDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException , Exception
    {
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/admin/board/priest_list");
        
        
        boolean success = priestService.admPriestDelete(_params);
        if(success)
            response.sendRedirect("/admin/board/priest_list.do");
    }

	@RequestMapping(value = "/admin/board/priest_departDelete.do")
    public ModelAndView admPriestDcodeDelete(HttpServletRequest request)
        throws ServletException, IOException , Exception
    {
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = null;
        
        
        boolean success = priestService.admPriestDepartDelete(_params);
        if(success)
        {
            mv = new ModelAndView("/admin/board/priest_view");
            Map admPriestContents = priestService.admPriestContents(_params);
            List admPriestDCodeList = priestService.admPriestDCodeList(_params);
            try
            {
                mv.addObject("DEPART_LIST1", commonUtilService.getDepartList("1"));
                mv.addObject("DEPART_LIST2", commonUtilService.getDepartList("2"));
                mv.addObject("DEPART_LIST3", commonUtilService.getDepartList("3"));
                mv.addObject("POS_LIST", commonUtilService.getPosList("1"));
            }
            catch(Exception exception) { }
            mv.addObject("_params", _params);
            mv.addObject("admPriestContents", admPriestContents);
            mv.addObject("admPriestDCodeList", admPriestDCodeList);
        } else
        {
            mv = new ModelAndView("/admin/board/priest_view");
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	
	/*
	 * 사제 임지 발령 생성/업데이트
	 */
	@RequestMapping(value = "/admin/board/priest_assing_upsert.do")
    public @ResponseBody Map<String,Object> upsertPreistAssign(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException , Exception
    {
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
		
		// service call
        CommonDaoDTO dto = null;
        String error = "";
        try {
        	dto = priestService.upsertPreistAssign(_params);
		} catch (Exception e) {
			error = e.getMessage();
			e.printStackTrace();
		}
        
    	// response
        Map rtMap = new HashMap();
        rtMap.put("idx", dto.otherData.toString());
        rtMap.put("status", "error");
        rtMap.put("message", "실패되었습니다.["+error+"]");
    	if(dto.isBool) {
    		rtMap.put("status", "normal");
    		rtMap.put("message", "발령정보가 반영 되었습니다.");
    	}
		
    	D(_logger, Thread.currentThread().getStackTrace(), "Response Controller >> \n\t\t"+rtMap  );
    	
		return rtMap;
    }
	
	/*
	 * 사제 임지 발령 정보 삭제
	 */
	@RequestMapping(value = "/admin/board/priest_assing_delete.do")
    public @ResponseBody Map<String,Object> deletePreistAssign(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException , Exception
    {
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
		
		// service call
        CommonDaoDTO dto = null;
        String error = "";
        try {
        	dto = priestService.deletePreistAssign(_params);
		} catch (Exception e) {
			error = e.getMessage();
			e.printStackTrace();
		}
        
    	// response
        Map rtMap = new HashMap();
        rtMap.put("idx", dto.otherData.toString());
        rtMap.put("status", "error");
        rtMap.put("message", "실패되었습니다.["+error+"]");
    	if(dto.isBool) {
    		rtMap.put("status", "normal");
    		rtMap.put("message", "발령정보가 반영 되었습니다.");
    	}
		
    	D(_logger, Thread.currentThread().getStackTrace(), "Response Controller >> \n\t\t"+rtMap  );
    	
		return rtMap;
    }
}
