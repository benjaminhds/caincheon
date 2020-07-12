// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DepartmentController.java

package kr.caincheon.church.intro;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonDaoMultiDTO;
import kr.caincheon.church.intro.service.DepartmentService;

@Controller
public class DepartmentController extends CommonController
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="departmentService")
    private DepartmentService departmentService;

    /* bjm found */
	@RequestMapping(value = "/intro/diocesan.do")
    public ModelAndView diocesan(HttpServletRequest request)
    		throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/intro/diocesan");
        CommonDaoMultiDTO dto = new CommonDaoMultiDTO();
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
//        
//        try {
//            departmentService.selectDepartment(_params, dto);
//        } catch(Exception e) {
//        	E(_logger, Thread.currentThread().getStackTrace(), "Controller Error", e);
//        } finally {
//	        departmentService.freeDataSource();
//        }
        
//        mv.addObject("LIST", dto.daoResult);
//        mv.addObject("depart_idx", pnull(_params, "depart_idx"));
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }
	
    /* bjm found */
	@RequestMapping(value = "/intro/diocesan_04.do")
    public ModelAndView diocesan_04(HttpServletRequest request)
    		throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/intro/diocesan_04");
        CommonDaoMultiDTO dto = new CommonDaoMultiDTO();
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        try {
            departmentService.selectDepartment(_params, dto);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "Controller Error", e);
        } finally {
	        departmentService.freeDataSource();
        }
        
        mv.addObject("LIST", dto.daoResult);
        mv.addObject("depart_idx", pnull(_params, "depart_idx"));
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

    
}
