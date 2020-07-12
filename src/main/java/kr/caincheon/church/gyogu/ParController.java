// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ParController.java

package kr.caincheon.church.gyogu;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.admin.ParControllerConst;
import kr.caincheon.church.admin.serivce.NBoardServiceImpl;
import kr.caincheon.church.common.base.CommonController;

@Controller
public class ParController extends CommonController implements ParControllerConst
{

	private final Logger _logger = Logger.getLogger(getClass());
//	
//	@Resource(name="parService")
//    private ParService parService;

    @Resource(name="nBoardService")
    private NBoardServiceImpl nBoardService;
	
    
    /* bjm found */
	@RequestMapping(value = "/gyogu/par_list.do")
    public ModelAndView parList(HttpServletRequest request)
    		throws ServletException, Exception
    {
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "_params ? "+_params );
        ModelAndView mv = new ModelAndView("/gyogu/par_list");
        
        pnullPut(_params, "pageNo",   "1");
        _params.put("is_view", "Y");
        
        // call a service
//        List<Map> parList = parService.parList(_params);
//        int totalCount = parService.parListCount(_params);

        // 
        pnullPut(_params, "TOP_COUNT", "5");//상단고정 개수
        callNBoardList(nBoardService, _params, mv, LEFT_MENU_DATA_PG, false, Integer.parseInt(ATTACHED_FILE_COUNT));//조직이 필요 없어서 false
        
        
        // response
        String schTextGubun = pnull(_params.get("schTextGubun"));
        String schText      = pnull(_params.get("schText"));
        
        Map<String, String> schTextGubunMap = new LinkedHashMap<String, String>();
        schTextGubunMap.put("all",     schTextGubun.equals("all")     ? "selected":"");
        schTextGubunMap.put("title",   schTextGubun.equals("title")   ? "selected":"");
        schTextGubunMap.put("content", schTextGubun.equals("content") ? "selected":"");
        
        
        String strCategoryName = "", c_idx = pnull(_params.get("c_idx"));
        String subTitleOn1 = "";
        String subTitleOn2 = "";
        String subTitleOn3 = "";
        if(c_idx.equals("ALL")) {
            strCategoryName = "전체보기";
            subTitleOn1 = "class=\"on\"";
        } else if(c_idx.equals("3")) {
            strCategoryName = "동정";
            subTitleOn2 = "class=\"on\"";
        } else if(c_idx.equals("4")) {
            strCategoryName = "강론";
            subTitleOn3 = "class=\"on\"";
        }
        
        mv.addObject("c_idx", c_idx);
        mv.addObject("strCategoryName", strCategoryName);
        mv.addObject("subTitleOn1", subTitleOn1);
        mv.addObject("subTitleOn2", subTitleOn2);
        mv.addObject("subTitleOn3", subTitleOn3);
        mv.addObject("schTextGubun", schTextGubun);
        mv.addObject("schText", schText);
        mv.addObject("schTextGubunMap", schTextGubunMap);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/gyogu/par_view.do")
    public ModelAndView parContents(HttpServletRequest request)
    		throws ServletException, Exception
    {
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "_params ? "+_params );
        ModelAndView mv = new ModelAndView("/gyogu/par_view");
        
        
        // call service modeul
//        Map parContents = parService.parContents(_params);

        
        //pnullPut(_params, "LV1", "02' AND LV2!='00' AND C.CHURCH_IDX IS NOT NULL AND LV3!='000");//본당조회
        callNBoardContents(nBoardService, _params, mv, LEFT_MENU_DATA_PG, false, Integer.parseInt(ATTACHED_FILE_COUNT));
        
        // response
        String schTextGubun = pnull(_params.get("schTextGubun"));
        String schText = pnull(_params.get("schText"));
        
        String strCategoryName = "전체보기";
        if(pnull(_params.get("c_idx")).equals("3")) strCategoryName = "동정";
        else if(pnull(_params.get("c_idx")).equals("4")) strCategoryName = "강론";
        else _params.put("c_idx", "ALL");
        
        mv.addObject("_params", _params);
        mv.addObject("c_idx",  pnull(_params.get("c_idx"))); // divistion code 
        mv.addObject("b_idx",  pnull(_params.get("b_idx"))); // Board code 
        mv.addObject("bl_idx", pnull(_params.get("bl_idx")));
        mv.addObject("strCategoryName", strCategoryName);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

    
}
