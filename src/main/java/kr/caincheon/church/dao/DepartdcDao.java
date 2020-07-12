// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DepartdcDao.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonException;

public interface DepartdcDao
{

    public abstract List departdcList(Map map);

    public abstract int departdcListCount(Map map);

    public abstract Map departdcContents(Map map) throws CommonException;

    public abstract List priestList(Map map);

    public abstract List departdcViewGetPriestList(Map map);

    public abstract List _1x000List(Map map);

    public abstract boolean departdcInsert(Map map);

    public abstract boolean departdcModify(Map map) throws CommonException;

    public abstract boolean departdcDelete(Map map);
}
