// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SupportDao.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

/*
 * 
 */
public interface SupportDao
{

    public abstract List cureList(Map map);

    public abstract int cureListCount(Map map);

    public abstract Map cureContents(Map map);
}
