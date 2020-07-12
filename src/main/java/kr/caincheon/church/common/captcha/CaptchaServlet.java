// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CaptchaServlet.java

package kr.caincheon.church.common.captcha;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.caincheon.church.common.CommonStorage;
import nl.captcha.Captcha;

// Referenced classes of package kr.caincheon.church.common:
//            CaptchaServletUtil

public class CaptchaServlet extends HttpServlet
{

    public CaptchaServlet()
    {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        try
        {
        	// 문자 이미지 생성
            Captcha captcha = ( new nl.captcha.Captcha.Builder(160, 40) ).addText().addBackground().addNoise().addBorder().build();
            
            System.out.println("captcha 자동가입방지 문자 : "+captcha.getAnswer());
            
            // Captcha 문자열 메모리 임시 보관하기
            CommonStorage.getInstance().saveCaptcha(captcha.getAnswer());
            
            // 응답하기
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0L);
            response.setContentType("image/jpeg");
            CaptchaServletUtil.writeImage(response, captcha.getImage());
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            response.sendError(500);
            return;
        }
    }
}
