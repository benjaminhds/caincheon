// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MovDao.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;

public interface MovDao
{

    public abstract String getBoardCategory(Map map);

    public abstract void movList(Map map, CommonDaoDTO dto);

    public abstract Map movContents(Map map);

    public abstract void movDownloads(Map map, CommonDaoDTO dto);

    public abstract void youtubeList(Map map, CommonDaoDTO dto);
}
