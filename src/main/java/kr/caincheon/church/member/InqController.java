package kr.caincheon.church.member;

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
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.Const;
import kr.caincheon.church.controller.InqControllerConst;
import kr.caincheon.church.member.service.InqService;

/**
 * 나누고싶은 이야기(1:1 문의하기) controller
 * @author benjamin
 */
@Controller
public class InqController extends CommonController implements InqControllerConst
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="inqService")
    private InqService inqService;

    /*
     * 1:1 문의하기 폼
     */
	@RequestMapping(value = "/community/tale.do")
    public ModelAndView loadTaleForm(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/community/tale");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        mv.addObject("query_type", "insert");
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/*
	 * 1:1 문의글을 DB에 등록하기
	 */
	@RequestMapping(value = "/community/tale_insert.do")
    public void inqSave(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception, CommonException
    {
        ModelAndView mv = null;
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );

        
        
        boolean success = false;
        String captchaP = pnull(_params.get("captcha"));
        String captcha  = pnull(request.getSession(true).getAttribute("correctAnswer"));
        
        D(_logger, Thread.currentThread().getStackTrace(), "[captcha:"+captcha+", captchaP:"+captchaP+"]" );
        
        //if(captcha.equals(captchaP))
        if(true)// TODO captcha 생성 및 검증 로직 확인 필요
        {
        	boolean isAdmin = false;
            success = inqService.inqInsert(_params, isAdmin);
            request.getRequestDispatcher("/member/my_tale.do").forward(request, response);
        } else {
            mv = new ModelAndView("/community/tale");
            mv.addObject("_params", _params);
            //request.setAttribute(name, o);
            request.getRequestDispatcher("/community/tale.do").forward(request, response);
        }
    }

	/*
	 * 
	 */
	@RequestMapping(value = "/member/my_tale.do")
    public ModelAndView inqList(HttpServletRequest request)
    		throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/member/my_tale");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );

        pnullPut(_params, "id", pnull(request.getSession(true).getAttribute(Const.SESSION_KEY_MEM_ID)));
        
        // 
        boolean isAdmin = false;
        CommonDaoDTO dto = inqService.inqList(_params, isAdmin);
        
        //
        mv.addObject("param", _params);
        mv.addObject("inqList", dto.daoList);
        mv.addObject("paging", dto.paging);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/member/my_tale_edit.do")
    public ModelAndView inqApply(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/member/my_tale_edit");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        boolean isAdmin = false;
        Map inqContents = inqService.inqContents(_params, isAdmin);
        mv.addObject("_params", _params);
        mv.addObject("inqContents", inqContents);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/myinfo/inq_insert.do")
    public void inqInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception, CommonException
    {
        ModelAndView mv = new ModelAndView("/member/tale_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        if(pnull(_params.get("pageNo")).equals(""))
            _params.put("pageNo", "1");
        
        boolean isAdmin = false;
        boolean success = inqService.inqInsert(_params, isAdmin);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/myinfo/inq_list.do");
        dispatcher.forward(request, response);
    }

	@RequestMapping(value = "/community/tale_edit.do")
    public ModelAndView inqApplyUpdate(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/community/tale");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        boolean isAdmin = false;
        Map inqContents = inqService.inqContents(_params, isAdmin);
        mv.addObject("_params", _params);
        mv.addObject("query_type", "update");
        mv.addObject("inqContents", inqContents);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/community/tale_modify.do")
    public void inqModify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/member/my_tale");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        if(pnull(_params.get("pageNo")).equals(""))
            _params.put("pageNo", "1");
        
        boolean isAdmin = false;
        boolean success = inqService.inqModify(_params, isAdmin);
        mv.addObject("success", Boolean.valueOf(success));
        RequestDispatcher dispatcher = request.getRequestDispatcher("/member/my_tale.do");
        dispatcher.forward(request, response);
    }

	@RequestMapping(value = "/member/inq_delete.do")
    public void inqDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/member/my_tale");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        if(pnull(_params.get("pageNo")).equals(""))
            _params.put("pageNo", "1");
        
        boolean isAdmin = false;
        boolean success = inqService.inqDelete(_params, isAdmin);
        mv.addObject("success", Boolean.valueOf(success));
        RequestDispatcher dispatcher = request.getRequestDispatcher("/member/my_tale.do");
        dispatcher.forward(request, response);
    }

	/*
	 * 나누고 싶은 이야기
	 */
	@RequestMapping(value = "/admin/board/inquire_list.do")
    public ModelAndView admInqList(HttpServletRequest request)
    		throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/inquire_list");
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        _params.put("ADM_YN", "Y");
        String delete_yn = pnull(_params.get("delete_yn"));
        
        // call a service
        boolean isAdmin = true;
        CommonDaoDTO dto = inqService.inqList(_params, isAdmin);
        
        mv.addObject("param", _params);
        mv.addObject("delete_yn", delete_yn);
        mv.addObject("inqList", dto.daoList);
        mv.addObject("paging", dto.paging);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/**
	 * 관리자) 회원서비스관리 > 나누고싶은이야기 : 글 보기
	 */
	@RequestMapping(value = "/admin/board/inquire_view.do")
    public ModelAndView admInqView(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/board/inquire_view");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        boolean isAdmin = true;
        Map inqContents = inqService.inqContents(_params, isAdmin);
        mv.addObject("_params", _params);
        mv.addObject("inqContents", inqContents);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/board/inquire_modify.do")
    public void admInqModify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        _params.put("ADM_YN", "Y");
        
        boolean isAdmin = true;
        boolean result = inqService.inqModify(_params, isAdmin);
        if(result) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/board/inquire_list.do");
            dispatcher.forward(request, response);
        }
    }

	@RequestMapping(value = "/admin/board/inquire_delete.do")
    public void admInqDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        boolean isAdmin = true; 
        boolean result = inqService.inqDelete(_params, isAdmin);
        
        //if(result) 
        {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/board/inquire_list.do");
            dispatcher.forward(request, response);
        }
    }

    
}
