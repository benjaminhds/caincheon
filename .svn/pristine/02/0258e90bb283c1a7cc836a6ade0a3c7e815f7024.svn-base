// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Interceptor.java

package kr.caincheon.church.common;

import javax.servlet.http.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class Interceptor extends HandlerInterceptorAdapter
{

    public Interceptor()
    {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception
    {
    	boolean rtBool = false;
    	try {
    		if(request.getSession().getAttribute("admSessionMemId") != null) {
    			response.sendRedirect("/admin/login");
    		} else {
    			rtBool = true;
    		}
    	} catch ( Exception e ) {
    		e.printStackTrace();
    	}
    	
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
