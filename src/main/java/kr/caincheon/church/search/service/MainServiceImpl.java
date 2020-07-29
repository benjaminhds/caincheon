// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MainServiceImpl.java

package kr.caincheon.church.search.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.admin.ParControllerConst;
import kr.caincheon.church.admin.dao.NBoardDao;
import kr.caincheon.church.admin.serivce.NBoardServiceImpl;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.common.utils.UtilHTML;
import kr.caincheon.church.controller.AdmChurchAlbumControllerConst;
import kr.caincheon.church.dao.MainDao;

// Referenced classes of package kr.caincheon.church.service:
//            MainService

@Service("mainService")
public class MainServiceImpl extends CommonService
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="mainDao")
    private MainDao mainDao;
    
	/*
	 * 공통 NBOARD 처리 서비스 모듈
	 */
    @Resource(name="nBoardDao")
    private NBoardDao nBoardDao;
    
    /*
     * NBoard Service instance
     */
    @Resource(name="nBoardService")
    private NBoardServiceImpl nBoardService;

    
    //
    public List homeMultiboardLists() {
    	List multiList = null;
    	
    	
    	
    	return multiList;
    }

    /*
     * 교구앨범 메인홈페에 서비스되는 OP :: 오늘의 소식
     * (non-Javadoc)
     * @see kr.caincheon.church.search.service.MainService#todayContents()
     */
    
    public Map todayContents() throws Exception 
    {
        return mainDao.todayContents();
    }

    /*
     * 교구앨범 메인홈페에 서비스되는 OP :: 전체소식/교회소식/교구소식/공동체소식
     * (non-Javadoc)
     * @see kr.caincheon.church.search.service.MainService#noticeList(java.lang.String)
     */
    
    public List noticeList(String b_idx) throws Exception 
    {
    	
        return mainDao.noticeList(b_idx);
    }

    /**
     * (non-Javadoc)
     * @see kr.caincheon.church.search.service.MainService#parishList()
     * @deprecated
     */
    public List parishList() throws Exception 
    {
        return mainDao.parishList();
    }

    /*
     * 교구앨범 메인홈페에 서비스되는 OP :: 이달의 사제
     * (non-Javadoc)
     * @see kr.caincheon.church.search.service.MainService#priestListOfThisMonth()
     */
    
    public List priestListOfThisMonth() throws Exception 
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "Service Called. [이달의 사제 in main]" );
    	
        return mainDao.priestListOfThisMonth();
    }

    /*
     * 교구앨범 메인홈페에 서비스되는 OP :: 교구장동정 -> 중앙 carousel
     * (non-Javadoc)
     * @see kr.caincheon.church.search.service.MainService#parList_Main(java.util.Map)
     */
    
    public List parList_Main(Map _params) throws Exception 
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "Service Called. [교구장동정 : 중앙 carousel in main]" );
    	
    	// NBoard list 조회
    	CommonDaoDTO rtDTO = new CommonDaoDTO();
    	try {
    		_params.put("pageSize", 4);//목록은 4개만 조회
            _params.put("HAS_CONTENT", "true");//list에서 content컬럼을 추가하여 조회
    		pnullPutBidxAll(_params, ParControllerConst.LEFT_MENU_DATA_PG);// 교구장동정:ParControllerConst.LEFT_MENU_DATA_PG
			nBoardDao.nboardList(rtDTO, _params, ParControllerConst.LEFT_MENU_DATA_PG, 1);
			
			// content에서 html tag는 모두 제거
			for( int i = 0, i2 = rtDTO.daoList.size() ; i < i2 ; i++ ) {
				Map row = (Map)rtDTO.daoList.get(i);
				String ctnt = UtilHTML.removeHTML(pnull(row, "CONTENT"));
				if(ctnt.length() > 280) ctnt = ctnt.substring(0, 280) + "...";
				row.put("CONTENT", ctnt);
			}
			
		} catch (Exception e) {
			//throw new CommonException("NBoard를 조회하지 못했습니다.", e, "EXPT-100", e.getMessage());
			throw e;
		}
    	
    	D(_logger, Thread.currentThread().getStackTrace(), "Result Service >> \n\t\t"+rtDTO  );
    	
    	return rtDTO.daoList;
    	
    }

    /*
     * 교구앨범 메인홈페에 서비스되는 OP :: 월간교구일정 
     * (non-Javadoc)
     * @see kr.caincheon.church.search.service.MainService#schList_Main()
     */
    
    public Map<String, Object> schList_Main(Map _params) throws Exception 
    {
    	Map<String, Object> result = mainDao.schList_Main(_params);
    	Object list = result.get("LIST");
    	if(list!=null) {
    		removeColumns((List)list, new String[]{"RNUM"});
    	}
        return result;
    }

    /**
     * 메인페이지 : 본당앨범 조회 
     * @param _params
     * @throws Exception
     */
    
    public List albList_Bondang(Map _params) throws Exception 
    {
    	String left_menu_data_pg = AdmChurchAlbumControllerConst.LEFT_MENU_DATA_PG;
    	boolean hasOrgList = false;
    	int ATTACHED_FILE_COUNT = 6;
		pnullPut(_params, "TOP_COUNT", "100");
		int attachedFileCount = ipnull(_params, "_ATTACHED_FILE_COUNT", ATTACHED_FILE_COUNT);
		CommonDaoDTO rtDto = nBoardService.nboardList(_params, left_menu_data_pg, hasOrgList, attachedFileCount);
		return rtDto.daoList;
    }
}
