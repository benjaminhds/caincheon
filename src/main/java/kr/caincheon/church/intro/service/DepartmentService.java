// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DepartmentService.java

package kr.caincheon.church.intro.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoMultiDTO;

public interface DepartmentService
{

    public abstract void selectDepartment(Map map, CommonDaoMultiDTO daodto)
        throws Exception;

    public abstract void freeDataSource();
    
    
    /*
     * 통합검색 서비스 :: 교구청 ( Called by UnifySearchServiceImpl)
     */
    public List unifySearch(Map _params, Connection conn) throws Exception ;
}
