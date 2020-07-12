// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NewsDao.java

package kr.caincheon.church.dao;

import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;

public interface NewsDao
{

    public abstract String getBoardCategory(Map map);

    public abstract CommonDaoDTO newsList(Map map);

    public abstract Map newsContents(Map map);

    public abstract void schList(Map map, CommonDaoDTO dto);

    public abstract Map schContents(Map map);
}
