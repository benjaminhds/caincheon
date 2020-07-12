// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DocController.java

package kr.caincheon.church.member;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.Const;
import kr.caincheon.church.common.base.Paging;
import kr.caincheon.church.member.service.DocService;
import kr.caincheon.church.service.AdmBonNoticeService;

@Controller
public class DocController extends CommonController
{

    private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="docService")
    private DocService docService;
	
	@Resource(name="admBonNoticeService")
    private AdmBonNoticeService admBonNoticeService;
	
	/*
	 * 내부 페이징 처리 함수
	 */
	private Paging getPaging(Map _params, int totalCount) {
        Paging paging = new Paging();
        paging.setPageNo(ipnull(_params, "pageNo", 1));
        paging.setPageSize(ipnull(_params, "pageSize", 10));
        paging.setTotalCount(totalCount);
        
		return paging; 
	} 
	/*
	 * 목록을 조회하는 내부 함수
	 */
	private void inquireDoctrineList(Map _params, ModelAndView mv) throws Exception {

        List doctrineList = docService.docList(_params);
        int totalCount = docService.docListCount(_params);
        
        mv.addObject("param", _params);
        mv.addObject("doctrineList", doctrineList);
        mv.addObject("paging", getPaging(_params, totalCount));
	}

    /* bjm found */
	@RequestMapping(value = "/community/doctrine_register.do")
    public ModelAndView docRegister(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, Exception
    {
        ModelAndView mv = null;
        build(request);
        _params.put("LEVEL", "2");
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        List _1x00xList = admBonNoticeService._1x00xList(_params);
        if(pnull(_params.get("id")).equals(""))
        {
            mv = new ModelAndView("/community/doctrine_register");
            mv.addObject("query_type", "insert");
            mv.addObject("CHURCH_LIST", _1x00xList);
            mv.addObject("param", _params);
        } else {
            _params.put("approve_yn", "N");
            List docList = docService.docList(_params);
            int totalCount = docService.docListCount(_params);
            
            if(totalCount > 0)
            {
                mv = new ModelAndView("/member/my_doctrine");
                mv.addObject("docList", docList);
                mv.addObject("paging", getPaging(_params, totalCount));
            } else {
                mv = new ModelAndView("/community/doctrine_register");
                Map docContents = docService.docContents(_params);
                mv.addObject("docContents", docContents);
                mv.addObject("query_type", "insert");
                mv.addObject("CHURCH_LIST", _1x00xList);
                mv.addObject("param", _params);
            }
        }
        mv.addObject("query_type", "insert");
        mv.addObject("CHURCH_LIST", _1x00xList);
        mv.addObject("param", _params);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );

        return mv;
    }

	@RequestMapping(value = "/member/my_doctrine.do")
    public ModelAndView docList(HttpServletRequest request) throws Exception
    {
        ModelAndView mv = new ModelAndView("/member/my_doctrine");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        pnullPut(_params, "id", pnull(request.getSession(true).getAttribute(Const.SESSION_KEY_MEM_ID)));
                
        inquireDoctrineList(_params, mv) ;
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );

        return mv;
    }

	@RequestMapping(value = "/community/doctrine_apply.do")
    public ModelAndView docApply(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/community/doctrine_register");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        Map docContents = docService.docContents(_params);
        mv.addObject("_params", _params);
        mv.addObject("query_type", "update");
        _params.put("LEVEL", "2");
        
        List _1x00xList = admBonNoticeService._1x00xList(_params);
        mv.addObject("CHURCH_LIST", _1x00xList);
        mv.addObject("docContents", docContents);

        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );

        return mv;
    }

	/**
	 * 통신교리 신청하기 처리OP
	 */
	@RequestMapping(value = "/community/doctrine_insert.do")
	public @ResponseBody Map<String, Object> docInsert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, Exception, CommonException, Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        // service call
        Map<String, Object> resData = new HashMap<String, Object>();
        boolean success = false;
		try {
			success = docService.docInsert(_params, request);
		} catch (Exception e) {
			e.printStackTrace();
			resData.put("RESULT_YN", "N");
			resData.put("MESSAGE", e.getMessage());
		}
        
        // result
		resData.put("RESULT_YN", success ? "Y" : "N");

        D(_logger, Thread.currentThread().getStackTrace(), "Response Map >> \n\t\t"+resData  );

        return resData;
    }

	@RequestMapping(value = "/community/doctrine_modify.do")
    public ModelAndView docModify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception, CommonException
    {
        ModelAndView mv = null;
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        boolean success = docService.docModify(_params, request);
        if(success)
        {
            mv = new ModelAndView("/community/doctrine_registerOk");
            mv.addObject("success", Boolean.valueOf(success));
            mv.addObject("RESULT_YN", "Y");
        } else
        {
            mv = new ModelAndView("/community/doctrine_register");
            mv.addObject("success", Boolean.valueOf(success));
            mv.addObject("RESULT_YN", "N");
        }

        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );

        return mv;
    }

	@RequestMapping(value = "/myinfo/doctrine_delete.do")
    public void docDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/myinfo/doctrine_apply");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        
        boolean success = docService.docDelete(_params);
        mv.addObject("success", Boolean.valueOf(success));
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/myinfo/doctrine_list.do");
        dispatcher.forward(request, response);
    }

	@RequestMapping(value = "/admin/board/doctrine_list.do")
    public ModelAndView doctrineList(HttpServletRequest request)
    		throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/doctrine_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        _params.put("ADM_YN", "Y");
        
        String searchGubun1 = pnull(_params.get("searchGubun1"));
        String searchGubun2 = pnull(_params.get("searchGubun2"));
        String searchText   = pnull(_params.get("searchText"));
        LinkedHashMap schTextGubunMap1 = new LinkedHashMap();
        if(searchGubun1.equals("1"))
            schTextGubunMap1.put("1", "selected");
        else
            schTextGubunMap1.put("1", "");
        if(searchGubun1.equals("2"))
            schTextGubunMap1.put("2", "selected");
        else
            schTextGubunMap1.put("2", "");
        LinkedHashMap schTextGubunMap2 = new LinkedHashMap();
        if(searchGubun2.equals("name"))
            schTextGubunMap2.put("name", "selected");
        else
            schTextGubunMap2.put("name", "");
        if(searchGubun2.equals("baptismal_name"))
            schTextGubunMap2.put("baptismal_name", "selected");
        else
            schTextGubunMap2.put("baptismal_name", "");
        
        
        List doctrineList = docService.docList(_params);
        int totalCount = docService.docListCount(_params);
        
        mv.addObject("searchText", searchText);
        mv.addObject("schTextGubunMap1", schTextGubunMap1);
        mv.addObject("schTextGubunMap2", schTextGubunMap2);
        mv.addObject("param", _params);
        mv.addObject("doctrineList", doctrineList);
        mv.addObject("paging", getPaging(_params, totalCount));

        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );

        return mv;
    }
	
	@RequestMapping(value = "/admin/board/doctrine_view.do")
    public ModelAndView doctrineView(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/doctrine_view");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        Map docContents = docService.docContents(_params);
        mv.addObject("_params", _params);
        _params.put("LEVEL", "2");
        
        List _1x00xList = admBonNoticeService._1x00xList(_params);
        mv.addObject("CHURCH_LIST", _1x00xList);
        mv.addObject("docContents", docContents);

        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );

        return mv;
    }

	@RequestMapping(value = "/admin/board/doctrine_insert.do")
    public void doctrineInsert(Map map, HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/doctrine_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        boolean success = false;
        
        try {
            success = docService.docInsert(_params, request);
        } catch(Exception exception) { }
        if(success)
            response.sendRedirect("/admin/board/doctrine_list.do");
    }

	@RequestMapping(value = "/admin/board/doctrine_modify.do")
    public void doctrineModify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception, CommonException
    {
        ModelAndView mv = new ModelAndView("/admin/board/doctrine_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        

        boolean success = docService.docModify(_params, request);
        if(success)
            response.sendRedirect("/admin/board/doctrine_list.do");
    }

	@RequestMapping(value = "/admin/board/doctrine_delete.do")
    public void doctrineDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/doctrine_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        

        boolean success = docService.docDelete(_params);
        if(success)
            response.sendRedirect("/admin/board/doctrine_list.do");
    }

}
