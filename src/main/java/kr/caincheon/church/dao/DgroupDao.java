// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DgroupDao.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

/*
 * 
 */
public interface DgroupDao
{

    public List dgroupList(Map map);

    public int dgroupListCount(Map map);

    public Map dgroupContents(Map map);

    public boolean dgroupInsert(Map map);

    public boolean dgroupModify(Map map);

    public boolean dgroupDelete(Map map);
}
