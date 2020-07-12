// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChurchController.java

package kr.caincheon.church.church;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.admin.serivce.NBoardServiceImpl;
import kr.caincheon.church.church.service.TempleService;
import kr.caincheon.church.common.CAGiguInfoUtil;
import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonDaoMultiDTO;
import kr.caincheon.church.controller.AdmBonNoticeControllerConst;
import kr.caincheon.church.controller.AdmChurchAlbumControllerConst;

@Controller
public class ChurchController extends CommonController implements AdmBonNoticeControllerConst //, ChurchControllerConst
{

    private final Logger _logger = Logger.getLogger(getClass());
	
    // 본당관련 서비스 클래스
	@Resource(name="templeService")
    private TempleService templeService;

	// 공통게시판 관련 서비스 클래스
	@Resource(name="nBoardService")
	private NBoardServiceImpl nBoardService;
    
	
    /* front : 본당 > 지구별 : 디폴트(중동구지구) */
	@RequestMapping(value = "/church/church.do")
    public ModelAndView temple(HttpServletRequest request)throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/church/church");
        
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        pnullPut(_params, "step1", "01");//중동구지구
        
        CommonDaoMultiDTO dto = new CommonDaoMultiDTO();
        String qc = pnull(_params.get("qc"));
        String qo = pnull(_params.get("qo"));
        String qk = pnull(_params.get("qk"));
        String step1 = pnull(_params, "step1");
        
        try
        {
        	//본당의 전반적인 정보 조회
            templeService.selectMailhallTotally(_params, dto); // dto.paging, dto.paging2, dto.daoResult, dto.daoResult1, dto.daoResult3, dto.daoResult4, dto.daoResult6
            //본당둘러보기이미지 조회
            templeService.selectChurchPictures(_params, dto); // dto.daoResult5
            
        } catch(Exception e) {
            _logger.error(e.getMessage(), e);
        } finally {
        	templeService.freeDataSource();
        }
        
        qk = "".equals(step1) ? qk : "";
        mv.addObject("vacancyList", dto.daoResult);
        mv.addObject("churchVO", dto.daoResult1);
        mv.addObject("misaList", dto.daoResult2);
        //mv.addObject("priestMap", dto.daoResult6);
        mv.addObject("mainHallList", dto.daoResult4);
        mv.addObject("mainPictures", dto.daoResult5);
        mv.addObject("newsList", ((Map)dto.daoResult3).get("LIST"));
        mv.addObject("newsTotal", ((Map)dto.daoResult3).get("TOTAL"));
        mv.addObject("paging", dto.paging);
        mv.addObject("pagingGongso", dto.paging2);
        
        mv.addObject("giguList", CAGiguInfoUtil.getInstance().getRegionList(false));//지구코드목록
        mv.addObject("giguListMixed", CAGiguInfoUtil.getInstance().getRegionList(true));
        mv.addObject("giguCodeList", CAGiguInfoUtil.getInstance().getRegionCodeList());
        
        mv.addObject("step1", pnull(_params, "step1"));
        mv.addObject("churchIdx", _params.get("churchIdx"));
        mv.addObject("mapPoint", "1476865845975");
        mv.addObject("mapScript", "\"timestamp\": \"1484720067087\",\"key\": \"fgno\",\"mapWidth\": \"1200\",\"mapHeight\": \"560\"");

        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/**
	 * front::공소현홍 > 미사시간 
	 */
	@RequestMapping(value = "/church/vacancy.do")
    public ModelAndView vacancy(HttpServletRequest request)throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/church/vacancy");
        
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        CommonDaoMultiDTO dto = new CommonDaoMultiDTO();
        String qc = pnull(_params.get("qc"));
        String qo = pnull(_params.get("qo"));
        String qk = pnull(_params.get("qk"));
        try
        {
            templeService.vacancyList(_params, dto);

        } catch(Exception e) {
            _logger.error(e.getMessage(), e);
        } finally {
        	templeService.freeDataSource();
        }
        
