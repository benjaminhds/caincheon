// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MsgController.java

package kr.caincheon.church.gyogu;

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

import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.Paging;
import kr.caincheon.church.gyogu.service.MsgService;

@Controller
public class MsgController extends CommonController implements MsgControllerConst
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="msgService")
    private MsgService msgService;

    /* bjm found */
	@RequestMapping(value = "/gyogu/msg_list.do")
    public ModelAndView msgList(HttpServletRequest request)
    		throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/gyogu/msg_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        String strCategoryName = "";
        if(pnull(_params.get("type")).equals("1"))
            strCategoryName = "\uAD50\uC11C";
        else
        if(pnull(_params.get("type")).equals("2"))
            strCategoryName = "\uC11C\uD55C";
        else
        if(pnull(_params.get("type")).equals("3"))
            strCategoryName = "\uB2F4\uD654\uBB38";
        else
            strCategoryName = "\uC804\uCCB4\uBCF4\uAE30";
        
        String schTextGubun = pnull(_params.get("schTextGubun"));
        String schText = pnull(_params.get("schText"));
        
        LinkedHashMap schTextGubunMap = new LinkedHashMap();
        if(schTextGubun.equals("title"))
            schTextGubunMap.put("title", "selected");
        else
            schTextGubunMap.put("title", "");
        if(schTextGubun.equals("content"))
            schTextGubunMap.put("content", "selected");
        else
            schTextGubunMap.put("content", "");
        if(schTextGubun.equals("all"))
            schTextGubunMap.put("all", "selected");
        else
            schTextGubunMap.put("all", "");
        
        
        // 
        java.util.List msgList = msgService.msgList(_params);
        int totalCount = msgService.msgListCount(_params);
        
        //
        Paging paging = new Paging();
        paging.setPageNo(ipnull(_params, "pageNo", 1));
        paging.setPageSize(ipnull(_params, "pageSize", 20));
        paging.setTotalCount(totalCount);
        mv.addObject("type", pnull(_params.get("type")));
        mv.addObject("strCategoryName", strCategoryName);
        mv.addObject("schTextGubun", schTextGubun);
        mv.addObject("schText", schText);
        mv.addObject("schTextGubunMap", schTextGubunMap);
        mv.addObject("msgList", msgList);
        mv.addObject("paging", paging);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/*
	 * front에서 메시지 보기
	 * - 가장 최근것을 자동으로 보여주기
	 */
	@RequestMapping(value = "/gyogu/msg_view_recently.do")
    public ModelAndView msgContentsRecentlyOnlyOne(HttpServletRequest request)
    		throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/gyogu/msg_view");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        // 가장 마지막 것을 자동으로 보기 위한 옵션(for dao class)
        _params.put("type", "1");
        _params.put("m_idx", "");
        _params.put("LAST_ONE_VIEW", "TRUE");
        
        Map msgContents = msgService.msgContents(_params);
        mv.addObject("_params", _params);
        mv.addObject("msgContents", msgContents);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }
	
	/*
	 * front에서 메시지 보기
	 */
	@RequestMapping(value = "/gyogu/msg_view.do")
    public ModelAndView msgContents(HttpServletRequest request)
    		throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/gyogu/msg_view");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        Map msgContents = msgService.msgContents(_params);
        mv.addObject("_params", _params);
        mv.addObject("msgContents", msgContents);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/msg_list.do")
    public ModelAndView admMsgList(HttpServletRequest request)
    		throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/msg_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        
        //
        java.util.List msgList = msgService.admMsgList(_params);
        int totalCount = msgService.admMsgListCount(_params);
        
        Paging paging = new Paging();
        paging.setPageNo(ipnull(_params, "pageNo", 1));
        paging.setPageSize(20);
        paging.setTotalCount(totalCount);
        
        mv.addObject("msgList", msgList);
        mv.addObject("paging", paging);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/msg_view.do")
    public ModelAndView admMsgView(HttpServletRequest request)
    		throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/msg_view");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        Map msgContents = msgService.admMsgContents(_params);
        mv.addObject("_params", _params);
        mv.addObject("msgContents", msgContents);
        return mv;
    }

	@RequestMapping(value = "/admin/board/msg_insert.do")
    public void msgInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/msg_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        if(pnull(_params.get("pageNo")).equals(""))
            _params.put("pageNo", "1");
        boolean success = msgService.msgInsert(_params);
        if(success)
            response.sendRedirect("/admin/board/msg_list.do");
    }

	@RequestMapping(value = "/admin/board/msg_modify.do")
    public void msgModify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/msg_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        if(pnull(_params.get("pageNo")).equals(""))
            _params.put("pageNo", "1");
        boolean success = msgService.msgModify(_params);
        if(success)
            response.sendRedirect("/admin/board/msg_list.do");
    }

	@RequestMapping(value = "/admin/board/msg_delete.do")
    public void schDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/msg_list");
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        if(pnull(_params.get("pageNo")).equals(""))
            _params.put("pageNo", "1");
        
        boolean success = msgService.msgDelete(_params);
        if(success) response.sendRedirect("/admin/board/msg_list.do");
    }

}
