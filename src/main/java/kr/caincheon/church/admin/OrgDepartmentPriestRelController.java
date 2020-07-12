package kr.caincheon.church.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.admin.serivce.OrgDepartmentPriestRelService;
import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.Const;
import kr.caincheon.church.common.base.Paging;

/**
 * 직급코드 관리 서비스를 제공하는 컨트롤러
 * @author benjamin
 */

@Controller
public class OrgDepartmentPriestRelController extends CommonController
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="orgDepartmentPriestRelService")
    private OrgDepartmentPriestRelService orgDepartmentPriestRelService;
    
    /**
     * 조직코드관리 > 직급코드조회
     * @param request
     * @return
     * @throws CommonException
     */
    @RequestMapping(value = "/admin/org/ard_code_mgmt.do")
    public ModelAndView multiBoardCodeMgmt(HttpServletRequest request) throws Exception
    {
    	ModelAndView mv = new ModelAndView("/admin/code/board_code_mgmt");
        
    	// request handling
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "params = " + _params);
        
        // call a service component 
        orgDepartmentPriestRelService.select(_params);
        
        D(_logger, Thread.currentThread().getStackTrace(), _params.toString());
        
        // response handling
        java.util.List list  = (java.util.List)_params.remove(Const.ADM_MAPKEY_LIST);
        Integer total  = (Integer)_params.remove(Const.ADM_MAPKEY_COUNT);
        Paging  paging = (Paging)_params.remove(Const.ADM_MAPKEY_PAGING);
        
        mv.addObject("rtn_list",   list);
        mv.addObject("rtn_total",  total);
        mv.addObject("rtn_paging", paging);

        D(_logger, Thread.currentThread().getStackTrace(), mv.toString());
        D(_logger, Thread.currentThread().getStackTrace(), mv.getModelMap().toString());
        
        return mv;
    }


}