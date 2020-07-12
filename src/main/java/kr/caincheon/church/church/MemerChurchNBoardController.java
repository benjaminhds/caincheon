// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MemerBondangManagementController.java

package kr.caincheon.church.church;

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
public class MemerChurchNBoardController extends CommonController
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="admChurchService")
    private AdmChurchService admChurchService;
	
	/*
	 * 성당 상세 정보 조회
	 */
	private void commonInquireChurchContents(Map _params, ModelAndView mv) throws Exception {

		CommonDaoDTO dto = admChurchService.admChurchContents(_params);
        
        mv.addObject("D_GROUP_LIST",   dto.daoList );
        mv.addObject("CONTENTS",       dto.daoDetailContent);
        mv.addObject("MISSA_LIST",     dto.otherList);
        mv.addObject("MISSA_LAST_UPD", dto.otherData);
        mv.addObject("_params",        _params);
	}
	
	
	/*
	 * 권한 있는 사용자가 본당 정보를 수정할 때 조회하는 기능
	 */
	@RequestMapping(value = "/church_member/bondang_alert_view.do")
    public ModelAndView admChurchContents(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, CommonException, Exception 
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/admin/church/church_view_for_member");
        
        commonInquireChurchContents(_params, mv);

        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/*
	 * 권한 있는 사용자가 본당 정보를 수정할 때 기능 제공
	 */
	@RequestMapping(value = "/church_member/bondang_alert_modify.do")
    public ModelAndView admChurchModify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, CommonException, Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/admin/church/church_view_for_member");

        admChurchService.admChurchModify(_params, request);
        commonInquireChurchContents(_params, mv);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

}
