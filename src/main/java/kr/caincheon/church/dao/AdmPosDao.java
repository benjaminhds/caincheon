// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmPosDao.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonException;

public interface AdmPosDao
{

    public abstract List posList(Map map)
        throws CommonException;

    public abstract int posListCount(Map map)
        throws CommonException;

    public abstract String admPosManage(Map map)
        throws CommonException;
}
