// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmailDao.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

/*
 * 
 */
public interface EmailDao
{

    public abstract List emailList(Map map);

    public abstract int emailListCount(Map map);

    public abstract Map emailContents(Map map);

    public abstract boolean emailInsert(Map map);

    public abstract boolean emailModify(Map map);

    public abstract boolean emailDelete(Map map);

    public abstract Map getMember(Map map);
}
