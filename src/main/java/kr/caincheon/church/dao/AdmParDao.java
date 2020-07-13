// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmParDao.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

/*
 * 
 */
public interface AdmParDao
{

    public List parList(Map map);

    public int parListCount(Map map);

    public Map parContents(Map map);

    public boolean admParInsert(Map map, List list);

    public boolean admParModify(Map map, List list);

    public boolean admParDelete(Map map);

    public Map getMember(Map map);
}
