// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MemerBondangManagementController.java

package kr.caincheon.church.church;

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
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.controller.AdmBonNoticeControllerConst;
import kr.caincheon.church.controller.AdmChurchAlbumControllerConst;
import kr.caincheon.church.service.AdmChurchService;

@Controller
public class MemerBondangManagementController extends CommonController implements AdmBonNoticeControllerConst
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="admChurchService")
    private AdmChurchService admChurchService;

	@Resource(name="nBoardService")
    private NBoardServiceImpl nBoardService;
	
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
	@RequestMapping(value = "/church_member/church_view.do")
    public ModelAndView admChurchContents(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
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
	@RequestMapping(value = "/church_member/church_modify.do")
    public ModelAndView admChurchModify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/admin/church/church_view_for_member");

        admChurchService.admChurchModify(_params, request);
        commonInquireChurchContents(_params, mv);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }
	

	/*
	 * 권한 있는 사용자가 본당 정보를 수정할 때 기능 제공
	 */
	@RequestMapping(value = "/church/temp_02_view_insert.do")
    public ModelAndView admBondangAlertInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
		// request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/church/temp_02");
        
        // service call
        
        _params.put("pageSize", "20");
        nBoardService.nboardInsert(_params, LEFT_MENU_DATA_PG, request);
        
        //commonInquireChurchContents(_params, mv);
        
        // response
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }


	/*
	 * 권한 있는 사용자가 본당 정보를 수정할 때 기능 제공
	 */
	@RequestMapping(value = "/church/temp_02_view_update.do")
    public void admBondangManagerAlertUpdate(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
		// request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        // service call
        
        nBoardService.nboardModify(_params, LEFT_MENU_DATA_PG, request);

        // response 
        //ModelAndView mv = new ModelAndView("/church/temp_02");
        //commonInquireChurchContents(_params, mv);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"  );
        
        response.sendRedirect("/church/temp_02.do?pageSize=20&pageNo=1&tabs=&qk=&church_idx="+pnull(_params, "church_idx"));
        
        //return mv;
    }
	

	/*
	 * 본당앨범 수정 : 본당관리자가 front에서 수정
	 */
	@RequestMapping(value = "/church/temp_03_view_insert.do")
    public void admBondangManagerAlumInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
		// request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        // service call
        
        _params.put("pageSize", "8");
        nBoardService.nboardInsert(_params, AdmChurchAlbumControllerConst.LEFT_MENU_DATA_PG, request);

        // response 
        //ModelAndView mv = new ModelAndView("/church/temp_02");
        //commonInquireChurchContents(_params, mv);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"  );
        
        response.sendRedirect("/church/temp_03.do?pageSize=20&pageNo=1&tabs=&qk=&church_idx="+pnull(_params, "church_idx"));
        
        //return mv;
    }
	

	/*
	 * 본당앨범 수정 : 본당관리자가 front에서 수정
	 */
	@RequestMapping(value = "/church/temp_03_view_update.do")
    public void admBondangManagerAlumUpdate(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
		// request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
        // service call
        
        _params.put("pageSize", "8");
        nBoardService.nboardModify(_params, AdmChurchAlbumControllerConst.LEFT_MENU_DATA_PG, request);

        // response 
        //ModelAndView mv = new ModelAndView("/church/temp_02");
        //commonInquireChurchContents(_params, mv);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"  );
        
        response.sendRedirect("/church/temp_03.do?pageSize=20&pageNo=1&tabs=&qk=&church_idx="+pnull(_params, "church_idx"));
        
        //return mv;
    }
	

	/*
	 * 본당 알림 :: 글쓰기 폼 & 조회
	 */
	@RequestMapping(value = "/admin/board/church_view_for_user.do")
    public ModelAndView church_view_for_user(HttpServletRequest request) throws Exception
    {
		build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/admin/board/church_view_for_user");
        
        //templeService.church_view_for_user(_params, LEFT_MENU_DATA_PG, request);

    	//
        String mode = pnull(_params, "mode");//I or U
    	if(mode.charAt(0)=='U' || mode.charAt(0)=='u') {
    		int attachedFileCount = 5;
    		nBoardService.nboardContents(_params, LEFT_MENU_DATA_PG, false, attachedFileCount); 
    	}
    	
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        return mv;
    }

	/* 
	 * 본당 알림 :: 글쓰기 등록/수정 
	 */
	@RequestMapping(value = "/admin/board/church_view_for_user_wu.do")
    public ModelAndView church_view_for_user_write_update(HttpServletRequest request) throws Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        ModelAndView mv = new ModelAndView("/church/temp_02.do?pageSize=20&pageNo="+pnull(_params, "pageNo")+"&tabs=&qk=&church_idx="+pnull(_params, "church_idx"));
        
        
        //templeService.church_view_for_user_write_update(_params, LEFT_MENU_DATA_PG, request);
    	String mode = pnull(_params, "mode");//I or U
    	switch(mode.charAt(0)) {
    	case 'i':
    	case 'I':
    		nBoardService.nboardInsert(_params, LEFT_MENU_DATA_PG, request);
    		break;
    	case 'u':
    	case 'U':
    		nBoardService.nboardModify(_params, LEFT_MENU_DATA_PG, request);
    		break;
    	}
    	
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }
	
    // TODO templeService 사용은 제거 되어야 함.
//	@Resource(name="templeService")
//    private TempleService templeService;
	
}
