package kr.caincheon.church.admin.mboard;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.common.CAGiguInfoUtil;
import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.Const;
import kr.caincheon.church.common.base.Paging;
import kr.caincheon.church.common.utils.UtilString;


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
		java.util.List list			= (java.util.List)_params.remove(Const.ADM_MAPKEY_LIST);
		java.util.List categoryList = (java.util.List)_params.remove(Const.ADM_MAPKEY_LIST_OTHERS);
		Integer total  = (Integer)_params.remove(Const.ADM_MAPKEY_COUNT);
		Paging  paging = (Paging)_params.remove(Const.ADM_MAPKEY_PAGING);
		
		mv.addObject("rtn_list",   list);
		mv.addObject("rtn_total",  total);
		mv.addObject("rtn_paging", paging);
		mv.addObject("categoryList", categoryList);
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
   public ModelAndView normal_board_list(HttpServletRequest request)
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
	   mv.addObject("postList", dto.daoList);
	   mv.addObject("paging",   dto.paging);
	   mv.addObject("bd_content",  bdContent);
	   /*카테고리 리스트*/
	   mv.addObject("category_list",   list);
	   
	   D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
	   
	   return mv;
   }
	/**
	* 전체 게시판 view
	* @param request
	* @return
	* @throws CommonException
	*/
	@RequestMapping(value = "/n/board/normal_board_view.do")
	public ModelAndView normal_board_view(HttpServletRequest request)
			throws ServletException, Exception
	{
		ModelAndView mv = new ModelAndView("/board/normal_board_view");
		build(request);
		
		/*보드 관리에 대한 내용*/
		mBoardService.boardViewContent(_params);
		Map 			bdContent	= (Map)_params.remove(Const.ADM_MAPKEY_CONTENT);
		java.util.List	list		= (java.util.List)_params.remove(Const.ADM_MAPKEY_LIST_OTHERS);
		
		if(!"W".equals(_params.get("mode"))) {
			/*게시글에 대한 내용*/
			mBoardService.postViewContent(_params);
			Map 			postContent	= (Map)_params.remove(Const.ADM_MAPKEY_CONTENT);
			mv.addObject("post_content", postContent);
			mv.addObject("attachList", _params.get("attachList"));
			
			/*comment List*/
			CommonDaoDTO dto = mBoardService.getCommentList(_params);
			mv.addObject("comment_list", dto.daoList);
		}

		mv.addObject("bd_content",  bdContent);
		
		/*카테고리 리스트*/
		mv.addObject("category_list",   list);
		mv.addObject("_params",   _params);
		
		// request
		D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
		
		return mv;
	}
	/**
	 * 게시판관리 > 게시판관리 저장
	 * @param request
	 * @return
	 * @throws CommonException
	 */
	@RequestMapping(value = "/n/board/comment_cud.do")
	public @ResponseBody Map<String,Object> cudComment(HttpServletRequest request) throws Exception
	{
		// request handling
		build(request);
		
		_params.remove("i_arrCgList");
		_params.remove("i_arrCgNameList");
		
		_params.put("i_arrCgList" , request.getParameterValues("i_arrCgList"));
		_params.put("i_arrCgNameList" , request.getParameterValues("i_arrCgNameList"));
		_params.put("SS_MEM_ID",UtilString.pnull(request.getSession(true).getAttribute(Const.SESSION_KEY_MEM_ID)));
		
		D(_logger, Thread.currentThread().getStackTrace(), "params = " + _params);
		
		try {
			/*보드 마스터 저장*/
			mBoardService.iudCommentMaster(_params);
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		JSONObject json = new JSONObject();
		return json;
	}
	/**
	 * 게시판관리 > 일반 게시판 front 저장
	 * @param request
	 * @return
	 * @throws CommonException
	 */
	@RequestMapping(value = "/n/board/{b_type}_board_iudboard.do")
	public @ResponseBody Map<String,Object> normal_board_iudboard(
				MultipartHttpServletRequest request
				, HttpServletResponse response
				, @PathVariable String b_type
			) throws Exception
	{
	
		build(request);
		
		D(_logger, Thread.currentThread().getStackTrace(), "params = " + _params);
		
		try {
			
			_params.put("SS_MEM_ID",UtilString.pnull(request.getSession(true).getAttribute(Const.SESSION_KEY_MEM_ID)));
			_params.put("SS_MEM_NM",UtilString.pnull(request.getSession(true).getAttribute(Const.SESSION_KEY_MEM_NM)));
			_params.put("SS_CHURCHIDX",UtilString.pnull(request.getSession(true).getAttribute(Const.SESSION_KEY_CHURCHIDX)));
			_params.put("SS_DEPARTIDX",UtilString.pnull(request.getSession(true).getAttribute(Const.SESSION_KEY_DEPARTIDX)));
			
			mBoardService.iudPostsMaster(_params, request);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		response.sendRedirect("/n/board/"+b_type+"_board_list.do?i_sBidx="+_params.get("i_sBidx")+"&i_sCIdx="+_params.get("i_sCIdx"));
		
		JSONObject json = new JSONObject();
		return json;
	
	}
	/**
	 * SELECT BOX 수정
	 * @param request
	 * @return
	 * @throws CommonException
	 */
	@RequestMapping(value = "/n/board/church_modify_ajax.do")
	public @ResponseBody Map<String,Object> church_modify_ajax(HttpServletRequest request) throws Exception
	{
		// request handling
		build(request);
		
		D(_logger, Thread.currentThread().getStackTrace(), "params = " + _params);
		
		Map<String,Object> result	=	new HashMap<String, Object>();
		
		try {
			/*본당 리스트*/
			mBoardService.getOrgIdxList(_params);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		result.put("orgIdxList", _params.get(Const.ADM_MAPKEY_LIST));
		
		return result;
	}
	
	@RequestMapping(value = "/n/board/alb_board_list.do")
	public ModelAndView albList(HttpServletRequest request) throws ServletException, Exception
	{
		ModelAndView mv = new ModelAndView("/board/alb_board_list");
		build(request, true);
		D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );
		
		mBoardService.boardViewContent(_params);
		Map 			bdContent	= (Map)_params.remove(Const.ADM_MAPKEY_CONTENT);
		java.util.List	list		= (java.util.List)_params.remove(Const.ADM_MAPKEY_LIST_OTHERS);
		
		CommonDaoDTO dto = mBoardService.albList(_params);
		
		mv.addObject("_params",  _params);
		mv.addObject("albList", dto.daoList);
		mv.addObject("paging",   dto.paging);
		
		mv.addObject("bd_content",  bdContent);
		/*카테고리 리스트*/
		mv.addObject("category_list",   list);
		
		mv.addObject("giguList", CAGiguInfoUtil.getInstance().getRegionList(false));//지구코드목록
		
		_params.put("code","00004");
		mBoardService.getCodeInstance(_params);
		_params.put("mainHallByGigu", _params.remove("code_list"));
		
		D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
		
		return mv;
	}
	/**
	 * 앨범 게시판 view
	 * */
	@RequestMapping(value = "/n/board/alb_board_view.do")
	public ModelAndView albContents(HttpServletRequest request) throws ServletException, Exception
	{
		ModelAndView mv = new ModelAndView("/board/alb_board_view");
		build(request);
		
		D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );
		
		mBoardService.boardViewContent(_params);
		Map 			bdContent	= (Map)_params.remove(Const.ADM_MAPKEY_CONTENT);
		java.util.List	list		= (java.util.List)_params.remove(Const.ADM_MAPKEY_LIST_OTHERS);
		
		mBoardService.getalbView(_params);
		
		mv.addObject("_params", _params);
		
		Map 			albContents	= (Map)_params.remove(Const.ADM_MAPKEY_CONTENT);
		mv.addObject("albContents",  albContents);
		
		mv.addObject("bd_content",  bdContent);
		/*카테고리 리스트*/
		mv.addObject("category_list",   list);
		
		mv.addObject("attachList", _params.get("attachList"));
		D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv.getModelMap() );
		
		return mv;
	}
	/*
	 * 홈 > 소식 > 교구영상 : 목록
	 */
	@RequestMapping(value = "/n/board/mov_board_list.do")
	public ModelAndView moveList(HttpServletRequest request) throws Exception
	{
		// request
		ModelAndView mv = new ModelAndView("/board/mov_board_list");
		build(request);
		
		D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
		
		_params.put("i_sIsView", "Y");
		
		mBoardService.boardViewContent(_params);
		Map 			bdContent	= (Map)_params.remove(Const.ADM_MAPKEY_CONTENT);
		java.util.List	list		= (java.util.List)_params.remove(Const.ADM_MAPKEY_LIST_OTHERS);
		
		
		// service call
		CommonDaoDTO dto =	mBoardService.youtubeList(_params, pnull(_params, "pageNo"), pnull(_params, "pageSize"));
		
		// Response
		String strCategoryName = "교구영상";
		String srchGubun = pnull(_params.get("srchGubun"));
		
		Map<String, String> schTextGubunMap = new HashMap<String, String>();
		if(srchGubun.equals("all"))  schTextGubunMap.put("all", "selected");  else schTextGubunMap.put("all", "");
		if(srchGubun.equals("date")) schTextGubunMap.put("date", "selected"); else schTextGubunMap.put("date", "");
		if(srchGubun.equals("contents")) schTextGubunMap.put("contents", "selected"); else schTextGubunMap.put("contents", "");
		
		mv.addObject("strCategoryName", strCategoryName);
		mv.addObject("schTextGubunMap", schTextGubunMap);
		
		mv.addObject("movList", dto.daoList);	// movList);
		mv.addObject("paging", dto.paging);		// paging);
		mv.addObject("_params", _params);
		
		mv.addObject("bd_content",  bdContent);
		
		D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
		
		return mv;
	}	
	@RequestMapping(value = "/n/board/mov_board_view.do")
	public ModelAndView movContents(HttpServletRequest request) throws Exception
	{
		ModelAndView mv = new ModelAndView("/board/mov_board_view");
		build(request);
		
		D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
		
		mBoardService.boardViewContent(_params);
		Map 			bdContent	= (Map)_params.remove(Const.ADM_MAPKEY_CONTENT);
		java.util.List	list		= (java.util.List)_params.remove(Const.ADM_MAPKEY_LIST_OTHERS);
		
		/*게시글에 대한 내용*/
		mBoardService.postViewContent(_params);
		
		/*comment List*/
		CommonDaoDTO dto = mBoardService.getCommentList(_params);
		mv.addObject("comment_list", dto.daoList);
		
		mv.addObject("_params", _params);
		
		Map 			movContents	= (Map)_params.remove(Const.ADM_MAPKEY_CONTENT);
		mv.addObject("movContents",  movContents);
		mv.addObject("attachList", _params.get("attachList"));
		
		mv.addObject("bd_content",  bdContent);
		/*카테고리 리스트*/
		mv.addObject("category_list",   list);
		
		D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
		
		return mv;
	}
	/* bjm found */
	@RequestMapping(value = "/n/board/mgz_board_list.do")
	public ModelAndView mgzList(HttpServletRequest request) throws Exception 
	{
		ModelAndView mv = new ModelAndView("/board/mgz_board_list");
		build(request);
		D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );

		mBoardService.getMagazineList(_params);
		mv.addObject("pub_idx", pnull(_params.get("pub_idx")));
		
		mv.addObject("mgzList", _params.get(Const.ADM_MAPKEY_LIST));
		Paging  paging = (Paging)_params.remove(Const.ADM_MAPKEY_PAGING);
		
		mv.addObject("paging", paging);
		
		D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
		
		return mv;
	}
}