// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BannerDao.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;

public interface BannerDao
{

    public void bannerList(Map map, CommonDaoDTO dto);

//    public int bannerListCount(Map map);

    public Map bannerContents(Map map);

    public boolean bannerInsert(Map map, List uploadedFiles);

    public boolean bannerModify(Map map, List uploadedFiles);

    public boolean bannerDelete(Map map);

    public Map getMember(Map map);
}
