// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgHierarchyController.java

package kr.caincheon.church.controller;

import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.Paging;
import kr.caincheon.church.common.utils.UtilString;
import kr.caincheon.church.service.AdmDepartmentService;

@Controller
public class AdmOrgController extends CommonController
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="admDepartmentService")
    private AdmDepartmentService admDepartmentService;
	
    /**
     * 관리자 :: 조직코드관리 > 임지코드관리
     * @param request
     * @return
     * @throws CommonException
     */
    @RequestMapping(value = "/admin/org/org_list.do")
    public ModelAndView orgList(HttpServletRequest request)
    	throws ServletException, Exception
    {
		// request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        // service call 
        java.util.List orgList = admDepartmentService.orgList(_params);
        int totalCount = admDepartmentService.orgListCount(_params);
        java.util.List welfareCodeList = admDepartmentService.welfareCodeList(_params);
        
        // response
        
    	ModelAndView mv = new ModelAndView("/admin/org/org_list");
        
        String searchGubun1 = UtilString.pnull(_params.get("searchGubun1"));
        String searchGubun2 = UtilString.pnull(_params.get("searchGubun2"));
        String searchText = UtilString.pnull(_params.get("searchText"));
        
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
            schTextGubunMap2.put("name1", "selected");
        else
            schTextGubunMap2.put("name", "");
        if(searchGubun2.equals("2baptismal_name"))
            schTextGubunMap2.put("baptismal_name", "selected");
        else
            schTextGubunMap2.put("baptismal_name", "");

        // 
        Paging paging = new Paging();
        paging.setPageNo(Integer.parseInt(UtilString.pnull(_params.get("pageNo"), "1")));
        paging.setPageSize(10);
        paging.setTotalCount(totalCount);
        
        //
        mv.addObject("searchText", searchText);
        mv.addObject("schTextGubunMap1", schTextGubunMap1);
        mv.addObject("schTextGubunMap2", schTextGubunMap2);
        mv.addObject("orgList", orgList);
        mv.addObject("paging", paging);
        mv.addObject("welfareCodeList", welfareCodeList);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

    /*
     * 관리자 :: 조직코드관리 > 임지코드추가
     */
    @RequestMapping(value = "/admin/org/org_insert.do")
    public ModelAndView admOrgInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception, CommonException
    {
		// request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        // service call
        
        String strReturn = admDepartmentService.admOrgManage(_params);
        
        // response
        ModelAndView mv = new ModelAndView("/admin/org/org_list");
        if(strReturn.substring(0, 3).equals("ERR"))
        {
            mv.addObject("CDD", "FAIL");
            mv.addObject("MSG", (new StringBuilder("\uB4F1\uB85D \uBBF8\uC644\uB8CC\uB418\uC5C8\uC2B5\uB2C8\uB2E4.[")).append(strReturn).append("]").toString());
        } else
        {
            mv.addObject("CDD", "SUCC");
            mv.addObject("MSG", strReturn);
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

    /*
     * 관리자 :: 조직코드관리 > 임지코드수정
     */
    @RequestMapping(value = "/admin/org/org_update.do")
    public ModelAndView admOrgUpdate(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception, CommonException
    {
		// request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        // service call
        
        String strReturn = admDepartmentService.admOrgManage(_params);
        
        // response
        ModelAndView mv = new ModelAndView("/admin/org/org_list");
        if(strReturn.substring(0, 3).equals("ERR")) {
            mv.addObject("CDD", "FAIL");
            mv.addObject("MSG", (new StringBuilder("\uB4F1\uB85D \uBBF8\uC644\uB8CC\uB418\uC5C8\uC2B5\uB2C8\uB2E4.[")).append(strReturn).append("]").toString());
        } else {
            mv.addObject("CDD", "SUCC");
            mv.addObject("MSG", strReturn);
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

    @RequestMapping(value = "/admin/org/org_delete.do")
    public ModelAndView admOrgManage(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception, CommonException
    {
        ModelAndView mv = new ModelAndView("/admin/org/org_list");
        build(request);
        _logger.info((new StringBuilder(" ===> [method=")).append(request.getMethod()).append("] _params ? ").append(_params.toString()).toString());
        if(UtilString.pnull(_params.get("pageNo")).equals(""))
            _params.put("pageNo", "1");
        String strReturn = admDepartmentService.admOrgManage(_params);
        
        if(strReturn.equals("DELOK")) {
            mv.addObject("CDD", "SUCC");
            mv.addObject("MSG", strReturn);
        } else {
            mv.addObject("CDD", "FAIL");
            mv.addObject("MSG", (new StringBuilder("\uC0AD\uC81C \uBBF8\uC644\uB8CC\uB418\uC5C8\uC2B5\uB2C8\uB2E4.[")).append(strReturn).append("]").toString());
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

}
