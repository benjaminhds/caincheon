package kr.caincheon.church.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.caincheon.church.admin.serivce.CodeService;
import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonException;

/**
 * 직급코드 관리 서비스를 제공하는 컨트롤러
 * @author benjamin
 */

@RestController
public class RestfulController extends CommonController
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="codeService")
    private CodeService codeService;
    
    /**
     * 본당지구코드 관리 > 지구코드 : 지구의 지도의 구획 좌표 데이터
     * @param request
     * @return map
     * @throws CommonException
     */
    //@RequestMapping(value = "/admin/org/area_code_mgmt_map.do", method=RequestMethod.POST, consumes={"application/json"})
    @RequestMapping(value = "/admin/org/area_code_mgmt_map.do", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> selectedAreaCodeMapPolygonPath(HttpServletRequest request) throws ServletException, IOException, Exception
    {
    	// request handling
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Req _params = " + _params);
        
        // call a service component 
        _params.put("code", "000004");//본당지구분류코드
        List<Map> rtList = codeService.selectedAreaCodeMapPolygonPath(_params);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response Map : "+rtList.get(0));
        
        // 단 1개만 리턴
        return rtList.get(0);
        //return new ResponseEntity<Map>(rtList.get(0), HttpStatus.OK);
    }


}