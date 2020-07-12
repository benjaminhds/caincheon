// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CureServiceImpl.java

package kr.caincheon.church.samok.service;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.admin.dao.NBoardDao;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.CommonService;

// Referenced classes of package kr.caincheon.church.service:
//            CureService

@Service("cureService")
public class CureServiceImpl extends CommonService
    implements CureService
{

    private final Logger _logger = Logger.getLogger(getClass());
    
//    @Resource(name="supportDao")
//    private SupportDao supportDao;

    @Resource(name="nBoardDao")
    private NBoardDao nBoardDao;
    
    /*
     * 공통 req 파라메터
     */
    private void reqParamsSettings(Map _params) {
    	pnullPut(_params, "is_view", "Y");
    }
    
    @Override
    public CommonDaoDTO cureList(Map _params, String left_menu_data_pg) throws CommonException
    {
    	reqParamsSettings(_params);
    	
    	CommonDaoDTO dto = new CommonDaoDTO();
    	
        try {
			nBoardDao.nboardList(dto, _params, left_menu_data_pg, 1);
		} catch (Exception e) {
			throw new CommonException("NBoard를 조회하지 못했습니다.", "EXPT-100", e.getMessage());
		}
        
        return dto;
    }

    @Override
    public CommonDaoDTO cureContents(Map _params, String left_menu_data_pg) throws CommonException
    {
    	reqParamsSettings(_params);
    	
    	CommonDaoDTO rtDTO = new CommonDaoDTO();
    	try {
			nBoardDao.nboardContents(rtDTO, _params, 5);
		} catch (Exception e) {
			throw new CommonException("NBoard를 조회하지 못했습니다.", "EXPT-200", e.getMessage());
		}
    	
    	return rtDTO;
    }

}
