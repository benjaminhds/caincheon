// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MgzDao.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

public interface MgzDao
{

    public abstract List mgzList(Map map);

    public abstract int mgzListCount(Map map);

    public abstract Map mgzContents(Map map);

    public abstract String mgzMaxNo(Map map);

    public abstract String mgzTitle(Map map);

    public abstract boolean mgzInsert(Map map);

    public abstract boolean mgzModify(Map map);

    public abstract boolean mgzDelete(Map map);
}
