// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TempleServiceImpl.java

package kr.caincheon.church.church.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.admin.serivce.NBoardServiceImpl;
import kr.caincheon.church.common.CAGiguInfoUtil;
import kr.caincheon.church.common.CAGiguInfoVO;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonDaoMultiDTO;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.common.base.Paging;
import kr.caincheon.church.common.utils.UtilCollection;
import kr.caincheon.church.common.utils.UtilInt;
import kr.caincheon.church.controller.AdmBonNoticeControllerConst;
import kr.caincheon.church.dao.AdmGongsoDao;
import kr.caincheon.church.dao.TempleDao;

// Referenced classes of package kr.caincheon.church.service:
//            TempleService

@Service("templeService")
public class TempleServiceImpl extends CommonService
    implements TempleService, AdmBonNoticeControllerConst
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    /* CHURCH 조회 */
    @Resource(name="templeDao")
    private TempleDao templeDao;

    /* NBOARD */
    @Resource(name="nBoardService")
    private NBoardServiceImpl nBoardService;
	
	/* 공소 정보 조회 */
    @Resource(name="admGongsoDao")
    private AdmGongsoDao admGongsoDao;

    /*
     * 공소현황 + 공소미사정보
     * (non-Javadoc)
     * @see kr.caincheon.church.church.service.TempleService#vacancyList(java.util.Map, kr.caincheon.church.common.CommonDaoMultiDTO)
     */
    @Override
    public void vacancyList(Map _params, CommonDaoMultiDTO dto)
        throws Exception
    {
    	// 공소 목록 조회
    	dto.paging = new Paging();
        dto.paging.setPageNo(UtilInt.pint(_params.get("pageNo"), 1));
        dto.paging.setPageSize(UtilInt.pint(_params.get("pageSize"), 4));
        
        templeDao.vacancyList(_params, dto);
        
        // 공소별 미사 조회
        if(dto.daoResult != null)
        {
        	//
            List<Map> rows = (List)dto.daoResult;            
            // 공소별 미사목록
            dto.daoResult2 = queryGongsoMissaList(rows);
        }
    }
    
    /*
     * 공소별 미사목록 조회
     */
    private Map<String, List> queryGongsoMissaList(List<Map> gongsoList) {
    	CommonDaoDTO dto2 = new CommonDaoDTO ();
    	Map<String, List> gonsoMissaList = new HashMap<String, List>();
    	Map<String, String> tmp = new HashMap<String, String>();
        for(Map row : gongsoList) {
        	String g_idx = pnull(row, "G_IDX");
        	tmp.put("g_idx", g_idx);
            admGongsoDao.admGMissaList(tmp, dto2, false);
            // 미사없으면 제거
            List<Map> misaL = new ArrayList<Map>();
            for(int j = 0; j < dto2.otherList.size() ; j++) {
            	Map misaRow = (Map)dto2.otherList.get(j);
            	String misag_idx = pnull(misaRow, "G_IDX");
            	String misa_week = pnull(misaRow, "WEEK");
            	String misa_mnm  = pnull(misaRow, "MNAME");
            	// 미사 최종업데이트날자
            	misaRow.put("UPD_DATE", String.valueOf(dto2.otherData));
            	// 미사 시간
            	if(misag_idx.length()>0 && misa_week.length()>0 && misa_mnm.length()>0) {
            		misaL.add(misaRow);
            	}
            }
            gonsoMissaList.put(g_idx, misaL);
        }
        return gonsoMissaList;
    }

    /**
     * 본당목록 조회 - 본당별 미사를 별도 조회
     */
    @Override
    public void inquireMisaInChurchList(Map _params, CommonDaoMultiDTO dto)
        throws Exception
    {
        if(dto.paging == null)
            dto.paging = new Paging();
        dto.paging.setPageNo(UtilInt.pint(_params.get("pageNo"), 1));
        dto.paging.setPageSize(UtilInt.pint(_params.get("pageSize"), 20));
        templeDao.inquireChurchListWithMisa(_params, dto); // dto.daoResult, dto.paging
        
        if(dto.daoResult != null)
        {
            List rows = (List)dto.daoResult;
            String churchIds[] = new String[rows.size()];
            int i = 0;
            for(int i2 = churchIds.length; i < i2; i++)
            {
                Object o = ((Map)rows.get(i)).get("CHURCH_IDX");
                if(o != null)
                    churchIds[i] = o.toString();
                else
                    churchIds[i] = "0";
            }

            templeDao.selectMisaInfo(churchIds, dto, _params); // 본당별 미사 목록 조회 :: dao.daoResult2
        }
        
    }


    /**
     * 지구별 성당 목록을 이중 MAP<MAP> 구조 데이터로 조회한다. 
     */
    public void groupbyMailhallInRegion(Map _params, CommonDaoMultiDTO dto)
        throws Exception
    {
        dto.daoResult3 = templeDao.groupbyMailhallInRegion(_params);
    }

    /**
     * 본당의 전반적인 정보 조회
     */
    public void selectMailhallTotally(Map _params, CommonDaoMultiDTO dto)
        throws Exception
    {
        if(dto.paging == null)
            dto.paging = new Paging();
        dto.paging.setPageNo(UtilInt.pint(_params.get("pageNo"), 1));
        dto.paging.setPageSize(UtilInt.pint(_params.get("pageSize"), 20));
        
        if(dto.paging2 == null)
            dto.paging2 = new Paging();
        dto.paging2.setPageNo(UtilInt.pint(_params.get("pageNo"), 1));
        dto.paging2.setPageSize(UtilInt.pint(_params.get("pageSize"), 4));
        
        String churchIdx = "";
        Object oChurchIdx = _params.get("churchIdx");
        
        // 필수값이 없다면, 디폴트로 중동구지구의 답동주교좌를 조회한다.
        if(oChurchIdx == null || oChurchIdx.toString().trim().length() == 0)  {
            oChurchIdx = "14001";
            _params.put("churchIdx", oChurchIdx);
            _params.put("church_idx", oChurchIdx);
            //_params.put("step1", "01");
        } else {
            _params.put("church_idx", oChurchIdx);
        }
        
        // 지구명조회
        churchIdx = oChurchIdx.toString();
        String gigu_nm = pnull(_params.get("gigu_nm"));
        if(gigu_nm.length() > 0) {
            String nm = CAGiguInfoUtil.getInstance().getRegionName(gigu_nm);//지구명
            CAGiguInfoVO vo = CAGiguInfoUtil.getInstance().findFirstChurchInGigu(nm);
            _params.put("churchIdx", vo.CHURCH_IDX);
            _params.put("church_idx", vo.CHURCH_IDX);
            _params.put("step1", gigu_nm);
            churchIdx = vo.CHURCH_IDX;
        }
        dto.daoResult1 = templeDao.selectTotallyMainhallInfo(churchIdx); // dto.daoResult1, 본당기본정보
        
        String churchName = pnull(((Map)dto.daoResult1).get("NAME"));
        String history = pnull(((Map)dto.daoResult1).get("HISTORY"));
        String intro = pnull(((Map)dto.daoResult1).get("INTRO"));
        ((Map)dto.daoResult1).put("HISTORY", history.replaceAll("<div style=\"display:none\">fiogf49gjkf0d</div>", ""));
        ((Map)dto.daoResult1).put("INTRO", intro.replaceAll("<div style=\"display:none\">fiogf49gjkf0d</div>", ""));
        
        
        dto.daoResult4 = templeDao.listMailhallInRegion(_params); // dto.daoResult4, 본당의 관할구역, /* 지구별 본당 목록인데, 본당명(키), 본당idx는(값)인 map을 리턴  */
        //dto.daoResult6 = templeDao.selectPriestInChurch(churchIdx); // dto.daoResult6, 본당 사제
        
        _params.put("qo", "B.NAME");
        _params.put("qk", churchName);
        Paging tempPage = new Paging();
        dto.paging.copy(tempPage);
        templeDao.vacancyList(_params, dto); // dto.daoResult
        
        Paging tempPage2 = new Paging();
        dto.paging.copy(tempPage2);
        dto.paging2 = tempPage2;
        dto.paging = tempPage;
        String church[] = new String[1];
        church[0] = churchIdx;
        
        templeDao.selectMisaInfo(church, dto, _params); // dto.daoResult2 (미사)
        dto.daoResult3 = templeDao.selectNewsInChurch(_params); // dto.daoResult3 (뉴스)
        String total = ((Map)dto.daoResult3).get("TOTAL").toString();
        dto.paging.setTotalCount(Integer.parseInt(total));
    }

    /**
     * 본당 둘러보기 사진 조회
     */
    public void selectChurchPictures(Map _params, CommonDaoMultiDTO dto)
        throws Exception
    {
        String church_idx = pnull(_params, "churchIdx");
        List list = templeDao.selectChurchPictures(church_idx);
        dto.daoResult5 = list;
    }

    public void freeDataSource()
    {
        templeDao.free();
    }

    public void newsList(Map _params, CommonDaoMultiDTO dto)
        throws Exception
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "Service Called. [params:"+_params+"]" );

		pnullPut(_params, "pageNo", "1");
		_params.put("pageSize", "20");
		_params.put("is_view", "Y");

