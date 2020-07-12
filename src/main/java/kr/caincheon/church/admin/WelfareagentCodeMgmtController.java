package kr.caincheon.church.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.admin.serivce.CodeService;
import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.Const;
import kr.caincheon.church.common.base.Paging;

/**
 * 직급코드 관리 서비스를 제공하는 컨트롤러
 * @author benjamin
 */

@Controller
public class WelfareagentCodeMgmtController extends CommonController
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="codeService")
    private CodeService codeService;
    
    /**
     * 조직코드관리 > 직급코드조회
     * @param request
     * @return
     * @throws CommonException
     */
    @RequestMapping(value = "/admin/org/welfareagent_code_mgmt.do")
    public ModelAndView positionCodeList(HttpServletRequest request) throws Exception
    {
    	ModelAndView mv = new ModelAndView("/admin/code/welfareagent_code_mgmt");
        
    	// request handling
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "params = " + _params);
        
        // call a service component 
        _params.put("code", "000005");//복지기관세목분류코드
		CommonDaoDTO dto = codeService.listCodeInstances(_params);
        
        // response handling
        mv.addObject("rtn_list",   dto.daoList);
        mv.addObject("rtn_total",  dto.daoTotalCount);
        mv.addObject("rtn_paging", dto.paging);

        D(_logger, Thread.currentThread().getStackTrace(), mv.toString());
        D(_logger, Thread.currentThread().getStackTrace(), mv.getModelMap().toString());
        
        return mv;
    }

    /**
     * 조직코드관리 > 직급코드관리 cud
     * @param request
     * @return
     * @throws CommonException
     */
    @RequestMapping(value = "/admin/org/welfareagent_code_mgmt_cud.do")
    public ModelAndView positionCodeCUD(HttpServletRequest request) throws Exception
    {
    	ModelAndView mv = new ModelAndView("/admin/code/welfareagent_code_mgmt");
        
    	// request handling
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "params = " + _params);
        
        // call a service component 
        codeService.iudCodeInstance(_params);
		CommonDaoDTO dto = codeService.listCodeInstances(_params);
		
        // response handling
        mv.addObject("rtn_list",   dto.daoTotalCount);
        mv.addObject("rtn_paging", dto.paging);

        D(_logger, Thread.currentThread().getStackTrace(), mv.toString());
        D(_logger, Thread.currentThread().getStackTrace(), mv.getModelMap().toString());
        
        return mv;
    }

}