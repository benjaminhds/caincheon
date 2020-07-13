// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ParDao.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

public interface ParDao
{

    public List parList(Map map);

    public int parListCount(Map map);

    public Map parContents(Map map);
}
