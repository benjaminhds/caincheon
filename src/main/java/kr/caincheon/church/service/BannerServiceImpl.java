package kr.caincheon.church.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.dao.BannerDao;

/**
 * 배너 서비스 클래스
 * @author benjamin
 */
@Service("bannerService")
public class BannerServiceImpl extends CommonService implements BannerService
{


    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="bannerDao")
    private BannerDao bannerDao;

    /*
     * (non-Javadoc)
     * @see kr.caincheon.church.service.BannerService#mainPopupList(java.util.Map)
     */
    @Override
    public List mainBannerList(Map _params)  throws CommonException 
    {
    	_params.put("use_yn", "Y"); // 사용여부(Y:사용, N:중지)
    	_params.put("open_yn", "Y"); // 노출여부
    	_params.put("open_date", "now");
    	
    	CommonDaoDTO dto = new CommonDaoDTO();
    	bannerDao.bannerList(_params, dto);
    	
    	_params.remove("use_yn");
    	_params.remove("open_yn");
    	_params.remove("open_date");
    	
    	return dto.daoList;
    }
    
    /**
     * 목록 조회
     * (non-Javadoc)
     * @see kr.caincheon.church.service.BannerService#admBannerList(java.util.Map)
     */
    @Override
    public CommonDaoDTO admBannerList(Map _params) throws CommonException 
    {
    	CommonDaoDTO dto = new CommonDaoDTO();
        bannerDao.bannerList(_params, dto);
        return dto;
    }

    /**
     * 배너 조회
     * (non-Javadoc)
     * @see kr.caincheon.church.service.BannerService#admBannerContents(java.util.Map)
     */
    @Override
    public Map admBannerContents(Map _params) throws CommonException 
    {
        return bannerDao.bannerContents(_params);
    }

    /**
     * 배너 신규 추가
     * (non-Javadoc)
     * @see kr.caincheon.church.service.BannerService#admBannerInsert(java.util.Map)
     */
    @Override
    public boolean admBannerInsert(Map _params, javax.servlet.http.HttpServletRequest request) throws CommonException 
    {

    	// file upload handle
        List uploadedFiles = fileUploadProcess(_params, request, getUploadBaseURI("banner"));
        if(uploadedFiles !=null ) {
        	D(_logger, Thread.currentThread().getStackTrace(), "A banner Image Uploaded.[count="+uploadedFiles.size()+", Files:"+uploadedFiles+"]" );
        }
        
        return bannerDao.bannerInsert(_params, uploadedFiles);
    }

    /**
     * 배너 수정
     * (non-Javadoc)
     * @see kr.caincheon.church.service.BannerService#admBannerModify(java.util.Map)
     */
    @Override
    public boolean admBannerModify(Map _params, javax.servlet.http.HttpServletRequest request) throws CommonException 
    {
    	// file upload handle
        List uploadedFiles = fileUploadProcess(_params, request, getUploadBaseURI("banner"));
        if(uploadedFiles !=null ) {
        	D(_logger, Thread.currentThread().getStackTrace(), "A banner Image Uploaded.[count="+uploadedFiles.size()+", Files:"+uploadedFiles+"]" );
        }
        
        return bannerDao.bannerModify(_params, uploadedFiles);
    }

    /**
     * 배너 서비스 중지/재개 flag 관리
     * (non-Javadoc)
     * @see kr.caincheon.church.service.BannerService#admBannerDelete(java.util.Map)
     */
    @Override
    public boolean admBannerDelete(Map _params) throws CommonException 
    {
        return bannerDao.bannerDelete(_params);
    }
}
