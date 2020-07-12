// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PriestServiceImpl.java

package kr.caincheon.church.father.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.common.email.EmailBean;
import kr.caincheon.church.common.email.EmailSenderOnSpring;
import kr.caincheon.church.dao.PriestDao;

// Referenced classes of package kr.caincheon.church.service:
//            PriestService

@Service("priestService")
public class PriestServiceImpl extends CommonService
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    // 사제 정보 조회 
    @Resource(name="priestDao")
    private PriestDao priestDao;
    
    // 메일 발송
    @Resource(name="emailSenderOnSpring")
    private EmailSenderOnSpring emailSenderOnSpring;

    //
    public CommonDaoDTO priestList(Map _params) throws Exception
    {
    	CommonDaoDTO dto = new CommonDaoDTO();
        priestDao.priestList(dto, _params);
        dto.setPaging(ipnull(_params, "pageNo", 1), ipnull(_params, "pageSize", 20));

        return dto;
    }

    /*
	 * 프론트에서 사제 상세보기 OP
	 */
    public CommonDaoDTO priestContents(Map _params) throws Exception
    {
    	CommonDaoDTO dto = new CommonDaoDTO();
    	priestDao.priestContents(dto, _params);
        return dto;
    }

    //
    public boolean priestMailSend(Map _params) throws CommonException
    {
        boolean success = true;
        try
        {
        	
            EmailBean bean = new EmailBean();
            if(_params.containsKey("senderNm")) bean.setFromName(pnull(_params.get("senderNm")));
            if(_params.containsKey("sender"  )) bean.setFrom(pnull(_params.get("sender")));
            
            bean.setTo(pnull(_params.get("receiver")));
            bean.setSubject(pnull(_params.get("title")));
            bean.setContent(pnull(_params.get("contents")));
            emailSenderOnSpring.SendEmail(bean);
        }
        catch(Exception e)
        {
            throw new CommonException(e.getMessage(), e);
        }
        return success;
    }

    //
    public CommonDaoDTO holyList(Map _params) throws Exception
    {
    	CommonDaoDTO dto = new CommonDaoDTO();
        priestDao.holyList(dto, _params);
        dto.setPaging(ipnull(_params, "pageNo", 1), ipnull(_params, "pageSize", 8));
        
        return dto;
    }

    //
    public Map holyContents(Map _params) throws Exception
    {
        return priestDao.holyContents(_params);
    }

    /*
	 * 선종사제 목록
	 */
    public CommonDaoDTO befPriestList(Map _params) throws Exception
    {
    	CommonDaoDTO dto = new CommonDaoDTO();
        priestDao.befPriestList(dto, _params);
        dto.setPaging(ipnull(_params, "pageNo", 1), ipnull(_params, "pageSize", 20));
        
        dto.otherData = priestDao.brialPlaceList(_params);
        
        return dto;
    }

    
    // 선종사제 묘소 추가 등록
    public boolean befPriestTombInsert(Map _params) throws Exception
    {
        return priestDao.befPriestTombInsert(_params);
    }

    // 선종사제 묘소 삭제
    public boolean befPriestTombDelete(Map _params) throws Exception
    {
        return priestDao.befPriestTombDelete(_params);
    }

    // 선종사제 묘소 상세정보
    public CommonDaoDTO befPriestContents(Map _params) throws Exception
    {
    	CommonDaoDTO dto = new CommonDaoDTO();
    	dto.daoDetailContent = priestDao.befPriestContents(_params);
    	dto.otherData = priestDao.brialPlaceList(_params);
        return dto;
    }

    /**
	 * 관리자 > 선종사제 등록
	 */
    public boolean befPriestInsert(Map _params) throws Exception
    {
        return priestDao.befPriestInsert(_params);
    }

    /**
	 * 관리자 > 선종사제 수정
	 */
    public boolean befPriestModify(Map _params) throws Exception
    {
        return priestDao.befPriestModify(_params);
    }

    /**
	 * 관리자 > 선종사제 수정
	 */
    public boolean befPriestDelete(Map _params) throws Exception
    {
        return priestDao.befPriestDelete(_params);
    }

    /*
     * 관리자) 사제 목록조회
     */
    public CommonDaoDTO admPriestList(Map _params) throws Exception
    {
    	CommonDaoDTO dto = new CommonDaoDTO();
        priestDao.admPriestList(dto, _params);
        dto.setPaging(ipnull(_params, "pageNo", 1), ipnull(_params, "pageSize", 20));

        return dto;
    }

    /*
     * 관리자) 사제 상세보기
     * (non-Javadoc)
     * @see kr.caincheon.church.father.service.PriestService#admPriestContents(java.util.Map)
     */
    public Map admPriestContents(Map _params) throws Exception
    {
        return priestDao.admPriestContents(_params);
    }

    //
    public List admPriestDCodeList(Map _params) throws Exception
    {
        return priestDao.admPriestDCodeList(_params);
    }

    //
    public boolean admPriestInsert(Map _params) throws Exception
    {
        return priestDao.admPriestInsert(_params);
    }

    /*
	 * 관리자에서 사제 정보 수정하기
	 */
    public boolean admPriestModify(Map _params) throws Exception
    {
        return priestDao.admPriestModify(_params);
    }

    /*
     * 사제 삭제
     */
    public boolean admPriestDelete(Map _params) throws Exception
    {
        return priestDao.admPriestOrgDelete(_params);
    }

    //
    public boolean admPriestDepartDelete(Map _params) throws Exception
    {
        return priestDao.admPriestDelete(_params);
    }

    /*
     * 발령정보 upsert 처리
     */
    public CommonDaoDTO upsertPreistAssign(Map _params) throws Exception {
    	CommonDaoDTO dto = new CommonDaoDTO();
    	priestDao.upsertPreistAssign(dto, _params);
    	return dto;
    }

    /*
     * 발령정보 upsert 처리
     */
    public CommonDaoDTO deletePreistAssign(Map _params) throws Exception {
    	CommonDaoDTO dto = new CommonDaoDTO();
    	priestDao.deletePreistAssign(dto, _params);
    	return dto;
    }

    /*
     * 사제 발령 정보를 조회한다.
     */
    public List selectOrgDepartmentPriestRel(Map _params) throws Exception {
    	String p_idx = pnull(_params, "p_idx");
    	return priestDao.selectOrgDepartmentPriestRel(p_idx);
    }

	/**
	 * 통합검색 시작시 리턴
	 */
	public Connection getConn() {
		return priestDao.getConn();
	}

	/**
	 * 통합검색 종료시 해제
	 */
	public void closeConn() {
		priestDao.closeConn();
	}
    
    /**
     * 1. 사제 : 성명/세례명/임지/성구
     * @see kr.caincheon.church.father.service.PriestService#unifySearchPriest(java.util.Map)
     */
	public List unifySearchPriest(Map _params) throws Exception {
		D(_logger, Thread.currentThread().getStackTrace(), "Service Called. params:"+_params );
		
		List list = priestDao.unifySearchPriest(_params);
		
		D(_logger, Thread.currentThread().getStackTrace(), "Service Returned. results:"+list );
		
		return list;
	}

	/**
	 * 5. 선종사제 : 이름, 세례명, 묘소
	 * @see kr.caincheon.church.father.service.PriestService#unifySearchPriestOld(java.util.Map)
	 */
	public List unifySearchPriestOld(Map _params) throws Exception {
		D(_logger, Thread.currentThread().getStackTrace(), "Service Called. params:"+_params );
		
		List list = priestDao.unifySearchPriestOld(_params);
		
		D(_logger, Thread.currentThread().getStackTrace(), "Service Returned. results:"+list );
		
		return list;
	}

}
