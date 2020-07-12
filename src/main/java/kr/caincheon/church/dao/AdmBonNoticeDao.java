// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmBonNoticeDao.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

public interface AdmBonNoticeDao
{

    public abstract List admBonNoticeList(Map map);

    public abstract int admBonNoticeListCount(Map map);

    public abstract Map admBonNoticeContents(Map map);

    public abstract boolean admBonNoticeInsert(Map map, List list);

    public abstract boolean admBonNoticeModify(Map map, List list);

    public abstract boolean admBonNoticeDelete(Map map);

    public abstract Map getMember(Map map);

    public abstract List _1x00xList(Map map);
}
