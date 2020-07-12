// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SchDao.java

package kr.caincheon.church.dao;

import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;

public interface SchDao
{

    public abstract CommonDaoDTO schList(Map map);

    //public abstract int schListCount(Map map);

    public abstract Map schContents(Map map);

    public abstract Map schCView(Map map);

    public abstract Map schView(Map map);

    public abstract boolean schInsert(Map map);

    public abstract boolean schModify(Map map);

    public abstract boolean schDelete(Map map);

    public abstract Map scheduleByDiary(Map map) throws Exception;

}
