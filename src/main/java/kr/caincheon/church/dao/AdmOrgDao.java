// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmOrgDao.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonException;

public interface AdmOrgDao
{

    public abstract List orgList(Map map)
        throws CommonException;

    public abstract int orgListCount(Map map)
        throws CommonException;

    public abstract List welfareCodeList(Map map)
        throws CommonException;

    public abstract String admOrgManage(Map map)
        throws CommonException;
}
