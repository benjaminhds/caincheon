package kr.caincheon.church.search.service;

import java.sql.Connection;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.church.service.TempleService;
import kr.caincheon.church.common.base.CommonDaoMultiDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.father.service.PriestServiceImpl;
import kr.caincheon.church.intro.service.DepartmentService;
import kr.caincheon.church.intro.service.GyoguIntroService;

@Service("unifySearchService")
public class UnifySearchServiceImpl extends CommonService {

	private final Logger _logger = Logger.getLogger(getClass());
	
	// 사제/선종사제
    @Resource(name="priestService")
    private PriestServiceImpl priestService;
    // 교구청
    @Resource(name="departmentService")
    private DepartmentService departmentService;
    // 본당/공소
    @Resource(name="templeService")
    private TempleService templeService;
    // 기관/단체/수도회
    @Resource(name="gyoguIntroService")
    private GyoguIntroService gyoguIntroService;
    
	
	/**
	 * 통합검색 메인 서비스
	 */
	public CommonDaoMultiDTO multiSearch(Map _params) throws CommonException {
		
		D(_logger, Thread.currentThread().getStackTrace(), "Service Called. params:"+_params );
		
		CommonDaoMultiDTO dto = new CommonDaoMultiDTO ();
		
		String srchText = pnull(_params, "srchText").trim();
		if(srchText.length() < 1) {
			return null;
		}
		
		// 1. 사제 : 성명/세례명/임지/성구
		Connection conn = null;
		try {
			conn = priestService.getConn();
			dto.daoResult1 = priestService.unifySearchPriest(_params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 2. 교구청 : 부서명, 부서소개
		try {
			dto.daoResult2 = departmentService.unifySearch(_params, conn);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		// 3. 본당/공소 : 본당명, 주보성인, 주소
		try {
			dto.daoResult3 = templeService.unifySearch(_params, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 4. 기관/단체/수도회 : 기관명
		try {
			dto.daoResult4 = gyoguIntroService.unifySearchOrganization(_params, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 5. 선종사제 : 이름, 세례명, 묘소
		try {
			dto.daoResult5 = priestService.unifySearchPriestOld(_params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 종료
		priestService.closeConn();
		
		D(_logger, Thread.currentThread().getStackTrace(), "Service Return. dto:"+dto );
		
		return dto;
	}
	
}
