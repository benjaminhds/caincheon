package kr.caincheon.church.controller;

import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.utils.UtilDate;
import kr.caincheon.church.common.utils.UtilString;
import kr.caincheon.church.service.ExcelService;

@Controller
public class ExcelController extends CommonController
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="excelService")
    ExcelService excelService;

    
    /* bjm found */
	@RequestMapping(value = "/admin/excelDownload.do")
    public String excelTransform(HttpServletRequest request, Map ModelMap, HttpServletResponse response) throws Exception
    {
        build(request);
        
        String target = UtilString.pnull(_params.get("target"));
        String fileName = "";
        if(target.equals("doctrine"))
            fileName = "통신교리신청_"+UtilDate.getYMDHMS();
        else if(target.equals("marry"))
            fileName = "카나안혼인강좌_"+UtilDate.getYMDHMS();
        else if(target.equals("member"))
            fileName = "회원관리_"+UtilDate.getYMDHMS();
        
        String userAgent = request.getHeader("User-Agent");
        if(userAgent.indexOf("MSIE") > -1)
            fileName = URLEncoder.encode(fileName, "utf-8");
        else
            fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
        response.setContentType("Application/Msexcel");
        response.setHeader("Content-disposition", "attachment; filename="+fileName+".xlsx");
        response.setHeader("Content-Transfer-Encoding", "binary");
        
        java.util.List excelList = excelService.getAllObjects(target, _params);
        ModelMap.put("excelList", excelList);
        ModelMap.put("target", target);
        
        
        _logger.info(" ===> [method=excelTransform] excelList ? "+excelList.toString() );
        
        
        /*
         * ExcelView.java 에서 엑셀 컬럼의 항목을 설정
         */
        return "excelView";
    }

    
}
