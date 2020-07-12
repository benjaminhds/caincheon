package kr.caincheon.church.admin.serivce;

import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonException;

public interface OrgHierarchyService
{

	/**
	 * 조직 TREE 구성을 위한 조직의 1레벨 목록 조회
	 * @param params
	 * @return
	 * @throws CommonException
	 */
	public List selectOrgHierarchy(Map _params) throws CommonException , Exception;
    
    
	/**
	 * 조직 TREE 구성을 위한 조직 목록 조회
	 * @param params
	 * @return
	 * @throws CommonException
	 */
    public void selectOrgHierarchyGroupby(Map params) throws CommonException;
    
    
    /**
     * 조직 정보 업데이트
     * @param params
     * @throws CommonException
     */
    public void updateOrgHierarchy(Map params) throws CommonException;
    
    
}
