package kr.caincheon.church.service;

import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;


/**
 * 배너 서비스 인터페이스
 * @author benjamin
 */
public interface BannerService
{

	/** 배너 사용 대상 조회 : home.do 에서.. */
	public List mainBannerList(Map _params) throws CommonException ;
	
	/** 배너 목록 */
    public  CommonDaoDTO admBannerList(Map map) throws CommonException ;

    //public  int bannerListCount(Map map);

    /** 배너 조회 */
    public  Map admBannerContents(Map map) throws CommonException ;

    /** 배너 신규 추가 */
    public  boolean admBannerInsert(Map map, javax.servlet.http.HttpServletRequest request) throws CommonException ;

    /** 배너 수정 */
    public  boolean admBannerModify(Map map, javax.servlet.http.HttpServletRequest request) throws CommonException ;

    /** 배너 상태변경 : 중지(N)/사용(Y) */
    public  boolean admBannerDelete(Map map) throws CommonException ;
}
