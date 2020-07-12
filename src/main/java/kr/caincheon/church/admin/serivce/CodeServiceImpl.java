package kr.caincheon.church.admin.serivce;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.admin.dao.CodeDao;
//import kr.caincheon.church.common.CommonSQL;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.common.base.Const;


@Service("codeService")
public class CodeServiceImpl extends CommonService implements CodeService
{
    private final Logger _logger = Logger.getLogger(getClass());
    
    
    @Resource(name="codeDao")
    private CodeDao codeDao;
    
    //============= code management ==================
    
	/** 코드를 등록 */
    public int iudCodeMaster(Map params) throws CommonException, Exception {
    	int i = -1;
    	String mode = pnull(params, "mode");
    	D(_logger, Thread.currentThread().getStackTrace(), mode+") ..called");
    	
    	if("i".equalsIgnoreCase(mode)) {
    		i = codeDao.insertCode(params);
    		
    	} else if("u".equalsIgnoreCase(mode)) {
    		i = codeDao.updateCode(params);
    		
    	} else if("d".equalsIgnoreCase(mode)) {
    		i = codeDao.deleteCode(params);
    	}
    	
    	D(_logger, Thread.currentThread().getStackTrace(), mode+") .. Return : " + i );
    	
		return i;
    }
    
	@Override
	public Map viewCode(Map params) throws CommonException, Exception {
		D(_logger, Thread.currentThread().getStackTrace(), "..called");
		
		CommonDaoDTO dto = codeDao.selectCodes(params);
		
		D(_logger, Thread.currentThread().getStackTrace(), ".. Return : " + dto.daoList );
		
		return dto.daoList==null||dto.daoList.size()<1 ? new HashMap():(Map)dto.daoList.get(0);
	}

	@Override
	public Map listCodes(Map params) throws CommonException, Exception {
		D(_logger, Thread.currentThread().getStackTrace(), "..called");
		
		CommonDaoDTO dto = null;
		int  rtTotal = -1;
		
		try {
			dto  = codeDao.selectCodes(params);
			
		} catch (SQLException e) {
			throw new CommonException(e.getMessage(), "ERR-900", e);
		}
		
		D(_logger, Thread.currentThread().getStackTrace(), ".. Return : " + dto.daoList );
		
		// return 
		String pageNo   = pnull(params, "pageNo",   "1");
		String pageSize = pnull(params, "pageSize", "20");
		params.put(Const.ADM_MAPKEY_LIST,  dto.daoList==null ? new ArrayList() : dto.daoList);
		params.put(Const.ADM_MAPKEY_COUNT, dto.daoTotalCount);
		setPaging(params, pageNo, pageSize, rtTotal);
		
		D(_logger, Thread.currentThread().getStackTrace(), "..return= " + dto);
		D(_logger, Thread.currentThread().getStackTrace(), "..return(m)= " + params);
		
		return null;
	}


    //============= code-instance management ==================

    /** 코드인스턴스를 리턴 */
	@Override
    public int iudCodeInstance(Map params) throws CommonException, Exception {
    	int i = -1;
    	
    	D(_logger, Thread.currentThread().getStackTrace(), "..called. params="+params);
    	
    	String mode = pnull(params, "mode");
    	
    	try {
			if("i".equalsIgnoreCase(mode)) {

				// 자동채번
				String code      = pnull(params, "code");
				pnullPut(params, "code_inst", "(SELECT MAX(CODE_INST)+1 FROM CODE_INSTANCE WHERE CODE='"+code+"')" );
				
				i = codeDao.insertCodeInstance(params);
				
			} else if("u".equalsIgnoreCase(mode)) {
				i = codeDao.updateCodeInstance(params);
				
			} else if("d".equalsIgnoreCase(mode)) {
				i = codeDao.deleteCodeInstance(params);
				
			}
		} catch (Exception e) {
			params.put("msg", e.getMessage());
		}
    	
    	D(_logger, Thread.currentThread().getStackTrace(), ".. Return : " + i );
    	
		return i;
    }
    
	
	@Override
	public Map viewCodeInstance(Map params) throws CommonException, Exception {
		D(_logger, Thread.currentThread().getStackTrace(), "..called");
		
		CommonDaoDTO dto = codeDao.selectCodeInstances(params);
		
		D(_logger, Thread.currentThread().getStackTrace(), ".. List : " + dto.daoList );
		
		return dto.daoList==null||dto.daoList.size()<1 ? new HashMap():(Map)dto.daoList.get(0);
	}

	@Override
	public CommonDaoDTO listCodeInstances(Map params) throws CommonException, Exception {
		D(_logger, Thread.currentThread().getStackTrace(), "service called: "+params);
		
		CommonDaoDTO dto = new CommonDaoDTO();
		
		try {
			params.remove("use_yn");//사용중
			params.remove("del_yn");//미삭제코드
			pnullPut(params, "orderby", "CODE_INST ASC, ORDER_NO ASC");//정렬
			
			dto = codeDao.selectCodeInstances(params);
			
			// 목록에서 memo 컬럼의 \n 제거
			checkMemoColumn(dto.daoList); 
			
		} catch (SQLException e) {
			throw new CommonException(e.getMessage(), "ERR-900", e);
		}
		
		// return 
		String pageNo   = pnull(params, "pageNo",   "1");
		String pageSize = pnull(params, "pageSize", "20");
		setPaging(dto, pageNo, pageSize, dto.daoTotalCount);
		
		D(_logger, Thread.currentThread().getStackTrace(), "service return: " + dto);
		
		return dto;
	}

	/** 코드인스턴스의 메모값을 조회 */
	@Override
	public List<Map> selectedAreaCodeMapPolygonPath(Map params) throws CommonException, Exception {
		
		D(_logger, Thread.currentThread().getStackTrace(), "..called");
		
		List<Map> rtList = codeDao.selectedAreaCodeMapPolygonPath(params);
		
		D(_logger, Thread.currentThread().getStackTrace(), "..return= " + rtList);
		return rtList ;
	}

	//
	private void checkMemoColumn(List<Map> rtList) {
		if(rtList!=null && rtList.size()>0) {
			String tmp = "";
			for(int i=0, i2=rtList.size() ; i<i2 ; i++) {
				
				Map row = rtList.get(i);
				tmp = pnull(row, "MEMO");
				tmp = tmp.replaceAll("\n", "<BR>").replaceAll("\r", "").replaceAll("\"", "'");
				row.put("MEMO", tmp);
			}
		}
	}

	//
	private void removeMemoColumn(List<Map> rtList) {
		if(rtList!=null && rtList.size()>0) {
			String tmp = "";
			for(int i=0, i2=rtList.size() ; i<i2 ; i++) {
				
				Map row = rtList.get(i);
				row.remove("MEMO");
			}
		}
	}
}
