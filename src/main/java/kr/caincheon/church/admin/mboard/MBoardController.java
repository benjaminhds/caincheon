package kr.caincheon.church.admin.mboard;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.Const;
import kr.caincheon.church.common.base.Paging;


/**
 * 게시판 관리 controller
 * @author kyjo
 * 
 * TODO:	1. 게시판 리스트 (MBOARD_MNGT)
 * 			2. 게시판 등록
 * 			3. 게시판 수정
 * 			4. 게시판 삭제
 */
@Controller
public class MBoardController extends CommonController
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    // 신 게시판 프로그램
    @Resource(name="mBoardCategoryService")
    private MBoardCategoryService mBoardCategoryService;
    
    // 보드 관리 
    @Resource(name="mBoardService")
    private MBoardService mBoardService;

    /**
     * 게시판관리 > 게시판관리 리스트
     * @param request
     * @return 
     * @throws CommonException
     * 
     * TODO : 1. 리스트 뿌리기(완료)
     * 		  2. 리스트 검색조건(협의필요)
     */
    @RequestMapping(value = "/n/admin/board/board_list.do")
    public ModelAndView admin_mboard_mng_list_do(HttpServletRequest request) throws Exception
    {
    	ModelAndView mv = new ModelAndView("/admin/board/board_list");
        
    	// request handling
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "params = " + _params);
        
        // call a service component 
        try {
			mBoardService.listBoard(_params);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
        
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
        mv.addObject("_params", _params);

        D(_logger, Thread.currentThread().getStackTrace(), mv.toString());
        D(_logger, Thread.currentThread().getStackTrace(), mv.getModelMap().toString());
        
        return mv;
    }
    /**
     * 게시판관리 > 게시판관리 View
     * @param request
     * @return 
     * @throws CommonException
     * 
     * TODO : 1. 게시판 관리 / 등록 화면 (완료)
     * 		  2. 게시판 관리 / 수정 화면 
     */
    @RequestMapping(value = "/n/admin/board/board_view.do")
    public ModelAndView admin_mboard_mng_view_do(HttpServletRequest request) throws Exception
    {
    	ModelAndView mv = new ModelAndView("/admin/board/board_view");
    	// request handling
    	build(request);
    	
    	D(_logger, Thread.currentThread().getStackTrace(), "params = " + _params);
    	
    	// call a service component 
    	try {
    		if("modify".equals(_params.get("query_type"))) {
    			mBoardService.boardViewContent(_params);
    		}
    		
    		_params.put("code","000012");
    		mBoardService.getCodeInstance(_params);
    		_params.put("cateCodegory_list", _params.remove("code_list"));
    		
    		_params.put("code","000013");
    		mBoardService.getCodeInstance(_params);
    		_params.put("boardkind_list", _params.remove("code_list"));
    		
    		_params.put("code","000014");
    		mBoardService.getCodeInstance(_params);
    		_params.put("boardtype_list", _params.remove("code_list"));
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw e;
    	}
    	
    	 // response handling
        java.util.List	list		= (java.util.List)_params.remove(Const.ADM_MAPKEY_LIST_OTHERS);
        
        if("modify".equals(_params.get("query_type"))) {
	        Map 			bdContent	= (Map)_params.remove(Const.ADM_MAPKEY_CONTENT);
	        mv.addObject("bd_content",  bdContent);
        }
        mv.addObject("category_list",   list);
        /*코드 리스트 Start*/
        mv.addObject("cateCodegory_list",   _params.remove("cateCodegory_list"));
        mv.addObject("boardtype_list",   _params.remove("boardtype_list"));
        mv.addObject("boardkind_list",   _params.remove("boardkind_list"));
        /*코드 리스트 End*/
        mv.addObject("query_type",   _params.remove("query_type"));
        mv.addObject("_params",   _params);
        
    	D(_logger, Thread.currentThread().getStackTrace(), _params.toString());
    	
    	return mv;
    }

    /**
     * 게시판관리 > 게시판관리 저장
     * @param request
     * @return
     * @throws CommonException
     */
    @RequestMapping(value = "/n/admin/board/board_cud.do")
    public @ResponseBody Map<String,Object> iudCodeMast(HttpServletRequest request) throws Exception
    {
    	// request handling
        build(request);
        
        _params.remove("i_arrCgList");
        _params.remove("i_arrCgNameList");
        
        _params.put("i_arrCgList" , request.getParameterValues("i_arrCgList"));
        _params.put("i_arrCgNameList" , request.getParameterValues("i_arrCgNameList"));
        
        D(_logger, Thread.currentThread().getStackTrace(), "params = " + _params);
        
        try {
	        /*보드 마스터 저장*/
	        mBoardService.iudBoardMaster(_params);
        
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
        
        
        JSONObject json = new JSONObject();
        return json;
    }
    /**
     * 전체 게시판
     * @param request
     * @return
     * @throws CommonException
     */
	@RequestMapping(value = "/n/board/normal_board_list.do")
   public ModelAndView newsList(HttpServletRequest request)
   		throws ServletException, Exception
   {
		ModelAndView mv = new ModelAndView("/board/normal_board_list");
		
		build(request);
		
		mBoardService.boardViewContent(_params);
		Map 			bdContent	= (Map)_params.remove(Const.ADM_MAPKEY_CONTENT);
		 // response handling
        java.util.List	list		= (java.util.List)_params.remove(Const.ADM_MAPKEY_LIST_OTHERS);
        
		// request
       D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
       
       if("D".equals(bdContent.get("B_TYPE"))) {
    	   
       }
       // service call
       CommonDaoDTO dto = mBoardService.NormalboardList(_params);
       
       
       mv.addObject("_params",  _params);
       mv.addObject("newsList", dto.daoList);
       mv.addObject("paging",   dto.paging);
       mv.addObject("bd_content",  bdContent);
       /*카테고리 리스트*/
       mv.addObject("category_list",   list);
       
       D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
       
       return mv;
   }
}