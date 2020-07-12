// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HolidayFileController.java

package kr.caincheon.church.controller;

import java.io.File;
import java.io.PrintStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.service.HolidayFileService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HolidayFileController extends CommonController
{

    private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="holidayFileService")
    private HolidayFileService holidayFileService;

    /* bjm found */
	@RequestMapping(value = "/admin/board/holiday_excelUploadAjax.do")
    public ModelAndView excelUploadAjax(HttpServletRequest req, MultipartHttpServletRequest request)
        throws Exception
    {
		D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. " );
		
		

//      List<Map<String, String>> list = fileUploadHandle(request, response, uploads, "upload/precedent/", true);
//      System.out.println( "\n\n\n >>>> " + list );
		String wwwroot = request.getSession().getServletContext().getRealPath("/") + "upload/precedent/";
		if( !new File(wwwroot).exists() ) {
			new File(wwwroot).mkdirs();
		}
		
        boolean success = false;
        MultipartFile excelFile = request.getFile("excelFile");
        System.out.println("엑셀 파일 업로드 컨트롤러.. 전례력 업로드");
        if(excelFile == null || excelFile.isEmpty())
            throw new RuntimeException("업로드 파일이 없습니다..");
        
        System.out.println( "\n\n\n >>>> " + wwwroot );
        
        File destFile = new File(wwwroot + excelFile.getOriginalFilename());
        
        try {
            excelFile.transferTo(destFile);
            success = holidayFileService.bulkloadPrecedentByXlsx(destFile);
        } catch(Exception e) {
        	e.printStackTrace();
        }
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/admin/board/holiday_list.do");
        
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Response. ModelAndView="+mv );
        
        return mv;
    }

}
