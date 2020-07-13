// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FaqDao.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

/*
 * 
 */
public interface FaqDao
{

    public List faqList(Map map);

    public int faqListCount(Map map);

    public Map faqContents(Map map);

    public boolean faqInsert(Map map);

    public boolean faqModify(Map map);

    public boolean faqDelete(Map map);
}
