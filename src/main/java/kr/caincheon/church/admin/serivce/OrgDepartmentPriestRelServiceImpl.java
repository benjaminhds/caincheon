package kr.caincheon.church.admin.serivce;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.admin.dao.OrgDepartmentPriestRelDao;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.common.base.Const;

/**
 * 임지 발령 정보를 관리
 * @author benjamin
 */
@Service("orgDepartmentPriestRelService")
public class OrgDepartmentPriestRelServiceImpl extends CommonService implements OrgDepartmentPriestRelService
{
    private final Logger _logger = Logger.getLogger(getClass());
    
    
    @Resource(name="orgDepartmentPriestRelDao")
    private OrgDepartmentPriestRelDao orgDepartmentPriestRelDao;
    
    //============= code management ==================
    
	@Override
	public int insert(Map params) throws CommonException, Exception {
		D(_logger, Thread.currentThread().getStackTrace(), "..called");
		int i = orgDepartmentPriestRelDao.insert(params);
		D(_logger, Thread.currentThread().getStackTrace(), ".. Return : " + i );
		return i;
	}

	@Override
	public int update(Map params) throws CommonException, Exception {
		D(_logger, Thread.currentThread().getStackTrace(), "..called");
		int i = orgDepartmentPriestRelDao.update(params);
		D(_logger, Thread.currentThread().getStackTrace(), ".. Return : " + i );
		return i;
	}

	@Override
	public int delete(Map params) throws CommonException, Exception {
		D(_logger, Thread.currentThread().getStackTrace(), "..called");
		int i = orgDepartmentPriestRelDao.delete(params);
		D(_logger, Thread.currentThread().getStackTrace(), ".. Return : " + i );
		return i;
	}

	@Override
	public void select(Map params) throws CommonException, Exception {
		D(_logger, Thread.currentThread().getStackTrace(), "..called");
		
		// param
		pnullPut(params, "pageNo", "1");
		pnullPut(params, "pageSize", "30");
		String pageNo = pnull(params, "pageNo");
		String pageSize = pnull(params, "pageSize");
		
		// dao
		List<Map> list = orgDepartmentPriestRelDao.select(params);
		int total = orgDepartmentPriestRelDao.selectCount();
		
		// return
		params.put(Const.ADM_MAPKEY_LIST, list);
		params.put(Const.ADM_MAPKEY_COUNT, total);
		setPaging(params, pageNo, pageSize, total);
		
		D(_logger, Thread.currentThread().getStackTrace(), ".. end. "  );
		
	}


}
