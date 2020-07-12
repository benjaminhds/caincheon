// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmGongsoController.java

package kr.caincheon.church.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.service.AdmGongsoService;

@Controller
public class AdmGongsoController extends CommonController
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="admGongsoService")
    private AdmGongsoService admGongsoService;
    
	/*
	 * 공소목록
	 */
	@RequestMapping(value = "/admin/church/gongso_list.do")
    public ModelAndView admGongsoList(HttpServletRequest request)throws ServletException, Exception
    {
		build(request);
		D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        // service call
        CommonDaoDTO dto = admGongsoService.admGongsoList(_params);
        
        // response
        ModelAndView mv = new ModelAndView("/admin/church/gongso_list");
        mv.addObject("pageNo", pnull(_params, "pageNo"));
        mv.addObject("_params", _params);
        mv.addObject("daoTotalCount", dto.daoTotalCount);
        mv.addObject("paging",        dto.paging);
        mv.addObject("admGongsoList", dto.daoList);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t"+mv  );
        
        return mv;
    }

	/*
	 * 공소보기
	 */
	@RequestMapping(value = "/admin/church/gongso_view.do")
    public ModelAndView admGongsoContents(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
		build(request);
		D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        _params.put("LEVEL", "2");
        
        // service call
        CommonDaoDTO dto = admGongsoService.admGongsoContents(_params);
        
        // response
        ModelAndView mv = new ModelAndView("/admin/church/gongso_view");
        mv.addObject("_params", _params);
        mv.addObject("admGongsoContents", dto.daoDetailContent); // 공소 정보
        mv.addObject("admGMissaList",  dto.otherList); // 공소 미사 목록
        mv.addObject("_1x00xList",     dto.orgList); // 조직 목록
        mv.addObject("MISSA_LAST_UPD", dto.otherData); // 공소 미사 최종 업데이트
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/church/gongso_insert.do")
    public void admGongsoInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/church/gongso_list");
        build(request);
        _logger.info((new StringBuilder(" ===> [method=")).append(request.getMethod()).append("] _params ? ").append(_params.toString()).toString());
        if(pnull(_params.get("pageNo")).equals(""))
            _params.put("pageNo", "1");
        boolean success = admGongsoService.admGongsoInsert(_params);
        if(success)
            response.sendRedirect("/admin/church/gongso_list.do");
    }

	@RequestMapping(value = "/admin/church/gongso_modify.do")
    public void admGongsoModify(HttpServletRequest request, HttpServletResponse response)
    	throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/church/gongso_list");
        build(request);
        _logger.info((new StringBuilder(" ===> [method=")).append(request.getMethod()).append("] _params ? ").append(_params.toString()).toString());
        if(pnull(_params.get("pageNo")).equals(""))
            _params.put("pageNo", "1");
        boolean success = admGongsoService.admGongsoModify(_params);
        if(success)
            response.sendRedirect("/admin/church/gongso_list.do");
    }

	@RequestMapping(value = "/admin/church/gongso_delete.do")
    public void admGongsoDelete(HttpServletRequest request, HttpServletResponse response)
    	throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/church/gongso_list");
        build(request);
        _logger.info((new StringBuilder(" ===> [method=")).append(request.getMethod()).append("] _params ? ").append(_params.toString()).toString());
        if(pnull(_params.get("pageNo")).equals(""))
            _params.put("pageNo", "1");
        boolean success = admGongsoService.admGongsoDelete(_params);
        if(success)
            response.sendRedirect("/admin/church/gongso_list.do");
    }

	/*
	 * 공소의 미사 정보 등록
	 */
	@RequestMapping(value = "/admin/church/gongsoMissa_insert.do")
	public @ResponseBody Map<String, String> admGongsoMissaInsert(HttpServletRequest request) 
		throws ServletException, Exception
	{
		//request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        _params.put("p_mType", "I");
        String week = pnull(_params.get("week"));
        
        // service call && response message setting
        Map<String, String> json = new HashMap<String, String>();
        try {
			String success = admGongsoService.admGongsoMissaManage(_params);
			if(success.length()>0) {
	        	json.put("status", "success");
	        	json.put("message", "정상적으로 등록 되었습니다.");
	        	json.put("gmi_idx", success);
	        } else {
	        	json.put("status", "fail");
	        	json.put("message", "등록되지 않았습니다.");
	        }
		} catch (Exception e) {
			json.put("status", "fail");
			json.put("message", "삭제되지 않았습니다.["+e.getMessage()+"]");
			e.printStackTrace();
		}
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Response JSON. [json:"+json+"]" );
        
        // response
        return json;
    }

	/*
	 * 공소의 미사 정보 삭제
	 */
	@RequestMapping(value = "/admin/church/gongsoMissa_delete.do")
	public @ResponseBody Map<String, String> admGongsoMissaDelete(HttpServletRequest request) 
    		throws ServletException, IOException, CommonException, Exception
	{
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        _params.put("p_mType", "D");
        
        // service call && response message setting
        Map<String, String> json = new HashMap<String, String>();
		try {
			String success = admGongsoService.admGongsoMissaManage(_params);
			if(success.length()>0) {
	        	json.put("status", "success");
	        	json.put("message", "정상적으로 삭제 되었습니다.");
	        } else {
	        	json.put("status", "fail");
	        	json.put("message", "삭제되지 않았습니다.");
	        }
		} catch (Exception e) {
			json.put("status", "fail");
			json.put("message", "삭제되지 않았습니다.["+e.getMessage()+"]");
			e.printStackTrace();
		}
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Response JSON. [json:"+json+"]" );
        
        // response
        return json;
    }

    
}
