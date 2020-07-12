// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MainDao.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

public interface MainDao
{

    public abstract Map todayContents();

    public abstract List noticeList(String s);

    public abstract List parishList();

    public abstract List priestListOfThisMonth();

    public abstract List parList_Main();

    public abstract Map<String, Object> schList_Main(Map _params);
}
