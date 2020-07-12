// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DgroupDao.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

public interface DgroupDao
{

    public abstract List dgroupList(Map map);

    public abstract int dgroupListCount(Map map);

    public abstract Map dgroupContents(Map map);

    public abstract boolean dgroupInsert(Map map);

    public abstract boolean dgroupModify(Map map);

    public abstract boolean dgroupDelete(Map map);
}