//        if(dto.paging == null)
//            dto.paging = new Paging();
//        
//        dto.paging.setPageNo(UtilInt.pint(_params.get("pageNo")));
//        dto.paging.setPageSize(UtilInt.pint(_params.get("pageSize")));
//        
//        _params.put("catcode", "4");
//        _params.put("b_idx", "11,17");
//        String idx = pnull(_params.get("church_idx"));
//        if(idx.length() > 0)
//        {
//            idx = (new StringBuilder("'")).append(idx.replace(",", "','")).append("'").toString();
//            _params.put("church_idx", idx);
//        }
//        templeDao.newsList(_params, dto);
//        _params.put("church_idx", idx.replace("'", ""));
		
		int attachedFileCount = 5;
        CommonDaoDTO dto2 = nBoardService.nboardList(_params, LEFT_MENU_DATA_PG, true, attachedFileCount);
        dto2.setPaging(ipnull(_params, "pageNo"), ipnull(_params, "pageSize"));
        dto.daoResult  = dto2.daoList;
        dto.paging     = dto2.paging;
        dto.daoResult3 = templeDao.groupbyMailhallInRegion(_params);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Result Service >> \n\t\t"+dto  );
    }

    public void newsVO(Map _params, CommonDaoMultiDTO dto)
        throws Exception
    {
        templeDao.newsVO(_params, dto);
    }

    public void parishAlbumList(Map _params, CommonDaoMultiDTO dto)
        throws Exception
    {
        if(dto.paging == null)
            dto.paging = new Paging();
        dto.paging.setPageNo(UtilInt.pint(_params.get("pageNo")));
        dto.paging.setPageSize(UtilInt.pint(_params.get("pageSize")));
        templeDao.parishAlbumList(_params, dto);
    }

    public void albumVO(Map _params, CommonDaoMultiDTO dto)
        throws Exception
    {
        if(dto.paging == null)
            dto.paging = new Paging();
        dto.paging.setPageNo(UtilInt.pint(_params.get("pageNo")));
        dto.paging.setPageSize(UtilInt.pint(_params.get("pageSize")));
        templeDao.albumVO(_params, dto);
    }

    public Map templeAlbumContents(Map _params)
        throws Exception
    {
        return templeDao.templeAlbumContents(_params);
    }

    // 글쓰기 권한 사용자를 위한 op
    public boolean church_view_for_user(Map _params, String left_menu_data_pg, HttpServletRequest httpservletrequest) throws Exception {
    	
    	String mode = pnull(_params, "mode");//I or U
    	
    	//
    	if(mode.charAt(0)=='U' || mode.charAt(0)=='u') {
    		int attachedFileCount = 5;
    		nBoardService.nboardContents(_params, left_menu_data_pg, false, attachedFileCount); 
    	}
    	
    	return true;
    }
    

    // 글쓰기 권한 사용자를 위한 op
    public boolean church_view_for_user_write_update(Map _params, String left_menu_data_pg, HttpServletRequest httpservletrequest) throws Exception {
    	
    	String mode = pnull(_params, "mode");//I or U
    	
    	switch(mode.charAt(0)) {
    	case 'i':
    	case 'I':
    		nBoardService.nboardInsert(_params, LEFT_MENU_DATA_PG, httpservletrequest);
    		break;
    	case 'u':
    	case 'U':
    		nBoardService.nboardModify(_params, LEFT_MENU_DATA_PG, httpservletrequest);
    		break;
    	}
    	
    	return true;
    }

    /**
     * front :: 통합검색
     * @see kr.caincheon.church.church.service.TempleService#unifySearch(java.util.Map, java.sql.Connection)
     */
	@Override
	public CommonDaoMultiDTO unifySearch(Map _params, Connection conn) throws Exception {
		D(_logger, Thread.currentThread().getStackTrace(), "Service Called. params:"+_params );
		
		//
		List list = templeDao.unifySearch(_params, conn);
		
		// 미사 조회를 위한 추출
		CommonDaoMultiDTO dto = new CommonDaoMultiDTO();
		List<String> chrchIdxList = new ArrayList<String>();
		List<Map> churchList = new ArrayList<Map>();
		List<Map> gongsoList = new ArrayList<Map>();
        for(int i = 0, i2 = list.size(); i < i2; i++) {
        	Map row = (Map)list.get(i);
        	Object t = row.get("TYPE");
            if(t.toString().equals("C")) {
            	chrchIdxList.add( row.get("CHURCH_IDX").toString() );
            	churchList.add(row);
            } else {
            	gongsoList.add(row);
            }
        }
        // 본당 미사 목록
        String[] churchIdxs = UtilCollection.toString(chrchIdxList);
        templeDao.selectMisaInfo(churchIdxs, dto, _params); // 본당별 미사 목록 조회 :: dto.daoResult2 // Map<String, List>
        dto.daoResult1 = churchList; // List
        
		// 공소 미사 목록
        dto.daoResult3 = gongsoList; // List
        dto.daoResult4 = queryGongsoMissaList(gongsoList); // Map<String, List>
        
		D(_logger, Thread.currentThread().getStackTrace(), "Service Returned. results:"+dto );
		
		return dto;
	}
}
