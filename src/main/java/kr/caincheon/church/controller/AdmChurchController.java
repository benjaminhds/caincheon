// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MemerBondangManagementController.java

package kr.caincheon.church.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
import kr.caincheon.church.common.base.Paging;
import kr.caincheon.church.service.AdmChurchService;

@Controller
public class AdmChurchController extends CommonController
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="admChurchService")
    private AdmChurchService admChurchService;
	
	/*
	 * 목록을 조회하여 리턴
	 */
	private void commonInquireChurchList(Map _params, ModelAndView mv) throws Exception {
		
		List ChurchList = admChurchService.admChurchList(_params);
        int  totalCount   = admChurchService.admChurchListCount(_params);
        
        Paging paging = new Paging();
        paging.setPageNo(ipnull(_params, "pageNo", 1));
        paging.setPageSize(ipnull(_params, "pageSize", 20));
        paging.setTotalCount(totalCount);
        
        mv.addObject("_params", _params);
        mv.addObject("daoTotalCount", totalCount);
        mv.addObject("paging", paging);
        mv.addObject("admChurchList", ChurchList);
	}
	
	/*
	 * 성당 상세 정보 조회
	 */
	private void commonInquireChurchContents(Map _params, ModelAndView mv) throws Exception {

		CommonDaoDTO dto = admChurchService.admChurchContents(_params);
        
        mv.addObject("D_GROUP_LIST",   dto.daoList );
        mv.addObject("CONTENTS",       dto.daoDetailContent);
        mv.addObject("MISSA_LIST",     dto.otherList);
        mv.addObject("MISSA_LAST_UPD", dto.otherData);
        //mv.addObject("PRIEST_MAP",     dto.otherData1);
        mv.addObject("_params",        _params);
	}
	
	
	@RequestMapping(value = "/admin/church/church_list.do")
    public ModelAndView admChurchList(HttpServletRequest request) throws Exception 
    {
        ModelAndView mv = new ModelAndView("/admin/church/church_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        commonInquireChurchList(_params, mv);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/admin/church/church_view.do")
    public ModelAndView admChurchContents(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, CommonException, Exception 
    {
        ModelAndView mv = new ModelAndView("/admin/church/church_view");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        _params.put("attached_files", "10");//첨부파일 10개
        commonInquireChurchContents(_params, mv);

        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/**
	 * 관리자 :: 정보 관리 > 본당 > 등록
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws CommonException
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/church/church_insert.do")
    public ModelAndView admChurchInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, CommonException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/church/church_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        pnullPut(_params,"pageNo","1");
        pnullPut(_params,"pageSize","20");
        
        admChurchService.admChurchInsert(_params, request);
        //response.sendRedirect("/admin/church/church_list.do");

        commonInquireChurchList(_params, mv);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/**
	 * 관리자 :: 정보 관리 > 본당 > 수정
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws CommonException
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/church/church_modify.do")
    public ModelAndView admChurchPhotoModify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, CommonException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/church/church_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );

        pnullPut(_params,"pageNo","1");
        pnullPut(_params,"pageSize","20");
        
        admChurchService.admChurchModify(_params, request);

        commonInquireChurchList(_params, mv);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/**
	 * 관리자 :: 정보 관리 > 본당 > 삭제
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws CommonException
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/church/church_delete.do")
    public ModelAndView admChurchMissaInfoDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, CommonException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/church/church_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        if(pnull(_params.get("pageNo")).equals(""))
            _params.put("pageNo", "1");
        
        admChurchService.admChurchDelete(_params);
        response.sendRedirect("/admin/church/church_list.do");

        //commonInquireChurchList(_params, mv);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/*
	 * 관리자  > 미사시간을 개별 등록처리 
	 */
	@RequestMapping(value = "/admin/church/churchMissa_insert.do")
    public @ResponseBody Map<String, String> admChurchMissaInsert(HttpServletRequest request)
        throws ServletException, IOException, CommonException, Exception
    {
        build(request);
        _params.put("p_mType", "I");
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        //
        String week = pnull(_params.get("week"));
        //
        Map<String, String> json = new HashMap<String, String>();
        int mi_idx=-1;
		try {
			mi_idx = admChurchService.admChurchMissaManage(_params);
			if(mi_idx > 0) {
				json.put("status", "success");
	        	json.put("mi_idx", ""+mi_idx);
	        	json.put("message", "미사 시간이 등록 되었습니다.");
	        } else {
	        	json.put("status", "fail");
	        	json.put("message", "미사 시간이 등록 되지 않았습니다.");
	        }
		} catch (Exception e) {
			json.put("status", "fail");
			json.put("message", "fail["+e.getMessage()+"]");
			e.printStackTrace();
		}
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response String (by JSON) >> \n\t\t"+json  );
        return json;
    }

	/*
	 * 관리자  > 개별 미사 시간 삭제 
	 */
	@RequestMapping(value = "/admin/church/churchMissa_delete.do")
    public @ResponseBody Map<String, String> admChurchMissaDelete(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException, CommonException, Exception
    {
        ModelAndView mv = null;
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        _params.put("p_mType", "D");
        
        
        Map<String, String> json = new HashMap<String, String>();
        boolean success=false;
		try {
			success = admChurchService.admChurchMissaInfoDelete(_params);
			if(success) {
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
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response JSON >> \n\t\t"+json  );
        return json;
    }

    
}
