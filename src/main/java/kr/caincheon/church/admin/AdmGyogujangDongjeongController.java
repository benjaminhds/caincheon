// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmGyogujangDongjeongController.java

package kr.caincheon.church.admin;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.admin.serivce.NBoardServiceImpl;
import kr.caincheon.church.common.base.CommonController;

/**
 * 교구장동정 관리
 * @author benjamin
 */
@Controller
public class AdmGyogujangDongjeongController extends CommonController implements ParControllerConst
{

    private final Logger _logger = Logger.getLogger(getClass());
    
//    @Resource(name="parService")
//    private ParService parService;
    
//    @Resource(name="admParService")
//    private AdmParService admParService;

    @Resource(name="nBoardService")
    private NBoardServiceImpl nBoardService;
    
	/**
	 * 교구장동적 목록 조회
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/board/par_list.do")
    public ModelAndView parList(HttpServletRequest request)throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/par_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "_params ? "+_params );
        
        //
        
        pnullPut(_params, "b_idx", "ALL");
        
        // 
        //pnullPut(_params, "LV1", "01' AND LV2!='00' AND C.CHURCH_IDX IS NOT NULL AND LV3!='000");//교구조직
        pnullPut(_params, "TOP_COUNT", "100");//상단고정 개수
        callNBoardList(nBoardService, _params, mv, LEFT_MENU_DATA_PG, false, Integer.parseInt(ATTACHED_FILE_COUNT));//조직이 필요 없어서 false
        
        //
        String searchGubun1 = pnull(_params.get("searchGubun1"));
        String searchGubun2 = pnull(_params.get("searchGubun2"));
        String searchText   = pnull(_params.get("searchText"));
        
        //
        LinkedHashMap schTextGubunMap1 = new LinkedHashMap();
        if(searchGubun1.equals("3"))   schTextGubunMap1.put("3", "selected");   else schTextGubunMap1.put("3", "");//동정
        if(searchGubun1.equals("4"))   schTextGubunMap1.put("4", "selected");   else schTextGubunMap1.put("4", "");//강론
        
        // 키워드 검색
        LinkedHashMap schTextGubunMap2 = new LinkedHashMap();
        if(searchGubun2.equals("title"))   schTextGubunMap2.put("title", "selected");   else schTextGubunMap2.put("title", "");
        if(searchGubun2.equals("content")) schTextGubunMap2.put("content", "selected"); else schTextGubunMap2.put("content", "");
        if(searchGubun2.equals("all"))     schTextGubunMap2.put("all", "selected");     else schTextGubunMap2.put("all", "");
        
        // response
        mv.addObject("searchText", searchText);
        mv.addObject("schTextGubunMap1", schTextGubunMap1);
        mv.addObject("schTextGubunMap2", schTextGubunMap2);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/par_view.do")
    public ModelAndView admParView(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/par_view");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "_params ? "+_params );
        
        
        //pnullPut(_params, "LV1", "02' AND LV2!='00' AND C.CHURCH_IDX IS NOT NULL AND LV3!='000");//본당조회
        callNBoardContents(nBoardService, _params, mv, LEFT_MENU_DATA_PG, false, Integer.parseInt(ATTACHED_FILE_COUNT));
        
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }


	/**
	 * 교구장동적 등록
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/board/par_insert.do")
    public ModelAndView admParInsert(Map map, HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "_params ? "+_params );
        
        

        boolean success = false;
        try {
            success = nBoardService.nboardInsert(_params, LEFT_MENU_DATA_PG, request);
        } catch(Exception e) { 
        	e.printStackTrace();
        }
        
        ModelAndView mv = new ModelAndView("/admin/board/par_list");
        if(!success) {
        	mv = new ModelAndView("/admin/board/par_view", _params);
        }
        _params.clear();
        callNBoardList(nBoardService, _params, mv, LEFT_MENU_DATA_PG, false, Integer.parseInt(ATTACHED_FILE_COUNT));
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
//        if(success)
//            response.sendRedirect("/admin/board/par_list.do");
//        else
//        	request.getRequestDispatcher("/admin/board/par_view.do").forward(request, response);//response.sendRedirect("/admin/board/par_view.do");
    }

	/**
	 * 교구장동적 내용 수정
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/board/par_modify.do")
    public ModelAndView admParModify(Map map, HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        //request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "_params ? "+_params );
        
        
        boolean success = false;
        
        // call a service
        try {
            success = nBoardService.nboardModify(_params, LEFT_MENU_DATA_PG, request);
        } catch(Exception e) { 
        	e.printStackTrace();
        }
        
        // response
        ModelAndView mv = new ModelAndView("/admin/board/par_list");
        if(!success) {
        	mv = new ModelAndView("/admin/board/par_view", _params);
        }
        _params.clear();
        callNBoardList(nBoardService, _params, mv, LEFT_MENU_DATA_PG, false, Integer.parseInt(ATTACHED_FILE_COUNT));
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/**
	 * 교구장동적 내용 삭제
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/board/par_delete.do")
    public ModelAndView admParDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "_params ? "+_params );
        
        
        
        boolean success = false;
        try {
        	pnullPut(_params, "CONTEXT_ROOT_PATH", request.getSession().getServletContext().getRealPath("/") + "upload/gyogu_chief/");
            nBoardService.nboardDelete(_params, LEFT_MENU_DATA_PG);
        } catch(Exception e) { 
        	e.printStackTrace();
        }
        
        ModelAndView mv = new ModelAndView("/admin/board/par_list");
        _params.clear();
        callNBoardList(nBoardService, _params, mv, LEFT_MENU_DATA_PG, false, Integer.parseInt(ATTACHED_FILE_COUNT));
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

}
