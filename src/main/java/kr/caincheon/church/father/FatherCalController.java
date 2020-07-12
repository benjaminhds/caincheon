package kr.caincheon.church.father;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.father.service.FatherCalServiceImpl;

@Controller
public class FatherCalController extends CommonController
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="fatherCalService")
    private FatherCalServiceImpl fatherCalService;
	
    
    /**
     * front :: 사제달력 - 서품과 축일에 맞는 사제 목록 조회
     */
	@RequestMapping(value = "/father/father_cal.do")
    public ModelAndView priestList(HttpServletRequest request) throws Exception
    {
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/father/father_cal");
        
        // service call
        Map rtMap = fatherCalService.selectFathersByDiary(_params);
        
        // response
        mv.addObject("L_DIARY", rtMap.get("DIARY"));
        mv.addObject("PRIEST" , rtMap.get("PRIEST"));
        mv.addObject("_params", _params);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }
}
