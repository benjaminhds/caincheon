// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmMemberController.java

package kr.caincheon.church.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.admin.serivce.OrgHierarchyService;
import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.Const;
import kr.caincheon.church.common.base.Paging;
import kr.caincheon.church.service.AdmMemberService;
import kr.caincheon.church.service.CommonUtilService;

@Controller
public class AdmMemberController extends CommonController
{
	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="admMemberService")
    private AdmMemberService admMemberService;
    
    @Resource(name="commonUtilService")
    private CommonUtilService commonUtilService;
    
    
    @Resource(name="orgHierarchyService")
    private OrgHierarchyService orgHierarchyService;
    
    /**
     * 
     * @date 2017.10.10 
     * 
     */
    @RequestMapping(value = "/admin/member/admmember_list.do")
    public ModelAndView admMemberList(HttpServletRequest request)throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/member/admmember_list");
        
        String admSessionId = pnull(request.getAttribute(Const.SESSION_KEY_ADM_MEM_ID));
        
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "memberLogin() [params:"+_params+"], " + Const.SESSION_KEY_ADM_MEM_ID + "=" + admSessionId );
        
        
        List admMemberList = null;
		int totalCount     = 0;
		try {
			admMemberList = admMemberService.admMemberList(_params);
			totalCount    = admMemberService.admMemberListCount(_params);
		} catch (CommonException e) {
			e.printStackTrace();
		}
        
        Paging paging = new Paging();
        paging.setPageNo(Integer.parseInt(pnull(_params.get("pageNo"), "1")));
        paging.setPageSize(10);
        paging.setTotalCount(totalCount);
        
        //
        String schTextGubun = pnull(_params.get("schTextGubun"));
        String schText      = pnull(_params.get("schText"));
        
        LinkedHashMap schTextGubunMap = new LinkedHashMap();
        schTextGubunMap.put("adm_id",    schTextGubun.equals("adm_id") ? "selected":"");
        schTextGubunMap.put("adm_name",  schTextGubun.equals("adm_name") ? "selected":"");
        schTextGubunMap.put("adm_depart",schTextGubun.equals("adm_depart") ? "selected":"");
        
        mv.addObject("b_idx", pnull(_params.get("b_idx")));
        mv.addObject("list", admMemberList);
        mv.addObject("paging", paging);
        mv.addObject("schTextGubun", schTextGubun);
        mv.addObject("schText", schText);
        mv.addObject("schTextGubunMap", schTextGubunMap);

        D(_logger, Thread.currentThread().getStackTrace(), "Controller Response mv \n\t>> "+mv);
        
        return mv;
    }

    /**
     * 
     * @date 2017.10.10 
     * 
     */
    @RequestMapping(value = "/admin/member/admmember_insertForm.do")
    public ModelAndView admMemberInsertForm(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/member/admmember_view");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "admMemberInsertForm() [params:"+_params+"]" );
        
        try
        {
            mv.addObject("DEPART_LIST1", commonUtilService.getDepartList("1"));
            mv.addObject("DEPART_LIST2", commonUtilService.getDepartList("2"));
            mv.addObject("DEPART_LIST3", commonUtilService.getDepartList("3"));
        } catch(Exception e) { 
        	e.printStackTrace();
        }
        
        mv.addObject("_params", _params);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Response mv \n\t>> "+mv);
        
        return mv;
    }

    /**
     * 
     * @date 2017.10.10 
     * 
     */
    @RequestMapping(value = "/admin/member/admmember_insert.do")
    public void admMemberInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "admMemberInsert() [params:"+_params+"]" );
        
        boolean success = false;
        
        try {
            success = admMemberService.admMemberInsert(_params);
        } catch(CommonException e) {
        	D(_logger, Thread.currentThread().getStackTrace(), "admMemberInsert() err[code="+e.getErrCode()+", msg="+e.getErrMessage()+"]" );
        	e.printStackTrace();
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller End. Goto dispatcher.forward() .. "+success+" .. ? ");
        
        if(success)
            response.sendRedirect("/admin/member/admmember_list.do");
        else
            response.sendRedirect("/admin/member/admmember_view.do");
    }

    /**
     * 
     * @date 2017.10.10 
     * 
     */
    @RequestMapping(value = "/admin/member/admmember_view.do")
    public ModelAndView admMemberView(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/member/admmember_view");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "admMemberView() [params:"+_params+"]" );
        
        Map contents = admMemberService.admMemberView(_params);
        List admDepartCodeList = admMemberService.admMemberDepartCodeList(_params);
        try
        {
            mv.addObject("DEPART_LIST1", commonUtilService.getDepartList("1"));
            mv.addObject("DEPART_LIST2", commonUtilService.getDepartList("2"));
            mv.addObject("DEPART_LIST3", commonUtilService.getDepartList("3"));
        } catch(Exception e) {
        	e.printStackTrace();
        }
        
        mv.addObject("_params", _params);
        mv.addObject("contents", contents);
        mv.addObject("admDepartCodeList", admDepartCodeList);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Response mv \n\t>> "+mv);
        
        return mv;
    }

    /**
     * 
     * @date 2017.10.10 
     * 
     */
    @RequestMapping(value = "/admin/member/admmember_modify.do")
    public void admMemberModify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/member/admmember_view");
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "admMemberModify() [params:"+_params+"]" );
        
        boolean success = false;
        try
        {
            success = admMemberService.admMemberModify(_params);
        }
        catch(CommonException e)
        {
            _logger.error((new StringBuilder("err_code=")).append(e.getErrMessage()).toString());
            _logger.error((new StringBuilder("err_msg=")).append(e.getErrCode()).toString());
            mv.addObject("ERR_MSG", e.getErrMessage());
            mv.addObject("ERR_CODE", e.getErrCode());
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller End. Goto dispatcher.forward() .. "+success+" .. ? ");
        
        response.sendRedirect("/admin/member/admmember_list.do");
        
    }

    /**
     * 
     * @date 2017.10.10 
     */
    @RequestMapping(value = "/admin/member/admmember_delete.do")
    public void admmemberDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );
        
        boolean success=false;
		try {
			success = admMemberService.admMemberDelete(_params);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		D(_logger, Thread.currentThread().getStackTrace(), "Controller End. Goto dispatcher.forward() .. "+success+" .. ? ");
		
		response.sendRedirect("/admin/member/admmember_list.do");
    }

    /**
     * 관리자 > 회원 목록 조회
     * @date 2017.10.10 
     */
    @RequestMapping(value = "/admin/member/member_list.do")
    public ModelAndView admUserMemberList(HttpServletRequest request)
    		throws ServletException, Exception
    {
    	// request
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"], " + Const.SESSION_KEY_ADM_MEM_ID  );
        
        
        // service call
        CommonDaoDTO rtDto = null;
