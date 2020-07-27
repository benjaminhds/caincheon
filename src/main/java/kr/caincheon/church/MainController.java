package kr.caincheon.church;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.church.ChurchService;
import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.news.service.AlbService;
import kr.caincheon.church.news.service.MgzService;
import kr.caincheon.church.news.service.MovService;
import kr.caincheon.church.search.service.MainServiceImpl;
import kr.caincheon.church.service.BannerService;
import kr.caincheon.church.service.PopupService;

/**
 * <pre>
 * 간략 : 
 * 상세 : 
 * kr.caincheon.church.nnew.main
 *   |_ MainController.java
 * </pre>
 * @author		benjamin
 * @since		2020-06-23
 * @version		1.0
 * @see
 *
 * <pre>
 * 수정일      	수정자		수정내용
 * ---------	----------	---------------------------
 * 2020-06-23	benjamin	최초 생성
 * </pre>
 */
@Controller
public class MainController extends CommonController
{

    private final Logger _logger = Logger.getLogger(getClass());
	
    // 메인서비스
	@Resource(name="mainService")
    private MainServiceImpl mainService;
	
	// 교구앨범
	@Resource(name="albService")
    private AlbService albService;
	
	// 교구 영상
	@Resource(name="movService")
    private MovService movService;
	
	// 팝업서비스
	@Resource(name="popupService")
    private PopupService popupService;

	// 배너서비스
	@Resource(name="bannerService")
    private BannerService bannerService;
	
	// 주보서비스
	@Resource(name="mgzService")
	private MgzService mgzService;	
	
	// 본당서비스
	@Resource(name="churchService")
    private ChurchService churchService;
	
    /*
     * main - index service OP
     * - @RequestMapping(value = "/home2.do")
     */
    @RequestMapping(value = "/home{homeId}.do")
    public ModelAndView home2(HttpServletRequest request, @PathVariable String homeId) throws Exception
    {
        // request
        build(request);
        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
        
        
    
        
        
        // service call
        _params.put("pageNo", "1");
        _params.put("is_view", "Y");
        java.util.Map todayContents    = mainService.todayContents();   // 오늘의 소식
        java.util.List totalNewsList   = new java.util.ArrayList();mainService.noticeList("ALL");  // 전체소식 : b_idx {9,10,11,12} top 6 (최신글만)
        java.util.List chuchNewsList   = new java.util.ArrayList();//mainService.noticeList("9");    // 교회소식 : b_idx = 9 top 6(상단고정 포함 top 2+최신 4)
        java.util.List bondangNewsList = new java.util.ArrayList();//mainService.noticeList("11");   // 본당소식 : b_idx in(본당 11) top 6(상단고정 포함 top 2+최신 4) - 제목앞에 조직정보가 붙어야 함.
        java.util.List unitList        = new java.util.ArrayList();//mainService.noticeList("10");   // 공동체소식 : b_idx in(공동체 10) top 6(상단고정 포함 top 2+최신 4) - 제목앞에 조직정보가 붙어야 함.
        java.util.List parishList      = new java.util.ArrayList();//mainService.noticeList("12");   // 교구소식 : b_idx = 12 top 6(상단고정 포함 top 2+최신 4)
        
        Map<String, Object> ggScheduls = mainService.schList_Main(_params); // 월간교구일정 : 최신 top 4, Gubun=1 or 2 로 표시, 1 or 2가 아니면, 교구/해당부서명 타이틀로 출력, 명칭이 5자 넘으면 절삭
        
        java.util.List albGgList       = new java.util.ArrayList();//albService.albList_Main(_params); // 교구앨범  10개
        java.util.List albBdList       = new java.util.ArrayList();//mainService.albList_Bondang(_params); // 본당앨범 
        
        CommonDaoDTO   movDto       = movService.movList_Main(_params); // 교구영상   5개
        
        java.util.List fatherList   = mainService.priestListOfThisMonth(); // 이달의 사제
        java.util.List parList      = new java.util.ArrayList();//mainService.parList_Main(_params); // 교구장동정 : 중앙 carousel
        
        java.util.List popupList    = popupService.mainPopupList(_params); // 팝업 조회
        java.util.List bannerList   = bannerService.mainBannerList(_params); // 배너 조회
        
        _params.put("pageNo", "1");
        _params.put("pageSize", "5");
        java.util.List jooboList    = mgzService.mgzList(_params); // 주소  조회
        
        java.util.List churchList   = churchService.listChurchListInGigu(_params); // 지구별 본당목록
        
        // response
        ModelAndView mv = new ModelAndView("home"+homeId);
        mv.addObject("homeId",          homeId);
        mv.addObject("todayContents",   todayContents); // 오늘의 소식
        mv.addObject("totalNewsList",   totalNewsList); // 교회소식 
        mv.addObject("chuchNewsList",   chuchNewsList); // 교구소식 
        mv.addObject("bondangNewsList", bondangNewsList); // 본당소식
        mv.addObject("unitList",        unitList);      // 공동체소식
        
        mv.addObject("parishList",      parishList);    // 교구장동정
        mv.addObject("ggSchedulsList",  ggScheduls.get("LIST")); // 월간교구일정
        mv.addObject("ggSchedulsWeek",  ggScheduls.get("WEEK")); // 주간교구일정
        mv.addObject("albGgList",       albGgList); // 교구앨범
        mv.addObject("albBdList",       albBdList); // 본당앨범
        mv.addObject("movList",         movDto.daoList); // 교구영상
        mv.addObject("fatherList",    fatherList);
        mv.addObject("parList",       parList);
        mv.addObject("popupList",     popupList);
        mv.addObject("bannerList",    bannerList);
        mv.addObject("jooboList",     jooboList); // 주보리스트
        
        mv.addObject("giguList",      churchList);// 지구별본당목록, TempleUtil.getInstance().getRegionList(false));
        
        
        D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
        return mv;
    }
    

    /*
     * main :: 교구일정을 조회한다.
     */
//    @RequestMapping(value = "/home/gyogu_monthly_schedule.do")
//    public @ResponseBody Map<String, Object> home_gyogu_monthly_schedule(HttpServletRequest request) throws Exception
//    {
//        // request
//        build(request);
//        D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );
//        
//        // service call
//        Map<String, Object> result = mainService.schList_Main(_params);
//        
//        // response
//        Map<String, Object> json = getJson(true);
//        json.put("SCHEDULE", result.get("LIST"));
//        json.put("THISWEEK", result.get("WEEK"));
//        
//        D(_logger, Thread.currentThread().getStackTrace(), "Response String (by JSON) >> \n\t\t"+json  );
//        
//        return json;
//    }

}
