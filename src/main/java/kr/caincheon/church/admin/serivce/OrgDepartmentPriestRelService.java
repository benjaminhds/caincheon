package kr.caincheon.church.admin.serivce;

import java.util.Map;

import kr.caincheon.church.common.base.CommonException;

/**
 * 임지 발령 정보를 관리
 * @author benjamin
 */
public interface OrgDepartmentPriestRelService
{
    public int insert(Map params) throws CommonException, Exception;
    
    public int update(Map params) throws CommonException, Exception;
    
    public int delete(Map params) throws CommonException, Exception;

    /** 코드를 리턴 */
    public void select(Map params) throws CommonException, Exception;
   
    
}
