package kr.caincheon.church.admin.mboard;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.Const;
import kr.caincheon.church.common.base.Paging;


/**
 * 직급코드 관리 서비스를 제공하는 컨트롤러
 * @author benjamin
 */
@Controller
public class MBoardController extends CommonController
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    // 구게시판 프로그램
//    @Resource(name="boardCategoryService")
//    private BoardCategoryService boardCategoryService;
    
    // 신 게시판 프로그램
    @Resource(name="mBoardCategoryService")
    private MBoardCategoryService mBoardCategoryService;
    
    /**
     * 코드관리 > 게시판코드조회
     * @param request
     * @return 
     * @throws CommonException
     */
    @RequestMapping(value = "/n/admin/code/board_list.do")
    public ModelAndView admin_code_mboard_list_do(HttpServletRequest request) throws Exception
    {
    	ModelAndView mv = new ModelAndView("/admin/code/board_list");
        
    	// request handling
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "params = " + _params);
        
        // call a service component 
        mBoardCategoryService.listBoard(_params);
        
        D(_logger, Thread.currentThread().getStackTrace(), _params.toString());
        
        // response handling
        java.util.List list    = (java.util.List)_params.remove(Const.ADM_MAPKEY_LIST);
        java.util.List b_types = (java.util.List)_params.remove(Const.ADM_MAPKEY_LIST_OTHERS);
        Integer total  = (Integer)_params.remove(Const.ADM_MAPKEY_COUNT);
        Paging  paging = (Paging)_params.remove(Const.ADM_MAPKEY_PAGING);
        
        mv.addObject("rtn_list",   list);
        mv.addObject("rtn_total",  total);
        mv.addObject("rtn_paging", paging);
        mv.addObject("rtn_b_type", b_types);

        D(_logger, Thread.currentThread().getStackTrace(), mv.toString());
        D(_logger, Thread.currentThread().getStackTrace(), mv.getModelMap().toString());
        
        return mv;
    }

    /**
	 * 코드관리 > 게시판코드관리
	 * @param request
	 * @return
	 * @throws CommonException
	 */
	@RequestMapping(value = "/n/admin/code/board_cud.do")
	public ModelAndView admin_code_mboard_cud_do(HttpServletRequest request) throws CommonException, Exception
	{
		ModelAndView mv = new ModelAndView("/admin/code/board_list");
	    
		// request handling
	    build(request);
	    
	    D(_logger, Thread.currentThread().getStackTrace(), "params = " + _params);
	    
	    String pageNo   = pnull(_params, "pageNo", "1");
	    String pageSize = pnull(_params, "pageSize", "30");
	    
	    // call a service component 
	    int rtVal = mBoardCategoryService.iudBoard(_params);
	    
	    _params.clear();
	    _params.put("pageNo", pageNo);
	    _params.put("pageSize", pageSize);
	    mBoardCategoryService.listBoard(_params);
	    
	    D(_logger, Thread.currentThread().getStackTrace(), _params.toString());
	    
	    // response handling
	    java.util.List list  = (java.util.List)_params.remove(Const.ADM_MAPKEY_LIST);
        java.util.List b_types = (java.util.List)_params.remove(Const.ADM_MAPKEY_LIST_OTHERS);
	    Integer total  = (Integer)_params.remove(Const.ADM_MAPKEY_COUNT);
	    Paging  paging = (Paging)_params.remove(Const.ADM_MAPKEY_PAGING);
	    if(rtVal != 1) {
	    	mv.addObject("ERR_MSG", "데이터 처리가 정상적이지 않습니다. 확인이 필요합니다. [처리건수 : "+rtVal+"]");
	    }
	    
	    mv.addObject("rtn_list",   list);
	    mv.addObject("rtn_total",  total);
	    mv.addObject("rtn_paging", paging);
        mv.addObject("rtn_b_type", b_types);
	
	    D(_logger, Thread.currentThread().getStackTrace(), mv.toString());
	    D(_logger, Thread.currentThread().getStackTrace(), mv.getModelMap().toString());
	    
	    return mv;
	}

	/**
     * 코드관리 > 게시판코드조회
     * @param request
     * @return
     * @throws CommonException
     */
    @RequestMapping(value = "/n/admin/code/board_category_list.do")
    public ModelAndView admin_code_mboard_category_list_do(HttpServletRequest request) throws Exception
    {
    	ModelAndView mv = new ModelAndView("/admin/code/board_category_list");
        
    	// request handling
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "params = " + _params);
        
        // call a service component 
        //boardCategoryService.listBoardCategory(_params);
        mBoardCategoryService.listBoardCategory(_params);
        
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

    /**
     * 코드관리 > 게시판코드관리
     * @param request
     * @return
     * @throws CommonException
     */
    @RequestMapping(value = "/n/admin/code/board_category_cud.do")
    public ModelAndView admin_code_mboard_category_cud_do(HttpServletRequest request) throws CommonException, Exception
    {
    	ModelAndView mv = new ModelAndView("/admin/code/board_category_list");
        
    	// request handling
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "params = " + _params);
        
        String pageNo   = pnull(_params, "pageNo");
        String pageSize = pnull(_params, "pageSize");
        
        // call a service component 
        int rtVal = mBoardCategoryService.iudBoardCategory(_params);
        
        _params.clear();
        _params.put("pageNo", pageNo);
        _params.put("pageSize", pageSize);
        mBoardCategoryService.listBoardCategory(_params);
        
        D(_logger, Thread.currentThread().getStackTrace(), _params.toString());
        
        // response handling
        java.util.List list  = (java.util.List)_params.remove(Const.ADM_MAPKEY_LIST);
        Integer total  = (Integer)_params.remove(Const.ADM_MAPKEY_COUNT);
        Paging  paging = (Paging)_params.remove(Const.ADM_MAPKEY_PAGING);
        if(rtVal != 1) {
        	mv.addObject("ERR_MSG", "데이터 처리가 정상적이지 않습니다. 확인이 필요합니다. [처리건수 : "+rtVal+"]");
        }
        
        mv.addObject("rtn_list",   list);
        mv.addObject("rtn_total",  total);
        mv.addObject("rtn_paging", paging);

        D(_logger, Thread.currentThread().getStackTrace(), mv.toString());
        D(_logger, Thread.currentThread().getStackTrace(), mv.getModelMap().toString());
        
        return mv;
    }
    
}