// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FaqDao.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

public interface FaqDao
{

    public abstract List faqList(Map map);

    public abstract int faqListCount(Map map);

    public abstract Map faqContents(Map map);

    public abstract boolean faqInsert(Map map);

    public abstract boolean faqModify(Map map);

    public abstract boolean faqDelete(Map map);
}