        mv.addObject("mainHallByGigu", dto.daoResult3);
        mv.addObject("vacancyList", dto.daoResult);
        mv.addObject("misaList", dto.daoResult2);
        mv.addObject("paging", dto.paging);
        mv.addObject("queryString", ("qc="+qc+"&qk="+qk+"&qo="+qo));
        mv.addObject("qo", qo);
        mv.addObject("qc", qc);
        mv.addObject("qk", qk);

        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/*
	 * front::본당현홍 > 미사시간 
	 */
	@RequestMapping(value = "/church/temp_01.do")
    public ModelAndView temp_01(HttpServletRequest request)throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/church/temp_01");

        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        CommonDaoMultiDTO dto = new CommonDaoMultiDTO();
        String tabs = pnull(_params.get("tabs"));
        String qc = pnull(_params.get("qc"));
        String qo = pnull(_params.get("qo"));
        String qk = pnull(_params.get("qk"));
        String srchDiv = pnull(_params.get("srchDiv"));
        String yoil = pnull(_params.get("yoil"));
        String sHour = pnull(_params.get("sHour"));
        String sMin = pnull(_params.get("sMin"));
        String eHour = pnull(_params.get("eHour"));
        String eMin = pnull(_params.get("eMin"));
        
        try
        {
            templeService.groupbyMailhallInRegion(_params, dto); // dto.daoResult3
            templeService.inquireMisaInChurchList(_params, dto); // dto.daoResult & dto.paging, & dto.daoResult2
            
        } catch(Exception e) {
            _logger.error(e.getMessage(), e);
        } finally {
        	templeService.freeDataSource();
        }
        
        qk = "".equals(tabs) ? qk : "";
        mv.addObject("mainHallByGigu", dto.daoResult3);//지구별 성당목록
        mv.addObject("vacancyList",    dto.daoResult);// 미사가 있는 성당 목록
        mv.addObject("paging",         dto.paging); //미사가 있는 성당 목록의 페이징
        mv.addObject("misaList",       dto.daoResult2);// 성당별 미사목록
        mv.addObject("giguList", CAGiguInfoUtil.getInstance().getRegionList(false));//지구코드목록
        
        mv.addObject("queryString",    "qc="+qc+"&qk="+qk+"&qo="+qo);
        mv.addObject("tabs", tabs);
        mv.addObject("qo", qo);
        mv.addObject("qc", qc);
        mv.addObject("qk", qk);
        mv.addObject("srchDiv", srchDiv);
        mv.addObject("yoil", yoil);
        mv.addObject("sHour", sHour);
        mv.addObject("sMin", sMin);
        mv.addObject("eHour", eHour);
        mv.addObject("eMin", eMin);

        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/church/temp_02.do")
    public ModelAndView temp_02(HttpServletRequest request)throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/church/temp_02");

        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        CommonDaoMultiDTO dto = new CommonDaoMultiDTO();
        
        String tabs = pnull(_params.get("tabs"));
        String qc = pnull(_params.get("qc"));
        String qo = pnull(_params.get("qo"));
        String qk = pnull(_params.get("qk"));
        if(tabs.length() == 0) _params.put("church_idx", "");
        
        try
        {
            //templeService.groupbyMailhallInRegion(_params, dto);
            templeService.newsList(_params, dto);
            
        } catch(Exception e) {
            _logger.error(e.getMessage(), e);
        } finally {
        	templeService.freeDataSource();
        }
        
        qk = "".equals(tabs) ? qk : "";
        mv.addObject("newsList",       dto.daoResult);
        mv.addObject("paging",         dto.paging);
        mv.addObject("mainHallByGigu", dto.daoResult3);
        mv.addObject("newsCount",      dto.paging.getTotalCount());
        mv.addObject("giguList", CAGiguInfoUtil.getInstance().getRegionList(false));//지구코드목록
        
        mv.addObject("queryString", "qc="+qc+"&qk="+qk+"&qo="+qo+"&church_idx="+_params.get("church_idx"));
        mv.addObject("tabs", tabs);
        mv.addObject("qo", qo);
        mv.addObject("qc", qc);
        mv.addObject("qk", qk);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	@RequestMapping(value = "/church/temp_02_view.do")
    public ModelAndView temp_02_view(HttpServletRequest request)throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/church/temp_02_view");
        
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        String tabs = pnull(_params.get("tabs"));
        String qc = pnull(_params.get("qc"));
        String qo = pnull(_params.get("qo"));
        String qk = pnull(_params.get("qk"));
        if(tabs.length() == 0 && "0".equals(tabs)) _params.put("church_idx", "");
        
        
        CommonDaoMultiDTO dto = new CommonDaoMultiDTO();
        
