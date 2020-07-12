// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmEngageController.java

package kr.caincheon.church.community;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.community.service.CanaMarryGuideService;

@Controller
public class CanaMarryGuideController extends CommonController
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="canaMarryGuideService")
    private CanaMarryGuideService canaMarryGuideService;

	
	/*
	 * 카나혼인강좌 & 약혼자주말신청 목록조회
	 */
	@RequestMapping(value = "/admin/board/marryguide_list.do")
    public ModelAndView listCanaEngage(HttpServletRequest request) throws Exception
    {
		ModelAndView mv = new ModelAndView("/admin/board/marryguide_list");
		build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        CommonDaoDTO dto = canaMarryGuideService.listMarryGuide(_params);
        mv.addObject("LIST", dto.daoList);
		mv.addObject("_params", _params);
        
		D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
		return mv;
    }
	
	
	/*
	 * 카나혼인강좌 & 약혼자주말신청 등록
	 */
	@RequestMapping(value = "/admin/board/marryguide_insert.do")
    public ModelAndView insertCanaEngage(HttpServletRequest request) throws Exception
    {
		ModelAndView mv = new ModelAndView("/admin/board/marryguide_list");
		build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        
        canaMarryGuideService.insertMarryGuide(_params);
        
        CommonDaoDTO dto = canaMarryGuideService.listMarryGuide(_params);
        mv.addObject("LIST", dto.daoList);
		mv.addObject("_params", _params);
        
		D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
		return mv;
    }
	
	

	/*
	 * 카나혼인강좌 & 약혼자주말신청 수정
	 */
	@RequestMapping(value = "/admin/board/marryguide_update.do")
    public ModelAndView updateCanaEngage(HttpServletRequest request) throws Exception
    {
		ModelAndView mv = new ModelAndView("/admin/board/marryguide_list");
		build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        
        canaMarryGuideService.updateMarryGuide(_params);
        
        CommonDaoDTO dto = canaMarryGuideService.listMarryGuide(_params);
        mv.addObject("LIST", dto.daoList);
		mv.addObject("_params", _params);
        
		D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
		return mv;
    }
	
	

	/*
	 * 카나혼인강좌 & 약혼자주말신청 삭제
	 */
	@RequestMapping(value = "/admin/board/marryguide_delete.do")
    public ModelAndView deleteCanaEngage(HttpServletRequest request) throws Exception
    {
		ModelAndView mv = new ModelAndView("/admin/board/marryguide_list");
		build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        
        try {
			canaMarryGuideService.deleteMarryGuide(_params);
        } catch (CommonException e) {
        	mv.addObject("ERR_MSG", e.getErrMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        CommonDaoDTO dto = canaMarryGuideService.listMarryGuide(_params);
        mv.addObject("LIST", dto.daoList);
		mv.addObject("_params", _params);
        
		D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
		return mv;
    }
	
    
}
