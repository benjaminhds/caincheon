// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExcelDao.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

/*
 * 
 */
public interface ExcelDao
{

    public List getBooks(Map map);

    public List getBooksDetail(Map map);
}
