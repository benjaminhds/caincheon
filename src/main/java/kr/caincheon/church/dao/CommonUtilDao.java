// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CommonUtilDao.java

package kr.caincheon.church.dao;

import java.util.List;

/*
 * 
 */
public interface CommonUtilDao
{

    public List getDepartList(String s)
        throws Exception;

    public List getDGroupCodeList(String s)
        throws Exception;

    public List getPosList(String s)
        throws Exception;
    
    // 코드 조회
    public List getCodeList(String code) throws Exception;
    
    // 코드인스턴스 조회
    public List getCodeInstanceList(String code, String orderbyCuase) throws Exception;
}
