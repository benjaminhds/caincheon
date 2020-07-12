package kr.caincheon.church.father.service;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.common.utils.UtilDate;


@Service("fatherCalService")
public class FatherCalServiceImpl extends CommonService {

	private final Logger _logger = Logger.getLogger(getClass());

    @Resource(name="fatherCalDao")
    private FatherCalDaoImpl fatherCalDao;
    
	/*
	 * 사제 달력 :: 사제 서품일/축일을 달력과 연동한 목록 조회
	 */
	public Map selectFathersByDiary(Map _params) throws CommonException {
		D(_logger, Thread.currentThread().getStackTrace(), "Service selectFathersByDiary Called.[params:"+_params+"]" );
		
		Map rtMap = fatherCalDao.selectFathersByDiary(_params);
		
		String yyyymm = pnull(_params.get("srch_ym"));
		_params.put("PREV_MONTH", UtilDate.getYM(yyyymm, -1));
		_params.put("NEXT_MONTH", UtilDate.getYM(yyyymm, 1));
		
		D(_logger, Thread.currentThread().getStackTrace(), "return Service selectFathersByDiary.["+rtMap+"]" );
		
		return rtMap;
	}
	
}
