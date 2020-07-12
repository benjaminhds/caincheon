// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BannerDao.java

package kr.caincheon.church.admin.dao;

import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonException;

public interface OrgHierarchyDao
{

	// management a code_master 
	public int insertOrgHierarchy(Map params) throws CommonException, Exception;
	public int updateOrgHierarchy(Map params) throws CommonException, Exception;
	public int deleteOrgHierarchy(Map params) throws CommonException, Exception;
	public int deleteRecoveryOrgHierarchy(Map params) throws CommonException, Exception;
	
	public List<Map> selectOrgHierarchyGroupby(int level, String whereLv1InCaseLv1) throws CommonException, Exception;
	public List<Map> selectOrgHierarchy(Map params) throws CommonException, Exception;
	public List<Map> selectOrgHierarchyPaging(Map params) throws CommonException, Exception;
	
	// common 
	public int totalCount() throws CommonException, Exception;
	public int selectOrgHierarchyTotalcount(Map params) throws CommonException, Exception;
}
