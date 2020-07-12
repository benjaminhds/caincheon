package kr.caincheon.church.admin.dao;

import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonException;

/**
 * 임지 발령 정보를 관리
 * @author benjamin
 */
public interface OrgDepartmentPriestRelDao
{

	// management a code_master 
	public int insert(Map params) throws CommonException, Exception;
	public int update(Map params) throws CommonException, Exception;
	public int delete(Map params) throws CommonException, Exception;
	public List<Map> select(Map params) throws CommonException, Exception;
	public int selectCount() throws CommonException, Exception;
	public int selectCount(Map params) throws CommonException, Exception;
	
}
