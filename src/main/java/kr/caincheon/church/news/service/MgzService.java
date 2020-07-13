// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MgzService.java

package kr.caincheon.church.news.service;

import java.util.List;
import java.util.Map;

public interface MgzService
{

    public List mgzList(Map map);

    public int mgzListCount(Map map);

    public Map mgzContents(Map map);

    public String mgzMaxNo(Map map);

    public String mgzTitle(Map map);

    public boolean mgzInsert(Map map);

    public boolean mgzModify(Map map);

    public boolean mgzDelete(Map map);
}
