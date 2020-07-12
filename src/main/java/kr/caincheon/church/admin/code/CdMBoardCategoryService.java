package kr.caincheon.church.admin.code;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.admin.dao.CodeDao;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.common.base.Const;


@Service("cdMBoardCategoryService")
public class CdMBoardCategoryService extends CommonService
{
    private final Logger L = Logger.getLogger(getClass());
    
    // 멀티게시판 조회
//    @Resource(name="boardCategoryDao")
//    private BoardCategoryDao boardCategoryDao;
    
    // 멀티게시판 조회
    @Resource(name="cdMBoardCategoryDAO")
    private CdMBoardCategoryDAO cdMBoardCategoryDAO;

    // 공통코드 조회
    @Resource(name="codeDao")
    private CodeDao codeDao;
    
    
    //============= 게시판코드(b_idx) management ==================

	/** 코드를 리턴 */
	public CommonDaoDTO listBoard(Map params) throws CommonException, Exception {
		L.debug("..called param: " + params);
		
		// param
		pnullPut(params, "code", "000011");
		pnullPut(params, "pageNo", "1");
		pnullPut(params, "pageSize", "30");
		String pageNo = pnull(params, "pageNo");
		String pageSize = pnull(params, "pageSize");
		
		// board list
		CommonDaoDTO dto = cdMBoardCategoryDAO.selectMBoardCatetoryList(params);

		// codelist : board type list
		pnullPut(params, "orderby", "ORDER_NO ASC");//정렬
		CommonDaoDTO cddto = codeDao.selectCodeInstances(params);
		params.remove("orderby");
		dto.otherList = cddto.daoList;
		
		// return
		params.put(Const.ADM_MAPKEY_LIST, dto.daoList);
		params.put(Const.ADM_MAPKEY_COUNT, dto.daoTotalCount);
		params.put(Const.ADM_MAPKEY_LIST_OTHERS, cddto.daoList);
		setPaging(params, pageNo, pageSize, dto.daoTotalCount);
		
		L.debug(".. end. rtn: " + dto );
		
		return dto;
	}

	// 보드 코드 관리
    /** mode in {I,U,D}에 따라서 CUD를 처리하는 메서드 */
    public int iudBoard(Map params) throws CommonException, Exception {
    	L.debug("..called param: " + params);
    	
    	int rtVal = 0;
    	String mode = pnull(params, "mode");
    	switch(mode.charAt(0)) {
    	case 'U':
    	case 'u':
//    		rtVal = boardCategoryDao.updateBoard(params);
    		break;

    	case 'I':
    	case 'i':
//    		rtVal = boardCategoryDao.insertBoard(params);
    		break;
    		
    	case 'D':
    	case 'd':
//    		rtVal = boardCategoryDao.deleteBoard(params);
    		break;
    	}
    	
    	L.debug("..done. rtn: "+rtVal);
    	return rtVal;
    }
    
    
   	
    
    
    //============= 게시판코드 카테고리(c_idx) management ==================
    
    // 
	public CommonDaoDTO listBoardCategory(Map params) throws CommonException, Exception {
		L.debug("..called param: " + params);
		
		// param
		pnullPut(params, "pageNo", "1");
		pnullPut(params, "pageSize", "30");
		String pageNo = pnull(params, "pageNo");
		String pageSize = pnull(params, "pageSize");
		
		// dao
		CommonDaoDTO dto = cdMBoardCategoryDAO.selectMBoardCatetoryList(params);
		
		// return
		params.put(Const.ADM_MAPKEY_LIST, dto.daoList);
		params.put(Const.ADM_MAPKEY_COUNT, dto.daoTotalCount);
		setPaging(params, pageNo, pageSize, dto.daoTotalCount);
		
		L.debug(".. end. rtn: " + dto );
		
		return dto;
	}

	/** mode in {I,U,D}에 따라서 CUD를 처리하는 메서드 */
    public int iudBoardCategory(Map params) throws CommonException, Exception {
    	L.debug("..called param: " + params);
    	
    	int rtVal = 0;
    	String mode = pnull(params, "mode");
    	switch(mode.charAt(0)) {
    	case 'U':
    	case 'u':
//    		rtVal = boardCategoryDao.updateBoardCategory(params);
    		break;

    	case 'I':
    	case 'i':
//    		rtVal = boardCategoryDao.insertBoardCategory(params);
    		break;
    		
    	case 'D':
    	case 'd':
//    		rtVal = boardCategoryDao.deleteBoardCategory(params);
    		break;
    	}
    	
    	L.debug("..done. rtn: "+rtVal);
    	return rtVal;
    }


}
