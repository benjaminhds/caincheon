// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EuchaController.java

package kr.caincheon.church.member;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.Const;
import kr.caincheon.church.common.base.Paging;
import kr.caincheon.church.common.utils.UtilDate;
import kr.caincheon.church.common.utils.UtilString;
import kr.caincheon.church.member.service.EuchaService;
import kr.caincheon.church.member.service.MemberService;

/**
 * 호출되는 URL이 없는 Controller에서 호출됨.
 * @author benjamin
 * @deprecated
 */
@Controller
public class EuchaController extends CommonController
{

    private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="euchaService")
    private EuchaService euchaService;
	
	@Resource(name="memberService")
    private MemberService memberService;

    /* bjm found */
	@RequestMapping(value = "/member/my_euca.do")
    public ModelAndView euchaList(HttpServletRequest request)
    		throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/member/my_euca");
        build(request);
        _logger.info((new StringBuilder(" ===> [method=")).append(request.getMethod()).append("] _params ? ").append(_params.toString()).toString());
        if(UtilString.pnull(_params.get("pageNo")).equals(""))
            _params.put("pageNo", "1");
        if(UtilString.pnull(_params.get("id")).equals(""))
            _params.put("id", UtilString.pnull(request.getSession(true).getAttribute(Const.SESSION_KEY_MEM_ID)));
        java.util.List euchaList = euchaService.euchaList(_params);
        int totalCount = euchaService.euchaListCount(_params);
        Paging paging = new Paging();
        paging.setPageNo(Integer.parseInt(UtilString.pnull(_params.get("pageNo"), "1")));
        paging.setPageSize(10);
        paging.setTotalCount(totalCount);
        mv.addObject("param", _params);
        mv.addObject("euchaList", euchaList);
        mv.addObject("paging", paging);
        return mv;
    }

	@RequestMapping(value = "/community/eucharist_apply.do")
    public ModelAndView euchaApplyInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/community/eucharist_apply");
        build(request);
        if(UtilString.pnull(_params.get("id")).equals(""))
            _params.put("id", UtilString.pnull(request.getSession(true).getAttribute(Const.SESSION_KEY_MEM_ID)));
        _logger.info((new StringBuilder(" ===> [method_eucha_apply.do=")).append(request.getMethod()).append("] _params ? ").append(_params.toString()).toString());
        Map memberInfo = null;
        try
        {
            memberInfo = memberService.selectMemberInfo(_params);
        }
        catch(Exception e)
        {
            memberInfo = new HashMap();
        }
        Map euchaContents = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        mv.addObject("MEMINFO_MAP", memberInfo);
        mv.addObject("_params", _params);
        mv.addObject("query_type", UtilString.pnull(_params.get("query_type"), "insert"));
        mv.addObject("APPLY_DAY", df.format(new Date()));
        mv.addObject("euchaContents", euchaContents);
        return mv;
    }

	@RequestMapping(value = "/community/eucharist_view.do")
    public ModelAndView euchaApply(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/community/eucharist_apply");
        build(request);
        _logger.info((new StringBuilder(" ===> [method_eucha_apply.do=")).append(request.getMethod()).append("] _params ? ").append(_params.toString()).toString());
        Map euchaContents = euchaService.euchaContents(_params);
        Map memberInfo = null;
        _logger.info((new StringBuilder(" ===> [eucharist_view.do=")).append(request.getMethod()).append("] euchaContents ? ").append(euchaContents.toString()).toString());
        mv.addObject("MEMINFO_MAP", euchaContents);
        mv.addObject("_params", _params);
        mv.addObject("query_type", UtilString.pnull(_params.get("query_type")));
        mv.addObject("APPLY_DAY", UtilDate.getYMD());
        mv.addObject("euchaContents", euchaContents);
        return mv;
    }

    @RequestMapping(value = "/community/eucharist_insert.do")
    public void euchaInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception, CommonException
    {
        ModelAndView mv = new ModelAndView("/member/my_euca");
        build(request);
        if(UtilString.pnull(_params.get("pageNo")).equals(""))
            _params.put("pageNo", "1");
        if(UtilString.pnull(_params.get("id")).equals(""))
            _params.put("id", UtilString.pnull(request.getSession(true).getAttribute(Const.SESSION_KEY_MEM_ID)));
        _logger.info((new StringBuilder(" ===> [method=")).append(request.getMethod()).append("] sessioon ? ").append(request.getSession().getAttribute("sessionMemId")).toString());
        _logger.info((new StringBuilder(" ===> [method=")).append(request.getMethod()).append("] _params ? ").append(_params.toString()).toString());
        boolean success = euchaService.euchaInsert(_params);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/member/my_euca.do");
        dispatcher.forward(request, response);
    }

    @RequestMapping(value = "/community/eucharist_modify.do")
    public void euchaModify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/myinfo/my_euca");
        build(request);
        _logger.info((new StringBuilder(" ===> [method=")).append(request.getMethod()).append("] _params ? ").append(_params.toString()).toString());
        if(UtilString.pnull(_params.get("pageNo")).equals(""))
            _params.put("pageNo", "1");
        boolean success = euchaService.euchaModify(_params);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/member/my_euca.do");
        dispatcher.forward(request, response);
    }

    @RequestMapping(value = "/community/eucharist_delete.do")
    public void euchaDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/myinfo/eucha_list");
        build(request);
        _logger.info((new StringBuilder(" ===> [method=")).append(request.getMethod()).append("] _params ? ").append(_params.toString()).toString());
        if(UtilString.pnull(_params.get("pageNo")).equals(""))
            _params.put("pageNo", "1");
        boolean success = euchaService.euchaDelete(_params);
        mv.addObject("success", Boolean.valueOf(success));
        RequestDispatcher dispatcher = request.getRequestDispatcher("/myinfo/eucha_list.do");
        dispatcher.forward(request, response);
    }

}
