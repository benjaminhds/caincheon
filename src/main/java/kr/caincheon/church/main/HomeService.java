<<<<<<< HEAD
package kr.caincheon.church.main;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.caincheon.church.church.ChurchService;
import kr.caincheon.church.common.base.CommonDaoMultiDTO;
import kr.caincheon.church.news.service.MgzService;
import kr.caincheon.church.search.service.MainServiceImpl;
import kr.caincheon.church.service.BannerService;
import kr.caincheon.church.service.PopupServiceImpl;

/**
 * 
 * @author benjamin
 */
@Service("homeService")
public class HomeService
{

    private final Logger L = Logger.getLogger(getClass());
    
    // HOME 멀티게시판 조회 
    @Resource(name="homeDAO")
    private HomeDAO homeDAO;

	// 본당서비스
	@Resource(name="churchService")
    private ChurchService churchService;

    // 메인서비스
	@Resource(name="mainService")
    private MainServiceImpl mainService;

	// 팝업
	@Autowired
    private PopupServiceImpl popupService;

	// 팝업
	@Autowired
    private BannerService bannerService;

	// 주보서비스
	@Autowired
	private MgzService mgzService;
	
    
    //
    public CommonDaoMultiDTO homeMultiboardLists(Map _params) throws Exception
    {
        L.debug("START >> homeMultiboardLists() called : 여러 게시판에서 일괄 조회..");
        
        CommonDaoMultiDTO dto = new CommonDaoMultiDTO();
        
        // basic check
        _params.put("pageNo", "1");
        _params.put("is_view", "Y");
        
        //
        List rtList = null;
        Map  rtMap  = null;
        
        // today contents
        rtMap = homeDAO.getTodayContents();
        dto.daoResult1 = rtMap;
        
        // 멀티게시판 한방 조회
        rtList = homeDAO.homeMultiboardLists();
        dto.daoResult2 = rtList;

        // 지구별 본당목록
        rtList = churchService.listChurchListInGigu(_params); // 지구별 본당목록
        dto.daoResult3 = rtList;
        
        // 월간교구일정
        rtMap  = mainService.schList_Main(_params); // 월간교구일정 : 최신 top 4, Gubun=1 or 2 로 표시, 1 or 2가 아니면, 교구/해당부서명 타이틀로 출력, 명칭이 5자 넘으면 절삭
        dto.daoResult4 = rtMap;
        
        // 이달의 사제
        rtList = mainService.priestListOfThisMonth(); // 이달의 사제
        dto.daoResult5 = rtList;
        
        //
        rtList = popupService.mainPopupList(_params); // 팝업 조회
        dto.daoResult6 = rtList;
        
        //
        rtList = bannerService.mainBannerList(_params); // 배너 조회
        dto.daoResult7 = rtList; 
        
        //
        _params.put("pageNo", "1");
        _params.put("pageSize", "5");
        rtList = mgzService.mgzList(_params); // 주소  조회
        dto.daoResult  = rtList; 
        
        
        L.debug("END   >> homeMultiboardLists() called : 여러 게시판에서 일괄 조회..끝");
        
        return dto;
    }


}
=======
package kr.caincheon.church.main;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.caincheon.church.church.ChurchService;
import kr.caincheon.church.common.base.CommonDaoMultiDTO;
import kr.caincheon.church.news.service.MgzService;
import kr.caincheon.church.search.service.MainServiceImpl;
import kr.caincheon.church.service.BannerService;
import kr.caincheon.church.service.PopupServiceImpl;

/**
 * 
 * @author benjamin
 */
@Service("homeService")
public class HomeService
{

    private final Logger L = Logger.getLogger(getClass());
    
    // HOME 멀티게시판 조회 
    @Resource(name="homeDAO")
    private HomeDAO homeDAO;

	// 본당서비스
	@Resource(name="churchService")
    private ChurchService churchService;

    // 메인서비스
	@Resource(name="mainService")
    private MainServiceImpl mainService;

	// 팝업
	@Autowired
    private PopupServiceImpl popupService;

	// 팝업
	@Autowired
    private BannerService bannerService;

	// 주보서비스
	@Autowired
	private MgzService mgzService;
	
    
    //
    public CommonDaoMultiDTO homeMultiboardLists(Map _params) throws Exception
    {
        L.debug("START >> homeMultiboardLists() called : 여러 게시판에서 일괄 조회..");
        
        CommonDaoMultiDTO dto = new CommonDaoMultiDTO();
        
        // basic check
        _params.put("pageNo", "1");
        _params.put("is_view", "Y");
        
        //
        List rtList = null;
        Map  rtMap  = null;
        
        // today contents
        rtMap = homeDAO.getTodayContents();
        dto.daoResult1 = rtMap;
        
        // 멀티게시판 한방 조회
        rtList = homeDAO.homeMultiboardLists();
        dto.daoResult2 = rtList;

        // 지구별 본당목록
        rtList = churchService.listChurchListInGigu(_params); // 지구별 본당목록
        dto.daoResult3 = rtList;
        
        // 월간교구일정
        rtMap  = mainService.schList_Main(_params); // 월간교구일정 : 최신 top 4, Gubun=1 or 2 로 표시, 1 or 2가 아니면, 교구/해당부서명 타이틀로 출력, 명칭이 5자 넘으면 절삭
        dto.daoResult4 = rtMap;
        
        // 이달의 사제
        rtList = mainService.priestListOfThisMonth(); // 이달의 사제
        dto.daoResult5 = rtList;
        
        //
        rtList = popupService.mainPopupList(_params); // 팝업 조회
        dto.daoResult6 = rtList;
        
        //
        rtList = bannerService.mainBannerList(_params); // 배너 조회
        dto.daoResult7 = rtList; 
        
        //
        _params.put("pageNo", "1");
        _params.put("pageSize", "5");
        rtList = mgzService.mgzList(_params); // 주소  조회
        dto.daoResult  = rtList; 
        
        
        L.debug("END   >> homeMultiboardLists() called : 여러 게시판에서 일괄 조회..끝");
        
        return dto;
    }


}
>>>>>>> branch 'master' of https://github.com/benjaminhds/caincheon
