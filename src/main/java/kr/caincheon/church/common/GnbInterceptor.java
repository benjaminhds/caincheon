// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Interceptor.java

package kr.caincheon.church.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.caincheon.church.admin.mboard.MBoardDAO;

public class GnbInterceptor extends HandlerInterceptorAdapter
{
	@Autowired
	private	MBoardDAO	mbaordDao;
	
    public GnbInterceptor()
    {
    }
    
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception
    {
    	boolean rtBool = true;
    	
    	Map param = new HashMap();
    	param.put("i_sViewFlag","Y");
    	
    	request.setAttribute("menu_list", mbaordDao.getMenuBoardList(param).daoList);
    	
        return rtBool;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
        throws Exception
    {
        super.postHandle(request, response, handler, modelAndView);
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception
    {
        super.afterCompletion(request, response, handler, ex);
    }
}