        try {
            //templeService.newsList(_params, dto);
            templeService.groupbyMailhallInRegion(_params, dto);
            templeService.newsVO(_params, dto);
        } catch(Exception e) {
            _logger.error(e.getMessage(), e);
        } finally {
        	templeService.freeDataSource();
        }
        
        qk = "".equals(tabs) ? qk : "";
        mv.addObject("mainHallByGigu", dto.daoResult3);
        mv.addObject("CONTENTS", dto.daoResult4);
//        mv.addObject("prevVO", dto.daoResult5);
//        mv.addObject("postVO", dto.daoResult6);
        mv.addObject("giguList", CAGiguInfoUtil.getInstance().getRegionList(false));//지구코드목록
        mv.addObject("queryString", "qc="+qc+"&qk="+qk+"&qo="+qo+"&church_idx="+_params.get("church_idx"));
        mv.addObject("tabs", tabs);
        mv.addObject("pageSize", pnull(_params.get("pageSize")));
        mv.addObject("pageNo", pnull(_params.get("pageNo")));
        mv.addObject("qo", qo);
        mv.addObject("qc", qc);
        mv.addObject("qk", qk);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/*
	 * 본당 앨범
	 */
	@RequestMapping(value = "/church/temp_03.do")
    public ModelAndView temp_03(HttpServletRequest request)throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/church/temp_03");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        CommonDaoDTO dto2 = null;
        CommonDaoMultiDTO dto = new CommonDaoMultiDTO();
        String tabs = pnull(_params.get("tabs"));
        String qc = pnull(_params.get("qc"));
        String qo = pnull(_params.get("qo"));
        String qk = pnull(_params.get("qk"));
        
        try {
        	
        	//
            templeService.groupbyMailhallInRegion(_params, dto); // 
            //templeService.parishAlbumList(_params, dto);// 교구장앨범 목록 조회
            
            // 공통 프로그램 호출 본당 앨범 조회
            //pnullPut(_params, "LV1", "02' AND LV2!='00' AND LV3!='000");
            _params.put("TOP_COUNT", "5");
    		dto2 = callNBoardList(nBoardService, _params, mv, AdmChurchAlbumControllerConst.LEFT_MENU_DATA_PG, false, Integer.parseInt(ATTACHED_FILE_COUNT));
    		
        } catch(Exception e) {
            _logger.error(e.getMessage(), e);
        } finally {
        	templeService.freeDataSource();
        }
        
        qk = "".equals(tabs) ? qk : "";
        mv.addObject("mainHallByGigu", dto.daoResult3);
        mv.addObject("LIST", dto2.daoList);//dto.daoResult4);
        mv.addObject("giguList", CAGiguInfoUtil.getInstance().getRegionList(false));//지구코드목록
        mv.addObject("paging", dto2.paging);
        mv.addObject("queryString", ("qc="+qc+"&qk="+qk+"&qo="+qo));
        mv.addObject("tabs", tabs);
        mv.addObject("qo", qo);
        mv.addObject("qc", qc);
        mv.addObject("qk", qk);

        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }

	/*
	 * 본당 앨범
	 */
	@RequestMapping(value = "/church/temp_03_view.do")
    public ModelAndView temp_03_view(HttpServletRequest request)throws ServletException, Exception
    {
        ModelAndView mv = new ModelAndView("/church/temp_03_view");
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        Map result = null;
        CommonDaoMultiDTO dto = new CommonDaoMultiDTO();
        String tabs = pnull(_params.get("tabs"));
        String qc = pnull(_params.get("qc"));
        String qo = pnull(_params.get("qo"));
        String qk = pnull(_params.get("qk"));
        
        try {
        	
        	
            templeService.groupbyMailhallInRegion(_params, dto);
            result = templeService.templeAlbumContents(_params);
            
        } catch(Exception e) {
            _logger.error(e.getMessage(), e);
        } finally {
        	templeService.freeDataSource();
        }
        
        qk = "".equals(tabs) ? qk : "";
        mv.addObject("mainHallByGigu", dto.daoResult3);
        mv.addObject("CONTENTS", result);
        mv.addObject("giguList", CAGiguInfoUtil.getInstance().getRegionList(false));//지구코드목록
        mv.addObject("paging", dto.paging);
        mv.addObject("queryString", "qc="+qc+"&qk="+qk+"&qo="+qo);
        mv.addObject("tabs", tabs);
        mv.addObject("qo", qo);
        mv.addObject("qc", qc);
        mv.addObject("qk", qk);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }
	

}
