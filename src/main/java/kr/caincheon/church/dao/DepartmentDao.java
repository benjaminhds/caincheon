// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DepartmentDao.java

package kr.caincheon.church.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.BaseDao;

public interface DepartmentDao
    extends BaseDao
{

    List<Map> selectDepartment(String lv1_of_org_idx, String lv2_of_org_idx, String lv3_of_org_idx) throws Exception;
 

    /*
     * 통합검색 서비스 :: 교구청 ( Called by UnifySearchServiceImpl)
     */
    List<Map> unifySearch(Map _params, Connection conn) throws Exception ;
}
