// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NBoardDao.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

public interface AdmNewsDao
{

    public abstract List newsList(Map map, String s);

    public abstract int newsListCount(Map map, String s);

    public abstract Map newsContents(Map map);

    public abstract boolean admNewsInsert(Map map, List list);

    public abstract boolean admNewsModify(Map map, List list);

    public abstract boolean admNewsDelete(Map map);

    public abstract Map getMember(Map map);
    
}