//        List admMemberList=null;
//		int totalCount=0;
		try {
			
			rtDto = admMemberService.selectMemberList(_params);
//			admMemberList = admMemberService.selectMemberList(_params);
//			totalCount = admMemberService.selectMemberListCount(_params);
		} catch (CommonException e) {
			e.printStackTrace();
		}
        
		// response
        String memberType = "";
        String schTextGubun = "";
        String schText = "";
        memberType = pnull(_params.get("memberType"));
        schTextGubun = pnull(_params.get("schTextGubun"));
        schText = pnull(_params.get("schText"));

        LinkedHashMap memberTypeMap = new LinkedHashMap();
        memberTypeMap.put("1",    schTextGubun.equals("1") ? "selected":"");
        memberTypeMap.put("2",    schTextGubun.equals("2") ? "selected":"");
        memberTypeMap.put("3",    schTextGubun.equals("3") ? "selected":"");
        memberTypeMap.put("4",    schTextGubun.equals("4") ? "selected":"");
        memberTypeMap.put("5",    schTextGubun.equals("5") ? "selected":"");
        
        LinkedHashMap schTextGubunMap = new LinkedHashMap();
        schTextGubunMap.put("id",    schTextGubun.equals("id") ? "selected":"");
        schTextGubunMap.put("name",  schTextGubun.equals("name") ? "selected":"");
        schTextGubunMap.put("baptismalname",schTextGubun.equals("baptismalname") ? "selected":"");
        
        ModelAndView mv = new ModelAndView("/admin/member/member_list");
        mv.addObject("list", rtDto.daoList);
        mv.addObject("paging", rtDto.paging);
        
        mv.addObject("memberType", memberType);
        mv.addObject("schTextGubun", schTextGubun);
        mv.addObject("schText", schText);
        mv.addObject("schTextGubunMap", schTextGubunMap);
        mv.addObject("memberTypeMap", memberTypeMap);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Response mv \n\t>> "+mv);
        
        return mv;
    }

    /**
     * 
     * @date 2017.10.10 
     * 
     */
    @RequestMapping(value = "/admin/member/member_insertForm.do")
    public ModelAndView admUserMemberInsertForm(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/member/member_view");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );
        
        try
        {
            mv.addObject("DEPART_LIST1", commonUtilService.getDepartList("1"));
            mv.addObject("DEPART_LIST2", commonUtilService.getDepartList("2"));
            mv.addObject("DEPART_LIST3", commonUtilService.getDepartList("3"));
        } catch(Exception e) { 
        	e.printStackTrace();
        }
        
        // 교회조직목록조회
        Map orgMAP = getChurchList();
        
        //mv.addObject("CHURCH_LIST", TempleUtil.listMailhallInRegion(false));
        mv.addObject("CHURCH_LIST", orgMAP.get(Const.ADM_MAPKEY_LIST));
        mv.addObject("query_type", "insert");
        mv.addObject("_params", _params);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Response mv \n\t>> "+mv);
        
        return mv;
    }
    
    /*
     * 조직 조회
     */
    private Map getChurchList() throws Exception {
    	Map _params = new HashMap();
    	_params.put("lv1", "02' AND O.LV3!='000' AND O.LV2!='00");// 교회 조회
    	_params.put("ORG_HIERARCHY_ORDERBY", " O.NAME ASC, O.LV1 ASC, O.LV2 ASC, O.LV3 ASC ");
    	
        try {
			orgHierarchyService.selectOrgHierarchyGroupby(_params);
		} catch (CommonException e) {
			throw e;
		}
        
        return _params;
    }

    /**
     * 
     * @date 2017.10.10 
     * 
     */
    @RequestMapping(value = "/admin/member/member_view.do")
    public ModelAndView admUserMemberView(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/member/member_view");
        build(request);
        
        String admSessionId = pnull(request.getAttribute(Const.SESSION_KEY_ADM_MEM_ID));
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"], " + Const.SESSION_KEY_ADM_MEM_ID + "=" + admSessionId );
        
        Map contents = null;
        try {
            contents = admMemberService.selectMemberView(_params);
        } catch(CommonException e) {
            e.printStackTrace();
        }
        
        // 교회조직목록조회
        //orgHierarchyService.selectOrgHierarchyGroupby(_params);
        Map orgMAP = getChurchList();
        
        //mv.addObject("CHURCH_LIST", TempleUtil.listMailhallInRegion(false));
        mv.addObject("CHURCH_LIST", orgMAP.get(Const.ADM_MAPKEY_LIST));
        mv.addObject("query_type",  "update");
        mv.addObject("_params",     _params);
        mv.addObject("contents",    contents);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Response mv \n\t>> "+mv);
        
        return mv;
    }

    /**
     * 회원아이디 중복 검사
     * @date 2017.10.10 
     */
    @RequestMapping(value = "/admin/member/member_view_dupcheck.do")
    public @ResponseBody Map<String,Object> selectUserMemberInfo(HttpServletRequest request)
    		throws ServletException, Exception
    {
        build(request);

        String admSessionId = pnull(request.getAttribute(Const.SESSION_KEY_ADM_MEM_ID));
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"], " + Const.SESSION_KEY_ADM_MEM_ID + "=" + admSessionId );
        
        //
        String status = "fail", message="이미 존재하는 아이디입니다.", result="";
        try {
        	result = admMemberService.selectMemberId(_params);
        	if(result.length() == 0) {
        		status = "success";
        		message = "등록 가능한 아이디입니다.";
        	}
        } catch(CommonException e) {
        	message = "아이디 중복 검사 중 오류 ["+e.getMessage()+"]";
            e.printStackTrace();
        }
        
        //
        JSONObject json = new JSONObject();
        json.put("status",  status);
        json.put("message", message);
        json.put("result",  result);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Response JSON \n\t>> "+json);
        
        return json;
    }

    /**
     * 아이디 중복 확인
     * @date 2017.10.10 
     * 
     */
    @RequestMapping(value = "/admin/member/selectAdminMemberInfo.do")
    public @ResponseBody Map<String,Object> selectAdminMemberInfo(HttpServletRequest request)
    		throws ServletException, Exception
    {
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), " [params:"+_params+"]" );
        
        String dupId = "";
        try {
            dupId = admMemberService.selectAdminMemberInfo(_params);
        } catch(CommonException e) {
            e.printStackTrace();
        }
        JSONObject json = new JSONObject();
        json.put("result", dupId);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Response JSON \n\t>> "+json);
        
        return json;
    }

    /**
     * 
     * @date 2017.10.10 
     * 
     */
    @RequestMapping(value = "/admin/member/member_insert.do")
    public void admUserMemberInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/member/member_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );
        
        
        
        boolean success = false;
        try {
            success = admMemberService.insertMember(_params);
        } catch(Exception e) { 
        	e.printStackTrace();
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller End. Goto dispatcher.forward() .. "+success+" .. ? ");
        
        response.sendRedirect("/admin/member/member_list.do");
    }

    /**
     * 
     * @date 2017.10.10 
     * 
     */
    @RequestMapping(value = "/admin/member/member_modify.do")
    public void admUserMemberModify(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/member/member_list");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );
        
        
        
        boolean success = false;
        try {
            success = admMemberService.updateMember(_params);
        } catch(CommonException e) {
            e.printStackTrace();
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller End. Goto dispatcher.forward() .. "+success+" .. ? ");
        
        response.sendRedirect("/admin/member/member_list.do");
    }

    /**
     * 회원탈퇴처리
     * @date 2017.10.10 
     */
    @RequestMapping(value = "/admin/member/member_delete.do")
    public void admUserMemberDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/member/member_list");
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );
        
        
        boolean success = false;
        try {
            success = admMemberService.deleteMember(_params);
        } catch(CommonException e) {
            e.printStackTrace();
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller End. Goto dispatcher.forward() .. "+success+" .. ? ");
        
        response.sendRedirect("/admin/member/member_list.do");
    }

    /**
     * 회원탈퇴처리
     * @date 2017.10.10 
     */
    @RequestMapping(value = "/admin/member/member_restore.do")
    public void admUserMemberRestore(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/member/member_list");
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );
        
        
        boolean success = false;
        try {
            success = admMemberService.restoreMember(_params);
        } catch(CommonException e) {
            e.printStackTrace();
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller End. Goto dispatcher.forward() .. "+success+" .. ? ");
        
        response.sendRedirect("/admin/member/member_list.do");
    }
    
    /**
     * 
     * @date 2017.10.10 
     * 
     */
    @RequestMapping(value = "/admin/member/admauth_list.do")
    public ModelAndView admMemberAuthList(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/member/admauth_list");
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );
        D(_logger, Thread.currentThread().getStackTrace(), "[ ************ 로직없음 ************ ]" );
        D(_logger, Thread.currentThread().getStackTrace(), "[ ************ 로직없음 ************ ]" );
        
        mv.addObject("_params", _params);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Response mv \n\t>> "+mv);
        
        return mv;
    }

    /**
     * 
     * @date 2017.10.10 
     * 
     */
    @RequestMapping(value = "/admin/member/admauth_view.do")
    public ModelAndView admMemberAuthView(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/member/admauth_view");
        build(request);
        
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );
        
        mv.addObject("_params", _params);
        
        List memberNonList=null;
		List memberAuthList=null;
		try {
			memberNonList = admMemberService.admMemberNonList(_params);
			memberAuthList = admMemberService.admMemberAuthList(_params);
		} catch (CommonException e) {
			e.printStackTrace();
		}
        
        mv.addObject("memberNonList", memberNonList);
        mv.addObject("memberAuthList", memberAuthList);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Response mv \n\t>> "+mv);
        
        return mv;
    }

    /**
     * 
     * @date 2017.10.10 
     * 
     */
    @RequestMapping(value = "/admin/member/admauth_insert.do")
    public void admMemberAuthInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/member/admauth_view");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );
        
        mv.addObject("_params", _params);
        String success="   ";
		try {
			success = admMemberService.admMemberAuthManage(_params);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        if(success.length() > 3 && success.substring(0, 3).equals("ERR")) {
            mv.addObject("CDD", "FAIL");
            mv.addObject("MSG", "등록 미완료되었습니다.["+success+"]");
        } else {
            mv.addObject("CDD", "SUCC");
            mv.addObject("MSG", success);
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller End. Goto dispatcher.forward() .. "+success+" .. ? ");
        
        request.getRequestDispatcher("/admin/member/admauth_view.do").forward(request, response);
    }

    /**
     * 
     * @date 2017.10.10 
     */
    @RequestMapping(value = "/admin/member/admauth_delete.do")
    public void admMemberAuthDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/admin/member/admauth_view");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "[params:"+_params+"]" );
        
        mv.addObject("_params", _params);
        String success = admMemberService.admMemberAuthManage(_params);
        if(success.equals("DELOK"))
        {
            mv.addObject("CDD", "SUCC");
            mv.addObject("MSG", success);
        } else
        {
            mv.addObject("CDD", "FAIL");
            mv.addObject("MSG", "삭제 미완료되었습니다.["+success+"]");
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller End. Goto dispatcher.forward() .. "+success+" .. ? ");
        
        request.getRequestDispatcher("/admin/member/admauth_view.do").forward(request, response);
    }

    
    /*
     * 휴면계정에 일괄 메일 발송
     */
    @RequestMapping(value = "/admin/member/send_mail_dormant.do")
    public @ResponseBody Map<String, Object> admMemberSendmaildormant(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, Exception
    {
        // request
        //build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        // service call
        Map<String, Object> result = admMemberService.admMemberSendmaildormant();
        
        // response
        Map<String, Object> json = getJson(true);
        json.put("count", result.get("count"));
        
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response String (by JSON) >> \n\t\t"+json  );
        
        return json;
    }
    
}
