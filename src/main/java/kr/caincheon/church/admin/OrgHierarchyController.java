package kr.caincheon.church.admin;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.admin.serivce.OrgHierarchyService;
import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.Const;
import kr.caincheon.church.common.base.Paging;

/**
 * 조직의 상하관계 데이터를 관리하는 서비스 컨트롤러. 
 * @author benjamin
 */

@Controller
public class OrgHierarchyController extends CommonController
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="orgHierarchyService")
    private OrgHierarchyService orgHierarchyService;
	

    /**
     * 조직코드관리 > 임지코드관리 > 조직코드관리
     * @param request
     * @return ModelAndView
     * @throws CommonException
     */
    @RequestMapping(value = "/admin/org/org_code_mgmtUp.do")
    public ModelAndView orgUpdate(HttpServletRequest request) throws Exception
    {
    	//ModelAndView mv = new ModelAndView("/admin/org/org_list");
    	ModelAndView mv = new ModelAndView("/admin/code/org_code_mgmt");
        
        build(request);

        //
        D(_logger, Thread.currentThread().getStackTrace(), "params = " + _params);

        // service call :: list select
        try {
			orgHierarchyService.updateOrgHierarchy(_params);
		} catch (Exception e) {
			if( e instanceof CommonException ) {
				mv.addObject("ERR_MSG",  ((CommonException)e).getErrMessage());
			} else 
				mv.addObject("ERR_MSG",  e.getMessage());
		}
        
        // response handling
        java.util.List listG1 = (java.util.List)_params.remove(Const.ADM_MAPKEY_GROUPLIST+"1");
        java.util.List listG2 = (java.util.List)_params.remove(Const.ADM_MAPKEY_GROUPLIST+"2");
        java.util.List list   = (java.util.List)_params.remove(Const.ADM_MAPKEY_LIST);
        Integer total  = (Integer)_params.remove(Const.ADM_MAPKEY_COUNT);
        Paging  paging = (Paging)_params.remove(Const.ADM_MAPKEY_PAGING);

        // response
        mv.addObject("rtn_groupby1", listG1);
        mv.addObject("rtn_groupby2", listG2);
        mv.addObject("rtn_list",    list);
        mv.addObject("rtn_total",   total);
        mv.addObject("rtn_paging",  paging);
        
        D(_logger, Thread.currentThread().getStackTrace(), "mv = " + mv);
        
        return mv;
    }
    
    /**
     * 조직코드관리 > 임지코드관리 > 조직코드관리
     * @param request
     * @return ModelAndView
     * @throws CommonException
     */
    @RequestMapping(value = "/admin/org/org_code_mgmt.do")
    public ModelAndView orgList(HttpServletRequest request) throws Exception
    {
    	//ModelAndView mv = new ModelAndView("/admin/org/org_list");
    	ModelAndView mv = new ModelAndView("/admin/code/org_code_mgmt");
        
        build(request);

        //
        D(_logger, Thread.currentThread().getStackTrace(), "params = " + _params);

        // service call :: list select
        pnullPut(_params, "ORG_HIERARCHY_ORDERBY", " O.LV1 ASC, O.LV2 ASC, O.LV3 ASC, O.ORDERNO ASC ");
        orgHierarchyService.selectOrgHierarchyGroupby(_params);
        
        // response handling
        java.util.List listG1 = (java.util.List)_params.remove(Const.ADM_MAPKEY_GROUPLIST+"1");
        java.util.List listG2 = (java.util.List)_params.remove(Const.ADM_MAPKEY_GROUPLIST+"2");
        java.util.List list   = (java.util.List)_params.remove(Const.ADM_MAPKEY_LIST);
        Integer total  = (Integer)_params.remove(Const.ADM_MAPKEY_COUNT);
        Paging  paging = (Paging)_params.remove(Const.ADM_MAPKEY_PAGING);

        // response
        mv.addObject("rtn_groupby1", listG1);
        mv.addObject("rtn_groupby2", listG2);
        mv.addObject("rtn_list",    list);
        mv.addObject("rtn_total",   total);
        mv.addObject("rtn_paging",  paging);
        
        D(_logger, Thread.currentThread().getStackTrace(), "mv = " + mv);
        
        return mv;
    }
    

    /**
     * LV1의 코드에 따른 LV2의 목록을 가져온다. (LV3은 제외된다.)
     * @param request
     * @return ModelAndView
     * @throws CommonException
     */
    @RequestMapping(value = "/admin/org/org_code_list_lv.do")
    public @ResponseBody List<Map> orgListByLV2(HttpServletRequest request) throws CommonException, Exception
    {
        build(request);

        //
        D(_logger, Thread.currentThread().getStackTrace(), "params = " + _params);

        // service call :: list select
//		String tp  = pnull(_params, "DEPTH_TYPE", "");
//		String lv1 = pnull(_params, "LV1", "");
		
        /*orgHierarchyService.selectOrgHierarchyGroupby(_params);
        List rtList = (List<Map>)_params.remove(Const.ADM_MAPKEY_GROUPLIST+"2");*/
        List rtList = orgHierarchyService.selectOrgHierarchy(_params);
        
        
        // response
        return rtList;
    }
    
    
}