package kr.caincheon.church.admin;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
public class CommonCodeMgmtController extends CommonController
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="codeService")
    private CodeService codeService;
    
    
    
    /**
     * 코드관리 > 공통코드 관리
     * @param request
     * @return
     * @throws CommonException
     */
    @RequestMapping(value = "/admin/code/common_code_master_list.do")
    public ModelAndView inquireCodeMastList(HttpServletRequest request) throws Exception
    {
    	ModelAndView mv = new ModelAndView("/admin/code/common_code_master_list");
        
    	// request handling
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "params = " + _params);
        
        // call a service component 
        codeService.listCodes(_params);
        
        D(_logger, Thread.currentThread().getStackTrace(), _params.toString());
        
        // response handling
        java.util.List list  = (java.util.List)_params.remove(Const.ADM_MAPKEY_LIST);
        Integer total  = (Integer)_params.remove(Const.ADM_MAPKEY_COUNT);
        Paging  paging = (Paging)_params.remove(Const.ADM_MAPKEY_PAGING);
        
        mv.addObject("rtn_list",   list);
        mv.addObject("rtn_total",  total);
        mv.addObject("rtn_paging", paging);

        D(_logger, Thread.currentThread().getStackTrace(), mv.toString());
        
        return mv;
    }

    /**
     * 조직코드관리 > 직급코드관리 cud
     * @param request
     * @return
     * @throws CommonException
     */
    @RequestMapping(value = "/admin/code/common_code_mgmt_cud.do")
    public ModelAndView iudCodeMast(HttpServletRequest request) throws Exception
    {
    	ModelAndView mv = new ModelAndView("/admin/code/common_code_master_list");
        
    	// request handling
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "params = " + _params);
        
        // call a service component
        codeService.iudCodeMaster(_params);
        codeService.listCodes(_params);
        
        // response handling
        java.util.List list  = (java.util.List)_params.remove(Const.ADM_MAPKEY_LIST);
        Integer total  = (Integer)_params.remove(Const.ADM_MAPKEY_COUNT);
        Paging  paging = (Paging)_params.remove(Const.ADM_MAPKEY_PAGING);
        
        // response handling
        mv.addObject("rtn_list",   list);
        mv.addObject("rtn_total",  total);
        mv.addObject("rtn_paging", paging);

        D(_logger, Thread.currentThread().getStackTrace(), mv.toString());
        
        return mv;
    }
    
    //----------- code instance
    
    /**
     * 코드관리 > 공통코드 관리 > 코드인스턴스관리
     * @param request
     * @return
     * @throws CommonException
     */
    @RequestMapping(value = "/admin/code/common_code_inst_list.do", method=RequestMethod.POST, headers="Accept=application/json; charset=utf-8")
    public @ResponseBody JSONObject inquireCodeInstList(HttpServletRequest request, HttpServletResponse response
    		, @RequestBody JSONObject _recvBody // PUT, POST,,,,
    		) throws Exception
    {
    	// request handling
        build(request, false);// basic : html/text
        _params.putAll(_recvBody); // because : application/json
        
        D(_logger, Thread.currentThread().getStackTrace(), "params = " + _params);
        
        // call a service component 
        CommonDaoDTO dto = codeService.listCodeInstances(_params);
        
        // MEMO column termination
        if(dto.daoList!=null && dto.daoList.size()>0) {
			String tmp = "";
			for(int i=0, i2=dto.daoList.size() ; i<i2 ; i++) {
				Map row = (Map)dto.daoList.get(i);
				row.remove("MEMO");
			}
		}
        D(_logger, Thread.currentThread().getStackTrace(), "response: "+ dto.toString());
        D(_logger, Thread.currentThread().getStackTrace(), "_params: "+_params);
        
        // response handling
        JSONObject resJson = new JSONObject();
        resJson.put("LIST", dto.daoList);
        resJson.put("COUNT", dto.daoTotalCount);
        resJson.put("PAGING", dto.paging);
        return resJson;
    }

    

    /**
     * 조직코드관리 > 직급코드관리 cud
     * @param request
     * @return
     * @throws CommonException
     */
    @RequestMapping(value = "/admin/code/common_code_inst_cud.do", method=RequestMethod.POST, headers="Accept=application/json; charset=utf-8")
    public @ResponseBody JSONObject iudCodeInstance(HttpServletRequest request, HttpServletResponse response
    		, @RequestBody JSONObject _recvBody // PUT, POST,,,,
    		) throws Exception
    {
    	// request handling
        build(request, false);// basic : html/text
        _params.putAll(_recvBody); // because : application/json
        
        D(_logger, Thread.currentThread().getStackTrace(), "params = " + _params);
        
        // call a service component
        int i = codeService.iudCodeInstance(_params);
        
        // response handling
        JSONObject resJson = new JSONObject();
        resJson.put("RESULT", i == 1 ? "sucess":"fail");
        resJson.put("COUNT",  i);
        resJson.put("MSG",  pnull(_params, "msg"));
        
        D(_logger, Thread.currentThread().getStackTrace(), resJson.toString());
        
        return resJson;
    }
    

}