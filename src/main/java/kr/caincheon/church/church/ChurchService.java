package kr.caincheon.church.church;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.admin.dao.CodeDao;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.common.base.Const;

/**
 * 
 * @author 1
 */
@Service("churchService")
public class ChurchService extends CommonService
{
    private final Logger L = Logger.getLogger(getClass());
    
    // 멀티게시판 조회
    @Resource(name="churchDAO")
    private ChurchDAO churchDAO;

    // 공통코드 조회
    @Resource(name="codeDao")
    private CodeDao codeDao;
    
    
	/** 
	 * 지구별 본당목록 조회
	 * - gigu_code : 파라메터가 있다면 해당 지구의 본당목록 조회, otherwise 전체 지구별 본당 목록 
	 */
	public java.util.List listChurchListInGigu(Map params) throws CommonException, Exception {
		L.debug("..SVC called: " + params);
		
		// param
		pnullPut(params, "pageSize", "300");// 총 개수
		
		// 파라메터는 1개 : gigu_code
		
		// list
		java.util.List rtList = churchDAO.selectChurchListInGigu(params);
		
		// return
		L.debug("..SVC end: " + rtList );
		
		return rtList;
	}

    
    
   	

}
