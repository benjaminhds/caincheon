// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MarryController.java

package kr.caincheon.church.member;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.Const;
import kr.caincheon.church.common.base.Paging;
import kr.caincheon.church.member.service.MarryService;
import kr.caincheon.church.service.AdmBonNoticeService;

@Controller
public class MarryController extends CommonController
{


    private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="marryService")
    private MarryService marryService;
	
	@Resource(name="admBonNoticeService")
    private AdmBonNoticeService admBonNoticeService;


	/*
	 * 홈페이지에서 카나혼인강좌안내의 `일정/시간표 조회`
	 */
	@RequestMapping(value = "/community/cana.do")
    public ModelAndView communityCana(HttpServletRequest request) throws Exception
    {
		ModelAndView mv = new ModelAndView("/community/cana");
		build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        _params.put("id", "admin");
		selectCanaLectureAndTimetable(_params, mv);
		mv.addObject("_params", _params);
        
		D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
		return mv;
    }
	
    /**
     * 나의 카나혼 신청 목록
     */
	@RequestMapping(value = "/member/my_cana.do")
    public ModelAndView marryList(HttpServletRequest request) throws Exception
    {
        ModelAndView mv = new ModelAndView("/member/my_cana");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        if(pnull(_params.get("id")).equals(""))
            _params.put("id", pnull(request.getSession(true).getAttribute(Const.SESSION_KEY_MEM_ID)));
        
        java.util.List marryList = marryService.marryList(_params);
        int totalCount = marryService.marryListCount(_params);
        Paging paging = new Paging();
        paging.setPageNo(ipnull(_params, "pageNo" , 1));
        paging.setPageSize(ipnull(_params, "pageSize" , 10));
        paging.setTotalCount(totalCount);
        
        mv.addObject("param", _params);
        mv.addObject("marryList", marryList);
        mv.addObject("paging", paging);
        
		D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
		return mv;
    }

	/**
	 * "카나혼강좌 or 약혼자주말강좌" 신청하기 폼..
	 */
	@RequestMapping(value = "/community/cana_register.do")
    public ModelAndView canaRegister(HttpServletRequest request)
        throws CommonException, Exception
    {
        ModelAndView mv = new ModelAndView("/community/cana_register");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        java.util.List lectureList = marryService.marryLectureList(_params); // 카나혼인강좌 스케쥴 조회
        java.util.List lectureList2 = marryService.marryLectureList2(_params); // 약혼자 주말강좌 스케쥴 조회
        mv.addObject("query_type", "insert");
        _params.put("LEVEL", "2");
        java.util.List _1x00xList = admBonNoticeService._1x00xList(_params);
        mv.addObject("CHURCH_LIST", _1x00xList);
        mv.addObject("LECTURE_LIST", lectureList);
        mv.addObject("LECTURE_LIST2", lectureList2);
        mv.addObject("param", _params);
        
		D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
		return mv;
    }

	@RequestMapping(value = "/community/marry_apply.do")
    public ModelAndView marryApply(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, CommonException, Exception
    {
        ModelAndView mv = new ModelAndView("/community/cana_register");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        Map marryContents = marryService.marryContents(_params);
        java.util.List lectureList = marryService.marryLectureList(_params); // 카나혼인강좌 스케쥴 조회
        java.util.List lectureList2 = marryService.marryLectureList2(_params);// 약혼자 주말강좌 스케쥴 조회
        mv.addObject("_params", _params);
        mv.addObject("query_type", "update");
        _params.put("LEVEL", "2");
        java.util.List _1x00xList = admBonNoticeService._1x00xList(_params);
        mv.addObject("CHURCH_LIST", _1x00xList);
        mv.addObject("LECTURE_LIST", lectureList);
        mv.addObject("LECTURE_LIST2", lectureList2);
        mv.addObject("marryContents", marryContents);
        
		D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
		return mv;
    }


	/**
	 * 카나혼강좌 신청처리
	 */
	@RequestMapping(value = "/community/marry_insert.do")
	public @ResponseBody Map<String, Object> marryInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, CommonException, Exception
    {

        // request handle
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        // service call
        boolean success = false;
        
        // response handle
        Map<String, Object> resData = new HashMap<String, Object>();
        try {
			success = marryService.marryInsert(_params);
			if(success) {
	            resData.put("RESULT_YN", "Y");
	        } else {
	        	resData.put("RESULT_YN", "N");
	        }
		} catch (Exception e) {
			e.printStackTrace();
			resData.put("RESULT_YN", "N");
			resData.put("MESSAGE", e.getMessage());
		}
        
		D(_logger, Thread.currentThread().getStackTrace(), "Response Map >> \n\t\t"+resData  );
		
		return resData;//return mv;
    }

	@RequestMapping(value = "/community/marry_modify.do")
    public ModelAndView marryModify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, CommonException, Exception
    {
        ModelAndView mv = null;
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        boolean success = marryService.marryModify(_params);
        if(success)
        {
            mv = new ModelAndView("/community/cana_registerOk");
            mv.addObject("RESULT_YN", "Y");
        } else {
            mv = new ModelAndView("/community/cana_register");
            mv.addObject("RESULT_YN", "N");
        }
        
		D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
		return mv;
    }

	@RequestMapping(value = "/community/marry_delete.do")
    public void marryDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, Exception
    {
        ModelAndView mv = new ModelAndView("/member/my_cana");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        boolean success = marryService.marryDelete(_params);
        mv.addObject("success", Boolean.valueOf(success));
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/member/my_cana.do");
        dispatcher.forward(request, response);
    }

	/*
	 * 목록을 조회하는 내부 공통
	 */
	private void selectMarryList(Map _params, ModelAndView mv) throws Exception {
		
		D(_logger, Thread.currentThread().getStackTrace(), "List select start..." );
		
		java.util.List marryList = marryService.marryList(_params);
        int totalCount = marryService.marryListCount(_params);
        
        Paging paging = new Paging();
        paging.setPageNo(ipnull(_params, "pageNo" , 1));
        paging.setPageSize(ipnull(_params, "pageSize" , 20));
        paging.setTotalCount(totalCount);
        
        mv.addObject("_params", _params);
        mv.addObject("marryList", marryList);
        mv.addObject("paging", paging);
        
        D(_logger, Thread.currentThread().getStackTrace(), "List select ended..." );
	}
	
	@RequestMapping(value = "/admin/board/marry_list.do")
    public ModelAndView adm_marry_list(HttpServletRequest request) throws Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/marry_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        String search_gubun1 = pnull(_params.get("searchGubun1"));
        String search_gubun2 = pnull(_params.get("searchGubun2"));
        String search_gubun3 = pnull(_params.get("searchGubun3"));
        String search_date = pnull(_params.get("searchDate"));
        String search_text = pnull(_params.get("searchText"));
        
        _params.put("page_type", "admin");
        
        selectMarryList(_params, mv);
        
        mv.addObject("search_gubun1", search_gubun1);
        mv.addObject("search_gubun2", search_gubun2);
        mv.addObject("search_gubun3", search_gubun3);
        mv.addObject("search_date", search_date);
        mv.addObject("search_text", search_text);
        
		D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
		return mv;
    }

	/*
	 * 카나강좌 일정&시간표 조회
	 */
	private void selectCanaLectureAndTimetable(Map _params, ModelAndView mv) {
		java.util.List marry_date = marryService.marryDList(_params);
        java.util.List marry_time = marryService.marryTList(_params);
        Map marry_guide = marryService.marryGuideContents(_params);
        
        mv.addObject("marry_date", marry_date);
        mv.addObject("marry_time", marry_time);
        mv.addObject("marryGContents", marry_guide);
	}
	
	/**
	 * [카나혼인강좌/주말혼인강좌]교육 시간 목록 조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/board/marry_guide.do")
	public ModelAndView adm_marry_guide(HttpServletRequest request) throws Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/marry_guide");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        
        selectCanaLectureAndTimetable(_params, mv);
        mv.addObject("_params", _params);
        
		D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
		return mv;
    }

	/**
	 * [카나혼인강좌/주말혼인강좌]교육 시간 수정
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws CommonException
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/board/marry_guide_modify.do")
    public void adm_marry_guide_modify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, CommonException, Exception
    {
        
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        
        try {
            marryService.marryGTimeUpsert(_params);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR Controller:", e);
        }
        
        //
        request.setAttribute("type", "1");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/board/marryguide_list.do");
        dispatcher.forward(request, response);
    }

//	@RequestMapping(value = "/admin/board/marry_guide_Datedelete.do")
//    public void adm_marry_guide_dateDelete(HttpServletRequest request, HttpServletResponse response)
//        throws ServletException, IOException, CommonException, Exception
//    {
//        ModelAndView mv = new ModelAndView("/admin/board/marry_guide");
//        build(request);
//        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
//        
//        try {
//            marryService.marryGDateDelete(_params);
//        } catch(Exception e) {
//        	E(_logger, Thread.currentThread().getStackTrace(), "ERROR Controller:", e);
//        }
//        
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/board/marry_guide.do");
//        dispatcher.forward(request, response);
//        
//    }

	@RequestMapping(value = "/admin/board/marry_guide_Timedelete.do")
    public void adm_marry_guide_timeDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, CommonException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/marry_guide");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        try {
            marryService.marryGTimeDelete(_params);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR Controller:", e);
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/board/marry_guide.do");
        dispatcher.forward(request, response);
    }

	@RequestMapping(value = "/admin/board/marry_view.do")
    public ModelAndView marryView(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, CommonException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/marry_view");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        Map marryContents = marryService.marryContents(_params);
        mv.addObject("_params", _params);
        
        _params.put("LEVEL", "2");
        java.util.List _1x00xList = admBonNoticeService._1x00xList(_params);
        mv.addObject("CHURCH_LIST", _1x00xList);
        
        java.util.List lectureList = marryService.marryLectureList(_params);
        java.util.List lectureList2 = marryService.marryLectureList2(_params);
        mv.addObject("LECTURE_LIST", lectureList);
        mv.addObject("LECTURE_LIST2", lectureList2);
        mv.addObject("marryContents", marryContents);
        
		D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
		return mv;
    }

	@RequestMapping(value = "/admin/board/marry_insert.do")
    public ModelAndView marryAdminInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException,  Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/marry_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        
        try {
            marryService.marryInsert(_params);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR Controller:", e);
        }
        
        selectMarryList(_params, mv);
        return mv;
    }

	@RequestMapping(value = "/admin/board/marry_modify.do")
    public ModelAndView marryAdminModify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, CommonException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/marry_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        boolean success = marryService.marryModify(_params);

        D(_logger, Thread.currentThread().getStackTrace(), "Controller Call Complete." );
        
        selectMarryList(_params, mv);
        return mv;
    }

	@RequestMapping(value = "/admin/board/marry_delete.do")
    public ModelAndView marryAdminDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/marry_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        
        boolean success = marryService.marryDelete(_params);

        selectMarryList(_params, mv);
        return mv;
    }

}
