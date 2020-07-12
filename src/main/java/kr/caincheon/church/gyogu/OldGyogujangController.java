// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OldGyogujangController.java

package kr.caincheon.church.gyogu;

import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.utils.UtilDate;
import kr.caincheon.church.gyogu.service.MsgService;
import kr.caincheon.church.news.service.AlbService;
import kr.caincheon.church.service.SchService;

@Controller
public class OldGyogujangController extends CommonController implements OldGyogujangControllerConst
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="albService")
    private AlbService albService;
	
	/*
	 * 역대 교구장 메시지
	 */
	@Resource(name="msgService")
    private MsgService msgService;

	/*
	 * 역대 교구장앨범
	 */
	@Resource(name="nBoardService")
    private NBoardServiceImpl nBoardService;

	/*
	 * 교구장일정
	 */
	@Resource(name="schService")
    private SchService schService;
	
    /*
     * front side :: 역대 교구장(Old Bishop) 보기
     */
	@RequestMapping(value = "/gyogu/ever_list.do")
    public ModelAndView everList(HttpServletRequest request) throws Exception
    {
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        pnullPut(_params, "type", "9");
        pnullPut(_params, "c_idx", "1");
        
        
        ModelAndView mv = null;
        String c_idx = pnull(_params.get("c_idx"));
        if(c_idx.equals("1"))//1대 교구장
            mv = new ModelAndView("/gyogu/ever_list");
        else if(c_idx.equals("2"))//2대 교구장
            mv = new ModelAndView("/gyogu/ever_list_02");
        
        
//        ModelAndView mv = null;
//        String e_idx = pnull(_params.get("e_idx"));
//        if(e_idx.equals("41"))//1대 교구장
//            mv = new ModelAndView("/gyogu/ever_list");
//        else if(e_idx.equals("42"))//2대 교구장
//            mv = new ModelAndView("/gyogu/ever_list_02");
        

        // service call
        // :: 역대교구장 메시지 조회
        _params.put("pageSize", "3");
        List msgList = msgService.msgList(_params);
        int msgTotalCount = msgService.msgListCount(_params);
        
        // :: 역대교구장 앨범 조회
//        Map m = new HashMap();
//        m.put("pageNo",   pnull(_params, "pageNo"));
//        m.put("pageSize", pnull(_params, "pageSize"));
//        m.put("s_gubun",  pnull(_params, "e_idx")); // TODO 코드테이블과 연계되어 수정 필요 (현, 1대 교구장 1, 2대 교구장 2 임)
//        List albList  = albService.oldAlbList(m);
//        int  albCount = albService.oldAlbListCount(m);

//      if(e_idx.equals("1")) paging.setTotalCount(0);
//      else if(e_idx.equals("2")) paging.setTotalCount(daoTotalCount);

//      mv.addObject("b_idx", pnull(_params.get("b_idx")));
      
//      if(e_idx.equals("1")) {
//          mv.addObject("albList", albList);
//      } else if(e_idx.equals("2")) {
//          //mv.addObject("albList", msgList);
//          mv.addObject("paging", paging);
//      }

        _params.put("pageSize", "8");
        _params.put("is_view", "Y");
        callNBoardList(nBoardService, _params, mv, LEFT_MENU_DATA_PG, false, 1);//목록이니까 1개만..
        
        // response
        String schTextGubun = pnull(_params.get("schTextGubun"));
        String schText = pnull(_params.get("schText"));
        
        LinkedHashMap schTextGubunMap = new LinkedHashMap();
        if(schTextGubun.equals("title"))   schTextGubunMap.put("title", "selected"); else schTextGubunMap.put("title", "");
        if(schTextGubun.equals("content")) schTextGubunMap.put("content", "selected"); else schTextGubunMap.put("content", "");
        if(schTextGubun.equals("all"))     schTextGubunMap.put("all", "selected"); else schTextGubunMap.put("all", "");
        
        String strCategoryName = "전체보기";
        
        // params
        mv.addObject("schTextGubun", schTextGubun);
        mv.addObject("schText", schText);
        mv.addObject("strCategoryName", strCategoryName);
        mv.addObject("schTextGubunMap", schTextGubunMap);
        // dao
        mv.addObject("msgList",       msgList);
        mv.addObject("msgTotalCount", msgTotalCount);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv );
        
        return mv;
    }

	/*
     * front side :: 역대 교구장(Old Bishop) 보기
     */
	@RequestMapping(value = "/gyogu/ever_view.do")
    public ModelAndView everContents(HttpServletRequest request) throws Exception
    {
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/gyogu/ever_view");
        
        // service call
        //Map content  = msgService.msgContents(_params);
        //Map content1 = albService.oldAlbContents(_params);
        callNBoardContents(nBoardService, _params, mv, LEFT_MENU_DATA_PG, false, Integer.parseInt(ATTACHED_FILE_COUNT));
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv );
        
        return mv;
    }

	@RequestMapping(value = "/gyogu/intro_04.do")
    public ModelAndView intro04(HttpServletRequest request)
    	throws ServletException, ParseException, Exception
    {
        ModelAndView mv = new ModelAndView("/gyogu/intro_04");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        String srch_ym = pnull(_params.get("srch_ym"));
        if(srch_ym.length() == 0)
            srch_ym = UtilDate.getDateFormat("yyyy-MM");
        else
        if(srch_ym.length() != 7)
            throw new ParseException((new StringBuilder("조회하는 년월의 데이터 형식이 맞지 않음 [TYPE:YYYY-MM, ")).append(srch_ym).append("]").toString(), -1);
        srch_ym = srch_ym.replace(".", "-");
        _params.put("srch_ym", srch_ym);
        Map map = null;
        
        try {
        	map = schService.scheduleByDiary(_params);
            //map = albService.scheduleByDiary(_params);
        } catch(Exception e) {
            map = new HashMap();
            _logger.error(e);
        }
        mv.addObject("L_DIARY", map.get("DIARY"));
        mv.addObject("M_SCHEDULE", map.get("SCHEDULE"));
        mv.addObject("srch_ym", srch_ym.replace("-", "."));
        mv.addObject("PREV_MONTH", UtilDate.moveMonthOfAddition("yyyy-MM", srch_ym, -1).replace("-", "."));
        mv.addObject("NEXT_MONTH", UtilDate.moveMonthOfAddition("yyyy-MM", srch_ym, 1).replace("-", "."));
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv );
        
        return mv;
    }

	@RequestMapping(value = "/gyogu/intro_04_view.do")
    public ModelAndView intro04view(HttpServletRequest request)
    		throws ServletException, ParseException, Exception
    {
        ModelAndView mv = new ModelAndView("/gyogu/intro_04_view");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        Map map = null;
        try {
            map = albService.scheduleContents(_params);
        } catch(Exception e) {
            map = new HashMap();
            _logger.error(e);
        }
        mv.addObject("_params", _params);
        mv.addObject("M_SCHEDULE", map);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv );
        
        return mv;
    }

	
	

	/*
	 * 역대교구장 앨범 목록 조회
	 */
	@RequestMapping(value = "/admin/board/oldAlb_list.do")
    public ModelAndView oldAlbList(HttpServletRequest request) throws ServletException, Exception
    {
		//request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );

        ModelAndView mv = new ModelAndView("/admin/board/oldAlb_list");
        
        
        // service call
        //pnullPut(_params, "LV1", "02' AND LV2!='00' AND LV3!='000"); // 조직 조회가 true라면, 조직 조회 파라메터 설정
        _params.put("is_view", "Y','N");
        _params.put("TOP_COUNT", "100");
        CommonDaoDTO dto = callNBoardList(nBoardService, _params, mv, LEFT_MENU_DATA_PG, false, Integer.parseInt(ATTACHED_FILE_COUNT));
		
        // response
        String c_idx = pnull(_params.get("c_idx"));
        String s_search    = pnull(_params.get("s_search"));
        String searchText  = pnull(_params.get("searchText"));
        
        Map<String,String> s_searchMap = new LinkedHashMap<String,String>();
        s_searchMap.put("searchText",  searchText);
        if(s_search.equals("title"))   s_searchMap.put("title",   "selected"); else s_searchMap.put("title", "");
        if(s_search.equals("content")) s_searchMap.put("content", "selected"); else s_searchMap.put("content", "");
        
        s_searchMap.put("c_idx", c_idx);
        if("1".equals(c_idx)) s_searchMap.put("1", "selected"); else s_searchMap.put("1", "");
        if("2".equals(c_idx)) s_searchMap.put("2", "selected"); else s_searchMap.put("2", "");
        
        mv.addObject("s_searchMap", s_searchMap);
        mv.addObject("_params", _params);
        mv.addObject("paging",  dto.paging);
        mv.addObject("LIST",    dto.daoList);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/*
	 * 역대교구장 앨범 특정 컨텐츠 보기
	 */
	@RequestMapping(value = "/admin/board/oldAlb_view.do")
    public ModelAndView oldAlbView(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
		// request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/admin/board/oldAlb_view");

        // service call
        CommonDaoDTO dto = callNBoardContents(nBoardService, _params, mv, LEFT_MENU_DATA_PG, false, Integer.parseInt(ATTACHED_FILE_COUNT));
        
        // response
        //Map albContents = albService.oldAlbContents(_params);
        mv.addObject("_params", _params);
        mv.addObject("CONTENTS", dto.daoDetailContent);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/*
	 * 역대교구장 앨범 등록
	 */
	@RequestMapping(value = "/admin/board/oldAlb_insert.do")
    public void oldAlbInsert(Map map, HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/oldAlb_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );

        
        
        boolean success = false;
        try {
            //success = albService.oldAlbInsert(_params, request);
        	success = nBoardService.nboardInsert(_params, LEFT_MENU_DATA_PG, request);
        } catch(Exception e) {
        	e.printStackTrace();
        }
        if(success)
            response.sendRedirect("/admin/board/oldAlb_list.do");
        else
            response.sendRedirect("/admin/board/oldAlb_view.do");
    }

	/*
	 * 역대교구장 앨범 수정
	 */
	@RequestMapping(value = "/admin/board/oldAlb_modify.do")
    public void oldAlbModify(Map map, HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/oldAlb_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );

        

        boolean success = false;
        try {
            //success = albService.oldAlbModify(_params, request);
        	success = nBoardService.nboardModify(_params, LEFT_MENU_DATA_PG, request);
        } catch(Exception e) {
            e.printStackTrace();
        }
        if(success)
            response.sendRedirect("/admin/board/oldAlb_list.do");
        else
            response.sendRedirect("/admin/board/oldAlb_view.do");
    }

	/*
	 * 역대교구장 앨범 삭제
	 */
	@RequestMapping(value = "/admin/board/oldAlb_delete.do")
    public void oldAlbDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/oldAlb_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );

        

        boolean success = false;
        try {
            //success = albService.oldAlbDelete(_params);
        	success = nBoardService.nboardDelete(_params, LEFT_MENU_DATA_PG);
        } catch(Exception e) {
            e.printStackTrace();
        }
        if(success)
            response.sendRedirect("/admin/board/oldAlb_list.do");
    }

    
}
